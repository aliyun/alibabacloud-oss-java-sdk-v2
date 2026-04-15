package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.tables.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientTableBucketMaintenanceConfigurationTest extends TestBaseTables {

    @Test
    public void testTableBucketMaintenanceConfigurationLifecycle() {
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

            // 2. Define a sample maintenance configuration
            IcebergUnreferencedFileRemovalSettings removalSettings = IcebergUnreferencedFileRemovalSettings.newBuilder()
                    .unreferencedDays(5)
                    .nonCurrentDays(15)
                    .build();
            
            TableBucketMaintenanceSettings settings = TableBucketMaintenanceSettings.newBuilder()
                    .icebergUnreferencedFileRemoval(removalSettings)
                    .build();
            
            TableBucketMaintenanceConfigurationValue value = TableBucketMaintenanceConfigurationValue.newBuilder()
                    .status("enabled")
                    .settings(settings)
                    .build();

            // 3. Set maintenance configuration on the table bucket
            PutTableBucketMaintenanceConfigurationResult putMaintResult = client.putTableBucketMaintenanceConfiguration(
                    PutTableBucketMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .type("iceberg-unreferenced-file-removal")
                            .value(value)
                            .build());

            // Assert successful maintenance configuration
            Assert.assertNotNull(putMaintResult);
            Assert.assertEquals(204, putMaintResult.statusCode());
            System.out.printf("Successfully configured maintenance for table bucket, request ID: %s%n", putMaintResult.requestId());

            // 4. Set maintenance configuration on the table bucket
            GetTableBucketMaintenanceConfigurationResult getMaintResult = client.getTableBucketMaintenanceConfiguration(
                    GetTableBucketMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN(tableBucketARN)
                            .build());

            // Assert successful maintenance configuration
            Assert.assertNotNull(getMaintResult);
            Assert.assertEquals(200, getMaintResult.statusCode());
            Assert.assertEquals(tableBucketARN, getMaintResult.tableBucketARN());
            System.out.printf("Successfully configured maintenance for table bucket, request ID: %s%n", getMaintResult.requestId());

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
    public void testSetMaintenanceOnNonExistentBucket() {
        OSSTablesClient client = getTablesClient();
            
        // Attempt to set maintenance configuration on a non-existent bucket
        try {
            IcebergUnreferencedFileRemovalSettings removalSettings = IcebergUnreferencedFileRemovalSettings.newBuilder()
                    .unreferencedDays(5)
                    .nonCurrentDays(15)
                    .build();
            
            TableBucketMaintenanceSettings settings = TableBucketMaintenanceSettings.newBuilder()
                    .icebergUnreferencedFileRemoval(removalSettings)
                    .build();
            
            TableBucketMaintenanceConfigurationValue value = TableBucketMaintenanceConfigurationValue.newBuilder()
                    .status("enabled")
                    .settings(settings)
                    .build();

            PutTableBucketMaintenanceConfigurationResult result = client.putTableBucketMaintenanceConfiguration(
                    PutTableBucketMaintenanceConfigurationRequest.newBuilder()
                            .tableBucketARN("acs:osstables:cn-hangzhou:123456789012:bucket/nonexistent-bucket-test-12345")
                            .type("iceberg-unreferenced-file-removal")
                            .value(value)
                            .build());
                
            // Should not reach here if proper error handling is in place
            Assert.fail("Expected exception when setting maintenance configuration on non-existent bucket");
        } catch (Exception e) {
            // Expected - bucket doesn't exist
            Assert.assertNotNull(e);
            System.out.printf("Successfully caught expected exception for non-existent bucket: %s%n", e.getMessage());
        }
    }
}