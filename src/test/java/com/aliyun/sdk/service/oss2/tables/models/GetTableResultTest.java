package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTableResultTest {

    @Test
    public void testEmptyBuilder() {
        GetTableResult result = GetTableResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.name()).isNull();
        assertThat(result.type()).isNull();
        assertThat(result.tableARN()).isNull();
        assertThat(result.namespace()).isNull();
        assertThat(result.namespaceId()).isNull();
        assertThat(result.versionToken()).isNull();
        assertThat(result.metadataLocation()).isNull();
        assertThat(result.warehouseLocation()).isNull();
        assertThat(result.createdAt()).isNull();
        assertThat(result.createdBy()).isNull();
        assertThat(result.modifiedAt()).isNull();
        assertThat(result.modifiedBy()).isNull();
        assertThat(result.ownerAccountId()).isNull();
        assertThat(result.format()).isNull();
        assertThat(result.tableBucketId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetTableResultJson bodyJson = new GetTableResultJson();
        bodyJson.name = "my_table";
        bodyJson.type = "customer";
        bodyJson.tableARN = "acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id";
        bodyJson.namespace = java.util.Arrays.asList("my_namespace");
        bodyJson.namespaceId = "ns-xxxxxxxx";
        bodyJson.versionToken = "aaabbb";
        bodyJson.metadataLocation = "oss://data-bucket/metadata/00000-xxx.metadata.json";
        bodyJson.warehouseLocation = "oss://data-bucket/warehouse/";
        bodyJson.createdAt = "2024-01-01T00:00:00.000000Z";
        bodyJson.createdBy = "1234567890";
        bodyJson.modifiedAt = "2024-01-01T00:00:00.000000Z";
        bodyJson.modifiedBy = "1234567890";
        bodyJson.ownerAccountId = "1234567890";
        bodyJson.format = "iceberg";
        bodyJson.tableBucketId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";

        GetTableResult result = GetTableResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.name()).isEqualTo("my_table");
        assertThat(result.type()).isEqualTo("customer");
        assertThat(result.tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id");
        assertThat(result.namespace()).containsExactly("my_namespace");
        assertThat(result.namespaceId()).isEqualTo("ns-xxxxxxxx");
        assertThat(result.versionToken()).isEqualTo("aaabbb");
        assertThat(result.metadataLocation()).isEqualTo("oss://data-bucket/metadata/00000-xxx.metadata.json");
        assertThat(result.warehouseLocation()).isEqualTo("oss://data-bucket/warehouse/");
        assertThat(result.createdAt()).isEqualTo("2024-01-01T00:00:00.000000Z");
        assertThat(result.createdBy()).isEqualTo("1234567890");
        assertThat(result.modifiedAt()).isEqualTo("2024-01-01T00:00:00.000000Z");
        assertThat(result.modifiedBy()).isEqualTo("1234567890");
        assertThat(result.ownerAccountId()).isEqualTo("1234567890");
        assertThat(result.format()).isEqualTo("iceberg");
        assertThat(result.tableBucketId()).isEqualTo("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx");
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void xmlBuilder() {

        String jsonData = "{\n" +
                "   \"name\": \"my_table\",\n" +
                "   \"type\": \"customer\",\n" +
                "   \"tableARN\": \"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id\",\n" +
                "   \"namespace\": [\"my_namespace\"],\n" +
                "   \"namespaceId\": \"ns-xxxxxxxx\",\n" +
                "   \"versionToken\": \"aaabbb\",\n" +
                "   \"metadataLocation\": \"oss://data-bucket/metadata/00000-xxx.metadata.json\",\n" +
                "   \"warehouseLocation\": \"oss://data-bucket/warehouse/\",\n" +
                "   \"createdAt\": \"2024-01-01T00:00:00.000000Z\",\n" +
                "   \"createdBy\": \"1234567890\",\n" +
                "   \"modifiedAt\": \"2024-01-01T00:00:00.000000Z\",\n" +
                "   \"modifiedBy\": \"1234567890\",\n" +
                "   \"ownerAccountId\": \"1234567890\",\n" +
                "   \"format\": \"iceberg\",\n" +
                "   \"tableBucketId\": \"xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx\"\n" +
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

        GetTableResult result = SerdeTableBasic.toGetTable(output);

        assertThat(result.name()).isEqualTo("my_table");
        assertThat(result.type()).isEqualTo("customer");
        assertThat(result.tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id");
        assertThat(result.namespace()).containsExactly("my_namespace");
        assertThat(result.namespaceId()).isEqualTo("ns-xxxxxxxx");
        assertThat(result.versionToken()).isEqualTo("aaabbb");
        assertThat(result.metadataLocation()).isEqualTo("oss://data-bucket/metadata/00000-xxx.metadata.json");
        assertThat(result.warehouseLocation()).isEqualTo("oss://data-bucket/warehouse/");
        assertThat(result.createdAt()).isEqualTo("2024-01-01T00:00:00.000000Z");
        assertThat(result.createdBy()).isEqualTo("1234567890");
        assertThat(result.modifiedAt()).isEqualTo("2024-01-01T00:00:00.000000Z");
        assertThat(result.modifiedBy()).isEqualTo("1234567890");
        assertThat(result.ownerAccountId()).isEqualTo("1234567890");
        assertThat(result.format()).isEqualTo("iceberg");
        assertThat(result.tableBucketId()).isEqualTo("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx");
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}