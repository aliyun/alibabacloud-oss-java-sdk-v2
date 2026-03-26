package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectFcAccessPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteAccessPointPolicyForObjectProcessRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteAccessPointPolicyForObjectProcessRequest request = DeleteAccessPointPolicyForObjectProcessRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteAccessPointPolicyForObjectProcessRequest request = DeleteAccessPointPolicyForObjectProcessRequest.newBuilder()
                .bucket("examplebucket")
                .accessPointForObjectProcessName("fc-ap-01")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.accessPointForObjectProcessName()).isEqualTo("fc-ap-01");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        DeleteAccessPointPolicyForObjectProcessRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.accessPointForObjectProcessName()).isEqualTo("fc-ap-01");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        DeleteAccessPointPolicyForObjectProcessRequest original = DeleteAccessPointPolicyForObjectProcessRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointForObjectProcessName("fc-ap-02")
                .build();

        DeleteAccessPointPolicyForObjectProcessRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.accessPointForObjectProcessName()).isEqualTo("fc-ap-02");
    }

    @Test
    public void testHeaderProperties() {
        DeleteAccessPointPolicyForObjectProcessRequest request = DeleteAccessPointPolicyForObjectProcessRequest.newBuilder()
                .bucket("delete-policy-bucket")
                .accessPointForObjectProcessName("fc-ap-03")
                .build();

        assertThat(request.bucket()).isEqualTo("delete-policy-bucket");
        assertThat(request.accessPointForObjectProcessName()).isEqualTo("fc-ap-03");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        DeleteAccessPointPolicyForObjectProcessRequest request = DeleteAccessPointPolicyForObjectProcessRequest.newBuilder()
                .bucket("xml-bucket")
                .accessPointForObjectProcessName("fc-ap-01")
                .build();

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromDeleteAccessPointPolicyForObjectProcess(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("accessPointPolicyForObjectProcess")).isEqualTo("");
        assertThat(input.headers().get("x-oss-access-point-for-object-process-name")).isEqualTo("fc-ap-01");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

    }
}