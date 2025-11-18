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
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketReplicationRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketReplicationRequest request = PutBucketReplicationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.replicationConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        // Create replication rule
        ReplicationRule rule = ReplicationRule.newBuilder()
                .id("replication-id")
                .status("enabled")
                .historicalObjectReplication("enabled")
                .action("PUT")
                .syncRole("aliyunramrole")
                .destination(ReplicationDestination.newBuilder()
                        .bucket("destbucket")
                        .location("oss-cn-beijing")
                        .transferType(TransferType.OSS_ACC)
                        .build())
                .encryptionConfiguration(ReplicationEncryptionConfiguration.newBuilder()
                        .replicaKmsKeyID("c4d49f85-ee30-426b-a5ed-95e9139d****")
                        .build())
                .sourceSelectionCriteria(ReplicationSourceSelectionCriteria.newBuilder()
                        .sseKmsEncryptedObjects(SseKmsEncryptedObjects.newBuilder()
                                .status("Enabled")
                                .build())
                        .build())
                .build();

        ReplicationConfiguration config = ReplicationConfiguration.newBuilder()
                .rules(Arrays.asList(rule))
                .build();

        PutBucketReplicationRequest request = PutBucketReplicationRequest.newBuilder()
                .bucket("examplebucket")
                .replicationConfiguration(config)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.replicationConfiguration()).isEqualTo(config);

        // Check all fields in the replication configuration
        assertThat(request.replicationConfiguration().rules()).hasSize(1);
        ReplicationRule actualRule = request.replicationConfiguration().rules().get(0);
        assertThat(actualRule.id()).isEqualTo("replication-id");
        assertThat(actualRule.status()).isEqualTo("enabled");
        assertThat(actualRule.historicalObjectReplication()).isEqualTo("enabled");
        assertThat(actualRule.action()).isEqualTo("PUT");
        assertThat(actualRule.syncRole()).isEqualTo("aliyunramrole");
        
        // Check destination
        assertThat(actualRule.destination()).isNotNull();
        assertThat(actualRule.destination().bucket()).isEqualTo("destbucket");
        assertThat(actualRule.destination().location()).isEqualTo("oss-cn-beijing");
        assertThat(actualRule.destination().transferType()).isEqualTo(TransferType.OSS_ACC.toString());
        
        // Check encryption configuration
        assertThat(actualRule.encryptionConfiguration()).isNotNull();
        assertThat(actualRule.encryptionConfiguration().replicaKmsKeyID()).isEqualTo("c4d49f85-ee30-426b-a5ed-95e9139d****");
        
        // Check source selection criteria
        assertThat(actualRule.sourceSelectionCriteria()).isNotNull();
        assertThat(actualRule.sourceSelectionCriteria().sseKmsEncryptedObjects()).isNotNull();
        assertThat(actualRule.sourceSelectionCriteria().sseKmsEncryptedObjects().status()).isEqualTo("Enabled");

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ReplicationRule rule = ReplicationRule.newBuilder()
                .id("replication-id")
                .status("enabled")
                .historicalObjectReplication("enabled")
                .action("PUT")
                .syncRole("aliyunramrole")
                .build();

        ReplicationConfiguration config = ReplicationConfiguration.newBuilder()
                .rules(Arrays.asList(rule))
                .build();

        PutBucketReplicationRequest original = PutBucketReplicationRequest.newBuilder()
                .bucket("testbucket")
                .replicationConfiguration(config)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutBucketReplicationRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.replicationConfiguration()).isEqualTo(config);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        ReplicationRule rule = ReplicationRule.newBuilder()
                .id("replication-id")
                .status("enabled")
                .historicalObjectReplication("enabled")
                .action("PUT")
                .syncRole("aliyunramrole")
                .build();

        ReplicationConfiguration config = ReplicationConfiguration.newBuilder()
                .rules(Arrays.asList(rule))
                .build();

        PutBucketReplicationRequest request = PutBucketReplicationRequest.newBuilder()
                .bucket("anotherbucket")
                .replicationConfiguration(config)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.replicationConfiguration()).isEqualTo(config);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<ReplicationConfiguration>\n" +
                "  <Rule>  \n" +
                "     <RTC>\n" +
                "        <Status>enabled</Status>\n" +
                "     </RTC>\n" +
                "     <PrefixSet>\n" +
                "        <Prefix>source1</Prefix>\n" +
                "        <Prefix>video</Prefix>\n" +
                "     </PrefixSet>\n" +
                "     <Action>PUT</Action>\n" +
                "     <Destination>\n" +
                "        <Bucket>destbucket</Bucket>\n" +
                "        <Location>oss-cn-beijing</Location>\n" +
                "        <TransferType>oss_acc</TransferType>\n" +
                "     </Destination>\n" +
                "     <HistoricalObjectReplication>enabled</HistoricalObjectReplication>\n" +
                "      <SyncRole>aliyunramrole</SyncRole>\n" +
                "      <SourceSelectionCriteria>\n" +
                "         <SseKmsEncryptedObjects>\n" +
                "           <Status>Enabled</Status>\n" +
                "         </SseKmsEncryptedObjects>\n" +
                "      </SourceSelectionCriteria>\n" +
                "      <EncryptionConfiguration>\n" +
                "           <ReplicaKmsKeyID>c4d49f85-ee30-426b-a5ed-95e9139d****</ReplicaKmsKeyID>\n" +
                "      </EncryptionConfiguration>     \n" +
                "  </Rule>\n" +
                "</ReplicationConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ReplicationConfiguration replicationConfiguration = xmlMapper.readValue(xml, ReplicationConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(replicationConfiguration);

        // Create a complete replication configuration with all nested objects
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
        
        SseKmsEncryptedObjects sseKms = SseKmsEncryptedObjects.newBuilder()
                .status("Enabled")
                .build();
        
        ReplicationSourceSelectionCriteria sourceCriteria = ReplicationSourceSelectionCriteria.newBuilder()
                .sseKmsEncryptedObjects(sseKms)
                .build();
        
        ReplicationEncryptionConfiguration encryptionConfig = ReplicationEncryptionConfiguration.newBuilder()
                .replicaKmsKeyID("c4d49f85-ee30-426b-a5ed-95e9139d****")
                .build();
        
        ReplicationRule rule = ReplicationRule.newBuilder()
                .rtc(rtc)
                .prefixSet(prefixSet)
                .action("PUT")
                .destination(destination)
                .historicalObjectReplication("enabled")
                .syncRole("aliyunramrole")
                .sourceSelectionCriteria(sourceCriteria)
                .encryptionConfiguration(encryptionConfig)
                .build();

        ReplicationConfiguration config = ReplicationConfiguration.newBuilder()
                .rules(Arrays.asList(rule))
                .build();

        PutBucketReplicationRequest request = PutBucketReplicationRequest.newBuilder()
                .bucket("oss-example")
                .replicationConfiguration(config)
                .build();

        OperationInput input = SerdeBucketReplication.fromPutBucketReplication(request);

        assertThat(input.bucket().get()).isEqualTo("oss-example");
        assertThat(input.parameters().get("replication")).isEqualTo("");
        assertThat(input.parameters().get("comp")).isEqualTo("add");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);

        assertThat(xmlContent).isEqualTo(expectedXml);

        assertThat(xmlContent).contains("<ReplicationConfiguration>");
        assertThat(xmlContent).contains("<Rule>");
        assertThat(xmlContent).contains("<RTC>");
        assertThat(xmlContent).contains("<Status>enabled</Status>");
        assertThat(xmlContent).contains("<PrefixSet>");
        assertThat(xmlContent).contains("<Prefix>source1</Prefix>");
        assertThat(xmlContent).contains("<Prefix>video</Prefix>");
        assertThat(xmlContent).contains("<Action>PUT</Action>");
        assertThat(xmlContent).contains("<Destination>");
        assertThat(xmlContent).contains("<Bucket>destbucket</Bucket>");
        assertThat(xmlContent).contains("<Location>oss-cn-beijing</Location>");
        assertThat(xmlContent).contains("<TransferType>oss_acc</TransferType>");
        assertThat(xmlContent).contains("<HistoricalObjectReplication>enabled</HistoricalObjectReplication>");
        assertThat(xmlContent).contains("<SyncRole>aliyunramrole</SyncRole>");
        assertThat(xmlContent).contains("<SourceSelectionCriteria>");
        assertThat(xmlContent).contains("<SseKmsEncryptedObjects>");
        assertThat(xmlContent).contains("<Status>Enabled</Status>");
        assertThat(xmlContent).contains("<EncryptionConfiguration>");
        assertThat(xmlContent).contains("<ReplicaKmsKeyID>c4d49f85-ee30-426b-a5ed-95e9139d****</ReplicaKmsKeyID>");
    }
}