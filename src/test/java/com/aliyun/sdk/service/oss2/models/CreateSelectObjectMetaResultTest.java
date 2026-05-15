package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateSelectObjectMetaResultTest {

    @Test
    public void testEmptyBuilder() {
        CreateSelectObjectMetaResult result = CreateSelectObjectMetaResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.selectMetaStatus()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        CreateSelectObjectMetaResult result = CreateSelectObjectMetaResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        CreateSelectObjectMetaResult original = CreateSelectObjectMetaResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        CreateSelectObjectMetaResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void testCsvCreateSelectObjectMetaResult() throws IOException {
        String csvContent = ".......%.........................................6Qq.";
        byte[] csvData = csvContent.getBytes(StandardCharsets.UTF_8);

        ByteArrayOutputStream framedBaos = new ByteArrayOutputStream();

        // End frame header (CSV: 8388614)
        byte[] frameTypeBytes = ByteBuffer.allocate(4).putInt(8388614).array();
        frameTypeBytes[0] = 1;
        framedBaos.write(frameTypeBytes);
        framedBaos.write(ByteBuffer.allocate(4).putInt(8 + 8 + 4 + 4 + 8 + 4 + csvData.length).array());
        framedBaos.write(new byte[4]);
        framedBaos.write(new byte[8]);

        // Payload
        framedBaos.write(new byte[8]); // totalScanSize = 0
        framedBaos.write(ByteBuffer.allocate(4).putInt(200).array()); // status = 200
        framedBaos.write(new byte[4]); // splits = 0
        framedBaos.write(new byte[8]); // rows = 0
        framedBaos.write(new byte[4]); // cols = 0
        framedBaos.write(csvData); // errorMessage

        // Payload checksum
        framedBaos.write(new byte[4]);

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromBytes(framedBaos.toByteArray()))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        CreateSelectObjectMetaResult result = SerdeObjectBasic.toCreateSelectObjectMeta(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.selectMetaStatus()).isNotNull();
        assertThat(result.selectMetaStatus().totalScannedBytes()).isEqualTo(0L);
        assertThat(result.selectMetaStatus().status()).isEqualTo(200L);
        assertThat(result.selectMetaStatus().splitsCount()).isEqualTo(0L);
        assertThat(result.selectMetaStatus().rowsCount()).isEqualTo(0L);
        assertThat(result.selectMetaStatus().colsCount()).isEqualTo(0L);
        assertThat(result.selectMetaStatus().errorMessage()).isEqualTo(csvContent);
    }

    @Test
    public void testJsonCreateSelectObjectMetaResult() throws IOException {
        String jsonContent = ".......!.......................................I.";
        byte[] jsonData = jsonContent.getBytes(StandardCharsets.UTF_8);

        ByteArrayOutputStream framedBaos = new ByteArrayOutputStream();

        // End frame header (JSON: 8388615)
        byte[] frameTypeBytes = ByteBuffer.allocate(4).putInt(8388615).array();
        frameTypeBytes[0] = 1;
        framedBaos.write(frameTypeBytes);
        framedBaos.write(ByteBuffer.allocate(4).putInt(8 + 8 + 4 + 4 + 8 + jsonData.length).array());
        framedBaos.write(new byte[4]);
        framedBaos.write(new byte[8]);

        // Payload
        framedBaos.write(new byte[8]); // totalScanSize = 0
        framedBaos.write(ByteBuffer.allocate(4).putInt(200).array()); // status = 200
        framedBaos.write(new byte[4]); // splits = 0
        framedBaos.write(new byte[8]); // rows = 0
        framedBaos.write(jsonData); // errorMessage

        // Payload checksum
        framedBaos.write(new byte[4]);

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromBytes(framedBaos.toByteArray()))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        CreateSelectObjectMetaResult result = SerdeObjectBasic.toCreateSelectObjectMeta(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.selectMetaStatus()).isNotNull();
        assertThat(result.selectMetaStatus().totalScannedBytes()).isEqualTo(0L);
        assertThat(result.selectMetaStatus().status()).isEqualTo(200L);
        assertThat(result.selectMetaStatus().splitsCount()).isEqualTo(0L);
        assertThat(result.selectMetaStatus().rowsCount()).isEqualTo(0L);
        assertThat(result.selectMetaStatus().colsCount()).isNull();
        assertThat(result.selectMetaStatus().errorMessage()).isEqualTo(jsonContent);
    }
}
