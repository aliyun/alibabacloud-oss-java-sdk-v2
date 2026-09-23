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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThat(request.knn()).containsExactly(knn);
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
        assertThat(copy.knn().get(0).field()).isEqualTo("vector");
        assertThat(copy.knn().get(0).topK()).isEqualTo(5);
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

        assertThat(request.knn()).isSameAs(knnList);
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
                .retriever(Retriever.newBuilder()
                        .simple(SimpleRetriever.newBuilder()
                                .query(createTextMatchQuery("title_field", "hello world", 2.0f))
                                .build())
                        .build())
                .build();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"simple\":{\"query\":{"
                + "\"title_field\":{\"$textMatch\":{\"value\":\"hello world\",\"boost\":2.0}}}}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testKnnRetrieverJsonStructure() throws Exception {
        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(Retriever.newBuilder()
                        .knn(Knn.newBuilder()
                                .field("vector_field")
                                .queryVector(Arrays.asList(10, 22, 77))
                                .build())
                        .build())
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
                .retriever(createTestRetriever())
                .build();

        OperationInput input = SerdeVectorsBasic.fromQueryVectorsFusion(request);

        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toBytes());

        assertThat(actualNode).isEqualTo(expectedNode);
    }

    @Test
    public void testWeightRetrieverJsonStructure() throws Exception {
        Retriever retriever = Retriever.newBuilder()
                .weight(WeightRetriever.newBuilder()
                        .windowSize(100)
                        .retrievers(Arrays.asList(
                                createKnnComponent("vector", Arrays.asList(10, 22, 77), 0.7f, "minMax"),
                                createSimpleComponent(
                                        createTextMatchQuery("title", "hello world", 2.0f),
                                        0.3f,
                                        "minMax")))
                        .build())
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(retriever)
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
        Retriever retriever = Retriever.newBuilder()
                .weight(WeightRetriever.newBuilder()
                        .windowSize(200)
                        .retrievers(Arrays.asList(
                                createKnnComponent("text_vector", Arrays.asList(10, 22, 77), 0.5f, "l2"),
                                createKnnComponent("image_vector", Arrays.asList(21, 35, 66), 0.3f, "l2"),
                                createSimpleComponent(
                                        createTextMatchQuery("description", "red sports car", 1.5f),
                                        0.2f,
                                        "minMax")))
                        .build())
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(retriever)
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

        Retriever retriever = Retriever.newBuilder()
                .rrf(RrfRetriever.newBuilder()
                        .k(50)
                        .windowSize(200)
                        .retrievers(Arrays.asList(
                                createRrfSimpleComponent(
                                        createTextMatchQuery("title", "hello world", null),
                                        1.0f),
                                RrfRetrieverComponent.newBuilder()
                                        .retriever(Retriever.newBuilder().weight(nestedWeight).build())
                                        .weight(1.2f)
                                        .build()))
                        .build())
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(retriever)
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
    public void testWeightRetrieverComponentNormalizerEnum() throws Exception {
        WeightRetrieverComponent minMaxComponent = WeightRetrieverComponent.newBuilder()
                .retriever(Retriever.newBuilder()
                        .knn(Knn.newBuilder().field("text_vector").queryVector(Arrays.asList(10, 22, 77)).build())
                        .build())
                .weight(0.7f)
                .normalizer(NormalizerType.MIN_MAX)
                .build();
        WeightRetrieverComponent l2Component = WeightRetrieverComponent.newBuilder()
                .retriever(Retriever.newBuilder()
                        .knn(Knn.newBuilder().field("image_vector").queryVector(Arrays.asList(21, 35, 66)).build())
                        .build())
                .weight(0.3f)
                .normalizer(NormalizerType.L2)
                .build();

        // the enum overload stores the serialized string value, getter still returns String
        assertThat(minMaxComponent.normalizer()).isEqualTo("minMax");
        assertThat(l2Component.normalizer()).isEqualTo("l2");

        Retriever retriever = Retriever.newBuilder()
                .weight(WeightRetriever.newBuilder()
                        .windowSize(100)
                        .retrievers(Arrays.asList(minMaxComponent, l2Component))
                        .build())
                .build();

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(retriever)
                .build();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":{\"weight\":{\"windowSize\":100,"
                + "\"retrievers\":[{\"retriever\":{\"knn\":{\"field\":\"text_vector\","
                + "\"queryVector\":[10,22,77]}},\"weight\":0.7,\"normalizer\":\"minMax\"},"
                + "{\"retriever\":{\"knn\":{\"field\":\"image_vector\",\"queryVector\":[21,35,66]}},"
                + "\"weight\":0.3,\"normalizer\":\"l2\"}]}}}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testRetrieverFromJsonString() throws Exception {
        // The whole nested retriever is provided as a raw JSON string and passed through as-is.
        String retrieverJson = "{\"rrf\":{\"k\":50,\"windowSize\":100,\"retrievers\":["
                + "{\"retriever\":{\"knn\":{\"field\":\"vector\",\"queryVector\":[10,22,77]}},\"weight\":1.0},"
                + "{\"retriever\":{\"simple\":{\"query\":{\"title\":{\"$textMatch\":{\"value\":\"hello world\",\"boost\":2.0}}}}},\"weight\":2.0}]}}";

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(retrieverJson)
                .limit(10)
                .build();

        // the raw JSON retriever is not a typed Retriever, so the typed getter returns null
        assertThat(request.retriever()).isNull();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":" + retrieverJson + ",\"limit\":10}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testRetrieverFromJsonStringPreservesUnknownFields() throws Exception {
        // Fields that have no strongly-typed model yet are still serialized verbatim, which keeps
        // the request forward-compatible when the server side adds new parameters.
        String retrieverJson = "{\"rrf\":{\"k\":50,\"futureParam\":\"x\",\"retrievers\":["
                + "{\"retriever\":{\"knn\":{\"field\":\"vector\",\"queryVector\":[1.0,2.0]}},\"weight\":1.0,"
                + "\"futureWeightParam\":42}]}}";

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .retriever(retrieverJson)
                .build();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"retriever\":" + retrieverJson + "}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testKnnFromJsonString() throws Exception {
        String knnJson = "[{\"field\":\"vector\",\"queryVector\":[0.1,0.2,0.3],\"topK\":10}]";

        QueryVectorsFusionRequest request = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .knn(knnJson)
                .build();

        // the raw JSON knn is not a typed list, so the typed getter returns null
        assertThat(request.knn()).isNull();

        String jsonStr = "{\"indexName\":\"fusion-index\",\"knn\":" + knnJson + "}";
        assertRequestJson(request, jsonStr);
    }

    @Test
    public void testRetrieverFromInvalidJsonString() {
        assertThatThrownBy(() -> QueryVectorsFusionRequest.newBuilder().retriever("{invalid json"))
                .isInstanceOf(IllegalArgumentException.class);
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
                .retriever(Retriever.newBuilder()
                        .rrf(RrfRetriever.newBuilder()
                                .k(50)
                                .windowSize(100)
                                .retrievers(Arrays.asList(
                                        RrfRetrieverComponent.newBuilder()
                                                .retriever(Retriever.newBuilder()
                                                        .knn(Knn.newBuilder()
                                                                .field("text_vector")
                                                                .queryVector(Arrays.asList(0.12f, 0.53f, 0.08f))
                                                                .topK(100)
                                                                .build())
                                                        .build())
                                                .weight(2.0f)
                                                .build(),
                                        createRrfSimpleComponent(
                                                createTextMatchQuery("title", "无线 耳机", null),
                                                0.5f)))
                                .build())
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
                .retriever(Retriever.newBuilder()
                        .weight(WeightRetriever.newBuilder()
                                .windowSize(100)
                                .retrievers(Arrays.asList(
                                        WeightRetrieverComponent.newBuilder()
                                                .retriever(Retriever.newBuilder()
                                                        .knn(Knn.newBuilder()
                                                                .field("text_vector")
                                                                .queryVector(Arrays.asList(0.12f, 0.53f, 0.08f))
                                                                .topK(100)
                                                                .build())
                                                        .build())
                                                .weight(0.7f)
                                                .normalizer("minMax")
                                                .build(),
                                        createSimpleComponent(
                                                createTextMatchQuery("title", "无线 耳机", null),
                                                0.3f,
                                                "minMax")))
                                .build())
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
                .retriever(Retriever.newBuilder()
                        .weight(WeightRetriever.newBuilder()
                                .windowSize(200)
                                .retrievers(Arrays.asList(
                                        WeightRetrieverComponent.newBuilder()
                                                .retriever(Retriever.newBuilder()
                                                        .knn(Knn.newBuilder()
                                                                .field("text_vector")
                                                                .queryVector(Arrays.asList(0.12f, 0.53f, 0.08f))
                                                                .topK(200)
                                                                .build())
                                                        .build())
                                                .weight(0.5f)
                                                .normalizer("l2")
                                                .build(),
                                        WeightRetrieverComponent.newBuilder()
                                                .retriever(Retriever.newBuilder()
                                                        .knn(Knn.newBuilder()
                                                                .field("image_vector")
                                                                .queryVector(Arrays.asList(0.44f, 0.21f, 0.77f))
                                                                .topK(200)
                                                                .build())
                                                        .build())
                                                .weight(0.3f)
                                                .normalizer("l2")
                                                .build(),
                                        createSimpleComponent(
                                                createTextMatchQuery("title", "红色 跑车", null),
                                                0.2f,
                                                "minMax")))
                                .build())
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
                .retriever(Retriever.newBuilder()
                        .simple(SimpleRetriever.newBuilder()
                                .query(createTextMatchQuery("title", "hello world", 2.0f))
                                .build())
                .build())
                .limit(10)
                .build();

        String simpleJson = "{\"indexName\":\"kbindex\",\"retriever\":{\"simple\":{\"query\":{\"title\":{\"$textMatch\":{\"value\":\"hello world\",\"boost\":2.0}}}}},\"limit\":10}";
        assertRequestJson(simpleRequest, simpleJson);

        // knn leaf
        QueryVectorsFusionRequest knnRequest = QueryVectorsFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("kbindex")
                .retriever(Retriever.newBuilder()
                        .knn(Knn.newBuilder()
                                .field("chunk_vector")
                                .queryVector(Arrays.asList(0.12f, 0.53f, 0.08f))
                                .topK(100)
                                .build())
                .build())
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

    private WeightRetrieverComponent createKnnComponent(
            String field, List<Integer> queryVector, Float weight, String normalizer) {
        WeightRetrieverComponent.Builder builder = WeightRetrieverComponent.newBuilder()
                .retriever(Retriever.newBuilder()
                        .knn(Knn.newBuilder().field(field).queryVector(queryVector).build())
                        .build())
                .weight(weight);
        if (normalizer != null) {
            builder.normalizer(normalizer);
        }
        return builder.build();
    }

    private WeightRetrieverComponent createSimpleComponent(
            Map<String, Object> query, Float weight, String normalizer) {
        WeightRetrieverComponent.Builder builder = WeightRetrieverComponent.newBuilder()
                .retriever(Retriever.newBuilder()
                        .simple(SimpleRetriever.newBuilder().query(query).build())
                        .build())
                .weight(weight);
        if (normalizer != null) {
            builder.normalizer(normalizer);
        }
        return builder.build();
    }

    private RrfRetrieverComponent createRrfKnnComponent(
            String field, List<Integer> queryVector, Float weight) {
        return RrfRetrieverComponent.newBuilder()
                .retriever(Retriever.newBuilder()
                        .knn(Knn.newBuilder().field(field).queryVector(queryVector).build())
                        .build())
                .weight(weight)
                .build();
    }

    private RrfRetrieverComponent createRrfSimpleComponent(
            Map<String, Object> query, Float weight) {
        return RrfRetrieverComponent.newBuilder()
                .retriever(Retriever.newBuilder()
                        .simple(SimpleRetriever.newBuilder().query(query).build())
                        .build())
                .weight(weight)
                .build();
    }

    private Retriever createTestRetriever() {
        Map<String, Object> query = new HashMap<>();
        Map<String, Object> textMatch = new HashMap<>();
        textMatch.put("value", "hello world");
        textMatch.put("boost", 2.0f);
        Map<String, Object> titleCondition = new HashMap<>();
        titleCondition.put("$textMatch", textMatch);
        query.put("title", titleCondition);

        return Retriever.newBuilder()
                .rrf(RrfRetriever.newBuilder()
                        .k(50)
                        .windowSize(100)
                        .retrievers(Arrays.asList(
                                createRrfKnnComponent("vector", Arrays.asList(10, 22, 77), 1.0f),
                                createRrfSimpleComponent(query, 2.0f)))
                        .build())
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
