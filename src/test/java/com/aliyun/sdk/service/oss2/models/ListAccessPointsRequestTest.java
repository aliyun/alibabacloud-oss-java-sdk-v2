package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ListAccessPointsRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListAccessPointsRequest request = ListAccessPointsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        ListAccessPointsRequest request = ListAccessPointsRequest.newBuilder()
                .maxKeys(10L)
                .continuationToken("abcd")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.maxKeys()).isEqualTo(10L);
        assertThat(request.continuationToken()).isEqualTo("abcd");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        ListAccessPointsRequest copy = request.toBuilder().build();
        assertThat(copy.maxKeys()).isEqualTo(10L);
        assertThat(copy.continuationToken()).isEqualTo("abcd");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        ListAccessPointsRequest original = ListAccessPointsRequest.newBuilder()
                .maxKeys(20L)
                .continuationToken("xyz")
                .build();

        ListAccessPointsRequest copy = original.toBuilder().build();

        assertThat(copy.maxKeys()).isEqualTo(20L);
        assertThat(copy.continuationToken()).isEqualTo("xyz");
    }

    @Test
    public void testParameterProperties() {
        ListAccessPointsRequest request = ListAccessPointsRequest.newBuilder()
                .maxKeys(5L)
                .continuationToken("token123")
                .build();

        assertThat(request.maxKeys()).isEqualTo(5L);
        assertThat(request.continuationToken()).isEqualTo("token123");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ListAccessPointsRequest request = ListAccessPointsRequest.newBuilder()
                .maxKeys(10L)
                .continuationToken("abcd")
                .build();

        OperationInput input = SerdeAccessPoint.fromListAccessPoints(request);

        assertThat(request.maxKeys()).isEqualTo(10L);
        assertThat(request.continuationToken()).isEqualTo("abcd");
        assertThat(input.parameters().get("max-keys")).isEqualTo("10");
        assertThat(input.parameters().get("continuation-token")).isEqualTo("abcd");
    }
}