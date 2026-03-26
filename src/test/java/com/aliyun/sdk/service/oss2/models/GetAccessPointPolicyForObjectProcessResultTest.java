package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectFcAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetAccessPointPolicyForObjectProcessResultTest {

    @Test
    public void testEmptyBuilder() {
        GetAccessPointPolicyForObjectProcessResult result = GetAccessPointPolicyForObjectProcessResult.newBuilder().build();
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

        GetAccessPointPolicyForObjectProcessResult result = GetAccessPointPolicyForObjectProcessResult.newBuilder()
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

        GetAccessPointPolicyForObjectProcessResult original = GetAccessPointPolicyForObjectProcessResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetAccessPointPolicyForObjectProcessResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        String policyJson = "{\n" +
                "\t\"Version\": \"1\",\n" +
                "\t\"Statement\": [{\n" +
                "\t\t\"Effect\": \"Allow\",\n" +
                "\t\t\"Action\": [\n" +
                "\t\t\t\"oss:GetObject\"\n" +
                "\t\t],\n" +
                "\t\t\"Principal\": [\n" +
                "\t\t\t\"23721626365841xxxx\"\n" +
                "\t\t],\n" +
                "\t\t\"Resource\": [\n" +
                "\t\t\t\"acs:oss:cn-qingdao:111933544165xxxx:accesspointforobjectprocess/fc-ap-001/object/*\"\n" +
                "\t\t]\n" +
                "\t}]\n" +
                "}";
        BinaryData policyBody = BinaryData.fromString(policyJson);

        OperationOutput output = OperationOutput.newBuilder()
                .body(policyBody)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetAccessPointPolicyForObjectProcessResult result = SerdeBucketObjectFcAccessPoint.toGetAccessPointPolicyForObjectProcess(output);

        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.body()).isEqualTo(policyBody);
        
        String responseBody = new String(result.body().toBytes());
        assertThat(responseBody).isEqualTo(policyJson);
    }
}