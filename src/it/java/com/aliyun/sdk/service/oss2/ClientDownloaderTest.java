package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transfermanager.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClientDownloaderTest extends TestBase {

    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }

    private static byte[] randomData(int length) {
        byte[] data = new byte[length];
        new Random().nextBytes(data);
        return data;
    }

    private void putObject(OSSClient client, String key, byte[] data) {
        PutObjectResult result = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(key)
                .body(BinaryData.fromBytes(data))
                .build());
        Assert.assertEquals(200, result.statusCode());
    }

    // region Single Part Download

    @Test
    public void testDownloaderSinglePart() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-dl-single";

        int length = 100 * 1024 + 123;
        byte[] data = randomData(length);
        putObject(client, objectName, data);

        File downloadFile = File.createTempFile("downloader-it-single-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        Downloader downloader = new Downloader(client);

        DownloadResult result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(length, result.written());
        byte[] downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertArrayEquals(data, downloaded);

        downloadFile.delete();
    }

    // endregion

    // region Multipart Download

    @Test
    public void testDownloaderMultipartSequential() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-dl-mp-seq";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, objectName, data);

        File downloadFile = File.createTempFile("downloader-it-mp-seq-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(1)
                .build());

        DownloadResult result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(length, result.written());
        byte[] downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertArrayEquals(data, downloaded);

        downloadFile.delete();
    }

    @Test
    public void testDownloaderMultipartParallel() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-dl-mp-par";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, objectName, data);

        File downloadFile = File.createTempFile("downloader-it-mp-par-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .build());

        DownloadResult result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(length, result.written());
        byte[] downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertArrayEquals(data, downloaded);

        downloadFile.delete();
    }

    // endregion

    // region Range Download

    @Test
    public void testDownloaderWithRange() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-dl-range";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, objectName, data);

        File downloadFile = File.createTempFile("downloader-it-range-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .useTempFile(false)
                .build());

        // Full download first
        DownloadResult result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(length, result.written());
        byte[] downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertArrayEquals(data, downloaded);

        // Range download: bytes=123-123456
        int rangeStart = 123;
        int rangeEnd = Math.min(123456, length - 1);
        int expectedLen = rangeEnd - rangeStart + 1;

        result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .range("bytes=" + rangeStart + "-" + rangeEnd)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(expectedLen, result.written());
        downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertEquals(expectedLen, downloaded.length);

        // Verify content matches the range of original data
        byte[] expected = new byte[expectedLen];
        System.arraycopy(data, rangeStart, expected, 0, expectedLen);
        Assert.assertArrayEquals(expected, downloaded);

        downloadFile.delete();
    }

    // endregion

    // region Empty Object

    @Test
    public void testDownloaderEmptyObject() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-dl-empty";

        putObject(client, objectName, new byte[0]);

        File downloadFile = File.createTempFile("downloader-it-empty-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        Downloader downloader = new Downloader(client);

        DownloadResult result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(0, result.written());
        Assert.assertTrue(downloadFile.exists());
        Assert.assertEquals(0, downloadFile.length());

        downloadFile.delete();
    }

    // endregion

    // region Temp File

    @Test
    public void testDownloaderWithTempFile() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-dl-tempfile";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, objectName, data);

        File downloadFile = File.createTempFile("downloader-it-temp-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        String tempPath = downloadFile.getAbsolutePath() + ".temp";

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .useTempFile(true)
                .build());

        DownloadResult result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(length, result.written());
        // Temp file should be renamed to final file
        Assert.assertTrue(downloadFile.exists());
        Assert.assertFalse(new File(tempPath).exists());

        byte[] downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertArrayEquals(data, downloaded);

        downloadFile.delete();
    }

    @Test
    public void testDownloaderWithoutTempFile() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-dl-no-tempfile";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, objectName, data);

        File downloadFile = File.createTempFile("downloader-it-notemp-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .useTempFile(false)
                .build());

        DownloadResult result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(length, result.written());
        byte[] downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertArrayEquals(data, downloaded);

        downloadFile.delete();
    }

    // endregion

    // region Progress

    @Test
    public void testDownloaderWithProgress() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-dl-progress";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, objectName, data);

        File downloadFile = File.createTempFile("downloader-it-progress-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicLong finishCount = new AtomicLong(0);

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .build());

        DownloadResult result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .progressListener(new ProgressListener() {
                            @Override
                            public void onProgress(long increment, long transferred, long total) {
                                totalIncrement.addAndGet(increment);
                                lastTransferred.set(transferred);
                                Assert.assertEquals(length, total);
                            }

                            @Override
                            public void onFinish() {
                                finishCount.incrementAndGet();
                            }
                        })
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(length, result.written());
        Assert.assertEquals(length, totalIncrement.get());
        Assert.assertEquals(length, lastTransferred.get());
        Assert.assertEquals(1, finishCount.get());

        downloadFile.delete();
    }

    // endregion

    // region Checkpoint

    @Test
    public void testDownloaderWithCheckpoint() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-dl-checkpoint";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, objectName, data);

        File downloadFile = File.createTempFile("downloader-it-cp-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        File cpDir = Files.createTempDirectory("downloader-it-cpdir-").toFile();
        cpDir.deleteOnExit();

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .enableCheckpoint(true)
                .checkpointDir(cpDir.getAbsolutePath())
                .build());

        DownloadResult result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(length, result.written());
        byte[] downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertArrayEquals(data, downloaded);

        // Checkpoint file should be cleaned up after success
        String[] cpFiles = cpDir.list();
        Assert.assertNotNull(cpFiles);
        Assert.assertEquals(0, cpFiles.length);

        downloadFile.delete();
        cpDir.delete();
    }

    // endregion

    // region Override Options

    @Test
    public void testDownloaderWithOverrideOptions() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-dl-override";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, objectName, data);

        File downloadFile = File.createTempFile("downloader-it-override-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        // Create with default (large partSize -> single chunk)
        Downloader downloader = new Downloader(client);

        // Override with small partSize
        DownloaderOptions overrideOpts = DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(2)
                .build();

        DownloadResult result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath(),
                overrideOpts);

        Assert.assertEquals(length, result.written());
        byte[] downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertArrayEquals(data, downloaded);

        downloadFile.delete();
    }

    // endregion

    // region Truncate

    @Test
    public void testDownloaderTruncateExistingFile() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-dl-truncate";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, objectName, data);

        File downloadFile = File.createTempFile("downloader-it-truncate-", ".dat");
        downloadFile.deleteOnExit();

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .useTempFile(false)
                .build());

        // First download: full object
        DownloadResult result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath());
        Assert.assertEquals(length, result.written());
        Assert.assertEquals(length, downloadFile.length());

        // Second download: range download (smaller), file should be truncated
        int rangeStart = 123;
        int rangeEnd = 1234;
        int rangeLen = rangeEnd - rangeStart + 1;
        result = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .range("bytes=" + rangeStart + "-" + rangeEnd)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(rangeLen, result.written());
        byte[] downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertEquals(rangeLen, downloaded.length);

        byte[] expected = new byte[rangeLen];
        System.arraycopy(data, rangeStart, expected, 0, rangeLen);
        Assert.assertArrayEquals(expected, downloaded);

        downloadFile.delete();
    }

    // endregion

    // region Upload then Download Roundtrip (large file)

    @Test
    public void testUploaderDownloaderLargeFile() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-large-roundtrip";

        int partSize = 100 * 1024;
        int length = 10 * partSize + 12345;
        byte[] data = randomData(length);

        // Upload via multipart
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(4)
                .build());

        UploadResult uploadResult = uploader.uploadFrom(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                BinaryData.fromBytes(data));
        Assert.assertNotNull(uploadResult);
        Assert.assertNotNull(uploadResult.uploadId());

        // Download via multipart
        File downloadFile = File.createTempFile("downloader-it-large-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(4)
                .build());

        DownloadResult downloadResult = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(length, downloadResult.written());
        byte[] downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertArrayEquals(data, downloaded);

        downloadFile.delete();
    }

    // endregion
}
