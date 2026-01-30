package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketMetaquery;
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
    }

    @Test
    public void testFullBuilder() {
        MetaQueryStatus metaQueryStatus = MetaQueryStatus.newBuilder()
                .state("Running")
                .phase("FullScanning")
                .createTime("2024-09-11T10:49:17.289372919+08:00")
                .updateTime("2024-09-11T10:49:17.289372919+08:00")
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetMetaQueryStatusResult result = GetMetaQueryStatusResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .innerBody(metaQueryStatus)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.metaQueryStatus()).isNotNull();
        assertThat(result.metaQueryStatus().state()).isEqualTo("Running");
        assertThat(result.metaQueryStatus().phase()).isEqualTo("FullScanning");
        assertThat(result.metaQueryStatus().createTime()).isEqualTo("2024-09-11T10:49:17.289372919+08:00");
        assertThat(result.metaQueryStatus().updateTime()).isEqualTo("2024-09-11T10:49:17.289372919+08:00");
    }

    @Test
    public void testToBuilderPreserveState() {
        MetaQueryStatus metaQueryStatus = MetaQueryStatus.newBuilder()
                .state("Ready")
                .phase("IncrementalScanning")
                .createTime("2024-09-11T10:49:17.289372919+08:00")
                .updateTime("2024-09-11T10:49:17.289372919+08:00")
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetMetaQueryStatusResult original = GetMetaQueryStatusResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .innerBody(metaQueryStatus)
                .build();

        GetMetaQueryStatusResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.metaQueryStatus()).isNotNull();
        assertThat(copy.metaQueryStatus().state()).isEqualTo("Ready");
        assertThat(copy.metaQueryStatus().phase()).isEqualTo("IncrementalScanning");
        assertThat(copy.metaQueryStatus().createTime()).isEqualTo("2024-09-11T10:49:17.289372919+08:00");
        assertThat(copy.metaQueryStatus().updateTime()).isEqualTo("2024-09-11T10:49:17.289372919+08:00");
    }

    @Test
    public void xmlBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<MetaQueryStatus>\n" +
                "  <State>Running</State>\n" +
                "  <Phase>FullScanning</Phase>\n" +
                "  <CreateTime>2024-09-11T10:49:17.289372919+08:00</CreateTime>\n" +
                "  <UpdateTime>2024-09-11T10:49:17.289372919+08:00</UpdateTime>\n" +
                "</MetaQueryStatus>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetMetaQueryStatusResult result = SerdeBucketMetaquery.toGetMetaQueryStatus(output);

        assertThat(result).isNotNull();
        assertThat(result.metaQueryStatus()).isNotNull();
        assertThat(result.metaQueryStatus().state()).isEqualTo("Running");
        assertThat(result.metaQueryStatus().phase()).isEqualTo("FullScanning");
        assertThat(result.metaQueryStatus().createTime()).isEqualTo("2024-09-11T10:49:17.289372919+08:00");
        assertThat(result.metaQueryStatus().updateTime()).isEqualTo("2024-09-11T10:49:17.289372919+08:00");

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        OperationOutput outputWithHeaders = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetMetaQueryStatusResult resultWithHeaders = SerdeBucketMetaquery.toGetMetaQueryStatus(outputWithHeaders);

        assertThat(resultWithHeaders.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(resultWithHeaders.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(resultWithHeaders.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(resultWithHeaders.statusCode()).isEqualTo(200);
        assertThat(resultWithHeaders.metaQueryStatus()).isNotNull();
        assertThat(resultWithHeaders.metaQueryStatus().state()).isEqualTo("Running");
        assertThat(resultWithHeaders.metaQueryStatus().phase()).isEqualTo("FullScanning");
        assertThat(resultWithHeaders.metaQueryStatus().createTime()).isEqualTo("2024-09-11T10:49:17.289372919+08:00");
        assertThat(resultWithHeaders.metaQueryStatus().updateTime()).isEqualTo("2024-09-11T10:49:17.289372919+08:00");
    }
}