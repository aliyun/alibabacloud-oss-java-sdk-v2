package com.aliyun.sdk.service.oss2.tables.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTablePolicyResultJson;
import com.aliyun.sdk.service.oss2.tables.transform.SerdeTableConfigBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetTablePolicyResultTest {

    @Test
    public void testEmptyBuilder() {
        GetTablePolicyResult result = GetTablePolicyResult.newBuilder().build();
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

        String policy = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetTable\"],\"Effect\":\"Allow\",\"Principal\":[\"9876543210\"],\"Resource\":[\"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/*\"]}]}";

        GetTablePolicyResultJson bodyJson = new GetTablePolicyResultJson();
        bodyJson.resourcePolicy = policy;

        GetTablePolicyResult result = GetTablePolicyResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.resourcePolicy()).isEqualTo(policy);
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

        String policy = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetTable\"],\"Effect\":\"Deny\",\"Principal\":[\"1234567890\"],\"Resource\":[\"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/original-table\"]}]}";

        GetTablePolicyResultJson bodyJson = new GetTablePolicyResultJson();
        bodyJson.resourcePolicy = policy;

        GetTablePolicyResult original = GetTablePolicyResult.newBuilder()
                .headers(headers)
                .innerBody(bodyJson)
                .status("Created")
                .statusCode(201)
                .build();

        GetTablePolicyResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.resourcePolicy()).isEqualTo(policy);
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {

        String policy = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:GetTable\"],\"Effect\":\"Allow\",\"Principal\":[\"9876543210\"],\"Resource\":[\"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/*\"]}]}";

        String jsonData = "{\"resourcePolicy\":\"{\\\"Version\\\":\\\"1\\\",\\\"Statement\\\":[{\\\"Action\\\":[\\\"oss:GetTable\\\"],\\\"Effect\\\":\\\"Allow\\\",\\\"Principal\\\":[\\\"9876543210\\\"],\\\"Resource\\\":[\\\"acs:osstable:cn-hangzhou:1234567890:bucket/my-table-bucket/table/*\\\"]}]}\"}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(jsonData))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-xml-builder-test",
                        "ETag", "\"xml-builder-etag\""
                ))
                .status("OK")
                .statusCode(200)
                .build();

        GetTablePolicyResult result = SerdeTableConfigBasic.toGetTablePolicy(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("ETag")).isEqualTo("\"xml-builder-etag\"");
        assertThat(result.resourcePolicy()).isEqualTo(policy);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}
