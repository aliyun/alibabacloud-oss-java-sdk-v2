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

public class ExtendBucketWormRequestTest {
    @Test
    public void testEmptyBuilder() {
        ExtendBucketWormRequest request = ExtendBucketWormRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.wormId()).isNull();
        assertThat(request.extendWormConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ExtendWormConfiguration extendWormConfiguration = ExtendWormConfiguration.newBuilder()
                .retentionPeriodInDays(366)
                .build();

        ExtendBucketWormRequest request = ExtendBucketWormRequest.newBuilder()
                .bucket("examplebucket")
                .wormId("1666E2CFB2B3418****")
                .extendWormConfiguration(extendWormConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.wormId()).isEqualTo("1666E2CFB2B3418****");
        assertThat(request.extendWormConfiguration()).isEqualTo(extendWormConfiguration);

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

        ExtendWormConfiguration extendWormConfiguration = ExtendWormConfiguration.newBuilder()
                .retentionPeriodInDays(366)
                .build();

        ExtendBucketWormRequest original = ExtendBucketWormRequest.newBuilder()
                .bucket("testbucket")
                .wormId("1666E2CFB2B3418****")
                .extendWormConfiguration(extendWormConfiguration)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        ExtendBucketWormRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.wormId()).isEqualTo("1666E2CFB2B3418****");
        assertThat(copy.extendWormConfiguration()).isEqualTo(extendWormConfiguration);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        ExtendWormConfiguration extendWormConfiguration = ExtendWormConfiguration.newBuilder()
                .retentionPeriodInDays(366)
                .build();

        ExtendBucketWormRequest request = ExtendBucketWormRequest.newBuilder()
                .bucket("anotherbucket")
                .wormId("1666E2CFB2B3418****")
                .extendWormConfiguration(extendWormConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.wormId()).isEqualTo("1666E2CFB2B3418****");
        assertThat(request.extendWormConfiguration()).isEqualTo(extendWormConfiguration);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<ExtendWormConfiguration>\n" +
                "  <RetentionPeriodInDays>366</RetentionPeriodInDays>\n" +
                "</ExtendWormConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ExtendWormConfiguration xmlConfiguration = xmlMapper.readValue(xml, ExtendWormConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        ExtendWormConfiguration extendWormConfiguration = ExtendWormConfiguration.newBuilder()
                .retentionPeriodInDays(366)
                .build();

        ExtendBucketWormRequest request = ExtendBucketWormRequest.newBuilder()
                .bucket("examplebucket")
                .wormId("1666E2CFB2B3418****")
                .extendWormConfiguration(extendWormConfiguration)
                .build();

        OperationInput input = SerdeBucketWorm.fromExtendBucketWorm(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("wormId")).isEqualTo("1666E2CFB2B3418****");
        assertThat(input.parameters().get("wormExtend")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<ExtendWormConfiguration>");
        assertThat(xmlContent).contains("<RetentionPeriodInDays>366</RetentionPeriodInDays>");
        assertThat(xmlContent).contains("</ExtendWormConfiguration>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }

}