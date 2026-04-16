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

public class DownloaderTest {

    // region Mock HTTP Client

    static class DownloaderMockHttpClient implements HttpClient {
        final AtomicInteger headObjectCount = new AtomicInteger(0);
        final AtomicInteger getObjectCount = new AtomicInteger(0);

        byte[] sourceData;
        boolean headObjectError = false;
        boolean getObjectError = false;

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
            headers.put("x-oss-hash-crc64ecma", "1234567890");
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

            if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
                String range = rangeHeader.substring(6);
                String[] parts = range.split("-", 2);
                int start = Integer.parseInt(parts[0]);
                int end = parts.length > 1 && !parts[1].isEmpty()
                        ? Integer.parseInt(parts[1]) : sourceData.length - 1;
                end = Math.min(end, sourceData.length - 1);
                sendData = Arrays.copyOfRange(sourceData, start, end + 1);
                statusCode = 206;
            }

            Map<String, String> headers = MapUtils.caseInsensitiveMap();
            headers.put("Content-Length", String.valueOf(sendData.length));
            headers.put("ETag", "\"source-etag\"");
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

    // region Options Tests

    @Test
    public void testDownloaderOptionsDefaults() {
        DownloaderOptions opts = DownloaderOptions.defaults();
        assertEquals(Defaults.DEFAULT_DOWNLOAD_PART_SIZE, opts.partSize());
        assertEquals(Defaults.DEFAULT_DOWNLOAD_PARALLEL, opts.parallelNum());
        assertTrue(opts.useTempFile());
    }

    @Test
    public void testDownloaderOptionsCustom() {
        DownloaderOptions opts = DownloaderOptions.newBuilder()
                .partSize(1024 * 1024)
                .parallelNum(5)
                .useTempFile(false)
                .build();
        assertEquals(1024 * 1024, opts.partSize());
        assertEquals(5, opts.parallelNum());
        assertFalse(opts.useTempFile());
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
        byte[] data = new byte[length];
        new Random(42).nextBytes(data);

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

    // endregion

    // region Parallel Download

    @Test
    public void testParallelDownload() throws Exception {
        int length = 3 * 1024 * 1024 + 1234;
        byte[] data = new byte[length];
        new Random(42).nextBytes(data);

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

    // endregion

    // region Temp File

    @Test
    public void testUseTempFile() throws Exception {
        int length = 1024;
        byte[] data = new byte[length];
        new Random(42).nextBytes(data);

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
        byte[] data = new byte[1024];
        new Random(42).nextBytes(data);

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

    // endregion

    // region Override Options

    @Test
    public void testOverrideOptions() throws Exception {
        int length = 1024;
        byte[] data = new byte[length];
        new Random(42).nextBytes(data);

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
}
