package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorIndexBasic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListVectorIndexesRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListVectorIndexesRequest request = ListVectorIndexesRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.maxResults()).isNull();
        assertThat(request.nextToken()).isNull();
        assertThat(request.prefix()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListVectorIndexesRequest request = ListVectorIndexesRequest.newBuilder()
                .bucket("test-bucket")
                .maxResults(100L)
                .nextToken("test-token")
                .prefix("test-")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.maxResults()).isEqualTo(100);
        assertThat(request.nextToken()).isEqualTo("test-token");
        assertThat(request.prefix()).isEqualTo("test-");
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

        ListVectorIndexesRequest original = ListVectorIndexesRequest.newBuilder()
                .bucket("testbucket")
                .maxResults(50L)
                .nextToken("original-token")
                .prefix("original-")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        ListVectorIndexesRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.maxResults()).isEqualTo(50);
        assertThat(copy.nextToken()).isEqualTo("original-token");
        assertThat(copy.prefix()).isEqualTo("original-");
        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).containsEntry("param3", "value3");
        assertThat(copy.parameters()).containsEntry("param4", "value4");
    }

    @Test
    public void testHeaderProperties() {
        ListVectorIndexesRequest request = ListVectorIndexesRequest.newBuilder()
                .bucket("anotherbucket")
                .header("x-oss-meta-custom", "custom-value")
                .header("Cache-Control", "no-cache")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.headers()).containsEntry("x-oss-meta-custom", "custom-value");
        assertThat(request.headers()).containsEntry("Cache-Control", "no-cache");
    }

    @Test
    public void xmlBuilder() throws Exception {
        String jsonStr = "{\"maxResults\": 100, \"nextToken\": \"test-token\", \"prefix\": \"test-\"}";

        ListVectorIndexesRequest request = ListVectorIndexesRequest.newBuilder()
                .bucket("test-bucket")
                .maxResults(100L)
                .nextToken("test-token")
                .prefix("test-")
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeVectorIndexBasic.fromListVectorIndexes(request);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonStr);
        String compactJson = objectMapper.writeValueAsString(jsonNode);

        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.headers()).containsEntry("Content-Type", "application/json");
        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.opName()).isEqualTo("ListVectorIndexes");
        assertThat(input.body().get().toString()).isEqualTo(compactJson);
    }
}