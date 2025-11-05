package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketPublicAccessBlock;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketPublicAccessBlockResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketPublicAccessBlockResult result = GetBucketPublicAccessBlockResult.newBuilder().build();
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

        BucketPublicAccessBlockConfiguration config = BucketPublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        GetBucketPublicAccessBlockResult result = GetBucketPublicAccessBlockResult.newBuilder()
                .headers(headers)
                .innerBody(config)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.publicAccessBlockConfiguration()).isEqualTo(config);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        BucketPublicAccessBlockConfiguration config = BucketPublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(false)
                .build();

        GetBucketPublicAccessBlockResult original = GetBucketPublicAccessBlockResult.newBuilder()
                .headers(headers)
                .innerBody(config)
                .build();

        GetBucketPublicAccessBlockResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.publicAccessBlockConfiguration()).isEqualTo(config);
    }

    @Test
    public void testXmlBuilder() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<PublicAccessBlockConfiguration>\n" +
                "  <BlockPublicAccess>true</BlockPublicAccess>\n" +
                "</PublicAccessBlockConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketPublicAccessBlockResult result = SerdeBucketPublicAccessBlock.toGetBucketPublicAccessBlock(output);

        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.publicAccessBlockConfiguration()).isNotNull();
        assertThat(result.publicAccessBlockConfiguration().blockPublicAccess()).isEqualTo(true);
    }
}