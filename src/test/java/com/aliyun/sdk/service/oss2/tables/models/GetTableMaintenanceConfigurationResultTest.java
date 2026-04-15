package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableConfigBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTableMaintenanceConfigurationResultTest {

    @Test
    public void testEmptyBuilder() {
        GetTableMaintenanceConfigurationResult result = GetTableMaintenanceConfigurationResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.tableARN()).isNull();
        assertThat(result.configuration()).isNotNull();
        assertThat(result.configuration().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetTableMaintenanceConfigurationResult result = GetTableMaintenanceConfigurationResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
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

        GetTableMaintenanceConfigurationResult original = GetTableMaintenanceConfigurationResult.newBuilder()
                .headers(headers)
                .status("Created")
                .statusCode(201)
                .build();

        GetTableMaintenanceConfigurationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {
        String jsonData = "{" +
                "\"tableARN\":\"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id\"," +
                "\"configuration\":{" +
                    "\"icebergCompaction\":{" +
                        "\"status\":\"enabled\"," +
                        "\"settings\":{" +
                            "\"icebergCompaction\":{" +
                                "\"targetFileSizeMB\":512," +
                                "\"strategy\":\"binpack\"" +
                            "}" +
                        "}" +
                    "}," +
                    "\"icebergSnapshotManagement\":{" +
                        "\"status\":\"enabled\"," +
                        "\"settings\":{" +
                            "\"icebergSnapshotManagement\":{" +
                                "\"minSnapshotsToKeep\":1," +
                                "\"maxSnapshotAgeHours\":720" +
                            "}" +
                        "}" +
                    "}" +
                "}" +
                "}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-xml-builder-test",
                        "ETag", "\"xml-builder-etag\""
                ))
                .status("OK")
                .statusCode(200)
                .build();

        GetTableMaintenanceConfigurationResult result = SerdeTableConfigBasic.toGetTableMaintenanceConfiguration(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
        assertThat(result.tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id");
        assertThat(result.configuration()).isNotNull();
        assertThat(result.configuration()).containsKey("icebergCompaction");
        assertThat(result.configuration()).containsKey("icebergSnapshotManagement");

        TableMaintenanceConfigurationValue compactionConfig = result.configuration().get("icebergCompaction");
        assertThat(compactionConfig).isNotNull();
        assertThat(compactionConfig.status()).isEqualTo("enabled");
        assertThat(compactionConfig.settings()).isNotNull();
        assertThat(compactionConfig.settings().icebergCompaction()).isNotNull();
        assertThat(compactionConfig.settings().icebergCompaction().targetFileSizeMB()).isEqualTo(512);
        assertThat(compactionConfig.settings().icebergCompaction().strategy()).isEqualTo("binpack");

        TableMaintenanceConfigurationValue snapshotConfig = result.configuration().get("icebergSnapshotManagement");
        assertThat(snapshotConfig).isNotNull();
        assertThat(snapshotConfig.status()).isEqualTo("enabled");
        assertThat(snapshotConfig.settings()).isNotNull();
        assertThat(snapshotConfig.settings().icebergSnapshotManagement()).isNotNull();
        assertThat(snapshotConfig.settings().icebergSnapshotManagement().minSnapshotsToKeep()).isEqualTo(1);
        assertThat(snapshotConfig.settings().icebergSnapshotManagement().maxSnapshotAgeHours()).isEqualTo(720);
    }
}
