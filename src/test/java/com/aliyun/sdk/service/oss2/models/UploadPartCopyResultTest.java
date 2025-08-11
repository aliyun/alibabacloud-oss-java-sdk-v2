package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectMultipart;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UploadPartCopyResultTest {

    @Test
    public void testEmptyBuilder() {
        UploadPartCopyResult result = UploadPartCopyResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.copySourceVersionId()).isNull();
        assertThat(result.copyPartResult()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "x-oss-copy-source-version-id", "copy-source-version-id-value"
        );

        CopyPartResult copyPartResult = CopyPartResult.newBuilder()
                .lastModified(Instant.parse("2014-07-17T06:27:54.000Z"))
                .eTag("\"5B3C1A2E053D763E1B002CC607C5****\"")
                .build();

        UploadPartCopyResult result = UploadPartCopyResult.newBuilder()
                .headers(headers)
                .innerBody(copyPartResult)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-copy-source-version-id")).isEqualTo("copy-source-version-id-value");

        assertThat(result.copySourceVersionId()).isEqualTo("copy-source-version-id-value");

        CopyPartResult resultCopyPartResult = result.copyPartResult();
        assertThat(resultCopyPartResult.lastModified()).isEqualTo(Instant.parse("2014-07-17T06:27:54.000Z"));
        assertThat(resultCopyPartResult.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5****\"");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "x-oss-copy-source-version-id", "original-copy-source-version-id"
        );

        CopyPartResult copyPartResult = CopyPartResult.newBuilder()
                .lastModified(Instant.parse("2014-07-17T06:27:54.000Z"))
                .eTag("\"5B3C1A2E053D763E1B002CC607C5****\"")
                .build();

        UploadPartCopyResult original = UploadPartCopyResult.newBuilder()
                .headers(headers)
                .innerBody(copyPartResult)
                .build();

        UploadPartCopyResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("x-oss-copy-source-version-id")).isEqualTo("original-copy-source-version-id");

        assertThat(copy.copySourceVersionId()).isEqualTo("original-copy-source-version-id");

        CopyPartResult copyCopyPartResult = copy.copyPartResult();
        assertThat(copyCopyPartResult.lastModified()).isEqualTo(Instant.parse("2014-07-17T06:27:54.000Z"));
        assertThat(copyCopyPartResult.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5****\"");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectMultipart.toUploadPartCopy(blankOutput);


        String xml =
                "<CopyPartResult xmlns=\"http://doc.oss-cn-hangzhou.aliyuncs.com\">\n" +
                        "    <LastModified>2014-07-17T06:27:54.000Z</LastModified>\n" +
                        "    <ETag>\"5B3C1A2E053D763E1B002CC607C5****\"</ETag>\n" +
                        "</CopyPartResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "x-oss-copy-source-version-id", "copy-source-version-id-value"))
                .build();
        UploadPartCopyResult result = SerdeObjectMultipart.toUploadPartCopy(output);

        assertThat(result.copySourceVersionId()).isEqualTo("copy-source-version-id-value");

        CopyPartResult resultCopyPartResult = result.copyPartResult();
        assertThat(resultCopyPartResult.lastModified()).isEqualTo(Instant.parse("2014-07-17T06:27:54.000Z"));
        assertThat(resultCopyPartResult.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5****\"");
    }
}
