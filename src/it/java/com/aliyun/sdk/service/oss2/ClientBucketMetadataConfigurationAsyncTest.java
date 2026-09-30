package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class ClientBucketMetadataConfigurationAsyncTest extends TestBase {

    @Test
    public void testBucketMetadataConfigurationOperationsAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        boolean created = false;

        try {
            // Create the bucket metadata configuration.
            CreateBucketMetadataConfigurationResult createResult = client.createBucketMetadataConfigurationAsync(
                    CreateBucketMetadataConfigurationRequest.newBuilder()
                            .bucket(bucketName)
                            .metadataConfiguration(newMetadataConfiguration())
                            .build()).get();
            created = true;
            Assert.assertEquals(200, createResult.statusCode());
            Assert.assertNotNull(createResult.requestId());

            // Get and verify the bucket metadata configuration.
            GetBucketMetadataConfigurationResult getResult = client.getBucketMetadataConfigurationAsync(
                    GetBucketMetadataConfigurationRequest.newBuilder()
                            .bucket(bucketName)
                            .build()).get();
            assertMetadataConfiguration(getResult);

            // Wait until the journal table can be updated.
            waitForJournalTableActive(client);

            // Update the journal table record expiration.
            UpdateBucketMetadataJournalTableConfigurationResult journalResult =
                    client.updateBucketMetadataJournalTableConfigurationAsync(
                            UpdateBucketMetadataJournalTableConfigurationRequest.newBuilder()
                                    .bucket(bucketName)
                                    .journalTableConfiguration(JournalTableConfiguration.newBuilder()
                                            .recordExpiration(MetadataTableRecordExpiration.newBuilder()
                                                    .expiration("ENABLED")
                                                    .days(60)
                                                    .build())
                                            .build())
                                    .build()).get();
            Assert.assertEquals(200, journalResult.statusCode());
            Assert.assertNotNull(journalResult.requestId());

            // Enable the inventory table.
            UpdateBucketMetadataInventoryTableConfigurationResult inventoryResult =
                    client.updateBucketMetadataInventoryTableConfigurationAsync(
                            UpdateBucketMetadataInventoryTableConfigurationRequest.newBuilder()
                                    .bucket(bucketName)
                                    .inventoryTableConfiguration(InventoryTableConfiguration.newBuilder()
                                            .configurationState("ENABLED")
                                            .build())
                                    .build()).get();
            Assert.assertEquals(200, inventoryResult.statusCode());
            Assert.assertNotNull(inventoryResult.requestId());
        } finally {
            if (created) {
                // Delete the bucket metadata configuration.
                DeleteBucketMetadataConfigurationResult deleteResult = client.deleteBucketMetadataConfigurationAsync(
                        DeleteBucketMetadataConfigurationRequest.newBuilder()
                                .bucket(bucketName)
                                .build()).get();
                Assert.assertEquals(204, deleteResult.statusCode());
                Assert.assertNotNull(deleteResult.requestId());
            }
        }
    }

    private static MetadataConfiguration newMetadataConfiguration() {
        return MetadataConfiguration.newBuilder()
                .journalTableConfiguration(JournalTableConfiguration.newBuilder()
                        .recordExpiration(MetadataTableRecordExpiration.newBuilder()
                                .expiration("ENABLED")
                                .days(30)
                                .build())
                        .build())
                .inventoryTableConfiguration(InventoryTableConfiguration.newBuilder()
                        .configurationState("DISABLED")
                        .build())
                .build();
    }

    private static void assertMetadataConfiguration(GetBucketMetadataConfigurationResult result) {
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.requestId());
        Assert.assertNotNull(result.metadataConfigurationResult());
        Assert.assertNotNull(result.metadataConfigurationResult().destinationResult());
        Assert.assertEquals("oss", result.metadataConfigurationResult().destinationResult().tableBucketType());
        Assert.assertNotNull(result.metadataConfigurationResult().journalTableConfigurationResult());
        Assert.assertNotNull(result.metadataConfigurationResult().inventoryTableConfigurationResult());
        Assert.assertEquals("DISABLED", result.metadataConfigurationResult()
                .inventoryTableConfigurationResult().configurationState());
    }

    private static void waitForJournalTableActive(OSSAsyncClient client)
            throws ExecutionException, InterruptedException {
        for (int i = 0; i < 30; i++) {
            GetBucketMetadataConfigurationResult result = client.getBucketMetadataConfigurationAsync(
                    GetBucketMetadataConfigurationRequest.newBuilder().bucket(bucketName).build()).get();
            String status = result.metadataConfigurationResult()
                    .journalTableConfigurationResult().tableStatus();
            if ("ACTIVE".equals(status)) {
                return;
            }
            if ("FAILED".equals(status)) {
                Assert.fail("Journal table creation failed");
            }
            waitForCacheExpiration(2);
        }
        Assert.fail("Journal table did not become active in time");
    }
}
