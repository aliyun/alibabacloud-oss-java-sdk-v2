package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MimeUtils;

import java.io.*;
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

    public UploadResult uploadFrom(PutObjectRequest request, InputStream body) throws UploadError {
        return uploadFrom(request, body, null);
    }

    public UploadResult uploadFrom(PutObjectRequest request, InputStream body, UploaderOptions overrideOptions) throws UploadError {
        validateRequest(request);

        if (body == null) {
            throw new IllegalArgumentException("the body is null");
        }

        // Try to determine total size for known stream types
        Long totalSize = null;
        
        // For FileInputStream, use FileChannel.size()
        if (body instanceof FileInputStream) {
            try {
                java.nio.channels.FileChannel channel = ((FileInputStream) body).getChannel();
                totalSize = channel.size();
            } catch (IOException e) {
                // ignore, size will be unknown
            }
        } else if (body instanceof ByteArrayInputStream) {
            try {
                totalSize = (long) body.available();
            } catch (IOException e) {
                // ignore, size will be unknown
            }
        }

        // Convert InputStream to BinaryData with size information if available
        BinaryData binaryData = BinaryData.fromStream(body, totalSize);
        
        // Delegate to BinaryData version
        return uploadFrom(request, binaryData, overrideOptions);
    }

    /**
     * Uploads data from a BinaryData source.
     * This method leverages the known content length from BinaryData for accurate multipart upload planning.
     *
     * @param request The put object request containing bucket, key, and other metadata
     * @param body    The BinaryData containing the data to upload
     * @return UploadResult containing upload metadata
     * @throws UploadError if upload fails
     */
    public UploadResult uploadFrom(PutObjectRequest request, BinaryData body) throws UploadError {
        return uploadFrom(request, body, null);
    }

    /**
     * Uploads data from a BinaryData source with override options.
     * This method leverages the known content length from BinaryData for accurate multipart upload planning.
     *
     * @param request       The put object request containing bucket, key, and other metadata
     * @param body          The BinaryData containing the data to upload
     * @param overrideOptions Override options for this upload operation
     * @return UploadResult containing upload metadata
     * @throws UploadError if upload fails
     */
    public UploadResult uploadFrom(PutObjectRequest request, BinaryData body, UploaderOptions overrideOptions) throws UploadError {
        validateRequest(request);

        if (body == null) {
            throw new IllegalArgumentException("the body is null");
        }

        UploaderOptions opts = overrideOptions != null ? overrideOptions : this.options;
        long partSize = opts.partSize();
        int parallelNum = opts.parallelNum();

        // Get total size from BinaryData - this is reliable as BinaryData knows its length
        Long length = body.getLength();
        long totalSize = length != null ? length : -1;

        // Adjust part size based on actual data size
        if (totalSize > 0) {
            while (totalSize / partSize >= Defaults.MAX_UPLOAD_PARTS) {
                partSize += opts.partSize();
            }
        }

        if (totalSize >= 0 && totalSize < partSize) {
            return singlePart(request, body.toStream(), totalSize);
        }

        return multiPart(request, body.toStream(), totalSize, partSize, parallelNum, opts.leavePartsOnError(),
                null, null);
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

        UploaderOptions opts = overrideOptions != null ? overrideOptions : this.options;
        long totalSize = file.length();
        long partSize = opts.partSize();
        int parallelNum = opts.parallelNum();

        // Adjust part size
        if (totalSize > 0) {
            while (totalSize / partSize >= Defaults.MAX_UPLOAD_PARTS) {
                partSize += opts.partSize();
            }
        }

        // Checkpoint support (only for file uploads)
        UploadCheckpoint checkpoint = null;
        boolean leavePartsOnError = opts.leavePartsOnError();
        if (opts.enableCheckpoint()) {
            String fileLastModified = String.valueOf(file.lastModified());
            checkpoint = UploadCheckpoint.create(
                    request.bucket(), request.key(), filePath,
                    opts.checkpointDir(), totalSize, fileLastModified, partSize);
            try {
                checkpoint.load();
            } catch (IOException e) {
                throw new UploadError("", "oss://" + request.bucket() + "/" + request.key(), e);
            }
            // When checkpoint is enabled, always leave parts on error so we can resume
            leavePartsOnError = true;
        }

        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new UploadError("", "oss://" + request.bucket() + "/" + request.key(), e);
        }

        try {
            // Detect content type from file extension
            String contentType = getContentType(filePath);

            if (totalSize < partSize && (checkpoint == null || !checkpoint.loaded)) {
                return singlePartFile(request, fis, totalSize, contentType);
            }

            return multiPartFile(request, fis, totalSize, partSize, parallelNum, leavePartsOnError,
                    contentType, checkpoint);
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    private void validateRequest(PutObjectRequest request) {
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

    private String getContentType(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            String mimeType = MimeUtils.getMimetype(filePath, "application/octet-stream");
            return mimeType;
        }
        return null;
    }

    private UploadResult singlePart(PutObjectRequest request, InputStream body, long totalSize) throws UploadError {
        PutObjectRequest.Builder builder = PutObjectRequest.newBuilder()
                .bucket(request.bucket())
                .key(request.key())
                .headers(request.headers())
                .parameters(request.parameters())
                .body(BinaryData.fromStream(body, totalSize >= 0 ? totalSize : null));

        try {
            PutObjectResult result = client.putObject(builder.build());
            return UploadResult.newBuilder()
                    .etag(result.eTag())
                    .versionId(result.versionId())
                    .hashCrc64ecma(result.hashCrc64ecma())
                    .headers(result.headers())
                    .statusCode(result.statusCode())
                    .build();
        } catch (Exception e) {
            throw new UploadError("", "oss://" + request.bucket() + "/" + request.key(), e);
        }
    }

    private UploadResult singlePartFile(PutObjectRequest request, FileInputStream fis, long totalSize, String contentType) throws UploadError {
        PutObjectRequest.Builder builder = PutObjectRequest.newBuilder()
                .bucket(request.bucket())
                .key(request.key())
                .headers(request.headers())
                .parameters(request.parameters())
                .body(BinaryData.fromStream(fis, totalSize));

        if (contentType != null && request.contentType() == null) {
            builder.contentType(contentType);
        }

        try {
            PutObjectResult result = client.putObject(builder.build());
            return UploadResult.newBuilder()
                    .etag(result.eTag())
                    .versionId(result.versionId())
                    .hashCrc64ecma(result.hashCrc64ecma())
                    .headers(result.headers())
                    .statusCode(result.statusCode())
                    .build();
        } catch (Exception e) {
            throw new UploadError("", "oss://" + request.bucket() + "/" + request.key(), e);
        }
    }

    private UploadResult multiPart(PutObjectRequest request, InputStream body, long totalSize,
                                   long partSize, int parallelNum, boolean leavePartsOnError,
                                   String contentType, UploadCheckpoint checkpoint) throws UploadError {
        return doMultiPart(request, body, totalSize, partSize, parallelNum, leavePartsOnError,
                contentType, checkpoint);
    }

    private UploadResult multiPartFile(PutObjectRequest request, FileInputStream fis, long totalSize,
                                       long partSize, int parallelNum, boolean leavePartsOnError,
                                       String contentType, UploadCheckpoint checkpoint) throws UploadError {
        return doMultiPart(request, fis, totalSize, partSize, parallelNum, leavePartsOnError,
                contentType, checkpoint);
    }

    private UploadResult doMultiPart(PutObjectRequest request, InputStream body, long totalSize,
                                     long partSize, int parallelNum, boolean leavePartsOnError,
                                     String contentType, UploadCheckpoint checkpoint) throws UploadError {
        String path = "oss://" + request.bucket() + "/" + request.key();

        // Check if we can resume from checkpoint
        String uploadId = null;
        int startPartNum = 0;
        long resumeOffset = 0;
        Set<Integer> uploadedPartNumbers = Collections.emptySet();

        if (checkpoint != null && checkpoint.loaded) {
            uploadId = checkpoint.info.data.uploadInfo.uploadId;
            // ListParts to find already uploaded parts
            try {
                ResumeInfo resumeInfo = getResumeInfo(request.bucket(), request.key(), uploadId, partSize);
                startPartNum = resumeInfo.partNumber;
                resumeOffset = resumeInfo.offset;
                uploadedPartNumbers = resumeInfo.uploadedPartNumbers;
            } catch (Exception e) {
                // Failed to list parts, start fresh
                uploadId = null;
                startPartNum = 0;
                resumeOffset = 0;
                uploadedPartNumbers = Collections.emptySet();
            }
        }

        // Initiate multipart upload if needed
        if (uploadId == null) {
            InitiateMultipartUploadRequest.Builder initBuilder = InitiateMultipartUploadRequest.newBuilder()
                    .bucket(request.bucket())
                    .key(request.key())
                    .headers(request.headers())
                    .parameters(request.parameters());

            if (contentType != null && request.contentType() == null) {
                initBuilder.contentType(contentType);
            }

            InitiateMultipartUploadResult initResult;
            try {
                initResult = client.initiateMultipartUpload(initBuilder.build());
            } catch (Exception e) {
                throw new UploadError("", path, e);
            }

            uploadId = initResult.initiateMultipartUpload().uploadId();
        }

        // Save checkpoint with uploadId
        if (checkpoint != null) {
            checkpoint.info.data.uploadInfo.uploadId = uploadId;
            try {
                checkpoint.dump();
            } catch (IOException e) {
                // best effort
            }
        }

        // Skip to resume offset if resuming
        if (resumeOffset > 0 && body instanceof FileInputStream) {
            try {
                java.nio.channels.FileChannel channel = ((FileInputStream) body).getChannel();
                channel.position(resumeOffset);
            } catch (IOException e) {
                // If seek fails, start from beginning
                startPartNum = 0;
                resumeOffset = 0;
                uploadedPartNumbers = Collections.emptySet();
            }
        }

        // Upload parts in parallel
        List<Part> completedParts = Collections.synchronizedList(new ArrayList<>());
        AtomicReference<Exception> firstError = new AtomicReference<>();
        AtomicLong transferred = new AtomicLong(0);

        // Add already uploaded parts to completedParts
        final Set<Integer> alreadyUploaded = uploadedPartNumbers;

        ExecutorService executor = Executors.newFixedThreadPool(parallelNum);
        List<Future<?>> futures = new ArrayList<>();
        final String finalUploadId = uploadId;

        try {
            long readerPos = resumeOffset;
            int partNum = startPartNum;

            if (body instanceof FileInputStream) {
                // File-based: use RandomAccessFile-like reading with sections
                FileInputStream fis = (FileInputStream) body;
                java.nio.channels.FileChannel channel = fis.getChannel();

                while (readerPos < totalSize && firstError.get() == null) {
                    partNum++;
                    long bytesLeft = totalSize - readerPos;
                    long currentPartSize = Math.min(bytesLeft, partSize);
                    final int currentPartNum = partNum;
                    final long currentOffset = readerPos;
                    final long currentSize = currentPartSize;

                    // Skip already uploaded parts
                    if (alreadyUploaded.contains(currentPartNum)) {
                        readerPos += currentPartSize;
                        continue;
                    }

                    futures.add(executor.submit(() -> {
                        if (firstError.get() != null) return;
                        try {
                            // Read data from channel at specific position
                            byte[] buffer = new byte[(int) currentSize];
                            int totalRead = 0;
                            synchronized (channel) {
                                channel.position(currentOffset);
                                java.io.InputStream partStream = java.nio.channels.Channels.newInputStream(channel);
                                while (totalRead < currentSize) {
                                    int read = partStream.read(buffer, totalRead, (int) currentSize - totalRead);
                                    if (read == -1) break;
                                    totalRead += read;
                                }
                            }

                            UploadPartRequest partRequest = UploadPartRequest.newBuilder()
                                    .bucket(request.bucket())
                                    .key(request.key())
                                    .parameter("uploadId", finalUploadId)
                                    .parameter("partNumber", String.valueOf(currentPartNum))
                                    .body(BinaryData.fromBytes(Arrays.copyOf(buffer, totalRead)))
                                    .build();

                            UploadPartResult partResult = client.uploadPart(partRequest);
                            completedParts.add(Part.newBuilder()
                                    .partNumber((long) currentPartNum)
                                    .eTag(partResult.eTag())
                                    .build());

                            transferred.addAndGet(totalRead);
                        } catch (Exception e) {
                            firstError.compareAndSet(null, e);
                        }
                    }));

                    readerPos += currentPartSize;
                }
            } else {
                // Stream-based: read sequentially, submit parts for upload
                while (firstError.get() == null) {
                    byte[] buffer = new byte[(int) partSize];
                    int totalRead = 0;
                    try {
                        while (totalRead < partSize) {
                            int read = body.read(buffer, totalRead, (int) partSize - totalRead);
                            if (read == -1) break;
                            totalRead += read;
                        }
                    } catch (IOException e) {
                        firstError.compareAndSet(null, e);
                        break;
                    }

                    if (totalRead == 0) break;

                    partNum++;
                    final int currentPartNum = partNum;
                    final byte[] partData = Arrays.copyOf(buffer, totalRead);
                    final int partDataLen = totalRead;

                    futures.add(executor.submit(() -> {
                        if (firstError.get() != null) return;
                        try {
                            UploadPartRequest partRequest = UploadPartRequest.newBuilder()
                                    .bucket(request.bucket())
                                    .key(request.key())
                                    .parameter("uploadId", finalUploadId)
                                    .parameter("partNumber", String.valueOf(currentPartNum))
                                    .body(BinaryData.fromBytes(partData))
                                    .build();

                            UploadPartResult partResult = client.uploadPart(partRequest);
                            completedParts.add(Part.newBuilder()
                                    .partNumber((long) currentPartNum)
                                    .eTag(partResult.eTag())
                                    .build());

                            transferred.addAndGet(partDataLen);
                        } catch (Exception e) {
                            firstError.compareAndSet(null, e);
                        }
                    }));

                    if (totalRead < partSize) break;
                }
            }

            // Wait for all parts to complete
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    if (firstError.get() == null) {
                        firstError.set(e.getCause() != null ? (Exception) e.getCause() : e);
                    }
                }
            }

        } finally {
            executor.shutdown();
        }

        // Check for errors
        if (firstError.get() != null) {
            if (!leavePartsOnError) {
                abortMultipartUpload(request.bucket(), request.key(), uploadId);
            }
            throw new UploadError(uploadId, path, firstError.get());
        }

        // Complete multipart upload - include both newly uploaded and previously uploaded parts
        // For resumed uploads, we need to re-list all parts to get complete list
        if (!alreadyUploaded.isEmpty()) {
            try {
                ListPartsResult listResult = client.listParts(ListPartsRequest.newBuilder()
                        .bucket(request.bucket())
                        .key(request.key())
                        .uploadId(uploadId)
                        .build());
                if (listResult.parts() != null) {
                    for (Part p : listResult.parts()) {
                        // Add parts that were already uploaded but not in our completedParts
                        boolean found = false;
                        for (Part cp : completedParts) {
                            if (cp.partNumber().equals(p.partNumber())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            completedParts.add(p);
                        }
                    }
                }
            } catch (Exception e) {
                // best effort
            }
        }

        completedParts.sort(Comparator.comparing(Part::partNumber));

        CompleteMultipartUploadRequest completeRequest = CompleteMultipartUploadRequest.newBuilder()
                .bucket(request.bucket())
                .key(request.key())
                .parameter("uploadId", uploadId)
                .completeMultipartUpload(CompleteMultipartUpload.newBuilder()
                        .parts(completedParts)
                        .build())
                .build();

        try {
            CompleteMultipartUploadResult cmResult = client.completeMultipartUpload(completeRequest);

            // Remove checkpoint on success
            if (checkpoint != null) {
                checkpoint.remove();
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
                abortMultipartUpload(request.bucket(), request.key(), uploadId);
            }
            throw new UploadError(uploadId, path, e);
        }
    }

    private static class ResumeInfo {
        int partNumber;
        long offset;
        Set<Integer> uploadedPartNumbers;
    }

    private ResumeInfo getResumeInfo(String bucket, String key, String uploadId, long partSize) {
        ResumeInfo info = new ResumeInfo();
        info.uploadedPartNumbers = new HashSet<>();

        int checkPartNumber = 1;
        ListPartsResult result = client.listParts(ListPartsRequest.newBuilder()
                .bucket(bucket)
                .key(key)
                .uploadId(uploadId)
                .build());

        if (result.parts() != null) {
            for (Part p : result.parts()) {
                if (p.partNumber() != null && p.partNumber() == checkPartNumber) {
                    info.uploadedPartNumbers.add(checkPartNumber);
                    checkPartNumber++;
                } else {
                    break;
                }
            }
        }

        info.partNumber = checkPartNumber - 1;
        info.offset = (long) info.partNumber * partSize;
        return info;
    }

    private void abortMultipartUpload(String bucket, String key, String uploadId) {
        try {
            AbortMultipartUploadRequest abortRequest = AbortMultipartUploadRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .parameter("uploadId", uploadId)
                    .build();
            client.abortMultipartUpload(abortRequest);
        } catch (Exception ignored) {
            // best effort abort
        }
    }
}
