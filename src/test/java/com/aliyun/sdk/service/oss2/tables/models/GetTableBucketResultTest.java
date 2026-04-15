package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableBucketResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBucketBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTableBucketResultTest {

    @Test
    public void testEmptyBuilder() {
        GetTableBucketResult result = GetTableBucketResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.arn()).isNull();
        assertThat(result.createdAt()).isNull();
        assertThat(result.name()).isNull();
        assertThat(result.ownerAccountId()).isNull();
        assertThat(result.tableBucketId()).isNull();
        assertThat(result.type()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetTableBucketResultJson bodyJson = new GetTableBucketResultJson();
        bodyJson.arn = "arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket";
        bodyJson.createdAt = "2023-12-17T00:20:57.000Z";
        bodyJson.name = "test-bucket";
        bodyJson.ownerAccountId = "123456789012";
        bodyJson.tableBucketId = "tb-1234567890abcdef";
        bodyJson.type = "TABLE";

        GetTableBucketResult result = GetTableBucketResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket");
        assertThat(result.createdAt()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(result.name()).isEqualTo("test-bucket");
        assertThat(result.ownerAccountId()).isEqualTo("123456789012");
        assertThat(result.tableBucketId()).isEqualTo("tb-1234567890abcdef");
        assertThat(result.type()).isEqualTo("TABLE");
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

        GetTableBucketResultJson bodyJson = new GetTableBucketResultJson();
        bodyJson.arn = "arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket";
        bodyJson.createdAt = "2023-12-17T00:20:57.000Z";
        bodyJson.name = "original-bucket";
        bodyJson.ownerAccountId = "123456789012";
        bodyJson.tableBucketId = "tb-original-1234567890";
        bodyJson.type = "TABLE";

        GetTableBucketResult original = GetTableBucketResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("Created")
                .statusCode(201)
                .build();

        GetTableBucketResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket");
        assertThat(copy.createdAt()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(copy.name()).isEqualTo("original-bucket");
        assertThat(copy.ownerAccountId()).isEqualTo("123456789012");
        assertThat(copy.tableBucketId()).isEqualTo("tb-original-1234567890");
        assertThat(copy.type()).isEqualTo("TABLE");
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {

        String jsonData = "{\n" +
                "  \"arn\": \"arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket\",\n" +
                "  \"createdAt\": \"2023-12-17T00:20:57.000Z\",\n" +
                "  \"name\": \"test-bucket\",\n" +
                "  \"ownerAccountId\": \"123456789012\",\n" +
                "  \"tableBucketId\": \"tb-1234567890abcdef\",\n" +
                "  \"type\": \"TABLE\"\n" +
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

        GetTableBucketResult result = SerdeTableBucketBasic.toGetTableBucket(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.arn()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket");
        assertThat(result.createdAt()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(result.name()).isEqualTo("test-bucket");
        assertThat(result.ownerAccountId()).isEqualTo("123456789012");
        assertThat(result.tableBucketId()).isEqualTo("tb-1234567890abcdef");
        assertThat(result.type()).isEqualTo("TABLE");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}