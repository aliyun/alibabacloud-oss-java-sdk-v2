package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class SelectObjectRequestTest {

    @Test
    public void testEmptyBuilder() {
        SelectObjectRequest request = SelectObjectRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.selectRequest()).isNull();
    }

    @Test
    public void testFullBuilder() {
        SelectRequest selectRequest = SelectRequest.newBuilder()
                .expression("c2VsZWN0IGNvdW50KCopIGZyb20gb3Nzb2JqZWN0IHdoZXJlIF80ID4gNDU=")
                .build();

        SelectObjectRequest request = SelectObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .selectRequest(selectRequest)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject");
        assertThat(request.selectRequest()).isEqualTo(selectRequest);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        SelectObjectRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("exampleobject");
        assertThat(copy.selectRequest()).isEqualTo(selectRequest);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        SelectRequest selectRequest = SelectRequest.newBuilder()
                .expression("test-expression")
                .build();

        SelectObjectRequest original = SelectObjectRequest.newBuilder()
                .bucket("test-bucket")
                .key("test-key")
                .selectRequest(selectRequest)
                .build();

        SelectObjectRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.key()).isEqualTo("test-key");
        assertThat(copy.selectRequest()).isEqualTo(selectRequest);
    }

    @Test
    public void testHeaderProperties() {
        SelectRequest selectRequest = SelectRequest.newBuilder()
                .expression("test-expression")
                .build();

        SelectObjectRequest request = SelectObjectRequest.newBuilder()
                .bucket("select-bucket")
                .key("select-key")
                .selectRequest(selectRequest)
                .requestPayer("requester")
                .build();

        assertThat(request.bucket()).isEqualTo("select-bucket");
        assertThat(request.key()).isEqualTo("select-key");
        assertThat(request.requestPayer()).isEqualTo("requester");
    }

    @Test
    public void xmlBuilderCsv() {
        CSVInput csvInput = CSVInput.newBuilder()
                .fileHeaderInfo("USE")
                .recordDelimiter("\n")
                .fieldDelimiter(",")
                .quoteCharacter("\"")
                .commentCharacter("#")
                .range("line-range=1-100")
                .allowQuotedRecordDelimiter(true)
                .build();

        InputSerialization inputSerialization = InputSerialization.newBuilder()
                .compressionType("None")
                .csv(csvInput)
                .build();

        CSVOutput csvOutput = CSVOutput.newBuilder()
                .recordDelimiter("\n")
                .fieldDelimiter(",")
                .build();

        OutputSerialization outputSerialization = OutputSerialization.newBuilder()
                .csv(csvOutput)
                .keepAllColumns(false)
                .outputRawData(false)
                .enablePayloadCrc(true)
                .outputHeader(false)
                .build();

        SelectRequestOptions selectOptions = SelectRequestOptions.newBuilder()
                .skipPartialDataRecord(false)
                .maxSkippedRecordsAllowed(100L)
                .build();

        SelectRequest selectRequest = SelectRequest.newBuilder()
                .expression("c2VsZWN0IGNvdW50KCopIGZyb20gb3Nzb2JqZWN0IHdoZXJlIF80ID4gNDU=")
                .inputSerialization(inputSerialization)
                .outputSerialization(outputSerialization)
                .options(selectOptions)
                .build();

        SelectObjectRequest request = SelectObjectRequest.newBuilder()
                .bucket("xml-bucket")
                .key("xml-key")
                .selectRequest(selectRequest)
                .build();

        OperationInput input = SerdeObjectBasic.fromSelectObject(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.key().get()).isEqualTo("xml-key");
        assertThat(input.parameters().get("x-oss-process")).isEqualTo("csv/select");

        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<SelectRequest>");
        assertThat(xmlContent).contains("<Expression>c2VsZWN0IGNvdW50KCopIGZyb20gb3Nzb2JqZWN0IHdoZXJlIF80ID4gNDU=</Expression>");
        assertThat(xmlContent).contains("<InputSerialization>");
        assertThat(xmlContent).contains("<CompressionType>None</CompressionType>");
        assertThat(xmlContent).contains("<CSV>");
        assertThat(xmlContent).contains("<FileHeaderInfo>USE</FileHeaderInfo>");
        assertThat(xmlContent).contains("<FieldDelimiter>,</FieldDelimiter>");
        assertThat(xmlContent).contains("<QuoteCharacter>\"</QuoteCharacter>");
        assertThat(xmlContent).contains("<CommentCharacter>#</CommentCharacter>");
        assertThat(xmlContent).contains("<Range>line-range=1-100</Range>");
        assertThat(xmlContent).contains("<AllowQuotedRecordDelimiter>true</AllowQuotedRecordDelimiter>");
        assertThat(xmlContent).contains("</CSV>");
        assertThat(xmlContent).contains("</InputSerialization>");
        assertThat(xmlContent).contains("<OutputSerialization>");
        assertThat(xmlContent).contains("<CSV>");
        assertThat(xmlContent).contains("<FieldDelimiter>,</FieldDelimiter>");
        assertThat(xmlContent).contains("</CSV>");
        assertThat(xmlContent).contains("<KeepAllColumns>false</KeepAllColumns>");
        assertThat(xmlContent).contains("<OutputRawData>false</OutputRawData>");
        assertThat(xmlContent).contains("<EnablePayloadCrc>true</EnablePayloadCrc>");
        assertThat(xmlContent).contains("<OutputHeader>false</OutputHeader>");
        assertThat(xmlContent).contains("</OutputSerialization>");
        assertThat(xmlContent).contains("<Options>");
        assertThat(xmlContent).contains("<SkipPartialDataRecord>false</SkipPartialDataRecord>");
        assertThat(xmlContent).contains("<MaxSkippedRecordsAllowed>100</MaxSkippedRecordsAllowed>");
        assertThat(xmlContent).contains("</Options>");
        assertThat(xmlContent).contains("</SelectRequest>");
    }

    @Test
    public void xmlBuilderJson() {
        JSONInput jsonInput = JSONInput.newBuilder()
                .type("LINES")
                .range("line-range=1-100")
                .parseJsonNumberAsString(true)
                .build();

        InputSerialization inputSerialization = InputSerialization.newBuilder()
                .compressionType("None")
                .json(jsonInput)
                .build();

        JSONOutput jsonOutput = JSONOutput.newBuilder()
                .recordDelimiter("\n")
                .build();

        OutputSerialization outputSerialization = OutputSerialization.newBuilder()
                .json(jsonOutput)
                .outputRawData(false)
                .enablePayloadCrc(true)
                .build();

        SelectRequestOptions selectOptions = SelectRequestOptions.newBuilder()
                .skipPartialDataRecord(false)
                .maxSkippedRecordsAllowed(100L)
                .build();

        SelectRequest selectRequest = SelectRequest.newBuilder()
                .expression("c2VsZWN0IGNvdW50KCopIGZyb20gb3Nzb2JqZWN0IHdoZXJlIF80ID4gNDU=")
                .inputSerialization(inputSerialization)
                .outputSerialization(outputSerialization)
                .options(selectOptions)
                .build();

        SelectObjectRequest request = SelectObjectRequest.newBuilder()
                .bucket("xml-bucket")
                .key("xml-key")
                .selectRequest(selectRequest)
                .build();

        OperationInput input = SerdeObjectBasic.fromSelectObject(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.key().get()).isEqualTo("xml-key");
        assertThat(input.parameters().get("x-oss-process")).isEqualTo("json/select");

        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<SelectRequest>");
        assertThat(xmlContent).contains("<Expression>c2VsZWN0IGNvdW50KCopIGZyb20gb3Nzb2JqZWN0IHdoZXJlIF80ID4gNDU=</Expression>");
        assertThat(xmlContent).contains("<InputSerialization>");
        assertThat(xmlContent).contains("<CompressionType>None</CompressionType>");
        assertThat(xmlContent).contains("<JSON>");
        assertThat(xmlContent).contains("<Type>LINES</Type>");
        assertThat(xmlContent).contains("<Range>line-range=1-100</Range>");
        assertThat(xmlContent).contains("<ParseJsonNumberAsString>true</ParseJsonNumberAsString>");
        assertThat(xmlContent).contains("</JSON>");
        assertThat(xmlContent).contains("</InputSerialization>");
        assertThat(xmlContent).contains("<OutputSerialization>");
        assertThat(xmlContent).contains("<JSON>");
        assertThat(xmlContent).contains("</JSON>");
        assertThat(xmlContent).contains("<OutputRawData>false</OutputRawData>");
        assertThat(xmlContent).contains("<EnablePayloadCrc>true</EnablePayloadCrc>");
        assertThat(xmlContent).contains("</OutputSerialization>");
        assertThat(xmlContent).contains("<Options>");
        assertThat(xmlContent).contains("<SkipPartialDataRecord>false</SkipPartialDataRecord>");
        assertThat(xmlContent).contains("<MaxSkippedRecordsAllowed>100</MaxSkippedRecordsAllowed>");
        assertThat(xmlContent).contains("</Options>");
        assertThat(xmlContent).contains("</SelectRequest>");
    }
}
