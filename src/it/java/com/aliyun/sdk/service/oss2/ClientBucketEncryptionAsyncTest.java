package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.ExecutionException;

public class ClientBucketEncryptionAsyncTest extends TestBase {

    @Test
    public void testBucketEncryption_default() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // 1. PutBucketEncryption
        ApplyServerSideEncryptionByDefault applyRule = ApplyServerSideEncryptionByDefault.newBuilder()
                .sSEAlgorithm("KMS")
                .kMSMasterKeyID("9468da86-3509-4f8d-a61e-6eab1eac****")
                .kMSDataEncryption("SM4")
                .build();

        ServerSideEncryptionRule rule = ServerSideEncryptionRule.newBuilder()
                .applyServerSideEncryptionByDefault(applyRule)
                .build();

        PutBucketEncryptionResult putResult = client.putBucketEncryptionAsync(PutBucketEncryptionRequest.newBuilder()
                .bucket(bucketName)
                .serverSideEncryptionRule(rule)
                .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(1);

        // 2. GetBucketEncryption
        GetBucketEncryptionResult getResult = client.getBucketEncryptionAsync(GetBucketEncryptionRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());

        ServerSideEncryptionRule resultRule = getResult.serverSideEncryptionRule();
        Assert.assertNotNull(resultRule);

        ApplyServerSideEncryptionByDefault resultApplyRule = resultRule.applyServerSideEncryptionByDefault();
        Assert.assertNotNull(resultApplyRule);
        Assert.assertEquals("KMS", resultApplyRule.sSEAlgorithm());
        Assert.assertEquals("9468da86-3509-4f8d-a61e-6eab1eac****", resultApplyRule.kMSMasterKeyID());
        Assert.assertEquals("SM4", resultApplyRule.kMSDataEncryption());

        // 3. DeleteBucketEncryption
        DeleteBucketEncryptionResult deleteResult = client.deleteBucketEncryptionAsync(DeleteBucketEncryptionRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

    @Test
    public void testBucketEncryption_AES256() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // 1. PutBucketEncryption with AES256
        ApplyServerSideEncryptionByDefault applyRule = ApplyServerSideEncryptionByDefault.newBuilder()
                .sSEAlgorithm("AES256")
                .build();

        ServerSideEncryptionRule rule = ServerSideEncryptionRule.newBuilder()
                .applyServerSideEncryptionByDefault(applyRule)
                .build();

        PutBucketEncryptionResult putResult = client.putBucketEncryptionAsync(PutBucketEncryptionRequest.newBuilder()
                .bucket(bucketName)
                .serverSideEncryptionRule(rule)
                .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(1);

        // 2. GetBucketEncryption
        GetBucketEncryptionResult getResult = client.getBucketEncryptionAsync(GetBucketEncryptionRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());

        ServerSideEncryptionRule resultRule = getResult.serverSideEncryptionRule();
        Assert.assertNotNull(resultRule);

        ApplyServerSideEncryptionByDefault resultApplyRule = resultRule.applyServerSideEncryptionByDefault();
        Assert.assertNotNull(resultApplyRule);
        Assert.assertEquals("AES256", resultApplyRule.sSEAlgorithm());
        Assert.assertNull(resultApplyRule.kMSMasterKeyID());
        Assert.assertNull(resultApplyRule.kMSDataEncryption());

        // 3. DeleteBucketEncryption
        DeleteBucketEncryptionResult deleteResult = client.deleteBucketEncryptionAsync(DeleteBucketEncryptionRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }
}