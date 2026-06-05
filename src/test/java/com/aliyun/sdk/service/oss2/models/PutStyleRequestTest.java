package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketStyle;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class PutStyleRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutStyleRequest request = PutStyleRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Style style = Style.newBuilder()
                .content("image/resize,p_50")
                .build();

        PutStyleRequest request = PutStyleRequest.newBuilder()
                .bucket("examplebucket")
                .style(style)
                .styleName("example-style")
                .category("image")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.style()).isEqualTo(style);
        assertThat(request.styleName()).isEqualTo("example-style");
        assertThat(request.category()).isEqualTo("image");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("styleName", "example-style")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        PutStyleRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.style()).isEqualTo(style);
        assertThat(copy.styleName()).isEqualTo("example-style");
        assertThat(copy.category()).isEqualTo("image");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("styleName", "example-style")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Style style = Style.newBuilder()
                .content("image/resize,p_50")
                .build();

        PutStyleRequest original = PutStyleRequest.newBuilder()
                .bucket("test-bucket")
                .style(style)
                .styleName("test-style")
                .build();

        PutStyleRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.style()).isEqualTo(style);
        assertThat(copy.styleName()).isEqualTo("test-style");
    }

    @Test
    public void testHeaderProperties() {
        Style style = Style.newBuilder()
                .content("image/resize,p_50")
                .build();

        PutStyleRequest request = PutStyleRequest.newBuilder()
                .bucket("style-bucket")
                .style(style)
                .styleName("test-style")
                .category("image")
                .build();

        assertThat(request.bucket()).isEqualTo("style-bucket");
        assertThat(request.style().content()).isEqualTo("image/resize,p_50");
        assertThat(request.styleName()).isEqualTo("test-style");
        assertThat(request.category()).isEqualTo("image");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<Style>\n" +
                "  <Content>image/resize,p_50</Content>\n" +
                "</Style>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Style xmlStyle = xmlMapper.readValue(xml, Style.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlStyle);

        Style style = Style.newBuilder()
                .content("image/resize,p_50")
                .build();

        PutStyleRequest request = PutStyleRequest.newBuilder()
                .bucket("xml-bucket")
                .style(style)
                .styleName("xml-style")
                .build();

        OperationInput input = SerdeBucketStyle.fromPutStyle(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("style")).isEqualTo("");
        assertThat(input.parameters().get("styleName")).isEqualTo("xml-style");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<Style>");
        assertThat(xmlContent).contains("<Content>image/resize,p_50</Content>");
        assertThat(xmlContent).contains("</Style>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}