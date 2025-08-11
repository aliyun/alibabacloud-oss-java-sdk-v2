package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        GetObjectResult result = GetObjectResult.newBuilder().build();
        assertThat(result.contentLength()).isNull();
        assertThat(result.contentType()).isNull();
        assertThat(result.eTag()).isNull();
        assertThat(result.hashCrc64ecma()).isNull();
        assertThat(result.lastModified()).isNull();
        assertThat(result.serverSideEncryption()).isNull();
        assertThat(result.storageClass()).isNull();
        assertThat(result.objectType()).isNull();
        assertThat(result.processStatus()).isNull();
        assertThat(result.contentMd5()).isNull();
        assertThat(result.serverSideEncryptionKeyId()).isNull();
        assertThat(result.restore()).isNull();
        assertThat(result.taggingCount()).isNull();
        assertThat(result.expiration()).isNull();
        assertThat(result.nextAppendPosition()).isNull();
        assertThat(result.metadata()).isEmpty();
        assertThat(result.contentRange()).isNull();
        assertThat(result.contentDisposition()).isNull();
        assertThat(result.contentEncoding()).isNull();
        assertThat(result.expires()).isNull();
        assertThat(result.versionId()).isNull();
        assertThat(result.serverSideDataEncryption()).isNull();
        assertThat(result.deleteMarker()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "1024");
        headers.put("Content-Type", "application/xml");
        headers.put("ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"");
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");
        headers.put("Last-Modified", "Fri, 12 Oct 2022 00:00:00 GMT");
        headers.put("x-oss-server-side-encryption", "SM4");
        headers.put("x-oss-storage-class", "ColdArchive");
        headers.put("x-oss-object-type", "Normal");
        headers.put("x-oss-process-status", "Process");
        headers.put("Content-Md5", "B5eJF1ptWaXm4bijSPyxw==");
        headers.put("x-oss-server-side-encryption-key-id", "9468da86-3509-4f8d-a61e-6eab1eac****");
        headers.put("x-oss-restore", "Restore");
        headers.put("x‑oss‑tagging‑count", "10");
        headers.put("x-oss-expiration", "2022-10-12T00:00:00.000Z");
        headers.put("x-oss-next-append-position", "100");
        headers.put("x-oss-meta-custom", "custom-value");
        headers.put("Content-Range", "bytes 0-1023/1024");
        headers.put("Content-Disposition", "attachment");
        headers.put("Content-Encoding", "gzip");
        headers.put("Expires", "2022-10-12T00:00:00.000Z");
        headers.put("x-oss-version-id", "v1234567890abcdefg");
        headers.put("x-oss-server-side-data-encryption", "KMS");
        headers.put("x-oss-delete-marker", "true");

        GetObjectResult result = GetObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.contentLength()).isEqualTo(1024L);
        assertThat(result.contentType()).isEqualTo("application/xml");
        assertThat(result.eTag()).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.hashCrc64ecma()).isEqualTo("1234567890123456789");
        assertThat(result.lastModified()).isEqualTo("Fri, 12 Oct 2022 00:00:00 GMT");
        assertThat(result.serverSideEncryption()).isEqualTo("SM4");
        assertThat(result.storageClass()).isEqualTo("ColdArchive");
        assertThat(result.objectType()).isEqualTo("Normal");
        assertThat(result.processStatus()).isEqualTo("Process");
        assertThat(result.contentMd5()).isEqualTo("B5eJF1ptWaXm4bijSPyxw==");
        assertThat(result.serverSideEncryptionKeyId()).isEqualTo("9468da86-3509-4f8d-a61e-6eab1eac****");
        assertThat(result.restore()).isEqualTo("Restore");
        assertThat(result.taggingCount()).isEqualTo(10L);
        assertThat(result.expiration()).isEqualTo("2022-10-12T00:00:00.000Z");
        assertThat(result.nextAppendPosition()).isEqualTo(100L);
        assertThat(result.metadata().get("custom")).isEqualTo("custom-value");
        assertThat(result.contentRange()).isEqualTo("bytes 0-1023/1024");
        assertThat(result.contentDisposition()).isEqualTo("attachment");
        assertThat(result.contentEncoding()).isEqualTo("gzip");
        assertThat(result.expires()).isEqualTo("2022-10-12T00:00:00.000Z");
        assertThat(result.versionId()).isEqualTo("v1234567890abcdefg");
        assertThat(result.serverSideDataEncryption()).isEqualTo("KMS");
        assertThat(result.deleteMarker()).isEqualTo(true);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "2048");
        headers.put("Content-Type", "application/json");
        headers.put("ETag", "\"original-etag\"");
        headers.put("x-oss-hash-crc64ecma", "9876543210987654321");
        headers.put("Last-Modified", "Sat, 13 Oct 2022 00:00:00 GMT");
        headers.put("x-oss-server-side-encryption", "AES256");
        headers.put("x-oss-storage-class", "Standard");
        headers.put("x-oss-object-type", "Appendable");
        headers.put("x-oss-process-status", "NoProcess");
        headers.put("Content-Md5", "original-md5");
        headers.put("x-oss-server-side-encryption-key-id", "original-key-id");
        headers.put("x-oss-restore", "original-restore");
        headers.put("x‑oss‑tagging‑count", "5");
        headers.put("x-oss-expiration", "2022-10-13T00:00:00.000Z");
        headers.put("x-oss-next-append-position", "200");

        GetObjectResult original = GetObjectResult.newBuilder()
                .headers(headers)
                .build();

        GetObjectResult copy = original.toBuilder().build();

        assertThat(copy.contentLength()).isEqualTo(2048L);
        assertThat(copy.contentType()).isEqualTo("application/json");
        assertThat(copy.eTag()).isEqualTo("\"original-etag\"");
        assertThat(copy.hashCrc64ecma()).isEqualTo("9876543210987654321");
        assertThat(copy.lastModified()).isEqualTo("Sat, 13 Oct 2022 00:00:00 GMT");
        assertThat(copy.serverSideEncryption()).isEqualTo("AES256");
        assertThat(copy.storageClass()).isEqualTo("Standard");
        assertThat(copy.objectType()).isEqualTo("Appendable");
        assertThat(copy.processStatus()).isEqualTo("NoProcess");
        assertThat(copy.contentMd5()).isEqualTo("original-md5");
        assertThat(copy.serverSideEncryptionKeyId()).isEqualTo("original-key-id");
        assertThat(copy.restore()).isEqualTo("original-restore");
        assertThat(copy.taggingCount()).isEqualTo(5L);
        assertThat(copy.expiration()).isEqualTo("2022-10-13T00:00:00.000Z");
        assertThat(copy.nextAppendPosition()).isEqualTo(200L);
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/xml");
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");

        GetObjectResult result = GetObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.contentType()).isEqualTo("application/xml");
        assertThat(result.hashCrc64ecma()).isEqualTo("1234567890123456789");
    }
}
