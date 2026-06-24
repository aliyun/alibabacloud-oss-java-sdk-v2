package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListSmartClustersResultTest {

    @Test
    public void testEmptyBuilder() {
        ListSmartClustersResult result = ListSmartClustersResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.smartClusters()).isNull();
        assertThat(result.nextToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Length", "512"
        );

        ListSmartClustersResult result = ListSmartClustersResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "Content-Length", "0"
        );

        ListSmartClustersResult original = ListSmartClustersResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListSmartClustersResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §17 ListSmartClusters response
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<ListSmartClustersResponse>"
                + "  <NextToken>page-2-token-xyz</NextToken>"
                + "  <SmartClusters>"
                + "    <SmartCluster>"
                + "      <ObjectId>cluster-abc123def456</ObjectId>"
                + "      <ClusterType>figure</ClusterType>"
                + "      <Name>face-cluster-alice</Name>"
                + "      <Description>Face cluster for alice</Description>"
                + "      <Rules>"
                + "        <Rule>"
                + "          <RuleType>face</RuleType>"
                + "          <BaseURIs>oss://examplebucket/refs/alice.jpg</BaseURIs>"
                + "        </Rule>"
                + "      </Rules>"
                + "      <CreateTime>2026-05-20T11:00:00.000+08:00</CreateTime>"
                + "      <UpdateTime>2026-05-20T11:08:00.000+08:00</UpdateTime>"
                + "    </SmartCluster>"
                + "  </SmartClusters>"
                + "</ListSmartClustersResponse>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListSmartClustersResult result = SerdeDatasetBasic.toListSmartClusters(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.nextToken()).isEqualTo("page-2-token-xyz");
        assertThat(result.smartClusters()).isNotNull();
        assertThat(result.smartClusters()).hasSize(1);
        assertThat(result.smartClusters().get(0).objectId()).isEqualTo("cluster-abc123def456");
        assertThat(result.smartClusters().get(0).clusterType()).isEqualTo("figure");
        assertThat(result.smartClusters().get(0).name()).isEqualTo("face-cluster-alice");
        assertThat(result.smartClusters().get(0).description()).isEqualTo("Face cluster for alice");
        assertThat(result.smartClusters().get(0).rules()).hasSize(1);
        assertThat(result.smartClusters().get(0).rules().get(0).ruleType()).isEqualTo("face");
        assertThat(result.smartClusters().get(0).createTime()).isEqualTo("2026-05-20T11:00:00.000+08:00");
    }
}
