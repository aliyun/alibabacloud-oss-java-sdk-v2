package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.InconsistentException;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.transport.*;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.Test;

import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

public class CopierTest {

    // region Mock HTTP Client

    static class CopierMockHttpClient implements HttpClient {
        final AtomicInteger headObjectCount = new AtomicInteger(0);
        final AtomicInteger copyObjectCount = new AtomicInteger(0);
        final AtomicInteger initiateMultipartCount = new AtomicInteger(0);
        final AtomicInteger uploadPartCopyCount = new AtomicInteger(0);
        final AtomicInteger completeMultipartCount = new AtomicInteger(0);
        final AtomicInteger abortMultipartCount = new AtomicInteger(0);
        final AtomicInteger getObjectTaggingCount = new AtomicInteger(0);

        long sourceContentLength = 100;
        String sourceServerSideEncryption = null;
        String sourceCRC64 = "1234567890";
        String completeCRC64 = "1234567890"; // default: matches source CRC
        int sourceTaggingCount = 0;

        boolean headObjectError = false;
        boolean copyObjectError = false;
        boolean copyObjectTimeout = false;
        boolean initiateMultipartError = false;
        boolean completeMultipartError = false;
        Set<Integer> uploadPartCopyErrors = new HashSet<>();

        static final String ERROR_XML =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<Error><Code>InvalidAccessKeyId</Code>" +
                        "<Message>Error</Message><RequestId>id-1234</RequestId></Error>";

        @Override
        public CompletableFuture<ResponseMessage> sendAsync(RequestMessage request, RequestContext context) {
            CompletableFuture<ResponseMessage> future = new CompletableFuture<>();
            future.complete(send(request, context));
            return future;
        }

        @Override
        public ResponseMessage send(RequestMessage request, RequestContext context) {
            String method = request.method();
            URI uri = request.uri();
            String query = uri.getQuery() != null ? uri.getQuery() : "";
            String path = uri.getPath() != null ? uri.getPath() : "";

            if ("HEAD".equals(method)) {
                return handleHeadObject(request);
            } else if ("PUT".equals(method)) {
                if (query.contains("uploadId=") && query.contains("partNumber=")) {
                    return handleUploadPartCopy(request, query);
                } else if (request.headers() != null && request.headers().containsKey("x-oss-copy-source")) {
                    return handleCopyObject(request);
                }
            } else if ("POST".equals(method)) {
                if (query.contains("uploads")) {
                    return handleInitiateMultipartUpload(request);
                } else if (query.contains("uploadId=")) {
                    return handleCompleteMultipartUpload(request);
                }
            } else if ("DELETE".equals(method)) {
                if (query.contains("uploadId=")) {
                    return handleAbortMultipartUpload(request);
                }
            } else if ("GET".equals(method)) {
                if (query.contains("tagging")) {
                    return handleGetObjectTagging(request);
                }
            }

            return buildResponse(400, "<Error><Code>Unknown</Code></Error>", Collections.emptyMap());
        }

        private ResponseMessage handleHeadObject(RequestMessage request) {
            headObjectCount.incrementAndGet();
            if (headObjectError) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Length", String.valueOf(sourceContentLength));
            headers.put("ETag", "\"source-etag\"");
            headers.put("x-oss-hash-crc64ecma", sourceCRC64);
            if (sourceServerSideEncryption != null) {
                headers.put("x-oss-server-side-encryption", sourceServerSideEncryption);
            }
            if (sourceTaggingCount > 0) {
                headers.put("x\u2011oss\u2011tagging\u2011count", String.valueOf(sourceTaggingCount));
            }
            return buildResponse(200, "", headers);
        }

        private ResponseMessage handleCopyObject(RequestMessage request) {
            copyObjectCount.incrementAndGet();
            if (copyObjectTimeout) {
                throw new RuntimeException(new java.net.SocketTimeoutException("Read timed out"));
            }
            if (copyObjectError) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }

