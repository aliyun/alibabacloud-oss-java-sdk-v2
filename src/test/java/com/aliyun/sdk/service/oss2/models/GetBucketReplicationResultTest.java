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

public class GetBucketReplicationResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketReplicationResult result = GetBucketReplicationResult.newBuilder().build();
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
        RTC rtc = RTC.newBuilder()
                .status("enabled")
                .build();

        ReplicationPrefixSet prefixSet = ReplicationPrefixSet.newBuilder()
                .prefixs(Arrays.asList("source1", "video"))
                .build();

        ReplicationDestination destination = ReplicationDestination.newBuilder()
                .bucket("destbucket")
                .location("oss-cn-beijing")
                .transferType("oss_acc")
                .build();

        ReplicationRule rule = ReplicationRule.newBuilder()
                .id("test_replication_1")
                .prefixSet(prefixSet)
                .action("PUT")
                .destination(destination)
                .status("doing")
                .historicalObjectReplication("enabled")
                .syncRole("aliyunramrole")
                .rtc(rtc)
                .build();

        ReplicationConfiguration config = ReplicationConfiguration.newBuilder()
                .rules(Arrays.asList(rule))
                .build();

        GetBucketReplicationResult result = GetBucketReplicationResult.newBuilder()
                .headers(headers)
                .innerBody(config)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        ReplicationConfiguration replicationConfig = result.replicationConfiguration();
        assertThat(replicationConfig).isNotNull();
        assertThat(replicationConfig.rules()).hasSize(1);

        ReplicationRule resultRule = replicationConfig.rules().get(0);
        assertThat(resultRule.id()).isEqualTo("test_replication_1");
        assertThat(resultRule.action()).isEqualTo("PUT");
        assertThat(resultRule.status()).isEqualTo("doing");
        assertThat(resultRule.historicalObjectReplication()).isEqualTo("enabled");
        assertThat(resultRule.syncRole()).isEqualTo("aliyunramrole");

        // Check RTC
        assertThat(resultRule.rtc()).isNotNull();
        assertThat(resultRule.rtc().status()).isEqualTo("enabled");

        // Check PrefixSet
        assertThat(resultRule.prefixSet()).isNotNull();
        assertThat(resultRule.prefixSet().prefixs()).containsExactly("source1", "video");

        // Check Destination
        assertThat(resultRule.destination()).isNotNull();
        assertThat(resultRule.destination().bucket()).isEqualTo("destbucket");
        assertThat(resultRule.destination().location()).isEqualTo("oss-cn-beijing");
        assertThat(resultRule.destination().transferType()).isEqualTo("oss_acc");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        RTC rtc = RTC.newBuilder()
                .status("enabled")
                .build();

        ReplicationPrefixSet prefixSet = ReplicationPrefixSet.newBuilder()
                .prefixs(Arrays.asList("source1", "video"))
                .build();

        ReplicationDestination destination = ReplicationDestination.newBuilder()
                .bucket("destbucket")
                .location("oss-cn-beijing")
                .transferType("oss_acc")
                .build();

        ReplicationRule rule = ReplicationRule.newBuilder()
                .id("test_replication_1")
                .prefixSet(prefixSet)
                .action("PUT")
                .destination(destination)
                .status("doing")
                .historicalObjectReplication("enabled")
                .syncRole("aliyunramrole")
                .rtc(rtc)
                .build();

        ReplicationConfiguration config = ReplicationConfiguration.newBuilder()
                .rules(Arrays.asList(rule))
                .build();

        GetBucketReplicationResult original = GetBucketReplicationResult.newBuilder()
                .headers(headers)
                .innerBody(config)
                .build();

        GetBucketReplicationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        ReplicationConfiguration replicationConfig = copy.replicationConfiguration();
        assertThat(replicationConfig).isNotNull();
        assertThat(replicationConfig.rules()).hasSize(1);

        ReplicationRule resultRule = replicationConfig.rules().get(0);
        assertThat(resultRule.id()).isEqualTo("test_replication_1");
        assertThat(resultRule.action()).isEqualTo("PUT");
        assertThat(resultRule.status()).isEqualTo("doing");
        assertThat(resultRule.historicalObjectReplication()).isEqualTo("enabled");
        assertThat(resultRule.syncRole()).isEqualTo("aliyunramrole");

        // Check RTC
        assertThat(resultRule.rtc()).isNotNull();
        assertThat(resultRule.rtc().status()).isEqualTo("enabled");

        // Check PrefixSet
        assertThat(resultRule.prefixSet()).isNotNull();
        assertThat(resultRule.prefixSet().prefixs()).containsExactly("source1", "video");

        // Check Destination
        assertThat(resultRule.destination()).isNotNull();
        assertThat(resultRule.destination().bucket()).isEqualTo("destbucket");
        assertThat(resultRule.destination().location()).isEqualTo("oss-cn-beijing");
        assertThat(resultRule.destination().transferType()).isEqualTo("oss_acc");
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String xml =
                "<ReplicationConfiguration>\n" +
                "  <Rule>\n" +
                "    <ID>test_replication_1</ID>\n" +
                "    <PrefixSet>\n" +
                "      <Prefix>source1</Prefix>\n" +
                "      <Prefix>video</Prefix>\n" +
                "    </PrefixSet>\n" +
                "    <Action>PUT</Action>\n" +
                "    <Destination>\n" +
                "      <Bucket>destbucket</Bucket>\n" +
                "      <Location>oss-cn-beijing</Location>\n" +
                "      <TransferType>oss_acc</TransferType>\n" +
                "    </Destination>\n" +
                "    <Status>doing</Status>\n" +
                "    <HistoricalObjectReplication>enabled</HistoricalObjectReplication>\n" +
                "    <SyncRole>aliyunramrole</SyncRole>\n" +
                "    <RTC>\n" +
                "      <Status>enabled</Status>\n" +
                "    </RTC>\n" +
                "  </Rule>\n" +
                "</ReplicationConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketReplicationResult result = SerdeBucketReplication.toGetBucketReplication(output);

        ReplicationConfiguration replicationConfig = result.replicationConfiguration();
        assertThat(replicationConfig).isNotNull();
        assertThat(replicationConfig.rules()).hasSize(1);

        ReplicationRule rule = replicationConfig.rules().get(0);
        assertThat(rule.id()).isEqualTo("test_replication_1");
        assertThat(rule.action()).isEqualTo("PUT");
        assertThat(rule.status()).isEqualTo("doing");
        assertThat(rule.historicalObjectReplication()).isEqualTo("enabled");
        assertThat(rule.syncRole()).isEqualTo("aliyunramrole");

        // Check RTC
        assertThat(rule.rtc()).isNotNull();
        assertThat(rule.rtc().status()).isEqualTo("enabled");

        // Check PrefixSet
        assertThat(rule.prefixSet()).isNotNull();
        assertThat(rule.prefixSet().prefixs()).containsExactly("source1", "video");

        // Check Destination
        assertThat(rule.destination()).isNotNull();
        assertThat(rule.destination().bucket()).isEqualTo("destbucket");
        assertThat(rule.destination().location()).isEqualTo("oss-cn-beijing");
        assertThat(rule.destination().transferType()).isEqualTo("oss_acc");
    }
}