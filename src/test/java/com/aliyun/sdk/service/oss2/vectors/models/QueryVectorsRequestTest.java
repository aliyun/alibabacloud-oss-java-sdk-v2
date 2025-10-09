package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorsBasic;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryVectorsRequestTest {

    @Test
    public void testEmptyBuilder() {
        QueryVectorsRequest request = QueryVectorsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.indexName()).isNull();
        assertThat(request.queryVector()).isNull();
        assertThat(request.topK()).isNull();
        assertThat(request.filter()).isNull();
        assertThat(request.returnDistance()).isNull();
        assertThat(request.returnMetadata()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        // Create query vector using Map
        Map<String, Object> queryVector = new HashMap<>();
        queryVector.put("float32", Arrays.asList(0.1f, 0.2f, 0.3f));

        // Create filter
        Map<String, Object> andConditions = new HashMap<>();
        Map<String, Object> typeCondition = new HashMap<>();
        typeCondition.put("$in", Arrays.asList("comedy", "documentary"));
        andConditions.put("type", typeCondition);
        
        Map<String, Object> yearCondition = new HashMap<>();
        yearCondition.put("$gte", 2020);
        andConditions.put("year", yearCondition);
        
        Map<String, Object> filter = new HashMap<>();
        filter.put("$and", Arrays.asList(typeCondition, yearCondition));

        QueryVectorsRequest request = QueryVectorsRequest.newBuilder()
                .bucket("examplebucket")
                .filter(filter)
                .indexName("example-index")
                .queryVector(queryVector)
                .returnDistance(true)
                .returnMetadata(false)
                .topK(10)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.indexName()).isEqualTo("example-index");
        assertThat(request.queryVector()).isSameAs(queryVector);
        assertThat(request.returnDistance()).isEqualTo(true);
        assertThat(request.returnMetadata()).isEqualTo(false);
        assertThat(request.topK()).isEqualTo(10);
        assertThat(request.filter()).isEqualTo(filter);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        // Create query vector using Map
        Map<String, Object> queryVector = new HashMap<>();
        queryVector.put("float32", Arrays.asList(0.4f, 0.5f, 0.6f));

        // Create filter
        Map<String, Object> andConditions = new HashMap<>();
        Map<String, Object> typeCondition = new HashMap<>();
        typeCondition.put("$in", Arrays.asList("action", "adventure"));
        andConditions.put("type", typeCondition);
        
        Map<String, Object> yearCondition = new HashMap<>();
        yearCondition.put("$gte", 2010);
        andConditions.put("year", yearCondition);
        
        Map<String, Object> filter = new HashMap<>();
        filter.put("$and", Arrays.asList(typeCondition, yearCondition));

        QueryVectorsRequest original = QueryVectorsRequest.newBuilder()
                .bucket("examplebucket")
                .filter(filter)
                .indexName("example-index")
                .queryVector(queryVector)
                .returnDistance(false)
                .returnMetadata(true)
                .topK(5)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        QueryVectorsRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");

        assertThat(copy.indexName()).isEqualTo("example-index");
        assertThat(copy.returnDistance()).isEqualTo(false);
        assertThat(copy.returnMetadata()).isEqualTo(true);
        assertThat(copy.topK()).isEqualTo(5);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testQueryVectorDirectSetter() {
        // Create query vector using Map
        Map<String, Object> queryVector = new HashMap<>();
        queryVector.put("float32", Arrays.asList(0.7f, 0.8f, 0.9f));

        QueryVectorsRequest request = QueryVectorsRequest.newBuilder()
                .bucket("config-bucket")
                .indexName("direct-index")
                .queryVector(queryVector)
                .returnDistance(true)
                .returnMetadata(false)
                .topK(20)
                .build();

        assertThat(request.bucket()).isEqualTo("config-bucket");
        assertThat(request.indexName()).isEqualTo("direct-index");
        assertThat(request.queryVector()).isSameAs(queryVector);
        assertThat(request.returnDistance()).isEqualTo(true);
        assertThat(request.returnMetadata()).isEqualTo(false);
        assertThat(request.topK()).isEqualTo(20);
    }
    
    @Test
    public void xmlBuilder() throws Exception {
        String jsonStr = "{\"filter\": {\"$and\": [{\"type\": {\"$in\": [\"comedy\", \"documentary\"]}}, {\"year\": {\"$gte\": 2020}}]}, \"indexName\": \"test-index\", \"queryVector\": {\"float32\": [0.1, 0.2, 0.3]}, \"returnDistance\": true, \"returnMetadata\": false, \"topK\": 10}";

        // Create query vector using Map
        Map<String, Object> queryVector = new HashMap<>();
        queryVector.put("float32", Arrays.asList(0.1f, 0.2f, 0.3f));

        // Create filter
        Map<String, Object> typeCondition = new HashMap<>();
        typeCondition.put("$in", Arrays.asList("comedy", "documentary"));
        
        Map<String, Object> yearCondition = new HashMap<>();
        yearCondition.put("$gte", 2020);
        
        Map<String, Object> andCondition = new HashMap<>();
        andCondition.put("$and", Arrays.asList(typeCondition, yearCondition));

        QueryVectorsRequest request = QueryVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .filter(andCondition)
                .indexName("test-index")
                .queryVector(queryVector)
                .returnDistance(true)
                .returnMetadata(false)
                .topK(10)
                .build();
                
        // Use SerdeVectorsBasic.fromQueryVectors to convert request to OperationInput
        OperationInput operationInput = SerdeVectorsBasic.fromQueryVectors(request);
        
        // Validate the operation input
        assertThat(operationInput).isNotNull();
        assertThat(operationInput.bucket()).isPresent();
        assertThat(operationInput.bucket().get()).isEqualTo("test-bucket");
        assertThat(operationInput.parameters()).containsEntry("queryVectors", "");
        assertThat(operationInput.headers()).containsEntry("Content-Type", "application/json");
        assertThat(operationInput.method()).isEqualTo("POST");
        assertThat(operationInput.opName()).isEqualTo("QueryVectors");
        
        // Parse and compare JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJsonNode = objectMapper.readTree(jsonStr);
        JsonNode actualJsonNode = objectMapper.readTree(operationInput.body().get().toBytes());
        
        assertThat(actualJsonNode.get("indexName").asText()).isEqualTo(expectedJsonNode.get("indexName").asText());
        assertThat(actualJsonNode.get("returnDistance").asBoolean()).isEqualTo(expectedJsonNode.get("returnDistance").asBoolean());
        assertThat(actualJsonNode.get("returnMetadata").asBoolean()).isEqualTo(expectedJsonNode.get("returnMetadata").asBoolean());
        assertThat(actualJsonNode.get("topK").asInt()).isEqualTo(expectedJsonNode.get("topK").asInt());
        
        // Verify queryVector
        JsonNode expectedQueryVector = expectedJsonNode.get("queryVector");
        JsonNode actualQueryVector = actualJsonNode.get("queryVector");
        assertThat(actualQueryVector.get("float32")).isEqualTo(expectedQueryVector.get("float32"));
        
        // Verify filter
        JsonNode expectedFilter = expectedJsonNode.get("filter");
        JsonNode actualFilter = actualJsonNode.get("filter");
        JsonNode expectedAnd = expectedFilter.get("$and");
        JsonNode actualAnd = actualFilter.get("$and");
        assertThat(actualAnd).hasSameSizeAs(expectedAnd);
    }
}