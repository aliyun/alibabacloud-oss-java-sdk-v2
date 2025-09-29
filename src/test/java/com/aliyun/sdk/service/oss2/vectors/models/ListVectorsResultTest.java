package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorsResultJson;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListVectorsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListVectorsResult result = ListVectorsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.nextToken()).isNull();
        assertThat(result.vectors()).isNull();
        assertThat(result.asVectors()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListVectorsResultJson listResult = createTestListResult();

        ListVectorsResult result = ListVectorsResult.newBuilder()
                .headers(headers)
                .innerBody(listResult)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.nextToken()).isEqualTo("next-token");
        assertThat(result.vectors()).isEqualTo(listResult.vectors);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");

        // Test asVectors method
        ListVectorsInfo vectorsInfo = result.asVectors();
        assertThat(vectorsInfo).isNotNull();
        assertThat(vectorsInfo.nextToken()).isEqualTo("next-token");
        assertThat(vectorsInfo.vectors()).isNotNull();
        assertThat(vectorsInfo.vectors()).hasSize(1);

        ListOutputVector vector = vectorsInfo.vectors().get(0);
        assertThat(vector.key()).isEqualTo("vector-key-1");
        assertThat(vector.data()).isNotNull();
        assertThat(vector.metadata()).isNotNull();
        
        Map<String, Object> data = vector.data();
        assertThat(data).containsKey("float32");
        Object float32Obj = data.get("float32");
        assertThat(float32Obj).isInstanceOf(List.class);
        List<Float> float32 = (List<Float>) float32Obj;
        assertThat(float32).containsExactly(0.1f, 0.2f, 0.3f);
        
        Map<String, Object> metadata = vector.metadata();
        assertThat(metadata).containsEntry("key1", "value1");
        assertThat(metadata).containsEntry("key2", "value2");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListVectorsResultJson listResult = createTestListResult();

        ListVectorsResult original = ListVectorsResult.newBuilder()
                .headers(headers)
                .innerBody(listResult)
                .status("Partial")
                .statusCode(206)
                .build();

        ListVectorsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.nextToken()).isEqualTo("next-token");
        assertThat(copy.vectors()).isEqualTo(listResult.vectors);
        assertThat(copy.status()).isEqualTo("Partial");
        assertThat(copy.statusCode()).isEqualTo(206);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");

        // Test asVectors method
        ListVectorsInfo vectorsInfo = copy.asVectors();
        assertThat(vectorsInfo).isNotNull();
        assertThat(vectorsInfo.nextToken()).isEqualTo("next-token");
        assertThat(vectorsInfo.vectors()).isNotNull();
        assertThat(vectorsInfo.vectors()).hasSize(1);

        ListOutputVector vector = vectorsInfo.vectors().get(0);
        assertThat(vector.key()).isEqualTo("vector-key-1");
        assertThat(vector.data()).isNotNull();
        assertThat(vector.metadata()).isNotNull();
        
        Map<String, Object> data = vector.data();
        assertThat(data).containsKey("float32");
        Object float32Obj = data.get("float32");
        assertThat(float32Obj).isInstanceOf(List.class);
        List<Float> float32 = (List<Float>) float32Obj;
        assertThat(float32).containsExactly(0.1f, 0.2f, 0.3f);
        
        Map<String, Object> metadata = vector.metadata();
        assertThat(metadata).containsEntry("key1", "value1");
        assertThat(metadata).containsEntry("key2", "value2");
    }

    @Test
    public void xmlBuilder() {
        String jsonData = "{\n" +
                "  \"nextToken\": \"next-token\",\n" +
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

        ListVectorsResult result = SerdeVectorsBasic.toListVectors(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.nextToken()).isEqualTo("next-token");
        assertThat(result.vectors()).isNotNull();
        assertThat(result.vectors()).hasSize(1);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");

        Map<String, Object> vector = result.vectors().get(0);
        assertThat(vector).containsKey("key");
        assertThat(vector.get("key")).isEqualTo("vector-key-1");
        
        assertThat(vector).containsKey("data");
        Object dataObj = vector.get("data");
        assertThat(dataObj).isInstanceOf(Map.class);
        Map<String, Object> data = (Map<String, Object>) dataObj;
        assertThat(data).containsKey("float32");
        Object float32Obj = data.get("float32");
        assertThat(float32Obj).isInstanceOf(List.class);
        List<Double> float32 = (List<Double>) float32Obj;
        assertThat(float32).containsExactly(0.1, 0.2, 0.3);
        
        assertThat(vector).containsKey("metadata");
        Object metadataObj = vector.get("metadata");
        assertThat(metadataObj).isInstanceOf(Map.class);
        Map<String, Object> metadata = (Map<String, Object>) metadataObj;
        assertThat(metadata).containsEntry("key1", "value1");
        assertThat(metadata).containsEntry("key2", "value2");

        // Test asVectors method
        ListVectorsInfo vectorsInfo = result.asVectors();
        assertThat(vectorsInfo).isNotNull();
        assertThat(vectorsInfo.nextToken()).isEqualTo("next-token");
        assertThat(vectorsInfo.vectors()).isNotNull();
        assertThat(vectorsInfo.vectors()).hasSize(1);

        ListOutputVector outputVector = vectorsInfo.vectors().get(0);
        assertThat(outputVector.key()).isEqualTo("vector-key-1");
        assertThat(outputVector.data()).isNotNull();
        assertThat(outputVector.metadata()).isNotNull();
        
        Map<String, Object> outputData = outputVector.data();
        assertThat(outputData).containsKey("float32");
        Object outputFloat32Obj = outputData.get("float32");
        assertThat(outputFloat32Obj).isInstanceOf(List.class);
        List<Double> outputFloat32 = (List<Double>) outputFloat32Obj;
        assertThat(outputFloat32).containsExactly(0.1, 0.2, 0.3);
        
        Map<String, Object> outputMetadata = outputVector.metadata();
        assertThat(outputMetadata).containsEntry("key1", "value1");
        assertThat(outputMetadata).containsEntry("key2", "value2");
    }

    private ListVectorsResultJson createTestListResult() {
        ListVectorsResultJson listResult = new ListVectorsResultJson();
        listResult.nextToken = "next-token";

        Map<String, Object> vector = new HashMap<>();
        vector.put("key", "vector-key-1");

        Map<String, Object> data = new HashMap<>();
        List<Float> float32 = Arrays.asList(0.1f, 0.2f, 0.3f);
        data.put("float32", float32);
        vector.put("data", data);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", "value2");
        vector.put("metadata", metadata);

        listResult.vectors = Arrays.asList(vector);
        return listResult;
    }
}