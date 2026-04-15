package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.ListNamespacesResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeNamespaceBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ListNamespacesResultTest {

    @Test
    public void testEmptyBuilder() {
        ListNamespacesResult result = ListNamespacesResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.continuationToken()).isNull();

    }

    @Test
    public void testFullBuilder() {
        NamespaceSummary namespaceSummary = NamespaceSummary.newBuilder()
                .namespace(Arrays.asList("my_namespace"))
                .namespaceId("ns-xxxxxxxx")
                .ownerAccountId("1234567890")
                .createdBy("1234567890")
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListNamespacesResultJson bodyJson = new ListNamespacesResultJson();
        bodyJson.continuationToken = "xxxxx";
        bodyJson.namespaces = Arrays.asList(namespaceSummary);

        ListNamespacesResult result = ListNamespacesResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.continuationToken()).isEqualTo("xxxxx");
        assertThat(result.namespaces()).isNotNull();
        assertThat(result.namespaces()).hasSize(1);
        assertThat(result.namespaces().get(0).namespace()).containsExactly("my_namespace");
        assertThat(result.namespaces().get(0).namespaceId()).isEqualTo("ns-xxxxxxxx");
        assertThat(result.namespaces().get(0).ownerAccountId()).isEqualTo("1234567890");
        assertThat(result.namespaces().get(0).createdBy()).isEqualTo("1234567890");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        NamespaceSummary namespaceSummary = NamespaceSummary.newBuilder()
                .namespace(Arrays.asList("original_namespace"))
                .namespaceId("ns-original-xxxxxxxx")
                .ownerAccountId("0987654321")
                .createdBy("0987654321")
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListNamespacesResultJson bodyJson = new ListNamespacesResultJson();
        bodyJson.continuationToken = "original-token";
        bodyJson.namespaces = Arrays.asList(namespaceSummary);

        ListNamespacesResult original = ListNamespacesResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        ListNamespacesResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.continuationToken()).isEqualTo("original-token");
        assertThat(copy.namespaces()).isNotNull();
        assertThat(copy.namespaces()).hasSize(1);
        assertThat(copy.namespaces().get(0).namespace()).containsExactly("original_namespace");
        assertThat(copy.namespaces().get(0).namespaceId()).isEqualTo("ns-original-xxxxxxxx");
        assertThat(copy.namespaces().get(0).ownerAccountId()).isEqualTo("0987654321");
        assertThat(copy.namespaces().get(0).createdBy()).isEqualTo("0987654321");
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {
        // Simulate JSON response: {
        //   "continuationToken": "xxxxx",
        //   "namespaces": [
        //      {
        //         "namespace": ["my_namespace"],
        //         "namespaceId": "ns-xxxxxxxx",
        //         "ownerAccountId": "1234567890",
        //         "createdAt": "2024-01-01T00:00:00.000000Z",
        //         "createdBy": "1234567890"
        //      }
        //   ]
        // }
        String jsonData = "{\n" +
                "  \"continuationToken\": \"xxxxx\",\n" +
                "  \"namespaces\": [\n" +
                "    {\n" +
                "      \"namespace\": [\"my_namespace\"],\n" +
                "      \"namespaceId\": \"ns-xxxxxxxx\",\n" +
                "      \"ownerAccountId\": \"1234567890\",\n" +
                "      \"createdAt\": \"2024-01-01T00:00:00.000000Z\",\n" +
                "      \"createdBy\": \"1234567890\"\n" +
                "    }\n" +
                "  ]\n" +
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

        ListNamespacesResult result = SerdeNamespaceBasic.toListNamespaces(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(result.continuationToken()).isEqualTo("xxxxx");
        assertThat(result.namespaces()).isNotNull();
        assertThat(result.namespaces()).hasSize(1);
        assertThat(result.namespaces().get(0).namespace()).containsExactly("my_namespace");
        assertThat(result.namespaces().get(0).namespaceId()).isEqualTo("ns-xxxxxxxx");
        assertThat(result.namespaces().get(0).ownerAccountId()).isEqualTo("1234567890");
        assertThat(result.namespaces().get(0).createdAt()).isEqualTo("2024-01-01T00:00:00.000000Z");
        assertThat(result.namespaces().get(0).createdBy()).isEqualTo("1234567890");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}