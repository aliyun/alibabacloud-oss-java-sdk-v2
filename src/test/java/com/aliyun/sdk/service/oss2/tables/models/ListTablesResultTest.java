package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.ListTablesResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListTablesResultTest {

    @Test
    public void testEmptyBuilder() {
        ListTablesResult result = ListTablesResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.continuationToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );


        List<TableSummary> tables = new ArrayList<>();
        
        TableSummary table1 = TableSummary.newBuilder()
                .name("test-table-1")
                .namespace(java.util.Arrays.asList("test-namespace-1"))
                .tableARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-bucket/table/test-table-1")
                .type("customer")
                .createdAt("2023-12-17T00:20:57.000Z")
                .modifiedAt("2023-12-17T00:21:57.000Z")
                .build();
                
        TableSummary table2 = TableSummary.newBuilder()
                .name("test-table-2")
                .namespace(java.util.Arrays.asList("test-namespace-2"))
                .tableARN("acs:osstable:cn-hangzhou:1234567890:bucket/test-bucket/table/test-table-2")
                .type("customer")
                .createdAt("2023-12-17T00:22:57.000Z")
                .modifiedAt("2023-12-17T00:23:57.000Z")
                .build();
                
        tables.add(table1);
        tables.add(table2);

        ListTablesResultJson bodyJson = new ListTablesResultJson();
        bodyJson.continuationToken = "next-continuation-token";
        bodyJson.tables = tables;

        ListTablesResult result = ListTablesResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.continuationToken()).isEqualTo("next-continuation-token");
        assertThat(result.tables()).hasSize(2);
        assertThat(result.tables().get(0).name()).isEqualTo("test-table-1");
        assertThat(result.tables().get(0).tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/test-bucket/table/test-table-1");
        assertThat(result.tables().get(1).name()).isEqualTo("test-table-2");
        assertThat(result.tables().get(1).tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/test-bucket/table/test-table-2");
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

        List<TableSummary> tables = new ArrayList<>();
        
        TableSummary table = TableSummary.newBuilder()
                .name("original-table")
                .namespace(java.util.Arrays.asList("original-namespace"))
                .tableARN("acs:osstable:cn-hangzhou:1234567890:bucket/original-bucket/table/original-table")
                .type("customer")
                .createdAt("2023-12-17T00:20:57.000Z")
                .modifiedAt("2023-12-17T00:21:57.000Z")
                .build();
                
        tables.add(table);

        ListTablesResultJson bodyJson = new ListTablesResultJson();
        bodyJson.continuationToken = "original-continuation-token";
        bodyJson.tables = tables;

        ListTablesResult original = ListTablesResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        ListTablesResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.continuationToken()).isEqualTo("original-continuation-token");
        assertThat(copy.tables()).hasSize(1);
        assertThat(copy.tables().get(0).name()).isEqualTo("original-table");
        assertThat(copy.tables().get(0).tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/original-bucket/table/original-table");
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {

        String jsonData = "{\n" +
                "  \"continuationToken\": \"test-continuation-token\",\n" +
                "  \"tables\": [\n" +
                "    {\n" +
                "      \"name\": \"test-table-1\",\n" +
                "      \"namespace\": [\"test-namespace-1\"],\n" +
                "      \"tableARN\": \"acs:osstable:cn-hangzhou:1234567890:bucket/test-bucket/table/test-table-1\",\n" +
                "      \"type\": \"customer\",\n" +
                "      \"createdAt\": \"2023-12-17T00:20:57.000Z\",\n" +
                "      \"modifiedAt\": \"2023-12-17T00:21:57.000Z\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"test-table-2\",\n" +
                "      \"namespace\": [\"test-namespace-2\"],\n" +
                "      \"tableARN\": \"acs:osstable:cn-hangzhou:1234567890:bucket/test-bucket/table/test-table-2\",\n" +
                "      \"type\": \"customer\",\n" +
                "      \"createdAt\": \"2023-12-17T00:22:57.000Z\",\n" +
                "      \"modifiedAt\": \"2023-12-17T00:23:57.000Z\"\n" +
                "    }\n" +
                "  ]\n" +
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

        ListTablesResult result = SerdeTableBasic.toListTables(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.continuationToken()).isEqualTo("test-continuation-token");
        assertThat(result.tables()).hasSize(2);
        
        // First table assertions
        assertThat(result.tables().get(0).name()).isEqualTo("test-table-1");
        assertThat(result.tables().get(0).namespace()).containsExactly("test-namespace-1");
        assertThat(result.tables().get(0).tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/test-bucket/table/test-table-1");
        assertThat(result.tables().get(0).type()).isEqualTo("customer");
        assertThat(result.tables().get(0).createdAt()).isEqualTo("2023-12-17T00:20:57.000Z");
        assertThat(result.tables().get(0).modifiedAt()).isEqualTo("2023-12-17T00:21:57.000Z");
        
        // Second table assertions
        assertThat(result.tables().get(1).name()).isEqualTo("test-table-2");
        assertThat(result.tables().get(1).namespace()).containsExactly("test-namespace-2");
        assertThat(result.tables().get(1).tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/test-bucket/table/test-table-2");
        assertThat(result.tables().get(1).type()).isEqualTo("customer");
        assertThat(result.tables().get(1).createdAt()).isEqualTo("2023-12-17T00:22:57.000Z");
        assertThat(result.tables().get(1).modifiedAt()).isEqualTo("2023-12-17T00:23:57.000Z");
        
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}