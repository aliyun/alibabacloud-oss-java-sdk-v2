package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketMetadataConfiguration;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketMetadataConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketMetadataConfigurationRequest request = GetBucketMetadataConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"");
        GetBucketMetadataConfigurationRequest request = GetBucketMetadataConfigurationRequest.newBuilder()
                .bucket("examplebucket")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2"));
    }

    @Test
    public void testToBuilderPreserveState() {
        GetBucketMetadataConfigurationRequest original = GetBucketMetadataConfigurationRequest.newBuilder()
                .bucket("testbucket")
                .header("x-header-value", "value")
                .parameter("param", "value")
                .build();
        GetBucketMetadataConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.headers()).containsEntry("x-header-value", "value");
        assertThat(copy.parameters()).containsEntry("param", "value");
    }

    @Test
    public void testHeaderProperties() {
        GetBucketMetadataConfigurationRequest request = GetBucketMetadataConfigurationRequest.newBuilder()
                .bucket("metadata-bucket")
                .build();
        assertThat(request.bucket()).isEqualTo("metadata-bucket");
    }

    @Test
    public void xmlBuilder() {
        GetBucketMetadataConfigurationRequest request = GetBucketMetadataConfigurationRequest.newBuilder()
                .bucket("examplebucket")
                .build();
        OperationInput input = SerdeBucketMetadataConfiguration.fromGetBucketMetadataConfiguration(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("metadataConfiguration")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.body().isPresent()).isFalse();
    }
}
