package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutAccessPointPolicyResultTest {

    @Test
    public void testEmptyBuilder() {
        PutAccessPointPolicyResult result = PutAccessPointPolicyResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        PutAccessPointPolicyResult result = PutAccessPointPolicyResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        PutAccessPointPolicyResult original = PutAccessPointPolicyResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        PutAccessPointPolicyResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        String blankXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        PutAccessPointPolicyResult putResult = SerdeAccessPoint.toPutAccessPointPolicy(blankOutput);
        assertThat(putResult).isNotNull();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        PutAccessPointPolicyResult result = SerdeAccessPoint.toPutAccessPointPolicy(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }
}