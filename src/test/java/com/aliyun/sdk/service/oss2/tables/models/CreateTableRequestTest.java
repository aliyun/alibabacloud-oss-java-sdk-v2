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
import java.util.Arrays;
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

    @Test
    public void testSchemaV2Builder() {
        IcebergSchemaV2 schemaV2 = IcebergSchemaV2.newBuilder()
            .schemaId(0)
            .identifierFieldIds(Arrays.asList(10))
            .fields(Arrays.asList(
                SchemaV2Field.newBuilder().id(10).name("order_id").type("long").required(true).doc("order id").build(),
                SchemaV2Field.newBuilder().id(20).name("customer").required(false)
                    .type(IcebergStructType.newBuilder()
                        .fields(Arrays.asList(
                            SchemaV2Field.newBuilder().id(21).name("name").type("string").required(false).build(),
                            SchemaV2Field.newBuilder().id(22).name("level").type("int").required(false).build()))
                        .build())
                    .build(),
                SchemaV2Field.newBuilder().id(30).name("tags").required(false)
                    .type(IcebergListType.newBuilder()
                        .elementId(31)
                        .element("string")
                        .elementRequired(false)
                        .build())
                    .build(),
                SchemaV2Field.newBuilder().id(40).name("attributes").required(false)
                    .type(IcebergMapType.newBuilder()
                        .keyId(41)
                        .key("string")
                        .valueId(42)
                        .value("string")
                        .valueRequired(false)
                        .build())
                    .build()))
            .build();

        IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder().schemaV2(schemaV2).build();
        CreateTableRequest request = CreateTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket")
                .namespace("test_namespace")
                .name("orders_nested")
                .format("ICEBERG")
                .metadata(TableMetadata.newBuilder().iceberg(icebergMetadata).build())
                .build();

        CreateTableRequest copy = request.toBuilder().build();
        assertThat(copy.metadata().iceberg().schema()).isNull();
        IcebergSchemaV2 v2 = copy.metadata().iceberg().schemaV2();
        assertThat(v2).isNotNull();
        assertThat(v2.type()).isEqualTo("struct");
        assertThat(v2.schemaId()).isEqualTo(0);
        assertThat(v2.identifierFieldIds()).containsExactly(10);
        assertThat(v2.fields()).hasSize(4);

        SchemaV2Field orderId = v2.fields().get(0);
        assertThat(orderId.id()).isEqualTo(10);
        assertThat(orderId.name()).isEqualTo("order_id");
        assertThat(orderId.type()).isEqualTo("long");
        assertThat(orderId.required()).isTrue();
        assertThat(orderId.doc()).isEqualTo("order id");

        IcebergStructType customer = (IcebergStructType) v2.fields().get(1).type();
        assertThat(customer.type()).isEqualTo("struct");
        assertThat(customer.fields()).hasSize(2);
        assertThat(customer.fields().get(1).name()).isEqualTo("level");
        assertThat(customer.fields().get(1).type()).isEqualTo("int");

        IcebergListType tags = (IcebergListType) v2.fields().get(2).type();
        assertThat(tags.type()).isEqualTo("list");
        assertThat(tags.elementId()).isEqualTo(31);
        assertThat(tags.element()).isEqualTo("string");
        assertThat(tags.elementRequired()).isFalse();

        IcebergMapType attributes = (IcebergMapType) v2.fields().get(3).type();
        assertThat(attributes.type()).isEqualTo("map");
        assertThat(attributes.keyId()).isEqualTo(41);
        assertThat(attributes.key()).isEqualTo("string");
        assertThat(attributes.valueId()).isEqualTo(42);
        assertThat(attributes.value()).isEqualTo("string");
        assertThat(attributes.valueRequired()).isFalse();

        IcebergMapType attributesCopy = attributes.toBuilder().valueRequired(true).build();
        assertThat(attributesCopy.keyId()).isEqualTo(41);
        assertThat(attributesCopy.valueRequired()).isTrue();
        IcebergSchemaV2 schemaCopy = v2.toBuilder().schemaId(1).build();
        assertThat(schemaCopy.type()).isEqualTo("struct");
        assertThat(schemaCopy.schemaId()).isEqualTo(1);
        assertThat(schemaCopy.identifierFieldIds()).containsExactly(10);
        assertThat(schemaCopy.fields()).hasSize(4);
        assertThat(icebergMetadata.toBuilder().build().schemaV2()).isSameAs(schemaV2);
        SchemaV2Field fieldCopy = orderId.toBuilder().build();
        assertThat(fieldCopy.id()).isEqualTo(orderId.id());
        assertThat(fieldCopy.name()).isEqualTo(orderId.name());
        assertThat(fieldCopy.type()).isEqualTo(orderId.type());
        assertThat(fieldCopy.required()).isEqualTo(orderId.required());
        assertThat(fieldCopy.doc()).isEqualTo(orderId.doc());
        IcebergStructType structCopy = customer.toBuilder().build();
        assertThat(structCopy.type()).isEqualTo("struct");
        assertThat(structCopy.fields()).isSameAs(customer.fields());
        IcebergListType listCopy = tags.toBuilder().build();
        assertThat(listCopy.type()).isEqualTo("list");
        assertThat(listCopy.elementId()).isEqualTo(31);
        assertThat(listCopy.element()).isEqualTo("string");
        assertThat(listCopy.elementRequired()).isFalse();
        assertThat(attributesCopy.type()).isEqualTo("map");
        assertThat(attributesCopy.key()).isEqualTo("string");
        assertThat(attributesCopy.valueId()).isEqualTo(42);
        assertThat(attributesCopy.value()).isEqualTo("string");
    }

    @Test
    public void jsonBuilderSchemaV2() throws JsonProcessingException {
        String expectedJson = "{\"name\":\"orders_nested\",\"format\":\"ICEBERG\",\"metadata\":{\"iceberg\":{" +
            "\"schemaV2\":{\"type\":\"struct\",\"schema-id\":0,\"identifier-field-ids\":[10],\"fields\":[" +
            "{\"id\":10,\"name\":\"order_id\",\"type\":\"long\",\"required\":true}," +
            "{\"id\":20,\"name\":\"customer\",\"required\":false,\"type\":{\"type\":\"struct\",\"fields\":[" +
            "{\"id\":21,\"name\":\"name\",\"type\":\"string\",\"required\":false}," +
            "{\"id\":22,\"name\":\"level\",\"type\":\"int\",\"required\":false}]}}," +
            "{\"id\":30,\"name\":\"tags\",\"required\":false,\"type\":{\"type\":\"list\",\"element-id\":31,\"element\":\"string\",\"element-required\":false}}," +
            "{\"id\":40,\"name\":\"attributes\",\"required\":false,\"type\":{\"type\":\"map\",\"key-id\":41,\"key\":\"string\",\"value-id\":42,\"value\":\"string\",\"value-required\":false}}," +
            "{\"id\":50,\"name\":\"items\",\"doc\":\"order items\",\"type\":{\"type\":\"list\",\"element-id\":51,\"element-required\":true,\"element\":{\"type\":\"struct\",\"fields\":[" +
            "{\"id\":52,\"name\":\"sku\",\"type\":\"string\",\"required\":true}," +
            "{\"id\":53,\"name\":\"props\",\"type\":{\"type\":\"map\",\"key-id\":54,\"key\":\"string\",\"value-id\":55,\"value\":{\"type\":\"list\",\"element-id\":56,\"element\":\"int\"}}}]}}}]}," +
            "\"properties\":{\"format-version\":\"2\"}}}}";
        ObjectMapper jsonMapper = new JsonMapper();

        List<SchemaV2Field> fields = new ArrayList<>();
        fields.add(SchemaV2Field.newBuilder().id(10).name("order_id").type("long").required(true).build());
        fields.add(SchemaV2Field.newBuilder().id(20).name("customer").required(false)
            .type(IcebergStructType.newBuilder()
                .fields(Arrays.asList(
                    SchemaV2Field.newBuilder().id(21).name("name").type("string").required(false).build(),
                    SchemaV2Field.newBuilder().id(22).name("level").type("int").required(false).build()))
                .build())
            .build());
        fields.add(SchemaV2Field.newBuilder().id(30).name("tags").required(false)
            .type(IcebergListType.newBuilder().elementId(31).element("string").elementRequired(false).build())
            .build());
        fields.add(SchemaV2Field.newBuilder().id(40).name("attributes").required(false)
            .type(IcebergMapType.newBuilder().keyId(41).key("string").valueId(42).value("string").valueRequired(false).build())
            .build());
        // Verify a list of structs whose fields contain nested maps and lists.
        fields.add(SchemaV2Field.newBuilder().id(50).name("items").doc("order items")
            .type(IcebergListType.newBuilder()
                .elementId(51)
                .elementRequired(true)
                .element(IcebergStructType.newBuilder()
                    .fields(Arrays.asList(
                        SchemaV2Field.newBuilder().id(52).name("sku").type("string").required(true).build(),
                        SchemaV2Field.newBuilder().id(53).name("props")
                            .type(IcebergMapType.newBuilder()
                                .keyId(54)
                                .key("string")
                                .valueId(55)
                                .value(IcebergListType.newBuilder().elementId(56).element("int").build())
                                .build())
                            .build()))
                    .build())
                .build())
            .build());

        IcebergSchemaV2 schemaV2 = IcebergSchemaV2.newBuilder()
            .schemaId(0)
            .identifierFieldIds(Arrays.asList(10))
            .fields(fields)
            .build();

        Map<String, String> properties = new java.util.HashMap<>();
        properties.put("format-version", "2");

        IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder()
            .schemaV2(schemaV2)
            .properties(properties)
            .build();

        CreateTableRequest request = CreateTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket")
                .namespace("test_namespace")
                .name("orders_nested")
                .format("ICEBERG")
                .metadata(TableMetadata.newBuilder().iceberg(icebergMetadata).build())
                .build();

        OperationInput input = SerdeTableBasic.fromCreateTable(request);
        assertThat(input.opName()).isEqualTo("CreateTable");

        String jsonContent = new String(input.body().get().toBytes(), StandardCharsets.UTF_8);
        assertThat(jsonContent).doesNotContain("\"schema\"");

        JsonNode expectedNode = jsonMapper.readTree(expectedJson);
        JsonNode actualNode = jsonMapper.readTree(jsonContent);
        assertThat(actualNode).isEqualTo(expectedNode);
    }

    @Test
    public void testSchemaV2EmptyModels() {
        IcebergSchemaV2 schema = IcebergSchemaV2.newBuilder().build();
        assertThat(schema.type()).isEqualTo("struct");
        assertThat(new IcebergSchemaV2().type()).isEqualTo("struct");
        assertThat(schema.schemaId()).isNull();
        assertThat(schema.identifierFieldIds()).isNull();
        assertThat(schema.fields()).isNull();
        assertThat(IcebergMetadata.newBuilder().build().schemaV2()).isNull();

        SchemaV2Field field = SchemaV2Field.newBuilder().build();
        assertThat(field.id()).isNull();
        assertThat(field.name()).isNull();
        assertThat(field.type()).isNull();
        assertThat(field.required()).isNull();
        assertThat(field.doc()).isNull();
        assertThat(IcebergStructType.newBuilder().build().type()).isEqualTo("struct");
        assertThat(IcebergStructType.newBuilder().build().fields()).isNull();
        IcebergListType list = IcebergListType.newBuilder().build();
        assertThat(list.type()).isEqualTo("list");
        assertThat(list.elementId()).isNull();
        assertThat(list.element()).isNull();
        assertThat(list.elementRequired()).isNull();
        IcebergMapType map = IcebergMapType.newBuilder().build();
        assertThat(map.type()).isEqualTo("map");
        assertThat(map.keyId()).isNull();
        assertThat(map.key()).isNull();
        assertThat(map.valueId()).isNull();
        assertThat(map.value()).isNull();
        assertThat(map.valueRequired()).isNull();
    }

    @Test
    public void testSchemaAndSchemaV2Serialization() {
        IcebergMetadata iceberg = IcebergMetadata.newBuilder()
                .schema(IcebergSchema.newBuilder().build())
                .schemaV2(IcebergSchemaV2.newBuilder().build())
                .build();
        CreateTableRequest request = CreateTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket")
                .namespace("test_namespace")
                .name("test_table")
                .format("ICEBERG")
                .metadata(TableMetadata.newBuilder().iceberg(iceberg).build())
                .build();
        // The server validates mutual exclusivity; the SDK preserves both fields without blocking the request.
        assertThat(SerdeTableBasic.fromCreateTable(request).body().get().toString())
                .contains("\"schema\"").contains("\"schemaV2\"");

        CreateTableRequest schemaOnly = request.toBuilder()
                .metadata(TableMetadata.newBuilder().iceberg(iceberg.toBuilder().schemaV2(null).build()).build())
                .build();
        assertThat(SerdeTableBasic.fromCreateTable(schemaOnly).body().get().toString())
                .contains("\"schema\"").doesNotContain("\"schemaV2\"");
        CreateTableRequest v2Only = request.toBuilder()
                .metadata(TableMetadata.newBuilder().iceberg(iceberg.toBuilder().schema(null).build()).build())
                .build();
        assertThat(SerdeTableBasic.fromCreateTable(v2Only).body().get().toString())
                .contains("\"schemaV2\"").doesNotContain("\"schema\"");
        CreateTableRequest noMetadata = request.toBuilder().metadata(null).build();
        assertThat(SerdeTableBasic.fromCreateTable(noMetadata)).isNotNull();
        CreateTableRequest noIceberg = request.toBuilder().metadata(TableMetadata.newBuilder().build()).build();
        assertThat(SerdeTableBasic.fromCreateTable(noIceberg)).isNotNull();
    }

    @Test
    public void jsonBuilderSchemaV2NestedMapKey() throws JsonProcessingException {
        IcebergStructType keyType = IcebergStructType.newBuilder().fields(Arrays.asList(
                SchemaV2Field.newBuilder().id(3).name("code").type("string").required(true).build())).build();
        IcebergMapType nestedMap = IcebergMapType.newBuilder()
                .keyId(6).key("string").valueId(7).value("long").valueRequired(true).build();
        IcebergMapType map = IcebergMapType.newBuilder()
                .keyId(2).key(keyType).valueId(4)
                .value(IcebergListType.newBuilder().elementId(5).element(nestedMap).elementRequired(false).build())
                .valueRequired(false).build();
        IcebergSchemaV2 schema = IcebergSchemaV2.newBuilder().fields(Arrays.asList(
                SchemaV2Field.newBuilder().id(1).name("nested").type(map).build())).build();
        CreateTableRequest request = CreateTableRequest.newBuilder()
                .tableBucketARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-table-bucket")
                .namespace("test_namespace").name("test_table").format("ICEBERG")
                .metadata(TableMetadata.newBuilder().iceberg(IcebergMetadata.newBuilder().schemaV2(schema).build()).build())
                .build();
        ObjectMapper mapper = new JsonMapper();
        JsonNode actual = mapper.readTree(SerdeTableBasic.fromCreateTable(request).body().get().toString());
        String expected = "{\"type\":\"struct\",\"fields\":[{\"id\":1,\"name\":\"nested\",\"type\":{" +
                "\"type\":\"map\",\"key-id\":2,\"key\":{\"type\":\"struct\",\"fields\":[" +
                "{\"id\":3,\"name\":\"code\",\"type\":\"string\",\"required\":true}]},\"value-id\":4," +
                "\"value\":{\"type\":\"list\",\"element-id\":5,\"element\":{\"type\":\"map\",\"key-id\":6," +
                "\"key\":\"string\",\"value-id\":7,\"value\":\"long\",\"value-required\":true}," +
                "\"element-required\":false},\"value-required\":false}}]}";
        assertThat(actual.path("metadata").path("iceberg").path("schemaV2")).isEqualTo(mapper.readTree(expected));
        assertThat(map.toBuilder().build().key()).isSameAs(keyType);
    }

    private SchemaField createSchemaField(String name, String type, boolean required) {
        return SchemaField.newBuilder()
            .name(name)
            .type(type)
            .required(required)
            .build();
    }
}