package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteAccessPointPolicyResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteAccessPointPolicyResult result = DeleteAccessPointPolicyResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        DeleteAccessPointPolicyResult result = DeleteAccessPointPolicyResult.newBuilder()
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

        DeleteAccessPointPolicyResult original = DeleteAccessPointPolicyResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 204 No Content")
                .statusCode(204)
                .build();

        DeleteAccessPointPolicyResult copy = original.toBuilder().build();

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
        DeleteAccessPointPolicyResult deleteResult = SerdeAccessPoint.toDeleteAccessPointPolicy(blankOutput);
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

        DeleteAccessPointPolicyResult result = SerdeAccessPoint.toDeleteAccessPointPolicy(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 204 No Content");
        assertThat(result.statusCode()).isEqualTo(204);
    }
}