package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketLogging;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutUserDefinedLogFieldsConfigResultTest {

    @Test
    public void testEmptyBuilder() {
        PutUserDefinedLogFieldsConfigResult result = PutUserDefinedLogFieldsConfigResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.statusCode()).isEqualTo(0);
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        PutUserDefinedLogFieldsConfigResult result = PutUserDefinedLogFieldsConfigResult.newBuilder()
                .headers(headers)
                .statusCode(200)
                .status("OK")
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        PutUserDefinedLogFieldsConfigResult original = PutUserDefinedLogFieldsConfigResult.newBuilder()
                .headers(headers)
                .statusCode(201)
                .status("Created")
                .build();

        PutUserDefinedLogFieldsConfigResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.status()).isEqualTo("Created");
    }

    @Test
    public void testXmlBuilder() {
        PutUserDefinedLogFieldsConfigResult result;

        OperationOutput output = OperationOutput.newBuilder()
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-1234567890abcdefg",
                        "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
                ))
                .status("OK")
                .statusCode(200)
                .body(BinaryData.fromString(""))
                .build();

        result = SerdeBucketLogging.toPutUserDefinedLogFieldsConfig(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
    }
}