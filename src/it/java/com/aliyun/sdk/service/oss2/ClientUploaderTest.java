package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transfermanager.*;
import com.aliyun.sdk.service.oss2.utils.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClientUploaderTest extends TestBase {

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

    // region Single Part Upload

    @Test
    public void testUploaderSinglePartFromStream() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-single-stream";

        int length = 100 * 1024 + 123;
        byte[] data = randomData(length);

        Uploader uploader = new Uploader(client);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        UploadResult result = uploader.uploadFrom(request, BinaryData.fromBytes(data));
        Assert.assertNotNull(result);
        Assert.assertNull(result.uploadId());
        Assert.assertNotNull(result.hashCrc64ecma());

        // Verify via HeadObject
        HeadObjectResult headResult = client.headObject(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        Assert.assertEquals(200, headResult.statusCode());
        Assert.assertEquals(Long.valueOf(length), headResult.contentLength());

        // Verify content via GetObject
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);
    }

    @Test
    public void testUploaderSinglePartFromFile() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-single-file";

        int length = 100 * 1024 + 123;
        byte[] data = randomData(length);

        File tempFile = File.createTempFile("uploader-it-single-", ".txt");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), data);

        Uploader uploader = new Uploader(client);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        UploadResult result = uploader.uploadFile(request, tempFile.getAbsolutePath());
        Assert.assertNotNull(result);
        Assert.assertNull(result.uploadId());
        Assert.assertNotNull(result.hashCrc64ecma());

        // Verify size
        HeadObjectResult headResult = client.headObject(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        Assert.assertEquals(Long.valueOf(length), headResult.contentLength());

        tempFile.delete();
    }

    // endregion

    // region Multipart Upload from Stream

    @Test
    public void testUploaderMultipartSequentialFromStream() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-mp-seq";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(1)
                .build());

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        UploadResult result = uploader.uploadFrom(request, BinaryData.fromBytes(data));
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.uploadId());
        Assert.assertNotNull(result.hashCrc64ecma());

        // Verify size
        HeadObjectResult headResult = client.headObject(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        Assert.assertEquals(Long.valueOf(length), headResult.contentLength());

        // Verify content
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);
    }

    @Test
    public void testUploaderMultipartParallelFromStream() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-mp-par";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .build());

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        UploadResult result = uploader.uploadFrom(request, BinaryData.fromBytes(data));
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.uploadId());
        Assert.assertNotNull(result.hashCrc64ecma());

        // Verify content
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);
    }

    @Test
    public void testUploaderMultipartFromStreamUnknownSize() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-mp-stream";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .build());

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        // Use stream without known length
        InputStream stream = new ByteArrayInputStream(data);
        UploadResult result = uploader.uploadFrom(request, BinaryData.fromStream(stream));
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.uploadId());

        // Verify content
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);
    }

    // endregion

    // region Multipart Upload from File

    @Test
    public void testUploaderMultipartSequentialFromFile() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-mp-file-seq";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);

        File tempFile = File.createTempFile("uploader-it-mp-", ".dat");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(1)
                .build());

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        UploadResult result = uploader.uploadFile(request, tempFile.getAbsolutePath());
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.uploadId());
        Assert.assertNotNull(result.hashCrc64ecma());

        // Verify content
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);

        tempFile.delete();
    }

    @Test
    public void testUploaderMultipartParallelFromFile() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-mp-file-par";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);

        File tempFile = File.createTempFile("uploader-it-mp-par-", ".dat");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(4)
                .build());

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        UploadResult result = uploader.uploadFile(request, tempFile.getAbsolutePath());
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.uploadId());

        // Verify content
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);

        tempFile.delete();
    }

    // endregion

    // region Empty Body

    @Test
    public void testUploaderEmptyBody() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-empty";

        Uploader uploader = new Uploader(client);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        UploadResult result = uploader.uploadFrom(request, BinaryData.fromBytes(new byte[0]));
        Assert.assertNotNull(result);
        Assert.assertNull(result.uploadId());

        HeadObjectResult headResult = client.headObject(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        Assert.assertEquals(Long.valueOf(0), headResult.contentLength());
    }

    // endregion

    // region Progress

    @Test
    public void testUploaderMultipartWithProgress() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-progress";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);

        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicLong finishCount = new AtomicLong(0);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .progressListener(new ProgressListener() {
                    @Override
                    public void onProgress(long increment, long transferred, long total) {
                        totalIncrement.addAndGet(increment);
                        lastTransferred.set(transferred);
                    }

                    @Override
                    public void onFinish() {
                        finishCount.incrementAndGet();
                    }
                })
                .build();

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .build());

        UploadResult result = uploader.uploadFrom(request, BinaryData.fromBytes(data));
        Assert.assertNotNull(result);
        Assert.assertEquals(length, totalIncrement.get());
        Assert.assertEquals(length, lastTransferred.get());
        Assert.assertEquals(1, finishCount.get());
    }

    @Test
    public void testUploaderFileWithProgress() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-file-progress";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);

        File tempFile = File.createTempFile("uploader-it-progress-", ".dat");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), data);

        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicLong finishCount = new AtomicLong(0);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
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

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .build());

        UploadResult result = uploader.uploadFile(request, tempFile.getAbsolutePath());
        Assert.assertNotNull(result);
        Assert.assertEquals(length, totalIncrement.get());
        Assert.assertEquals(1, finishCount.get());

        tempFile.delete();
    }

    // endregion

    // region Upload + Download roundtrip

    @Test
    public void testUploaderAndDownloadRoundtrip() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-roundtrip";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);

        // Upload via Uploader
        File uploadFile = File.createTempFile("uploader-it-rt-upload-", ".dat");
        uploadFile.deleteOnExit();
        Files.write(uploadFile.toPath(), data);

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .build());

        UploadResult uploadResult = uploader.uploadFile(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                uploadFile.getAbsolutePath());
        Assert.assertNotNull(uploadResult);

        // Download via Downloader
        File downloadFile = File.createTempFile("uploader-it-rt-download-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete(); // let Downloader create it

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .build());

        DownloadResult downloadResult = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build(),
                downloadFile.getAbsolutePath());
        Assert.assertEquals(length, downloadResult.written());

        // Compare files
        byte[] downloaded = Files.readAllBytes(downloadFile.toPath());
        Assert.assertArrayEquals(data, downloaded);

        uploadFile.delete();
        downloadFile.delete();
    }

    // endregion

    // region Checkpoint Upload from File

    @Test
    public void testUploaderFileWithCheckpoint() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-checkpoint";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);

        File tempFile = File.createTempFile("uploader-it-cp-", ".dat");
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), data);

        File cpDir = Files.createTempDirectory("uploader-it-cpdir-").toFile();
        cpDir.deleteOnExit();

        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(3)
                .enableCheckpoint(true)
                .checkpointDir(cpDir.getAbsolutePath())
                .build());

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        UploadResult result = uploader.uploadFile(request, tempFile.getAbsolutePath());
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.uploadId());

        // Verify content
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);

        // Checkpoint file should be cleaned up after success
        String[] cpFiles = cpDir.list();
        Assert.assertNotNull(cpFiles);
        Assert.assertEquals(0, cpFiles.length);

        tempFile.delete();
        cpDir.delete();
    }

    // endregion

    // region Override Options

    @Test
    public void testUploaderWithOverrideOptions() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-uploader-override";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);

        // Create with default options (large partSize -> single part)
        Uploader uploader = new Uploader(client);

        PutObjectRequest request = PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        // Override with small partSize to force multipart
        UploaderOptions overrideOpts = UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(2)
                .build();

        UploadResult result = uploader.uploadFrom(request, BinaryData.fromBytes(data), overrideOpts);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.uploadId()); // multipart was used

        // Verify content
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);
    }

    // endregion
}
