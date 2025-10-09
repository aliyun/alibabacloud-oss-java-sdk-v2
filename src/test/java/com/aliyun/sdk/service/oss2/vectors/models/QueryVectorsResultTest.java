package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.QueryVectorsJson;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryVectorsResultTest {

    @Test
    public void testEmptyBuilder() {
        QueryVectorsResult result = QueryVectorsResult.newBuilder().build();
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

        QueryVectorsSummary vectorSummary = createTestQueryVectorSummary();

        QueryVectorsJson queryVectorsJson = new QueryVectorsJson();
        queryVectorsJson.vectors = Arrays.asList(vectorSummary);

        QueryVectorsResult result = QueryVectorsResult.newBuilder()
                .headers(headers)
                .innerBody(queryVectorsJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.vectors()).isEqualTo(Arrays.asList(vectorSummary));
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

        QueryVectorsSummary vectorSummary = createTestQueryVectorSummary();

        QueryVectorsJson queryVectorsJson = new QueryVectorsJson();
        queryVectorsJson.vectors = Arrays.asList(vectorSummary);

        QueryVectorsResult original = QueryVectorsResult.newBuilder()
                .headers(headers)
                .innerBody(queryVectorsJson)
                .status("Partial")
                .statusCode(206)
                .build();

        QueryVectorsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.vectors()).isEqualTo(Arrays.asList(vectorSummary));
        assertThat(copy.status()).isEqualTo("Partial");
        assertThat(copy.statusCode()).isEqualTo(206);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {
        String jsonData = "{\n" +
                "  \"vectors\": [\n" +
                "    {\n" +
                "      \"data\": {\n" +
                "        \"float32\": [0.1, 0.2, 0.3]\n" +
                "      },\n" +
                "      \"distance\": 1,\n" +
                "      \"key\": \"vector-key-1\",\n" +
                "      \"metadata\": {\n" +
                "        \"key1\": \"value1\",\n" +
                "        \"key2\": \"value2\"\n" +
                "      }\n" +
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

        QueryVectorsResult result = SerdeVectorsBasic.toQueryVectors(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.vectors()).isNotNull();
        assertThat(result.vectors()).hasSize(1);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");

        QueryVectorsSummary vector = result.vectors().get(0);
        assertThat(vector.key()).isEqualTo("vector-key-1");
        assertThat(vector.distance()).isEqualTo(1);
        assertThat(vector.data()).isNotNull();
        assertThat(vector.metadata()).isNotNull();
        
        Map<String, Object> data = vector.data();
        assertThat(data).containsKey("float32");
        Object float32Obj = data.get("float32");
        assertThat(float32Obj).isInstanceOf(List.class);
        List<Double> float32 = (List<Double>) float32Obj;
        assertThat(float32).containsExactly(0.1, 0.2, 0.3);
        
        Map<String, Object> metadata = vector.metadata();
        assertThat(metadata).containsEntry("key1", "value1");
        assertThat(metadata).containsEntry("key2", "value2");
    }
    
    private QueryVectorsSummary createTestQueryVectorSummary() {
        QueryVectorsSummary vectorSummary = QueryVectorsSummary.newBuilder()
                .key("vector-key-1")
                .distance(1)
                .build();

        Map<String, Object> data = new HashMap<>();
        List<Float> float32 = Arrays.asList(0.1f, 0.2f, 0.3f);
        data.put("float32", float32);
        vectorSummary = vectorSummary.toBuilder().data(data).build();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", "value2");
        vectorSummary = vectorSummary.toBuilder().metadata(metadata).build();
        
        return vectorSummary;
    }
}