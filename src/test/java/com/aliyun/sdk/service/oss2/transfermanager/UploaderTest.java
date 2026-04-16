package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.*;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class UploaderTest {

    // region Mock HTTP Client

    static class UploaderMockHttpClient implements HttpClient {
        final AtomicInteger putObjectCount = new AtomicInteger(0);
        final AtomicInteger uploadPartCount = new AtomicInteger(0);
        final AtomicInteger initiateMultipartCount = new AtomicInteger(0);
        final AtomicInteger completeMultipartCount = new AtomicInteger(0);
        final AtomicInteger abortMultipartCount = new AtomicInteger(0);

        boolean initiateMultipartError = false;
        boolean completeMultipartError = false;
        boolean putObjectError = false;
        Set<Integer> uploadPartErrors = new HashSet<>();

        final List<byte[]> savedParts = Collections.synchronizedList(new ArrayList<>());
        String lastContentType;

        static final String ERROR_XML =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<Error><Code>InvalidAccessKeyId</Code>" +
                        "<Message>The OSS Access Key Id you provided does not exist in our records.</Message>" +
                        "<RequestId>id-1234</RequestId></Error>";

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

            if ("PUT".equals(method)) {
                if (query.contains("uploadId=") && query.contains("partNumber=")) {
                    return handleUploadPart(request, query);
                } else {
                    return handlePutObject(request);
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
            }

            return buildResponse(400, "<Error><Code>Unknown</Code></Error>", Collections.emptyMap());
        }

        private ResponseMessage handlePutObject(RequestMessage request) {
            putObjectCount.incrementAndGet();
            lastContentType = request.headers() != null ? request.headers().get("Content-Type") : null;

            if (putObjectError) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }

            byte[] data = readBody(request);
            savedParts.add(data);

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("ETag", "\"etag-single\"");
            headers.put("x-oss-hash-crc64ecma", "1234567890");
            headers.put("x-oss-version-id", "version-1");
            return buildResponse(200, "", headers);
        }

        private ResponseMessage handleInitiateMultipartUpload(RequestMessage request) {
            initiateMultipartCount.incrementAndGet();
            lastContentType = request.headers() != null ? request.headers().get("Content-Type") : null;

            if (initiateMultipartError) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }

            String xml = "<InitiateMultipartUploadResult>" +
                    "<Bucket>bucket</Bucket>" +
                    "<Key>key</Key>" +
                    "<UploadId>uploadId-1234</UploadId>" +
                    "</InitiateMultipartUploadResult>";

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");
            return buildResponse(200, xml, headers);
        }

        private ResponseMessage handleUploadPart(RequestMessage request, String query) {
            int partNum = uploadPartCount.incrementAndGet();

            // Extract part number from query
            int pn = extractPartNumber(query);
            if (uploadPartErrors.contains(pn)) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }

            byte[] data = readBody(request);
            savedParts.add(data);

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("ETag", "\"etag-part-" + pn + "\"");
            headers.put("x-oss-hash-crc64ecma", String.valueOf(pn));
            return buildResponse(200, "", headers);
        }

        private ResponseMessage handleCompleteMultipartUpload(RequestMessage request) {
            completeMultipartCount.incrementAndGet();

            if (completeMultipartError) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }

            String xml = "<CompleteMultipartUploadResult>" +
                    "<Location>bucket/key</Location>" +
                    "<Bucket>bucket</Bucket>" +
                    "<Key>key</Key>" +
                    "<ETag>\"etag-complete\"</ETag>" +
                    "</CompleteMultipartUploadResult>";

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("x-oss-hash-crc64ecma", "9876543210");
            headers.put("x-oss-version-id", "version-2");
            return buildResponse(200, xml, headers);
        }

        private ResponseMessage handleAbortMultipartUpload(RequestMessage request) {
            abortMultipartCount.incrementAndGet();
            return buildResponse(204, "", Collections.emptyMap());
        }

        private int extractPartNumber(String query) {
            for (String param : query.split("&")) {
                if (param.startsWith("partNumber=")) {
                    return Integer.parseInt(param.substring("partNumber=".length()));
                }
            }
            return -1;
        }

        private byte[] readBody(RequestMessage request) {
            if (request.body() == null) return new byte[0];
            try {
                return request.body().toBytes();
            } catch (Exception e) {
                return new byte[0];
            }
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

    private OSSClient createMockClient(UploaderMockHttpClient mockHandler) {
        return OSSClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .disableUploadCRC64Check(true)
                .build();
    }

    // region Argument Validation

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFromNullRequest() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        uploader.uploadFrom(null, new ByteArrayInputStream(new byte[0]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFromNullBucket() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .key("key")
                .build();
        uploader.uploadFrom(request, new ByteArrayInputStream(new byte[0]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFromNullKey() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .build();
        uploader.uploadFrom(request, new ByteArrayInputStream(new byte[0]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFromNullBody() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();
        uploader.uploadFrom(request, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFileNullPath() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();
        uploader.uploadFile(request, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFileEmptyPath() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();
        uploader.uploadFile(request, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFileNotExists() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();
        uploader.uploadFile(request, "/non/existent/file.txt");
    }

    // endregion

    // region Single Part Upload

    @Test
    public void testSinglePartUpload() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        byte[] data = new byte[100];
        Arrays.fill(data, (byte) 'A');

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        UploadResult result = uploader.uploadFrom(request, new ByteArrayInputStream(data));
        assertNotNull(result);
        assertEquals(1, mock.putObjectCount.get());
        assertEquals(0, mock.initiateMultipartCount.get());
    }

    @Test
    public void testSinglePartUploadFromFile() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        // Create temp file smaller than default part size
        File tempFile = File.createTempFile("upload-test-", ".txt");
        tempFile.deleteOnExit();
        byte[] data = new byte[100];
        Arrays.fill(data, (byte) 'B');
        Files.write(tempFile.toPath(), data);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        UploadResult result = uploader.uploadFile(request, tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(1, mock.putObjectCount.get());
        assertEquals(0, mock.initiateMultipartCount.get());
        tempFile.delete();
    }

    // endregion

    // region Multipart Upload

    @Test
    public void testMultipartUploadSequential() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .build());

        byte[] data = new byte[350];
        Arrays.fill(data, (byte) 'C');

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        UploadResult result = uploader.uploadFrom(request, new ByteArrayInputStream(data));
        assertNotNull(result);
        assertEquals(0, mock.putObjectCount.get());
        assertEquals(1, mock.initiateMultipartCount.get());
        assertEquals(4, mock.uploadPartCount.get()); // 350 / 100 = 3 full + 1 partial = 4
        assertEquals(1, mock.completeMultipartCount.get());
        assertNotNull(result.uploadId());
    }

    @Test
    public void testMultipartUploadParallel() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(3)
                .build());

        byte[] data = new byte[350];
        Arrays.fill(data, (byte) 'D');

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        UploadResult result = uploader.uploadFrom(request, new ByteArrayInputStream(data));
        assertNotNull(result);
        assertEquals(0, mock.putObjectCount.get());
        assertEquals(1, mock.initiateMultipartCount.get());
        assertEquals(4, mock.uploadPartCount.get());
        assertEquals(1, mock.completeMultipartCount.get());
    }

    @Test
    public void testMultipartUploadFromFile() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(3)
                .build());

        File tempFile = File.createTempFile("upload-multipart-", ".dat");
        tempFile.deleteOnExit();
        byte[] data = new byte[350];
        Arrays.fill(data, (byte) 'E');
        Files.write(tempFile.toPath(), data);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        UploadResult result = uploader.uploadFile(request, tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(0, mock.putObjectCount.get());
        assertEquals(1, mock.initiateMultipartCount.get());
        assertTrue(mock.uploadPartCount.get() > 0);
        assertEquals(1, mock.completeMultipartCount.get());
        tempFile.delete();
    }

    // endregion

    // region Error Cases

    @Test
    public void testInitiateMultipartUploadFail() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.initiateMultipartError = true;
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .build());

        byte[] data = new byte[350];
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        try {
            uploader.uploadFrom(request, new ByteArrayInputStream(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
        }
    }

    @Test
    public void testUploadPartFail() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.uploadPartErrors.add(2);
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .build());

        byte[] data = new byte[350];
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        try {
            uploader.uploadFrom(request, new ByteArrayInputStream(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
            assertEquals(1, mock.abortMultipartCount.get());
        }
    }

    @Test
    public void testUploadPartFailLeavePartsOnError() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.uploadPartErrors.add(2);
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .leavePartsOnError(true)
                .build());

        byte[] data = new byte[350];
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        try {
            uploader.uploadFrom(request, new ByteArrayInputStream(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
            assertEquals(0, mock.abortMultipartCount.get());
        }
    }

    @Test
    public void testCompleteMultipartUploadFail() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.completeMultipartError = true;
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .build());

        byte[] data = new byte[350];
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        try {
            uploader.uploadFrom(request, new ByteArrayInputStream(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
            assertEquals(1, mock.abortMultipartCount.get());
        }
    }

    @Test
    public void testPutObjectFail() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.putObjectError = true;
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        byte[] data = new byte[100];
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        try {
            uploader.uploadFrom(request, new ByteArrayInputStream(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
        }
    }

    // endregion

    // region Options

    @Test
    public void testUploaderOptionsDefaults() {
        UploaderOptions opts = UploaderOptions.defaults();
        assertEquals(Defaults.DEFAULT_UPLOAD_PART_SIZE, opts.partSize());
        assertEquals(Defaults.DEFAULT_UPLOAD_PARALLEL, opts.parallelNum());
        assertFalse(opts.leavePartsOnError());
    }

    @Test
    public void testUploaderOptionsCustom() {
        UploaderOptions opts = UploaderOptions.newBuilder()
                .partSize(1024 * 1024)
                .parallelNum(5)
                .leavePartsOnError(true)
                .build();
        assertEquals(1024 * 1024, opts.partSize());
        assertEquals(5, opts.parallelNum());
        assertTrue(opts.leavePartsOnError());
    }

    @Test
    public void testUploaderOverrideOptions() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        assertEquals(Defaults.DEFAULT_UPLOAD_PART_SIZE, uploader.options().partSize());

        byte[] data = new byte[350];
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        UploaderOptions overrideOpts = UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .build();

        UploadResult result = uploader.uploadFrom(request, new ByteArrayInputStream(data), overrideOpts);
        assertNotNull(result);
        assertEquals(1, mock.initiateMultipartCount.get());
        assertEquals(4, mock.uploadPartCount.get());
    }

    // endregion
}
