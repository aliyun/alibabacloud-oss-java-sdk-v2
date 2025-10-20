package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketPolicy;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketPolicyStatusResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketPolicyStatusResult result = GetBucketPolicyStatusResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.policyStatus()).isNull();
    }

    @Test
    public void testFullBuilder() {
        PolicyStatus policyStatus = PolicyStatus.newBuilder()
                .isPublic(true)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetBucketPolicyStatusResult result = GetBucketPolicyStatusResult.newBuilder()
                .headers(headers)
                .innerBody(policyStatus)
                .statusCode(200)
                .status("OK")
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.policyStatus()).isEqualTo(policyStatus);
        assertThat(result.policyStatus().isPublic()).isEqualTo(true);
    }

    @Test
    public void testToBuilderPreserveState() {
        PolicyStatus policyStatus = PolicyStatus.newBuilder()
                .isPublic(false)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetBucketPolicyStatusResult original = GetBucketPolicyStatusResult.newBuilder()
                .headers(headers)
                .innerBody(policyStatus)
                .statusCode(201)
                .status("Created")
                .build();

        GetBucketPolicyStatusResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.policyStatus()).isEqualTo(policyStatus);
        assertThat(copy.policyStatus().isPublic()).isEqualTo(false);
    }

    @Test
    public void testXmlBuilderWithEncoding() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<PolicyStatus>\n   <IsPublic>true</IsPublic>\n</PolicyStatus>";
        GetBucketPolicyStatusResult result;

        OperationOutput output = OperationOutput.newBuilder()
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-1234567890abcdefg",
                        "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
                ))
                .status("OK")
                .statusCode(200)
                .body(BinaryData.fromString(xml))
                .build();

        result = SerdeBucketPolicy.toGetBucketPolicyStatus(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.policyStatus()).isNotNull();
        assertThat(result.policyStatus().isPublic()).isEqualTo(true);
    }
}