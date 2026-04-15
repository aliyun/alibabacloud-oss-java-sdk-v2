package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableConfigBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class PutTablePolicyRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutTablePolicyRequest request = PutTablePolicyRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.namespace()).isNull();
        assertThat(request.name()).isNull();
        assertThat(request.resourcePolicy()).isNull();
    }

    @Test
    public void testFullBuilder() {
        String policy = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetTable\"],\"Effect\":\"Deny\",\"Principal\":[\"1234567890\"],\"Resource\":[\"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/my-table\"]}]}";

        PutTablePolicyRequest request = PutTablePolicyRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .namespace("test-namespace")
                .name("test-table")
                .resourcePolicy(policy)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket");
        assertThat(request.namespace()).isEqualTo("test-namespace");
        assertThat(request.name()).isEqualTo("test-table");
        assertThat(request.resourcePolicy()).isEqualTo(policy);

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
        String policy = "test-policy-content";

        PutTablePolicyRequest original = PutTablePolicyRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket")
                .namespace("original-namespace")
                .name("original-table")
                .resourcePolicy(policy)
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        PutTablePolicyRequest copy = original.toBuilder().build();

        assertThat(copy.tableBucketARN()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket");
        assertThat(copy.namespace()).isEqualTo("original-namespace");
        assertThat(copy.name()).isEqualTo("original-table");
        assertThat(copy.resourcePolicy()).isEqualTo(policy);

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String policy = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetTable\"],\"Effect\":\"Allow\",\"Principal\":[\"9876543210\"],\"Resource\":[\"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/*\"]}]}";
        String expectedJson = "{\"resourcePolicy\":\"{\\\"Version\\\":\\\"1\\\",\\\"Statement\\\":[{\\\"Action\\\":[\\\"oss:GetTable\\\"],\\\"Effect\\\":\\\"Allow\\\",\\\"Principal\\\":[\\\"9876543210\\\"],\\\"Resource\\\":[\\\"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/*\\\"]}]}\"}";
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        PutTablePolicyRequest request = PutTablePolicyRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .namespace("test-namespace")
                .name("test-table")
                .resourcePolicy(policy)
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableConfigBasic.fromPutTablePolicy(request);

        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.opName()).isEqualTo("PutTablePolicy");

        BinaryData body = input.body().get();
        String jsonContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(jsonContent).contains("\"resourcePolicy\":");
        assertThat(jsonContent).contains("oss:GetTable");
        assertThat(jsonContent).contains("9876543210");
        assertThat(jsonContent).contains("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/*");

        assertThat(jsonContent).isEqualTo(expectedJson);
    }
}
