package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableConfigBasic;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTableMaintenanceConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetTableMaintenanceConfigurationRequest request = GetTableMaintenanceConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.namespace()).isNull();
        assertThat(request.name()).isNull();
    }

    @Test
    public void testFullBuilder() {
        GetTableMaintenanceConfigurationRequest request = GetTableMaintenanceConfigurationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .namespace("test-namespace")
                .name("test-table")
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-table-arn", "acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket");
        assertThat(request.namespace()).isEqualTo("test-namespace");
        assertThat(request.name()).isEqualTo("test-table");

        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-oss-table-arn", "acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id"),
                new AbstractMap.SimpleEntry<>("x-oss-header2", "header-value2")
        );

        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        GetTableMaintenanceConfigurationRequest original = GetTableMaintenanceConfigurationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket")
                .namespace("original-namespace")
                .name("original-table")
                .parameter("original-param", "original-value")
                .header("x-oss-table-arn", "acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket/table/original_table_id")
                .build();

        GetTableMaintenanceConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.tableBucketARN()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket");
        assertThat(copy.namespace()).isEqualTo("original-namespace");
        assertThat(copy.name()).isEqualTo("original-table");

        assertThat(copy.headers().get("x-oss-table-arn")).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket/table/original_table_id");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        GetTableMaintenanceConfigurationRequest request = GetTableMaintenanceConfigurationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .namespace("test-namespace")
                .name("test-table")
                .header("x-oss-table-arn", "acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id")
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableConfigBasic.fromGetTableMaintenanceConfiguration(request);

        assertThat(input.headers()).containsEntry("x-oss-table-arn", "acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id");
        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.opName()).isEqualTo("GetTableMaintenanceConfiguration");

        assertThat(input.body().isPresent()).isFalse();
    }
}
