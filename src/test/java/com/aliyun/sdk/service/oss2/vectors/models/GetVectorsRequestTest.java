package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetVectorsRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetVectorsRequest request = GetVectorsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.indexName()).isNull();
        assertThat(request.keys()).isNull();
        assertThat(request.returnData()).isNull();
        assertThat(request.returnMetadata()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        List<String> keys = Arrays.asList("key1", "key2");

        GetVectorsRequest request = GetVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .keys(keys)
                .returnData(true)
                .returnMetadata(false)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.indexName()).isEqualTo("test-index");
        assertThat(request.keys()).isEqualTo(keys);
        assertThat(request.returnData()).isEqualTo(true);
        assertThat(request.returnMetadata()).isEqualTo(false);
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

        List<String> keys = Arrays.asList("key3", "key4");

        GetVectorsRequest original = GetVectorsRequest.newBuilder()
                .bucket("testbucket")
                .indexName("original-index")
                .keys(keys)
                .returnData(false)
                .returnMetadata(true)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        GetVectorsRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.indexName()).isEqualTo("original-index");
        assertThat(copy.keys()).isEqualTo(keys);
        assertThat(copy.returnData()).isEqualTo(false);
        assertThat(copy.returnMetadata()).isEqualTo(true);
        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).containsEntry("param3", "value3");
        assertThat(copy.parameters()).containsEntry("param4", "value4");
    }

    @Test
    public void testHeaderProperties() {
        GetVectorsRequest request = GetVectorsRequest.newBuilder()
                .bucket("anotherbucket")
                .indexName("header-test-index")
                .header("x-oss-meta-custom", "custom-value")
                .header("Cache-Control", "no-cache")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.indexName()).isEqualTo("header-test-index");
        assertThat(request.headers()).containsEntry("x-oss-meta-custom", "custom-value");
        assertThat(request.headers()).containsEntry("Cache-Control", "no-cache");
    }

    @Test
    public void xmlBuilder() throws Exception {
        String jsonStr = "{\"returnMetadata\":false,\"indexName\":\"test-index\",\"keys\":[\"key1\",\"key2\"],\"returnData\":true}";

        List<String> keys = Arrays.asList("key1", "key2");

        GetVectorsRequest request = GetVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .keys(keys)
                .returnData(true)
                .returnMetadata(false)
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeVectorsBasic.fromGetVectors(request);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonStr);
        String compactJson = objectMapper.writeValueAsString(jsonNode);

        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.headers()).containsEntry("Content-Type", "application/json");
        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.opName()).isEqualTo("GetVectors");
        assertThat(input.body().get().toString()).isEqualTo(compactJson);
    }
}