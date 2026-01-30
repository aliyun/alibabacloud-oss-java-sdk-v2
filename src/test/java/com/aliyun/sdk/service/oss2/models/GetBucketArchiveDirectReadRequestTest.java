package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketArchiveDirectReadRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketArchiveDirectReadRequest request = GetBucketArchiveDirectReadRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        GetBucketArchiveDirectReadRequest request = GetBucketArchiveDirectReadRequest.newBuilder()
                .bucket("examplebucket")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        GetBucketArchiveDirectReadRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        GetBucketArchiveDirectReadRequest original = GetBucketArchiveDirectReadRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        GetBucketArchiveDirectReadRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
    }

    @Test
    public void testHeaderProperties() {
        GetBucketArchiveDirectReadRequest request = GetBucketArchiveDirectReadRequest.newBuilder()
                .bucket("direct-read-bucket")
                .build();

        assertThat(request.bucket()).isEqualTo("direct-read-bucket");
    }

    @Test
    public void serdeBuilder() throws JsonProcessingException {
        GetBucketArchiveDirectReadRequest request = GetBucketArchiveDirectReadRequest.newBuilder()
                .bucket("xml-bucket")
                .build();

        OperationInput input = com.aliyun.sdk.service.oss2.transform.SerdeBucketArchiveDirectRead.fromGetBucketArchiveDirectRead(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("bucketArchiveDirectRead")).isEqualTo("");
        assertThat(input.method()).isEqualTo("GET");
    }
}