package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableBucketMaintenanceConfigurationResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBucketConfigBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTableBucketMaintenanceConfigurationResultTest {

    @Test
    public void testEmptyBuilder() {
        GetTableBucketMaintenanceConfigurationResult result = GetTableBucketMaintenanceConfigurationResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.tableBucketARN()).isNull();
        assertThat(result.configuration()).isNotNull();
        assertThat(result.configuration().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetTableBucketMaintenanceConfigurationResultJson bodyJson = new GetTableBucketMaintenanceConfigurationResultJson();
        bodyJson.tableBucketARN = "acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket";

        // Create configuration map
        Map<String, TableBucketMaintenanceConfigurationValue> configuration = new java.util.HashMap<>();
        IcebergUnreferencedFileRemovalSettings removalSettings = IcebergUnreferencedFileRemovalSettings.newBuilder()
                .unreferencedDays(3)
                .nonCurrentDays(10)
                .build();
        TableBucketMaintenanceSettings settings = TableBucketMaintenanceSettings.newBuilder()
                .icebergUnreferencedFileRemoval(removalSettings)
                .build();
        TableBucketMaintenanceConfigurationValue configValue = TableBucketMaintenanceConfigurationValue.newBuilder()
                .status("enabled")
                .settings(settings)
                .build();
        configuration.put("icebergUnreferencedFileRemoval", configValue);
        bodyJson.configuration = configuration;

        GetTableBucketMaintenanceConfigurationResult result = GetTableBucketMaintenanceConfigurationResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");
        assertThat(result.configuration()).isNotNull();
        assertThat(result.configuration().size()).isEqualTo(1);
        assertThat(result.configuration().containsKey("icebergUnreferencedFileRemoval")).isTrue();
        assertThat(result.configuration().get("icebergUnreferencedFileRemoval").status()).isEqualTo("enabled");
        assertThat(result.configuration().get("icebergUnreferencedFileRemoval").settings().icebergUnreferencedFileRemoval().unreferencedDays()).isEqualTo(3);
        assertThat(result.configuration().get("icebergUnreferencedFileRemoval").settings().icebergUnreferencedFileRemoval().nonCurrentDays()).isEqualTo(10);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetTableBucketMaintenanceConfigurationResultJson bodyJson = new GetTableBucketMaintenanceConfigurationResultJson();
        bodyJson.tableBucketARN = "acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket";

        // Create configuration map
        Map<String, TableBucketMaintenanceConfigurationValue> configuration = new java.util.HashMap<>();
        IcebergUnreferencedFileRemovalSettings removalSettings = IcebergUnreferencedFileRemovalSettings.newBuilder()
                .unreferencedDays(5)
                .nonCurrentDays(15)
                .build();
        TableBucketMaintenanceSettings settings = TableBucketMaintenanceSettings.newBuilder()
                .icebergUnreferencedFileRemoval(removalSettings)
                .build();
        TableBucketMaintenanceConfigurationValue configValue = TableBucketMaintenanceConfigurationValue.newBuilder()
                .status("disabled")
                .settings(settings)
                .build();
        configuration.put("icebergUnreferencedFileRemoval", configValue);
        bodyJson.configuration = configuration;

        GetTableBucketMaintenanceConfigurationResult original = GetTableBucketMaintenanceConfigurationResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("Created")
                .statusCode(201)
                .build();

        GetTableBucketMaintenanceConfigurationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket");
        assertThat(copy.configuration()).isNotNull();
        assertThat(copy.configuration().size()).isEqualTo(1);
        assertThat(copy.configuration().containsKey("icebergUnreferencedFileRemoval")).isTrue();
        assertThat(copy.configuration().get("icebergUnreferencedFileRemoval").status()).isEqualTo("disabled");
        assertThat(copy.configuration().get("icebergUnreferencedFileRemoval").settings().icebergUnreferencedFileRemoval().unreferencedDays()).isEqualTo(5);
        assertThat(copy.configuration().get("icebergUnreferencedFileRemoval").settings().icebergUnreferencedFileRemoval().nonCurrentDays()).isEqualTo(15);
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {

        String jsonData = "{\n" +
                "  \"tableBucketARN\": \"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket\",\n" +
                "  \"configuration\": {\n" +
                "    \"icebergUnreferencedFileRemoval\": {\n" +
                "      \"status\": \"enabled\",\n" +
                "      \"settings\": {\n" +
                "        \"icebergUnreferencedFileRemoval\": {\n" +
                "          \"unreferencedDays\": 3,\n" +
                "          \"nonCurrentDays\": 10\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-xml-builder-test",
                        "Content-Type", "application/json"
                ))
                .status("OK")
                .statusCode(200)
                .build();

        GetTableBucketMaintenanceConfigurationResult result = SerdeTableBucketConfigBasic.toGetTableBucketMaintenanceConfiguration(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(result.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");
        assertThat(result.configuration()).isNotNull();
        assertThat(result.configuration().size()).isEqualTo(1);
        assertThat(result.configuration().containsKey("icebergUnreferencedFileRemoval")).isTrue();
        assertThat(result.configuration().get("icebergUnreferencedFileRemoval").status()).isEqualTo("enabled");
        // Verify the detailed settings
        assertThat(result.configuration().get("icebergUnreferencedFileRemoval").settings()).isNotNull();
        assertThat(result.configuration().get("icebergUnreferencedFileRemoval").settings().icebergUnreferencedFileRemoval()).isNotNull();
        assertThat(result.configuration().get("icebergUnreferencedFileRemoval").settings().icebergUnreferencedFileRemoval().unreferencedDays()).isEqualTo(3);
        assertThat(result.configuration().get("icebergUnreferencedFileRemoval").settings().icebergUnreferencedFileRemoval().nonCurrentDays()).isEqualTo(10);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}