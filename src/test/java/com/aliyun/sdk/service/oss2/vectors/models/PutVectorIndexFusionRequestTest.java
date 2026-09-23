package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorIndexBasic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PutVectorIndexFusionRequestTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testEmptyBuilder() {
        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.indexName()).isNull();
        assertThat(request.mode()).isNull();
        assertThat(request.schemaConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .mode("fusion")
                .schemaConfiguration(createTestSchemaConfiguration())
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.indexName()).isEqualTo("fusion-index");
        assertThat(request.mode()).isEqualTo("fusion");
        assertThat(request.schemaConfiguration()).isNotNull();
        assertThat(request.schemaConfiguration().fields()).hasSize(4);
        assertThat(request.schemaConfiguration().fields().get(0).name()).isEqualTo("vector_1");
        assertThat(request.schemaConfiguration().fields().get(0).type()).isEqualTo("vector");
        assertThat(request.schemaConfiguration().fields().get(0).dataType()).isEqualTo("float32");
        assertThat(request.schemaConfiguration().fields().get(0).dimension()).isEqualTo(1024);
        assertThat(request.schemaConfiguration().fields().get(0).distanceMetric()).isEqualTo("euclidean");
        assertThat(request.schemaConfiguration().fields().get(1).isArray()).isTrue();
        assertThat(request.schemaConfiguration().fields().get(2).isPartitionKey()).isTrue();
        assertThat(request.schemaConfiguration().fields().get(3).exactMatch()).isTrue();
        assertThat(request.schemaConfiguration().fields().get(3).text()).isNotNull();
        assertThat(request.schemaConfiguration().fields().get(3).text().enabled()).isTrue();
        assertThat(request.schemaConfiguration().fields().get(3).text().analyzer()).isEqualTo("standard");
        assertThat(request.schemaConfiguration().fields().get(3).text().analyzerParameters().caseSensitive()).isTrue();
        assertThat(request.schemaConfiguration().fields().get(3).text().analyzerParameters().delimitWord()).isFalse();

        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-oss-header1", "header-value1"),
                new AbstractMap.SimpleEntry<>("x-oss-header2", "header-value2")
        );

        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        PutVectorIndexFusionRequest original = PutVectorIndexFusionRequest.newBuilder()
                .bucket("original-bucket")
                .indexName("original-index")
                .mode("fusion")
                .schemaConfiguration(createTestSchemaConfiguration())
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        PutVectorIndexFusionRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("original-bucket");
        assertThat(copy.indexName()).isEqualTo("original-index");
        assertThat(copy.mode()).isEqualTo("fusion");
        assertThat(copy.schemaConfiguration()).isNotNull();
        assertThat(copy.schemaConfiguration().fields()).hasSize(4);

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void bodyBuilder() {
        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("body-test-bucket")
                .indexName("body-test-index")
                .schemaConfiguration(createTestSchemaConfiguration())
                .build();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndexFusion(request);

        assertThat(input.bucket().get()).isEqualTo("body-test-bucket");
        // Note: indexName is part of the configuration, not a separate parameter
        assertThat(input.parameters().get("indexName")).isNull();
        assertThat(input.parameters()).containsEntry("putVectorIndexFusion", "");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String jsonStr = "{\"indexName\":\"fusion-index\",\"mode\":\"fusion\",\"schemaConfiguration\":{\"fields\":["
                + "{\"name\":\"vector_1\",\"type\":\"vector\",\"dataType\":\"float32\",\"dimension\":1024,\"distanceMetric\":\"euclidean\"},"
                + "{\"name\":\"timestamps\",\"type\":\"long\",\"isArray\":true},"
                + "{\"name\":\"user_id\",\"type\":\"string\",\"isPartitionKey\":true},"
                + "{\"name\":\"title_1\",\"type\":\"string\",\"exactMatch\":true,\"text\":{\"enabled\":true,\"analyzer\":\"standard\","
                + "\"analyzerParameters\":{\"caseSensitive\":true,\"delimitWord\":false}}}]}}";

        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .mode("fusion")
                .schemaConfiguration(createTestSchemaConfiguration())
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndexFusion(request);

        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toString());

        assertThat(input.bucket()).isPresent();
        assertThat(input.bucket()).hasValue("test-bucket");
        assertThat(input.headers()).containsEntry("Content-Type", "application/json");
        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.parameters()).containsEntry("putVectorIndexFusion", "");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.opName()).isEqualTo("PutVectorIndexFusion");
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    @Test
    public void testDefaultModeWhenAbsent() throws JsonProcessingException {
        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .schemaConfiguration(createTestSchemaConfiguration())
                .build();

        // the request itself is not modified
        assertThat(request.mode()).isNull();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndexFusion(request);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toString());

        assertThat(actualNode.get("mode").asText()).isEqualTo("fusion");
        assertThat(request.mode()).isNull();
    }

    @Test
    public void testSchemaConfigurationFromJsonString() throws JsonProcessingException {
        // The nested schema is provided as a raw JSON string and passed through as-is. Fields that
        // have no strongly-typed model yet (futureParam) are still serialized verbatim.
        String schemaJson = "{\"fields\":[{\"name\":\"embedding\",\"type\":\"vector\",\"dataType\":\"float32\","
                + "\"dimension\":4,\"distanceMetric\":\"cosine\",\"futureParam\":\"x\"}]}";

        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .mode("fusion")
                .schemaConfiguration(schemaJson)
                .build();

        // the raw JSON schema is not a typed SchemaConfiguration, so the typed getter returns null
        assertThat(request.schemaConfiguration()).isNull();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndexFusion(request);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toString());

        String jsonStr = "{\"indexName\":\"fusion-index\",\"mode\":\"fusion\",\"schemaConfiguration\":" + schemaJson + "}";
        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    @Test
    public void testSchemaConfigurationFromInvalidJsonString() {
        assertThatThrownBy(() -> PutVectorIndexFusionRequest.newBuilder().schemaConfiguration("{invalid json"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFieldSchemaJsonNames() throws JsonProcessingException {
        FieldSchema fieldSchema = FieldSchema.newBuilder()
                .name("user_id")
                .type("string")
                .isArray(true)
                .isPartitionKey(true)
                .build();

        JsonNode node = OBJECT_MAPPER.readTree(OBJECT_MAPPER.writeValueAsString(fieldSchema));

        assertThat(node.has("isArray")).isTrue();
        assertThat(node.has("isPartitionKey")).isTrue();
        assertThat(node.has("array")).isFalse();
        assertThat(node.has("partitionKey")).isFalse();
        assertThat(node.get("isArray").asBoolean()).isTrue();
        assertThat(node.get("isPartitionKey").asBoolean()).isTrue();

        FieldSchema parsed = OBJECT_MAPPER.treeToValue(node, FieldSchema.class);
        assertThat(parsed.name()).isEqualTo("user_id");
        assertThat(parsed.type()).isEqualTo("string");
        assertThat(parsed.isArray()).isTrue();
        assertThat(parsed.isPartitionKey()).isTrue();
    }

    @Test
    public void testFieldSchemaToBuilderPreserveState() {
        FieldSchema original = FieldSchema.newBuilder()
                .name("vector_1")
                .type("vector")
                .dataType("float32")
                .dimension(1024)
                .distanceMetric("euclidean")
                .isArray(true)
                .isPartitionKey(false)
                .exactMatch(true)
                .text(TextSchema.newBuilder()
                        .enabled(true)
                        .analyzer("standard")
                        .analyzerParameters(AnalyzerParameters.newBuilder()
                                .caseSensitive(true)
                                .delimitWord(false)
                                .delimiter(",")
                                .build())
                        .build())
                .build();

        FieldSchema copy = original.toBuilder().build();

        assertThat(copy.name()).isEqualTo("vector_1");
        assertThat(copy.type()).isEqualTo("vector");
        assertThat(copy.dataType()).isEqualTo("float32");
        assertThat(copy.dimension()).isEqualTo(1024);
        assertThat(copy.distanceMetric()).isEqualTo("euclidean");
        assertThat(copy.isArray()).isTrue();
        assertThat(copy.isPartitionKey()).isFalse();
        assertThat(copy.exactMatch()).isTrue();
        assertThat(copy.text().enabled()).isTrue();
        assertThat(copy.text().analyzer()).isEqualTo("standard");
        assertThat(copy.text().analyzerParameters().caseSensitive()).isTrue();
        assertThat(copy.text().analyzerParameters().delimitWord()).isFalse();
        assertThat(copy.text().analyzerParameters().delimiter()).isEqualTo(",");
    }

    @Test
    public void testSchemaConfigurationEmptyBuilder() {
        SchemaConfiguration schemaConfiguration = SchemaConfiguration.newBuilder().build();
        assertThat(schemaConfiguration).isNotNull();
        assertThat(schemaConfiguration.fields()).isNull();

        AnalyzerParameters analyzerParameters = AnalyzerParameters.newBuilder().build();
        assertThat(analyzerParameters.caseSensitive()).isNull();
        assertThat(analyzerParameters.delimitWord()).isNull();
        assertThat(analyzerParameters.delimiter()).isNull();
    }

    @Test
    public void testEnumOverloads() throws JsonProcessingException {
        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("fusion-index")
                .mode(IndexModeType.FUSION)
                .schemaConfiguration(SchemaConfiguration.newBuilder()
                        .fields(Arrays.asList(
                                FieldSchema.newBuilder()
                                        .name("vector_1")
                                        .type(FieldType.VECTOR)
                                        .dataType(VectorDataType.FLOAT32)
                                        .dimension(1024)
                                        .distanceMetric(DistanceMetricType.EUCLIDEAN)
                                        .build(),
                                FieldSchema.newBuilder()
                                        .name("title_1")
                                        .type(FieldType.STRING)
                                        .exactMatch(true)
                                        .text(TextSchema.newBuilder()
                                                .enabled(true)
                                                .analyzer(AnalyzerType.STANDARD)
                                                .build())
                                        .build()))
                        .build())
                .build();

        // the enum overloads store the serialized string value, getters still return String
        assertThat(request.mode()).isEqualTo("fusion");
        FieldSchema vectorField = request.schemaConfiguration().fields().get(0);
        assertThat(vectorField.type()).isEqualTo("vector");
        assertThat(vectorField.dataType()).isEqualTo("float32");
        assertThat(vectorField.distanceMetric()).isEqualTo("euclidean");
        FieldSchema titleField = request.schemaConfiguration().fields().get(1);
        assertThat(titleField.type()).isEqualTo("string");
        assertThat(titleField.text().analyzer()).isEqualTo("standard");

        // the serialized JSON matches the string based builder
        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndexFusion(request);
        JsonNode node = OBJECT_MAPPER.readTree(input.body().get().toString());
        assertThat(node.get("mode").asText()).isEqualTo("fusion");
        JsonNode fields = node.get("schemaConfiguration").get("fields");
        assertThat(fields.get(0).get("type").asText()).isEqualTo("vector");
        assertThat(fields.get(0).get("dataType").asText()).isEqualTo("float32");
        assertThat(fields.get(0).get("distanceMetric").asText()).isEqualTo("euclidean");
        assertThat(fields.get(1).get("type").asText()).isEqualTo("string");
        assertThat(fields.get(1).get("text").get("analyzer").asText()).isEqualTo("standard");
    }

    /**
     * A2 from the doc: e-commerce product index with two vector fields, scalar fields and a
     * full-text title field.
     */
    @Test
    public void testProductIndexSchemaFromDoc() throws JsonProcessingException {
        String jsonStr = "{\"indexName\":\"productindex\",\"mode\":\"fusion\",\"schemaConfiguration\":{\"fields\":["
                + "{\"name\":\"text_vector\",\"type\":\"vector\",\"dataType\":\"float32\",\"dimension\":768,\"distanceMetric\":\"euclidean\"},"
                + "{\"name\":\"image_vector\",\"type\":\"vector\",\"dataType\":\"float32\",\"dimension\":512,\"distanceMetric\":\"euclidean\"},"
                + "{\"name\":\"title\",\"type\":\"string\",\"exactMatch\":true,\"text\":{\"enabled\":true,\"analyzer\":\"standard\"}},"
                + "{\"name\":\"brand\",\"type\":\"string\"},"
                + "{\"name\":\"category\",\"type\":\"string\",\"isArray\":true},"
                + "{\"name\":\"price\",\"type\":\"double\"},"
                + "{\"name\":\"stock\",\"type\":\"long\"},"
                + "{\"name\":\"on_sale\",\"type\":\"bool\"},"
                + "{\"name\":\"is_flagship\",\"type\":\"long\"},"
                + "{\"name\":\"coupon_id\",\"type\":\"string\"}"
                + "]}}";

        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("productindex")
                .mode("fusion")
                .schemaConfiguration(SchemaConfiguration.newBuilder()
                        .fields(Arrays.asList(
                                FieldSchema.newBuilder()
                                        .name("text_vector").type("vector").dataType("float32")
                                        .dimension(768).distanceMetric("euclidean").build(),
                                FieldSchema.newBuilder()
                                        .name("image_vector").type("vector").dataType("float32")
                                        .dimension(512).distanceMetric("euclidean").build(),
                                FieldSchema.newBuilder()
                                        .name("title").type("string").exactMatch(true)
                                        .text(TextSchema.newBuilder()
                                                .enabled(true).analyzer("standard").build())
                                        .build(),
                                FieldSchema.newBuilder().name("brand").type("string").build(),
                                FieldSchema.newBuilder().name("category").type("string").isArray(true).build(),
                                FieldSchema.newBuilder().name("price").type("double").build(),
                                FieldSchema.newBuilder().name("stock").type("long").build(),
                                FieldSchema.newBuilder().name("on_sale").type("bool").build(),
                                FieldSchema.newBuilder().name("is_flagship").type("long").build(),
                                FieldSchema.newBuilder().name("coupon_id").type("string").build()))
                        .build())
                .build();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndexFusion(request);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toString());
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    /**
     * A2-min from the doc: a minimal e-commerce index with a single vector field and a few scalar
     * fields.
     */
    @Test
    public void testShopIndexSchemaFromDoc() throws JsonProcessingException {
        String jsonStr = "{\"indexName\":\"shopindex\",\"mode\":\"fusion\",\"schemaConfiguration\":{\"fields\":["
                + "{\"name\":\"product_vector\",\"type\":\"vector\",\"dataType\":\"float32\",\"dimension\":768,\"distanceMetric\":\"euclidean\"},"
                + "{\"name\":\"brand\",\"type\":\"string\"},"
                + "{\"name\":\"price\",\"type\":\"double\"},"
                + "{\"name\":\"on_sale\",\"type\":\"bool\"}"
                + "]}}";

        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("shopindex")
                .mode("fusion")
                .schemaConfiguration(SchemaConfiguration.newBuilder()
                        .fields(Arrays.asList(
                                FieldSchema.newBuilder()
                                        .name("product_vector").type("vector").dataType("float32")
                                        .dimension(768).distanceMetric("euclidean").build(),
                                FieldSchema.newBuilder().name("brand").type("string").build(),
                                FieldSchema.newBuilder().name("price").type("double").build(),
                                FieldSchema.newBuilder().name("on_sale").type("bool").build()))
                        .build())
                .build();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndexFusion(request);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toString());
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    /**
     * A3 from the doc: a multimodal index with three vector fields (text/image/video) and mixed
     * distance metrics, including Fusion-Mode-only {@code ip}.
     */
    @Test
    public void testMultimodalIndexSchemaFromDoc() throws JsonProcessingException {
        String jsonStr = "{\"indexName\":\"multimodalindex\",\"mode\":\"fusion\",\"schemaConfiguration\":{\"fields\":["
                + "{\"name\":\"text_vector\",\"type\":\"vector\",\"dataType\":\"float32\",\"dimension\":1024,\"distanceMetric\":\"cosine\"},"
                + "{\"name\":\"image_vector\",\"type\":\"vector\",\"dataType\":\"float32\",\"dimension\":512,\"distanceMetric\":\"euclidean\"},"
                + "{\"name\":\"video_vector\",\"type\":\"vector\",\"dataType\":\"float32\",\"dimension\":256,\"distanceMetric\":\"ip\"},"
                + "{\"name\":\"title\",\"type\":\"string\",\"exactMatch\":true,\"text\":{\"enabled\":true,\"analyzer\":\"standard\"}},"
                + "{\"name\":\"duration\",\"type\":\"long\"}"
                + "]}}";

        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("multimodalindex")
                .mode("fusion")
                .schemaConfiguration(SchemaConfiguration.newBuilder()
                        .fields(Arrays.asList(
                                FieldSchema.newBuilder()
                                        .name("text_vector").type("vector").dataType("float32")
                                        .dimension(1024).distanceMetric("cosine").build(),
                                FieldSchema.newBuilder()
                                        .name("image_vector").type("vector").dataType("float32")
                                        .dimension(512).distanceMetric("euclidean").build(),
                                FieldSchema.newBuilder()
                                        .name("video_vector").type("vector").dataType("float32")
                                        .dimension(256).distanceMetric("ip").build(),
                                FieldSchema.newBuilder()
                                        .name("title").type("string").exactMatch(true)
                                        .text(TextSchema.newBuilder()
                                                .enabled(true).analyzer("standard").build())
                                        .build(),
                                FieldSchema.newBuilder().name("duration").type("long").build()))
                        .build())
                .build();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndexFusion(request);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toString());
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    /**
     * A6 from the doc: a multi-tenant RAG index with a string partition key and a text field.
     */
    @Test
    public void testTenantKbIndexSchemaFromDoc() throws JsonProcessingException {
        String jsonStr = "{\"indexName\":\"tenantkbindex\",\"mode\":\"fusion\",\"schemaConfiguration\":{\"fields\":["
                + "{\"name\":\"chunk_vector\",\"type\":\"vector\",\"dataType\":\"float32\",\"dimension\":1024,\"distanceMetric\":\"cosine\"},"
                + "{\"name\":\"tenant_id\",\"type\":\"string\",\"isPartitionKey\":true},"
                + "{\"name\":\"doc_id\",\"type\":\"string\"},"
                + "{\"name\":\"content\",\"type\":\"string\",\"exactMatch\":false,\"text\":{\"enabled\":true,\"analyzer\":\"standard\"}},"
                + "{\"name\":\"updated_at\",\"type\":\"long\"}"
                + "]}}";

        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("tenantkbindex")
                .mode("fusion")
                .schemaConfiguration(SchemaConfiguration.newBuilder()
                        .fields(Arrays.asList(
                                FieldSchema.newBuilder()
                                        .name("chunk_vector").type("vector").dataType("float32")
                                        .dimension(1024).distanceMetric("cosine").build(),
                                FieldSchema.newBuilder()
                                        .name("tenant_id").type("string").isPartitionKey(true).build(),
                                FieldSchema.newBuilder().name("doc_id").type("string").build(),
                                FieldSchema.newBuilder()
                                        .name("content").type("string").exactMatch(false)
                                        .text(TextSchema.newBuilder()
                                                .enabled(true).analyzer("standard").build())
                                        .build(),
                                FieldSchema.newBuilder().name("updated_at").type("long").build()))
                        .build())
                .build();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndexFusion(request);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toString());
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    /**
     * A8 from the doc: a network/security log index with an {@code ip} field and a split analyzer
     * configured with a custom delimiter.
     */
    @Test
    public void testAccessLogIndexSchemaFromDoc() throws JsonProcessingException {
        String jsonStr = "{\"indexName\":\"accesslogindex\",\"mode\":\"fusion\",\"schemaConfiguration\":{\"fields\":["
                + "{\"name\":\"log_vector\",\"type\":\"vector\",\"dataType\":\"float32\",\"dimension\":256,\"distanceMetric\":\"euclidean\"},"
                + "{\"name\":\"client_ip\",\"type\":\"ip\"},"
                + "{\"name\":\"x_forwarded_ips\",\"type\":\"ip\",\"isArray\":true},"
                + "{\"name\":\"status_code\",\"type\":\"long\"},"
                + "{\"name\":\"request_time_ms\",\"type\":\"long\"},"
                + "{\"name\":\"request_path\",\"type\":\"string\",\"exactMatch\":false,\"text\":{\"enabled\":true,\"analyzer\":\"split\",\"analyzerParameters\":{\"caseSensitive\":false,\"delimiter\":\"/\"}}}"
                + "]}}";

        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("accesslogindex")
                .mode("fusion")
                .schemaConfiguration(SchemaConfiguration.newBuilder()
                        .fields(Arrays.asList(
                                FieldSchema.newBuilder()
                                        .name("log_vector").type("vector").dataType("float32")
                                        .dimension(256).distanceMetric("euclidean").build(),
                                FieldSchema.newBuilder().name("client_ip").type("ip").build(),
                                FieldSchema.newBuilder().name("x_forwarded_ips").type("ip").isArray(true).build(),
                                FieldSchema.newBuilder().name("status_code").type("long").build(),
                                FieldSchema.newBuilder().name("request_time_ms").type("long").build(),
                                FieldSchema.newBuilder()
                                        .name("request_path").type("string").exactMatch(false)
                                        .text(TextSchema.newBuilder()
                                                .enabled(true).analyzer("split")
                                                .analyzerParameters(AnalyzerParameters.newBuilder()
                                                        .caseSensitive(false).delimiter("/").build())
                                                .build())
                                        .build()))
                        .build())
                .build();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndexFusion(request);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toString());
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    /**
     * A11 from the doc: a migration-friendly index whose single vector field is named {@code float32}
     * so that existing Standard-Mode write/query code keeps working.
     */
    @Test
    public void testMigratedIndexSchemaFromDoc() throws JsonProcessingException {
        String jsonStr = "{\"indexName\":\"migratedindex\",\"mode\":\"fusion\",\"schemaConfiguration\":{\"fields\":["
                + "{\"name\":\"float32\",\"type\":\"vector\",\"dataType\":\"float32\",\"dimension\":1024,\"distanceMetric\":\"euclidean\"},"
                + "{\"name\":\"category\",\"type\":\"string\",\"isArray\":true},"
                + "{\"name\":\"title\",\"type\":\"string\"},"
                + "{\"name\":\"year\",\"type\":\"long\"}"
                + "]}}";

        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("migratedindex")
                .mode("fusion")
                .schemaConfiguration(SchemaConfiguration.newBuilder()
                        .fields(Arrays.asList(
                                FieldSchema.newBuilder()
                                        .name("float32").type("vector").dataType("float32")
                                        .dimension(1024).distanceMetric("euclidean").build(),
                                FieldSchema.newBuilder().name("category").type("string").isArray(true).build(),
                                FieldSchema.newBuilder().name("title").type("string").build(),
                                FieldSchema.newBuilder().name("year").type("long").build()))
                        .build())
                .build();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndexFusion(request);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(jsonStr);
        JsonNode actualNode = OBJECT_MAPPER.readTree(input.body().get().toString());
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    private SchemaConfiguration createTestSchemaConfiguration() {
        List<FieldSchema> fields = Arrays.asList(
                FieldSchema.newBuilder()
                        .name("vector_1")
                        .type("vector")
                        .dataType("float32")
                        .dimension(1024)
                        .distanceMetric("euclidean")
                        .build(),
                FieldSchema.newBuilder()
                        .name("timestamps")
                        .type("long")
                        .isArray(true)
                        .build(),
                FieldSchema.newBuilder()
                        .name("user_id")
                        .type("string")
                        .isPartitionKey(true)
                        .build(),
                FieldSchema.newBuilder()
                        .name("title_1")
                        .type("string")
                        .exactMatch(true)
                        .text(TextSchema.newBuilder()
                                .enabled(true)
                                .analyzer("standard")
                                .analyzerParameters(AnalyzerParameters.newBuilder()
                                        .caseSensitive(true)
                                        .delimitWord(false)
                                        .build())
                                .build())
                        .build()
        );

        return SchemaConfiguration.newBuilder().fields(fields).build();
    }
}
