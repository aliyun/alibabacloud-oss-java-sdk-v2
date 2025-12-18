package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketOverwriteConfig;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketOverwriteConfigResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketOverwriteConfigResult result = GetBucketOverwriteConfigResult.newBuilder().build();
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
    }

    @Test
    public void testToBuilderPreserveState() {

    }

    @Test
    public void testXmlBuilder() {
        String xml =
                "<OverwriteConfiguration>\n" +
                "  <Rule>\n" +
                "    <ID>forbid-write-rule1</ID>\n" +
                "    <Action>forbid</Action>\n" +
                "    <Prefix>a/</Prefix>\n" +
                "    <Suffix>.txt</Suffix>\n" +
                "    <Principals>\n" +
                "        <Principal>2773</Principal>\n" +
                "    </Principals>\n" +
                "  </Rule>\n" +
                "</OverwriteConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketOverwriteConfigResult result = SerdeBucketOverwriteConfig.toGetBucketOverwriteConfig(output);

        OverwriteConfiguration overwriteConfiguration = result.overwriteConfiguration();
        assertThat(overwriteConfiguration).isNotNull();
        assertThat(overwriteConfiguration.rules()).isNotNull();
        assertThat(overwriteConfiguration.rules()).hasSize(1);

        OverwriteRule rule = overwriteConfiguration.rules().get(0);
        assertThat(rule.id()).isEqualTo("forbid-write-rule1");
        assertThat(rule.action()).isEqualTo("forbid");
        assertThat(rule.prefix()).isEqualTo("a/");
        assertThat(rule.suffix()).isEqualTo(".txt");

        OverwritePrincipals principals = rule.principals();
        assertThat(principals).isNotNull();
        assertThat(principals.principals()).isNotNull();
        assertThat(principals.principals()).hasSize(1);

        String principal = principals.principals().get(0);
        assertThat(principal).isEqualTo("2773");
    }


    @Test
    public void testXmlBuilderEmpty() {
        String xml =
                "<OverwriteConfiguration>\n" +
                "</OverwriteConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketOverwriteConfigResult result = SerdeBucketOverwriteConfig.toGetBucketOverwriteConfig(output);

        OverwriteConfiguration overwriteConfiguration = result.overwriteConfiguration();
        assertThat(overwriteConfiguration).isNotNull();
        assertThat(overwriteConfiguration.rules()).isNull();
    }
}