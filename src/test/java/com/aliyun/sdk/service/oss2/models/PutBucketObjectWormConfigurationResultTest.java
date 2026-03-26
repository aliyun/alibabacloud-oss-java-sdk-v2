package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectWormConfiguration;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketObjectWormConfigurationResultTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketObjectWormConfigurationResult result = PutBucketObjectWormConfigurationResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        PutBucketObjectWormConfigurationResult result = PutBucketObjectWormConfigurationResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        PutBucketObjectWormConfigurationResult original = PutBucketObjectWormConfigurationResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        PutBucketObjectWormConfigurationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeBucketObjectWormConfiguration.toPutBucketObjectWormConfiguration(blankOutput);

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-111111111111111111"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .body(null)
                .build();

        PutBucketObjectWormConfigurationResult result = SerdeBucketObjectWormConfiguration.toPutBucketObjectWormConfiguration(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
    }
}
