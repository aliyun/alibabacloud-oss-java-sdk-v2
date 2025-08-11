package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListBucketsRequestTest {
    @Test
    public void testEmptyBuilder() {
        ListBucketsRequest request = ListBucketsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.resourceGroupId()).isNull();
        assertThat(request.prefix()).isNull();
        assertThat(request.marker()).isNull();
        assertThat(request.maxKeys()).isNull();
        assertThat(request.tagKey()).isNull();
        assertThat(request.tagValue()).isNull();
        assertThat(request.tagging()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListBucketsRequest request = ListBucketsRequest.newBuilder()
                .resourceGroupId("rg-1234567890abcdefg")
                .prefix("example")
                .marker("examplebucket")
                .maxKeys(100L)
                .tagKey("env")
                .tagValue("test")
                .tagging("env:test")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.resourceGroupId()).isEqualTo("rg-1234567890abcdefg");
        assertThat(request.prefix()).isEqualTo("example");
        assertThat(request.marker()).isEqualTo("examplebucket");
        assertThat(request.maxKeys()).isEqualTo(100L);
        assertThat(request.tagKey()).isEqualTo("env");
        assertThat(request.tagValue()).isEqualTo("test");
        assertThat(request.tagging()).isEqualTo("env:test");

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

        ListBucketsRequest original = ListBucketsRequest.newBuilder()
                .resourceGroupId("rg-765432109876543210")
                .prefix("test")
                .marker("testbucket")
                .maxKeys(50L)
                .tagKey("project")
                .tagValue("demo")
                .tagging("project:demo")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        ListBucketsRequest copy = original.toBuilder().build();

        assertThat(copy.resourceGroupId()).isEqualTo("rg-765432109876543210");
        assertThat(copy.prefix()).isEqualTo("test");
        assertThat(copy.marker()).isEqualTo("testbucket");
        assertThat(copy.maxKeys()).isEqualTo(50L);
        assertThat(copy.tagKey()).isEqualTo("project");
        assertThat(copy.tagValue()).isEqualTo("demo");
        assertThat(copy.tagging()).isEqualTo("project:demo");

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        ListBucketsRequest request = ListBucketsRequest.newBuilder()
                .resourceGroupId("rg-another1234567890")
                .prefix("another")
                .marker("anotherbucket")
                .maxKeys(25L)
                .tagKey("type")
                .tagValue("data")
                .tagging("type:data")
                .build();

        assertThat(request.resourceGroupId()).isEqualTo("rg-another1234567890");
        assertThat(request.prefix()).isEqualTo("another");
        assertThat(request.marker()).isEqualTo("anotherbucket");
        assertThat(request.maxKeys()).isEqualTo(25L);
        assertThat(request.tagKey()).isEqualTo("type");
        assertThat(request.tagValue()).isEqualTo("data");
        assertThat(request.tagging()).isEqualTo("type:data");
    }
}
