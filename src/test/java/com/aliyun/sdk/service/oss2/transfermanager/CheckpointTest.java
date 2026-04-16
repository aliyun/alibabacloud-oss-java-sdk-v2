package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.*;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class CheckpointTest {

    // region UploadCheckpoint Unit Tests

    @Test
    public void testUploadCheckpointCreateDumpLoad() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-upload-test");
        tmpDir.toFile().deleteOnExit();

        UploadCheckpoint cp = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 12345, "1234567890", 1024 * 1024);

        assertNotNull(cp.cpFilePath);
        assertTrue(cp.cpFilePath.endsWith(Defaults.CHECKPOINT_FILE_SUFFIX_UPLOADER));
        assertEquals(Defaults.CHECKPOINT_MAGIC, cp.info.magic);
        assertEquals("oss://bucket/key", cp.info.data.objectInfo.name);
        assertEquals(12345, cp.info.data.fileMeta.size);
        assertEquals("1234567890", cp.info.data.fileMeta.lastModified);
        assertEquals(1024 * 1024, cp.info.data.partSize);

        // Set uploadId and dump
        cp.info.data.uploadInfo.uploadId = "upload-id-123";
        cp.dump();
        assertTrue(new File(cp.cpFilePath).exists());

        // Create a new checkpoint with same params and load
        UploadCheckpoint cp2 = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 12345, "1234567890", 1024 * 1024);

        assertTrue(cp2.load());
        assertTrue(cp2.loaded);
        assertEquals("upload-id-123", cp2.info.data.uploadInfo.uploadId);

        // Cleanup
        cp2.remove();
        assertFalse(new File(cp.cpFilePath).exists());
        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testUploadCheckpointLoadNoFile() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-upload-nofile");
        tmpDir.toFile().deleteOnExit();

        UploadCheckpoint cp = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 100, "111", 1024);

        assertFalse(cp.load());
        assertFalse(cp.loaded);

        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testUploadCheckpointInvalidMagic() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-upload-badmagic");
        tmpDir.toFile().deleteOnExit();

        UploadCheckpoint cp = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 100, "111", 1024);
        cp.info.data.uploadInfo.uploadId = "uid-1";
        cp.dump();

        // Corrupt the file by changing the magic
        String content = new String(Files.readAllBytes(new File(cp.cpFilePath).toPath()), StandardCharsets.UTF_8);
        content = content.replace(Defaults.CHECKPOINT_MAGIC, "BAD-MAGIC");
        Files.write(new File(cp.cpFilePath).toPath(), content.getBytes(StandardCharsets.UTF_8));

        UploadCheckpoint cp2 = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 100, "111", 1024);
        assertFalse(cp2.load());
        // File should be removed when invalid
        assertFalse(new File(cp.cpFilePath).exists());

        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testUploadCheckpointInvalidMd5() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-upload-badmd5");
        tmpDir.toFile().deleteOnExit();

        UploadCheckpoint cp = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 100, "111", 1024);
        cp.info.data.uploadInfo.uploadId = "uid-1";
        cp.dump();

        // Corrupt the data portion to invalidate MD5
        String content = new String(Files.readAllBytes(new File(cp.cpFilePath).toPath()), StandardCharsets.UTF_8);
        content = content.replace("uid-1", "uid-corrupted");
        Files.write(new File(cp.cpFilePath).toPath(), content.getBytes(StandardCharsets.UTF_8));

        UploadCheckpoint cp2 = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 100, "111", 1024);
        assertFalse(cp2.load());

        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testUploadCheckpointMetaMismatch() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-upload-metamismatch");
        tmpDir.toFile().deleteOnExit();

        UploadCheckpoint cp = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 100, "111", 1024);
        cp.info.data.uploadInfo.uploadId = "uid-1";
        cp.dump();

        // Try to load with different file size
        UploadCheckpoint cp2 = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 200, "111", 1024); // different size
        assertFalse(cp2.load());

        // Try to load with different lastModified
        UploadCheckpoint cp3 = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 100, "222", 1024); // different lastModified

        // Dump cp again since cp2.load() removed it
        cp.dump();
        assertFalse(cp3.load());

        // Try to load with different partSize
        cp.dump();
        UploadCheckpoint cp4 = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 100, "111", 2048); // different partSize
        assertFalse(cp4.load());

        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testUploadCheckpointEmptyUploadId() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-upload-nouploadid");
        tmpDir.toFile().deleteOnExit();

        UploadCheckpoint cp = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 100, "111", 1024);
        // Don't set uploadId (default empty string)
        cp.dump();

        UploadCheckpoint cp2 = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                tmpDir.toString(), 100, "111", 1024);
        assertFalse(cp2.load()); // empty uploadId should be invalid

        deleteDir(tmpDir.toFile());
    }

    @Test(expected = IOException.class)
    public void testUploadCheckpointInvalidDir() throws Exception {
        UploadCheckpoint cp = UploadCheckpoint.create(
                "bucket", "key", "/tmp/testfile.txt",
                "/nonexistent/dir/that/does/not/exist", 100, "111", 1024);
        cp.load(); // should throw IOException
    }

    // endregion

    // region DownloadCheckpoint Unit Tests

    @Test
    public void testDownloadCheckpointCreateDumpLoad() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-download-test");
        tmpDir.toFile().deleteOnExit();

        DownloadCheckpoint cp = DownloadCheckpoint.create(
                "bucket", "key", null, null,
                "/tmp/download.dat", tmpDir.toString(),
                50000, "Wed, 01 Jan 2025 00:00:00 GMT", "\"etag-123\"",
                1024 * 1024);

        assertNotNull(cp.cpFilePath);
        assertTrue(cp.cpFilePath.endsWith(Defaults.CHECKPOINT_FILE_SUFFIX_DOWNLOADER));
        assertEquals(Defaults.CHECKPOINT_MAGIC, cp.info.magic);
        assertEquals("oss://bucket/key", cp.info.data.objectInfo.name);
        assertEquals(50000, cp.info.data.objectMeta.size);
        assertEquals("\"etag-123\"", cp.info.data.objectMeta.eTag);

        // Set download offset and dump
        cp.info.data.downloadInfo.offset = 1024 * 1024; // 1 part done
        cp.dump();
        assertTrue(new File(cp.cpFilePath).exists());

        // Create a new checkpoint with same params and load
        DownloadCheckpoint cp2 = DownloadCheckpoint.create(
                "bucket", "key", null, null,
                "/tmp/download.dat", tmpDir.toString(),
                50000, "Wed, 01 Jan 2025 00:00:00 GMT", "\"etag-123\"",
                1024 * 1024);

        assertTrue(cp2.load());
        assertTrue(cp2.loaded);
        assertEquals(1024 * 1024, cp2.info.data.downloadInfo.offset);

        // Cleanup
        cp2.remove();
        assertFalse(new File(cp.cpFilePath).exists());
        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testDownloadCheckpointLoadNoFile() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-download-nofile");
        tmpDir.toFile().deleteOnExit();

        DownloadCheckpoint cp = DownloadCheckpoint.create(
                "bucket", "key", null, null,
                "/tmp/download.dat", tmpDir.toString(),
                100, "mod", "etag", 1024);

        assertFalse(cp.load());
        assertFalse(cp.loaded);

        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testDownloadCheckpointInvalidMagic() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-download-badmagic");
        tmpDir.toFile().deleteOnExit();

        DownloadCheckpoint cp = DownloadCheckpoint.create(
                "bucket", "key", null, null,
                "/tmp/download.dat", tmpDir.toString(),
                2048, "mod", "etag", 1024);
        cp.info.data.downloadInfo.offset = 1024;
        cp.dump();

        // Corrupt magic
        String content = new String(Files.readAllBytes(new File(cp.cpFilePath).toPath()), StandardCharsets.UTF_8);
        content = content.replace(Defaults.CHECKPOINT_MAGIC, "BAD-MAGIC");
        Files.write(new File(cp.cpFilePath).toPath(), content.getBytes(StandardCharsets.UTF_8));

        DownloadCheckpoint cp2 = DownloadCheckpoint.create(
                "bucket", "key", null, null,
                "/tmp/download.dat", tmpDir.toString(),
                2048, "mod", "etag", 1024);
        assertFalse(cp2.load());

        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testDownloadCheckpointMetaMismatch() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-download-metamismatch");
        tmpDir.toFile().deleteOnExit();

        DownloadCheckpoint cp = DownloadCheckpoint.create(
                "bucket", "key", null, null,
                "/tmp/download.dat", tmpDir.toString(),
                2048, "mod", "etag", 1024);
        cp.info.data.downloadInfo.offset = 1024;
        cp.dump();

        // Different size
        DownloadCheckpoint cp2 = DownloadCheckpoint.create(
                "bucket", "key", null, null,
                "/tmp/download.dat", tmpDir.toString(),
                9999, "mod", "etag", 1024);
        assertFalse(cp2.load());

        // Different ETag
        cp.dump();
        DownloadCheckpoint cp3 = DownloadCheckpoint.create(
                "bucket", "key", null, null,
                "/tmp/download.dat", tmpDir.toString(),
                2048, "mod", "different-etag", 1024);
        assertFalse(cp3.load());

        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testDownloadCheckpointOffsetAlignment() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-download-align");
        tmpDir.toFile().deleteOnExit();

        DownloadCheckpoint cp = DownloadCheckpoint.create(
                "bucket", "key", null, null,
                "/tmp/download.dat", tmpDir.toString(),
                4096, "mod", "etag", 1024);
        // Offset not aligned with partSize
        cp.info.data.downloadInfo.offset = 500;
        cp.dump();

        DownloadCheckpoint cp2 = DownloadCheckpoint.create(
                "bucket", "key", null, null,
                "/tmp/download.dat", tmpDir.toString(),
                4096, "mod", "etag", 1024);
        assertFalse(cp2.load());

        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testDownloadCheckpointWithRange() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-download-range");
        tmpDir.toFile().deleteOnExit();

        DownloadCheckpoint cp = DownloadCheckpoint.create(
                "bucket", "key", null, "bytes=1024-4095",
                "/tmp/download.dat", tmpDir.toString(),
                10000, "mod", "etag", 1024);
        cp.info.data.downloadInfo.offset = 2048; // 1024 (rangeStart) + 1024 (1 part)
        cp.dump();

        DownloadCheckpoint cp2 = DownloadCheckpoint.create(
                "bucket", "key", null, "bytes=1024-4095",
                "/tmp/download.dat", tmpDir.toString(),
                10000, "mod", "etag", 1024);
        assertTrue(cp2.load());
        assertTrue(cp2.loaded);
        assertEquals(2048, cp2.info.data.downloadInfo.offset);

        cp2.remove();
        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testDownloadCheckpointWithVersionId() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-download-version");
        tmpDir.toFile().deleteOnExit();

        DownloadCheckpoint cp = DownloadCheckpoint.create(
                "bucket", "key", "version-1", null,
                "/tmp/download.dat", tmpDir.toString(),
                2048, "mod", "etag", 1024);
        cp.info.data.downloadInfo.offset = 1024;
        cp.dump();

        // Same versionId should load
        DownloadCheckpoint cp2 = DownloadCheckpoint.create(
                "bucket", "key", "version-1", null,
                "/tmp/download.dat", tmpDir.toString(),
                2048, "mod", "etag", 1024);
        assertTrue(cp2.load());

        // Different versionId should generate different checkpoint file
        DownloadCheckpoint cp3 = DownloadCheckpoint.create(
                "bucket", "key", "version-2", null,
                "/tmp/download.dat", tmpDir.toString(),
                2048, "mod", "etag", 1024);
        assertFalse(cp3.load()); // different file path, no file

        cp2.remove();
        deleteDir(tmpDir.toFile());
    }

    @Test(expected = IOException.class)
    public void testDownloadCheckpointInvalidDir() throws Exception {
        DownloadCheckpoint cp = DownloadCheckpoint.create(
                "bucket", "key", null, null,
                "/tmp/download.dat", "/nonexistent/dir/that/does/not/exist",
                100, "mod", "etag", 1024);
        cp.load(); // should throw IOException
    }

    // endregion

    // region MD5 Utility

    @Test
    public void testMd5Hex() {
        // Known MD5 values
        String hash = UploadCheckpoint.md5Hex("");
        assertNotNull(hash);
        assertEquals(32, hash.length());

        // Same input should produce same hash
        String hash1 = UploadCheckpoint.md5Hex("test");
        String hash2 = UploadCheckpoint.md5Hex("test");
        assertEquals(hash1, hash2);

        // Different input should produce different hash
        String hash3 = UploadCheckpoint.md5Hex("different");
        assertNotEquals(hash1, hash3);
    }

    // endregion

    // region Integration: Uploader with Checkpoint

    static class CheckpointUploaderMockHttpClient implements HttpClient {
        final AtomicInteger initiateCount = new AtomicInteger(0);
        final AtomicInteger uploadPartCount = new AtomicInteger(0);
        final AtomicInteger completeCount = new AtomicInteger(0);
        final AtomicInteger listPartsCount = new AtomicInteger(0);

        Set<Integer> uploadPartErrors = new HashSet<>();
        boolean failOnFirstInitiate = false;
        int initiateCallThreshold = 0; // fail initiate after this many calls

        @Override
        public CompletableFuture<ResponseMessage> sendAsync(RequestMessage request, RequestContext context) {
            CompletableFuture<ResponseMessage> future = new CompletableFuture<>();
            future.complete(send(request, context));
            return future;
        }

        @Override
        public ResponseMessage send(RequestMessage request, RequestContext context) {
            String method = request.method();
            String query = request.uri().getQuery() != null ? request.uri().getQuery() : "";

            if ("PUT".equals(method) && query.contains("uploadId=") && query.contains("partNumber=")) {
                return handleUploadPart(request, query);
            } else if ("PUT".equals(method)) {
                return handlePutObject(request);
            } else if ("POST".equals(method) && query.contains("uploads")) {
                return handleInitiate(request);
            } else if ("POST".equals(method) && query.contains("uploadId=")) {
                return handleComplete(request);
            } else if ("GET".equals(method) && query.contains("uploadId=")) {
                return handleListParts(request, query);
            } else if ("DELETE".equals(method) && query.contains("uploadId=")) {
                return handleAbort(request);
            }
            return buildResponse(400, "<Error><Code>Unknown</Code></Error>", Collections.emptyMap());
        }

        private ResponseMessage handlePutObject(RequestMessage request) {
            readBody(request);
            Map<String, String> h = MapUtils.caseInsensitiveMap();
            h.put("ETag", "\"etag-single\"");
            h.put("x-oss-hash-crc64ecma", "123");
            return buildResponse(200, "", h);
        }

        private ResponseMessage handleInitiate(RequestMessage request) {
            int count = initiateCount.incrementAndGet();
            if (failOnFirstInitiate && count <= initiateCallThreshold) {
                return buildResponse(403, "<Error><Code>Err</Code><Message>Err</Message><RequestId>r</RequestId></Error>", Collections.emptyMap());
            }
            String xml = "<InitiateMultipartUploadResult>" +
                    "<Bucket>bucket</Bucket><Key>key</Key>" +
                    "<UploadId>upload-cp-" + count + "</UploadId>" +
                    "</InitiateMultipartUploadResult>";
            Map<String, String> h = MapUtils.caseInsensitiveMap();
            h.put("Content-Type", "application/xml");
            return buildResponse(200, xml, h);
        }

        private ResponseMessage handleUploadPart(RequestMessage request, String query) {
            int pn = extractPartNumber(query);
            uploadPartCount.incrementAndGet();
            if (uploadPartErrors.contains(pn)) {
                return buildResponse(403, "<Error><Code>Err</Code><Message>Err</Message><RequestId>r</RequestId></Error>", Collections.emptyMap());
            }
            readBody(request);
            Map<String, String> h = MapUtils.caseInsensitiveMap();
            h.put("ETag", "\"etag-part-" + pn + "\"");
            h.put("x-oss-hash-crc64ecma", String.valueOf(pn));
            return buildResponse(200, "", h);
        }

        private ResponseMessage handleComplete(RequestMessage request) {
            completeCount.incrementAndGet();
            String xml = "<CompleteMultipartUploadResult>" +
                    "<Bucket>bucket</Bucket><Key>key</Key>" +
                    "<ETag>\"etag-complete\"</ETag>" +
                    "</CompleteMultipartUploadResult>";
            Map<String, String> h = MapUtils.caseInsensitiveMap();
            h.put("x-oss-hash-crc64ecma", "999");
            return buildResponse(200, xml, h);
        }

        private ResponseMessage handleListParts(RequestMessage request, String query) {
            listPartsCount.incrementAndGet();
            // Return parts 1 and 2 as already uploaded
            String xml = "<ListPartsResult>" +
                    "<Bucket>bucket</Bucket><Key>key</Key>" +
                    "<UploadId>upload-cp-1</UploadId>" +
                    "<Part><PartNumber>1</PartNumber><ETag>\"etag-part-1\"</ETag><Size>100</Size></Part>" +
                    "<Part><PartNumber>2</PartNumber><ETag>\"etag-part-2\"</ETag><Size>100</Size></Part>" +
                    "</ListPartsResult>";
            Map<String, String> h = MapUtils.caseInsensitiveMap();
            h.put("Content-Type", "application/xml");
            return buildResponse(200, xml, h);
        }

        private ResponseMessage handleAbort(RequestMessage request) {
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

        private void readBody(RequestMessage request) {
            if (request.body() != null) {
                try { request.body().toBytes(); } catch (Exception ignored) {}
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

    @Test
    public void testUploaderWithCheckpointSuccess() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-uploader-test");
        tmpDir.toFile().deleteOnExit();

        CheckpointUploaderMockHttpClient mock = new CheckpointUploaderMockHttpClient();
        OSSClient client = OSSClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mock)
                .disableUploadCRC64Check(true)
                .build();

        UploaderOptions opts = UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .enableCheckpoint(true)
                .checkpointDir(tmpDir.toString())
                .build();
        assertTrue(opts.enableCheckpoint());
        assertEquals(tmpDir.toString(), opts.checkpointDir());

        Uploader uploader = new Uploader(client, opts);

        // Create test file
        File testFile = File.createTempFile("cp-upload-", ".dat", tmpDir.toFile());
        testFile.deleteOnExit();
        byte[] data = new byte[350];
        Arrays.fill(data, (byte) 'X');
        Files.write(testFile.toPath(), data);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        UploadResult result = uploader.uploadFile(request, testFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(1, mock.initiateCount.get());
        assertTrue(mock.uploadPartCount.get() > 0);
        assertEquals(1, mock.completeCount.get());

        // Checkpoint file should be removed after success
        File[] cpFiles = tmpDir.toFile().listFiles((dir, name) -> name.endsWith(Defaults.CHECKPOINT_FILE_SUFFIX_UPLOADER));
        assertTrue(cpFiles == null || cpFiles.length == 0);

        testFile.delete();
        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testUploaderCheckpointResume() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-uploader-resume");
        tmpDir.toFile().deleteOnExit();

        // Create test file
        File testFile = File.createTempFile("cp-upload-resume-", ".dat", tmpDir.toFile());
        testFile.deleteOnExit();
        byte[] data = new byte[400];
        Arrays.fill(data, (byte) 'Y');
        Files.write(testFile.toPath(), data);

        // First attempt: fail on part 3
        CheckpointUploaderMockHttpClient mock1 = new CheckpointUploaderMockHttpClient();
        mock1.uploadPartErrors.add(3);
        OSSClient client1 = OSSClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mock1)
                .disableUploadCRC64Check(true)
                .build();

        UploaderOptions opts = UploaderOptions.newBuilder()
                .partSize(100)
                .parallelNum(1)
                .enableCheckpoint(true)
                .checkpointDir(tmpDir.toString())
                .build();

        Uploader uploader1 = new Uploader(client1, opts);
        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        try {
            uploader1.uploadFile(request, testFile.getAbsolutePath());
            fail("Expected UploadError");
        } catch (UploadError e) {
            // Expected - part 3 failed
        }

        // Checkpoint file should exist (leavePartsOnError=true when checkpoint enabled)
        File[] cpFiles = tmpDir.toFile().listFiles((dir, name) -> name.endsWith(Defaults.CHECKPOINT_FILE_SUFFIX_UPLOADER));
        assertNotNull(cpFiles);
        assertEquals(1, cpFiles.length);

        // Second attempt: should resume (list parts, skip already uploaded)
        CheckpointUploaderMockHttpClient mock2 = new CheckpointUploaderMockHttpClient();
        OSSClient client2 = OSSClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mock2)
                .disableUploadCRC64Check(true)
                .build();

        Uploader uploader2 = new Uploader(client2, opts);
        UploadResult result = uploader2.uploadFile(request, testFile.getAbsolutePath());
        assertNotNull(result);

        // Should NOT have called initiate again (reused upload ID from checkpoint)
        assertEquals(0, mock2.initiateCount.get());
        // Should have called listParts to find already uploaded parts
        assertTrue(mock2.listPartsCount.get() > 0);
        assertEquals(1, mock2.completeCount.get());

        // Checkpoint file should be removed after success
        cpFiles = tmpDir.toFile().listFiles((dir, name) -> name.endsWith(Defaults.CHECKPOINT_FILE_SUFFIX_UPLOADER));
        assertTrue(cpFiles == null || cpFiles.length == 0);

        testFile.delete();
        deleteDir(tmpDir.toFile());
    }

    // endregion

    // region Integration: Downloader with Checkpoint

    static class CheckpointDownloaderMockHttpClient implements HttpClient {
        final AtomicInteger headCount = new AtomicInteger(0);
        final AtomicInteger getCount = new AtomicInteger(0);

        byte[] sourceData;
        boolean getObjectError = false;
        Set<Integer> failOnGetCall = new HashSet<>(); // fail specific GET calls

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
                return handleHead(request);
            } else if ("GET".equals(method)) {
                return handleGet(request);
            }
            return buildResponse(400, new byte[0], Collections.emptyMap());
        }

        private ResponseMessage handleHead(RequestMessage request) {
            headCount.incrementAndGet();
            Map<String, String> h = MapUtils.caseInsensitiveMap();
            h.put("Content-Length", String.valueOf(sourceData.length));
            h.put("ETag", "\"source-etag\"");
            h.put("Last-Modified", "Wed, 01 Jan 2025 00:00:00 GMT");
            return buildResponse(200, new byte[0], h);
        }

        private ResponseMessage handleGet(RequestMessage request) {
            int callNum = getCount.incrementAndGet();
            if (getObjectError || failOnGetCall.contains(callNum)) {
                return buildResponse(403, "<Error><Code>Err</Code><Message>Err</Message><RequestId>r</RequestId></Error>".getBytes(), Collections.emptyMap());
            }

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

            Map<String, String> h = MapUtils.caseInsensitiveMap();
            h.put("Content-Length", String.valueOf(sendData.length));
            h.put("ETag", "\"source-etag\"");
            return buildResponse(statusCode, sendData, h);
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

    @Test
    public void testDownloaderWithCheckpointSuccess() throws Exception {
        Path tmpDir = Files.createTempDirectory("cp-downloader-test");
        tmpDir.toFile().deleteOnExit();

        int length = 1024;
        byte[] data = new byte[length];
        new Random(42).nextBytes(data);

        CheckpointDownloaderMockHttpClient mock = new CheckpointDownloaderMockHttpClient();
        mock.sourceData = data;
        OSSClient client = OSSClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .httpClient(mock)
                .disableUploadCRC64Check(true)
                .build();

        DownloaderOptions opts = DownloaderOptions.newBuilder()
                .partSize(2 * 1024 * 1024) // larger than file
                .parallelNum(1)
                .useTempFile(false)
                .enableCheckpoint(true)
                .checkpointDir(tmpDir.toString())
                .build();
        assertTrue(opts.enableCheckpoint());

        Downloader downloader = new Downloader(client, opts);

        File outFile = File.createTempFile("cp-download-", ".dat", tmpDir.toFile());
        outFile.deleteOnExit();

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("bucket")
                .key("key")
                .build();

        DownloadResult result = downloader.downloadFile(request, outFile.getAbsolutePath());
        assertNotNull(result);
        assertEquals(length, result.written());

        // Verify content
        byte[] downloaded = Files.readAllBytes(outFile.toPath());
        assertArrayEquals(data, downloaded);

        // Checkpoint file should be removed after success
        File[] cpFiles = tmpDir.toFile().listFiles((dir, name) -> name.endsWith(Defaults.CHECKPOINT_FILE_SUFFIX_DOWNLOADER));
        assertTrue(cpFiles == null || cpFiles.length == 0);

        outFile.delete();
        deleteDir(tmpDir.toFile());
    }

    @Test
    public void testDownloaderCheckpointOptionsInOptions() {
        DownloaderOptions opts = DownloaderOptions.newBuilder()
                .enableCheckpoint(true)
                .checkpointDir("/tmp/cp")
                .build();
        assertTrue(opts.enableCheckpoint());
        assertEquals("/tmp/cp", opts.checkpointDir());

        DownloaderOptions defaults = DownloaderOptions.defaults();
        assertFalse(defaults.enableCheckpoint());
        assertNull(defaults.checkpointDir());
    }

    @Test
    public void testUploaderCheckpointOptionsInOptions() {
        UploaderOptions opts = UploaderOptions.newBuilder()
                .enableCheckpoint(true)
                .checkpointDir("/tmp/cp")
                .build();
        assertTrue(opts.enableCheckpoint());
        assertEquals("/tmp/cp", opts.checkpointDir());

        UploaderOptions defaults = UploaderOptions.defaults();
        assertFalse(defaults.enableCheckpoint());
        assertNull(defaults.checkpointDir());
    }

    // endregion

    private void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteDir(child);
                }
            }
        }
        dir.delete();
    }
}
