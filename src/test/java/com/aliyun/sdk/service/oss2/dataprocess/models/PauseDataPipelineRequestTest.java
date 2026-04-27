package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDataPipelineBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class PauseDataPipelineRequestTest {

    @Test
    public void testEmptyBuilder() {
        PauseDataPipelineRequest request = PauseDataPipelineRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.dataPipelineName()).isNull();
    }

    @Test
    public void testFullBuilder() {
        PauseDataPipelineRequest request = PauseDataPipelineRequest.newBuilder()
                .dataPipelineName("test-pipeline")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.dataPipelineName()).isEqualTo("test-pipeline");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        PauseDataPipelineRequest copy = request.toBuilder().build();
        assertThat(copy.dataPipelineName()).isEqualTo("test-pipeline");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        PauseDataPipelineRequest original = PauseDataPipelineRequest.newBuilder()
                .dataPipelineName("test-pipeline")
                .build();

        PauseDataPipelineRequest copy = original.toBuilder().build();

        assertThat(copy.dataPipelineName()).isEqualTo("test-pipeline");
    }

    @Test
    public void testHeaderProperties() {
        PauseDataPipelineRequest request = PauseDataPipelineRequest.newBuilder()
                .dataPipelineName("header-pipeline")
                .build();

        assertThat(request.dataPipelineName()).isEqualTo("header-pipeline");
    }

    @Test
    public void xmlBuilder() {
        PauseDataPipelineRequest request = PauseDataPipelineRequest.newBuilder()
                .dataPipelineName("my-data-pipeline")
                .build();

        OperationInput input = SerdeDataPipelineBasic.fromPauseDataPipeline(request);

        assertThat(input.parameters().get("dataPipelineName")).isEqualTo("my-data-pipeline");
        assertThat(input.parameters().get("action")).isEqualTo("pauseDataPipeline");
        assertThat(input.body().isPresent()).isFalse();
    }
}
