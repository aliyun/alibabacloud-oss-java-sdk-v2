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

            // 3. List Data Pipeline Configurations
            ListDataPipelineConfigurationsResult listResult = client.listDataPipelineConfigurations(
                    ListDataPipelineConfigurationsRequest.newBuilder()
                            .build());

            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.dataPipelineConfigurations());
            Assert.assertNotNull(listResult.dataPipelineConfigurations());

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
