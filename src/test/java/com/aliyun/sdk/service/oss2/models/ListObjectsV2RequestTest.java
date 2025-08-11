package com.aliyun.sdk.service.oss2.models;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ListObjectsV2RequestTest {

    @Test
    public void testEmptyBuilder() {
        ListObjectsV2Request request = ListObjectsV2Request.newBuilder().build();
        assertEquals(0, request.headers().size());
        assertEquals(0, request.parameters().size());
        assertNull(request.bucket());
    }

    @Test
    public void fullBuilder() {
        ListObjectsV2Request request = ListObjectsV2Request.newBuilder()
                .bucket("example-bucket")
                .header("x-header", "value1")
                .header("x-header", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .prefix("example-prefix")
                .delimiter("|")
                .maxKeys(500L)
                .startAfter("start-key")
                .continuationToken("token-123")
                .encodingType("url")
                .fetchOwner(true)
                .build();

        assertNotNull(request);
        assertThat(request.bucket()).isEqualTo("example-bucket");
        assertEquals(1, request.headers().size());
        Assertions.assertThat(request.headers()).contains(new AbstractMap.SimpleEntry<>("x-header", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("prefix", "example-prefix"),
                new AbstractMap.SimpleEntry<>("delimiter", "|"),
                new AbstractMap.SimpleEntry<>("max-keys", "500"),
                new AbstractMap.SimpleEntry<>("start-after", "start-key"),
                new AbstractMap.SimpleEntry<>("continuation-token", "token-123"),
                new AbstractMap.SimpleEntry<>("encoding-type", "url"),
                new AbstractMap.SimpleEntry<>("fetch-owner", "true")
        );
        assertNull(request.parameters().get("null-param"));

        // Test toBuilder
        ListObjectsV2Request.Builder builder = request.toBuilder();
        ListObjectsV2Request rebuiltRequest = builder
                .bucket("rebuilt-bucket")
                .prefix("rebuilt-prefix")
                .delimiter("/")
                .build();

        assertNotNull(rebuiltRequest);
        assertThat(rebuiltRequest.bucket()).isEqualTo("rebuilt-bucket");
        assertThat(rebuiltRequest.prefix()).isEqualTo("rebuilt-prefix");
        assertThat(rebuiltRequest.delimiter()).isEqualTo("/");
        assertEquals(10, rebuiltRequest.parameters().size());
    }

    @Test
    public void testToBuilder() {
        ListObjectsV2Request originalRequest = ListObjectsV2Request.newBuilder()
                .bucket("original-bucket")
                .prefix("original-prefix")
                .delimiter("/")
                .maxKeys(1000L)
                .header("x-test-header", "original-header-value")
                .parameter("test-param", "original-param-value")
                .build();

        ListObjectsV2Request.Builder builder = originalRequest.toBuilder();
        ListObjectsV2Request rebuiltRequest = builder
                .bucket("rebuilt-bucket")
                .prefix("rebuilt-prefix")
                .delimiter("-")
                .maxKeys(200L)
                .header("x-test-header", "rebuilt-header-value")
                .parameter("test-param", "rebuilt-param-value")
                .build();

        assertNotNull(rebuiltRequest);
        assertThat(rebuiltRequest.bucket()).isEqualTo("rebuilt-bucket");
        assertThat(rebuiltRequest.prefix()).isEqualTo("rebuilt-prefix");
        assertThat(rebuiltRequest.delimiter()).isEqualTo("-");
        assertThat(rebuiltRequest.maxKeys()).isEqualTo(200L);
        assertEquals(1, rebuiltRequest.headers().size());
        Assertions.assertThat(rebuiltRequest.headers()).contains(new AbstractMap.SimpleEntry<>("x-test-header", "rebuilt-header-value"));
        assertThat(rebuiltRequest.parameters()).contains(new AbstractMap.SimpleEntry<>("test-param", "rebuilt-param-value"));
    }

}
