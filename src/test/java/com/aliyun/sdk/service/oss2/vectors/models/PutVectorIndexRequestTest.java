package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorIndexBasic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutVectorIndexRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutVectorIndexRequest request = PutVectorIndexRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.indexName()).isNull();
        assertThat(request.vectorIndexConfiguration()).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", 123);

        PutVectorIndexRequest request = PutVectorIndexRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .dataType("float32")
                .dimension(128)
                .distanceMetric("euclidean")
                .metadata(metadata)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.indexName()).isEqualTo("test-index");
        assertThat(request.vectorIndexConfiguration().dataType()).isEqualTo("float32");
        assertThat(request.vectorIndexConfiguration().dimension()).isEqualTo(128);
        assertThat(request.vectorIndexConfiguration().distanceMetric()).isEqualTo("euclidean");
        assertThat(request.vectorIndexConfiguration().metadata()).containsEntry("key1", "value1");
        assertThat(request.vectorIndexConfiguration().metadata()).containsEntry("key2", 123);

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
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");

        PutVectorIndexRequest original = PutVectorIndexRequest.newBuilder()
                .bucket("original-bucket")
                .indexName("original-index")
                .dataType("float32")
                .dimension(256)
                .distanceMetric("cosine")
                .metadata(metadata)
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        PutVectorIndexRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("original-bucket");
        assertThat(copy.indexName()).isEqualTo("original-index");
        assertThat(copy.vectorIndexConfiguration().dataType()).isEqualTo("float32");
        assertThat(copy.vectorIndexConfiguration().dimension()).isEqualTo(256);
        assertThat(copy.vectorIndexConfiguration().distanceMetric()).isEqualTo("cosine");
        assertThat(copy.vectorIndexConfiguration().metadata()).containsEntry("key1", "value1");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void bodyBuilder() throws JsonProcessingException {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("env", "test");
        metadata.put("version", "1.0");

        PutVectorIndexRequest request = PutVectorIndexRequest.newBuilder()
                .bucket("body-test-bucket")
                .indexName("body-test-index")
                .dataType("float32")
                .dimension(512)
                .distanceMetric("inner_product")
                .metadata(metadata)
                .build();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndex(request);

        assertThat(input.bucket().get()).isEqualTo("body-test-bucket");
        // Note: indexName is part of the configuration, not a separate parameter
        assertThat(input.parameters().get("indexName")).isNull();
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {

        String jsonStr = "{\"dataType\": \"vector\", \"dimension\": 128, \"distanceMetric\": \"EUCLIDEAN\", \"indexName\": \"test-index\", \"metadata\": {\"nonFilterableMetadataKeys\": [\"key1\", \"key2\"]}}";

        Map<String, Object> metadata = new HashMap<>();
        List<String> nonFilterableKeys = Arrays.asList("key1", "key2");
        metadata.put("nonFilterableMetadataKeys", nonFilterableKeys);

        PutVectorIndexRequest request = PutVectorIndexRequest.newBuilder()
                .bucket("test-bucket")
                .dataType("vector")
                .dimension(128)
                .distanceMetric("EUCLIDEAN")
                .indexName("test-index")
                .metadata(metadata)
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeVectorIndexBasic.fromPutVectorIndex(request);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonStr);
        String compactJson = objectMapper.writeValueAsString(jsonNode);

        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.headers()).containsEntry("Content-Type", "application/json");
        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.opName()).isEqualTo("PutVectorIndex");
        assertThat(input.body().get().toString()).isEqualTo(compactJson);
    }
}