package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.hash.CRC64;
import com.aliyun.sdk.service.oss2.internal.TestUtils;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ClientMiscAsyncTest extends TestBase {

     @Test
    public void testInvokeOperation() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        String objectName = "invoke-operation-test.json";
        // put object
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutObject")
                .method("PUT");
        builder.body(BinaryData.fromString("hello world"));
        builder.bucket(bucketName);
        builder.key(objectName);

        // headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        headers.put("x-oss-meta-123", "value3");
        headers.put("x-oss-meta-ABC", "value7");
        headers.put("x-oss-meta-aaa", "value2");
        headers.put("x-oss-meta-abc-123", "value5");
        headers.put("x-oss-meta-abc123", "value4");
        headers.put("x-oss-meta-zzz", "value1");

        builder.headers(headers);

        OperationInput input = builder.build();

        client.invokeOperationAsync(input, OperationOptions.defaults()).get();

        // get object
        GetObjectResult getResult = client.getObjectAsync(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build()).get();
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
    }

    @Test
    public void testDoesBucketExist() throws Exception {

        OSSAsyncClient client = getDefaultAsyncClient();
        String bucketNameNoExist = bucketName + "-not-exist";

        assertTrue(client.doesBucketExistAsync(bucketName).get());
        assertFalse(client.doesBucketExistAsync(bucketNameNoExist).get());

        //noPermClient
        try (OSSAsyncClient noPermClient = OSSAsyncClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .build()) {
            assertTrue(noPermClient.doesBucketExistAsync(bucketName).get());
            assertFalse(noPermClient.doesBucketExistAsync(bucketNameNoExist).get());
        }

        //errorClient
        try (OSSAsyncClient errorClient = OSSAsyncClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(new StaticCredentialsProvider("", ""))
                .build()) {
            assertTrue(errorClient.doesBucketExistAsync(bucketName).get());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Credentials is null or empty.");
        }
    }

    @Test
    public void testDoesObjectExist() throws Exception {

        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = "objectName-exist";
        String objectNameNoExist = "objectName-no-exist";

        String bucketNameNoExist = bucketName + "-not-exist";

        client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build()).get();

        assertTrue(client.doesObjectExistAsync(bucketName, objectName).get());
        assertFalse(client.doesObjectExistAsync(bucketName, objectNameNoExist).get());
        assertTrue(client.doesObjectExistLegacyAsync(bucketName, objectName).get());
        assertFalse(client.doesObjectExistLegacyAsync(bucketName, objectNameNoExist).get());


        try {
            assertFalse(client.doesObjectExistAsync(bucketNameNoExist, objectName).get());
            fail("should not here");
        } catch (Exception e) {
            ServiceException err = ServiceException.asCause(e);
            assertThat(err.errorCode()).isEqualTo("NoSuchBucket");
        }
        assertFalse(client.doesObjectExistLegacyAsync(bucketNameNoExist, objectName).get());


        try {
            assertFalse(client.doesObjectExistAsync(bucketNameNoExist, objectNameNoExist).get());
            fail("should not here");
        } catch (Exception e) {
            ServiceException err = ServiceException.asCause(e);
            assertThat(err.errorCode()).isEqualTo("NoSuchBucket");
        }
        assertFalse(client.doesObjectExistLegacyAsync(bucketNameNoExist, objectNameNoExist).get());

        //noPermClient
        try (OSSAsyncClient noPermClient = OSSAsyncClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .build()) {

            try {
                assertTrue(noPermClient.doesObjectExistAsync(bucketName, objectName).get());
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("InvalidAccessKeyId");
            }

            try {
                assertTrue(noPermClient.doesObjectExistLegacyAsync(bucketName, objectName).get());
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("InvalidAccessKeyId");
            }

            try {
                assertTrue(noPermClient.doesObjectExistAsync(bucketName, objectNameNoExist).get());
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("InvalidAccessKeyId");
            }

            try {
                assertTrue(noPermClient.doesObjectExistLegacyAsync(bucketName, objectNameNoExist).get());
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("InvalidAccessKeyId");
            }

            try {
                assertTrue(noPermClient.doesObjectExistAsync(bucketNameNoExist, objectName).get());
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("NoSuchBucket");
            }
            assertFalse(noPermClient.doesObjectExistLegacyAsync(bucketNameNoExist, objectName).get());

            try {
                assertTrue(noPermClient.doesObjectExistAsync(bucketNameNoExist, objectNameNoExist).get());
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("NoSuchBucket");
            }
            assertFalse(noPermClient.doesObjectExistLegacyAsync(bucketNameNoExist, objectNameNoExist).get());
        }

        //errorClient
        try (OSSAsyncClient errorClient = OSSAsyncClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(new StaticCredentialsProvider("", ""))
                .build()) {
            try {
                assertTrue(errorClient.doesObjectExistAsync(bucketName, objectName).get());
                fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("Credentials is null or empty.");
            }

            try {
                assertTrue(errorClient.doesObjectExistLegacyAsync(bucketName, objectName).get());
                fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("Credentials is null or empty.");
            }

            try {
                assertTrue(errorClient.doesObjectExistAsync(bucketNameNoExist, objectName).get());
                fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("Credentials is null or empty.");
            }

            try {
                assertTrue(errorClient.doesObjectExistLegacyAsync(bucketNameNoExist, objectName).get());
                fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("Credentials is null or empty.");
            }
        }
    }

    @Test
    public void testPutObjectWithByteChannel() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = "objectName-from-file.bin";

        Path path = Files.createTempFile("small-file", ".bin");
        Files.deleteIfExists(path);
        String content = "abcdefghijklmnopqrstuvwxyz0123456789011234567890";
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        Files.write(path, contentBytes);

        File file = createSampleFile("byte-channel.bin", 512*1024+ 123456);
        String fileMd5 = calculateMD5(file);
        assertThat(fileMd5).isNotNull();
        assertThat(fileMd5).isNotEmpty();
    }

    @Test
    public void testPutObjectFromFile() throws Exception {
        class MockProgressListener implements ProgressListener {
            public long total;
            public long incTotal;
            public long transferred;
            public boolean finished;
            public long allTotal;

            public MockProgressListener() {
                this.total = 0;
                this.incTotal = 0;
                this.transferred = 0;
                this.finished = false;
            }

            @Override
            public void onProgress(long increment, long transferred, long total) {
                this.incTotal += increment;
                this.total = total;
                this.transferred = transferred;

                int rate;
                if (total > 0) {
                    rate = (int) (100.0 * ((double) incTotal / (double) allTotal));
                } else {
                    rate = 0;
                }
                //System.out.println("\r" + rate + "% , increment:" + increment);
            }

            @Override
            public void onFinish() {
                this.finished = true;
            }
        }

        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = "objectName-from-file.bin";

        File file = createSampleFile("from-file.bin", 512*1024+ 123456);
        String fileMd5 = calculateMD5(file);
        assertThat(fileMd5).isNotNull();
        assertThat(fileMd5).isNotEmpty();

        MockProgressListener progListener = new MockProgressListener();
        progListener.allTotal = file.length();

        PutObjectResult putResult = client.putObjectFromFileAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .progressListener(progListener)
                .build(), file.toPath()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // get object
        String objectMd5;
        try (GetObjectResult getResult = client.getObjectAsync(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build()).get()) {
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            objectMd5 = calculateMD5(getResult.body());
        }
        assertThat(objectMd5).isEqualTo(fileMd5);
        Files.delete(file.toPath());

        // upload from invalid file path
        File noFile = new File(file.toString() + ".dst");
        assertThat(noFile).doesNotExist();

        try {
            client.putObjectFromFileAsync(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build(), noFile.toPath()).get();
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("NoSuchFileException");
        }
    }

    @Test
    public void testUploadData_DifferentBinaryData() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = "UploadData_DifferentBinaryData.bin";

        String contentStr = "hello world";
        byte[] contentByte = contentStr.getBytes();
        InputStream contentIs;
        ByteBuffer contentBB = ByteBuffer.wrap(contentByte);
        Path filePath = Files.createTempFile("test-data", ".bin");
        Files.write(filePath, contentByte);
        Files.deleteIfExists(filePath);
        CRC64 hash = new CRC64(contentByte, contentByte.length);
        String contentCrc = Long.toUnsignedString(hash.getValue());
        String lastRequestId = "first";

        // upload data from bytes
        PutObjectResult result = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName + "byte")
                .body(BinaryData.fromBytes(contentByte))
                .build()).get();

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
        lastRequestId = result.requestId();

        // upload data from byte array
        result = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName + "array")
                .body(BinaryData.fromByteBuffer(contentBB))
                .build()).get();

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
        lastRequestId = result.requestId();

        // upload data from string
        result = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName + "string")
                .body(BinaryData.fromString(contentStr))
                .build()).get();

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
        lastRequestId = result.requestId();

        // upload data from input stream
        contentIs = new ByteArrayInputStream(contentByte);
        result = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName + "inputStream")
                .body(BinaryData.fromStream(contentIs))
                .build()).get();

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
        lastRequestId = result.requestId();

        // upload data from input stream + length
        contentIs = new ByteArrayInputStream(contentByte);
        result = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName + "inputStream-length")
                .body(BinaryData.fromStream(contentIs, 2L))
                .build()).get();

        CRC64 cutHash = new CRC64(contentByte, 2);
        String cutCrc = Long.toUnsignedString(cutHash.getValue());
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(cutCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
        lastRequestId = result.requestId();

        // upload data from ByteChannel
        contentIs = new ByteArrayInputStream(contentByte);
        result = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName + "byteChannel")
                .body(BinaryData.fromByteChannel(Channels.newChannel(contentIs)))
                .build()).get();

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
        lastRequestId = result.requestId();

        // upload data from ByteChannel + length
        contentIs = new ByteArrayInputStream(contentByte);
        result = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName + "byteChannel-length")
                .body(BinaryData.fromByteChannel(Channels.newChannel(contentIs), 4L))
                .build()).get();

        cutHash = new CRC64(contentByte, 4);
        cutCrc = Long.toUnsignedString(cutHash.getValue());
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(cutCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
    }

    @Test
    public void testUploadDataWithByteChannel_small_DifferentLength() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = "UploadDataWithByteChannelSamll.bin";

        // small data
        String contentStr = "abcdefghijklmnopqrstuvwxyz0123456789";
        byte[] contentByte = contentStr.getBytes();
        Path filePath = Files.createTempFile("test-data", ".bin");
        Files.write(filePath, contentByte);

        for (int i = 0; i < 15; i++) {
            try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)) {
                String key = objectName + "-" + i;
                PutObjectResult result = client.putObjectAsync(PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(key)
                        .body(BinaryData.fromByteChannel(fileChannel, (long) i))
                        .build()).get();

                CRC64 cutHash = new CRC64(contentByte, i);
                String cutCrc = Long.toUnsignedString(cutHash.getValue());
                assertThat(result.statusCode()).isEqualTo(200);
                assertThat(result.hashCrc64ecma()).isEqualTo(cutCrc);

                GetObjectMetaResult gResult = client.getObjectMetaAsync(GetObjectMetaRequest.newBuilder()
                        .bucket(bucketName)
                        .key(key)
                        .build()).get();
                assertThat(gResult.statusCode()).isEqualTo(200);
                assertThat(gResult.contentLength()).isEqualTo(i);
            }
        }
        Files.delete(filePath);
    }


    @Test
    public void testUploadDataWithByteChannel_middle_DifferentLength() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = "UploadDataWithByteChannel-32K.bin";

        byte[] contentByte = TestUtils.generateTestData(128*1024 + 1234);
        CRC64 hash = new CRC64(contentByte, contentByte.length);
        String contentCrc = Long.toUnsignedString(hash.getValue());
        Path filePath = Files.createTempFile("test-data", ".bin");
        Files.write(filePath, contentByte);
        assertThat(filePath).hasSize(contentByte.length);

        //  whole file without length
        try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            PutObjectResult result = client.putObjectAsync(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .body(BinaryData.fromByteChannel(fileChannel))
                    .build()).get();

            assertThat(result.statusCode()).isEqualTo(200);
            assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);

            GetObjectMetaResult gResult = client.getObjectMetaAsync(GetObjectMetaRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build()).get();
            assertThat(gResult.statusCode()).isEqualTo(200);
            assertThat(gResult.contentLength()).isEqualTo(contentByte.length);
        }

        // whole file with length
        try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            PutObjectResult result = client.putObjectAsync(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .body(BinaryData.fromByteChannel(fileChannel, (long)contentByte.length))
                    .build()).get();

            assertThat(result.statusCode()).isEqualTo(200);
            assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);

            GetObjectMetaResult gResult = client.getObjectMetaAsync(GetObjectMetaRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build()).get();
            assertThat(gResult.statusCode()).isEqualTo(200);
            assertThat(gResult.contentLength()).isEqualTo(contentByte.length);
        }

        // part
        for (int i = 0; i < 8; i++) {
            long size = 32*1024 -1 + i;
            try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)) {
                String key = objectName + "-32K-" + i;
                PutObjectResult result = client.putObjectAsync(PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(key)
                        .body(BinaryData.fromByteChannel(fileChannel, size))
                        .build()).get();

                assertThat(size).isGreaterThan(30*1024);
                CRC64 cutHash = new CRC64(contentByte, (int)size);
                String cutCrc = Long.toUnsignedString(cutHash.getValue());
                assertThat(result.statusCode()).isEqualTo(200);
                assertThat(result.hashCrc64ecma()).isEqualTo(cutCrc);

                GetObjectMetaResult gResult = client.getObjectMetaAsync(GetObjectMetaRequest.newBuilder()
                        .bucket(bucketName)
                        .key(key)
                        .build()).get();
                assertThat(gResult.statusCode()).isEqualTo(200);
                assertThat(gResult.contentLength()).isEqualTo(size);
            }
        }

        Files.delete(filePath);
    }


    @Test
    public void testUploadDataWithByteChannel_big_DifferentLength() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = "UploadDataWithByteChannel-2M.bin";

        byte[] contentByte = TestUtils.generateTestData(2*1024*1024 + 1237231);
        CRC64 hash = new CRC64(contentByte, contentByte.length);
        String contentCrc = Long.toUnsignedString(hash.getValue());
        Path filePath = Files.createTempFile("test-data", ".bin");
        Files.write(filePath, contentByte);
        assertThat(filePath).hasSize(contentByte.length);

        //  whole file without length
        try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            PutObjectResult result = client.putObjectAsync(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .body(BinaryData.fromByteChannel(fileChannel))
                    .build()).get();

            assertThat(result.statusCode()).isEqualTo(200);
            assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);

            GetObjectMetaResult gResult = client.getObjectMetaAsync(GetObjectMetaRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build()).get();
            assertThat(gResult.statusCode()).isEqualTo(200);
            assertThat(gResult.contentLength()).isEqualTo(contentByte.length);
        }

        // whole file with length
        try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            PutObjectResult result = client.putObjectAsync(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .body(BinaryData.fromByteChannel(fileChannel, (long)contentByte.length))
                    .build()).get();

            assertThat(result.statusCode()).isEqualTo(200);
            assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);

            GetObjectMetaResult gResult = client.getObjectMetaAsync(GetObjectMetaRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build()).get();
            assertThat(gResult.statusCode()).isEqualTo(200);
            assertThat(gResult.contentLength()).isEqualTo(contentByte.length);
        }

        // part
        for (int i = 0; i < 5; i++) {
            long size = 1024 * 1024L + 123437 + i;
            try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)) {
                String key = objectName + "-32K-" + i;
                PutObjectResult result = client.putObjectAsync(PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(key)
                        .body(BinaryData.fromByteChannel(fileChannel, size))
                        .build()).get();

                assertThat(size).isGreaterThan(30*1024);
                CRC64 cutHash = new CRC64(contentByte, (int)size);
                String cutCrc = Long.toUnsignedString(cutHash.getValue());
                assertThat(result.statusCode()).isEqualTo(200);
                assertThat(result.hashCrc64ecma()).isEqualTo(cutCrc);

                GetObjectMetaResult gResult = client.getObjectMetaAsync(GetObjectMetaRequest.newBuilder()
                        .bucket(bucketName)
                        .key(key)
                        .build()).get();
                assertThat(gResult.statusCode()).isEqualTo(200);
                assertThat(gResult.contentLength()).isEqualTo(size);
            }
        }

        Files.delete(filePath);
    }


    @Test
    public void testGetObjectToFile() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = "objectName-to-file.bin";

        byte[] contentByte = TestUtils.generateTestData(123);
        CRC64 hash = new CRC64(contentByte, contentByte.length);
        String contentCrc = Long.toUnsignedString(hash.getValue());
        String contentMd5 = calculateMD5(new ByteArrayInputStream(contentByte));

        PutObjectResult putResult = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(contentByte))
                .build()).get();
        assertNotNull(putResult);
        assertEquals(200, putResult.statusCode());
        assertEquals(contentCrc, putResult.hashCrc64ecma());

        // write to exist file
        Path filePath = Files.createTempFile("test-data", ".bin");
        assertThat(filePath).exists();
        GetObjectResult getResult = client.getObjectToFileAsync(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build(), filePath).get();

        assertNull(getResult.body());
        assertEquals(200, getResult.statusCode());
        assertThat(getResult.contentLength()).isEqualTo(contentByte.length);
        String objectMd5 = calculateMD5(filePath.toFile());
        assertThat(objectMd5).isEqualTo(contentMd5);
        Files.delete(filePath);

        // write to non exist
        File noFile = new File(filePath.toString() + ".1");
        assertThat(noFile).doesNotExist();
        getResult = client.getObjectToFileAsync(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build(), noFile.toPath()).get();

        assertNull(getResult.body());
        assertEquals(200, getResult.statusCode());
        assertThat(getResult.contentLength()).isEqualTo(contentByte.length);
        objectMd5 = calculateMD5(noFile);
        assertThat(objectMd5).isEqualTo(contentMd5);
        Files.delete(noFile.toPath());
    }

    @Test
    public void testGetObjectToFile_withError() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = "objectName-to-file-non-exist.bin";

        Path filePath = Files.createTempFile("test-data", ".bin");
        assertThat(filePath).exists();

        try {
            client.getObjectToFileAsync(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build(), filePath).get();
            fail("should not here");
        } catch (Exception e) {
            ServiceException err = ServiceException.asCause(e);
            assertThat(err.errorCode()).isEqualTo("NoSuchKey");
        }
        assertThat(filePath).hasSize(0);
        Files.delete(filePath);


        assertThat(filePath).doesNotExist();
        try {
            client.getObjectToFileAsync(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build(), filePath).get();
            fail("should not here");
        } catch (Exception e) {
            ServiceException err = ServiceException.asCause(e);
            assertThat(err.errorCode()).isEqualTo("NoSuchKey");
        }
        assertThat(filePath).doesNotExist();
    }
}
