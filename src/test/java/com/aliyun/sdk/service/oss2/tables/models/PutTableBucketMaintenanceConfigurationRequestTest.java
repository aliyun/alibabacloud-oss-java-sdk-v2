package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBucketConfigBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class PutTableBucketMaintenanceConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutTableBucketMaintenanceConfigurationRequest request = PutTableBucketMaintenanceConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.tableBucketARN()).isNull();
        assertThat(request.type()).isNull();
        assertThat(request.value()).isNull();
    }

    @Test
    public void testFullBuilder() {
        IcebergUnreferencedFileRemovalSettings removalSettings = IcebergUnreferencedFileRemovalSettings.newBuilder()
                .unreferencedDays(5)
                .nonCurrentDays(15)
                .build();
        
        TableBucketMaintenanceSettings settings = TableBucketMaintenanceSettings.newBuilder()
                .icebergUnreferencedFileRemoval(removalSettings)
                .build();
        
        TableBucketMaintenanceConfigurationValue value = TableBucketMaintenanceConfigurationValue.newBuilder()
                .status("enabled")
                .settings(settings)
                .build();

        PutTableBucketMaintenanceConfigurationRequest request = PutTableBucketMaintenanceConfigurationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket")
                .type("iceberg-unreferenced-file-removal")
                .value(value)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .header("x-oss-header1", "header-value1")
                .header("x-oss-header2", "header-value2")
                .build();

        assertThat(request.tableBucketARN()).isEqualTo("arn:acs:oss-tables:cn-hangzhou:123456789012:bucket/test-bucket");
        assertThat(request.type()).isEqualTo("iceberg-unreferenced-file-removal");
        assertThat(request.value().status()).isEqualTo("enabled");
        assertThat(request.value().settings().icebergUnreferencedFileRemoval().unreferencedDays()).isEqualTo(5);
        assertThat(request.value().settings().icebergUnreferencedFileRemoval().nonCurrentDays()).isEqualTo(15);

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
        IcebergUnreferencedFileRemovalSettings removalSettings = IcebergUnreferencedFileRemovalSettings.newBuilder()
                .unreferencedDays(5)
                .nonCurrentDays(15)
                .build();
        
        TableBucketMaintenanceSettings settings = TableBucketMaintenanceSettings.newBuilder()
                .icebergUnreferencedFileRemoval(removalSettings)
                .build();
        
        TableBucketMaintenanceConfigurationValue value = TableBucketMaintenanceConfigurationValue.newBuilder()
                .status("enabled")
                .settings(settings)
                .build();

        PutTableBucketMaintenanceConfigurationRequest original = PutTableBucketMaintenanceConfigurationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss:cn-hangzhou:123456789012:bucket/original-bucket")
                .type("iceberg-unreferenced-file-removal")
                .value(value)
                .parameter("original-param", "original-value")
                .header("x-oss-original", "original-header")
                .build();

        PutTableBucketMaintenanceConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.tableBucketARN()).isEqualTo("arn:acs:oss:cn-hangzhou:123456789012:bucket/original-bucket");
        assertThat(copy.type()).isEqualTo("iceberg-unreferenced-file-removal");
        assertThat(copy.value().status()).isEqualTo("enabled");
        assertThat(copy.value().settings().icebergUnreferencedFileRemoval().unreferencedDays()).isEqualTo(5);
        assertThat(copy.value().settings().icebergUnreferencedFileRemoval().nonCurrentDays()).isEqualTo(15);

        assertThat(copy.headers().get("x-oss-original")).isEqualTo("original-header");
        assertThat(copy.parameters().get("original-param")).isEqualTo("original-value");
    }

    @Test
    public void jsonBuilder() throws JsonProcessingException {
        String expectedJson = "{\"value\":{\"status\":\"enabled\",\"settings\":{\"icebergUnreferencedFileRemoval\":{\"unreferencedDays\":5,\"nonCurrentDays\":15}}}}";
        ObjectMapper jsonMapper = new JsonMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        IcebergUnreferencedFileRemovalSettings removalSettings = IcebergUnreferencedFileRemovalSettings.newBuilder()
                .unreferencedDays(5)
                .nonCurrentDays(15)
                .build();
        
        TableBucketMaintenanceSettings tableSettings = TableBucketMaintenanceSettings.newBuilder()
                .icebergUnreferencedFileRemoval(removalSettings)
                .build();
        
        TableBucketMaintenanceConfigurationValue value = TableBucketMaintenanceConfigurationValue.newBuilder()
                .status("enabled")
                .settings(tableSettings)
                .build();

        PutTableBucketMaintenanceConfigurationRequest request = PutTableBucketMaintenanceConfigurationRequest.newBuilder()
                .tableBucketARN("arn:acs:oss:cn-hangzhou:123456789012:bucket/test-bucket")
                .type("iceberg-unreferenced-file-removal")
                .value(value)
                .header("x-oss-request-id", "test-request-id")
                .parameter("test-param", "test-value")
                .build();

        OperationInput input = SerdeTableBucketConfigBasic.fromPutTableBucketMaintenanceConfiguration(request);

        assertThat(input.headers()).containsEntry("x-oss-request-id", "test-request-id");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.parameters()).containsEntry("test-param", "test-value");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.opName()).isEqualTo("PutTableBucketMaintenanceConfiguration");

        // Verify the JSON body content
        BinaryData body = input.body().get();
        String jsonContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(jsonContent).contains("\"status\":\"enabled\"");
        assertThat(jsonContent).contains("\"unreferencedDays\":5");
        assertThat(jsonContent).contains("\"nonCurrentDays\":15");

        // Compare with expected JSON
        assertThat(jsonContent).isEqualTo(expectedJson);
    }
}