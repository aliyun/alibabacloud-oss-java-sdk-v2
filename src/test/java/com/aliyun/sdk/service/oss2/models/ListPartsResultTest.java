package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.internal.ListPartResultXml;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectMultipart;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListPartsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListPartsResult result = ListPartsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.bucket()).isNull();
        assertThat(result.key()).isNull();
        assertThat(result.uploadId()).isNull();
        assertThat(result.partNumberMarker()).isNull();
        assertThat(result.nextPartNumberMarker()).isNull();
        assertThat(result.maxParts()).isNull();
        assertThat(result.isTruncated()).isNull();
        assertThat(result.parts()).isEmpty();
        assertThat(result.encodingType()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        Part part1 = Part.newBuilder()
                .partNumber(1L)
                .eTag("\"3349DC700140D7F86A0784842780****\"")
                .build();

        Part part2 = Part.newBuilder()
                .partNumber(2L)
                .eTag("\"3349DC700140D7F86A0784842780****\"")
                .build();

        Part part3 = Part.newBuilder()
                .partNumber(5L)
                .eTag("\"7265F4D211B56873A381D321F586****\"")
                .build();

        List<Part> parts = Arrays.asList(part1, part2, part3);

        ListPartResultXml listPartResultXml = new ListPartResultXml();
        listPartResultXml.bucket = "multipart_upload";
        listPartResultXml.key = "multipart.data";
        listPartResultXml.uploadId = "0004B999EF5A239BB9138C6227D6****";
        listPartResultXml.nextPartNumberMarker = 5L;
        listPartResultXml.maxParts = 1000L;
        listPartResultXml.isTruncated = false;
        listPartResultXml.parts = parts;

        ListPartsResult result = ListPartsResult.newBuilder()
                .headers(headers)
                .innerBody(listPartResultXml)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");

        assertThat(result.bucket()).isEqualTo("multipart_upload");
        assertThat(result.key()).isEqualTo("multipart.data");
        assertThat(result.uploadId()).isEqualTo("0004B999EF5A239BB9138C6227D6****");
        assertThat(result.nextPartNumberMarker()).isEqualTo(5L);
        assertThat(result.maxParts()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);

        assertThat(result.parts()).hasSize(3);

        Part resultPart1 = result.parts().get(0);
        assertThat(resultPart1.partNumber()).isEqualTo(1L);
        assertThat(resultPart1.eTag()).isEqualTo("\"3349DC700140D7F86A0784842780****\"");

        Part resultPart2 = result.parts().get(1);
        assertThat(resultPart2.partNumber()).isEqualTo(2L);
        assertThat(resultPart2.eTag()).isEqualTo("\"3349DC700140D7F86A0784842780****\"");

        Part resultPart3 = result.parts().get(2);
        assertThat(resultPart3.partNumber()).isEqualTo(5L);
        assertThat(resultPart3.eTag()).isEqualTo("\"7265F4D211B56873A381D321F586****\"");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        Part part = Part.newBuilder()
                .partNumber(1L)
                .eTag("\"3349DC700140D7F86A0784842780****\"")
                .build();

        ListPartResultXml listPartResultXml = new ListPartResultXml();
        listPartResultXml.bucket = "multipart_upload";
        listPartResultXml.key = "multipart.data";
        listPartResultXml.uploadId = "0004B999EF5A239BB9138C6227D6****";
        listPartResultXml.nextPartNumberMarker = 5L;
        listPartResultXml.maxParts = 1000L;
        listPartResultXml.isTruncated = false;
        listPartResultXml.parts = Arrays.asList(part);

        ListPartsResult original = ListPartsResult.newBuilder()
                .headers(headers)
                .innerBody(listPartResultXml)
                .build();

        ListPartsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");

        assertThat(copy.bucket()).isEqualTo("multipart_upload");
        assertThat(copy.key()).isEqualTo("multipart.data");
        assertThat(copy.uploadId()).isEqualTo("0004B999EF5A239BB9138C6227D6****");
        assertThat(copy.nextPartNumberMarker()).isEqualTo(5L);
        assertThat(copy.maxParts()).isEqualTo(1000L);
        assertThat(copy.isTruncated()).isEqualTo(false);

        assertThat(copy.parts()).hasSize(1);

        Part resultPart = copy.parts().get(0);
        assertThat(resultPart.partNumber()).isEqualTo(1L);
        assertThat(resultPart.eTag()).isEqualTo("\"3349DC700140D7F86A0784842780****\"");
    }

    @Test
    public void testXmlBuilder() {
        String xml =
                "<ListPartsResult>\n" +
                        "    <Bucket>multipart_upload</Bucket>\n" +
                        "    <Key>multipart.data</Key>\n" +
                        "    <UploadId>0004B999EF5A239BB9138C6227D6****</UploadId>\n" +
                        "    <NextPartNumberMarker>5</NextPartNumberMarker>\n" +
                        "    <MaxParts>1000</MaxParts>\n" +
                        "    <IsTruncated>false</IsTruncated>\n" +
                        "    <Part>\n" +
                        "        <PartNumber>1</PartNumber>\n" +
                        "        <LastModified>2012-02-23T07:01:34.000Z</LastModified>\n" +
                        "        <ETag>\"3349DC700140D7F86A0784842780****\"</ETag>\n" +
                        "        <Size>6291456</Size>\n" +
                        "    </Part>\n" +
                        "    <Part>\n" +
                        "        <PartNumber>2</PartNumber>\n" +
                        "        <LastModified>2012-02-23T07:01:12.000Z</LastModified>\n" +
                        "        <ETag>\"3349DC700140D7F86A0784842780****\"</ETag>\n" +
                        "        <Size>6291456</Size>\n" +
                        "    </Part>\n" +
                        "    <Part>\n" +
                        "        <PartNumber>5</PartNumber>\n" +
                        "        <LastModified>2012-02-23T07:02:03.000Z</LastModified>\n" +
                        "        <ETag>\"7265F4D211B56873A381D321F586****\"</ETag>\n" +
                        "        <Size>1024</Size>\n" +
                        "    </Part>\n" +
                        "</ListPartsResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-1234567890abcdefg"))
                .build();
        ListPartsResult result = SerdeObjectMultipart.toListParts(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");

        assertThat(result.bucket()).isEqualTo("multipart_upload");
        assertThat(result.key()).isEqualTo("multipart.data");
        assertThat(result.uploadId()).isEqualTo("0004B999EF5A239BB9138C6227D6****");
        assertThat(result.nextPartNumberMarker()).isEqualTo(5L);
        assertThat(result.maxParts()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);

        assertThat(result.parts()).hasSize(3);

        Part resultPart1 = result.parts().get(0);
        assertThat(resultPart1.partNumber()).isEqualTo(1L);
        assertThat(resultPart1.eTag()).isEqualTo("\"3349DC700140D7F86A0784842780****\"");

        Part resultPart2 = result.parts().get(1);
        assertThat(resultPart2.partNumber()).isEqualTo(2L);
        assertThat(resultPart2.eTag()).isEqualTo("\"3349DC700140D7F86A0784842780****\"");

        Part resultPart3 = result.parts().get(2);
        assertThat(resultPart3.partNumber()).isEqualTo(5L);
        assertThat(resultPart3.eTag()).isEqualTo("\"7265F4D211B56873A381D321F586****\"");
    }

    @Test
    public void testXmlBuilderWithEncoding() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectMultipart.toListParts(blankOutput);

        String xml =
                "<ListPartsResult>\n" +
                        "    <Bucket>multipart_upload</Bucket>\n" +
                        "    <Key>multipart%2Fdata%23.jpg</Key>\n" +
                        "    <UploadId>0004B999EF5A239BB9138C6227D6****</UploadId>\n" +
                        "    <NextPartNumberMarker>5</NextPartNumberMarker>\n" +
                        "    <MaxParts>1000</MaxParts>\n" +
                        "    <IsTruncated>false</IsTruncated>\n" +
                        "    <EncodingType>url</EncodingType>\n" +
                        "    <Part>\n" +
                        "        <PartNumber>1</PartNumber>\n" +
                        "        <LastModified>2012-02-23T07:01:34.000Z</LastModified>\n" +
                        "        <ETag>\"3349DC700140D7F86A0784842780****\"</ETag>\n" +
                        "        <Size>6291456</Size>\n" +
                        "    </Part>\n" +
                        "    <Part>\n" +
                        "        <PartNumber>2</PartNumber>\n" +
                        "        <LastModified>2012-02-23T07:01:12.000Z</LastModified>\n" +
                        "        <ETag>\"3349DC700140D7F86A0784842780****\"</ETag>\n" +
                        "        <Size>6291456</Size>\n" +
                        "    </Part>\n" +
                        "    <Part>\n" +
                        "        <PartNumber>5</PartNumber>\n" +
                        "        <LastModified>2012-02-23T07:02:03.000Z</LastModified>\n" +
                        "        <ETag>\"7265F4D211B56873A381D321F586****\"</ETag>\n" +
                        "        <Size>1024</Size>\n" +
                        "    </Part>\n" +
                        "</ListPartsResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-1234567890abcdefg"))
                .build();
        ListPartsResult result = SerdeObjectMultipart.toListParts(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");

        assertThat(result.bucket()).isEqualTo("multipart_upload");
        assertThat(result.key()).isEqualTo("multipart/data#.jpg");
        assertThat(result.uploadId()).isEqualTo("0004B999EF5A239BB9138C6227D6****");
        assertThat(result.nextPartNumberMarker()).isEqualTo(5L);
        assertThat(result.maxParts()).isEqualTo(1000L);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isEqualTo("url");

        assertThat(result.parts()).hasSize(3);

        Part resultPart1 = result.parts().get(0);
        assertThat(resultPart1.partNumber()).isEqualTo(1L);
        assertThat(resultPart1.eTag()).isEqualTo("\"3349DC700140D7F86A0784842780****\"");

        Part resultPart2 = result.parts().get(1);
        assertThat(resultPart2.partNumber()).isEqualTo(2L);
        assertThat(resultPart2.eTag()).isEqualTo("\"3349DC700140D7F86A0784842780****\"");

        Part resultPart3 = result.parts().get(2);
        assertThat(resultPart3.partNumber()).isEqualTo(5L);
        assertThat(resultPart3.eTag()).isEqualTo("\"7265F4D211B56873A381D321F586****\"");
    }
}
