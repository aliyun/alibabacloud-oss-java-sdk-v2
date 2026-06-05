package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketStyle;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteStyleRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteStyleRequest request = DeleteStyleRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteStyleRequest request = DeleteStyleRequest.newBuilder()
                .bucket("examplebucket")
                .styleName("imagestyle")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.styleName()).isEqualTo("imagestyle");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("styleName", "imagestyle")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        DeleteStyleRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.styleName()).isEqualTo("imagestyle");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("styleName", "imagestyle")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        DeleteStyleRequest original = DeleteStyleRequest.newBuilder()
                .bucket("test-bucket")
                .styleName("test-style")
                .build();

        DeleteStyleRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.styleName()).isEqualTo("test-style");
    }

    @Test
    public void testHeaderProperties() {
        DeleteStyleRequest request = DeleteStyleRequest.newBuilder()
                .bucket("style-bucket")
                .styleName("test-style")
                .build();

        assertThat(request.bucket()).isEqualTo("style-bucket");
        assertThat(request.styleName()).isEqualTo("test-style");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        DeleteStyleRequest request = DeleteStyleRequest.newBuilder()
                .bucket("xml-bucket")
                .styleName("xml-style")
                .build();

        OperationInput input = SerdeBucketStyle.fromDeleteStyle(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("style")).isEqualTo("");
        assertThat(input.parameters().get("styleName")).isEqualTo("xml-style");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

    }
}