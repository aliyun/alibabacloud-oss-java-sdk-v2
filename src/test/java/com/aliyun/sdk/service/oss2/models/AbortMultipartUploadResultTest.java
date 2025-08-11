package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectMultipart;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AbortMultipartUploadResultTest {

    @Test
    public void testEmptyBuilder() {
        AbortMultipartUploadResult result = AbortMultipartUploadResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg"
        );

        AbortMultipartUploadResult result = AbortMultipartUploadResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210"
        );

        AbortMultipartUploadResult original = AbortMultipartUploadResult.newBuilder()
                .headers(headers)
                .build();

        AbortMultipartUploadResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";

        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectMultipart.toAbortMultipartUpload(blankOutput);


        String xml = "<Error>Not implemented</Error>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "x-request-id", "req-1234567890abcdefg"))
                .build();
        AbortMultipartUploadResult result = SerdeObjectMultipart.toAbortMultipartUpload(output);

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
    }
}
