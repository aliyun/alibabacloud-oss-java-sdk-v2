package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBucketBasic;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTableBucketRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetTableBucketRequest request = GetTableBucketRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
    }

    @Test
    public void testFullBuilder() {
        GetTableBucketRequest request = GetTableBucketRequest.newBuilder()
                .tableBucketARN("test-bucket-arn")
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("test-bucket-arn");

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
        GetTableBucketRequest original = GetTableBucketRequest.newBuilder()
                .tableBucketARN("original-bucket-arn")
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        GetTableBucketRequest copy = original.toBuilder().build();

        assertThat(copy.tableBucketARN()).isEqualTo("original-bucket-arn");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {

        GetTableBucketRequest request = GetTableBucketRequest.newBuilder()
                .tableBucketARN("test-bucket-arn")
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableBucketBasic.fromGetTableBucket(request);

        assertThat(input.headers().get("x-oss-request-id")).isEqualTo("test-request-id");
        assertThat(input.parameters().get("test-param")).isEqualTo("test-value");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.opName()).isEqualTo("GetTableBucket");
    }
}