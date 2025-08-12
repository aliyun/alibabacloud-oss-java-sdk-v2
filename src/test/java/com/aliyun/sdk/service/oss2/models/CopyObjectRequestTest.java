package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CopyObjectRequestTest {

    @Test
    public void testEmptyBuilder() {
        CopyObjectRequest request = CopyObjectRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.sourceBucket()).isNull();
        assertThat(request.sourceKey()).isNull();
        assertThat(request.sourceVersionId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .sourceBucket("sourcebucket")
                .sourceKey("sourceobject.txt")
                .sourceVersionId("version123")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject.txt");
        assertThat(request.sourceBucket()).isEqualTo("sourcebucket");
        assertThat(request.sourceKey()).isEqualTo("sourceobject.txt");
        assertThat(request.sourceVersionId()).isEqualTo("version123");

        assertThat(request.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        CopyObjectRequest original = CopyObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .sourceBucket("sourcebucket")
                .sourceKey("sourceobject.txt")
                .sourceVersionId("version456")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        CopyObjectRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("exampleobject.txt");
        assertThat(copy.sourceBucket()).isEqualTo("sourcebucket");
        assertThat(copy.sourceKey()).isEqualTo("sourceobject.txt");
        assertThat(copy.sourceVersionId()).isEqualTo("version456");

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

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .sourceBucket("sourcebucket")
                .sourceKey("sourceobject.txt")
                .sourceVersionId("version789")
                .forbidOverwrite("false")
                .copySourceIfMatch("etag-match")
                .copySourceIfNoneMatch("etag-none-match")
                .copySourceIfUnmodifiedSince("2023-12-08T08:12:20.000Z")
                .copySourceIfModifiedSince("2023-12-08T08:12:20.000Z")
                .metadataDirective("COPY")
                .serverSideEncryption("AES256")
                .serverSideDataEncryption("KMS")
                .serverSideEncryptionKeyId("key-1234567890")
                .objectAcl("private")
                .storageClass("Standard")
                .tagging("TagA=A&TagB=B")
                .taggingDirective("Copy")
                .cacheControl("no-cache")
                .contentDisposition("attachment")
                .contentEncoding("gzip")
                .contentLength(1024L)
                .contentMd5("md5sum")
                .contentType("text/plain")
                .expires("2023-12-08T08:12:20.000Z")
                .trafficLimit(1000000)
                .requestPayer("requester")
                .metadata(metadata)
                .build();

        assertThat(request.sourceBucket()).isEqualTo("sourcebucket");
        assertThat(request.sourceKey()).isEqualTo("sourceobject.txt");
        assertThat(request.sourceVersionId()).isEqualTo("version789");
        assertThat(request.forbidOverwrite()).isEqualTo("false");
        assertThat(request.copySourceIfMatch()).isEqualTo("etag-match");
        assertThat(request.copySourceIfNoneMatch()).isEqualTo("etag-none-match");
        assertThat(request.copySourceIfUnmodifiedSince()).isEqualTo("2023-12-08T08:12:20.000Z");
        assertThat(request.copySourceIfModifiedSince()).isEqualTo("2023-12-08T08:12:20.000Z");
        assertThat(request.metadataDirective()).isEqualTo("COPY");
        assertThat(request.serverSideEncryption()).isEqualTo("AES256");
        assertThat(request.serverSideDataEncryption()).isEqualTo("KMS");
        assertThat(request.serverSideEncryptionKeyId()).isEqualTo("key-1234567890");
        assertThat(request.objectAcl()).isEqualTo("private");
        assertThat(request.storageClass()).isEqualTo("Standard");
        assertThat(request.tagging()).isEqualTo("TagA=A&TagB=B");
        assertThat(request.taggingDirective()).isEqualTo("Copy");
        assertThat(request.cacheControl()).isEqualTo("no-cache");
        assertThat(request.contentDisposition()).isEqualTo("attachment");
        assertThat(request.contentEncoding()).isEqualTo("gzip");
        assertThat(request.contentLength()).isEqualTo(1024L);
        assertThat(request.contentMd5()).isEqualTo("md5sum");
        assertThat(request.contentType()).isEqualTo("text/plain");
        assertThat(request.expires()).isEqualTo("2023-12-08T08:12:20.000Z");
        assertThat(request.trafficLimit()).isEqualTo(1000000);
        assertThat(request.requestPayer()).isEqualTo("requester");

        Map<String, String> resultMetadata = request.metadata();
        assertThat(resultMetadata).contains(
                new AbstractMap.SimpleEntry<>("client-side-encryption-key", "nyXOp7delQ/MQLjKQMhHLaTHIB6q+C+RA6lGwqqYVa+n3aV5uWhygyv1MWmESurppg="),
                new AbstractMap.SimpleEntry<>("client-side-encryption-start", "De/S3T8wFjx7QPxAAFl7h7TeI2EsZlfCwovrHyoSZGr343NxCUGIp6fQ9sSuOLMoJg7hNw="),
                new AbstractMap.SimpleEntry<>("client-side-encryption-cek-alg", "AES/CTR/NoPadding"),
                new AbstractMap.SimpleEntry<>("client-side-encryption-wrap-alg", "RSA/NONE/PKCS1Padding")
        );
    }

    @Test
    public void xmlBuilder() {

        CopyObjectRequest request = CopyObjectRequest.newBuilder()
                .bucket("destbucket")
                .key("destobject.txt")
                .sourceBucket("sourcebucket")
                .sourceKey("source object.txt")
                .sourceVersionId("version123")
                .build();

        OperationInput input = SerdeObjectBasic.fromCopyObject(request);

        assertThat(input.bucket().get()).isEqualTo("destbucket");
        assertThat(input.key().get()).isEqualTo("destobject.txt");
        assertThat(input.headers().get("x-oss-copy-source")).isEqualTo("/sourcebucket/source%20object.txt?versionId=version123");
    }
}
