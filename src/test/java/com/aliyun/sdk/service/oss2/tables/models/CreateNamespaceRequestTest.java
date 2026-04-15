package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeNamespaceBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateNamespaceRequestTest {

    @Test
    public void testEmptyBuilder() {
        CreateNamespaceRequest request = CreateNamespaceRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.namespace()).isNull();
    }

    @Test
    public void testFullBuilder() {
        List<String> namespace = new ArrayList<>();
        namespace.add("my_namespace");

        CreateNamespaceRequest request = CreateNamespaceRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket")
                .namespace(namespace)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");
        assertThat(request.namespace()).containsExactly("my_namespace");

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
        List<String> namespace = new ArrayList<>();
        namespace.add("original_namespace");

        CreateNamespaceRequest original = CreateNamespaceRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket")
                .namespace(namespace)
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        CreateNamespaceRequest copy = original.toBuilder().build();

        assertThat(copy.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket");
        assertThat(copy.namespace()).containsExactly("original_namespace");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void jsonBuilder() throws JsonProcessingException {
        String expectedJson = "{\"namespace\":[\"my_namespace\"]}";
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        List<String> namespace = new ArrayList<>();
        namespace.add("my_namespace");

        CreateNamespaceRequest request = CreateNamespaceRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket")
                .namespace(namespace)
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeNamespaceBasic.fromCreateNamespace(request);

        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.opName()).isEqualTo("CreateNamespace");

        // Verify the JSON body content
        BinaryData body = input.body().get();
        String jsonContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(jsonContent).contains("\"namespace\":[\"my_namespace\"]");

        // Compare with expected JSON
        assertThat(jsonContent).isEqualTo(expectedJson);
    }
}