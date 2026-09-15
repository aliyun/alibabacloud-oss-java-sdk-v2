package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.vectors.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class ClientVectorsTest extends TestBaseVectors {

    private static final String TEST_INDEX_NAME = "testVectorsIndex";
    private static final int TEST_DIMENSION = 4;
    private static final String TEST_DISTANCE_METRIC = "euclidean";
    private static final String TEST_DATA_TYPE = "float32";

    private static final String TEST_FUSION_INDEX_NAME = "testVectorsFusionIndex";
    private static final String TEST_FUSION_VECTOR_FIELD = "vectorField";
    private static final String TEST_FUSION_PARTITION_FIELD = "userId";

    @Test
    public void testPutAndGetVectors() {
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
            // 2. Create a vector index first (prerequisite for vectors)
            createTestVectorIndex(vectorsClient, bucketName, TEST_INDEX_NAME);

            // 3. Put (Insert) vectors into the index
            List<Map<String, Object>> vectorsToInsert = createSampleVectors();
            
            PutVectorsResult putVectorsResult = vectorsClient.putVectors(
                    PutVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_INDEX_NAME)
                            .vectors(vectorsToInsert)
                            .build());

            Assert.assertNotNull(putVectorsResult);
            Assert.assertEquals(200, putVectorsResult.statusCode());

            // 4. Get the inserted vectors by their keys
            List<String> vectorKeys = Arrays.asList("vector-key-1", "vector-key-2");
            
            GetVectorsResult getVectorsResult = vectorsClient.getVectors(
                    GetVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_INDEX_NAME)
                            .keys(vectorKeys)
                            .returnData(true)
                            .returnMetadata(true)
                            .build());

            Assert.assertNotNull(getVectorsResult);
            Assert.assertEquals(200, getVectorsResult.statusCode());
            Assert.assertNotNull(getVectorsResult.vectors());
            Assert.assertEquals(2, getVectorsResult.vectors().size());
        } finally {
            cleanupTestResources(vectorsClient, bucketName, TEST_INDEX_NAME);
        }
    }

    @Test
    public void testListVectors() {
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
            // 2. Create a vector index first (prerequisite for vectors)
            createTestVectorIndex(vectorsClient, bucketName, TEST_INDEX_NAME);

            // 3. Put (Insert) vectors into the index
            List<Map<String, Object>> vectorsToInsert = createSampleVectors();
            
            PutVectorsResult putVectorsResult = vectorsClient.putVectors(
                    PutVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_INDEX_NAME)
                            .vectors(vectorsToInsert)
                            .build());

            Assert.assertNotNull(putVectorsResult);
            Assert.assertEquals(200, putVectorsResult.statusCode());

            // 4. List vectors in the index
            ListVectorsResult listVectorsResult = vectorsClient.listVectors(
                    ListVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_INDEX_NAME)
                            .maxResults(10)
                            .returnData(false) // Not returning data for list operation
                            .returnMetadata(true)
                            .build());

            Assert.assertNotNull(listVectorsResult);
            Assert.assertEquals(200, listVectorsResult.statusCode());
            Assert.assertNotNull(listVectorsResult.vectors());
            Assert.assertEquals(2, listVectorsResult.vectors().size());
        } finally {
            cleanupTestResources(vectorsClient, bucketName, TEST_INDEX_NAME);
        }
    }

    @Test
    public void testDeleteVectors() {
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
            // 2. Create a vector index first (prerequisite for vectors)
            createTestVectorIndex(vectorsClient, bucketName, TEST_INDEX_NAME);

            // 3. Put (Insert) vectors into the index
            List<Map<String, Object>> vectorsToInsert = createSampleVectors();
            
            PutVectorsResult putVectorsResult = vectorsClient.putVectors(
                    PutVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_INDEX_NAME)
                            .vectors(vectorsToInsert)
                            .build());

            Assert.assertNotNull(putVectorsResult);
            Assert.assertEquals(200, putVectorsResult.statusCode());

            // 4. Delete vectors by their keys
            List<String> vectorKeys = Arrays.asList("vector-key-1", "vector-key-2");
            DeleteVectorsResult deleteVectorsResult = vectorsClient.deleteVectors(
                    DeleteVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_INDEX_NAME)
                            .keys(vectorKeys)
                            .build());

            Assert.assertNotNull(deleteVectorsResult);
            Assert.assertEquals(204, deleteVectorsResult.statusCode());

            // 5. Verify vectors are deleted by trying to get them
            GetVectorsResult getVectorsAfterDeleteResult = vectorsClient.getVectors(
                    GetVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_INDEX_NAME)
                            .keys(vectorKeys)
                            .returnData(true)
                            .returnMetadata(true)
                            .build());

            Assert.assertNotNull(getVectorsAfterDeleteResult);
            // Depending on implementation, this might return 200 with empty list or 404
            Assert.assertTrue(
                "Expected either 200 with empty result or 404 for deleted vectors",
                getVectorsAfterDeleteResult.statusCode() == 200 || getVectorsAfterDeleteResult.statusCode() == 404
            );
            
            if (getVectorsAfterDeleteResult.statusCode() == 200) {
                Assert.assertTrue(
                    "Expected empty or smaller vector list after deletion",
                    getVectorsAfterDeleteResult.vectors() == null || getVectorsAfterDeleteResult.vectors().isEmpty() || 
                    getVectorsAfterDeleteResult.vectors().size() < 2
                );
            }
        } finally {
            cleanupTestResources(vectorsClient, bucketName, TEST_INDEX_NAME);
        }
    }

    @Test
    public void testQueryVectorsFusion() {
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
            // 2. Create a fusion vector index with the user defined vector field
            createTestFusionVectorIndex(vectorsClient, bucketName);

            // 3. Put (Insert) vectors, the data uses the user defined vector field name
            PutVectorsResult putVectorsResult = vectorsClient.putVectors(
                    PutVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_INDEX_NAME)
                            .vectors(createSampleFusionVectors())
                            .build());

            Assert.assertNotNull(putVectorsResult);
            Assert.assertEquals(200, putVectorsResult.statusCode());

            // 4. Query vectors in the fusion index with the knn query
            Knn knn = Knn.newBuilder()
                    .field(TEST_FUSION_VECTOR_FIELD)
                    .queryVector(Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f))
                    .topK(10)
                    .build();

            QueryVectorsFusionResult queryResult = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_INDEX_NAME)
                            .knn(Arrays.asList(knn))
                            .limit(10)
                            .returnMetadata(true)
                            .partitionKeys(Arrays.asList("user-1"))
                            .build());

            Assert.assertNotNull(queryResult);
            Assert.assertEquals(200, queryResult.statusCode());
            Assert.assertNotNull(queryResult.vectors());

            // 5. Query vectors in the fusion index with the retriever
            Retriever retriever = Retriever.newBuilder()
                    .knn(Knn.newBuilder()
                            .field(TEST_FUSION_VECTOR_FIELD)
                            .queryVector(Arrays.asList(0.5f, 0.6f, 0.7f, 0.8f))
                            .build())
                    .build();

            QueryVectorsFusionResult retrieverResult = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_INDEX_NAME)
                            .retriever(retriever)
                            .limit(10)
                            .build());

            Assert.assertNotNull(retrieverResult);
            Assert.assertEquals(200, retrieverResult.statusCode());
        } finally {
            cleanupTestResources(vectorsClient, bucketName, TEST_FUSION_INDEX_NAME);
        }
    }

    private void createTestVectorIndex(OSSVectorsClient client, String bucketName, String indexName) {
        PutVectorIndexResult putIndexResult = client.putVectorIndex(
                PutVectorIndexRequest.newBuilder()
                        .bucket(bucketName)
                        .dataType(TEST_DATA_TYPE)
                        .dimension(TEST_DIMENSION)
                        .distanceMetric(TEST_DISTANCE_METRIC)
                        .indexName(indexName)
                        .build());
        Assert.assertNotNull(putIndexResult);
        Assert.assertEquals(200, putIndexResult.statusCode());
    }

    private void createTestFusionVectorIndex(OSSVectorsClient client, String bucketName) {
        List<FieldSchema> fields = Arrays.asList(
                FieldSchema.newBuilder()
                        .name(TEST_FUSION_VECTOR_FIELD)
                        .type("vector")
                        .dataType(TEST_DATA_TYPE)
                        .dimension(TEST_DIMENSION)
                        .distanceMetric(TEST_DISTANCE_METRIC)
                        .build(),
                FieldSchema.newBuilder()
                        .name(TEST_FUSION_PARTITION_FIELD)
                        .type("string")
                        .isPartitionKey(true)
                        .build(),
                FieldSchema.newBuilder()
                        .name("category")
                        .type("string")
                        .exactMatch(true)
                        .build());

        PutVectorIndexFusionResult putIndexResult = client.putVectorIndexFusion(
                PutVectorIndexFusionRequest.newBuilder()
                        .bucket(bucketName)
                        .indexName(TEST_FUSION_INDEX_NAME)
                        .schemaConfiguration(SchemaConfiguration.newBuilder().fields(fields).build())
                        .build());
        Assert.assertNotNull(putIndexResult);
        Assert.assertEquals(200, putIndexResult.statusCode());
    }

    private void cleanupTestResources(OSSVectorsClient client, String bucketName, String indexName) {
        try {
            // Delete the vector index
            client.deleteVectorIndex(
                    DeleteVectorIndexRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(indexName)
                            .build());
        } catch (Exception e) {
            // Ignore exceptions during cleanup
        }
        
        // Delete the test bucket
        try {
            DeleteVectorBucketResult deleteBucketResult = client.deleteVectorBucket(
                    DeleteVectorBucketRequest.newBuilder()
                            .bucket(bucketName)
                            .build());
            Assert.assertNotNull(deleteBucketResult);
            Assert.assertEquals(204, deleteBucketResult.statusCode());
        } catch (Exception e) {
            // Ignore exceptions during cleanup
        }
    }

    private List<Map<String, Object>> createSampleVectors() {
        List<Map<String, Object>> vectors = new ArrayList<>();

        // Create first vector
        Map<String, Object> vectorData1 = new HashMap<>();
        vectorData1.put("float32", Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f));

        Map<String, Object> metadata1 = new HashMap<>();
        metadata1.put("category", "A");
        metadata1.put("source", "test1");

        Map<String, Object> vector1 = new HashMap<>();
        vector1.put("data", vectorData1);
        vector1.put("key", "vector-key-1");
        vector1.put("metadata", metadata1);
        vectors.add(vector1);

        // Create second vector
        Map<String, Object> vectorData2 = new HashMap<>();
        vectorData2.put("float32", Arrays.asList(0.5f, 0.6f, 0.7f, 0.8f));

        Map<String, Object> metadata2 = new HashMap<>();
        metadata2.put("category", "B");
        metadata2.put("source", "test2");

        Map<String, Object> vector2 = new HashMap<>();
        vector2.put("data", vectorData2);
        vector2.put("key", "vector-key-2");
        vector2.put("metadata", metadata2);
        vectors.add(vector2);

        return vectors;
    }

    private List<Map<String, Object>> createSampleFusionVectors() {
        List<Map<String, Object>> vectors = new ArrayList<>();

        // The data of the fusion index uses the user defined vector field name
        Map<String, Object> vectorData1 = new HashMap<>();
        vectorData1.put(TEST_FUSION_VECTOR_FIELD, Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f));

        Map<String, Object> metadata1 = new HashMap<>();
        metadata1.put(TEST_FUSION_PARTITION_FIELD, "user-1");
        metadata1.put("category", "A");

        Map<String, Object> vector1 = new HashMap<>();
        vector1.put("data", vectorData1);
        vector1.put("key", "fusion-vector-key-1");
        vector1.put("metadata", metadata1);
        vectors.add(vector1);

        Map<String, Object> vectorData2 = new HashMap<>();
        vectorData2.put(TEST_FUSION_VECTOR_FIELD, Arrays.asList(0.5f, 0.6f, 0.7f, 0.8f));

        Map<String, Object> metadata2 = new HashMap<>();
        metadata2.put(TEST_FUSION_PARTITION_FIELD, "user-2");
        metadata2.put("category", "B");

        Map<String, Object> vector2 = new HashMap<>();
        vector2.put("data", vectorData2);
        vector2.put("key", "fusion-vector-key-2");
        vector2.put("metadata", metadata2);
        vectors.add(vector2);

        return vectors;
    }
}