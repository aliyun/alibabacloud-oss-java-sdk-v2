package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transfermanager.*;
import com.aliyun.sdk.service.oss2.utils.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ClientCopierTest extends TestBase {

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

    private void putObject(OSSClient client, String bucket, String key, byte[] data) {
        PutObjectResult result = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucket)
                .key(key)
                .body(BinaryData.fromBytes(data))
                .build());
        Assert.assertEquals(200, result.statusCode());
    }

    private void putObjectWithSSE(OSSClient client, String bucket, String key, byte[] data) {
        PutObjectResult result = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucket)
                .key(key)
                .serverSideEncryption("AES256")
                .body(BinaryData.fromBytes(data))
                .build());
        Assert.assertEquals(200, result.statusCode());
    }

    // region Single Copy

    @Test
    public void testCopierSingleCopy() throws Exception {
        OSSClient client = getDefaultClient();
        String srcKey = genObjectName() + "-cp-src";
        String dstKey = genObjectName() + "-cp-dst-single";

        int length = 100 * 1024 + 123;
        byte[] data = randomData(length);
        putObject(client, bucketName, srcKey, data);

        Copier copier = new Copier(client);

        CopyResult result = copier.copy(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .sourceKey(srcKey)
                .build());

        Assert.assertNotNull(result);
        Assert.assertNull(result.uploadId());

        // Verify content
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);
    }

    // endregion

    // region Multipart Copy

    @Test
    public void testCopierMultipartSequential() throws Exception {
        OSSClient client = getDefaultClient();
        String srcKey = genObjectName() + "-cp-src-mp-seq";
        String dstKey = genObjectName() + "-cp-dst-mp-seq";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, bucketName, srcKey, data);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .partSize(partSize)
                .multipartCopyThreshold(partSize)
                .parallelNum(1)
                .disableShallowCopy(true)
                .build());

        CopyResult result = copier.copy(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .sourceKey(srcKey)
                .build());

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.uploadId());

        // Verify content
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);
    }

    @Test
    public void testCopierMultipartParallel() throws Exception {
        OSSClient client = getDefaultClient();
        String srcKey = genObjectName() + "-cp-src-mp-par";
        String dstKey = genObjectName() + "-cp-dst-mp-par";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, bucketName, srcKey, data);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .partSize(partSize)
                .multipartCopyThreshold(partSize)
                .parallelNum(3)
                .disableShallowCopy(true)
                .build());

        CopyResult result = copier.copy(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .sourceKey(srcKey)
                .build());

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.uploadId());

        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);
    }

    // endregion

    // region Shallow Copy

    @Test
    public void testCopierShallowCopy() throws Exception {
        OSSClient client = getDefaultClient();
        String srcKey = genObjectName() + "-cp-src-shallow";
        String dstKey = genObjectName() + "-cp-dst-shallow";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, bucketName, srcKey, data);

        // Same bucket, no SSE, no storage class change -> shallow copy
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .partSize(partSize)
                .multipartCopyThreshold(partSize)
                .parallelNum(1)
                .build());

        CopyResult result = copier.copy(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .sourceKey(srcKey)
                .build());

        Assert.assertNotNull(result);

        // Shallow copy uses CopyObject -> Normal object type
        HeadObjectResult headResult = client.headObject(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .build());
        Assert.assertEquals("Normal", headResult.objectType());

        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);
    }

    // endregion

    // region NoCheckSSE Flag

    @Test
    public void testCopierNoCheckSSE() throws Exception {
        OSSClient client = getDefaultClient();
        String srcKey = genObjectName() + "-cp-sse-src";
        String dstKey = genObjectName() + "-cp-sse-dst";
        String dstKeyNoCheck = genObjectName() + "-cp-sse-dst-nocheck";
        String dstKeyCopierLevel = genObjectName() + "-cp-sse-dst-copier";

        int partSize = 100 * 1024;
        int length = 2 * partSize + 1234;
        byte[] data = randomData(length);
        putObjectWithSSE(client, bucketName, srcKey, data);

        // Default: SSE detected -> multipart copy
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .partSize(partSize)
                .multipartCopyThreshold(partSize)
                .parallelNum(1)
                .build());

        CopyResult result = copier.copy(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .sourceKey(srcKey)
                .build());

        Assert.assertNotNull(result);
        HeadObjectResult headResult = client.headObject(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .build());
        Assert.assertEquals("Multipart", headResult.objectType());

        // Override with noCheckSSE -> shallow copy (Normal)
        CopyResult result2 = copier.copy(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKeyNoCheck)
                .sourceKey(srcKey)
                .build(), CopierOptions.newBuilder()
                .partSize(partSize)
                .multipartCopyThreshold(partSize)
                .parallelNum(1)
                .noCheckSSE(true)
                .build());

        Assert.assertNotNull(result2);
        HeadObjectResult headResult2 = client.headObject(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKeyNoCheck)
                .build());
        Assert.assertEquals("Normal", headResult2.objectType());

        // Copier-level noCheckSSE
        Copier copier2 = new Copier(client, CopierOptions.newBuilder()
                .partSize(partSize)
                .multipartCopyThreshold(partSize)
                .parallelNum(1)
                .noCheckSSE(true)
                .build());

        CopyResult result3 = copier2.copy(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKeyCopierLevel)
                .sourceKey(srcKey)
                .build());

        Assert.assertNotNull(result3);
        HeadObjectResult headResult3 = client.headObject(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKeyCopierLevel)
                .build());
        Assert.assertEquals("Normal", headResult3.objectType());
    }

    // endregion

    // region NoCheckCrossBucket Flag

    @Test
    public void testCopierNoCheckCrossBucket() throws Exception {
        OSSClient client = getDefaultClient();
        String crossBucket = genBucketName();
        createBucket(crossBucket);

        try {
            String srcKey = genObjectName() + "-cp-cross-src";
            String dstKey = genObjectName() + "-cp-cross-dst";
            String dstKeyNoCheck = genObjectName() + "-cp-cross-dst-nocheck";
            String dstKeyCopierLevel = genObjectName() + "-cp-cross-dst-copier";

            int partSize = 100 * 1024;
            int length = 2 * partSize + 1234;
            byte[] data = randomData(length);
            putObject(client, bucketName, srcKey, data);

            // Default: cross-bucket -> multipart copy
            Copier copier = new Copier(client, CopierOptions.newBuilder()
                    .partSize(partSize)
                    .multipartCopyThreshold(partSize)
                    .parallelNum(1)
                    .build());

            CopyResult result = copier.copy(CopyObjectRequest.newBuilder()
                    .bucket(crossBucket)
                    .key(dstKey)
                    .sourceBucket(bucketName)
                    .sourceKey(srcKey)
                    .build());

            Assert.assertNotNull(result);
            HeadObjectResult headResult = client.headObject(HeadObjectRequest.newBuilder()
                    .bucket(crossBucket)
                    .key(dstKey)
                    .build());
            Assert.assertEquals("Multipart", headResult.objectType());

            // Override with noCheckCrossBucket -> shallow copy (Normal)
            CopyResult result2 = copier.copy(CopyObjectRequest.newBuilder()
                    .bucket(crossBucket)
                    .key(dstKeyNoCheck)
                    .sourceBucket(bucketName)
                    .sourceKey(srcKey)
                    .build(), CopierOptions.newBuilder()
                    .partSize(partSize)
                    .multipartCopyThreshold(partSize)
                    .parallelNum(1)
                    .noCheckCrossBucket(true)
                    .build());

            Assert.assertNotNull(result2);
            HeadObjectResult headResult2 = client.headObject(HeadObjectRequest.newBuilder()
                    .bucket(crossBucket)
                    .key(dstKeyNoCheck)
                    .build());
            Assert.assertEquals("Normal", headResult2.objectType());

            // Copier-level noCheckCrossBucket
            Copier copier2 = new Copier(client, CopierOptions.newBuilder()
                    .partSize(partSize)
                    .multipartCopyThreshold(partSize)
                    .parallelNum(1)
                    .noCheckCrossBucket(true)
                    .build());

            CopyResult result3 = copier2.copy(CopyObjectRequest.newBuilder()
                    .bucket(crossBucket)
                    .key(dstKeyCopierLevel)
                    .sourceBucket(bucketName)
                    .sourceKey(srcKey)
                    .build());

            Assert.assertNotNull(result3);
            HeadObjectResult headResult3 = client.headObject(HeadObjectRequest.newBuilder()
                    .bucket(crossBucket)
                    .key(dstKeyCopierLevel)
                    .build());
            Assert.assertEquals("Normal", headResult3.objectType());

            // Verify content
            GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                    .bucket(crossBucket)
                    .key(dstKeyCopierLevel)
                    .build());
            byte[] downloaded = IOUtils.toByteArray(getResult.body());
            Assert.assertArrayEquals(data, downloaded);
        } finally {
            cleanBucket(crossBucket, region());
        }
    }

    // endregion

    // region Progress

    @Test
    public void testCopierSingleCopyWithProgress() throws Exception {
        OSSClient client = getDefaultClient();
        String srcKey = genObjectName() + "-cp-prog-src";
        String dstKey = genObjectName() + "-cp-prog-dst";

        int length = 100 * 1024 + 123;
        byte[] data = randomData(length);
        putObject(client, bucketName, srcKey, data);

        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicLong finishCount = new AtomicLong(0);

        Copier copier = new Copier(client);

        CopyResult result = copier.copy(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .sourceKey(srcKey)
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
                .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(length, totalIncrement.get());
        Assert.assertEquals(length, lastTransferred.get());
        Assert.assertEquals(1, finishCount.get());
    }

    @Test
    public void testCopierMultipartCopyWithProgress() throws Exception {
        OSSClient client = getDefaultClient();
        String srcKey = genObjectName() + "-cp-mp-prog-src";
        String dstKey = genObjectName() + "-cp-mp-prog-dst";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, bucketName, srcKey, data);

        AtomicLong totalIncrement = new AtomicLong(0);
        AtomicLong lastTransferred = new AtomicLong(0);
        AtomicLong finishCount = new AtomicLong(0);

        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .partSize(partSize)
                .multipartCopyThreshold(partSize)
                .parallelNum(3)
                .disableShallowCopy(true)
                .build());

        CopyResult result = copier.copy(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .sourceKey(srcKey)
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
                .build());

        Assert.assertNotNull(result);
        Assert.assertEquals(length, totalIncrement.get());
        Assert.assertEquals(1, finishCount.get());
    }

    // endregion

    // region Override Options

    @Test
    public void testCopierWithOverrideOptions() throws Exception {
        OSSClient client = getDefaultClient();
        String srcKey = genObjectName() + "-cp-override-src";
        String dstKey = genObjectName() + "-cp-override-dst";

        int partSize = 100 * 1024;
        int length = 4 * partSize + 123;
        byte[] data = randomData(length);
        putObject(client, bucketName, srcKey, data);

        // Default copier (large threshold -> single copy)
        Copier copier = new Copier(client);

        // Override with small threshold and partSize to force multipart
        CopierOptions overrideOpts = CopierOptions.newBuilder()
                .partSize(partSize)
                .multipartCopyThreshold(partSize)
                .parallelNum(2)
                .disableShallowCopy(true)
                .build();

        CopyResult result = copier.copy(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .sourceKey(srcKey)
                .build(), overrideOpts);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.uploadId());

        // Verify content
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .build());
        byte[] downloaded = IOUtils.toByteArray(getResult.body());
        Assert.assertArrayEquals(data, downloaded);
    }

    // endregion

    // region Upload + Copy + Download Roundtrip

    @Test
    public void testUploadCopyDownloadRoundtrip() throws Exception {
        OSSClient client = getDefaultClient();
        String srcKey = genObjectName() + "-roundtrip-src";
        String dstKey = genObjectName() + "-roundtrip-dst";

        int partSize = 100 * 1024;
        int length = 10 * partSize + 12345;
        byte[] data = randomData(length);

        // Upload via Uploader
        Uploader uploader = new Uploader(client, UploaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(4)
                .build());

        UploadResult uploadResult = uploader.uploadFrom(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(srcKey)
                        .build(),
                BinaryData.fromBytes(data));
        Assert.assertNotNull(uploadResult);

        // Copy via Copier (multipart)
        Copier copier = new Copier(client, CopierOptions.newBuilder()
                .partSize(partSize)
                .multipartCopyThreshold(partSize)
                .parallelNum(4)
                .disableShallowCopy(true)
                .build());

        CopyResult copyResult = copier.copy(CopyObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(dstKey)
                .sourceKey(srcKey)
                .build());
        Assert.assertNotNull(copyResult);
        Assert.assertNotNull(copyResult.uploadId());

        // Download via Downloader
        java.io.File downloadFile = java.io.File.createTempFile("copier-it-roundtrip-", ".dat");
        downloadFile.deleteOnExit();
        downloadFile.delete();

        Downloader downloader = new Downloader(client, DownloaderOptions.newBuilder()
                .partSize(partSize)
                .parallelNum(4)
                .build());

        DownloadResult downloadResult = downloader.downloadFile(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(dstKey)
                        .build(),
                downloadFile.getAbsolutePath());

        Assert.assertEquals(length, downloadResult.written());
        byte[] downloaded = java.nio.file.Files.readAllBytes(downloadFile.toPath());
        Assert.assertArrayEquals(data, downloaded);

        downloadFile.delete();
    }

    // endregion
}
