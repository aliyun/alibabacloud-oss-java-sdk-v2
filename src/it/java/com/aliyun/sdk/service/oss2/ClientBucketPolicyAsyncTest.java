package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.ExecutionException;

public class ClientBucketPolicyAsyncTest extends TestBase {

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
    public void testBucketPolicyOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        
        String policyDocument = "{\n" +
                "  \"Version\": \"1\",\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Principal\": [\"*\"],\n" +
                "      \"Action\": [\"oss:GetObject\"],\n" +
                "      \"Resource\": [\"acs:oss:*:*:" + bucketName + "/*\"]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // putBucketPolicy
        PutBucketPolicyResult putResult = client.putBucketPolicyAsync(PutBucketPolicyRequest.newBuilder()
                .bucket(bucketName)
                .body(BinaryData.fromString(policyDocument))
                .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(1);

        // getBucketPolicy
        GetBucketPolicyResult getResult = client.getBucketPolicyAsync(GetBucketPolicyRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.body());
        String policyContent = getResult.body().toString();
        Assert.assertTrue(policyContent.contains("\"Version\": \"1\""));
        Assert.assertTrue(policyContent.contains("\"Effect\": \"Allow\""));

        // deleteBucketPolicy
        DeleteBucketPolicyResult deleteResult = client.deleteBucketPolicyAsync(DeleteBucketPolicyRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

    @Test
    public void testBucketPolicyFail() {
        // Add failure test cases if needed
    }
}