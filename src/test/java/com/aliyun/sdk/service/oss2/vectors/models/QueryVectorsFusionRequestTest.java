package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryVectorsFusionRequestTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testEmptyBuilder() {
        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.indexName()).isNull();
        assertThat(request.knn()).isNull();
        assertThat(request.query()).isNull();
        assertThat(request.retriever()).isNull();
        assertThat(request.returnMetadata()).isNull();
        assertThat(request.returnMetadataFields()).isNull();
        assertThat(request.partitionKeys()).isNull();
        assertThat(request.limit()).isNull();
        assertThat(request.nextToken()).isNull();
        assertThat(request.sort()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        Knn knn = Knn.newBuilder()
                .field("vector")
                .queryVector(Arrays.asList(0.1f, 0.2f, 0.3f))
                .topK(10)
                .numCandidates(100)
                .boost(1.5f)
                .filter(createFilter())
                .build();

        Map<String, Object> query = new HashMap<>();
        Map<String, Object> textMatch = new HashMap<>();
        textMatch.put("value", "hello world");
        textMatch.put("boost", 2.0f);
        Map<String, Object> titleCondition = new HashMap<>();
        titleCondition.put("$textMatch", textMatch);
        query.put("title", titleCondition);

        Map<String, Object> sortField = new HashMap<>();
        sortField.put("order", "desc");
        Map<String, Object> sortItem = new HashMap<>();
        sortItem.put("timestamps", sortField);
        List<Map<String, Object>> sort = Arrays.asList(sortItem);

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("examplebucket")
                .indexName("fusion-index")
                .knn(Arrays.asList(knn))
                .query(query)
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("title", "timestamps"))
                .partitionKeys(Arrays.asList("user_id_1", "user_id_2"))
                .limit(20)
                .sort(sort)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.indexName()).isEqualTo("fusion-index");
        assertThat(request.knn()).hasSize(1);
        assertThat(request.knn().get(0)).isEqualTo(knn.toMap());
        assertThat(request.query()).isSameAs(query);
        assertThat(request.retriever()).isNull();
        assertThat(request.returnMetadata()).isTrue();
        assertThat(request.returnMetadataFields()).containsExactly("title", "timestamps");
        assertThat(request.partitionKeys()).containsExactly("user_id_1", "user_id_2");
        assertThat(request.limit()).isEqualTo(20);
        assertThat(request.nextToken()).isNull();
        assertThat(request.sort()).isSameAs(sort);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        QueryVectorsFusionRequest original = QueryVectorsFusionRequest.newBuilder()
                .bucket("original-bucket")
                .indexName("original-index")
                .knn(Arrays.asList(Knn.newBuilder()
                        .field("vector")
                        .queryVector(Arrays.asList(0.4f, 0.5f, 0.6f))
                        .topK(5)
                        .build()))
                .returnMetadata(false)
                .returnMetadataFields(Arrays.asList("title"))
                .limit(5)
                .header("x-oss-original", "original-header")
                .parameter("original-param", "original-value")
                .build();

        QueryVectorsFusionRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("original-bucket");
        assertThat(copy.indexName()).isEqualTo("original-index");
        assertThat(copy.knn()).hasSize(1);
        assertThat(((Map<?, ?>) copy.knn().get(0)).get("field")).isEqualTo("vector");
        assertThat(((Map<?, ?>) copy.knn().get(0)).get("topK")).isEqualTo(5);
        assertThat(copy.returnMetadata()).isFalse();
        assertThat(copy.returnMetadataFields()).containsExactly("title");
        assertThat(copy.limit()).isEqualTo(5);

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void testKnnAsList() {
        List<Knn> knnList = Arrays.asList(
                Knn.newBuilder().field("vector_1").queryVector(Arrays.asList(0.1f, 0.2f)).build(),
                Knn.newBuilder().field("vector_2").queryVector(Arrays.asList(0.3f, 0.4f)).build()
        );

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("examplebucket")
                .indexName("fusion-index")
                .knn(knnList)
                .build();

        // the typed instances are stored as their raw representation
        assertThat(request.knn()).isEqualTo(Arrays.asList(
                knnList.get(0).toMap(), knnList.get(1).toMap()));
    }

    @Test
    public void testKnnAsGenericMapList() throws Exception {
        // The generic Map form is passed through verbatim, so attributes without a strongly-typed
        // model still reach the wire and the request stays forward-compatible.
        Map<String, Object> knnMap = new HashMap<>();
        knnMap.put("field", "vector");
        knnMap.put("queryVector", Arrays.asList(0.1f, 0.2f, 0.3f));
        knnMap.put("topK", 10);
        knnMap.put("futureParam", "x");

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .knn(Arrays.asList(knnMap))
                .build();

        assertThat(request.knn()).hasSize(1);
        Map<?, ?> storedKnn = (Map<?, ?>) request.knn().get(0);
        assertThat(storedKnn.get("field")).isEqualTo("vector");
        assertThat(storedKnn.get("topK")).isEqualTo(10);

        String jsonStr = "{\"indexName\":\"fusion-index\",\"knn\":[{\"field\":\"vector\","
                + "\"queryVector\":[0.1,0.2,0.3],\"topK\":10,\"futureParam\":\"x\"}]}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testKnnMapSerializesSameAsTypedForm() throws Exception {
        Map<String, Object> knnMap = new HashMap<>();
        knnMap.put("field", "vector");
        knnMap.put("queryVector", Arrays.asList(0.1f, 0.2f, 0.3f));
        knnMap.put("topK", 10);

        QueryVectorsFusionRequest genericRequest = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .knn(Arrays.asList(knnMap))
                .build();

        QueryVectorsFusionRequest typedRequest = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .knn(Knn.newBuilder()
                        .field("vector")
                        .queryVector(Arrays.asList(0.1f, 0.2f, 0.3f))
                        .topK(10)
                        .build())
                .build();

        JsonNode genericNode = OBJECT_MAPPER.readTree(
                SerdeVectorsBasic.fromQueryVectorsFusion(genericRequest).body().get().toBytes());
        JsonNode typedNode = OBJECT_MAPPER.readTree(
                SerdeVectorsBasic.fromQueryVectorsFusion(typedRequest).body().get().toBytes());
        assertThat(genericNode).isEqualTo(typedNode);
    }

    @Test
    public void testQueryJsonStructure() throws Exception {
        Map<String, Object> textMatch = new HashMap<>();
        textMatch.put("value", "hello world");
        textMatch.put("boost", 2.0f);
        Map<String, Object> textCondition = new HashMap<>();
        textCondition.put("$textMatch", textMatch);
        Map<String, Object> firstClause = new HashMap<>();
        firstClause.put("field_name_1", textCondition);

        Map<String, Object> gte = new HashMap<>();
        gte.put("value", 2027);
        gte.put("boost", 1.0f);
        Map<String, Object> rangeCondition = new HashMap<>();
        rangeCondition.put("$gte", gte);
        Map<String, Object> secondClause = new HashMap<>();
        secondClause.put("field_name_2", rangeCondition);

        Map<String, Object> query = new HashMap<>();
        query.put("$and", Arrays.asList(firstClause, secondClause));

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("string")
                .query(query)
                .partitionKeys(Arrays.asList("user1", "user2"))
                .limit(200)
                .nextToken("next-token-value")
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("field_a", "field_b", "field_c"))
                .build();

        String jsonStr = "{\"indexName\":\"string\",\"query\":{\"$and\":["
                + "{\"field_name_1\":{\"$textMatch\":{\"value\":\"hello world\",\"boost\":2.0}}},"
                + "{\"field_name_2\":{\"$gte\":{\"value\":2027,\"boost\":1.0}}}]},"
                + "\"partitionKeys\":[\"user1\",\"user2\"],\"limit\":200,"
                + "\"nextToken\":\"next-token-value\",\"returnMetadata\":true,"
                + "\"returnMetadataFields\":[\"field_a\",\"field_b\",\"field_c\"]}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testMultipleKnnJsonStructure() throws Exception {
        Map<String, Object> filterCondition = new HashMap<>();
        filterCondition.put("$eq", "abc");
        Map<String, Object> filter = new HashMap<>();
        filter.put("meta_field_1", filterCondition);

        Map<String, Object> scoreOrder = new HashMap<>();
        scoreOrder.put("order", "desc");
        Map<String, Object> scoreSort = new HashMap<>();
        scoreSort.put("_score", scoreOrder);

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("string")
                .knn(Arrays.asList(
                        Knn.newBuilder()
                                .field("vector_field_name_1")
                                .queryVector(Arrays.asList(0, 0, 5, 5, 5, 0))
                                .topK(200)
                                .filter(filter)
                                .boost(1.0f)
                                .build(),
                        Knn.newBuilder()
                                .field("vector_field_name_2")
                                .queryVector(Arrays.asList(0, 0, 5, 5, 5, 0))
                                .topK(200)
                                .filter(filter)
                                .boost(2.0f)
                                .build()))
                .limit(100)
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("field_a", "field_b", "field_c"))
                .sort(Arrays.asList(scoreSort))
                .build();

        String jsonStr = "{\"indexName\":\"string\",\"knn\":["
                + "{\"field\":\"vector_field_name_1\",\"queryVector\":[0,0,5,5,5,0],\"topK\":200,"
                + "\"filter\":{\"meta_field_1\":{\"$eq\":\"abc\"}},\"boost\":1.0},"
                + "{\"field\":\"vector_field_name_2\",\"queryVector\":[0,0,5,5,5,0],\"topK\":200,"
                + "\"filter\":{\"meta_field_1\":{\"$eq\":\"abc\"}},\"boost\":2.0}],"
                + "\"limit\":100,\"returnMetadata\":true,"
                + "\"returnMetadataFields\":[\"field_a\",\"field_b\",\"field_c\"],"
                + "\"sort\":[{\"_score\":{\"order\":\"desc\"}}]}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void bodyBuilder() {
        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("body-test-bucket")
                .indexName("body-test-index")
                .limit(10)
                .build();

        OperationInput input = SerdeVectorsBasic.fromQueryVectorsFusion(request);

        assertThat(input.bucket().get()).isEqualTo("body-test-bucket");
        assertThat(input.parameters().get("indexName")).isNull();
        assertThat(input.parameters()).containsEntry("queryVectorsFusion", "");
    }

    @Test
    public void xmlBuilder() throws Exception {
        String jsonStr = "{\"indexName\":\"fusion-index\",\"knn\":[{\"field\":\"vector\",\"queryVector\":[0.1,0.2,0.3],"
                + "\"topK\":10,\"numCandidates\":100}],\"returnMetadata\":true,\"limit\":20,"
                + "\"returnMetadataFields\":[\"title\"],\"partitionKeys\":[\"user_id_1\"]}";

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .knn(Arrays.asList(Knn.newBuilder()
                        .field("vector")
                        .queryVector(Arrays.asList(0.1f, 0.2f, 0.3f))
                        .topK(10)
                        .numCandidates(100)
                        .build()))
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("title"))
                .partitionKeys(Arrays.asList("user_id_1"))
                .limit(20)
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeVectorsBasic.fromQueryVectorsFusion(request);

        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toBytes());

        assertThat(input).isNotNull();
        assertThat(input.bucket()).isPresent();
        assertThat(input.bucket()).hasValue("test-bucket");
        assertThat(input.headers()).containsEntry("Content-Type", "application/json");
        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.parameters()).containsEntry("queryVectorsFusion", "");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.opName()).isEqualTo("QueryVectorsFusion");
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    @Test
    public void testSimpleRetrieverJsonStructure() throws Exception {
        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(SimpleRetriever.newBuilder()
                        .query(createTextMatchQuery("title_field", "hello world", 2.0f))
                        .build())
                .build();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"simple\":{\"query\":{"
                + "\"title_field\":{\"$textMatch\":{\"value\":\"hello world\",\"boost\":2.0}}}}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testKnnRetrieverFromMap() throws Exception {
        // The knn leaf retriever has no dedicated typed overload; it is expressed with the generic
        // map form, optionally reusing the typed Knn through toMap().
        Map<String, Object> retriever = new HashMap<>();
        retriever.put("knn", Knn.newBuilder()
                .field("vector_field")
                .queryVector(Arrays.asList(10, 22, 77))
                .build().toMap());

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(retriever)
                .build();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"knn\":{"
                + "\"field\":\"vector_field\",\"queryVector\":[10,22,77]}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testRetrieverJsonStructure() throws Exception {
        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"rrf\":{\"k\":50,\"windowSize\":100,\"retrievers\":["
                + "{\"retriever\":{\"knn\":{\"field\":\"vector\",\"queryVector\":[10,22,77]}},\"weight\":1.0},"
                + "{\"retriever\":{\"simple\":{\"query\":{\"title\":{\"$textMatch\":{\"value\":\"hello world\",\"boost\":2.0}}}}},"
                + "\"weight\":2.0}]}}}";

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(createTestRrfRetriever())
                .build();

        OperationInput input = SerdeVectorsBasic.fromQueryVectorsFusion(request);

        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toBytes());

        assertThat(actualNode).isEqualTo(expectedNode);
    }

    @Test
    public void testWeightRetrieverJsonStructure() throws Exception {
        WeightRetriever weightRetriever = WeightRetriever.newBuilder()
                .windowSize(100)
                .retrievers(Arrays.asList(
                        createKnnComponent("vector", Arrays.asList(10, 22, 77), 0.7f, "minMax"),
                        createSimpleComponent(
                                createTextMatchQuery("title", "hello world", 2.0f),
                                0.3f,
                                "minMax")))
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(weightRetriever)
                .build();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"weight\":{\"windowSize\":100,"
                + "\"retrievers\":[{\"retriever\":{\"knn\":{\"field\":\"vector\","
                + "\"queryVector\":[10,22,77]}},\"weight\":0.7,\"normalizer\":\"minMax\"},"
                + "{\"retriever\":{\"simple\":{\"query\":{\"title\":{\"$textMatch\":{"
                + "\"value\":\"hello world\",\"boost\":2.0}}}}},\"weight\":0.3,"
                + "\"normalizer\":\"minMax\"}]}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testThreeWayWeightRetrieverJsonStructure() throws Exception {
        WeightRetriever weightRetriever = WeightRetriever.newBuilder()
                .windowSize(200)
                .retrievers(Arrays.asList(
                        createKnnComponent("text_vector", Arrays.asList(10, 22, 77), 0.5f, "l2"),
                        createKnnComponent("image_vector", Arrays.asList(21, 35, 66), 0.3f, "l2"),
                        createSimpleComponent(
                                createTextMatchQuery("description", "red sports car", 1.5f),
                                0.2f,
                                "minMax")))
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(weightRetriever)
                .build();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"weight\":{\"windowSize\":200,"
                + "\"retrievers\":[{\"retriever\":{\"knn\":{\"field\":\"text_vector\","
                + "\"queryVector\":[10,22,77]}},\"weight\":0.5,\"normalizer\":\"l2\"},"
                + "{\"retriever\":{\"knn\":{\"field\":\"image_vector\",\"queryVector\":[21,35,66]}},"
                + "\"weight\":0.3,\"normalizer\":\"l2\"},{\"retriever\":{\"simple\":{\"query\":{"
                + "\"description\":{\"$textMatch\":{\"value\":\"red sports car\",\"boost\":1.5}}}}},"
                + "\"weight\":0.2,\"normalizer\":\"minMax\"}]}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testNestedRetrieverJsonStructure() throws Exception {
        WeightRetriever nestedWeight = WeightRetriever.newBuilder()
                .windowSize(100)
                .retrievers(Arrays.asList(
                        createKnnComponent("text_vector", Arrays.asList(10, 22, 77), 0.7f, "minMax"),
                        createKnnComponent("image_vector", Arrays.asList(21, 35, 66), 0.3f, "minMax")))
                .build();

        // A nested compound retriever has no typed overload in the component; it is expressed
        // with the generic map form, wrapped by its type key.
        Map<String, Object> nestedWeightRetriever = new HashMap<>();
        nestedWeightRetriever.put("weight", nestedWeight.toMap());

        RrfRetriever rrfRetriever = RrfRetriever.newBuilder()
                .k(50)
                .windowSize(200)
                .retrievers(Arrays.asList(
                        createRrfSimpleComponent(
                                createTextMatchQuery("title", "hello world", null),
                                1.0f),
                        RetrieverComponent.newBuilder()
                                .retriever(nestedWeightRetriever)
                                .weight(1.2f)
                                .build()))
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(rrfRetriever)
                .build();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"rrf\":{\"k\":50,"
                + "\"windowSize\":200,\"retrievers\":[{\"retriever\":{\"simple\":{\"query\":{\"title\":{"
                + "\"$textMatch\":{\"value\":\"hello world\"}}}}},\"weight\":1.0},{\"retriever\":{\"weight\":{"
                + "\"windowSize\":100,\"retrievers\":[{\"retriever\":{\"knn\":{\"field\":\"text_vector\","
                + "\"queryVector\":[10,22,77]}},\"weight\":0.7,\"normalizer\":\"minMax\"},"
                + "{\"retriever\":{\"knn\":{\"field\":\"image_vector\",\"queryVector\":[21,35,66]}},"
                + "\"weight\":0.3,\"normalizer\":\"minMax\"}]}},\"weight\":1.2}]}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testKnnToBuilderPreserveState() {
        Map<String, Object> filter = createFilter();

        Knn original = Knn.newBuilder()
                .field("vector")
                .queryVector(Arrays.asList(0.1f, 0.2f))
                .topK(10)
                .filter(filter)
                .numCandidates(100)
                .boost(2.0f)
                .build();

        Knn copy = original.toBuilder().build();

        assertThat(copy.field()).isEqualTo("vector");
        assertThat(copy.topK()).isEqualTo(10);
        assertThat(copy.filter()).isEqualTo(filter);
        assertThat(copy.numCandidates()).isEqualTo(100);
        assertThat(copy.boost()).isEqualTo(2.0f);
    }

    @Test
    public void testRetrieverComponentNormalizerEnum() throws Exception {
        RetrieverComponent minMaxComponent = RetrieverComponent.newBuilder()
                .retriever(Knn.newBuilder().field("text_vector").queryVector(Arrays.asList(10, 22, 77)).build())
                .weight(0.7f)
                .normalizer(NormalizerType.MIN_MAX)
                .build();
        RetrieverComponent l2Component = RetrieverComponent.newBuilder()
                .retriever(Knn.newBuilder().field("image_vector").queryVector(Arrays.asList(21, 35, 66)).build())
                .weight(0.3f)
                .normalizer(NormalizerType.L2)
                .build();

        // the enum overload stores the serialized string value, getter still returns String
        assertThat(minMaxComponent.normalizer()).isEqualTo("minMax");
        assertThat(l2Component.normalizer()).isEqualTo("l2");

        WeightRetriever weightRetriever = WeightRetriever.newBuilder()
                .windowSize(100)
                .retrievers(Arrays.asList(minMaxComponent, l2Component))
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(weightRetriever)
                .build();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"weight\":{\"windowSize\":100,"
                + "\"retrievers\":[{\"retriever\":{\"knn\":{\"field\":\"text_vector\","
                + "\"queryVector\":[10,22,77]}},\"weight\":0.7,\"normalizer\":\"minMax\"},"
                + "{\"retriever\":{\"knn\":{\"field\":\"image_vector\",\"queryVector\":[21,35,66]}},"
                + "\"weight\":0.3,\"normalizer\":\"l2\"}]}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testRetrieverFromRrfRetriever() throws Exception {
        // The specialized RrfRetriever overload is stored as {"rrf": ...} after normalization.
        RrfRetriever rrf = RrfRetriever.newBuilder()
                .k(50)
                .windowSize(100)
                .retrievers(Arrays.asList(
                        createRrfKnnComponent("vector", Arrays.asList(10, 22, 77), 1.0f),
                        createRrfSimpleComponent(createTextMatchQuery("title", "hello world", 2.0f), 2.0f)))
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(rrf)
                .limit(10)
                .build();

        assertThat(request.retriever()).containsKey("rrf");

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"rrf\":{\"k\":50,\"windowSize\":100,"
                + "\"retrievers\":[{\"retriever\":{\"knn\":{\"field\":\"vector\",\"queryVector\":[10,22,77]}},"
                + "\"weight\":1.0},{\"retriever\":{\"simple\":{\"query\":{\"title\":{\"$textMatch\":"
                + "{\"value\":\"hello world\",\"boost\":2.0}}}}},\"weight\":2.0}]}},\"limit\":10}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testRetrieverFromWeightRetriever() throws Exception {
        WeightRetriever weight = WeightRetriever.newBuilder()
                .windowSize(100)
                .retrievers(Arrays.asList(
                        createKnnComponent("vector", Arrays.asList(10, 22, 77), 0.7f, "minMax"),
                        createSimpleComponent(createTextMatchQuery("title", "hello world", 2.0f), 0.3f, "minMax")))
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(weight)
                .build();

        assertThat(request.retriever()).containsKey("weight");

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"weight\":{\"windowSize\":100,"
                + "\"retrievers\":[{\"retriever\":{\"knn\":{\"field\":\"vector\",\"queryVector\":[10,22,77]}},"
                + "\"weight\":0.7,\"normalizer\":\"minMax\"},{\"retriever\":{\"simple\":{\"query\":{\"title\":"
                + "{\"$textMatch\":{\"value\":\"hello world\",\"boost\":2.0}}}}},\"weight\":0.3,"
                + "\"normalizer\":\"minMax\"}]}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testRrfRetrieverAcceptsRawComponentMap() throws Exception {
        // A sub retriever may be supplied as a raw map and mixed with the typed components, so the
        // structure stays forward-compatible when the typed model does not cover every attribute.
        Map<String, Object> rawRetriever = new HashMap<>();
        rawRetriever.put("knn", Knn.newBuilder()
                .field("raw_vector")
                .queryVector(Arrays.asList(10, 22, 77))
                .build().toMap());
        Map<String, Object> rawComponent = new HashMap<>();
        rawComponent.put("retriever", rawRetriever);
        rawComponent.put("weight", 3.0f);

        RrfRetriever rrf = RrfRetriever.newBuilder()
                .k(50)
                .retrievers(Arrays.asList(
                        createRrfKnnComponent("vector", Arrays.asList(10, 22, 77), 1.0f),
                        rawComponent))
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(rrf)
                .build();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"rrf\":{\"k\":50,"
                + "\"retrievers\":[{\"retriever\":{\"knn\":{\"field\":\"vector\",\"queryVector\":[10,22,77]}},"
                + "\"weight\":1.0},{\"retriever\":{\"knn\":{\"field\":\"raw_vector\",\"queryVector\":[10,22,77]}},"
                + "\"weight\":3.0}]}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testWeightRetrieverAcceptsRawComponentMap() throws Exception {
        Map<String, Object> rawRetriever = new HashMap<>();
        rawRetriever.put("simple", SimpleRetriever.newBuilder()
                .query(createTextMatchQuery("title", "hello world", null))
                .build().toMap());
        Map<String, Object> rawComponent = new HashMap<>();
        rawComponent.put("retriever", rawRetriever);
        rawComponent.put("weight", 0.4f);
        rawComponent.put("normalizer", "l2");

        WeightRetriever weight = WeightRetriever.newBuilder()
                .windowSize(100)
                .retrievers(Arrays.asList(
                        createKnnComponent("vector", Arrays.asList(10, 22, 77), 0.6f, "minMax"),
                        rawComponent))
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(weight)
                .build();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"weight\":{\"windowSize\":100,"
                + "\"retrievers\":[{\"retriever\":{\"knn\":{\"field\":\"vector\",\"queryVector\":[10,22,77]}},"
                + "\"weight\":0.6,\"normalizer\":\"minMax\"},{\"retriever\":{\"simple\":{\"query\":{\"title\":"
                + "{\"$textMatch\":{\"value\":\"hello world\"}}}}},\"weight\":0.4,\"normalizer\":\"l2\"}]}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testRetrieverFromSimpleRetriever() throws Exception {
        SimpleRetriever simple = SimpleRetriever.newBuilder()
                .query(createTextMatchQuery("title_field", "hello world", 2.0f))
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(simple)
                .build();

        assertThat(request.retriever()).containsKey("simple");

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"simple\":{\"query\":{"
                + "\"title_field\":{\"$textMatch\":{\"value\":\"hello world\",\"boost\":2.0}}}}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testRetrieverFromGenericMap() throws Exception {
        // The generic map form is passed through verbatim, so attributes without a strongly-typed
        // model still reach the wire and the request stays forward-compatible.
        Map<String, Object> knnLeaf = new HashMap<>();
        knnLeaf.put("field", "vector");
        knnLeaf.put("queryVector", Arrays.asList(10, 22, 77));

        Map<String, Object> knnComponent = new HashMap<>();
        knnComponent.put("retriever", new HashMap<String, Object>() {{ put("knn", knnLeaf); }});
        knnComponent.put("weight", 1.0f);
        knnComponent.put("futureWeightParam", 42);

        Map<String, Object> rrf = new HashMap<>();
        rrf.put("k", 50);
        rrf.put("futureParam", "x");
        rrf.put("retrievers", Arrays.asList(knnComponent));

        Map<String, Object> retriever = new HashMap<>();
        retriever.put("rrf", rrf);

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(retriever)
                .limit(10)
                .build();

        assertThat(request.retriever()).isSameAs(retriever);

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"rrf\":{\"k\":50,\"futureParam\":\"x\","
                + "\"retrievers\":[{\"retriever\":{\"knn\":{\"field\":\"vector\",\"queryVector\":[10,22,77]}},"
                + "\"weight\":1.0,\"futureWeightParam\":42}]}},\"limit\":10}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testRetrieverTypedAndMapSerializeSame() throws Exception {
        OperationInput genericInput = SerdeVectorsBasic.fromQueryVectorsFusion(
                QueryVectorsFusionRequest.newBuilder()
                        .bucket("test-bucket")
                        .indexName("fusion-index")
                        .retriever(new HashMap<String, Object>() {{
                            put("rrf", new HashMap<String, Object>() {{
                                put("k", 50);
                                put("windowSize", 100);
                                put("retrievers", Arrays.asList(
                                        new HashMap<String, Object>() {{
                                            put("retriever", new HashMap<String, Object>() {{
                                                put("knn", Knn.newBuilder()
                                                        .field("vector")
                                                        .queryVector(Arrays.asList(10, 22, 77))
                                                        .build().toMap());
                                            }});
                                            put("weight", 1.0f);
                                        }}));
                            }});
                        }})
                        .build());

        OperationInput typedInput = SerdeVectorsBasic.fromQueryVectorsFusion(
                QueryVectorsFusionRequest.newBuilder()
                        .bucket("test-bucket")
                        .indexName("fusion-index")
                        .retriever(RrfRetriever.newBuilder()
                                .k(50)
                                .windowSize(100)
                                .retrievers(Arrays.asList(
                                        createRrfKnnComponent("vector", Arrays.asList(10, 22, 77), 1.0f)))
                                .build())
                        .build());

        JsonNode genericNode = OBJECT_MAPPER.readTree(genericInput.body().get().toBytes());
        JsonNode typedNode = OBJECT_MAPPER.readTree(typedInput.body().get().toBytes());
        assertThat(genericNode).isEqualTo(typedNode);
    }

    /**
     * B2 from the doc: single vector search with a pre-filter composed of $in, $range and $eq.
     */
    @Test
    public void testKnnWithPreFilterFromDoc() throws Exception {
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

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("productindex")
                .knn(Knn.newBuilder()
                        .field("text_vector")
                        .queryVector(Arrays.asList(0.12f, 0.53f, 0.08f, 0.91f))
                        .topK(100)
                        .numCandidates(200)
                        .filter(filter)
                        .build())
                .limit(20)
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("title", "brand", "price"))
                .build();

        String jsonStr = "{\"indexName\":\"productindex\",\"knn\":[{\"field\":\"text_vector\",\"queryVector\":[0.12,0.53,0.08,0.91],"
                + "\"topK\":100,\"numCandidates\":200,\"filter\":{\"$and\":{\"clauses\":["
                + "{\"brand\":{\"$in\":{\"value\":[\"AliBrand\",\"NovaBrand\"]}}},"
                + "{\"price\":{\"$range\":{\"gte\":100,\"lt\":999}}},"
                + "{\"on_sale\":{\"$eq\":true}}"
                + "]}}}],\"limit\":20,\"returnMetadata\":true,\"returnMetadataFields\":[\"title\",\"brand\",\"price\"]}";
        assertRequestJson(request, jsonStr);
    }

    /**
     * B3 from the doc: multi-way vector search over text_vector and image_vector with per-road
     * boost and an explicit _score sort.
     */
    @Test
    public void testMultiWayKnnFromDoc() throws Exception {
        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("multimodalindex")
                .knn(Arrays.asList(
                        Knn.newBuilder()
                                .field("text_vector")
                                .queryVector(Arrays.asList(0.12f, 0.53f, 0.08f))
                                .topK(100)
                                .boost(1.5f)
                                .build(),
                        Knn.newBuilder()
                                .field("image_vector")
                                .queryVector(Arrays.asList(0.44f, 0.21f, 0.77f))
                                .topK(100)
                                .boost(1.0f)
                                .build()))
                .sort(Arrays.asList(new HashMap<String, Object>() {{
                    put("_score", new HashMap<String, Object>() {{ put("order", "desc"); }});
                }}))
                .limit(10)
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("title", "duration"))
                .build();

        String jsonStr = "{\"indexName\":\"multimodalindex\",\"knn\":["
                + "{\"field\":\"text_vector\",\"queryVector\":[0.12,0.53,0.08],\"topK\":100,\"boost\":1.5},"
                + "{\"field\":\"image_vector\",\"queryVector\":[0.44,0.21,0.77],\"topK\":100,\"boost\":1.0}],"
                + "\"sort\":[{\"_score\":{\"order\":\"desc\"}}],\"limit\":10,\"returnMetadata\":true,\"returnMetadataFields\":[\"title\",\"duration\"]}";
        assertRequestJson(request, jsonStr);
    }

    /**
     * B4 from the doc: pure scalar search without vectors, using $and/$in/$range/$gt and a custom
     * price sort.
     */
    @Test
    public void testPureScalarQueryFromDoc() throws Exception {
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

        Map<String, Object> sortItem = new HashMap<>();
        sortItem.put("price", new HashMap<String, Object>() {{ put("order", "asc"); }});

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("productindex")
                .query(query)
                .sort(Arrays.asList(sortItem))
                .limit(20)
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("title", "brand", "price", "stock"))
                .build();

        String jsonStr = "{\"indexName\":\"productindex\",\"query\":{\"$and\":{\"clauses\":["
                + "{\"category\":{\"$in\":{\"value\":[\"phone\",\"tablet\"]}}},"
                + "{\"price\":{\"$range\":{\"gte\":1000,\"lte\":5000}}},"
                + "{\"stock\":{\"$gt\":0}}"
                + "]}},\"sort\":[{\"price\":{\"order\":\"asc\"}}],\"limit\":20,\"returnMetadata\":true,\"returnMetadataFields\":[\"title\",\"brand\",\"price\",\"stock\"]}";
        assertRequestJson(request, jsonStr);
    }

    /**
     * B5 from the doc: full-text search with $textMatch, including operator, minShouldMatch and
     * boost.
     */
    @Test
    public void testTextMatchQueryFromDoc() throws Exception {
        Map<String, Object> textMatch = new HashMap<>();
        textMatch.put("value", "无线 降噪 耳机");
        textMatch.put("operator", "or");
        textMatch.put("minShouldMatch", "2");
        textMatch.put("boost", 1.0f);
        Map<String, Object> bodyCondition = new HashMap<>();
        bodyCondition.put("$textMatch", textMatch);
        Map<String, Object> query = new HashMap<>();
        query.put("body", bodyCondition);

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("kbindex")
                .query(query)
                .limit(10)
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("title", "doc_id"))
                .build();

        String jsonStr = "{\"indexName\":\"kbindex\",\"query\":{\"body\":{\"$textMatch\":{\"value\":\"无线 降噪 耳机\",\"operator\":\"or\",\"minShouldMatch\":\"2\",\"boost\":1.0}}},"
                + "\"limit\":10,\"returnMetadata\":true,\"returnMetadataFields\":[\"title\",\"doc_id\"]}";
        assertRequestJson(request, jsonStr);
    }

    /**
     * B16 from the doc: RRF fusion of a knn leaf retriever and a simple text-match leaf retriever.
     */
    @Test
    public void testRrfRetrieverFromDoc() throws Exception {
        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("productindex")
                .retriever(RrfRetriever.newBuilder()
                        .k(50)
                        .windowSize(100)
                        .retrievers(Arrays.asList(
                                RetrieverComponent.newBuilder()
                                        .retriever(Knn.newBuilder()
                                                        .field("text_vector")
                                                        .queryVector(Arrays.asList(0.12f, 0.53f, 0.08f))
                                                        .topK(100)
                                                        .build())
                                        .weight(2.0f)
                                        .build(),
                                createRrfSimpleComponent(
                                        createTextMatchQuery("title", "无线 耳机", null),
                                        0.5f)))
                        .build())
                .limit(10)
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("title", "brand"))
                .build();

        String jsonStr = "{\"indexName\":\"productindex\",\"retriever\":{\"rrf\":{\"k\":50,\"windowSize\":100,\"retrievers\":["
                + "{\"retriever\":{\"knn\":{\"field\":\"text_vector\",\"queryVector\":[0.12,0.53,0.08],\"topK\":100}},\"weight\":2.0},"
                + "{\"retriever\":{\"simple\":{\"query\":{\"title\":{\"$textMatch\":{\"value\":\"无线 耳机\"}}}}},\"weight\":0.5}]}}"
                + ",\"limit\":10,\"returnMetadata\":true,\"returnMetadataFields\":[\"title\",\"brand\"]}";
        assertRequestJson(request, jsonStr);
    }

    /**
     * B17 from the doc: Weight fusion with minMax normalizer over a knn leaf and a simple
     * text-match leaf.
     */
    @Test
    public void testWeightRetrieverFromDoc() throws Exception {
        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("productindex")
                .retriever(WeightRetriever.newBuilder()
                        .windowSize(100)
                        .retrievers(Arrays.asList(
                                RetrieverComponent.newBuilder()
                                        .retriever(Knn.newBuilder()
                                                        .field("text_vector")
                                                        .queryVector(Arrays.asList(0.12f, 0.53f, 0.08f))
                                                        .topK(100)
                                                        .build())
                                        .weight(0.7f)
                                        .normalizer("minMax")
                                        .build(),
                                createSimpleComponent(
                                        createTextMatchQuery("title", "无线 耳机", null),
                                        0.3f,
                                        "minMax")))
                        .build())
                .limit(10)
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("title", "brand"))
                .build();

        String jsonStr = "{\"indexName\":\"productindex\",\"retriever\":{\"weight\":{\"windowSize\":100,\"retrievers\":["
                + "{\"retriever\":{\"knn\":{\"field\":\"text_vector\",\"queryVector\":[0.12,0.53,0.08],\"topK\":100}},\"weight\":0.7,\"normalizer\":\"minMax\"},"
                + "{\"retriever\":{\"simple\":{\"query\":{\"title\":{\"$textMatch\":{\"value\":\"无线 耳机\"}}}}},\"weight\":0.3,\"normalizer\":\"minMax\"}]}}"
                + ",\"limit\":10,\"returnMetadata\":true,\"returnMetadataFields\":[\"title\",\"brand\"]}";
        assertRequestJson(request, jsonStr);
    }

    /**
     * B18 from the doc: three-way weight fusion over text_vector, image_vector and a title
     * text-match, using different normalizers per road.
     */
    @Test
    public void testThreeWayWeightRetrieverFromDoc() throws Exception {
        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("multimodalindex")
                .retriever(WeightRetriever.newBuilder()
                        .windowSize(200)
                        .retrievers(Arrays.asList(
                                RetrieverComponent.newBuilder()
                                        .retriever(Knn.newBuilder()
                                                        .field("text_vector")
                                                        .queryVector(Arrays.asList(0.12f, 0.53f, 0.08f))
                                                        .topK(200)
                                                        .build())
                                        .weight(0.5f)
                                        .normalizer("l2")
                                        .build(),
                                RetrieverComponent.newBuilder()
                                        .retriever(Knn.newBuilder()
                                                        .field("image_vector")
                                                        .queryVector(Arrays.asList(0.44f, 0.21f, 0.77f))
                                                        .topK(200)
                                                        .build())
                                        .weight(0.3f)
                                        .normalizer("l2")
                                        .build(),
                                createSimpleComponent(
                                        createTextMatchQuery("title", "红色 跑车", null),
                                        0.2f,
                                        "minMax")))
                        .build())
                .limit(10)
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("title", "duration"))
                .build();

        String jsonStr = "{\"indexName\":\"multimodalindex\",\"retriever\":{\"weight\":{\"windowSize\":200,\"retrievers\":["
                + "{\"retriever\":{\"knn\":{\"field\":\"text_vector\",\"queryVector\":[0.12,0.53,0.08],\"topK\":200}},\"weight\":0.5,\"normalizer\":\"l2\"},"
                + "{\"retriever\":{\"knn\":{\"field\":\"image_vector\",\"queryVector\":[0.44,0.21,0.77],\"topK\":200}},\"weight\":0.3,\"normalizer\":\"l2\"},"
                + "{\"retriever\":{\"simple\":{\"query\":{\"title\":{\"$textMatch\":{\"value\":\"红色 跑车\"}}}}},\"weight\":0.2,\"normalizer\":\"minMax\"}]}}"
                + ",\"limit\":10,\"returnMetadata\":true,\"returnMetadataFields\":[\"title\",\"duration\"]}";
        assertRequestJson(request, jsonStr);
    }

    /**
     * B19 from the doc: knn and query coexist; vector scores and query scores are added directly.
     */
    @Test
    public void testKnnAndQueryCoexistFromDoc() throws Exception {
        Map<String, Object> brandIn = new HashMap<>();
        brandIn.put("value", Arrays.asList("AliBrand"));
        brandIn.put("boost", 1.0f);
        Map<String, Object> brandCondition = new HashMap<>();
        brandCondition.put("$in", brandIn);
        Map<String, Object> brandClause = new HashMap<>();
        brandClause.put("brand", brandCondition);

        Map<String, Object> titleTextMatch = new HashMap<>();
        titleTextMatch.put("value", "耳机");
        titleTextMatch.put("boost", 4.0f);
        Map<String, Object> titleCondition = new HashMap<>();
        titleCondition.put("$textMatch", titleTextMatch);
        Map<String, Object> titleClause = new HashMap<>();
        titleClause.put("title", titleCondition);

        Map<String, Object> orNode = new HashMap<>();
        orNode.put("clauses", Arrays.asList(brandClause, titleClause));
        Map<String, Object> query = new HashMap<>();
        query.put("$or", orNode);

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("productindex")
                .knn(Arrays.asList(
                        Knn.newBuilder()
                                .field("text_vector")
                                .queryVector(Arrays.asList(0.12f, 0.53f, 0.08f))
                                .topK(100)
                                .boost(2.0f)
                                .build(),
                        Knn.newBuilder()
                                .field("image_vector")
                                .queryVector(Arrays.asList(0.44f, 0.21f, 0.77f))
                                .topK(100)
                                .boost(0.5f)
                                .build()))
                .query(query)
                .sort(Arrays.asList(new HashMap<String, Object>() {{
                    put("_score", new HashMap<String, Object>() {{ put("order", "desc"); }});
                }}))
                .limit(20)
                .returnMetadata(true)
                .returnMetadataFields(Arrays.asList("title", "brand", "price"))
                .build();

        String jsonStr = "{\"indexName\":\"productindex\",\"knn\":["
                + "{\"field\":\"text_vector\",\"queryVector\":[0.12,0.53,0.08],\"topK\":100,\"boost\":2.0},"
                + "{\"field\":\"image_vector\",\"queryVector\":[0.44,0.21,0.77],\"topK\":100,\"boost\":0.5}],"
                + "\"query\":{\"$or\":{\"clauses\":["
                + "{\"brand\":{\"$in\":{\"value\":[\"AliBrand\"],\"boost\":1.0}}},"
                + "{\"title\":{\"$textMatch\":{\"value\":\"耳机\",\"boost\":4.0}}}"
                + "]}},\"sort\":[{\"_score\":{\"order\":\"desc\"}}],\"limit\":20,\"returnMetadata\":true,\"returnMetadataFields\":[\"title\",\"brand\",\"price\"]}";
        assertRequestJson(request, jsonStr);
    }

    /**
     * B20 from the doc: single leaf retriever used directly as the top-level retriever.
     */
    @Test
    public void testSingleLeafRetrieverFromDoc() throws Exception {
        // simple leaf
        QueryVectorsFusionRequest simpleRequest = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("kbindex")
                .retriever(SimpleRetriever.newBuilder()
                        .query(createTextMatchQuery("title", "hello world", 2.0f))
                        .build())
                .limit(10)
                .build();

        String simpleJson = "{\"indexName\":\"kbindex\",\"retriever\":{\"simple\":{\"query\":{\"title\":{\"$textMatch\":{\"value\":\"hello world\",\"boost\":2.0}}}}},\"limit\":10}";
        assertRequestJson(simpleRequest, simpleJson);

        // knn leaf, expressed with the generic map form
        Map<String, Object> knnRetriever = new HashMap<>();
        knnRetriever.put("knn", Knn.newBuilder()
                .field("chunk_vector")
                .queryVector(Arrays.asList(0.12f, 0.53f, 0.08f))
                .topK(100)
                .build().toMap());

        QueryVectorsFusionRequest knnRequest = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("kbindex")
                .retriever(knnRetriever)
                .limit(10)
                .build();

        String knnJson = "{\"indexName\":\"kbindex\",\"retriever\":{\"knn\":{\"field\":\"chunk_vector\",\"queryVector\":[0.12,0.53,0.08],\"topK\":100}},\"limit\":10}";
        assertRequestJson(knnRequest, knnJson);
    }

    private void assertRequestJson(QueryVectorsFusionRequest request, String jsonStr) throws Exception {
        OperationInput input = SerdeVectorsBasic.fromQueryVectorsFusion(request);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toBytes());
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    private Map<String, Object> createTextMatchQuery(String field, String value, Float boost) {
        Map<String, Object> textMatch = new HashMap<>();
        textMatch.put("value", value);
        if (boost != null) {
            textMatch.put("boost", boost);
        }
        Map<String, Object> condition = new HashMap<>();
        condition.put("$textMatch", textMatch);
        Map<String, Object> query = new HashMap<>();
        query.put(field, condition);
        return query;
    }

    private RetrieverComponent createKnnComponent(
            String field, List<Integer> queryVector, Float weight, String normalizer) {
        RetrieverComponent.Builder builder = RetrieverComponent.newBuilder()
                .retriever(Knn.newBuilder().field(field).queryVector(queryVector).build())
                .weight(weight);
        if (normalizer != null) {
            builder.normalizer(normalizer);
        }
        return builder.build();
    }

    private RetrieverComponent createSimpleComponent(
            Map<String, Object> query, Float weight, String normalizer) {
        RetrieverComponent.Builder builder = RetrieverComponent.newBuilder()
                .retriever(SimpleRetriever.newBuilder().query(query).build())
                .weight(weight);
        if (normalizer != null) {
            builder.normalizer(normalizer);
        }
        return builder.build();
    }

    private RetrieverComponent createRrfKnnComponent(
            String field, List<Integer> queryVector, Float weight) {
        return RetrieverComponent.newBuilder()
                .retriever(Knn.newBuilder().field(field).queryVector(queryVector).build())
                .weight(weight)
                .build();
    }

    private RetrieverComponent createRrfSimpleComponent(
            Map<String, Object> query, Float weight) {
        return RetrieverComponent.newBuilder()
                .retriever(SimpleRetriever.newBuilder().query(query).build())
                .weight(weight)
                .build();
    }

    private RrfRetriever createTestRrfRetriever() {
        Map<String, Object> query = new HashMap<>();
        Map<String, Object> textMatch = new HashMap<>();
        textMatch.put("value", "hello world");
        textMatch.put("boost", 2.0f);
        Map<String, Object> titleCondition = new HashMap<>();
        titleCondition.put("$textMatch", textMatch);
        query.put("title", titleCondition);

        return RrfRetriever.newBuilder()
                .k(50)
                .windowSize(100)
                .retrievers(Arrays.asList(
                        createRrfKnnComponent("vector", Arrays.asList(10, 22, 77), 1.0f),
                        createRrfSimpleComponent(query, 2.0f)))
                .build();
    }

    private Map<String, Object> createFilter() {
        Map<String, Object> typeOperator = new HashMap<>();
        typeOperator.put("$in", Arrays.asList("comedy", "documentary"));
        Map<String, Object> typeCondition = new HashMap<>();
        typeCondition.put("type", typeOperator);

        Map<String, Object> yearOperator = new HashMap<>();
        yearOperator.put("$gte", 2020);
        Map<String, Object> yearCondition = new HashMap<>();
        yearCondition.put("year", yearOperator);

        Map<String, Object> filter = new HashMap<>();
        filter.put("$and", Arrays.asList(typeCondition, yearCondition));
        return filter;
    }
}
