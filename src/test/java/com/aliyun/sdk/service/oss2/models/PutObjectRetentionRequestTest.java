package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectWorm;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutObjectRetentionRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutObjectRetentionRequest request = PutObjectRetentionRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.versionId()).isNull();
        assertThat(request.retention()).isNull();
        assertThat(request.bypassGovernanceRetention()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg"
        );

        Retention retention = Retention.newBuilder()
                .mode(ObjectRetentionModeType.COMPLIANCE.toString())
                .retainUntilDate("2025-12-31T00:00:00.000Z")
                .build();

        PutObjectRetentionRequest request = PutObjectRetentionRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .versionId("version-id")
                .retention(retention)
                .bypassGovernanceRetention(true)
                .headers(headers)
                .parameter("param1", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject");
        assertThat(request.versionId()).isEqualTo("version-id");
        assertThat(request.retention()).isEqualTo(retention);
        assertThat(request.bypassGovernanceRetention()).isEqualTo(true);
        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Retention retention = Retention.newBuilder()
                .mode(ObjectRetentionModeType.GOVERNANCE.toString())
                .retainUntilDate("2026-06-30T00:00:00.000Z")
                .build();

        PutObjectRetentionRequest original = PutObjectRetentionRequest.newBuilder()
                .bucket("testbucket")
                .key("testobject")
                .versionId("original-version-id")
                .retention(retention)
                .bypassGovernanceRetention(false)
                .build();

        PutObjectRetentionRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.key()).isEqualTo("testobject");
        assertThat(copy.versionId()).isEqualTo("original-version-id");
        assertThat(copy.retention()).isEqualTo(retention);
        assertThat(copy.bypassGovernanceRetention()).isEqualTo(false);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        Retention retention = Retention.newBuilder()
                .mode(ObjectRetentionModeType.COMPLIANCE.toString())
                .retainUntilDate("2025-12-31T00:00:00.000Z")
                .build();

        PutObjectRetentionRequest request = PutObjectRetentionRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .retention(retention)
                .bypassGovernanceRetention(true)
                .build();

        OperationInput input = SerdeObjectWorm.fromPutObjectRetention(request);

        assertThat(input.parameters().get("retention")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.headers().get("x-oss-bypass-governance-retention")).isEqualTo("true");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<Retention>");
        assertThat(xmlContent).contains("<Mode>COMPLIANCE</Mode>");
        assertThat(xmlContent).contains("<RetainUntilDate>2025-12-31T00:00:00.000Z</RetainUntilDate>");
        assertThat(xmlContent).contains("</Retention>");
    }
}
