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
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutAccessPointPolicyRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutAccessPointPolicyRequest request = PutAccessPointPolicyRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        String policyJson = "{\n" +
                "   \"Version\":\"1\",\n" +
                "   \"Statement\":[\n" +
                "   {\n" +
                "     \"Action\":[\n" +
                "       \"oss:PutObject\",\n" +
                "       \"oss:GetObject\"\n" +
                "    ],\n" +
                "    \"Effect\":\"Deny\",\n" +
                "    \"Principal\":[\"27737962156157xxxx\"],\n" +
                "    \"Resource\":[\n" +
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/ap-01\",\n" +
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/ap-01/object/*\"\n" +
                "     ]\n" +
                "   }\n" +
                "  ]\n" +
                " }";

        PutAccessPointPolicyRequest request = PutAccessPointPolicyRequest.newBuilder()
                .bucket("examplebucket")
                .accessPointName("apname")
                .body(BinaryData.fromString(policyJson))
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.accessPointName()).isEqualTo("apname");
        assertThat(request.body()).isNotNull();
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-oss-access-point-name", "apname"),
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        // to builder
        PutAccessPointPolicyRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.accessPointName()).isEqualTo("apname");
        assertThat(copy.body()).isNotNull();
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-oss-access-point-name", "apname"),
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        String policyJson = "{\n" +
                "   \"Version\":\"1\",\n" +
                "   \"Statement\":[\n" +
                "   {\n" +
                "     \"Action\":[\n" +
                "       \"oss:PutObject\",\n" +
                "       \"oss:GetObject\"\n" +
                "    ],\n" +
                "    \"Effect\":\"Deny\",\n" +
                "    \"Principal\":[\"27737962156157xxxx\"],\n" +
                "    \"Resource\":[\n" +
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/ap-01\",\n" +
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/ap-01/object/*\"\n" +
                "     ]\n" +
                "   }\n" +
                "  ]\n" +
                " }";

        PutAccessPointPolicyRequest original = PutAccessPointPolicyRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointName("apname")
                .body(BinaryData.fromString(policyJson))
                .build();

        PutAccessPointPolicyRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.accessPointName()).isEqualTo("apname");
        assertThat(copy.body()).isNotNull();
    }

    @Test
    public void testHeaderProperties() {
        String policyJson = "{\n" +
                "   \"Version\":\"1\",\n" +
                "   \"Statement\":[\n" +
                "   {\n" +
                "     \"Action\":[\n" +
                "       \"oss:PutObject\",\n" +
                "       \"oss:GetObject\"\n" +
                "    ],\n" +
                "    \"Effect\":\"Deny\",\n" +
                "    \"Principal\":[\"27737962156157xxxx\"],\n" +
                "    \"Resource\":[\n" +
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/ap-01\",\n" +
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/ap-01/object/*\"\n" +
                "     ]\n" +
                "   }\n" +
                "  ]\n" +
                " }";

        PutAccessPointPolicyRequest request = PutAccessPointPolicyRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointName("apname")
                .body(BinaryData.fromString(policyJson))
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.accessPointName()).isEqualTo("apname");
        assertThat(request.body()).isNotNull();
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String policyJson = "{\n" +
                "   \"Version\":\"1\",\n" +
                "   \"Statement\":[\n" +
                "   {\n" +
                "     \"Action\":[\n" +
                "       \"oss:PutObject\",\n" +
                "       \"oss:GetObject\"\n" +
                "    ],\n" +
                "    \"Effect\":\"Deny\",\n" +
                "    \"Principal\":[\"27737962156157xxxx\"],\n" +
                "    \"Resource\":[\n" +
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/ap-01\",\n" +
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/ap-01/object/*\"\n" +
                "     ]\n" +
                "   }\n" +
                "  ]\n" +
                " }";

        PutAccessPointPolicyRequest request = PutAccessPointPolicyRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointName("apname")
                .body(BinaryData.fromString(policyJson))
                .build();

        OperationInput input = SerdeAccessPoint.fromPutAccessPointPolicy(request);

        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.parameters().get("accessPointPolicy")).isEqualTo("");
        assertThat(input.headers().get("x-oss-access-point-name")).isEqualTo("apname");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the JSON body content
        BinaryData body = input.body().get();
        String jsonContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(jsonContent).contains("\"Version\":\"1\"");
        assertThat(jsonContent).contains("\"Statement\"");
        assertThat(jsonContent).contains("\"Action\"");
        assertThat(jsonContent).contains("\"Effect\":\"Deny\"");
        assertThat(jsonContent).contains("\"Principal\"");
        assertThat(jsonContent).contains("\"Resource\"");

        String expectedJson = policyJson;
        assertThat(jsonContent).isEqualTo(expectedJson);
    }
}