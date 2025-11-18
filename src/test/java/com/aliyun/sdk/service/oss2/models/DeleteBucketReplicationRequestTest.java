package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketReplication;
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

public class DeleteBucketReplicationRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteBucketReplicationRequest request = DeleteBucketReplicationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.bucket()).isNull();
        assertThat(request.replicationRules()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ReplicationRules replicationRules = ReplicationRules.newBuilder()
                .id("rule id")
                .build();

        DeleteBucketReplicationRequest request = DeleteBucketReplicationRequest.newBuilder()
                .bucket("examplebucket")
                .replicationRules(replicationRules)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.replicationRules()).isEqualTo(replicationRules);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new java.util.AbstractMap.SimpleEntry<>("param1", "value1"),
                new java.util.AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ReplicationRules replicationRules = ReplicationRules.newBuilder()
                .id("rule id")
                .build();

        DeleteBucketReplicationRequest original = DeleteBucketReplicationRequest.newBuilder()
                .bucket("testbucket")
                .replicationRules(replicationRules)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        DeleteBucketReplicationRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.replicationRules()).isEqualTo(replicationRules);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new java.util.AbstractMap.SimpleEntry<>("param3", "value3"),
                new java.util.AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        ReplicationRules replicationRules = ReplicationRules.newBuilder()
                .id("rule id")
                .build();

        DeleteBucketReplicationRequest request = DeleteBucketReplicationRequest.newBuilder()
                .bucket("anotherbucket")
                .replicationRules(replicationRules)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.replicationRules().id()).isEqualTo("rule id");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ReplicationRules replicationRules = ReplicationRules.newBuilder()
                .id("rule id")
                .build();

        DeleteBucketReplicationRequest request = DeleteBucketReplicationRequest.newBuilder()
                .bucket("examplebucket")
                .replicationRules(replicationRules)
                .build();

        OperationInput input = SerdeBucketReplication.fromDeleteBucketReplication(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("replication")).isEqualTo("");
        assertThat(input.parameters().get("comp")).isEqualTo("delete");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<ReplicationRules>");
        assertThat(xmlContent).contains("<ID>rule id</ID>");
        assertThat(xmlContent).contains("</ReplicationRules>");
    }
}