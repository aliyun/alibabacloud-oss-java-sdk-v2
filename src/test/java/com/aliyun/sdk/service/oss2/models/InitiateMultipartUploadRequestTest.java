package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class InitiateMultipartUploadRequestTest {
    @Test
    public void testEmptyBuilder() {
        InitiateMultipartUploadRequest request = InitiateMultipartUploadRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.forbidOverwrite()).isNull();
        assertThat(request.storageClass()).isNull();
        assertThat(request.tagging()).isNull();
        assertThat(request.serverSideEncryption()).isNull();
        assertThat(request.serverSideDataEncryption()).isNull();
        assertThat(request.serverSideEncryptionKeyId()).isNull();
        assertThat(request.cacheControl()).isNull();
        assertThat(request.contentDisposition()).isNull();
        assertThat(request.contentEncoding()).isNull();
        assertThat(request.expires()).isNull();
        assertThat(request.encodingType()).isNull();
        assertThat(request.contentLength()).isNull();
        assertThat(request.contentMD5()).isNull();
        assertThat(request.contentType()).isNull();
        assertThat(request.requestPayer()).isNull();
        assertThat(request.metadata()).isNotNull();
        assertThat(request.metadata().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        Map<String, String> metadata = MapUtils.of(
                "meta1", "value1",
                "meta2", "value2"
        );

        InitiateMultipartUploadRequest request = InitiateMultipartUploadRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .forbidOverwrite("true")
                .storageClass("Standard")
                .tagging("tag1=value1&tag2=value2")
                .serverSideEncryption("AES256")
                .serverSideDataEncryption("SM4")
                .serverSideEncryptionKeyId("key-id")
                .cacheControl("no-cache")
                .contentDisposition("attachment")
                .contentEncoding("gzip")
                .expires("Thu, 01 Dec 1994 16:00:00 GMT")
                .encodingType("url")
                .contentLength(1024L)
                .contentMD5("md5hash")
                .contentType("application/octet-stream")
                .requestPayer("requester")
                .metadata(metadata)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("examplekey");
        assertThat(request.forbidOverwrite()).isEqualTo("true");
        assertThat(request.storageClass()).isEqualTo("Standard");
        assertThat(request.tagging()).isEqualTo("tag1=value1&tag2=value2");
        assertThat(request.serverSideEncryption()).isEqualTo("AES256");
        assertThat(request.serverSideDataEncryption()).isEqualTo("SM4");
        assertThat(request.serverSideEncryptionKeyId()).isEqualTo("key-id");
        assertThat(request.cacheControl()).isEqualTo("no-cache");
        assertThat(request.contentDisposition()).isEqualTo("attachment");
        assertThat(request.contentEncoding()).isEqualTo("gzip");
        assertThat(request.expires()).isEqualTo("Thu, 01 Dec 1994 16:00:00 GMT");
        assertThat(request.encodingType()).isEqualTo("url");
        assertThat(request.contentLength()).isEqualTo(1024L);
        assertThat(request.contentMD5()).isEqualTo("md5hash");
        assertThat(request.contentType()).isEqualTo("application/octet-stream");
        assertThat(request.requestPayer()).isEqualTo("requester");
        assertThat(request.metadata()).contains(
                new AbstractMap.SimpleEntry<>("meta1", "value1"),
                new AbstractMap.SimpleEntry<>("meta2", "value2")
        );

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        Map<String, String> metadata = MapUtils.of(
                "meta3", "value3",
                "meta4", "value4"
        );

        InitiateMultipartUploadRequest original = InitiateMultipartUploadRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .forbidOverwrite("false")
                .storageClass("IA")
                .tagging("tag3=value3&tag4=value4")
                .serverSideEncryption("KMS")
                .serverSideDataEncryption("SM4")
                .serverSideEncryptionKeyId("key-id-2")
                .cacheControl("private")
                .contentDisposition("inline")
                .contentEncoding("deflate")
                .expires("Thu, 01 Dec 1995 16:00:00 GMT")
                .encodingType("url")
                .contentLength(2048L)
                .contentMD5("md5hash2")
                .contentType("text/plain")
                .requestPayer("requester")
                .metadata(metadata)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        InitiateMultipartUploadRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("examplekey");
        assertThat(copy.forbidOverwrite()).isEqualTo("false");
        assertThat(copy.storageClass()).isEqualTo("IA");
        assertThat(copy.tagging()).isEqualTo("tag3=value3&tag4=value4");
        assertThat(copy.serverSideEncryption()).isEqualTo("KMS");
        assertThat(copy.serverSideDataEncryption()).isEqualTo("SM4");
        assertThat(copy.serverSideEncryptionKeyId()).isEqualTo("key-id-2");
        assertThat(copy.cacheControl()).isEqualTo("private");
        assertThat(copy.contentDisposition()).isEqualTo("inline");
        assertThat(copy.contentEncoding()).isEqualTo("deflate");
        assertThat(copy.expires()).isEqualTo("Thu, 01 Dec 1995 16:00:00 GMT");
        assertThat(copy.encodingType()).isEqualTo("url");
        assertThat(copy.contentLength()).isEqualTo(2048L);
        assertThat(copy.contentMD5()).isEqualTo("md5hash2");
        assertThat(copy.contentType()).isEqualTo("text/plain");
        assertThat(copy.requestPayer()).isEqualTo("requester");
        assertThat(copy.metadata()).contains(
                new AbstractMap.SimpleEntry<>("meta3", "value3"),
                new AbstractMap.SimpleEntry<>("meta4", "value4")
        );

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        Map<String, String> metadata = MapUtils.of(
                "meta5", "value5",
                "meta6", "value6"
        );

        InitiateMultipartUploadRequest request = InitiateMultipartUploadRequest.newBuilder()
                .bucket("examplebucket")
                .key("examplekey")
                .forbidOverwrite(true)
                .storageClass("Archive")
                .tagging("tag5=value5&tag6=value6")
                .serverSideEncryption("AES256")
                .serverSideDataEncryption("SM4")
                .serverSideEncryptionKeyId("key-id-3")
                .cacheControl("public")
                .contentDisposition("attachment; filename=\"filename.txt\"")
                .contentEncoding("br")
                .expires("Thu, 01 Dec 1996 16:00:00 GMT")
                .encodingType("url")
                .contentLength(4096L)
                .contentMD5("md5hash3")
                .contentType("application/json")
                .requestPayer("requester")
                .metadata(metadata)
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("examplekey");
        assertThat(request.forbidOverwrite()).isEqualTo("true");
        assertThat(request.storageClass()).isEqualTo("Archive");
        assertThat(request.tagging()).isEqualTo("tag5=value5&tag6=value6");
        assertThat(request.serverSideEncryption()).isEqualTo("AES256");
        assertThat(request.serverSideDataEncryption()).isEqualTo("SM4");
        assertThat(request.serverSideEncryptionKeyId()).isEqualTo("key-id-3");
        assertThat(request.cacheControl()).isEqualTo("public");
        assertThat(request.contentDisposition()).isEqualTo("attachment; filename=\"filename.txt\"");
        assertThat(request.contentEncoding()).isEqualTo("br");
        assertThat(request.expires()).isEqualTo("Thu, 01 Dec 1996 16:00:00 GMT");
        assertThat(request.encodingType()).isEqualTo("url");
        assertThat(request.contentLength()).isEqualTo(4096L);
        assertThat(request.contentMD5()).isEqualTo("md5hash3");
        assertThat(request.contentType()).isEqualTo("application/json");
        assertThat(request.requestPayer()).isEqualTo("requester");
        assertThat(request.metadata()).contains(
                new AbstractMap.SimpleEntry<>("meta5", "value5"),
                new AbstractMap.SimpleEntry<>("meta6", "value6")
        );
    }
}
