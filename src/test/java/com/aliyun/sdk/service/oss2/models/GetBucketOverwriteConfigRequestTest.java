package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketOverwriteConfig;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketOverwriteConfigRequestTest {
    @Test
    public void testEmptyBuilder() {
        GetBucketOverwriteConfigRequest request = GetBucketOverwriteConfigRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetBucketOverwriteConfigRequest request = GetBucketOverwriteConfigRequest.newBuilder()
                .bucket("examplebucket")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new java.util.AbstractMap.SimpleEntry<>("param1", "value1"),
                new java.util.AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetBucketOverwriteConfigRequest original = GetBucketOverwriteConfigRequest.newBuilder()
                .bucket("testbucket")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        GetBucketOverwriteConfigRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new java.util.AbstractMap.SimpleEntry<>("param3", "value3"),
                new java.util.AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        GetBucketOverwriteConfigRequest request = GetBucketOverwriteConfigRequest.newBuilder()
                .bucket("anotherbucket")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
    }

    @Test
    public void xmlBuilder() {
        GetBucketOverwriteConfigRequest request = GetBucketOverwriteConfigRequest.newBuilder()
                .bucket("examplebucket")
                .build();

        OperationInput input = SerdeBucketOverwriteConfig.fromGetBucketOverwriteConfig(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("overwriteConfig")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.body()).isNotPresent();

    }
}