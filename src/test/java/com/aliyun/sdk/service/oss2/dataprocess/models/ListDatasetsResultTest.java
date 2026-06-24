package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListDatasetsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListDatasetsResult result = ListDatasetsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.datasets()).isNull();
        assertThat(result.nextToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Length", "512"
        );

        ListDatasetsResult result = ListDatasetsResult.newBuilder()
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

        ListDatasetsResult original = ListDatasetsResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListDatasetsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §9 ListDatasets response
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<ListDatasetsResponse>"
                + "  <NextToken>page-2-token-abc</NextToken>"
                + "  <Datasets>"
                + "    <Dataset>"
                + "      <DatasetName>oss_1023210024677934_examplebucket</DatasetName>"
                + "      <CreateTime>2026-05-20T08:00:00.000+08:00</CreateTime>"
                + "      <UpdateTime>2026-05-20T08:00:00.000+08:00</UpdateTime>"
                + "    </Dataset>"
                + "    <Dataset>"
                + "      <DatasetName>photos-2026</DatasetName>"
                + "      <CreateTime>2026-05-21T09:00:00.000+08:00</CreateTime>"
                + "      <UpdateTime>2026-05-22T10:30:00.000+08:00</UpdateTime>"
                + "    </Dataset>"
                + "  </Datasets>"
                + "</ListDatasetsResponse>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .headers(MapUtils.of("x-oss-request-id", "req-list-datasets"))
                .build();

        ListDatasetsResult result = SerdeDatasetBasic.toListDatasets(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-list-datasets");
        assertThat(result.nextToken()).isEqualTo("page-2-token-abc");

        // Verify datasets
        assertThat(result.datasets()).isNotNull();
        assertThat(result.datasets()).hasSize(2);
        assertThat(result.datasets().get(0).datasetName()).isEqualTo("oss_1023210024677934_examplebucket");
        assertThat(result.datasets().get(0).createTime()).isEqualTo("2026-05-20T08:00:00.000+08:00");
        assertThat(result.datasets().get(0).updateTime()).isEqualTo("2026-05-20T08:00:00.000+08:00");
        assertThat(result.datasets().get(1).datasetName()).isEqualTo("photos-2026");
    }

    @Test
    public void xmlBuilderEmpty() {
        // No datasets
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<ListDatasetsResponse>"
                + "  <Datasets/>"
                + "</ListDatasetsResponse>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListDatasetsResult result = SerdeDatasetBasic.toListDatasets(output);

        assertThat(result.nextToken()).isNull();
        // Datasets may be null or empty depending on deserialization
    }
}
