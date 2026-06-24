package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class GetDatasetRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetDatasetRequest request = GetDatasetRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.datasetName()).isNull();
        assertThat(request.withStatistics()).isNull();
    }

    @Test
    public void testFullBuilder() {
        GetDatasetRequest request = GetDatasetRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("my-dataset")
                .withStatistics(true)
                .header("x-custom-header", "value1")
                .parameter("extra-param", "extra-value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.datasetName()).isEqualTo("my-dataset");
        assertThat(request.withStatistics()).isTrue();
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("extra-param", "extra-value"));

        GetDatasetRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.datasetName()).isEqualTo("my-dataset");
        assertThat(copy.withStatistics()).isTrue();
    }

    @Test
    public void testToBuilderPreserveState() {
        GetDatasetRequest original = GetDatasetRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .build();

        GetDatasetRequest copy = original.toBuilder()
                .withStatistics(true)
                .build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
        assertThat(copy.withStatistics()).isTrue();
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §6 GetDataset URL params
        GetDatasetRequest request = GetDatasetRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("photos-2026")
                .withStatistics(true)
                .build();

        OperationInput input = SerdeDatasetBasic.fromGetDataset(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("getDataset");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("datasetName")).isEqualTo("photos-2026");
        assertThat(input.parameters().get("withStatistics")).isEqualTo("true");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.body().isPresent()).isFalse();
    }
}
