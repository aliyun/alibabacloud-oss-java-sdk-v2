package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketPublicAccessBlock;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBucketPublicAccessBlockResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteBucketPublicAccessBlockResult result = DeleteBucketPublicAccessBlockResult.newBuilder().build();
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

        DeleteBucketPublicAccessBlockResult result = DeleteBucketPublicAccessBlockResult.newBuilder()
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

        DeleteBucketPublicAccessBlockResult original = DeleteBucketPublicAccessBlockResult.newBuilder()
                .headers(headers)
                .build();

        DeleteBucketPublicAccessBlockResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        DeleteBucketPublicAccessBlockResult result = SerdeBucketPublicAccessBlock.toDeleteBucketPublicAccessBlock(blankOutput);

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
        DeleteBucketPublicAccessBlockResult fullResult = SerdeBucketPublicAccessBlock.toDeleteBucketPublicAccessBlock(fullOutput);

        assertThat(fullResult).isNotNull();
        assertThat(fullResult.headers()).isNotNull();
        assertThat(fullResult.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(fullResult.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
    }
}