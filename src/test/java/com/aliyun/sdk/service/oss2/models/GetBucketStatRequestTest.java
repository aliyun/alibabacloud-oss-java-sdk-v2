package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketStatRequestTest {
    @Test
    public void testEmptyBuilder() {
        GetBucketStatRequest request = GetBucketStatRequest.newBuilder().build();
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
                "x-oss-request-payer", "requester"
        );

        GetBucketStatRequest request = GetBucketStatRequest.newBuilder()
                .bucket("examplebucket")
                .headers(headers)
                .parameter("param1", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.headers().get("x-oss-request-payer")).isEqualTo("requester");
        assertThat(request.parameters()).contains(new AbstractMap.SimpleEntry<>("param1", "value1"));
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-payer", "requester"
        );

        GetBucketStatRequest original = GetBucketStatRequest.newBuilder()
                .bucket("examplebucket")
                .headers(headers)
                .parameter("param1", "value1")
                .build();

        GetBucketStatRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.headers().get("x-oss-request-payer")).isEqualTo("requester");
        assertThat(copy.parameters()).contains(new AbstractMap.SimpleEntry<>("param1", "value1"));
    }

    @Test
    public void testHeaderProperties() {
        GetBucketStatRequest request = GetBucketStatRequest.newBuilder()
                .bucket("examplebucket")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
    }
}