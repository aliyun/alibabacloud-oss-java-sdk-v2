package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.CreateTableResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateTableResultTest {

    @Test
    public void testEmptyBuilder() {
        CreateTableResult result = CreateTableResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.tableARN()).isNull();
        assertThat(result.versionToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        CreateTableResultJson bodyJson = new CreateTableResultJson();
        bodyJson.tableARN = "acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id";
        bodyJson.versionToken = "aaabbb";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        CreateTableResult result = CreateTableResult.newBuilder()
                .innerBody(bodyJson)
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id");
        assertThat(result.versionToken()).isEqualTo("aaabbb");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        CreateTableResultJson bodyJson = new CreateTableResultJson();
        bodyJson.tableARN = "acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket/table/table_id";
        bodyJson.versionToken = "original-version-token";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        CreateTableResult original = CreateTableResult.newBuilder()
                .innerBody(bodyJson)
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        CreateTableResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket/table/table_id");
        assertThat(copy.versionToken()).isEqualTo("original-version-token");
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {
        String jsonData = "{\n" +
                "  \"tableARN\": \"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id\",\n" +
                "  \"versionToken\": \"aaabbb\"\n" +
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

        CreateTableResult result = SerdeTableBasic.toCreateTable(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(result.tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id");
        assertThat(result.versionToken()).isEqualTo("aaabbb");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}