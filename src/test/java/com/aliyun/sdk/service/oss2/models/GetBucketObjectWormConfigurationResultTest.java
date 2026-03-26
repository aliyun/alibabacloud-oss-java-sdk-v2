package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectWormConfiguration;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketObjectWormConfigurationResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketObjectWormConfigurationResult result = GetBucketObjectWormConfigurationResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.objectWormConfiguration()).isNull();
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

        GetBucketObjectWormConfigurationResult result = GetBucketObjectWormConfigurationResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .objectWormConfiguration(objectWormConfiguration)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.objectWormConfiguration()).isEqualTo(objectWormConfiguration);
        assertThat(result.objectWormConfiguration().objectWormEnabled()).isEqualTo("Enabled");
        assertThat(result.objectWormConfiguration().rule().defaultRetention().mode()).isEqualTo(ObjectWormConfigurationModeType.COMPLIANCE.toString());
        assertThat(result.objectWormConfiguration().rule().defaultRetention().days()).isEqualTo(Integer.valueOf(30));
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

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

        GetBucketObjectWormConfigurationResult original = GetBucketObjectWormConfigurationResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .objectWormConfiguration(objectWormConfiguration)
                .build();

        GetBucketObjectWormConfigurationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.objectWormConfiguration()).isEqualTo(objectWormConfiguration);
    }

    @Test
    public void testXmlBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ObjectWormConfiguration>\n" +
                "  <ObjectWormEnabled>Enabled</ObjectWormEnabled>\n" +
                "  <Rule>\n" +
                "    <DefaultRetention>\n" +
                "      <Mode>COMPLIANCE</Mode>\n" +
                "      <Days>30</Days>\n" +
                "    </DefaultRetention>\n" +
                "  </Rule>\n" +
                "</ObjectWormConfiguration>";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-111111111111111111"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .body(BinaryData.fromString(xml))
                .build();

        GetBucketObjectWormConfigurationResult result = SerdeBucketObjectWormConfiguration.toGetBucketObjectWormConfiguration(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
        assertThat(result.objectWormConfiguration()).isNotNull();
        assertThat(result.objectWormConfiguration().objectWormEnabled()).isEqualTo("Enabled");
        assertThat(result.objectWormConfiguration().rule()).isNotNull();
        assertThat(result.objectWormConfiguration().rule().defaultRetention()).isNotNull();
        assertThat(result.objectWormConfiguration().rule().defaultRetention().mode()).isEqualTo(ObjectWormConfigurationModeType.COMPLIANCE.toString());
        assertThat(result.objectWormConfiguration().rule().defaultRetention().days()).isEqualTo(Integer.valueOf(30));
    }
}
