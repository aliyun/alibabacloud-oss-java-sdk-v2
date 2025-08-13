package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectMultipart;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CompleteMultipartUploadResultTest {

    @Test
    public void testEmptyBuilder() {
        CompleteMultipartUploadResult result = CompleteMultipartUploadResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.hashCRC64()).isNull();
        assertThat(result.versionId()).isNull();
        assertThat(result.completeMultipartUpload()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "x-oss-hash-crc64ecma", "1234567890123456789",
                "x-oss-version-id", "version-id-value"
        );

        CompleteMultipartUploadResultXml completeMultipartUploadResultXml = CompleteMultipartUploadResultXml.newBuilder()
                .encodingType("url")
                .location("http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data")
                .bucket("oss-example")
                .key("multipart.data")
                .eTag("\"B864DB6A936D376F9F8D3ED3BBE540****\"")
                .build();

        CompleteMultipartUploadResult result = CompleteMultipartUploadResult.newBuilder()
                .headers(headers)
                .innerBody(completeMultipartUploadResultXml)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-hash-crc64ecma")).isEqualTo("1234567890123456789");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("version-id-value");

        assertThat(result.hashCRC64()).isEqualTo("1234567890123456789");
        assertThat(result.versionId()).isEqualTo("version-id-value");

        CompleteMultipartUploadResultXml resultCompleteMultipartUpload = result.completeMultipartUpload();
        assertThat(resultCompleteMultipartUpload.encodingType()).isEqualTo("url");
        assertThat(resultCompleteMultipartUpload.location()).isEqualTo("http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data");
        assertThat(resultCompleteMultipartUpload.bucket()).isEqualTo("oss-example");
        assertThat(resultCompleteMultipartUpload.key()).isEqualTo("multipart.data");
        assertThat(resultCompleteMultipartUpload.eTag()).isEqualTo("\"B864DB6A936D376F9F8D3ED3BBE540****\"");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "x-oss-hash-crc64ecma", "9876543210987654321",
                "x-oss-version-id", "original-version-id"
        );

        CompleteMultipartUploadResultXml completeMultipartUploadResultXml = CompleteMultipartUploadResultXml.newBuilder()
                .encodingType("url")
                .location("http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data")
                .bucket("oss-example")
                .key("multipart.data")
                .eTag("\"B864DB6A936D376F9F8D3ED3BBE540****\"")
                .build();

        CompleteMultipartUploadResult original = CompleteMultipartUploadResult.newBuilder()
                .headers(headers)
                .innerBody(completeMultipartUploadResultXml)
                .build();

        CompleteMultipartUploadResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("x-oss-hash-crc64ecma")).isEqualTo("9876543210987654321");
        assertThat(copy.headers().get("x-oss-version-id")).isEqualTo("original-version-id");

        assertThat(copy.hashCRC64()).isEqualTo("9876543210987654321");
        assertThat(copy.versionId()).isEqualTo("original-version-id");

        CompleteMultipartUploadResultXml copyCompleteMultipartUpload = copy.completeMultipartUpload();
        assertThat(copyCompleteMultipartUpload.encodingType()).isEqualTo("url");
        assertThat(copyCompleteMultipartUpload.location()).isEqualTo("http://oss-example.oss-cn-hangzhou.aliyuncs.com/multipart.data");
        assertThat(copyCompleteMultipartUpload.bucket()).isEqualTo("oss-example");
        assertThat(copyCompleteMultipartUpload.key()).isEqualTo("multipart.data");
        assertThat(copyCompleteMultipartUpload.eTag()).isEqualTo("\"B864DB6A936D376F9F8D3ED3BBE540****\"");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";

        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectMultipart.toCompleteMultipartUpload(blankOutput);


        String xml =
                "<CompleteMultipartUploadResult xmlns=\"http://doc.oss-cn-hangzhou.aliyuncs.com\">\n" +
                        "    <EncodingType>url</EncodingType>\n" +
                        "    <Location>http://oss-example.oss-cn-hangzhou.aliyuncs.com /multipart.data</Location>\n" +
                        "    <Bucket>oss-example</Bucket>\n" +
                        "    <Key>multipart.data</Key>\n" +
                        "    <ETag>\"B864DB6A936D376F9F8D3ED3BBE540****\"</ETag>\n" +
                        "</CompleteMultipartUploadResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "x-oss-hash-crc64ecma", "1234567890123456789",
                        "x-oss-version-id", "version-id-value"))
                .build();
        CompleteMultipartUploadResult result = SerdeObjectMultipart.toCompleteMultipartUpload(output);

        assertThat(result.hashCRC64()).isEqualTo("1234567890123456789");
        assertThat(result.versionId()).isEqualTo("version-id-value");

        CompleteMultipartUploadResultXml resultCompleteMultipartUpload = result.completeMultipartUpload();
        assertThat(resultCompleteMultipartUpload.encodingType()).isEqualTo("url");
        assertThat(resultCompleteMultipartUpload.location()).isEqualTo("http://oss-example.oss-cn-hangzhou.aliyuncs.com /multipart.data");
        assertThat(resultCompleteMultipartUpload.bucket()).isEqualTo("oss-example");
        assertThat(resultCompleteMultipartUpload.key()).isEqualTo("multipart.data");
        assertThat(resultCompleteMultipartUpload.eTag()).isEqualTo("\"B864DB6A936D376F9F8D3ED3BBE540****\"");
    }
}
