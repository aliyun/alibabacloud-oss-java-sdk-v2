package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateTableRequestTest {

    @Test
    public void testEmptyBuilder() {
        CreateTableRequest request = CreateTableRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.namespace()).isNull();
        assertThat(request.name()).isNull();
    }

    @Test
    public void testFullBuilder() {
        IcebergSchema icebergSchema = new IcebergSchema();
        
        List<SchemaField> fields = new ArrayList<>();
        fields.add(createSchemaField("id", "long", true));
        fields.add(createSchemaField("name", "string", false));
        fields.add(createSchemaField("ts", "timestamptz", false));
        icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
        
        // Create partition spec
        IcebergPartitionField partitionField = IcebergPartitionField.newBuilder()
            .sourceId(2)
            .transform("identity")
            .name("region")
            .fieldId(1001)
            .build();
        // Convert to list for compatibility
        List<IcebergPartitionField> partitionFields = new ArrayList<>();
        partitionFields.add(partitionField);
        IcebergPartitionSpec partitionSpec = IcebergPartitionSpec.newBuilder()
            .specId(0)
            .fields(partitionFields)
            .build();
        
        // Create iceberg metadata
        IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder()
            .schema(icebergSchema)
            .partitionSpec(partitionSpec)
            .build();
        
        TableMetadata metadata = new TableMetadata();
        metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();
        
        EncryptionConfiguration encryptionConfig = new EncryptionConfiguration();
        encryptionConfig = EncryptionConfiguration.newBuilder().sseAlgorithm("AES256").build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Type", "application/json"
        );

        CreateTableRequest request = CreateTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket")
                .namespace("my_namespace")
                .name("my_table")
                .format("iceberg")
                .metadata(metadata)
                .encryptionConfiguration(encryptionConfig)
                .headers(headers)
                .build();

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(request.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");
        assertThat(request.namespace()).isEqualTo("my_namespace");
        assertThat(request.name()).isEqualTo("my_table");
        assertThat(request.format()).isEqualTo("iceberg");
        assertThat(request.metadata()).isNotNull();
        assertThat(request.metadata().iceberg()).isNotNull();
        assertThat(request.metadata().iceberg().schema()).isNotNull();
        assertThat(request.metadata().iceberg().schema().fields()).hasSize(3);
        assertThat(request.encryptionConfiguration()).isNotNull();
        assertThat(request.encryptionConfiguration().sseAlgorithm()).isEqualTo("AES256");
    }

    @Test
    public void testToBuilderPreserveState() {
        IcebergSchema icebergSchema = new IcebergSchema();
        
        List<SchemaField> fields = new ArrayList<>();
        fields.add(createSchemaField("id", "long", true));
        fields.add(createSchemaField("name", "string", false));
        icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
        
        // Create partition spec
        IcebergPartitionField partitionField = IcebergPartitionField.newBuilder()
            .sourceId(2)
            .transform("identity")
            .name("region")
            .fieldId(1001)
            .build();
        // Convert to list for compatibility
        List<IcebergPartitionField> partitionFields = new ArrayList<>();
        partitionFields.add(partitionField);
        IcebergPartitionSpec partitionSpec = IcebergPartitionSpec.newBuilder()
            .specId(0)
            .fields(partitionFields)
            .build();
        
        // Create iceberg metadata
        IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder()
            .schema(icebergSchema)
            .partitionSpec(partitionSpec)
            .build();
        
        TableMetadata metadata = new TableMetadata();
        metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "Content-Type", "application/json"
        );

        CreateTableRequest original = CreateTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket")
                .namespace("original_namespace")
                .name("original_table")
                .format("iceberg")
                .metadata(metadata)
                .headers(headers)
                .build();

        CreateTableRequest copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(copy.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket");
        assertThat(copy.namespace()).isEqualTo("original_namespace");
        assertThat(copy.name()).isEqualTo("original_table");
        assertThat(copy.format()).isEqualTo("iceberg");
        assertThat(copy.metadata()).isNotNull();
        assertThat(copy.metadata().iceberg()).isNotNull();
        assertThat(copy.metadata().iceberg().schema()).isNotNull();
        assertThat(copy.metadata().iceberg().schema().fields()).hasSize(2);
    }

    @Test
    public void jsonBuilder() throws JsonProcessingException {
        String expectedJson = "{\"name\":\"test_table\",\"format\":\"iceberg\",\"metadata\":{\"iceberg\":{\"schema\":{\"fields\":[{\"name\":\"id\",\"type\":\"long\",\"required\":true},{\"name\":\"name\",\"type\":\"string\",\"required\":false},{\"name\":\"ts\",\"type\":\"timestamptz\",\"required\":false}]},\"partitionSpec\":{\"specId\":0,\"fields\":[{\"sourceId\":2,\"transform\":\"identity\",\"name\":\"region\",\"fieldId\":1001}]},\"writeOrder\":{\"orderId\":1,\"fields\":[{\"sourceId\":1,\"transform\":\"identity\",\"direction\":\"asc\",\"nullOrder\":\"nulls-first\"}]},\"properties\":{\"owner\":\"table-owner\",\"environment\":\"production\"}}},\"encryptionConfiguration\":{\"sseAlgorithm\":\"AES256\"}}";
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        IcebergSchema icebergSchema = new IcebergSchema();
        
        List<SchemaField> fields = new ArrayList<>();
        fields.add(createSchemaField("id", "long", true));
        fields.add(createSchemaField("name", "string", false));
        fields.add(createSchemaField("ts", "timestamptz", false));
        icebergSchema = IcebergSchema.newBuilder().fields(fields).build();
        
        // Create partition spec
        IcebergPartitionField partitionField = IcebergPartitionField.newBuilder()
            .sourceId(2)
            .transform("identity")
            .name("region")
            .fieldId(1001)
            .build();
        // Convert to list for compatibility
        List<IcebergPartitionField> partitionFields = new ArrayList<>();
        partitionFields.add(partitionField);
        IcebergPartitionSpec partitionSpec = IcebergPartitionSpec.newBuilder()
            .specId(0)
            .fields(partitionFields)
            .build();
        
        // Create write order
        IcebergWriteOrderField writeOrderField = IcebergWriteOrderField.newBuilder()
            .sourceId(1)
            .transform("identity")
            .direction("asc")
            .nullOrder("nulls-first")
            .build();
        List<IcebergWriteOrderField> writeOrderFields = new ArrayList<>();
        writeOrderFields.add(writeOrderField);
        IcebergWriteOrder writeOrder = IcebergWriteOrder.newBuilder()
            .orderId(1)
            .fields(writeOrderFields)
            .build();
        
        // Create properties map
        Map<String, String> properties = new java.util.HashMap<>();
        properties.put("owner", "table-owner");
        properties.put("environment", "production");
        
        // Create iceberg metadata
        IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder()
            .schema(icebergSchema)
            .partitionSpec(partitionSpec)
            .writeOrder(writeOrder)
            .properties(properties)
            .build();
        
        TableMetadata metadata = new TableMetadata();
        metadata = TableMetadata.newBuilder().iceberg(icebergMetadata).build();
        
        EncryptionConfiguration encryptionConfig = new EncryptionConfiguration();
        encryptionConfig = EncryptionConfiguration.newBuilder().sseAlgorithm("AES256").build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-json-builder-test",
                "Content-Type", "application/json"
        );

        CreateTableRequest request = CreateTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket")
                .namespace("test_namespace")
                .name("test_table")
                .format("iceberg")
                .metadata(metadata)
                .encryptionConfiguration(encryptionConfig)
                .headers(headers)
                .build();

        OperationInput input = SerdeTableBasic.fromCreateTable(request);

        assertThat(input.headers().get("x-oss-request-id")).isEqualTo("req-json-builder-test");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.key().get()).contains("tables/");
        assertThat(input.opName()).isEqualTo("CreateTable");
        
        // Verify the JSON body content
        BinaryData body = input.body().get();
        String jsonContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(jsonContent).contains("\"name\":\"test_table\"");
        assertThat(jsonContent).contains("\"format\":\"iceberg\"");
        assertThat(jsonContent).contains("\"sseAlgorithm\":\"AES256\"");
        assertThat(jsonContent).contains("\"id\"");
        assertThat(jsonContent).contains("\"name\"");
        assertThat(jsonContent).contains("\"ts\"");
        assertThat(jsonContent).contains("\"writeOrder\"");
        assertThat(jsonContent).contains("\"properties\"");
        assertThat(jsonContent).contains("\"owner\"");
        assertThat(jsonContent).contains("\"environment\"");

        // Compare JSON structures instead of raw strings
        JsonNode expectedNode = jsonMapper.readTree(expectedJson);
        JsonNode actualNode = jsonMapper.readTree(jsonContent);
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    private SchemaField createSchemaField(String name, String type, boolean required) {
        return SchemaField.newBuilder()
            .name(name)
            .type(type)
            .required(required)
            .build();
    }
}