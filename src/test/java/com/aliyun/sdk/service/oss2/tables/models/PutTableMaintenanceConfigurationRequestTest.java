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

public class PutTableMaintenanceConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutTableMaintenanceConfigurationRequest request = PutTableMaintenanceConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.namespace()).isNull();
        assertThat(request.name()).isNull();
        assertThat(request.type()).isNull();
        assertThat(request.value()).isNull();
    }

    @Test
    public void testFullBuilder() {
        TableMaintenanceSettings settings = TableMaintenanceSettings.newBuilder()
                .icebergCompaction(IcebergCompactionSettings.newBuilder()
                        .targetFileSizeMB(100)
                        .strategy("auto")
                        .build())
                .build();

        TableMaintenanceConfigurationValue value = TableMaintenanceConfigurationValue.newBuilder()
                .status("enabled")
                .settings(settings)
                .build();

        PutTableMaintenanceConfigurationRequest request = PutTableMaintenanceConfigurationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .namespace("test-namespace")
                .name("test-table")
                .type("icebergCompaction")
                .value(value)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket");
        assertThat(request.namespace()).isEqualTo("test-namespace");
        assertThat(request.name()).isEqualTo("test-table");
        assertThat(request.type()).isEqualTo("icebergCompaction");
        assertThat(request.value()).isNotNull();
        assertThat(request.value().status()).isEqualTo("enabled");

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
        TableMaintenanceSettings settings = TableMaintenanceSettings.newBuilder()
                .icebergCompaction(IcebergCompactionSettings.newBuilder()
                        .targetFileSizeMB(200)
                        .strategy("binpack")
                        .build())
                .build();

        TableMaintenanceConfigurationValue value = TableMaintenanceConfigurationValue.newBuilder()
                .status("disabled")
                .settings(settings)
                .build();

        PutTableMaintenanceConfigurationRequest original = PutTableMaintenanceConfigurationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket")
                .namespace("original-namespace")
                .name("original-table")
                .type("icebergCompaction")
                .value(value)
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        PutTableMaintenanceConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.tableBucketARN()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/original-bucket");
        assertThat(copy.namespace()).isEqualTo("original-namespace");
        assertThat(copy.name()).isEqualTo("original-table");
        assertThat(copy.type()).isEqualTo("icebergCompaction");
        assertThat(copy.value()).isNotNull();
        assertThat(copy.value().status()).isEqualTo("disabled");

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void xmlBuilderIcebergCompaction() throws JsonProcessingException {
        String expectedJson = "{\"value\":{\"status\":\"enabled\",\"settings\":{\"icebergCompaction\":{\"targetFileSizeMB\":100,\"strategy\":\"auto\"}}}}";
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        TableMaintenanceSettings settings = TableMaintenanceSettings.newBuilder()
                .icebergCompaction(IcebergCompactionSettings.newBuilder()
                        .targetFileSizeMB(100)
                        .strategy("auto")
                        .build())
                .build();

        TableMaintenanceConfigurationValue value = TableMaintenanceConfigurationValue.newBuilder()
                .status("enabled")
                .settings(settings)
                .build();

        PutTableMaintenanceConfigurationRequest request = PutTableMaintenanceConfigurationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .namespace("test-namespace")
                .name("test-table")
                .type("icebergCompaction")
                .value(value)
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableConfigBasic.fromPutTableMaintenanceConfiguration(request);

        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.opName()).isEqualTo("PutTableMaintenanceConfiguration");

        BinaryData body = input.body().get();
        String jsonContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(jsonContent).contains("\"value\":");
        assertThat(jsonContent).contains("\"status\":\"enabled\"");
        assertThat(jsonContent).contains("\"icebergCompaction\":");
        assertThat(jsonContent).contains("\"targetFileSizeMB\":100");
        assertThat(jsonContent).contains("\"strategy\":\"auto\"");

        assertThat(jsonContent).isEqualTo(expectedJson);
    }

    @Test
    public void xmlBuilderIcebergSnapshotManagement() throws JsonProcessingException {
        String expectedJson = "{\"value\":{\"status\":\"enabled\",\"settings\":{\"icebergSnapshotManagement\":{\"minSnapshotsToKeep\":1,\"maxSnapshotAgeHours\":1}}}}";
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        TableMaintenanceSettings settings = TableMaintenanceSettings.newBuilder()
                .icebergSnapshotManagement(IcebergSnapshotManagementSettings.newBuilder()
                        .minSnapshotsToKeep(1)
                        .maxSnapshotAgeHours(1)
                        .build())
                .build();

        TableMaintenanceConfigurationValue value = TableMaintenanceConfigurationValue.newBuilder()
                .status("enabled")
                .settings(settings)
                .build();

        PutTableMaintenanceConfigurationRequest request = PutTableMaintenanceConfigurationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .namespace("test-namespace")
                .name("test-table")
                .type("icebergSnapshotManagement")
                .value(value)
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableConfigBasic.fromPutTableMaintenanceConfiguration(request);

        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.opName()).isEqualTo("PutTableMaintenanceConfiguration");

        BinaryData body = input.body().get();
        String jsonContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(jsonContent).contains("\"value\":");
        assertThat(jsonContent).contains("\"status\":\"enabled\"");
        assertThat(jsonContent).contains("\"icebergSnapshotManagement\":");
        assertThat(jsonContent).contains("\"minSnapshotsToKeep\":1");
        assertThat(jsonContent).contains("\"maxSnapshotAgeHours\":1");

        assertThat(jsonContent).isEqualTo(expectedJson);
    }
}
