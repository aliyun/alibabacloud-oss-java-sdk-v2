package com.aliyun.sdk.service.oss2.models;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ListObjectsRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListObjectsRequest request = ListObjectsRequest.newBuilder().build();
        assertEquals(0, request.headers().size());
        assertEquals(0, request.parameters().size());
        assertNull(request.bucket());
    }

    @Test
    public void fullBuilder() {
        ListObjectsRequest request = ListObjectsRequest.newBuilder()
                .bucket("bucket")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .prefix("prefix")
                .delimiter("/")
                .maxKeys(100L)
                .marker("token")
                .encodingType("url")
                .build();

        assertNotNull(request);
        assertThat(request.bucket()).isEqualTo("bucket");
        assertEquals(1, request.headers().size());
        Assertions.assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertEquals(1, request.headers().size());
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("prefix", "prefix"),
                new AbstractMap.SimpleEntry<>("delimiter", "/"),
                new AbstractMap.SimpleEntry<>("max-keys", "100"),
                new AbstractMap.SimpleEntry<>("marker", "token"),
                new AbstractMap.SimpleEntry<>("encoding-type", "url"));
        assertNull(request.parameters().get("null-param"));

        // Test toBuilder
        ListObjectsRequest.Builder builder = request.toBuilder();
        request = builder
                .header("x-header-value", "value1")
                .bucket("new-bucket")
                .prefix("new-prefix")
                .build();

        assertNotNull(request);
        assertThat(request.bucket()).isEqualTo("new-bucket");
        assertEquals(1, request.headers().size());
        Assertions.assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value1"));
        assertEquals(8, request.parameters().size());
        assertThat(request.prefix()).isEqualTo("new-prefix");
    }

    @Test
    public void testToBuilder() {
        ListObjectsRequest originalRequest = ListObjectsRequest.newBuilder()
                .bucket("original-bucket")
                .prefix("original-prefix")
                .delimiter("original-delimiter")
                .header("x-header", "original-value")
                .parameter("key", "original-value")
                .build();

        ListObjectsRequest.Builder builder = originalRequest.toBuilder();
        ListObjectsRequest rebuiltRequest = builder
                .bucket("rebuilt-bucket")
                .prefix("rebuilt-prefix")
                .delimiter("rebuilt-delimiter")
                .header("x-header", "rebuilt-value")
                .parameter("key", "rebuilt-value")
                .build();

        assertNotNull(rebuiltRequest);
        assertThat(rebuiltRequest.bucket()).isEqualTo("rebuilt-bucket");
        assertThat(rebuiltRequest.prefix()).isEqualTo("rebuilt-prefix");
        assertThat(rebuiltRequest.delimiter()).isEqualTo("rebuilt-delimiter");
        assertEquals(1, rebuiltRequest.headers().size());
        Assertions.assertThat(rebuiltRequest.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header", "rebuilt-value"));
        assertThat(rebuiltRequest.parameters()).contains(
                new AbstractMap.SimpleEntry<>("key", "rebuilt-value"));
    }

}
