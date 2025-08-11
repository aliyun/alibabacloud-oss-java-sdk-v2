package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.CompleteMultipartUploadResult;
import com.aliyun.sdk.service.oss2.models.InitiateMultipartUploadResult;
import com.aliyun.sdk.service.oss2.models.ListMultipartUploadsResult;
import com.aliyun.sdk.service.oss2.models.ListPartsResult;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SerdeObjectMultipartTest {

    @Test
    void toInitiateMultipartUpload() {
        // no url encoding type
        String xml = "<InitiateMultipartUploadResult>\n" +
                "  <Bucket>examplebucket</Bucket>\n" +
                "  <Key>exampleobject</Key>\n" +
                "  <UploadId>0004B9894A22E5B1888A1E29F823****</UploadId>\n" +
                "</InitiateMultipartUploadResult>";

        InitiateMultipartUploadResult result = SerdeObjectMultipart.toInitiateMultipartUpload(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.initiateMultipartUpload().bucket()).isEqualTo("examplebucket");
        assertThat(result.initiateMultipartUpload().key()).isEqualTo("exampleobject");
        assertThat(result.initiateMultipartUpload().uploadId()).isEqualTo("0004B9894A22E5B1888A1E29F823****");
        assertThat(result.initiateMultipartUpload().encodingType()).isNull();

        // has url encoding type
        xml = "<InitiateMultipartUploadResult>\n" +
                "  <Bucket>examplebucket</Bucket>\n" +
                "  <Key>example%2Fobject</Key>\n" +
                "  <UploadId>0004B9894A22E5B1888A1E29F823****</UploadId>\n" +
                "  <EncodingType>url</EncodingType>\n" +
                "</InitiateMultipartUploadResult>";

        result = SerdeObjectMultipart.toInitiateMultipartUpload(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.initiateMultipartUpload().bucket()).isEqualTo("examplebucket");
        assertThat(result.initiateMultipartUpload().key()).isEqualTo("example/object");
        assertThat(result.initiateMultipartUpload().uploadId()).isEqualTo("0004B9894A22E5B1888A1E29F823****");
        assertThat(result.initiateMultipartUpload().encodingType()).isEqualTo("url");
    }

    @Test
    void toCompleteMultipartUpload() {
        // no url encoding type
        String xml = "<CompleteMultipartUploadResult>\n" +
                "  <Location>http://examplebucket.oss-cn-hangzhou.aliyuncs.com/exampleobject</Location>\n" +
                "  <Bucket>examplebucket</Bucket>\n" +
                "  <Key>exampleobject</Key>\n" +
                "  <ETag>\"3858F62230AC3C915F300C664312C11F-8\"</ETag>\n" +
                "</CompleteMultipartUploadResult>";

        String versionId = "CAEQMhiBgIC4hZ2aqRciIDQzYjc4NDY5NzBiYjQ5MzVjNzVmMTAwMTdl****";
        String crc64 = "40923849838498493****";
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("x-oss-version-id", versionId);
        headers.put("x-oss-hash-crc64ecma", crc64);

        CompleteMultipartUploadResult result = SerdeObjectMultipart.toCompleteMultipartUpload(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(headers)
                        .statusCode(200)
                        .build());

        assertThat(result.completeMultipartUpload().location()).isEqualTo("http://examplebucket.oss-cn-hangzhou.aliyuncs.com/exampleobject");
        assertThat(result.completeMultipartUpload().bucket()).isEqualTo("examplebucket");
        assertThat(result.completeMultipartUpload().key()).isEqualTo("exampleobject");
        assertThat(result.completeMultipartUpload().eTag()).isEqualTo("\"3858F62230AC3C915F300C664312C11F-8\"");
        assertThat(result.completeMultipartUpload().encodingType()).isNull();
        assertThat(result.versionId()).isEqualTo(versionId);
        assertThat(result.hashCRC64()).isEqualTo(crc64);

        // has url encoding type
        xml = "<CompleteMultipartUploadResult>\n" +
                "  <Location>http://examplebucket.oss-cn-hangzhou.aliyuncs.com/example%2Fobject</Location>\n" +
                "  <Bucket>examplebucket</Bucket>\n" +
                "  <Key>example%2Fobject</Key>\n" +
                "  <ETag>\"3858F62230AC3C915F300C664312C11F-8\"</ETag>\n" +
                "  <EncodingType>url</EncodingType>\n" +
                "</CompleteMultipartUploadResult>";

        result = SerdeObjectMultipart.toCompleteMultipartUpload(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(headers)
                        .statusCode(200)
                        .build());

        assertThat(result.completeMultipartUpload().location()).isEqualTo("http://examplebucket.oss-cn-hangzhou.aliyuncs.com/example%2Fobject");
        assertThat(result.completeMultipartUpload().bucket()).isEqualTo("examplebucket");
        assertThat(result.completeMultipartUpload().key()).isEqualTo("example/object");
        assertThat(result.completeMultipartUpload().eTag()).isEqualTo("\"3858F62230AC3C915F300C664312C11F-8\"");
        assertThat(result.completeMultipartUpload().encodingType()).isEqualTo("url");
        assertThat(result.versionId()).isEqualTo(versionId);
        assertThat(result.hashCRC64()).isEqualTo(crc64);
    }

    @Test
    void toListMultipartUploads() {
        // no url encoding type
        String xml = "<ListMultipartUploadsResult>\n" +
                "  <Bucket>examplebucket</Bucket>\n" +
                "  <KeyMarker>examplekey</KeyMarker>\n" +
                "  <UploadIdMarker>exampleid</UploadIdMarker>\n" +
                "  <NextKeyMarker>nextkey</NextKeyMarker>\n" +
                "  <NextUploadIdMarker>nextid</NextUploadIdMarker>\n" +
                "  <Delimiter>/</Delimiter>\n" +
                "  <Prefix>prefix</Prefix>\n" +
                "  <MaxUploads>1000</MaxUploads>\n" +
                "  <IsTruncated>false</IsTruncated>\n" +
                "  <Upload>\n" +
                "    <Key>exampleobject</Key>\n" +
                "    <UploadId>0004B9894A22E5B1888A1E29F823****</UploadId>\n" +
                "    <Initiated>2020-05-11T08:36:13.000Z</Initiated>\n" +
                "  </Upload>\n" +
                "  <Upload>\n" +
                "    <Key>testobject</Key>\n" +
                "    <UploadId>1234B9894A22E5B1888A1E29F823****</UploadId>\n" +
                "    <Initiated>2020-05-12T08:36:13.000Z</Initiated>\n" +
                "  </Upload>\n" +
                "</ListMultipartUploadsResult>";

        ListMultipartUploadsResult result = SerdeObjectMultipart.toListMultipartUploads(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.bucket()).isEqualTo("examplebucket");
        assertThat(result.keyMarker()).isEqualTo("examplekey");
        assertThat(result.uploadIdMarker()).isEqualTo("exampleid");
        assertThat(result.nextKeyMarker()).isEqualTo("nextkey");
        assertThat(result.nextUploadIdMarker()).isEqualTo("nextid");
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.prefix()).isEqualTo("prefix");
        assertThat(result.maxUploads()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isNull();
        assertThat(result.uploads()).hasSize(2);
        assertThat(result.uploads().get(0).key()).isEqualTo("exampleobject");
        assertThat(result.uploads().get(0).uploadId()).isEqualTo("0004B9894A22E5B1888A1E29F823****");
        assertThat(result.uploads().get(1).key()).isEqualTo("testobject");
        assertThat(result.uploads().get(1).uploadId()).isEqualTo("1234B9894A22E5B1888A1E29F823****");

        // has url encoding type
        xml = "<ListMultipartUploadsResult>\n" +
                "  <Bucket>examplebucket</Bucket>\n" +
                "  <KeyMarker>example%2Fkey</KeyMarker>\n" +
                "  <UploadIdMarker>exampleid</UploadIdMarker>\n" +
                "  <NextKeyMarker>next%2Fkey</NextKeyMarker>\n" +
                "  <NextUploadIdMarker>nextid</NextUploadIdMarker>\n" +
                "  <Delimiter>%2F</Delimiter>\n" +
                "  <Prefix>prefix%2F</Prefix>\n" +
                "  <MaxUploads>1000</MaxUploads>\n" +
                "  <IsTruncated>false</IsTruncated>\n" +
                "  <EncodingType>url</EncodingType>\n" +
                "  <Upload>\n" +
                "    <Key>example%2Fobject</Key>\n" +
                "    <UploadId>0004B9894A22E5B1888A1E29F823****</UploadId>\n" +
                "    <Initiated>2020-05-11T08:36:13.000Z</Initiated>\n" +
                "  </Upload>\n" +
                "  <Upload>\n" +
                "    <Key>test%2Fobject</Key>\n" +
                "    <UploadId>1234B9894A22E5B1888A1E29F823****</UploadId>\n" +
                "    <Initiated>2020-05-12T08:36:13.000Z</Initiated>\n" +
                "  </Upload>\n" +
                "</ListMultipartUploadsResult>";

        result = SerdeObjectMultipart.toListMultipartUploads(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.bucket()).isEqualTo("examplebucket");
        assertThat(result.keyMarker()).isEqualTo("example/key");
        assertThat(result.uploadIdMarker()).isEqualTo("exampleid");
        assertThat(result.nextKeyMarker()).isEqualTo("next/key");
        assertThat(result.nextUploadIdMarker()).isEqualTo("nextid");
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.prefix()).isEqualTo("prefix/");
        assertThat(result.maxUploads()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.uploads()).hasSize(2);
        assertThat(result.uploads().get(0).key()).isEqualTo("example/object");
        assertThat(result.uploads().get(0).uploadId()).isEqualTo("0004B9894A22E5B1888A1E29F823****");
        assertThat(result.uploads().get(1).key()).isEqualTo("test/object");
        assertThat(result.uploads().get(1).uploadId()).isEqualTo("1234B9894A22E5B1888A1E29F823****");
    }

    @Test
    void toListParts() {
        // no url encoding type
        String xml = "<ListPartResult>\n" +
                "  <Bucket>examplebucket</Bucket>\n" +
                "  <Key>exampleobject</Key>\n" +
                "  <UploadId>0004B9894A22E5B1888A1E29F823****</UploadId>\n" +
                "  <PartNumberMarker>1</PartNumberMarker>\n" +
                "  <NextPartNumberMarker>3</NextPartNumberMarker>\n" +
                "  <MaxParts>1000</MaxParts>\n" +
                "  <IsTruncated>false</IsTruncated>\n" +
                "  <Part>\n" +
                "    <PartNumber>2</PartNumber>\n" +
                "    <ETag>\"7265F4D211B56873A381D321F586E4A9\"</ETag>\n" +
                "  </Part>\n" +
                "  <Part>\n" +
                "    <PartNumber>3</PartNumber>\n" +
                "    <ETag>\"7265F4D211B56873A381D321F586E4A8\"</ETag>\n" +
                "  </Part>\n" +
                "</ListPartResult>";

        ListPartsResult result = SerdeObjectMultipart.toListParts(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.bucket()).isEqualTo("examplebucket");
        assertThat(result.key()).isEqualTo("exampleobject");
        assertThat(result.uploadId()).isEqualTo("0004B9894A22E5B1888A1E29F823****");
        assertThat(result.partNumberMarker()).isEqualTo(1L);
        assertThat(result.nextPartNumberMarker()).isEqualTo(3L);
        assertThat(result.maxParts()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isNull();
        assertThat(result.parts()).hasSize(2);
        assertThat(result.parts().get(0).partNumber()).isEqualTo(2L);
        assertThat(result.parts().get(0).eTag()).isEqualTo("\"7265F4D211B56873A381D321F586E4A9\"");
        assertThat(result.parts().get(1).partNumber()).isEqualTo(3L);
        assertThat(result.parts().get(1).eTag()).isEqualTo("\"7265F4D211B56873A381D321F586E4A8\"");

        // has url encoding type
        xml = "<ListPartResult>\n" +
                "  <Bucket>examplebucket</Bucket>\n" +
                "  <Key>example%2Fobject</Key>\n" +
                "  <UploadId>0004B9894A22E5B1888A1E29F823****</UploadId>\n" +
                "  <PartNumberMarker>1</PartNumberMarker>\n" +
                "  <NextPartNumberMarker>3</NextPartNumberMarker>\n" +
                "  <MaxParts>1000</MaxParts>\n" +
                "  <IsTruncated>false</IsTruncated>\n" +
                "  <EncodingType>url</EncodingType>\n" +
                "  <Part>\n" +
                "    <PartNumber>2</PartNumber>\n" +
                "    <ETag>\"7265F4D211B56873A381D321F586E4A9\"</ETag>\n" +
                "  </Part>\n" +
                "  <Part>\n" +
                "    <PartNumber>3</PartNumber>\n" +
                "    <ETag>\"7265F4D211B56873A381D321F586E4A8\"</ETag>\n" +
                "  </Part>\n" +
                "</ListPartResult>";

        result = SerdeObjectMultipart.toListParts(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.bucket()).isEqualTo("examplebucket");
        assertThat(result.key()).isEqualTo("example/object");
        assertThat(result.uploadId()).isEqualTo("0004B9894A22E5B1888A1E29F823****");
        assertThat(result.partNumberMarker()).isEqualTo(1L);
        assertThat(result.nextPartNumberMarker()).isEqualTo(3L);
        assertThat(result.maxParts()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.parts()).hasSize(2);
        assertThat(result.parts().get(0).partNumber()).isEqualTo(2L);
        assertThat(result.parts().get(0).eTag()).isEqualTo("\"7265F4D211B56873A381D321F586E4A9\"");
        assertThat(result.parts().get(1).partNumber()).isEqualTo(3L);
        assertThat(result.parts().get(1).eTag()).isEqualTo("\"7265F4D211B56873A381D321F586E4A8\"");
    }

    @Test
    void toListMultipartUploads_ensureList() {
        // no url encoding type
        String xml = "<ListMultipartUploadsResult>\n" +
                "  <Bucket>examplebucket</Bucket>\n" +
                "  <KeyMarker>examplekey</KeyMarker>\n" +
                "  <UploadIdMarker>exampleid</UploadIdMarker>\n" +
                "  <NextKeyMarker>nextkey</NextKeyMarker>\n" +
                "  <NextUploadIdMarker>nextid</NextUploadIdMarker>\n" +
                "  <Delimiter>/</Delimiter>\n" +
                "  <Prefix>prefix</Prefix>\n" +
                "  <MaxUploads>1000</MaxUploads>\n" +
                "  <IsTruncated>false</IsTruncated>\n" +
                "</ListMultipartUploadsResult>";

        ListMultipartUploadsResult result = SerdeObjectMultipart.toListMultipartUploads(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.bucket()).isEqualTo("examplebucket");
        assertThat(result.keyMarker()).isEqualTo("examplekey");
        assertThat(result.uploadIdMarker()).isEqualTo("exampleid");
        assertThat(result.nextKeyMarker()).isEqualTo("nextkey");
        assertThat(result.nextUploadIdMarker()).isEqualTo("nextid");
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.prefix()).isEqualTo("prefix");
        assertThat(result.maxUploads()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isNull();
        assertThat(result.uploads()).isEmpty();
    }


    @Test
    void toListParts_ensureList() {
        // no url encoding type
        String xml = "<ListPartResult>\n" +
                "  <Bucket>examplebucket</Bucket>\n" +
                "  <Key>exampleobject</Key>\n" +
                "  <UploadId>0004B9894A22E5B1888A1E29F823****</UploadId>\n" +
                "  <PartNumberMarker>1</PartNumberMarker>\n" +
                "  <NextPartNumberMarker>3</NextPartNumberMarker>\n" +
                "  <MaxParts>1000</MaxParts>\n" +
                "  <IsTruncated>false</IsTruncated>\n" +
                "</ListPartResult>";

        ListPartsResult result = SerdeObjectMultipart.toListParts(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.bucket()).isEqualTo("examplebucket");
        assertThat(result.key()).isEqualTo("exampleobject");
        assertThat(result.uploadId()).isEqualTo("0004B9894A22E5B1888A1E29F823****");
        assertThat(result.partNumberMarker()).isEqualTo(1L);
        assertThat(result.nextPartNumberMarker()).isEqualTo(3L);
        assertThat(result.maxParts()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isNull();
        assertThat(result.parts()).isEmpty();
    }
}
