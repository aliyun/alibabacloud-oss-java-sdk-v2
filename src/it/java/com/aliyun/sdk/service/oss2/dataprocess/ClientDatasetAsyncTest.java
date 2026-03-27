package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for Dataset CRUD operations via OSSDataProcessClient.
 */
public class ClientDatasetAsyncTest extends TestBaseDataProcess {

    @Test
    public void testDatasetLifecycle() throws Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();
        String dsName = genDatasetName();
        // 1. Create dataset
        CreateDatasetResult createResult = client.createDatasetAsync(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .workflowParameters(Collections.singletonList(WorkflowParameter.newBuilder().name("ImageInsightEnable").value("True").build()))
                        .description("integration test dataset")
                        .build()).get();

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull(createResult.dataset());
        assertThat(createResult.dataset().datasetName()).isEqualTo(dsName);
        assertThat(createResult.dataset().workflowParameters().workflowParameters().size()).isEqualTo(1);
        assertThat(createResult.dataset().workflowParameters().workflowParameters().get(0).name()).isEqualTo("ImageInsightEnable");
        assertThat(createResult.dataset().workflowParameters().workflowParameters().get(0).value()).isEqualTo("True");


        try {
            // 2. Get dataset
            GetDatasetResult getResult = client.getDatasetAsync(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build()).get();

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.dataset());
            assertThat(getResult.dataset().datasetName()).isEqualTo(dsName);
            assertThat(getResult.dataset().description()).isEqualTo("integration test dataset");
            assertThat(getResult.dataset().workflowParameters().workflowParameters().size()).isEqualTo(1);
            assertThat(getResult.dataset().workflowParameters().workflowParameters().get(0).name()).isEqualTo("ImageInsightEnable");
            assertThat(getResult.dataset().workflowParameters().workflowParameters().get(0).value()).isEqualTo("True");


            // 3. Get dataset with statistics
            GetDatasetResult getWithStatsResult = client.getDatasetAsync(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .withStatistics(true)
                            .build()).get();

            Assert.assertNotNull(getWithStatsResult);
            Assert.assertEquals(200, getWithStatsResult.statusCode());
            Assert.assertNotNull(getWithStatsResult.dataset());

