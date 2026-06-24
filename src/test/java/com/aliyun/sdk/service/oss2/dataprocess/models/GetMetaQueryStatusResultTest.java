package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetMetaQueryStatusResultTest {

    @Test
    public void testEmptyBuilder() {
        GetMetaQueryStatusResult result = GetMetaQueryStatusResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.metaQueryStatus()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Length", "128"
        );

        GetMetaQueryStatusResult result = GetMetaQueryStatusResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "Content-Length", "0"
        );

        GetMetaQueryStatusResult original = GetMetaQueryStatusResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetMetaQueryStatusResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §2 GetMetaQueryStatus response
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<MetaQueryStatus>"
                + "  <State>Running</State>"
                + "  <Phase>IncrementalScanning</Phase>"
                + "  <CreateTime>2026-05-20T08:00:00.000+08:00</CreateTime>"
                + "  <UpdateTime>2026-05-20T08:30:00.000+08:00</UpdateTime>"
                + "  <MetaQueryMode>semantic</MetaQueryMode>"
                + "  <Filters>"
                + "    <Filter>Size &gt; 1024</Filter>"
                + "    <Filter>ObjectACL = 'default'</Filter>"
                + "  </Filters>"
                + "  <WorkflowParameters>"
                + "    <WorkflowParameter>"
                + "      <Name>ImageInsightEnable</Name>"
                + "      <Value>True</Value>"
                + "    </WorkflowParameter>"
                + "  </WorkflowParameters>"
                + "  <IndexOptions>"
                + "    <IgnoreObjectDelete>True</IgnoreObjectDelete>"
                + "  </IndexOptions>"
                + "  <RouteRule>"
                + "    <Type>OSSTag</Type>"
                + "    <AutoCreateDataset>True</AutoCreateDataset>"
                + "    <OSSTagKey>routing-dataset</OSSTagKey>"
                + "  </RouteRule>"
                + "  <NotificationAttributes>"
                + "    <Notifications>"
                + "      <Notification>"
                + "        <MNS>imm-index-notification</MNS>"
                + "      </Notification>"
                + "    </Notifications>"
                + "    <WithFields>"
                + "      <WithField>Insights</WithField>"
                + "    </WithFields>"
                + "  </NotificationAttributes>"
                + "  <DatasetConfig>"
                + "    <Insights>"
                + "      <Language>en</Language>"
                + "    </Insights>"
                + "  </DatasetConfig>"
                + "</MetaQueryStatus>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .headers(MapUtils.of("x-oss-request-id", "req-get-metaquery-status"))
                .build();

        GetMetaQueryStatusResult result = SerdeDatasetBasic.toGetMetaQueryStatus(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-get-metaquery-status");
        assertThat(result.metaQueryStatus()).isNotNull();

        MetaQueryStatus status = result.metaQueryStatus();
        assertThat(status.state()).isEqualTo("Running");
        assertThat(status.phase()).isEqualTo("IncrementalScanning");
        assertThat(status.createTime()).isEqualTo("2026-05-20T08:00:00.000+08:00");
        assertThat(status.updateTime()).isEqualTo("2026-05-20T08:30:00.000+08:00");
        assertThat(status.metaQueryMode()).isEqualTo("semantic");

        // Filters
        assertThat(status.filters()).isNotNull();
        assertThat(status.filters()).containsExactly("Size > 1024", "ObjectACL = 'default'");

        // WorkflowParameters
        assertThat(status.workflowParameters()).isNotNull();
        assertThat(status.workflowParameters().workflowParameters()).hasSize(1);
        assertThat(status.workflowParameters().workflowParameters().get(0).name()).isEqualTo("ImageInsightEnable");
        assertThat(status.workflowParameters().workflowParameters().get(0).value()).isEqualTo("True");

        // IndexOptions
        assertThat(status.indexOptions()).isNotNull();
        assertThat(status.indexOptions().ignoreObjectDelete()).isEqualTo("True");

        // RouteRule
        assertThat(status.routeRule()).isNotNull();
        assertThat(status.routeRule().type()).isEqualTo("OSSTag");
        assertThat(status.routeRule().autoCreateDataset()).isEqualTo("True");
        assertThat(status.routeRule().ossTagKey()).isEqualTo("routing-dataset");

        // NotificationAttributes
        assertThat(status.notificationAttributes()).isNotNull();
        assertThat(status.notificationAttributes().notifications()).isNotNull();
        assertThat(status.notificationAttributes().notifications().notifications()).hasSize(1);
        assertThat(status.notificationAttributes().notifications().notifications().get(0).mns()).isEqualTo("imm-index-notification");
        assertThat(status.notificationAttributes().withFields()).containsExactly("Insights");

        // DatasetConfig
        assertThat(status.datasetConfig()).isNotNull();
        assertThat(status.datasetConfig().insights()).isNotNull();
        assertThat(status.datasetConfig().insights().language()).isEqualTo("en");
    }

    @Test
    public void xmlBuilderMinimal() {
        // Minimal status response
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<MetaQueryStatus>"
                + "  <State>Ready</State>"
                + "  <Phase>FullScanning</Phase>"
                + "  <MetaQueryMode>basic</MetaQueryMode>"
                + "</MetaQueryStatus>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetMetaQueryStatusResult result = SerdeDatasetBasic.toGetMetaQueryStatus(output);

        assertThat(result.metaQueryStatus()).isNotNull();
        assertThat(result.metaQueryStatus().state()).isEqualTo("Ready");
        assertThat(result.metaQueryStatus().phase()).isEqualTo("FullScanning");
        assertThat(result.metaQueryStatus().metaQueryMode()).isEqualTo("basic");
        assertThat(result.metaQueryStatus().workflowParameters()).isNull();
        assertThat(result.metaQueryStatus().routeRule()).isNull();
    }
}
