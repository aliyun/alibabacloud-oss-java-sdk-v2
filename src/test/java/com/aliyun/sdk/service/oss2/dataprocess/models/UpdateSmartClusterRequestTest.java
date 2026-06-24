package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateSmartClusterRequestTest {

    @Test
    public void testEmptyBuilder() {
        UpdateSmartClusterRequest request = UpdateSmartClusterRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.datasetName()).isNull();
        assertThat(request.objectId()).isNull();
        assertThat(request.name()).isNull();
        assertThat(request.description()).isNull();
        assertThat(request.rules()).isNull();
        assertThat(request.rule()).isNull();
        assertThat(request.notification()).isNull();
    }

    @Test
    public void testFullBuilder() {
        UpdateSmartClusterRequest request = UpdateSmartClusterRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("my-dataset")
                .objectId("obj-abc-123")
                .name("updated-cluster")
                .description("updated description")
                .notification("{\"Topic\":\"arn:acs:...\"}")
                .header("x-custom-header", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.datasetName()).isEqualTo("my-dataset");
        assertThat(request.objectId()).isEqualTo("obj-abc-123");
        assertThat(request.name()).isEqualTo("updated-cluster");
        assertThat(request.description()).isEqualTo("updated description");
        assertThat(request.notification()).isNotNull();
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));

        UpdateSmartClusterRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.datasetName()).isEqualTo("my-dataset");
        assertThat(copy.objectId()).isEqualTo("obj-abc-123");
        assertThat(copy.name()).isEqualTo("updated-cluster");
    }

    @Test
    public void testToBuilderPreserveState() {
        UpdateSmartClusterRequest original = UpdateSmartClusterRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .objectId("obj-1")
                .build();

        UpdateSmartClusterRequest copy = original.toBuilder()
                .description("updated")
                .build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
        assertThat(copy.objectId()).isEqualTo("obj-1");
        assertThat(copy.description()).isEqualTo("updated");
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §15 UpdateSmartCluster URL params
        UpdateSmartClusterRequest request = UpdateSmartClusterRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("photos-2026")
                .objectId("cluster-abc123def456")
                .name("face-cluster-alice-updated")
                .description("Updated face cluster for alice")
                .notification("{\"MNS\":{\"TopicName\":\"imm-cluster-notification\"}}")
                .build();

        OperationInput input = SerdeDatasetBasic.fromUpdateSmartCluster(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("updateSmartCluster");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("datasetName")).isEqualTo("photos-2026");
        assertThat(input.parameters().get("objectId")).isEqualTo("cluster-abc123def456");
        assertThat(input.parameters().get("name")).isEqualTo("face-cluster-alice-updated");
        assertThat(input.parameters().get("description")).isEqualTo("Updated face cluster for alice");
        assertThat(input.parameters().get("notification")).isNotNull();
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.body().isPresent()).isFalse();
    }
}
