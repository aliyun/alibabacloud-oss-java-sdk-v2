package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketLocationResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketLocationResult result = GetBucketLocationResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.locationConstraint()).isNull();
    }

    @Test
    public void testFullBuilder() {
        String location = "oss-cn-hangzhou";

        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetBucketLocationResult result = GetBucketLocationResult.newBuilder()
                .headers(headers)
                .innerBody(location)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.locationConstraint()).isEqualTo(location);
    }

    @Test
    public void testToBuilderPreserveState() {
        String location = "oss-cn-hangzhou";

        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetBucketLocationResult original = GetBucketLocationResult.newBuilder()
                .headers(headers)
                .innerBody(location)
                .build();

        GetBucketLocationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.locationConstraint()).isEqualTo(location);
    }

    @Test
    public void testXmlBuilder() {
        String xml = "<LocationConstraint>oss-cn-hangzhou</LocationConstraint>";

        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            String innerBody = xmlMapper.readValue(xml, String.class);
            GetBucketLocationResult result = GetBucketLocationResult.newBuilder()
                    .innerBody(innerBody)
                    .build();

            assertThat(result.locationConstraint()).isEqualTo("oss-cn-hangzhou");
        } catch (Exception e) {
            // Should not reach here
            throw new RuntimeException(e);
        }
    }
}
