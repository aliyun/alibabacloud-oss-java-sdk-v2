package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetAccessPointPolicyResultTest {

    @Test
    public void testEmptyBuilder() {
        GetAccessPointPolicyResult result = GetAccessPointPolicyResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.body()).isNull();
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
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/$ap-01\",\n" +
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/$ap-01/object/*\"\n" +
                "     ]\n" +
                "   }\n" +
                "  ]\n" +
                " }";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Type", "application/json"
        );

        GetAccessPointPolicyResult result = GetAccessPointPolicyResult.newBuilder()
                .headers(headers)
                .innerBody(BinaryData.fromString(policyJson))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(result.body()).isNotNull();
        assertThat(result.body().toString()).contains("\"Version\":\"1\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
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
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/$ap-01\",\n" +
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/$ap-01/object/*\"\n" +
                "     ]\n" +
                "   }\n" +
                "  ]\n" +
                " }";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        GetAccessPointPolicyResult original = GetAccessPointPolicyResult.newBuilder()
                .headers(headers)
                .innerBody(BinaryData.fromString(policyJson))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetAccessPointPolicyResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.body()).isNotNull();
        assertThat(copy.body().toString()).contains("\"Version\":\"1\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
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
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/$ap-01\",\n" +
                "       \"acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/$ap-01/object/*\"\n" +
                "     ]\n" +
                "   }\n" +
                "  ]\n" +
                " }";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(policyJson))
                .headers(MapUtils.of("x-oss-request-id", "req-1234567890abcdefg"))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetAccessPointPolicyResult result = SerdeAccessPoint.toGetAccessPointPolicy(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.body()).isNotNull();
        assertThat(result.body().toString()).contains("\"Version\":\"1\"");
        assertThat(result.body().toString()).contains("\"Statement\"");
        assertThat(result.body().toString()).contains("\"Action\"");
        assertThat(result.body().toString()).contains("\"Effect\":\"Deny\"");
        assertThat(result.body().toString()).contains("\"Principal\"");
        assertThat(result.body().toString()).contains("\"Resource\"");
    }
}