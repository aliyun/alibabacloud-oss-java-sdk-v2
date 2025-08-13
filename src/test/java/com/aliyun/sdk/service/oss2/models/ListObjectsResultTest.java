package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.internal.ListBucketResultXml;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketBasic;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ListObjectsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListObjectsResult result = ListObjectsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Owner owner1 = Owner.newBuilder()
                .id("0022012****")
                .displayName("user-example")
                .build();

        ObjectSummary objectSummary1 = ObjectSummary.newBuilder()
                .storageClass("ColdArchive")
                .owner(owner1)
                .key("exampleobject11.txt")
                .lastModified(Instant.parse("2020-06-22T11:42:32.000Z"))
                .eTag("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"")
                .type("Normal")
                .size(344606L)
                .restoreInfo("ongoing-request=\"true\"")
                .build();

        Owner owner2 = Owner.newBuilder()
                .id("0022012****22")
                .displayName("user-example22")
                .build();

        ObjectSummary objectSummary2 = ObjectSummary.newBuilder()
                .storageClass("DeepColdArchive")
                .owner(owner2)
                .key("exampleobject2.txt")
                .lastModified(Instant.parse("2023-12-08T08:12:20.000Z"))
                .eTag("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"")
                .type("Normal2")
                .size(344607L)
                .restoreInfo("ongoing-request=\"false\", expiry-date=\"Sat, 05 Nov 2022 07:38:08 GMT\"")
                .transitionTime(Instant.parse("2023-12-08T08:12:20.000Z"))
                .build();

        CommonPrefix commonPrefix = CommonPrefix.newBuilder()
                .prefix("a/b/")
                .build();

        ListBucketResultXml listBucketResult = new ListBucketResultXml();
        listBucketResult.name = "examplebucket";
        listBucketResult.prefix = "aaa";
        listBucketResult.maxKeys = 100;
        listBucketResult.delimiter = "/";
        listBucketResult.isTruncated = false;
        listBucketResult.encodingType = "url";
        listBucketResult.marker = "CgJiYw--";
        listBucketResult.nextMarker = "NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA";
        listBucketResult.contents = Arrays.asList(objectSummary1, objectSummary2);
        listBucketResult.commonPrefixes = Collections.singletonList(commonPrefix);

        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-request-id", "req-1234567890abcdefg");
        headers.put("ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"");

        ListObjectsResult result = ListObjectsResult.newBuilder()
                .headers(headers)
                .innerBody(listBucketResult)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.name()).isEqualTo("examplebucket");
        assertThat(result.prefix()).isEqualTo("aaa");
        assertThat(result.maxKeys()).isEqualTo(100);
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.marker()).isEqualTo("CgJiYw--");
        assertThat(result.nextMarker()).isEqualTo("NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA");

        List<ObjectSummary> contents = result.contents();
        assertThat(contents).isNotNull();
        assertThat(contents.size()).isEqualTo(2);

        ObjectSummary summary1 = contents.get(0);
        assertThat(summary1.storageClass()).isEqualTo("ColdArchive");
        assertThat(summary1.owner().id()).isEqualTo("0022012****");
        assertThat(summary1.owner().displayName()).isEqualTo("user-example");
        assertThat(summary1.key()).isEqualTo("exampleobject11.txt");
        assertThat(summary1.lastModified()).isEqualTo(Instant.parse("2020-06-22T11:42:32.000Z"));
        assertThat(summary1.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"");
        assertThat(summary1.type()).isEqualTo("Normal");
        assertThat(summary1.size()).isEqualTo(344606L);
        assertThat(summary1.restoreInfo()).isEqualTo("ongoing-request=\"true\"");

        ObjectSummary summary2 = contents.get(1);
        assertThat(summary2.storageClass()).isEqualTo("DeepColdArchive");
        assertThat(summary2.owner().id()).isEqualTo("0022012****22");
        assertThat(summary2.owner().displayName()).isEqualTo("user-example22");
        assertThat(summary2.key()).isEqualTo("exampleobject2.txt");
        assertThat(summary2.lastModified()).isEqualTo(Instant.parse("2023-12-08T08:12:20.000Z"));
        assertThat(summary2.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"");
        assertThat(summary2.type()).isEqualTo("Normal2");
        assertThat(summary2.size()).isEqualTo(344607L);
        assertThat(summary2.restoreInfo()).isEqualTo("ongoing-request=\"false\", expiry-date=\"Sat, 05 Nov 2022 07:38:08 GMT\"");
        assertThat(summary2.transitionTime()).isEqualTo(Instant.parse("2023-12-08T08:12:20.000Z"));

        List<CommonPrefix> prefixes = result.commonPrefixes();
        assertThat(prefixes).isNotNull();
        assertThat(prefixes.size()).isEqualTo(1);
        assertThat(prefixes.get(0).prefix()).isEqualTo("a/b/");
    }

    @Test
    public void testToBuilderPreserveState() {
        Owner owner1 = Owner.newBuilder()
                .id("0022012****")
                .displayName("user-example")
                .build();

        ObjectSummary objectSummary1 = ObjectSummary.newBuilder()
                .storageClass("ColdArchive")
                .owner(owner1)
                .key("exampleobject11.txt")
                .lastModified(Instant.parse("2020-06-22T11:42:32.000Z"))
                .eTag("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"")
                .type("Normal")
                .size(344606L)
                .restoreInfo("ongoing-request=\"true\"")
                .build();

        Owner owner2 = Owner.newBuilder()
                .id("0022012****22")
                .displayName("user-example22")
                .build();

        ObjectSummary objectSummary2 = ObjectSummary.newBuilder()
                .storageClass("DeepColdArchive")
                .owner(owner2)
                .key("exampleobject2.txt")
                .lastModified(Instant.parse("2023-12-08T08:12:20.000Z"))
                .eTag("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"")
                .type("Normal2")
                .size(344607L)
                .restoreInfo("ongoing-request=\"false\", expiry-date=\"Sat, 05 Nov 2022 07:38:08 GMT\"")
                .transitionTime(Instant.parse("2023-12-08T08:12:20.000Z"))
                .build();

        CommonPrefix commonPrefix = CommonPrefix.newBuilder()
                .prefix("a/b/")
                .build();

        ListBucketResultXml originalListBucketResult = new ListBucketResultXml();
        originalListBucketResult.name = "examplebucket";
        originalListBucketResult.prefix = "aaa";
        originalListBucketResult.maxKeys = 100;
        originalListBucketResult.delimiter = "/";
        originalListBucketResult.isTruncated = false;
        originalListBucketResult.encodingType = "url";
        originalListBucketResult.marker = "CgJiYw--";
        originalListBucketResult.nextMarker = "NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA";
        originalListBucketResult.contents = Arrays.asList(objectSummary1, objectSummary2);
        originalListBucketResult.commonPrefixes = Collections.singletonList(commonPrefix);


        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-request-id", "req-765432109876543210");
        headers.put("ETag", "\"original-etag\"");

        ListObjectsResult original = ListObjectsResult.newBuilder()
                .headers(headers)
                .innerBody(originalListBucketResult)
                .build();

        ListObjectsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.name()).isEqualTo("examplebucket");
        assertThat(copy.prefix()).isEqualTo("aaa");
        assertThat(copy.maxKeys()).isEqualTo(100);
        assertThat(copy.delimiter()).isEqualTo("/");
        assertThat(copy.isTruncated()).isEqualTo(false);
        assertThat(copy.encodingType()).isEqualTo("url");
        assertThat(copy.marker()).isEqualTo("CgJiYw--");
        assertThat(copy.nextMarker()).isEqualTo("NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA");

        List<ObjectSummary> contents = copy.contents();
        assertThat(contents).isNotNull();
        assertThat(contents.size()).isEqualTo(2);

        ObjectSummary summary1 = contents.get(0);
        assertThat(summary1.storageClass()).isEqualTo("ColdArchive");
        assertThat(summary1.owner().id()).isEqualTo("0022012****");
        assertThat(summary1.owner().displayName()).isEqualTo("user-example");
        assertThat(summary1.key()).isEqualTo("exampleobject11.txt");
        assertThat(summary1.lastModified()).isEqualTo(Instant.parse("2020-06-22T11:42:32.000Z"));
        assertThat(summary1.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"");
        assertThat(summary1.type()).isEqualTo("Normal");
        assertThat(summary1.size()).isEqualTo(344606L);
        assertThat(summary1.restoreInfo()).isEqualTo("ongoing-request=\"true\"");

        ObjectSummary summary2 = contents.get(1);
        assertThat(summary2.storageClass()).isEqualTo("DeepColdArchive");
        assertThat(summary2.owner().id()).isEqualTo("0022012****22");
        assertThat(summary2.owner().displayName()).isEqualTo("user-example22");
        assertThat(summary2.key()).isEqualTo("exampleobject2.txt");
        assertThat(summary2.lastModified()).isEqualTo(Instant.parse("2023-12-08T08:12:20.000Z"));
        assertThat(summary2.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"");
        assertThat(summary2.type()).isEqualTo("Normal2");
        assertThat(summary2.size()).isEqualTo(344607L);
        assertThat(summary2.restoreInfo()).isEqualTo("ongoing-request=\"false\", expiry-date=\"Sat, 05 Nov 2022 07:38:08 GMT\"");
        assertThat(summary2.transitionTime()).isEqualTo(Instant.parse("2023-12-08T08:12:20.000Z"));

        List<CommonPrefix> prefixes = copy.commonPrefixes();
        assertThat(prefixes).isNotNull();
        assertThat(prefixes.size()).isEqualTo(1);
        assertThat(prefixes.get(0).prefix()).isEqualTo("a/b/");
    }


    @Test
    public void testXmlParsing() throws IOException {
        String xml = "";

        OperationOutput output = new OperationOutput.Builder()
                .body(SerdeUtils.serializeXmlBody(xml))
                .build();

        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // empty XML
        String xmlContent = new String(output.body.toBytes(), StandardCharsets.UTF_8);

        Object innerBody = xmlMapper.readValue(xmlContent, ListBucketResultXml.class);
        ListObjectsResult result = ListObjectsResult.newBuilder()
                .innerBody(innerBody)
                .build();

        assertNotNull(result);

        // full XML
        xml = "<ListBucketResult>\n" +
                "  <Name>examplebucket</Name>\n" +
                "  <Prefix></Prefix>\n" +
                "  <Marker>test1.txt</Marker>\n" +
                "  <MaxKeys>2</MaxKeys>\n" +
                "  <Delimiter></Delimiter>\n" +
                "  <EncodingType>url</EncodingType>\n" +
                "  <IsTruncated>true</IsTruncated>\n" +
                "  <NextMarker>test100.txt</NextMarker>\n" +
                "  <Contents>\n" +
                "    <Key>test10.txt</Key>\n" +
                "    <LastModified>2020-05-26T07:50:18.000Z</LastModified>\n" +
                "    <ETag>\"C4CA4238A0B923820DCC509A6F75****\"</ETag>\n" +
                "    <Type>Normal</Type>\n" +
                "    <Size>1</Size>\n" +
                "    <StorageClass>Standard</StorageClass>\n" +
                "    <Owner>\n" +
                "      <ID>1305433xxx</ID>\n" +
                "      <DisplayName>1305433xxx</DisplayName>\n" +
                "    </Owner>\n" +
                "  </Contents>\n" +
                "  <Contents>\n" +
                "    <Key>test100.txt</Key>\n" +
                "    <LastModified>2020-05-26T07:50:20.012Z</LastModified>\n" +
                "    <ETag>\"C4CA4238A0B923820DCC509A6F75****\"</ETag>\n" +
                "    <Type>Normal</Type>\n" +
                "    <Size>1</Size>\n" +
                "    <StorageClass>Standard</StorageClass>\n" +
                "    <Owner>\n" +
                "      <ID>1305433xxx</ID>\n" +
                "      <DisplayName>1305433xxx</DisplayName>\n" +
                "    </Owner>\n" +
                "  </Contents>\n" +
                "  <CommonPrefixes>\n" +
                "      <Prefix>fun/movie/</Prefix>\n" +
                "  </CommonPrefixes>\n" +
                "</ListBucketResult>";

        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ListBucketResultXml listBucketResult = xmlMapper.readValue(xml, ListBucketResultXml.class);


        assertNotNull(listBucketResult);

        // assert
        assertEquals("examplebucket", listBucketResult.name);
        assertEquals("", listBucketResult.prefix);
        assertEquals("test1.txt", listBucketResult.marker);
        assertEquals(Integer.valueOf(2), listBucketResult.maxKeys);
        assertEquals("", listBucketResult.delimiter);
        assertEquals("url", listBucketResult.encodingType);
        assertEquals(Boolean.TRUE, listBucketResult.isTruncated);
        assertEquals("test100.txt", listBucketResult.nextMarker);

        List<ObjectSummary> contents = listBucketResult.contents;
        assertEquals(2, contents.size());

        ObjectSummary object1 = contents.get(0);
        assertEquals("test10.txt", object1.key());
        assertEquals(Instant.parse("2020-05-26T07:50:18.000Z"), object1.lastModified());
        assertEquals("\"C4CA4238A0B923820DCC509A6F75****\"", object1.eTag());
        assertEquals("Normal", object1.type());
        assertEquals(Long.valueOf(1), object1.size());
        assertEquals("Standard", object1.storageClass());
        assertNotNull(object1.owner());
        assertEquals("1305433xxx", object1.owner().id());
        assertEquals("1305433xxx", object1.owner().displayName());

        ObjectSummary object2 = contents.get(1);
        assertEquals("test100.txt", object2.key());
        assertEquals(Instant.parse("2020-05-26T07:50:20.012Z"), object2.lastModified());
        assertEquals("\"C4CA4238A0B923820DCC509A6F75****\"", object2.eTag());
        assertEquals("Normal", object2.type());
        assertEquals(Long.valueOf(1), object2.size());
        assertEquals("Standard", object2.storageClass());
        assertNotNull(object2.owner());
        assertEquals("1305433xxx", object2.owner().id());
        assertEquals("1305433xxx", object2.owner().displayName());

        List<CommonPrefix> commonPrefixes = listBucketResult.commonPrefixes;
        assertEquals(1, commonPrefixes.size());
        assertEquals("fun/movie/", commonPrefixes.get(0).prefix());
    }

    @Test
    public void testXmlBuilderWithEncoding() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeBucketBasic.toListObjects(blankOutput);


        String xml =
                "<ListBucketResult>\n" +
                        "    <Name>examplebucket</Name>\n" +
                        "    <Prefix>folder%2Fsub-folder</Prefix>\n" +
                        "    <Marker></Marker>\n" +
                        "    <MaxKeys>100</MaxKeys>\n" +
                        "    <Delimiter>%2F</Delimiter>\n" +
                        "    <IsTruncated>false</IsTruncated>\n" +
                        "    <EncodingType>url</EncodingType>\n" +
                        "    <Contents>\n" +
                        "        <Key>folder%2Fsub-folder%2Ftest%20image%23.jpg</Key>\n" +
                        "        <LastModified>2020-05-26T07:50:18.000Z</LastModified>\n" +
                        "        <ETag>\"C4CA4238A0B923820DCC509A6F75****\"</ETag>\n" +
                        "        <Type>Normal</Type>\n" +
                        "        <Size>1024</Size>\n" +
                        "        <StorageClass>Standard</StorageClass>\n" +
                        "        <Owner>\n" +
                        "            <ID>1305433xxx</ID>\n" +
                        "            <DisplayName>1305433xxx</DisplayName>\n" +
                        "        </Owner>\n" +
                        "    </Contents>\n" +
                        "    <CommonPrefixes>\n" +
                        "        <Prefix>folder%2Fprefix%2F</Prefix>\n" +
                        "    </CommonPrefixes>\n" +
                        "</ListBucketResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        ListObjectsResult result = SerdeBucketBasic.toListObjects(output);

        assertThat(result.name()).isEqualTo("examplebucket");
        assertThat(result.prefix()).isEqualTo("folder/sub-folder");
        assertThat(result.marker()).isEqualTo("");
        assertThat(result.maxKeys()).isEqualTo(100);
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.isTruncated()).isEqualTo(false);

        List<ObjectSummary> contents = result.contents();
        assertThat(contents).hasSize(1);
        ObjectSummary objectSummary = contents.get(0);
        assertThat(objectSummary.key()).isEqualTo("folder/sub-folder/test image#.jpg");
        assertThat(objectSummary.lastModified()).isEqualTo(Instant.parse("2020-05-26T07:50:18.000Z"));
        assertThat(objectSummary.eTag()).isEqualTo("\"C4CA4238A0B923820DCC509A6F75****\"");
        assertThat(objectSummary.type()).isEqualTo("Normal");
        assertThat(objectSummary.size()).isEqualTo(1024L);
        assertThat(objectSummary.storageClass()).isEqualTo("Standard");

        List<CommonPrefix> commonPrefixes = result.commonPrefixes();
        assertThat(commonPrefixes).hasSize(1);
        CommonPrefix commonPrefix = commonPrefixes.get(0);
        assertThat(commonPrefix.prefix()).isEqualTo("folder/prefix/");
    }
}
