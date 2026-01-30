package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketMetaquery;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class CloseMetaQueryRequestTest {

    @Test
    public void testEmptyBuilder() {
        CloseMetaQueryRequest request = CloseMetaQueryRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        CloseMetaQueryRequest request = CloseMetaQueryRequest.newBuilder()
                .bucket("examplebucket")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        CloseMetaQueryRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        CloseMetaQueryRequest original = CloseMetaQueryRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        CloseMetaQueryRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
    }

    @Test
    public void testHeaderProperties() {
        CloseMetaQueryRequest request = CloseMetaQueryRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
    }

    @Test
    public void xmlBuilder() {
        CloseMetaQueryRequest request = CloseMetaQueryRequest.newBuilder()
                .bucket("xml-bucket")
                .build();

        OperationInput input = SerdeBucketMetaquery.fromCloseMetaQuery(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("comp")).isEqualTo("delete");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content is empty since request_body = false
        if (input.body().isPresent()) {
            BinaryData body = input.body().get();
            String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
            assertThat(xmlContent).isEmpty();
        }
    }
}