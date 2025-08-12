package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketAcl;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketAclRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketAclRequest request = PutBucketAclRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        PutBucketAclRequest request = PutBucketAclRequest.newBuilder()
                .bucket("example-bucket")
                .acl("public-read")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("example-bucket");
        assertThat(request.acl()).isEqualTo("public-read");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        PutBucketAclRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("example-bucket");
        assertThat(copy.acl()).isEqualTo("public-read");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        PutBucketAclRequest original = PutBucketAclRequest.newBuilder()
                .bucket("test-bucket")
                .acl("private")
                .build();

        PutBucketAclRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.acl()).isEqualTo("private");
    }

    @Test
    public void testHeaderProperties() {
        PutBucketAclRequest request = PutBucketAclRequest.newBuilder()
                .bucket("acl-bucket")
                .acl("public-read-write")
                .build();

        assertThat(request.bucket()).isEqualTo("acl-bucket");
        assertThat(request.acl()).isEqualTo("public-read-write");
    }

    @Test
    public void xmlBuilder() {
        PutBucketAclRequest request = PutBucketAclRequest.newBuilder()
                .bucket("xml-bucket")
                .acl(ObjectACLType.PUBLIC_READ)
                .build();

        OperationInput input = SerdeBucketAcl.fromPutBucketAcl(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.headers().get("x-oss-acl")).isEqualTo(ObjectACLType.PUBLIC_READ.toString());

    }
}
