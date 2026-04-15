package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableBucketPolicyResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableBucketConfigBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTableBucketPolicyResultTest {

    @Test
    public void testEmptyBuilder() {
        GetTableBucketPolicyResult result = GetTableBucketPolicyResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.resourcePolicy()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetTableBucketPolicyResultJson bodyJson = new GetTableBucketPolicyResultJson();
        bodyJson.resourcePolicy = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetTable\"],\"Effect\":\"Deny\",\"Principal\":[\"1234567890\"],\"Resource\":[\"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket\"]}]}";

        GetTableBucketPolicyResult result = GetTableBucketPolicyResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.resourcePolicy()).isEqualTo("{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetTable\"],\"Effect\":\"Deny\",\"Principal\":[\"1234567890\"],\"Resource\":[\"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket\"]}]}");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetTableBucketPolicyResultJson bodyJson = new GetTableBucketPolicyResultJson();
        bodyJson.resourcePolicy = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:ListTableBuckets\"],\"Effect\":\"Allow\",\"Principal\":[\"*\"],\"Resource\":[\"*\"]}]}";

        GetTableBucketPolicyResult original = GetTableBucketPolicyResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        GetTableBucketPolicyResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.resourcePolicy()).isEqualTo("{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:ListTableBuckets\"],\"Effect\":\"Allow\",\"Principal\":[\"*\"],\"Resource\":[\"*\"]}]}");
    }

    @Test
    public void xmlBuilder() {

        String jsonData = "{\"resourcePolicy\":\"{\\\"Version\\\":\\\"1\\\",\\\"Statement\\\":[{\\\"Action\\\":[\\\"oss:GetTable\\\"],\\\"Effect\\\":\\\"Deny\\\",\\\"Principal\\\":[\\\"1234567890\\\"],\\\"Resource\\\":[\\\"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket\\\"]}]}\"}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-xml-builder-test",
                        "ETag", "\"xml-builder-etag\""
                ))
                .status("OK")
                .statusCode(200)
                .build();

        GetTableBucketPolicyResult result = SerdeTableBucketConfigBasic.toGetTableBucketPolicy(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
        assertThat(result.resourcePolicy()).contains("Version");
        assertThat(result.resourcePolicy()).contains("Statement");
    }
}