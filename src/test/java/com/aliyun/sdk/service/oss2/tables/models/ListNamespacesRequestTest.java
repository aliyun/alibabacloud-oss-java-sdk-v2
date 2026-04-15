package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListNamespacesRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListNamespacesRequest request = ListNamespacesRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.continuationToken()).isNull();
        assertThat(request.maxNamespaces()).isNull();
        assertThat(request.prefix()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-user-agent", "aliyun-sdk/1.0",
                "User-Agent", "Mozilla/5.0"
        );
    
        ListNamespacesRequest request = ListNamespacesRequest.newBuilder()
                .headers(headers)
                .tableBucketARN("arn:oss:table:cn-hangzhou:1234567890:bucket123")
                .continuationToken("continuation-token-123")
                .maxNamespaces(10)
                .prefix("test-prefix")
                .build();
    
        assertThat(request.headers().get("x-oss-user-agent")).isEqualTo("aliyun-sdk/1.0");
        assertThat(request.headers().get("User-Agent")).isEqualTo("Mozilla/5.0");
        assertThat(request.tableBucketARN()).isEqualTo("arn:oss:table:cn-hangzhou:1234567890:bucket123");
        assertThat(request.continuationToken()).isEqualTo("continuation-token-123");
        assertThat(request.maxNamespaces()).isEqualTo(10);
        assertThat(request.prefix()).isEqualTo("test-prefix");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListNamespacesRequest original = ListNamespacesRequest.newBuilder()
                .headers(headers)
                .tableBucketARN("arn:oss:table:cn-hangzhou:1234567890:original-bucket")
                .continuationToken("original-token")
                .maxNamespaces(20)
                .prefix("original-prefix")
                .build();

        ListNamespacesRequest copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.tableBucketARN()).isEqualTo("arn:oss:table:cn-hangzhou:1234567890:original-bucket");
        assertThat(copy.continuationToken()).isEqualTo("original-token");
        assertThat(copy.maxNamespaces()).isEqualTo(20);
        assertThat(copy.prefix()).isEqualTo("original-prefix");
    }

    @Test
    public void xmlBuilder() {
        ListNamespacesRequest request = ListNamespacesRequest.newBuilder()
                .tableBucketARN("arn:oss:table:cn-hangzhou:1234567890:test-bucket")
                .continuationToken("token-123")
                .maxNamespaces(5)
                .prefix("test-")
                .header("x-oss-user-agent", "MyApp/1.0")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("arn:oss:table:cn-hangzhou:1234567890:test-bucket");
        assertThat(request.continuationToken()).isEqualTo("token-123");
        assertThat(request.maxNamespaces()).isEqualTo(5);
        assertThat(request.prefix()).isEqualTo("test-");
        assertThat(request.headers().get("x-oss-user-agent")).isEqualTo("MyApp/1.0");
    }
}