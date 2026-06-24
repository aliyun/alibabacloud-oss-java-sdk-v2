package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDataPipelineBasic;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteDataPipelineConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteDataPipelineConfigurationRequest request = DeleteDataPipelineConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.dataPipelineName()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteDataPipelineConfigurationRequest request = DeleteDataPipelineConfigurationRequest.newBuilder()
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

        DeleteDataPipelineConfigurationRequest copy = request.toBuilder().build();
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
        DeleteDataPipelineConfigurationRequest original = DeleteDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("test-pipeline")
                .build();

        DeleteDataPipelineConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.dataPipelineName()).isEqualTo("test-pipeline");
    }

    @Test
    public void testHeaderProperties() {
        DeleteDataPipelineConfigurationRequest request = DeleteDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("header-pipeline")
                .build();

        assertThat(request.dataPipelineName()).isEqualTo("header-pipeline");
    }

    @Test
    public void xmlBuilder() {
        DeleteDataPipelineConfigurationRequest request = DeleteDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("my-data-pipeline")
                .build();

        OperationInput input = SerdeDataPipelineBasic.fromDeleteDataPipelineConfiguration(request);

        assertThat(input.parameters().get("dataPipeline")).isEqualTo("");
        assertThat(input.parameters().get("action")).isEqualTo("deleteDataPipelineConfiguration");
        assertThat(input.parameters().get("dataPipelineName")).isEqualTo("my-data-pipeline");
        assertThat(input.body().isPresent()).isFalse();
    }
}
