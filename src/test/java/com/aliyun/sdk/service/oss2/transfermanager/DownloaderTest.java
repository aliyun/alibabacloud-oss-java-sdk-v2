package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.hash.CRC64;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.transport.*;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

public class DownloaderTest {

    // region Mock HTTP Client

    static class DownloaderMockHttpClient implements HttpClient {
        final AtomicInteger headObjectCount = new AtomicInteger(0);
        final AtomicInteger getObjectCount = new AtomicInteger(0);

        byte[] sourceData;
        boolean headObjectError = false;
        boolean getObjectError = false;

        // Return wrong CRC64 in HeadObject response
        boolean headRequestCRCErr = false;

        // Fail at specific part number (0-based offset = failPartNum * partSize)
        int failPartNum = 0;
        int partSize = 0;
        long rStart = 0;

        // Change ETag after this offset
        long etagChangeOffset = 0;

        // Return only half the body data (once), simulating network truncation
        volatile boolean halfBodyErr = false;

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

            if ("HEAD".equals(method)) {
                return handleHeadObject(request);
            } else if ("GET".equals(method)) {
                return handleGetObject(request);
            }

            return buildResponse(400, new byte[0], Collections.emptyMap());
        }

        private ResponseMessage handleHeadObject(RequestMessage request) {
            headObjectCount.incrementAndGet();
            if (headObjectError) {
                return buildResponse(403, ERROR_XML.getBytes(), Collections.emptyMap());
            }

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Length", String.valueOf(sourceData.length));
            headers.put("ETag", "\"source-etag\"");
            headers.put("Last-Modified", "Wed, 01 Jan 2025 00:00:00 GMT");

            if (headRequestCRCErr) {
                headers.put("x-oss-hash-crc64ecma", "12345");
            } else {
                CRC64 crc = new CRC64(0);
                crc.update(sourceData, 0, sourceData.length);
                headers.put("x-oss-hash-crc64ecma", Long.toUnsignedString(crc.getValue()));
            }

            return buildResponse(200, new byte[0], headers);
        }

        private ResponseMessage handleGetObject(RequestMessage request) {
            getObjectCount.incrementAndGet();
            if (getObjectError) {
                return buildResponse(403, ERROR_XML.getBytes(), Collections.emptyMap());
            }

            // Parse range header
            String rangeHeader = request.headers() != null ? request.headers().get("Range") : null;
            byte[] sendData = sourceData;
            int statusCode = 200;
            int start = 0;
            int end = sourceData.length - 1;

            if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
                String range = rangeHeader.substring(6);
                String[] parts = range.split("-", 2);
                start = Integer.parseInt(parts[0]);
                end = parts.length > 1 && !parts[1].isEmpty()
                        ? Integer.parseInt(parts[1]) : sourceData.length - 1;
                end = Math.min(end, sourceData.length - 1);
                sendData = Arrays.copyOfRange(sourceData, start, end + 1);
                statusCode = 206;
            }

            // Check failPartNum
            if (failPartNum > 0 && partSize > 0) {
                long failOffset = (long) failPartNum * partSize + rStart;
                if (start == failOffset) {
                    return buildResponse(403, ERROR_XML.getBytes(), Collections.emptyMap());
                }
            }

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Length", String.valueOf(sendData.length));

            // ETag change simulation
            if (etagChangeOffset > 0 && start > 0 && start > etagChangeOffset) {
                headers.put("ETag", "\"changed-etag\"");
            } else {
                headers.put("ETag", "\"source-etag\"");
            }

            // Content-Range for 206
            if (statusCode == 206) {
                headers.put("Content-Range",
                        "bytes " + start + "-" + end + "/" + sourceData.length);
            }

            // Half body simulation: return truncated data but keep original Content-Length
            // This simulates a network disconnect mid-transfer
            if (halfBodyErr) {
                halfBodyErr = false;
                sendData = Arrays.copyOf(sendData, sendData.length / 2);
            }

