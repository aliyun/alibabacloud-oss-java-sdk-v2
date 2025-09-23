package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class SealAppendObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        SealAppendObjectResult result = SealAppendObjectResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "x-oss-sealed-time", "Sun, 05 Sep 2021 23:00:00 GMT"
        );

        SealAppendObjectResult result = SealAppendObjectResult.newBuilder()
                .headers(headers)
                .status("status")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-sealed-time")).isEqualTo("Sun, 05 Sep 2021 23:00:00 GMT");
        assertThat(result.sealedTime()).isEqualTo("Sun, 05 Sep 2021 23:00:00 GMT");
        assertThat(result.requestId()).isEqualTo("requestId");
        assertThat(result.status()).isEqualTo("status");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "x-oss-sealed-time", "Sun, 05 Sep 2021 23:00:00 GMT"
        );

        SealAppendObjectResult original = SealAppendObjectResult.newBuilder()
                .headers(headers)
                .status("originalStatus")
                .statusCode(200)
                .build();

        SealAppendObjectResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("x-oss-sealed-time")).isEqualTo("Sun, 05 Sep 2021 23:00:00 GMT");
        assertThat(copy.sealedTime()).isEqualTo("Sun, 05 Sep 2021 23:00:00 GMT");
        assertThat(copy.requestId()).isEqualTo("originalRequestId");
        assertThat(copy.status()).isEqualTo("originalStatus");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void testHeaderProperties() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-111111111111111111",
                "x-oss-sealed-time", "Sun, 05 Sep 2021 23:00:00 GMT"
        );

        SealAppendObjectResult result = SealAppendObjectResult.newBuilder()
                .headers(headers)
                .status("testStatus")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.headers().get("x-oss-sealed-time")).isEqualTo("Sun, 05 Sep 2021 23:00:00 GMT");
        assertThat(result.sealedTime()).isEqualTo("Sun, 05 Sep 2021 23:00:00 GMT");
        assertThat(result.requestId()).isEqualTo("testRequestId");
        assertThat(result.status()).isEqualTo("testStatus");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-222222222222222222",
                "x-oss-sealed-time", "Sun, 05 Sep 2021 23:00:00 GMT"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("outputStatus")
                .statusCode(200)
                .build();
        SealAppendObjectResult result = SerdeObjectBasic.toSealAppendObject(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-222222222222222222");
        assertThat(result.headers().get("x-oss-sealed-time")).isEqualTo("Sun, 05 Sep 2021 23:00:00 GMT");
        assertThat(result.sealedTime()).isEqualTo("Sun, 05 Sep 2021 23:00:00 GMT");
        assertThat(result.requestId()).isEqualTo("outputRequestId");
        assertThat(result.status()).isEqualTo("outputStatus");
        assertThat(result.statusCode()).isEqualTo(200);
    }
}
