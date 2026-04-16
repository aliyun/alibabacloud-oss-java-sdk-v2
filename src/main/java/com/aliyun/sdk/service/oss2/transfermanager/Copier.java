package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.models.*;

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

        CopierOptions opts = overrideOptions != null ? overrideOptions : this.options;
        String path = "oss://" + request.bucket() + "/" + request.key();

        // HeadObject to get source size
        String sourceBucket = request.sourceBucket() != null ? request.sourceBucket() : request.bucket();
        String sourceKey = request.sourceKey();

        HeadObjectResult headResult;
        try {
            HeadObjectRequest.Builder headBuilder = HeadObjectRequest.newBuilder()
                    .bucket(sourceBucket)
                    .key(sourceKey);
            if (request.sourceVersionId() != null) {
                headBuilder.versionId(request.sourceVersionId());
            }
            headResult = client.headObject(headBuilder.build());
        } catch (Exception e) {
            throw new CopyError("", path, e);
        }

        long sizeInBytes = headResult.contentLength() != null ? headResult.contentLength() : 0;

        // Determine copy strategy
        long multipartCopyThreshold = opts.multipartCopyThreshold();
        if (sizeInBytes <= multipartCopyThreshold) {
            return singleCopy(request, path);
        }

        // Adjust part size
        long partSize = opts.partSize();
        if (sizeInBytes > 0) {
            while (sizeInBytes / partSize >= Defaults.MAX_UPLOAD_PARTS) {
                partSize += opts.partSize();
            }
        }

        if (!opts.disableShallowCopy() && canUseShallowCopy(request, headResult)) {
            return shallowCopy(request, path, sizeInBytes, partSize, opts);
        }

        return multiCopy(request, path, sizeInBytes, partSize, opts, headResult);
    }

    private void validateRequest(CopyObjectRequest request) {
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

    private boolean canUseShallowCopy(CopyObjectRequest request, HeadObjectResult headResult) {
        // Change StorageClass
        if (request.storageClass() != null && !request.storageClass().isEmpty()) {
            return false;
        }

        // Cross bucket
        if (request.sourceBucket() != null && !request.sourceBucket().equals(request.bucket())) {
            return false;
        }

        // Server-side encryption
        if (headResult.serverSideEncryption() != null && !headResult.serverSideEncryption().isEmpty()) {
            return false;
        }

        return true;
    }

    private CopyResult singleCopy(CopyObjectRequest request, String path) throws CopyError {
        try {
            CopyObjectResult result = client.copyObject(request);
            return CopyResult.newBuilder()
                    .etag(result.eTag())
                    .versionId(result.versionId())
                    .hashCrc64ecma(result.hashCrc64())
                    .headers(result.headers())
                    .statusCode(result.statusCode())
                    .build();
        } catch (Exception e) {
            throw new CopyError("", path, e);
        }
    }

    private CopyResult shallowCopy(CopyObjectRequest request, String path, long sizeInBytes,
                                    long partSize, CopierOptions opts) throws CopyError {
        // Try single CopyObject first; if it times out, fallback to multiCopy
        try {
            CopyObjectResult result = client.copyObject(request);
            return CopyResult.newBuilder()
                    .etag(result.eTag())
                    .versionId(result.versionId())
                    .hashCrc64ecma(result.hashCrc64())
                    .headers(result.headers())
                    .statusCode(result.statusCode())
                    .build();
        } catch (Exception e) {
            // On timeout or cancellation, fallback to multipart copy
            if (isTimeoutException(e)) {
                return multiCopy(request, path, sizeInBytes, partSize, opts, null);
            }
            throw new CopyError("", path, e);
        }
    }

    private boolean isTimeoutException(Exception e) {
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

    private CopyResult multiCopy(CopyObjectRequest request, String path, long sizeInBytes,
                                  long partSize, CopierOptions opts, HeadObjectResult headResult) throws CopyError {
        // Initiate multipart upload
        InitiateMultipartUploadRequest.Builder initBuilder = InitiateMultipartUploadRequest.newBuilder()
                .bucket(request.bucket())
                .key(request.key())
                .headers(request.headers())
                .parameters(request.parameters());

        // Handle metadata directive for multipart copy
        applyMetadataForMultipart(initBuilder, request, headResult);

        // Handle tagging directive for multipart copy
        applyTaggingForMultipart(initBuilder, request, headResult);

        InitiateMultipartUploadResult initResult;
        try {
            initResult = client.initiateMultipartUpload(initBuilder.build());
        } catch (Exception e) {
            throw new CopyError("", path, e);
        }

        String uploadId = initResult.initiateMultipartUpload().uploadId();

        // Upload parts in parallel using UploadPartCopy
        List<Part> completedParts = Collections.synchronizedList(new ArrayList<>());
        AtomicReference<Exception> firstError = new AtomicReference<>();
        int parallelNum = opts.parallelNum();

        ExecutorService executor = Executors.newFixedThreadPool(parallelNum);
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
                        String sourceRange = "bytes=" + currentOffset + "-" + (currentOffset + currentSize - 1);

                        UploadPartCopyRequest.Builder partBuilder = UploadPartCopyRequest.newBuilder()
                                .bucket(request.bucket())
                                .key(request.key())
                                .sourceBucket(request.sourceBucket() != null ? request.sourceBucket() : request.bucket())
                                .sourceKey(request.sourceKey())
                                .uploadId(uploadId)
                                .partNumber((long) currentPartNum)
                                .copySourceRange(sourceRange);
                        if (request.sourceVersionId() != null) {
                            partBuilder.sourceVersionId(request.sourceVersionId());
                        }
                        UploadPartCopyRequest partRequest = partBuilder.build();

                        UploadPartCopyResult partResult = client.uploadPartCopy(partRequest);
                        String eTag = partResult.copyPartResult() != null ? partResult.copyPartResult().eTag() : null;
                        completedParts.add(Part.newBuilder()
                                .partNumber((long) currentPartNum)
                                .eTag(eTag)
                                .build());
                    } catch (Exception e) {
                        firstError.compareAndSet(null, e);
                    }
                }));

                readerPos += currentPartSize;
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
            if (!opts.leavePartsOnError()) {
                abortMultipartUpload(request.bucket(), request.key(), uploadId);
            }
            throw new CopyError(uploadId, path, firstError.get());
        }

        // Complete multipart upload
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
            return CopyResult.newBuilder()
                    .uploadId(uploadId)
                    .etag(cmResult.completeMultipartUpload() != null ? cmResult.completeMultipartUpload().eTag() : null)
                    .versionId(cmResult.versionId())
                    .hashCrc64ecma(cmResult.hashCRC64())
                    .headers(cmResult.headers())
                    .statusCode(cmResult.statusCode())
                    .build();
        } catch (Exception e) {
            if (!opts.leavePartsOnError()) {
                abortMultipartUpload(request.bucket(), request.key(), uploadId);
            }
            throw new CopyError(uploadId, path, e);
        }
    }

    private void applyMetadataForMultipart(InitiateMultipartUploadRequest.Builder initBuilder,
                                            CopyObjectRequest request, HeadObjectResult headResult) {
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

    private void applyTaggingForMultipart(InitiateMultipartUploadRequest.Builder initBuilder,
                                           CopyObjectRequest request, HeadObjectResult headResult) {
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
