package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketVersioningRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutBucketVersioningRequest request = PutBucketVersioningRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.versioningConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        VersioningConfiguration versioningConfiguration = VersioningConfiguration.newBuilder()
                .status("Enabled")
                .build();

        PutBucketVersioningRequest request = PutBucketVersioningRequest.newBuilder()
                .bucket("examplebucket")
                .versioningConfiguration(versioningConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.versioningConfiguration()).isEqualTo(versioningConfiguration);

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

        VersioningConfiguration versioningConfiguration = VersioningConfiguration.newBuilder()
                .status("Suspended")
                .build();

        PutBucketVersioningRequest original = PutBucketVersioningRequest.newBuilder()
                .bucket("examplebucket")
                .versioningConfiguration(versioningConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        PutBucketVersioningRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.versioningConfiguration()).isEqualTo(versioningConfiguration);

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testHeaderProperties() {
        VersioningConfiguration versioningConfiguration = VersioningConfiguration.newBuilder()
                .status("Enabled")
                .build();

        PutBucketVersioningRequest request = PutBucketVersioningRequest.newBuilder()
                .bucket("examplebucket")
                .versioningConfiguration(versioningConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.versioningConfiguration().status()).isEqualTo("Enabled");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        VersioningConfiguration versioningConfiguration = VersioningConfiguration.newBuilder()
                .status("Enabled")
                .build();

        String xml = xmlMapper.writeValueAsString(versioningConfiguration);

        VersioningConfiguration value = xmlMapper.readValue(xml, VersioningConfiguration.class);

        assertThat(value.status()).isEqualTo("Enabled");
    }
}
