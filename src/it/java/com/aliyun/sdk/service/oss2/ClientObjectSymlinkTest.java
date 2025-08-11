package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.internal.TestUtils;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.ByteArrayBinaryData;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

public class ClientObjectSymlinkTest extends TestBase {

    private String genObjectName() {
        long ticks = new Date().getTime() / 1000;
        long val = new Random().nextInt(5000);
        return OJBJECT_NAME_PREFIX + ticks + "-" + val;
    }

    @Test
    public void testSymlinkOperations() {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-symlink-target";
        String symlinkName = genObjectName() + "-symlink";

        // 1. Create a target object first
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObject(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // 2. Put symlink
        PutSymlinkResult putSymlinkResult = client.putSymlink(
                PutSymlinkRequest.newBuilder()
                        .bucket(bucketName)
                        .key(symlinkName)
                        .symlinkTarget(objectName)
                        .objectAcl("private")
                        .build());
        Assert.assertNotNull(putSymlinkResult);
        Assert.assertEquals(200, putSymlinkResult.statusCode());

        // 3. Get symlink
        GetSymlinkResult getSymlinkResult = client.getSymlink(
                GetSymlinkRequest.newBuilder()
                        .bucket(bucketName)
                        .key(symlinkName)
                        .build());
        Assert.assertNotNull(getSymlinkResult);
        Assert.assertEquals(200, getSymlinkResult.statusCode());
        Assert.assertEquals(objectName, getSymlinkResult.symlinkTarget());

        // 4. Use symlink to get target object
        GetObjectResult getObjectResult = client.getObject(
                GetObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(symlinkName)
                        .build());
        Assert.assertNotNull(getObjectResult);
        Assert.assertEquals(200, getObjectResult.statusCode());
        Assert.assertEquals(content.length, getObjectResult.contentLength().intValue());

        // 5. Clean up - delete symlink
        DeleteObjectResult deleteSymlinkResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(symlinkName)
                        .build());
        Assert.assertNotNull(deleteSymlinkResult);
        Assert.assertEquals(204, deleteSymlinkResult.statusCode());

        // 6. Clean up - delete target object
        DeleteObjectResult deleteTargetResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(deleteTargetResult);
        Assert.assertEquals(204, deleteTargetResult.statusCode());
    }

    @Test
    public void testSymlinkWithRequestPayer() {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-symlink-target-payer";
        String symlinkName = genObjectName() + "-symlink-payer";

        // 1. Create a target object first
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObject(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());


        // 2. Put symlink with request payer
        PutSymlinkResult putSymlinkResult = client.putSymlink(
                PutSymlinkRequest.newBuilder()
                        .bucket(bucketName)
                        .key(symlinkName)
                        .symlinkTarget(objectName)
                        .requestPayer("requester")
                        .build());
        Assert.assertNotNull(putSymlinkResult);
        Assert.assertEquals(200, putSymlinkResult.statusCode());

        // 3. Get symlink with request payer
        GetSymlinkResult getSymlinkResult = client.getSymlink(
                GetSymlinkRequest.newBuilder()
                        .bucket(bucketName)
                        .key(symlinkName)
                        .requestPayer("requester")
                        .build());
        Assert.assertNotNull(getSymlinkResult);
        Assert.assertEquals(200, getSymlinkResult.statusCode());
        Assert.assertEquals(objectName, getSymlinkResult.symlinkTarget());

        // 4. Clean up - delete symlink
        DeleteObjectResult deleteSymlinkResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(symlinkName)
                        .build());
        Assert.assertNotNull(deleteSymlinkResult);
        Assert.assertEquals(204, deleteSymlinkResult.statusCode());

        // 5. Clean up - delete target object
        DeleteObjectResult deleteTargetResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(deleteTargetResult);
        Assert.assertEquals(204, deleteTargetResult.statusCode());
    }

    @Test
    public void testSymlinkWithForbidOverwrite() {
        OSSClient client = getDefaultClient();
        String objectName = genObjectName() + "-symlink-target-overwrite";
        String symlinkName = genObjectName() + "-symlink-overwrite";

        // 1. Create a target object first
        byte[] content = TestUtils.generateTestData(1024);
        PutObjectResult putResult = client.putObject(
                PutObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .body(new ByteArrayBinaryData(content))
                        .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // 2. Put symlink with forbid overwrite
        PutSymlinkResult putSymlinkResult = client.putSymlink(
                PutSymlinkRequest.newBuilder()
                        .bucket(bucketName)
                        .key(symlinkName)
                        .symlinkTarget(objectName)
                        .forbidOverwrite("true")
                        .build());
        Assert.assertNotNull(putSymlinkResult);
        Assert.assertEquals(200, putSymlinkResult.statusCode());

        // 3. Put symlink again with forbid overwrite
        putSymlinkResult = client.putSymlink(
                PutSymlinkRequest.newBuilder()
                        .bucket(bucketName)
                        .key(symlinkName)
                        .symlinkTarget(objectName)
                        .forbidOverwrite("false")
                        .build());
        Assert.assertNotNull(putSymlinkResult);
        Assert.assertEquals(200, putSymlinkResult.statusCode());

        // 4. Get symlink
        GetSymlinkResult getSymlinkResult = client.getSymlink(
                GetSymlinkRequest.newBuilder()
                        .bucket(bucketName)
                        .key(symlinkName)
                        .build());
        Assert.assertNotNull(getSymlinkResult);
        Assert.assertEquals(200, getSymlinkResult.statusCode());
        Assert.assertEquals(objectName, getSymlinkResult.symlinkTarget());

        // 5. Clean up - delete symlink
        DeleteObjectResult deleteSymlinkResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(symlinkName)
                        .build());
        Assert.assertNotNull(deleteSymlinkResult);
        Assert.assertEquals(204, deleteSymlinkResult.statusCode());

        // 6. Clean up - delete target object
        DeleteObjectResult deleteTargetResult = client.deleteObject(
                DeleteObjectRequest.newBuilder()
                        .bucket(bucketName)
                        .key(objectName)
                        .build());
        Assert.assertNotNull(deleteTargetResult);
        Assert.assertEquals(204, deleteTargetResult.statusCode());
    }
}
