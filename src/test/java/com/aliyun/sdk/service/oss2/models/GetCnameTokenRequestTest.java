package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketCname;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetCnameTokenRequestTest {
    @Test
    public void testEmptyBuilder() {
        GetCnameTokenRequest request = GetCnameTokenRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.cname()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        GetCnameTokenRequest request = GetCnameTokenRequest.newBuilder()
                .bucket("examplebucket")
                .cname("example.com")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.cname()).isEqualTo("example.com");

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        GetCnameTokenRequest original = GetCnameTokenRequest.newBuilder()
                .bucket("testbucket")
                .cname("test.example.com")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        GetCnameTokenRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.cname()).isEqualTo("test.example.com");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        GetCnameTokenRequest request = GetCnameTokenRequest.newBuilder()
                .bucket("anotherbucket")
                .cname("sample.example.com")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.cname()).isEqualTo("sample.example.com");
    }
    
    @Test
    public void xmlBuilder() {
        GetCnameTokenRequest request = GetCnameTokenRequest.newBuilder()
                .bucket("examplebucket")
                .cname("example.com")
                .build();

        OperationInput input = SerdeBucketCname.fromGetCnameToken(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("cname")).isEqualTo("example.com");
        assertThat(input.parameters().get("comp")).isEqualTo("token");
        assertThat(input.method()).isEqualTo("GET");
    }
}