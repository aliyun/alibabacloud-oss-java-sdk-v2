package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RestoreObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        RestoreObjectResult result = RestoreObjectResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.objectRestorePriority()).isNull();
        assertThat(result.versionId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-object-restore-priority", "Standard");
        headers.put("x-oss-version-id", "v1234567890abcdefg");

        RestoreObjectResult result = RestoreObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.objectRestorePriority()).isEqualTo("Standard");
        assertThat(result.versionId()).isEqualTo("v1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-object-restore-priority", "Expedited");
        headers.put("x-oss-version-id", "v765432109876543210");

        RestoreObjectResult original = RestoreObjectResult.newBuilder()
                .headers(headers)
                .build();

        RestoreObjectResult copy = original.toBuilder().build();

        assertThat(copy.objectRestorePriority()).isEqualTo("Expedited");
        assertThat(copy.versionId()).isEqualTo("v765432109876543210");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-object-restore-priority", "Standard");

        RestoreObjectResult result = RestoreObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.objectRestorePriority()).isEqualTo("Standard");
    }
}
