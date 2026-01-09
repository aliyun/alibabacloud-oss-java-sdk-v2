package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;

public class ClientBucketInventoryAsyncTest extends TestBase {

    @Test
    public void testBucketInventoryOperationsAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();
        String bucketName = genBucketName();
        String DestBucketName = "acs:oss:::" + bucketName;

        // Create bucket first
        client.putBucketAsync(PutBucketRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        try {
            // Create bucket destination configuration
            InventoryOSSBucketDestination destination = InventoryOSSBucketDestination.newBuilder()
                    .format("CSV")
                    .accountId(OSS_TEST_RAM_UID)
                    .roleArn(OSS_TEST_RAM_ROLE_ARN)
                    .bucket(DestBucketName)
                    .prefix("prefix1/")
                    .build();

            // Create inventory destination
            InventoryDestination inventoryDestination = InventoryDestination.newBuilder()
                    .oSSBucketDestination(destination)
                    .build();

            // Create schedule configuration
            InventorySchedule schedule = InventorySchedule.newBuilder()
                    .frequency("Daily")
                    .build();

            // Create filter configuration
            InventoryFilter filter = InventoryFilter.newBuilder()
                    .prefix("Pics/")
                    .build();

            // Create optional fields
            List<InventoryOptionalFieldType> fields = Arrays.asList(
                    InventoryOptionalFieldType.SIZE,
                    InventoryOptionalFieldType.LAST_MODIFIED_DATE,
                    InventoryOptionalFieldType.E_TAG,
                    InventoryOptionalFieldType.STORAGE_CLASS,
                    InventoryOptionalFieldType.IS_MULTIPART_UPLOADED,
                    InventoryOptionalFieldType.ENCRYPTION_STATUS
            );
            OptionalFields optionalFields = OptionalFields.newBuilder()
                    .fields(fields)
                    .build();

            // Create inventory configuration
            InventoryConfiguration inventoryConfiguration = InventoryConfiguration.newBuilder()
                    .id("test-inventory-async")
                    .isEnabled(true)
                    .destination(inventoryDestination)
                    .schedule(schedule)
                    .filter(filter)
                    .includedObjectVersions("All")
                    .optionalFields(optionalFields)
                    .build();

            // Test putBucketInventoryAsync
            CompletableFuture<PutBucketInventoryResult> putFuture = client.putBucketInventoryAsync(PutBucketInventoryRequest.newBuilder()
                    .bucket(bucketName)
                    .inventoryId("test-inventory-async")
                    .inventoryConfiguration(inventoryConfiguration)
                    .build());
            PutBucketInventoryResult putResult = putFuture.get();
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(1);

            // Test getBucketInventoryAsync
            CompletableFuture<GetBucketInventoryResult> getFuture = client.getBucketInventoryAsync(GetBucketInventoryRequest.newBuilder()
                    .bucket(bucketName)
                    .inventoryId("test-inventory-async")
                    .build());
            GetBucketInventoryResult getResult = getFuture.get();
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.inventoryConfiguration());
            Assert.assertEquals("test-inventory-async", getResult.inventoryConfiguration().id());
            Assert.assertEquals(true, getResult.inventoryConfiguration().isEnabled());

            // Test listBucketInventoryAsync
            CompletableFuture<ListBucketInventoryResult> listFuture = client.listBucketInventoryAsync(ListBucketInventoryRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
            ListBucketInventoryResult listResult = listFuture.get();
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.listInventoryConfigurationsResult());
            Assert.assertNotNull(listResult.listInventoryConfigurationsResult().inventoryConfigurations());
            Assert.assertTrue(listResult.listInventoryConfigurationsResult().inventoryConfigurations().size() >= 1);

            // Test deleteBucketInventoryAsync
            CompletableFuture<DeleteBucketInventoryResult> deleteFuture = client.deleteBucketInventoryAsync(DeleteBucketInventoryRequest.newBuilder()
                    .bucket(bucketName)
                    .inventoryId("test-inventory-async")
                    .build());
            DeleteBucketInventoryResult deleteResult = deleteFuture.get();
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());

            waitForCacheExpiration(1);

            // Verify deletion by trying to get the inventory again
            try {
                CompletableFuture<GetBucketInventoryResult> getAfterDeleteFuture = client.getBucketInventoryAsync(GetBucketInventoryRequest.newBuilder()
                        .bucket(bucketName)
                        .inventoryId("test-inventory-async")
                        .build());
                getAfterDeleteFuture.get();
            } catch (Exception e) {
                ServiceException serr = findCause(e, ServiceException.class);
                Assert.assertEquals(404, serr.statusCode());
                Assert.assertEquals("NoSuchInventory", serr.errorCode());
                Assert.assertEquals(24, serr.requestId().length());
            }
        } finally {
            // Clean up: delete the bucket
            client.deleteBucketAsync(DeleteBucketRequest.newBuilder()
                    .bucket(bucketName)
                    .build()).get();
        }
    }
}