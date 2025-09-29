package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutVectorsRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutVectorsRequest request = PutVectorsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.vectorsConfiguration()).isNotNull();
        assertThat(request.vectorsConfiguration().indexName()).isNull();
        assertThat(request.vectorsConfiguration().vectors()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        // Test using Map<String, Object> type vector data
        Map<String, Object> vectorData = new HashMap<>();
        vectorData.put("float32", Arrays.asList(0.1f, 0.2f, 0.3f));

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", "value2");

        Map<String, Object> vectorMap = new HashMap<>();
        vectorMap.put("data", vectorData);
        vectorMap.put("key", "vector-key-1");
        vectorMap.put("metadata", metadata);

        List<Map<String, Object>> vectors = Arrays.asList(vectorMap);

        PutVectorsRequest request = PutVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .vectorsMap(vectors)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.vectorsConfiguration().indexName()).isEqualTo("test-index");
        assertThat(request.vectorsConfiguration().vectors()).isEqualTo(vectors);
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).containsEntry("param1", "value1");
        assertThat(request.parameters()).containsEntry("param2", "value2");
    }

    @Test
    public void testFullBuilderWithPutInputVector() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        // Test using PutInputVector type vector data
        Map<String, Object> vectorData = new HashMap<>();
        vectorData.put("float32", Arrays.asList(0.1f, 0.2f, 0.3f));

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", "value2");

        PutInputVector vector = PutInputVector.newBuilder()
                .data(vectorData)
                .key("vector-key-1")
                .metadata(metadata)
                .build();

        List<PutInputVector> vectors = Arrays.asList(vector);

        PutVectorsRequest request = PutVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .vectors(vectors)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.vectorsConfiguration().indexName()).isEqualTo("test-index");
        assertThat(request.vectorsConfiguration().vectors()).isNotNull();
        assertThat(request.vectorsConfiguration().vectors()).hasSize(1);
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).containsEntry("param1", "value1");
        assertThat(request.parameters()).containsEntry("param2", "value2");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        Map<String, Object> vectorData = new HashMap<>();
        vectorData.put("float32", Arrays.asList(0.4f, 0.5f, 0.6f));

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key3", "value3");
        metadata.put("key4", "value4");

        Map<String, Object> vectorMap = new HashMap<>();
        vectorMap.put("data", vectorData);
        vectorMap.put("key", "vector-key-2");
        vectorMap.put("metadata", metadata);

        List<Map<String, Object>> vectors = Arrays.asList(vectorMap);

        PutVectorsRequest original = PutVectorsRequest.newBuilder()
                .bucket("testbucket")
                .indexName("original-index")
                .vectorsMap(vectors)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutVectorsRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.vectorsConfiguration().indexName()).isEqualTo("original-index");
        assertThat(copy.vectorsConfiguration().vectors()).isEqualTo(vectors);
        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).containsEntry("param3", "value3");
        assertThat(copy.parameters()).containsEntry("param4", "value4");
    }

    @Test
    public void testHeaderProperties() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-meta-custom", "custom-value",
                "Cache-Control", "no-cache"
        );

        PutVectorsRequest request = PutVectorsRequest.newBuilder()
                .bucket("anotherbucket")
                .indexName("header-test-index")
                .headers(headers)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.vectorsConfiguration().indexName()).isEqualTo("header-test-index");
        assertThat(request.headers()).containsEntry("x-oss-meta-custom", "custom-value");
        assertThat(request.headers()).containsEntry("Cache-Control", "no-cache");
    }

    @Test
    public void xmlBuilder() throws Exception {
        String jsonStr = "{\"indexName\":\"test-index\",\"vectors\":[{\"data\":{\"float32\":[0.1,0.2,0.3]},\"key\":\"vector-key-1\",\"metadata\":{\"key1\":\"value1\",\"key2\":\"value2\"}}]}";

        Map<String, Object> vectorData = new HashMap<>();
        vectorData.put("float32", Arrays.asList(0.1f, 0.2f, 0.3f));

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", "value2");

        Map<String, Object> vectorMap = new HashMap<>();
        vectorMap.put("data", vectorData);
        vectorMap.put("key", "vector-key-1");
        vectorMap.put("metadata", metadata);

        List<Map<String, Object>> vectors = Arrays.asList(vectorMap);

        PutVectorsRequest request = PutVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .vectorsMap(vectors)
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeVectorsBasic.fromPutVectors(request);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJsonNode = objectMapper.readTree(jsonStr);
        JsonNode actualJsonNode = objectMapper.readTree(input.body().get().toBytes());

        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.headers()).containsEntry("Content-Type", "application/json");
        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.opName()).isEqualTo("PutVectors");
        assertThat(actualJsonNode.get("indexName").asText()).isEqualTo(expectedJsonNode.get("indexName").asText());

        // Verify vector array
        JsonNode expectedVectors = expectedJsonNode.get("vectors");
        JsonNode actualVectors = actualJsonNode.get("vectors");
        assertThat(actualVectors).hasSameSizeAs(expectedVectors);

        // Verify the first vector
        JsonNode expectedVector = expectedVectors.get(0);
        JsonNode actualVector = actualVectors.get(0);

        // Verify key
        assertThat(actualVector.get("key").asText()).isEqualTo(expectedVector.get("key").asText());

        // Verify data
        JsonNode expectedData = expectedVector.get("data");
        JsonNode actualData = actualVector.get("data");
        assertThat(actualData.get("float32")).isEqualTo(expectedData.get("float32"));

        // Verify metadata
        JsonNode expectedMetadata = expectedVector.get("metadata");
        JsonNode actualMetadata = actualVector.get("metadata");
        assertThat(actualMetadata.get("key1").asText()).isEqualTo(expectedMetadata.get("key1").asText());
        assertThat(actualMetadata.get("key2").asText()).isEqualTo(expectedMetadata.get("key2").asText());
    }
}