            // 4. Update dataset
            UpdateDatasetResult updateResult = client.updateDatasetAsync(
                    UpdateDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .description("updated description 1")
                            .build()).get();

            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());
            Assert.assertNotNull(updateResult.dataset());

            // 5. Verify update by getting again
            GetDatasetResult getAfterUpdate = client.getDatasetAsync(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build()).get();

            Assert.assertNotNull(getAfterUpdate);
            Assert.assertEquals(200, getAfterUpdate.statusCode());
            assertThat(getAfterUpdate.dataset().description()).isEqualTo("updated description 1");

            // 6. Delete dataset
            DeleteDatasetResult deleteResult = client.deleteDatasetAsync(
                    DeleteDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build()).get();

            Assert.assertNotNull(deleteResult);
            Assert.assertTrue("Expected 200 or 204 for delete",
                    deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);

        } catch (Exception e) {
            //TODO

        } finally {
            // Ensure cleanup
            try {
                client.deleteDatasetAsync(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build()).get();
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testCreateAndDeleteDataset() throws Exception{
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();
        String dsName = genDatasetName();

        // Create
        CreateDatasetResult createResult = client.createDatasetAsync(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build()).get();

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        // Delete
        DeleteDatasetResult deleteResult = client.deleteDatasetAsync(
                DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build()).get();

        Assert.assertNotNull(deleteResult);
        Assert.assertTrue("Expected 200 or 204 for delete",
                deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);
    }

    @Test
    public void testGetNonExistentDataset() throws Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();

        try {
            client.getDatasetAsync(GetDatasetRequest.newBuilder()
                    .bucket(testBucketName)
                    .datasetName("non-existent-dataset-" + System.currentTimeMillis())
                    .build()).get();
            Assert.fail("Expected ServiceException for non-existent dataset");
        } catch (Exception e) {
            ServiceException serviceException = findCause(e, ServiceException.class);
            Assert.assertNotNull("Expected ServiceException", serviceException);
            Assert.assertTrue("Expected 404 or 400 status",
                    serviceException.statusCode() == 404 || serviceException.statusCode() == 400);
        }
    }

    @Test
    public void testUpdateDatasetWithConfig() throws Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();
        String dsName = genDatasetName();

        // Create dataset first
        CreateDatasetResult createResult = client.createDatasetAsync(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build()).get();

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        try {
            // Update with DatasetConfig (Language = "en")
            DatasetConfig config = DatasetConfig.newBuilder()
                    .insights(InsightsConfig.newBuilder()
                            .language("en")
                            .build())
                    .build();

            UpdateDatasetResult updateResult = client.updateDatasetAsync(
                    UpdateDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .datasetConfig(config)
                            .build()).get();

            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());

            // Verify DatasetConfig is returned correctly
            GetDatasetResult getResult = client.getDatasetAsync(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build()).get();

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.dataset().datasetConfig());
            Assert.assertNotNull(getResult.dataset().datasetConfig().insights());
            assertThat(getResult.dataset().datasetConfig().insights().language()).isEqualTo("en");
        } finally {
            try {
                client.deleteDatasetAsync(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build()).get();
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testCreateDatasetWithDatasetConfig() throws  Exception{
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();
        String dsName = genDatasetName();

        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .language("ch")
                        .build())
                .build();

        CreateDatasetResult createResult = client.createDatasetAsync(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .datasetConfig(config)
                        .build()).get();

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());

        try {
            // Verify DatasetConfig is returned in get
            GetDatasetResult getResult = client.getDatasetAsync(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build()).get();

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.dataset().datasetConfig());
            Assert.assertNotNull(getResult.dataset().datasetConfig().insights());
            assertThat(getResult.dataset().datasetConfig().insights().language()).isEqualTo("ch");
        } finally {
            try {
                client.deleteDatasetAsync(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build()).get();
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testCreateDatasetWithWorkflowParameters()throws  Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();
        String dsName = genDatasetName();

        List<WorkflowParameter> workflowParams = Arrays.asList(
                WorkflowParameter.newBuilder()
                        .name("VideoInsightEnable")
                        .value("true")
                        .build()
        );

        CreateDatasetResult createResult = client.createDatasetAsync(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .description("test with workflow parameters")
                        .workflowParameters(workflowParams)
                        .build()).get();

        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull(createResult.dataset());
        assertThat(createResult.dataset().datasetName()).isEqualTo(dsName);

        try {
            // Verify workflow parameters are returned in get
            GetDatasetResult getResult = client.getDatasetAsync(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build()).get();

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
                client.deleteDatasetAsync(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build()).get();
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testUpdateDatasetWithWorkflowParameters() throws  Exception{
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();
        String dsName = genDatasetName();

        // Create dataset without workflow parameters
        CreateDatasetResult createResult = client.createDatasetAsync(
                CreateDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build()).get();

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

            UpdateDatasetResult updateResult = client.updateDatasetAsync(
                    UpdateDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .workflowParameters(workflowParams)
                            .build()).get();

            Assert.assertNotNull(updateResult);
            Assert.assertEquals(200, updateResult.statusCode());

            // Verify update by getting the dataset
            GetDatasetResult getResult = client.getDatasetAsync(
                    GetDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(dsName)
                            .build()).get();

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
                client.deleteDatasetAsync(DeleteDatasetRequest.newBuilder()
                        .bucket(testBucketName)
                        .datasetName(dsName)
                        .build()).get();
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testListDatasets() throws Exception {
        OSSDataProcessAsyncClient client = getDataProcessAsyncClient();

        // Use a unique prefix for this test to isolate from other datasets
        String testPrefix = "list-async-" + System.currentTimeMillis() + "-";
        String dsName1 = testPrefix + "a";
        String dsName2 = testPrefix + "b";
        String dsName3 = testPrefix + "c";

        // Create 3 datasets
        for (String name : new String[]{dsName1, dsName2, dsName3}) {
            CreateDatasetResult cr = client.createDatasetAsync(
                    CreateDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(name)
                            .build()).get();
            Assert.assertNotNull(cr);
            Assert.assertEquals("create " + name + " should return 200", 200, cr.statusCode());
        }

        try {
            // 1. List with prefix, verify all 3 datasets are returned
            ListDatasetsResult listAll = client.listDatasetsAsync(
                    ListDatasetsRequest.newBuilder()
                            .bucket(testBucketName)
                            .prefix(testPrefix)
                            .build()).get();

            Assert.assertNotNull(listAll);
            Assert.assertEquals(200, listAll.statusCode());
            Assert.assertNotNull("datasets should not be null", listAll.datasets());
            Assert.assertEquals("should list exactly 3 datasets with prefix", 3, listAll.datasets().size());

            // Collect listed dataset names and verify each one
            java.util.Set<String> listedNames = new java.util.HashSet<>();
            for (Dataset ds : listAll.datasets()) {
                Assert.assertNotNull("dataset name should not be null", ds.datasetName());
                listedNames.add(ds.datasetName());
            }
            Assert.assertTrue("dsName1 should be in list", listedNames.contains(dsName1));
            Assert.assertTrue("dsName2 should be in list", listedNames.contains(dsName2));
            Assert.assertTrue("dsName3 should be in list", listedNames.contains(dsName3));

            // 2. Paginate with maxResults=1, walk through all pages using nextToken
            java.util.Set<String> paginatedNames = new java.util.HashSet<>();
            String nextToken = null;
            int pageCount = 0;

            do {
                ListDatasetsRequest.Builder reqBuilder = ListDatasetsRequest.newBuilder()
                        .bucket(testBucketName)
                        .prefix(testPrefix)
                        .maxResults(1L);
                if (nextToken != null) {
                    reqBuilder.nextToken(nextToken);
                }

                ListDatasetsResult pageResult = client.listDatasetsAsync(reqBuilder.build()).get();
                Assert.assertNotNull(pageResult);
                Assert.assertEquals(200, pageResult.statusCode());
                Assert.assertNotNull(pageResult.datasets());
                Assert.assertEquals("each page should have exactly 1 dataset", 1, pageResult.datasets().size());

                paginatedNames.add(pageResult.datasets().get(0).datasetName());
                nextToken = pageResult.nextToken();
                pageCount++;

                // Safety guard against infinite loop
                Assert.assertTrue("pagination should not exceed 10 pages", pageCount <= 10);
            } while (nextToken != null && !nextToken.isEmpty());

            // Verify pagination walked through all 3 datasets
            Assert.assertEquals("should have paginated through 3 pages", 3, pageCount);
            Assert.assertTrue("paginated dsName1 should be found", paginatedNames.contains(dsName1));
            Assert.assertTrue("paginated dsName2 should be found", paginatedNames.contains(dsName2));
            Assert.assertTrue("paginated dsName3 should be found", paginatedNames.contains(dsName3));

            // 3. Verify nextToken is null/empty when all results fit in one page
            ListDatasetsResult fullPage = client.listDatasetsAsync(
                    ListDatasetsRequest.newBuilder()
                            .bucket(testBucketName)
                            .prefix(testPrefix)
                            .maxResults(100L)
                            .build()).get();
            Assert.assertEquals(200, fullPage.statusCode());
            Assert.assertEquals(3, fullPage.datasets().size());
            Assert.assertTrue("nextToken should be null or empty when all results returned",
                    fullPage.nextToken() == null || fullPage.nextToken().isEmpty());

        } finally {
            // Cleanup all 3 datasets
            for (String name : new String[]{dsName1, dsName2, dsName3}) {
                try {
                    client.deleteDatasetAsync(DeleteDatasetRequest.newBuilder()
                            .bucket(testBucketName)
                            .datasetName(name)
                            .build()).get();
                } catch (Exception ignored) {
                }
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
