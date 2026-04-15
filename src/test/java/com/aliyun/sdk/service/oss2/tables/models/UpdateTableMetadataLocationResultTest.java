package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.UpdateTableMetadataLocationResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableConfigBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateTableMetadataLocationResultTest {

    @Test
    public void testEmptyBuilder() {
        UpdateTableMetadataLocationResult result = UpdateTableMetadataLocationResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.metadataLocation()).isNull();
        assertThat(result.versionToken()).isNull();
        assertThat(result.name()).isNull();
        assertThat(result.namespace()).isNull();
        assertThat(result.tableARN()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "test-etag-full"
        );

        UpdateTableMetadataLocationResultJson bodyJson = new UpdateTableMetadataLocationResultJson();
        bodyJson.metadataLocation = "oss://data-bucket/metadata/00001-xxx.metadata.json";
        bodyJson.name = "my_table";
        bodyJson.namespace = Arrays.asList("my_namespace");
        bodyJson.versionToken = "cccbbb";
        bodyJson.tableARN = "acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id";

        UpdateTableMetadataLocationResult result = UpdateTableMetadataLocationResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("test-etag-full");
        assertThat(result.metadataLocation()).isEqualTo("oss://data-bucket/metadata/00001-xxx.metadata.json");
        assertThat(result.name()).isEqualTo("my_table");
        assertThat(result.namespace()).containsExactly("my_namespace");
        assertThat(result.versionToken()).isEqualTo("cccbbb");
        assertThat(result.tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "original-etag"
        );

        UpdateTableMetadataLocationResultJson bodyJson = new UpdateTableMetadataLocationResultJson();
        bodyJson.metadataLocation = "oss://data-bucket/metadata/original.metadata.json";
        bodyJson.name = "original_table";
        bodyJson.namespace = Arrays.asList("original_namespace");
        bodyJson.versionToken = "originalToken";
        bodyJson.tableARN = "acs:osstable:cn-hangzhou:1234567890:bucket/original-bucket/table/original_id";

        UpdateTableMetadataLocationResult original = UpdateTableMetadataLocationResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("Created")
                .statusCode(201)
                .build();

        UpdateTableMetadataLocationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("original-etag");
        assertThat(copy.metadataLocation()).isEqualTo("oss://data-bucket/metadata/original.metadata.json");
        assertThat(copy.name()).isEqualTo("original_table");
        assertThat(copy.namespace()).containsExactly("original_namespace");
        assertThat(copy.versionToken()).isEqualTo("originalToken");
        assertThat(copy.tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/original-bucket/table/original_id");
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {

        String jsonData = "{\n" +
                "  \"metadataLocation\": \"oss://data-bucket/metadata/00001-xxx.metadata.json\",\n" +
                "  \"name\": \"my_table\",\n" +
                "  \"namespace\": [\"my_namespace\"],\n" +
                "  \"tableARN\": \"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id\",\n" +
                "  \"versionToken\": \"cccbbb\"\n" +
                "}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-xml-builder-test",
                        "ETag", "xml-builder-etag"
                ))
                .status("OK")
                .statusCode(200)
                .build();

        UpdateTableMetadataLocationResult result = SerdeTableConfigBasic.toUpdateTableMetadataLocation(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("xml-builder-etag");
        assertThat(result.metadataLocation()).isEqualTo("oss://data-bucket/metadata/00001-xxx.metadata.json");
        assertThat(result.name()).isEqualTo("my_table");
        assertThat(result.namespace()).containsExactly("my_namespace");
        assertThat(result.versionToken()).isEqualTo("cccbbb");
        assertThat(result.tableARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/table_id");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}
