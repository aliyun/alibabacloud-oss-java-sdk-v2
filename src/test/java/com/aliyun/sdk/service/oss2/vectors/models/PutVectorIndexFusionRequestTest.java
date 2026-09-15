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
