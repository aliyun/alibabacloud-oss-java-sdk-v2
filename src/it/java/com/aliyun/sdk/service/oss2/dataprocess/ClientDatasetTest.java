package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for Dataset CRUD operations via OSSDataProcessClient.
 */
public class ClientDatasetTest extends TestBaseDataProcess {

    @Test
    public void testDatasetLifecycle() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        // 1. Create dataset
        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .description("integration test dataset")
                        .datasetMaxFileCount(100L)
                        .datasetMaxBindCount(10L)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull(createResult.dataset());
        assertThat(createResult.dataset().datasetName()).isEqualTo(dsName);

        try {
            // 2. Get dataset
            GetDatasetResult getResult = client.getDataset(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.dataset());
            assertThat(getResult.dataset().datasetName()).isEqualTo(dsName);
            assertThat(getResult.dataset().description()).isEqualTo("integration test dataset");

            // 3. Get dataset with statistics
            GetDatasetResult getWithStatsResult = client.getDataset(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .withStatistics(true)
                            .build());

            Assert.assertNotNull(getWithStatsResult);
            Assert.assertEquals(200, getWithStatsResult.statusCode());
            Assert.assertNotNull(getWithStatsResult.dataset());

            // 4. Update dataset
            UpdateDatasetResult updateResult = client.updateDataset(
                    UpdateDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .description("updated description")
                            .datasetMaxFileCount(200L)
                            .build());

            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());
            Assert.assertNotNull(updateResult.dataset());

            // 5. Verify update by getting again
            GetDatasetResult getAfterUpdate = client.getDataset(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build());

            Assert.assertNotNull(getAfterUpdate);
            Assert.assertEquals(200, getAfterUpdate.statusCode());
            assertThat(getAfterUpdate.dataset().description()).isEqualTo("updated description");

            // 6. Delete dataset
            DeleteDatasetResult deleteResult = client.deleteDataset(
                    DeleteDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertTrue("Expected 200 or 204 for delete",
                    deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);

        } finally {
            // Ensure cleanup
            try {
                client.deleteDataset(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testCreateAndDeleteDataset() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        // Create
        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        // Delete
        DeleteDatasetResult deleteResult = client.deleteDataset(
                DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());

        Assert.assertNotNull(deleteResult);
        Assert.assertTrue("Expected 200 or 204 for delete",
                deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);
    }

    @Test
    public void testGetNonExistentDataset() {
        OSSDataProcessClient client = getDataProcessClient();

        try {
            client.getDataset(GetDatasetRequest.newBuilder()
                    .bucket(testBucketName)
                    .datasetName("non-existent-dataset-" + System.currentTimeMillis())
                    .build());
            Assert.fail("Expected ServiceException for non-existent dataset");
        } catch (Exception e) {
            ServiceException serviceException = findCause(e, ServiceException.class);
            Assert.assertNotNull("Expected ServiceException", serviceException);
            Assert.assertTrue("Expected 404 or 400 status",
                    serviceException.statusCode() == 404 || serviceException.statusCode() == 400);
        }
    }

    @Test
    public void testUpdateDatasetWithConfig() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        // Create dataset first
        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        try {
            // Update with DatasetConfig
            DatasetConfig config = DatasetConfig.newBuilder()
                    .build();

            UpdateDatasetResult updateResult = client.updateDataset(
                    UpdateDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .datasetConfig(config)
                            .datasetMaxEntityCount(500L)
                            .datasetMaxRelationCount(200L)
                            .datasetMaxTotalFileSize(1073741824L)
                            .build());

            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());
        } finally {
            try {
                client.deleteDataset(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }

    private static <T extends Throwable> T findCause(Throwable throwable, Class<T> type) {
        Throwable cause = throwable;
        while (cause != null) {
            if (type.isInstance(cause)) {
                @SuppressWarnings("unchecked")
                T result = (T) cause;
                return result;
            }
            cause = cause.getCause();
        }
        return null;
    }
}
