package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketRequestPayment;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketRequestPaymentRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketRequestPaymentRequest request = PutBucketRequestPaymentRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        RequestPaymentConfiguration requestPaymentConfiguration = RequestPaymentConfiguration.newBuilder()
                .payer("Requester")
                .build();

        PutBucketRequestPaymentRequest request = PutBucketRequestPaymentRequest.newBuilder()
                .bucket("examplebucket")
                .requestPaymentConfiguration(requestPaymentConfiguration)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .parameter("requestPayment", "")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.requestPaymentConfiguration()).isEqualTo(requestPaymentConfiguration);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value"),
                new AbstractMap.SimpleEntry<>("requestPayment", "")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        PutBucketRequestPaymentRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.requestPaymentConfiguration()).isEqualTo(requestPaymentConfiguration);
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
        RequestPaymentConfiguration requestPaymentConfiguration = RequestPaymentConfiguration.newBuilder()
                .payer("BucketOwner")
                .build();

        PutBucketRequestPaymentRequest original = PutBucketRequestPaymentRequest.newBuilder()
                .bucket("test-bucket")
                .requestPaymentConfiguration(requestPaymentConfiguration)
                .build();

        PutBucketRequestPaymentRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.requestPaymentConfiguration()).isEqualTo(requestPaymentConfiguration);
    }

    @Test
    public void testHeaderProperties() {
        RequestPaymentConfiguration requestPaymentConfiguration = RequestPaymentConfiguration.newBuilder()
                .payer("Requester")
                .build();

        PutBucketRequestPaymentRequest request = PutBucketRequestPaymentRequest.newBuilder()
                .bucket("payment-bucket")
                .requestPaymentConfiguration(requestPaymentConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("payment-bucket");
        assertThat(request.requestPaymentConfiguration().payer()).isEqualTo("Requester");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<RequestPaymentConfiguration>\n" +
                "  <Payer>Requester</Payer>\n" +
                "</RequestPaymentConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestPaymentConfiguration xmlConfiguration = xmlMapper.readValue(xml, RequestPaymentConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        RequestPaymentConfiguration requestPaymentConfiguration = RequestPaymentConfiguration.newBuilder()
                .payer("Requester")
                .build();

        PutBucketRequestPaymentRequest request = PutBucketRequestPaymentRequest.newBuilder()
                .bucket("xml-bucket")
                .requestPaymentConfiguration(requestPaymentConfiguration)
                .build();

        OperationInput input = SerdeBucketRequestPayment.fromPutBucketRequestPayment(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("requestPayment")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<RequestPaymentConfiguration>");
        assertThat(xmlContent).contains("<Payer>Requester</Payer>");
        assertThat(xmlContent).contains("</RequestPaymentConfiguration>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}