package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBucketConfigBasic;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteTableBucketPolicyRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteTableBucketPolicyRequest request = DeleteTableBucketPolicyRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteTableBucketPolicyRequest request = DeleteTableBucketPolicyRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket");

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
        DeleteTableBucketPolicyRequest original = DeleteTableBucketPolicyRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket")
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        DeleteTableBucketPolicyRequest copy = original.toBuilder().build();

        assertThat(copy.tableBucketARN()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void jsonBuilder() throws JsonProcessingException {
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        DeleteTableBucketPolicyRequest request = DeleteTableBucketPolicyRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableBucketConfigBasic.fromDeleteTableBucketPolicy(request);

        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("DELETE");
        assertThat(input.opName()).isEqualTo("DeleteTableBucketPolicy");

        // DELETE requests typically don't have body content
        assertThat(input.body().isPresent()).isFalse();
    }
}