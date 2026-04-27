package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDataPipelineBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;

public class GetDataPipelineConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetDataPipelineConfigurationRequest request = GetDataPipelineConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.dataPipelineName()).isNull();
    }

    @Test
    public void testFullBuilder() {
        GetDataPipelineConfigurationRequest request = GetDataPipelineConfigurationRequest.newBuilder()
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

        GetDataPipelineConfigurationRequest copy = request.toBuilder().build();
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
        GetDataPipelineConfigurationRequest original = GetDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("test-pipeline")
                .build();

        GetDataPipelineConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.dataPipelineName()).isEqualTo("test-pipeline");
    }

    @Test
    public void testHeaderProperties() {
        GetDataPipelineConfigurationRequest request = GetDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("header-pipeline")
                .build();

        assertThat(request.dataPipelineName()).isEqualTo("header-pipeline");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        DataPipelineSourceFilterConfiguration filterConfig = DataPipelineSourceFilterConfiguration.newBuilder()
                .prefixSet(Arrays.asList("prefix1/", "prefix2/prefix3/"))
                .objectMediaTypes(Arrays.asList("text", "image", "video"))
                .build();

        DataPipelineSource source = DataPipelineSource.newBuilder()
                .inputBucket("my-bucket")
                .inputDataScope("All")
                .ignoreDelete(true)
                .filterConfiguration(filterConfig)
                .build();

        DataPipelineEmbeddingConfiguration embeddingConfig = DataPipelineEmbeddingConfiguration.newBuilder()
                .embeddingProvider("bailian")
                .apiKey("xxxx")
                .model("qwen2.5-vl-embedding")
                .fps(1.0f)
                .build();

        DataPipelineDestination destination = DataPipelineDestination.newBuilder()
                .vectorBucketName("my-vector-bucket")
                .vectorIndexNames(Collections.singletonList("my-index"))
                .vectorKeyPrefix("")
                .objectTagToMetadata(Arrays.asList("key1", "key2"))
                .usermetaToMetadata(Collections.singletonList("x-oss-meta-key1"))
                .build();

        DataPipelineError errorConfig = DataPipelineError.newBuilder()
                .errorMode("ignoreAndRecord")
                .errorBucket("my-error-bucket")
                .errorPrefix("error-output/")
                .build();

        GetDataPipelineConfigurationRequest request = GetDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("my-data-pipeline")
                .build();

        OperationInput input = SerdeDataPipelineBasic.fromGetDataPipelineConfiguration(request);

        assertThat(input.parameters().get("dataPipelineName")).isEqualTo("my-data-pipeline");
        assertThat(input.parameters().get("action")).isEqualTo("getDataPipelineConfiguration");
        assertThat(input.body().isPresent()).isFalse();
    }
}
