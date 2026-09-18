package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.TestBase;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDataPipelineBasic;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.CreateBucketConfiguration;
import com.aliyun.sdk.service.oss2.models.DeleteBucketRequest;
import com.aliyun.sdk.service.oss2.models.PutBucketRequest;
import com.aliyun.sdk.service.oss2.models.PutBucketResult;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;


/**
 * Integration tests for Data Pipeline CRUD operations via OSSDataProcessClient.
 */
public class ClientDataPipelineTest extends TestBaseDataProcess {

    @Test
    public void testDataPipelineLifecycle() {
        OSSDataProcessClient client = getDataClient();
        String pipelineName = "test-pipeline-" + System.currentTimeMillis();
        String roleName = roleName();

        // 1. Put Data Pipeline Configuration
        DataPipelineSourceFilterConfiguration filterConfig = DataPipelineSourceFilterConfiguration.newBuilder()
                .prefixSet(Arrays.asList("prefix1/", "prefix2/prefix3/"))
                .objectMediaTypes(Arrays.asList("text", "image", "video"))
                .build();

        DataPipelineSource source = DataPipelineSource.newBuilder()
                .inputBucket(bucketName)
                .inputDataScope("All")
                .ignoreDelete(true)
                .filterConfiguration(filterConfig)
                .build();

        DataPipelineEmbeddingConfiguration embeddingConfig = DataPipelineEmbeddingConfiguration.newBuilder()
                .embeddingProvider("bailian")
                .apiKey(apiKey())
                .model(modelType())
                .fps(1.0f)
                .build();


        OSSVectorsClient vectorsClient = getVectorsClient();
        String vectorBucketName = "test-vector-" + System.currentTimeMillis() + "-" +
                (int) (Math.random() * 10000);


        // Put vector bucket
        PutVectorBucketResult putVectorResult = vectorsClient.putVectorBucket(PutVectorBucketRequest.newBuilder()
                .bucket(vectorBucketName)
                .build());
        Assert.assertNotNull(putVectorResult);
        Assert.assertEquals(200, putVectorResult.statusCode());

        // 2. Put a vector index
        String indexName = "testIndexForIntegration";
        int dimension = Integer.parseInt(dimension());
        String distanceMetric = "cosine";
        String dataType = "float32";

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("nonFilterableMetadataKeys", new String[]{"key1", "key2"});

        PutVectorIndexResult putVectorIndexResult = vectorsClient.putVectorIndex(
                PutVectorIndexRequest.newBuilder()
                        .bucket(vectorBucketName)
                        .dataType(dataType)
                        .dimension(dimension)
                        .distanceMetric(distanceMetric)
                        .indexName(indexName)
                        .metadata(metadata)
                        .build());

        // Assert successful creation
        Assert.assertNotNull(putVectorIndexResult);
        Assert.assertEquals(200, putVectorIndexResult.statusCode());

        DataPipelineDestination destination = DataPipelineDestination.newBuilder()
                .vectorBucketName(vectorBucketName)
                .vectorKeyPrefix("")
                .vectorIndexNames(Collections.singletonList(indexName))
                .objectTagToMetadata(Arrays.asList("key1", "key2"))
                .usermetaToMetadata(Collections.singletonList("x-oss-meta-key1"))
                .build();

        String errorBucketName = genBucketName() + "-error-test";

        // Create a error bucket
        OSSClient ossClient = getDefaultClient();
        PutBucketResult putBucketResult = ossClient.putBucket(
                PutBucketRequest.newBuilder()
                        .bucket(errorBucketName)
                        .createBucketConfiguration(CreateBucketConfiguration.newBuilder()
                                .storageClass("Standard")
                                .build())
                        .build());
        Assert.assertNotNull(putBucketResult);
        Assert.assertEquals(200, putBucketResult.statusCode());
        waitForCacheExpiration(1);

        DataPipelineError errorConfig = DataPipelineError.newBuilder()
                .errorMode("ignoreAndRecord")
                .errorBucket(errorBucketName)
                .errorPrefix("error-output/")
                .build();

        PutDataPipelineConfigurationConfiguration config = PutDataPipelineConfigurationConfiguration.newBuilder()
                .dataPipelineDescription("使用百炼多模态模型为业务数据向量化")
                .sources(Collections.singletonList(source))
                .dataPipelineEmbeddingConfiguration(embeddingConfig)
                .destination(destination)
                .dataPipelineError(errorConfig)
                .build();

        PutDataPipelineConfigurationResult putResult = client.putDataPipelineConfiguration(
                PutDataPipelineConfigurationRequest.newBuilder()
                        .dataPipelineName(pipelineName)
                        .role(roleName)
                        .putDataPipelineConfigurationConfiguration(config)
                        .build());

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        try {
            // 2. Get Data Pipeline Configuration
            GetDataPipelineConfigurationResult getResult = client.getDataPipelineConfiguration(
                    GetDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.dataPipelineConfiguration());
            Assert.assertNotNull(getResult.dataPipelineConfiguration().status());
            Assert.assertNotNull(getResult.dataPipelineConfiguration().phase());
            Assert.assertEquals(Boolean.TRUE,
                    getResult.dataPipelineConfiguration().sources().get(0).ignoreDelete());

            // 3. List Data Pipeline Configurations
            ListDataPipelineConfigurationsResult listResult = client.listDataPipelineConfigurations(
                    ListDataPipelineConfigurationsRequest.newBuilder()
                            .maxResults(0)
                            .prefix(pipelineName)
                            .inputBucket(bucketName)
                            .build());

            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.dataPipelineConfigurations());
            DataPipelineConfiguration listedConfiguration = listResult.dataPipelineConfigurations().stream()
                    .filter(item -> pipelineName.equals(item.dataPipelineName()))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(listedConfiguration);
            Assert.assertNotNull(listedConfiguration.status());
            Assert.assertNotNull(listedConfiguration.phase());
            Assert.assertEquals(Boolean.TRUE, listedConfiguration.sources().get(0).ignoreDelete());

            // 4. Pause Data Pipeline
            PauseDataPipelineResult pauseResult = client.pauseDataPipeline(
                    PauseDataPipelineRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .build());

            Assert.assertNotNull(pauseResult);
            Assert.assertEquals(200, pauseResult.statusCode());

            // 5. Restart Data Pipeline
            RestartDataPipelineResult restartResult = client.restartDataPipeline(
                    RestartDataPipelineRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .build());

            Assert.assertNotNull(restartResult);
            Assert.assertEquals(200, restartResult.statusCode());

            // 6. Delete Data Pipeline Configuration
            DeleteDataPipelineConfigurationResult deleteResult = client.deleteDataPipelineConfiguration(
                    DeleteDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertTrue("Expected 200 or 204 for delete",
                    deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);

        } finally {
            // Ensure cleanup
            try {
                client.deleteDataPipelineConfiguration(DeleteDataPipelineConfigurationRequest.newBuilder()
                        .dataPipelineName(pipelineName)
                        .build());
            } catch (Exception ignored) {
            }

            DeleteVectorIndexResult deleteResult = vectorsClient.deleteVectorIndex(
                    DeleteVectorIndexRequest.newBuilder()
                            .bucket(vectorBucketName)
                            .indexName(indexName)
                            .build());

            // Assert successful deletion (Delete operations often return 204 No Content)
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());

            try {
                vectorsClient.deleteVectorBucket(
                        DeleteVectorBucketRequest.newBuilder()
                                .bucket(vectorBucketName)
                                .build());
            } catch (Exception ignored) {
            }

            try {
                ossClient.deleteBucket(DeleteBucketRequest.newBuilder()
                        .bucket(errorBucketName)
                        .build());
            } catch (Exception ignored) {
            }

        }
    }


    @Test
    public void testV2DataPipelineLifecycleAndValidation() {
        OSSDataProcessClient client = getDataClient();
        OSSVectorsClient vectorsClient = getVectorsClient();
        String suffix = String.valueOf(System.currentTimeMillis());
        String pipelineName = "test-v2-" + suffix;
        String invalidPipelineName = "test-v2-invalid-" + suffix;
        String vectorBucketName = "test-v2-vector-" + suffix + "-" + (int) (Math.random() * 10000);
        String indexName = "video-frame-" + suffix;

        vectorsClient.putVectorBucket(PutVectorBucketRequest.newBuilder()
                .bucket(vectorBucketName)
                .build());
        vectorsClient.putVectorIndex(PutVectorIndexRequest.newBuilder()
                .bucket(vectorBucketName)
                .indexName(indexName)
                .dataType("float32")
                .dimension(768)
                .distanceMetric("cosine")
                .metadata(Collections.singletonMap(
                        "nonFilterableMetadataKeys", Collections.singletonList("VideoStreams")))
                .build());

        DataPipelineSource source = DataPipelineSource.newBuilder()
                .inputBucket(bucketName)
                .inputDataScope("All")
                .filterConfiguration(DataPipelineSourceFilterConfiguration.newBuilder()
                        .objectMediaTypes(Collections.singletonList("video"))
                        .build())
                .build();
        DataPipelineInsights insights = DataPipelineInsights.newBuilder()
                .video(DataPipelineInsightsVideo.newBuilder()
                        .frameEmbedding(DataPipelineInsightsFrameEmbedding.newBuilder()
                                .snapshot(DataPipelineInsightsSnapshot.newBuilder()
                                        .mode("interval")
                                        .interval(1.0d)
                                        .build())
                                .build())
                        .build())
                .build();
        DataPipelineDestination destination = DataPipelineDestination.newBuilder()
                .videoFrameEmbedding(DataPipelineDestinationVideoFrameEmbedding.newBuilder()
                        .bucket(vectorBucketName)
                        .indexName(indexName)
                        .prefix("v2")
                        .build())
                .build();
        DataPipelineDataProcessConfiguration processConfiguration =
                DataPipelineDataProcessConfiguration.newBuilder()
                        .searchMode("fast")
                        .insights(insights)
                        .build();
        PutDataPipelineConfigurationConfiguration configuration =
                PutDataPipelineConfigurationConfiguration.newBuilder()
                        .dataPipelineDescription("V2 fast video pipeline")
                        .sources(Collections.singletonList(source))
                        .modelTier("standard")
                        .dataPipelineDataProcessConfiguration(processConfiguration)
                        .destination(destination)
                        .build();

        try {
            PutDataPipelineConfigurationConfiguration multipleSources = configuration.toBuilder()
                    .sources(Arrays.asList(source, source))
                    .build();
            try {
                client.putDataPipelineConfiguration(PutDataPipelineConfigurationRequest.newBuilder()
                        .dataPipelineName(invalidPipelineName)
                        .role(ramRoleArn())
                        .putDataPipelineConfigurationConfiguration(multipleSources)
                        .build());
                Assert.fail("Expected multiple Sources to be rejected");
            } catch (Exception e) {
                ServiceException serviceException = findCause(e, ServiceException.class);
                Assert.assertNotNull("Expected ServiceException", serviceException);
                Assert.assertEquals(400, serviceException.statusCode());
            }

            PutDataPipelineConfigurationResult putResult = client.putDataPipelineConfiguration(
                    PutDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .role(ramRoleArn())
                            .putDataPipelineConfigurationConfiguration(configuration)
                            .build());
            Assert.assertEquals(200, putResult.statusCode());

            GetDataPipelineConfigurationRequest getRequest = GetDataPipelineConfigurationRequest.newBuilder()
                    .dataPipelineName(pipelineName)
                    .build();
            GetDataPipelineConfigurationResult getResult = client.getDataPipelineConfiguration(getRequest);
            Assert.assertEquals(200, getResult.statusCode());
            assertV2Configuration(getResult.dataPipelineConfiguration(), pipelineName, vectorBucketName, indexName);

            OperationInput getInput = SerdeDataPipelineBasic.fromGetDataPipelineConfiguration(getRequest)
                    .toBuilder()
                    .method("GET")
                    .build();
            OperationOutput getOutput = client.invokeOperation(getInput, OperationOptions.defaults());
            GetDataPipelineConfigurationResult getByGetMethod =
                    SerdeDataPipelineBasic.toGetDataPipelineConfiguration(getOutput);
            Assert.assertEquals(200, getByGetMethod.statusCode());
            assertV2Configuration(
                    getByGetMethod.dataPipelineConfiguration(), pipelineName, vectorBucketName, indexName);

            ListDataPipelineConfigurationsRequest listRequest =
                    ListDataPipelineConfigurationsRequest.newBuilder()
                            .maxResults(0)
                            .prefix(pipelineName)
                            .inputBucket(bucketName)
                            .build();
            ListDataPipelineConfigurationsResult listResult =
                    client.listDataPipelineConfigurations(listRequest);
            Assert.assertEquals(200, listResult.statusCode());
            assertV2ListResult(listResult, pipelineName, vectorBucketName, indexName);

            OperationInput listInput = SerdeDataPipelineBasic.fromListDataPipelineConfigurations(listRequest)
                    .toBuilder()
                    .method("GET")
                    .build();
            OperationOutput listOutput = client.invokeOperation(listInput, OperationOptions.defaults());
            ListDataPipelineConfigurationsResult listByGetMethod =
                    SerdeDataPipelineBasic.toListDataPipelineConfigurations(listOutput);
            Assert.assertEquals(200, listByGetMethod.statusCode());
            assertV2ListResult(listByGetMethod, pipelineName, vectorBucketName, indexName);
        } finally {
            for (String name : Arrays.asList(pipelineName, invalidPipelineName)) {
                try {
                    client.deleteDataPipelineConfiguration(DeleteDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(name)
                            .build());
                } catch (Exception ignored) {
                }
            }
            try {
                vectorsClient.deleteVectorIndex(DeleteVectorIndexRequest.newBuilder()
                        .bucket(vectorBucketName)
                        .indexName(indexName)
                        .build());
            } catch (Exception ignored) {
            }
            try {
                vectorsClient.deleteVectorBucket(DeleteVectorBucketRequest.newBuilder()
                        .bucket(vectorBucketName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }

    private static void assertV2ListResult(
            ListDataPipelineConfigurationsResult result,
            String pipelineName,
            String vectorBucketName,
            String indexName) {
        Assert.assertNotNull(result.dataPipelineConfigurations());
        DataPipelineConfiguration configuration = result.dataPipelineConfigurations().stream()
                .filter(item -> pipelineName.equals(item.dataPipelineName()))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(configuration);
        assertV2Configuration(configuration, pipelineName, vectorBucketName, indexName);
    }

    private static void assertV2Configuration(
            DataPipelineConfiguration configuration,
            String pipelineName,
            String vectorBucketName,
            String indexName) {
        Assert.assertNotNull(configuration);
        Assert.assertEquals(pipelineName, configuration.dataPipelineName());
        Assert.assertEquals("standard", configuration.modelTier());
        Assert.assertNotNull(configuration.status());
        Assert.assertNotNull(configuration.phase());
        Assert.assertEquals(1, configuration.sources().size());
        Assert.assertEquals(Boolean.FALSE, configuration.sources().get(0).ignoreDelete());
        Assert.assertEquals("fast", configuration.dataPipelineDataProcessConfiguration().searchMode());
        DataPipelineInsightsSnapshot snapshot = configuration.dataPipelineDataProcessConfiguration()
                .insights().video().frameEmbedding().snapshot();
        Assert.assertEquals("interval", snapshot.mode());
        Assert.assertEquals(Double.valueOf(1.0d), snapshot.interval());
        DataPipelineDestinationVideoFrameEmbedding videoFrame = configuration.destination().videoFrameEmbedding();
        Assert.assertEquals(vectorBucketName, videoFrame.bucket());
        Assert.assertEquals(indexName, videoFrame.indexName());
        Assert.assertEquals("v2", videoFrame.prefix());
        Assert.assertNull(configuration.destination().imageEmbedding());
        Assert.assertNull(configuration.destination().imageTextEmbedding());
        Assert.assertNull(configuration.destination().videoTextEmbedding());
        Assert.assertNull(configuration.destination().documentChunkEmbedding());
    }

    @Test
    public void testGetNonExistentPipelineConfiguration() {
        OSSDataProcessClient client = getDataClient();

        try {
            client.getDataPipelineConfiguration(GetDataPipelineConfigurationRequest.newBuilder()
                    .dataPipelineName("non-existent-pipeline-" + System.currentTimeMillis())
                    .build());
            Assert.fail("Expected ServiceException for non-existent pipeline");
        } catch (Exception e) {
            ServiceException serviceException = findCause(e, ServiceException.class);
            Assert.assertNotNull("Expected ServiceException", serviceException);
            Assert.assertTrue("Expected 404 or 400 status",
                    serviceException.statusCode() == 404 || serviceException.statusCode() == 400);
        }
    }

    @Test
    public void testDeleteNonExistentPipelineConfiguration() {
        OSSDataProcessClient client = getDataClient();

        try {
            DeleteDataPipelineConfigurationResult deleteResult = client.deleteDataPipelineConfiguration(
                    DeleteDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName("non-existent-pipeline-" + System.currentTimeMillis())
                            .build());
            // Delete might return 204 even if not exists
            Assert.assertNotNull(deleteResult);
            Assert.assertTrue("Expected 200, 204 or 404",
                    deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204 || deleteResult.statusCode() == 404);
        } catch (Exception e) {
            // Or throw 404
            ServiceException serviceException = findCause(e, ServiceException.class);
            Assert.assertNotNull("Expected ServiceException", serviceException);
            Assert.assertEquals("Expected 404 status", 404, serviceException.statusCode());
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
