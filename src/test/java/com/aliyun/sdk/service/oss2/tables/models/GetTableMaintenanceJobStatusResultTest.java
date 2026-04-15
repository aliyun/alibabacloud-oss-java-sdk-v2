package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableConfigBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTableMaintenanceJobStatusResultTest {

    @Test
    public void testEmptyBuilder() {
        GetTableMaintenanceJobStatusResult result = GetTableMaintenanceJobStatusResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.tableARN()).isNull();
        assertThat(result.jobStatus()).isNotNull();
        assertThat(result.jobStatus().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetTableMaintenanceJobStatusResult result = GetTableMaintenanceJobStatusResult.newBuilder()
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

        GetTableMaintenanceJobStatusResult original = GetTableMaintenanceJobStatusResult.newBuilder()
                .headers(headers)
                .status("Created")
                .statusCode(201)
                .build();

        GetTableMaintenanceJobStatusResult copy = original.toBuilder().build();

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
                "\"status\":{" +
                    "\"icebergCompaction\":{" +
                        "\"status\":\"Successful\"," +
                        "\"failureMessage\":\"\"," +
                        "\"lastRunTimestamp\":\"2024-01-15T10:30:00.000000Z\"" +
                    "}," +
                    "\"icebergSnapshotManagement\":{" +
                        "\"status\":\"Successful\"," +
                        "\"failureMessage\":\"\"," +
                        "\"lastRunTimestamp\":\"2024-01-15T08:00:00.000000Z\"" +
                    "}," +
                    "\"icebergUnreferencedFileRemoval\":{" +
                        "\"status\":\"Successful\"," +
                        "\"failureMessage\":\"\"," +
                        "\"lastRunTimestamp\":\"2024-01-15T06:00:00.000000Z\"" +
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

        GetTableMaintenanceJobStatusResult result = SerdeTableConfigBasic.toGetTableMaintenanceJobStatus(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
        assertThat(result.tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id");
        assertThat(result.jobStatus()).isNotNull();
        assertThat(result.jobStatus()).containsKey("icebergCompaction");
        assertThat(result.jobStatus()).containsKey("icebergSnapshotManagement");
        assertThat(result.jobStatus()).containsKey("icebergUnreferencedFileRemoval");

        TableMaintenanceJobStatusValue compactionStatus = result.jobStatus().get("icebergCompaction");
        assertThat(compactionStatus).isNotNull();
        assertThat(compactionStatus.status()).isEqualTo("Successful");
        assertThat(compactionStatus.failureMessage()).isEqualTo("");
        assertThat(compactionStatus.lastRunTimestamp()).isNotNull();
        assertThat(compactionStatus.lastRunTimestamp().toString()).contains("2024-01-15");

        TableMaintenanceJobStatusValue snapshotStatus = result.jobStatus().get("icebergSnapshotManagement");
        assertThat(snapshotStatus).isNotNull();
        assertThat(snapshotStatus.status()).isEqualTo("Successful");
        assertThat(snapshotStatus.failureMessage()).isEqualTo("");
        assertThat(snapshotStatus.lastRunTimestamp()).isNotNull();
        assertThat(snapshotStatus.lastRunTimestamp().toString()).contains("2024-01-15");

        TableMaintenanceJobStatusValue removalStatus = result.jobStatus().get("icebergUnreferencedFileRemoval");
        assertThat(removalStatus).isNotNull();
        assertThat(removalStatus.status()).isEqualTo("Successful");
        assertThat(removalStatus.failureMessage()).isEqualTo("");
        assertThat(removalStatus.lastRunTimestamp()).isNotNull();
        assertThat(removalStatus.lastRunTimestamp().toString()).contains("2024-01-15");
    }
}
