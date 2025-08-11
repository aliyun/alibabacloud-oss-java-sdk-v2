package com.aliyun.sdk.service.oss2.models;


import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutObjectAclResultTest {

    @Test
    public void testEmptyBuilder() {
        PutObjectAclResult result = PutObjectAclResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"",
                "x-oss-version-id", "version123456"
        );

        PutObjectAclResult result = PutObjectAclResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .innerBody("test-body")
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("version123456");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.versionId()).isEqualTo("version123456");

    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\"",
                "x-oss-version-id", "original-version"
        );

        PutObjectAclResult original = PutObjectAclResult.newBuilder()
                .headers(headers)
                .status("Created")
                .statusCode(201)
                .innerBody("original-body")
                .build();

        PutObjectAclResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.headers().get("x-oss-version-id")).isEqualTo("original-version");
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.versionId()).isEqualTo("original-version");
    }
}
