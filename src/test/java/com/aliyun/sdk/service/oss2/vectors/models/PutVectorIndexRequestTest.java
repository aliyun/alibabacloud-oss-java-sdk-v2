package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.VectorIndexConfigurationJson;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorIndexBasic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

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
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.vectorIndexConfigurationJson().dataType).isEqualTo("vector");
        assertThat(request.vectorIndexConfigurationJson().dimension).isEqualTo(128);
        assertThat(request.vectorIndexConfigurationJson().distanceMetric).isEqualTo("EUCLIDEAN");
        assertThat(request.vectorIndexConfigurationJson().indexName).isEqualTo("test-index");
        assertThat(request.vectorIndexConfigurationJson().metadata).isEqualTo(metadata);
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).containsEntry("param1", "value1");
        assertThat(request.parameters()).containsEntry("param2", "value2");
    }

    @Test
    public void testToBuilderPreserveState() throws JsonProcessingException {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        Map<String, Object> metadata = new HashMap<>();
        List<String> nonFilterableKeys = Arrays.asList("key3", "key4");
        metadata.put("nonFilterableMetadataKeys", nonFilterableKeys);

        ObjectMapper objectMapper = new ObjectMapper();
        String metadataJson = objectMapper.writeValueAsString(metadata);

        PutVectorIndexRequest original = PutVectorIndexRequest.newBuilder()
                .bucket("testbucket")
                .dataType("vector")
                .dimension(256)
                .distanceMetric("COSINE")
                .indexName("original-index")
                .metadata(metadata)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutVectorIndexRequest copy = original.toBuilder().build();

        String copyMetadataJson = objectMapper.writeValueAsString(copy.vectorIndexConfigurationJson().metadata);

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.vectorIndexConfigurationJson().dataType).isEqualTo("vector");
        assertThat(copy.vectorIndexConfigurationJson().dimension).isEqualTo(256);
        assertThat(copy.vectorIndexConfigurationJson().distanceMetric).isEqualTo("COSINE");
        assertThat(copy.vectorIndexConfigurationJson().indexName).isEqualTo("original-index");
        assertThat(copyMetadataJson).isEqualTo(metadataJson);
        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).containsEntry("param3", "value3");
        assertThat(copy.parameters()).containsEntry("param4", "value4");
    }

    @Test
    public void testHeaderProperties() {
        PutVectorIndexRequest request = PutVectorIndexRequest.newBuilder()
                .bucket("anotherbucket")
                .indexName("header-test-index")
                .header("x-oss-meta-custom", "custom-value")
                .header("Cache-Control", "no-cache")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.vectorIndexConfigurationJson().indexName).isEqualTo("header-test-index");
        assertThat(request.headers()).containsEntry("x-oss-meta-custom", "custom-value");
        assertThat(request.headers()).containsEntry("Cache-Control", "no-cache");
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
