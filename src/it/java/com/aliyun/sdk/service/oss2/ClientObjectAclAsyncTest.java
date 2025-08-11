package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.internal.TestUtils;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.ByteArrayBinaryData;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ClientObjectAclAsyncTest extends TestBase {
    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }

    @Test
    public void testObjectAclOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = genObjectName() + "-acl-test";

        // 1. Create an object first
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObjectAsync(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // 2. Get object ACL (default)
        GetObjectAclResult getAclResult = client.getObjectAclAsync(
                GetObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build()).get();
        Assert.assertNotNull(getAclResult);
        Assert.assertEquals(200, getAclResult.statusCode());
        Assert.assertNotNull(getAclResult.accessControlPolicy());
        Assert.assertNotNull(getAclResult.accessControlPolicy().owner());
        Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
        Assert.assertEquals("default", getAclResult.accessControlPolicy().accessControlList().grant());

        // 3. Put object ACL to private
        PutObjectAclResult putAclResult = client.putObjectAclAsync(
                PutObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .objectAcl("private")
                        .build()).get();
        Assert.assertNotNull(putAclResult);
        Assert.assertEquals(200, putAclResult.statusCode());

        // 4. Get object ACL (private)
        getAclResult = client.getObjectAclAsync(
                GetObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build()).get();
        Assert.assertNotNull(getAclResult);
        Assert.assertEquals(200, getAclResult.statusCode());
        Assert.assertNotNull(getAclResult.accessControlPolicy());
        Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
        Assert.assertEquals("private", getAclResult.accessControlPolicy().accessControlList().grant());

        // 5. Put object ACL to public-read
        putAclResult = client.putObjectAclAsync(
                PutObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .objectAcl("public-read")
                        .build()).get();
        Assert.assertNotNull(putAclResult);
        Assert.assertEquals(200, putAclResult.statusCode());

        // 6. Get object ACL (public-read)
        getAclResult = client.getObjectAclAsync(
                GetObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build()).get();
        Assert.assertNotNull(getAclResult);
        Assert.assertEquals(200, getAclResult.statusCode());
        Assert.assertNotNull(getAclResult.accessControlPolicy());
        Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
        Assert.assertEquals("public-read", getAclResult.accessControlPolicy().accessControlList().grant());

        // 7. Clean up - delete object
        DeleteObjectResult deleteResult = client.deleteObjectAsync(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build()).get();
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

    @Test
    public void testObjectAclWithRequestPayer() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String objectName = genObjectName() + "-acl-request-payer";

        // 1. Create an object first
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObjectAsync(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build()).get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // 2. Put object ACL with request payer
        PutObjectAclResult putAclResult = client.putObjectAclAsync(
                PutObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .objectAcl("private")
                        .requestPayer("requester")
                        .build()).get();
        Assert.assertNotNull(putAclResult);
        Assert.assertEquals(200, putAclResult.statusCode());

        // 3. Get object ACL with request payer
        GetObjectAclResult getAclResult = client.getObjectAclAsync(
                GetObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .requestPayer("requester")
                        .build()).get();
        Assert.assertNotNull(getAclResult);
        Assert.assertEquals(200, getAclResult.statusCode());
        Assert.assertNotNull(getAclResult.accessControlPolicy());
        Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
        Assert.assertEquals("private", getAclResult.accessControlPolicy().accessControlList().grant());

        // 4. Clean up - delete object
        DeleteObjectResult deleteResult = client.deleteObjectAsync(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build()).get();
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }
}
