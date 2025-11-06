package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketEncryption;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketEncryptionResultTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketEncryptionResult result = PutBucketEncryptionResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        PutBucketEncryptionResult result = PutBucketEncryptionResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        PutBucketEncryptionResult original = PutBucketEncryptionResult.newBuilder()
                .headers(headers)
                .build();

        PutBucketEncryptionResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-put-encryption-1234567890abcdefg",
                "ETag", "\"PutEncryption-B5eJF1ptWaXm4bijSPyxw==\""
        );

        OperationOutput operationOutput = OperationOutput.newBuilder()
                .headers(headers)
                .statusCode(200)
                .status("OK")
                .build();

        PutBucketEncryptionResult result = SerdeBucketEncryption.toPutBucketEncryption(operationOutput);

        assertThat(result).isNotNull();
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-put-encryption-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"PutEncryption-B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
    }
}