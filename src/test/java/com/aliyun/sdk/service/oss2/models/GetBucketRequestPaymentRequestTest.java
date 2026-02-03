package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketRequestPayment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketRequestPaymentRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketRequestPaymentRequest request = GetBucketRequestPaymentRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        GetBucketRequestPaymentRequest request = GetBucketRequestPaymentRequest.newBuilder()
                .bucket("examplebucket")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .parameter("requestPayment", "")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("requestPayment", "")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        GetBucketRequestPaymentRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("requestPayment", "")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        GetBucketRequestPaymentRequest original = GetBucketRequestPaymentRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        GetBucketRequestPaymentRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
    }

    @Test
    public void testHeaderProperties() {
        GetBucketRequestPaymentRequest request = GetBucketRequestPaymentRequest.newBuilder()
                .bucket("payment-bucket")
                .build();

        assertThat(request.bucket()).isEqualTo("payment-bucket");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        GetBucketRequestPaymentRequest request = GetBucketRequestPaymentRequest.newBuilder()
                .bucket("xml-bucket")
                .build();

        OperationInput input = SerdeBucketRequestPayment.fromGetBucketRequestPayment(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("requestPayment")).isEqualTo("");
    }
}