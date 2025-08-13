package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBucketResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteBucketResult result = DeleteBucketResult.newBuilder().build();
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
        headers.put("x-oss-request-id", "req-1234567890abcdefg");
        headers.put("ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"");
        headers.put("Date", "Fri, 12 Oct 2022 00:00:00 GMT");

        DeleteBucketResult result = DeleteBucketResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(204)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.headers().get("Date")).isEqualTo("Fri, 12 Oct 2022 00:00:00 GMT");

        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(204);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-request-id", "req-765432109876543210");
        headers.put("ETag", "\"original-etag\"");
        headers.put("Date", "Sat, 13 Oct 2022 00:00:00 GMT");

        DeleteBucketResult original = DeleteBucketResult.newBuilder()
                .headers(headers)
                .status("No Content")
                .statusCode(204)
                .build();

        DeleteBucketResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.headers().get("Date")).isEqualTo("Sat, 13 Oct 2022 00:00:00 GMT");

        assertThat(copy.status()).isEqualTo("No Content");
        assertThat(copy.statusCode()).isEqualTo(204);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-request-id", "req-1234567890abcdefg");
        headers.put("Content-Type", "application/xml");

        DeleteBucketResult result = DeleteBucketResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/xml");
    }
}