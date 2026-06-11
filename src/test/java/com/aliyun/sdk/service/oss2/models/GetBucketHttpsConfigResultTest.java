package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketHttpsConfig;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketHttpsConfigResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketHttpsConfigResult result = GetBucketHttpsConfigResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() throws JsonProcessingException {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

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
                "    <CustomCipherSuite>ECDHE-RSA-AES128-GCM-SHA256</CustomCipherSuite>\n" +
                "    <CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "  </CipherSuite>\n" +
                "</HttpsConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        HttpsConfiguration httpsConfiguration = xmlMapper.readValue(xml, HttpsConfiguration.class);

        GetBucketHttpsConfigResult result = GetBucketHttpsConfigResult.newBuilder()
                .headers(headers)
                .innerBody(httpsConfiguration)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.httpsConfiguration()).isNotNull();
        assertThat(result.httpsConfiguration().tls().enable()).isEqualTo(true);
        assertThat(result.httpsConfiguration().tls().tlsVersions()).containsExactly("TLSv1.2", "TLSv1.3");
        assertThat(result.httpsConfiguration().cipherSuite().enable()).isEqualTo(true);
        assertThat(result.httpsConfiguration().cipherSuite().strongCipherSuite()).isEqualTo(false);
        assertThat(result.httpsConfiguration().cipherSuite().customCipherSuites()).containsExactly("ECDHE-ECDSA-AES128-SHA256", "ECDHE-RSA-AES128-GCM-SHA256", "ECDHE-ECDSA-AES256-CCM8");
        assertThat(result.httpsConfiguration().cipherSuite().tls13CustomCipherSuites()).containsExactly("ECDHE-ECDSA-AES256-CCM8", "ECDHE-ECDSA-AES256-CCM8", "ECDHE-ECDSA-AES256-CCM8");
    }

    @Test
    public void testToBuilderPreserveState() throws JsonProcessingException {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

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
                "    <CustomCipherSuite>ECDHE-RSA-AES128-GCM-SHA256</CustomCipherSuite>\n" +
                "    <CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "  </CipherSuite>\n" +
                "</HttpsConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        HttpsConfiguration httpsConfiguration = xmlMapper.readValue(xml, HttpsConfiguration.class);

        GetBucketHttpsConfigResult original = GetBucketHttpsConfigResult.newBuilder()
                .headers(headers)
                .innerBody(httpsConfiguration)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetBucketHttpsConfigResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.httpsConfiguration()).isNotNull();
        assertThat(copy.httpsConfiguration().tls().enable()).isEqualTo(true);
        assertThat(copy.httpsConfiguration().tls().tlsVersions()).containsExactly("TLSv1.2", "TLSv1.3");
        assertThat(copy.httpsConfiguration().cipherSuite().enable()).isEqualTo(true);
        assertThat(copy.httpsConfiguration().cipherSuite().strongCipherSuite()).isEqualTo(false);
        assertThat(copy.httpsConfiguration().cipherSuite().customCipherSuites()).containsExactly("ECDHE-ECDSA-AES128-SHA256", "ECDHE-RSA-AES128-GCM-SHA256", "ECDHE-ECDSA-AES256-CCM8");
        assertThat(copy.httpsConfiguration().cipherSuite().tls13CustomCipherSuites()).containsExactly("ECDHE-ECDSA-AES256-CCM8", "ECDHE-ECDSA-AES256-CCM8", "ECDHE-ECDSA-AES256-CCM8");
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
                "    <CustomCipherSuite>ECDHE-RSA-AES128-GCM-SHA256</CustomCipherSuite>\n" +
                "    <CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "    <TLS13CustomCipherSuite>ECDHE-ECDSA-AES256-CCM8</TLS13CustomCipherSuite>\n" +
                "  </CipherSuite>\n" +
                "</HttpsConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        HttpsConfiguration xmlConfiguration = xmlMapper.readValue(xml, HttpsConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketHttpsConfigResult result = SerdeBucketHttpsConfig.toGetBucketHttpsConfig(output);

        assertThat(result.httpsConfiguration()).isNotNull();
        assertThat(result.httpsConfiguration().tls().enable()).isEqualTo(true);
        assertThat(result.httpsConfiguration().tls().tlsVersions()).containsExactly("TLSv1.2", "TLSv1.3");
        assertThat(result.httpsConfiguration().cipherSuite().enable()).isEqualTo(true);
        assertThat(result.httpsConfiguration().cipherSuite().strongCipherSuite()).isEqualTo(false);
        assertThat(result.httpsConfiguration().cipherSuite().customCipherSuites()).containsExactly("ECDHE-ECDSA-AES128-SHA256", "ECDHE-RSA-AES128-GCM-SHA256", "ECDHE-ECDSA-AES256-CCM8");
        assertThat(result.httpsConfiguration().cipherSuite().tls13CustomCipherSuites()).containsExactly("ECDHE-ECDSA-AES256-CCM8", "ECDHE-ECDSA-AES256-CCM8", "ECDHE-ECDSA-AES256-CCM8");

        String resultXml = xmlMapper.writeValueAsString(result.httpsConfiguration());
        assertThat(resultXml).isEqualTo(expectedXml);
    }
}
