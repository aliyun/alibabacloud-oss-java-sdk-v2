package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteAccessPointResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteAccessPointResult result = DeleteAccessPointResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        DeleteAccessPointResult result = DeleteAccessPointResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 204 No Content")
                .statusCode(204)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 204 No Content");
        assertThat(result.statusCode()).isEqualTo(204);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        DeleteAccessPointResult original = DeleteAccessPointResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 204 No Content")
                .statusCode(204)
                .build();

        DeleteAccessPointResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 204 No Content");
        assertThat(copy.statusCode()).isEqualTo(204);
    }

    @Test
    public void xmlBuilder() {
        String blankXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        DeleteAccessPointResult deleteResult = SerdeAccessPoint.toDeleteAccessPoint(blankOutput);
        assertThat(deleteResult).isNotNull();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .headers(headers)
                .status("HTTP/1.1 204 No Content")
                .statusCode(204)
                .build();

        DeleteAccessPointResult result = SerdeAccessPoint.toDeleteAccessPoint(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 204 No Content");
        assertThat(result.statusCode()).isEqualTo(204);
    }
}