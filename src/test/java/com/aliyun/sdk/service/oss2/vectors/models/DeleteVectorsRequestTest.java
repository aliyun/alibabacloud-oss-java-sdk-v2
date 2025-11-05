package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteVectorsRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteVectorsRequest request = DeleteVectorsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.indexName()).isNull();
        assertThat(request.keys()).isNull();
    }

    @Test
    public void testFullBuilder() {
        List<String> keys = Arrays.asList("key1", "key2", "key3");

        DeleteVectorsRequest request = DeleteVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .keys(keys)
                .header("x-oss-request-id", "req-1234567890abcdefg")
                .parameter("param1", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.indexName()).isEqualTo("test-index");
        assertThat(request.keys()).isEqualTo(keys);
        assertThat(request.headers()).containsEntry("x-oss-request-id", "req-1234567890abcdefg");
        assertThat(request.parameters()).containsEntry("param1", "value1");
    }

    @Test
    public void testToBuilderPreserveState() {
        List<String> keys = Arrays.asList("key1", "key2", "key3");

        DeleteVectorsRequest original = DeleteVectorsRequest.newBuilder()
                .bucket("testbucket")
                .indexName("original-index")
                .keys(keys)
                .header("x-oss-request-id", "req-765432109876543210")
                .parameter("param3", "value3")
                .build();

        DeleteVectorsRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.indexName()).isEqualTo("original-index");
        assertThat(copy.keys()).isEqualTo(keys);
        assertThat(copy.headers()).containsEntry("x-oss-request-id", "req-765432109876543210");
        assertThat(copy.parameters()).containsEntry("param3", "value3");
    }

    @Test
    public void xmlBuilder() throws Exception {
        String jsonStr = "{\"indexName\": \"test-index\", \"keys\": [\"key1\", \"key2\", \"key3\"]}";

        List<String> keys = Arrays.asList("key1", "key2", "key3");

        DeleteVectorsRequest request = DeleteVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .keys(keys)
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeVectorsBasic.fromDeleteVectors(request);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJsonNode = objectMapper.readTree(jsonStr);
        JsonNode actualJsonNode = objectMapper.readTree(input.body().get().toBytes());

        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.headers()).containsEntry("Content-Type", "application/json");
        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.opName()).isEqualTo("DeleteVectors");
        assertThat(actualJsonNode.get("indexName").asText()).isEqualTo(expectedJsonNode.get("indexName").asText());

        // Verify keys array
        JsonNode expectedKeys = expectedJsonNode.get("keys");
        JsonNode actualKeys = actualJsonNode.get("keys");
        assertThat(actualKeys).hasSameSizeAs(expectedKeys);

        for (int i = 0; i < expectedKeys.size(); i++) {
            assertThat(actualKeys.get(i).asText()).isEqualTo(expectedKeys.get(i).asText());
        }
    }
}