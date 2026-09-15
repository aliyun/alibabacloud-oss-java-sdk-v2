package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.vectors.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientVectorIndexTest extends TestBaseVectors {

    private static final String TEST_FUSION_INDEX_NAME = "testFusionIndexForIntegration";
    private static final String TEST_FUSION_VECTOR_FIELD = "vectorField";
    private static final String TEST_FUSION_PARTITION_FIELD = "userId";
    private static final String TEST_FUSION_TITLE_FIELD = "title";
    private static final int TEST_FUSION_DIMENSION = 4;
    private static final String TEST_FUSION_DATA_TYPE = "float32";
    private static final String TEST_FUSION_DISTANCE_METRIC = "euclidean";

    @Test
    public void testVectorIndexLifecycle() {
        OSSVectorsClient vectorsClient = getVectorClient();
        String bucketName = genVectorBucketName();

        // 1. Create bucket for testing
        PutVectorBucketResult createBucketResult = vectorsClient.putVectorBucket(
                PutVectorBucketRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
        Assert.assertNotNull(createBucketResult);
        Assert.assertEquals(200, createBucketResult.statusCode());

        try {
            // 2. Put (Create) a vector index
            String indexName = "testIndexForIntegration";
            int dimension = 3;
            String distanceMetric = "cosine";
            String dataType = "float32";

            Map<String, Object> metadata = new HashMap<>();
            metadata.put("nonFilterableMetadataKeys", new String[]{"key1", "key2"});

            PutVectorIndexResult putResult = vectorsClient.putVectorIndex(
                    PutVectorIndexRequest.newBuilder()
                            .bucket(bucketName)
                            .dataType(dataType)
                            .dimension(dimension)
                            .distanceMetric(distanceMetric)
                            .indexName(indexName)
                            .metadata(metadata)
                            .build());

            // Assert successful creation
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            // 3. Get the created vector index
            GetVectorIndexResult getResult = vectorsClient.getVectorIndex(
                    GetVectorIndexRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(indexName)
                            .build());

            // Assert successful retrieval
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.index());

            // Assert retrieved index details match the created ones
            Assert.assertEquals(indexName, getResult.index().indexName());
            Assert.assertEquals(Integer.valueOf(dimension), getResult.index().dimension());
            Assert.assertEquals(distanceMetric, getResult.index().distanceMetric());

            // 4. List vector indexes and verify our index is included
            ListVectorIndexesResult listResult = vectorsClient.listVectorIndexes(
                    ListVectorIndexesRequest.newBuilder()
                            .bucket(bucketName)
                            .build());

            // Assert successful listing
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.indexes());
            Assert.assertEquals(1, listResult.indexes().size());

            // Get our specific index from the list
            IndexSummary foundIndex = listResult.indexes().get(0);
            
            Assert.assertNotNull("Index '" + indexName + "' not found in the list", foundIndex);
            Assert.assertEquals(indexName, foundIndex.indexName());
            Assert.assertEquals(Integer.valueOf(dimension), foundIndex.dimension());
            Assert.assertEquals(distanceMetric, foundIndex.distanceMetric());

            // 5. Delete the vector index
            DeleteVectorIndexResult deleteResult = vectorsClient.deleteVectorIndex(
                    DeleteVectorIndexRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(indexName)
                            .build());

            // Assert successful deletion (Delete operations often return 204 No Content)
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());

        } finally {
            // 6. Cleanup: Delete the test bucket
            DeleteVectorBucketResult deleteBucketResult = vectorsClient.deleteVectorBucket(
                    DeleteVectorBucketRequest.newBuilder()
                            .bucket(bucketName)
                            .build());
            Assert.assertNotNull(deleteBucketResult);
            Assert.assertEquals(204, deleteBucketResult.statusCode());

        }
    }

    @Test
    public void testFusionVectorIndexLifecycle() {
        OSSVectorsClient vectorsClient = getVectorClient();
        String bucketName = genVectorBucketName();

        // 1. Create bucket for testing
        PutVectorBucketResult createBucketResult = vectorsClient.putVectorBucket(
                PutVectorBucketRequest.newBuilder()
                        .bucket(bucketName)
                        .build());
        Assert.assertNotNull(createBucketResult);
        Assert.assertEquals(200, createBucketResult.statusCode());

        try {
            // 2. Put (Create) a fusion vector index with the schema configuration
            List<FieldSchema> fields = Arrays.asList(
                    FieldSchema.newBuilder()
                            .name(TEST_FUSION_VECTOR_FIELD)
                            .type("vector")
                            .dataType(TEST_FUSION_DATA_TYPE)
                            .dimension(TEST_FUSION_DIMENSION)
                            .distanceMetric(TEST_FUSION_DISTANCE_METRIC)
                            .build(),
                    FieldSchema.newBuilder()
                            .name(TEST_FUSION_PARTITION_FIELD)
                            .type("string")
                            .isPartitionKey(true)
                            .build(),
                    FieldSchema.newBuilder()
                            .name(TEST_FUSION_TITLE_FIELD)
                            .type("string")
                            .exactMatch(true)
                            .text(TextSchema.newBuilder()
                                    .enabled(true)
                                    .analyzer("standard")
                                    .analyzerParameters(AnalyzerParameters.newBuilder()
                                            .caseSensitive(false)
                                            .delimitWord(false)
                                            .build())
                                    .build())
                            .build());

            PutVectorIndexFusionResult putResult = vectorsClient.putVectorIndexFusion(
                    PutVectorIndexFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_INDEX_NAME)
                            .mode("fusion")
                            .schemaConfiguration(SchemaConfiguration.newBuilder().fields(fields).build())
                            .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            // 3. Get the created fusion vector index, the mode and the schema configuration are returned
            GetVectorIndexResult getResult = vectorsClient.getVectorIndex(
                    GetVectorIndexRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_INDEX_NAME)
                            .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.index());
            Assert.assertEquals(TEST_FUSION_INDEX_NAME, getResult.index().indexName());
            Assert.assertEquals("fusion", getResult.index().mode());

            SchemaConfiguration returnedSchema = getResult.index().schemaConfiguration();
            Assert.assertNotNull(returnedSchema);
            Assert.assertNotNull(returnedSchema.fields());
            Assert.assertEquals(fields.size(), returnedSchema.fields().size());

            // 4. List vector indexes and verify the mode of our index
            ListVectorIndexesResult listResult = vectorsClient.listVectorIndexes(
                    ListVectorIndexesRequest.newBuilder()
                            .bucket(bucketName)
                            .build());

            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            Assert.assertNotNull(listResult.indexes());
            Assert.assertEquals(1, listResult.indexes().size());

            IndexSummary foundIndex = listResult.indexes().get(0);
            Assert.assertEquals(TEST_FUSION_INDEX_NAME, foundIndex.indexName());
            Assert.assertEquals("fusion", foundIndex.mode());

            // 5. Delete the fusion vector index
            DeleteVectorIndexResult deleteResult = vectorsClient.deleteVectorIndex(
                    DeleteVectorIndexRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_INDEX_NAME)
                            .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());

        } finally {
            // 6. Cleanup: Delete the test bucket
            cleanupFusionTestResources(vectorsClient, bucketName);
        }
    }

    private void cleanupFusionTestResources(OSSVectorsClient client, String bucketName) {
        try {
            client.deleteVectorIndex(
                    DeleteVectorIndexRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_INDEX_NAME)
                            .build());
        } catch (Exception e) {
            // Ignore exceptions during cleanup
        }

        try {
            client.deleteVectorBucket(
                    DeleteVectorBucketRequest.newBuilder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            // Ignore exceptions during cleanup
        }
    }
}