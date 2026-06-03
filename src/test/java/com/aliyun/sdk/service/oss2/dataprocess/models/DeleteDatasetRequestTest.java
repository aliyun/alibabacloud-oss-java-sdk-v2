package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteDatasetRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteDatasetRequest request = DeleteDatasetRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.datasetName()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteDatasetRequest request = DeleteDatasetRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("my-dataset")
                .header("x-custom-header", "value1")
                .parameter("extra-param", "extra-value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.datasetName()).isEqualTo("my-dataset");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("extra-param", "extra-value"));

        DeleteDatasetRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.datasetName()).isEqualTo("my-dataset");
    }

    @Test
    public void testToBuilderPreserveState() {
        DeleteDatasetRequest original = DeleteDatasetRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .build();

        DeleteDatasetRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §8 DeleteDataset URL params
        DeleteDatasetRequest request = DeleteDatasetRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("photos-2026")
                .build();

        OperationInput input = SerdeDatasetBasic.fromDeleteDataset(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("deleteDataset");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("datasetName")).isEqualTo("photos-2026");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.body().isPresent()).isFalse();
    }
}
