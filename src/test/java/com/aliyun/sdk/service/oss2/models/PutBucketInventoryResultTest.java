package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketInventory;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketInventoryResultTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketInventoryResult result = PutBucketInventoryResult.newBuilder().build();
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

        PutBucketInventoryResult result = PutBucketInventoryResult.newBuilder()
                .headers(headers)
                .statusCode(200)
                .status("HTTP/1.1 200 OK")
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        PutBucketInventoryResult original = PutBucketInventoryResult.newBuilder()
                .headers(headers)
                .statusCode(200)
                .status("HTTP/1.1 200 OK")
                .build();

        PutBucketInventoryResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void testHeaderProperties() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-test-header-props",
                "Date", "Mon, 07 Jan 2026 12:00:00 GMT"
        );

        PutBucketInventoryResult result = PutBucketInventoryResult.newBuilder()
                .headers(headers)
                .statusCode(200)
                .status("HTTP/1.1 200 OK")
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-test-header-props");
        assertThat(result.headers().get("Date")).isEqualTo("Mon, 07 Jan 2026 12:00:00 GMT");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-test-header-props");
    }

    @Test
    public void xmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeBucketInventory.toPutBucketInventory(blankOutput);

        String xml = "";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-xml-builder-test",
                        "ETag", "\"xml-builder-etag\""
                ))
                .statusCode(200)
                .status("HTTP/1.1 200 OK")
                .build();
        PutBucketInventoryResult result = SerdeBucketInventory.toPutBucketInventory(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}