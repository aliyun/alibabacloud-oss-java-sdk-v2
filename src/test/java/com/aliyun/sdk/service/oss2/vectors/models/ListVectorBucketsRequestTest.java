package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorBucketBasic;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

public class ListVectorBucketsRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListVectorBucketsRequest request = ListVectorBucketsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.marker()).isNull();
        assertThat(request.maxKeys()).isNull();
        assertThat(request.prefix()).isNull();
        assertThat(request.resourceGroupId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListVectorBucketsRequest request = ListVectorBucketsRequest.newBuilder()
                .marker("bucket1")
                .maxKeys(100L)
                .prefix("test")
                .resourceGroupId("rg-123456")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.marker()).isEqualTo("bucket1");
        assertThat(request.maxKeys()).isEqualTo(100);
        assertThat(request.prefix()).isEqualTo("test");
        assertThat(request.resourceGroupId()).isEqualTo("rg-123456");

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).containsEntry("param1", "value1");
        assertThat(request.parameters()).containsEntry("param2", "value2");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListVectorBucketsRequest original = ListVectorBucketsRequest.newBuilder()
                .marker("bucket2")
                .maxKeys(50L)
                .prefix("example")
                .resourceGroupId("rg-789012")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        ListVectorBucketsRequest copy = original.toBuilder().build();

        assertThat(copy.marker()).isEqualTo("bucket2");
        assertThat(copy.maxKeys()).isEqualTo(50);
        assertThat(copy.prefix()).isEqualTo("example");
        assertThat(copy.resourceGroupId()).isEqualTo("rg-789012");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).containsEntry("param3", "value3");
        assertThat(copy.parameters()).containsEntry("param4", "value4");
    }

    @Test
    public void testHeaderProperties() {
        ListVectorBucketsRequest request = ListVectorBucketsRequest.newBuilder()
                .marker("bucket3")
                .maxKeys(200L)
                .prefix("data")
                .resourceGroupId("rg-345678")
                .header("x-oss-meta-custom", "custom-value")
                .header("Cache-Control", "no-cache")
                .build();

        assertThat(request.marker()).isEqualTo("bucket3");
        assertThat(request.maxKeys()).isEqualTo(200);
        assertThat(request.prefix()).isEqualTo("data");
        assertThat(request.resourceGroupId()).isEqualTo("rg-345678");
        assertThat(request.headers()).containsEntry("x-oss-meta-custom", "custom-value");
        assertThat(request.headers()).containsEntry("Cache-Control", "no-cache");
    }

    @Test
    public void xmlBuilder() {
        ListVectorBucketsRequest request = ListVectorBucketsRequest.newBuilder()
                .marker("examplebucket")
                .maxKeys(100L)
                .prefix("test")
                .resourceGroupId("test-resource-group")
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeVectorBucketBasic.fromListVectorBuckets(request);

        assertThat(input.bucket()).isEqualTo(Optional.empty());
        assertThat(input.headers()).containsEntry("Content-Type", "application/octet-stream");
        assertThat(input.parameters()).containsEntry("vector", "");
        assertThat(input.headers()).containsEntry("x-oss-resource-group-id", "test-resource-group");
        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.parameters()).containsEntry("marker", "examplebucket");
        assertThat(input.parameters()).containsEntry("max-keys", "100");
        assertThat(input.parameters()).containsEntry("prefix", "test");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.opName()).isEqualTo("ListVectorBuckets");
    }
}

