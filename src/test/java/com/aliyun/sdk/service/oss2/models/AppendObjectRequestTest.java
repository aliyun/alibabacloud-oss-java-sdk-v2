package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AppendObjectRequestTest {
    @Test
    public void testEmptyBuilder() {
        AppendObjectRequest request = AppendObjectRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.body()).isNull();
        assertThat(request.progressListener()).isNull();
    }

    @Test
    public void testFullBuilder() {
        BinaryData body = StringBinaryData.fromString("test-content");

        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        AppendObjectRequest request = AppendObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .body(body)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject.txt");
        assertThat(request.body()).isEqualTo(body);

        assertThat(request.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        BinaryData body = StringBinaryData.fromString("test-content");

        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        AppendObjectRequest original = AppendObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .body(body)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        AppendObjectRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("exampleobject.txt");
        assertThat(copy.body()).isEqualTo(body);

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testHeaderProperties() {
        Map<String, String> metadata = MapUtils.of(
                "client-side-encryption-key", "nyXOp7delQ/MQLjKQMhHLaTHIB6q+C+RA6lGwqqYVa+n3aV5uWhygyv1MWmESurppg=",
                "client-side-encryption-start", "De/S3T8wFjx7QPxAAFl7h7TeI2EsZlfCwovrHyoSZGr343NxCUGIp6fQ9sSuOLMoJg7hNw=",
                "client-side-encryption-cek-alg", "AES/CTR/NoPadding",
                "client-side-encryption-wrap-alg", "RSA/NONE/PKCS1Padding"
        );

        AppendObjectRequest request = AppendObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .body(StringBinaryData.fromString("test-content"))
                .forbidOverwrite(false)
                .serverSideEncryption("AES256")
                .serverSideDataEncryption("SM4")
                .serverSideEncryptionKeyId("key-1234567890")
                .objectAcl("private")
                .storageClass("Standard")
                .tagging("TagA=A&TagB=B")
                .cacheControl("no-cache")
                .contentDisposition("attachment")
                .contentEncoding("gzip")
                .contentLength(1024L)
                .contentMd5("md5sum")
                .contentType("text/plain")
                .expires("2023-12-08T08:12:20.000Z")
                .requestPayer("requester")
                .trafficLimit(1000000)
                .metadata(metadata)
                .build();

        assertThat(request.forbidOverwrite()).isEqualTo(false);
        assertThat(request.serverSideEncryption()).isEqualTo("AES256");
        assertThat(request.serverSideDataEncryption()).isEqualTo("SM4");
        assertThat(request.serverSideEncryptionKeyId()).isEqualTo("key-1234567890");
        assertThat(request.objectAcl()).isEqualTo("private");
        assertThat(request.storageClass()).isEqualTo("Standard");
        assertThat(request.tagging()).isEqualTo("TagA=A&TagB=B");
        assertThat(request.cacheControl()).isEqualTo("no-cache");
        assertThat(request.contentDisposition()).isEqualTo("attachment");
        assertThat(request.contentEncoding()).isEqualTo("gzip");
        assertThat(request.contentLength()).isEqualTo(1024);
        assertThat(request.contentMd5()).isEqualTo("md5sum");
        assertThat(request.contentType()).isEqualTo("text/plain");
        assertThat(request.expires()).isEqualTo("2023-12-08T08:12:20.000Z");
        assertThat(request.requestPayer()).isEqualTo("requester");
        assertThat(request.trafficLimit()).isEqualTo(1000000);

        Map<String, String> resultMetadata = request.metadata();
        assertThat(resultMetadata).contains(
                new AbstractMap.SimpleEntry<>("client-side-encryption-key", "nyXOp7delQ/MQLjKQMhHLaTHIB6q+C+RA6lGwqqYVa+n3aV5uWhygyv1MWmESurppg="),
                new AbstractMap.SimpleEntry<>("client-side-encryption-start", "De/S3T8wFjx7QPxAAFl7h7TeI2EsZlfCwovrHyoSZGr343NxCUGIp6fQ9sSuOLMoJg7hNw="),
                new AbstractMap.SimpleEntry<>("client-side-encryption-cek-alg", "AES/CTR/NoPadding"),
                new AbstractMap.SimpleEntry<>("client-side-encryption-wrap-alg", "RSA/NONE/PKCS1Padding")
        );
    }
}