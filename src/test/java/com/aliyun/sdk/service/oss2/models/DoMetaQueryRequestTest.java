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
import static org.assertj.core.api.Assertions.assertThat;

public class DoMetaQueryRequestTest {

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
        assertThat(request.metaQuery()).isNull();
    }

    @Test
    public void testFullBuilder() {
        // Create a MetaQuery for basic mode
        String queryCondition = "{\"Field\": \"Size\",\"Value\": \"1048576\",\"Operation\": \"gt\"}";
        MetaQueryAggregation agg1 = MetaQueryAggregation.newBuilder().field("Size").operation("sum").build();
        MetaQueryAggregation agg2 = MetaQueryAggregation.newBuilder().field("Size").operation("max").build();
        
        MetaQueryAggregations aggregationsContainer = MetaQueryAggregations.newBuilder()
                .aggregation(java.util.Arrays.asList(agg1, agg2))
                .build();
        
        MetaQuery metaQuery = MetaQuery.newBuilder()
                .nextToken("test-token")
                .maxResults(5)
                .query(queryCondition)
                .sort("Size")
                .order(MetaQuery.SortOrder.asc)
                .aggregations(aggregationsContainer)
                .build();

        DoMetaQueryRequest request = DoMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .mode("basic")
                .metaQuery(metaQuery)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.mode()).isEqualTo("basic");
        assertThat(request.metaQuery()).isEqualTo(metaQuery);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        DoMetaQueryRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.mode()).isEqualTo("basic");
        assertThat(copy.metaQuery()).isEqualTo(metaQuery);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testFullBuilderSemantic() {
        // Create a MetaQuery for semantic mode
        String simpleQuery = "{\"Operation\":\"gt\", \"Field\": \"Size\", \"Value\": \"30\"}";
        
        MetaQuery metaQuery = MetaQuery.newBuilder()
                .maxResults(99)
                .query("俯瞰白雪覆盖的森林")
                .mediaTypes(java.util.Arrays.asList("image"))
                .simpleQuery(simpleQuery)
                .build();

        DoMetaQueryRequest request = DoMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .mode("semantic")
                .metaQuery(metaQuery)
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.mode()).isEqualTo("semantic");
        assertThat(request.metaQuery()).isEqualTo(metaQuery);
    }

    @Test
    public void testToBuilderPreserveState() {
        String queryCondition = "{\"Field\": \"Size\",\"Value\": \"1048576\",\"Operation\": \"gt\"}";
        
        MetaQuery metaQuery = MetaQuery.newBuilder()
                .nextToken("test-token")
                .maxResults(5)
                .query(queryCondition)
                .build();

        DoMetaQueryRequest original = DoMetaQueryRequest.newBuilder()
                .bucket("test-bucket")
                .mode("basic")
                .metaQuery(metaQuery)
                .build();

        DoMetaQueryRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.mode()).isEqualTo("basic");
        assertThat(copy.metaQuery()).isEqualTo(metaQuery);
    }

    @Test
    public void testHeaderProperties() {
        String queryCondition = "{\"Field\": \"Size\",\"Value\": \"1048576\",\"Operation\": \"gt\"}";
        
        MetaQuery metaQuery = MetaQuery.newBuilder()
                .nextToken("test-token")
                .maxResults(5)
                .query(queryCondition)
                .build();

        DoMetaQueryRequest request = DoMetaQueryRequest.newBuilder()
                .bucket("test-bucket")
                .mode("basic")
                .metaQuery(metaQuery)
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.mode()).isEqualTo("basic");
        assertThat(request.metaQuery()).isEqualTo(metaQuery);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<MetaQuery>\n" +
                "  <NextToken>test-token</NextToken>\n" +
                "  <MaxResults>5</MaxResults>\n" +
                "  <Query>{\"Field\": \"Size\",\"Value\": \"1048576\",\"Operation\": \"gt\"}</Query>\n" +
                "  <Sort>Size</Sort>\n" +
                "  <Order>asc</Order>\n" +
                "  <Aggregations>\n" +
                "    <Aggregation>\n" +
                "      <Field>Size</Field>\n" +
                "      <Operation>sum</Operation>\n" +
                "    </Aggregation>\n" +
                "    <Aggregation>\n" +
                "      <Field>Size</Field>\n" +
                "      <Operation>max</Operation>\n" +
                "    </Aggregation>\n" +
                "  </Aggregations>\n" +
                "</MetaQuery>";
                
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MetaQuery xmlConfiguration = xmlMapper.readValue(xml, MetaQuery.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        String queryCondition = "{\"Field\": \"Size\",\"Value\": \"1048576\",\"Operation\": \"gt\"}";
        MetaQueryAggregation agg1 = MetaQueryAggregation.newBuilder().field("Size").operation("sum").build();
        MetaQueryAggregation agg2 = MetaQueryAggregation.newBuilder().field("Size").operation("max").build();
        
        MetaQueryAggregations aggregationsContainer = MetaQueryAggregations.newBuilder()
                .aggregation(java.util.Arrays.asList(agg1, agg2))
                .build();
        
        MetaQuery metaQuery = MetaQuery.newBuilder()
                .nextToken("test-token")
                .maxResults(5)
                .query(queryCondition)
                .sort("Size")
                .order(MetaQuery.SortOrder.asc)
                .aggregations(aggregationsContainer)
                .build();

        DoMetaQueryRequest request = DoMetaQueryRequest.newBuilder()
                .bucket("xml-bucket")
                .mode("basic")
                .metaQuery(metaQuery)
                .build();

        OperationInput input = SerdeBucketMetaquery.fromDoMetaQuery(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("comp")).isEqualTo("query");
        assertThat(input.parameters().get("mode")).isEqualTo("basic");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<MetaQuery>");
        assertThat(xmlContent).contains("<NextToken>test-token</NextToken>");
        assertThat(xmlContent).contains("<MaxResults>5</MaxResults>");
        assertThat(xmlContent).contains("<Query>{\"Field\": \"Size\",\"Value\": \"1048576\",\"Operation\": \"gt\"}</Query>");
        assertThat(xmlContent).contains("<Sort>Size</Sort>");
        assertThat(xmlContent).contains("<Order>asc</Order>");

        // Compare with expected XML
        assertThat(xmlContent).isEqualTo(expectedXml);
    }
    
    @Test
    public void xmlBuilderSemantic() throws JsonProcessingException {
        String xml = "" +
                "<MetaQuery>\n" +
                "  <MaxResults>99</MaxResults>\n" +
                "  <Query>俯瞰白雪覆盖的森林</Query>\n" +
                "  <MediaTypes>\n" +
                "    <MediaType>image</MediaType>\n" +
                "  </MediaTypes>\n" +
                "  <SimpleQuery>{\"Operation\":\"gt\", \"Field\": \"Size\", \"Value\": \"30\"}</SimpleQuery>\n" +
                "</MetaQuery>";
                
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MetaQuery xmlConfiguration = xmlMapper.readValue(xml, MetaQuery.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        String simpleQuery = "{\"Operation\":\"gt\", \"Field\": \"Size\", \"Value\": \"30\"}";
        
        MetaQuery metaQuery = MetaQuery.newBuilder()
                .maxResults(99)
                .query("俯瞰白雪覆盖的森林")
                .mediaTypes(java.util.Arrays.asList("image"))
                .simpleQuery(simpleQuery)
                .build();

        DoMetaQueryRequest request = DoMetaQueryRequest.newBuilder()
                .bucket("xml-bucket")
                .mode("semantic")
                .metaQuery(metaQuery)
                .build();

        OperationInput input = SerdeBucketMetaquery.fromDoMetaQuery(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("comp")).isEqualTo("query");
        assertThat(input.parameters().get("mode")).isEqualTo("semantic");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<MetaQuery>");
        assertThat(xmlContent).contains("<MaxResults>99</MaxResults>");
        assertThat(xmlContent).contains("<Query>俯瞰白雪覆盖的森林</Query>");
        assertThat(xmlContent).contains("<MediaType>image</MediaType>");
        assertThat(xmlContent).contains("<SimpleQuery>{\"Operation\":\"gt\", \"Field\": \"Size\", \"Value\": \"30\"}</SimpleQuery>");

        // Compare with expected XML
        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}