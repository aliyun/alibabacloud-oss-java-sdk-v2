package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.CommonPrefix;
import com.aliyun.sdk.service.oss2.models.ListObjectsResult;
import com.aliyun.sdk.service.oss2.models.ListObjectsV2Result;
import com.aliyun.sdk.service.oss2.models.ObjectSummary;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class SerdeBucketBasicTest {

    @Test
    public void toListObjects() {
        // no url encoding type
        String xml = "<ListBucketResult>\n" +
                "  <Name>examplebucket</Name>\n" +
                "  <Prefix>prefix</Prefix>\n" +
                "  <Marker>test1.txt</Marker>\n" +
                "  <MaxKeys>2</MaxKeys>\n" +
                "  <Delimiter>/</Delimiter>\n" +
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
                "    <Size>2</Size>\n" +
                "    <StorageClass>IA</StorageClass>\n" +
                "    <Owner>\n" +
                "      <ID>1305433xxx</ID>\n" +
                "      <DisplayName>1305433xxx</DisplayName>\n" +
                "    </Owner>\n" +
                "  </Contents>\n" +
                "  <CommonPrefixes>\n" +
                "      <Prefix>fun/movie/</Prefix>\n" +
                "  </CommonPrefixes>\n" +
                "</ListBucketResult>";

        ListObjectsResult result = SerdeBucketBasic.toListObjects(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.name()).isEqualTo("examplebucket");
        assertThat(result.prefix()).isEqualTo("prefix");
        assertThat(result.marker()).isEqualTo("test1.txt");
        assertThat(result.maxKeys()).isEqualTo(2);
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.encodingType()).isNull();
        assertThat(result.nextMarker()).isEqualTo("test100.txt");
        assertThat(result.contents()).hasSize(2);
        assertThat(result.contents().get(0).key()).isEqualTo("test10.txt");
        assertThat(result.contents().get(0).type()).isEqualTo("Normal");
        assertThat(result.contents().get(0).storageClass()).isEqualTo("Standard");
        assertThat(result.contents().get(0).size()).isEqualTo(1L);
        assertThat(result.contents().get(1).key()).isEqualTo("test100.txt");
        assertThat(result.contents().get(1).storageClass()).isEqualTo("IA");
        assertThat(result.contents().get(1).size()).isEqualTo(2L);
        assertThat(result.commonPrefixes()).hasSize(1);
        assertThat(result.commonPrefixes().get(0).prefix()).isEqualTo("fun/movie/");

        // has url encoding type
        xml = "<ListBucketResult>\n" +
                "  <Name>examplebucket</Name>\n" +
                "  <Prefix>sub%2Fprefix</Prefix>\n" +
                "  <Marker>sub%2Ftest1.txt</Marker>\n" +
                "  <MaxKeys>2</MaxKeys>\n" +
                "  <Delimiter>%2F</Delimiter>\n" +
                "  <EncodingType>url</EncodingType>\n" +
                "  <IsTruncated>true</IsTruncated>\n" +
                "  <NextMarker>sub%2Ftest100.txt</NextMarker>\n" +
                "  <Contents>\n" +
                "    <Key>sub%2Ftest10.txt</Key>\n" +
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
                "    <Key>sub%2Ftest100.txt</Key>\n" +
                "    <LastModified>2020-05-26T07:50:20.012Z</LastModified>\n" +
                "    <ETag>\"C4CA4238A0B923820DCC509A6F75****\"</ETag>\n" +
                "    <Type>Normal</Type>\n" +
                "    <Size>3</Size>\n" +
                "    <StorageClass>IA</StorageClass>\n" +
                "    <Owner>\n" +
                "      <ID>1305433xxx</ID>\n" +
                "      <DisplayName>1305433xxx</DisplayName>\n" +
                "    </Owner>\n" +
                "  </Contents>\n" +
                "  <CommonPrefixes>\n" +
                "      <Prefix>fun%2Fmovie%2F</Prefix>\n" +
                "  </CommonPrefixes>\n" +
                "</ListBucketResult>";

        result = SerdeBucketBasic.toListObjects(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.name()).isEqualTo("examplebucket");
        assertThat(result.prefix()).isEqualTo("sub/prefix");
        assertThat(result.marker()).isEqualTo("sub/test1.txt");
        assertThat(result.maxKeys()).isEqualTo(2);
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.nextMarker()).isEqualTo("sub/test100.txt");
        assertThat(result.contents()).hasSize(2);
        assertThat(result.contents().get(0).key()).isEqualTo("sub/test10.txt");
        assertThat(result.contents().get(0).type()).isEqualTo("Normal");
        assertThat(result.contents().get(0).storageClass()).isEqualTo("Standard");
        assertThat(result.contents().get(0).size()).isEqualTo(1L);
        assertThat(result.contents().get(1).key()).isEqualTo("sub/test100.txt");
        assertThat(result.contents().get(1).storageClass()).isEqualTo("IA");
        assertThat(result.contents().get(1).size()).isEqualTo(3L);
        assertThat(result.commonPrefixes()).hasSize(1);
        assertThat(result.commonPrefixes().get(0).prefix()).isEqualTo("fun/movie/");
    }

    @Test
    public void toListObjects_ensureList() {
        // no url encoding type
        String xml = "<ListBucketResult>\n" +
                "  <Name>examplebucket</Name>\n" +
                "  <Prefix>prefix</Prefix>\n" +
                "  <Marker>test1.txt</Marker>\n" +
                "  <MaxKeys>2</MaxKeys>\n" +
                "  <Delimiter>/</Delimiter>\n" +
                "  <IsTruncated>true</IsTruncated>\n" +
                "  <NextMarker>test100.txt</NextMarker>\n" +
                "</ListBucketResult>";

        ListObjectsResult result = SerdeBucketBasic.toListObjects(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.name()).isEqualTo("examplebucket");
        assertThat(result.prefix()).isEqualTo("prefix");
        assertThat(result.marker()).isEqualTo("test1.txt");
        assertThat(result.maxKeys()).isEqualTo(2);
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.encodingType()).isNull();
        assertThat(result.nextMarker()).isEqualTo("test100.txt");
        for (ObjectSummary ignore: result.contents()) {
            fail("should not here");
        }

        for (CommonPrefix ignore: result.commonPrefixes()) {
            fail("should not here");
        }

    }

    @Test
    public void toListObjectsV2() {

        // no url encoding type
        String xml =
                "<ListBucketResult>\n" +
                        "  <Name>examplebucket</Name>\n" +
                        "  <Prefix>aaa</Prefix>\n" +
                        "  <MaxKeys>100</MaxKeys>\n" +
                        "  <KeyCount>3</KeyCount>\n" +
                        "  <Delimiter>/</Delimiter>\n" +
                        "  <StartAfter>b</StartAfter>\n" +
                        "  <IsTruncated>false</IsTruncated>\n" +
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

        ListObjectsV2Result result = SerdeBucketBasic.toListObjectsV2(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.name()).isEqualTo("examplebucket");
        assertThat(result.prefix()).isEqualTo("aaa");
        assertThat(result.maxKeys()).isEqualTo(100);
        assertThat(result.keyCount()).isEqualTo(3);
        assertThat(result.startAfter()).isEqualTo("b");
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isNull();
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

        // has url encoding type
        xml =
                "<ListBucketResult>\n" +
                        "  <Name>examplebucket</Name>\n" +
                        "  <Prefix>sub%2Faaa</Prefix>\n" +
                        "  <MaxKeys>100</MaxKeys>\n" +
                        "  <KeyCount>3</KeyCount>\n" +
                        "  <Delimiter>%2F</Delimiter>\n" +
                        "  <StartAfter>b</StartAfter>\n" +
                        "  <IsTruncated>false</IsTruncated>\n" +
                        "  <EncodingType>url</EncodingType>\n" +
                        "  <ContinuationToken>%2FCgJiYw--</ContinuationToken>\n" +
                        "  <NextContinuationToken>%2FNextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA</NextContinuationToken>\n" +
                        "  <Contents>\n" +
                        "    <Key>sub%2Fexampleobject11.txt</Key>\n" +
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
                        "    <Key>sub%2Fexampleobject2.txt</Key>\n" +
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
                        "    <Prefix>a%2Fb%2F</Prefix>\n" +
                        "  </CommonPrefixes>\n" +
                        "</ListBucketResult>";

        result = SerdeBucketBasic.toListObjectsV2(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.name()).isEqualTo("examplebucket");
        assertThat(result.prefix()).isEqualTo("sub/aaa");
        assertThat(result.maxKeys()).isEqualTo(100);
        assertThat(result.keyCount()).isEqualTo(3);
        assertThat(result.startAfter()).isEqualTo("b");
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.continuationToken()).isEqualTo("/CgJiYw--");
        assertThat(result.nextContinuationToken()).isEqualTo("/NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA");

        contents = result.contents();
        assertThat(contents).isNotNull();
        assertThat(contents.size()).isEqualTo(2);

        summary1 = contents.get(0);
        assertThat(summary1.key()).isEqualTo("sub/exampleobject11.txt");
        assertThat(summary1.lastModified()).isEqualTo(Instant.parse("2020-06-22T11:42:32.000Z"));
        assertThat(summary1.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"");
        assertThat(summary1.type()).isEqualTo("Normal");
        assertThat(summary1.size()).isEqualTo(344606L);
        assertThat(summary1.storageClass()).isEqualTo("ColdArchive");
        assertThat(summary1.owner().id()).isEqualTo("0022012****");
        assertThat(summary1.owner().displayName()).isEqualTo("user-example");
        assertThat(summary1.restoreInfo()).isEqualTo("ongoing-request=\"true\"");

        summary2 = contents.get(1);
        assertThat(summary2.key()).isEqualTo("sub/exampleobject2.txt");
        assertThat(summary2.lastModified()).isEqualTo(Instant.parse("2023-12-08T08:12:20.000Z"));
        assertThat(summary2.eTag()).isEqualTo("\"5B3C1A2E053D763E1B002CC607C5A0FE1****\"");
        assertThat(summary2.type()).isEqualTo("Normal2");
        assertThat(summary2.size()).isEqualTo(344607L);
        assertThat(summary2.storageClass()).isEqualTo("DeepColdArchive");
        assertThat(summary2.owner().id()).isEqualTo("0022012****22");
        assertThat(summary2.owner().displayName()).isEqualTo("user-example22");
        assertThat(summary2.restoreInfo()).isEqualTo("ongoing-request=\"false\", expiry-date=\"Sat, 05 Nov 2022 07:38:08 GMT\"");
        assertThat(summary2.transitionTime()).isEqualTo(Instant.parse("2023-12-08T08:12:20.000Z"));

        prefixes = result.commonPrefixes();
        assertThat(prefixes).isNotNull();
        assertThat(prefixes.size()).isEqualTo(1);
        assertThat(prefixes.get(0).prefix()).isEqualTo("a/b/");

    }

    @Test
    public void toListObjectsV2_ensureList() {
        // no url encoding type
        String xml =
                "<ListBucketResult>\n" +
                        "  <Name>examplebucket</Name>\n" +
                        "  <Prefix>aaa</Prefix>\n" +
                        "  <MaxKeys>100</MaxKeys>\n" +
                        "  <KeyCount>3</KeyCount>\n" +
                        "  <Delimiter>/</Delimiter>\n" +
                        "  <StartAfter>b</StartAfter>\n" +
                        "  <IsTruncated>false</IsTruncated>\n" +
                        "  <ContinuationToken>CgJiYw--</ContinuationToken>\n" +
                        "  <NextContinuationToken>NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA</NextContinuationToken>\n" +
                        "</ListBucketResult>";

        ListObjectsV2Result result = SerdeBucketBasic.toListObjectsV2(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.name()).isEqualTo("examplebucket");
        assertThat(result.prefix()).isEqualTo("aaa");
        assertThat(result.maxKeys()).isEqualTo(100);
        assertThat(result.keyCount()).isEqualTo(3);
        assertThat(result.startAfter()).isEqualTo("b");
        assertThat(result.delimiter()).isEqualTo("/");
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.encodingType()).isNull();
        assertThat(result.continuationToken()).isEqualTo("CgJiYw--");
        assertThat(result.nextContinuationToken()).isEqualTo("NextChR1c2VyL2VyaWMvZGVtbzMuanNvbhAA");

        for (ObjectSummary ignore: result.contents()) {
            fail("should not here");
        }

        for (CommonPrefix ignore: result.commonPrefixes()) {
            fail("should not here");
        }
    }
}