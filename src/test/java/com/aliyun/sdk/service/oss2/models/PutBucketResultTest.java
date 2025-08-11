package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketResultTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketResult result = PutBucketResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.status()).isEqualTo("");
        assertThat(result.statusCode()).isEqualTo(0);
        assertThat(result.requestId()).isEqualTo("");
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-request-id", "req-1234567890abcdefg");
        headers.put("ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"");
        headers.put("Location", "/my-bucket");
        headers.put("x-oss-server-side-encryption", "AES256");
        headers.put("x-oss-server-side-encryption-key-id", "key-1234567890abcdefg");
        headers.put("x-oss-version-id", "v1234567890abcdefg");
        headers.put("Date", "Fri, 12 Oct 2022 00:00:00 GMT");
        headers.put("x-oss-sse-kms-key-id", "arn:acs:kms:cn-hangzhou:123456789012:12345678-1234-1234-1234-123456789012");

        PutBucketResult result = PutBucketResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.headers().get("Location")).isEqualTo("/my-bucket");
        assertThat(result.headers().get("x-oss-server-side-encryption")).isEqualTo("AES256");
        assertThat(result.headers().get("x-oss-server-side-encryption-key-id")).isEqualTo("key-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("v1234567890abcdefg");
        assertThat(result.headers().get("Date")).isEqualTo("Fri, 12 Oct 2022 00:00:00 GMT");
        assertThat(result.headers().get("x-oss-sse-kms-key-id")).isEqualTo("arn:acs:kms:cn-hangzhou:123456789012:12345678-1234-1234-1234-123456789012");

        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-request-id", "req-765432109876543210");
        headers.put("ETag", "\"original-etag\"");
        headers.put("Location", "/original-bucket");
        headers.put("x-oss-server-side-encryption", "SM4");
        headers.put("x-oss-server-side-encryption-key-id", "original-key-id");
        headers.put("x-oss-version-id", "v765432109876543210");
        headers.put("Date", "Sat, 13 Oct 2022 00:00:00 GMT");
        headers.put("x-oss-sse-kms-key-id", "arn:acs:kms:cn-hangzhou:123456789012:87654321-4321-4321-4321-210987654321");

        PutBucketResult original = PutBucketResult.newBuilder()
                .headers(headers)
                .status("Created")
                .statusCode(201)
                .build();

        PutBucketResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.headers().get("Location")).isEqualTo("/original-bucket");
        assertThat(copy.headers().get("x-oss-server-side-encryption")).isEqualTo("SM4");
        assertThat(copy.headers().get("x-oss-server-side-encryption-key-id")).isEqualTo("original-key-id");
        assertThat(copy.headers().get("x-oss-version-id")).isEqualTo("v765432109876543210");
        assertThat(copy.headers().get("Date")).isEqualTo("Sat, 13 Oct 2022 00:00:00 GMT");
        assertThat(copy.headers().get("x-oss-sse-kms-key-id")).isEqualTo("arn:acs:kms:cn-hangzhou:123456789012:87654321-4321-4321-4321-210987654321");

        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-request-id", "req-1234567890abcdefg");
        headers.put("Content-Type", "application/xml");

        PutBucketResult result = PutBucketResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/xml");
    }
}