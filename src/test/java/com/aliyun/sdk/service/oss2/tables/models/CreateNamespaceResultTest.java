package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.CreateNamespaceResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeNamespaceBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateNamespaceResultTest {

    @Test
    public void testEmptyBuilder() {
        CreateNamespaceResult result = CreateNamespaceResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.tableBucketARN()).isNull();
        assertThat(result.namespace()).isNull();
    }

    @Test
    public void testFullBuilder() {
        CreateNamespaceResultJson jsonResult = new CreateNamespaceResultJson();
        jsonResult.tableBucketARN = "acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket";
        jsonResult.namespace = new ArrayList<>();
        jsonResult.namespace.add("my_namespace");

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        CreateNamespaceResult result = CreateNamespaceResult.newBuilder()
                .innerBody(jsonResult)
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");
        assertThat(result.namespace()).containsExactly("my_namespace");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        CreateNamespaceResultJson jsonResult = new CreateNamespaceResultJson();
        jsonResult.tableBucketARN = "acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket";
        jsonResult.namespace = new ArrayList<>();
        jsonResult.namespace.add("original_namespace");

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        CreateNamespaceResult original = CreateNamespaceResult.newBuilder()
                .innerBody(jsonResult)
                .headers(headers)
                .status("Created")
                .statusCode(201)
                .build();

        CreateNamespaceResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/original-table-bucket");
        assertThat(copy.namespace()).containsExactly("original_namespace");
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {
        String jsonData = "{\n" +
                "  \"tableBucketARN\": \"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket\",\n" +
                "  \"namespace\": [\"my_namespace\"]\n" +
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

        CreateNamespaceResult result = SerdeNamespaceBasic.toCreateNamespace(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(result.tableBucketARN()).isEqualTo("acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket");
        assertThat(result.namespace()).containsExactly("my_namespace");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}