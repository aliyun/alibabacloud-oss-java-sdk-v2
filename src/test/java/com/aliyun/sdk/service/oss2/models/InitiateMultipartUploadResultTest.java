package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectMultipart;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class InitiateMultipartUploadResultTest {

    @Test
    public void testEmptyBuilder() {
        InitiateMultipartUploadResult result = InitiateMultipartUploadResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.initiateMultipartUpload()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        InitiateMultipartUpload initiateMultipartUpload = InitiateMultipartUpload.newBuilder()
                .bucket("oss-example")
                .key("multipart.data")
                .uploadId("0004B9894A22E5B1888A1E29F823****")
                .build();

        InitiateMultipartUploadResult result = InitiateMultipartUploadResult.newBuilder()
                .headers(headers)
                .innerBody(initiateMultipartUpload)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        InitiateMultipartUpload resultInitiateMultipartUpload = result.initiateMultipartUpload();
        assertThat(resultInitiateMultipartUpload.bucket()).isEqualTo("oss-example");
        assertThat(resultInitiateMultipartUpload.key()).isEqualTo("multipart.data");
        assertThat(resultInitiateMultipartUpload.uploadId()).isEqualTo("0004B9894A22E5B1888A1E29F823****");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        InitiateMultipartUpload initiateMultipartUpload = InitiateMultipartUpload.newBuilder()
                .bucket("oss-example")
                .key("multipart.data")
                .uploadId("0004B9894A22E5B1888A1E29F823****")
                .build();

        InitiateMultipartUploadResult original = InitiateMultipartUploadResult.newBuilder()
                .headers(headers)
                .innerBody(initiateMultipartUpload)
                .build();

        InitiateMultipartUploadResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        InitiateMultipartUpload copyInitiateMultipartUpload = copy.initiateMultipartUpload();
        assertThat(copyInitiateMultipartUpload.bucket()).isEqualTo("oss-example");
        assertThat(copyInitiateMultipartUpload.key()).isEqualTo("multipart.data");
        assertThat(copyInitiateMultipartUpload.uploadId()).isEqualTo("0004B9894A22E5B1888A1E29F823****");
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String xml =
                "<InitiateMultipartUploadResult xmlns=\"http://doc.oss-cn-hangzhou.aliyuncs.com\">\n" +
                        "    <Bucket>oss-example</Bucket>\n" +
                        "    <Key>multipart.data</Key>\n" +
                        "    <UploadId>0004B9894A22E5B1888A1E29F823****</UploadId>\n" +
                        "</InitiateMultipartUploadResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        InitiateMultipartUploadResult result = SerdeObjectMultipart.toInitiateMultipartUpload(output);


        InitiateMultipartUpload resultInitiateMultipartUpload = result.initiateMultipartUpload();
        assertThat(resultInitiateMultipartUpload.bucket()).isEqualTo("oss-example");
        assertThat(resultInitiateMultipartUpload.key()).isEqualTo("multipart.data");
        assertThat(resultInitiateMultipartUpload.uploadId()).isEqualTo("0004B9894A22E5B1888A1E29F823****");
    }


    @Test
    public void testXmlBuilderWithEncoding() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectMultipart.toCompleteMultipartUpload(blankOutput);

        String xml =
                "<CompleteMultipartUploadResult>\n" +
                        "    <EncodingType>url</EncodingType>\n" +
                        "    <Location>http://oss-example.oss-cn-hangzhou.aliyuncs.com/example%2Fobject%23.jpg</Location>\n" +
                        "    <Bucket>oss-example</Bucket>\n" +
                        "    <Key>example%2Fobject%23.jpg</Key>\n" +
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
        assertThat(resultCompleteMultipartUpload.location()).isEqualTo("http://oss-example.oss-cn-hangzhou.aliyuncs.com/example%2Fobject%23.jpg");
        assertThat(resultCompleteMultipartUpload.bucket()).isEqualTo("oss-example");
        assertThat(resultCompleteMultipartUpload.key()).isEqualTo("example/object#.jpg");
        assertThat(resultCompleteMultipartUpload.eTag()).isEqualTo("\"B864DB6A936D376F9F8D3ED3BBE540****\"");
    }
}
