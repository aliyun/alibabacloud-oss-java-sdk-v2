package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectWormConfiguration;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketObjectWormConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketObjectWormConfigurationRequest request = PutBucketObjectWormConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.objectWormConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        ObjectWormConfigurationDefaultRetention defaultRetention = ObjectWormConfigurationDefaultRetention.newBuilder()
                .mode(ObjectWormConfigurationModeType.COMPLIANCE.toString())
                .days(30)
                .build();

        ObjectWormConfigurationRule rule = ObjectWormConfigurationRule.newBuilder()
                .defaultRetention(defaultRetention)
                .build();

        ObjectWormConfiguration objectWormConfiguration = ObjectWormConfiguration.newBuilder()
                .objectWormEnabled("Enabled")
                .rule(rule)
                .build();

        PutBucketObjectWormConfigurationRequest request = PutBucketObjectWormConfigurationRequest.newBuilder()
                .bucket("examplebucket")
                .objectWormConfiguration(objectWormConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.objectWormConfiguration()).isEqualTo(objectWormConfiguration);
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        ObjectWormConfigurationDefaultRetention defaultRetention = ObjectWormConfigurationDefaultRetention.newBuilder()
                .mode(ObjectWormConfigurationModeType.GOVERNANCE.toString())
                .years(1)
                .build();

        ObjectWormConfigurationRule rule = ObjectWormConfigurationRule.newBuilder()
                .defaultRetention(defaultRetention)
                .build();

        ObjectWormConfiguration objectWormConfiguration = ObjectWormConfiguration.newBuilder()
                .objectWormEnabled("Enabled")
                .rule(rule)
                .build();

        PutBucketObjectWormConfigurationRequest original = PutBucketObjectWormConfigurationRequest.newBuilder()
                .bucket("testbucket")
                .objectWormConfiguration(objectWormConfiguration)
                .build();

        PutBucketObjectWormConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.objectWormConfiguration()).isEqualTo(objectWormConfiguration);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectWormConfigurationDefaultRetention defaultRetention = ObjectWormConfigurationDefaultRetention.newBuilder()
                .mode(ObjectWormConfigurationModeType.COMPLIANCE.toString())
                .days(30)
                .build();

        ObjectWormConfigurationRule rule = ObjectWormConfigurationRule.newBuilder()
                .defaultRetention(defaultRetention)
                .build();

        ObjectWormConfiguration objectWormConfiguration = ObjectWormConfiguration.newBuilder()
                .objectWormEnabled("Enabled")
                .rule(rule)
                .build();

        PutBucketObjectWormConfigurationRequest request = PutBucketObjectWormConfigurationRequest.newBuilder()
                .bucket("examplebucket")
                .objectWormConfiguration(objectWormConfiguration)
                .build();

        OperationInput input = SerdeBucketObjectWormConfiguration.fromPutBucketObjectWormConfiguration(request);

        assertThat(input.parameters().get("objectWorm")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<ObjectWormConfiguration>");
        assertThat(xmlContent).contains("<ObjectWormEnabled>Enabled</ObjectWormEnabled>");
        assertThat(xmlContent).contains("<Rule>");
        assertThat(xmlContent).contains("<DefaultRetention>");
        assertThat(xmlContent).contains("<Mode>COMPLIANCE</Mode>");
        assertThat(xmlContent).contains("<Days>30</Days>");
        assertThat(xmlContent).contains("</DefaultRetention>");
        assertThat(xmlContent).contains("</Rule>");
        assertThat(xmlContent).contains("</ObjectWormConfiguration>");
    }
}
