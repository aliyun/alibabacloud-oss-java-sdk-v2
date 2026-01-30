package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketWorm;
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

public class CompleteBucketWormRequestTest {
    @Test
    public void testEmptyBuilder() {
        CompleteBucketWormRequest request = CompleteBucketWormRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.wormId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        CompleteBucketWormRequest request = CompleteBucketWormRequest.newBuilder()
                .bucket("examplebucket")
                .wormId("wormId123")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.wormId()).isEqualTo("wormId123");

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

        CompleteBucketWormRequest original = CompleteBucketWormRequest.newBuilder()
                .bucket("testbucket")
                .wormId("wormId456")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        CompleteBucketWormRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.wormId()).isEqualTo("wormId456");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        CompleteBucketWormRequest request = CompleteBucketWormRequest.newBuilder()
                .bucket("anotherbucket")
                .wormId("wormId789")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.wormId()).isEqualTo("wormId789");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        CompleteBucketWormRequest request = CompleteBucketWormRequest.newBuilder()
                .bucket("examplebucket")
                .wormId("wormId123")
                .build();

        OperationInput input = SerdeBucketWorm.fromCompleteBucketWorm(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("wormId")).isEqualTo("wormId123");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content is empty since request_body is false
        if (input.body().isPresent()) {
            BinaryData body = input.body().get();
            String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
            assertThat(xmlContent).isEmpty();
        }
    }
}