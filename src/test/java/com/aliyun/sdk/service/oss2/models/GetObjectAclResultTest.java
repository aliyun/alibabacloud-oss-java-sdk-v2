package com.aliyun.sdk.service.oss2.models;


import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetObjectAclResultTest {

    @Test
    public void testEmptyBuilder() {
        GetObjectAclResult result = GetObjectAclResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Owner owner = Owner.newBuilder()
                .id("1234513715092****")
                .displayName("1234-****")
                .build();

        AccessControlList accessControlList = AccessControlList.newBuilder()
                .grant("public-read")
                .build();

        AccessControlPolicy accessControlPolicy = AccessControlPolicy.newBuilder()
                .owner(owner)
                .accessControlList(accessControlList)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\"",
                "versionId", "version123456"
        );

        GetObjectAclResult result = GetObjectAclResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .innerBody(accessControlPolicy)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.headers().get("versionId")).isEqualTo("version123456");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.versionId()).isEqualTo("version123456");

        AccessControlPolicy policy = result.accessControlPolicy();
        assertThat(policy).isNotNull();

        Owner ownerResult = policy.owner();
        assertThat(ownerResult).isNotNull();
        assertThat(ownerResult.id()).isEqualTo("1234513715092****");
        assertThat(ownerResult.displayName()).isEqualTo("1234-****");

        AccessControlList aclResult = policy.accessControlList();
        assertThat(aclResult).isNotNull();
        assertThat(aclResult.grant()).isEqualTo("public-read");
    }

    @Test
    public void testToBuilderPreserveState() {
        Owner owner = Owner.newBuilder()
                .id("original-id")
                .displayName("original-display")
                .build();

        AccessControlList accessControlList = AccessControlList.newBuilder()
                .grant("private")
                .build();

        AccessControlPolicy accessControlPolicy = AccessControlPolicy.newBuilder()
                .owner(owner)
                .accessControlList(accessControlList)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\"",
                "versionId", "original-version"
        );

        GetObjectAclResult original = GetObjectAclResult.newBuilder()
                .headers(headers)
                .status("Created")
                .statusCode(201)
                .innerBody(accessControlPolicy)
                .build();

        GetObjectAclResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.headers().get("versionId")).isEqualTo("original-version");
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.versionId()).isEqualTo("original-version");

        AccessControlPolicy policy = copy.accessControlPolicy();
        assertThat(policy).isNotNull();

        Owner ownerResult = policy.owner();
        assertThat(ownerResult).isNotNull();
        assertThat(ownerResult.id()).isEqualTo("original-id");
        assertThat(ownerResult.displayName()).isEqualTo("original-display");

        AccessControlList aclResult = policy.accessControlList();
        assertThat(aclResult).isNotNull();
        assertThat(aclResult.grant()).isEqualTo("private");
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String xml =
                "<AccessControlPolicy>\n" +
                        "  <Owner>\n" +
                        "    <ID>1234513715092****</ID>\n" +
                        "    <DisplayName>1234-****</DisplayName>\n" +
                        "  </Owner>\n" +
                        "  <AccessControlList>\n" +
                        "    <Grant>public-read</Grant>\n" +
                        "  </AccessControlList>\n" +
                        "</AccessControlPolicy>";

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        AccessControlPolicy innerBody = xmlMapper.readValue(xml, AccessControlPolicy.class);
        GetObjectAclResult result = GetObjectAclResult.newBuilder()
                .innerBody(innerBody)
                .build();

        AccessControlPolicy policy = result.accessControlPolicy();
        assertThat(policy).isNotNull();

        Owner ownerResult = policy.owner();
        assertThat(ownerResult).isNotNull();
        assertThat(ownerResult.id()).isEqualTo("1234513715092****");
        assertThat(ownerResult.displayName()).isEqualTo("1234-****");

        AccessControlList aclResult = policy.accessControlList();
        assertThat(aclResult).isNotNull();
        assertThat(aclResult.grant()).isEqualTo("public-read");
    }
}
