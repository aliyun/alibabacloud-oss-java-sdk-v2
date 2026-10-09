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

            // Wait until the asynchronously built index makes the vectors queryable
            waitForVectorsQueryable(vectorsClient, bucketName, TEST_FUSION_INDEX_NAME,
                    TEST_FUSION_VECTOR_FIELD, Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f));

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
            Assert.assertFalse("knn query should hit the written vectors",
                    queryResult.vectors().isEmpty());

            // 5. Query vectors in the fusion index with the retriever (knn leaf, generic Map form)
            Map<String, Object> knnLeaf = new LinkedHashMap<>();
            knnLeaf.put("field", TEST_FUSION_VECTOR_FIELD);
            knnLeaf.put("queryVector", Arrays.asList(0.5f, 0.6f, 0.7f, 0.8f));
            Map<String, Object> retriever = new LinkedHashMap<>();
            retriever.put("knn", knnLeaf);

            QueryVectorsFusionResult retrieverResult = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_INDEX_NAME)
                            .retriever(retriever)
                            .limit(10)
                            .build());

            Assert.assertNotNull(retrieverResult);
            Assert.assertEquals(200, retrieverResult.statusCode());
            Assert.assertNotNull(retrieverResult.vectors());
            Assert.assertFalse("retriever query should hit the written vectors",
                    retrieverResult.vectors().isEmpty());
        } finally {
            cleanupTestResources(vectorsClient, bucketName, TEST_FUSION_INDEX_NAME);
        }
    }

    private static final String TEST_FUSION_PRODUCT_INDEX_NAME = "testFusionProductIndex";
    private static final String TEST_FUSION_TEXT_VECTOR_FIELD = "text_vector";
    private static final String TEST_FUSION_IMAGE_VECTOR_FIELD = "image_vector";
    private static final String TEST_FUSION_TITLE_FIELD = "title";

    @Test
    public void testQueryVectorsFusionDocExamples() {
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
            // 2. Create a fusion index matching the productindex schema from the doc
            createDocFusionIndex(vectorsClient, bucketName, TEST_FUSION_PRODUCT_INDEX_NAME);

            // 3. Put sample vectors
            PutVectorsResult putVectorsResult = vectorsClient.putVectors(
                    PutVectorsRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .vectors(createDocFusionVectors())
                            .build());
            Assert.assertNotNull(putVectorsResult);
            Assert.assertEquals(200, putVectorsResult.statusCode());

            // Wait until the asynchronously built index makes the vectors queryable
            waitForVectorsQueryable(vectorsClient, bucketName, TEST_FUSION_PRODUCT_INDEX_NAME,
                    TEST_FUSION_TEXT_VECTOR_FIELD, Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f));

            // B2. knn with pre-filter
            Knn knnWithFilter = Knn.newBuilder()
                    .field(TEST_FUSION_TEXT_VECTOR_FIELD)
                    .queryVector(Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f))
                    .topK(10)
                    .filter(createDocFilter())
                    .build();
            QueryVectorsFusionResult b2Result = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .knn(knnWithFilter)
                            .limit(20)
                            .returnMetadata(true)
                            .returnMetadataFields(Arrays.asList(TEST_FUSION_TITLE_FIELD, "brand", "price"))
                            .build());
            Assert.assertNotNull(b2Result);
            Assert.assertEquals(200, b2Result.statusCode());
            Assert.assertNotNull(b2Result.vectors());
            Assert.assertFalse("B2 knn+filter should hit fusion-product-1", b2Result.vectors().isEmpty());

            // B3. multi-way knn over text_vector and image_vector
            Knn textKnn = Knn.newBuilder()
                    .field(TEST_FUSION_TEXT_VECTOR_FIELD)
                    .queryVector(Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f))
                    .topK(10)
                    .boost(1.5f)
                    .build();
            Knn imageKnn = Knn.newBuilder()
                    .field(TEST_FUSION_IMAGE_VECTOR_FIELD)
                    .queryVector(Arrays.asList(0.4f, 0.3f, 0.2f, 0.1f))
                    .topK(10)
                    .boost(1.0f)
                    .build();
            QueryVectorsFusionResult b3Result = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .knn(Arrays.asList(textKnn, imageKnn))
                            .sort(Arrays.asList(new HashMap<String, Object>() {{
                                put("_score", new HashMap<String, Object>() {{ put("order", "desc"); }});
                            }}))
                            .limit(10)
                            .returnMetadata(true)
                            .returnMetadataFields(Arrays.asList(TEST_FUSION_TITLE_FIELD, "brand"))
                            .build());
            Assert.assertNotNull(b3Result);
            Assert.assertEquals(200, b3Result.statusCode());
            Assert.assertNotNull(b3Result.vectors());
            Assert.assertFalse("B3 multi-way knn should hit written vectors", b3Result.vectors().isEmpty());

            // B4. pure scalar query
            QueryVectorsFusionResult b4Result = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .query(createDocScalarQuery())
                            .sort(Arrays.asList(new HashMap<String, Object>() {{
                                put("price", new HashMap<String, Object>() {{ put("order", "asc"); }});
                            }}))
                            .limit(20)
                            .returnMetadata(true)
                            .returnMetadataFields(Arrays.asList(TEST_FUSION_TITLE_FIELD, "brand", "price", "stock"))
                            .build());
            Assert.assertNotNull(b4Result);
            Assert.assertEquals(200, b4Result.statusCode());
            Assert.assertNotNull(b4Result.vectors());
            Assert.assertFalse("B4 scalar query should hit fusion-product-2", b4Result.vectors().isEmpty());

            // B5. full-text search with $textMatch
            QueryVectorsFusionResult b5Result = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .query(createTextMatchQuery(TEST_FUSION_TITLE_FIELD, "headphones", null))
                            .limit(10)
                            .returnMetadata(true)
                            .returnMetadataFields(Arrays.asList(TEST_FUSION_TITLE_FIELD, "brand"))
                            .build());
            Assert.assertNotNull(b5Result);
            Assert.assertEquals(200, b5Result.statusCode());
            Assert.assertNotNull(b5Result.vectors());
            Assert.assertFalse("B5 text-match should hit fusion-product-1", b5Result.vectors().isEmpty());

            // B16. RRF fusion of knn and text-match retrievers
            RrfRetriever rrfRetriever = RrfRetriever.newBuilder()
                    .k(50)
                    .windowSize(100)
                    .retrievers(Arrays.asList(
                            RetrieverComponent.newBuilder()
                                    .retriever(Knn.newBuilder()
                                                    .field(TEST_FUSION_TEXT_VECTOR_FIELD)
                                                    .queryVector(Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f))
                                                    .topK(10)
                                                    .build())
                                    .weight(2.0f)
                                    .build(),
                            RetrieverComponent.newBuilder()
                                    .retriever(SimpleRetriever.newBuilder()
                                                    .query(createTextMatchQuery(TEST_FUSION_TITLE_FIELD, "headphones", null))
                                                    .build())
                                    .weight(0.5f)
                                    .build()))
                    .build();
            QueryVectorsFusionResult b16Result = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .retriever(rrfRetriever)
                            .limit(10)
                            .returnMetadata(true)
                            .returnMetadataFields(Arrays.asList(TEST_FUSION_TITLE_FIELD, "brand"))
                            .build());
            Assert.assertNotNull(b16Result);
            Assert.assertEquals(200, b16Result.statusCode());
            Assert.assertNotNull(b16Result.vectors());
            Assert.assertFalse("B16 RRF retriever should hit written vectors", b16Result.vectors().isEmpty());

            // B17. Weight fusion with minMax normalizer
            WeightRetriever weightRetriever = WeightRetriever.newBuilder()
                    .windowSize(100)
                    .retrievers(Arrays.asList(
                            RetrieverComponent.newBuilder()
                                    .retriever(Knn.newBuilder()
                                                    .field(TEST_FUSION_TEXT_VECTOR_FIELD)
                                                    .queryVector(Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f))
                                                    .topK(10)
                                                    .build())
                                    .weight(0.7f)
                                    .normalizer("minMax")
                                    .build(),
                            RetrieverComponent.newBuilder()
                                    .retriever(SimpleRetriever.newBuilder()
                                                    .query(createTextMatchQuery(TEST_FUSION_TITLE_FIELD, "headphones", null))
                                                    .build())
                                    .weight(0.3f)
                                    .normalizer("minMax")
                                    .build()))
                    .build();
            QueryVectorsFusionResult b17Result = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .retriever(weightRetriever)
                            .limit(10)
                            .returnMetadata(true)
                            .returnMetadataFields(Arrays.asList(TEST_FUSION_TITLE_FIELD, "brand"))
                            .build());
            Assert.assertNotNull(b17Result);
            Assert.assertEquals(200, b17Result.statusCode());
            Assert.assertNotNull(b17Result.vectors());
            Assert.assertFalse("B17 weight retriever should hit written vectors", b17Result.vectors().isEmpty());

            // B18. Three-way weight fusion over text_vector, image_vector and title text-match
            WeightRetriever threeWayWeightRetriever = WeightRetriever.newBuilder()
                    .windowSize(100)
                    .retrievers(Arrays.asList(
                            RetrieverComponent.newBuilder()
                                    .retriever(Knn.newBuilder()
                                                    .field(TEST_FUSION_TEXT_VECTOR_FIELD)
                                                    .queryVector(Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f))
                                                    .topK(10)
                                                    .build())
                                    .weight(0.5f)
                                    .normalizer("l2")
                                    .build(),
                            RetrieverComponent.newBuilder()
                                    .retriever(Knn.newBuilder()
                                                    .field(TEST_FUSION_IMAGE_VECTOR_FIELD)
                                                    .queryVector(Arrays.asList(0.4f, 0.3f, 0.2f, 0.1f))
                                                    .topK(10)
                                                    .build())
                                    .weight(0.3f)
                                    .normalizer("l2")
                                    .build(),
                            RetrieverComponent.newBuilder()
                                    .retriever(SimpleRetriever.newBuilder()
                                                    .query(createTextMatchQuery(TEST_FUSION_TITLE_FIELD, "sports car", null))
                                                    .build())
                                    .weight(0.2f)
                                    .normalizer("minMax")
                                    .build()))
                    .build();
            QueryVectorsFusionResult b18Result = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .retriever(threeWayWeightRetriever)
                            .limit(10)
                            .returnMetadata(true)
                            .returnMetadataFields(Arrays.asList(TEST_FUSION_TITLE_FIELD, "brand"))
                            .build());
            Assert.assertNotNull(b18Result);
            Assert.assertEquals(200, b18Result.statusCode());
            Assert.assertNotNull(b18Result.vectors());
            Assert.assertFalse("B18 three-way weight retriever should hit written vectors", b18Result.vectors().isEmpty());

            // B19. knn and query coexist
            QueryVectorsFusionResult b19Result = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .knn(Arrays.asList(
                                    Knn.newBuilder()
                                            .field(TEST_FUSION_TEXT_VECTOR_FIELD)
                                            .queryVector(Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f))
                                            .topK(10)
                                            .boost(2.0f)
                                            .build(),
                                    Knn.newBuilder()
                                            .field(TEST_FUSION_IMAGE_VECTOR_FIELD)
                                            .queryVector(Arrays.asList(0.4f, 0.3f, 0.2f, 0.1f))
                                            .topK(10)
                                            .boost(0.5f)
                                            .build()))
                            .query(createDocOrQuery())
                            .sort(Arrays.asList(new HashMap<String, Object>() {{
                                put("_score", new HashMap<String, Object>() {{ put("order", "desc"); }});
                            }}))
                            .limit(20)
                            .returnMetadata(true)
                            .returnMetadataFields(Arrays.asList(TEST_FUSION_TITLE_FIELD, "brand", "price"))
                            .build());
            Assert.assertNotNull(b19Result);
            Assert.assertEquals(200, b19Result.statusCode());
            Assert.assertNotNull(b19Result.vectors());
            Assert.assertFalse("B19 knn+query should hit written vectors", b19Result.vectors().isEmpty());

            // B20. single leaf retriever used directly as top-level retriever
            SimpleRetriever simpleLeaf = SimpleRetriever.newBuilder()
                    .query(createTextMatchQuery(TEST_FUSION_TITLE_FIELD, "headphones", 2.0f))
                    .build();
            QueryVectorsFusionResult b20SimpleResult = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .retriever(simpleLeaf)
                            .limit(10)
                            .build());
            Assert.assertNotNull(b20SimpleResult);
            Assert.assertEquals(200, b20SimpleResult.statusCode());
            Assert.assertNotNull(b20SimpleResult.vectors());
            Assert.assertFalse("B20 simple retriever should hit fusion-product-1", b20SimpleResult.vectors().isEmpty());

            Map<String, Object> knnLeafMap = new LinkedHashMap<>();
            knnLeafMap.put("field", TEST_FUSION_TEXT_VECTOR_FIELD);
            knnLeafMap.put("queryVector", Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f));
            knnLeafMap.put("topK", 10);
            Map<String, Object> knnLeafRetriever = new LinkedHashMap<>();
            knnLeafRetriever.put("knn", knnLeafMap);
            QueryVectorsFusionResult b20KnnResult = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .retriever(knnLeafRetriever)
                            .limit(10)
                            .build());
            Assert.assertNotNull(b20KnnResult);
            Assert.assertEquals(200, b20KnnResult.statusCode());
            Assert.assertNotNull(b20KnnResult.vectors());
            Assert.assertFalse("B20 knn leaf retriever should hit written vectors", b20KnnResult.vectors().isEmpty());

            // Verify the generic Map form of knn, which passes through attributes verbatim
            Map<String, Object> genericKnn = new HashMap<>();
            genericKnn.put("field", TEST_FUSION_TEXT_VECTOR_FIELD);
            genericKnn.put("queryVector", Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f));
            genericKnn.put("topK", 10);
            QueryVectorsFusionResult genericKnnResult = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .knn(Arrays.asList(genericKnn))
                            .limit(10)
                            .build());
            Assert.assertNotNull(genericKnnResult);
            Assert.assertEquals(200, genericKnnResult.statusCode());
            Assert.assertNotNull(genericKnnResult.vectors());
            Assert.assertFalse("generic knn map form should hit written vectors", genericKnnResult.vectors().isEmpty());

            // Verify the generic Map form of the retriever
            Map<String, Object> leafKnn = new HashMap<>();
            leafKnn.put("field", TEST_FUSION_TEXT_VECTOR_FIELD);
            leafKnn.put("queryVector", Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f));
            leafKnn.put("topK", 10);
            Map<String, Object> genericRetriever = new HashMap<>();
            genericRetriever.put("knn", leafKnn);
            QueryVectorsFusionResult genericRetrieverResult = vectorsClient.queryVectorsFusion(
                    QueryVectorsFusionRequest.newBuilder()
                            .bucket(bucketName)
                            .indexName(TEST_FUSION_PRODUCT_INDEX_NAME)
                            .retriever(genericRetriever)
                            .limit(10)
                            .build());
            Assert.assertNotNull(genericRetrieverResult);
            Assert.assertEquals(200, genericRetrieverResult.statusCode());
            Assert.assertNotNull(genericRetrieverResult.vectors());
            Assert.assertFalse("generic retriever map form should hit written vectors", genericRetrieverResult.vectors().isEmpty());

        } finally {
            cleanupTestResources(vectorsClient, bucketName, TEST_FUSION_PRODUCT_INDEX_NAME);
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
                        .mode("fusion")
                        .schemaConfiguration(SchemaConfiguration.newBuilder().fields(fields).build())
                        .build());
        Assert.assertNotNull(putIndexResult);
        Assert.assertEquals(200, putIndexResult.statusCode());
    }

    /**
     * Vector index building is asynchronous: after PutVectors the data is not immediately
     * queryable by knn / full-text / scalar search. Poll a simple knn query until at least one
     * result is returned (or the timeout elapses) so subsequent assertions verify real hits.
     */
    private void waitForVectorsQueryable(OSSVectorsClient client, String bucketName, String indexName,
                                         String vectorField, List<Float> queryVector) {
        long deadline = System.currentTimeMillis() + 120_000L;
        while (System.currentTimeMillis() < deadline) {
            try {
                QueryVectorsFusionResult probe = client.queryVectorsFusion(
                        QueryVectorsFusionRequest.newBuilder()
                                .bucket(bucketName)
                                .indexName(indexName)
                                .knn(Knn.newBuilder()
                                        .field(vectorField)
                                        .queryVector(queryVector)
                                        .topK(10)
                                        .build())
                                .limit(10)
                                .build());
                if (probe != null && probe.vectors() != null && !probe.vectors().isEmpty()) {
                    return;
                }
            } catch (Exception e) {
                // Index may not be ready yet, keep polling until the deadline.
            }
            try {
                Thread.sleep(5_000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
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
            e.printStackTrace();
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
            e.printStackTrace();
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

    private void createDocFusionIndex(OSSVectorsClient client, String bucketName, String indexName) {
        List<FieldSchema> fields = Arrays.asList(
                FieldSchema.newBuilder()
                        .name(TEST_FUSION_TEXT_VECTOR_FIELD)
                        .type("vector")
                        .dataType(TEST_DATA_TYPE)
                        .dimension(TEST_DIMENSION)
                        .distanceMetric(TEST_DISTANCE_METRIC)
                        .build(),
                FieldSchema.newBuilder()
                        .name(TEST_FUSION_IMAGE_VECTOR_FIELD)
                        .type("vector")
                        .dataType(TEST_DATA_TYPE)
                        .dimension(TEST_DIMENSION)
                        .distanceMetric(TEST_DISTANCE_METRIC)
                        .build(),
                FieldSchema.newBuilder()
                        .name(TEST_FUSION_TITLE_FIELD)
                        .type("string")
                        .exactMatch(true)
                        .text(TextSchema.newBuilder()
                                .enabled(true)
                                .analyzer("standard")
                                .build())
                        .build(),
                FieldSchema.newBuilder()
                        .name("brand")
                        .type("string")
                        .build(),
                FieldSchema.newBuilder()
                        .name("category")
                        .type("string")
                        .isArray(true)
                        .build(),
                FieldSchema.newBuilder()
                        .name("price")
                        .type("double")
                        .build(),
                FieldSchema.newBuilder()
                        .name("stock")
                        .type("long")
                        .build(),
                FieldSchema.newBuilder()
                        .name("on_sale")
                        .type("bool")
                        .build());

        PutVectorIndexFusionResult putIndexResult = client.putVectorIndexFusion(
                PutVectorIndexFusionRequest.newBuilder()
                        .bucket(bucketName)
                        .indexName(indexName)
                        .mode("fusion")
                        .schemaConfiguration(SchemaConfiguration.newBuilder().fields(fields).build())
                        .build());
        Assert.assertNotNull(putIndexResult);
        Assert.assertEquals(200, putIndexResult.statusCode());
    }

    private List<Map<String, Object>> createDocFusionVectors() {
        List<Map<String, Object>> vectors = new ArrayList<>();

        // First vector: matches B2/B5/B16/B17/B19/B20
        Map<String, Object> data1 = new HashMap<>();
        data1.put(TEST_FUSION_TEXT_VECTOR_FIELD, Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f));
        data1.put(TEST_FUSION_IMAGE_VECTOR_FIELD, Arrays.asList(0.4f, 0.3f, 0.2f, 0.1f));

        Map<String, Object> metadata1 = new HashMap<>();
        metadata1.put(TEST_FUSION_TITLE_FIELD, "Wireless headphones");
        metadata1.put("brand", "AliBrand");
        metadata1.put("category", Arrays.asList("phone"));
        metadata1.put("price", 199.0);
        metadata1.put("stock", 10L);
        metadata1.put("on_sale", true);

        Map<String, Object> vector1 = new HashMap<>();
        vector1.put("data", data1);
        vector1.put("key", "fusion-product-1");
        vector1.put("metadata", metadata1);
        vectors.add(vector1);

        // Second vector: matches B4/B18/B19
        Map<String, Object> data2 = new HashMap<>();
        data2.put(TEST_FUSION_TEXT_VECTOR_FIELD, Arrays.asList(0.5f, 0.6f, 0.7f, 0.8f));
        data2.put(TEST_FUSION_IMAGE_VECTOR_FIELD, Arrays.asList(0.8f, 0.7f, 0.6f, 0.5f));

        Map<String, Object> metadata2 = new HashMap<>();
        metadata2.put(TEST_FUSION_TITLE_FIELD, "Sports car");
        metadata2.put("brand", "NovaBrand");
        metadata2.put("category", Arrays.asList("phone", "tablet"));
        metadata2.put("price", 1999.0);
        metadata2.put("stock", 5L);
        metadata2.put("on_sale", true);

        Map<String, Object> vector2 = new HashMap<>();
        vector2.put("data", data2);
        vector2.put("key", "fusion-product-2");
        vector2.put("metadata", metadata2);
        vectors.add(vector2);

        return vectors;
    }

    private Map<String, Object> createDocFilter() {
        Map<String, Object> brandIn = new HashMap<>();
        brandIn.put("value", Arrays.asList("AliBrand", "NovaBrand"));
        Map<String, Object> brandCondition = new HashMap<>();
        brandCondition.put("$in", brandIn);
        Map<String, Object> brandClause = new HashMap<>();
        brandClause.put("brand", brandCondition);

        Map<String, Object> priceRange = new HashMap<>();
        priceRange.put("gte", 100);
        priceRange.put("lt", 999);
        Map<String, Object> priceCondition = new HashMap<>();
        priceCondition.put("$range", priceRange);
        Map<String, Object> priceClause = new HashMap<>();
        priceClause.put("price", priceCondition);

        Map<String, Object> onSaleEq = new HashMap<>();
        onSaleEq.put("$eq", true);
        Map<String, Object> onSaleClause = new HashMap<>();
        onSaleClause.put("on_sale", onSaleEq);

        Map<String, Object> andNode = new HashMap<>();
        andNode.put("clauses", Arrays.asList(brandClause, priceClause, onSaleClause));
        Map<String, Object> filter = new HashMap<>();
        filter.put("$and", andNode);
        return filter;
    }

    private Map<String, Object> createDocScalarQuery() {
        Map<String, Object> categoryIn = new HashMap<>();
        categoryIn.put("value", Arrays.asList("phone", "tablet"));
        Map<String, Object> categoryCondition = new HashMap<>();
        categoryCondition.put("$in", categoryIn);
        Map<String, Object> categoryClause = new HashMap<>();
        categoryClause.put("category", categoryCondition);

        Map<String, Object> priceRange = new HashMap<>();
        priceRange.put("gte", 1000);
        priceRange.put("lte", 5000);
        Map<String, Object> priceCondition = new HashMap<>();
        priceCondition.put("$range", priceRange);
        Map<String, Object> priceClause = new HashMap<>();
        priceClause.put("price", priceCondition);

        Map<String, Object> stockGt = new HashMap<>();
        stockGt.put("$gt", 0);
        Map<String, Object> stockClause = new HashMap<>();
        stockClause.put("stock", stockGt);

        Map<String, Object> andNode = new HashMap<>();
        andNode.put("clauses", Arrays.asList(categoryClause, priceClause, stockClause));
        Map<String, Object> query = new HashMap<>();
        query.put("$and", andNode);
        return query;
    }

    private Map<String, Object> createDocOrQuery() {
        Map<String, Object> brandIn = new HashMap<>();
        brandIn.put("value", Arrays.asList("AliBrand"));
        brandIn.put("boost", 1.0f);
        Map<String, Object> brandCondition = new HashMap<>();
        brandCondition.put("$in", brandIn);
        Map<String, Object> brandClause = new HashMap<>();
        brandClause.put("brand", brandCondition);

        Map<String, Object> titleTextMatch = new HashMap<>();
        titleTextMatch.put("value", "car");
        titleTextMatch.put("boost", 4.0f);
        Map<String, Object> titleCondition = new HashMap<>();
        titleCondition.put("$textMatch", titleTextMatch);
        Map<String, Object> titleClause = new HashMap<>();
        titleClause.put(TEST_FUSION_TITLE_FIELD, titleCondition);

        Map<String, Object> orNode = new HashMap<>();
        orNode.put("clauses", Arrays.asList(brandClause, titleClause));
        Map<String, Object> query = new HashMap<>();
        query.put("$or", orNode);
        return query;
    }

    private Map<String, Object> createTextMatchQuery(String field, String value, Float boost) {
        Map<String, Object> textMatch = new HashMap<>();
        textMatch.put("value", value);
        if (boost != null) {
            textMatch.put("boost", boost);
        }
        Map<String, Object> fieldCondition = new HashMap<>();
        fieldCondition.put("$textMatch", textMatch);
        Map<String, Object> query = new HashMap<>();
        query.put(field, fieldCondition);
        return query;
    }

}