package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketEncryption;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBucketEncryptionResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteBucketEncryptionResult result = DeleteBucketEncryptionResult.newBuilder().build();
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

        DeleteBucketEncryptionResult result = DeleteBucketEncryptionResult.newBuilder()
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

        DeleteBucketEncryptionResult original = DeleteBucketEncryptionResult.newBuilder()
                .headers(headers)
                .build();

        DeleteBucketEncryptionResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-delete-encryption-1234567890abcdefg",
                "ETag", "\"DeleteEncryption-B5eJF1ptWaXm4bijSPyxw==\""
        );

        OperationOutput operationOutput = OperationOutput.newBuilder()
                .headers(headers)
                .statusCode(204)
                .status("No Content")
                .build();

        DeleteBucketEncryptionResult result = SerdeBucketEncryption.toDeleteBucketEncryption(operationOutput);

        assertThat(result).isNotNull();
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-delete-encryption-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"DeleteEncryption-B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(204);
        assertThat(result.status()).isEqualTo("No Content");
    }
}