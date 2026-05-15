package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateSelectObjectMetaRequestTest {

    @Test
    public void testEmptyBuilder() {
        CreateSelectObjectMetaRequest request = CreateSelectObjectMetaRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.selectMetaRequest()).isNull();
    }

    @Test
    public void testFullBuilder() {
        SelectMetaRequest selectMetaRequest = SelectMetaRequest.newBuilder()
                .overwriteIfExists(true)
                .build();

        CreateSelectObjectMetaRequest request = CreateSelectObjectMetaRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .selectMetaRequest(selectMetaRequest)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject");
        assertThat(request.selectMetaRequest()).isEqualTo(selectMetaRequest);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        CreateSelectObjectMetaRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("exampleobject");
        assertThat(copy.selectMetaRequest()).isEqualTo(selectMetaRequest);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        SelectMetaRequest selectMetaRequest = SelectMetaRequest.newBuilder()
                .overwriteIfExists(false)
                .build();

        CreateSelectObjectMetaRequest original = CreateSelectObjectMetaRequest.newBuilder()
                .bucket("test-bucket")
                .key("test-key")
                .selectMetaRequest(selectMetaRequest)
                .build();

        CreateSelectObjectMetaRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.key()).isEqualTo("test-key");
        assertThat(copy.selectMetaRequest()).isEqualTo(selectMetaRequest);
    }

    @Test
    public void testHeaderProperties() {
        SelectMetaRequest selectMetaRequest = SelectMetaRequest.newBuilder()
                .overwriteIfExists(true)
                .build();

        CreateSelectObjectMetaRequest request = CreateSelectObjectMetaRequest.newBuilder()
                .bucket("meta-bucket")
                .key("meta-key")
                .selectMetaRequest(selectMetaRequest)
                .build();

        assertThat(request.bucket()).isEqualTo("meta-bucket");
        assertThat(request.key()).isEqualTo("meta-key");
        assertThat(request.selectMetaRequest().overwriteIfExists()).isEqualTo(true);
    }

    @Test
    public void xmlBuilderCsv() {
        CSVInput csvInput = CSVInput.newBuilder()
                .recordDelimiter("Cg==")
                .fieldDelimiter("LA==")
                .quoteCharacter("Ig==")
                .build();

        InputSerialization inputSerialization = InputSerialization.newBuilder()
                .compressionType("None")
                .csv(csvInput)
                .build();

        SelectMetaRequest selectMetaRequest = SelectMetaRequest.newBuilder()
                .inputSerialization(inputSerialization)
                .overwriteIfExists(true)
                .build();

        CreateSelectObjectMetaRequest request = CreateSelectObjectMetaRequest.newBuilder()
                .bucket("xml-bucket")
                .key("xml-key")
                .selectMetaRequest(selectMetaRequest)
                .build();

        OperationInput input = SerdeObjectBasic.fromCreateSelectObjectMeta(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.key().get()).isEqualTo("xml-key");
        assertThat(input.parameters().get("x-oss-process")).isEqualTo("csv/meta");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<CsvMetaRequest>");
        assertThat(xmlContent).contains("<InputSerialization>");
        assertThat(xmlContent).contains("<CompressionType>None</CompressionType>");
        assertThat(xmlContent).contains("<CSV>");
        assertThat(xmlContent).contains("<RecordDelimiter>Cg==</RecordDelimiter>");
        assertThat(xmlContent).contains("<FieldDelimiter>LA==</FieldDelimiter>");
        assertThat(xmlContent).contains("<QuoteCharacter>Ig==</QuoteCharacter>");
        assertThat(xmlContent).contains("</CSV>");
        assertThat(xmlContent).contains("</InputSerialization>");
        assertThat(xmlContent).contains("<OverwriteIfExists>true</OverwriteIfExists>");
        assertThat(xmlContent).contains("</CsvMetaRequest>");
    }

    @Test
    public void xmlBuilderJson() {
        JSONInput jsonInput = JSONInput.newBuilder()
                .type("LINES")
                .build();

        InputSerialization inputSerialization = InputSerialization.newBuilder()
                .json(jsonInput)
                .build();

        SelectMetaRequest selectMetaRequest = SelectMetaRequest.newBuilder()
                .inputSerialization(inputSerialization)
                .overwriteIfExists(false)
                .build();

        CreateSelectObjectMetaRequest request = CreateSelectObjectMetaRequest.newBuilder()
                .bucket("xml-bucket")
                .key("xml-key")
                .selectMetaRequest(selectMetaRequest)
                .build();

        OperationInput input = SerdeObjectBasic.fromCreateSelectObjectMeta(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.key().get()).isEqualTo("xml-key");
        assertThat(input.parameters().get("x-oss-process")).isEqualTo("json/meta");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<JsonMetaRequest>");
        assertThat(xmlContent).contains("<InputSerialization>");
        assertThat(xmlContent).contains("<JSON>");
        assertThat(xmlContent).contains("<Type>LINES</Type>");
        assertThat(xmlContent).contains("</JSON>");
        assertThat(xmlContent).contains("</InputSerialization>");
        assertThat(xmlContent).contains("<OverwriteIfExists>false</OverwriteIfExists>");
        assertThat(xmlContent).contains("</JsonMetaRequest>");
    }
}
