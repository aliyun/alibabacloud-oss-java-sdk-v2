package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.vectors.transform.SerdeJsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SchemaConfigurationTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testFieldSchemaToMapMatchesSerialization() {
        FieldSchema field = FieldSchema.newBuilder()
                .name("title_1")
                .type("string")
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

        // the map form serializes to exactly the same bytes as the typed object
        assertThat(SerdeJsonUtils.toJson(field.toMap()).toBytes())
                .isEqualTo(SerdeJsonUtils.toJson(field).toBytes());
    }

    @Test
    public void testTextSchemaToMapMatchesSerialization() {
        TextSchema text = TextSchema.newBuilder()
                .enabled(true)
                .analyzer("split")
                .analyzerParameters(AnalyzerParameters.newBuilder()
                        .caseSensitive(false)
                        .delimitWord(false)
                        .delimiter("/")
                        .build())
                .build();

        assertThat(SerdeJsonUtils.toJson(text.toMap()).toBytes())
                .isEqualTo(SerdeJsonUtils.toJson(text).toBytes());
    }

    @Test
    public void testAnalyzerParametersToMapMatchesSerialization() {
        AnalyzerParameters parameters = AnalyzerParameters.newBuilder()
                .caseSensitive(true)
                .delimitWord(false)
                .delimiter(";")
                .build();

        assertThat(SerdeJsonUtils.toJson(parameters.toMap()).toBytes())
                .isEqualTo(SerdeJsonUtils.toJson(parameters).toBytes());
    }

    @Test
    public void testToMapOmitsUnsetAttributes() {
        FieldSchema field = FieldSchema.newBuilder().name("brand").type("string").build();

        assertThat(field.toMap())
                .containsEntry("name", "brand")
                .containsEntry("type", "string")
                .doesNotContainKeys("dataType", "dimension", "distanceMetric", "isArray",
                        "isPartitionKey", "exactMatch", "text");
    }

    @Test
    public void testRawFieldsPreservedVerbatim() {
        // a field carrying an attribute the SDK does not model yet
        String rawField = "{\"name\":\"embedding\",\"type\":\"vector\",\"futureParam\":\"x\"}";

        SchemaConfiguration schema = SchemaConfiguration.newBuilder()
                .fields(Arrays.asList(OBJECT_MAPPER.convertValue(parse(rawField), Map.class)))
                .build();

        assertThat(schema.fields()).hasSize(1);
        assertThat(SerdeJsonUtils.toJson(schema).toString()).contains("\"futureParam\":\"x\"");
    }

    @Test
    public void testFieldSchemasDropsUnmodeledAttributes() {
        // the raw view keeps everything ...
        SchemaConfiguration schema = SchemaConfiguration.newBuilder()
                .fields(Arrays.asList(OBJECT_MAPPER.convertValue(
                        parse("{\"name\":\"embedding\",\"type\":\"vector\",\"futureParam\":\"x\"}"), Map.class)))
                .build();

        // ... while the typed view exposes only the attributes the SDK models
        FieldSchema typed = schema.fieldSchemas().get(0);
        assertThat(typed.name()).isEqualTo("embedding");
        assertThat(typed.type()).isEqualTo("vector");
        assertThat(typed.dataType()).isNull();
        assertThat(typed.toMap()).doesNotContainKey("futureParam");
    }

    @Test
    public void testUnknownAttributesOutsideFieldsArePreserved() {
        // a raw schemaConfiguration Map passes through verbatim, so an attribute next to
        // "fields" that the SDK does not model is kept instead of being dropped
        Map<String, Object> schema = new LinkedHashMap<>();
        schema.put("fields", Arrays.asList(OBJECT_MAPPER.convertValue(
                parse("{\"name\":\"v\",\"type\":\"vector\"}"), Map.class)));
        schema.put("futureTop", 123);
        PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                .bucket("test-bucket")
                .bodyField("schemaConfiguration", schema)
                .build();

        assertThat(request.bodyFields().get("schemaConfiguration")).isSameAs(schema);
    }

    private static Object parse(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, Object.class);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
