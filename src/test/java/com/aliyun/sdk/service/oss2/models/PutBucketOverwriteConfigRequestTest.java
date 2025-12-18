package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketOverwriteConfig;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketOverwriteConfigRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutBucketOverwriteConfigRequest request = PutBucketOverwriteConfigRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.overwriteConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        List<OverwriteRule> rules = Arrays.asList(
                OverwriteRule.newBuilder()
                        .id("id-1")
                        .action("forbid")
                        .prefix("prefix1")
                        .suffix("suffix1")
                        .principals(OverwritePrincipals.newBuilder()
                                .principals(Arrays.asList("p1", "p2"))
                                .build())
                        .build(),
                OverwriteRule.newBuilder()
                        .id("id-2")
                        .action("forbid")
                        .prefix("prefix2")
                        .suffix("suffix2")
                        .principals(OverwritePrincipals.newBuilder()
                                .principals(Arrays.asList("p3"))
                                .build())
                        .build()
        );

        OverwriteConfiguration overwriteConfiguration = OverwriteConfiguration.newBuilder()
                .rules(rules)
                .build();

        PutBucketOverwriteConfigRequest request = PutBucketOverwriteConfigRequest.newBuilder()
                .bucket("examplebucket")
                .overwriteConfiguration(overwriteConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.overwriteConfiguration()).isEqualTo(overwriteConfiguration);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new java.util.AbstractMap.SimpleEntry<>("param1", "value1"),
                new java.util.AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        List<OverwriteRule> rules = Arrays.asList(
                OverwriteRule.newBuilder()
                        .id("id-1")
                        .action("forbid")
                        .prefix("prefix1")
                        .suffix("suffix1")
                        .principals(OverwritePrincipals.newBuilder()
                                .principals(Arrays.asList("p1", "p2"))
                                .build())
                        .build(),
                OverwriteRule.newBuilder()
                        .id("id-2")
                        .action("allow")
                        .prefix("prefix2")
                        .suffix("suffix2")
                        .principals(OverwritePrincipals.newBuilder()
                                .principals(Arrays.asList("p3"))
                                .build())
                        .build()
        );


        OverwriteConfiguration overwriteConfiguration = OverwriteConfiguration.newBuilder()
                .rules(rules)
                .build();

        PutBucketOverwriteConfigRequest request = PutBucketOverwriteConfigRequest.newBuilder()
                .bucket("examplebucket")
                .overwriteConfiguration(overwriteConfiguration)
                .build();

        OperationInput input = SerdeBucketOverwriteConfig.fromPutBucketOverwriteConfig(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("overwriteConfig")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<OverwriteConfiguration>");
        assertThat(xmlContent).contains("<Rule>");
        assertThat(xmlContent).contains("<ID>id-1</ID>");
        assertThat(xmlContent).contains("<Action>forbid</Action>");
        assertThat(xmlContent).contains("<Prefix>prefix1</Prefix>");
        assertThat(xmlContent).contains("<Suffix>suffix1</Suffix>");
        assertThat(xmlContent).contains("<Principals>");
        assertThat(xmlContent).contains("<Principal>p1</Principal>");
        assertThat(xmlContent).contains("<Principal>p2</Principal>");
        assertThat(xmlContent).contains("</Principals>");
        assertThat(xmlContent).contains("</Rule>");
        assertThat(xmlContent).contains("<Rule>");
        assertThat(xmlContent).contains("<Rule>");
        assertThat(xmlContent).contains("<ID>id-2</ID>");
        assertThat(xmlContent).contains("<Action>allow</Action>");
        assertThat(xmlContent).contains("<Prefix>prefix2</Prefix>");
        assertThat(xmlContent).contains("<Suffix>suffix2</Suffix>");
        assertThat(xmlContent).contains("<Principals>");
        assertThat(xmlContent).contains("<Principal>p3</Principal>");
        assertThat(xmlContent).contains("</Principals>");
        assertThat(xmlContent).contains("</Rule>");
        assertThat(xmlContent).contains("</OverwriteConfiguration>");
    }
}