            return buildResponse(statusCode, sendData, headers);
        }

        private ResponseMessage buildResponse(int statusCode, byte[] body, Map<String, String> headers) {
            ResponseMessage.Builder builder = ResponseMessage.newBuilder()
                    .statusCode(statusCode)
                    .body(BinaryData.fromBytes(body));
            if (headers != null && !headers.isEmpty()) {
                builder.headers(headers);
            }
            return builder.build();
        }
    }

    // endregion

    private OSSClient createMockClient(DownloaderMockHttpClient mockHandler) {
        return OSSClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mockHandler)
                .disableUploadCRC64Check(true)
                .build();
    }

    private static byte[] createTestData(int size) {
        byte[] data = new byte[size];
        new Random(42).nextBytes(data);
        return data;
    }

    private static long computeCRC64(byte[] data) {
        CRC64 crc = new CRC64(0);
        crc.update(data, 0, data.length);
        return crc.getValue();
    }

    private static long computeCRC64(byte[] data, int offset, int len) {
        CRC64 crc = new CRC64(0);
        crc.update(data, offset, len);
        return crc.getValue();
    }

    // region Options Tests

    @Test
    public void testDownloaderOptionsDefaults() {
        DownloaderOptions opts = DownloaderOptions.defaults();
        assertEquals(Defaults.DEFAULT_DOWNLOAD_PART_SIZE, opts.partSize());
        assertEquals(Defaults.DEFAULT_DOWNLOAD_PARALLEL, opts.parallelNum());
        assertTrue(opts.useTempFile());
        assertTrue(opts.enableCRC64Check());
    }

    @Test
    public void testDownloaderOptionsCustom() {
        DownloaderOptions opts = DownloaderOptions.newBuilder()
                .partSize(1024 * 1024)
                .parallelNum(5)
                .useTempFile(false)
                .enableCRC64Check(true)
                .build();
        assertEquals(1024 * 1024, opts.partSize());
        assertEquals(5, opts.parallelNum());
        assertFalse(opts.useTempFile());
        assertTrue(opts.enableCRC64Check());
    }

    @Test
    public void testDownloaderConstructorOptions() {
        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = new byte[0];
        OSSClient client = createMockClient(mock);

        Downloader d = new Downloader(client);
        assertEquals(Defaults.DEFAULT_DOWNLOAD_PART_SIZE, d.options().partSize());
        assertEquals(Defaults.DEFAULT_DOWNLOAD_PARALLEL, d.options().parallelNum());

        DownloaderOptions opts = DownloaderOptions.newBuilder()
                .partSize(2 * 1024 * 1024)
                .parallelNum(5)
                .build();
        Downloader d2 = new Downloader(client, opts);
        assertEquals(2 * 1024 * 1024, d2.options().partSize());
        assertEquals(5, d2.options().parallelNum());
    }

    @Test
    public void testWriteBufferSizeAlignment() {
        DownloaderOptions opts = DownloaderOptions.newBuilder()
                .writeBufferSize(4096)
                .build();
        assertEquals(4096, opts.writeBufferSize());

        opts = DownloaderOptions.newBuilder()
                .writeBufferSize(4095)
                .build();
        assertEquals(4096, opts.writeBufferSize());

        opts = DownloaderOptions.newBuilder()
                .writeBufferSize(4097)
                .build();
        assertEquals(8192, opts.writeBufferSize());

        opts = DownloaderOptions.newBuilder()
                .writeBufferSize(0)
                .build();
        assertEquals(0, opts.writeBufferSize());
    }

    // endregion

    // region Argument Validation

    @Test(expected = IllegalArgumentException.class)
    public void testDownloadFileNullRequest() throws DownloadError {
        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = new byte[0];
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client);
        downloader.downloadFile(null, "/tmp/test.dat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDownloadFileNullBucket() throws DownloadError {
        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = new byte[0];
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client);
        GetObjectRequest request = GetObjectRequest.newBuilder()
                .key("key")
                .build();
        downloader.downloadFile(request, "/tmp/test.dat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDownloadFileNullKey() throws DownloadError {
        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = new byte[0];
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client);
        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .build();
        downloader.downloadFile(request, "/tmp/test.dat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDownloadFileNullPath() throws DownloadError {
        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = new byte[0];
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client);
        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();
        downloader.downloadFile(request, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDownloadFileEmptyPath() throws DownloadError {
        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = new byte[0];
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client);
        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();
        downloader.downloadFile(request, "");
    }

    // endregion

    // region Single Download

    @Test
    public void testSingleDownload() throws Exception {
        int length = 1024;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(2 * 1024 * 1024) // larger than file
                .parallelNum(1)
                .useTempFile(false)
                .build());

        File tempFile = File.createTempFile("download-single-", ".dat");
        tempFile.deleteOnExit();
        String filePath = tempFile.getAbsolutePath();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, filePath);
        assertNotNull(result);
        assertEquals(length, result.written());
        assertEquals(1, mock.headObjectCount.get());
        assertEquals(1, mock.getObjectCount.get());

        // Verify content
        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        tempFile.delete();
    }

    @Test
    public void testSingleDownloadWithRange() throws Exception {
        int length = 1024;
        byte[] data = createTestData(length);
        int rangeStart = 100;
        int rangeCount = 500;

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(2 * 1024 * 1024)
                .parallelNum(1)
                .useTempFile(false)
                .build());

        File tempFile = File.createTempFile("download-range-single-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .range("bytes=" + rangeStart + "-" + (rangeStart + rangeCount - 1))
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(rangeCount, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        byte[] expected = Arrays.copyOfRange(data, rangeStart, rangeStart + rangeCount);
        assertArrayEquals(expected, downloaded);
        tempFile.delete();
    }

    @Test
    public void testDownloadFileSizeLessPartSize() throws Exception {
        int length = 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(1024 * 1024)
                .parallelNum(3)
                .useTempFile(false)
                .build());

        File tempFile = File.createTempFile("download-small-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertEquals(length, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        // Only 1 GET call since file < partSize
        assertEquals(1, mock.getObjectCount.get());
        tempFile.delete();
    }

    @Test
    public void testEmptyFile() throws Exception {
        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = new byte[0];
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .useTempFile(false)
                .build());

        File tempFile = File.createTempFile("download-empty-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertEquals(0, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertEquals(0, downloaded.length);
        // No GET call for empty file
        assertEquals(0, mock.getObjectCount.get());
        tempFile.delete();
    }

    // endregion

    // region Parallel Download

    @Test
    public void testParallelDownload() throws Exception {
        int length = 3 * 1024 * 1024 + 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(1024 * 1024)
                .parallelNum(3)
                .useTempFile(true)
                .build());

        File tempFile = File.createTempFile("download-parallel-", ".dat");
        tempFile.deleteOnExit();
        String filePath = tempFile.getAbsolutePath();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, filePath);
        assertNotNull(result);
        assertEquals(length, result.written());
        assertEquals(1, mock.headObjectCount.get());
        assertTrue(mock.getObjectCount.get() >= 4); // 3MB/1MB + 1 partial = 4 parts

        // Verify content
        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        tempFile.delete();
    }

    @Test
    public void testParallelDownloadWithRange() throws Exception {
        int length = 1024;
        byte[] data = createTestData(length);
        int rangeStart = 50;
        int rangeCount = 800;

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(200)
                .parallelNum(3)
                .useTempFile(false)
                .build());

        File tempFile = File.createTempFile("download-range-parallel-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .range("bytes=" + rangeStart + "-" + (rangeStart + rangeCount - 1))
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertEquals(rangeCount, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        byte[] expected = Arrays.copyOfRange(data, rangeStart, rangeStart + rangeCount);
        assertArrayEquals(expected, downloaded);
        tempFile.delete();
    }

    @Test
    public void testDownloadWithoutTempFile() throws Exception {
        int length = 3 * 1024 + 123;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(1024)
                .parallelNum(2)
                .useTempFile(false)
                .build());

        File tempFile = File.createTempFile("download-notemp-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertEquals(length, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        tempFile.delete();
    }

    // endregion

    // region Temp File

    @Test
    public void testUseTempFile() throws Exception {
        int length = 1024;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(2 * 1024 * 1024)
                .parallelNum(1)
                .useTempFile(true)
                .build());

        File tempFile = File.createTempFile("download-tempfile-", ".dat");
        tempFile.deleteOnExit();
        String filePath = tempFile.getAbsolutePath();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, filePath);
        assertNotNull(result);

        // Verify that temp file was renamed to final path
        assertTrue(new File(filePath).exists());
        assertFalse(new File(filePath + Defaults.TEMP_FILE_SUFFIX).exists());

        byte[] downloaded = Files.readAllBytes(new File(filePath).toPath());
        assertArrayEquals(data, downloaded);
        new File(filePath).delete();
    }

    // endregion

    // region Error Cases

    @Test
    public void testHeadObjectFail() {
        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = new byte[100];
        mock.headObjectError = true;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client);

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        try {
            File tempFile = File.createTempFile("download-err-", ".dat");
            tempFile.deleteOnExit();
            downloader.downloadFile(request, tempFile.getAbsolutePath());
            fail("Expected DownloadError");
        } catch (DownloadError e) {
            assertNotNull(e.getCause());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testGetObjectFail() {
        byte[] data = createTestData(1024);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        mock.getObjectError = true;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(2 * 1024 * 1024)
                .parallelNum(1)
                .useTempFile(false)
                .build());

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        try {
            File tempFile = File.createTempFile("download-err-", ".dat");
            tempFile.deleteOnExit();
            downloader.downloadFile(request, tempFile.getAbsolutePath());
            fail("Expected DownloadError");
        } catch (DownloadError e) {
            assertNotNull(e.getCause());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testDownloadWithInvalidPath() {
        byte[] data = createTestData(1024);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(2 * 1024 * 1024)
                .parallelNum(1)
                .useTempFile(false)
                .build());

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        try {
            downloader.downloadFile(request, "./no-exist-folder/file-no-surfix");
            fail("Expected DownloadError");
        } catch (DownloadError e) {
            assertNotNull(e.getCause());
        }
    }

    @Test
    public void testDownloadFileChange() throws Exception {
        int length = 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        mock.etagChangeOffset = 700;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(128)
                .parallelNum(3)
                .useTempFile(true)
                .build());

        File tempFile = File.createTempFile("download-change-", ".dat");
        tempFile.deleteOnExit();
        String filePath = tempFile.getAbsolutePath();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        try {
            downloader.downloadFile(request, filePath);
            fail("Expected DownloadError due to ETag change");
        } catch (DownloadError e) {
            assertTrue(e.getCause().getMessage().contains("Source file is changed")
                    || e.getCause().getMessage().contains("GetObject failed"));
        }

        // Temp file should be cleaned up
        assertFalse(new File(filePath + Defaults.TEMP_FILE_SUFFIX).exists());
    }

    // endregion

    // region Override Options

    @Test
    public void testOverrideOptions() throws Exception {
        int length = 1024;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client); // default options

        File tempFile = File.createTempFile("download-override-", ".dat");
        tempFile.deleteOnExit();
        String filePath = tempFile.getAbsolutePath();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloaderOptions overrideOpts = DownloaderOptions.newBuilder()
                .partSize(2 * 1024 * 1024)
                .parallelNum(1)
                .useTempFile(false)
                .build();

        DownloadResult result = downloader.downloadFile(request, filePath, overrideOpts);
        assertNotNull(result);
        assertEquals(length, result.written());
        tempFile.delete();
    }

    // endregion

    // region Checkpoint Tests

    @Test
    public void testEnableCheckpointNormal() throws Exception {
        int length = 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client);

        File tempFile = File.createTempFile("download-cp-normal-", ".dat");
        tempFile.deleteOnExit();

        File cpDir = Files.createTempDirectory("download-cp-dir-").toFile();
        cpDir.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath(),
                DownloaderOptions.newBuilder()
                        .partSize(128)
                        .parallelNum(3)
                        .checkpointDir(cpDir.getAbsolutePath())
                        .enableCheckpoint(true)
                        .build());
        assertNotNull(result);
        assertEquals(length, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);

        // Checkpoint file should be removed after success
        File[] cpFiles = cpDir.listFiles((dir, name) -> name.endsWith(".dcp"));
        assertTrue(cpFiles == null || cpFiles.length == 0);

        tempFile.delete();
        cpDir.delete();
    }

    @Test
    public void testEnableCheckpointResume() throws Exception {
        int partSize = 128;
        int length = 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        mock.failPartNum = 6;
        mock.partSize = partSize;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client);

        File tempFile = File.createTempFile("download-cp-resume-", ".dat");
        tempFile.deleteOnExit();
        String filePath = tempFile.getAbsolutePath();

        File cpDir = Files.createTempDirectory("download-cp-dir-").toFile();
        cpDir.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        // First download - should fail at part 6
        try {
            downloader.downloadFile(request, filePath,
                    DownloaderOptions.newBuilder()
                            .partSize(partSize)
                            .parallelNum(3)
                            .checkpointDir(cpDir.getAbsolutePath())
                            .enableCheckpoint(true)
                            .build());
            fail("Expected DownloadError");
        } catch (DownloadError e) {
            // expected
        }

        // Temp file and checkpoint should exist
        assertTrue(new File(filePath + Defaults.TEMP_FILE_SUFFIX).exists());
        File[] cpFiles = cpDir.listFiles((dir, name) -> name.endsWith(".dcp"));
        assertNotNull(cpFiles);
        assertEquals(1, cpFiles.length);

        // Resume download - remove the fail condition
        mock.failPartNum = 0;
        DownloadResult result = downloader.downloadFile(request, filePath,
                DownloaderOptions.newBuilder()
                        .partSize(partSize)
                        .parallelNum(3)
                        .checkpointDir(cpDir.getAbsolutePath())
                        .enableCheckpoint(true)
                        .build());

        assertNotNull(result);
        assertEquals(length, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);

        // Checkpoint file should be cleaned up
        cpFiles = cpDir.listFiles((dir, name) -> name.endsWith(".dcp"));
        assertTrue(cpFiles == null || cpFiles.length == 0);

        tempFile.delete();
        cpDir.delete();
    }

    @Test
    public void testEnableCheckpointWithRange() throws Exception {
        int partSize = 128;
        int length = 1234;
        byte[] data = createTestData(length);
        int rs = 5;
        int rcount = 832;

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        mock.failPartNum = 6;
        mock.partSize = partSize;
        mock.rStart = rs;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client);

        File tempFile = File.createTempFile("download-cp-range-", ".dat");
        tempFile.deleteOnExit();
        String filePath = tempFile.getAbsolutePath();

        File cpDir = Files.createTempDirectory("download-cp-dir-").toFile();
        cpDir.deleteOnExit();

        String rangeHeader = "bytes=" + rs + "-" + (rs + rcount - 1);
        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .range(rangeHeader)
                .build();

        // First download - should fail
        try {
            downloader.downloadFile(request, filePath,
                    DownloaderOptions.newBuilder()
                            .partSize(partSize)
                            .parallelNum(3)
                            .checkpointDir(cpDir.getAbsolutePath())
                            .enableCheckpoint(true)
                            .build());
            fail("Expected DownloadError");
        } catch (DownloadError e) {
            // expected
        }

        // Resume download
        mock.failPartNum = 0;
        DownloadResult result = downloader.downloadFile(request, filePath,
                DownloaderOptions.newBuilder()
                        .partSize(partSize)
                        .parallelNum(3)
                        .checkpointDir(cpDir.getAbsolutePath())
                        .enableCheckpoint(true)
                        .build());

        assertNotNull(result);
        long expectLen = Math.min(length - rs, rcount);
        assertEquals(expectLen, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        byte[] expected = Arrays.copyOfRange(data, rs, (int) Math.min(rs + rcount, length));
        assertArrayEquals(expected, downloaded);

        tempFile.delete();
        cpDir.delete();
    }

    // endregion

    // region CRC64 Tests

    @Test
    public void testCRCCheckSuccess() throws Exception {
        int length = 5 * 100 * 1024 + 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(100 * 1024)
                .parallelNum(3)
                .useTempFile(false)
                .enableCRC64Check(true)
                .build());

        File tempFile = File.createTempFile("download-crc-ok-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(length, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        tempFile.delete();
    }

    @Test
    public void testCRCCheckSingleThread() throws Exception {
        int length = 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(2 * 1024 * 1024) // larger than data, single thread
                .parallelNum(1)
                .useTempFile(false)
                .enableCRC64Check(true)
                .build());

        File tempFile = File.createTempFile("download-crc-single-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertEquals(length, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        tempFile.delete();
    }

    @Test
    public void testCRCCheckMismatch() throws Exception {
        int length = 5 * 100 * 1024 + 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        mock.headRequestCRCErr = true; // Server returns wrong CRC
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(100 * 1024)
                .parallelNum(3)
                .useTempFile(false)
                .enableCRC64Check(true)
                .build());

        File tempFile = File.createTempFile("download-crc-err-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        try {
            downloader.downloadFile(request, tempFile.getAbsolutePath());
            fail("Expected DownloadError due to CRC mismatch");
        } catch (DownloadError e) {
            assertTrue(e.getCause().getMessage().contains("CRC64 mismatch"));
        }

        tempFile.delete();
    }

    @Test
    public void testCRCCheckDisabled() throws Exception {
        int length = 5 * 100 * 1024 + 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        mock.headRequestCRCErr = true; // Wrong CRC, but check is disabled
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(100 * 1024)
                .parallelNum(3)
                .useTempFile(false)
                .enableCRC64Check(false)
                .build());

        File tempFile = File.createTempFile("download-crc-disabled-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(length, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        tempFile.delete();
    }

    @Test
    public void testCRCCheckSkippedForRange() throws Exception {
        int length = 5 * 100 * 1024 + 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        mock.headRequestCRCErr = true; // Wrong CRC, but should be skipped for range
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(100 * 1024)
                .parallelNum(3)
                .useTempFile(false)
                .enableCRC64Check(true)
                .build());

        File tempFile = File.createTempFile("download-crc-range-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .range("bytes=0-1023")
                .build();

        // Should succeed because CRC check is skipped for range requests
        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(1024, result.written());
        tempFile.delete();
    }

    @Test
    public void testCRCCheckWithCheckpointResume() throws Exception {
        int partSize = 128;
        int length = 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        mock.failPartNum = 6;
        mock.partSize = partSize;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client);

        File tempFile = File.createTempFile("download-crc-cp-resume-", ".dat");
        tempFile.deleteOnExit();
        String filePath = tempFile.getAbsolutePath();

        File cpDir = Files.createTempDirectory("download-crc-cp-dir-").toFile();
        cpDir.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        // First download - should fail at part 6
        try {
            downloader.downloadFile(request, filePath,
                    DownloaderOptions.newBuilder()
                            .partSize(partSize)
                            .parallelNum(3)
                            .checkpointDir(cpDir.getAbsolutePath())
                            .enableCheckpoint(true)
                            .enableCRC64Check(true)
                            .build());
            fail("Expected DownloadError");
        } catch (DownloadError e) {
            // expected
        }

        // Verify checkpoint has CRC64
        File[] cpFiles = cpDir.listFiles((dir, name) -> name.endsWith(".dcp"));
        assertNotNull(cpFiles);
        assertEquals(1, cpFiles.length);

        // Resume download
        mock.failPartNum = 0;
        DownloadResult result = downloader.downloadFile(request, filePath,
                DownloaderOptions.newBuilder()
                        .partSize(partSize)
                        .parallelNum(3)
                        .checkpointDir(cpDir.getAbsolutePath())
                        .enableCheckpoint(true)
                        .enableCRC64Check(true)
                        .build());

        assertNotNull(result);
        assertEquals(length, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);

        tempFile.delete();
        cpDir.delete();
    }

    // endregion

    // region Progress Tests

    @Test
    public void testProgressSingleThread() throws Exception {
        int length = 3 * 1024 * 1024 + 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);

        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicInteger callCount = new AtomicInteger(0);
        AtomicInteger finishCount = new AtomicInteger(0);

        ProgressListener listener = new ProgressListener() {
            @Override
            public void onProgress(long increment, long transferred, long total) {
                assertTrue(increment > 0);
                assertTrue(transferred > 0);
                lastTransferred.set(transferred);
                totalIncrement.addAndGet(increment);
                callCount.incrementAndGet();
            }

            @Override
            public void onFinish() {
                finishCount.incrementAndGet();
            }
        };

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(1024 * 1024)
                .parallelNum(1)
                .useTempFile(false)
                .build());

        File tempFile = File.createTempFile("download-progress-single-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .progressListener(listener)
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertEquals(length, result.written());
        assertEquals(length, lastTransferred.get());
        assertEquals(length, totalIncrement.get());
        assertTrue(callCount.get() > 0);
        assertEquals(1, finishCount.get());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        tempFile.delete();
    }

    @Test
    public void testProgressParallel() throws Exception {
        int length = 3 * 1024 * 1024 + 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);

        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicInteger finishCount = new AtomicInteger(0);

        ProgressListener listener = new ProgressListener() {
            @Override
            public void onProgress(long increment, long transferred, long total) {
                lastTransferred.set(transferred);
                totalIncrement.addAndGet(increment);
            }

            @Override
            public void onFinish() {
                finishCount.incrementAndGet();
            }
        };

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(1024 * 1024)
                .parallelNum(3)
                .useTempFile(false)
                .build());

        File tempFile = File.createTempFile("download-progress-parallel-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .progressListener(listener)
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertEquals(length, result.written());
        assertEquals(length, lastTransferred.get());
        assertEquals(length, totalIncrement.get());
        assertEquals(1, finishCount.get());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        tempFile.delete();
    }

    @Test
    public void testProgressWithCheckpointResume() throws Exception {
        int partSize = 128;
        int length = 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        mock.failPartNum = 6;
        mock.partSize = partSize;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client);

        File tempFile = File.createTempFile("download-progress-cp-", ".dat");
        tempFile.deleteOnExit();
        String filePath = tempFile.getAbsolutePath();

        File cpDir = Files.createTempDirectory("download-progress-cp-dir-").toFile();
        cpDir.deleteOnExit();

        AtomicLong totalIncrement1 = new AtomicLong(0);
        ProgressListener listener1 = new ProgressListener() {
            @Override
            public void onProgress(long increment, long transferred, long total) {
                totalIncrement1.addAndGet(increment);
            }

            @Override
            public void onFinish() {
            }
        };

        GetObjectRequest request1 = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .progressListener(listener1)
                .build();

        // First download - should fail at part 6
        try {
            downloader.downloadFile(request1, filePath,
                    DownloaderOptions.newBuilder()
                            .partSize(partSize)
                            .parallelNum(3)
                            .checkpointDir(cpDir.getAbsolutePath())
                            .enableCheckpoint(true)
                            .build());
            fail("Expected DownloadError");
        } catch (DownloadError e) {
            // expected
        }

        assertTrue(totalIncrement1.get() > 0);
        assertTrue(totalIncrement1.get() < length);

        // Resume download
        mock.failPartNum = 0;
        AtomicLong totalIncrement2 = new AtomicLong(0);
        AtomicInteger finishCount = new AtomicInteger(0);
        ProgressListener listener2 = new ProgressListener() {
            @Override
            public void onProgress(long increment, long transferred, long total) {
                totalIncrement2.addAndGet(increment);
            }

            @Override
            public void onFinish() {
                finishCount.incrementAndGet();
            }
        };

        GetObjectRequest request2 = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .progressListener(listener2)
                .build();

        DownloadResult result = downloader.downloadFile(request2, filePath,
                DownloaderOptions.newBuilder()
                        .partSize(partSize)
                        .parallelNum(3)
                        .checkpointDir(cpDir.getAbsolutePath())
                        .enableCheckpoint(true)
                        .build());

        assertEquals(length, result.written());
        // Total increment of second run should include resumed part + new part
        assertEquals(length, totalIncrement2.get());
        assertEquals(1, finishCount.get());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);

        tempFile.delete();
        cpDir.delete();
    }

    // endregion

    // region WriteBufferSize Tests

    @Test
    public void testWriteBufferSize() throws Exception {
        int length = 5 * 100 * 1024 + 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(100 * 1024)
                .parallelNum(3)
                .useTempFile(false)
                .writeBufferSize(16 * 1024)
                .build());

        File tempFile = File.createTempFile("download-wbuf-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(length, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        tempFile.delete();
    }

    @Test
    public void testWriteBufferSizeWithCRC() throws Exception {
        int length = 5 * 100 * 1024 + 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);
        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(100 * 1024)
                .parallelNum(3)
                .useTempFile(false)
                .writeBufferSize(16 * 1024)
                .enableCRC64Check(true)
                .build());

        File tempFile = File.createTempFile("download-wbuf-crc-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(length, result.written());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        tempFile.delete();
    }

    // endregion

    // region Combined Features Tests

    @Test
    public void testProgressAndCRCTogether() throws Exception {
        int length = 3 * 1024 * 1024 + 1234;
        byte[] data = createTestData(length);

        DownloaderMockHttpClient mock = new DownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = createMockClient(mock);

        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicInteger finishCount = new AtomicInteger(0);

        ProgressListener listener = new ProgressListener() {
            @Override
            public void onProgress(long increment, long transferred, long total) {
                lastTransferred.set(transferred);
            }

            @Override
            public void onFinish() {
                finishCount.incrementAndGet();
            }
        };

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(1024 * 1024)
                .parallelNum(3)
                .useTempFile(false)
                .enableCRC64Check(true)
                .build());

        File tempFile = File.createTempFile("download-combined-", ".dat");
        tempFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .progressListener(listener)
                .build();

        DownloadResult result = downloader.downloadFile(request, tempFile.getAbsolutePath());
        assertEquals(length, result.written());
        assertEquals(length, lastTransferred.get());
        assertEquals(1, finishCount.get());

        byte[] downloaded = Files.readAllBytes(tempFile.toPath());
        assertArrayEquals(data, downloaded);
        tempFile.delete();
    }

    // endregion
}
