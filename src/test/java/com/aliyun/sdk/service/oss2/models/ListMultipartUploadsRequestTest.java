package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListMultipartUploadsRequestTest {
    @Test
    public void testEmptyBuilder() {
        ListMultipartUploadsRequest request = ListMultipartUploadsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.delimiter()).isNull();
        assertThat(request.maxUploads()).isNull();
        assertThat(request.keyMarker()).isNull();
        assertThat(request.prefix()).isNull();
        assertThat(request.uploadIdMarker()).isNull();
        assertThat(request.encodingType()).isNull();
        assertThat(request.requestPayer()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListMultipartUploadsRequest request = ListMultipartUploadsRequest.newBuilder()
                .bucket("examplebucket")
                .delimiter("/")
                .maxUploads(100L)
                .keyMarker("example-key")
                .prefix("example-prefix")
                .uploadIdMarker("upload-id-marker")
                .encodingType("url")
                .requestPayer("requester")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.delimiter()).isEqualTo("/");
        assertThat(request.maxUploads()).isEqualTo(100L);
        assertThat(request.keyMarker()).isEqualTo("example-key");
        assertThat(request.prefix()).isEqualTo("example-prefix");
        assertThat(request.uploadIdMarker()).isEqualTo("upload-id-marker");
        assertThat(request.encodingType()).isEqualTo("url");
        assertThat(request.requestPayer()).isEqualTo("requester");

        assertThat(request.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListMultipartUploadsRequest original = ListMultipartUploadsRequest.newBuilder()
                .bucket("examplebucket")
                .delimiter("|")
                .maxUploads(50L)
                .keyMarker("original-key")
                .prefix("original-prefix")
                .uploadIdMarker("original-upload-id-marker")
                .encodingType("url")
                .requestPayer("requester")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        ListMultipartUploadsRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.delimiter()).isEqualTo("|");
        assertThat(copy.maxUploads()).isEqualTo(50L);
        assertThat(copy.keyMarker()).isEqualTo("original-key");
        assertThat(copy.prefix()).isEqualTo("original-prefix");
        assertThat(copy.uploadIdMarker()).isEqualTo("original-upload-id-marker");
        assertThat(copy.encodingType()).isEqualTo("url");
        assertThat(copy.requestPayer()).isEqualTo("requester");

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        ListMultipartUploadsRequest request = ListMultipartUploadsRequest.newBuilder()
                .bucket("examplebucket")
                .delimiter(",")
                .maxUploads(25L)
                .keyMarker("another-key")
                .prefix("another-prefix")
                .uploadIdMarker("another-upload-id-marker")
                .encodingType("url")
                .requestPayer("requester")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.delimiter()).isEqualTo(",");
        assertThat(request.maxUploads()).isEqualTo(25L);
        assertThat(request.keyMarker()).isEqualTo("another-key");
        assertThat(request.prefix()).isEqualTo("another-prefix");
        assertThat(request.uploadIdMarker()).isEqualTo("another-upload-id-marker");
        assertThat(request.encodingType()).isEqualTo("url");
        assertThat(request.requestPayer()).isEqualTo("requester");
    }
}
