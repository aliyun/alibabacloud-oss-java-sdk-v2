package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.internal.TestUtils;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.ByteArrayBinaryData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

public class ClientObjectAclTest extends TestBase {
    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }

    @Before
    public void allowPublicAccess() {
        OSSClient client = getDefaultClient();
        client.putBucketPublicAccessBlock(
                PutBucketPublicAccessBlockRequest.newBuilder()
                        .bucket(bucketName)
                        .bucketPublicAccessBlockConfiguration(BucketPublicAccessBlockConfiguration.newBuilder()
                                .blockPublicAccess(false)
                                .build())
                        .build()
        );
    }

    @Test
    public void testObjectAclOperations() {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-acl-test";

        // 1. Create an object first
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObject(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // 2. Get object ACL (default)
        GetObjectAclResult getAclResult = client.getObjectAcl(
                GetObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(getAclResult);
        Assert.assertEquals(200, getAclResult.statusCode());
        Assert.assertNotNull(getAclResult.accessControlPolicy());
        Assert.assertNotNull(getAclResult.accessControlPolicy().owner());
        Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
        Assert.assertEquals("default", getAclResult.accessControlPolicy().accessControlList().grant());

        // 3. Put object ACL to private
        PutObjectAclResult putAclResult = client.putObjectAcl(
                PutObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .objectAcl("private")
                        .build());
        Assert.assertNotNull(putAclResult);
        Assert.assertEquals(200, putAclResult.statusCode());

        // 4. Get object ACL (private)
        getAclResult = client.getObjectAcl(
                GetObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(getAclResult);
        Assert.assertEquals(200, getAclResult.statusCode());
        Assert.assertNotNull(getAclResult.accessControlPolicy());
        Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
        Assert.assertEquals("private", getAclResult.accessControlPolicy().accessControlList().grant());

        // 5. Put object ACL to public-read
        putAclResult = client.putObjectAcl(
                PutObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .objectAcl("public-read")
                        .build());
        Assert.assertNotNull(putAclResult);
        Assert.assertEquals(200, putAclResult.statusCode());

        // 6. Get object ACL (public-read)
        getAclResult = client.getObjectAcl(
                GetObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(getAclResult);
        Assert.assertEquals(200, getAclResult.statusCode());
        Assert.assertNotNull(getAclResult.accessControlPolicy());
        Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
        Assert.assertEquals("public-read", getAclResult.accessControlPolicy().accessControlList().grant());

        // 7. Clean up - delete object
        DeleteObjectResult deleteResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

    @Test
    public void testObjectAclWithRequestPayer() {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-acl-request-payer";

        // 1. Create an object first
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObject(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // 2. Put object ACL with request payer
        PutObjectAclResult putAclResult = client.putObjectAcl(
                PutObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .objectAcl("private")
                        .requestPayer("requester")
                        .build());
        Assert.assertNotNull(putAclResult);
        Assert.assertEquals(200, putAclResult.statusCode());

        // 3. Get object ACL with request payer
        GetObjectAclResult getAclResult = client.getObjectAcl(
                GetObjectAclRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .requestPayer("requester")
                        .build());
        Assert.assertNotNull(getAclResult);
        Assert.assertEquals(200, getAclResult.statusCode());
        Assert.assertNotNull(getAclResult.accessControlPolicy());
        Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
        Assert.assertEquals("private", getAclResult.accessControlPolicy().accessControlList().grant());

        // 4. Clean up - delete object
        DeleteObjectResult deleteResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }
}
