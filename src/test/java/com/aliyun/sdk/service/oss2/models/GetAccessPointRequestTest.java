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

public class GetAccessPointRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetAccessPointRequest request = GetAccessPointRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        GetAccessPointRequest request = GetAccessPointRequest.newBuilder()
                .bucket("examplebucket")
                .accessPointName("ap-01")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.accessPointName()).isEqualTo("ap-01");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        GetAccessPointRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.accessPointName()).isEqualTo("ap-01");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        GetAccessPointRequest original = GetAccessPointRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointName("test-ap")
                .build();

        GetAccessPointRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.accessPointName()).isEqualTo("test-ap");
    }

    @Test
    public void testHeaderProperties() {
        GetAccessPointRequest request = GetAccessPointRequest.newBuilder()
                .bucket("access-point-bucket")
                .accessPointName("test-ap")
                .build();

        assertThat(request.bucket()).isEqualTo("access-point-bucket");
        assertThat(request.accessPointName()).isEqualTo("test-ap");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        GetAccessPointRequest request = GetAccessPointRequest.newBuilder()
                .bucket("xml-bucket")
                .accessPointName("test-ap")
                .build();

        OperationInput input = SerdeAccessPoint.fromGetAccessPoint(request);

        assertThat(request.bucket()).isEqualTo("xml-bucket");
        assertThat(request.accessPointName()).isEqualTo("test-ap");
        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.headers().get("x-oss-access-point-name")).isEqualTo("test-ap");
    }
}