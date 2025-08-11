package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CopyObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        CopyObjectResult result = CopyObjectResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.versionId()).isNull();
        assertThat(result.hashCrc64()).isNull();
        assertThat(result.serverSideEncryption()).isNull();
        assertThat(result.serverSideDataEncryption()).isNull();
        assertThat(result.serverSideEncryptionKeyId()).isNull();
        assertThat(result.eTag()).isNull();
        assertThat(result.lastModified()).isNull();
        assertThat(result.copySourceVersionId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-copy-source-version-id", "copy-source-version-123");
        headers.put("x-oss-version-id", "version-123");
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");
        headers.put("x-oss-server-side-encryption", "AES256");
        headers.put("x-oss-server-side-data-encryption", "KMS");
        headers.put("x-oss-server-side-encryption-key-id", "key-id-123");
        headers.put("ETag", "\"etag-123\"");
        headers.put("Last-Modified", "Wed, 14 Oct 2022 00:00:00 GMT");

        CopyObjectResult result = CopyObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.copySourceVersionId()).isEqualTo("copy-source-version-123");
        assertThat(result.versionId()).isEqualTo("version-123");
        assertThat(result.hashCrc64()).isEqualTo("1234567890123456789");
        assertThat(result.serverSideEncryption()).isEqualTo("AES256");
        assertThat(result.serverSideDataEncryption()).isEqualTo("KMS");
        assertThat(result.serverSideEncryptionKeyId()).isEqualTo("key-id-123");
        assertThat(result.eTag()).isEqualTo("\"etag-123\"");
        assertThat(result.lastModified()).isEqualTo("Wed, 14 Oct 2022 00:00:00 GMT");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-copy-source-version-id", "copy-source-version-456");
        headers.put("x-oss-version-id", "version-456");
        headers.put("x-oss-hash-crc64ecma", "9876543210987654321");
        headers.put("x-oss-server-side-encryption", "KMS");
        headers.put("x-oss-server-side-data-encryption", "AES256");
        headers.put("x-oss-server-side-encryption-key-id", "key-id-456");
        headers.put("ETag", "\"etag-456\"");
        headers.put("Last-Modified", "Thu, 15 Oct 2022 00:00:00 GMT");

        CopyObjectResult original = CopyObjectResult.newBuilder()
                .headers(headers)
                .build();

        CopyObjectResult copy = original.toBuilder().build();

        assertThat(copy.copySourceVersionId()).isEqualTo("copy-source-version-456");
        assertThat(copy.versionId()).isEqualTo("version-456");
        assertThat(copy.hashCrc64()).isEqualTo("9876543210987654321");
        assertThat(copy.serverSideEncryption()).isEqualTo("KMS");
        assertThat(copy.serverSideDataEncryption()).isEqualTo("AES256");
        assertThat(copy.serverSideEncryptionKeyId()).isEqualTo("key-id-456");
        assertThat(copy.eTag()).isEqualTo("\"etag-456\"");
        assertThat(copy.lastModified()).isEqualTo("Thu, 15 Oct 2022 00:00:00 GMT");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");

        CopyObjectResult result = CopyObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.hashCrc64()).isEqualTo("1234567890123456789");
    }
}
