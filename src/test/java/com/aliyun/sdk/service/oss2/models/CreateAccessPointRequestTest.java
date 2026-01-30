package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateAccessPointRequestTest {

    @Test
    public void testEmptyBuilder() {
        CreateAccessPointRequest request = CreateAccessPointRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        CreateAccessPointConfiguration createAccessPointConfiguration = CreateAccessPointConfiguration.newBuilder()
                .accessPointName("ap-01")
                .networkOrigin("vpc")
                .vpcConfiguration(AccessPointVpcConfiguration.newBuilder().vpcId("vpc-t4nlw426y44rd3iq4xxxx").build())
                .build();

        CreateAccessPointRequest request = CreateAccessPointRequest.newBuilder()
                .bucket("examplebucket")
                .createAccessPointConfiguration(createAccessPointConfiguration)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.createAccessPointConfiguration()).isEqualTo(createAccessPointConfiguration);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        CreateAccessPointRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.createAccessPointConfiguration()).isEqualTo(createAccessPointConfiguration);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        CreateAccessPointConfiguration createAccessPointConfiguration = CreateAccessPointConfiguration.newBuilder()
                .accessPointName("ap-01")
                .networkOrigin("internet")
                .vpcConfiguration(AccessPointVpcConfiguration.newBuilder().vpcId("vpc-123456").build())
                .build();

        CreateAccessPointRequest original = CreateAccessPointRequest.newBuilder()
                .bucket("test-bucket")
                .createAccessPointConfiguration(createAccessPointConfiguration)
                .build();

        CreateAccessPointRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.createAccessPointConfiguration()).isEqualTo(createAccessPointConfiguration);
    }

    @Test
    public void testHeaderProperties() {
        CreateAccessPointConfiguration createAccessPointConfiguration = CreateAccessPointConfiguration.newBuilder()
                .accessPointName("ap-01")
                .networkOrigin("vpc")
                .vpcConfiguration(AccessPointVpcConfiguration.newBuilder().vpcId("vpc-t4nlw426y44rd3iq4xxxx").build())
                .build();

        CreateAccessPointRequest request = CreateAccessPointRequest.newBuilder()
                .bucket("access-point-bucket")
                .createAccessPointConfiguration(createAccessPointConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("access-point-bucket");
        assertThat(request.createAccessPointConfiguration().accessPointName()).isEqualTo("ap-01");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<CreateAccessPointConfiguration>\n" +
                "  <AccessPointName>ap-01</AccessPointName>\n" +
                "  <NetworkOrigin>vpc</NetworkOrigin>\n" +
                "  <VpcConfiguration>\n" +
                "    <VpcId>vpc-t4nlw426y44rd3iq4xxxx</VpcId>\n" +
                "  </VpcConfiguration>\n" +
                "</CreateAccessPointConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        CreateAccessPointConfiguration xmlConfiguration = xmlMapper.readValue(xml, CreateAccessPointConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        CreateAccessPointConfiguration createAccessPointConfiguration = CreateAccessPointConfiguration.newBuilder()
                .accessPointName("ap-01")
                .networkOrigin("vpc")
                .vpcConfiguration(AccessPointVpcConfiguration.newBuilder().vpcId("vpc-t4nlw426y44rd3iq4xxxx").build())
                .build();

        CreateAccessPointRequest request = CreateAccessPointRequest.newBuilder()
                .bucket("xml-bucket")
                .createAccessPointConfiguration(createAccessPointConfiguration)
                .build();

        OperationInput input = SerdeAccessPoint.fromCreateAccessPoint(request);

        assertThat(request.bucket()).isEqualTo("xml-bucket");
        assertThat(request.createAccessPointConfiguration().accessPointName()).isEqualTo("ap-01");
        assertThat(request.createAccessPointConfiguration().networkOrigin()).isEqualTo("vpc");
        assertThat(request.createAccessPointConfiguration().vpcConfiguration().vpcId()).isEqualTo("vpc-t4nlw426y44rd3iq4xxxx");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<CreateAccessPointConfiguration>");
        assertThat(xmlContent).contains("<AccessPointName>ap-01</AccessPointName>");
        assertThat(xmlContent).contains("<NetworkOrigin>vpc</NetworkOrigin>");
        assertThat(xmlContent).contains("<VpcId>vpc-t4nlw426y44rd3iq4xxxx</VpcId>");
        assertThat(xmlContent).contains("</CreateAccessPointConfiguration>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}