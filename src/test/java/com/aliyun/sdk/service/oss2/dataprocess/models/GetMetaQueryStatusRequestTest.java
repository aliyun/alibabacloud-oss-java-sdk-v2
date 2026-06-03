package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class GetMetaQueryStatusRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetMetaQueryStatusRequest request = GetMetaQueryStatusRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        GetMetaQueryStatusRequest request = GetMetaQueryStatusRequest.newBuilder()
                .bucket("examplebucket")
                .header("x-custom-header", "value1")
                .parameter("extra-param", "extra-value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("extra-param", "extra-value"));

        GetMetaQueryStatusRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
    }

    @Test
    public void testToBuilderPreserveState() {
        GetMetaQueryStatusRequest original = GetMetaQueryStatusRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        GetMetaQueryStatusRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §2 GetMetaQueryStatus - no URL params, no body
        GetMetaQueryStatusRequest request = GetMetaQueryStatusRequest.newBuilder()
                .bucket("examplebucket")
                .build();

        OperationInput input = SerdeDatasetBasic.fromGetMetaQueryStatus(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("getMetaQueryStatus");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.body().isPresent()).isFalse();
    }
}
