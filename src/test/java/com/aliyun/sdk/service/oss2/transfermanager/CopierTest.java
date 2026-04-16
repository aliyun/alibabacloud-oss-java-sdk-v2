package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.*;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.Test;

import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

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

        boolean headObjectError = false;
        boolean copyObjectError = false;
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
            headers.put("x-oss-hash-crc64ecma", "1234567890");
            if (sourceServerSideEncryption != null) {
                headers.put("x-oss-server-side-encryption", sourceServerSideEncryption);
            }
            return buildResponse(200, "", headers);
        }

        private ResponseMessage handleCopyObject(RequestMessage request) {
            copyObjectCount.incrementAndGet();
            if (copyObjectError) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }

            String xml = "<CopyObjectResult>" +
                    "<LastModified>2023-01-01T00:00:00.000Z</LastModified>" +
                    "<ETag>\"copy-etag\"</ETag>" +
                    "</CopyObjectResult>";
            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("x-oss-hash-crc64ecma", "9876543210");
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
            headers.put("x-oss-hash-crc64ecma", "1234567890");
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
}
