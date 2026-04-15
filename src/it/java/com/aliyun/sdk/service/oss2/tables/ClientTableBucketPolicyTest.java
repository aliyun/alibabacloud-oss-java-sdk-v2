package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.tables.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientTableBucketPolicyTest extends TestBaseTables {

    @Test
    public void testTableBucketPolicyLifecycle() {
        OSSTablesClient client = getTablesClient();
        String bucketName = genTableBucketName();
        String tableBucketARN = null;

        try {
            // 1. Create a table bucket for testing
            CreateTableBucketResult createResult = client.createTableBucket(
                    CreateTableBucketRequest.newBuilder()
                            .name(bucketName)
                            .build());

            Assert.assertNotNull(createResult);
            Assert.assertEquals(200, createResult.statusCode());
            Assert.assertNotNull(createResult.arn());
            System.out.printf("Successfully created table bucket with ARN: %s%n", createResult.arn());

            tableBucketARN = createResult.arn();

            // 2. Define a sample policy
            String policy = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetTable\"],\"Effect\":\"Deny\",\"Principal\":[\"1234567890\"],\"Resource\":[\"acs:osstable:cn-hangzhou:1234567890:bucket/" + bucketName + "\"]}]}";

            // 3. Set policy on the table bucket
            PutTableBucketPolicyResult putPolicyResult = client.putTableBucketPolicy(
                    PutTableBucketPolicyRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .resourcePolicy(policy)
                            .build());

            // Assert successful policy configuration
            Assert.assertNotNull(putPolicyResult);
            Assert.assertEquals(200, putPolicyResult.statusCode());
            System.out.printf("Successfully configured policy for table bucket, request ID: %s%n", putPolicyResult.requestId());

            // 4. Get the policy to verify it was set
            GetTableBucketPolicyResult getPolicyResult = client.getTableBucketPolicy(
                    GetTableBucketPolicyRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .build());

            // Assert successful retrieval of policy
            Assert.assertNotNull(getPolicyResult);
            Assert.assertEquals(200, getPolicyResult.statusCode());
            Assert.assertNotNull(getPolicyResult.resourcePolicy());
            Assert.assertTrue(getPolicyResult.resourcePolicy().contains("Version"));
            Assert.assertTrue(getPolicyResult.resourcePolicy().contains("Statement"));
            System.out.printf("Successfully retrieved policy, contains: %s%n", 
                    getPolicyResult.resourcePolicy().substring(0, Math.min(50, getPolicyResult.resourcePolicy().length())) + "...");

            // 5. Delete the policy
            DeleteTableBucketPolicyResult deletePolicyResult = client.deleteTableBucketPolicy(
                    DeleteTableBucketPolicyRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .build());

            // Assert successful deletion of policy
            Assert.assertNotNull(deletePolicyResult);
            Assert.assertEquals(204, deletePolicyResult.statusCode());
            System.out.printf("Successfully deleted policy, request ID: %s%n", 
                    deletePolicyResult.requestId());

        } finally {
            // Cleanup: Delete the test bucket if it still exists
            if (tableBucketARN != null) {
                try {
                    DeleteTableBucketResult deleteResult = client.deleteTableBucket(
                            DeleteTableBucketRequest.newBuilder()
                                    .tableBucketARN(tableBucketARN)
                                    .build());
                    Assert.assertNotNull(deleteResult);
                    Assert.assertEquals(204, deleteResult.statusCode());
                    System.out.printf("Successfully cleaned up table bucket, request ID: %s%n", 
                            deleteResult.requestId());
                } catch (ServiceException e) {
                    // If the bucket was already deleted, that's fine
                    System.out.printf("Cleanup: Could not delete table bucket (may have been already deleted): %s%n", 
                            e.getMessage());
                }
            }
        }
    }

}