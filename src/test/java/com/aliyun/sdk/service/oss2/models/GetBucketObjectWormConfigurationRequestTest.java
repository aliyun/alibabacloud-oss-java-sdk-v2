package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectWormConfiguration;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketObjectWormConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketObjectWormConfigurationRequest request = GetBucketObjectWormConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        GetBucketObjectWormConfigurationRequest request = GetBucketObjectWormConfigurationRequest.newBuilder()
                .bucket("examplebucket")
                .headers(headers)
                .parameter("param1", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        GetBucketObjectWormConfigurationRequest original = GetBucketObjectWormConfigurationRequest.newBuilder()
                .bucket("testbucket")
                .build();

        GetBucketObjectWormConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        GetBucketObjectWormConfigurationRequest request = GetBucketObjectWormConfigurationRequest.newBuilder()
                .bucket("examplebucket")
                .build();

        OperationInput input = SerdeBucketObjectWormConfiguration.fromGetBucketObjectWormConfiguration(request);

        assertThat(input.parameters().get("objectWorm")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("GET");
    }
}
