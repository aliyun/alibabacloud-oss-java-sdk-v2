package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketLogging;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutUserDefinedLogFieldsConfigRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutUserDefinedLogFieldsConfigRequest request = PutUserDefinedLogFieldsConfigRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.userDefinedLogFieldsConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        HeaderSet headerSet = HeaderSet.newBuilder().headers(Arrays.asList("header1", "header2", "header3")).build();
        ParamSet paramSet = ParamSet.newBuilder().parameters(Arrays.asList("param1", "param2")).build();
        
        UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration = UserDefinedLogFieldsConfiguration.newBuilder()
                .headerSet(headerSet)
                .paramSet(paramSet)
                .build();

        PutUserDefinedLogFieldsConfigRequest request = PutUserDefinedLogFieldsConfigRequest.newBuilder()
                .bucket("examplebucket")
                .userDefinedLogFieldsConfiguration(userDefinedLogFieldsConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.userDefinedLogFieldsConfiguration()).isEqualTo(userDefinedLogFieldsConfiguration);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        HeaderSet headerSet = HeaderSet.newBuilder().headers(Arrays.asList("header1", "header2", "header3")).build();
        ParamSet paramSet = ParamSet.newBuilder().parameters(Arrays.asList("param1", "param2")).build();
        
        UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration = UserDefinedLogFieldsConfiguration.newBuilder()
                .headerSet(headerSet)
                .paramSet(paramSet)
                .build();

        PutUserDefinedLogFieldsConfigRequest original = PutUserDefinedLogFieldsConfigRequest.newBuilder()
                .bucket("testbucket")
                .userDefinedLogFieldsConfiguration(userDefinedLogFieldsConfiguration)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutUserDefinedLogFieldsConfigRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.userDefinedLogFieldsConfiguration()).isEqualTo(userDefinedLogFieldsConfiguration);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        HeaderSet headerSet = HeaderSet.newBuilder().headers(Arrays.asList("header1", "header2", "header3")).build();
        ParamSet paramSet = ParamSet.newBuilder().parameters(Arrays.asList("param1", "param2")).build();
        
        UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration = UserDefinedLogFieldsConfiguration.newBuilder()
                .headerSet(headerSet)
                .paramSet(paramSet)
                .build();

        PutUserDefinedLogFieldsConfigRequest request = PutUserDefinedLogFieldsConfigRequest.newBuilder()
                .bucket("anotherbucket")
                .userDefinedLogFieldsConfiguration(userDefinedLogFieldsConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.userDefinedLogFieldsConfiguration()).isEqualTo(userDefinedLogFieldsConfiguration);
    }

    @Test
    public void xmlBuilder() {
        HeaderSet headerSet = HeaderSet.newBuilder().headers(Arrays.asList("header1", "header2", "header3")).build();
        ParamSet paramSet = ParamSet.newBuilder().parameters(Arrays.asList("param1", "param2")).build();
        
        UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration = UserDefinedLogFieldsConfiguration.newBuilder()
                .headerSet(headerSet)
                .paramSet(paramSet)
                .build();

        PutUserDefinedLogFieldsConfigRequest request = PutUserDefinedLogFieldsConfigRequest.newBuilder()
                .bucket("examplebucket")
                .userDefinedLogFieldsConfiguration(userDefinedLogFieldsConfiguration)
                .build();

        OperationInput input = SerdeBucketLogging.fromPutUserDefinedLogFieldsConfig(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("userDefinedLogFieldsConfig")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<UserDefinedLogFieldsConfiguration>");
        assertThat(xmlContent).contains("<HeaderSet>");
        assertThat(xmlContent).contains("<header>header1</header>");
        assertThat(xmlContent).contains("<header>header2</header>");
        assertThat(xmlContent).contains("<header>header3</header>");
        assertThat(xmlContent).contains("</HeaderSet>");
        assertThat(xmlContent).contains("<ParamSet>");
        assertThat(xmlContent).contains("<parameter>param1</parameter>");
        assertThat(xmlContent).contains("<parameter>param2</parameter>");
        assertThat(xmlContent).contains("</ParamSet>");
        assertThat(xmlContent).contains("</UserDefinedLogFieldsConfiguration>");

    }
}
