package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetSmartClusterResultTest {

    @Test
    public void testEmptyBuilder() {
        GetSmartClusterResult result = GetSmartClusterResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.smartCluster()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Length", "512"
        );

        GetSmartClusterResult result = GetSmartClusterResult.newBuilder()
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

        GetSmartClusterResult original = GetSmartClusterResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetSmartClusterResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §14 GetSmartCluster response
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<GetSmartClusterResponse>"
                + "  <SmartCluster>"
                + "    <ObjectId>cluster-abc123def456</ObjectId>"
                + "    <ClusterType>figure</ClusterType>"
                + "    <Name>face-cluster-alice</Name>"
                + "    <Description>Face cluster for alice</Description>"
                + "    <Rule>"
                + "    </Rule>"
                + "    <Rules>"
                + "      <Rule>"
                + "        <RuleType>face</RuleType>"
                + "        <BaseURIs>oss://examplebucket/refs/alice1.jpg</BaseURIs>"
                + "        <BaseURIs>oss://examplebucket/refs/alice2.jpg</BaseURIs>"
                + "        <Keywords>人物</Keywords>"
                + "        <Keywords>车辆</Keywords>"
                + "        <Sensitivity>0.7</Sensitivity>"
                + "      </Rule>"
                + "    </Rules>"
                + "    <Reason></Reason>"
                + "    <Notification>"
                + "      <MNS><TopicName>imm-cluster-notification</TopicName></MNS>"
                + "    </Notification>"
                + "    <CreateTime>2026-05-20T11:00:00.000+08:00</CreateTime>"
                + "    <UpdateTime>2026-05-20T11:08:00.000+08:00</UpdateTime>"
                + "  </SmartCluster>"
                + "</GetSmartClusterResponse>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .headers(MapUtils.of("x-oss-request-id", "req-get-smartcluster"))
                .build();

        GetSmartClusterResult result = SerdeDatasetBasic.toGetSmartCluster(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-get-smartcluster");
        assertThat(result.smartCluster()).isNotNull();

        SmartClusterInfo sc = result.smartCluster();
        assertThat(sc.objectId()).isEqualTo("cluster-abc123def456");
        assertThat(sc.clusterType()).isEqualTo("figure");
        assertThat(sc.name()).isEqualTo("face-cluster-alice");
        assertThat(sc.description()).isEqualTo("Face cluster for alice");
        assertThat(sc.createTime()).isEqualTo("2026-05-20T11:00:00.000+08:00");
        assertThat(sc.updateTime()).isEqualTo("2026-05-20T11:08:00.000+08:00");

        // Rules
        assertThat(sc.rules()).isNotNull();
        assertThat(sc.rules()).hasSize(1);
        assertThat(sc.rules().get(0).ruleType()).isEqualTo("face");
        assertThat(sc.rules().get(0).baseURIs()).containsExactly("oss://examplebucket/refs/alice1.jpg", "oss://examplebucket/refs/alice2.jpg");
        assertThat(sc.rules().get(0).keywords()).containsExactly("人物", "车辆");
        assertThat(sc.rules().get(0).sensitivity()).isEqualTo(0.7f);

        // Reason (empty in success response)
        assertThat(sc.reason()).isNullOrEmpty();

        // Notification
        assertThat(sc.notification()).isNotNull();
        assertThat(sc.notification().mns()).isNotNull();
        assertThat(sc.notification().mns().topicName()).isEqualTo("imm-cluster-notification");
    }

    @Test
    public void xmlBuilderKeywordCluster() {
        // Keywords-type cluster
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<GetSmartClusterResponse>"
                + "  <SmartCluster>"
                + "    <ObjectId>cluster-keywords-001</ObjectId>"
                + "    <ClusterType>knowledge</ClusterType>"
                + "    <Name>keyword-cluster-1</Name>"
                + "    <Rules>"
                + "      <Rule>"
                + "        <RuleType>keywords</RuleType>"
                + "        <Keywords>人物</Keywords>"
                + "        <Keywords>车辆</Keywords>"
                + "      </Rule>"
                + "    </Rules>"
                + "  </SmartCluster>"
                + "</GetSmartClusterResponse>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetSmartClusterResult result = SerdeDatasetBasic.toGetSmartCluster(output);

        assertThat(result.smartCluster()).isNotNull();
        assertThat(result.smartCluster().clusterType()).isEqualTo("knowledge");
        assertThat(result.smartCluster().rules()).hasSize(1);
        assertThat(result.smartCluster().rules().get(0).ruleType()).isEqualTo("keywords");
        assertThat(result.smartCluster().rules().get(0).keywords()).containsExactly("人物", "车辆");
    }
}
