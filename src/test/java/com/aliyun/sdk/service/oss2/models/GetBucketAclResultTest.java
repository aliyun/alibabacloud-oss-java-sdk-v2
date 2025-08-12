package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketAcl;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketAclResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketAclResult result = GetBucketAclResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        Owner owner = Owner.newBuilder()
                .id("0022012****")
                .displayName("user_example")
                .build();

        AccessControlList accessControlList = AccessControlList.newBuilder()
                .grant("public-read")
                .build();

        AccessControlPolicy accessControlPolicy = AccessControlPolicy.newBuilder()
                .owner(owner)
                .accessControlList(accessControlList)
                .build();

        GetBucketAclResult result = GetBucketAclResult.newBuilder()
                .headers(headers)
                .statusCode(200)
                .status("OK")
                .innerBody(accessControlPolicy)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");

        assertThat(result.accessControlPolicy()).isNotNull();
        assertThat(result.accessControlPolicy().owner()).isNotNull();
        assertThat(result.accessControlPolicy().owner().id()).isEqualTo("0022012****");
        assertThat(result.accessControlPolicy().owner().displayName()).isEqualTo("user_example");
        assertThat(result.accessControlPolicy().accessControlList()).isNotNull();
        assertThat(result.accessControlPolicy().accessControlList().grant()).isEqualTo("public-read");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        Owner owner = Owner.newBuilder()
                .id("0022012****")
                .displayName("user_example")
                .build();

        AccessControlList accessControlList = AccessControlList.newBuilder()
                .grant("public-read")
                .build();

        AccessControlPolicy accessControlPolicy = AccessControlPolicy.newBuilder()
                .owner(owner)
                .accessControlList(accessControlList)
                .build();

        GetBucketAclResult original = GetBucketAclResult.newBuilder()
                .headers(headers)
                .statusCode(200)
                .status("OK")
                .innerBody(accessControlPolicy)
                .build();

        GetBucketAclResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");

        assertThat(copy.accessControlPolicy()).isNotNull();
        assertThat(copy.accessControlPolicy().owner()).isNotNull();
        assertThat(copy.accessControlPolicy().owner().id()).isEqualTo("0022012****");
        assertThat(copy.accessControlPolicy().owner().displayName()).isEqualTo("user_example");
        assertThat(copy.accessControlPolicy().accessControlList()).isNotNull();
        assertThat(copy.accessControlPolicy().accessControlList().grant()).isEqualTo("public-read");
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeBucketAcl.toGetBucketAcl(blankOutput);

        String xml =
                "<AccessControlPolicy>\n" +
                        "    <Owner>\n" +
                        "        <ID>0022012****</ID>\n" +
                        "        <DisplayName>user_example</DisplayName>\n" +
                        "    </Owner>\n" +
                        "    <AccessControlList>\n" +
                        "        <Grant>public-read</Grant>\n" +
                        "    </AccessControlList>\n" +
                        "</AccessControlPolicy>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .statusCode(200)
                .status("OK")
                .headers(MapUtils.of(
                        "x-request-id", "req-1234567890abcdefg",
                        "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
                ))
                .build();
        GetBucketAclResult result = SerdeBucketAcl.toGetBucketAcl(output);

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");

        assertThat(result.accessControlPolicy()).isNotNull();
        assertThat(result.accessControlPolicy().owner()).isNotNull();
        assertThat(result.accessControlPolicy().owner().id()).isEqualTo("0022012****");
        assertThat(result.accessControlPolicy().owner().displayName()).isEqualTo("user_example");
        assertThat(result.accessControlPolicy().accessControlList()).isNotNull();
        assertThat(result.accessControlPolicy().accessControlList().grant()).isEqualTo("public-read");
    }
}
