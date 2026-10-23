package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.TestBase;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.CreateBucketConfiguration;
import com.aliyun.sdk.service.oss2.models.DeleteBucketRequest;
import com.aliyun.sdk.service.oss2.models.PutBucketRequest;
import com.aliyun.sdk.service.oss2.models.PutBucketResult;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for Data Pipeline CRUD operations via OSSDataProcessAsyncClient.
 */
public class ClientDataPipelineAsyncTest extends TestBaseDataProcess {

    @Test
    public void testDataPipelineLifecycleAsync() throws Exception {
        OSSDataProcessAsyncClient client = getDataAsyncClient();
        OSSVectorsClient vectorsClient = getVectorsClient();
        OSSClient ossClient = getDefaultClient();
        String pipelineName = "test-async-" + System.currentTimeMillis();
        String roleName = roleName();

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

        // Vector bucket name must be shorter than 32 characters.
        String vectorBucketName = "sdk-async-" + (System.currentTimeMillis() % 1000000000L);

        // Put vector bucket
        PutVectorBucketResult putVectorResult = vectorsClient.putVectorBucket(PutVectorBucketRequest.newBuilder()
                .bucket(vectorBucketName)
                .build());
        Assert.assertNotNull(putVectorResult);
        Assert.assertEquals(200, putVectorResult.statusCode());

        String indexName = "testIndexForAsyncIntegration";
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
        Assert.assertNotNull(putVectorIndexResult);
        Assert.assertEquals(200, putVectorIndexResult.statusCode());

        DataPipelineDestination destination = DataPipelineDestination.newBuilder()
                .vectorBucketName(vectorBucketName)
                .vectorKeyPrefix("")
                .vectorIndexNames(Collections.singletonList(indexName))
                .objectTagToMetadata(Arrays.asList("key1", "key2"))
                .usermetaToMetadata(Collections.singletonList("x-oss-meta-key1"))
                .build();

        String errorBucketName = genBucketName() + "-error-test-async";

        // Create a error bucket
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

        PutDataPipelineConfigurationResult putResult = client.putDataPipelineConfigurationAsync(
                PutDataPipelineConfigurationRequest.newBuilder()
                        .dataPipelineName(pipelineName)
                        .role(roleName)
                        .putDataPipelineConfigurationConfiguration(config)
                        .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        try {
            // 2. Get Data Pipeline Configuration
            CompletableFuture<GetDataPipelineConfigurationResult> getFuture = client.getDataPipelineConfigurationAsync(
                    GetDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .build());

            GetDataPipelineConfigurationResult getResult = getFuture.get(120, TimeUnit.SECONDS);
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.dataPipelineConfiguration());
            Assert.assertNotNull(getResult.dataPipelineConfiguration().status());
            Assert.assertNotNull(getResult.dataPipelineConfiguration().phase());
            Assert.assertEquals(Boolean.TRUE,
                    getResult.dataPipelineConfiguration().sources().get(0).ignoreDelete());

            // 3. List Data Pipeline Configurations
            CompletableFuture<ListDataPipelineConfigurationsResult> listFuture = client.listDataPipelineConfigurationsAsync(
                    ListDataPipelineConfigurationsRequest.newBuilder()
                            .maxResults(0)
                            .prefix(pipelineName)
                            .inputBucket(bucketName)
                            .build());

            ListDataPipelineConfigurationsResult listResult = listFuture.get(120, TimeUnit.SECONDS);
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
            CompletableFuture<PauseDataPipelineResult> pauseFuture = client.pauseDataPipelineAsync(
                    PauseDataPipelineRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .build());

            PauseDataPipelineResult pauseResult = pauseFuture.get(120, TimeUnit.SECONDS);
            Assert.assertNotNull(pauseResult);
            Assert.assertEquals(200, pauseResult.statusCode());

            // 5. Restart Data Pipeline
            CompletableFuture<RestartDataPipelineResult> restartFuture = client.restartDataPipelineAsync(
                    RestartDataPipelineRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .build());

            RestartDataPipelineResult restartResult = restartFuture.get(120, TimeUnit.SECONDS);
            Assert.assertNotNull(restartResult);
            Assert.assertEquals(200, restartResult.statusCode());

            // 6. Delete Data Pipeline Configuration
            CompletableFuture<DeleteDataPipelineConfigurationResult> deleteFuture = client.deleteDataPipelineConfigurationAsync(
                    DeleteDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .build());

            DeleteDataPipelineConfigurationResult deleteResult = deleteFuture.get(120, TimeUnit.SECONDS);
            Assert.assertNotNull(deleteResult);
            Assert.assertTrue("Expected 200 or 204 for delete",
                    deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);

        } finally {
            // Ensure cleanup
            try {
                client.deleteDataPipelineConfigurationAsync(DeleteDataPipelineConfigurationRequest.newBuilder()
                        .dataPipelineName(pipelineName)
                        .build()).get(10, TimeUnit.SECONDS);
            } catch (Exception ignored) {
            }

            try {
                vectorsClient.deleteVectorIndex(
                        DeleteVectorIndexRequest.newBuilder()
                                .bucket(vectorBucketName)
                                .indexName(indexName)
                                .build());
            } catch (Exception ignored) {
            }

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
    public void testV2DataPipelineLifecycleAndValidationAsync() throws Exception {
        OSSDataProcessAsyncClient client = getDataAsyncClient();
        OSSVectorsClient vectorsClient = getVectorsClient();
        OSSClient ossClient = getDefaultClient();
        String roleName = ramRoleArn();
        String suffix = String.valueOf(System.currentTimeMillis());
        String pipelineName = "test-v2-async-" + suffix;
        String invalidPipelineName = "test-v2-async-invalid-" + suffix;
        String vectorBucketName = "test-v2-async-vector-" + (int) (Math.random() * 100);
        // indexName only allows letters and digits, must start with a letter.
        String indexName = "videoFrameAsync" + suffix;

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

        // DataPipelineError and its ErrorMode are mandatory.
        String errorBucketName = genBucketName() + "-error-test-async";
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

        DataPipelineSource source = DataPipelineSource.newBuilder()
                .inputBucket(bucketName)
                .inputDataScope("All")
                .filterConfiguration(DataPipelineSourceFilterConfiguration.newBuilder()
                        .objectMediaTypes(Collections.singletonList("video"))
                        .build())
                .build();
        DataPipelineInsights insights = DataPipelineInsights.newBuilder()
                .video(InsightsVideo.newBuilder()
                        .frameEmbedding(InsightsFrameEmbedding.newBuilder()
                                .snapshot(InsightsSnapshot.newBuilder()
                                        .mode("interval")
                                        .interval(1.0d)
                                        .build())
                                .build())
                        .build())
                .build();
        DataPipelineDestination destination = DataPipelineDestination.newBuilder()
                .videoFrameEmbedding(VideoFrameEmbedding.newBuilder()
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
                        .dataPipelineDescription("V2 fast video pipeline async")
                        .sources(Collections.singletonList(source))
                        .modelTier("standard")
                        .dataPipelineDataProcessConfiguration(processConfiguration)
                        .destination(destination)
                        .dataPipelineError(errorConfig)
                        .build();

        try {
            PutDataPipelineConfigurationConfiguration multipleSources = configuration.toBuilder()
                    .sources(Arrays.asList(source, source))
                    .build();
            try {
                client.putDataPipelineConfigurationAsync(PutDataPipelineConfigurationRequest.newBuilder()
                        .dataPipelineName(invalidPipelineName)
                        .role(roleName)
                        .putDataPipelineConfigurationConfiguration(multipleSources)
                        .build()).get(120, TimeUnit.SECONDS);
                Assert.fail("Expected multiple Sources to be rejected");
            } catch (Exception e) {
                ServiceException serviceException = findCause(e, ServiceException.class);
                Assert.assertNotNull("Expected ServiceException", serviceException);
                Assert.assertEquals(400, serviceException.statusCode());
            }

            PutDataPipelineConfigurationResult putResult = client.putDataPipelineConfigurationAsync(
                    PutDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .role(roleName)
                            .putDataPipelineConfigurationConfiguration(configuration)
                            .build()).get(120, TimeUnit.SECONDS);
            Assert.assertEquals(200, putResult.statusCode());

            GetDataPipelineConfigurationResult getResult = client.getDataPipelineConfigurationAsync(
                    GetDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .build()).get(120, TimeUnit.SECONDS);
            Assert.assertEquals(200, getResult.statusCode());
            assertV2Configuration(getResult.dataPipelineConfiguration(), pipelineName, vectorBucketName, indexName);

            ListDataPipelineConfigurationsResult listResult = client.listDataPipelineConfigurationsAsync(
                    ListDataPipelineConfigurationsRequest.newBuilder()
                            .maxResults(0)
                            .prefix(pipelineName)
                            .inputBucket(bucketName)
                            .build()).get(120, TimeUnit.SECONDS);
            Assert.assertEquals(200, listResult.statusCode());
            assertV2ListResult(listResult, pipelineName, vectorBucketName, indexName);
        } finally {
            for (String name : Arrays.asList(pipelineName, invalidPipelineName)) {
                try {
                    client.deleteDataPipelineConfigurationAsync(DeleteDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(name)
                            .build()).get(10, TimeUnit.SECONDS);
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
            try {
                ossClient.deleteBucket(DeleteBucketRequest.newBuilder()
                        .bucket(errorBucketName)
                        .build());
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testGetNonExistentPipelineConfigurationAsync() throws Exception {
        OSSDataProcessAsyncClient client = getDataAsyncClient();

        try {
            client.getDataPipelineConfigurationAsync(GetDataPipelineConfigurationRequest.newBuilder()
                    .dataPipelineName("non-existent-pipeline-async-" + System.currentTimeMillis())
                    .build()).get();
            Assert.fail("Expected ServiceException for non-existent pipeline");
        } catch (Exception e) {
            ServiceException serviceException = findCause(e, ServiceException.class);
            Assert.assertNotNull("Expected ServiceException", serviceException);
            Assert.assertTrue("Expected 404 or 400 status",
                    serviceException.statusCode() == 404 || serviceException.statusCode() == 400);
        }
    }

    @Test
    public void testDeleteNonExistentPipelineConfigurationAsync() throws Exception {
        OSSDataProcessAsyncClient client = getDataAsyncClient();

        try {
            DeleteDataPipelineConfigurationResult deleteResult = client.deleteDataPipelineConfigurationAsync(
                    DeleteDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName("non-existent-pipeline-async-" + System.currentTimeMillis())
                            .build()).get();
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
        InsightsSnapshot snapshot = configuration.dataPipelineDataProcessConfiguration()
                .insights().video().frameEmbedding().snapshot();
        Assert.assertEquals("interval", snapshot.mode());
        Assert.assertEquals(Double.valueOf(1.0d), snapshot.interval());
        Assert.assertNotNull(configuration.dataPipelineError());
        Assert.assertEquals("ignoreAndRecord", configuration.dataPipelineError().errorMode());
        VideoFrameEmbedding videoFrame = configuration.destination().videoFrameEmbedding();
        Assert.assertEquals(vectorBucketName, videoFrame.bucket());
        Assert.assertEquals(indexName, videoFrame.indexName());
        Assert.assertEquals("v2", videoFrame.prefix());
        Assert.assertNull(configuration.destination().imageEmbedding());
        Assert.assertNull(configuration.destination().imageTextEmbedding());
        Assert.assertNull(configuration.destination().videoTextEmbedding());
        Assert.assertNull(configuration.destination().documentChunkEmbedding());
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
