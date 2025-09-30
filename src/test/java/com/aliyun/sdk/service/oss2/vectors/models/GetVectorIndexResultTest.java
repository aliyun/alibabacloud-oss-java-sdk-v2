package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorIndexBasic;
import com.aliyun.sdk.service.oss2.vectors.models.IndexInfo;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetVectorIndexResultTest {

    @Test
    public void testEmptyBuilder() {
        GetVectorIndexResult result = GetVectorIndexResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.index()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        IndexInfo indexInfo = createTestIndexInfo();

        GetVectorIndexResult result = GetVectorIndexResult.newBuilder()
                .headers(headers)
                .innerBody(indexInfo)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.index()).isEqualTo(indexInfo.index());
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

        IndexInfo indexInfo = createTestIndexInfo();

        GetVectorIndexResult original = GetVectorIndexResult.newBuilder()
                .headers(headers)
                .innerBody(indexInfo)
                .status("Created")
                .statusCode(201)
                .build();

        GetVectorIndexResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.index()).isEqualTo(indexInfo.index());
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {
        String jsonData = "{\n" +
                "  \"index\": {\n" +
                "    \"createTime\": \"2023-12-17T00:20:57.000Z\",\n" +
                "    \"dataType\": \"float32\",\n" +
                "    \"dimension\": 3,\n" +
                "    \"distanceMetric\": \"cosine\",\n" +
                "    \"indexName\": \"test-index\",\n" +
                "    \"metadata\": {\n" +
                "      \"nonFilterableMetadataKeys\": [\"key1\", \"key2\"]\n" +
                "    },\n" +
                "    \"status\": \"Active\",\n" +
                "    \"vectorBucketName\": \"test-bucket\"\n" +
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

        GetVectorIndexResult result = SerdeVectorIndexBasic.toGetVectorIndex(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.index()).isNotNull();
        assertThat(result.index().get("createTime")).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(result.index().get("dataType")).isEqualTo("float32");
        assertThat(result.index().get("dimension")).isEqualTo(3);
        assertThat(result.index().get("distanceMetric")).isEqualTo("cosine");
        assertThat(result.index().get("indexName")).isEqualTo("test-index");
        assertThat(result.index().get("status")).isEqualTo("Active");
        assertThat(result.index().get("vectorBucketName")).isEqualTo("test-bucket");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");

        Object metadataObj = result.index().get("metadata");
        assertThat(metadataObj).isNotNull();
        assertThat(metadataObj).isInstanceOf(Map.class);
        Map<String, Object> metadata = (Map<String, Object>) metadataObj;

        Object nonFilterableKeysObj = metadata.get("nonFilterableMetadataKeys");
        assertThat(nonFilterableKeysObj).isNotNull();
        assertThat(nonFilterableKeysObj).isInstanceOf(List.class);
        List<String> nonFilterableKeys = (List<String>) nonFilterableKeysObj;
        assertThat(nonFilterableKeys).containsExactly("key1", "key2");
    }

    @Test
    public void testAsIndex() {
        String jsonData = "{\n" +
                "  \"index\": {\n" +
                "    \"createTime\": \"2023-12-17T00:20:57.000Z\",\n" +
                "    \"dataType\": \"float32\",\n" +
                "    \"dimension\": 3,\n" +
                "    \"distanceMetric\": \"cosine\",\n" +
                "    \"indexName\": \"test-index\",\n" +
                "    \"metadata\": {\n" +
                "      \"nonFilterableMetadataKeys\": [\"key1\", \"key2\"]\n" +
                "    },\n" +
                "    \"status\": \"Active\",\n" +
                "    \"vectorBucketName\": \"test-bucket\"\n" +
                "  }\n" +
                "}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-as-index-test",
                        "ETag", "\"as-index-etag\""
                ))
                .status("OK")
                .statusCode(200)
                .build();

        GetVectorIndexResult result = SerdeVectorIndexBasic.toGetVectorIndex(output);
        IndexSummary indexSummary = result.asIndex();

        assertThat(indexSummary).isNotNull();
        assertThat(indexSummary.createTime()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(indexSummary.dataType()).isEqualTo("float32");
        assertThat(indexSummary.dimension()).isEqualTo(3);
        assertThat(indexSummary.distanceMetric()).isEqualTo("cosine");
        assertThat(indexSummary.indexName()).isEqualTo("test-index");
        assertThat(indexSummary.status()).isEqualTo("Active");
        assertThat(indexSummary.vectorBucketName()).isEqualTo("test-bucket");

        assertThat(indexSummary.metadata()).isNotNull();
        Object nonFilterableKeysObj = indexSummary.metadata().get("nonFilterableMetadataKeys");
        assertThat(nonFilterableKeysObj).isNotNull();
        assertThat(nonFilterableKeysObj).isInstanceOf(List.class);
        List<String> nonFilterableKeys = (List<String>) nonFilterableKeysObj;
        assertThat(nonFilterableKeys).containsExactly("key1", "key2");
    }

    private IndexInfo createTestIndexInfo() {
        IndexInfo indexInfo = IndexInfo.newBuilder().build();
        Map<String, Object> index = new HashMap<>();
        index.put("createTime", "2023-12-17T00:20:57.000Z");
        index.put("dataType", "vector");
        index.put("dimension", 128);
        index.put("distanceMetric", "EUCLIDEAN");
        index.put("indexName", "test-index");

        Map<String, Object> metadata = new HashMap<>();
        List<String> nonFilterableKeys = Arrays.asList("key1", "key2");
        metadata.put("nonFilterableMetadataKeys", nonFilterableKeys);
        index.put("metadata", metadata);

        index.put("status", "Active");
        index.put("vectorBucketName", "test-bucket");
        indexInfo = indexInfo.toBuilder().index(index).build();
        return indexInfo;
    }
}