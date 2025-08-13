package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.internal.ListBucketV2ResultXml;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketBasic;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
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
import static org.junit.Assert.assertNotNull;

public class ListObjectsV2ResultTest {

    @Test
    public void testEmptyBuilder() {
        ListObjectsV2Result result = ListObjectsV2Result.newBuilder().build();

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

        ListBucketV2ResultXml listBucketV2Result = new ListBucketV2ResultXml();
        listBucketV2Result.name = "examplebucket";
        listBucketV2Result.prefix = "aaa";
        listBucketV2Result.maxKeys = 100;
        listBucketV2Result.keyCount = 3;
        listBucketV2Result.delimiter = "/";
        listBucketV2Result.isTruncated = false;
        listBucketV2Result.encodingType = "url";
        listBucketV2Result.continuationToken = "CgJiYw--";
        listBucketV2Result.nextContinuationToken = "NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA";
        listBucketV2Result.contents = Arrays.asList(objectSummary1, objectSummary2);
        listBucketV2Result.commonPrefixes = Collections.singletonList(commonPrefix);
        listBucketV2Result.startAfter = "b";



        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-request-id", "req-1234567890abcdefg");
        headers.put("ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"");

        ListObjectsV2Result result = ListObjectsV2Result.newBuilder()
                .headers(headers)
                .innerBody(listBucketV2Result)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.name()).isEqualTo("examplebucket");
        assertThat(result.prefix()).isEqualTo("aaa");
        assertThat(result.maxKeys()).isEqualTo(100);
        assertThat(result.keyCount()).isEqualTo(3);
        assertThat(result.startAfter()).isEqualTo("b");
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.continuationToken()).isEqualTo("CgJiYw--");
        assertThat(result.nextContinuationToken()).isEqualTo("NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA");

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


        ListBucketV2ResultXml originalListBucketV2Result = new ListBucketV2ResultXml();
        originalListBucketV2Result.name = "examplebucket";
        originalListBucketV2Result.prefix = "aaa";
        originalListBucketV2Result.maxKeys = 100;
        originalListBucketV2Result.keyCount = 3;
        originalListBucketV2Result.delimiter = "/";
        originalListBucketV2Result.isTruncated = false;
        originalListBucketV2Result.encodingType = "url";
        originalListBucketV2Result.continuationToken = "CgJiYw--";
        originalListBucketV2Result.nextContinuationToken = "NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA";
        originalListBucketV2Result.contents = Arrays.asList(objectSummary1, objectSummary2);
        originalListBucketV2Result.commonPrefixes = Collections.singletonList(commonPrefix);
        originalListBucketV2Result.startAfter = "b";

        Map<String, String> headers = new HashMap<>();
        headers.put("x-oss-request-id", "req-765432109876543210");
        headers.put("ETag", "\"original-etag\"");

        ListObjectsV2Result original = ListObjectsV2Result.newBuilder()
                .headers(headers)
                .innerBody(originalListBucketV2Result)
                .build();

