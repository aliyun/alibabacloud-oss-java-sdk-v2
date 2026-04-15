package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableConfigBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateTableMetadataLocationRequestTest {

    @Test
    public void testEmptyBuilder() {
        UpdateTableMetadataLocationRequest request = UpdateTableMetadataLocationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.namespace()).isNull();
        assertThat(request.name()).isNull();
        assertThat(request.versionToken()).isNull();
        assertThat(request.metadataLocation()).isNull();
    }

    @Test
    public void testFullBuilder() {
        UpdateTableMetadataLocationRequest request = UpdateTableMetadataLocationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .namespace("test-namespace")
                .name("test-table")
                .versionToken("aaabbb")
                .metadataLocation("oss://data-bucket/metadata/00001-xxx.metadata.json")
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket");
        assertThat(request.namespace()).isEqualTo("test-namespace");
        assertThat(request.name()).isEqualTo("test-table");
        assertThat(request.versionToken()).isEqualTo("aaabbb");
        assertThat(request.metadataLocation()).isEqualTo("oss://data-bucket/metadata/00001-xxx.metadata.json");

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
        UpdateTableMetadataLocationRequest original = UpdateTableMetadataLocationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket")
                .namespace("original-namespace")
                .name("original-table")
                .versionToken("originalToken")
                .metadataLocation("oss://data-bucket/metadata/original.metadata.json")
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        UpdateTableMetadataLocationRequest copy = original.toBuilder().build();

        assertThat(copy.tableBucketARN()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket");
        assertThat(copy.namespace()).isEqualTo("original-namespace");
        assertThat(copy.name()).isEqualTo("original-table");
        assertThat(copy.versionToken()).isEqualTo("originalToken");
        assertThat(copy.metadataLocation()).isEqualTo("oss://data-bucket/metadata/original.metadata.json");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String expectedJson = "{\"versionToken\":\"aaabbb\",\"metadataLocation\":\"oss://data-bucket/metadata/00001-xxx.metadata.json\"}";
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        UpdateTableMetadataLocationRequest request = UpdateTableMetadataLocationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .namespace("test-namespace")
                .name("test-table")
                .versionToken("aaabbb")
                .metadataLocation("oss://data-bucket/metadata/00001-xxx.metadata.json")
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableConfigBasic.fromUpdateTableMetadataLocation(request);

        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.opName()).isEqualTo("UpdateTableMetadataLocation");

        BinaryData body = input.body().get();
        String jsonContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(jsonContent).contains("\"versionToken\":\"aaabbb\"");
        assertThat(jsonContent).contains("\"metadataLocation\":\"oss://data-bucket/metadata/00001-xxx.metadata.json\"");

        assertThat(jsonContent).isEqualTo(expectedJson);
    }
}
