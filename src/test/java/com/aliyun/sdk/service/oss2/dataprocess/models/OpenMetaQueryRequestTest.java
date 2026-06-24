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
        assertThat(request.mode()).isNull();
        assertThat(request.role()).isNull();
        assertThat(request.metaQueryBody()).isNull();
    }

    @Test
    public void testFullBuilderBasicMode() {
        WorkflowParameters workflowParams = WorkflowParameters.newBuilder()
                .workflowParameters(Arrays.asList(
                        WorkflowParameter.newBuilder().name("ImageInsightEnable").value("True").build(),
                        WorkflowParameter.newBuilder().name("VideoInsightEnable").value("True").build()))
                .build();

        MetaQueryOpenBody body = MetaQueryOpenBody.newBuilder()
                .workflowParameters(workflowParams)
                .filters(Collections.singletonList("Size > 0"))
                .build();

        OpenMetaQueryRequest request = OpenMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .mode("basic")
                .role("acs:ram::1234567890:role/AliyunMetaQueryDefaultRole")
                .metaQueryBody(body)
                .header("x-custom-header", "value1")
                .parameter("extra-param", "extra-value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.mode()).isEqualTo("basic");
        assertThat(request.role()).isEqualTo("acs:ram::1234567890:role/AliyunMetaQueryDefaultRole");
        assertThat(request.metaQueryBody()).isSameAs(body);
        assertThat(request.metaQueryBody().filters()).containsExactly("Size > 0");
        assertThat(request.metaQueryBody().workflowParameters().workflowParameters()).hasSize(2);
        assertThat(request.metaQueryBody().workflowParameters().workflowParameters().get(0).name()).isEqualTo("ImageInsightEnable");
        assertThat(request.metaQueryBody().workflowParameters().workflowParameters().get(1).name()).isEqualTo("VideoInsightEnable");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("extra-param", "extra-value"));

        // to builder
        OpenMetaQueryRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.mode()).isEqualTo("basic");
        assertThat(copy.role()).isEqualTo("acs:ram::1234567890:role/AliyunMetaQueryDefaultRole");
        assertThat(copy.metaQueryBody()).isSameAs(body);
    }

    @Test
    public void testFullBuilderSemanticMode() {
        // semantic mode supports RouteRule, IndexOptions
        RouteRule routeRule = RouteRule.newBuilder()
                .type("OSSTag")
                .autoCreateDataset("True")
                .ossTagKey("routing-dataset")
                .build();

        IndexOptions indexOptions = IndexOptions.newBuilder()
                .ignoreObjectDelete("True")
                .build();

        MetaQueryNotification notification = MetaQueryNotification.newBuilder()
                .mns("imm-index-notification")
                .build();
        MetaQueryNotifications notifications = MetaQueryNotifications.newBuilder()
                .notifications(Collections.singletonList(notification))
                .build();
        NotificationAttributes notificationAttributes = NotificationAttributes.newBuilder()
                .notifications(notifications)
                .withFields(Arrays.asList("Insights", "Labels"))
                .build();

        InsightsConfig insightsConfig = InsightsConfig.newBuilder()
                .language("en")
                .build();
        DatasetConfig datasetConfig = DatasetConfig.newBuilder()
                .insights(insightsConfig)
                .build();

        WorkflowParameters workflowParams = WorkflowParameters.newBuilder()
                .workflowParameters(Collections.singletonList(
                        WorkflowParameter.newBuilder().name("ImageInsightEnable").value("True").build()))
                .build();

        MetaQueryOpenBody body = MetaQueryOpenBody.newBuilder()
                .workflowParameters(workflowParams)
                .notificationAttributes(notificationAttributes)
                .indexOptions(indexOptions)
                .routeRule(routeRule)
                .datasetConfig(datasetConfig)
                .build();

        OpenMetaQueryRequest request = OpenMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .mode("semantic")
                .role("AliyunMetaQueryDefaultRole")
                .metaQueryBody(body)
                .build();

        assertThat(request.mode()).isEqualTo("semantic");
        assertThat(request.metaQueryBody().routeRule()).isSameAs(routeRule);
        assertThat(request.metaQueryBody().routeRule().type()).isEqualTo("OSSTag");
        assertThat(request.metaQueryBody().routeRule().autoCreateDataset()).isEqualTo("True");
        assertThat(request.metaQueryBody().routeRule().ossTagKey()).isEqualTo("routing-dataset");
        assertThat(request.metaQueryBody().indexOptions()).isSameAs(indexOptions);
        assertThat(request.metaQueryBody().indexOptions().ignoreObjectDelete()).isEqualTo("True");
        assertThat(request.metaQueryBody().notificationAttributes().notifications().notifications()).hasSize(1);
        assertThat(request.metaQueryBody().notificationAttributes().notifications().notifications().get(0).mns()).isEqualTo("imm-index-notification");
        assertThat(request.metaQueryBody().notificationAttributes().withFields()).containsExactly("Insights", "Labels");
        assertThat(request.metaQueryBody().datasetConfig().insights().language()).isEqualTo("en");
    }

    @Test
    public void testToBuilderPreserveState() {
        MetaQueryOpenBody body = MetaQueryOpenBody.newBuilder()
                .filters(Arrays.asList("Size > 0", "ContentType = 'image/jpeg'"))
                .build();

        OpenMetaQueryRequest original = OpenMetaQueryRequest.newBuilder()
                .bucket("test-bucket")
                .mode("basic")
                .role("my-role")
                .metaQueryBody(body)
                .build();

        OpenMetaQueryRequest copy = original.toBuilder()
                .role("new-role")
                .build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.mode()).isEqualTo("basic");
        assertThat(copy.role()).isEqualTo("new-role");
        assertThat(copy.metaQueryBody()).isSameAs(body);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        // Reference: sdk_internal_reference(3).md §1 OpenMetaQuery full XML example
        String xml = "" +
                "<MetaQuery>\n" +
                "  <WorkflowParameters>\n" +
                "    <WorkflowParameter>\n" +
                "      <Name>ImageInsightEnable</Name>\n" +
                "      <Value>True</Value>\n" +
                "    </WorkflowParameter>\n" +
                "    <WorkflowParameter>\n" +
                "      <Name>VideoInsightEnable</Name>\n" +
                "      <Value>True</Value>\n" +
                "    </WorkflowParameter>\n" +
                "  </WorkflowParameters>\n" +
                "  <NotificationAttributes>\n" +
                "    <Notifications>\n" +
                "      <Notification>\n" +
                "        <MNS>imm-index-notification</MNS>\n" +
                "      </Notification>\n" +
                "    </Notifications>\n" +
                "    <WithFields>\n" +
                "      <WithField>Insights</WithField>\n" +
                "      <WithField>Labels</WithField>\n" +
                "    </WithFields>\n" +
                "  </NotificationAttributes>\n" +
                "  <IndexOptions>\n" +
                "    <IgnoreObjectDelete>True</IgnoreObjectDelete>\n" +
                "  </IndexOptions>\n" +
                "  <RouteRule>\n" +
                "    <Type>OSSTag</Type>\n" +
                "    <AutoCreateDataset>True</AutoCreateDataset>\n" +
                "    <OSSTagKey>routing-dataset</OSSTagKey>\n" +
                "  </RouteRule>\n" +
                "  <DatasetConfig>\n" +
                "    <Insights>\n" +
                "      <Language>en</Language>\n" +
                "    </Insights>\n" +
                "  </DatasetConfig>\n" +
                "</MetaQuery>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MetaQueryOpenBody xmlBody = xmlMapper.readValue(xml, MetaQueryOpenBody.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlBody);

        MetaQueryNotification notification = MetaQueryNotification.newBuilder()
                .mns("imm-index-notification")
                .build();
        MetaQueryNotifications notifications = MetaQueryNotifications.newBuilder()
                .notifications(Collections.singletonList(notification))
                .build();
        NotificationAttributes notificationAttributes = NotificationAttributes.newBuilder()
                .notifications(notifications)
                .withFields(Arrays.asList("Insights", "Labels"))
                .build();

        RouteRule routeRule = RouteRule.newBuilder()
                .type("OSSTag")
                .autoCreateDataset("True")
                .ossTagKey("routing-dataset")
                .build();

        IndexOptions indexOptions = IndexOptions.newBuilder()
                .ignoreObjectDelete("True")
                .build();

        InsightsConfig insightsConfig = InsightsConfig.newBuilder()
                .language("en")
                .build();
        DatasetConfig datasetConfig = DatasetConfig.newBuilder()
                .insights(insightsConfig)
                .build();

        WorkflowParameters workflowParams = WorkflowParameters.newBuilder()
                .workflowParameters(Arrays.asList(
                        WorkflowParameter.newBuilder().name("ImageInsightEnable").value("True").build(),
                        WorkflowParameter.newBuilder().name("VideoInsightEnable").value("True").build()))
                .build();

        MetaQueryOpenBody body = MetaQueryOpenBody.newBuilder()
                .workflowParameters(workflowParams)
                .notificationAttributes(notificationAttributes)
                .indexOptions(indexOptions)
                .routeRule(routeRule)
                .datasetConfig(datasetConfig)
                .build();

        OpenMetaQueryRequest request = OpenMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .mode("semantic")
                .role("AliyunMetaQueryDefaultRole")
                .metaQueryBody(body)
                .build();

        OperationInput input = SerdeDatasetBasic.fromOpenMetaQuery(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("openMetaQuery");
        assertThat(input.parameters().get("mode")).isEqualTo("semantic");
        assertThat(input.parameters().get("role")).isEqualTo("AliyunMetaQueryDefaultRole");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData bodyData = input.body().get();
        String xmlContent = new String(bodyData.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<MetaQuery>");
        assertThat(xmlContent).contains("<WorkflowParameter>");
        assertThat(xmlContent).contains("<Name>ImageInsightEnable</Name>");
        assertThat(xmlContent).contains("<Value>True</Value>");
        assertThat(xmlContent).contains("<MNS>imm-index-notification</MNS>");
        assertThat(xmlContent).contains("<WithField>Insights</WithField>");
        assertThat(xmlContent).contains("<IgnoreObjectDelete>True</IgnoreObjectDelete>");
        assertThat(xmlContent).contains("<Type>OSSTag</Type>");
        assertThat(xmlContent).contains("<AutoCreateDataset>True</AutoCreateDataset>");
        assertThat(xmlContent).contains("<OSSTagKey>routing-dataset</OSSTagKey>");
        assertThat(xmlContent).contains("<Language>en</Language>");
        assertThat(xmlContent).contains("</MetaQuery>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }

    @Test
    public void xmlBuilderBasicModeMinimal() throws JsonProcessingException {
        // basic mode: only filters
        String xml = "" +
                "<MetaQuery>\n" +
                "  <Filters>\n" +
                "    <Filter>Size &gt; 0</Filter>\n" +
                "  </Filters>\n" +
                "</MetaQuery>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MetaQueryOpenBody xmlBody = xmlMapper.readValue(xml, MetaQueryOpenBody.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlBody);

        MetaQueryOpenBody body = MetaQueryOpenBody.newBuilder()
                .filters(Collections.singletonList("Size > 0"))
                .build();

        OpenMetaQueryRequest request = OpenMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .mode("basic")
                .metaQueryBody(body)
                .build();

        OperationInput input = SerdeDatasetBasic.fromOpenMetaQuery(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("openMetaQuery");
        assertThat(input.parameters().get("mode")).isEqualTo("basic");

        BinaryData bodyData = input.body().get();
        String xmlContent = new String(bodyData.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<MetaQuery>");
        assertThat(xmlContent).contains("<Filter>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}
