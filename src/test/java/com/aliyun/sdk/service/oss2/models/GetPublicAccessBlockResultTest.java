package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdePublicAccessBlock;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetPublicAccessBlockResultTest {

    @Test
    public void testEmptyBuilder() {
        GetPublicAccessBlockResult result = GetPublicAccessBlockResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetPublicAccessBlockResult result = GetPublicAccessBlockResult.newBuilder()
                .headers(headers)
                .innerBody(config)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.publicAccessBlockConfiguration()).isNotNull();
        assertThat(result.publicAccessBlockConfiguration().blockPublicAccess()).isEqualTo(true);
    }

    @Test
    public void testToBuilderPreserveState() {
        PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(false)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetPublicAccessBlockResult original = GetPublicAccessBlockResult.newBuilder()
                .headers(headers)
                .innerBody(config)
                .build();

        GetPublicAccessBlockResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.publicAccessBlockConfiguration()).isNotNull();
        assertThat(copy.publicAccessBlockConfiguration().blockPublicAccess()).isEqualTo(false);
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String xml =
                "<PublicAccessBlockConfiguration>\n" +
                "  <BlockPublicAccess>true</BlockPublicAccess>\n" +
                "</PublicAccessBlockConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetPublicAccessBlockResult result = SerdePublicAccessBlock.toGetPublicAccessBlock(output);

        assertThat(result.publicAccessBlockConfiguration()).isNotNull();
        assertThat(result.publicAccessBlockConfiguration().blockPublicAccess()).isEqualTo(true);
    }
}