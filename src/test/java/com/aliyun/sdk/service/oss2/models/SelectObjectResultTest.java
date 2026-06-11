package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        SelectObjectResult result = SelectObjectResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.hashCrc64ecma()).isNull();
        assertThat(result.contentType()).isNull();
        assertThat(result.body()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Type", "application/octet-stream",
                "x-oss-hash-crc64ecma", "1234567890123456789"
        );

        SelectObjectResult result = SelectObjectResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/octet-stream");
        assertThat(result.headers().get("x-oss-hash-crc64ecma")).isEqualTo("1234567890123456789");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo("1234567890123456789");
        assertThat(result.contentType()).isEqualTo("application/octet-stream");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "Content-Type", "application/octet-stream",
                "x-oss-hash-crc64ecma", "9876543210987654321"
        );

        SelectObjectResult original = SelectObjectResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        SelectObjectResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("Content-Type")).isEqualTo("application/octet-stream");
        assertThat(copy.headers().get("x-oss-hash-crc64ecma")).isEqualTo("9876543210987654321");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.hashCrc64ecma()).isEqualTo("9876543210987654321");
        assertThat(copy.contentType()).isEqualTo("application/octet-stream");
    }

    @Test
    public void testCsvSelectObjectResult() throws IOException {
        String csvContent = "....................Lora Francis,School,Staples Inc,27\n" +
                "Eleanor Little,School,\"Conectiv, Inc\",43\n" +
                "Rosie Hughes,School,Western Gas Resources Inc,44\n" +
                "...........-............Lawrence Ross,School,MetLife Inc.,24\n" +
                "........................................";

        ByteArrayOutputStream framedBaos = new ByteArrayOutputStream();
        byte[] csvData = csvContent.getBytes(StandardCharsets.UTF_8);

        // Data frame
        byte[] frameTypeBytes = ByteBuffer.allocate(4).putInt(8388609).array();
        frameTypeBytes[0] = 1;
        framedBaos.write(frameTypeBytes);
        framedBaos.write(ByteBuffer.allocate(4).putInt(8 + csvData.length).array());
        framedBaos.write(new byte[4]); // header checksum
        framedBaos.write(new byte[8]); // scanned data bytes
        framedBaos.write(csvData);
        framedBaos.write(new byte[4]); // payload checksum

        // End frame
        frameTypeBytes = ByteBuffer.allocate(4).putInt(8388613).array();
        frameTypeBytes[0] = 1;
        framedBaos.write(frameTypeBytes);
        framedBaos.write(ByteBuffer.allocate(4).putInt(20).array());
        framedBaos.write(new byte[4]); // header checksum
        framedBaos.write(new byte[8]); // scanned data bytes
        framedBaos.write(new byte[8]); // total scan size
        framedBaos.write(ByteBuffer.allocate(4).putInt(200).array()); // status
        framedBaos.write(new byte[4]); // payload checksum

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Type", "application/octet-stream",
                "x-oss-hash-crc64ecma", "1234567890123456789"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromBytes(framedBaos.toByteArray()))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        SelectObjectResult result = SerdeObjectBasic.toSelectObject(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/octet-stream");
        assertThat(result.headers().get("x-oss-hash-crc64ecma")).isEqualTo("1234567890123456789");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.hashCrc64ecma()).isEqualTo("1234567890123456789");
        assertThat(result.contentType()).isEqualTo("application/octet-stream");
        assertThat(result.body()).isNotNull();

        InputStream is = result.body();
        ByteArrayOutputStream parsedBaos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            parsedBaos.write(buffer, 0, len);
        }
        String parsedContent = new String(parsedBaos.toByteArray(), StandardCharsets.UTF_8);
        assertThat(parsedContent).isEqualTo(csvContent);
    }

    @Test
    public void testJsonSelectObjectResult() throws IOException {
        String jsonContent = "{\n" +
                "\t\"name\": \"Lora Francis\",\n" +
                "\t\"age\": 27,\n" +
                "\t\"company\": \"Staples Inc\"\n" +
                "}\n" +
                "{\n" +
                "\t\"k2\": [-1, 79, 90],\n" +
                "\t\"k3\": {\n" +
                "\t\t\"k2\": 5,\n" +
                "\t\t\"k3\": 1,\n" +
                "\t\t\"k4\": 0\n" +
                "\t}\n" +
                "}\n" +
                "{\n" +
                "\t\"k1\": 1,\n" +
                "\t\"k2\": {\n" +
                "\t\t\"k2\": 5\n" +
                "\t},\n" +
                "\t\"k3\": []\n" +
                "}\n";

        ByteArrayOutputStream framedBaos = new ByteArrayOutputStream();
        byte[] jsonData = jsonContent.getBytes(StandardCharsets.UTF_8);

        // Data frame
        byte[] frameTypeBytes = ByteBuffer.allocate(4).putInt(8388609).array();
        frameTypeBytes[0] = 1;
        framedBaos.write(frameTypeBytes);
        framedBaos.write(ByteBuffer.allocate(4).putInt(8 + jsonData.length).array());
        framedBaos.write(new byte[4]); // header checksum
        framedBaos.write(new byte[8]); // scanned data bytes
        framedBaos.write(jsonData);
        framedBaos.write(new byte[4]); // payload checksum

        // End frame
        frameTypeBytes = ByteBuffer.allocate(4).putInt(8388613).array();
        frameTypeBytes[0] = 1;
        framedBaos.write(frameTypeBytes);
        framedBaos.write(ByteBuffer.allocate(4).putInt(20).array());
        framedBaos.write(new byte[4]); // header checksum
        framedBaos.write(new byte[8]); // scanned data bytes
        framedBaos.write(new byte[8]); // total scan size
        framedBaos.write(ByteBuffer.allocate(4).putInt(200).array()); // status
        framedBaos.write(new byte[4]); // payload checksum

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Type", "application/octet-stream",
                "x-oss-hash-crc64ecma", "1234567890123456789"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromBytes(framedBaos.toByteArray()))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        SelectObjectResult result = SerdeObjectBasic.toSelectObject(output);

        assertThat(result.body()).isNotNull();

        InputStream is = result.body();
        ByteArrayOutputStream parsedBaos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            parsedBaos.write(buffer, 0, len);
        }
        String parsedContent = new String(parsedBaos.toByteArray(), StandardCharsets.UTF_8);
        assertThat(parsedContent).isEqualTo(jsonContent);
    }
}
