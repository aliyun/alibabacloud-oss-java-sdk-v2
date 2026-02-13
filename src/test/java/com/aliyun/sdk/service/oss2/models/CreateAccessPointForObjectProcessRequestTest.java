package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectFcAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateAccessPointForObjectProcessRequestTest {

    @Test
    public void testEmptyBuilder() {
        CreateAccessPointForObjectProcessRequest request = CreateAccessPointForObjectProcessRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        ObjectProcessAllowedFeatures allowedFeatures = ObjectProcessAllowedFeatures.newBuilder()
                .allowedFeature(Arrays.asList("GetObject-Range"))
                .build();

        AccessPointActions actions = AccessPointActions.newBuilder()
                .action(Arrays.asList("GetObject"))
                .build();

        TransformationConfiguration transformationConfiguration = TransformationConfiguration.newBuilder()
                .actions(actions)
                .contentTransformation(ContentTransformation.newBuilder()
                        .functionCompute(ObjectProcessFunctionCompute.newBuilder()
                                .functionAssumeRoleArn("string")
                                .functionArn("string")
                                .build())
                        .build())
                .build();

        TransformationConfigurations transformationConfigurations = TransformationConfigurations.newBuilder()
                .transformationConfiguration(Arrays.asList(transformationConfiguration))
                .build();

        ObjectProcessConfiguration objectProcessConfiguration = ObjectProcessConfiguration.newBuilder()
                .allowedFeatures(allowedFeatures)
                .transformationConfigurations(transformationConfigurations)
                .build();

        CreateAccessPointForObjectProcessConfiguration configuration = CreateAccessPointForObjectProcessConfiguration.newBuilder()
                .accessPointName("ap-01")
                .objectProcessConfiguration(objectProcessConfiguration)
                .build();

        CreateAccessPointForObjectProcessRequest request = CreateAccessPointForObjectProcessRequest.newBuilder()
                .bucket("examplebucket")
                .accessPointForObjectProcessName("fc-ap-01")
                .createAccessPointForObjectProcessConfiguration(configuration)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.accessPointForObjectProcessName()).isEqualTo("fc-ap-01");
        assertThat(request.createAccessPointForObjectProcessConfiguration()).isEqualTo(configuration);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-oss-access-point-for-object-process-name", "fc-ap-01"),
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        CreateAccessPointForObjectProcessRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.accessPointForObjectProcessName()).isEqualTo("fc-ap-01");
        assertThat(copy.createAccessPointForObjectProcessConfiguration()).isEqualTo(configuration);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-oss-access-point-for-object-process-name", "fc-ap-01"),
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        CreateAccessPointForObjectProcessConfiguration configuration = CreateAccessPointForObjectProcessConfiguration.newBuilder()
                .accessPointName("test-ap")
                .build();

        CreateAccessPointForObjectProcessRequest original = CreateAccessPointForObjectProcessRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointForObjectProcessName("test-fc-ap")
                .createAccessPointForObjectProcessConfiguration(configuration)
                .build();

        CreateAccessPointForObjectProcessRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.accessPointForObjectProcessName()).isEqualTo("test-fc-ap");
        assertThat(copy.createAccessPointForObjectProcessConfiguration()).isEqualTo(configuration);
    }

    @Test
    public void testHeaderProperties() {
        CreateAccessPointForObjectProcessConfiguration configuration = CreateAccessPointForObjectProcessConfiguration.newBuilder()
                .accessPointName("header-ap")
                .build();

        CreateAccessPointForObjectProcessRequest request = CreateAccessPointForObjectProcessRequest.newBuilder()
                .bucket("header-bucket")
                .accessPointForObjectProcessName("header-fc-ap")
                .createAccessPointForObjectProcessConfiguration(configuration)
                .build();

        assertThat(request.bucket()).isEqualTo("header-bucket");
        assertThat(request.accessPointForObjectProcessName()).isEqualTo("header-fc-ap");
        assertThat(request.createAccessPointForObjectProcessConfiguration().accessPointName()).isEqualTo("header-ap");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<CreateAccessPointForObjectProcessConfiguration>\n" +
                "  <AccessPointName>ap-01</AccessPointName>\n" +
                "  <ObjectProcessConfiguration>\n" +
                "    <AllowedFeatures>\n" +
                "      <AllowedFeature>GetObject-Range</AllowedFeature>\n" +
                "    </AllowedFeatures>\n" +
                "    <TransformationConfigurations>\n" +
                "      <TransformationConfiguration>\n" +
                "        <Actions>\n" +
                "          <Action>GetObject</Action>\n" +
                "        </Actions>\n" +
                "        <ContentTransformation>\n" +
                "          <FunctionCompute>\n" +
                "            <FunctionAssumeRoleArn>string</FunctionAssumeRoleArn>\n" +
                "            <FunctionArn>string</FunctionArn>\n" +
                "          </FunctionCompute>\n" +
                "        </ContentTransformation>\n" +
                "      </TransformationConfiguration>\n" +
                "    </TransformationConfigurations>\n" +
                "  </ObjectProcessConfiguration>\n" +
                "</CreateAccessPointForObjectProcessConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        CreateAccessPointForObjectProcessConfiguration xmlConfiguration = xmlMapper.readValue(xml, CreateAccessPointForObjectProcessConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        ObjectProcessAllowedFeatures allowedFeatures = ObjectProcessAllowedFeatures.newBuilder()
                .allowedFeature(Arrays.asList("GetObject-Range"))
                .build();

        AccessPointActions actions = AccessPointActions.newBuilder()
                .action(Arrays.asList("GetObject"))
                .build();

        TransformationConfiguration transformationConfiguration = TransformationConfiguration.newBuilder()
                .actions(actions)
                .contentTransformation(ContentTransformation.newBuilder()
                        .functionCompute(ObjectProcessFunctionCompute.newBuilder()
                                .functionAssumeRoleArn("string")
                                .functionArn("string")
                                .build())
                        .build())
                .build();

        TransformationConfigurations transformationConfigurations = TransformationConfigurations.newBuilder()
                .transformationConfiguration(Arrays.asList(transformationConfiguration))
                .build();

        ObjectProcessConfiguration objectProcessConfiguration = ObjectProcessConfiguration.newBuilder()
                .allowedFeatures(allowedFeatures)
                .transformationConfigurations(transformationConfigurations)
                .build();

        CreateAccessPointForObjectProcessConfiguration configuration = CreateAccessPointForObjectProcessConfiguration.newBuilder()
                .accessPointName("ap-01")
                .objectProcessConfiguration(objectProcessConfiguration)
                .build();

        CreateAccessPointForObjectProcessRequest request = CreateAccessPointForObjectProcessRequest.newBuilder()
                .bucket("xml-bucket")
                .accessPointForObjectProcessName("fc-ap-01")
                .createAccessPointForObjectProcessConfiguration(configuration)
                .build();

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromCreateAccessPointForObjectProcess(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("accessPointForObjectProcess")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.headers().get("x-oss-access-point-for-object-process-name")).isEqualTo("fc-ap-01");

        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}