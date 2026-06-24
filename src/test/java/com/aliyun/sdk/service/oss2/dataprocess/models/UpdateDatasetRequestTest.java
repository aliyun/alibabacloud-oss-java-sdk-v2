package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateDatasetRequestTest {

    @Test
    public void testEmptyBuilder() {
        UpdateDatasetRequest request = UpdateDatasetRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.datasetName()).isNull();
        assertThat(request.description()).isNull();
        assertThat(request.workflowParameters()).isNull();
        assertThat(request.datasetConfig()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DatasetConfig config = DatasetConfig.newBuilder().build();
        UpdateDatasetRequest request = UpdateDatasetRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("my-dataset")
                .description("updated description")
                .datasetConfig(config)
                .header("x-custom-header", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.datasetName()).isEqualTo("my-dataset");
        assertThat(request.description()).isEqualTo("updated description");
        assertThat(request.datasetConfig()).isNotNull();
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));

        UpdateDatasetRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.datasetName()).isEqualTo("my-dataset");
        assertThat(copy.description()).isEqualTo("updated description");
    }

    @Test
    public void testToBuilderPreserveState() {
        UpdateDatasetRequest original = UpdateDatasetRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .build();

        UpdateDatasetRequest copy = original.toBuilder()
                .description("updated")
                .build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
        assertThat(copy.description()).isEqualTo("updated");
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §7 UpdateDataset URL params
        UpdateDatasetRequest request = UpdateDatasetRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("photos-2026")
                .description("Updated photo collection for year 2026")
                .build();

        OperationInput input = SerdeDatasetBasic.fromUpdateDataset(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("updateDataset");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("datasetName")).isEqualTo("photos-2026");
        assertThat(input.parameters().get("description")).isEqualTo("Updated photo collection for year 2026");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.body().isPresent()).isFalse();
    }
}
