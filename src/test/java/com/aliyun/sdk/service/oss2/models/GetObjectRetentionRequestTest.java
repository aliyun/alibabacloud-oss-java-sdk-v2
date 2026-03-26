package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectWorm;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetObjectRetentionRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetObjectRetentionRequest request = GetObjectRetentionRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.versionId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        GetObjectRetentionRequest request = GetObjectRetentionRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .versionId("version-id")
                .headers(headers)
                .parameter("param1", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject");
        assertThat(request.versionId()).isEqualTo("version-id");
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        GetObjectRetentionRequest original = GetObjectRetentionRequest.newBuilder()
                .bucket("testbucket")
                .key("testobject")
                .versionId("original-version-id")
                .build();

        GetObjectRetentionRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.key()).isEqualTo("testobject");
        assertThat(copy.versionId()).isEqualTo("original-version-id");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        GetObjectRetentionRequest request = GetObjectRetentionRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .versionId("version-id")
                .build();

        OperationInput input = SerdeObjectWorm.fromGetObjectRetention(request);

        assertThat(input.parameters().get("retention")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("GET");
    }
}
