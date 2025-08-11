package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HeadObjectRequestTest {
    @Test
    public void testEmptyBuilder() {
        HeadObjectRequest request = HeadObjectRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "If-Modified-Since", "Wed, 21 Oct 2025 07:25:42 GMT",
                "If-Match", "\"1234567890\"",
                "If-None-Match", "\"0987654321\""
        );

        HeadObjectRequest request = HeadObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .headers(headers)
                .parameter("versionId", "1234567890")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject.txt");

        assertThat(request.headers().get("If-Modified-Since")).isEqualTo("Wed, 21 Oct 2025 07:25:42 GMT");
        assertThat(request.headers().get("If-Match")).isEqualTo("\"1234567890\"");
        assertThat(request.headers().get("If-None-Match")).isEqualTo("\"0987654321\"");
        assertThat(request.parameters()).contains(new AbstractMap.SimpleEntry<>("versionId", "1234567890"));
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "If-Unmodified-Since", "Thu, 22 Oct 2025 08:30:45 GMT",
                "If-None-Match", "\"abcdef1234\""
        );

        HeadObjectRequest original = HeadObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .headers(headers)
                .parameter("versionId", "9876543210")
                .build();

        HeadObjectRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("exampleobject.txt");

        assertThat(copy.headers().get("If-Unmodified-Since")).isEqualTo("Thu, 22 Oct 2025 08:30:45 GMT");
        assertThat(copy.headers().get("If-None-Match")).isEqualTo("\"abcdef1234\"");
        assertThat(copy.parameters()).contains(new AbstractMap.SimpleEntry<>("versionId", "9876543210"));
    }

    @Test
    public void testHeaderProperties() {
        HeadObjectRequest request = HeadObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .ifModifiedSince("Wed, 21 Oct 2025 07:25:42 GMT")
                .ifUnmodifiedSince("Thu, 22 Oct 2025 08:30:45 GMT")
                .ifMatch("\"1234567890\"")
                .ifNoneMatch("\"0987654321\"")
                .versionId("1234567890")
                .requestPayer("requester")
                .build();

        assertThat(request.ifModifiedSince()).isEqualTo("Wed, 21 Oct 2025 07:25:42 GMT");
        assertThat(request.ifUnmodifiedSince()).isEqualTo("Thu, 22 Oct 2025 08:30:45 GMT");
        assertThat(request.ifMatch()).isEqualTo("\"1234567890\"");
        assertThat(request.ifNoneMatch()).isEqualTo("\"0987654321\"");
        assertThat(request.versionId()).isEqualTo("1234567890");
        assertThat(request.requestPayer()).isEqualTo("requester");
    }
}