package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteObjectResult result = DeleteObjectResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.deleteMarker()).isNull();
        assertThat(result.versionId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-delete-marker", "true");
        headers.put("x-oss-version-id", "v1234567890abcdefg");

        DeleteObjectResult result = DeleteObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.deleteMarker()).isEqualTo(true);
        assertThat(result.versionId()).isEqualTo("v1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-delete-marker", "false");
        headers.put("x-oss-version-id", "v765432109876543210");

        DeleteObjectResult original = DeleteObjectResult.newBuilder()
                .headers(headers)
                .build();

        DeleteObjectResult copy = original.toBuilder().build();

        assertThat(copy.deleteMarker()).isEqualTo(false);
        assertThat(copy.versionId()).isEqualTo("v765432109876543210");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-delete-marker", "true");

        DeleteObjectResult result = DeleteObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.deleteMarker()).isEqualTo(true);
    }
}
