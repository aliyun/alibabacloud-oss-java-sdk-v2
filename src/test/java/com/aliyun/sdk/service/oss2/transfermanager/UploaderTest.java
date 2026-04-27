package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.InconsistentException;
import com.aliyun.sdk.service.oss2.hash.CRC64;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.transport.*;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

public class UploaderTest {

    // region Mock HTTP Client

    /**
     * Mock HTTP client that computes real CRC64 values, matching the Go SDK mock server behavior.
     * Supports configurable errors per part, per-part CRC invalidation, and data capture.
     */
    static class UploaderMockHttpClient implements HttpClient {
        final AtomicInteger putObjectCount = new AtomicInteger(0);
        final AtomicInteger uploadPartCount = new AtomicInteger(0);
        final AtomicInteger initiateMultipartCount = new AtomicInteger(0);
        final AtomicInteger completeMultipartCount = new AtomicInteger(0);
        final AtomicInteger abortMultipartCount = new AtomicInteger(0);
        final AtomicInteger listPartsCount = new AtomicInteger(0);

        boolean initiateMultipartError = false;
        boolean completeMultipartError = false;
        boolean putObjectError = false;
        boolean listPartsError = false;
        Set<Integer> uploadPartErrors = new HashSet<>();
        Set<Integer> crcPartInvalid = new HashSet<>();

        final List<byte[]> savedParts = Collections.synchronizedList(new ArrayList<>());
        // indexed by partNumber (1-based), stores part data for CRC computation
        final Map<Integer, byte[]> savedPartsByNumber = Collections.synchronizedMap(new HashMap<>());
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
            } else if ("GET".equals(method)) {
                if (query.contains("uploadId=")) {
                    return handleListParts(request, query);
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

            CRC64 crc = new CRC64();
            crc.update(data, data.length);
            String crc64ecma = Long.toUnsignedString(crc.getValue());

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("ETag", "\"etag-single\"");
            headers.put("x-oss-hash-crc64ecma", crc64ecma);
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
            uploadPartCount.incrementAndGet();

            int pn = extractPartNumber(query);
            if (uploadPartErrors.contains(pn)) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }

            byte[] data = readBody(request);
            savedParts.add(data);
            savedPartsByNumber.put(pn, data);

            CRC64 crc = new CRC64();
            crc.update(data, data.length);
            String crc64ecma;
            if (crcPartInvalid.contains(pn)) {
                crc64ecma = "12345";
            } else {
                crc64ecma = Long.toUnsignedString(crc.getValue());
            }

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("ETag", "\"etag-part-" + pn + "\"");
            headers.put("x-oss-hash-crc64ecma", crc64ecma);
            return buildResponse(200, "", headers);
        }

        private ResponseMessage handleCompleteMultipartUpload(RequestMessage request) {
            completeMultipartCount.incrementAndGet();

            if (completeMultipartError) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }

            // Compute combined CRC64 from all saved parts in order
            long combinedCrc = 0;
            List<Integer> sortedParts = new ArrayList<>(savedPartsByNumber.keySet());
            Collections.sort(sortedParts);
            for (int pn : sortedParts) {
                byte[] data = savedPartsByNumber.get(pn);
                CRC64 partCrc = new CRC64();
                partCrc.update(data, data.length);
                combinedCrc = CRC64.combine(combinedCrc, partCrc.getValue(), data.length);
            }

