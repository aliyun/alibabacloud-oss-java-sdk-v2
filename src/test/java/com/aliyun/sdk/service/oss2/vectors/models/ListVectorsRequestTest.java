package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListVectorsRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListVectorsRequest request = ListVectorsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.listVectorsConfiguration()).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListVectorsRequest request = ListVectorsRequest.newBuilder()
                .bucket("examplebucket")
                .indexName("example-index")
                .maxResults(100)
                .nextToken("next-token-value")
                .returnData(true)
                .returnMetadata(false)
                .segmentCount(5)
                .segmentIndex(2)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");

        ListVectorsConfiguration config = request.listVectorsConfiguration();
        assertThat(config.indexName()).isEqualTo("example-index");
        assertThat(config.maxResults()).isEqualTo(100);
        assertThat(config.nextToken()).isEqualTo("next-token-value");
        assertThat(config.returnData()).isEqualTo(true);
        assertThat(config.returnMetadata()).isEqualTo(false);
        assertThat(config.segmentCount()).isEqualTo(5);
        assertThat(config.segmentIndex()).isEqualTo(2);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListVectorsRequest original = ListVectorsRequest.newBuilder()
                .bucket("examplebucket")
                .indexName("example-index")
                .maxResults(50)
                .nextToken("original-token")
                .returnData(false)
                .returnMetadata(true)
                .segmentCount(3)
                .segmentIndex(1)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        ListVectorsRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");

        ListVectorsConfiguration config = copy.listVectorsConfiguration();
        assertThat(config.indexName()).isEqualTo("example-index");
        assertThat(config.maxResults()).isEqualTo(50);
        assertThat(config.nextToken()).isEqualTo("original-token");
        assertThat(config.returnData()).isEqualTo(false);
        assertThat(config.returnMetadata()).isEqualTo(true);
        assertThat(config.segmentCount()).isEqualTo(3);
        assertThat(config.segmentIndex()).isEqualTo(1);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testListVectorsConfigurationDirectSetter() {
        ListVectorsConfiguration config = ListVectorsConfiguration.newBuilder()
                .indexName("direct-index")
                .maxResults(200)
                .nextToken("direct-token")
                .returnData(true)
                .returnMetadata(true)
                .segmentCount(10)
                .segmentIndex(0)
                .build();

        ListVectorsRequest request = ListVectorsRequest.newBuilder()
                .bucket("config-bucket")
                .listVectorsConfiguration(config)
                .build();

        assertThat(request.bucket()).isEqualTo("config-bucket");
        assertThat(request.listVectorsConfiguration()).isSameAs(config);
        assertThat(request.listVectorsConfiguration().indexName()).isEqualTo("direct-index");
        assertThat(request.listVectorsConfiguration().maxResults()).isEqualTo(200);
        assertThat(request.listVectorsConfiguration().nextToken()).isEqualTo("direct-token");
        assertThat(request.listVectorsConfiguration().returnData()).isEqualTo(true);
        assertThat(request.listVectorsConfiguration().returnMetadata()).isEqualTo(true);
        assertThat(request.listVectorsConfiguration().segmentCount()).isEqualTo(10);
        assertThat(request.listVectorsConfiguration().segmentIndex()).isEqualTo(0);
    }

    @Test
    public void xmlBuilder() throws Exception {
        String jsonStr = "{\"indexName\": \"test-index\", \"maxResults\": 100, \"nextToken\": \"test-token\", \"returnData\": true, \"returnMetadata\": false, \"segmentCount\": 5, \"segmentIndex\": 2}";

        ListVectorsRequest request = ListVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .maxResults(100)
                .nextToken("test-token")
                .returnData(true)
                .returnMetadata(false)
                .segmentCount(5)
                .segmentIndex(2)
                .build();

        // Use SerdeVectorsBasic.fromListVectors to convert request to OperationInput
        OperationInput operationInput = SerdeVectorsBasic.fromListVectors(request);

        // Validate the operation input
        assertThat(operationInput).isNotNull();

        assertThat(operationInput.bucket()).isPresent();
        assertThat(operationInput.bucket().get()).isEqualTo("test-bucket");
        assertThat(operationInput.parameters()).containsEntry("listVectors", "");
        assertThat(operationInput.headers()).containsEntry("Content-Type", "application/json");
        assertThat(operationInput.method()).isEqualTo("POST");
        assertThat(operationInput.opName()).isEqualTo("ListVectors");

        // Parse and compare JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJsonNode = objectMapper.readTree(jsonStr);
        JsonNode actualJsonNode = objectMapper.readTree(operationInput.body().get().toBytes());

        assertThat(actualJsonNode.get("indexName").asText()).isEqualTo(expectedJsonNode.get("indexName").asText());
        assertThat(actualJsonNode.get("maxResults").asInt()).isEqualTo(expectedJsonNode.get("maxResults").asInt());
        assertThat(actualJsonNode.get("nextToken").asText()).isEqualTo(expectedJsonNode.get("nextToken").asText());
        assertThat(actualJsonNode.get("returnData").asBoolean()).isEqualTo(expectedJsonNode.get("returnData").asBoolean());
        assertThat(actualJsonNode.get("returnMetadata").asBoolean()).isEqualTo(expectedJsonNode.get("returnMetadata").asBoolean());
        assertThat(actualJsonNode.get("segmentCount").asInt()).isEqualTo(expectedJsonNode.get("segmentCount").asInt());
        assertThat(actualJsonNode.get("segmentIndex").asInt()).isEqualTo(expectedJsonNode.get("segmentIndex").asInt());
    }
}