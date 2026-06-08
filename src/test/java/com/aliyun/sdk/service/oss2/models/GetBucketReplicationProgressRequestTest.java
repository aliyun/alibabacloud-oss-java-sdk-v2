package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketReplication;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketReplicationProgressRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketReplicationProgressRequest request = GetBucketReplicationProgressRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.ruleId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetBucketReplicationProgressRequest request = GetBucketReplicationProgressRequest.newBuilder()
                .bucket("examplebucket")
                .ruleId("test_replication_1")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.ruleId()).isEqualTo("test_replication_1");

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2"),
                new AbstractMap.SimpleEntry<>("rule-id", "test_replication_1")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetBucketReplicationProgressRequest original = GetBucketReplicationProgressRequest.newBuilder()
                .bucket("testbucket")
                .ruleId("test_replication_1")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        GetBucketReplicationProgressRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.ruleId()).isEqualTo("test_replication_1");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4"),
                new AbstractMap.SimpleEntry<>("rule-id", "test_replication_1")
        );
    }

    @Test
    public void testHeaderProperties() {
        GetBucketReplicationProgressRequest request = GetBucketReplicationProgressRequest.newBuilder()
                .bucket("anotherbucket")
                .ruleId("test_replication_1")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.ruleId()).isEqualTo("test_replication_1");
    }

    @Test
    public void testXmlBuilder() {
        GetBucketReplicationProgressRequest request = GetBucketReplicationProgressRequest.newBuilder()
                .bucket("examplebucket")
                .ruleId("test_replication_1")
                .build();

        OperationInput input = SerdeBucketReplication.fromGetBucketReplicationProgress(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("replicationProgress")).isEqualTo("");
        assertThat(input.parameters().get("rule-id")).isEqualTo("test_replication_1");
    }
}