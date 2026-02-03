package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketRequestPayment;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketRequestPaymentResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketRequestPaymentResult result = GetBucketRequestPaymentResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        RequestPaymentConfiguration requestPaymentConfiguration = RequestPaymentConfiguration.newBuilder()
                .payer("BucketOwner")
                .build();
                
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetBucketRequestPaymentResult result = GetBucketRequestPaymentResult.newBuilder()
                .innerBody(requestPaymentConfiguration)
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.requestPaymentConfiguration().payer()).isEqualTo("BucketOwner");
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        RequestPaymentConfiguration requestPaymentConfiguration = RequestPaymentConfiguration.newBuilder()
                .payer("Requester")
                .build();
                
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetBucketRequestPaymentResult original = GetBucketRequestPaymentResult.newBuilder()
                .innerBody(requestPaymentConfiguration)
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetBucketRequestPaymentResult copy = original.toBuilder().build();

        assertThat(copy.requestPaymentConfiguration().payer()).isEqualTo("Requester");
        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        String xml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<RequestPaymentConfiguration>\n" +
                "  <Payer>BucketOwner</Payer>\n" +
                "</RequestPaymentConfiguration>";
        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketRequestPaymentResult getResult = SerdeBucketRequestPayment.toGetBucketRequestPayment(output);
        assertThat(getResult).isNotNull();
        assertThat(getResult.requestPaymentConfiguration().payer()).isEqualTo("BucketOwner");

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        OperationOutput fullOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetBucketRequestPaymentResult result = SerdeBucketRequestPayment.toGetBucketRequestPayment(fullOutput);

        assertThat(result.requestPaymentConfiguration().payer()).isEqualTo("BucketOwner");
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }
}