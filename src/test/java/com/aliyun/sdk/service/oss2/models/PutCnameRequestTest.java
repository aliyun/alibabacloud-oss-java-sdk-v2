package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketCname;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutCnameRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutCnameRequest request = PutCnameRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.bucketCnameConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        CertificateConfiguration certificateConfiguration = CertificateConfiguration.newBuilder()
                .certId("493****-cn-hangzhou")
                .certificate("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .privateKey("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .previousCertId("493****-cn-hangzhou")
                .force(true)
                .build();

        Cname cname = Cname.newBuilder()
                .domain("example.com")
                .certificateConfiguration(certificateConfiguration)
                .build();

        BucketCnameConfiguration bucketCnameConfiguration = BucketCnameConfiguration.newBuilder()
                .cname(cname)
                .build();

        PutCnameRequest request = PutCnameRequest.newBuilder()
                .bucket("examplebucket")
                .bucketCnameConfiguration(bucketCnameConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.bucketCnameConfiguration()).isEqualTo(bucketCnameConfiguration);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testFullBuilderWithDeleteCertificate() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        CertificateConfiguration certificateConfiguration = CertificateConfiguration.newBuilder()
                .deleteCertificate(true)
                .certId("493****-cn-hangzhou")
                .certificate("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .privateKey("-----BEGIN PRIVATE KEY----- **** -----END PRIVATE KEY-----")
                .previousCertId("493****-cn-hangzhou")
                .force(true)
                .build();

        Cname cname = Cname.newBuilder()
                .domain("example.com")
                .certificateConfiguration(certificateConfiguration)
                .build();

        BucketCnameConfiguration bucketCnameConfiguration = BucketCnameConfiguration.newBuilder()
                .cname(cname)
                .build();

        PutCnameRequest request = PutCnameRequest.newBuilder()
                .bucket("examplebucket")
                .bucketCnameConfiguration(bucketCnameConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.bucketCnameConfiguration()).isEqualTo(bucketCnameConfiguration);
        assertThat(request.bucketCnameConfiguration().cname().certificateConfiguration().deleteCertificate()).isTrue();

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        CertificateConfiguration certificateConfiguration = CertificateConfiguration.newBuilder()
                .certId("493****-cn-hangzhou")
                .certificate("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .privateKey("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .previousCertId("493****-cn-hangzhou")
                .force(true)
                .build();

        Cname cname = Cname.newBuilder()
                .domain("example.com")
                .certificateConfiguration(certificateConfiguration)
                .build();

        BucketCnameConfiguration bucketCnameConfiguration = BucketCnameConfiguration.newBuilder()
                .cname(cname)
                .build();

        PutCnameRequest original = PutCnameRequest.newBuilder()
                .bucket("testbucket")
                .bucketCnameConfiguration(bucketCnameConfiguration)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutCnameRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.bucketCnameConfiguration()).isEqualTo(bucketCnameConfiguration);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testToBuilderPreserveStateWithDeleteCertificate() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        CertificateConfiguration certificateConfiguration = CertificateConfiguration.newBuilder()
                .deleteCertificate(true)
                .certId("493****-cn-hangzhou")
                .certificate("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .privateKey("-----BEGIN PRIVATE KEY----- **** -----END PRIVATE KEY-----")
                .previousCertId("493****-cn-hangzhou")
                .force(true)
                .build();

        Cname cname = Cname.newBuilder()
                .domain("example.com")
                .certificateConfiguration(certificateConfiguration)
                .build();

        BucketCnameConfiguration bucketCnameConfiguration = BucketCnameConfiguration.newBuilder()
                .cname(cname)
                .build();

        PutCnameRequest original = PutCnameRequest.newBuilder()
                .bucket("testbucket")
                .bucketCnameConfiguration(bucketCnameConfiguration)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutCnameRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.bucketCnameConfiguration()).isEqualTo(bucketCnameConfiguration);
        assertThat(copy.bucketCnameConfiguration().cname().certificateConfiguration().deleteCertificate()).isTrue();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        CertificateConfiguration certificateConfiguration = CertificateConfiguration.newBuilder()
                .certId("493****-cn-hangzhou")
                .certificate("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .privateKey("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .previousCertId("493****-cn-hangzhou")
                .force(true)
                .build();

        Cname cname = Cname.newBuilder()
                .domain("example.com")
                .certificateConfiguration(certificateConfiguration)
                .build();

        BucketCnameConfiguration bucketCnameConfiguration = BucketCnameConfiguration.newBuilder()
                .cname(cname)
                .build();

        PutCnameRequest request = PutCnameRequest.newBuilder()
                .bucket("anotherbucket")
                .bucketCnameConfiguration(bucketCnameConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.bucketCnameConfiguration()).isEqualTo(bucketCnameConfiguration);
    }

    @Test
    public void testHeaderPropertiesWithDeleteCertificate() {
        CertificateConfiguration certificateConfiguration = CertificateConfiguration.newBuilder()
                .deleteCertificate(true)
                .certId("493****-cn-hangzhou")
                .certificate("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .privateKey("-----BEGIN PRIVATE KEY----- **** -----END PRIVATE KEY-----")
                .previousCertId("493****-cn-hangzhou")
                .force(true)
                .build();

        Cname cname = Cname.newBuilder()
                .domain("example.com")
                .certificateConfiguration(certificateConfiguration)
                .build();

        BucketCnameConfiguration bucketCnameConfiguration = BucketCnameConfiguration.newBuilder()
                .cname(cname)
                .build();

        PutCnameRequest request = PutCnameRequest.newBuilder()
                .bucket("anotherbucket")
                .bucketCnameConfiguration(bucketCnameConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.bucketCnameConfiguration()).isEqualTo(bucketCnameConfiguration);
        assertThat(request.bucketCnameConfiguration().cname().certificateConfiguration().deleteCertificate()).isTrue();
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        CertificateConfiguration certificateConfiguration = CertificateConfiguration.newBuilder()
                .certId("493****-cn-hangzhou")
                .certificate("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .privateKey("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .previousCertId("493****-cn-hangzhou")
                .force(true)
                .build();

        Cname cname = Cname.newBuilder()
                .domain("example.com")
                .certificateConfiguration(certificateConfiguration)
                .build();

        BucketCnameConfiguration bucketCnameConfiguration = BucketCnameConfiguration.newBuilder()
                .cname(cname)
                .build();

        PutCnameRequest request = PutCnameRequest.newBuilder()
                .bucket("examplebucket")
                .bucketCnameConfiguration(bucketCnameConfiguration)
                .build();

        OperationInput input = SerdeBucketCname.fromPutCname(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("cname")).isEqualTo("");
        assertThat(input.parameters().get("comp")).isEqualTo("add");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<BucketCnameConfiguration>");
        assertThat(xmlContent).contains("<Cname>");
        assertThat(xmlContent).contains("<Domain>example.com</Domain>");
        assertThat(xmlContent).contains("<CertificateConfiguration>");
        assertThat(xmlContent).contains("<CertId>493****-cn-hangzhou</CertId>");
        assertThat(xmlContent).contains("<PreviousCertId>493****-cn-hangzhou</PreviousCertId>");
        assertThat(xmlContent).contains("<Force>true</Force>");
        assertThat(xmlContent).contains("</CertificateConfiguration>");
        assertThat(xmlContent).contains("</Cname>");
        assertThat(xmlContent).contains("</BucketCnameConfiguration>");
    }

    @Test
    public void xmlBuilderWithDeleteCertificate() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        CertificateConfiguration certificateConfiguration = CertificateConfiguration.newBuilder()
                .deleteCertificate(true)
                .certId("493****-cn-hangzhou")
                .certificate("-----BEGIN CERTIFICATE----- MIIDhDCCAmwCCQCFs8ixARsyrDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMC **** -----END CERTIFICATE-----")
                .privateKey("-----BEGIN PRIVATE KEY----- **** -----END PRIVATE KEY-----")
                .previousCertId("493****-cn-hangzhou")
                .force(true)
                .build();

        Cname cname = Cname.newBuilder()
                .domain("example.com")
                .certificateConfiguration(certificateConfiguration)
                .build();

        BucketCnameConfiguration bucketCnameConfiguration = BucketCnameConfiguration.newBuilder()
                .cname(cname)
                .build();

        PutCnameRequest request = PutCnameRequest.newBuilder()
                .bucket("examplebucket")
                .bucketCnameConfiguration(bucketCnameConfiguration)
                .build();

        OperationInput input = SerdeBucketCname.fromPutCname(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("cname")).isEqualTo("");
        assertThat(input.parameters().get("comp")).isEqualTo("add");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<BucketCnameConfiguration>");
        assertThat(xmlContent).contains("<Cname>");
        assertThat(xmlContent).contains("<Domain>example.com</Domain>");
        assertThat(xmlContent).contains("<CertificateConfiguration>");
        assertThat(xmlContent).contains("<DeleteCertificate>true</DeleteCertificate>");
        assertThat(xmlContent).contains("<CertId>493****-cn-hangzhou</CertId>");
        assertThat(xmlContent).contains("<PreviousCertId>493****-cn-hangzhou</PreviousCertId>");
        assertThat(xmlContent).contains("<Force>true</Force>");
        assertThat(xmlContent).contains("</CertificateConfiguration>");
        assertThat(xmlContent).contains("</Cname>");
        assertThat(xmlContent).contains("</BucketCnameConfiguration>");
    }
}