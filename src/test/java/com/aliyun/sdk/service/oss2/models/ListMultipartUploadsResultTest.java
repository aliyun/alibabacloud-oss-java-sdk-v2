package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.internal.ListMultipartUploadsResultXml;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectMultipart;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListMultipartUploadsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListMultipartUploadsResult result = ListMultipartUploadsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.bucket()).isNull();
        assertThat(result.keyMarker()).isNull();
        assertThat(result.uploadIdMarker()).isNull();
        assertThat(result.nextKeyMarker()).isNull();
        assertThat(result.nextUploadIdMarker()).isNull();
        assertThat(result.delimiter()).isNull();
        assertThat(result.prefix()).isNull();
        assertThat(result.maxUploads()).isNull();
        assertThat(result.isTruncated()).isNull();
        assertThat(result.uploads()).isEmpty();
        assertThat(result.encodingType()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        Upload upload1 = Upload.newBuilder()
                .key("multipart1.data")
                .uploadId("0004B999EF518A1FE585B0C9360DC4C8")
                .initiated(Instant.parse("2011-02-23T04:18:23.000Z"))
                .build();

        Upload upload2 = Upload.newBuilder()
                .key("multipart2.data")
                .uploadId("F5A239BB9138C6227D6****")
                .initiated(Instant.parse("2012-02-23T04:18:23.000Z"))
                .build();

        Upload upload3 = Upload.newBuilder()
                .key("oss.avi")
                .uploadId("0004B99B8E707874FC2D692FA5D7****")
                .initiated(Instant.parse("2012-02-23T06:14:27.000Z"))
                .build();

        List<Upload> uploads = Arrays.asList(upload1, upload2, upload3);

        ListMultipartUploadsResultXml listMultipartUploadsResultXml = new ListMultipartUploadsResultXml();
        listMultipartUploadsResultXml.bucket = "oss-example";
        listMultipartUploadsResultXml.keyMarker = "sadagfsd";
        listMultipartUploadsResultXml.uploadIdMarker = "eeev34536dd";
        listMultipartUploadsResultXml.nextKeyMarker = "oss.avi";
        listMultipartUploadsResultXml.nextUploadIdMarker = "0004B99B8E707874FC2D692FA5D77D3F";
        listMultipartUploadsResultXml.delimiter = "/";
        listMultipartUploadsResultXml.prefix = "11waa/";
        listMultipartUploadsResultXml.maxUploads = 1000L;
        listMultipartUploadsResultXml.isTruncated = false;
        listMultipartUploadsResultXml.uploads = uploads;
        listMultipartUploadsResultXml.encodingType = null;

        ListMultipartUploadsResult result = ListMultipartUploadsResult.newBuilder()
                .headers(headers)
                .innerBody(listMultipartUploadsResultXml)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");

        assertThat(result.bucket()).isEqualTo("oss-example");
        assertThat(result.keyMarker()).isEqualTo("sadagfsd");
        assertThat(result.uploadIdMarker()).isEqualTo("eeev34536dd");
        assertThat(result.nextKeyMarker()).isEqualTo("oss.avi");
        assertThat(result.nextUploadIdMarker()).isEqualTo("0004B99B8E707874FC2D692FA5D77D3F");
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.prefix()).isEqualTo("11waa/");
        assertThat(result.maxUploads()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isNull();

        assertThat(result.uploads()).hasSize(3);

        Upload resultUpload1 = result.uploads().get(0);
        assertThat(resultUpload1.key()).isEqualTo("multipart1.data");
        assertThat(resultUpload1.uploadId()).isEqualTo("0004B999EF518A1FE585B0C9360DC4C8");
        assertThat(resultUpload1.initiated()).isEqualTo(Instant.parse("2011-02-23T04:18:23.000Z"));

        Upload resultUpload2 = result.uploads().get(1);
        assertThat(resultUpload2.key()).isEqualTo("multipart2.data");
        assertThat(resultUpload2.uploadId()).isEqualTo("F5A239BB9138C6227D6****");
        assertThat(resultUpload2.initiated()).isEqualTo(Instant.parse("2012-02-23T04:18:23.000Z"));

        Upload resultUpload3 = result.uploads().get(2);
        assertThat(resultUpload3.key()).isEqualTo("oss.avi");
        assertThat(resultUpload3.uploadId()).isEqualTo("0004B99B8E707874FC2D692FA5D7****");
        assertThat(resultUpload3.initiated()).isEqualTo(Instant.parse("2012-02-23T06:14:27.000Z"));
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        Upload upload = Upload.newBuilder()
                .key("multipart1.data")
                .uploadId("0004B999EF518A1FE585B0C9360DC4C8")
                .initiated(Instant.parse("2011-02-23T04:18:23.000Z"))
                .build();

        ListMultipartUploadsResultXml listMultipartUploadsResultXml = new ListMultipartUploadsResultXml();
        listMultipartUploadsResultXml.bucket = "oss-example";
        listMultipartUploadsResultXml.keyMarker = "sadagfsd";
        listMultipartUploadsResultXml.uploadIdMarker = "eeev34536dd";
        listMultipartUploadsResultXml.nextKeyMarker = "oss.avi";
        listMultipartUploadsResultXml.nextUploadIdMarker = "0004B99B8E707874FC2D692FA5D77D3F";
        listMultipartUploadsResultXml.delimiter = "/";
        listMultipartUploadsResultXml.prefix = "11waa/";
        listMultipartUploadsResultXml.maxUploads = 1000L;
        listMultipartUploadsResultXml.isTruncated = false;
        listMultipartUploadsResultXml.uploads = Arrays.asList(upload);
        listMultipartUploadsResultXml.encodingType = null;

        ListMultipartUploadsResult original = ListMultipartUploadsResult.newBuilder()
                .headers(headers)
                .innerBody(listMultipartUploadsResultXml)
                .build();

        ListMultipartUploadsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");

        assertThat(copy.bucket()).isEqualTo("oss-example");
        assertThat(copy.keyMarker()).isEqualTo("sadagfsd");
        assertThat(copy.uploadIdMarker()).isEqualTo("eeev34536dd");
        assertThat(copy.nextKeyMarker()).isEqualTo("oss.avi");
        assertThat(copy.nextUploadIdMarker()).isEqualTo("0004B99B8E707874FC2D692FA5D77D3F");
        assertThat(copy.delimiter()).isEqualTo("/");
        assertThat(copy.prefix()).isEqualTo("11waa/");
        assertThat(copy.maxUploads()).isEqualTo(1000L);
        assertThat(copy.isTruncated()).isEqualTo(false);
        assertThat(copy.encodingType()).isNull();

        assertThat(copy.uploads()).hasSize(1);

        Upload resultUpload = copy.uploads().get(0);
        assertThat(resultUpload.key()).isEqualTo("multipart1.data");
        assertThat(resultUpload.uploadId()).isEqualTo("0004B999EF518A1FE585B0C9360DC4C8");
        assertThat(resultUpload.initiated()).isEqualTo(Instant.parse("2011-02-23T04:18:23.000Z"));
    }

    @Test
    public void testXmlBuilder() {
        String xml =
                "<ListMultipartUploadsResult>\n" +
                        "    <Bucket>oss-example</Bucket>\n" +
                        "    <KeyMarker>sadagfsd</KeyMarker>\n" +
                        "    <UploadIdMarker>eeev34536dd</UploadIdMarker>\n" +
                        "    <NextKeyMarker>oss.avi</NextKeyMarker>\n" +
                        "    <NextUploadIdMarker>0004B99B8E707874FC2D692FA5D77D3F</NextUploadIdMarker>\n" +
                        "    <Delimiter>/</Delimiter>\n" +
                        "    <Prefix>11waa/</Prefix>\n" +
                        "    <MaxUploads>1000</MaxUploads>\n" +
                        "    <IsTruncated>false</IsTruncated>\n" +
                        "    <Upload>\n" +
                        "        <Key>multipart1.data</Key>\n" +
                        "        <UploadId>0004B999EF518A1FE585B0C9360DC4C8</UploadId>\n" +
                        "        <Initiated>2011-02-23T04:18:23.000Z</Initiated>\n" +
                        "    </Upload>\n" +
                        "    <Upload>\n" +
                        "        <Key>multipart2.data</Key>\n" +
                        "        <UploadId>F5A239BB9138C6227D6****</UploadId>\n" +
                        "        <Initiated>2012-02-23T04:18:23.000Z</Initiated>\n" +
                        "    </Upload>\n" +
                        "    <Upload>\n" +
                        "        <Key>oss.avi</Key>\n" +
                        "        <UploadId>0004B99B8E707874FC2D692FA5D7****</UploadId>\n" +
                        "        <Initiated>2012-02-23T06:14:27.000Z</Initiated>\n" +
                        "    </Upload>\n" +
                        "</ListMultipartUploadsResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-1234567890abcdefg"))
                .build();
        ListMultipartUploadsResult result = SerdeObjectMultipart.toListMultipartUploads(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");

        assertThat(result.bucket()).isEqualTo("oss-example");
        assertThat(result.keyMarker()).isEqualTo("sadagfsd");
        assertThat(result.uploadIdMarker()).isEqualTo("eeev34536dd");
        assertThat(result.nextKeyMarker()).isEqualTo("oss.avi");
        assertThat(result.nextUploadIdMarker()).isEqualTo("0004B99B8E707874FC2D692FA5D77D3F");
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.prefix()).isEqualTo("11waa/");
        assertThat(result.maxUploads()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);

        assertThat(result.uploads()).hasSize(3);

        Upload resultUpload1 = result.uploads().get(0);
        assertThat(resultUpload1.key()).isEqualTo("multipart1.data");
        assertThat(resultUpload1.uploadId()).isEqualTo("0004B999EF518A1FE585B0C9360DC4C8");
        assertThat(resultUpload1.initiated()).isEqualTo(Instant.parse("2011-02-23T04:18:23.000Z"));

        Upload resultUpload2 = result.uploads().get(1);
        assertThat(resultUpload2.key()).isEqualTo("multipart2.data");
        assertThat(resultUpload2.uploadId()).isEqualTo("F5A239BB9138C6227D6****");
        assertThat(resultUpload2.initiated()).isEqualTo(Instant.parse("2012-02-23T04:18:23.000Z"));

        Upload resultUpload3 = result.uploads().get(2);
        assertThat(resultUpload3.key()).isEqualTo("oss.avi");
        assertThat(resultUpload3.uploadId()).isEqualTo("0004B99B8E707874FC2D692FA5D7****");
        assertThat(resultUpload3.initiated()).isEqualTo(Instant.parse("2012-02-23T06:14:27.000Z"));
    }

    @Test
    public void testXmlBuilderWithEncoding() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectMultipart.toListMultipartUploads(blankOutput);

        String xml =
                "<ListMultipartUploadsResult>\n" +
                        "    <Bucket>oss-example</Bucket>\n" +
                        "    <KeyMarker>example%2Fkey%23.jpg</KeyMarker>\n" +
                        "    <UploadIdMarker>eeev34536dd</UploadIdMarker>\n" +
                        "    <NextKeyMarker>next%2Fkey%23.jpg</NextKeyMarker>\n" +
                        "    <NextUploadIdMarker>0004B99B8E707874FC2D692FA5D77D3F</NextUploadIdMarker>\n" +
                        "    <Delimiter>%2F</Delimiter>\n" +
                        "    <Prefix>prefix%2Fsub%2F</Prefix>\n" +
                        "    <MaxUploads>1000</MaxUploads>\n" +
                        "    <IsTruncated>false</IsTruncated>\n" +
                        "    <EncodingType>url</EncodingType>\n" +
                        "    <Upload>\n" +
                        "        <Key>example%2Fobject%23.jpg</Key>\n" +
                        "        <UploadId>0004B999EF518A1FE585B0C9360DC4C8</UploadId>\n" +
                        "        <Initiated>2011-02-23T04:18:23.000Z</Initiated>\n" +
                        "    </Upload>\n" +
                        "    <Upload>\n" +
                        "        <Key>test%2Fobject%40.png</Key>\n" +
                        "        <UploadId>F5A239BB9138C6227D6****</UploadId>\n" +
                        "        <Initiated>2012-02-23T04:18:23.000Z</Initiated>\n" +
                        "    </Upload>\n" +
                        "</ListMultipartUploadsResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-1234567890abcdefg"))
                .build();
        ListMultipartUploadsResult result = SerdeObjectMultipart.toListMultipartUploads(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");

        assertThat(result.bucket()).isEqualTo("oss-example");
        assertThat(result.keyMarker()).isEqualTo("example/key#.jpg");
        assertThat(result.uploadIdMarker()).isEqualTo("eeev34536dd");
        assertThat(result.nextKeyMarker()).isEqualTo("next/key#.jpg");
        assertThat(result.nextUploadIdMarker()).isEqualTo("0004B99B8E707874FC2D692FA5D77D3F");
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.prefix()).isEqualTo("prefix/sub/");
        assertThat(result.maxUploads()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isEqualTo("url");

        assertThat(result.uploads()).hasSize(2);

        Upload resultUpload1 = result.uploads().get(0);
        assertThat(resultUpload1.key()).isEqualTo("example/object#.jpg");
        assertThat(resultUpload1.uploadId()).isEqualTo("0004B999EF518A1FE585B0C9360DC4C8");
        assertThat(resultUpload1.initiated()).isEqualTo(Instant.parse("2011-02-23T04:18:23.000Z"));

        Upload resultUpload2 = result.uploads().get(1);
        assertThat(resultUpload2.key()).isEqualTo("test/object@.png");
        assertThat(resultUpload2.uploadId()).isEqualTo("F5A239BB9138C6227D6****");
        assertThat(resultUpload2.initiated()).isEqualTo(Instant.parse("2012-02-23T04:18:23.000Z"));
    }
}
