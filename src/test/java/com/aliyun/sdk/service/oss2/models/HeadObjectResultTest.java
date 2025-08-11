package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HeadObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        HeadObjectResult result = HeadObjectResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.cacheControl()).isNull();
        assertThat(result.contentDisposition()).isNull();
        assertThat(result.contentEncoding()).isNull();
        assertThat(result.expires()).isNull();
        assertThat(result.versionId()).isNull();
        assertThat(result.serverSideDataEncryption()).isNull();
        assertThat(result.allowOrigin()).isNull();
        assertThat(result.allowMethods()).isNull();
        assertThat(result.allowAge()).isNull();
        assertThat(result.allowHeaders()).isNull();
        assertThat(result.exposeHeaders()).isNull();
        assertThat(result.restore()).isNull();
        assertThat(result.requestCharged()).isNull();
        assertThat(result.storageClass()).isNull();
        assertThat(result.hashCrc64ecma()).isNull();
        assertThat(result.serverSideEncryptionKeyId()).isNull();
        assertThat(result.nextAppendPosition()).isNull();
        assertThat(result.lastModified()).isNull();
        assertThat(result.contentType()).isNull();
        assertThat(result.objectType()).isNull();
        assertThat(result.contentMd5()).isNull();
        assertThat(result.processStatus()).isNull();
        assertThat(result.contentLength()).isNull();
        assertThat(result.eTag()).isNull();
        assertThat(result.metadata()).isEmpty();
        assertThat(result.transitionTime()).isNull();
        assertThat(result.taggingCount()).isNull();
        assertThat(result.serverSideEncryption()).isNull();
        assertThat(result.expiration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Cache-Control", "max-age=3600");
        headers.put("Content-Disposition", "attachment");
        headers.put("Content-Encoding", "gzip");
        headers.put("Expires", "2022-10-12T00:00:00.000Z");
        headers.put("x-oss-version-id", "v1234567890abcdefg");
        headers.put("x-oss-server-side-data-encryption", "KMS");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "GET,HEAD");
        headers.put("Access-Control-Allow-Age", "3000");
        headers.put("Access-Control-Allow-Headers", "Authorization");
        headers.put("Access-Control-Expose-Headers", "x-oss-header");
        headers.put("x-oss-restore", "ongoing-request");
        headers.put("x-oss-request-charged", "requester");
        headers.put("x-oss-storage-class", "ColdArchive");
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");
        headers.put("x-oss-server-side-encryption-key-id", "key-id-123");
        headers.put("x-oss-next-append-position", "100");
        headers.put("Last-Modified", "Fri, 12 Oct 2022 00:00:00 GMT");
        headers.put("Content-Type", "application/xml");
        headers.put("x-oss-object-type", "Normal");
        headers.put("Content-Md5", "B5eJF1ptWaXm4bijSPyxw==");
        headers.put("x-oss-process-status", "Process");
        headers.put("Content-Length", "1024");
        headers.put("ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"");
        headers.put("x-oss-meta-custom", "custom-value");
        headers.put("x-oss-transition-time", "2022-10-12T00:00:00.000Z");
        headers.put("x‑oss‑tagging‑count", "10");
        headers.put("x-oss-server-side-encryption", "AES256");
        headers.put("x-oss-expiration", "2022-10-12T00:00:00.000Z");

        HeadObjectResult result = HeadObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.cacheControl()).isEqualTo("max-age=3600");
        assertThat(result.contentDisposition()).isEqualTo("attachment");
        assertThat(result.contentEncoding()).isEqualTo("gzip");
        assertThat(result.expires()).isEqualTo("2022-10-12T00:00:00.000Z");
        assertThat(result.versionId()).isEqualTo("v1234567890abcdefg");
        assertThat(result.serverSideDataEncryption()).isEqualTo("KMS");
        assertThat(result.allowOrigin()).isEqualTo("*");
        assertThat(result.allowMethods()).isEqualTo("GET,HEAD");
        assertThat(result.allowAge()).isEqualTo("3000");
        assertThat(result.allowHeaders()).isEqualTo("Authorization");
        assertThat(result.exposeHeaders()).isEqualTo("x-oss-header");
        assertThat(result.restore()).isEqualTo("ongoing-request");
        assertThat(result.requestCharged()).isEqualTo("requester");
        assertThat(result.storageClass()).isEqualTo("ColdArchive");
        assertThat(result.hashCrc64ecma()).isEqualTo("1234567890123456789");
        assertThat(result.serverSideEncryptionKeyId()).isEqualTo("key-id-123");
        assertThat(result.nextAppendPosition()).isEqualTo(100L);
        assertThat(result.lastModified()).isEqualTo("Fri, 12 Oct 2022 00:00:00 GMT");
        assertThat(result.contentType()).isEqualTo("application/xml");
        assertThat(result.objectType()).isEqualTo("Normal");
        assertThat(result.contentMd5()).isEqualTo("B5eJF1ptWaXm4bijSPyxw==");
        assertThat(result.processStatus()).isEqualTo("Process");
        assertThat(result.contentLength()).isEqualTo(1024L);
        assertThat(result.eTag()).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.metadata()).hasSize(1);
        assertThat(result.metadata().get("custom")).isEqualTo("custom-value");
        assertThat(result.transitionTime()).isEqualTo("2022-10-12T00:00:00.000Z");
        assertThat(result.taggingCount()).isEqualTo(10L);
        assertThat(result.serverSideEncryption()).isEqualTo("AES256");
        assertThat(result.expiration()).isEqualTo("2022-10-12T00:00:00.000Z");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Cache-Control", "no-cache");
        headers.put("Content-Disposition", "inline");
        headers.put("Content-Encoding", "identity");
        headers.put("Expires", "2022-10-13T00:00:00.000Z");
        headers.put("x-oss-version-id", "v765432109876543210");
        headers.put("x-oss-server-side-data-encryption", "AES256");
        headers.put("Access-Control-Allow-Origin", "http://example.com");
        headers.put("Access-Control-Allow-Methods", "POST");
        headers.put("Access-Control-Allow-Age", "6000");
        headers.put("Access-Control-Allow-Headers", "X-Custom-Header");
        headers.put("Access-Control-Expose-Headers", "x-custom-expose");
        headers.put("x-oss-restore", "restored");
        headers.put("x-oss-request-charged", "requester");
        headers.put("x-oss-storage-class", "Standard");
        headers.put("x-oss-hash-crc64ecma", "9876543210987654321");
        headers.put("x-oss-server-side-encryption-key-id", "key-id-456");
        headers.put("x-oss-next-append-position", "200");
        headers.put("Last-Modified", "Sat, 13 Oct 2022 00:00:00 GMT");
        headers.put("Content-Type", "application/json");
        headers.put("x-oss-object-type", "Appendable");
        headers.put("Content-Md5", "original-md5");
        headers.put("x-oss-process-status", "NoProcess");
        headers.put("Content-Length", "2048");
        headers.put("ETag", "\"original-etag\"");
        headers.put("x-oss-meta-custom", "custom-value-2");
        headers.put("x-oss-transition-time", "2022-10-13T00:00:00.000Z");
        headers.put("x‑oss‑tagging‑count", "5");
        headers.put("x-oss-server-side-encryption", "SM4");
        headers.put("x-oss-expiration", "2022-10-13T00:00:00.000Z");

        HeadObjectResult original = HeadObjectResult.newBuilder()
                .headers(headers)
                .build();

        HeadObjectResult copy = original.toBuilder().build();

        assertThat(copy.cacheControl()).isEqualTo("no-cache");
        assertThat(copy.contentDisposition()).isEqualTo("inline");
        assertThat(copy.contentEncoding()).isEqualTo("identity");
        assertThat(copy.expires()).isEqualTo("2022-10-13T00:00:00.000Z");
        assertThat(copy.versionId()).isEqualTo("v765432109876543210");
        assertThat(copy.serverSideDataEncryption()).isEqualTo("AES256");
        assertThat(copy.allowOrigin()).isEqualTo("http://example.com");
        assertThat(copy.allowMethods()).isEqualTo("POST");
        assertThat(copy.allowAge()).isEqualTo("6000");
        assertThat(copy.allowHeaders()).isEqualTo("X-Custom-Header");
        assertThat(copy.exposeHeaders()).isEqualTo("x-custom-expose");
        assertThat(copy.restore()).isEqualTo("restored");
        assertThat(copy.requestCharged()).isEqualTo("requester");
        assertThat(copy.storageClass()).isEqualTo("Standard");
        assertThat(copy.hashCrc64ecma()).isEqualTo("9876543210987654321");
        assertThat(copy.serverSideEncryptionKeyId()).isEqualTo("key-id-456");
        assertThat(copy.nextAppendPosition()).isEqualTo(200L);
        assertThat(copy.lastModified()).isEqualTo("Sat, 13 Oct 2022 00:00:00 GMT");
        assertThat(copy.contentType()).isEqualTo("application/json");
        assertThat(copy.objectType()).isEqualTo("Appendable");
        assertThat(copy.contentMd5()).isEqualTo("original-md5");
        assertThat(copy.processStatus()).isEqualTo("NoProcess");
        assertThat(copy.contentLength()).isEqualTo(2048L);
        assertThat(copy.eTag()).isEqualTo("\"original-etag\"");
        assertThat(copy.metadata()).hasSize(1);
        assertThat(copy.metadata().get("custom")).isEqualTo("custom-value-2");
        assertThat(copy.transitionTime()).isEqualTo("2022-10-13T00:00:00.000Z");
        assertThat(copy.taggingCount()).isEqualTo(5L);
        assertThat(copy.serverSideEncryption()).isEqualTo("SM4");
        assertThat(copy.expiration()).isEqualTo("2022-10-13T00:00:00.000Z");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/xml");
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");

        HeadObjectResult result = HeadObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.contentType()).isEqualTo("application/xml");
        assertThat(result.hashCrc64ecma()).isEqualTo("1234567890123456789");
    }
}
