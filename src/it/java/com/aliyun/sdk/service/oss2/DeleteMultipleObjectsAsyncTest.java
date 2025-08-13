package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.ByteArrayBinaryData;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;

public class DeleteMultipleObjectsAsyncTest extends TestBase {

    @Test
    public void testDeleteMultipleObjectsBasic() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // Generate multiple test objects
        String objectName1 = genObjectName() + "-multi-delete-1";
        String objectName2 = genObjectName() + "-multi-delete-2";
        String objectName3 = genObjectName() + "-multi-delete-3";

        // Upload test objects
        PutObjectResult putResult1 = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName1)
                .body(new ByteArrayBinaryData("content1".getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult1);
        Assert.assertEquals(200, putResult1.statusCode());

        PutObjectResult putResult2 = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName2)
                .body(new ByteArrayBinaryData("content2".getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult2);
        Assert.assertEquals(200, putResult2.statusCode());

        PutObjectResult putResult3 = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName3)
                .body(new ByteArrayBinaryData("content3".getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult3);
        Assert.assertEquals(200, putResult3.statusCode());

        // Verify objects exist
        HeadObjectResult headResult1 = client.headObjectAsync(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName1)
                .build()).get();
        Assert.assertNotNull(headResult1);
        Assert.assertEquals(200, headResult1.statusCode());

        HeadObjectResult headResult2 = client.headObjectAsync(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName2)
                .build()).get();
        Assert.assertNotNull(headResult2);
        Assert.assertEquals(200, headResult2.statusCode());

        HeadObjectResult headResult3 = client.headObjectAsync(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName3)
                .build()).get();
        Assert.assertNotNull(headResult3);
        Assert.assertEquals(200, headResult3.statusCode());

        // Delete multiple objects
        DeleteMultipleObjectsResult deleteMultipleResult = client.deleteMultipleObjectsAsync(DeleteMultipleObjectsRequest.newBuilder()
                .bucket(bucketName)
                .quiet(false)
                .deleteObjects(Arrays.asList(
                        DeleteObject.newBuilder().key(objectName1).build(),
                        DeleteObject.newBuilder().key(objectName2).build(),
                        DeleteObject.newBuilder().key(objectName3).build()
                ))
                .build()).get();

        Assert.assertNotNull(deleteMultipleResult);
        Assert.assertEquals(200, deleteMultipleResult.statusCode());
        Assert.assertNotNull(deleteMultipleResult.deletedObjects());
        Assert.assertEquals(3, deleteMultipleResult.deletedObjects().size());


        try {
            client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName1)
                    .build()).get();
            Assert.fail("Expected exception for deleted object");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("NoSuchKey", serr.errorCode());
            Assert.assertEquals("The specified key does not exist.", serr.errorMessage());
        }

        try {
            client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName2)
                    .build()).get();
            Assert.fail("Expected exception for deleted object");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("NoSuchKey", serr.errorCode());
            Assert.assertEquals("The specified key does not exist.", serr.errorMessage());
        }

        try {
            client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName3)
                    .build()).get();
            Assert.fail("Expected exception for deleted object");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("NoSuchKey", serr.errorCode());
            Assert.assertEquals("The specified key does not exist.", serr.errorMessage());
        }
    }

    @Test
    public void testDeleteMultipleObjectsWithNonExistentObject() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // Create test object
        String objectName = genObjectName() + "-multi-delete-existing";

        PutObjectResult putResult = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName)
                .body(new ByteArrayBinaryData("content".getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // Delete multiple objects including a non-existent one
        DeleteMultipleObjectsResult deleteMultipleResult = client.deleteMultipleObjectsAsync(DeleteMultipleObjectsRequest.newBuilder()
                .bucket(bucketName)
                .quiet(false)
                .deleteObjects(Arrays.asList(
                        DeleteObject.newBuilder().key(objectName).build(),
                        DeleteObject.newBuilder().key("non-existent-object").build()
                ))
                .build()).get();

        Assert.assertNotNull(deleteMultipleResult);
        Assert.assertEquals(200, deleteMultipleResult.statusCode());
        Assert.assertNotNull(deleteMultipleResult.deletedObjects());
        Assert.assertEquals(2, deleteMultipleResult.deletedObjects().size());

        try {
            client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build()).get();
            Assert.fail("Expected exception for deleted object");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("NoSuchKey", serr.errorCode());
            Assert.assertEquals("The specified key does not exist.", serr.errorMessage());
        }
    }

    @Test
    public void testDeleteMultipleObjectsQuietMode() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // Generate multiple test objects
        String objectName1 = genObjectName() + "-multi-delete-quiet-1";
        String objectName2 = genObjectName() + "-multi-delete-quiet-2";

        // Upload test objects
        PutObjectResult putResult1 = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName1)
                .body(new ByteArrayBinaryData("content1".getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult1);
        Assert.assertEquals(200, putResult1.statusCode());

        PutObjectResult putResult2 = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(objectName2)
                .body(new ByteArrayBinaryData("content2".getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult2);
        Assert.assertEquals(200, putResult2.statusCode());

        DeleteMultipleObjectsResult deleteMultipleResult = client.deleteMultipleObjectsAsync(DeleteMultipleObjectsRequest.newBuilder()
                .bucket(bucketName)
                .quiet(true)
                .deleteObjects(Arrays.asList(
                        DeleteObject.newBuilder().key(objectName1).build(),
                        DeleteObject.newBuilder().key(objectName2).build()
                ))
                .build()).get();

        Assert.assertNotNull(deleteMultipleResult);
        Assert.assertEquals(200, deleteMultipleResult.statusCode());
        // In quiet mode, the deleted objects list should be empty or null
        Assert.assertTrue(deleteMultipleResult.deletedObjects() == null || deleteMultipleResult.deletedObjects().size() == 0);

        // Verify objects are deleted
        try {
            client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName1)
                    .build()).get();
            Assert.fail("Expected exception for deleted object");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("NoSuchKey", serr.errorCode());
            Assert.assertEquals("The specified key does not exist.", serr.errorMessage());
        }

        try {
            client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName2)
                    .build()).get();
            Assert.fail("Expected exception for deleted object");
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("NoSuchKey", serr.errorCode());
            Assert.assertEquals("The specified key does not exist.", serr.errorMessage());
        }
    }

    @Test
    public void testDeleteMultipleObjectsWithSpecialCharacters() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // Generate multiple test objects with special characters in keys
        String specialKey1 = "special-key-!@#$%^&*()_+{}|:<>?[];',./`~";
        String specialKey2 = "special-key-测试中文字符";
        String specialKey3 = "special-key-🌟emoji字符";
        byte[] keyData = new byte[]
                { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                        0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21,
                        (byte) 0xe4, (byte) 0xbd, (byte) 0xa0, (byte) 0xe5, (byte) 0xa5, (byte) 0xbd};
        String specialKey4 = new String(keyData, StandardCharsets.UTF_8);

        // Upload test objects with special characters in keys
        PutObjectResult putResult1 = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey1)
                .body(new ByteArrayBinaryData("content1".getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult1);
        Assert.assertEquals(200, putResult1.statusCode());

        PutObjectResult putResult2 = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey2)
                .body(new ByteArrayBinaryData("content2".getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult2);
        Assert.assertEquals(200, putResult2.statusCode());

        PutObjectResult putResult3 = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey3)
                .body(new ByteArrayBinaryData("content3".getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult3);
        Assert.assertEquals(200, putResult3.statusCode());

        PutObjectResult putResult4 = client.putObjectAsync(PutObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey4)
                .body(new ByteArrayBinaryData("content3".getBytes(StandardCharsets.UTF_8)))
                .build()).get();
        Assert.assertNotNull(putResult4);
        Assert.assertEquals(200, putResult4.statusCode());

        // Verify objects exist
        HeadObjectResult headResult1 = client.headObjectAsync(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey1)
                .build()).get();
        Assert.assertNotNull(headResult1);
        Assert.assertEquals(200, headResult1.statusCode());

        HeadObjectResult headResult2 = client.headObjectAsync(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey2)
                .build()).get();
        Assert.assertNotNull(headResult2);
        Assert.assertEquals(200, headResult2.statusCode());

        HeadObjectResult headResult3 = client.headObjectAsync(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey3)
                .build()).get();
        Assert.assertNotNull(headResult3);
        Assert.assertEquals(200, headResult3.statusCode());

        HeadObjectResult headResult4 = client.headObjectAsync(HeadObjectRequest.newBuilder()
                .bucket(bucketName)
                .key(specialKey4)
                .build()).get();
        Assert.assertNotNull(headResult4);
        Assert.assertEquals(200, headResult4.statusCode());

        DeleteMultipleObjectsResult deleteMultipleResult = client.deleteMultipleObjectsAsync(DeleteMultipleObjectsRequest.newBuilder()
                .bucket(bucketName)
                .quiet(false)
                .encodingType("url")
                .deleteObjects(Arrays.asList(
                        DeleteObject.newBuilder().key(specialKey1).build(),
                        DeleteObject.newBuilder().key(specialKey2).build(),
                        DeleteObject.newBuilder().key(specialKey3).build(),
                        DeleteObject.newBuilder().key(specialKey4).build()
                ))
                .build()).get();

        Assert.assertNotNull(deleteMultipleResult);
        Assert.assertEquals(200, deleteMultipleResult.statusCode());
        Assert.assertNotNull(deleteMultipleResult.deletedObjects());
        Assert.assertEquals(4, deleteMultipleResult.deletedObjects().size());

        // Check that DeletedInfo keys are properly decoded when encodingType is set to "url"
        for (DeletedInfo deletedInfo : deleteMultipleResult.deletedObjects()) {
            // Keys should be properly decoded and match the original values
            Assert.assertTrue(
                    specialKey1.equals(deletedInfo.key()) ||
                            specialKey2.equals(deletedInfo.key()) ||
                            specialKey3.equals(deletedInfo.key()) ||
                            specialKey4.equals(deletedInfo.key())
            );
        }

        // Verify objects are deleted by trying to access them
        try {
            client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(specialKey1)
                    .build()).get();
            Assert.fail("Expected exception for deleted object: " + specialKey1);
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("NoSuchKey", serr.errorCode());
            Assert.assertEquals("The specified key does not exist.", serr.errorMessage());
        }

        try {
            client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(specialKey2)
                    .build()).get();
            Assert.fail("Expected exception for deleted object: " + specialKey2);
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("NoSuchKey", serr.errorCode());
            Assert.assertEquals("The specified key does not exist.", serr.errorMessage());
        }

        try {
            client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(specialKey3)
                    .build()).get();
            Assert.fail("Expected exception for deleted object: " + specialKey3);
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(404, serr.statusCode());
            Assert.assertEquals("NoSuchKey", serr.errorCode());
            Assert.assertEquals("The specified key does not exist.", serr.errorMessage());
        }

        try {
            client.headObjectAsync(HeadObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(specialKey4)
                    .build()).get();
            Assert.fail("Expected exception for deleted object: " + specialKey4);
        } catch (Exception ec) {
            ServiceException serr = findCause(ec, ServiceException.class);
            Assert.assertEquals(404, serr.statusCode());
        }
    }

    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }
}
