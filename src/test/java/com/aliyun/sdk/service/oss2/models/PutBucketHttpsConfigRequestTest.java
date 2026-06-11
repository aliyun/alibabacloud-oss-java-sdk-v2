package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketHttpsConfig;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketHttpsConfigRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketHttpsConfigRequest request = PutBucketHttpsConfigRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() throws JsonProcessingException {
        String xml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<HttpsConfiguration>\n" +
                "  <TLS>\n" +
                "    <Enable>true</Enable>\n" +
                "    <TLSVersion>TLSv1.2</TLSVersion>\n" +
                "    <TLSVersion>TLSv1.3</TLSVersion>\n" +
                "  </TLS>\n" +
                "  <CipherSuite>\n" +
                "    <Enable>true</Enable>\n" +
                "    <StrongCipherSuite>false</StrongCipherSuite>\n" +
                "    <CustomCipherSuite>ECDHE-ECDSA-AES128-SHA256</CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "  </CipherSuite>\n" +
                "</HttpsConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        HttpsConfiguration httpsConfiguration = xmlMapper.readValue(xml, HttpsConfiguration.class);

        PutBucketHttpsConfigRequest request = PutBucketHttpsConfigRequest.newBuilder()
                .bucket("examplebucket")
                .httpsConfiguration(httpsConfiguration)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.httpsConfiguration()).isEqualTo(httpsConfiguration);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        PutBucketHttpsConfigRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.httpsConfiguration()).isEqualTo(httpsConfiguration);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() throws JsonProcessingException {
        String xml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<HttpsConfiguration>\n" +
                "  <TLS>\n" +
                "    <Enable>true</Enable>\n" +
                "    <TLSVersion>TLSv1.2</TLSVersion>\n" +
                "    <TLSVersion>TLSv1.3</TLSVersion>\n" +
                "  </TLS>\n" +
                "  <CipherSuite>\n" +
                "    <Enable>true</Enable>\n" +
                "    <StrongCipherSuite>false</StrongCipherSuite>\n" +
                "    <CustomCipherSuite>ECDHE-ECDSA-AES128-SHA256</CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "  </CipherSuite>\n" +
                "</HttpsConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        HttpsConfiguration httpsConfiguration = xmlMapper.readValue(xml, HttpsConfiguration.class);

        PutBucketHttpsConfigRequest original = PutBucketHttpsConfigRequest.newBuilder()
                .bucket("test-bucket")
                .httpsConfiguration(httpsConfiguration)
                .build();

        PutBucketHttpsConfigRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.httpsConfiguration()).isEqualTo(httpsConfiguration);
    }

    @Test
    public void testHeaderProperties() throws JsonProcessingException {
        String xml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<HttpsConfiguration>\n" +
                "  <TLS>\n" +
                "    <Enable>true</Enable>\n" +
                "    <TLSVersion>TLSv1.2</TLSVersion>\n" +
                "    <TLSVersion>TLSv1.3</TLSVersion>\n" +
                "  </TLS>\n" +
                "  <CipherSuite>\n" +
                "    <Enable>true</Enable>\n" +
                "    <StrongCipherSuite>false</StrongCipherSuite>\n" +
                "    <CustomCipherSuite>ECDHE-ECDSA-AES128-SHA256</CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "  </CipherSuite>\n" +
                "</HttpsConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        HttpsConfiguration httpsConfiguration = xmlMapper.readValue(xml, HttpsConfiguration.class);

        PutBucketHttpsConfigRequest request = PutBucketHttpsConfigRequest.newBuilder()
                .bucket("https-config-bucket")
                .httpsConfiguration(httpsConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("https-config-bucket");
        assertThat(request.httpsConfiguration()).isEqualTo(httpsConfiguration);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<HttpsConfiguration>\n" +
                "  <TLS>\n" +
                "    <Enable>true</Enable>\n" +
                "    <TLSVersion>TLSv1.2</TLSVersion>\n" +
                "    <TLSVersion>TLSv1.3</TLSVersion>\n" +
                "  </TLS>\n" +
                "  <CipherSuite>\n" +
                "    <Enable>true</Enable>\n" +
                "    <StrongCipherSuite>false</StrongCipherSuite>\n" +
                "    <CustomCipherSuite>ECDHE-ECDSA-AES128-SHA256</CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "  </CipherSuite>\n" +
                "</HttpsConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        HttpsConfiguration xmlConfiguration = xmlMapper.readValue(xml, HttpsConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        HttpsConfiguration httpsConfiguration = xmlMapper.readValue(xml, HttpsConfiguration.class);

        PutBucketHttpsConfigRequest request = PutBucketHttpsConfigRequest.newBuilder()
                .bucket("xml-bucket")
                .httpsConfiguration(httpsConfiguration)
                .build();

        OperationInput input = SerdeBucketHttpsConfig.fromPutBucketHttpsConfig(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("httpsConfig")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<HttpsConfiguration>");
        assertThat(xmlContent).contains("<TLS>");
        assertThat(xmlContent).contains("<Enable>true</Enable>");
        assertThat(xmlContent).contains("<TLSVersion>TLSv1.2</TLSVersion>");
        assertThat(xmlContent).contains("<TLSVersion>TLSv1.3</TLSVersion>");
        assertThat(xmlContent).contains("</TLS>");
        assertThat(xmlContent).contains("<CipherSuite>");
        assertThat(xmlContent).contains("<StrongCipherSuite>false</StrongCipherSuite>");
        assertThat(xmlContent).contains("<CustomCipherSuite>ECDHE-ECDSA-AES128-SHA256</CustomCipherSuite>");
        assertThat(xmlContent).contains("<TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>");
        assertThat(xmlContent).contains("</CipherSuite>");
        assertThat(xmlContent).contains("</HttpsConfiguration>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}
