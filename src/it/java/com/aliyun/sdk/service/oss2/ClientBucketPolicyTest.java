package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;

public class ClientBucketPolicyTest extends TestBase {

    @Test
    public void testBucketPolicyOperations() throws Exception {
        try (OSSClient client = getOssClient()) {
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
            PutBucketPolicyResult putResult = client.putBucketPolicy(PutBucketPolicyRequest.newBuilder()
                    .bucket(bucketName)
                    .body(BinaryData.fromString(policyDocument))
                    .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(1);

            // getBucketPolicy
            GetBucketPolicyResult getResult = client.getBucketPolicy(GetBucketPolicyRequest.newBuilder()
                    .bucket(bucketName)
                    .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.body());
            String policyContent = getResult.body().toString();
            Assert.assertTrue(policyContent.contains("\"Version\": \"1\""));
            Assert.assertTrue(policyContent.contains("\"Effect\": \"Allow\""));

            // deleteBucketPolicy
            DeleteBucketPolicyResult deleteResult = client.deleteBucketPolicy(DeleteBucketPolicyRequest.newBuilder()
                    .bucket(bucketName)
                    .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
        }
    }

    @Test
    public void testBucketPolicyFail() {
        // Add failure test cases if needed
    }
}