package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.internal.ListVersionsResultXml;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketVersioning;
import com.aliyun.sdk.service.oss2.transform.SerdeService;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListObjectVersionsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListObjectVersionsResult result = ListObjectVersionsResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        Owner owner = Owner.newBuilder()
                .id("1283641064516515")
                .displayName("1283641064516515")
                .build();

        ObjectVersion version1 = ObjectVersion.newBuilder()
                .key("prefix-测试中文-/file2.txt")
                .versionId("CAEQehiBgID98azQwxkiIDA2OWY1OTUyZDk0OTQ2NmNhYzY5MWQ0M2Y0ODBmMzBk")
                .isLatest(false)
                .lastModified(Instant.parse("2025-08-04T07:58:04.000Z"))
                .eTag("\"22AAFB9DF4E1A3FA266DA17AAAC6B0B7\"")
                .size(18L)
                .storageClass("Standard")
                .owner(owner)
                .build();

        ObjectVersion version2 = ObjectVersion.newBuilder()
                .key("special-key-!@#$%^&*()_+{}|:<>?[];',./`~")
                .versionId("CAEQehiBgIDO8azQwxkiIDUyNDZmYTQzYTcwYTQ4YmQ5YjVlY2JlZTBiZTdmMzFh")
                .isLatest(false)
                .lastModified(Instant.parse("2025-08-04T07:58:04.000Z"))
                .eTag("\"D07E56733A818404B513816A698EFD21\"")
                .size(11L)
                .storageClass("Standard")
                .owner(owner)
                .build();

        ObjectVersion version3 = ObjectVersion.newBuilder()
                .key("special-key-!@#$%^&*()_+{}|:<>?[];',./`~")
                .versionId("CAEQehiBgMC_8azQwxkiIDQ3NmZkZmY4ZGU3NjQyODJhZWU3OTkxMTRmZjRlNzhj")
                .isLatest(false)
                .lastModified(Instant.parse("2025-08-04T07:58:04.000Z"))
                .eTag("\"02D3E01BFD63A8379ABB4A6CF48E4486\"")
                .size(11L)
                .storageClass("Standard")
                .owner(owner)
                .build();

        ObjectVersion version4 = ObjectVersion.newBuilder()
                .key("special-key-测试中文字符")
                .versionId("CAEQehiBgMDc8azQwxkiIDkzMDVhNmM4NjEwYjQ3NmFiYTkxZjdhZDdkODI2OGM5")
                .isLatest(false)
                .lastModified(Instant.parse("2025-08-04T07:58:04.000Z"))
                .eTag("\"82B76297B8DA475080D94E10071DCA5A\"")
                .size(11L)
                .storageClass("Standard")
                .owner(owner)
                .build();

        DeleteMarkerEntry deleteMarker1 = DeleteMarkerEntry.newBuilder()
                .key("special-key-!@#$%^&*()_+{}|:<>?[];',./`~")
                .versionId("CAEQehiCgMDBjq3QwxkiIDFiMzgwMzJlOTI0MTRjMThiODQwZmI1ZTYyODY1ZDky")
                .isLatest(true)
                .lastModified(Instant.parse("2025-08-04T07:58:12.000Z"))
                .owner(owner)
                .build();

        DeleteMarkerEntry deleteMarker2 = DeleteMarkerEntry.newBuilder()
                .key("special-key-测试中文字符")
                .versionId("CAEQehiCgIDJjq3QwxkiIGExM2RiMmVkNzA3MTRmNzE4NmRkOTgxOTEyY2JlYThl")
                .isLatest(true)
                .lastModified(Instant.parse("2025-08-04T07:58:12.000Z"))
                .owner(owner)
                .build();

        CommonPrefix commonPrefix = CommonPrefix.newBuilder()
                .prefix("test/")
                .build();

        List<ObjectVersion> versions = Arrays.asList(version1, version2, version3, version4);
        List<DeleteMarkerEntry> deleteMarkers = Arrays.asList(deleteMarker1, deleteMarker2);
        List<CommonPrefix> commonPrefixes = Arrays.asList(commonPrefix);

        ListVersionsResultXml listVersionsResultXml = new ListVersionsResultXml();
        listVersionsResultXml.name = "java-sdk-test-bucket-1754294276-175-1226";
        listVersionsResultXml.prefix = "";
        listVersionsResultXml.keyMarker = "";
        listVersionsResultXml.versionIdMarker = "";
        listVersionsResultXml.maxKeys = 100L;
        listVersionsResultXml.delimiter = "%2F";
        listVersionsResultXml.encodingType = "url";
        listVersionsResultXml.isTruncated = false;
        listVersionsResultXml.nextKeyMarker = null;
        listVersionsResultXml.nextVersionIdMarker = null;
        listVersionsResultXml.versions = versions;
        listVersionsResultXml.deleteMarkers = deleteMarkers;
        listVersionsResultXml.commonPrefixes = commonPrefixes;

        ListObjectVersionsResult result = ListObjectVersionsResult.newBuilder()
                .headers(headers)
                .innerBody(listVersionsResultXml)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.name()).isEqualTo("java-sdk-test-bucket-1754294276-175-1226");
        assertThat(result.prefix()).isEqualTo("");
        assertThat(result.keyMarker()).isEqualTo("");
        assertThat(result.versionIdMarker()).isEqualTo("");
        assertThat(result.maxKeys()).isEqualTo(100L);
        assertThat(result.delimiter()).isEqualTo("%2F");
        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.nextKeyMarker()).isNull();
        assertThat(result.nextVersionIdMarker()).isNull();

        assertThat(result.versions()).hasSize(4);
        assertThat(result.deleteMarkers()).hasSize(2);
        assertThat(result.commonPrefixes()).hasSize(1);

        ObjectVersion resultVersion1 = result.versions().get(0);
        assertThat(resultVersion1.key()).isEqualTo("prefix-测试中文-/file2.txt");
        assertThat(resultVersion1.versionId()).isEqualTo("CAEQehiBgID98azQwxkiIDA2OWY1OTUyZDk0OTQ2NmNhYzY5MWQ0M2Y0ODBmMzBk");
        assertThat(resultVersion1.isLatest()).isEqualTo(false);
        assertThat(resultVersion1.lastModified()).isEqualTo(Instant.parse("2025-08-04T07:58:04.000Z"));
        assertThat(resultVersion1.eTag()).isEqualTo("\"22AAFB9DF4E1A3FA266DA17AAAC6B0B7\"");
        assertThat(resultVersion1.size()).isEqualTo(18L);
        assertThat(resultVersion1.storageClass()).isEqualTo("Standard");
        assertThat(resultVersion1.owner().id()).isEqualTo("1283641064516515");
        assertThat(resultVersion1.owner().displayName()).isEqualTo("1283641064516515");

        ObjectVersion resultVersion2 = result.versions().get(1);
        assertThat(resultVersion2.key()).isEqualTo("special-key-!@#$%^&*()_+{}|:<>?[];',./`~");
        assertThat(resultVersion2.versionId()).isEqualTo("CAEQehiBgIDO8azQwxkiIDUyNDZmYTQzYTcwYTQ4YmQ5YjVlY2JlZTBiZTdmMzFh");
        assertThat(resultVersion2.isLatest()).isEqualTo(false);
        assertThat(resultVersion2.lastModified()).isEqualTo(Instant.parse("2025-08-04T07:58:04.000Z"));
        assertThat(resultVersion2.eTag()).isEqualTo("\"D07E56733A818404B513816A698EFD21\"");
        assertThat(resultVersion2.size()).isEqualTo(11L);
        assertThat(resultVersion2.storageClass()).isEqualTo("Standard");
        assertThat(resultVersion2.owner().id()).isEqualTo("1283641064516515");
        assertThat(resultVersion2.owner().displayName()).isEqualTo("1283641064516515");

        DeleteMarkerEntry resultDeleteMarker1 = result.deleteMarkers().get(0);
        assertThat(resultDeleteMarker1.key()).isEqualTo("special-key-!@#$%^&*()_+{}|:<>?[];',./`~");
        assertThat(resultDeleteMarker1.versionId()).isEqualTo("CAEQehiCgMDBjq3QwxkiIDFiMzgwMzJlOTI0MTRjMThiODQwZmI1ZTYyODY1ZDky");
        assertThat(resultDeleteMarker1.isLatest()).isEqualTo(true);
        assertThat(resultDeleteMarker1.lastModified()).isEqualTo(Instant.parse("2025-08-04T07:58:12.000Z"));
        assertThat(resultDeleteMarker1.owner().id()).isEqualTo("1283641064516515");
        assertThat(resultDeleteMarker1.owner().displayName()).isEqualTo("1283641064516515");

        CommonPrefix resultCommonPrefix = result.commonPrefixes().get(0);
        assertThat(resultCommonPrefix.prefix()).isEqualTo("test/");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        Owner owner = Owner.newBuilder()
                .id("1283641064516515")
                .displayName("1283641064516515")
                .build();

        ObjectVersion version = ObjectVersion.newBuilder()
                .key("prefix-测试中文-/file2.txt")
                .versionId("CAEQehiBgID98azQwxkiIDA2OWY1OTUyZDk0OTQ2NmNhYzY5MWQ0M2Y0ODBmMzBk")
                .isLatest(false)
                .lastModified(Instant.parse("2025-08-04T07:58:04.000Z"))
                .eTag("\"22AAFB9DF4E1A3FA266DA17AAAC6B0B7\"")
                .size(18L)
                .storageClass("Standard")
                .owner(owner)
                .build();

        DeleteMarkerEntry deleteMarker = DeleteMarkerEntry.newBuilder()
                .key("special-key-!@#$%^&*()_+{}|:<>?[];',./`~")
                .versionId("CAEQehiCgMDBjq3QwxkiIDFiMzgwMzJlOTI0MTRjMThiODQwZmI1ZTYyODY1ZDky")
                .isLatest(true)
                .lastModified(Instant.parse("2025-08-04T07:58:12.000Z"))
                .owner(owner)
                .build();

        CommonPrefix commonPrefix = CommonPrefix.newBuilder()
                .prefix("test/")
                .build();

        ListVersionsResultXml listVersionsResultXml = new ListVersionsResultXml();
        listVersionsResultXml.name = "java-sdk-test-bucket-1754294276-175-1226";
        listVersionsResultXml.prefix = "";
        listVersionsResultXml.keyMarker = "";
        listVersionsResultXml.versionIdMarker = "";
        listVersionsResultXml.maxKeys = 100L;
        listVersionsResultXml.delimiter = "%2F";
        listVersionsResultXml.encodingType = "url";
        listVersionsResultXml.isTruncated = false;
        listVersionsResultXml.versions = Arrays.asList(version);
        listVersionsResultXml.deleteMarkers = Arrays.asList(deleteMarker);
        listVersionsResultXml.commonPrefixes = Arrays.asList(commonPrefix);

        ListObjectVersionsResult original = ListObjectVersionsResult.newBuilder()
                .headers(headers)
                .innerBody(listVersionsResultXml)
                .build();

        ListObjectVersionsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.name()).isEqualTo("java-sdk-test-bucket-1754294276-175-1226");
        assertThat(copy.prefix()).isEqualTo("");
        assertThat(copy.keyMarker()).isEqualTo("");
        assertThat(copy.versionIdMarker()).isEqualTo("");
        assertThat(copy.maxKeys()).isEqualTo(100L);
        assertThat(copy.delimiter()).isEqualTo("%2F");
        assertThat(copy.encodingType()).isEqualTo("url");
        assertThat(copy.isTruncated()).isEqualTo(false);

        assertThat(copy.versions()).hasSize(1);
        assertThat(copy.deleteMarkers()).hasSize(1);
        assertThat(copy.commonPrefixes()).hasSize(1);

        ObjectVersion resultVersion = copy.versions().get(0);
        assertThat(resultVersion.key()).isEqualTo("prefix-测试中文-/file2.txt");
        assertThat(resultVersion.versionId()).isEqualTo("CAEQehiBgID98azQwxkiIDA2OWY1OTUyZDk0OTQ2NmNhYzY5MWQ0M2Y0ODBmMzBk");
        assertThat(resultVersion.isLatest()).isEqualTo(false);
        assertThat(resultVersion.lastModified()).isEqualTo(Instant.parse("2025-08-04T07:58:04.000Z"));
        assertThat(resultVersion.eTag()).isEqualTo("\"22AAFB9DF4E1A3FA266DA17AAAC6B0B7\"");
        assertThat(resultVersion.size()).isEqualTo(18L);
        assertThat(resultVersion.storageClass()).isEqualTo("Standard");
        assertThat(resultVersion.owner().id()).isEqualTo("1283641064516515");
        assertThat(resultVersion.owner().displayName()).isEqualTo("1283641064516515");

        DeleteMarkerEntry resultDeleteMarker = copy.deleteMarkers().get(0);
        assertThat(resultDeleteMarker.key()).isEqualTo("special-key-!@#$%^&*()_+{}|:<>?[];',./`~");
        assertThat(resultDeleteMarker.versionId()).isEqualTo("CAEQehiCgMDBjq3QwxkiIDFiMzgwMzJlOTI0MTRjMThiODQwZmI1ZTYyODY1ZDky");
        assertThat(resultDeleteMarker.isLatest()).isEqualTo(true);
        assertThat(resultDeleteMarker.lastModified()).isEqualTo(Instant.parse("2025-08-04T07:58:12.000Z"));
        assertThat(resultDeleteMarker.owner().id()).isEqualTo("1283641064516515");
        assertThat(resultDeleteMarker.owner().displayName()).isEqualTo("1283641064516515");

        CommonPrefix resultCommonPrefix = copy.commonPrefixes().get(0);
        assertThat(resultCommonPrefix.prefix()).isEqualTo("test/");
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeBucketVersioning.toListObjectVersions(blankOutput);


        String xml =
                "<ListVersionsResult>\n" +
                        "  <Name>java-sdk-test-bucket-1754294276-175-1226</Name>\n" +
                        "  <Prefix></Prefix>\n" +
                        "  <KeyMarker></KeyMarker>\n" +
                        "  <VersionIdMarker></VersionIdMarker>\n" +
                        "  <MaxKeys>100</MaxKeys>\n" +
                        "  <Delimiter>%2F</Delimiter>\n" +
                        "  <EncodingType>url</EncodingType>\n" +
                        "  <IsTruncated>false</IsTruncated>\n" +
                        "  <Version>\n" +
                        "    <Key>prefix-%E6%B5%8B%E8%AF%95%E4%B8%AD%E6%96%87-%2Ffile2.txt</Key>\n" +
                        "    <VersionId>CAEQehiBgID98azQwxkiIDA2OWY1OTUyZDk0OTQ2NmNhYzY5MWQ0M2Y0ODBmMzBk</VersionId>\n" +
                        "    <IsLatest>false</IsLatest>\n" +
                        "    <LastModified>2025-08-04T07:58:04.000Z</LastModified>\n" +
                        "    <ETag>\"22AAFB9DF4E1A3FA266DA17AAAC6B0B7\"</ETag>\n" +
                        "    <Type>Normal</Type>\n" +
                        "    <Size>18</Size>\n" +
                        "    <StorageClass>Standard</StorageClass>\n" +
                        "    <Owner>\n" +
                        "      <ID>1283641064516515</ID>\n" +
                        "      <DisplayName>1283641064516515</DisplayName>\n" +
                        "    </Owner>\n" +
                        "  </Version>\n" +
                        "  <DeleteMarker>\n" +
                        "    <Key>special-key-%21%40%23%24%25%5E%26%2A%28%29_%2B%7B%7D%7C%3A%3C%3E%3F%5B%5D%3B%27%2C.%2F%60~</Key>\n" +
                        "    <VersionId>CAEQehiCgMDBjq3QwxkiIDFiMzgwMzJlOTI0MTRjMThiODQwZmI1ZTYyODY1ZDky</VersionId>\n" +
                        "    <IsLatest>true</IsLatest>\n" +
                        "    <LastModified>2025-08-04T07:58:12.000Z</LastModified>\n" +
                        "    <Owner>\n" +
                        "      <ID>1283641064516515</ID>\n" +
                        "      <DisplayName>1283641064516515</DisplayName>\n" +
                        "    </Owner>\n" +
                        "  </DeleteMarker>\n" +
                        "  <Version>\n" +
                        "    <Key>special-key-%21%40%23%24%25%5E%26%2A%28%29_%2B%7B%7D%7C%3A%3C%3E%3F%5B%5D%3B%27%2C.%2F%60~</Key>\n" +
                        "    <VersionId>CAEQehiBgIDO8azQwxkiIDUyNDZmYTQzYTcwYTQ4YmQ5YjVlY2JlZTBiZTdmMzFh</VersionId>\n" +
                        "    <IsLatest>false</IsLatest>\n" +
                        "    <LastModified>2025-08-04T07:58:04.000Z</LastModified>\n" +
                        "    <ETag>\"D07E56733A818404B513816A698EFD21\"</ETag>\n" +
                        "    <Type>Normal</Type>\n" +
                        "    <Size>11</Size>\n" +
                        "    <StorageClass>Standard</StorageClass>\n" +
                        "    <Owner>\n" +
                        "      <ID>1283641064516515</ID>\n" +
                        "      <DisplayName>1283641064516515</DisplayName>\n" +
                        "    </Owner>\n" +
                        "  </Version>\n" +
                        "  <Version>\n" +
                        "    <Key>special-key-%21%40%23%24%25%5E%26%2A%28%29_%2B%7B%7D%7C%3A%3C%3E%3F%5B%5D%3B%27%2C.%2F%60~</Key>\n" +
                        "    <VersionId>CAEQehiBgMC_8azQwxkiIDQ3NmZkZmY4ZGU3NjQyODJhZWU3OTkxMTRmZjRlNzhj</VersionId>\n" +
                        "    <IsLatest>false</IsLatest>\n" +
                        "    <LastModified>2025-08-04T07:58:04.000Z</LastModified>\n" +
                        "    <ETag>\"02D3E01BFD63A8379ABB4A6CF48E4486\"</ETag>\n" +
                        "    <Type>Normal</Type>\n" +
                        "    <Size>11</Size>\n" +
                        "    <StorageClass>Standard</StorageClass>\n" +
                        "    <Owner>\n" +
                        "      <ID>1283641064516515</ID>\n" +
                        "      <DisplayName>1283641064516515</DisplayName>\n" +
                        "    </Owner>\n" +
                        "  </Version>\n" +
                        "  <DeleteMarker>\n" +
                        "    <Key>special-key-%E6%B5%8B%E8%AF%95%E4%B8%AD%E6%96%87%E5%AD%97%E7%AC%A6</Key>\n" +
                        "    <VersionId>CAEQehiCgIDJjq3QwxkiIGExM2RiMmVkNzA3MTRmNzE4NmRkOTgxOTEyY2JlYThl</VersionId>\n" +
                        "    <IsLatest>true</IsLatest>\n" +
                        "    <LastModified>2025-08-04T07:58:12.000Z</LastModified>\n" +
                        "    <Owner>\n" +
                        "      <ID>1283641064516515</ID>\n" +
                        "      <DisplayName>1283641064516515</DisplayName>\n" +
                        "    </Owner>\n" +
                        "  </DeleteMarker>\n" +
                        "  <Version>\n" +
                        "    <Key>special-key-%E6%B5%8B%E8%AF%95%E4%B8%AD%E6%96%87%E5%AD%97%E7%AC%A6</Key>\n" +
                        "    <VersionId>CAEQehiBgMDc8azQwxkiIDkzMDVhNmM4NjEwYjQ3NmFiYTkxZjdhZDdkODI2OGM5</VersionId>\n" +
                        "    <IsLatest>false</IsLatest>\n" +
                        "    <LastModified>2025-08-04T07:58:04.000Z</LastModified>\n" +
                        "    <ETag>\"82B76297B8DA475080D94E10071DCA5A\"</ETag>\n" +
                        "    <Type>Normal</Type>\n" +
                        "    <Size>11</Size>\n" +
                        "    <StorageClass>Standard</StorageClass>\n" +
                        "    <Owner>\n" +
                        "      <ID>1283641064516515</ID>\n" +
                        "      <DisplayName>1283641064516515</DisplayName>\n" +
                        "    </Owner>\n" +
                        "  </Version>\n" +
                        "</ListVersionsResult>";


        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        ListObjectVersionsResult result = SerdeBucketVersioning.toListObjectVersions(output);

        assertThat(result.name()).isEqualTo("java-sdk-test-bucket-1754294276-175-1226");
        assertThat(result.prefix()).isEqualTo("");
        assertThat(result.keyMarker()).isEqualTo("");
        assertThat(result.versionIdMarker()).isEqualTo("");
        assertThat(result.maxKeys()).isEqualTo(100L);
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.nextKeyMarker()).isNull();
        assertThat(result.nextVersionIdMarker()).isNull();

        assertThat(result.versions()).hasSize(4);
        assertThat(result.deleteMarkers()).hasSize(2);
        assertThat(result.commonPrefixes()).isEmpty();

        ObjectVersion resultVersion1 = result.versions().get(0);
        assertThat(resultVersion1.key()).isEqualTo("prefix-测试中文-/file2.txt");
        assertThat(resultVersion1.versionId()).isEqualTo("CAEQehiBgID98azQwxkiIDA2OWY1OTUyZDk0OTQ2NmNhYzY5MWQ0M2Y0ODBmMzBk");
        assertThat(resultVersion1.isLatest()).isEqualTo(false);
        assertThat(resultVersion1.lastModified()).isEqualTo(Instant.parse("2025-08-04T07:58:04.000Z"));
        assertThat(resultVersion1.eTag()).isEqualTo("\"22AAFB9DF4E1A3FA266DA17AAAC6B0B7\"");
        assertThat(resultVersion1.size()).isEqualTo(18L);
        assertThat(resultVersion1.storageClass()).isEqualTo("Standard");
        assertThat(resultVersion1.owner().id()).isEqualTo("1283641064516515");
        assertThat(resultVersion1.owner().displayName()).isEqualTo("1283641064516515");

        ObjectVersion resultVersion2 = result.versions().get(1);
        assertThat(resultVersion2.key()).isEqualTo("special-key-!@#$%^&*()_+{}|:<>?[];',./`~");
        assertThat(resultVersion2.versionId()).isEqualTo("CAEQehiBgIDO8azQwxkiIDUyNDZmYTQzYTcwYTQ4YmQ5YjVlY2JlZTBiZTdmMzFh");
        assertThat(resultVersion2.isLatest()).isEqualTo(false);
        assertThat(resultVersion2.lastModified()).isEqualTo(Instant.parse("2025-08-04T07:58:04.000Z"));
        assertThat(resultVersion2.eTag()).isEqualTo("\"D07E56733A818404B513816A698EFD21\"");
        assertThat(resultVersion2.size()).isEqualTo(11L);
        assertThat(resultVersion2.storageClass()).isEqualTo("Standard");
        assertThat(resultVersion2.owner().id()).isEqualTo("1283641064516515");
        assertThat(resultVersion2.owner().displayName()).isEqualTo("1283641064516515");

        ObjectVersion resultVersion3 = result.versions().get(2);
        assertThat(resultVersion3.key()).isEqualTo("special-key-!@#$%^&*()_+{}|:<>?[];',./`~");
        assertThat(resultVersion3.versionId()).isEqualTo("CAEQehiBgMC_8azQwxkiIDQ3NmZkZmY4ZGU3NjQyODJhZWU3OTkxMTRmZjRlNzhj");
        assertThat(resultVersion3.isLatest()).isEqualTo(false);
        assertThat(resultVersion3.lastModified()).isEqualTo(Instant.parse("2025-08-04T07:58:04.000Z"));
        assertThat(resultVersion3.eTag()).isEqualTo("\"02D3E01BFD63A8379ABB4A6CF48E4486\"");
        assertThat(resultVersion3.size()).isEqualTo(11L);
        assertThat(resultVersion3.storageClass()).isEqualTo("Standard");
        assertThat(resultVersion3.owner().id()).isEqualTo("1283641064516515");
        assertThat(resultVersion3.owner().displayName()).isEqualTo("1283641064516515");

        ObjectVersion resultVersion4 = result.versions().get(3);
        assertThat(resultVersion4.key()).isEqualTo("special-key-测试中文字符");
        assertThat(resultVersion4.versionId()).isEqualTo("CAEQehiBgMDc8azQwxkiIDkzMDVhNmM4NjEwYjQ3NmFiYTkxZjdhZDdkODI2OGM5");
        assertThat(resultVersion4.isLatest()).isEqualTo(false);
        assertThat(resultVersion4.lastModified()).isEqualTo(Instant.parse("2025-08-04T07:58:04.000Z"));
        assertThat(resultVersion4.eTag()).isEqualTo("\"82B76297B8DA475080D94E10071DCA5A\"");
        assertThat(resultVersion4.size()).isEqualTo(11L);
        assertThat(resultVersion4.storageClass()).isEqualTo("Standard");
        assertThat(resultVersion4.owner().id()).isEqualTo("1283641064516515");
        assertThat(resultVersion4.owner().displayName()).isEqualTo("1283641064516515");

        DeleteMarkerEntry resultDeleteMarker1 = result.deleteMarkers().get(0);
        assertThat(resultDeleteMarker1.key()).isEqualTo("special-key-!@#$%^&*()_+{}|:<>?[];',./`~");
        assertThat(resultDeleteMarker1.versionId()).isEqualTo("CAEQehiCgMDBjq3QwxkiIDFiMzgwMzJlOTI0MTRjMThiODQwZmI1ZTYyODY1ZDky");
        assertThat(resultDeleteMarker1.isLatest()).isEqualTo(true);
        assertThat(resultDeleteMarker1.lastModified()).isEqualTo(Instant.parse("2025-08-04T07:58:12.000Z"));
        assertThat(resultDeleteMarker1.owner().id()).isEqualTo("1283641064516515");
        assertThat(resultDeleteMarker1.owner().displayName()).isEqualTo("1283641064516515");

        DeleteMarkerEntry resultDeleteMarker2 = result.deleteMarkers().get(1);
        assertThat(resultDeleteMarker2.key()).isEqualTo("special-key-测试中文字符");
        assertThat(resultDeleteMarker2.versionId()).isEqualTo("CAEQehiCgIDJjq3QwxkiIGExM2RiMmVkNzA3MTRmNzE4NmRkOTgxOTEyY2JlYThl");
        assertThat(resultDeleteMarker2.isLatest()).isEqualTo(true);
        assertThat(resultDeleteMarker2.lastModified()).isEqualTo(Instant.parse("2025-08-04T07:58:12.000Z"));
        assertThat(resultDeleteMarker2.owner().id()).isEqualTo("1283641064516515");
        assertThat(resultDeleteMarker2.owner().displayName()).isEqualTo("1283641064516515");
    }
}
