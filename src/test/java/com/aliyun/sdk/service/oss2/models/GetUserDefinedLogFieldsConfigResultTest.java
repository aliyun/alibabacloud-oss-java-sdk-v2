package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketLogging;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetUserDefinedLogFieldsConfigResultTest {

    @Test
    public void testEmptyBuilder() {
        GetUserDefinedLogFieldsConfigResult result = GetUserDefinedLogFieldsConfigResult.newBuilder().build();
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

        LoggingHeaderSet headerSet = LoggingHeaderSet.newBuilder().headers(Arrays.asList("header1", "header2", "header3")).build();
        LoggingParamSet paramSet = LoggingParamSet.newBuilder().parameters(Arrays.asList("param1", "param2")).build();
        
        UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration = UserDefinedLogFieldsConfiguration.newBuilder()
                .headerSet(headerSet)
                .paramSet(paramSet)
                .build();

        GetUserDefinedLogFieldsConfigResult result = GetUserDefinedLogFieldsConfigResult.newBuilder()
                .headers(headers)
                .statusCode(200)
                .status("OK")
                .innerBody(userDefinedLogFieldsConfiguration)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.userDefinedLogFieldsConfiguration()).isEqualTo(userDefinedLogFieldsConfiguration);
        
        assertThat(result.userDefinedLogFieldsConfiguration().headerSet().headers()).containsExactly("header1", "header2", "header3");
        assertThat(result.userDefinedLogFieldsConfiguration().paramSet().parameters()).containsExactly("param1", "param2");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        LoggingHeaderSet headerSet = LoggingHeaderSet.newBuilder().headers(Arrays.asList("header1", "header2")).build();
        LoggingParamSet paramSet = LoggingParamSet.newBuilder().parameters(Arrays.asList("param1")).build();
        
        UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration = UserDefinedLogFieldsConfiguration.newBuilder()
                .headerSet(headerSet)
                .paramSet(paramSet)
                .build();

        GetUserDefinedLogFieldsConfigResult original = GetUserDefinedLogFieldsConfigResult.newBuilder()
                .headers(headers)
                .statusCode(200)
                .status("OK")
                .innerBody(userDefinedLogFieldsConfiguration)
                .build();

        GetUserDefinedLogFieldsConfigResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.userDefinedLogFieldsConfiguration()).isEqualTo(userDefinedLogFieldsConfiguration);
        
        assertThat(copy.userDefinedLogFieldsConfiguration().headerSet().headers()).containsExactly("header1", "header2");
        assertThat(copy.userDefinedLogFieldsConfiguration().paramSet().parameters()).containsExactly("param1");
    }

    @Test
    public void xmlBuilder() {
        String xml = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<UserDefinedLogFieldsConfiguration>\n" +
                "\t<HeaderSet>\n" +
                "\t\t<header>header1</header>\n" +
                "\t\t<header>header2</header>\n" +
                "\t\t<header>header3</header>\n" +
                "\t</HeaderSet>\n" +
                "\t<ParamSet>\n" +
                "\t\t<parameter>param1</parameter>\n" +
                "\t\t<parameter>param2</parameter>\n" +
                "\t</ParamSet>\n" +
                "</UserDefinedLogFieldsConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetUserDefinedLogFieldsConfigResult result = SerdeBucketLogging.toGetUserDefinedLogFieldsConfig(output);

        assertThat(result.userDefinedLogFieldsConfiguration().headerSet().headers()).containsExactly("header1", "header2", "header3");
        assertThat(result.userDefinedLogFieldsConfiguration().paramSet().parameters()).containsExactly("param1", "param2");
    }
}
