package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketLifecycle;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBucketLifecycleRequestTest {
    @Test
    public void testEmptyBuilder() {
        DeleteBucketLifecycleRequest request = DeleteBucketLifecycleRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        DeleteBucketLifecycleRequest request = DeleteBucketLifecycleRequest.newBuilder()
                .bucket("examplebucket")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        DeleteBucketLifecycleRequest original = DeleteBucketLifecycleRequest.newBuilder()
                .bucket("testbucket")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        DeleteBucketLifecycleRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        DeleteBucketLifecycleRequest request = DeleteBucketLifecycleRequest.newBuilder()
                .bucket("anotherbucket")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
    }

    @Test
    public void xmlBuilder() {
        DeleteBucketLifecycleRequest request = DeleteBucketLifecycleRequest.newBuilder()
                .bucket("examplebucket")
                .build();

        OperationInput input = SerdeBucketLifecycle.fromDeleteBucketLifecycle(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.method()).isEqualTo("DELETE");
        assertThat(input.parameters().get("lifecycle")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
    }
}