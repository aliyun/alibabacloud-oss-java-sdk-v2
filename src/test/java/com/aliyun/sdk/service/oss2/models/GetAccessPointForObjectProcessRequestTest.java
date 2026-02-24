package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectFcAccessPoint;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class GetAccessPointForObjectProcessRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetAccessPointForObjectProcessRequest request = GetAccessPointForObjectProcessRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        GetAccessPointForObjectProcessRequest request = GetAccessPointForObjectProcessRequest.newBuilder()
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

        // to builder
        GetAccessPointForObjectProcessRequest copy = request.toBuilder().build();
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
        GetAccessPointForObjectProcessRequest original = GetAccessPointForObjectProcessRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointForObjectProcessName("fc-ap-02")
                .build();

        GetAccessPointForObjectProcessRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.accessPointForObjectProcessName()).isEqualTo("fc-ap-02");
    }

    @Test
    public void testHeaderProperties() {
        GetAccessPointForObjectProcessRequest request = GetAccessPointForObjectProcessRequest.newBuilder()
                .bucket("accesspoint-bucket")
                .accessPointForObjectProcessName("fc-ap-03")
                .build();

        assertThat(request.bucket()).isEqualTo("accesspoint-bucket");
        assertThat(request.accessPointForObjectProcessName()).isEqualTo("fc-ap-03");
    }

    @Test
    public void xmlBuilder() {
        GetAccessPointForObjectProcessRequest request = GetAccessPointForObjectProcessRequest.newBuilder()
                .bucket("xml-bucket")
                .accessPointForObjectProcessName("fc-ap-01")
                .build();

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromGetAccessPointForObjectProcess(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("accessPointForObjectProcess")).isEqualTo("");
        assertThat(input.headers().get("x-oss-access-point-for-object-process-name")).isEqualTo("fc-ap-01");
    }
}