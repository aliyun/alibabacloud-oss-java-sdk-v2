package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketPublicAccessBlock;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketPublicAccessBlockRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutBucketPublicAccessBlockRequest request = PutBucketPublicAccessBlockRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.publicAccessBlockConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutBucketPublicAccessBlockRequest request = PutBucketPublicAccessBlockRequest.newBuilder()
                .bucket("examplebucket")
                .publicAccessBlockConfiguration(config)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.publicAccessBlockConfiguration()).isEqualTo(config);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(false)
                .build();

        PutBucketPublicAccessBlockRequest original = PutBucketPublicAccessBlockRequest.newBuilder()
                .bucket("testbucket")
                .publicAccessBlockConfiguration(config)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutBucketPublicAccessBlockRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.publicAccessBlockConfiguration()).isEqualTo(config);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutBucketPublicAccessBlockRequest request = PutBucketPublicAccessBlockRequest.newBuilder()
                .bucket("anotherbucket")
                .publicAccessBlockConfiguration(config)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.publicAccessBlockConfiguration()).isEqualTo(config);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutBucketPublicAccessBlockRequest request = PutBucketPublicAccessBlockRequest.newBuilder()
                .bucket("examplebucket")
                .publicAccessBlockConfiguration(config)
                .build();

        OperationInput input = SerdeBucketPublicAccessBlock.fromPutBucketPublicAccessBlock(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("publicAccessBlock")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<PublicAccessBlockConfiguration>");
        assertThat(xmlContent).contains("<BlockPublicAccess>true</BlockPublicAccess>");
        assertThat(xmlContent).contains("</PublicAccessBlockConfiguration>");
    }
}