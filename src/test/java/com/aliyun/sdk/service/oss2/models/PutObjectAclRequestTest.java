package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutObjectAclRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutObjectAclRequest request = PutObjectAclRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-payer", "requester"
        );

        PutObjectAclRequest request = PutObjectAclRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .headers(headers)
                .parameter("versionId", "1234567890")
                .objectAcl("private")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject.txt");

        assertThat(request.headers().get("x-oss-request-payer")).isEqualTo("requester");
        assertThat(request.headers().get("x-oss-object-acl")).isEqualTo("private");
        assertThat(request.parameters()).contains(new AbstractMap.SimpleEntry<>("versionId", "1234567890"));
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-payer", "requester"
        );

        PutObjectAclRequest original = PutObjectAclRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .headers(headers)
                .parameter("versionId", "9876543210")
                .objectAcl("public-read")
                .build();

        PutObjectAclRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("exampleobject.txt");

        assertThat(copy.headers().get("x-oss-request-payer")).isEqualTo("requester");
        assertThat(copy.headers().get("x-oss-object-acl")).isEqualTo("public-read");
        assertThat(copy.parameters()).contains(new AbstractMap.SimpleEntry<>("versionId", "9876543210"));
    }

    @Test
    public void testHeaderProperties() {
        PutObjectAclRequest request = PutObjectAclRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .versionId("1234567890")
                .requestPayer("requester")
                .objectAcl("public-read-write")
                .build();

        assertThat(request.versionId()).isEqualTo("1234567890");
        assertThat(request.requestPayer()).isEqualTo("requester");
        assertThat(request.objectAcl()).isEqualTo("public-read-write");
    }
}