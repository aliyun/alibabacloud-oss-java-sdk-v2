package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketReplication;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketReplicationResultTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketReplicationResult result = PutBucketReplicationResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "x-oss-replication-rule-id", "replication-rule-id-123",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        PutBucketReplicationResult result = PutBucketReplicationResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-replication-rule-id")).isEqualTo("replication-rule-id-123");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.replicationRuleId()).isEqualTo("replication-rule-id-123");
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "x-oss-replication-rule-id", "replication-rule-id-456",
                "ETag", "\"original-etag\""
        );

        PutBucketReplicationResult original = PutBucketReplicationResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        PutBucketReplicationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("x-oss-replication-rule-id")).isEqualTo("replication-rule-id-456");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.replicationRuleId()).isEqualTo("replication-rule-id-456");
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeBucketReplication.toPutBucketReplication(blankOutput);

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-111111111111111111",
                "x-oss-replication-rule-id", "replication-rule-id-789"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .body(null)
                .build();

        PutBucketReplicationResult result = SerdeBucketReplication.toPutBucketReplication(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.headers().get("x-oss-replication-rule-id")).isEqualTo("replication-rule-id-789");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.replicationRuleId()).isEqualTo("replication-rule-id-789");
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
    }
}