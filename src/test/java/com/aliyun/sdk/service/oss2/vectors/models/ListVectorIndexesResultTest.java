package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorIndexBasic;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorIndexesResultJson;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListVectorIndexesResultTest {

    @Test
    public void testEmptyBuilder() {
        ListVectorIndexesResult result = ListVectorIndexesResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.statusCode()).isEqualTo(0);
        assertThat(result.status()).isEqualTo("");
        assertThat(result.requestId()).isEqualTo("");
        assertThat(result.nextToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListVectorIndexesResultJson listResult = createTestListResult();

        ListVectorIndexesResult result = ListVectorIndexesResult.newBuilder()
                .headers(headers)
                .innerBody(listResult)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.indexes()).isEqualTo(listResult.indexes);
        assertThat(result.nextToken()).isEqualTo("next-token");
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

        ListVectorIndexesResultJson listResult = createTestListResult();

        ListVectorIndexesResult original = ListVectorIndexesResult.newBuilder()
                .headers(headers)
                .innerBody(listResult)
                .status("Created")
                .statusCode(201)
                .build();

        ListVectorIndexesResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.indexes()).isEqualTo(listResult.indexes);
        assertThat(copy.nextToken()).isEqualTo("next-token");
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {
        String jsonData = "{\n" +
                "  \"indexes\": [\n" +
                "    {\n" +
                "      \"createTime\": \"2023-12-17T00:20:57.000Z\",\n" +
                "      \"indexName\": \"test-index1\",\n" +
                "      \"dataType\": \"float32\",\n" +
                "      \"dimension\": 128,\n" +
                "      \"distanceMetric\": \"cosine\",\n" +
                "      \"metadata\": {\n" +
                "        \"nonFilterableMetadataKeys\": [\"key1\", \"key2\"]\n" +
                "      },\n" +
                "      \"vectorBucketName\": \"test-bucket\",\n" +
                "      \"status\": \"Active\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"nextToken\": \"next-token\"\n" +
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

        ListVectorIndexesResult result = SerdeVectorIndexBasic.toListVectorIndexes(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.indexes()).isNotNull();
        assertThat(result.indexes()).hasSize(1);
        assertThat(result.nextToken()).isEqualTo("next-token");

        IndexSummary index = result.indexes().get(0);
        assertThat(index.createTime()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(index.indexName()).isEqualTo("test-index1");
        assertThat(index.dataType()).isEqualTo("float32");
        assertThat(index.dimension()).isEqualTo(128);
        assertThat(index.distanceMetric()).isEqualTo("cosine");
        assertThat(index.vectorBucketName()).isEqualTo("test-bucket");
        assertThat(index.status()).isEqualTo("Active");

        Map<String, Object> metadata = index.metadata();
        assertThat(metadata).isNotNull();
        List<String> nonFilterableKeys = (List<String>) metadata.get("nonFilterableMetadataKeys");
        assertThat(nonFilterableKeys).containsExactly("key1", "key2");

        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }

    private ListVectorIndexesResultJson createTestListResult() {
        ListVectorIndexesResultJson listResult = new ListVectorIndexesResultJson();
        listResult.nextToken = "next-token";

        IndexSummary indexSummary = IndexSummary.newBuilder()
                .createTime("2023-12-17T00:20:57.000Z")
                .indexName("test-index1")
                .dataType("vector")
                .dimension(128)
                .distanceMetric("EUCLIDEAN")
                .vectorBucketName("test-bucket")
                .status("Active")
                .build();

        Map<String, Object> metadata = new HashMap<>();
        List<String> nonFilterableKeys = Arrays.asList("key1", "key2");
        metadata.put("nonFilterableMetadataKeys", nonFilterableKeys);
        indexSummary = indexSummary.toBuilder().metadata(metadata).build();

        listResult.indexes = Arrays.asList(indexSummary);
        return listResult;
    }
}