        ListObjectsV2Result copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.name()).isEqualTo("examplebucket");
        assertThat(copy.prefix()).isEqualTo("aaa");
        assertThat(copy.maxKeys()).isEqualTo(100);
        assertThat(copy.keyCount()).isEqualTo(3);
        assertThat(copy.startAfter()).isEqualTo("b");
        assertThat(copy.delimiter()).isEqualTo("/");
        assertThat(copy.isTruncated()).isEqualTo(false);
        assertThat(copy.encodingType()).isEqualTo("url");
        assertThat(copy.continuationToken()).isEqualTo("CgJiYw--");
        assertThat(copy.nextContinuationToken()).isEqualTo("NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA");

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

        Object innerBody = xmlMapper.readValue(xmlContent, ListBucketV2ResultXml.class);
        ListObjectsV2Result result = ListObjectsV2Result.newBuilder()
                .innerBody(innerBody)
                .build();

        assertNotNull(result);

        // full XML
        xml =
                "<ListBucketResult>\n" +
                        "  <Name>examplebucket</Name>\n" +
                        "  <Prefix>aaa</Prefix>\n" +
                        "  <MaxKeys>100</MaxKeys>\n" +
                        "  <KeyCount>3</KeyCount>\n" +
                        "  <Delimiter>/</Delimiter>\n" +
                        "  <StartAfter>b</StartAfter>\n" +
                        "  <IsTruncated>false</IsTruncated>\n" +
                        "  <EncodingType>url</EncodingType>\n" +
                        "  <ContinuationToken>CgJiYw--</ContinuationToken>\n" +
                        "  <NextContinuationToken>NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA</NextContinuationToken>\n" +
                        "  <Contents>\n" +
                        "    <Key>exampleobject11.txt</Key>\n" +
                        "    <LastModified>2020-06-22T11:42:32.000Z</LastModified>\n" +
                        "    <ETag>\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"</ETag>\n" +
                        "    <Type>Normal</Type>\n" +
                        "    <Size>344606</Size>\n" +
                        "    <StorageClass>ColdArchive</StorageClass>\n" +
                        "    <Owner>\n" +
                        "      <ID>0022012****</ID>\n" +
                        "      <DisplayName>user-example</DisplayName>\n" +
                        "    </Owner>\n" +
                        "    <RestoreInfo>ongoing-request=\"true\"</RestoreInfo>\n" +
                        "  </Contents>\n" +
                        "  <Contents>\n" +
                        "    <Key>exampleobject2.txt</Key>\n" +
                        "    <LastModified>2023-12-08T08:12:20.000Z</LastModified>\n" +
                        "    <ETag>\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"</ETag>\n" +
                        "    <Type>Normal2</Type>\n" +
                        "    <Size>344607</Size>\n" +
                        "    <StorageClass>DeepColdArchive</StorageClass>\n" +
                        "    <Owner>\n" +
                        "      <ID>0022012****22</ID>\n" +
                        "      <DisplayName>user-example22</DisplayName>\n" +
                        "    </Owner>\n" +
                        "    <RestoreInfo>ongoing-request=\"false\", expiry-date=\"Sat, 05 Nov 2022 07:38:08 GMT\"</RestoreInfo>\n" +
                        "    <TransitionTime>2023-12-08T08:12:20.000Z</TransitionTime>\n" +
                        "  </Contents>\n" +
                        "  <CommonPrefixes>\n" +
                        "    <Prefix>a/b/</Prefix>\n" +
                        "  </CommonPrefixes>\n" +
                        "</ListBucketResult>";


        innerBody = xmlMapper.readValue(xml, ListBucketV2ResultXml.class);
        result = ListObjectsV2Result.newBuilder()
                .innerBody(innerBody)
                .build();

        assertThat(result.name()).isEqualTo("examplebucket");
        assertThat(result.prefix()).isEqualTo("aaa");
        assertThat(result.maxKeys()).isEqualTo(100);
        assertThat(result.keyCount()).isEqualTo(3);
        assertThat(result.startAfter()).isEqualTo("b");
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.continuationToken()).isEqualTo("CgJiYw--");
        assertThat(result.nextContinuationToken()).isEqualTo("NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA");

        List<ObjectSummary> contents = result.contents();
        assertThat(contents).isNotNull();
        assertThat(contents.size()).isEqualTo(2);

        ObjectSummary summary1 = contents.get(0);
        assertThat(summary1.key()).isEqualTo("exampleobject11.txt");
        assertThat(summary1.lastModified()).isEqualTo(Instant.parse("2020-06-22T11:42:32.000Z"));
        assertThat(summary1.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"");
        assertThat(summary1.type()).isEqualTo("Normal");
        assertThat(summary1.size()).isEqualTo(344606L);
        assertThat(summary1.storageClass()).isEqualTo("ColdArchive");
        assertThat(summary1.owner().id()).isEqualTo("0022012****");
        assertThat(summary1.owner().displayName()).isEqualTo("user-example");
        assertThat(summary1.restoreInfo()).isEqualTo("ongoing-request=\"true\"");

        ObjectSummary summary2 = contents.get(1);
        assertThat(summary2.key()).isEqualTo("exampleobject2.txt");
        assertThat(summary2.lastModified()).isEqualTo(Instant.parse("2023-12-08T08:12:20.000Z"));
        assertThat(summary2.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"");
        assertThat(summary2.type()).isEqualTo("Normal2");
        assertThat(summary2.size()).isEqualTo(344607L);
        assertThat(summary2.storageClass()).isEqualTo("DeepColdArchive");
        assertThat(summary2.owner().id()).isEqualTo("0022012****22");
        assertThat(summary2.owner().displayName()).isEqualTo("user-example22");
        assertThat(summary2.restoreInfo()).isEqualTo("ongoing-request=\"false\", expiry-date=\"Sat, 05 Nov 2022 07:38:08 GMT\"");
        assertThat(summary2.transitionTime()).isEqualTo(Instant.parse("2023-12-08T08:12:20.000Z"));

        List<CommonPrefix> prefixes = result.commonPrefixes();
        assertThat(prefixes).isNotNull();
        assertThat(prefixes.size()).isEqualTo(1);
        assertThat(prefixes.get(0).prefix()).isEqualTo("a/b/");
    }

    @Test
    public void testXmlBuilderWithEncoding() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeBucketBasic.toListObjectsV2(blankOutput);


        String xml =
                "<ListBucketResult>\n" +
                        "    <Name>examplebucket</Name>\n" +
                        "    <Prefix>folder%2Fsub-folder</Prefix>\n" +
                        "    <MaxKeys>100</MaxKeys>\n" +
                        "    <KeyCount>2</KeyCount>\n" +
                        "    <Delimiter>%2F</Delimiter>\n" +
                        "    <StartAfter>prefix%2F</StartAfter>\n" +
                        "    <IsTruncated>false</IsTruncated>\n" +
                        "    <EncodingType>url</EncodingType>\n" +
                        "    <ContinuationToken>%2FCgJiYw--</ContinuationToken>\n" +
                        "    <NextContinuationToken>%2FNextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA</NextContinuationToken>\n" +
                        "    <Contents>\n" +
                        "        <Key>folder%2Fsub-folder%2Ftest%20image%23.jpg</Key>\n" +
                        "        <LastModified>2020-06-22T11:42:32.000Z</LastModified>\n" +
                        "        <ETag>\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"</ETag>\n" +
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
        ListObjectsV2Result result = SerdeBucketBasic.toListObjectsV2(output);

        assertThat(result.name()).isEqualTo("examplebucket");
        assertThat(result.prefix()).isEqualTo("folder/sub-folder");
        assertThat(result.maxKeys()).isEqualTo(100);
        assertThat(result.keyCount()).isEqualTo(2);
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.startAfter()).isEqualTo("prefix/");
        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.continuationToken()).isEqualTo("/CgJiYw--");
        assertThat(result.nextContinuationToken()).isEqualTo("/NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA");

        List<ObjectSummary> contents = result.contents();
        assertThat(contents).hasSize(1);
        ObjectSummary objectSummary = contents.get(0);
        assertThat(objectSummary.key()).isEqualTo("folder/sub-folder/test image#.jpg");
        assertThat(objectSummary.lastModified()).isEqualTo(Instant.parse("2020-06-22T11:42:32.000Z"));
        assertThat(objectSummary.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"");
        assertThat(objectSummary.type()).isEqualTo("Normal");
        assertThat(objectSummary.size()).isEqualTo(1024L);
        assertThat(objectSummary.storageClass()).isEqualTo("Standard");

        List<CommonPrefix> commonPrefixes = result.commonPrefixes();
        assertThat(commonPrefixes).hasSize(1);
        CommonPrefix commonPrefix = commonPrefixes.get(0);
        assertThat(commonPrefix.prefix()).isEqualTo("folder/prefix/");
    }
}
