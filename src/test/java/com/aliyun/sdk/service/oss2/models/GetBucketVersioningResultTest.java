package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketVersioning;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketVersioningResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketVersioningResult result = GetBucketVersioningResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.versioningConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        VersioningConfiguration versioningConfiguration = VersioningConfiguration.newBuilder()
                .status("Enabled")
                .build();

        GetBucketVersioningResult result = GetBucketVersioningResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .innerBody(versioningConfiguration)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.versioningConfiguration()).isNotNull();
        assertThat(result.versioningConfiguration().status()).isEqualTo("Enabled");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        VersioningConfiguration versioningConfiguration = VersioningConfiguration.newBuilder()
                .status("Suspended")
                .build();

        GetBucketVersioningResult original = GetBucketVersioningResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .innerBody(versioningConfiguration)
                .build();

        GetBucketVersioningResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.versioningConfiguration()).isNotNull();
        assertThat(copy.versioningConfiguration().status()).isEqualTo("Suspended");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeBucketVersioning.toGetBucketVersioning(blankOutput);

        String xml =
                "<VersioningConfiguration>\n" +
                        "    <Status>Enabled</Status>\n" +
                        "</VersioningConfiguration>";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-111111111111111111",
                "ETag", "\"xml-builder-etag\""
        );

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetBucketVersioningResult result = SerdeBucketVersioning.toGetBucketVersioning(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
        assertThat(result.versioningConfiguration()).isNotNull();
        assertThat(result.versioningConfiguration().status()).isEqualTo("Enabled");
    }
}
