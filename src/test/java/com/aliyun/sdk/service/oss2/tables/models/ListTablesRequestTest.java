package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBasic;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ListTablesRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListTablesRequest request = ListTablesRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.continuationToken()).isNull();
        assertThat(request.maxTables()).isNull();
        assertThat(request.namespace()).isNull();
        assertThat(request.prefix()).isNull();
    }

    @Test
    public void testFullBuilder() {
        ListTablesRequest request = ListTablesRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket")
                .continuationToken("test-continuation-token")
                .maxTables(10)
                .namespace("test-namespace")
                .prefix("test-prefix")
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");
        assertThat(request.continuationToken()).isEqualTo("test-continuation-token");
        assertThat(request.maxTables()).isEqualTo("10");
        assertThat(request.namespace()).isEqualTo("test-namespace");
        assertThat(request.prefix()).isEqualTo("test-prefix");

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
        ListTablesRequest original = ListTablesRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket")
                .continuationToken("original-continuation-token")
                .maxTables(20)
                .namespace("original-namespace")
                .prefix("original-prefix")
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        ListTablesRequest copy = original.toBuilder().build();

        assertThat(copy.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");
        assertThat(copy.continuationToken()).isEqualTo("original-continuation-token");
        assertThat(copy.maxTables()).isEqualTo("20");
        assertThat(copy.namespace()).isEqualTo("original-namespace");
        assertThat(copy.prefix()).isEqualTo("original-prefix");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {

        ListTablesRequest request = ListTablesRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket")
                .continuationToken("test-continuation-token")
                .maxTables(10)
                .namespace("test-namespace")
                .prefix("test-prefix")
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableBasic.fromListTables(request);

        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.parameters()).containsEntry("continuationToken", "test-continuation-token");
        assertThat(input.parameters()).containsEntry("maxTables", "10");
        assertThat(input.parameters()).containsEntry("namespace", "test-namespace");
        assertThat(input.parameters()).containsEntry("prefix", "test-prefix");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.opName()).isEqualTo("ListTables");
    }
}