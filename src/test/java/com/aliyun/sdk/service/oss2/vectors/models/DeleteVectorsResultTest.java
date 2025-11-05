package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteVectorsResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteVectorsResult result = DeleteVectorsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.statusCode()).isEqualTo(0);
        assertThat(result.status()).isEqualTo("");
        assertThat(result.requestId()).isEqualTo("");
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        DeleteVectorsResult result = DeleteVectorsResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        DeleteVectorsResult original = DeleteVectorsResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        DeleteVectorsResult copy = original.toBuilder().build();

        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
    }

    @Test
    public void testHeaderProperties() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "test-request-id",
                "Cache-Control", "no-cache"
        );

        DeleteVectorsResult result = DeleteVectorsResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.requestId()).isEqualTo("test-request-id");
        assertThat(result.headers()).containsEntry("Cache-Control", "no-cache");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "test-request-id",
                "Content-Type", "application/json"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        DeleteVectorsResult result = SerdeVectorsBasic.toDeleteVectors(output);

        assertThat(result.requestId()).isEqualTo("test-request-id");
        assertThat(result.headers()).containsEntry("Content-Type", "application/json");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }
}