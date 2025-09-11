package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.BucketProperties;
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

        List<BucketProperties> buckets = Arrays.asList(
                BucketProperties.newBuilder()
                        .name("test-bucket-3")
                        .location("oss-cn-shanghai")
                        .creationDate(Instant.parse("2014-02-07T18:12:43.000Z"))
                        .extranetEndpoint("oss-cn-shanghai.oss-vectors.aliyuncs.com")
                        .intranetEndpoint("oss-cn-shanghai-internal.oss-vectors.aliyuncs.com")
                        .region("cn-shanghai")
                        .resourceGroupId("rg-default-id")
                        .build(),
                BucketProperties.newBuilder()
                        .name("test-bucket-4")
                        .location("oss-cn-hangzhou")
                        .creationDate(Instant.parse("2014-02-05T11:21:04.000Z"))
                        .extranetEndpoint("oss-cn-hangzhou.oss-vectors.aliyuncs.com")
                        .intranetEndpoint("oss-cn-hangzhou-internal.oss-vectors.aliyuncs.com")
                        .region("cn-hangzhou")
                        .resourceGroupId("rg-default-id")
                        .build()
        );

        VectorBucketsJson vectorBucketsJson = VectorBucketsJson.newBuilder()
                .prefix("test")
                .marker("marker1")
                .maxKeys(100)
                .isTruncated(false)
                .nextMarker("")
                .buckets(buckets)
                .build();

        VectorBucketsResponse vectorBucketsResponse = new VectorBucketsResponse();
        vectorBucketsResponse.setVectorBuckets(vectorBucketsJson);

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

        List<BucketProperties> resultBuckets = result.buckets();
        assertThat(resultBuckets).isNotNull();
        assertThat(resultBuckets).hasSize(2);

        BucketProperties bucket1 = resultBuckets.get(0);
        assertThat(bucket1.name()).isEqualTo("test-bucket-3");
        assertThat(bucket1.location()).isEqualTo("oss-cn-shanghai");
        assertThat(bucket1.creationDate()).isEqualTo(Instant.parse("2014-02-07T18:12:43.000Z"));
        assertThat(bucket1.extranetEndpoint()).isEqualTo("oss-cn-shanghai.oss-vectors.aliyuncs.com");
        assertThat(bucket1.intranetEndpoint()).isEqualTo("oss-cn-shanghai-internal.oss-vectors.aliyuncs.com");
        assertThat(bucket1.region()).isEqualTo("cn-shanghai");
        assertThat(bucket1.resourceGroupId()).isEqualTo("rg-default-id");

        BucketProperties bucket2 = resultBuckets.get(1);
        assertThat(bucket2.name()).isEqualTo("test-bucket-4");
        assertThat(bucket2.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(bucket2.creationDate()).isEqualTo(Instant.parse("2014-02-05T11:21:04.000Z"));
        assertThat(bucket2.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.oss-vectors.aliyuncs.com");
        assertThat(bucket2.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.oss-vectors.aliyuncs.com");
        assertThat(bucket2.region()).isEqualTo("cn-hangzhou");
        assertThat(bucket2.resourceGroupId()).isEqualTo("rg-default-id");

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

        List<BucketProperties> buckets = Arrays.asList(
                BucketProperties.newBuilder()
                        .name("test-bucket-5")
                        .location("oss-cn-beijing")
                        .creationDate(Instant.parse("2015-03-08T19:13:44.000Z"))
                        .extranetEndpoint("oss-cn-beijing.oss-vectors.aliyuncs.com")
                        .intranetEndpoint("oss-cn-beijing-internal.oss-vectors.aliyuncs.com")
                        .region("cn-beijing")
                        .resourceGroupId("rg-another-id")
                        .build()
        );

        VectorBucketsJson vectorBucketsJson = VectorBucketsJson.newBuilder()
                .prefix("example")
                .marker("marker2")
                .maxKeys(50)
                .isTruncated(true)
                .nextMarker("next-marker-value")
                .buckets(buckets)
                .build();

        VectorBucketsResponse vectorBucketsResponse = new VectorBucketsResponse();
        vectorBucketsResponse.setVectorBuckets(vectorBucketsJson);

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

        List<BucketProperties> resultBuckets = copy.buckets();
        assertThat(resultBuckets).isNotNull();
        assertThat(resultBuckets).hasSize(1);

        BucketProperties bucket = resultBuckets.get(0);
        assertThat(bucket.name()).isEqualTo("test-bucket-5");
        assertThat(bucket.location()).isEqualTo("oss-cn-beijing");
        assertThat(bucket.creationDate()).isEqualTo(Instant.parse("2015-03-08T19:13:44.000Z"));
        assertThat(bucket.extranetEndpoint()).isEqualTo("oss-cn-beijing.oss-vectors.aliyuncs.com");
        assertThat(bucket.intranetEndpoint()).isEqualTo("oss-cn-beijing-internal.oss-vectors.aliyuncs.com");
        assertThat(bucket.region()).isEqualTo("cn-beijing");
        assertThat(bucket.resourceGroupId()).isEqualTo("rg-another-id");

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

        List<BucketProperties> resultBuckets = result.buckets();
        assertThat(resultBuckets).isNotNull();
        assertThat(resultBuckets).hasSize(2);

        BucketProperties bucket1 = resultBuckets.get(0);
        assertThat(bucket1.name()).isEqualTo("test-bucket-3");
        assertThat(bucket1.location()).isEqualTo("oss-cn-shanghai");
        assertThat(bucket1.creationDate()).isEqualTo(Instant.parse("2014-02-07T18:12:43.000Z"));
        assertThat(bucket1.extranetEndpoint()).isEqualTo("oss-cn-shanghai.oss-vectors.aliyuncs.com");
        assertThat(bucket1.intranetEndpoint()).isEqualTo("oss-cn-shanghai-internal.oss-vectors.aliyuncs.com");
        assertThat(bucket1.region()).isEqualTo("cn-shanghai");
        assertThat(bucket1.resourceGroupId()).isEqualTo("rg-default-id");

        BucketProperties bucket2 = resultBuckets.get(1);
        assertThat(bucket2.name()).isEqualTo("test-bucket-4");
        assertThat(bucket2.location()).isEqualTo("oss-cn-hangzhou");
        assertThat(bucket2.creationDate()).isEqualTo(Instant.parse("2014-02-05T11:21:04.000Z"));
        assertThat(bucket2.extranetEndpoint()).isEqualTo("oss-cn-hangzhou.oss-vectors.aliyuncs.com");
        assertThat(bucket2.intranetEndpoint()).isEqualTo("oss-cn-hangzhou-internal.oss-vectors.aliyuncs.com");
        assertThat(bucket2.region()).isEqualTo("cn-hangzhou");
        assertThat(bucket2.resourceGroupId()).isEqualTo("rg-default-id");

        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}
