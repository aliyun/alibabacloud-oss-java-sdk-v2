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

public class DeleteAccessPointPolicyRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteAccessPointPolicyRequest request = DeleteAccessPointPolicyRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteAccessPointPolicyRequest request = DeleteAccessPointPolicyRequest.newBuilder()
                .bucket("examplebucket")
                .accessPointName("apname")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.accessPointName()).isEqualTo("apname");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-oss-access-point-name", "apname"),
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        DeleteAccessPointPolicyRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.accessPointName()).isEqualTo("apname");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-oss-access-point-name", "apname"),
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        DeleteAccessPointPolicyRequest original = DeleteAccessPointPolicyRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointName("apname")
                .build();

        DeleteAccessPointPolicyRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.accessPointName()).isEqualTo("apname");
    }

    @Test
    public void testHeaderProperties() {
        DeleteAccessPointPolicyRequest request = DeleteAccessPointPolicyRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointName("apname")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.accessPointName()).isEqualTo("apname");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        DeleteAccessPointPolicyRequest request = DeleteAccessPointPolicyRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointName("apname")
                .build();

        OperationInput input = SerdeAccessPoint.fromDeleteAccessPointPolicy(request);

        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.parameters().get("accessPointPolicy")).isEqualTo("");
        assertThat(input.headers().get("x-oss-access-point-name")).isEqualTo("apname");

        // Verify that there is no body for DELETE request
        assertThat(input.body().isPresent()).isFalse();
    }
}