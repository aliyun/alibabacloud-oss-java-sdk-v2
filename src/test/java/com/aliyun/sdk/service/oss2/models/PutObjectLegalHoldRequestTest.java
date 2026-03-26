package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectWorm;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutObjectLegalHoldRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutObjectLegalHoldRequest request = PutObjectLegalHoldRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.versionId()).isNull();
        assertThat(request.legalHold()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        LegalHold legalHold = LegalHold.newBuilder()
                .status(ObjectLegalHoldStatusType.ON.toString())
                .build();

        PutObjectLegalHoldRequest request = PutObjectLegalHoldRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .versionId("version-id")
                .legalHold(legalHold)
                .headers(headers)
                .parameter("param1", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject");
        assertThat(request.versionId()).isEqualTo("version-id");
        assertThat(request.legalHold()).isEqualTo(legalHold);
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        LegalHold legalHold = LegalHold.newBuilder()
                .status(ObjectLegalHoldStatusType.OFF.toString())
                .build();

        PutObjectLegalHoldRequest original = PutObjectLegalHoldRequest.newBuilder()
                .bucket("testbucket")
                .key("testobject")
                .versionId("original-version-id")
                .legalHold(legalHold)
                .build();

        PutObjectLegalHoldRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.key()).isEqualTo("testobject");
        assertThat(copy.versionId()).isEqualTo("original-version-id");
        assertThat(copy.legalHold()).isEqualTo(legalHold);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        LegalHold legalHold = LegalHold.newBuilder()
                .status(ObjectLegalHoldStatusType.ON.toString())
                .build();

        PutObjectLegalHoldRequest request = PutObjectLegalHoldRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .legalHold(legalHold)
                .build();

        OperationInput input = SerdeObjectWorm.fromPutObjectLegalHold(request);

        assertThat(input.parameters().get("legalHold")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<LegalHold>");
        assertThat(xmlContent).contains("<Status>ON</Status>");
        assertThat(xmlContent).contains("</LegalHold>");
    }
}
