package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectWorm;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetObjectRetentionResultTest {

    @Test
    public void testEmptyBuilder() {
        GetObjectRetentionResult result = GetObjectRetentionResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.retention()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        Retention retention = Retention.newBuilder()
                .mode(ObjectRetentionModeType.COMPLIANCE.toString())
                .retainUntilDate("2025-12-31T00:00:00.000Z")
                .build();

        GetObjectRetentionResult result = GetObjectRetentionResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .retention(retention)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.retention()).isEqualTo(retention);
        assertThat(result.retention().mode()).isEqualTo(ObjectRetentionModeType.COMPLIANCE.toString());
        assertThat(result.retention().retainUntilDate()).isEqualTo("2025-12-31T00:00:00.000Z");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        Retention retention = Retention.newBuilder()
                .mode(ObjectRetentionModeType.GOVERNANCE.toString())
                .retainUntilDate("2026-06-30T00:00:00.000Z")
                .build();

        GetObjectRetentionResult original = GetObjectRetentionResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .retention(retention)
                .build();

        GetObjectRetentionResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.retention()).isEqualTo(retention);
    }

    @Test
    public void testXmlBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Retention>\n" +
                "  <Mode>COMPLIANCE</Mode>\n" +
                "  <RetainUntilDate>2025-12-31T00:00:00.000Z</RetainUntilDate>\n" +
                "</Retention>";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-111111111111111111"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .body(BinaryData.fromString(xml))
                .build();

        GetObjectRetentionResult result = SerdeObjectWorm.toGetObjectRetention(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
        assertThat(result.retention()).isNotNull();
        assertThat(result.retention().mode()).isEqualTo(ObjectRetentionModeType.COMPLIANCE.toString());
        assertThat(result.retention().retainUntilDate()).isEqualTo("2025-12-31T00:00:00.000Z");
    }
}
