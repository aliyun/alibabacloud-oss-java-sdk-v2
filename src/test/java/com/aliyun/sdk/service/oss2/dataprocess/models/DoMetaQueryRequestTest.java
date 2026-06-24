package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class DoMetaQueryRequestTest {

    // ==================== basic mode ====================

    @Test
    public void testEmptyBuilder() {
        DoMetaQueryRequest request = DoMetaQueryRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.mode()).isNull();
        assertThat(request.metaQueryBody()).isNull();
    }

    @Test
    public void testFullBuilderBasicMode() {
        Aggregation agg = Aggregation.newBuilder()
                .field("Size").operation("sum").build();

        MetaQueryDoBody body = MetaQueryDoBody.newBuilder()
                .query("{\"Field\":\"Size\",\"Operation\":\"gt\",\"Value\":\"1048576\"}")
                .sort("Size")
                .order("desc")
                .aggregations(Collections.singletonList(agg))
                .maxResults(100)
                .nextToken("token-basic-001")
                .withFields(Arrays.asList("Filename", "Size", "FileModifiedTime"))
                .withoutTotalHits("True")
                .build();

        DoMetaQueryRequest request = DoMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .mode("basic")
                .metaQueryBody(body)
                .header("x-custom-header", "value1")
                .parameter("extra-param", "extra-value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.mode()).isEqualTo("basic");
        assertThat(request.metaQueryBody()).isSameAs(body);
        assertThat(request.metaQueryBody().query()).isEqualTo("{\"Field\":\"Size\",\"Operation\":\"gt\",\"Value\":\"1048576\"}");
        assertThat(request.metaQueryBody().sort()).isEqualTo("Size");
        assertThat(request.metaQueryBody().order()).isEqualTo("desc");
        assertThat(request.metaQueryBody().maxResults()).isEqualTo(100);
        assertThat(request.metaQueryBody().nextToken()).isEqualTo("token-basic-001");
        assertThat(request.metaQueryBody().withoutTotalHits()).isEqualTo("True");
        assertThat(request.metaQueryBody().aggregations()).hasSize(1);
        assertThat(request.metaQueryBody().aggregations().get(0).field()).isEqualTo("Size");
        assertThat(request.metaQueryBody().aggregations().get(0).operation()).isEqualTo("sum");
        assertThat(request.metaQueryBody().withFields()).containsExactly("Filename", "Size", "FileModifiedTime");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("extra-param", "extra-value"));

        // to builder
        DoMetaQueryRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.mode()).isEqualTo("basic");
        assertThat(copy.metaQueryBody()).isSameAs(body);
    }

    @Test
    public void testBasicModeXmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<MetaQuery>\n" +
                "  <Query>{\"Field\":\"Size\",\"Operation\":\"gt\",\"Value\":\"1048576\"}</Query>\n" +
                "  <Sort>Size</Sort>\n" +
                "  <Order>desc</Order>\n" +
                "  <Aggregations>\n" +
                "    <Aggregation>\n" +
                "      <Field>Size</Field>\n" +
                "      <Operation>sum</Operation>\n" +
                "    </Aggregation>\n" +
                "  </Aggregations>\n" +
                "  <MaxResults>100</MaxResults>\n" +
                "</MetaQuery>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MetaQueryDoBody xmlBody = xmlMapper.readValue(xml, MetaQueryDoBody.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlBody);

        Aggregation agg = Aggregation.newBuilder()
                .field("Size").operation("sum").build();

        MetaQueryDoBody body = MetaQueryDoBody.newBuilder()
                .query("{\"Field\":\"Size\",\"Operation\":\"gt\",\"Value\":\"1048576\"}")
                .sort("Size")
                .order("desc")
                .aggregations(Collections.singletonList(agg))
                .maxResults(100)
                .build();

        DoMetaQueryRequest request = DoMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .mode("basic")
                .metaQueryBody(body)
                .build();

        OperationInput input = SerdeDatasetBasic.fromDoMetaQuery(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("doMetaQuery");
        assertThat(input.parameters().get("mode")).isEqualTo("basic");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData bodyData = input.body().get();
        String xmlContent = new String(bodyData.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<MetaQuery>");
        assertThat(xmlContent).contains("<Query>");
        assertThat(xmlContent).contains("<Sort>Size</Sort>");
        assertThat(xmlContent).contains("<Order>desc</Order>");
        assertThat(xmlContent).contains("<MaxResults>100</MaxResults>");
        assertThat(xmlContent).contains("<Aggregation>");
        assertThat(xmlContent).contains("<Field>Size</Field>");
        assertThat(xmlContent).contains("<Operation>sum</Operation>");
        assertThat(xmlContent).contains("</MetaQuery>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }

    // ==================== semantic mode ====================

    @Test
    public void testFullBuilderSemanticMode() {
        MetaQueryDoBody body = MetaQueryDoBody.newBuilder()
                .query("客厅里的猫")
                .mediaTypes(Collections.singletonList("image"))
                .simpleQuery("{\"Field\":\"Size\",\"Operation\":\"gt\",\"Value\":\"102400\"}")
                .maxResults(20)
                .withFields(Arrays.asList("Filename", "Size", "Insights", "Labels"))
                .smartClusterIds(Arrays.asList("cluster-abc123def456", "cluster-xyz789"))
                .build();

        DoMetaQueryRequest request = DoMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .mode("semantic")
                .metaQueryBody(body)
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.mode()).isEqualTo("semantic");
        assertThat(request.metaQueryBody()).isSameAs(body);
        assertThat(request.metaQueryBody().query()).isEqualTo("客厅里的猫");
        assertThat(request.metaQueryBody().mediaTypes()).containsExactly("image");
        assertThat(request.metaQueryBody().simpleQuery()).isEqualTo("{\"Field\":\"Size\",\"Operation\":\"gt\",\"Value\":\"102400\"}");
        assertThat(request.metaQueryBody().maxResults()).isEqualTo(20);
        assertThat(request.metaQueryBody().smartClusterIds()).containsExactly("cluster-abc123def456", "cluster-xyz789");
        assertThat(request.metaQueryBody().sort()).isNull();
        assertThat(request.metaQueryBody().order()).isNull();
        assertThat(request.metaQueryBody().aggregations()).isNull();
    }

    @Test
    public void testSemanticModeWithSourceURI() {
        MetaQueryDoBody body = MetaQueryDoBody.newBuilder()
                .sourceURI("oss://examplebucket/photos/cat.jpg")
                .mediaTypes(Arrays.asList("image", "video"))
                .maxResults(10)
                .build();

        DoMetaQueryRequest request = DoMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .mode("semantic")
                .metaQueryBody(body)
                .build();

        assertThat(request.mode()).isEqualTo("semantic");
        assertThat(request.metaQueryBody().sourceURI()).isEqualTo("oss://examplebucket/photos/cat.jpg");
        assertThat(request.metaQueryBody().mediaTypes()).containsExactly("image", "video");
        assertThat(request.metaQueryBody().query()).isNull();
    }

    @Test
    public void testSemanticModeXmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<MetaQuery>\n" +
                "  <Query>客厅里的猫</Query>\n" +
                "  <MediaTypes>\n" +
                "    <MediaType>image</MediaType>\n" +
                "  </MediaTypes>\n" +
                "  <SimpleQuery>{\"Field\":\"Size\",\"Operation\":\"gt\",\"Value\":\"102400\"}</SimpleQuery>\n" +
                "  <MaxResults>20</MaxResults>\n" +
                "</MetaQuery>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MetaQueryDoBody xmlBody = xmlMapper.readValue(xml, MetaQueryDoBody.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlBody);

        MetaQueryDoBody body = MetaQueryDoBody.newBuilder()
                .query("客厅里的猫")
                .mediaTypes(Collections.singletonList("image"))
                .simpleQuery("{\"Field\":\"Size\",\"Operation\":\"gt\",\"Value\":\"102400\"}")
                .maxResults(20)
                .build();

        DoMetaQueryRequest request = DoMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .mode("semantic")
                .metaQueryBody(body)
                .build();

        OperationInput input = SerdeDatasetBasic.fromDoMetaQuery(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("doMetaQuery");
        assertThat(input.parameters().get("mode")).isEqualTo("semantic");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData bodyData = input.body().get();
        String xmlContent = new String(bodyData.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<MetaQuery>");
        assertThat(xmlContent).contains("<Query>客厅里的猫</Query>");
        assertThat(xmlContent).contains("<MediaType>image</MediaType>");
        assertThat(xmlContent).contains("<SimpleQuery>");
        assertThat(xmlContent).contains("<MaxResults>20</MaxResults>");
        assertThat(xmlContent).contains("</MetaQuery>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }

    // ==================== toBuilder preserve ====================

    @Test
    public void testToBuilderPreserveState() {
        MetaQueryDoBody body = MetaQueryDoBody.newBuilder()
                .query("{\"Field\":\"Filename\",\"Operation\":\"eq\",\"Value\":\"test.jpg\"}")
                .sort("FileModifiedTime")
                .order("asc")
                .maxResults(50)
                .build();

        DoMetaQueryRequest original = DoMetaQueryRequest.newBuilder()
                .bucket("test-bucket")
                .mode("basic")
                .metaQueryBody(body)
                .build();

        DoMetaQueryRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.mode()).isEqualTo("basic");
        assertThat(copy.metaQueryBody()).isSameAs(body);
        assertThat(copy.metaQueryBody().query()).isEqualTo("{\"Field\":\"Filename\",\"Operation\":\"eq\",\"Value\":\"test.jpg\"}");
        assertThat(copy.metaQueryBody().sort()).isEqualTo("FileModifiedTime");
        assertThat(copy.metaQueryBody().order()).isEqualTo("asc");
        assertThat(copy.metaQueryBody().maxResults()).isEqualTo(50);
    }
}
