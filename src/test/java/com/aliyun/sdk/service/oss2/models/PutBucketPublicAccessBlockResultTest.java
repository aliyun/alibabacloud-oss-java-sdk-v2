package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketPublicAccessBlock;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketPublicAccessBlockResultTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketPublicAccessBlockResult result = PutBucketPublicAccessBlockResult.newBuilder().build();
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

        PutBucketPublicAccessBlockResult result = PutBucketPublicAccessBlockResult.newBuilder()
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

        PutBucketPublicAccessBlockResult original = PutBucketPublicAccessBlockResult.newBuilder()
                .headers(headers)
                .build();

        PutBucketPublicAccessBlockResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        PutBucketPublicAccessBlockResult result = SerdeBucketPublicAccessBlock.toPutBucketPublicAccessBlock(blankOutput);

        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();

        String emptyXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );
        
        OperationOutput fullOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(emptyXml))
                .headers(headers)
                .build();
        PutBucketPublicAccessBlockResult fullResult = SerdeBucketPublicAccessBlock.toPutBucketPublicAccessBlock(fullOutput);

        assertThat(fullResult).isNotNull();
        assertThat(fullResult.headers()).isNotNull();
        assertThat(fullResult.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(fullResult.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
    }
}