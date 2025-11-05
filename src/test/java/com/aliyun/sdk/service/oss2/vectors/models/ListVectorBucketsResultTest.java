package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorBucketBasic;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorBucketsResultJson;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListVectorBucketsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListVectorBucketsResult result = ListVectorBucketsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        VectorBucketSummary bucket1 = VectorBucketSummary.newBuilder()
                .name("test-bucket-3")
                .location("oss-cn-shanghai")
                .creationDate(Instant.parse("2014-02-07T18:12:43.000Z"))
                .extranetEndpoint("oss-cn-shanghai.oss-vectors.aliyuncs.com")
                .intranetEndpoint("oss-cn-shanghai-internal.oss-vectors.aliyuncs.com")
                .region("cn-shanghai")
                .resourceGroupId("rg-default-id")
                .build();

        VectorBucketSummary bucket2 = VectorBucketSummary.newBuilder()
                .name("test-bucket-4")
                .location("oss-cn-hangzhou")
                .creationDate(Instant.parse("2014-02-05T11:21:04.000Z"))
                .extranetEndpoint("oss-cn-hangzhou.oss-vectors.aliyuncs.com")
                .intranetEndpoint("oss-cn-hangzhou-internal.oss-vectors.aliyuncs.com")
                .region("cn-hangzhou")
                .resourceGroupId("rg-default-id")
                .build();

        List<VectorBucketSummary> buckets = Arrays.asList(bucket1, bucket2);

        BucketsSummary bucketsSummary = BucketsSummary.newBuilder()
                .prefix("test")
                .marker("marker1")
                .maxKeys(100L)
                .isTruncated(false)
                .nextMarker("")
                .buckets(buckets)
                .build();

        ListVectorBucketsResultJson listVectorBucketsResultJson = new ListVectorBucketsResultJson();
        listVectorBucketsResultJson.bucketsSummary = bucketsSummary;

        ListVectorBucketsResult result = ListVectorBucketsResult.newBuilder()
                .headers(headers)
                .innerBody(listVectorBucketsResultJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.prefix()).isEqualTo("test");
        assertThat(result.marker()).isEqualTo("marker1");
        assertThat(result.maxKeys()).isEqualTo(100L);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.nextMarker()).isEqualTo("");

        List<VectorBucketSummary> resultBuckets = result.buckets();
        assertThat(resultBuckets).isNotNull();
        assertThat(resultBuckets).hasSize(2);

        VectorBucketSummary resultBucket1 = resultBuckets.get(0);
        assertThat(resultBucket1.name()).isEqualTo("test-bucket-3");
        assertThat(resultBucket1.location()).isEqualTo("oss-cn-shanghai");
        assertThat(resultBucket1.creationDate()).isEqualTo(Instant.parse("2014-02-07T18:12:43.000Z"));
        assertThat(resultBucket1.extranetEndpoint()).isEqualTo("oss-cn-shanghai.oss-vectors.aliyuncs.com");
        assertThat(resultBucket1.intranetEndpoint()).isEqualTo("oss-cn-shanghai-internal.oss-vectors.aliyuncs.com");
        assertThat(resultBucket1.region()).isEqualTo("cn-shanghai");
        assertThat(resultBucket1.resourceGroupId()).isEqualTo("rg-default-id");

        VectorBucketSummary resultBucket2 = resultBuckets.get(1);
        assertThat(resultBucket2.name()).isEqualTo("test-bucket-4");
        assertThat(resultBucket2.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket2.creationDate()).isEqualTo(Instant.parse("2014-02-05T11:21:04.000Z"));
        assertThat(resultBucket2.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.oss-vectors.aliyuncs.com");
        assertThat(resultBucket2.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.oss-vectors.aliyuncs.com");
        assertThat(resultBucket2.region()).isEqualTo("cn-hangzhou");
        assertThat(resultBucket2.resourceGroupId()).isEqualTo("rg-default-id");

        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        VectorBucketSummary bucket = VectorBucketSummary.newBuilder()
                .name("test-bucket-5")
                .location("oss-cn-beijing")
                .creationDate(Instant.parse("2015-03-08T19:13:44.000Z"))
                .extranetEndpoint("oss-cn-beijing.oss-vectors.aliyuncs.com")
                .intranetEndpoint("oss-cn-beijing-internal.oss-vectors.aliyuncs.com")
                .region("cn-beijing")
                .resourceGroupId("rg-another-id")
                .build();

        List<VectorBucketSummary> buckets = Arrays.asList(bucket);

        BucketsSummary bucketsSummary = BucketsSummary.newBuilder()
                .prefix("example")
                .marker("marker2")
                .maxKeys(50L)
                .isTruncated(true)
                .nextMarker("next-marker-value")
                .buckets(buckets)
                .build();

        ListVectorBucketsResultJson listVectorBucketsResultJson = new ListVectorBucketsResultJson();
        listVectorBucketsResultJson.bucketsSummary = bucketsSummary;

        ListVectorBucketsResult original = ListVectorBucketsResult.newBuilder()
                .headers(headers)
                .innerBody(listVectorBucketsResultJson)
                .status("Partial")
                .statusCode(206)
                .build();

        ListVectorBucketsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.prefix()).isEqualTo("example");
        assertThat(copy.marker()).isEqualTo("marker2");
        assertThat(copy.maxKeys()).isEqualTo(50L);
        assertThat(copy.isTruncated()).isEqualTo(true);
        assertThat(copy.nextMarker()).isEqualTo("next-marker-value");

        List<VectorBucketSummary> resultBuckets = copy.buckets();
        assertThat(resultBuckets).isNotNull();
        assertThat(resultBuckets).hasSize(1);

        VectorBucketSummary resultBucket = resultBuckets.get(0);
        assertThat(resultBucket.name()).isEqualTo("test-bucket-5");
        assertThat(resultBucket.location()).isEqualTo("oss-cn-beijing");
        assertThat(resultBucket.creationDate()).isEqualTo(Instant.parse("2015-03-08T19:13:44.000Z"));
        assertThat(resultBucket.extranetEndpoint()).isEqualTo("oss-cn-beijing.oss-vectors.aliyuncs.com");
        assertThat(resultBucket.intranetEndpoint()).isEqualTo("oss-cn-beijing-internal.oss-vectors.aliyuncs.com");
        assertThat(resultBucket.region()).isEqualTo("cn-beijing");
        assertThat(resultBucket.resourceGroupId()).isEqualTo("rg-another-id");

        assertThat(copy.status()).isEqualTo("Partial");
        assertThat(copy.statusCode()).isEqualTo(206);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {
        String jsonData = "{\n" +
                "  \"ListAllMyBucketsResult\": {\n" +
                "    \"Prefix\": \"test\",\n" +
                "    \"Marker\": \"marker1\",\n" +
                "    \"MaxKeys\": 100,\n" +
                "    \"IsTruncated\": false,\n" +
                "    \"NextMarker\": \"\",\n" +
                "    \"Buckets\": [\n" +
                "      {\n" +
                "        \"CreationDate\": \"2014-02-07T18:12:43.000Z\",\n" +
                "        \"ExtranetEndpoint\": \"oss-cn-shanghai.oss-vectors.aliyuncs.com\",\n" +
                "        \"IntranetEndpoint\": \"oss-cn-shanghai-internal.oss-vectors.aliyuncs.com\",\n" +
                "        \"Location\": \"oss-cn-shanghai\",\n" +
                "        \"Name\": \"test-bucket-3\",\n" +
                "        \"Region\": \"cn-shanghai\",\n" +
                "        \"ResourceGroupId\": \"rg-default-id\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"CreationDate\": \"2014-02-05T11:21:04.000Z\",\n" +
                "        \"ExtranetEndpoint\": \"oss-cn-hangzhou.oss-vectors.aliyuncs.com\",\n" +
                "        \"IntranetEndpoint\": \"oss-cn-hangzhou-internal.oss-vectors.aliyuncs.com\",\n" +
                "        \"Location\": \"oss-cn-hangzhou\",\n" +
                "        \"Name\": \"test-bucket-4\",\n" +
                "        \"Region\": \"cn-hangzhou\",\n" +
                "        \"ResourceGroupId\": \"rg-default-id\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-xml-builder-test",
                        "ETag", "\"xml-builder-etag\""
                ))
                .status("OK")
                .statusCode(200)
                .build();

        ListVectorBucketsResult result = SerdeVectorBucketBasic.toListVectorBuckets(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");

        assertThat(result.prefix()).isEqualTo("test");
        assertThat(result.marker()).isEqualTo("marker1");
        assertThat(result.maxKeys()).isEqualTo(100L);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.nextMarker()).isEqualTo("");

        List<VectorBucketSummary> resultBuckets = result.buckets();
        assertThat(resultBuckets).isNotNull();
        assertThat(resultBuckets).hasSize(2);

        VectorBucketSummary resultBucket1 = resultBuckets.get(0);
        assertThat(resultBucket1.name()).isEqualTo("test-bucket-3");
        assertThat(resultBucket1.location()).isEqualTo("oss-cn-shanghai");
        assertThat(resultBucket1.creationDate()).isEqualTo(Instant.parse("2014-02-07T18:12:43.000Z"));
        assertThat(resultBucket1.extranetEndpoint()).isEqualTo("oss-cn-shanghai.oss-vectors.aliyuncs.com");
        assertThat(resultBucket1.intranetEndpoint()).isEqualTo("oss-cn-shanghai-internal.oss-vectors.aliyuncs.com");
        assertThat(resultBucket1.region()).isEqualTo("cn-shanghai");
        assertThat(resultBucket1.resourceGroupId()).isEqualTo("rg-default-id");

        VectorBucketSummary resultBucket2 = resultBuckets.get(1);
        assertThat(resultBucket2.name()).isEqualTo("test-bucket-4");
        assertThat(resultBucket2.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket2.creationDate()).isEqualTo(Instant.parse("2014-02-05T11:21:04.000Z"));
        assertThat(resultBucket2.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.oss-vectors.aliyuncs.com");
        assertThat(resultBucket2.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.oss-vectors.aliyuncs.com");
        assertThat(resultBucket2.region()).isEqualTo("cn-hangzhou");
        assertThat(resultBucket2.resourceGroupId()).isEqualTo("rg-default-id");

        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}