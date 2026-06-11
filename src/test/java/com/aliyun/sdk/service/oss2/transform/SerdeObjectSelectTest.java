package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SerdeObjectSelectTest {

    // --- fromSelectObject tests ---

    @Test
    void fromSelectObject_basicCSV() {
        SelectObjectRequest request = SelectObjectRequest.newBuilder()
                .bucket("test-bucket")
                .key("test.csv")
                .selectRequest(SelectRequest.newBuilder()
                        .expression("c2VsZWN0ICogZnJvbSBvc3NvYmplY3Q=")
                        .inputSerialization(InputSerialization.newBuilder()
                                .compressionType("NONE")
                                .csv(CSVInput.newBuilder()
                                        .fileHeaderInfo("Use")
                                        .recordDelimiter("Cg==")
                                        .fieldDelimiter("LA==")
                                        .build())
                                .build())
                        .outputSerialization(OutputSerialization.newBuilder()
                                .csv(CSVOutput.newBuilder()
                                        .recordDelimiter("Cg==")
                                        .fieldDelimiter("LA==")
                                        .build())
                                .keepAllColumns(false)
                                .outputRawData(false)
                                .enablePayloadCrc(true)
                                .build())
                        .build())
                .process("csv/select")
                .build();

        OperationInput opInput = SerdeObjectBasic.fromSelectObject(request);

        assertThat(opInput.opName()).isEqualTo("SelectObject");
        assertThat(opInput.method()).isEqualTo("POST");
        assertThat(opInput.bucket()).hasValue("test-bucket");
        assertThat(opInput.key()).hasValue("test.csv");
        assertThat(opInput.parameters().get("x-oss-process")).isEqualTo("csv/select");
        assertThat(opInput.headers().get("Content-Type")).isEqualTo("application/xml");

        assertThat(opInput.body()).isPresent();
        String bodyStr = bodyToString(opInput.body().get());
        assertThat(bodyStr).contains("<SelectRequest>");
        assertThat(bodyStr).contains("<Expression>c2VsZWN0ICogZnJvbSBvc3NvYmplY3Q=</Expression>");
        assertThat(bodyStr).contains("<CompressionType>NONE</CompressionType>");
        assertThat(bodyStr).contains("<FileHeaderInfo>Use</FileHeaderInfo>");
        assertThat(bodyStr).contains("<RecordDelimiter>Cg==</RecordDelimiter>");
        assertThat(bodyStr).contains("<FieldDelimiter>LA==</FieldDelimiter>");
        assertThat(bodyStr).contains("<KeepAllColumns>false</KeepAllColumns>");
        assertThat(bodyStr).contains("<OutputRawData>false</OutputRawData>");
        assertThat(bodyStr).contains("<EnablePayloadCrc>true</EnablePayloadCrc>");
        assertThat(bodyStr).contains("</SelectRequest>");
    }

    @Test
    void fromSelectObject_JSON() {
        SelectObjectRequest request = SelectObjectRequest.newBuilder()
                .bucket("test-bucket")
                .key("test.json")
                .selectRequest(SelectRequest.newBuilder()
                        .expression("c2VsZWN0ICogZnJvbSBvc3NvYmplY3Q=")
                        .inputSerialization(InputSerialization.newBuilder()
                                .json(JSONInput.newBuilder()
                                        .type("LINES")
                                        .parseJsonNumberAsString(true)
                                        .build())
                                .build())
                        .outputSerialization(OutputSerialization.newBuilder()
                                .json(JSONOutput.newBuilder()
                                        .recordDelimiter("Cg==")
                                        .build())
                                .build())
                        .build())
                .process("json/select")
                .build();

        OperationInput opInput = SerdeObjectBasic.fromSelectObject(request);

        assertThat(opInput.parameters().get("x-oss-process")).isEqualTo("json/select");

        String bodyStr = bodyToString(opInput.body().get());
        assertThat(bodyStr).contains("<Type>LINES</Type>");
        assertThat(bodyStr).contains("<ParseJsonNumberAsString>true</ParseJsonNumberAsString>");
    }

    @Test
    void fromSelectObject_withOptions() {
        SelectObjectRequest request = SelectObjectRequest.newBuilder()
                .bucket("b")
                .key("k")
                .selectRequest(SelectRequest.newBuilder()
                        .expression("dGVzdA==")
                        .options(SelectRequestOptions.newBuilder()
                                .skipPartialDataRecord(true)
                                .maxSkippedRecordsAllowed(100L)
                                .build())
                        .build())
                .process("csv/select")
                .build();

        OperationInput opInput = SerdeObjectBasic.fromSelectObject(request);

        String bodyStr = bodyToString(opInput.body().get());
        assertThat(bodyStr).contains("<SkipPartialDataRecord>true</SkipPartialDataRecord>");
        assertThat(bodyStr).contains("<MaxSkippedRecordsAllowed>100</MaxSkippedRecordsAllowed>");
    }

    // --- fromCreateSelectObjectMeta tests ---

    @Test
    void fromCreateSelectObjectMeta_CSV() {
        CreateSelectObjectMetaRequest request = CreateSelectObjectMetaRequest.newBuilder()
                .bucket("test-bucket")
                .key("test.csv")
                .selectMetaRequest(SelectMetaRequest.newBuilder()
                        .inputSerialization(InputSerialization.newBuilder()
                                .compressionType("NONE")
                                .csv(CSVInput.newBuilder()
                                        .recordDelimiter("Cg==")
                                        .fieldDelimiter("LA==")
                                        .quoteCharacter("Ig==")
                                        .build())
                                .build())
                        .overwriteIfExists(true)
                        .build())
                .process("csv/meta")
                .build();

        OperationInput opInput = SerdeObjectBasic.fromCreateSelectObjectMeta(request);

        assertThat(opInput.opName()).isEqualTo("CreateSelectObjectMeta");
        assertThat(opInput.method()).isEqualTo("POST");
        assertThat(opInput.bucket()).hasValue("test-bucket");
        assertThat(opInput.key()).hasValue("test.csv");
        assertThat(opInput.parameters().get("x-oss-process")).isEqualTo("csv/meta");
        assertThat(opInput.headers().get("Content-Type")).isEqualTo("application/xml");

        String bodyStr = bodyToString(opInput.body().get());
        assertThat(bodyStr).contains("<CsvMetaRequest>");
        assertThat(bodyStr).contains("<CompressionType>NONE</CompressionType>");
        assertThat(bodyStr).contains("<RecordDelimiter>Cg==</RecordDelimiter>");
        assertThat(bodyStr).contains("<OverwriteIfExists>true</OverwriteIfExists>");
        assertThat(bodyStr).contains("</CsvMetaRequest>");
    }

    @Test
    void fromCreateSelectObjectMeta_JSON() {
        CreateSelectObjectMetaRequest request = CreateSelectObjectMetaRequest.newBuilder()
                .bucket("test-bucket")
                .key("test.json")
                .selectMetaRequest(SelectMetaRequest.newBuilder()
                        .inputSerialization(InputSerialization.newBuilder()
                                .json(JSONInput.newBuilder()
                                        .type("LINES")
                                        .build())
                                .build())
                        .overwriteIfExists(false)
                        .build())
                .process("json/meta")
                .build();

        OperationInput opInput = SerdeObjectBasic.fromCreateSelectObjectMeta(request);

        assertThat(opInput.parameters().get("x-oss-process")).isEqualTo("json/meta");

        String bodyStr = bodyToString(opInput.body().get());
        assertThat(bodyStr).contains("<JsonMetaRequest>");
        assertThat(bodyStr).contains("<Type>LINES</Type>");
        assertThat(bodyStr).contains("<OverwriteIfExists>false</OverwriteIfExists>");
        assertThat(bodyStr).contains("</JsonMetaRequest>");
    }

    // --- toSelectObject tests ---

    @Test
    void toSelectObject_basic() throws Exception {
        byte[] dataFrame = buildFrame(SelectFrameDecoder.DATA_FRAME, "hello world".getBytes());
        byte[] endFrame = buildEndFrame(200);

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        buf.write(dataFrame);
        buf.write(endFrame);

        OperationOutput output = OperationOutput.newBuilder()
                .statusCode(206)
                .headers(MapUtils.caseInsensitiveMap())
                .body(BinaryData.fromStream(new ByteArrayInputStream(buf.toByteArray())))
                .build();

        SelectObjectResult result = SerdeObjectBasic.toSelectObject(output);
        assertThat(result.statusCode()).isEqualTo(206);

        try (InputStream is = result.body()) {
            byte[] readBuf = new byte[1024];
            int n = is.read(readBuf);
            assertThat(new String(readBuf, 0, n)).isEqualTo("hello world");
            assertThat(is.read()).isEqualTo(-1);
        }
    }

    @Test
    void toSelectObject_noBody() {
        OperationOutput output = OperationOutput.newBuilder()
                .statusCode(206)
                .headers(MapUtils.caseInsensitiveMap())
                .build();

        SelectObjectResult result = SerdeObjectBasic.toSelectObject(output);
        assertThat(result.statusCode()).isEqualTo(206);
        assertThat(result.body()).isNotNull();
    }

    // --- toCreateSelectObjectMeta tests ---

    @Test
    void toCreateSelectObjectMeta_noBody() {
        OperationOutput output = OperationOutput.newBuilder()
                .statusCode(200)
                .headers(MapUtils.caseInsensitiveMap())
                .build();

        assertThatThrownBy(() -> SerdeObjectBasic.toCreateSelectObjectMeta(output))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Unexpected end of stream");
    }

    @Test
    void toCreateSelectObjectMeta_csvMetaEnd() {
        byte[] frameBody = buildCsvMetaFrame(2048, 200, 3, 50, 5);

        OperationOutput output = OperationOutput.newBuilder()
                .statusCode(200)
                .headers(MapUtils.caseInsensitiveMap())
                .body(BinaryData.fromStream(new ByteArrayInputStream(frameBody)))
                .build();

        CreateSelectObjectMetaResult result = SerdeObjectBasic.toCreateSelectObjectMeta(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.selectObjectMeta()).isNotNull();
        assertThat(result.selectObjectMeta().totalScanned()).isEqualTo(2048);
        assertThat(result.selectObjectMeta().status()).isEqualTo(200);
        assertThat(result.selectObjectMeta().splitsCount()).isEqualTo(3);
        assertThat(result.selectObjectMeta().rowsCount()).isEqualTo(50);
        assertThat(result.selectObjectMeta().columnsCount()).isEqualTo(5);
    }

    @Test
    void toCreateSelectObjectMeta_jsonMetaEnd() {
        byte[] frameBody = buildJsonMetaFrame(1024, 200, 2, 30);

        OperationOutput output = OperationOutput.newBuilder()
                .statusCode(200)
                .headers(MapUtils.caseInsensitiveMap())
                .body(BinaryData.fromStream(new ByteArrayInputStream(frameBody)))
                .build();

        CreateSelectObjectMetaResult result = SerdeObjectBasic.toCreateSelectObjectMeta(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.selectObjectMeta()).isNotNull();
        assertThat(result.selectObjectMeta().totalScanned()).isEqualTo(1024);
        assertThat(result.selectObjectMeta().status()).isEqualTo(200);
        assertThat(result.selectObjectMeta().splitsCount()).isEqualTo(2);
        assertThat(result.selectObjectMeta().rowsCount()).isEqualTo(30);
        assertThat(result.selectObjectMeta().columnsCount()).isNull();
    }

    @Test
    void toCreateSelectObjectMeta_continuousFrameBeforeMetaEnd() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        byte[] contFrame = buildMetaFrame(0x800004, new byte[0]); // CONTINUOUS_FRAME
        byte[] metaFrame = buildCsvMetaFrame(512, 200, 1, 10, 3);
        try {
            buf.write(contFrame);
            buf.write(metaFrame);
        } catch (IOException ignored) {
        }

        OperationOutput output = OperationOutput.newBuilder()
                .statusCode(200)
                .headers(MapUtils.caseInsensitiveMap())
                .body(BinaryData.fromStream(new ByteArrayInputStream(buf.toByteArray())))
                .build();

        CreateSelectObjectMetaResult result = SerdeObjectBasic.toCreateSelectObjectMeta(output);
        assertThat(result.selectObjectMeta()).isNotNull();
        assertThat(result.selectObjectMeta().rowsCount()).isEqualTo(10);
        assertThat(result.selectObjectMeta().columnsCount()).isEqualTo(3);
    }

    @Test
    void toCreateSelectObjectMeta_non2xxStatusThrows() {
        byte[] frameBody = buildCsvMetaFrameWithError(403, "AccessDenied");

        OperationOutput output = OperationOutput.newBuilder()
                .statusCode(200)
                .headers(MapUtils.caseInsensitiveMap())
                .body(BinaryData.fromStream(new ByteArrayInputStream(frameBody)))
                .build();

        assertThatThrownBy(() -> SerdeObjectBasic.toCreateSelectObjectMeta(output))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("403");
    }

    @Test
    void toCreateSelectObjectMeta_truncatedFrame() {
        // Truncated frame: only partial header, readFully should fail
        byte[] truncatedData = new byte[]{
                0x01, (byte) 0x80, 0x00, 0x06, 0x00, 0x00, 0x00, 0x25,
                0x00, 0x00, 0x00, 0x00
        };

        OperationOutput output = OperationOutput.newBuilder()
                .statusCode(200)
                .headers(MapUtils.caseInsensitiveMap())
                .body(BinaryData.fromStream(new ByteArrayInputStream(truncatedData)))
                .build();

        assertThatThrownBy(() -> SerdeObjectBasic.toCreateSelectObjectMeta(output))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void toCreateSelectObjectMeta_unknownFrameType() {
        // Frame type 0x800008 is not recognized by parseCreateSelectMetaStream
        byte[] unknownTypeFrame = buildMetaFrame(0x800008, new byte[]{0x01, 0x02, 0x03, 0x04});

        OperationOutput output = OperationOutput.newBuilder()
                .statusCode(200)
                .headers(MapUtils.caseInsensitiveMap())
                .body(BinaryData.fromStream(new ByteArrayInputStream(unknownTypeFrame)))
                .build();

        assertThatThrownBy(() -> SerdeObjectBasic.toCreateSelectObjectMeta(output))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Unsupported frame type");
    }

    // --- Helper methods ---

    private static String bodyToString(BinaryData body) {
        try (InputStream is = body.toStream()) {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte[] tmp = new byte[1024];
            int n;
            while ((n = is.read(tmp)) != -1) {
                buf.write(tmp, 0, n);
            }
            return buf.toString("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeBE32(ByteArrayOutputStream buf, int value) {
        buf.write((value >> 24) & 0xFF);
        buf.write((value >> 16) & 0xFF);
        buf.write((value >> 8) & 0xFF);
        buf.write(value & 0xFF);
    }

    private static void writeBE64(ByteArrayOutputStream buf, long value) {
        for (int i = 7; i >= 0; i--) {
            buf.write((int) ((value >> (i * 8)) & 0xFF));
        }
    }

    private static byte[] buildFrame(int frameType, byte[] payload) {
        ByteArrayOutputStream frame = new ByteArrayOutputStream();
        frame.write(1);
        frame.write((frameType >> 16) & 0xFF);
        frame.write((frameType >> 8) & 0xFF);
        frame.write(frameType & 0xFF);
        int payloadLength = payload.length + 8;
        writeBE32(frame, payloadLength);
        writeBE32(frame, 0);
        writeBE64(frame, 0);
        frame.write(payload, 0, payload.length);

        byte[] frameBytes = frame.toByteArray();
        CRC32 crc = new CRC32();
        crc.update(frameBytes, 12, 8 + payload.length);
        writeBE32(frame, (int) crc.getValue());
        return frame.toByteArray();
    }

    private static byte[] buildEndFrame(int status) {
        ByteArrayOutputStream payload = new ByteArrayOutputStream();
        writeBE64(payload, 100);
        writeBE32(payload, status);
        return buildFrame(SelectFrameDecoder.END_FRAME, payload.toByteArray());
    }

    private static byte[] buildMetaFrame(int frameType, byte[] payload) {
        ByteArrayOutputStream frame = new ByteArrayOutputStream();
        frame.write(1);
        frame.write((frameType >> 16) & 0xFF);
        frame.write((frameType >> 8) & 0xFF);
        frame.write(frameType & 0xFF);
        int payloadLength = payload.length + 8;
        writeBE32(frame, payloadLength);
        writeBE32(frame, 0);
        writeBE64(frame, 0);
        frame.write(payload, 0, payload.length);
        writeBE32(frame, 0);
        return frame.toByteArray();
    }

    private static byte[] buildCsvMetaFrame(long totalScanned, int status, int splits, long rows, int cols) {
        ByteArrayOutputStream payload = new ByteArrayOutputStream();
        writeBE64(payload, totalScanned);
        writeBE32(payload, status);
        writeBE32(payload, splits);
        writeBE64(payload, rows);
        writeBE32(payload, cols);
        return buildMetaFrame(0x800006, payload.toByteArray());
    }

    private static byte[] buildCsvMetaFrameWithError(int status, String errorMessage) {
        ByteArrayOutputStream payload = new ByteArrayOutputStream();
        writeBE64(payload, 100);
        writeBE32(payload, status);
        writeBE32(payload, 1);
        writeBE64(payload, 0);
        writeBE32(payload, 0);
        byte[] errBytes = errorMessage.getBytes();
        payload.write(errBytes, 0, errBytes.length);
        return buildMetaFrame(0x800006, payload.toByteArray());
    }

    private static byte[] buildJsonMetaFrame(long totalScanned, int status, int splits, long rows) {
        ByteArrayOutputStream payload = new ByteArrayOutputStream();
        writeBE64(payload, totalScanned);
        writeBE32(payload, status);
        writeBE32(payload, splits);
        writeBE64(payload, rows);
        return buildMetaFrame(0x800007, payload.toByteArray());
    }
}
