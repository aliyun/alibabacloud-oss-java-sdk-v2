package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectWorm;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetObjectLegalHoldResultTest {

    @Test
    public void testEmptyBuilder() {
        GetObjectLegalHoldResult result = GetObjectLegalHoldResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.legalHold()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        LegalHold legalHold = LegalHold.newBuilder()
                .status(ObjectLegalHoldStatusType.ON.toString())
                .build();

        GetObjectLegalHoldResult result = GetObjectLegalHoldResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .legalHold(legalHold)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.legalHold()).isEqualTo(legalHold);
        assertThat(result.legalHold().status()).isEqualTo(ObjectLegalHoldStatusType.ON.toString());
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        LegalHold legalHold = LegalHold.newBuilder()
                .status(ObjectLegalHoldStatusType.OFF.toString())
                .build();

        GetObjectLegalHoldResult original = GetObjectLegalHoldResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .legalHold(legalHold)
                .build();

        GetObjectLegalHoldResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.legalHold()).isEqualTo(legalHold);
    }

    @Test
    public void testXmlBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<LegalHold>\n" +
                "  <Status>ON</Status>\n" +
                "</LegalHold>";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-111111111111111111"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .body(BinaryData.fromString(xml))
                .build();

        GetObjectLegalHoldResult result = SerdeObjectWorm.toGetObjectLegalHold(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
        assertThat(result.legalHold()).isNotNull();
        assertThat(result.legalHold().status()).isEqualTo(ObjectLegalHoldStatusType.ON.toString());
    }
}
