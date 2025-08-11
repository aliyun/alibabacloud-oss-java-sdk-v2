package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationInputTest {
    private static final AttributeMap.Key<String> STRING_KEY = new AttributeMap.Key<String>(String.class) {
    };

    @Test
    public void buildOperationInputWithEmptyValues() {
        OperationInput input = OperationInput.newBuilder()
                .build();

        assertThat(input.opName()).isEqualTo("");
        assertThat(input.method()).isNull();
        assertThat(input.bucket()).isNotPresent();
        assertThat(input.key()).isNotPresent();
        assertThat(input.headers()).isEmpty();
        assertThat(input.parameters()).isEmpty();
        assertThat(input.opMetadata()).isNotNull();
        assertThat(input.body()).isNotPresent();
    }

    @Test
    public void buildOperationInputWithValues() {
        // header parameters, attributeMap empty values
        // body has value
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        BinaryData body = new StringBinaryData("");
        AttributeMap attributeMap = AttributeMap.empty();
        OperationInput input = OperationInput.newBuilder()
                .opName("Operation")
                .bucket("bucket")
                .key("key")
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .opMetadata(attributeMap)
                .body(body)
                .build();

        assertThat(input.opName()).isEqualTo("Operation");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.bucket()).hasValue("bucket");
        assertThat(input.key()).hasValue("key");
        assertThat(input.headers()).isEmpty();
        assertThat(input.parameters()).isEmpty();
        assertThat(input.body()).hasValue(body);

        // all have values
        headers = MapUtils.caseInsensitiveMap();
        headers.put("header", "h-str-value");
        parameters = MapUtils.caseSensitiveMap();
        parameters.put("param", "p-str-value");
        body = new StringBinaryData("hello world");
        attributeMap = AttributeMap.empty();
        input = OperationInput.newBuilder()
                .opName("Operation")
                .bucket("bucket")
                .key("key")
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .opMetadata(attributeMap)
                .body(body)
                .build();

        assertThat(input.opName()).isEqualTo("Operation");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.bucket()).hasValue("bucket");
        assertThat(input.key()).hasValue("key");
        assertThat(input.headers()).hasSize(1);
        assertThat(input.headers().get("header")).isEqualTo("h-str-value");
        // case-insensitive
        assertThat(input.headers().get("Header")).isEqualTo("h-str-value");
        assertThat(input.parameters()).hasSize(1);
        assertThat(input.parameters().get("param")).isEqualTo("p-str-value");
        // case-sensitive
        assertThat(input.parameters().get("Param")).isNull();
        assertThat(input.body()).hasValue(body);
    }

    @Test
    public void buildAndUpdateOperationInput() {
        OperationInput input = OperationInput.newBuilder()
                .build();

        assertThat(input.opName()).isEqualTo("");
        assertThat(input.method()).isNull();
        assertThat(input.bucket()).isNotPresent();
        assertThat(input.key()).isNotPresent();
        assertThat(input.headers()).isEmpty();
        assertThat(input.parameters()).isEmpty();
        assertThat(input.opMetadata()).isNotNull();
        assertThat(input.body()).isNotPresent();

        // update header parameters, opMetadata
        input.headers().put("header", "h-str-value");
        input.parameters().put("param", "p-str-value");
        input.opMetadata().put(STRING_KEY, "str-value");

        assertThat(input.headers()).hasSize(1);
        assertThat(input.headers().get("header")).isEqualTo("h-str-value");
        assertThat(input.parameters()).hasSize(1);
        assertThat(input.parameters().get("param")).isEqualTo("p-str-value");
        assertThat(input.opMetadata().get(STRING_KEY)).isEqualTo("str-value");
    }
}
