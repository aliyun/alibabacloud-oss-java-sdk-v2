package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDataPipelineBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetDataPipelineConfigurationResultTest {

    @Test
    public void testEmptyBuilder() {
        GetDataPipelineConfigurationResult result = GetDataPipelineConfigurationResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Type", "application/xml"
        );

        GetDataPipelineConfigurationResult result = GetDataPipelineConfigurationResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "Content-Type", "application/xml"
        );

        GetDataPipelineConfigurationResult original = GetDataPipelineConfigurationResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetDataPipelineConfigurationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<DataPipelineConfiguration>\n" +
                "  <DataPipelineName>my-data-pipeline</DataPipelineName>\n" +
                "  <DataPipelineDescription>使用百炼多模态模型为业务数据向量化</DataPipelineDescription>\n" +
                "  <DataPipelineRole>my-data-pipeline-role</DataPipelineRole>\n" +
                "  <Status>Running</Status>\n" +
                "  <Phase>IncrementalScanning</Phase>\n" +
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
                "  <CreateTime>2021-06-29T14:50:13.011643661+08:00</CreateTime>\n" +
                "</DataPipelineConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-1234567890abcdefg",
                        "Content-Type", "application/xml"
                ))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetDataPipelineConfigurationResult result = SerdeDataPipelineBasic.toGetDataPipelineConfiguration(output);

        assertThat(result).isNotNull();
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.dataPipelineConfiguration()).isNotNull();
        assertThat(result.dataPipelineConfiguration().dataPipelineName()).isEqualTo("my-data-pipeline");
        assertThat(result.dataPipelineConfiguration().dataPipelineDescription()).isEqualTo("使用百炼多模态模型为业务数据向量化");
        assertThat(result.dataPipelineConfiguration().dataPipelineRole()).isEqualTo("my-data-pipeline-role");
        assertThat(result.dataPipelineConfiguration().status()).isEqualTo("Running");
        assertThat(result.dataPipelineConfiguration().phase()).isEqualTo("IncrementalScanning");
        assertThat(result.dataPipelineConfiguration().createTime()).isEqualTo("2021-06-29T14:50:13.011643661+08:00");
        assertThat(result.dataPipelineConfiguration().sources()).isNotNull();
        assertThat(result.dataPipelineConfiguration().sources().size()).isEqualTo(1);
        assertThat(result.dataPipelineConfiguration().sources().get(0).inputBucket()).isEqualTo("my-bucket");
        assertThat(result.dataPipelineConfiguration().sources().get(0).inputDataScope()).isEqualTo("All");
        assertThat(result.dataPipelineConfiguration().sources().get(0).ignoreDelete()).isEqualTo(true);
        assertThat(result.dataPipelineConfiguration().sources().get(0).filterConfiguration()).isNotNull();
        assertThat(result.dataPipelineConfiguration().sources().get(0).filterConfiguration().prefixSet()).containsExactly("prefix1/", "prefix2/prefix3/");
        assertThat(result.dataPipelineConfiguration().sources().get(0).filterConfiguration().objectMediaTypes()).containsExactly("text", "image", "video");
        assertThat(result.dataPipelineConfiguration().dataPipelineEmbeddingConfiguration()).isNotNull();
        assertThat(result.dataPipelineConfiguration().dataPipelineEmbeddingConfiguration().embeddingProvider()).isEqualTo("bailian");
        assertThat(result.dataPipelineConfiguration().dataPipelineEmbeddingConfiguration().apiKey()).isEqualTo("xxxx");
        assertThat(result.dataPipelineConfiguration().dataPipelineEmbeddingConfiguration().model()).isEqualTo("qwen2.5-vl-embedding");
        assertThat(result.dataPipelineConfiguration().dataPipelineEmbeddingConfiguration().fps()).isEqualTo(1.0f);
        assertThat(result.dataPipelineConfiguration().destination()).isNotNull();
        assertThat(result.dataPipelineConfiguration().destination().vectorBucketName()).isEqualTo("my-vector-bucket");
        assertThat(result.dataPipelineConfiguration().destination().vectorIndexNames()).containsExactly("my-index");
        assertThat(result.dataPipelineConfiguration().destination().vectorKeyPrefix()).isEqualTo("");
        assertThat(result.dataPipelineConfiguration().destination().objectTagToMetadata()).containsExactly("key1", "key2");
        assertThat(result.dataPipelineConfiguration().destination().usermetaToMetadata()).containsExactly("x-oss-meta-key1");
        assertThat(result.dataPipelineConfiguration().dataPipelineError()).isNotNull();
        assertThat(result.dataPipelineConfiguration().dataPipelineError().errorMode()).isEqualTo("ignoreAndRecord");
        assertThat(result.dataPipelineConfiguration().dataPipelineError().errorBucket()).isEqualTo("my-error-bucket");
        assertThat(result.dataPipelineConfiguration().dataPipelineError().errorPrefix()).isEqualTo("error-output/");
    }
}
