package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AppendObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        AppendObjectResult result = AppendObjectResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.nextAppendPosition()).isNull();
        assertThat(result.hashCrc64ecma()).isNull();
        assertThat(result.versionId()).isNull();
        assertThat(result.serverSideEncryption()).isNull();
        assertThat(result.serverSideDataEncryption()).isNull();
        assertThat(result.serverSideEncryptionKeyId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-next-append-position", "100");
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");
        headers.put("versionId", "v1234567890abcdefg");
        headers.put("x-oss-server-side-encryption", "AES256");
        headers.put("x-oss-server-side-data-encryption", "KMS");
        headers.put("x-oss-server-side-encryption-key-id", "key-id-123");

        AppendObjectResult result = AppendObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.nextAppendPosition()).isEqualTo(100L);
        assertThat(result.hashCrc64ecma()).isEqualTo("1234567890123456789");
        assertThat(result.versionId()).isEqualTo("v1234567890abcdefg");
        assertThat(result.serverSideEncryption()).isEqualTo("AES256");
        assertThat(result.serverSideDataEncryption()).isEqualTo("KMS");
        assertThat(result.serverSideEncryptionKeyId()).isEqualTo("key-id-123");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-next-append-position", "200");
        headers.put("x-oss-hash-crc64ecma", "9876543210987654321");
        headers.put("versionId", "v765432109876543210");
        headers.put("x-oss-server-side-encryption", "KMS");
        headers.put("x-oss-server-side-data-encryption", "AES256");
        headers.put("x-oss-server-side-encryption-key-id", "key-id-456");

        AppendObjectResult original = AppendObjectResult.newBuilder()
                .headers(headers)
                .build();

        AppendObjectResult copy = original.toBuilder().build();

        assertThat(copy.nextAppendPosition()).isEqualTo(200L);
        assertThat(copy.hashCrc64ecma()).isEqualTo("9876543210987654321");
        assertThat(copy.versionId()).isEqualTo("v765432109876543210");
        assertThat(copy.serverSideEncryption()).isEqualTo("KMS");
        assertThat(copy.serverSideDataEncryption()).isEqualTo("AES256");
        assertThat(copy.serverSideEncryptionKeyId()).isEqualTo("key-id-456");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");

        AppendObjectResult result = AppendObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.hashCrc64ecma()).isEqualTo("1234567890123456789");
    }
}
