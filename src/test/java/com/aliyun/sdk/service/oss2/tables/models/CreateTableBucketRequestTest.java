package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBucketBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateTableBucketRequestTest {

    @Test
    public void testEmptyBuilder() {
        CreateTableBucketRequest request = CreateTableBucketRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.name()).isNull();
        assertThat(request.encryptionConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        EncryptionConfiguration encryptionConfig = EncryptionConfiguration.newBuilder()
                .kmsKeyArn("test-kms-key-arn")
                .sseAlgorithm("AES256")
                .build();

        CreateTableBucketRequest request = CreateTableBucketRequest.newBuilder()
                .name("test-bucket-name")
                .encryptionConfiguration(encryptionConfig)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.name()).isEqualTo("test-bucket-name");
        assertThat(request.encryptionConfiguration().kmsKeyArn()).isEqualTo("test-kms-key-arn");
        assertThat(request.encryptionConfiguration().sseAlgorithm()).isEqualTo("AES256");

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
        EncryptionConfiguration encryptionConfig = EncryptionConfiguration.newBuilder()
                .kmsKeyArn("original-kms-key-arn")
                .sseAlgorithm("AES256")
                .build();

        CreateTableBucketRequest original = CreateTableBucketRequest.newBuilder()
                .name("original-bucket-name")
                .encryptionConfiguration(encryptionConfig)
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        CreateTableBucketRequest copy = original.toBuilder().build();

        assertThat(copy.name()).isEqualTo("original-bucket-name");
        assertThat(copy.encryptionConfiguration().kmsKeyArn()).isEqualTo("original-kms-key-arn");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void jsonBuilder() throws JsonProcessingException {
        String expectedJson = "{\"name\":\"test-bucket-name\",\"encryptionConfiguration\":{\"sseAlgorithm\":\"AES256\",\"kmsKeyArn\":\"test-kms-key-arn\"}}";
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        EncryptionConfiguration encryptionConfig = EncryptionConfiguration.newBuilder()
                .kmsKeyArn("test-kms-key-arn")
                .sseAlgorithm("AES256")
                .build();

        CreateTableBucketRequest request = CreateTableBucketRequest.newBuilder()
                .name("test-bucket-name")
                .encryptionConfiguration(encryptionConfig)
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableBucketBasic.fromCreateTableBucket(request);

        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.opName()).isEqualTo("CreateTableBucket");

        // Verify the JSON body content
        BinaryData body = input.body().get();
        String jsonContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(jsonContent).contains("\"name\":\"test-bucket-name\"");
        assertThat(jsonContent).contains("\"sseAlgorithm\":\"AES256\"");
        assertThat(jsonContent).contains("\"kmsKeyArn\":\"test-kms-key-arn\"");

        // Compare with expected JSON
        assertThat(jsonContent).isEqualTo(expectedJson);
    }
}