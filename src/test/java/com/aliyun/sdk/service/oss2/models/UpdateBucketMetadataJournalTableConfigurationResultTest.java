package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketMetadataConfiguration;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateBucketMetadataJournalTableConfigurationResultTest {

    @Test
    public void testEmptyBuilder() {
        UpdateBucketMetadataJournalTableConfigurationResult result =
                UpdateBucketMetadataJournalTableConfigurationResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"");
        UpdateBucketMetadataJournalTableConfigurationResult result =
                UpdateBucketMetadataJournalTableConfigurationResult.newBuilder()
                        .headers(headers)
                        .status("HTTP/1.1 200 OK")
                        .statusCode(200)
                        .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of("x-oss-request-id", "req-765432109876543210");
        UpdateBucketMetadataJournalTableConfigurationResult original =
                UpdateBucketMetadataJournalTableConfigurationResult.newBuilder()
                        .headers(headers)
                        .status("HTTP/1.1 200 OK")
                        .statusCode(200)
                        .build();
        UpdateBucketMetadataJournalTableConfigurationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = MapUtils.of("x-oss-request-id", "req-1234567890abcdefg");
        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(""))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();
        UpdateBucketMetadataJournalTableConfigurationResult result = SerdeBucketMetadataConfiguration
                .toUpdateBucketMetadataJournalTableConfiguration(output);

        assertThat(result).isNotNull();
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }
}
