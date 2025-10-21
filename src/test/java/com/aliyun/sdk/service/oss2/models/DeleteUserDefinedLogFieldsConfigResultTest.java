package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketLogging;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteUserDefinedLogFieldsConfigResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteUserDefinedLogFieldsConfigResult result = DeleteUserDefinedLogFieldsConfigResult.newBuilder().build();
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

        DeleteUserDefinedLogFieldsConfigResult result = DeleteUserDefinedLogFieldsConfigResult.newBuilder()
                .headers(headers)
                .statusCode(204)
                .status("No Content")
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(204);
        assertThat(result.status()).isEqualTo("No Content");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        DeleteUserDefinedLogFieldsConfigResult original = DeleteUserDefinedLogFieldsConfigResult.newBuilder()
                .headers(headers)
                .statusCode(204)
                .status("No Content")
                .build();

        DeleteUserDefinedLogFieldsConfigResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.statusCode()).isEqualTo(204);
        assertThat(copy.status()).isEqualTo("No Content");
    }

    @Test
    public void xmlBuilder() {
        String xml = "";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        DeleteUserDefinedLogFieldsConfigResult result = SerdeBucketLogging.toDeleteUserDefinedLogFieldsConfig(output);

        assertThat(result).isNotNull();
    }
}