package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetObjectRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetObjectRequest request = GetObjectRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.range()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject.txt");

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

        GetObjectRequest original = GetObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        GetObjectRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("exampleobject.txt");

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testHeaderProperties() {
        GetObjectRequest request = GetObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .range("bytes=0-9")
                .ifModifiedSince("Fri, 13 Nov 2015 14:47:53 GMT")
                .ifUnmodifiedSince("Fri, 13 Nov 2015 14:47:53 GMT")
                .ifMatch("etag-match")
                .ifNoneMatch("etag-none-match")
                .acceptEncoding("gzip")
                .responseContentType("text/plain")
                .responseContentLanguage("en-US")
                .responseExpires("2023-12-08T08:12:20.000Z")
                .responseCacheControl("no-cache")
                .responseContentDisposition("attachment")
                .responseContentEncoding("gzip")
                .versionId("version-1234567890")
                .trafficLimit(1000000)
                .process("image-process")
                .requestPayer("requester")
                .rangeBehavior("standard")
                .build();

        assertThat(request.range()).isEqualTo("bytes=0-9");
        assertThat(request.ifModifiedSince()).isEqualTo("Fri, 13 Nov 2015 14:47:53 GMT");
        assertThat(request.ifUnmodifiedSince()).isEqualTo("Fri, 13 Nov 2015 14:47:53 GMT");
        assertThat(request.ifMatch()).isEqualTo("etag-match");
        assertThat(request.ifNoneMatch()).isEqualTo("etag-none-match");
        assertThat(request.acceptEncoding()).isEqualTo("gzip");
        assertThat(request.responseContentType()).isEqualTo("text/plain");
        assertThat(request.responseContentLanguage()).isEqualTo("en-US");
        assertThat(request.responseExpires()).isEqualTo("2023-12-08T08:12:20.000Z");
        assertThat(request.responseCacheControl()).isEqualTo("no-cache");
        assertThat(request.responseContentDisposition()).isEqualTo("attachment");
        assertThat(request.responseContentEncoding()).isEqualTo("gzip");
        assertThat(request.versionId()).isEqualTo("version-1234567890");
        assertThat(request.trafficLimit()).isEqualTo(1000000);
        assertThat(request.process()).isEqualTo("image-process");
        assertThat(request.requestPayer()).isEqualTo("requester");
        assertThat(request.rangeBehavior()).isEqualTo("standard");
    }
}
