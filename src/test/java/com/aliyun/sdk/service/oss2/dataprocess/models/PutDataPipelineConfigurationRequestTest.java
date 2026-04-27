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

public class PutDataPipelineConfigurationRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutDataPipelineConfigurationRequest request = PutDataPipelineConfigurationRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.dataPipelineName()).isNull();
        assertThat(request.role()).isNull();
        assertThat(request.putDataPipelineConfigurationConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
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

        PutDataPipelineConfigurationConfiguration config = PutDataPipelineConfigurationConfiguration.newBuilder()
                .dataPipelineDescription("使用百炼多模态模型为业务数据向量化")
                .sources(Collections.singletonList(source))
                .dataPipelineEmbeddingConfiguration(embeddingConfig)
                .destination(destination)
                .dataPipelineError(errorConfig)
                .build();

        PutDataPipelineConfigurationRequest request = PutDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("test-pipeline")
                .role("test-role")
                .putDataPipelineConfigurationConfiguration(config)
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.dataPipelineName()).isEqualTo("test-pipeline");
        assertThat(request.role()).isEqualTo("test-role");
        assertThat(request.putDataPipelineConfigurationConfiguration()).isEqualTo(config);
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        PutDataPipelineConfigurationRequest copy = request.toBuilder().build();
        assertThat(copy.dataPipelineName()).isEqualTo("test-pipeline");
        assertThat(copy.role()).isEqualTo("test-role");
        assertThat(copy.putDataPipelineConfigurationConfiguration()).isEqualTo(config);
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        DataPipelineSource source = DataPipelineSource.newBuilder()
                .inputBucket("test-bucket")
                .inputDataScope("All")
                .build();

        PutDataPipelineConfigurationConfiguration config = PutDataPipelineConfigurationConfiguration.newBuilder()
                .dataPipelineDescription("test description")
                .sources(Collections.singletonList(source))
                .build();

        PutDataPipelineConfigurationRequest original = PutDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("test-pipeline")
                .role("test-role")
                .putDataPipelineConfigurationConfiguration(config)
                .build();

        PutDataPipelineConfigurationRequest copy = original.toBuilder().build();

        assertThat(copy.dataPipelineName()).isEqualTo("test-pipeline");
        assertThat(copy.role()).isEqualTo("test-role");
        assertThat(copy.putDataPipelineConfigurationConfiguration()).isEqualTo(config);
    }

    @Test
    public void testHeaderProperties() {
        DataPipelineSource source = DataPipelineSource.newBuilder()
                .inputBucket("pipeline-bucket")
                .inputDataScope("All")
                .build();

        PutDataPipelineConfigurationConfiguration config = PutDataPipelineConfigurationConfiguration.newBuilder()
                .dataPipelineDescription("test")
                .sources(Collections.singletonList(source))
                .build();

        PutDataPipelineConfigurationRequest request = PutDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("header-pipeline")
                .role("header-role")
                .putDataPipelineConfigurationConfiguration(config)
                .build();

        assertThat(request.dataPipelineName()).isEqualTo("header-pipeline");
        assertThat(request.role()).isEqualTo("header-role");
        assertThat(request.putDataPipelineConfigurationConfiguration().dataPipelineDescription()).isEqualTo("test");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<DataPipelineConfiguration>\n" +
                "  <DataPipelineDescription>使用百炼多模态模型为业务数据向量化</DataPipelineDescription>\n" +
                "  <Sources>\n" +
                "      <InputBucket>my-bucket</InputBucket>\n" +
                "      <InputDataScope>All</InputDataScope>\n" +
                "      <IgnoreDelete>true</IgnoreDelete>\n" +
                "      <FilterConfiguration>\n" +
                "          <PrefixSet>prefix1/</PrefixSet>\n" +
                "          <PrefixSet>prefix2/prefix3/</PrefixSet>\n" +
                "          <ObjectMediaTypes>text</ObjectMediaTypes>\n" +
                "          <ObjectMediaTypes>image</ObjectMediaTypes>\n" +
                "          <ObjectMediaTypes>video</ObjectMediaTypes>\n" +
                "      </FilterConfiguration>\n" +
                "  </Sources>\n" +
                "  <DataPipelineEmbeddingConfiguration>\n" +
                "      <EmbeddingProvider>bailian</EmbeddingProvider>\n" +
                "      <ApiKey>xxxx</ApiKey>\n" +
                "      <Model>qwen2.5-vl-embedding</Model>\n" +
                "      <FPS>1</FPS>\n" +
                "  </DataPipelineEmbeddingConfiguration>\n" +
                "  <Destination>\n" +
                "      <VectorBucketName>my-vector-bucket</VectorBucketName>\n" +
                "      <VectorIndexNames>my-index</VectorIndexNames>\n" +
                "      <VectorKeyPrefix></VectorKeyPrefix>\n" +
                "      <ObjectTagToMetadata>key1</ObjectTagToMetadata>\n" +
                "      <ObjectTagToMetadata>key2</ObjectTagToMetadata>\n" +
                "      <UsermetaToMetadata>x-oss-meta-key1</UsermetaToMetadata>\n" +
                "  </Destination>\n" +
                "  <DataPipelineError>\n" +
                "      <ErrorMode>ignoreAndRecord</ErrorMode>\n" +
                "      <ErrorBucket>my-error-bucket</ErrorBucket>\n" +
                "      <ErrorPrefix>error-output/</ErrorPrefix>\n" +
                "  </DataPipelineError>\n" +
                "</DataPipelineConfiguration>";
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        PutDataPipelineConfigurationConfiguration xmlConfiguration = xmlMapper.readValue(xml, PutDataPipelineConfigurationConfiguration.class);
        String expectedXml = xmlMapper.writeValueAsString(xmlConfiguration);

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

        PutDataPipelineConfigurationConfiguration config = PutDataPipelineConfigurationConfiguration.newBuilder()
                .dataPipelineDescription("使用百炼多模态模型为业务数据向量化")
                .sources(Collections.singletonList(source))
                .dataPipelineEmbeddingConfiguration(embeddingConfig)
                .destination(destination)
                .dataPipelineError(errorConfig)
                .build();

        PutDataPipelineConfigurationRequest request = PutDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("xml-pipeline")
                .role("xml-role")
                .putDataPipelineConfigurationConfiguration(config)
                .build();

        OperationInput input = SerdeDataPipelineBasic.fromPutDataPipelineConfiguration(request);

        assertThat(input.parameters().get("dataPipelineName")).isEqualTo("xml-pipeline");
        assertThat(input.parameters().get("role")).isEqualTo("xml-role");
        assertThat(input.parameters().get("action")).isEqualTo("putDataPipelineConfiguration");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<DataPipelineConfiguration>");
        assertThat(xmlContent).contains("<DataPipelineDescription>使用百炼多模态模型为业务数据向量化</DataPipelineDescription>");
        assertThat(xmlContent).contains("<InputBucket>my-bucket</InputBucket>");
        assertThat(xmlContent).contains("<EmbeddingProvider>bailian</EmbeddingProvider>");
        assertThat(xmlContent).contains("<VectorBucketName>my-vector-bucket</VectorBucketName>");
        assertThat(xmlContent).contains("<ErrorMode>ignoreAndRecord</ErrorMode>");

        assertThat(xmlContent).isEqualTo(expectedXml);
    }
}
