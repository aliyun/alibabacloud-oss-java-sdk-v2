package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

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
                        .workflowParameters(Collections.singletonList(WorkflowParameter.newBuilder().name("ImageInsightEnable").value("True").build()))
                        .description("integration test dataset")
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
            assertThat(getResult.dataset().workflowParameters().workflowParameters().size()).isEqualTo(1);
            assertThat(getResult.dataset().workflowParameters().workflowParameters().get(0).name()).isEqualTo("ImageInsightEnable");
            assertThat(getResult.dataset().workflowParameters().workflowParameters().get(0).value()).isEqualTo("True");


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
                            .description("updated description 1")
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
            assertThat(getAfterUpdate.dataset().description()).isEqualTo("updated description 1");

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
            // Update with DatasetConfig (Language = "en")
            DatasetConfig config = DatasetConfig.newBuilder()
                    .insights(InsightsConfig.newBuilder()
                            .language("en")
                            .build())
                    .build();

            UpdateDatasetResult updateResult = client.updateDataset(
                    UpdateDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .datasetConfig(config)
                            .build());

            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());

            // Verify DatasetConfig is returned correctly
            GetDatasetResult getResult = client.getDataset(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.dataset().datasetConfig());
            Assert.assertNotNull(getResult.dataset().datasetConfig().insights());
            assertThat(getResult.dataset().datasetConfig().insights().language()).isEqualTo("en");
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

    @Test
    public void testCreateDatasetWithDatasetConfig() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .language("ch")
                        .build())
                .build();

        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .datasetConfig(config)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        try {
            // Verify DatasetConfig is returned in get
            GetDatasetResult getResult = client.getDataset(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.dataset().datasetConfig());
            Assert.assertNotNull(getResult.dataset().datasetConfig().insights());
            assertThat(getResult.dataset().datasetConfig().insights().language()).isEqualTo("ch");
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

    @Test
    public void testCreateDatasetWithWorkflowParameters() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        List<WorkflowParameter> workflowParams = Arrays.asList(
                WorkflowParameter.newBuilder()
                        .name("VideoInsightEnable")
                        .value("true")
                        .build()
        );

        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .description("test with workflow parameters")
                        .workflowParameters(workflowParams)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull(createResult.dataset());
        assertThat(createResult.dataset().datasetName()).isEqualTo(dsName);

        try {
            // Verify workflow parameters are returned in get
            GetDatasetResult getResult = client.getDataset(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.dataset());
            Assert.assertNotNull("WorkflowParameters should be returned", getResult.dataset().workflowParameters());

            List<WorkflowParameter> returnedParams = getResult.dataset().workflowParameters().workflowParameters();
            Assert.assertNotNull("WorkflowParameter list should be returned", returnedParams);
            Assert.assertEquals(1, returnedParams.size());
            assertThat(returnedParams.get(0).name()).isEqualTo("VideoInsightEnable");
            assertThat(returnedParams.get(0).value()).isEqualTo("true");
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

    @Test
    public void testUpdateDatasetWithWorkflowParameters() {
        OSSDataProcessClient client = getDataProcessClient();
        String dsName = genDatasetName();

        // Create dataset without workflow parameters
        CreateDatasetResult createResult = client.createDataset(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build());

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        try {
            // Update with workflow parameters
            List<WorkflowParameter> workflowParams = Arrays.asList(
                    WorkflowParameter.newBuilder()
                            .name("VideoInsightEnable")
                            .value("true")
                            .build()
            );

            UpdateDatasetResult updateResult = client.updateDataset(
                    UpdateDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .workflowParameters(workflowParams)
                            .build());

            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());

            // Verify update by getting the dataset
            GetDatasetResult getResult = client.getDataset(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull("WorkflowParameters should be returned after update", getResult.dataset().workflowParameters());

            List<WorkflowParameter> returnedParams = getResult.dataset().workflowParameters().workflowParameters();
            Assert.assertNotNull("WorkflowParameter list should be returned after update", returnedParams);
            Assert.assertEquals(1, returnedParams.size());
            assertThat(returnedParams.get(0).name()).isEqualTo("VideoInsightEnable");
            assertThat(returnedParams.get(0).value()).isEqualTo("true");
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
