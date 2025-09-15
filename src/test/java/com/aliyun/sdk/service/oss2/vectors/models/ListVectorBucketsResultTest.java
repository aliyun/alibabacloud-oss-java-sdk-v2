package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.BucketPropertiesJson;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorBucketBasic;
import com.aliyun.sdk.service.oss2.vectors.models.internal.VectorBucketsJson;
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
        assertThat(result.prefix()).isNull();
        assertThat(result.marker()).isNull();
        assertThat(result.maxKeys()).isNull();
        assertThat(result.isTruncated()).isNull();
        assertThat(result.nextMarker()).isNull();
        assertThat(result.buckets()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        BucketPropertiesJson bucket1 = new BucketPropertiesJson();
        bucket1.name = "test-bucket-3";
        bucket1.location = "oss-cn-shanghai";
        bucket1.creationDate = Instant.parse("2014-02-07T18:12:43.000Z");
        bucket1.extranetEndpoint = "oss-cn-shanghai.oss-vectors.aliyuncs.com";
        bucket1.intranetEndpoint = "oss-cn-shanghai-internal.oss-vectors.aliyuncs.com";
        bucket1.region = "cn-shanghai";
        bucket1.resourceGroupId = "rg-default-id";

        BucketPropertiesJson bucket2 = new BucketPropertiesJson();
        bucket2.name = "test-bucket-4";
        bucket2.location = "oss-cn-hangzhou";
        bucket2.creationDate = Instant.parse("2014-02-05T11:21:04.000Z");
        bucket2.extranetEndpoint = "oss-cn-hangzhou.oss-vectors.aliyuncs.com";
        bucket2.intranetEndpoint = "oss-cn-hangzhou-internal.oss-vectors.aliyuncs.com";
        bucket2.region = "cn-hangzhou";
        bucket2.resourceGroupId = "rg-default-id";

        List<BucketPropertiesJson> buckets = Arrays.asList(bucket1, bucket2);

        VectorBucketsJson vectorBucketsJson = new VectorBucketsJson();
        vectorBucketsJson.prefix = "test";
        vectorBucketsJson.marker = "marker1";
        vectorBucketsJson.maxKeys = 100;
        vectorBucketsJson.isTruncated = false;
        vectorBucketsJson.nextMarker = "";
        vectorBucketsJson.buckets = buckets;

        VectorBucketsResponse vectorBucketsResponse = VectorBucketsResponse.newBuilder()
                .vectorBuckets(vectorBucketsJson)
                .build();

        ListVectorBucketsResult result = ListVectorBucketsResult.newBuilder()
                .headers(headers)
                .innerBody(vectorBucketsResponse)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.prefix()).isEqualTo("test");
        assertThat(result.marker()).isEqualTo("marker1");
        assertThat(result.maxKeys()).isEqualTo(100);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.nextMarker()).isEqualTo("");

        List<BucketPropertiesJson> resultBuckets = result.buckets();
        assertThat(resultBuckets).isNotNull();
        assertThat(resultBuckets).hasSize(2);

        BucketPropertiesJson resultBucket1 = resultBuckets.get(0);
        assertThat(resultBucket1.name).isEqualTo("test-bucket-3");
        assertThat(resultBucket1.location).isEqualTo("oss-cn-shanghai");
        assertThat(resultBucket1.creationDate).isEqualTo(Instant.parse("2014-02-07T18:12:43.000Z"));
        assertThat(resultBucket1.extranetEndpoint).isEqualTo("oss-cn-shanghai.oss-vectors.aliyuncs.com");
        assertThat(resultBucket1.intranetEndpoint).isEqualTo("oss-cn-shanghai-internal.oss-vectors.aliyuncs.com");
        assertThat(resultBucket1.region).isEqualTo("cn-shanghai");
        assertThat(resultBucket1.resourceGroupId).isEqualTo("rg-default-id");

        BucketPropertiesJson resultBucket2 = resultBuckets.get(1);
        assertThat(resultBucket2.name).isEqualTo("test-bucket-4");
        assertThat(resultBucket2.location).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket2.creationDate).isEqualTo(Instant.parse("2014-02-05T11:21:04.000Z"));
        assertThat(resultBucket2.extranetEndpoint).isEqualTo("oss-cn-hangzhou.oss-vectors.aliyuncs.com");
        assertThat(resultBucket2.intranetEndpoint).isEqualTo("oss-cn-hangzhou-internal.oss-vectors.aliyuncs.com");
        assertThat(resultBucket2.region).isEqualTo("cn-hangzhou");
        assertThat(resultBucket2.resourceGroupId).isEqualTo("rg-default-id");

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

        BucketPropertiesJson bucket = new BucketPropertiesJson();
        bucket.name = "test-bucket-5";
        bucket.location = "oss-cn-beijing";
        bucket.creationDate = Instant.parse("2015-03-08T19:13:44.000Z");
        bucket.extranetEndpoint = "oss-cn-beijing.oss-vectors.aliyuncs.com";
        bucket.intranetEndpoint = "oss-cn-beijing-internal.oss-vectors.aliyuncs.com";
        bucket.region = "cn-beijing";
        bucket.resourceGroupId = "rg-another-id";

        List<BucketPropertiesJson> buckets = Arrays.asList(bucket);

        VectorBucketsJson vectorBucketsJson = new VectorBucketsJson();
        vectorBucketsJson.prefix = "example";
        vectorBucketsJson.marker = "marker2";
        vectorBucketsJson.maxKeys = 50;
        vectorBucketsJson.isTruncated = true;
        vectorBucketsJson.nextMarker = "next-marker-value";
        vectorBucketsJson.buckets = buckets;

        VectorBucketsResponse vectorBucketsResponse = VectorBucketsResponse.newBuilder()
                .vectorBuckets(vectorBucketsJson)
                .build();

        ListVectorBucketsResult original = ListVectorBucketsResult.newBuilder()
                .headers(headers)
                .innerBody(vectorBucketsResponse)
                .status("Partial")
                .statusCode(206)
                .build();

        ListVectorBucketsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.prefix()).isEqualTo("example");
        assertThat(copy.marker()).isEqualTo("marker2");
        assertThat(copy.maxKeys()).isEqualTo(50);
        assertThat(copy.isTruncated()).isEqualTo(true);
        assertThat(copy.nextMarker()).isEqualTo("next-marker-value");

        List<BucketPropertiesJson> resultBuckets = copy.buckets();
        assertThat(resultBuckets).isNotNull();
        assertThat(resultBuckets).hasSize(1);

        BucketPropertiesJson resultBucket = resultBuckets.get(0);
        assertThat(resultBucket.name).isEqualTo("test-bucket-5");
        assertThat(resultBucket.location).isEqualTo("oss-cn-beijing");
        assertThat(resultBucket.creationDate).isEqualTo(Instant.parse("2015-03-08T19:13:44.000Z"));
        assertThat(resultBucket.extranetEndpoint).isEqualTo("oss-cn-beijing.oss-vectors.aliyuncs.com");
        assertThat(resultBucket.intranetEndpoint).isEqualTo("oss-cn-beijing-internal.oss-vectors.aliyuncs.com");
        assertThat(resultBucket.region).isEqualTo("cn-beijing");
        assertThat(resultBucket.resourceGroupId).isEqualTo("rg-another-id");

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
        assertThat(result.maxKeys()).isEqualTo(100);
        assertThat(result.isTruncated()).isEqualTo(false);
        assertThat(result.nextMarker()).isEqualTo("");

        List<BucketPropertiesJson> resultBuckets = result.buckets();
        assertThat(resultBuckets).isNotNull();
        assertThat(resultBuckets).hasSize(2);

        BucketPropertiesJson resultBucket1 = resultBuckets.get(0);
        assertThat(resultBucket1.name).isEqualTo("test-bucket-3");
        assertThat(resultBucket1.location).isEqualTo("oss-cn-shanghai");
        assertThat(resultBucket1.creationDate).isEqualTo(Instant.parse("2014-02-07T18:12:43.000Z"));
        assertThat(resultBucket1.extranetEndpoint).isEqualTo("oss-cn-shanghai.oss-vectors.aliyuncs.com");
        assertThat(resultBucket1.intranetEndpoint).isEqualTo("oss-cn-shanghai-internal.oss-vectors.aliyuncs.com");
        assertThat(resultBucket1.region).isEqualTo("cn-shanghai");
        assertThat(resultBucket1.resourceGroupId).isEqualTo("rg-default-id");

        BucketPropertiesJson resultBucket2 = resultBuckets.get(1);
        assertThat(resultBucket2.name).isEqualTo("test-bucket-4");
        assertThat(resultBucket2.location).isEqualTo("oss-cn-hangzhou");
        assertThat(resultBucket2.creationDate).isEqualTo(Instant.parse("2014-02-05T11:21:04.000Z"));
        assertThat(resultBucket2.extranetEndpoint).isEqualTo("oss-cn-hangzhou.oss-vectors.aliyuncs.com");
        assertThat(resultBucket2.intranetEndpoint).isEqualTo("oss-cn-hangzhou-internal.oss-vectors.aliyuncs.com");
        assertThat(resultBucket2.region).isEqualTo("cn-hangzhou");
        assertThat(resultBucket2.resourceGroupId).isEqualTo("rg-default-id");

        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}
