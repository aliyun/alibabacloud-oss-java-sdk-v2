package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateSmartClusterRequestTest {

    @Test
    public void testEmptyBuilder() {
        CreateSmartClusterRequest request = CreateSmartClusterRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.datasetName()).isNull();
        assertThat(request.name()).isNull();
        assertThat(request.clusterType()).isNull();
        assertThat(request.description()).isNull();
        assertThat(request.rules()).isNull();
        assertThat(request.notification()).isNull();
    }

    @Test
    public void testFullBuilder() {
        SmartClusterRule rule = SmartClusterRule.newBuilder()
                .ruleType("Keyword")
                .build();

        CreateSmartClusterRequest request = CreateSmartClusterRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("my-dataset")
                .name("my-cluster")
                .clusterType("keyword")
                .description("test cluster")
                .rules(Collections.singletonList(rule))
                .notification("{\"Topic\":\"arn:acs:...\"}")
                .header("x-custom-header", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.datasetName()).isEqualTo("my-dataset");
        assertThat(request.name()).isEqualTo("my-cluster");
        assertThat(request.clusterType()).isEqualTo("keyword");
        assertThat(request.description()).isEqualTo("test cluster");
        assertThat(request.rules()).isNotNull();
        assertThat(request.notification()).isNotNull();
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));

        CreateSmartClusterRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.datasetName()).isEqualTo("my-dataset");
        assertThat(copy.name()).isEqualTo("my-cluster");
    }

    @Test
    public void testToBuilderPreserveState() {
        CreateSmartClusterRequest original = CreateSmartClusterRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .name("cluster-1")
                .clusterType("keyword")
                .build();

        CreateSmartClusterRequest copy = original.toBuilder()
                .description("updated")
                .build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
        assertThat(copy.name()).isEqualTo("cluster-1");
        assertThat(copy.clusterType()).isEqualTo("keyword");
        assertThat(copy.description()).isEqualTo("updated");
    }

    @Test
    public void testWithSingleRule() {
        SmartClusterRule rule = SmartClusterRule.newBuilder()
                .ruleType("Keyword")
                .keywords(Collections.singletonList("test"))
                .build();

        CreateSmartClusterRequest request = CreateSmartClusterRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .name("cluster-1")
                .clusterType("keyword")
                .build();

    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §13 CreateSmartCluster URL params
        SmartClusterRule rule = SmartClusterRule.newBuilder()
                .ruleType("face")
                .baseURIs(Collections.singletonList("oss://examplebucket/refs/face1.jpg"))
                .sensitivity((float)0.7)
                .build();

        CreateSmartClusterRequest request = CreateSmartClusterRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("photos-2026")
                .name("face-cluster-alice")
                .clusterType("figure")
                .description("Face cluster for alice")
                .rules(Collections.singletonList(rule))
                .build();

        OperationInput input = SerdeDatasetBasic.fromCreateSmartCluster(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("createSmartCluster");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("datasetName")).isEqualTo("photos-2026");
        assertThat(input.parameters().get("name")).isEqualTo("face-cluster-alice");
        assertThat(input.parameters().get("clusterType")).isEqualTo("figure");
        assertThat(input.parameters().get("description")).isEqualTo("Face cluster for alice");
        assertThat(input.parameters().get("rules")).isNotNull();
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.body().isPresent()).isFalse();
    }

    @Test
    public void xmlBuilderKeywordsCluster() {
        // Reference: sdk_internal_reference(3).md §13 CreateSmartCluster with keywords rule
        SmartClusterRule rule = SmartClusterRule.newBuilder()
                .ruleType("keywords")
                .keywords(java.util.Arrays.asList("人物", "车辆"))
                .build();

        CreateSmartClusterRequest request = CreateSmartClusterRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("photos-2026")
                .name("keyword-cluster-1")
                .clusterType("knowledge")
                .build();

        OperationInput input = SerdeDatasetBasic.fromCreateSmartCluster(request);

        assertThat(input.parameters().get("action")).isEqualTo("createSmartCluster");
        assertThat(input.parameters().get("clusterType")).isEqualTo("knowledge");
        assertThat(input.method()).isEqualTo("POST");
    }
}
