package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CleanRestoredObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        CleanRestoredObjectResult result = CleanRestoredObjectResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-request-id", "req-1234567890abcdefg");
        headers.put("x-oss-version-id", "v1234567890abcdefg");

        CleanRestoredObjectResult result = CleanRestoredObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("v1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-request-id", "req-765432109876543210");
        headers.put("x-oss-version-id", "v765432109876543210");

        CleanRestoredObjectResult original = CleanRestoredObjectResult.newBuilder()
                .headers(headers)
                .build();

        CleanRestoredObjectResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("x-oss-version-id")).isEqualTo("v765432109876543210");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-version-id", "v1234567890abcdefg");

        CleanRestoredObjectResult result = CleanRestoredObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("v1234567890abcdefg");
    }
}
