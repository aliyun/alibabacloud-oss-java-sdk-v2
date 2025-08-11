package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutObjectResultTest {
    @Test
    public void testEmptyBuilder() {
        PutObjectResult result = PutObjectResult.newBuilder().build();
        assertThat(result).isNotNull();
        ResultModel resultModel = result;
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");
        headers.put("x-oss-version-id", "v1234567890abcdefg");
        headers.put("x-request-id", "req-1234567890abcdefg");
        headers.put("ETag", "\"D41D8CD98F00B204E9800998ECF8****\"");
        headers.put("Content-Md5", "1B2M2Y8AsgTpgAmY7PhC****");
        String callbackResult = "callback-result-value";

        PutObjectResult result = PutObjectResult.newBuilder()
                .headers(headers)
                .innerBody(callbackResult)
                .build();

        assertThat(result.hashCrc64ecma()).isEqualTo("1234567890123456789");
        assertThat(result.versionId()).isEqualTo("v1234567890abcdefg");
        assertThat(result.eTag()).isEqualTo("\"D41D8CD98F00B204E9800998ECF8****\"");
        assertThat(result.contentMd5()).isEqualTo("1B2M2Y8AsgTpgAmY7PhC****");
        assertThat(result.callbackResult()).isEqualTo(callbackResult);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");
        headers.put("x-oss-version-id", "v1234567890abcdefg");
        headers.put("x-request-id", "req-1234567890abcdefg");
        headers.put("Content-Type", "application/xml");
        headers.put("Content-Md5", "content-md5-value");

        PutObjectResult original = PutObjectResult.newBuilder()
                .headers(headers)
                .build();

        PutObjectResult copy = original.toBuilder().build();

        assertThat(copy.hashCrc64ecma()).isEqualTo("1234567890123456789");

        assertThat(copy.versionId()).isEqualTo("v1234567890abcdefg");

        assertThat(copy.requestId()).isEqualTo("req-1234567890abcdefg");

        assertThat(copy.headers().get("Content-Type")).isEqualTo("application/xml");

        assertThat(copy.headers().get("Content-Md5")).isEqualTo("content-md5-value");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");
        headers.put("Content-Type", "application/xml");

        PutObjectResult result = PutObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("Content-Type")).isEqualTo("application/xml");

        assertThat(result.hashCrc64ecma()).isEqualTo("1234567890123456789");
    }

}
