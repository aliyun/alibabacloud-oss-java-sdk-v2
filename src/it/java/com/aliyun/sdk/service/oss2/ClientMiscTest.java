package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.hash.CRC64;
import com.aliyun.sdk.service.oss2.internal.TestUtils;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.IOUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class ClientMiscTest extends TestBase {

    @Test
    public void testInvokeOperation() {
        OSSClient client = getDefaultClient();

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

        client.invokeOperation(input, OperationOptions.defaults());


        // get object
        GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
    }

    @Test
    public void useApache4HttpClientSendRequest() throws Exception {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        try (OSSClient client = OSSClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .useApacheHttpClient4(true)
                .credentialsProvider(provider)
                .build()) {

            client.getBucketInfo(GetBucketInfoRequest.newBuilder().bucket(bucketName).build());

            String objectName = "1.txt";
            byte[] content = "hello world".getBytes();

            // put object
            PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .body(BinaryData.fromBytes(content))
                    .build());
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            // get object
            try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build())) {
                Assert.assertNotNull(getResult);
                byte[] gotData = IOUtils.toByteArray(getResult.body());
                Assert.assertEquals(200, getResult.statusCode());
                Assert.assertArrayEquals(content, gotData);
            }

            // get object meta
            GetObjectMetaResult metaResult = client.getObjectMeta(GetObjectMetaRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());
            Assert.assertNotNull(metaResult);
            Assert.assertEquals(200, metaResult.statusCode());
            Assert.assertEquals(Long.valueOf(content.length), metaResult.contentLength());

            client.deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());
        }
    }

    @Test
    public void testDoesBucketExist() throws Exception {

        OSSClient client = getDefaultClient();
        String bucketNameNoExist = bucketName + "-not-exist";

        assertTrue(client.doesBucketExist(bucketName));
        assertFalse(client.doesBucketExist(bucketNameNoExist));

        //noPermClient
        try (OSSClient noPermClient = OSSClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .build()) {
            assertTrue(noPermClient.doesBucketExist(bucketName));
            assertFalse(noPermClient.doesBucketExist(bucketNameNoExist));
        }

        //errorClient
        try (OSSClient errorClient = OSSClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(new StaticCredentialsProvider("", ""))
                .build()) {
            assertTrue(errorClient.doesBucketExist(bucketName));
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("Credentials is null or empty.");
        }
    }

    @Test
    public void testDoesObjectExist() throws Exception {

        OSSClient client = getDefaultClient();
        String objectName = "objectName-exist";
        String objectNameNoExist = "objectName-no-exist";

        String bucketNameNoExist = bucketName + "-not-exist";

        client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build());

        assertTrue(client.doesObjectExist(bucketName, objectName));
        assertFalse(client.doesObjectExist(bucketName, objectNameNoExist));
        assertTrue(client.doesObjectExistLegacy(bucketName, objectName));
        assertFalse(client.doesObjectExistLegacy(bucketName, objectNameNoExist));


        try {
            assertFalse(client.doesObjectExist(bucketNameNoExist, objectName));
            fail("should not here");
        } catch (Exception e) {
            ServiceException err = ServiceException.asCause(e);
            assertThat(err.errorCode()).isEqualTo("NoSuchBucket");
        }
        assertFalse(client.doesObjectExistLegacy(bucketNameNoExist, objectName));


        try {
            assertFalse(client.doesObjectExist(bucketNameNoExist, objectNameNoExist));
            fail("should not here");
        } catch (Exception e) {
            ServiceException err = ServiceException.asCause(e);
            assertThat(err.errorCode()).isEqualTo("NoSuchBucket");
        }
        assertFalse(client.doesObjectExistLegacy(bucketNameNoExist, objectNameNoExist));

        //noPermClient
        try (OSSClient noPermClient = OSSClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(new StaticCredentialsProvider("ak", "sk"))
                .build()) {

            try {
                assertTrue(noPermClient.doesObjectExist(bucketName, objectName));
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("InvalidAccessKeyId");
            }

            try {
                assertTrue(noPermClient.doesObjectExistLegacy(bucketName, objectName));
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("InvalidAccessKeyId");
            }

            try {
                assertTrue(noPermClient.doesObjectExist(bucketName, objectNameNoExist));
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("InvalidAccessKeyId");
            }

            try {
                assertTrue(noPermClient.doesObjectExistLegacy(bucketName, objectNameNoExist));
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("InvalidAccessKeyId");
            }

            try {
                assertTrue(noPermClient.doesObjectExist(bucketNameNoExist, objectName));
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("NoSuchBucket");
            }
            assertFalse(noPermClient.doesObjectExistLegacy(bucketNameNoExist, objectName));

            try {
                assertTrue(noPermClient.doesObjectExist(bucketNameNoExist, objectNameNoExist));
                fail("should not here");
            } catch (Exception e) {
                ServiceException err = ServiceException.asCause(e);
                assertThat(err.errorCode()).isEqualTo("NoSuchBucket");
            }
            assertFalse(noPermClient.doesObjectExistLegacy(bucketNameNoExist, objectNameNoExist));
        }

        //errorClient
        try (OSSClient errorClient = OSSClient.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .credentialsProvider(new StaticCredentialsProvider("", ""))
                .build()) {
            try {
                assertTrue(errorClient.doesObjectExist(bucketName, objectName));
                fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("Credentials is null or empty.");
            }

            try {
                assertTrue(errorClient.doesObjectExistLegacy(bucketName, objectName));
                fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("Credentials is null or empty.");
            }

            try {
                assertTrue(errorClient.doesObjectExist(bucketNameNoExist, objectName));
                fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("Credentials is null or empty.");
            }

            try {
                assertTrue(errorClient.doesObjectExistLegacy(bucketNameNoExist, objectName));
                fail("should not here");
            } catch (Exception e) {
                assertThat(e).hasMessageContaining("Credentials is null or empty.");
            }
        }
    }

    @Test
    public void testPutObjectFromFile() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = "objectName-from-file.bin";

        File file = createSampleFile("from-file.bin", 512 * 1024 + 123456);
        String fileMd5 = calculateMD5(file);
        assertThat(fileMd5).isNotNull();
        assertThat(fileMd5).isNotEmpty();

        PutObjectResult putResult = client.putObjectFromFile(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build(), file.toPath());

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // get object
        String objectMd5;
        try (GetObjectResult getResult = client.getObject(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build())) {
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
            client.putObjectFromFile(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build(), noFile);
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("FileNotFoundException");
        }
    }

    @Test
    public void testGetObjectToFile() throws Exception {
        OSSClient client = getDefaultClient();
        String objectName = "objectName-to-file.bin";

        File file = createSampleFile("to-file.bin", 0);
        assertThat(file).exists();
        assertThat(file).hasSize(0);
        File noFile = new File(file.toString() + ".dst");
        assertThat(noFile).doesNotExist();

        byte[] content = TestUtils.generateTestData(200 * 1024 + 123); // 200kb + 123 bytes
        String contentMd5 = calculateMD5(new ByteArrayInputStream(content));

        assertThat(contentMd5).isNotNull();
        PutObjectResult putResult = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(content))
                .build());
        assertNotNull(putResult);
        assertEquals(200, putResult.statusCode());

        // write to exist file
        String objectMd5;
        try (GetObjectResult getResult = client.getObjectToFile(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build(), file.toPath())) {
            assertNull(getResult.body());
            assertEquals(200, getResult.statusCode());
            assertThat(getResult.contentLength()).isEqualTo(content.length);
        }
        objectMd5 = calculateMD5(file);
        assertThat(objectMd5).isEqualTo(contentMd5);

        // write to non-exist file
        try (GetObjectResult getResult = client.getObjectToFile(GetObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .build(), noFile)) {
            assertNull(getResult.body());
            assertEquals(200, getResult.statusCode());
            assertThat(getResult.contentLength()).isEqualTo(content.length);
        }
        assertThat(noFile).exists();
        objectMd5 = calculateMD5(noFile);
        assertThat(objectMd5).isEqualTo(contentMd5);

        Files.delete(file.toPath());
        Files.delete(noFile.toPath());
    }

    @Test
    public void testUploadData_DifferentBinaryData() throws Exception {
        OSSClient client = getDefaultClient();
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
        PutObjectResult result = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromBytes(contentByte))
                .build());

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
        lastRequestId = result.requestId();

        // upload data from byte array
        result = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromByteBuffer(contentBB))
                .build());

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
        lastRequestId = result.requestId();

        // upload data from string
        result = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromString(contentStr))
                .build());

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
        lastRequestId = result.requestId();

        // upload data from input stream
        contentIs = new ByteArrayInputStream(contentByte);
        result = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(contentIs))
                .build());

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(contentCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
        lastRequestId = result.requestId();

        // upload data from input stream + length
        contentIs = new ByteArrayInputStream(contentByte);
        result = client.putObject(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(BinaryData.fromStream(contentIs, 2L))
                .build());

        CRC64 cutHash = new CRC64(contentByte, 2);
        String cutCrc = Long.toUnsignedString(cutHash.getValue());
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo(cutCrc);
        assertThat(result.requestId()).isNotEqualTo(lastRequestId);
        lastRequestId = result.requestId();

        // upload data from ByteChannel
        try {
            contentIs = new ByteArrayInputStream(contentByte);
            client.putObject(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .body(BinaryData.fromByteChannel(Channels.newChannel(contentIs)))
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("The synchronous interface does not support ByteChannelBinaryData");
        }

        // upload data from ByteChannel + length
        try {
            contentIs = new ByteArrayInputStream(contentByte);
            client.putObject(PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .body(BinaryData.fromByteChannel(Channels.newChannel(contentIs), 2L))
                    .build());
            fail("should not here");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("The synchronous interface does not support ByteChannelBinaryData");
        }
    }

}
