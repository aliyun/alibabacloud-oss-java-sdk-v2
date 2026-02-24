package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectFcAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetAccessPointConfigForObjectProcessResultTest {

    @Test
    public void testEmptyBuilder() {
        GetAccessPointConfigForObjectProcessResult result = GetAccessPointConfigForObjectProcessResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetAccessPointConfigForObjectProcessResult result = GetAccessPointConfigForObjectProcessResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetAccessPointConfigForObjectProcessResult original = GetAccessPointConfigForObjectProcessResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetAccessPointConfigForObjectProcessResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<GetAccessPointConfigForObjectProcessResult>\n" +
                "  <ObjectProcessConfiguration>\n" +
                "    <AllowedFeatures/>\n" +
                "    <TransformationConfigurations>\n" +
                "      <TransformationConfiguration>\n" +
                "        <Actions>\n" +
                "          <Action>getobject</Action>\n" +
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
                "  </PublicAccessBlockConfiguration> \n" +
                "</GetAccessPointConfigForObjectProcessResult>";
        
        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetAccessPointConfigForObjectProcessResult result = SerdeBucketObjectFcAccessPoint.toGetAccessPointConfigForObjectProcess(output);

        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.accessPointConfigForObjectProcessResult()).isNotNull();
        
        GetAccessPointConfigForObjectProcessResultXml configResult = result.accessPointConfigForObjectProcessResult();
        
        // Validate object process configuration
        assertThat(configResult.objectProcessConfiguration()).isNotNull();
        ObjectProcessConfiguration opc = configResult.objectProcessConfiguration();
        
        // Validate allowed features
        assertThat(opc.allowedFeatures()).isNotNull();
        
        // Validate transformation configurations
        assertThat(opc.transformationConfigurations()).isNotNull();
        TransformationConfigurations tcs = opc.transformationConfigurations();
        assertThat(tcs.transformationConfiguration()).isNotNull();
        assertThat(tcs.transformationConfiguration()).hasSize(1);
        
        TransformationConfiguration tc = tcs.transformationConfiguration().get(0);
        
        // Validate actions configuration
        assertThat(tc.actions()).isNotNull();
        AccessPointActions actions = tc.actions();
        assertThat(actions.action()).isNotNull();
        assertThat(actions.action()).hasSize(1);
        assertThat(actions.action().get(0)).isEqualTo("getobject");
        
        // Validate content transformation configuration
        assertThat(tc.contentTransformation()).isNotNull();
        ContentTransformation ct = tc.contentTransformation();
        assertThat(ct.functionCompute()).isNotNull();
        ObjectProcessFunctionCompute fc = ct.functionCompute();
        assertThat(fc.functionAssumeRoleArn()).isEqualTo("acs:ram::111933544165****:role/aliyunossobjectfcforossdefaultrole");
        assertThat(fc.functionArn()).isEqualTo("acs:oss:cn-qingdao:111933544165****:services/oss-fc.LATEST/functions/oss-fc-02");
        
        // Validate public access block configuration
        assertThat(configResult.publicAccessBlockConfiguration()).isNotNull();
        PublicAccessBlockConfiguration pabc = configResult.publicAccessBlockConfiguration();
        assertThat(pabc.blockPublicAccess()).isEqualTo(true);
    }
}