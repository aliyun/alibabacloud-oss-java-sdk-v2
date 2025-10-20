package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketPolicy;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketPolicyResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketPolicyResult result = GetBucketPolicyResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.body()).isNull();
    }

    @Test
    public void testFullBuilder() {
        String policyContent = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:PutObject\",\"oss:GetObject\"],\"Effect\":\"Deny\",\"Principal\":[\"1234567890\"],\"Resource\":[\"acs:oss:*:1234567890:*/*\"]}]}";
        BinaryData body = BinaryData.fromString(policyContent);

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetBucketPolicyResult result = GetBucketPolicyResult.newBuilder()
                .headers(headers)
                .innerBody(body)
                .statusCode(200)
                .status("OK")
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.body().toString()).isEqualTo(policyContent);
    }

    @Test
    public void testToBuilderPreserveState() {
        String policyContent = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:PutObject\",\"oss:GetObject\"],\"Effect\":\"Deny\",\"Principal\":[\"1234567890\"],\"Resource\":[\"acs:oss:*:1234567890:*/*\"]}]}";
        BinaryData body = BinaryData.fromString(policyContent);

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetBucketPolicyResult original = GetBucketPolicyResult.newBuilder()
                .headers(headers)
                .innerBody(body)
                .statusCode(201)
                .status("Created")
                .build();

        GetBucketPolicyResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.body().toString()).isEqualTo(policyContent);
    }

    @Test
    public void testXmlBuilderWithEncoding() {
        String policyContent = "{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:PutObject\",\"oss:GetObject\"],\"Effect\":\"Deny\",\"Principal\":[\"1234567890\"],\"Resource\":[\"acs:oss:*:1234567890:*/*\"]}]}";
        GetBucketPolicyResult result;
        OperationOutput output = OperationOutput.newBuilder()
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-1234567890abcdefg",
                        "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
                ))
                .status("OK")
                .statusCode(200)
                .body(BinaryData.fromString(policyContent))
                .build();
        
        result = SerdeBucketPolicy.toGetBucketPolicy(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.body().toString()).isEqualTo(policyContent);
    }
}