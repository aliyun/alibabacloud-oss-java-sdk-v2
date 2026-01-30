package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketMetaquery;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class OpenMetaQueryRequestTest {

    @Test
    public void testEmptyBuilder() {
        OpenMetaQueryRequest request = OpenMetaQueryRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        MetaQueryFilters filter = MetaQueryFilters.newBuilder()
                .filter(Arrays.asList(
                    "Size > 1024, FileModifiedTime > 2025-06-03T09:20:47.999Z",
                    "Filename prefix (YWEvYmIv)"
                ))
                .build();
        MetaQueryOpenRequest metaQueryOpenRequest = MetaQueryOpenRequest.newBuilder()
                .filters(filter)
                .build();

        OpenMetaQueryRequest request = OpenMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .metaQueryOpenRequest(metaQueryOpenRequest)
                .mode("basic")
                .role("role-name")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.metaQueryOpenRequest()).isEqualTo(metaQueryOpenRequest);
        assertThat(request.mode()).isEqualTo("basic");
        assertThat(request.role()).isEqualTo("role-name");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("mode", "basic")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        OpenMetaQueryRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.metaQueryOpenRequest()).isEqualTo(metaQueryOpenRequest);
        assertThat(copy.mode()).isEqualTo("basic");
        assertThat(copy.role()).isEqualTo("role-name");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("mode", "basic")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        MetaQueryFilters filter = MetaQueryFilters.newBuilder()
                .filter(Arrays.asList(
                    "Size > 1024, FileModifiedTime > 2025-06-03T09:20:47.999Z"
                ))
                .build();
        MetaQueryOpenRequest metaQueryOpenRequest = MetaQueryOpenRequest.newBuilder()
                .filters(filter)
                .build();

        OpenMetaQueryRequest original = OpenMetaQueryRequest.newBuilder()
                .bucket("test-bucket")
                .metaQueryOpenRequest(metaQueryOpenRequest)
                .mode("basic")
                .build();

        OpenMetaQueryRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.metaQueryOpenRequest()).isEqualTo(metaQueryOpenRequest);
        assertThat(copy.mode()).isEqualTo("basic");
    }

    @Test
    public void testHeaderProperties() {
        MetaQueryFilters filter = MetaQueryFilters.newBuilder()
                .filter(Arrays.asList(
                    "Size > 1024, FileModifiedTime > 2025-06-03T09:20:47.999Z",
                    "Filename prefix (YWEvYmIv)"
                ))
                .build();
        MetaQueryOpenRequest metaQueryOpenRequest = MetaQueryOpenRequest.newBuilder()
                .filters(filter)
                .build();

        OpenMetaQueryRequest request = OpenMetaQueryRequest.newBuilder()
                .bucket("metaquery-bucket")
                .metaQueryOpenRequest(metaQueryOpenRequest)
                .mode("basic")
                .role("test-role")
                .build();

        assertThat(request.bucket()).isEqualTo("metaquery-bucket");
        assertThat(request.metaQueryOpenRequest().filters().filter()).hasSize(2);
        assertThat(request.mode()).isEqualTo("basic");
        assertThat(request.role()).isEqualTo("test-role");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<MetaQuery>\n" +
                "  <Filters>\n" +
                "    <Filter>Size > 1024, FileModifiedTime > 2025-06-03T09:20:47.999Z</Filter>\n" +
                "    <Filter>Filename prefix (YWEvYmIv)</Filter>\n" +
                "  </Filters>\n" +
                "</MetaQuery>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MetaQueryOpenRequest xmlConfiguration = xmlMapper.readValue(xml, MetaQueryOpenRequest.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        MetaQueryFilters filter = MetaQueryFilters.newBuilder()
                .filter(Arrays.asList(
                    "Size > 1024, FileModifiedTime > 2025-06-03T09:20:47.999Z",
                    "Filename prefix (YWEvYmIv)"
                ))
                .build();
        MetaQueryOpenRequest metaQueryOpenRequest = MetaQueryOpenRequest.newBuilder()
                .filters(filter)
                .build();

        OpenMetaQueryRequest request = OpenMetaQueryRequest.newBuilder()
                .bucket("xml-bucket")
                .metaQueryOpenRequest(metaQueryOpenRequest)
                .mode("basic")
                .build();

        OperationInput input = SerdeBucketMetaquery.fromOpenMetaQuery(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("comp")).isEqualTo("add");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<MetaQuery>");
        assertThat(xmlContent).contains("<Filters>");
        assertThat(xmlContent).contains("<Filter>Size > 1024, FileModifiedTime > 2025-06-03T09:20:47.999Z</Filter>");
        assertThat(xmlContent).contains("<Filter>Filename prefix (YWEvYmIv)</Filter>");
        assertThat(xmlContent).contains("</MetaQuery>");

        // Compare with expected XML
        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}