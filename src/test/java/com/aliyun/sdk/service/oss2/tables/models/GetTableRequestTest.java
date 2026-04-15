package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBasic;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTableRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetTableRequest request = GetTableRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.namespace()).isNull();
        assertThat(request.name()).isNull();
        assertThat(request.tableArn()).isNull();
        assertThat(request.bodyFields()).isNotNull();
        assertThat(request.bodyFields().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        GetTableRequest request = GetTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket")
                .namespace("test_namespace")
                .name("test_table")
                .tableArn("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket/table/test_table_id")
                .header("x-oss-request-id", "test-request-id")
                .bodyFields(MapUtils.of("field1", "value1"))
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket");
        assertThat(request.namespace()).isEqualTo("test_namespace");
        assertThat(request.name()).isEqualTo("test_table");
        assertThat(request.tableArn()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket/table/test_table_id");
        assertThat(request.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(request.bodyFields()).containsEntry("field1", "value1");
    }

    @Test
    public void xmlBuilderWithTableParams() throws JsonProcessingException {
        ObjectMapper mapper = JsonMapper.builder()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();

        GetTableRequest request = GetTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket")
                .namespace("test_namespace")
                .name("test_table")
                .header("x-oss-request-id", "req-json-builder-test")
                .header("Content-Type", "application/json")
                .build();

        OperationInput input = SerdeTableBasic.fromGetTable(request);

        assertThat(input.headers().get("x-oss-request-id")).isEqualTo("req-json-builder-test");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.key().get()).isEqualTo("get-table");
        assertThat(input.key().isPresent()).isTrue();
        assertThat(input.key().get().contains("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket"));
        assertThat(input.key().get().contains("test_namespace"));
        assertThat(input.key().get().contains("test_table"));
        assertThat(input.opName()).isEqualTo("GetTable");

        // Verify that the request body is empty for GET operation
        assertThat(input.body().isPresent()).isFalse();
    }

    @Test
    public void xmlBuilderWithTableArn() throws JsonProcessingException {
        ObjectMapper mapper = JsonMapper.builder()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();

        GetTableRequest request = GetTableRequest.newBuilder()
                .tableArn("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket/table/test_table_id")
                .header("x-oss-request-id", "req-json-builder-test")
                .header("Content-Type", "application/json")
                .build();

        OperationInput input = SerdeTableBasic.fromGetTable(request);

        assertThat(input.headers().get("x-oss-request-id")).isEqualTo("req-json-builder-test");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.key().get()).isEqualTo("get-table");
        assertThat(input.parameters().get("tableArn")).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket/table/test_table_id");
        assertThat(input.opName()).isEqualTo("GetTable");

        // Verify that the request body is empty for GET operation
        assertThat(input.body().isPresent()).isFalse();
    }
}