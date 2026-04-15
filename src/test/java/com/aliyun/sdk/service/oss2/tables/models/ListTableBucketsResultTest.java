package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.ListTableBucketsResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBucketBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListTableBucketsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListTableBucketsResult result = ListTableBucketsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.continuationToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        List<TableBucketSummary> tableBuckets = new ArrayList<>();
        
        TableBucketSummary bucket1 = TableBucketSummary.newBuilder()
                .arn("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket-1")
                .name("test-bucket-1")
                .ownerAccountId("123456789012")
                .tableBucketId("tb-1234567890abcdef1")
                .createdAt("2023-12-17T00:20:57.000Z")
                .build();
                
        TableBucketSummary bucket2 = TableBucketSummary.newBuilder()
                .arn("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket-2")
                .name("test-bucket-2")
                .ownerAccountId("123456789012")
                .tableBucketId("tb-1234567890abcdef2")
                .createdAt("2023-12-17T00:21:57.000Z")
                .build();
                
        tableBuckets.add(bucket1);
        tableBuckets.add(bucket2);

        ListTableBucketsResultJson bodyJson = new ListTableBucketsResultJson();
        bodyJson.continuationToken = "next-continuation-token";
        bodyJson.tableBuckets = tableBuckets;

        ListTableBucketsResult result = ListTableBucketsResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.continuationToken()).isEqualTo("next-continuation-token");
        assertThat(result.tableBuckets()).hasSize(2);
        assertThat(result.tableBuckets().get(0).arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket-1");
        assertThat(result.tableBuckets().get(0).name()).isEqualTo("test-bucket-1");
        assertThat(result.tableBuckets().get(1).arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket-2");
        assertThat(result.tableBuckets().get(1).name()).isEqualTo("test-bucket-2");
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

        List<TableBucketSummary> tableBuckets = new ArrayList<>();
        
        TableBucketSummary bucket = TableBucketSummary.newBuilder()
                .arn("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket")
                .name("original-bucket")
                .ownerAccountId("123456789012")
                .tableBucketId("tb-original-1234567890")
                .createdAt("2023-12-17T00:20:57.000Z")
                .build();
                
        tableBuckets.add(bucket);

        ListTableBucketsResultJson bodyJson = new ListTableBucketsResultJson();
        bodyJson.continuationToken = "original-continuation-token";
        bodyJson.tableBuckets = tableBuckets;

        ListTableBucketsResult original = ListTableBucketsResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        ListTableBucketsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.continuationToken()).isEqualTo("original-continuation-token");
        assertThat(copy.tableBuckets()).hasSize(1);
        assertThat(copy.tableBuckets().get(0).arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket");
        assertThat(copy.tableBuckets().get(0).name()).isEqualTo("original-bucket");
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {

        String jsonData = "{\n" +
                "  \"continuationToken\": \"test-continuation-token\",\n" +
                "  \"tableBuckets\": [\n" +
                "    {\n" +
                "      \"arn\": \"arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket-1\",\n" +
                "      \"createdAt\": \"2023-12-17T00:20:57.000Z\",\n" +
                "      \"name\": \"test-bucket-1\",\n" +
                "      \"ownerAccountId\": \"123456789012\",\n" +
                "      \"tableBucketId\": \"tb-1234567890abcdef1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"arn\": \"arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket-2\",\n" +
                "      \"createdAt\": \"2023-12-17T00:21:57.000Z\",\n" +
                "      \"name\": \"test-bucket-2\",\n" +
                "      \"ownerAccountId\": \"123456789012\",\n" +
                "      \"tableBucketId\": \"tb-1234567890abcdef2\"\n" +
                "    }\n" +
                "  ]\n" +
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

        ListTableBucketsResult result = SerdeTableBucketBasic.toListTableBuckets(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.continuationToken()).isEqualTo("test-continuation-token");
        assertThat(result.tableBuckets()).hasSize(2);
        assertThat(result.tableBuckets().get(0).arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket-1");
        assertThat(result.tableBuckets().get(0).name()).isEqualTo("test-bucket-1");
        assertThat(result.tableBuckets().get(1).arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket-2");
        assertThat(result.tableBuckets().get(1).name()).isEqualTo("test-bucket-2");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}