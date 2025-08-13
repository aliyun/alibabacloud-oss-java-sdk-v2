package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.internal.BucketsXml;
import com.aliyun.sdk.service.oss2.models.internal.ListAllMyBucketsResultXml;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectSymlink;
import com.aliyun.sdk.service.oss2.transform.SerdeService;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListBucketsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListBucketsResult result = ListBucketsResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        Owner owner = Owner.newBuilder()
                .id("ut_test_put_bucket")
                .displayName("ut_test_put_bucket")
                .build();

        BucketSummary bucket1 = BucketSummary.newBuilder()
                .creationDate(Instant.parse("2014-05-14T11:18:32.000Z"))
                .extranetEndpoint("oss-cn-hangzhou.aliyuncs.com")
                .intranetEndpoint("oss-cn-hangzhou-internal.aliyuncs.com")
                .location("oss-cn-hangzhou")
                .name("mybucket01")
                .region("cn-hangzhou")
                .storageClass("Standard")
                .build();

        BucketSummary bucket2 = BucketSummary.newBuilder()
                .creationDate(Instant.parse("2014-02-05T11:21:04.000Z"))
                .extranetEndpoint("oss-cn-hangzhou.aliyuncs.com")
                .intranetEndpoint("oss-cn-hangzhou-internal.aliyuncs.com")
                .location("oss-cn-hangzhou")
                .name("test-bucket-4")
                .region("cn-hangzhou")
                .storageClass("IA")
                .resourceGroupId("rg-default-id")
                .build();

        BucketsXml bucketsXml = BucketsXml.newBuilder()
                        .buckets(java.util.Arrays.asList(bucket1, bucket2))
                        .build();

        ListAllMyBucketsResultXml listAllMyBucketsResultXml = new ListAllMyBucketsResultXml();
        listAllMyBucketsResultXml.prefix = "my";
        listAllMyBucketsResultXml.marker = "mybucket";
        listAllMyBucketsResultXml.maxKeys = 10L;
        listAllMyBucketsResultXml.isTruncated = true;
        listAllMyBucketsResultXml.nextMarker = "mybucket10";
        listAllMyBucketsResultXml.owner = owner;
        listAllMyBucketsResultXml.buckets = bucketsXml;

        ListBucketsResult result = ListBucketsResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .innerBody(listAllMyBucketsResultXml)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.prefix()).isEqualTo("my");
        assertThat(result.marker()).isEqualTo("mybucket");
        assertThat(result.maxKeys()).isEqualTo(10L);
        assertThat(result.isTruncated()).isEqualTo(true);
        assertThat(result.nextMarker()).isEqualTo("mybucket10");
        assertThat(result.owner()).isNotNull();
        assertThat(result.owner().id()).isEqualTo("ut_test_put_bucket");
        assertThat(result.owner().displayName()).isEqualTo("ut_test_put_bucket");
        assertThat(result.buckets()).hasSize(2);

        BucketSummary resultBucket1 = result.buckets().get(0);
        assertThat(resultBucket1.creationDate()).isEqualTo(Instant.parse("2014-05-14T11:18:32.000Z"));
        assertThat(resultBucket1.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultBucket1.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultBucket1.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket1.name()).isEqualTo("mybucket01");
        assertThat(resultBucket1.region()).isEqualTo("cn-hangzhou");
        assertThat(resultBucket1.storageClass()).isEqualTo("Standard");

        BucketSummary resultBucket2 = result.buckets().get(1);
        assertThat(resultBucket2.creationDate()).isEqualTo(Instant.parse("2014-02-05T11:21:04.000Z"));
        assertThat(resultBucket2.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultBucket2.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultBucket2.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket2.name()).isEqualTo("test-bucket-4");
        assertThat(resultBucket2.region()).isEqualTo("cn-hangzhou");
        assertThat(resultBucket2.storageClass()).isEqualTo("IA");
        assertThat(resultBucket2.resourceGroupId()).isEqualTo("rg-default-id");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210"
        );

        Owner owner = Owner.newBuilder()
                .id("ut_test_put_bucket")
                .displayName("ut_test_put_bucket")
                .build();

        BucketSummary bucket1 = BucketSummary.newBuilder()
                .creationDate(Instant.parse("2014-05-14T11:18:32.000Z"))
                .extranetEndpoint("oss-cn-hangzhou.aliyuncs.com")
                .intranetEndpoint("oss-cn-hangzhou-internal.aliyuncs.com")
                .location("oss-cn-hangzhou")
                .name("mybucket01")
                .region("cn-hangzhou")
                .storageClass("Standard")
                .build();

        BucketSummary bucket2 = BucketSummary.newBuilder()
                .creationDate(Instant.parse("2014-02-05T11:21:04.000Z"))
                .extranetEndpoint("oss-cn-hangzhou.aliyuncs.com")
                .intranetEndpoint("oss-cn-hangzhou-internal.aliyuncs.com")
                .location("oss-cn-hangzhou")
                .name("test-bucket-4")
                .region("cn-hangzhou")
                .storageClass("IA")
                .resourceGroupId("rg-default-id")
                .build();

        BucketsXml bucketsXml = BucketsXml.newBuilder()
                        .buckets(java.util.Arrays.asList(bucket1, bucket2))
                        .build();

        ListAllMyBucketsResultXml listAllMyBucketsResultXml = new ListAllMyBucketsResultXml();
        listAllMyBucketsResultXml.prefix = "my";
        listAllMyBucketsResultXml.marker = "mybucket";
        listAllMyBucketsResultXml.maxKeys = 10L;
        listAllMyBucketsResultXml.isTruncated = true;
        listAllMyBucketsResultXml.nextMarker = "mybucket10";
        listAllMyBucketsResultXml.owner = owner;
        listAllMyBucketsResultXml.buckets = bucketsXml;

        ListBucketsResult original = ListBucketsResult.newBuilder()
                .headers(headers)
                .status("Found")
                .statusCode(302)
                .innerBody(listAllMyBucketsResultXml)
                .build();

        ListBucketsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("Found");
        assertThat(copy.statusCode()).isEqualTo(302);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.prefix()).isEqualTo("my");
        assertThat(copy.marker()).isEqualTo("mybucket");
        assertThat(copy.maxKeys()).isEqualTo(10L);
        assertThat(copy.isTruncated()).isEqualTo(true);
        assertThat(copy.nextMarker()).isEqualTo("mybucket10");
        assertThat(copy.owner()).isNotNull();
        assertThat(copy.owner().id()).isEqualTo("ut_test_put_bucket");
        assertThat(copy.owner().displayName()).isEqualTo("ut_test_put_bucket");
        assertThat(copy.buckets()).hasSize(2);

        BucketSummary resultBucket1 = copy.buckets().get(0);
        assertThat(resultBucket1.creationDate()).isEqualTo(Instant.parse("2014-05-14T11:18:32.000Z"));
        assertThat(resultBucket1.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultBucket1.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultBucket1.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket1.name()).isEqualTo("mybucket01");
        assertThat(resultBucket1.region()).isEqualTo("cn-hangzhou");
        assertThat(resultBucket1.storageClass()).isEqualTo("Standard");

        BucketSummary resultBucket2 = copy.buckets().get(1);
        assertThat(resultBucket2.creationDate()).isEqualTo(Instant.parse("2014-02-05T11:21:04.000Z"));
        assertThat(resultBucket2.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultBucket2.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultBucket2.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket2.name()).isEqualTo("test-bucket-4");
        assertThat(resultBucket2.region()).isEqualTo("cn-hangzhou");
        assertThat(resultBucket2.storageClass()).isEqualTo("IA");
        assertThat(resultBucket2.resourceGroupId()).isEqualTo("rg-default-id");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeService.toListBuckets(blankOutput);


        String xml = "<ListAllMyBucketsResult>\n" +
                "  <Prefix>my</Prefix>\n" +
                "  <Marker>mybucket</Marker>\n" +
                "  <MaxKeys>10</MaxKeys>\n" +
                "  <IsTruncated>true</IsTruncated>\n" +
                "  <NextMarker>mybucket10</NextMarker>\n" +
                "  <Owner>\n" +
                "    <ID>ut_test_put_bucket</ID>\n" +
                "    <DisplayName>ut_test_put_bucket</DisplayName>\n" +
                "  </Owner>\n" +
                "  <Buckets>\n" +
                "    <Bucket>\n" +
                "      <CreationDate>2014-05-14T11:18:32.000Z</CreationDate>\n" +
                "      <ExtranetEndpoint>oss-cn-hangzhou.aliyuncs.com</ExtranetEndpoint>\n" +
                "      <IntranetEndpoint>oss-cn-hangzhou-internal.aliyuncs.com</IntranetEndpoint>\n" +
                "      <Location>oss-cn-hangzhou</Location>\n" +
                "      <Name>mybucket01</Name>\n" +
                "      <Region>cn-hangzhou</Region>\n" +
                "      <StorageClass>Standard</StorageClass>\n" +
                "    </Bucket>\n" +
                "    <Bucket>\n" +
                "      <CreationDate>2014-02-05T11:21:04.000Z</CreationDate>\n" +
                "      <ExtranetEndpoint>oss-cn-hangzhou.aliyuncs.com</ExtranetEndpoint>\n" +
                "      <IntranetEndpoint>oss-cn-hangzhou-internal.aliyuncs.com</IntranetEndpoint>\n" +
                "      <Location>oss-cn-hangzhou</Location>\n" +
                "      <Name>test-bucket-4</Name>\n" +
                "      <Region>cn-hangzhou</Region>\n" +
                "      <StorageClass>IA</StorageClass>\n" +
                "      <ResourceGroupId>rg-default-id</ResourceGroupId>\n" +
                "    </Bucket>\n" +
                "  </Buckets>\n" +
                "</ListAllMyBucketsResult>";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-111111111111111111"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .body(BinaryData.fromString(xml))
                .build();

        ListBucketsResult result = SerdeService.toListBuckets(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
        assertThat(result.prefix()).isEqualTo("my");
        assertThat(result.marker()).isEqualTo("mybucket");
        assertThat(result.maxKeys()).isEqualTo(10L);
        assertThat(result.isTruncated()).isEqualTo(true);
        assertThat(result.nextMarker()).isEqualTo("mybucket10");
        assertThat(result.owner()).isNotNull();
        assertThat(result.owner().id()).isEqualTo("ut_test_put_bucket");
        assertThat(result.owner().displayName()).isEqualTo("ut_test_put_bucket");
        assertThat(result.buckets()).hasSize(2);

        BucketSummary resultBucket1 = result.buckets().get(0);
        assertThat(resultBucket1.creationDate()).isEqualTo(Instant.parse("2014-05-14T11:18:32.000Z"));
        assertThat(resultBucket1.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultBucket1.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultBucket1.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket1.name()).isEqualTo("mybucket01");
        assertThat(resultBucket1.region()).isEqualTo("cn-hangzhou");
        assertThat(resultBucket1.storageClass()).isEqualTo("Standard");

        BucketSummary resultBucket2 = result.buckets().get(1);
        assertThat(resultBucket2.creationDate()).isEqualTo(Instant.parse("2014-02-05T11:21:04.000Z"));
        assertThat(resultBucket2.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(resultBucket2.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.aliyuncs.com");
        assertThat(resultBucket2.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket2.name()).isEqualTo("test-bucket-4");
        assertThat(resultBucket2.region()).isEqualTo("cn-hangzhou");
        assertThat(resultBucket2.storageClass()).isEqualTo("IA");
        assertThat(resultBucket2.resourceGroupId()).isEqualTo("rg-default-id");
    }
}
