package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketWorm;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class InitiateBucketWormRequestTest {
    @Test
    public void testEmptyBuilder() {
        InitiateBucketWormRequest request = InitiateBucketWormRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.initiateWormConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        InitiateWormConfiguration initiateWormConfiguration = InitiateWormConfiguration.newBuilder()
                .retentionPeriodInDays(365)
                .build();

        InitiateBucketWormRequest request = InitiateBucketWormRequest.newBuilder()
                .bucket("examplebucket")
                .initiateWormConfiguration(initiateWormConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.initiateWormConfiguration()).isEqualTo(initiateWormConfiguration);

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

        InitiateWormConfiguration initiateWormConfiguration = InitiateWormConfiguration.newBuilder()
                .retentionPeriodInDays(180)
                .build();

        InitiateBucketWormRequest original = InitiateBucketWormRequest.newBuilder()
                .bucket("testbucket")
                .initiateWormConfiguration(initiateWormConfiguration)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        InitiateBucketWormRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.initiateWormConfiguration()).isEqualTo(initiateWormConfiguration);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        InitiateWormConfiguration initiateWormConfiguration = InitiateWormConfiguration.newBuilder()
                .retentionPeriodInDays(365)
                .build();

        InitiateBucketWormRequest request = InitiateBucketWormRequest.newBuilder()
                .bucket("anotherbucket")
                .initiateWormConfiguration(initiateWormConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.initiateWormConfiguration()).isEqualTo(initiateWormConfiguration);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<InitiateWormConfiguration>\n" +
                "  <RetentionPeriodInDays>365</RetentionPeriodInDays>\n" +
                "</InitiateWormConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        InitiateWormConfiguration xmlConfiguration = xmlMapper.readValue(xml, InitiateWormConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        InitiateWormConfiguration initiateWormConfiguration = InitiateWormConfiguration.newBuilder()
                .retentionPeriodInDays(365)
                .build();

        InitiateBucketWormRequest request = InitiateBucketWormRequest.newBuilder()
                .bucket("examplebucket")
                .initiateWormConfiguration(initiateWormConfiguration)
                .build();

        OperationInput input = SerdeBucketWorm.fromInitiateBucketWorm(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("worm")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<InitiateWormConfiguration>");
        assertThat(xmlContent).contains("<RetentionPeriodInDays>365</RetentionPeriodInDays>");
        assertThat(xmlContent).contains("</InitiateWormConfiguration>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }

}