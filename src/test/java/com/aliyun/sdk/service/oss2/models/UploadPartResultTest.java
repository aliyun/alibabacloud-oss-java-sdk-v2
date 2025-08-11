package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectMultipart;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UploadPartResultTest {

    @Test
    public void testEmptyBuilder() {
        UploadPartResult result = UploadPartResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.contentMD5()).isNull();
        assertThat(result.eTag()).isNull();
        assertThat(result.hashCRC64()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "Content-MD5", "content-md5-value",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"",
                "x-oss-hash-crc64ecma", "1234567890123456789"
        );

        UploadPartResult result = UploadPartResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Content-MD5")).isEqualTo("content-md5-value");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.headers().get("x-oss-hash-crc64ecma")).isEqualTo("1234567890123456789");

        assertThat(result.contentMD5()).isEqualTo("content-md5-value");
        assertThat(result.eTag()).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.hashCRC64()).isEqualTo("1234567890123456789");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "Content-MD5", "original-content-md5",
                "ETag", "\"original-etag\"",
                "x-oss-hash-crc64ecma", "9876543210987654321"
        );

        UploadPartResult original = UploadPartResult.newBuilder()
                .headers(headers)
                .build();

        UploadPartResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("Content-MD5")).isEqualTo("original-content-md5");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.headers().get("x-oss-hash-crc64ecma")).isEqualTo("9876543210987654321");

        assertThat(copy.contentMD5()).isEqualTo("original-content-md5");
        assertThat(copy.eTag()).isEqualTo("\"original-etag\"");
        assertThat(copy.hashCRC64()).isEqualTo("9876543210987654321");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectMultipart.toUploadPart(blankOutput);


        String xml = "<Error>Not implemented</Error>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "Content-MD5", "content-md5-value",
                        "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"",
                        "x-oss-hash-crc64ecma", "1234567890123456789"))
                .build();
        UploadPartResult result = SerdeObjectMultipart.toUploadPart(output);

        assertThat(result.contentMD5()).isEqualTo("content-md5-value");
        assertThat(result.eTag()).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.hashCRC64()).isEqualTo("1234567890123456789");
    }
}
