package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketMetadataConfiguration;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBucketMetadataConfigurationResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteBucketMetadataConfigurationResult result = DeleteBucketMetadataConfigurationResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"");
        DeleteBucketMetadataConfigurationResult result = DeleteBucketMetadataConfigurationResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 204 No Content")
                .statusCode(204)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 204 No Content");
        assertThat(result.statusCode()).isEqualTo(204);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of("x-oss-request-id", "req-765432109876543210");
        DeleteBucketMetadataConfigurationResult original = DeleteBucketMetadataConfigurationResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 204 No Content")
                .statusCode(204)
                .build();
        DeleteBucketMetadataConfigurationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 204 No Content");
        assertThat(copy.statusCode()).isEqualTo(204);
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = MapUtils.of("x-oss-request-id", "req-1234567890abcdefg");
        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(""))
                .headers(headers)
                .status("HTTP/1.1 204 No Content")
                .statusCode(204)
                .build();
        DeleteBucketMetadataConfigurationResult result = SerdeBucketMetadataConfiguration
                .toDeleteBucketMetadataConfiguration(output);

        assertThat(result).isNotNull();
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 204 No Content");
        assertThat(result.statusCode()).isEqualTo(204);
    }
}
