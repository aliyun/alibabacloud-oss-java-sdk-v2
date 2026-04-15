package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBasic;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteTableRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteTableRequest request = DeleteTableRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.namespace()).isNull();
        assertThat(request.name()).isNull();
        assertThat(request.versionToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteTableRequest request = DeleteTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket")
                .namespace("test_namespace")
                .name("test_table")
                .versionToken("version_token_123")
                .header("x-oss-request-id", "test-request-id")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket");
        assertThat(request.namespace()).isEqualTo("test_namespace");
        assertThat(request.name()).isEqualTo("test_table");
        assertThat(request.versionToken()).isEqualTo("version_token_123");
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("test-request-id");
    }

    @Test
    public void xmlBuilder() throws Exception {
        DeleteTableRequest request = DeleteTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket")
                .namespace("test_namespace")
                .name("test_table")
                .versionToken("version_token_123")
                .header("x-oss-request-id", "test-request-id")
                .build();

        OperationInput input = SerdeTableBasic.fromDeleteTable(request);

        assertThat(input.headers().get("x-oss-request-id")).isEqualTo("test-request-id");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.key().get()).contains("tables/");
        assertThat(input.parameters()).containsEntry("versionToken", "version_token_123");
        assertThat(input.opName()).isEqualTo("DeleteTable");

        // Verify that the request body is empty for DELETE operation
        assertThat(input.body().isPresent()).isFalse();
    }

    @Test
    public void jsonBuilder() throws JsonProcessingException {
        String expectedJson = "{}"; // DELETE operations typically have empty body
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        DeleteTableRequest request = DeleteTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket")
                .namespace("test_namespace")
                .name("test_table")
                .versionToken("version_token_123")
                .header("x-oss-request-id", "req-json-builder-test")
                .build();

        OperationInput input = SerdeTableBasic.fromDeleteTable(request);

        assertThat(input.headers().get("x-oss-request-id")).isEqualTo("req-json-builder-test");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.key().get()).contains("tables/");
        assertThat(input.parameters()).containsEntry("versionToken", "version_token_123");
        assertThat(input.opName()).isEqualTo("DeleteTable");

        // Verify that the request body is empty for DELETE operation
        assertThat(input.body().isPresent()).isFalse();
    }
}