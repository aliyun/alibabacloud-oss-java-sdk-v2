package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.exceptions.InconsistentException;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Copier {
    private static final Set<String> METADATA_COPIED = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    static {
        METADATA_COPIED.add("content-type");
        METADATA_COPIED.add("content-language");
        METADATA_COPIED.add("content-encoding");
        METADATA_COPIED.add("content-disposition");
        METADATA_COPIED.add("cache-control");
        METADATA_COPIED.add("expires");
    }

    private final OSSClient client;
    private final CopierOptions options;

    public Copier(OSSClient client) {
        this(client, CopierOptions.defaults());
    }

    public Copier(OSSClient client, CopierOptions options) {
        this.client = Objects.requireNonNull(client, "client must not be null");
        this.options = Objects.requireNonNull(options, "options must not be null");
    }

    public CopierOptions options() {
        return options;
    }

    public CopyResult copy(CopyObjectRequest request) throws CopyError {
        return copy(request, null);
    }

    public CopyResult copy(CopyObjectRequest request, CopierOptions overrideOptions) throws CopyError {
        validateRequest(request);

        CopierDelegate delegate = new CopierDelegate(client, request, options, overrideOptions);
        delegate.checkSource();
        delegate.adjustPartSize();
        return delegate.doCopy();
    }

    private static void validateRequest(CopyObjectRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("null field, request");
        }
        if (request.bucket() == null) {
            throw new IllegalArgumentException("null field, request.Bucket");
        }
        if (request.key() == null) {
            throw new IllegalArgumentException("null field, request.Key");
        }
        if (request.sourceKey() == null) {
            throw new IllegalArgumentException("null field, request.SourceKey");
        }
    }

    // -------------------------------------------------------------------------
    // CopierDelegate per-copy state and logic
    // -------------------------------------------------------------------------

    private static class CopierDelegate {
        private final OSSClient client;
        private final CopyObjectRequest request;
        private final CopierOptions opts;

        // Source info
        private HeadObjectResult headResult;
        private long sizeInBytes;
        private long partSize;
        private final boolean leavePartsOnError;
        private final boolean enableCRC;

        // Progress
        private final ProgressListener progressListener;
        private final AtomicLong transferred = new AtomicLong(0);

        CopierDelegate(OSSClient client, CopyObjectRequest request,
                        CopierOptions defaultOpts, CopierOptions overrideOptions) {
            this.client = client;
            this.request = request;
            this.opts = overrideOptions != null ? overrideOptions : defaultOpts;
            this.partSize = opts.partSize();
            this.leavePartsOnError = opts.leavePartsOnError();
            this.enableCRC = opts.enableCRC64Check();
            this.progressListener = request.progressListener();
        }

        void checkSource() throws CopyError {
            String sourceBucket = request.sourceBucket() != null ? request.sourceBucket() : request.bucket();
            String sourceKey = request.sourceKey();

            try {
                HeadObjectRequest.Builder headBuilder = HeadObjectRequest.newBuilder()
                        .bucket(sourceBucket)
                        .key(sourceKey);
                if (request.sourceVersionId() != null) {
                    headBuilder.versionId(request.sourceVersionId());
                }
                headResult = client.headObject(headBuilder.build());
            } catch (Exception e) {
                throw new CopyError("", ossPath(), e);
            }

            sizeInBytes = headResult.contentLength() != null ? headResult.contentLength() : 0;
        }

        void adjustPartSize() {
            if (sizeInBytes > 0) {
                while (sizeInBytes / partSize >= Defaults.MAX_UPLOAD_PARTS) {
                    partSize += opts.partSize();
                }
            }
        }

        CopyResult doCopy() throws CopyError {
            if (sizeInBytes <= opts.multipartCopyThreshold()) {
                return singleCopy();
            }

            if (!opts.disableShallowCopy() && canUseShallowCopy()) {
                return shallowCopy();
            }

            return multiCopy();
        }

        private boolean canUseShallowCopy() {
            // Change StorageClass
            if (request.storageClass() != null && !request.storageClass().isEmpty()) {
                return false;
            }

            // Cross bucket
            if (!opts.noCheckCrossBucket()) {
                if (request.sourceBucket() != null && !request.sourceBucket().equals(request.bucket())) {
                    return false;
                }
            }

            // Server-side encryption
            if (!opts.noCheckSSE()) {
                if (headResult.serverSideEncryption() != null && !headResult.serverSideEncryption().isEmpty()) {
                    return false;
                }
            }

            return true;
        }

        private CopyResult singleCopy() throws CopyError {
            try {
                CopyObjectResult result = client.copyObject(request);

                // Progress
                if (progressListener != null) {
                    transferred.set(sizeInBytes);
                    progressListener.onProgress(sizeInBytes, sizeInBytes, sizeInBytes);
                    progressListener.onFinish();
                }

                return CopyResult.newBuilder()
                        .etag(result.eTag())
                        .versionId(result.versionId())
                        .hashCrc64ecma(result.hashCrc64())
                        .headers(result.headers())
                        .statusCode(result.statusCode())
                        .build();
            } catch (Exception e) {
                throw new CopyError("", ossPath(), e);
            }
        }

        private CopyResult shallowCopy() throws CopyError {
            try {
                CopyObjectResult result = client.copyObject(request);

                // Progress
                if (progressListener != null) {
                    transferred.set(sizeInBytes);
                    progressListener.onProgress(sizeInBytes, sizeInBytes, sizeInBytes);
                    progressListener.onFinish();
                }

                return CopyResult.newBuilder()
                        .etag(result.eTag())
                        .versionId(result.versionId())
                        .hashCrc64ecma(result.hashCrc64())
                        .headers(result.headers())
                        .statusCode(result.statusCode())
                        .build();
            } catch (Exception e) {
                if (isTimeoutException(e)) {
                    return multiCopy();
                }
                throw new CopyError("", ossPath(), e);
            }
        }

        private CopyResult multiCopy() throws CopyError {
            // Initiate multipart upload
            InitiateMultipartUploadRequest.Builder initBuilder = InitiateMultipartUploadRequest.newBuilder()
                    .bucket(request.bucket())
                    .key(request.key())
                    .headers(request.headers())
                    .parameters(request.parameters());

            applyMetadataForMultipart(initBuilder);
            applyTaggingForMultipart(initBuilder);

            InitiateMultipartUploadResult initResult;
            try {
                initResult = client.initiateMultipartUpload(initBuilder.build());
            } catch (Exception e) {
                throw new CopyError("", ossPath(), e);
            }

            String uploadId = initResult.initiateMultipartUpload().uploadId();

            // Upload parts in parallel using UploadPartCopy
            List<Part> completedParts = Collections.synchronizedList(new ArrayList<>());
            AtomicReference<Exception> firstError = new AtomicReference<>();

            copyParts(uploadId, completedParts, firstError);

            // Check for errors
            if (firstError.get() != null) {
                if (!leavePartsOnError) {
                    abortMultipartUpload(uploadId);
                }
                throw new CopyError(uploadId, ossPath(), firstError.get());
            }

            // Complete multipart upload
            return completeUpload(uploadId, completedParts);
        }

        private void copyParts(String uploadId, List<Part> completedParts,
                                AtomicReference<Exception> firstError) {
            ExecutorService executor = Executors.newFixedThreadPool(opts.parallelNum());
            List<Future<?>> futures = new ArrayList<>();

            try {
                long readerPos = 0;
                int partNum = 0;

                while (readerPos < sizeInBytes && firstError.get() == null) {
                    partNum++;
                    long bytesLeft = sizeInBytes - readerPos;
                    long currentPartSize = Math.min(bytesLeft, partSize);
                    final int currentPartNum = partNum;
                    final long currentOffset = readerPos;
                    final long currentSize = currentPartSize;

                    futures.add(executor.submit(() -> {
                        if (firstError.get() != null) return;
                        try {
                            copyPart(uploadId, currentPartNum, currentOffset, currentSize, completedParts);
                        } catch (Exception e) {
                            firstError.compareAndSet(null, e);
                        }
                    }));

                    readerPos += currentPartSize;
                }

                waitForFutures(futures, firstError);
            } finally {
                executor.shutdown();
            }
        }

        private void copyPart(String uploadId, int partNum, long offset, long size,
                               List<Part> completedParts) {
            String sourceRange = "bytes=" + offset + "-" + (offset + size - 1);

            UploadPartCopyRequest.Builder partBuilder = UploadPartCopyRequest.newBuilder()
                    .bucket(request.bucket())
                    .key(request.key())
                    .sourceBucket(request.sourceBucket() != null ? request.sourceBucket() : request.bucket())
                    .sourceKey(request.sourceKey())
                    .uploadId(uploadId)
                    .partNumber((long) partNum)
                    .copySourceRange(sourceRange);
            if (request.sourceVersionId() != null) {
                partBuilder.sourceVersionId(request.sourceVersionId());
            }

            UploadPartCopyResult partResult = client.uploadPartCopy(partBuilder.build());
            String eTag = partResult.copyPartResult() != null ? partResult.copyPartResult().eTag() : null;
            completedParts.add(Part.newBuilder()
                    .partNumber((long) partNum)
                    .eTag(eTag)
                    .build());

            // Progress
            if (progressListener != null) {
                long total = transferred.addAndGet(size);
                progressListener.onProgress(size, total, sizeInBytes);
            }
        }

        private CopyResult completeUpload(String uploadId, List<Part> completedParts) throws CopyError {
            completedParts.sort(Comparator.comparing(Part::partNumber));

            CompleteMultipartUploadRequest completeRequest = CompleteMultipartUploadRequest.newBuilder()
                    .bucket(request.bucket())
                    .key(request.key())
                    .uploadId(uploadId)
                    .completeMultipartUpload(CompleteMultipartUpload.newBuilder()
                            .parts(completedParts)
                            .build())
                    .build();

            try {
                CompleteMultipartUploadResult cmResult = client.completeMultipartUpload(completeRequest);

                // CRC64 verification: compare source CRC with destination CRC
                if (enableCRC && cmResult.hashCRC64() != null) {
                    verifyCRC64(cmResult.hashCRC64(), cmResult.headers());
                }

                // Progress finish
                if (progressListener != null) {
                    progressListener.onFinish();
                }

                return CopyResult.newBuilder()
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
                throw new CopyError(uploadId, ossPath(), e);
            }
        }

        private void verifyCRC64(String destCRC, Map<String, String> headers) {
            if (headResult == null) return;
            String sourceCRC = headResult.hashCrc64ecma();
            if (sourceCRC == null || sourceCRC.isEmpty()) return;
            if (!sourceCRC.equals(destCRC)) {
                throw new InconsistentException(sourceCRC, destCRC, headers);
            }
        }

        private void applyMetadataForMultipart(InitiateMultipartUploadRequest.Builder initBuilder) {
            String directive = request.metadataDirective();
            if (directive == null || directive.isEmpty() || "COPY".equalsIgnoreCase(directive)) {
                // Copy metadata from source
                if (headResult != null) {
                    if (headResult.contentType() != null) {
                        initBuilder.contentType(headResult.contentType());
                    }
                    if (headResult.contentEncoding() != null) {
                        initBuilder.contentEncoding(headResult.contentEncoding());
                    }
                    if (headResult.contentDisposition() != null) {
                        initBuilder.contentDisposition(headResult.contentDisposition());
                    }
                    if (headResult.cacheControl() != null) {
                        initBuilder.cacheControl(headResult.cacheControl());
                    }
                    if (headResult.expires() != null) {
                        initBuilder.expires(headResult.expires());
                    }
                    // Copy user metadata from source headers
                    Map<String, String> sourceHeaders = headResult.headers();
                    if (sourceHeaders != null) {
                        for (Map.Entry<String, String> entry : sourceHeaders.entrySet()) {
                            String key = entry.getKey();
                            if (key != null && key.toLowerCase().startsWith("x-oss-meta")) {
                                initBuilder.header(key.toLowerCase(), entry.getValue());
                            }
                        }
                    }
                }
            }
            // For "REPLACE" directive, the metadata from the request itself is used (already in headers)
        }

        private void applyTaggingForMultipart(InitiateMultipartUploadRequest.Builder initBuilder) {
            String directive = request.taggingDirective();
            if (directive == null || directive.isEmpty() || "COPY".equalsIgnoreCase(directive)) {
                // Copy tagging from source
                if (headResult != null && headResult.taggingCount() != null && headResult.taggingCount() > 0) {
                    try {
                        String sourceBucket = request.sourceBucket() != null ? request.sourceBucket() : request.bucket();
                        GetObjectTaggingRequest.Builder tagBuilder = GetObjectTaggingRequest.newBuilder()
                                .bucket(sourceBucket)
                                .key(request.sourceKey());
                        if (request.sourceVersionId() != null) {
                            tagBuilder.versionId(request.sourceVersionId());
                        }
                        GetObjectTaggingRequest tagRequest = tagBuilder.build();
                        GetObjectTaggingResult tagResult = client.getObjectTagging(tagRequest);
                        if (tagResult.tagging() != null && tagResult.tagging().tagSet() != null
                                && tagResult.tagging().tagSet().tags() != null) {
                            StringBuilder sb = new StringBuilder();
                            for (Tag tag : tagResult.tagging().tagSet().tags()) {
                                if (sb.length() > 0) sb.append("&");
                                sb.append(tag.key()).append("=").append(tag.value());
                            }
                            if (sb.length() > 0) {
                                initBuilder.tagging(sb.toString());
                            }
                        }
                    } catch (Exception ignored) {
                        // best effort
                    }
                }
            }
            // For "REPLACE" directive, the tagging from the request is used (already set)
        }

        private void abortMultipartUpload(String uploadId) {
            try {
                AbortMultipartUploadRequest abortRequest = AbortMultipartUploadRequest.newBuilder()
                        .bucket(request.bucket())
                        .key(request.key())
                        .uploadId(uploadId)
                        .build();
                client.abortMultipartUpload(abortRequest);
            } catch (Exception ignored) {
                // best effort abort
            }
        }

        private static boolean isTimeoutException(Exception e) {
            Throwable t = e;
            while (t != null) {
                if (t instanceof java.util.concurrent.TimeoutException ||
                        t instanceof java.net.SocketTimeoutException) {
                    return true;
                }
                String msg = t.getMessage();
                if (msg != null && (msg.contains("timeout") || msg.contains("Timeout") || msg.contains("timed out"))) {
                    return true;
                }
                t = t.getCause();
            }
            return false;
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

        String ossPath() {
            return "oss://" + request.bucket() + "/" + request.key();
        }
    }
}
