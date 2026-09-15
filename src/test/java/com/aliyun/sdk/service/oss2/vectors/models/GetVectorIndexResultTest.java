package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.GetVectorIndexResultJson;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorIndexBasic;
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

        GetVectorIndexResultJson indexInfo = createTestGetVectorIndexBodyJson();

        GetVectorIndexResult result = GetVectorIndexResult.newBuilder()
                .headers(headers)
                .innerBody(indexInfo)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.index()).isEqualTo(indexInfo.index);
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

        GetVectorIndexResultJson indexInfo = createTestGetVectorIndexBodyJson();

        GetVectorIndexResult original = GetVectorIndexResult.newBuilder()
                .headers(headers)
                .innerBody(indexInfo)
                .status("Created")
                .statusCode(201)
                .build();

        GetVectorIndexResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.index()).isEqualTo(indexInfo.index);
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
                "    \"bucketArn\": \"acs:oss:::test-bucket\",\n" +
                "    \"mode\": \"standard\"\n" +
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
        assertThat(result.index().createTime()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(result.index().dataType()).isEqualTo("float32");
        assertThat(result.index().dimension()).isEqualTo(3);
        assertThat(result.index().distanceMetric()).isEqualTo("cosine");
        assertThat(result.index().indexName()).isEqualTo("test-index");
        assertThat(result.index().status()).isEqualTo("Active");
        assertThat(result.index().bucketArn()).isEqualTo("acs:oss:::test-bucket");
        assertThat(result.index().mode()).isEqualTo("standard");
        assertThat(result.index().schemaConfiguration()).isNull();
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");

        Object metadataObj = result.index().metadata();
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
                "    \"bucketArn\": \"acs:oss:::test-bucket\",\n" +
                "    \"mode\": \"standard\"\n" +
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
        IndexSummary indexSummary = result.index();

        assertThat(indexSummary).isNotNull();
        assertThat(indexSummary.createTime()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(indexSummary.dataType()).isEqualTo("float32");
        assertThat(indexSummary.dimension()).isEqualTo(3);
        assertThat(indexSummary.distanceMetric()).isEqualTo("cosine");
        assertThat(indexSummary.indexName()).isEqualTo("test-index");
        assertThat(indexSummary.status()).isEqualTo("Active");
        assertThat(indexSummary.bucketArn()).isEqualTo("acs:oss:::test-bucket");
        assertThat(indexSummary.mode()).isEqualTo("standard");
        assertThat(indexSummary.schemaConfiguration()).isNull();

        assertThat(indexSummary.metadata()).isNotNull();
        Object nonFilterableKeysObj = indexSummary.metadata().get("nonFilterableMetadataKeys");
        assertThat(nonFilterableKeysObj).isNotNull();
        assertThat(nonFilterableKeysObj).isInstanceOf(List.class);
        List<String> nonFilterableKeys = (List<String>) nonFilterableKeysObj;
        assertThat(nonFilterableKeys).containsExactly("key1", "key2");
    }

    @Test
    public void testLegacyVectorBucketNameOnly() {
        String jsonData = "{\n" +
                "  \"index\": {\n" +
                "    \"indexName\": \"test-index\",\n" +
                "    \"status\": \"Active\",\n" +
                "    \"vectorBucketName\": \"test-bucket\"\n" +
                "  }\n" +
                "}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of("x-oss-request-id", "req-legacy-test"))
                .status("OK")
                .statusCode(200)
                .build();

        GetVectorIndexResult result = SerdeVectorIndexBasic.toGetVectorIndex(output);

        assertThat(result.index()).isNotNull();
        assertThat(result.index().indexName()).isEqualTo("test-index");
        assertThat(result.index().vectorBucketName()).isEqualTo("test-bucket");
        assertThat(result.index().bucketArn()).isNull();
    }

    @Test
    public void testBothBucketFields() {
        String jsonData = "{\n" +
                "  \"index\": {\n" +
                "    \"indexName\": \"test-index\",\n" +
                "    \"status\": \"Active\",\n" +
                "    \"vectorBucketName\": \"test-bucket\",\n" +
                "    \"bucketArn\": \"acs:oss:::test-bucket\"\n" +
                "  }\n" +
                "}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of("x-oss-request-id", "req-both-test"))
                .status("OK")
                .statusCode(200)
                .build();

        GetVectorIndexResult result = SerdeVectorIndexBasic.toGetVectorIndex(output);

        assertThat(result.index()).isNotNull();
        assertThat(result.index().vectorBucketName()).isEqualTo("test-bucket");
        assertThat(result.index().bucketArn()).isEqualTo("acs:oss:::test-bucket");
    }

    @Test
    public void testFusionIndexWithSchemaConfiguration() {
        String jsonData = "{\n" +
                "  \"index\": {\n" +
                "    \"createTime\": \"2023-12-17T00:20:57.000Z\",\n" +
                "    \"indexName\": \"fusion-index\",\n" +
                "    \"status\": \"Active\",\n" +
                "    \"mode\": \"fusion\",\n" +
                "    \"bucketArn\": \"acs:oss:::test-bucket\",\n" +
                "    \"schemaConfiguration\": {\n" +
                "      \"fields\": [\n" +
                "        {\n" +
                "          \"name\": \"vector_1\",\n" +
                "          \"type\": \"vector\",\n" +
                "          \"dataType\": \"float32\",\n" +
                "          \"dimension\": 1024,\n" +
                "          \"distanceMetric\": \"euclidean\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"timestamps\",\n" +
                "          \"type\": \"long\",\n" +
                "          \"isArray\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"user_id\",\n" +
                "          \"type\": \"string\",\n" +
                "          \"isPartitionKey\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"title_1\",\n" +
                "          \"type\": \"string\",\n" +
                "          \"exactMatch\": true,\n" +
                "          \"text\": {\n" +
                "            \"enabled\": true,\n" +
                "            \"analyzer\": \"standard\",\n" +
                "            \"analyzerParameters\": {\n" +
                "              \"caseSensitive\": true,\n" +
                "              \"delimitWord\": false,\n" +
                "              \"delimiter\": \",\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of("x-oss-request-id", "req-fusion-test"))
                .status("OK")
                .statusCode(200)
                .build();

        GetVectorIndexResult result = SerdeVectorIndexBasic.toGetVectorIndex(output);
        IndexSummary indexSummary = result.index();

        assertThat(indexSummary).isNotNull();
        assertThat(indexSummary.indexName()).isEqualTo("fusion-index");
        assertThat(indexSummary.mode()).isEqualTo("fusion");
        assertThat(indexSummary.schemaConfiguration()).isNotNull();
        assertThat(indexSummary.schemaConfiguration().fields()).hasSize(4);

        FieldSchema vectorField = indexSummary.schemaConfiguration().fields().get(0);
        assertThat(vectorField.name()).isEqualTo("vector_1");
        assertThat(vectorField.type()).isEqualTo("vector");
        assertThat(vectorField.dataType()).isEqualTo("float32");
        assertThat(vectorField.dimension()).isEqualTo(1024);
        assertThat(vectorField.distanceMetric()).isEqualTo("euclidean");

        FieldSchema arrayField = indexSummary.schemaConfiguration().fields().get(1);
        assertThat(arrayField.name()).isEqualTo("timestamps");
        assertThat(arrayField.type()).isEqualTo("long");
        assertThat(arrayField.isArray()).isTrue();

        FieldSchema partitionField = indexSummary.schemaConfiguration().fields().get(2);
        assertThat(partitionField.name()).isEqualTo("user_id");
        assertThat(partitionField.isPartitionKey()).isTrue();

        FieldSchema textField = indexSummary.schemaConfiguration().fields().get(3);
        assertThat(textField.name()).isEqualTo("title_1");
        assertThat(textField.exactMatch()).isTrue();
        assertThat(textField.text()).isNotNull();
        assertThat(textField.text().enabled()).isTrue();
        assertThat(textField.text().analyzer()).isEqualTo("standard");
        assertThat(textField.text().analyzerParameters()).isNotNull();
        assertThat(textField.text().analyzerParameters().caseSensitive()).isTrue();
        assertThat(textField.text().analyzerParameters().delimitWord()).isFalse();
        assertThat(textField.text().analyzerParameters().delimiter()).isEqualTo(",");
    }

    @Test
    public void testIndexSummaryToBuilderPreserveMode() {
        SchemaConfiguration schemaConfiguration = SchemaConfiguration.newBuilder()
                .fields(Arrays.asList(FieldSchema.newBuilder().name("vector_1").type("vector").build()))
                .build();

        IndexSummary original = IndexSummary.newBuilder()
                .indexName("fusion-index")
                .mode("fusion")
                .schemaConfiguration(schemaConfiguration)
                .build();

        IndexSummary copy = original.toBuilder().build();

        assertThat(copy.indexName()).isEqualTo("fusion-index");
        assertThat(copy.mode()).isEqualTo("fusion");
        assertThat(copy.schemaConfiguration()).isSameAs(schemaConfiguration);
    }

    @Test
    public void testIndexSummaryEnumOverloads() {
        IndexSummary summary = IndexSummary.newBuilder()
                .indexName("fusion-index")
                .mode(IndexModeType.FUSION)
                .dataType(VectorDataType.FLOAT32)
                .distanceMetric(DistanceMetricType.COSINE)
                .build();

        // the enum overloads store the serialized string value, getters still return String
        assertThat(summary.mode()).isEqualTo("fusion");
        assertThat(summary.dataType()).isEqualTo("float32");
        assertThat(summary.distanceMetric()).isEqualTo("cosine");

        // toBuilder preserves the values set via the enum overloads
        IndexSummary copy = summary.toBuilder().build();
        assertThat(copy.mode()).isEqualTo("fusion");
        assertThat(copy.dataType()).isEqualTo("float32");
        assertThat(copy.distanceMetric()).isEqualTo("cosine");
    }

    private GetVectorIndexResultJson createTestGetVectorIndexBodyJson() {

        IndexSummary.Builder builder = IndexSummary.newBuilder();

        builder.createTime("2023-12-17T00:20:57.000Z");
        builder.dataType("vector");
        builder.dimension(128);
        builder.distanceMetric("EUCLIDEAN");
        builder.indexName("test-index");
        builder.dimension(128);

        Map<String, Object> metadata = new HashMap<>();
        List<String> nonFilterableKeys = Arrays.asList("key1", "key2");
        metadata.put("nonFilterableMetadataKeys", nonFilterableKeys);
        builder.metadata(metadata);

        builder.status("Active");
        builder.bucketArn("acs:oss:::test-bucket");
        builder.mode("standard");

        GetVectorIndexResultJson bodyJson = new GetVectorIndexResultJson();
        bodyJson.index = builder.build();

        return bodyJson;
    }
}