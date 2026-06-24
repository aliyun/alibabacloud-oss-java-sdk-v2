package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetDatasetResultTest {

    @Test
    public void testEmptyBuilder() {
        GetDatasetResult result = GetDatasetResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.dataset()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Length", "256"
        );

        GetDatasetResult result = GetDatasetResult.newBuilder()
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

        GetDatasetResult original = GetDatasetResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetDatasetResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §6 GetDataset response (withStatistics=true)
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<GetDatasetResponse>"
                + "  <Dataset>"
                + "    <DatasetName>photos-2026</DatasetName>"
                + "    <Description>Photo collection for year 2026</Description>"
                + "    <CreateTime>2026-05-20T08:00:00.000+08:00</CreateTime>"
                + "    <UpdateTime>2026-05-20T08:30:00.000+08:00</UpdateTime>"
                + "    <WorkflowParameters>"
                + "      <WorkflowParameter><Name>ImageInsightEnable</Name><Value>True</Value></WorkflowParameter>"
                + "    </WorkflowParameters>"
                + "    <DatasetMaxBindCount>10</DatasetMaxBindCount>"
                + "    <DatasetMaxFileCount>100000000</DatasetMaxFileCount>"
                + "    <DatasetMaxEntityCount>10000000000</DatasetMaxEntityCount>"
                + "    <DatasetMaxRelationCount>100000000000</DatasetMaxRelationCount>"
                + "    <DatasetMaxTotalFileSize>90000000000000000</DatasetMaxTotalFileSize>"
                + "    <DatasetConfig>"
                + "      <Insights><Language>en</Language></Insights>"
                + "    </DatasetConfig>"
                + "    <FileCount>3456</FileCount>"
                + "    <TotalFileSize>10737418240</TotalFileSize>"
                + "  </Dataset>"
                + "</GetDatasetResponse>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .headers(MapUtils.of("x-oss-request-id", "req-get-dataset"))
                .build();

        GetDatasetResult result = SerdeDatasetBasic.toGetDataset(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-get-dataset");
        assertThat(result.dataset()).isNotNull();

        Dataset ds = result.dataset();
        assertThat(ds.datasetName()).isEqualTo("photos-2026");
        assertThat(ds.description()).isEqualTo("Photo collection for year 2026");
        assertThat(ds.createTime()).isEqualTo("2026-05-20T08:00:00.000+08:00");
        assertThat(ds.updateTime()).isEqualTo("2026-05-20T08:30:00.000+08:00");

        // WorkflowParameters
        assertThat(ds.workflowParameters()).isNotNull();
        assertThat(ds.workflowParameters().workflowParameters()).hasSize(1);
        assertThat(ds.workflowParameters().workflowParameters().get(0).name()).isEqualTo("ImageInsightEnable");
        assertThat(ds.workflowParameters().workflowParameters().get(0).value()).isEqualTo("True");

        // Quota fields
        assertThat(ds.datasetMaxBindCount()).isEqualTo(10L);
        assertThat(ds.datasetMaxFileCount()).isEqualTo(100000000L);
        assertThat(ds.datasetMaxEntityCount()).isEqualTo(10000000000L);
        assertThat(ds.datasetMaxRelationCount()).isEqualTo(100000000000L);
        assertThat(ds.datasetMaxTotalFileSize()).isEqualTo(90000000000000000L);

        // DatasetConfig
        assertThat(ds.datasetConfig()).isNotNull();
        assertThat(ds.datasetConfig().insights()).isNotNull();
        assertThat(ds.datasetConfig().insights().language()).isEqualTo("en");

        // withStatistics fields
        assertThat(ds.fileCount()).isEqualTo(3456L);
        assertThat(ds.totalFileSize()).isEqualTo(10737418240L);
    }

    @Test
    public void xmlBuilderWithoutStatistics() {
        // Response without withStatistics fields
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<GetDatasetResponse>"
                + "  <Dataset>"
                + "    <DatasetName>oss_1023210024677934_examplebucket</DatasetName>"
                + "    <CreateTime>2026-05-20T08:00:00.000+08:00</CreateTime>"
                + "    <UpdateTime>2026-05-20T08:00:00.000+08:00</UpdateTime>"
                + "  </Dataset>"
                + "</GetDatasetResponse>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetDatasetResult result = SerdeDatasetBasic.toGetDataset(output);

        assertThat(result.dataset()).isNotNull();
        assertThat(result.dataset().datasetName()).isEqualTo("oss_1023210024677934_examplebucket");
        assertThat(result.dataset().description()).isNull();
        assertThat(result.dataset().fileCount()).isNull();
        assertThat(result.dataset().totalFileSize()).isNull();
        assertThat(result.dataset().workflowParameters()).isNull();
        assertThat(result.dataset().datasetConfig()).isNull();
    }
}