            String xml = "<CopyObjectResult>" +
                    "<LastModified>2023-01-01T00:00:00.000Z</LastModified>" +
                    "<ETag>\"copy-etag\"</ETag>" +
                    "</CopyObjectResult>";
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("ETag", "\"copy-etag\"");
            headers.put("x-oss-hash-crc64ecma", sourceCRC64);
            headers.put("x-oss-version-id", "version-copy");
            return buildResponse(200, xml, headers);
        }

        private ResponseMessage handleInitiateMultipartUpload(RequestMessage request) {
            initiateMultipartCount.incrementAndGet();
            if (initiateMultipartError) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }
            String xml = "<InitiateMultipartUploadResult>" +
                    "<Bucket>bucket</Bucket><Key>key</Key><UploadId>uploadId-copy</UploadId>" +
                    "</InitiateMultipartUploadResult>";
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");
            return buildResponse(200, xml, headers);
        }

        private ResponseMessage handleUploadPartCopy(RequestMessage request, String query) {
            int partNum = uploadPartCopyCount.incrementAndGet();
            int pn = extractPartNumber(query);
            if (uploadPartCopyErrors.contains(pn)) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }

            String xml = "<CopyPartResult>" +
                    "<LastModified>2023-01-01T00:00:00.000Z</LastModified>" +
                    "<ETag>\"etag-part-" + pn + "\"</ETag>" +
                    "</CopyPartResult>";
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            return buildResponse(200, xml, headers);
        }

        private ResponseMessage handleCompleteMultipartUpload(RequestMessage request) {
            completeMultipartCount.incrementAndGet();
            if (completeMultipartError) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }
            String xml = "<CompleteMultipartUploadResult>" +
                    "<Location>bucket/key</Location><Bucket>bucket</Bucket><Key>key</Key>" +
                    "<ETag>\"etag-complete\"</ETag></CompleteMultipartUploadResult>";
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("x-oss-hash-crc64ecma", completeCRC64);
            headers.put("x-oss-version-id", "version-mc");
            return buildResponse(200, xml, headers);
        }

        private ResponseMessage handleAbortMultipartUpload(RequestMessage request) {
            abortMultipartCount.incrementAndGet();
            return buildResponse(204, "", Collections.emptyMap());
        }

        private ResponseMessage handleGetObjectTagging(RequestMessage request) {
            getObjectTaggingCount.incrementAndGet();
            String xml = "<Tagging><TagSet>" +
                    "<Tag><Key>key1</Key><Value>value1</Value></Tag>" +
                    "</TagSet></Tagging>";
            return buildResponse(200, xml, Collections.emptyMap());
        }

        private int extractPartNumber(String query) {
            for (String param : query.split("&")) {
                if (param.startsWith("partNumber=")) {
                    return Integer.parseInt(param.substring("partNumber=".length()));
                }
            }
            return -1;
        }

        private ResponseMessage buildResponse(int statusCode, String body, Map<String, String> headers) {
            ResponseMessage.Builder builder = ResponseMessage.newBuilder()
                    .statusCode(statusCode)
                    .body(new StringBinaryData(body));
            if (headers != null && !headers.isEmpty()) {
                builder.headers(headers);
            }
            return builder.build();
        }
    }

    // endregion

    private OSSClient createMockClient(CopierMockHttpClient mockHandler) {
        return OSSClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .disableUploadCRC64Check(true)
                .build();
    }

    // region Options Tests

    @Test
    public void testCopierOptionsDefaults() {
        CopierOptions opts = CopierOptions.defaults();
        assertEquals(Defaults.DEFAULT_COPY_PART_SIZE, opts.partSize());
        assertEquals(Defaults.DEFAULT_COPY_PARALLEL, opts.parallelNum());
        assertEquals(Defaults.DEFAULT_COPY_THRESHOLD, opts.multipartCopyThreshold());
        assertFalse(opts.leavePartsOnError());
        assertFalse(opts.disableShallowCopy());
    }

    @Test
    public void testCopierOptionsCustom() {
        CopierOptions opts = CopierOptions.newBuilder()
                .partSize(1024 * 1024)
                .parallelNum(5)
                .multipartCopyThreshold(10 * 1024 * 1024)
                .leavePartsOnError(true)
                .disableShallowCopy(true)
                .build();
        assertEquals(1024 * 1024, opts.partSize());
        assertEquals(5, opts.parallelNum());
        assertEquals(10 * 1024 * 1024, opts.multipartCopyThreshold());
        assertTrue(opts.leavePartsOnError());
        assertTrue(opts.disableShallowCopy());
    }

    // endregion

    // region Argument Validation

    @Test(expected = IllegalArgumentException.class)
    public void testCopyNullRequest() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);
        copier.copy(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyNullBucket() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);
        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .key("key")
                .sourceKey("src-key")
                .build();
        copier.copy(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyNullKey() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);
        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .sourceKey("src-key")
                .build();
        copier.copy(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyNullSourceKey() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);
        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();
        copier.copy(request);
    }

    // endregion

    // region Single Copy

    @Test
    public void testSingleCopy() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 100; // Below default threshold
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(1, mock.headObjectCount.get());
        assertEquals(1, mock.copyObjectCount.get());
        assertEquals(0, mock.initiateMultipartCount.get());
    }

    // endregion

    // region Multi-part Copy

    @Test
    public void testMultipartCopy() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500; // Above threshold
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(2)
                .disableShallowCopy(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(1, mock.headObjectCount.get());
        assertEquals(0, mock.copyObjectCount.get());
        assertEquals(1, mock.initiateMultipartCount.get());
        assertEquals(5, mock.uploadPartCopyCount.get()); // 500 / 100 = 5
        assertEquals(1, mock.completeMultipartCount.get());
    }

    @Test
    public void testMultipartCopyWithUploadPartCopyError() {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.uploadPartCopyErrors.add(3);
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        try {
            copier.copy(request);
            fail("Expected CopyError");
        } catch (CopyError e) {
            assertNotNull(e.getCause());
            assertEquals(1, mock.abortMultipartCount.get());
        }
    }

    @Test
    public void testMultipartCopyLeavePartsOnError() {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.uploadPartCopyErrors.add(2);
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .leavePartsOnError(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        try {
            copier.copy(request);
            fail("Expected CopyError");
        } catch (CopyError e) {
            assertNotNull(e.getCause());
            assertEquals(0, mock.abortMultipartCount.get());
        }
    }

    // endregion

    // region Shallow Copy

    @Test
    public void testShallowCopyUsed() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500; // Above threshold
        mock.sourceServerSideEncryption = null; // No SSE
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(2)
                .build());

        // Same bucket, no storage class change, no SSE -> shallow copy
        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(1, mock.headObjectCount.get());
        assertEquals(1, mock.copyObjectCount.get()); // shallow copy uses CopyObject
        assertEquals(0, mock.initiateMultipartCount.get());
    }

    @Test
    public void testShallowCopyDisabledBySSE() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.sourceServerSideEncryption = "AES256";
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        // SSE enabled -> falls back to multipart
        assertEquals(1, mock.initiateMultipartCount.get());
        assertTrue(mock.uploadPartCopyCount.get() > 0);
    }

    @Test
    public void testShallowCopyDisabledByCrossBucket() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("dest-bucket")
                .key("key")
                .sourceBucket("src-bucket")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        // Cross bucket -> falls back to multipart
        assertEquals(1, mock.initiateMultipartCount.get());
        assertTrue(mock.uploadPartCopyCount.get() > 0);
    }

    @Test
    public void testShallowCopyDisabledByStorageClass() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .storageClass("IA")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        // StorageClass change -> falls back to multipart
        assertEquals(1, mock.initiateMultipartCount.get());
    }

    @Test
    public void testShallowCopyDisabledByOption() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        // disableShallowCopy -> falls back to multipart
        assertEquals(1, mock.initiateMultipartCount.get());
        assertTrue(mock.uploadPartCopyCount.get() > 0);
    }

    // endregion

    // region HeadObject Error

    @Test
    public void testHeadObjectFail() {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.headObjectError = true;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        try {
            copier.copy(request);
            fail("Expected CopyError");
        } catch (CopyError e) {
            assertNotNull(e.getCause());
        }
    }

    // endregion

    // region Options: enableCRC64Check, noCheckSSE, noCheckCrossBucket

    @Test
    public void testCopierOptionsDefaultsIncludeNewFields() {
        CopierOptions opts = CopierOptions.defaults();
        assertEquals(Defaults.DEFAULT_COPY_PART_SIZE, opts.partSize());
        assertEquals(Defaults.DEFAULT_COPY_PARALLEL, opts.parallelNum());
        assertEquals(Defaults.DEFAULT_COPY_THRESHOLD, opts.multipartCopyThreshold());
        assertFalse(opts.leavePartsOnError());
        assertFalse(opts.disableShallowCopy());
        assertTrue(opts.enableCRC64Check());
        assertFalse(opts.noCheckSSE());
        assertFalse(opts.noCheckCrossBucket());
    }

    @Test
    public void testCopierOptionsCustomAllFields() {
        CopierOptions opts = CopierOptions.newBuilder()
                .partSize(1024 * 1024)
                .parallelNum(2)
                .multipartCopyThreshold(5 * 1024 * 1024)
                .leavePartsOnError(true)
                .disableShallowCopy(true)
                .enableCRC64Check(false)
                .noCheckSSE(true)
                .noCheckCrossBucket(true)
                .build();
        assertEquals(1024 * 1024, opts.partSize());
        assertEquals(2, opts.parallelNum());
        assertEquals(5 * 1024 * 1024, opts.multipartCopyThreshold());
        assertTrue(opts.leavePartsOnError());
        assertTrue(opts.disableShallowCopy());
        assertFalse(opts.enableCRC64Check());
        assertTrue(opts.noCheckSSE());
        assertTrue(opts.noCheckCrossBucket());
    }

    // endregion

    // region Override Options

    @Test
    public void testCopyWithOverrideOptions() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        OSSClient client = createMockClient(mock);

        // Default copier (large threshold -> single copy)
        Copier copier = new Copier(client);

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        // Override with small threshold and partSize to force multipart
        CopierOptions overrideOpts = CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(2)
                .disableShallowCopy(true)
                .build();

        CopyResult result = copier.copy(request, overrideOpts);
        assertNotNull(result);
        assertEquals(1, mock.initiateMultipartCount.get());
        assertEquals(5, mock.uploadPartCopyCount.get());
        assertEquals(1, mock.completeMultipartCount.get());
    }

    // endregion

    // region ShallowCopy Flags: noCheckSSE, noCheckCrossBucket

    @Test
    public void testNoCheckSSEAllowsShallowCopy() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.sourceServerSideEncryption = "AES256";
        OSSClient client = createMockClient(mock);

        // Without noCheckSSE: SSE forces multipart
        Copier copier1 = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result1 = copier1.copy(request);
        assertNotNull(result1);
        assertEquals(1, mock.initiateMultipartCount.get());
        assertEquals(0, mock.copyObjectCount.get());

        // Reset
        mock.initiateMultipartCount.set(0);
        mock.uploadPartCopyCount.set(0);
        mock.completeMultipartCount.set(0);
        mock.headObjectCount.set(0);

        // With noCheckSSE: SSE is ignored, shallow copy used
        Copier copier2 = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .noCheckSSE(true)
                .build());

        CopyResult result2 = copier2.copy(request);
        assertNotNull(result2);
        assertEquals(1, mock.copyObjectCount.get()); // shallow copy
        assertEquals(0, mock.initiateMultipartCount.get());
    }

    @Test
    public void testNoCheckCrossBucketAllowsShallowCopy() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        OSSClient client = createMockClient(mock);

        // Without noCheckCrossBucket: cross-bucket forces multipart
        Copier copier1 = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("dest-bucket")
                .key("key")
                .sourceBucket("src-bucket")
                .sourceKey("src-key")
                .build();

        CopyResult result1 = copier1.copy(request);
        assertNotNull(result1);
        assertEquals(1, mock.initiateMultipartCount.get());
        assertEquals(0, mock.copyObjectCount.get());

        // Reset
        mock.initiateMultipartCount.set(0);
        mock.uploadPartCopyCount.set(0);
        mock.completeMultipartCount.set(0);
        mock.headObjectCount.set(0);

        // With noCheckCrossBucket: cross-bucket is ignored, shallow copy used
        Copier copier2 = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .noCheckCrossBucket(true)
                .build());

        CopyResult result2 = copier2.copy(request);
        assertNotNull(result2);
        assertEquals(1, mock.copyObjectCount.get()); // shallow copy
        assertEquals(0, mock.initiateMultipartCount.get());
    }

    @Test
    public void testNoCheckSSEAndNoCheckCrossBucketTogether() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.sourceServerSideEncryption = "AES256";
        OSSClient client = createMockClient(mock);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .noCheckSSE(true)
                .noCheckCrossBucket(true)
                .build());

        // Cross bucket + SSE, but both checks disabled -> shallow copy
        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("dest-bucket")
                .key("key")
                .sourceBucket("src-bucket")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(1, mock.copyObjectCount.get());
        assertEquals(0, mock.initiateMultipartCount.get());
    }

    @Test
    public void testStorageClassStillBlocksShallowCopyWithNoCheckFlags() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        OSSClient client = createMockClient(mock);

        // Even with noCheckSSE + noCheckCrossBucket, storageClass change disables shallow copy
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .noCheckSSE(true)
                .noCheckCrossBucket(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .storageClass("IA")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(1, mock.initiateMultipartCount.get());
        assertEquals(0, mock.copyObjectCount.get());
    }

    // endregion

    // region Progress Reporting

    @Test
    public void testSingleCopyProgress() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 100;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);

        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicLong lastTotal = new AtomicLong(0);
        AtomicLong finishCount = new AtomicLong(0);

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .progressListener(new ProgressListener() {
                    @Override
                    public void onProgress(long increment, long transferred, long total) {
                        totalIncrement.addAndGet(increment);
                        lastTransferred.set(transferred);
                        lastTotal.set(total);
                    }

                    @Override
                    public void onFinish() {
                        finishCount.incrementAndGet();
                    }
                })
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(100, totalIncrement.get());
        assertEquals(100, lastTransferred.get());
        assertEquals(100, lastTotal.get());
        assertEquals(1, finishCount.get());
    }

    @Test
    public void testMultipartCopyProgress() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        OSSClient client = createMockClient(mock);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .build());

        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicLong finishCount = new AtomicLong(0);

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .progressListener(new ProgressListener() {
                    @Override
                    public void onProgress(long increment, long transferred, long total) {
                        totalIncrement.addAndGet(increment);
                        lastTransferred.set(transferred);
                        assertEquals(500, total);
                    }

                    @Override
                    public void onFinish() {
                        finishCount.incrementAndGet();
                    }
                })
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(500, totalIncrement.get());
        assertEquals(500, lastTransferred.get());
        assertEquals(1, finishCount.get());
    }

    @Test
    public void testShallowCopyProgress() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        OSSClient client = createMockClient(mock);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .build());

        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicLong finishCount = new AtomicLong(0);

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .progressListener(new ProgressListener() {
                    @Override
                    public void onProgress(long increment, long transferred, long total) {
                        totalIncrement.addAndGet(increment);
                    }

                    @Override
                    public void onFinish() {
                        finishCount.incrementAndGet();
                    }
                })
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(500, totalIncrement.get());
        assertEquals(1, finishCount.get());
    }

    @Test
    public void testProgressNotCalledOnError() {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 100;
        mock.copyObjectError = true;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);

        AtomicLong finishCount = new AtomicLong(0);

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .progressListener(new ProgressListener() {
                    @Override
                    public void onProgress(long increment, long transferred, long total) {
                    }

                    @Override
                    public void onFinish() {
                        finishCount.incrementAndGet();
                    }
                })
                .build();

        try {
            copier.copy(request);
            fail("Expected CopyError");
        } catch (CopyError e) {
            assertEquals(0, finishCount.get());
        }
    }

    // endregion

    // region CRC64 Verification

    @Test
    public void testMultipartCopyCRC64MatchSuccess() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.sourceCRC64 = "9999";
        mock.completeCRC64 = "9999"; // matches source
        OSSClient client = createMockClient(mock);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .enableCRC64Check(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals("9999", result.hashCrc64ecma());
    }

    @Test
    public void testMultipartCopyCRC64Mismatch() {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.sourceCRC64 = "1111";
        mock.completeCRC64 = "2222"; // does NOT match source
        OSSClient client = createMockClient(mock);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .enableCRC64Check(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        try {
            copier.copy(request);
            fail("Expected CopyError wrapping InconsistentException");
        } catch (CopyError e) {
            assertTrue(e.getCause() instanceof InconsistentException);
            InconsistentException ie = (InconsistentException) e.getCause();
            assertTrue(ie.getMessage().contains("1111"));
            assertTrue(ie.getMessage().contains("2222"));
        }
    }

    @Test
    public void testMultipartCopyCRC64DisabledNoError() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.sourceCRC64 = "1111";
        mock.completeCRC64 = "2222"; // mismatch but CRC check disabled
        OSSClient client = createMockClient(mock);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .enableCRC64Check(false)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        // Should succeed despite CRC mismatch because check is disabled
        CopyResult result = copier.copy(request);
        assertNotNull(result);
    }

    // endregion

    // region CopyObject Error

    @Test
    public void testSingleCopyError() {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 100;
        mock.copyObjectError = true;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        try {
            copier.copy(request);
            fail("Expected CopyError");
        } catch (CopyError e) {
            assertNotNull(e.getCause());
            assertEquals(1, mock.copyObjectCount.get());
        }
    }

    @Test
    public void testInitiateMultipartError() {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.initiateMultipartError = true;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .disableShallowCopy(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        try {
            copier.copy(request);
            fail("Expected CopyError");
        } catch (CopyError e) {
            assertNotNull(e.getCause());
            assertEquals(1, mock.initiateMultipartCount.get());
            assertEquals(0, mock.uploadPartCopyCount.get());
        }
    }

    @Test
    public void testCompleteMultipartError() {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.completeMultipartError = true;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        try {
            copier.copy(request);
            fail("Expected CopyError");
        } catch (CopyError e) {
            assertNotNull(e.getCause());
            assertEquals(1, mock.completeMultipartCount.get());
            assertEquals(1, mock.abortMultipartCount.get());
        }
    }

    @Test
    public void testCompleteMultipartErrorWithLeavePartsOnError() {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.completeMultipartError = true;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .leavePartsOnError(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        try {
            copier.copy(request);
            fail("Expected CopyError");
        } catch (CopyError e) {
            assertNotNull(e.getCause());
            assertEquals(0, mock.abortMultipartCount.get());
        }
    }

    // endregion

    // region Shallow Copy Timeout Fallback

    @Test
    public void testShallowCopyTimeoutFallsBackToMultipart() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.copyObjectTimeout = true;
        OSSClient client = createMockClient(mock);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .build());

        // Same bucket, no SSE -> shallow copy attempted, but timeout -> multipart
        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(1, mock.copyObjectCount.get()); // shallow copy attempted
        assertEquals(1, mock.initiateMultipartCount.get()); // fell back to multipart
        assertTrue(mock.uploadPartCopyCount.get() > 0);
    }

    // endregion

    // region Source Version

    @Test
    public void testCopyWithSourceVersionId() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 100;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceBucket("src-bucket")
                .sourceKey("src-key")
                .sourceVersionId("version-123")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(1, mock.headObjectCount.get());
        assertEquals(1, mock.copyObjectCount.get());
    }

    // endregion

    // region Tagging

    @Test
    public void testMultipartCopyWithTagging() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.sourceTaggingCount = 1;
        OSSClient client = createMockClient(mock);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(1, mock.getObjectTaggingCount.get());
    }

    // endregion

    // region Part Size Adjustment

    @Test
    public void testPartSizeAdjustedForLargeObject() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        // Set size such that partSize needs adjustment
        long partSize = 100;
        mock.sourceContentLength = partSize * Defaults.MAX_UPLOAD_PARTS + 1;
        OSSClient client = createMockClient(mock);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(partSize)
                .parallelNum(1)
                .disableShallowCopy(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        // Part count should be <= MAX_UPLOAD_PARTS
        assertTrue(mock.uploadPartCopyCount.get() <= Defaults.MAX_UPLOAD_PARTS);
    }

    // endregion

    // region CopyResult Fields

    @Test
    public void testSingleCopyResultFields() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 100;
        mock.sourceCRC64 = "5555";
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNull(result.uploadId());
        assertNotNull(result.etag());
        assertEquals("version-copy", result.versionId());
        assertEquals("5555", result.hashCrc64ecma());
        assertNotNull(result.headers());
        assertEquals(200, result.statusCode());
    }

    @Test
    public void testMultipartCopyResultFields() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.sourceCRC64 = "7777";
        mock.completeCRC64 = "7777";
        OSSClient client = createMockClient(mock);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertEquals("uploadId-copy", result.uploadId());
        assertNotNull(result.etag());
        assertEquals("version-mc", result.versionId());
        assertEquals("7777", result.hashCrc64ecma());
    }

    // endregion

    // region CopyError Fields

    @Test
    public void testCopyErrorFields() {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        mock.uploadPartCopyErrors.add(1);
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(1)
                .disableShallowCopy(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        try {
            copier.copy(request);
            fail("Expected CopyError");
        } catch (CopyError e) {
            assertEquals("uploadId-copy", e.uploadId());
            assertEquals("oss://bucket/key", e.path());
            assertNotNull(e.getCause());
        }
    }

    @Test
    public void testCopyErrorFieldsForHeadObjectFailure() {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.headObjectError = true;
        OSSClient client = createMockClient(mock);
        Copier copier = new Copier(client);

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        try {
            copier.copy(request);
            fail("Expected CopyError");
        } catch (CopyError e) {
            assertEquals("", e.uploadId());
            assertEquals("oss://bucket/key", e.path());
        }
    }

    // endregion

    // region Multipart Parallel

    @Test
    public void testMultipartCopyParallel() throws CopyError {
        CopierMockHttpClient mock = new CopierMockHttpClient();
        mock.sourceContentLength = 500;
        OSSClient client = createMockClient(mock);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .multipartCopyThreshold(200)
                .partSize(100)
                .parallelNum(4)
                .disableShallowCopy(true)
                .build());

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .sourceKey("src-key")
                .build();

        CopyResult result = copier.copy(request);
        assertNotNull(result);
        assertEquals(5, mock.uploadPartCopyCount.get());
        assertEquals(1, mock.completeMultipartCount.get());
    }

    // endregion
}
