package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketReplication;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketReplicationProgressResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketReplicationProgressResult result = GetBucketReplicationProgressResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        // Create test data based on the provided XML structure
        Progress progress = Progress.newBuilder()
                .historicalObject("enabled")
                .newObject("2015-09-24T15:28:14.000Z")
                .build();

        ReplicationPrefixSet prefixSet = ReplicationPrefixSet.newBuilder()
                .prefixs(Arrays.asList("source_image", "video"))
                .build();

        ReplicationDestination destination = ReplicationDestination.newBuilder()
                .bucket("target-bucket")
                .location("oss-cn-beijing")
                .transferType("oss_acc")
                .build();

        ReplicationProgressRule rule = ReplicationProgressRule.newBuilder()
                .id("test_replication_1")
                .prefixSet(prefixSet)
                .action("PUT")
                .destination(destination)
                .status("doing")
                .historicalObjectReplication("enabled")
                .progress(progress)
                .build();

        ReplicationProgress replicationProgress = ReplicationProgress.newBuilder()
                .rules(Arrays.asList(rule))
                .build();

        GetBucketReplicationProgressResult result = GetBucketReplicationProgressResult.newBuilder()
                .headers(headers)
                .innerBody(replicationProgress)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        ReplicationProgress resultReplicationProgress = result.replicationProgress();
        assertThat(resultReplicationProgress).isNotNull();
        assertThat(resultReplicationProgress.rules()).hasSize(1);

        ReplicationProgressRule resultRule = resultReplicationProgress.rules().get(0);
        assertThat(resultRule.id()).isEqualTo("test_replication_1");
        assertThat(resultRule.action()).isEqualTo("PUT");
        assertThat(resultRule.status()).isEqualTo("doing");
        assertThat(resultRule.historicalObjectReplication()).isEqualTo("enabled");

        // Check PrefixSet
        assertThat(resultRule.prefixSet()).isNotNull();
        assertThat(resultRule.prefixSet().prefixs()).containsExactly("source_image", "video");

        // Check Destination
        assertThat(resultRule.destination()).isNotNull();
        assertThat(resultRule.destination().bucket()).isEqualTo("target-bucket");
        assertThat(resultRule.destination().location()).isEqualTo("oss-cn-beijing");
        assertThat(resultRule.destination().transferType()).isEqualTo("oss_acc");

        // Check Progress
        assertThat(resultRule.progress()).isNotNull();
        assertThat(resultRule.progress().historicalObject()).isEqualTo("enabled");
        assertThat(resultRule.progress().newObject()).isEqualTo("2015-09-24T15:28:14.000Z");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        Progress progress = Progress.newBuilder()
                .historicalObject("enabled")
                .newObject("2015-09-24T15:28:14.000Z")
                .build();

        ReplicationPrefixSet prefixSet = ReplicationPrefixSet.newBuilder()
                .prefixs(Arrays.asList("source_image", "video"))
                .build();

        ReplicationDestination destination = ReplicationDestination.newBuilder()
                .bucket("target-bucket")
                .location("oss-cn-beijing")
                .transferType("oss_acc")
                .build();

        ReplicationProgressRule rule = ReplicationProgressRule.newBuilder()
                .id("test_replication_1")
                .prefixSet(prefixSet)
                .action("PUT")
                .destination(destination)
                .status("doing")
                .historicalObjectReplication("enabled")
                .progress(progress)
                .build();

        ReplicationProgress replicationProgress = ReplicationProgress.newBuilder()
                .rules(Arrays.asList(rule))
                .build();

        GetBucketReplicationProgressResult original = GetBucketReplicationProgressResult.newBuilder()
                .headers(headers)
                .innerBody(replicationProgress)
                .build();

        GetBucketReplicationProgressResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        ReplicationProgress resultReplicationProgress = copy.replicationProgress();
        assertThat(resultReplicationProgress).isNotNull();
        assertThat(resultReplicationProgress.rules()).hasSize(1);

        ReplicationProgressRule resultRule = resultReplicationProgress.rules().get(0);
        assertThat(resultRule.id()).isEqualTo("test_replication_1");
        assertThat(resultRule.action()).isEqualTo("PUT");
        assertThat(resultRule.status()).isEqualTo("doing");
        assertThat(resultRule.historicalObjectReplication()).isEqualTo("enabled");

        // Check PrefixSet
        assertThat(resultRule.prefixSet()).isNotNull();
        assertThat(resultRule.prefixSet().prefixs()).containsExactly("source_image", "video");

        // Check Destination
        assertThat(resultRule.destination()).isNotNull();
        assertThat(resultRule.destination().bucket()).isEqualTo("target-bucket");
        assertThat(resultRule.destination().location()).isEqualTo("oss-cn-beijing");
        assertThat(resultRule.destination().transferType()).isEqualTo("oss_acc");

        // Check Progress
        assertThat(resultRule.progress()).isNotNull();
        assertThat(resultRule.progress().historicalObject()).isEqualTo("enabled");
        assertThat(resultRule.progress().newObject()).isEqualTo("2015-09-24T15:28:14.000Z");
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String xml =
                "<ReplicationProgress>\n" +
                " <Rule>\n" +
                "   <ID>test_replication_1</ID>\n" +
                "   <PrefixSet>\n" +
                "    <Prefix>source_image</Prefix>\n" +
                "    <Prefix>video</Prefix>\n" +
                "   </PrefixSet>\n" +
                "   <Action>PUT</Action>\n" +
                "   <Destination>\n" +
                "    <Bucket>target-bucket</Bucket>\n" +
                "    <Location>oss-cn-beijing</Location>\n" +
                "    <TransferType>oss_acc</TransferType>\n" +
                "   </Destination>\n" +
                "   <Status>doing</Status>\n" +
                "   <HistoricalObjectReplication>enabled</HistoricalObjectReplication>\n" +
                "   <Progress>\n" +
                "    <HistoricalObject>0.85</HistoricalObject>\n" +
                "    <NewObject>2015-09-24T15:28:14.000Z</NewObject>\n" +
                "   </Progress>\n" +
                " </Rule>\n" +
                "</ReplicationProgress>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketReplicationProgressResult result = SerdeBucketReplication.toGetBucketReplicationProgress(output);

        ReplicationProgress replicationProgress = result.replicationProgress();
        assertThat(replicationProgress).isNotNull();
        assertThat(replicationProgress.rules()).hasSize(1);

        ReplicationProgressRule rule = replicationProgress.rules().get(0);
        assertThat(rule.id()).isEqualTo("test_replication_1");
        assertThat(rule.action()).isEqualTo("PUT");
        assertThat(rule.status()).isEqualTo("doing");
        assertThat(rule.historicalObjectReplication()).isEqualTo("enabled");

        // Check PrefixSet
        assertThat(rule.prefixSet()).isNotNull();
        assertThat(rule.prefixSet().prefixs()).containsExactly("source_image", "video");

        // Check Destination
        assertThat(rule.destination()).isNotNull();
        assertThat(rule.destination().bucket()).isEqualTo("target-bucket");
        assertThat(rule.destination().location()).isEqualTo("oss-cn-beijing");
        assertThat(rule.destination().transferType()).isEqualTo("oss_acc");

        // Check Progress
        assertThat(rule.progress()).isNotNull();
        assertThat(rule.progress().historicalObject()).isEqualTo("0.85");
        assertThat(rule.progress().newObject()).isEqualTo("2015-09-24T15:28:14.000Z");
    }
}