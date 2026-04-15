package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBucketBasic;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ListTableBucketsRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListTableBucketsRequest request = ListTableBucketsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.continuationToken()).isNull();
        assertThat(request.maxBuckets()).isNull();
        assertThat(request.prefix()).isNull();
    }

    @Test
    public void testFullBuilder() {
        ListTableBucketsRequest request = ListTableBucketsRequest.newBuilder()
                .continuationToken("test-continuation-token")
                .maxBuckets(10)
                .prefix("test-prefix")
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.continuationToken()).isEqualTo("test-continuation-token");
        assertThat(request.maxBuckets()).isEqualTo("10");
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
        ListTableBucketsRequest original = ListTableBucketsRequest.newBuilder()
                .continuationToken("original-continuation-token")
                .maxBuckets(20)
                .prefix("original-prefix")
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        ListTableBucketsRequest copy = original.toBuilder().build();

        assertThat(copy.continuationToken()).isEqualTo("original-continuation-token");
        assertThat(copy.maxBuckets()).isEqualTo("20");
        assertThat(copy.prefix()).isEqualTo("original-prefix");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {

        ListTableBucketsRequest request = ListTableBucketsRequest.newBuilder()
                .continuationToken("test-continuation-token")
                .maxBuckets(10)
                .prefix("test-prefix")
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableBucketBasic.fromListTableBuckets(request);

        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.parameters()).containsEntry("continuationToken", "test-continuation-token");
        assertThat(input.parameters()).containsEntry("maxBuckets", "10");
        assertThat(input.parameters()).containsEntry("prefix", "test-prefix");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.opName()).isEqualTo("ListTableBuckets");
    }
}