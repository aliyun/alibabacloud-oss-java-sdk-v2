package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteSmartClusterRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteSmartClusterRequest request = DeleteSmartClusterRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.datasetName()).isNull();
        assertThat(request.objectId()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteSmartClusterRequest request = DeleteSmartClusterRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("my-dataset")
                .objectId("obj-abc-123")
                .header("x-custom-header", "value1")
                .parameter("extra-param", "extra-value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.datasetName()).isEqualTo("my-dataset");
        assertThat(request.objectId()).isEqualTo("obj-abc-123");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("extra-param", "extra-value"));

        DeleteSmartClusterRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.datasetName()).isEqualTo("my-dataset");
        assertThat(copy.objectId()).isEqualTo("obj-abc-123");
    }

    @Test
    public void testToBuilderPreserveState() {
        DeleteSmartClusterRequest original = DeleteSmartClusterRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .objectId("obj-1")
                .build();

        DeleteSmartClusterRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
        assertThat(copy.objectId()).isEqualTo("obj-1");
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §16 DeleteSmartCluster URL params
        DeleteSmartClusterRequest request = DeleteSmartClusterRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("photos-2026")
                .objectId("cluster-abc123def456")
                .build();

        OperationInput input = SerdeDatasetBasic.fromDeleteSmartCluster(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("deleteSmartCluster");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("datasetName")).isEqualTo("photos-2026");
        assertThat(input.parameters().get("objectId")).isEqualTo("cluster-abc123def456");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.body().isPresent()).isFalse();
    }
}
