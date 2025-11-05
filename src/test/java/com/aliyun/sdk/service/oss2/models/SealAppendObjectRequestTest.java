package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class SealAppendObjectRequestTest {

    @Test
    public void testEmptyBuilder() {
        SealAppendObjectRequest request = SealAppendObjectRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.position()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        SealAppendObjectRequest request = SealAppendObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .position("12345")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject");
        assertThat(request.position()).isEqualTo("12345");

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

        SealAppendObjectRequest original = SealAppendObjectRequest.newBuilder()
                .bucket("testbucket")
                .key("testobject")
                .position("54321")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        SealAppendObjectRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.key()).isEqualTo("testobject");
        assertThat(copy.position()).isEqualTo("54321");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        SealAppendObjectRequest request = SealAppendObjectRequest.newBuilder()
                .bucket("anotherbucket")
                .key("anotherobject")
                .position("98765")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.key()).isEqualTo("anotherobject");
        assertThat(request.position()).isEqualTo("98765");
    }

    @Test
    public void xmlBuilder() {
        SealAppendObjectRequest request = SealAppendObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .position("12345")
                .build();

        OperationInput input = SerdeObjectBasic.fromSealAppendObject(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.key().get()).isEqualTo("exampleobject");
        assertThat(input.parameters().get("seal")).isEqualTo("");
        assertThat(input.parameters().get("position")).isEqualTo("12345");
    }
}
