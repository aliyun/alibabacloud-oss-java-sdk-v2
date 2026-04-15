package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetNamespaceResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeNamespaceBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetNamespaceResultTest {

    @Test
    public void testEmptyBuilder() {
        GetNamespaceResult result = GetNamespaceResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.namespace()).isNull();
        assertThat(result.namespaceId()).isNull();
        assertThat(result.tableBucketId()).isNull();
        assertThat(result.ownerAccountId()).isNull();
        assertThat(result.createdAt()).isNull();
        assertThat(result.createdBy()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetNamespaceResultJson bodyJson = new GetNamespaceResultJson();
        List<String> namespace = new ArrayList<>();
        namespace.add("my_namespace");
        bodyJson.namespace = namespace;
        bodyJson.namespaceId = "ns-xxxxxxxx";
        bodyJson.tableBucketId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
        bodyJson.ownerAccountId = "1234567890";
        bodyJson.createdAt = "2024-01-01T00:00:00.000000Z";
        bodyJson.createdBy = "1234567890";

        GetNamespaceResult result = GetNamespaceResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.namespace()).containsExactly("my_namespace");
        assertThat(result.namespaceId()).isEqualTo("ns-xxxxxxxx");
        assertThat(result.tableBucketId()).isEqualTo("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx");
        assertThat(result.ownerAccountId()).isEqualTo("1234567890");
        assertThat(result.createdAt()).isEqualTo("2024-01-01T00:00:00.000000Z");
        assertThat(result.createdBy()).isEqualTo("1234567890");
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

        GetNamespaceResultJson bodyJson = new GetNamespaceResultJson();
        List<String> namespace = new ArrayList<>();
        namespace.add("original_namespace");
        bodyJson.namespace = namespace;
        bodyJson.namespaceId = "ns-original-xxxxxxxx";
        bodyJson.tableBucketId = "original-xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
        bodyJson.ownerAccountId = "0987654321";
        bodyJson.createdAt = "2024-01-01T00:00:00.000000Z";
        bodyJson.createdBy = "0987654321";

        GetNamespaceResult original = GetNamespaceResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("Created")
                .statusCode(201)
                .build();

        GetNamespaceResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.namespace()).containsExactly("original_namespace");
        assertThat(copy.namespaceId()).isEqualTo("ns-original-xxxxxxxx");
        assertThat(copy.tableBucketId()).isEqualTo("original-xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx");
        assertThat(copy.ownerAccountId()).isEqualTo("0987654321");
        assertThat(copy.createdAt()).isEqualTo("2024-01-01T00:00:00.000000Z");
        assertThat(copy.createdBy()).isEqualTo("0987654321");
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {

        String jsonData = "{\n" +
                "  \"namespace\": [\"my_namespace\"],\n" +
                "  \"namespaceId\": \"ns-xxxxxxxx\",\n" +
                "  \"tableBucketId\": \"xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx\",\n" +
                "  \"ownerAccountId\": \"1234567890\",\n" +
                "  \"createdAt\": \"2024-01-01T00:00:00.000000Z\",\n" +
                "  \"createdBy\": \"1234567890\"\n" +
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

        GetNamespaceResult result = SerdeNamespaceBasic.toGetNamespace(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(result.namespace()).containsExactly("my_namespace");
        assertThat(result.namespaceId()).isEqualTo("ns-xxxxxxxx");
        assertThat(result.tableBucketId()).isEqualTo("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx");
        assertThat(result.ownerAccountId()).isEqualTo("1234567890");
        assertThat(result.createdAt()).isEqualTo("2024-01-01T00:00:00.000000Z");
        assertThat(result.createdBy()).isEqualTo("1234567890");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}