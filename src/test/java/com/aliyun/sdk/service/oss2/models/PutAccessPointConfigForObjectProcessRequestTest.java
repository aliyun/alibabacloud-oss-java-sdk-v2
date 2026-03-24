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
import static org.assertj.core.api.Assertions.assertThat;

public class PutAccessPointConfigForObjectProcessRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutAccessPointConfigForObjectProcessRequest request = PutAccessPointConfigForObjectProcessRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        ObjectProcessConfiguration objectProcessConfiguration = ObjectProcessConfiguration.newBuilder()
                .allowedFeatures(ObjectProcessAllowedFeatures.newBuilder()
                        .allowedFeature(java.util.Arrays.asList("GetObject-Range"))
                        .build())
                .transformationConfigurations(TransformationConfigurations.newBuilder()
                        .transformationConfiguration(java.util.Arrays.asList(
                                TransformationConfiguration.newBuilder()
                                        .actions(AccessPointActions.newBuilder()
                                                .action(java.util.Arrays.asList("GetObject"))
                                                .build())
                                        .contentTransformation(ContentTransformation.newBuilder()
                                                .functionCompute(ObjectProcessFunctionCompute.newBuilder()
                                                        .functionAssumeRoleArn("acs:ram::111933544165****:role/aliyunossobjectfcforossdefaultrole")
                                                        .functionArn("acs:oss:cn-qingdao:111933544165****:services/oss-fc.LATEST/functions/oss-fc-02")
                                                        .build())
                                                .build())
                                        .build()))
                        .build())
                .build();

        PublicAccessBlockConfiguration publicAccessBlockConfiguration = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutAccessPointConfigForObjectProcessConfiguration config = PutAccessPointConfigForObjectProcessConfiguration.newBuilder()
                .objectProcessConfiguration(objectProcessConfiguration)
                .publicAccessBlockConfiguration(publicAccessBlockConfiguration)
                .build();

        PutAccessPointConfigForObjectProcessRequest request = PutAccessPointConfigForObjectProcessRequest.newBuilder()
                .bucket("examplebucket")
                .accessPointForObjectProcessName("fc-ap-01")
                .putAccessPointConfigForObjectProcessConfiguration(config)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.accessPointForObjectProcessName()).isEqualTo("fc-ap-01");
        assertThat(request.putAccessPointConfigForObjectProcessConfiguration()).isEqualTo(config);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        PutAccessPointConfigForObjectProcessRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.accessPointForObjectProcessName()).isEqualTo("fc-ap-01");
        assertThat(copy.putAccessPointConfigForObjectProcessConfiguration()).isEqualTo(config);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        ObjectProcessConfiguration objectProcessConfiguration = ObjectProcessConfiguration.newBuilder()
                .allowedFeatures(ObjectProcessAllowedFeatures.newBuilder().build())
                .build();

        PublicAccessBlockConfiguration publicAccessBlockConfiguration = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(false)
                .build();

        PutAccessPointConfigForObjectProcessConfiguration config = PutAccessPointConfigForObjectProcessConfiguration.newBuilder()
                .objectProcessConfiguration(objectProcessConfiguration)
                .publicAccessBlockConfiguration(publicAccessBlockConfiguration)
                .build();

        PutAccessPointConfigForObjectProcessRequest original = PutAccessPointConfigForObjectProcessRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointForObjectProcessName("fc-ap-02")
                .putAccessPointConfigForObjectProcessConfiguration(config)
                .build();

        PutAccessPointConfigForObjectProcessRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.accessPointForObjectProcessName()).isEqualTo("fc-ap-02");
        assertThat(copy.putAccessPointConfigForObjectProcessConfiguration()).isEqualTo(config);
    }

    @Test
    public void testHeaderProperties() {
        ObjectProcessConfiguration objectProcessConfiguration = ObjectProcessConfiguration.newBuilder()
                .allowedFeatures(ObjectProcessAllowedFeatures.newBuilder().build())
                .build();

        PublicAccessBlockConfiguration publicAccessBlockConfiguration = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutAccessPointConfigForObjectProcessConfiguration config = PutAccessPointConfigForObjectProcessConfiguration.newBuilder()
                .objectProcessConfiguration(objectProcessConfiguration)
                .publicAccessBlockConfiguration(publicAccessBlockConfiguration)
                .build();

        PutAccessPointConfigForObjectProcessRequest request = PutAccessPointConfigForObjectProcessRequest.newBuilder()
                .bucket("config-bucket")
                .accessPointForObjectProcessName("fc-ap-03")
                .putAccessPointConfigForObjectProcessConfiguration(config)
                .build();

        assertThat(request.bucket()).isEqualTo("config-bucket");
        assertThat(request.accessPointForObjectProcessName()).isEqualTo("fc-ap-03");
        assertThat(request.putAccessPointConfigForObjectProcessConfiguration()).isEqualTo(config);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<PutAccessPointConfigForObjectProcessConfiguration>\n" +
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
                "            <FunctionAssumeRoleArn>acs:ram::111933544165****:role/aliyunossobjectfcforossdefaultrole</FunctionAssumeRoleArn>\n" +
                "            <FunctionArn>acs:oss:cn-qingdao:111933544165****:services/oss-fc.LATEST/functions/oss-fc-02</FunctionArn>\n" +
                "          </FunctionCompute>\n" +
                "        </ContentTransformation>\n" +
                "      </TransformationConfiguration>\n" +
                "    </TransformationConfigurations>\n" +
                "  </ObjectProcessConfiguration>\n" +
                "  <PublicAccessBlockConfiguration>\n" +
                "    <BlockPublicAccess>true</BlockPublicAccess>\n" +
                "  </PublicAccessBlockConfiguration>\n" +
                "</PutAccessPointConfigForObjectProcessConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        PutAccessPointConfigForObjectProcessConfiguration xmlConfiguration = xmlMapper.readValue(xml, PutAccessPointConfigForObjectProcessConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

        ObjectProcessConfiguration objectProcessConfiguration = ObjectProcessConfiguration.newBuilder()
                .allowedFeatures(ObjectProcessAllowedFeatures.newBuilder()
                        .allowedFeature(java.util.Arrays.asList("GetObject-Range"))
                        .build())
                .transformationConfigurations(TransformationConfigurations.newBuilder()
                        .transformationConfiguration(java.util.Arrays.asList(
                                TransformationConfiguration.newBuilder()
                                        .actions(AccessPointActions.newBuilder()
                                                .action(java.util.Arrays.asList("GetObject"))
                                                .build())
                                        .contentTransformation(ContentTransformation.newBuilder()
                                                .functionCompute(ObjectProcessFunctionCompute.newBuilder()
                                                        .functionAssumeRoleArn("acs:ram::111933544165****:role/aliyunossobjectfcforossdefaultrole")
                                                        .functionArn("acs:oss:cn-qingdao:111933544165****:services/oss-fc.LATEST/functions/oss-fc-02")
                                                        .build())
                                                .build())
                                        .build()))
                        .build())
                .build();

        PublicAccessBlockConfiguration publicAccessBlockConfiguration = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutAccessPointConfigForObjectProcessConfiguration config = PutAccessPointConfigForObjectProcessConfiguration.newBuilder()
                .objectProcessConfiguration(objectProcessConfiguration)
                .publicAccessBlockConfiguration(publicAccessBlockConfiguration)
                .build();

        PutAccessPointConfigForObjectProcessRequest request = PutAccessPointConfigForObjectProcessRequest.newBuilder()
                .bucket("xml-bucket")
                .accessPointForObjectProcessName("fc-ap-01")
                .putAccessPointConfigForObjectProcessConfiguration(config)
                .build();

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromPutAccessPointConfigForObjectProcess(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("accessPointConfigForObjectProcess")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.headers().get("x-oss-access-point-for-object-process-name")).isEqualTo("fc-ap-01");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}