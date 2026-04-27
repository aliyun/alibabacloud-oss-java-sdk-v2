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
        String roleName = "immdatatest";

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

        String vectorBucketName = "test-async-" + System.currentTimeMillis() + "-" +
                (int) (Math.random() * 10000);

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

            // 3. List Data Pipeline Configurations
            CompletableFuture<ListDataPipelineConfigurationsResult> listFuture = client.listDataPipelineConfigurationsAsync(
                    ListDataPipelineConfigurationsRequest.newBuilder()
                            .build());

            ListDataPipelineConfigurationsResult listResult = listFuture.get(120, TimeUnit.SECONDS);
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.dataPipelineConfigurations());
            Assert.assertFalse(listResult.dataPipelineConfigurations().isEmpty());

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
