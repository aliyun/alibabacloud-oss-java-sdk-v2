package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorBucketBasic;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class PutVectorBucketRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutVectorBucketRequest request = PutVectorBucketRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.resourceGroupId()).isNull();
        assertThat(request.bucketTagging()).isNull();
    }

    @Test
    public void testFullBuilder() {
        PutVectorBucketRequest request = PutVectorBucketRequest.newBuilder()
                .bucket("test-bucket")
                .resourceGroupId("rg-123456")
                .bucketTagging("tag1=value1&tag2=value2")
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.resourceGroupId()).isEqualTo("rg-123456");
        assertThat(request.bucketTagging()).isEqualTo("tag1=value1&tag2=value2");

        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-oss-header1", "header-value1"),
                new AbstractMap.SimpleEntry<>("x-oss-header2", "header-value2")
        );

        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        PutVectorBucketRequest original = PutVectorBucketRequest.newBuilder()
                .bucket("original-bucket")
                .resourceGroupId("rg-789012")
                .bucketTagging("env=test&team=dev")
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        PutVectorBucketRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("original-bucket");
        assertThat(copy.resourceGroupId()).isEqualTo("rg-789012");
        assertThat(copy.bucketTagging()).isEqualTo("env=test&team=dev");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void testHeaderProperties() {
        PutVectorBucketRequest request = PutVectorBucketRequest.newBuilder()
                .bucket("header-test-bucket")
                .resourceGroupId("rg-abcdef")
                .bucketTagging("department=engineering")
                .build();

        assertThat(request.bucket()).isEqualTo("header-test-bucket");
        assertThat(request.resourceGroupId()).isEqualTo("rg-abcdef");
        assertThat(request.bucketTagging()).isEqualTo("department=engineering");
    }

    @Test
    public void bodyBuilder() throws JsonProcessingException {
        PutVectorBucketRequest request = PutVectorBucketRequest.newBuilder()
                .bucket("body-test-bucket")
                .resourceGroupId("rg-body")
                .bucketTagging("type=test")
                .build();

        OperationInput input = SerdeVectorBucketBasic.fromPutVectorBucket(request);

        assertThat(input.bucket().get()).isEqualTo("body-test-bucket");
        assertThat(input.headers().get("x-oss-resource-group-id")).isEqualTo("rg-body");
        assertThat(input.headers().get("x-oss-bucket-tagging")).isEqualTo("type=test");
    }
}
