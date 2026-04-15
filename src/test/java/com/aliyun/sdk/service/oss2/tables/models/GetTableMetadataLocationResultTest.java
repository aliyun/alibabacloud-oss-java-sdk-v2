package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableMetadataLocationResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableConfigBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTableMetadataLocationResultTest {

    @Test
    public void testEmptyBuilder() {
        GetTableMetadataLocationResult result = GetTableMetadataLocationResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.metadataLocation()).isNull();
        assertThat(result.versionToken()).isNull();
        assertThat(result.warehouseLocation()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetTableMetadataLocationResultJson bodyJson = new GetTableMetadataLocationResultJson();
        bodyJson.metadataLocation = "oss://data-bucket/metadata/00000-xxx.metadata.json";
        bodyJson.versionToken = "aaabbb";
        bodyJson.warehouseLocation = "oss://data-bucket/warehouse/";

        GetTableMetadataLocationResult result = GetTableMetadataLocationResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.metadataLocation()).isEqualTo("oss://data-bucket/metadata/00000-xxx.metadata.json");
        assertThat(result.versionToken()).isEqualTo("aaabbb");
        assertThat(result.warehouseLocation()).isEqualTo("oss://data-bucket/warehouse/");
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

        GetTableMetadataLocationResultJson bodyJson = new GetTableMetadataLocationResultJson();
        bodyJson.metadataLocation = "oss://data-bucket/metadata/original.metadata.json";
        bodyJson.versionToken = "originalToken";
        bodyJson.warehouseLocation = "oss://data-bucket/original-warehouse/";

        GetTableMetadataLocationResult original = GetTableMetadataLocationResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("Created")
                .statusCode(201)
                .build();

        GetTableMetadataLocationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.metadataLocation()).isEqualTo("oss://data-bucket/metadata/original.metadata.json");
        assertThat(copy.versionToken()).isEqualTo("originalToken");
        assertThat(copy.warehouseLocation()).isEqualTo("oss://data-bucket/original-warehouse/");
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {

        String jsonData = "{\n" +
                "  \"metadataLocation\": \"oss://data-bucket/metadata/00000-xxx.metadata.json\",\n" +
                "  \"versionToken\": \"aaabbb\",\n" +
                "  \"warehouseLocation\": \"oss://data-bucket/warehouse/\"\n" +
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

        GetTableMetadataLocationResult result = SerdeTableConfigBasic.toGetTableMetadataLocation(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.metadataLocation()).isEqualTo("oss://data-bucket/metadata/00000-xxx.metadata.json");
        assertThat(result.versionToken()).isEqualTo("aaabbb");
        assertThat(result.warehouseLocation()).isEqualTo("oss://data-bucket/warehouse/");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}
