package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.QueryVectorsFusionJson;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryVectorsFusionResultTest {

    @Test
    public void testEmptyBuilder() {
        QueryVectorsFusionResult result = QueryVectorsFusionResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.vectors()).isNotNull();
        assertThat(result.vectors()).isEmpty();
        assertThat(result.nextToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        QueryVectorsFusionSummary vectorSummary = createTestQueryVectorsFusionSummary();

        QueryVectorsFusionJson queryVectorsFusionJson = new QueryVectorsFusionJson();
        queryVectorsFusionJson.vectors = Collections.singletonList(vectorSummary);
        queryVectorsFusionJson.nextToken = "next-token-value";

        QueryVectorsFusionResult result = QueryVectorsFusionResult.newBuilder()
                .headers(headers)
                .innerBody(queryVectorsFusionJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.vectors()).isEqualTo(Collections.singletonList(vectorSummary));
        assertThat(result.nextToken()).isEqualTo("next-token-value");
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

        QueryVectorsFusionSummary vectorSummary = createTestQueryVectorsFusionSummary();

        QueryVectorsFusionJson queryVectorsFusionJson = new QueryVectorsFusionJson();
        queryVectorsFusionJson.vectors = Collections.singletonList(vectorSummary);
        queryVectorsFusionJson.nextToken = "original-next-token";

        QueryVectorsFusionResult original = QueryVectorsFusionResult.newBuilder()
                .headers(headers)
                .innerBody(queryVectorsFusionJson)
                .status("Partial")
                .statusCode(206)
                .build();

        QueryVectorsFusionResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.vectors()).isEqualTo(Collections.singletonList(vectorSummary));
        assertThat(copy.nextToken()).isEqualTo("original-next-token");
        assertThat(copy.status()).isEqualTo("Partial");
        assertThat(copy.statusCode()).isEqualTo(206);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {
        String jsonData = "{\n" +
                "  \"vectors\": [\n" +
                "    {\n" +
                "      \"key\": \"vector-key-1\",\n" +
                "      \"score\": 0.85,\n" +
                "      \"metadata\": {\n" +
                "        \"title\": \"hello world\",\n" +
                "        \"timestamps\": 1700000000\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"vector-key-2\",\n" +
                "      \"score\": 0.62\n" +
                "    }\n" +
                "  ],\n" +
                "  \"nextToken\": \"next-token-value\"\n" +
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

        QueryVectorsFusionResult result = SerdeVectorsBasic.toQueryVectorsFusion(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.vectors()).isNotNull();
        assertThat(result.vectors()).hasSize(2);
        assertThat(result.nextToken()).isEqualTo("next-token-value");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");

        QueryVectorsFusionSummary vector = result.vectors().get(0);
        assertThat(vector.key()).isEqualTo("vector-key-1");
        assertThat(vector.score()).isEqualTo(0.85f);
        assertThat(vector.metadata()).isNotNull();
        assertThat(vector.metadata()).containsEntry("title", "hello world");
        assertThat(vector.metadata()).containsEntry("timestamps", 1700000000);

        QueryVectorsFusionSummary secondVector = result.vectors().get(1);
        assertThat(secondVector.key()).isEqualTo("vector-key-2");
        assertThat(secondVector.score()).isEqualTo(0.62f);
        assertThat(secondVector.metadata()).isNull();
    }

    @Test
    public void testSummaryToBuilderPreserveState() {
        QueryVectorsFusionSummary original = createTestQueryVectorsFusionSummary();
        QueryVectorsFusionSummary copy = original.toBuilder().build();

        assertThat(copy.key()).isEqualTo("vector-key-1");
        assertThat(copy.score()).isEqualTo(0.85f);
        assertThat(copy.metadata()).containsEntry("title", "hello world");
    }

    @Test
    public void testSummaryEmptyBuilder() {
        QueryVectorsFusionSummary summary = QueryVectorsFusionSummary.newBuilder().build();
        assertThat(summary).isNotNull();
        assertThat(summary.key()).isNull();
        assertThat(summary.score()).isNull();
        assertThat(summary.metadata()).isNull();
    }

    private QueryVectorsFusionSummary createTestQueryVectorsFusionSummary() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("title", "hello world");

        return QueryVectorsFusionSummary.newBuilder()
                .key("vector-key-1")
                .score(0.85f)
                .metadata(metadata)
                .build();
    }

    @Test
    public void testUnknownFieldsIgnored() {
        // The legacy distance field is not part of the fusion response, it must be ignored.
        String jsonData = "{\"vectors\":[{\"key\":\"vector-key-1\",\"distance\":0.5,\"score\":0.9,\"data\":{\"vector\":[0.1]}}]}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of("x-oss-request-id", "req-unknown-fields"))
                .status("OK")
                .statusCode(200)
                .build();

        QueryVectorsFusionResult result = SerdeVectorsBasic.toQueryVectorsFusion(output);

        assertThat(result.vectors()).hasSize(1);
        assertThat(result.vectors().get(0).key()).isEqualTo("vector-key-1");
        assertThat(result.vectors().get(0).score()).isEqualTo(0.9f);
    }

    @Test
    public void testVectorsWithMetadataFields() {
        QueryVectorsFusionSummary summary = QueryVectorsFusionSummary.newBuilder()
                .key("vector-key-1")
                .score(1.0f)
                .metadata(new HashMap<>(Collections.singletonMap("k", (Object) "v")))
                .build();

        assertThat(summary.metadata()).containsEntry("k", "v");
        assertThat(Arrays.asList(summary)).hasSize(1);
    }
}
