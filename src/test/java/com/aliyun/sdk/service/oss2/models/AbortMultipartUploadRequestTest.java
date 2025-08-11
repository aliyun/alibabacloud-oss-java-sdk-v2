package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AbortMultipartUploadRequestTest {
    @Test
    public void testEmptyBuilder() {
        AbortMultipartUploadRequest request = AbortMultipartUploadRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.uploadId()).isNull();
        assertThat(request.requestPayer()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        AbortMultipartUploadRequest request = AbortMultipartUploadRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .uploadId("upload-id")
                .requestPayer("requester")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("examplekey");
        assertThat(request.uploadId()).isEqualTo("upload-id");
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

        AbortMultipartUploadRequest original = AbortMultipartUploadRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .uploadId("upload-id-2")
                .requestPayer("requester")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        AbortMultipartUploadRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("examplekey");
        assertThat(copy.uploadId()).isEqualTo("upload-id-2");
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
        AbortMultipartUploadRequest request = AbortMultipartUploadRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .uploadId("upload-id-3")
                .requestPayer("requester")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("examplekey");
        assertThat(request.uploadId()).isEqualTo("upload-id-3");
        assertThat(request.requestPayer()).isEqualTo("requester");
    }
}
