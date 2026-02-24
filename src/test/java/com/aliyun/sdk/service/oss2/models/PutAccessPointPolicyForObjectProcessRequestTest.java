package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectFcAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class PutAccessPointPolicyForObjectProcessRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutAccessPointPolicyForObjectProcessRequest request = PutAccessPointPolicyForObjectProcessRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.body()).isNull();
    }

    @Test
    public void testFullBuilder() {
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

        PutAccessPointPolicyForObjectProcessRequest request = PutAccessPointPolicyForObjectProcessRequest.newBuilder()
                .bucket("examplebucket")
                .accessPointForObjectProcessName("fc-ap-01")
                .body(policyBody)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.accessPointForObjectProcessName()).isEqualTo("fc-ap-01");
        assertThat(request.body()).isEqualTo(policyBody);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        PutAccessPointPolicyForObjectProcessRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.accessPointForObjectProcessName()).isEqualTo("fc-ap-01");
        assertThat(copy.body()).isEqualTo(policyBody);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        String policyJson = "{\n" +
                "\t\"Version\": \"1\",\n" +
                "\t\"Statement\": [{\n" +
                "\t\t\"Effect\": \"Deny\",\n" +
                "\t\t\"Action\": [\n" +
                "\t\t\t\"oss:PutObject\"\n" +
                "\t\t],\n" +
                "\t\t\"Principal\": [\n" +
                "\t\t\t\"*\"\n" +
                "\t\t],\n" +
                "\t\t\"Resource\": [\n" +
                "\t\t\t\"acs:oss:*:*:accesspointforobjectprocess/*/object/*\"\n" +
                "\t\t]\n" +
                "\t}]\n" +
                "}";
        BinaryData policyBody = BinaryData.fromString(policyJson);

        PutAccessPointPolicyForObjectProcessRequest original = PutAccessPointPolicyForObjectProcessRequest.newBuilder()
                .bucket("test-bucket")
                .accessPointForObjectProcessName("fc-ap-02")
                .body(policyBody)
                .build();

        PutAccessPointPolicyForObjectProcessRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.accessPointForObjectProcessName()).isEqualTo("fc-ap-02");
        assertThat(copy.body()).isEqualTo(policyBody);
    }

    @Test
    public void testHeaderProperties() {
        String policyJson = "{\n" +
                "\t\"Version\": \"1\",\n" +
                "\t\"Statement\": [{\n" +
                "\t\t\"Effect\": \"Allow\",\n" +
                "\t\t\"Action\": [\n" +
                "\t\t\t\"oss:*\"\n" +
                "\t\t],\n" +
                "\t\t\"Principal\": [\n" +
                "\t\t\t\"acs:ram::1234567890123456:root\"\n" +
                "\t\t],\n" +
                "\t\t\"Resource\": [\n" +
                "\t\t\t\"acs:oss:*:1234567890123456:accesspointforobjectprocess/fc-ap-test/object/*\"\n" +
                "\t\t]\n" +
                "\t}]\n" +
                "}";
        BinaryData policyBody = BinaryData.fromString(policyJson);

        PutAccessPointPolicyForObjectProcessRequest request = PutAccessPointPolicyForObjectProcessRequest.newBuilder()
                .bucket("policy-bucket")
                .accessPointForObjectProcessName("fc-ap-03")
                .body(policyBody)
                .build();

        assertThat(request.bucket()).isEqualTo("policy-bucket");
        assertThat(request.accessPointForObjectProcessName()).isEqualTo("fc-ap-03");
        assertThat(request.body()).isEqualTo(policyBody);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
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

        PutAccessPointPolicyForObjectProcessRequest request = PutAccessPointPolicyForObjectProcessRequest.newBuilder()
                .bucket("xml-bucket")
                .accessPointForObjectProcessName("fc-ap-01")
                .body(policyBody)
                .build();

        OperationInput input = SerdeBucketObjectFcAccessPoint.fromPutAccessPointPolicyForObjectProcess(request);

        assertThat(input.bucket().get()).isEqualTo("xml-bucket");
        assertThat(input.parameters().get("accessPointPolicyForObjectProcess")).isEqualTo("");
        assertThat(input.headers().get("x-oss-access-point-for-object-process-name")).isEqualTo("fc-ap-01");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        BinaryData body = input.body().get();
        String bodyContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(bodyContent).isEqualTo(policyJson);
    }
}