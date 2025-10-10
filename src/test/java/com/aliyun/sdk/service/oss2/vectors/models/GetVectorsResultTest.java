package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.GetVectorsResultJson;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetVectorsResultTest {

    @Test
    public void testEmptyBuilder() {
        GetVectorsResult result = GetVectorsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.statusCode()).isEqualTo(0);
        assertThat(result.status()).isEqualTo("");
        assertThat(result.requestId()).isEqualTo("");
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        VectorsSummary vectorSummary = createTestVectorsSummary();

        GetVectorsResult result = GetVectorsResult.newBuilder()
                .headers(headers)
                .innerBody(createGetVectorsResultJson(Arrays.asList(vectorSummary)))
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.vectors()).containsExactly(vectorSummary);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        VectorsSummary vectorSummary = createTestVectorsSummary();

        GetVectorsResult original = GetVectorsResult.newBuilder()
                .headers(headers)
                .innerBody(createGetVectorsResultJson(Arrays.asList(vectorSummary)))
                .status("Created")
                .statusCode(201)
                .build();

        GetVectorsResult copy = original.toBuilder().build();

        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.vectors()).containsExactly(vectorSummary);
    }

    @Test
    public void xmlBuilder() {
        String jsonData = "{\n" +
                "  \"vectors\": [\n" +
                "    {\n" +
                "      \"data\": {\n" +
                "        \"float32\": [0.1, 0.2, 0.3]\n" +
                "      },\n" +
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

        GetVectorsResult result = SerdeVectorsBasic.toGetVectors(output);

        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
        assertThat(result.headers()).containsEntry("ETag", "\"xml-builder-etag\"");
        assertThat(result.vectors()).isNotNull();
        assertThat(result.vectors()).hasSize(1);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);

        VectorsSummary vector = result.vectors().get(0);
        assertThat(vector.key()).isEqualTo("vector-key-1");
        assertThat(vector.data()).isNotNull();
        assertThat(vector.metadata()).isNotNull();
        
        Map<String, Object> data = vector.data();
        assertThat(data.get("float32")).isNotNull();
        assertThat(data.get("float32")).isInstanceOf(List.class);
        List<Double> float32 = (List<Double>) data.get("float32");
        assertThat(float32).containsExactly(0.1, 0.2, 0.3);

        Map<String, Object> metadata = vector.metadata();
        assertThat(metadata).containsEntry("key1", "value1");
        assertThat(metadata).containsEntry("key2", "value2");
    }

    private VectorsSummary createTestVectorsSummary() {
        Map<String, Object> vectorData = new HashMap<>();
        vectorData.put("float32", Arrays.asList(0.1, 0.2, 0.3));

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", "value2");

        return VectorsSummary.newBuilder()
                .data(vectorData)
                .key("vector-key-1")
                .metadata(metadata)
                .build();
    }
    
    private GetVectorsResultJson createGetVectorsResultJson(List<VectorsSummary> vectorSummaries) {
        GetVectorsResultJson json = new GetVectorsResultJson();
        json.vectors = vectorSummaries;
        return json;
    }
}