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

    @Test
    public void xmlBuilderV2DocumentExample() throws JsonProcessingException {
        PutDataPipelineConfigurationRequest request = PutDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("media-pipeline")
                .role("acs:ram::1234567890123456:role/AliyunOSSDataPipelineRole")
                .putDataPipelineConfigurationConfiguration(v2Configuration())
                .build();

        OperationInput input = SerdeDataPipelineBasic.fromPutDataPipelineConfiguration(request);
        String xml = new String(input.body().get().toBytes(), StandardCharsets.UTF_8);

        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.parameters().get("dataPipeline")).isEmpty();
        assertThat(input.parameters().get("dataPipelineName")).isEqualTo("media-pipeline");
        assertThat(input.parameters().get("role")).isEqualTo("acs:ram::1234567890123456:role/AliyunOSSDataPipelineRole");
        assertThat(input.parameters().get("action")).isEqualTo("putDataPipelineConfiguration");
        assertThat(xml).isEqualTo(canonicalizePutXml(v2RequestExampleXml()));
        assertThat(xml).doesNotContain("<IgnoreDelete>");
        assertThat(xml).doesNotContain("<DataPipelineEmbeddingConfiguration>");
    }

    @Test
    public void xmlBuilderV2DhashSnapshotAndOptionalFields() throws JsonProcessingException {
        DataPipelineInsightsSnapshot snapshot = DataPipelineInsightsSnapshot.newBuilder()
                .mode("dhash")
                .number(20)
                .build();
        PutDataPipelineConfigurationConfiguration configuration =
                PutDataPipelineConfigurationConfiguration.newBuilder()
                        .sources(Collections.singletonList(DataPipelineSource.newBuilder()
                                .inputBucket("source-bucket")
                                .filterConfiguration(DataPipelineSourceFilterConfiguration.newBuilder()
                                        .objectMediaTypes(Collections.singletonList("video"))
                                        .build())
                                .build()))
                        .dataPipelineDataProcessConfiguration(DataPipelineDataProcessConfiguration.newBuilder()
                                .searchMode("fast")
                                .insights(DataPipelineInsights.newBuilder()
                                        .video(DataPipelineInsightsVideo.newBuilder()
                                                .frameEmbedding(DataPipelineInsightsFrameEmbedding.newBuilder()
                                                        .snapshot(snapshot)
                                                        .build())
                                                .build())
                                        .build())
                                .build())
                        .destination(DataPipelineDestination.newBuilder()
                                .videoFrameEmbedding(DataPipelineDestinationVideoFrameEmbedding.newBuilder()
                                        .bucket("vector-bucket")
                                        .indexName("video-frame")
                                        .build())
                                .build())
                        .build();
        PutDataPipelineConfigurationRequest request = PutDataPipelineConfigurationRequest.newBuilder()
                .dataPipelineName("fast-video-pipeline")
                .role("acs:ram::1234567890123456:role/AliyunOSSDataPipelineRole")
                .putDataPipelineConfigurationConfiguration(configuration)
                .build();

        OperationInput input = SerdeDataPipelineBasic.fromPutDataPipelineConfiguration(request);
        String xml = new String(input.body().get().toBytes(), StandardCharsets.UTF_8);

        assertThat(xml).isEqualTo(canonicalizePutXml(dhashRequestExampleXml()));
        assertThat(xml).contains("<SearchMode>fast</SearchMode>");
        assertThat(xml).contains("<Snapshot><Mode>dhash</Mode><Number>20</Number></Snapshot>");
        assertThat(xml).doesNotContain("<Interval>", "<Prefix>", "<ModelTier>", "<Caption>");
    }

    @Test
    public void testV2ModelsToBuilderPreserveState() {
        PutDataPipelineConfigurationConfiguration copy = v2Configuration().toBuilder().build();

        assertThat(copy.modelTier()).isEqualTo("standard");
        assertThat(copy.dataPipelineDataProcessConfiguration().searchMode()).isEqualTo("balanced");
        assertThat(copy.dataPipelineDataProcessConfiguration().insights().image())
                .isInstanceOf(DataPipelineInsightsImage.class);
        assertThat(copy.dataPipelineDataProcessConfiguration().insights().image()
                .caption()).isInstanceOf(DataPipelineInsightsCaption.class);
        assertThat(copy.dataPipelineDataProcessConfiguration().insights().image()
                .caption().prompt()).isEqualTo("Describe the image.");
        assertThat(copy.dataPipelineDataProcessConfiguration().insights().video()
                .caption().prompt()).isEqualTo("Describe each video scene.");
        assertThat(copy.dataPipelineDataProcessConfiguration().insights().video())
                .isInstanceOf(DataPipelineInsightsVideo.class);
        assertThat(copy.dataPipelineDataProcessConfiguration().insights().video()
                .frameEmbedding()).isInstanceOf(DataPipelineInsightsFrameEmbedding.class);
        assertThat(copy.dataPipelineDataProcessConfiguration().insights().video()
                .frameEmbedding().snapshot()).isInstanceOf(DataPipelineInsightsSnapshot.class);
        assertThat(copy.dataPipelineDataProcessConfiguration().insights().video()
                .frameEmbedding().snapshot().interval()).isEqualTo(1.0d);
        assertThat(copy.destination().imageEmbedding())
                .isInstanceOf(DataPipelineDestinationImageEmbedding.class);
        assertThat(copy.destination().imageTextEmbedding())
                .isInstanceOf(DataPipelineDestinationImageTextEmbedding.class);
        assertThat(copy.destination().videoFrameEmbedding())
                .isInstanceOf(DataPipelineDestinationVideoFrameEmbedding.class);
        assertThat(copy.destination().videoTextEmbedding())
                .isInstanceOf(DataPipelineDestinationVideoTextEmbedding.class);
        assertThat(copy.destination().documentChunkEmbedding())
                .isInstanceOf(DataPipelineDestinationDocumentChunkEmbedding.class);
        assertThat(copy.destination().videoTextEmbedding().indexName()).isEqualTo("video-text");
        assertThat(copy.sources().get(0).ignoreDelete()).isNull();

        DataPipelineSource source = copy.sources().get(0).toBuilder().ignoreDelete(false).build();
        assertThat(source.ignoreDelete()).isFalse();

        DataPipelineInsightsSnapshot dhash = DataPipelineInsightsSnapshot.newBuilder()
                .mode("dhash")
                .number(20)
                .build()
                .toBuilder()
                .build();
        assertThat(dhash.mode()).isEqualTo("dhash");
        assertThat(dhash.number()).isEqualTo(20);
        assertThat(dhash.interval()).isNull();
    }

    private static PutDataPipelineConfigurationConfiguration v2Configuration() {
        DataPipelineInsights insights = DataPipelineInsights.newBuilder()
                .image(DataPipelineInsightsImage.newBuilder()
                        .caption(DataPipelineInsightsCaption.newBuilder()
                                .prompt("Describe the image.")
                                .build())
                        .build())
                .video(DataPipelineInsightsVideo.newBuilder()
                        .caption(DataPipelineInsightsCaption.newBuilder()
                                .prompt("Describe each video scene.")
                                .build())
                        .frameEmbedding(DataPipelineInsightsFrameEmbedding.newBuilder()
                                .snapshot(DataPipelineInsightsSnapshot.newBuilder()
                                        .mode("interval")
                                        .interval(1.0d)
                                        .build())
                                .build())
                        .build())
                .build();
        DataPipelineSource source = DataPipelineSource.newBuilder()
                .inputBucket("source-bucket")
                .inputDataScope("All")
                .filterConfiguration(DataPipelineSourceFilterConfiguration.newBuilder()
                        .prefixSet(Collections.singletonList("media/"))
                        .objectMediaTypes(Arrays.asList("image", "video", "text"))
                        .build())
                .build();

        return PutDataPipelineConfigurationConfiguration.newBuilder()
                .dataPipelineDescription("多媒体语义向量")
                .sources(Collections.singletonList(source))
                .modelTier("standard")
                .dataPipelineDataProcessConfiguration(DataPipelineDataProcessConfiguration.newBuilder()
                        .searchMode("balanced")
                        .insights(insights)
                        .build())
                .destination(DataPipelineDestination.newBuilder()
                        .imageEmbedding(DataPipelineDestinationImageEmbedding.newBuilder()
                                .bucket("vector-bucket").indexName("image").prefix("v2").build())
                        .imageTextEmbedding(DataPipelineDestinationImageTextEmbedding.newBuilder()
                                .bucket("vector-bucket").indexName("image-text").prefix("v2").build())
                        .videoFrameEmbedding(DataPipelineDestinationVideoFrameEmbedding.newBuilder()
                                .bucket("vector-bucket").indexName("video-frame").prefix("v2").build())
                        .videoTextEmbedding(DataPipelineDestinationVideoTextEmbedding.newBuilder()
                                .bucket("vector-bucket").indexName("video-text").prefix("v2").build())
                        .documentChunkEmbedding(DataPipelineDestinationDocumentChunkEmbedding.newBuilder()
                                .bucket("vector-bucket").indexName("document").prefix("v2").build())
                        .objectTagToMetadata(Collections.singletonList("category"))
                        .usermetaToMetadata(Collections.singletonList("x-oss-meta-source"))
                        .build())
                .dataPipelineError(DataPipelineError.newBuilder()
                        .errorMode("ignoreAndRecord")
                        .errorBucket("error-bucket")
                        .errorPrefix("v2/")
                        .build())
                .build();
    }

    private static String canonicalizePutXml(String xml) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        PutDataPipelineConfigurationConfiguration configuration =
                xmlMapper.readValue(xml, PutDataPipelineConfigurationConfiguration.class);
        return xmlMapper.writeValueAsString(configuration);
    }

    private static String v2RequestExampleXml() {
        return "<DataPipelineConfiguration>"
                + "<DataPipelineDescription>多媒体语义向量</DataPipelineDescription>"
                + "<Sources><InputBucket>source-bucket</InputBucket><InputDataScope>All</InputDataScope>"
                + "<FilterConfiguration><PrefixSet>media/</PrefixSet>"
                + "<ObjectMediaTypes>image</ObjectMediaTypes><ObjectMediaTypes>video</ObjectMediaTypes>"
                + "<ObjectMediaTypes>text</ObjectMediaTypes></FilterConfiguration></Sources>"
                + "<ModelTier>standard</ModelTier>"
                + "<DataPipelineDataProcessConfiguration><SearchMode>balanced</SearchMode><Insights>"
                + "<Image><Caption><Prompt>Describe the image.</Prompt></Caption></Image>"
                + "<Video><Caption><Prompt>Describe each video scene.</Prompt></Caption>"
                + "<FrameEmbedding><Snapshot><Mode>interval</Mode><Interval>1.0</Interval></Snapshot>"
                + "</FrameEmbedding></Video></Insights></DataPipelineDataProcessConfiguration>"
                + "<Destination>"
                + "<ImageEmbedding><Bucket>vector-bucket</Bucket><IndexName>image</IndexName><Prefix>v2</Prefix></ImageEmbedding>"
                + "<ImageTextEmbedding><Bucket>vector-bucket</Bucket><IndexName>image-text</IndexName><Prefix>v2</Prefix></ImageTextEmbedding>"
                + "<VideoFrameEmbedding><Bucket>vector-bucket</Bucket><IndexName>video-frame</IndexName><Prefix>v2</Prefix></VideoFrameEmbedding>"
                + "<VideoTextEmbedding><Bucket>vector-bucket</Bucket><IndexName>video-text</IndexName><Prefix>v2</Prefix></VideoTextEmbedding>"
                + "<DocumentChunkEmbedding><Bucket>vector-bucket</Bucket><IndexName>document</IndexName><Prefix>v2</Prefix></DocumentChunkEmbedding>"
                + "<ObjectTagToMetadata>category</ObjectTagToMetadata>"
                + "<UsermetaToMetadata>x-oss-meta-source</UsermetaToMetadata></Destination>"
                + "<DataPipelineError><ErrorMode>ignoreAndRecord</ErrorMode><ErrorBucket>error-bucket</ErrorBucket>"
                + "<ErrorPrefix>v2/</ErrorPrefix></DataPipelineError>"
                + "</DataPipelineConfiguration>";
    }

    private static String dhashRequestExampleXml() {
        return "<DataPipelineConfiguration><Sources><InputBucket>source-bucket</InputBucket>"
                + "<FilterConfiguration><ObjectMediaTypes>video</ObjectMediaTypes></FilterConfiguration></Sources>"
                + "<DataPipelineDataProcessConfiguration><SearchMode>fast</SearchMode><Insights><Video>"
                + "<FrameEmbedding><Snapshot><Mode>dhash</Mode><Number>20</Number></Snapshot></FrameEmbedding>"
                + "</Video></Insights></DataPipelineDataProcessConfiguration><Destination>"
                + "<VideoFrameEmbedding><Bucket>vector-bucket</Bucket><IndexName>video-frame</IndexName>"
                + "</VideoFrameEmbedding></Destination></DataPipelineConfiguration>";
    }
}
