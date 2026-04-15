package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.tables.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientTableBucketEncryptionTest extends TestBaseTables {

    @Test
    public void testTableBucketEncryptionLifecycle() {
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

            // 2. Set encryption configuration on the table bucket
            EncryptionConfiguration encryptionConfig = EncryptionConfiguration.newBuilder()
                    .sseAlgorithm("AES256")
                    .kmsKeyArn(null) // Using null for basic encryption
                    .build();

            PutTableBucketEncryptionResult putEncryptionResult = client.putTableBucketEncryption(
                    PutTableBucketEncryptionRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .encryptionConfiguration(encryptionConfig)
                            .build());

            // Assert successful encryption configuration
            Assert.assertNotNull(putEncryptionResult);
            Assert.assertEquals(200, putEncryptionResult.statusCode());
            System.out.printf("Successfully configured encryption for table bucket, request ID: %s%n", putEncryptionResult.requestId());

            // 3. Get the encryption configuration to verify it was set
            GetTableBucketEncryptionResult getEncryptionResult = client.getTableBucketEncryption(
                    GetTableBucketEncryptionRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .build());

            // Assert successful retrieval of encryption configuration
            Assert.assertNotNull(getEncryptionResult);
            Assert.assertEquals(200, getEncryptionResult.statusCode());
            Assert.assertNotNull(getEncryptionResult.encryptionConfiguration());
            Assert.assertEquals("AES256", getEncryptionResult.encryptionConfiguration().sseAlgorithm());
            System.out.printf("Successfully retrieved encryption configuration, algorithm: %s%n", 
                    getEncryptionResult.encryptionConfiguration().sseAlgorithm());

            // 4. Delete the encryption configuration
            DeleteTableBucketEncryptionResult deleteEncryptionResult = client.deleteTableBucketEncryption(
                    DeleteTableBucketEncryptionRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .build());

            // Assert successful deletion of encryption configuration
            Assert.assertNotNull(deleteEncryptionResult);
            Assert.assertEquals(204, deleteEncryptionResult.statusCode());
            System.out.printf("Successfully deleted encryption configuration, request ID: %s%n", 
                    deleteEncryptionResult.requestId());

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

    @Test
    public void testGetEncryptionOnNonExistentBucket() {
        OSSTablesClient client = getTablesClient();
        
        // Attempt to get encryption configuration on a non-existent bucket
        try {
            GetTableBucketEncryptionResult getResult = client.getTableBucketEncryption(
                    GetTableBucketEncryptionRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .build());
            
            // Should not reach here if proper error handling is in place
            Assert.fail("Expected exception when getting encryption from non-existent bucket");
        } catch (Exception e) {
            // Expected - bucket doesn't exist
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent bucket: %s%n", e.getMessage());
        }
    }
}