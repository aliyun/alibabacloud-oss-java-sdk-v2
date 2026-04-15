package com.aliyun.sdk.service.oss2.tables.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteNamespaceRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteNamespaceRequest request = DeleteNamespaceRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.namespace()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteNamespaceRequest request = DeleteNamespaceRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket")
                .namespace("my_namespace")
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");
        assertThat(request.namespace()).isEqualTo("my_namespace");

        assertThat(request.headers()).contains(
                new java.util.AbstractMap.SimpleEntry<>("x-oss-header1", "header-value1"),
                new java.util.AbstractMap.SimpleEntry<>("x-oss-header2", "header-value2")
        );

        assertThat(request.parameters()).contains(
                new java.util.AbstractMap.SimpleEntry<>("param1", "value1"),
                new java.util.AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        DeleteNamespaceRequest original = DeleteNamespaceRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket")
                .namespace("original_namespace")
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        DeleteNamespaceRequest copy = original.toBuilder().build();

        assertThat(copy.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket");
        assertThat(copy.namespace()).isEqualTo("original_namespace");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void xmlBuilder() {
        DeleteNamespaceRequest request = DeleteNamespaceRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket")
                .namespace("my_namespace")
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");
        assertThat(request.namespace()).isEqualTo("my_namespace");
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("test-request-id");
        assertThat(request.parameters().get("test-param")).isEqualTo("test-value");
    }
}