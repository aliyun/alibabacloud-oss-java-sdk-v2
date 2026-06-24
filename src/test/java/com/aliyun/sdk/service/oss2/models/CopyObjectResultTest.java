package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.internal.CopyObjectResultXml;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CopyObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        CopyObjectResult result = CopyObjectResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.versionId()).isNull();
        assertThat(result.hashCrc64()).isNull();
        assertThat(result.serverSideEncryption()).isNull();
        assertThat(result.serverSideDataEncryption()).isNull();
        assertThat(result.serverSideEncryptionKeyId()).isNull();
        assertThat(result.eTag()).isNull();
        assertThat(result.lastModified()).isNull();
        assertThat(result.copySourceVersionId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-copy-source-version-id", "copy-source-version-123");
        headers.put("x-oss-version-id", "version-123");
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");
        headers.put("x-oss-server-side-encryption", "AES256");
        headers.put("x-oss-server-side-data-encryption", "KMS");
        headers.put("x-oss-server-side-encryption-key-id", "key-id-123");

        CopyObjectResultXml xmlBody = new CopyObjectResultXml();
        xmlBody.eTag = "\"F2064A169EE92E9775EE5324D0B1****\"";
        xmlBody.lastModified = "2018-12-28T09:41:56.000Z";

        CopyObjectResult result = CopyObjectResult.newBuilder()
                .headers(headers)
                .innerBody(xmlBody)
                .build();

        assertThat(result.copySourceVersionId()).isEqualTo("copy-source-version-123");
        assertThat(result.versionId()).isEqualTo("version-123");
        assertThat(result.hashCrc64()).isEqualTo("1234567890123456789");
        assertThat(result.serverSideEncryption()).isEqualTo("AES256");
        assertThat(result.serverSideDataEncryption()).isEqualTo("KMS");
        assertThat(result.serverSideEncryptionKeyId()).isEqualTo("key-id-123");
        assertThat(result.eTag()).isEqualTo("\"F2064A169EE92E9775EE5324D0B1****\"");
        assertThat(result.lastModified()).isEqualTo("2018-12-28T09:41:56.000Z");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-copy-source-version-id", "copy-source-version-456");
        headers.put("x-oss-version-id", "version-456");
        headers.put("x-oss-hash-crc64ecma", "9876543210987654321");
        headers.put("x-oss-server-side-encryption", "KMS");
        headers.put("x-oss-server-side-data-encryption", "AES256");
        headers.put("x-oss-server-side-encryption-key-id", "key-id-456");

        CopyObjectResultXml xmlBody = new CopyObjectResultXml();
        xmlBody.eTag = "\"etag-456\"";
        xmlBody.lastModified = "Thu, 15 Oct 2022 00:00:00 GMT";

        CopyObjectResult original = CopyObjectResult.newBuilder()
                .headers(headers)
                .innerBody(xmlBody)
                .build();

        CopyObjectResult copy = original.toBuilder().build();

        assertThat(copy.copySourceVersionId()).isEqualTo("copy-source-version-456");
        assertThat(copy.versionId()).isEqualTo("version-456");
        assertThat(copy.hashCrc64()).isEqualTo("9876543210987654321");
        assertThat(copy.serverSideEncryption()).isEqualTo("KMS");
        assertThat(copy.serverSideDataEncryption()).isEqualTo("AES256");
        assertThat(copy.serverSideEncryptionKeyId()).isEqualTo("key-id-456");
        assertThat(copy.eTag()).isEqualTo("\"etag-456\"");
        assertThat(copy.lastModified()).isEqualTo("Thu, 15 Oct 2022 00:00:00 GMT");
    }

    @Test
    public void testXmlBuilder() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-hash-crc64ecma", "1234567890123456789");
        headers.put("ETag", "\"header-etag\"");
        headers.put("Last-Modified", "Wed, 14 Oct 2022 00:00:00 GMT");

        CopyObjectResultXml xmlBody = new CopyObjectResultXml();
        xmlBody.eTag = "\"body-etag\"";
        xmlBody.lastModified = "2018-12-28T09:41:56.000Z";

        CopyObjectResult result = CopyObjectResult.newBuilder()
                .headers(headers)
                .innerBody(xmlBody)
                .build();

        assertThat(result.hashCrc64()).isEqualTo("1234567890123456789");
        assertThat(result.eTag()).isEqualTo("\"body-etag\"");
        assertThat(result.lastModified()).isEqualTo("2018-12-28T09:41:56.000Z");
    }

    @Test
    public void testHeaderFallback() {
        Map<String, String> headers = new HashMap<>();
        headers.put("ETag", "\"etag-fallback\"");
        headers.put("Last-Modified", "Wed, 14 Oct 2022 00:00:00 GMT");

        CopyObjectResult result = CopyObjectResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.eTag()).isEqualTo("\"etag-fallback\"");
        assertThat(result.lastModified()).isEqualTo("Wed, 14 Oct 2022 00:00:00 GMT");
    }

    @Test
    public void testXmlDeserialization() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<CopyObjectResult>\n" +
                "  <ETag>\"F2064A169EE92E9775EE5324D0B1****\"</ETag>\n" +
                "  <LastModified>2018-12-28T09:41:56.000Z</LastModified>\n" +
                "</CopyObjectResult>";

        CopyObjectResult result = SerdeObjectBasic.toCopyObject(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.eTag()).isEqualTo("\"F2064A169EE92E9775EE5324D0B1****\"");
        assertThat(result.lastModified()).isEqualTo("2018-12-28T09:41:56.000Z");
    }
}
