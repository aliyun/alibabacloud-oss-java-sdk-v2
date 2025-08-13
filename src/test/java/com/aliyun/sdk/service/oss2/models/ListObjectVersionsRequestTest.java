package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListObjectVersionsRequestTest {
    @Test
    public void testEmptyBuilder() {
        ListObjectVersionsRequest request = ListObjectVersionsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.delimiter()).isNull();
        assertThat(request.keyMarker()).isNull();
        assertThat(request.versionIdMarker()).isNull();
        assertThat(request.maxKeys()).isNull();
        assertThat(request.prefix()).isNull();
        assertThat(request.encodingType()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListObjectVersionsRequest request = ListObjectVersionsRequest.newBuilder()
                .bucket("examplebucket")
                .delimiter("/")
                .keyMarker("example-key")
                .versionIdMarker("version-id")
                .maxKeys(100L)
                .prefix("example-prefix")
                .encodingType("url")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.delimiter()).isEqualTo("/");
        assertThat(request.keyMarker()).isEqualTo("example-key");
        assertThat(request.versionIdMarker()).isEqualTo("version-id");
        assertThat(request.maxKeys()).isEqualTo(100L);
        assertThat(request.prefix()).isEqualTo("example-prefix");
        assertThat(request.encodingType()).isEqualTo("url");

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListObjectVersionsRequest original = ListObjectVersionsRequest.newBuilder()
                .bucket("examplebucket")
                .delimiter("/")
                .keyMarker("example-key")
                .versionIdMarker("version-id")
                .maxKeys(100L)
                .prefix("example-prefix")
                .encodingType("url")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        ListObjectVersionsRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.delimiter()).isEqualTo("/");
        assertThat(copy.keyMarker()).isEqualTo("example-key");
        assertThat(copy.versionIdMarker()).isEqualTo("version-id");
        assertThat(copy.maxKeys()).isEqualTo(100L);
        assertThat(copy.prefix()).isEqualTo("example-prefix");
        assertThat(copy.encodingType()).isEqualTo("url");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testHeaderProperties() {
        ListObjectVersionsRequest request = ListObjectVersionsRequest.newBuilder()
                .bucket("examplebucket")
                .delimiter("/")
                .keyMarker("example-key")
                .versionIdMarker("version-id")
                .maxKeys(100L)
                .prefix("example-prefix")
                .encodingType("url")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.delimiter()).isEqualTo("/");
        assertThat(request.keyMarker()).isEqualTo("example-key");
        assertThat(request.versionIdMarker()).isEqualTo("version-id");
        assertThat(request.maxKeys()).isEqualTo(100L);
        assertThat(request.prefix()).isEqualTo("example-prefix");
        assertThat(request.encodingType()).isEqualTo("url");
    }
}
