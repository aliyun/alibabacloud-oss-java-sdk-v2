package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.exceptions.InconsistentException;
import com.aliyun.sdk.service.oss2.hash.CRC64;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MimeUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Uploader {
    private final OSSClient client;
    private final UploaderOptions options;

    public Uploader(OSSClient client) {
        this(client, UploaderOptions.defaults());
    }

    public Uploader(OSSClient client, UploaderOptions options) {
        this.client = Objects.requireNonNull(client, "client must not be null");
        this.options = Objects.requireNonNull(options, "options must not be null");
    }

    public UploaderOptions options() {
        return options;
    }

    public UploadResult uploadFrom(PutObjectRequest request, BinaryData body) throws UploadError {
        return uploadFrom(request, body, null);
    }

    public UploadResult uploadFrom(PutObjectRequest request, BinaryData body, UploaderOptions overrideOptions) throws UploadError {
        validateRequest(request);
        if (body == null) {
            throw new IllegalArgumentException("the body is null");
        }

        UploaderDelegate delegate = new UploaderDelegate(client, request, options, overrideOptions);
        delegate.adjustPartSize(body);

        if (delegate.totalSize >= 0 && delegate.totalSize < delegate.partSize) {
            return delegate.singlePart(body);
        }

        return delegate.multiPart(body);
    }

    public UploadResult uploadFile(PutObjectRequest request, String filePath) throws UploadError {
        return uploadFile(request, filePath, null);
    }

    public UploadResult uploadFile(PutObjectRequest request, String filePath, UploaderOptions overrideOptions) throws UploadError {
        validateRequest(request);
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("filePath is required");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not exists, " + filePath);
        }

        UploaderDelegate delegate = new UploaderDelegate(client, request, options, overrideOptions);
        delegate.prepareFileUpload(file, filePath);

        if (delegate.totalSize < delegate.partSize) {
            return delegate.singlePartFile(filePath);
        }
        return delegate.multiPartFile(filePath);
    }

    private static void validateRequest(PutObjectRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("null field, request");
        }
        if (request.bucket() == null) {
            throw new IllegalArgumentException("null field, request.Bucket");
        }
        if (request.key() == null) {
            throw new IllegalArgumentException("null field, request.Key");
        }
    }

    // -------------------------------------------------------------------------
    // UploaderDelegate per-upload state and logic
    // -------------------------------------------------------------------------

    private static class UploaderDelegate {
        private final OSSClient client;
        private final PutObjectRequest request;
        private final UploaderOptions opts;

        // Upload state
        long totalSize = -1;
        long partSize;
        private boolean leavePartsOnError;
        private String contentType;
        private UploadCheckpoint checkpoint;

        // CRC64
        private final boolean enableCRC;

        // Progress
        private final ProgressListener progressListener;
        private final AtomicLong transferred = new AtomicLong(0);

        UploaderDelegate(OSSClient client, PutObjectRequest request,
                         UploaderOptions defaultOpts, UploaderOptions overrideOptions) {
            this.client = client;
            this.request = request;
            this.opts = overrideOptions != null ? overrideOptions : defaultOpts;
            this.partSize = opts.partSize();
            this.leavePartsOnError = opts.leavePartsOnError();
            this.enableCRC = opts.enableCRC64Check();
            this.progressListener = request.progressListener();
        }

        /**
         * Determine total size from BinaryData and adjust part size to stay within MAX_UPLOAD_PARTS.
         */
        void adjustPartSize(BinaryData body) {
            Long length = body.getLength();
            if (length != null) {
                totalSize = length;
            }

            adjustPartSizeForTotal();
        }

        /**
         * Prepare file upload: set totalSize, contentType, checkpoint, and adjust partSize.
         */
        void prepareFileUpload(File file, String filePath) throws UploadError {
            this.totalSize = file.length();
            this.contentType = detectContentType(filePath);

            adjustPartSizeForTotal();

            if (opts.enableCheckpoint()) {
                String fileLastModified = String.valueOf(file.lastModified());
                this.checkpoint = UploadCheckpoint.create(
                        request.bucket(), request.key(), filePath,
                        opts.checkpointDir(), totalSize, fileLastModified, partSize);
                try {
                    checkpoint.load();
                } catch (IOException e) {
                    throw new UploadError("", ossPath(), e);
                }
                this.leavePartsOnError = true;
            }
        }

        private void adjustPartSizeForTotal() {
            if (totalSize > 0) {
                while (totalSize / partSize >= Defaults.MAX_UPLOAD_PARTS) {
                    partSize += opts.partSize();
                }
            }
        }

        // -- Single part uploads --

        UploadResult singlePart(BinaryData body) throws UploadError {
            try {
                PutObjectResult result = client.putObject(request.toBuilder()
                        .body(body)
                        .build());
                return buildUploadResult(result);
            } catch (Exception e) {
                throw new UploadError("", ossPath(), e);
            }
        }

        UploadResult singlePartFile(String filePath) throws UploadError {
            try (FileInputStream fis = new FileInputStream(filePath)) {
                PutObjectRequest.Builder builder = request.toBuilder()
                        .body(BinaryData.fromStream(fis, totalSize));
                applyContentType(builder);
                PutObjectResult result = client.putObject(builder.build());
                return buildUploadResult(result);
            } catch (Exception e) {
                throw new UploadError("", ossPath(), e);
            }
        }

        private UploadResult buildUploadResult(PutObjectResult result) {
            return UploadResult.newBuilder()
                    .etag(result.eTag())
                    .versionId(result.versionId())
                    .hashCrc64ecma(result.hashCrc64ecma())
                    .headers(result.headers())
                    .statusCode(result.statusCode())
                    .build();
        }

        // -- Multipart uploads --

        UploadResult multiPart(BinaryData body) throws UploadError {
            return doMultiPart(body);
        }

        UploadResult multiPartFile(String filePath) throws UploadError {
            return doMultiPartFile(filePath);
        }

        private UploadResult doMultiPartFile(String filePath) throws UploadError {
            // Try to resume from checkpoint
            String uploadId = null;
            List<Part> completedParts = Collections.synchronizedList(new ArrayList<>());

            if (checkpoint != null && checkpoint.loaded) {
                uploadId = checkpoint.info.data.uploadInfo.uploadId;
                try {
                    for (ListPartsResult result : client.listPartsPaginator(buildListPartsRequest(uploadId))) {
                        for (Part p : result.parts()) {
                            if (p.partNumber() == null || p.size() == null) continue;
                            if (p.size() == partSize) {
                                completedParts.add(p);
                            }
                        }
                    }
                } catch (Exception e) {
                    uploadId = null;
                    completedParts.clear();
                }
            }

            // Report resumed progress
            if (progressListener != null && !completedParts.isEmpty()) {
                long resumedBytes = (long) completedParts.size() * partSize;
                transferred.set(resumedBytes);
                progressListener.onProgress(resumedBytes, resumedBytes, totalSize);
            }

            if (uploadId == null) {
                uploadId = initiateUpload();
            }

            // Save checkpoint with uploadId
            if (checkpoint != null) {
                checkpoint.info.data.uploadInfo.uploadId = uploadId;
                try {
                    checkpoint.dump();
                } catch (IOException ignored) {
                }
            }

            AtomicReference<Exception> firstError = new AtomicReference<>();

            uploadPartsFile(filePath, uploadId, completedParts, firstError);

            if (firstError.get() != null) {
                if (!leavePartsOnError) {
                    abortMultipartUpload(uploadId);
                }
                throw new UploadError(uploadId, ossPath(), firstError.get());
            }

            return completeUpload(uploadId, completedParts);
        }

        private UploadResult doMultiPart(BinaryData body) throws UploadError {
            String uploadId = initiateUpload();

            List<Part> completedParts = Collections.synchronizedList(new ArrayList<>());
            AtomicReference<Exception> firstError = new AtomicReference<>();

            uploadParts(body, uploadId, completedParts, firstError);

            if (firstError.get() != null) {
                if (!leavePartsOnError) {
                    abortMultipartUpload(uploadId);
                }
                throw new UploadError(uploadId, ossPath(), firstError.get());
            }

            return completeUpload(uploadId, completedParts);
        }

        private String initiateUpload() throws UploadError {
            InitiateMultipartUploadRequest.Builder initBuilder = InitiateMultipartUploadRequest.newBuilder()
                    .bucket(request.bucket())
                    .key(request.key())
                    .headers(request.headers())
                    .parameters(request.parameters());

            if (contentType != null && request.contentType() == null) {
                initBuilder.contentType(contentType);
            }

            try {
                InitiateMultipartUploadResult initResult = client.initiateMultipartUpload(initBuilder.build());
                return initResult.initiateMultipartUpload().uploadId();
            } catch (Exception e) {
                throw new UploadError("", ossPath(), e);
            }
        }

        /**
         * Sequentially reads chunks from the body via nextChunk and submits them
         * for parallel upload.
         */
        private void uploadParts(BinaryData body, String uploadId,
                                 List<Part> completedParts,
                                 AtomicReference<Exception> firstError) {
            InputStream stream = body.toStream();

            ByteBufferPool pool = new ByteBufferPool((int) partSize, opts.parallelNum() + 1);
            ExecutorService executor = Executors.newFixedThreadPool(opts.parallelNum());
            List<Future<?>> futures = new ArrayList<>();
            final String uid = uploadId;

            try {
                int partNum = 0;
                long readerPos = 0;

                while (firstError.get() == null) {
                    DataChunk chunk;
                    try {
                        chunk = nextChunk(stream, readerPos, pool);
                    } catch (IOException e) {
                        firstError.compareAndSet(null, e);
                        break;
                    }
                    if (chunk == null) break;

                    readerPos += chunk.size;
                    partNum++;
                    final int currentPartNum = partNum;
                    final int currentChunkSize = chunk.size;

                    final DataChunk c = chunk;
                    futures.add(executor.submit(() -> {
                        if (firstError.get() != null) {
                            pool.release(c.buffer);
                            return;
                        }
                        try {
                            uploadPart(uid, currentPartNum, c.data, currentChunkSize, completedParts);
                        } catch (Exception e) {
                            firstError.compareAndSet(null, e);
                        } finally {
                            pool.release(c.buffer);
                        }
                    }));

                    if (chunk.last) break;
                }

                waitForFutures(futures, firstError);
            } finally {
                executor.shutdown();
            }
        }

        /**
         * Uploads file parts in parallel. Each worker opens its own FileInputStream
         * and seeks to the chunk offset, avoiding memory buffering.
         * Parts in {@code alreadyUploaded} are skipped for checkpoint resume.
         */
        private void uploadPartsFile(String filePath, String uploadId,
                                     List<Part> completedParts,
                                     AtomicReference<Exception> firstError) {
            Set<Integer> alreadyUploaded = new HashSet<>();
            for (Part p : completedParts) {
                alreadyUploaded.add(p.partNumber().intValue());
            }

            ExecutorService executor = Executors.newFixedThreadPool(opts.parallelNum());
            List<Future<?>> futures = new ArrayList<>();
            final String uid = uploadId;

            try {
                int partNum = 0;
                long offset = 0;

                while (offset < totalSize && firstError.get() == null) {
                    long chunkSize = Math.min(partSize, totalSize - offset);
                    partNum++;

                    if (alreadyUploaded.contains(partNum)) {
                        offset += chunkSize;
                        continue;
                    }

                    final int currentPartNum = partNum;
                    final long currentOffset = offset;
                    final long currentChunkSize = chunkSize;

                    futures.add(executor.submit(() -> {
                        if (firstError.get() != null) return;
                        try (FileInputStream fis = new FileInputStream(filePath)) {
                            long skipped = 0;
                            while (skipped < currentOffset) {
                                long n = fis.skip(currentOffset - skipped);
                                if (n <= 0) break;
                                skipped += n;
                            }
                            BinaryData data = BinaryData.fromStream(fis, currentChunkSize);
                            uploadPart(uid, currentPartNum, data, (int) currentChunkSize, completedParts);
                        } catch (Exception e) {
                            firstError.compareAndSet(null, e);
                        }
                    }));

                    offset += chunkSize;
                }

                waitForFutures(futures, firstError);
            } finally {
                executor.shutdown();
            }
        }

        /**
         * Reads the next chunk from the stream into a pooled ByteBuffer. Returns null on EOF.
         */
        private DataChunk nextChunk(InputStream stream, long readerPos, ByteBufferPool pool) throws IOException {
            long bytesLeft = totalSize >= 0 ? totalSize - readerPos : Long.MAX_VALUE;
            if (bytesLeft <= 0) return null;

            int chunkSize = (int) Math.min(partSize, bytesLeft);
            ByteBuffer buffer = pool.acquire();
            buffer.clear();
            if (chunkSize < buffer.capacity()) {
                buffer.limit(chunkSize);
            }

            int totalRead = readFill(stream, buffer);
            if (totalRead == 0) {
                pool.release(buffer);
                return null;
            }

            buffer.flip();
            BinaryData data = BinaryData.fromByteBuffer(buffer);
            return new DataChunk(data, buffer, totalRead, totalRead < chunkSize);
        }

        private static class DataChunk {
            final BinaryData data;
            final ByteBuffer buffer;
            final int size;
            final boolean last;

            DataChunk(BinaryData data, ByteBuffer buffer, int size, boolean last) {
                this.data = data;
                this.buffer = buffer;
                this.size = size;
                this.last = last;
            }
        }

        /**
         * Reads from the stream into the ByteBuffer until it is full or EOF is reached.
         * Returns the number of bytes actually read.
         */
        private static int readFill(InputStream stream, ByteBuffer buffer) throws IOException {
            byte[] array = buffer.array();
            int offset = buffer.arrayOffset() + buffer.position();
            int totalRead = 0;
            while (buffer.hasRemaining()) {
                int read = stream.read(array, offset + totalRead, buffer.remaining());
                if (read == -1) break;
                totalRead += read;
                buffer.position(buffer.position() + read);
            }
            return totalRead;
        }

        /**
         * A simple pool of fixed-size ByteBuffers for reuse across upload chunks.
         */
        private static class ByteBufferPool {
            private final int bufferSize;
            private final ArrayBlockingQueue<ByteBuffer> pool;

            ByteBufferPool(int bufferSize, int capacity) {
                this.bufferSize = bufferSize;
                this.pool = new ArrayBlockingQueue<>(capacity);
            }

            ByteBuffer acquire() {
                ByteBuffer buf = pool.poll();
                if (buf != null) {
                    return buf;
                }
                return ByteBuffer.allocate(bufferSize);
            }

            void release(ByteBuffer buffer) {
                if (buffer != null && buffer.capacity() == bufferSize) {
                    pool.offer(buffer);
                }
            }
        }

        private void uploadPart(String uploadId, int partNum, BinaryData body, int dataSize,
                                List<Part> completedParts) {
            UploadPartRequest.Builder partBuilder = UploadPartRequest.newBuilder()
                    .bucket(request.bucket())
                    .key(request.key())
                    .uploadId(uploadId)
                    .partNumber((long) partNum)
                    .body(body);

            if (request.requestPayer() != null) {
                partBuilder.requestPayer(request.requestPayer());
            }

            UploadPartRequest partRequest = partBuilder.build();

            UploadPartResult partResult = client.uploadPart(partRequest);
            Part.Builder pb = Part.newBuilder()
                    .partNumber((long) partNum)
                    .eTag(partResult.eTag())
                    .size((long) dataSize);
            if (enableCRC) {
                pb.hashCrc64ecma(partResult.hashCRC64());
            }
            completedParts.add(pb.build());

            if (progressListener != null) {
                long total = transferred.addAndGet(dataSize);
                progressListener.onProgress(dataSize, total, totalSize);
            }
        }

        private UploadResult completeUpload(String uploadId, List<Part> completedParts) throws UploadError {
            completedParts.sort(Comparator.comparing(Part::partNumber));

            CompleteMultipartUploadRequest.Builder completeBuilder = CompleteMultipartUploadRequest.newBuilder()
                    .bucket(request.bucket())
                    .key(request.key())
                    .uploadId(uploadId)
                    .completeMultipartUpload(CompleteMultipartUpload.newBuilder()
                            .parts(completedParts)
                            .build());

            if (request.objectAcl() != null) {
                completeBuilder.objectAcl(request.objectAcl());
            }
            if (request.requestPayer() != null) {
                completeBuilder.requestPayer(request.requestPayer());
            }

            CompleteMultipartUploadRequest completeRequest = completeBuilder.build();

            try {
                CompleteMultipartUploadResult cmResult = client.completeMultipartUpload(completeRequest);

                if (enableCRC && cmResult.hashCRC64() != null) {
                    verifyCRC64(completedParts, cmResult.hashCRC64(), cmResult.headers());
                }

                if (checkpoint != null) {
                    checkpoint.remove();
                }

                if (progressListener != null) {
                    progressListener.onFinish();
                }

                return UploadResult.newBuilder()
                        .uploadId(uploadId)
                        .etag(cmResult.completeMultipartUpload() != null ? cmResult.completeMultipartUpload().eTag() : null)
                        .versionId(cmResult.versionId())
                        .hashCrc64ecma(cmResult.hashCRC64())
                        .headers(cmResult.headers())
                        .statusCode(cmResult.statusCode())
                        .build();
            } catch (Exception e) {
                if (!leavePartsOnError) {
                    abortMultipartUpload(uploadId);
                }
                throw new UploadError(uploadId, ossPath(), e);
            }
        }

        private void abortMultipartUpload(String uploadId) {
            try {
                AbortMultipartUploadRequest.Builder abortBuilder = AbortMultipartUploadRequest.newBuilder()
                        .bucket(request.bucket())
                        .key(request.key())
                        .uploadId(uploadId);

                if (request.requestPayer() != null) {
                    abortBuilder.requestPayer(request.requestPayer());
                }

                AbortMultipartUploadRequest abortRequest = abortBuilder.build();
                client.abortMultipartUpload(abortRequest);
            } catch (Exception ignored) {
            }
        }

        private void applyContentType(PutObjectRequest.Builder builder) {
            if (contentType != null && request.contentType() == null) {
                builder.contentType(contentType);
            }
        }

        private static void waitForFutures(List<Future<?>> futures, AtomicReference<Exception> firstError) {
            for (Future<?> future : futures) {
                if (firstError.get() != null) {
                    future.cancel(false);
                    continue;
                }
                try {
                    future.get();
                } catch (CancellationException ignored) {
                } catch (InterruptedException | ExecutionException e) {
                    if (firstError.get() == null) {
                        firstError.set(e.getCause() != null ? (Exception) e.getCause() : e);
                    }
                }
            }
        }

        private static String detectContentType(String filePath) {
            if (filePath != null && !filePath.isEmpty()) {
                return MimeUtils.getMimetype(filePath, "application/octet-stream");
            }
            return null;
        }

        private ListPartsRequest buildListPartsRequest(String uploadId) {
            ListPartsRequest.Builder builder = ListPartsRequest.newBuilder()
                    .bucket(request.bucket())
                    .key(request.key())
                    .uploadId(uploadId);

            if (request.requestPayer() != null) {
                builder.requestPayer(request.requestPayer());
            }

            return builder.build();
        }


        private void verifyCRC64(List<Part> parts, String serverCRC, Map<String, String> headers) {
            long crc = 0;
            for (Part p : parts) {
                if (p.hashCrc64ecma() == null || p.size() == null) return;
                long value = Long.parseUnsignedLong(p.hashCrc64ecma());
                crc = CRC64.combine(crc, value, p.size());
            }
            String clientCRC = Long.toUnsignedString(crc);
            if (!clientCRC.equals(serverCRC)) {
                throw new InconsistentException(clientCRC, serverCRC, headers);
            }
        }

        String ossPath() {
            return "oss://" + request.bucket() + "/" + request.key();
        }
    }
}
