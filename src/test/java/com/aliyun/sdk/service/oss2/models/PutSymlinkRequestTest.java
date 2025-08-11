package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutSymlinkRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutSymlinkRequest request = PutSymlinkRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.symlinkTarget()).isNull();
        assertThat(request.objectAcl()).isNull();
        assertThat(request.storageClass()).isNull();
        assertThat(request.forbidOverwrite()).isNull();
        assertThat(request.requestPayer()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        PutSymlinkRequest request = PutSymlinkRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplesymlink")
                .symlinkTarget("target-object")
                .objectAcl("public-read")
                .storageClass("Standard")
                .forbidOverwrite("true")
                .requestPayer("requester")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("examplesymlink");
        assertThat(request.symlinkTarget()).isEqualTo("target-object");
        assertThat(request.objectAcl()).isEqualTo("public-read");
        assertThat(request.storageClass()).isEqualTo("Standard");
        assertThat(request.forbidOverwrite()).isEqualTo("true");
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

        PutSymlinkRequest original = PutSymlinkRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplesymlink")
                .symlinkTarget("original-target-object")
                .objectAcl("private")
                .storageClass("IA")
                .forbidOverwrite("false")
                .requestPayer("requester")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutSymlinkRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("examplesymlink");
        assertThat(copy.symlinkTarget()).isEqualTo("original-target-object");
        assertThat(copy.objectAcl()).isEqualTo("private");
        assertThat(copy.storageClass()).isEqualTo("IA");
        assertThat(copy.forbidOverwrite()).isEqualTo("false");
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
        PutSymlinkRequest request = PutSymlinkRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplesymlink")
                .symlinkTarget("another-target-object")
                .objectAcl("public-read-write")
                .storageClass("Archive")
                .forbidOverwrite("true")
                .requestPayer("requester")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("examplesymlink");
        assertThat(request.symlinkTarget()).isEqualTo("another-target-object");
        assertThat(request.objectAcl()).isEqualTo("public-read-write");
        assertThat(request.storageClass()).isEqualTo("Archive");
        assertThat(request.forbidOverwrite()).isEqualTo("true");
        assertThat(request.requestPayer()).isEqualTo("requester");
    }
}
