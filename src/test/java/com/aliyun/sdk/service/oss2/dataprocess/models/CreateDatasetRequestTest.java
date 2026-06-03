package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateDatasetRequestTest {

    @Test
    public void testEmptyBuilder() {
        CreateDatasetRequest request = CreateDatasetRequest.newBuilder().build();
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
        WorkflowParameter wp = WorkflowParameter.newBuilder()
                .name("ImageInsightEnable")
                .value("True")
                .build();

        CreateDatasetRequest request = CreateDatasetRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("my-dataset")
                .description("test description")
                .workflowParameters(Arrays.asList(wp))
                .datasetConfig(config)
                .header("x-custom-header", "value1")
                .parameter("extra-param", "extra-value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.datasetName()).isEqualTo("my-dataset");
        assertThat(request.description()).isEqualTo("test description");
        assertThat(request.workflowParameters()).isNotNull();
        assertThat(request.datasetConfig()).isNotNull();
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("extra-param", "extra-value"));

        CreateDatasetRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.datasetName()).isEqualTo("my-dataset");
        assertThat(copy.description()).isEqualTo("test description");
    }

    @Test
    public void testToBuilderPreserveState() {
        CreateDatasetRequest original = CreateDatasetRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .build();

        CreateDatasetRequest copy = original.toBuilder()
                .description("updated")
                .build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
        assertThat(copy.description()).isEqualTo("updated");
    }

    @Test
    public void testWithStringParameters() {
        CreateDatasetRequest request = CreateDatasetRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .workflowParameters("[{\"Name\":\"ImageInsightEnable\",\"Value\":\"True\"}]")
                .datasetConfig("{\"Insights\":{\"Language\":\"ch\"}}")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.datasetName()).isEqualTo("test-dataset");
        assertThat(request.workflowParameters()).isEqualTo("[{\"Name\":\"ImageInsightEnable\",\"Value\":\"True\"}]");
        assertThat(request.datasetConfig()).isEqualTo("{\"Insights\":{\"Language\":\"ch\"}}");
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §5 CreateDataset URL params
        WorkflowParameter wp = WorkflowParameter.newBuilder()
                .name("ImageInsightEnable")
                .value("True")
                .build();

        InsightsConfig insightsConfig = InsightsConfig.newBuilder()
                .language("en")
                .build();
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(insightsConfig)
                .build();

        CreateDatasetRequest request = CreateDatasetRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("photos-2026")
                .description("Photo collection for year 2026")
                .workflowParameters(java.util.Arrays.asList(wp))
                .datasetConfig(config)
                .build();

        OperationInput input = SerdeDatasetBasic.fromCreateDataset(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("createDataset");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("datasetName")).isEqualTo("photos-2026");
        assertThat(input.parameters().get("description")).isEqualTo("Photo collection for year 2026");
        assertThat(input.method()).isEqualTo("POST");
    }
}
