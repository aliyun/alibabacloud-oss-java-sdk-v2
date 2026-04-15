package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class RenameTableRequestTest {

    @Test
    public void testEmptyBuilder() {
        RenameTableRequest request = RenameTableRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.namespace()).isNull();
        assertThat(request.name()).isNull();
        assertThat(request.newNamespaceName()).isNull();
        assertThat(request.newName()).isNull();
        assertThat(request.versionToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        RenameTableRequest request = RenameTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket")
                .namespace("my-namespace")
                .name("my-table")
                .newNamespaceName("new-namespace")
                .newName("new-table-name")
                .versionToken("aaabbb")
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");
        assertThat(request.namespace()).isEqualTo("my-namespace");
        assertThat(request.name()).isEqualTo("my-table");
        assertThat(request.newNamespaceName()).isEqualTo("new-namespace");
        assertThat(request.newName()).isEqualTo("new-table-name");
        assertThat(request.versionToken()).isEqualTo("aaabbb");

        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-oss-header1", "header-value1"),
                new AbstractMap.SimpleEntry<>("x-oss-header2", "header-value2")
        );

        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        RenameTableRequest original = RenameTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/original-bucket")
                .namespace("original-namespace")
                .name("original-table")
                .newNamespaceName("original-new-namespace")
                .newName("original-new-name")
                .versionToken("original-version-token")
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        RenameTableRequest copy = original.toBuilder().build();

        assertThat(copy.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/original-bucket");
        assertThat(copy.namespace()).isEqualTo("original-namespace");
        assertThat(copy.name()).isEqualTo("original-table");
        assertThat(copy.newNamespaceName()).isEqualTo("original-new-namespace");
        assertThat(copy.newName()).isEqualTo("original-new-name");
        assertThat(copy.versionToken()).isEqualTo("original-version-token");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void jsonBuilder() throws JsonProcessingException {
        String expectedJson = "{\"newNamespaceName\":\"new-namespace\",\"newName\":\"new-table-name\",\"versionToken\":\"aaabbb\"}";
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        RenameTableRequest request = RenameTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket")
                .namespace("my-namespace")
                .name("my-table")
                .newNamespaceName("new-namespace")
                .newName("new-table-name")
                .versionToken("aaabbb")
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableBasic.fromRenameTable(request);

        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.opName()).isEqualTo("RenameTable");
        assertThat(input.bucket().get()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");


        // Verify the JSON body content
        BinaryData body = input.body().get();
        String jsonContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(jsonContent).contains("\"newNamespaceName\":\"new-namespace\"");
        assertThat(jsonContent).contains("\"newName\":\"new-table-name\"");
        assertThat(jsonContent).contains("\"versionToken\":\"aaabbb\"");

        // Compare JSON structures instead of raw strings
        JsonNode expectedNode = jsonMapper.readTree(expectedJson);
        JsonNode actualNode = jsonMapper.readTree(jsonContent);
        assertThat(actualNode).isEqualTo(expectedNode);
    }
}