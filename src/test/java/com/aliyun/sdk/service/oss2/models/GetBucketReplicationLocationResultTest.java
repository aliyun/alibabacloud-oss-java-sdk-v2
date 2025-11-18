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

public class GetBucketReplicationLocationResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketReplicationLocationResult result = GetBucketReplicationLocationResult.newBuilder().build();
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
        LocationTransferType locationTransferType1 = LocationTransferType.newBuilder()
                .location("oss-cn-hongkong")
                .transferTypes(TransferTypes.newBuilder().types(Arrays.asList("oss_acc")).build())
                .build();

        LocationTransferType locationTransferType2 = LocationTransferType.newBuilder()
                .location("oss-us-west-1")
                .transferTypes(TransferTypes.newBuilder().types(Arrays.asList("oss_acc")).build())
                .build();

        LocationTransferTypeConstraint locationTransferTypeConstraint = LocationTransferTypeConstraint.newBuilder()
                .locationTransferTypes(Arrays.asList(locationTransferType1, locationTransferType2))
                .build();

        ReplicationLocation replicationLocation = ReplicationLocation.newBuilder()
                .locations(Arrays.asList("oss-cn-beijing", "oss-cn-qingdao", "oss-cn-shenzhen", "oss-cn-hongkong", "oss-us-west-1"))
                .locationTransferTypeConstraint(locationTransferTypeConstraint)
                .build();

        GetBucketReplicationLocationResult result = GetBucketReplicationLocationResult.newBuilder()
                .headers(headers)
                .innerBody(replicationLocation)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        ReplicationLocation resultReplicationLocation = result.replicationLocation();
        assertThat(resultReplicationLocation).isNotNull();
        assertThat(resultReplicationLocation.locations()).containsExactly(
                "oss-cn-beijing", "oss-cn-qingdao", "oss-cn-shenzhen", "oss-cn-hongkong", "oss-us-west-1");

        // Check LocationTransferTypeConstraint
        LocationTransferTypeConstraint resultLocationTransferTypeConstraint = resultReplicationLocation.locationTransferTypeConstraint();
        assertThat(resultLocationTransferTypeConstraint).isNotNull();
        assertThat(resultLocationTransferTypeConstraint.locationTransferTypes()).hasSize(2);

        LocationTransferType firstLocationTransferType = resultLocationTransferTypeConstraint.locationTransferTypes().get(0);
        assertThat(firstLocationTransferType.location()).isEqualTo("oss-cn-hongkong");
        assertThat(firstLocationTransferType.transferTypes()).isNotNull();
        assertThat(firstLocationTransferType.transferTypes().types()).containsExactly("oss_acc");

        LocationTransferType secondLocationTransferType = resultLocationTransferTypeConstraint.locationTransferTypes().get(1);
        assertThat(secondLocationTransferType.location()).isEqualTo("oss-us-west-1");
        assertThat(secondLocationTransferType.transferTypes()).isNotNull();
        assertThat(secondLocationTransferType.transferTypes().types()).containsExactly("oss_acc");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        LocationTransferType locationTransferType1 = LocationTransferType.newBuilder()
                .location("oss-cn-hongkong")
                .transferTypes(TransferTypes.newBuilder().types(Arrays.asList("oss_acc")).build())
                .build();

        LocationTransferType locationTransferType2 = LocationTransferType.newBuilder()
                .location("oss-us-west-1")
                .transferTypes(TransferTypes.newBuilder().types(Arrays.asList("oss_acc")).build())
                .build();

        LocationTransferTypeConstraint locationTransferTypeConstraint = LocationTransferTypeConstraint.newBuilder()
                .locationTransferTypes(Arrays.asList(locationTransferType1, locationTransferType2))
                .build();

        ReplicationLocation replicationLocation = ReplicationLocation.newBuilder()
                .locations(Arrays.asList("oss-cn-beijing", "oss-cn-qingdao", "oss-cn-shenzhen", "oss-cn-hongkong", "oss-us-west-1"))
                .locationTransferTypeConstraint(locationTransferTypeConstraint)
                .build();

        GetBucketReplicationLocationResult original = GetBucketReplicationLocationResult.newBuilder()
                .headers(headers)
                .innerBody(replicationLocation)
                .build();

        GetBucketReplicationLocationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        ReplicationLocation resultReplicationLocation = copy.replicationLocation();
        assertThat(resultReplicationLocation).isNotNull();
        assertThat(resultReplicationLocation.locations()).containsExactly(
                "oss-cn-beijing", "oss-cn-qingdao", "oss-cn-shenzhen", "oss-cn-hongkong", "oss-us-west-1");

        // Check LocationTransferTypeConstraint
        LocationTransferTypeConstraint resultLocationTransferTypeConstraint = resultReplicationLocation.locationTransferTypeConstraint();
        assertThat(resultLocationTransferTypeConstraint).isNotNull();
        assertThat(resultLocationTransferTypeConstraint.locationTransferTypes()).hasSize(2);

        LocationTransferType firstLocationTransferType = resultLocationTransferTypeConstraint.locationTransferTypes().get(0);
        assertThat(firstLocationTransferType.location()).isEqualTo("oss-cn-hongkong");
        assertThat(firstLocationTransferType.transferTypes()).isNotNull();
        assertThat(firstLocationTransferType.transferTypes().types()).containsExactly("oss_acc");

        LocationTransferType secondLocationTransferType = resultLocationTransferTypeConstraint.locationTransferTypes().get(1);
        assertThat(secondLocationTransferType.location()).isEqualTo("oss-us-west-1");
        assertThat(secondLocationTransferType.transferTypes()).isNotNull();
        assertThat(secondLocationTransferType.transferTypes().types()).containsExactly("oss_acc");
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String xml =
                "<ReplicationLocation>\n" +
                "  <Location>oss-cn-beijing</Location>\n" +
                "  <Location>oss-cn-qingdao</Location>\n" +
                "  <Location>oss-cn-shenzhen</Location>\n" +
                "  <Location>oss-cn-hongkong</Location>\n" +
                "  <Location>oss-us-west-1</Location>\n" +
                "  <LocationTransferTypeConstraint>\n" +
                "    <LocationTransferType>\n" +
                "      <Location>oss-cn-hongkong</Location>\n" +
                "        <TransferTypes>\n" +
                "          <Type>oss_acc</Type>\n" +
                "        </TransferTypes>\n" +
                "      </LocationTransferType>\n" +
                "      <LocationTransferType>\n" +
                "        <Location>oss-us-west-1</Location>\n" +
                "        <TransferTypes>\n" +
                "          <Type>oss_acc</Type>\n" +
                "        </TransferTypes>\n" +
                "      </LocationTransferType>\n" +
                "    </LocationTransferTypeConstraint>\n" +
                "</ReplicationLocation>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketReplicationLocationResult result = SerdeBucketReplication.toGetBucketReplicationLocation(output);

        ReplicationLocation replicationLocation = result.replicationLocation();
        assertThat(replicationLocation).isNotNull();
        assertThat(replicationLocation.locations()).containsExactly(
                "oss-cn-beijing", "oss-cn-qingdao", "oss-cn-shenzhen", "oss-cn-hongkong", "oss-us-west-1");

        // Check LocationTransferTypeConstraint
        LocationTransferTypeConstraint locationTransferTypeConstraint = replicationLocation.locationTransferTypeConstraint();
        assertThat(locationTransferTypeConstraint).isNotNull();
        assertThat(locationTransferTypeConstraint.locationTransferTypes()).hasSize(2);

        LocationTransferType firstLocationTransferType = locationTransferTypeConstraint.locationTransferTypes().get(0);
        assertThat(firstLocationTransferType.location()).isEqualTo("oss-cn-hongkong");
        assertThat(firstLocationTransferType.transferTypes()).isNotNull();
        assertThat(firstLocationTransferType.transferTypes().types()).containsExactly("oss_acc");

        LocationTransferType secondLocationTransferType = locationTransferTypeConstraint.locationTransferTypes().get(1);
        assertThat(secondLocationTransferType.location()).isEqualTo("oss-us-west-1");
        assertThat(secondLocationTransferType.transferTypes()).isNotNull();
        assertThat(secondLocationTransferType.transferTypes().types()).containsExactly("oss_acc");
    }
}