            String xml = "<CompleteMultipartUploadResult>" +
                    "<Location>bucket/key</Location>" +
                    "<Bucket>bucket</Bucket>" +
                    "<Key>key</Key>" +
                    "<ETag>\"etag-complete\"</ETag>" +
                    "</CompleteMultipartUploadResult>";

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("x-oss-hash-crc64ecma", Long.toUnsignedString(combinedCrc));
            headers.put("x-oss-version-id", "version-2");
            return buildResponse(200, xml, headers);
        }

        private ResponseMessage handleAbortMultipartUpload(RequestMessage request) {
            abortMultipartCount.incrementAndGet();
            return buildResponse(204, "", Collections.emptyMap());
        }

        private ResponseMessage handleListParts(RequestMessage request, String query) {
            listPartsCount.incrementAndGet();

            if (listPartsError) {
                return buildResponse(403, ERROR_XML, Collections.emptyMap());
            }

            StringBuilder buf = new StringBuilder();
            buf.append("<ListPartsResult>");
            buf.append("<Bucket>bucket</Bucket>");
            buf.append("<Key>key</Key>");
            buf.append("<UploadId>uploadId-1234</UploadId>");
            buf.append("<IsTruncated>false</IsTruncated>");

            List<Integer> sortedParts = new ArrayList<>(savedPartsByNumber.keySet());
            Collections.sort(sortedParts);
            for (int pn : sortedParts) {
                byte[] data = savedPartsByNumber.get(pn);
                CRC64 crc = new CRC64();
                crc.update(data, data.length);
                buf.append("<Part>");
                buf.append("<PartNumber>").append(pn).append("</PartNumber>");
                buf.append("<LastModified>2012-02-23T07:01:34.000Z</LastModified>");
                buf.append("<ETag>\"etag-part-").append(pn).append("\"</ETag>");
                buf.append("<Size>").append(data.length).append("</Size>");
                buf.append("<HashCrc64ecma>").append(Long.toUnsignedString(crc.getValue())).append("</HashCrc64ecma>");
                buf.append("</Part>");
            }
            buf.append("</ListPartsResult>");

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Type", "application/xml");
            return buildResponse(200, buf.toString(), headers);
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
                Long length = request.body().getLength();
                if (length != null && length >= 0) {
                    // Read exactly 'length' bytes (important for FileInputStream-backed bodies)
                    InputStream is = request.body().toStream();
                    byte[] buf = new byte[length.intValue()];
                    int read = 0;
                    while (read < buf.length) {
                        int n = is.read(buf, read, buf.length - read);
                        if (n < 0) break;
                        read += n;
                    }
                    return read == buf.length ? buf : Arrays.copyOf(buf, read);
                }
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

    // region Helpers

    private OSSClient createMockClient(UploaderMockHttpClient mockHandler) {
        return OSSClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .disableUploadCRC64Check(true)
                .build();
    }

    private static byte[] randomData(int length) {
        byte[] data = new byte[length];
        Random rnd = new Random(42);
        rnd.nextBytes(data);
        return data;
    }

    private static String computeCRC64(byte[] data) {
        CRC64 crc = new CRC64();
        crc.update(data, data.length);
        return Long.toUnsignedString(crc.getValue());
    }

    private static PutObjectRequest defaultRequest() {
        return PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();
    }

    // endregion

    // region Argument Validation

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFromNullRequest() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        uploader.uploadFrom(null, BinaryData.fromBytes(new byte[0]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFromNullBucket() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .key("key")
                .build();
        uploader.uploadFrom(request, BinaryData.fromBytes(new byte[0]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFromNullKey() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .build();
        uploader.uploadFrom(request, BinaryData.fromBytes(new byte[0]));
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
        uploader.uploadFrom(request, (BinaryData) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFileNullPath() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        uploader.uploadFile(defaultRequest(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFileEmptyPath() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        uploader.uploadFile(defaultRequest(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUploadFileNotExists() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);
        uploader.uploadFile(defaultRequest(), "/non/existent/file.txt");
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

        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);
        assertEquals(1, mock.putObjectCount.get());
        assertEquals(0, mock.initiateMultipartCount.get());
        assertNull(result.uploadId());
        assertEquals(computeCRC64(data), result.hashCrc64ecma());
    }

    @Test
    public void testSinglePartUploadFromFile() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        File tempFile = File.createTempFile("upload-test-", ".txt");
        tempFile.deleteOnExit();
        byte[] data = new byte[100];
        Arrays.fill(data, (byte) 'B');
        Files.write(tempFile.toPath(), data);

        UploadResult result = uploader.uploadFile(defaultRequest(), tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(1, mock.putObjectCount.get());
        assertEquals(0, mock.initiateMultipartCount.get());
        assertNull(result.uploadId());
        assertEquals(computeCRC64(data), result.hashCrc64ecma());
        tempFile.delete();
    }

    @Test
    public void testSinglePartUploadEmptyBody() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        byte[] data = new byte[0];
        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);
        assertNull(result.uploadId());
        assertEquals(1, mock.putObjectCount.get());
        assertEquals(0, mock.initiateMultipartCount.get());
        assertEquals(computeCRC64(data), result.hashCrc64ecma());
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
                .enableCRC64Check(false)
                .build());

        byte[] data = new byte[350];
        Arrays.fill(data, (byte) 'C');

        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);
        assertEquals(0, mock.putObjectCount.get());
        assertEquals(1, mock.initiateMultipartCount.get());
        assertEquals(4, mock.uploadPartCount.get()); // 350 / 100 = 3 full + 1 partial = 4
        assertEquals(1, mock.completeMultipartCount.get());
        assertEquals("uploadId-1234", result.uploadId());
    }

    @Test
    public void testMultipartUploadParallel() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(3)
                .enableCRC64Check(false)
                .build());

        byte[] data = new byte[350];
        Arrays.fill(data, (byte) 'D');

        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
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
                .enableCRC64Check(false)
                .build());

        File tempFile = File.createTempFile("upload-multipart-", ".dat");
        tempFile.deleteOnExit();
        byte[] data = new byte[350];
        Arrays.fill(data, (byte) 'E');
        Files.write(tempFile.toPath(), data);

        UploadResult result = uploader.uploadFile(defaultRequest(), tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(0, mock.putObjectCount.get());
        assertEquals(1, mock.initiateMultipartCount.get());
        assertTrue(mock.uploadPartCount.get() > 0);
        assertEquals(1, mock.completeMultipartCount.get());
        tempFile.delete();
    }

    // endregion

    // region Data Integrity

    @Test
    public void testMultipartUploadDataIntegritySequential() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);
        String dataCrc = computeCRC64(data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(1)
                .enableCRC64Check(false)
                .build());

        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);
        assertEquals("uploadId-1234", result.uploadId());

        // Verify all parts combine back to original data
        byte[] reassembled = reassembleParts(mock, 6);
        assertEquals(dataCrc, computeCRC64(reassembled));

        assertEquals(0, mock.putObjectCount.get());
        assertEquals(6, mock.uploadPartCount.get());
    }

    @Test
    public void testMultipartUploadDataIntegrityParallel() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);
        String dataCrc = computeCRC64(data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(4)
                .enableCRC64Check(false)
                .build());

        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);

        byte[] reassembled = reassembleParts(mock, 6);
        assertEquals(dataCrc, computeCRC64(reassembled));
    }

    @Test
    public void testMultipartUploadFromFileDataIntegrity() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);
        String dataCrc = computeCRC64(data);

        File tempFile = File.createTempFile("upload-integrity-", ".dat");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(4)
                .enableCRC64Check(false)
                .build());

        UploadResult result = uploader.uploadFile(defaultRequest(), tempFile.getAbsolutePath());
        assertNotNull(result);

        byte[] reassembled = reassembleParts(mock, 6);
        assertEquals(dataCrc, computeCRC64(reassembled));
        tempFile.delete();
    }

    @Test
    public void testSinglePartUploadDataIntegrity() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        byte[] data = randomData(5 * 100 * 1024 + 123);
        String dataCrc = computeCRC64(data);

        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);
        assertEquals(dataCrc, result.hashCrc64ecma());
        assertEquals(1, mock.putObjectCount.get());

        // Verify saved data matches
        assertEquals(1, mock.savedParts.size());
        assertEquals(dataCrc, computeCRC64(mock.savedParts.get(0)));
    }

    @Test
    public void testSinglePartUploadFromFileDataIntegrity() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        byte[] data = randomData(5 * 100 * 1024 + 123);
        String dataCrc = computeCRC64(data);

        File tempFile = File.createTempFile("upload-integrity-single-", ".txt");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), data);

        UploadResult result = uploader.uploadFile(defaultRequest(), tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(dataCrc, result.hashCrc64ecma());
        assertEquals(1, mock.putObjectCount.get());
        tempFile.delete();
    }

    private static byte[] reassembleParts(UploaderMockHttpClient mock, int expectedParts) {
        assertEquals(expectedParts, mock.savedPartsByNumber.size());
        int totalLen = 0;
        for (byte[] part : mock.savedPartsByNumber.values()) {
            totalLen += part.length;
        }
        byte[] result = new byte[totalLen];
        int offset = 0;
        for (int i = 1; i <= expectedParts; i++) {
            byte[] part = mock.savedPartsByNumber.get(i);
            assertNotNull("Missing part " + i, part);
            System.arraycopy(part, 0, result, offset, part.length);
            offset += part.length;
        }
        return result;
    }

    // endregion

    // region CRC64 Verification

    @Test
    public void testMultipartUploadCRC64Success() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);
        String dataCrc = computeCRC64(data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(1)
                .enableCRC64Check(true)
                .build());

        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);
        assertEquals("uploadId-1234", result.uploadId());
        assertEquals(dataCrc, result.hashCrc64ecma());
    }

    @Test
    public void testMultipartUploadCRC64SuccessParallel() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);
        String dataCrc = computeCRC64(data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(4)
                .enableCRC64Check(true)
                .build());

        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);
        assertEquals(dataCrc, result.hashCrc64ecma());
    }

    @Test
    public void testMultipartUploadFromFileCRC64Success() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);
        String dataCrc = computeCRC64(data);

        File tempFile = File.createTempFile("upload-crc-", ".dat");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .enableCRC64Check(true)
                .build());

        UploadResult result = uploader.uploadFile(defaultRequest(), tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(dataCrc, result.hashCrc64ecma());
        tempFile.delete();
    }

    @Test
    public void testMultipartUploadCRC64Fail() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.crcPartInvalid.add(3); // Invalidate CRC for part 3
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(1)
                .enableCRC64Check(true)
                .build());

        try {
            uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
            fail("Expected UploadError wrapping InconsistentException");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
            assertTrue("Expected InconsistentException but got: " + e.getCause().getClass().getName(),
                    e.getCause() instanceof InconsistentException);
            assertTrue(e.getCause().getMessage().contains("crc is inconsistent"));
        }
    }

    @Test
    public void testMultipartUploadCRC64DisabledWithInvalidCRC() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.crcPartInvalid.add(3);
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);
        String dataCrc = computeCRC64(data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(1)
                .enableCRC64Check(false)
                .build());

        // Should succeed because CRC check is disabled
        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);
        assertEquals("uploadId-1234", result.uploadId());
        // Combined CRC from server is still correct for the data it received
        assertEquals(dataCrc, result.hashCrc64ecma());
    }

    // endregion

    // region Progress Reporting

    @Test
    public void testSinglePartUploadFromFileWithProgress() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        byte[] data = randomData(5 * 100 * 1024 + 123);

        File tempFile = File.createTempFile("upload-progress-", ".txt");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), data);

        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicInteger progressCallCount = new AtomicInteger(0);
        AtomicInteger finishCallCount = new AtomicInteger(0);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .progressListener(new ProgressListener() {
                    @Override
                    public void onProgress(long increment, long transferred, long total) {
                        lastTransferred.set(transferred);
                        progressCallCount.incrementAndGet();
                    }

                    @Override
                    public void onFinish() {
                        finishCallCount.incrementAndGet();
                    }
                })
                .build();

        UploadResult result = uploader.uploadFile(request, tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(1, mock.putObjectCount.get());
        // Note: single part upload does not call progressListener (Go SDK doesn't either for PutObject)
        tempFile.delete();
    }

    @Test
    public void testMultipartUploadWithProgress() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);

        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicLong lastTotal = new AtomicLong(0);
        AtomicInteger finishCallCount = new AtomicInteger(0);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .progressListener(new ProgressListener() {
                    @Override
                    public void onProgress(long increment, long transferred, long total) {
                        totalIncrement.addAndGet(increment);
                        lastTransferred.set(transferred);
                        lastTotal.set(total);
                    }

                    @Override
                    public void onFinish() {
                        finishCallCount.incrementAndGet();
                    }
                })
                .build();

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(1)
                .enableCRC64Check(false)
                .build());

        UploadResult result = uploader.uploadFrom(request, BinaryData.fromBytes(data));
        assertNotNull(result);
        assertEquals(data.length, totalIncrement.get());
        assertEquals(data.length, lastTransferred.get());
        assertEquals(data.length, lastTotal.get());
        assertEquals(1, finishCallCount.get());
    }

    @Test
    public void testMultipartUploadFromFileWithProgress() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);

        File tempFile = File.createTempFile("upload-progress-mp-", ".dat");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), data);

        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicInteger finishCallCount = new AtomicInteger(0);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .progressListener(new ProgressListener() {
                    @Override
                    public void onProgress(long increment, long transferred, long total) {
                        totalIncrement.addAndGet(increment);
                        lastTransferred.set(transferred);
                        assertEquals(data.length, total);
                    }

                    @Override
                    public void onFinish() {
                        finishCallCount.incrementAndGet();
                    }
                })
                .build();

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .enableCRC64Check(false)
                .build());

        UploadResult result = uploader.uploadFile(request, tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(data.length, totalIncrement.get());
        assertEquals(data.length, lastTransferred.get());
        assertEquals(1, finishCallCount.get());
        tempFile.delete();
    }

    @Test
    public void testMultipartUploadProgressOnPartFailure() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.uploadPartErrors.add(2);
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);

        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicInteger finishCallCount = new AtomicInteger(0);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .progressListener(new ProgressListener() {
                    @Override
                    public void onProgress(long increment, long transferred, long total) {
                        totalIncrement.addAndGet(increment);
                    }

                    @Override
                    public void onFinish() {
                        finishCallCount.incrementAndGet();
                    }
                })
                .build();

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(1)
                .enableCRC64Check(false)
                .build());

        try {
            uploader.uploadFrom(request, BinaryData.fromBytes(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            // Progress should be less than total since part 2 failed
            assertTrue(totalIncrement.get() < data.length);
            // onFinish should NOT be called on failure
            assertEquals(0, finishCallCount.get());
        }
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
                .enableCRC64Check(false)
                .build());

        byte[] data = new byte[350];

        try {
            uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
            assertEquals("", e.uploadId());
            assertEquals("oss://bucket/key", e.path());
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
                .enableCRC64Check(false)
                .build());

        byte[] data = new byte[350];

        try {
            uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
            assertEquals("uploadId-1234", e.uploadId());
            assertEquals("oss://bucket/key", e.path());
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
                .enableCRC64Check(false)
                .build());

        byte[] data = new byte[350];

        try {
            uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
            assertEquals(0, mock.abortMultipartCount.get());
        }
    }

    @Test
    public void testUploadPartFailParallel() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.uploadPartErrors.add(3);
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(2)
                .enableCRC64Check(false)
                .build());

        try {
            uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertEquals("uploadId-1234", e.uploadId());
            assertEquals("oss://bucket/key", e.path());

            // Parts 1 and 2 should have been uploaded, part 3 failed
            assertNotNull(mock.savedPartsByNumber.get(1));
            assertNotNull(mock.savedPartsByNumber.get(2));
            assertNull(mock.savedPartsByNumber.get(3));
        }
    }

    @Test
    public void testUploadPartFailFromFile() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.uploadPartErrors.add(2);
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);

        File tempFile = File.createTempFile("upload-partfail-", ".dat");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(1)
                .enableCRC64Check(false)
                .build());

        try {
            uploader.uploadFile(defaultRequest(), tempFile.getAbsolutePath());
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertEquals("uploadId-1234", e.uploadId());
            assertEquals("oss://bucket/key", e.path());
            assertEquals(1, mock.abortMultipartCount.get());
        }
        tempFile.delete();
    }

    @Test
    public void testCompleteMultipartUploadFail() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.completeMultipartError = true;
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .enableCRC64Check(false)
                .build());

        byte[] data = new byte[350];

        try {
            uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
            assertEquals("uploadId-1234", e.uploadId());
            assertEquals("oss://bucket/key", e.path());
            assertEquals(1, mock.abortMultipartCount.get());
        }
    }

    @Test
    public void testCompleteMultipartUploadFailLeavePartsOnError() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.completeMultipartError = true;
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .leavePartsOnError(true)
                .enableCRC64Check(false)
                .build());

        byte[] data = new byte[350];

        try {
            uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
            // leavePartsOnError=true, so abort should NOT be called
            assertEquals(0, mock.abortMultipartCount.get());
        }
    }

    @Test
    public void testPutObjectFail() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.putObjectError = true;
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        byte[] data = new byte[100];

        try {
            uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
            assertEquals("", e.uploadId());
            assertEquals("oss://bucket/key", e.path());
        }
    }

    @Test
    public void testPutObjectFailFromFile() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        mock.putObjectError = true;
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        File tempFile = File.createTempFile("upload-putfail-", ".txt");
        tempFile.deleteOnExit();
        byte[] data = new byte[100];
        Files.write(tempFile.toPath(), data);

        try {
            uploader.uploadFile(defaultRequest(), tempFile.getAbsolutePath());
            fail("Expected UploadError");
        } catch (UploadError e) {
            assertNotNull(e.getCause());
            assertEquals("", e.uploadId());
            assertEquals("oss://bucket/key", e.path());
        }
        tempFile.delete();
    }

    // endregion

    // region Content Type Detection

    @Test
    public void testFileUploadContentTypeText() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        File tempFile = File.createTempFile("upload-ct-", ".txt");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), new byte[100]);

        uploader.uploadFile(defaultRequest(), tempFile.getAbsolutePath());
        assertEquals("text/plain", mock.lastContentType);
        tempFile.delete();
    }

    @Test
    public void testFileUploadContentTypeHtml() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        File tempFile = File.createTempFile("upload-ct-", ".html");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), new byte[100]);

        uploader.uploadFile(defaultRequest(), tempFile.getAbsolutePath());
        assertEquals("text/html", mock.lastContentType);
        tempFile.delete();
    }

    @Test
    public void testMultipartFileUploadContentType() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .enableCRC64Check(false)
                .build());

        File tempFile = File.createTempFile("upload-ct-", ".tif");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), new byte[350]);

        uploader.uploadFile(defaultRequest(), tempFile.getAbsolutePath());
        assertEquals("image/tiff", mock.lastContentType);
        tempFile.delete();
    }

    // endregion

    // region Stream Upload (unknown size)

    @Test
    public void testUploadFromStreamUnknownSize() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);

        int partSize = 100 * 1024;
        byte[] data = randomData(5 * 100 * 1024 + 123);
        String dataCrc = computeCRC64(data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(4)
                .enableCRC64Check(false)
                .build());

        // Wrap in a stream that doesn't report length
        InputStream stream = new ByteArrayInputStream(data);
        BinaryData body = BinaryData.fromStream(stream);

        UploadResult result = uploader.uploadFrom(defaultRequest(), body);
        assertNotNull(result);
        assertEquals("uploadId-1234", result.uploadId());

        byte[] reassembled = reassembleParts(mock, 6);
        assertEquals(dataCrc, computeCRC64(reassembled));
    }

    // endregion

    // region RequestPayer

    @Test
    public void testUploadWithRequestPayer() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        byte[] data = new byte[100];
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .requestPayer("requester")
                .build();

        UploadResult result = uploader.uploadFrom(request, BinaryData.fromBytes(data));
        assertNotNull(result);
        assertEquals(1, mock.putObjectCount.get());
    }

    @Test
    public void testMultipartUploadWithRequestPayer() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);

        byte[] data = new byte[350];
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .requestPayer("requester")
                .build();

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .enableCRC64Check(false)
                .build());

        UploadResult result = uploader.uploadFrom(request, BinaryData.fromBytes(data));
        assertNotNull(result);
        assertEquals(1, mock.initiateMultipartCount.get());
        assertEquals(4, mock.uploadPartCount.get());
        assertEquals(1, mock.completeMultipartCount.get());
    }

    @Test
    public void testUploadFileWithRequestPayer() throws Exception {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        File tempFile = File.createTempFile("upload-payer-", ".txt");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), new byte[100]);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .requestPayer("requester")
                .build();

        UploadResult result = uploader.uploadFile(request, tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(1, mock.putObjectCount.get());
        tempFile.delete();
    }

    // endregion

    // region Options

    @Test
    public void testUploaderOptionsDefaults() {
        UploaderOptions opts = UploaderOptions.defaults();
        assertEquals(Defaults.DEFAULT_UPLOAD_PART_SIZE, opts.partSize());
        assertEquals(Defaults.DEFAULT_UPLOAD_PARALLEL, opts.parallelNum());
        assertFalse(opts.leavePartsOnError());
        assertFalse(opts.enableCheckpoint());
        assertNull(opts.checkpointDir());
        assertTrue(opts.enableCRC64Check());
    }

    @Test
    public void testUploaderOptionsCustom() {
        UploaderOptions opts = UploaderOptions.newBuilder()
                .partSize(1024 * 1024)
                .parallelNum(5)
                .leavePartsOnError(true)
                .enableCheckpoint(true)
                .checkpointDir("/tmp/cp")
                .enableCRC64Check(false)
                .build();
        assertEquals(1024 * 1024, opts.partSize());
        assertEquals(5, opts.parallelNum());
        assertTrue(opts.leavePartsOnError());
        assertTrue(opts.enableCheckpoint());
        assertEquals("/tmp/cp", opts.checkpointDir());
        assertFalse(opts.enableCRC64Check());
    }

    @Test
    public void testUploaderOverrideOptions() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        assertEquals(Defaults.DEFAULT_UPLOAD_PART_SIZE, uploader.options().partSize());

        byte[] data = new byte[350];

        UploaderOptions overrideOpts = UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .enableCRC64Check(false)
                .build();

        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data), overrideOpts);
        assertNotNull(result);
        assertEquals(1, mock.initiateMultipartCount.get());
        assertEquals(4, mock.uploadPartCount.get());

        // Original options should be unchanged
        assertEquals(Defaults.DEFAULT_UPLOAD_PART_SIZE, uploader.options().partSize());
    }

    @Test
    public void testPartSizeAutoAdjust() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);

        // Create data that requires part size adjustment
        // With partSize=100, 100*10000=1,000,000 bytes max. Data of 1,100,000 bytes needs adjustment.
        int partSize = 100;
        byte[] data = new byte[partSize * Defaults.MAX_UPLOAD_PARTS + 100]; // exceeds max parts
        Arrays.fill(data, (byte) 'X');

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(1)
                .enableCRC64Check(false)
                .build());

        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);
        // Part size should have been adjusted so parts <= MAX_UPLOAD_PARTS
        assertTrue(mock.uploadPartCount.get() <= Defaults.MAX_UPLOAD_PARTS);
    }

    // endregion

    // region Result Fields

    @Test
    public void testUploadResultFields() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client);

        byte[] data = new byte[100];
        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);
        assertNull(result.uploadId()); // single part
        assertEquals("\"etag-single\"", result.etag());
        assertEquals("version-1", result.versionId());
        assertNotNull(result.hashCrc64ecma());
        assertEquals(200, result.statusCode());
        assertNotNull(result.headers());
    }

    @Test
    public void testMultipartUploadResultFields() throws UploadError {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .enableCRC64Check(false)
                .build());

        byte[] data = new byte[350];
        UploadResult result = uploader.uploadFrom(defaultRequest(), BinaryData.fromBytes(data));
        assertNotNull(result);
        assertEquals("uploadId-1234", result.uploadId());
        assertEquals("\"etag-complete\"", result.etag());
        assertEquals("version-2", result.versionId());
        assertNotNull(result.hashCrc64ecma());
        assertEquals(200, result.statusCode());
    }

    // endregion

    // region Constructor

    @Test(expected = NullPointerException.class)
    public void testUploaderNullClient() {
        new Uploader(null);
    }

    @Test(expected = NullPointerException.class)
    public void testUploaderNullOptions() {
        UploaderMockHttpClient mock = new UploaderMockHttpClient();
        OSSClient client = createMockClient(mock);
        new Uploader(client, null);
    }

    // endregion
}
