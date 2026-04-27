package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDataPipelineBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListDataPipelineConfigurationsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListDataPipelineConfigurationsResult result = ListDataPipelineConfigurationsResult.newBuilder().build();
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

        ListDataPipelineConfigurationsResult result = ListDataPipelineConfigurationsResult.newBuilder()
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

        ListDataPipelineConfigurationsResult original = ListDataPipelineConfigurationsResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListDataPipelineConfigurationsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListDataPipelineConfigurationsResult>\n" +
                "  <DataPipelineConfigurations>\n" +
                "    <DataPipelineConfiguration>\n" +
                "      <DataPipelineName>my-data-pipeline</DataPipelineName>\n" +
                "      <DataPipelineDescription>使用百炼多模态模型为业务数据向量化</DataPipelineDescription>\n" +
                "      <DataPipelineRole>my-data-pipeline-role</DataPipelineRole>\n" +
                "      <Status>Running</Status>\n" +
                "      <Sources>\n" +
                "          <InputBucket>my-bucket</InputBucket>\n" +
                "          <InputDataScope>All</InputDataScope>\n" +
                "          <IgnoreDelete>true</IgnoreDelete>\n" +
                "          <FilterConfiguration>\n" +
                "              <PrefixSet>prefix1/</PrefixSet>\n" +
                "              <PrefixSet>prefix2/prefix3/</PrefixSet>\n" +
                "              <ObjectMediaTypes>text</ObjectMediaTypes>\n" +
                "              <ObjectMediaTypes>image</ObjectMediaTypes>\n" +
                "              <ObjectMediaTypes>video</ObjectMediaTypes>\n" +
                "          </FilterConfiguration>\n" +
                "      </Sources>\n" +
                "      <DataPipelineEmbeddingConfiguration>\n" +
                "          <EmbeddingProvider>bailian</EmbeddingProvider>\n" +
                "          <ApiKey>xxxx</ApiKey>\n" +
                "          <Model>qwen2.5-vl-embedding</Model>\n" +
                "          <FPS>1</FPS>\n" +
                "      </DataPipelineEmbeddingConfiguration>\n" +
                "      <Destination>\n" +
                "          <VectorBucketName>my-vector-bucket</VectorBucketName>\n" +
                "          <VectorIndexNames>my-index</VectorIndexNames>\n" +
                "          <VectorKeyPrefix></VectorKeyPrefix>\n" +
                "          <ObjectTagToMetadata>key1</ObjectTagToMetadata>\n" +
                "          <ObjectTagToMetadata>key2</ObjectTagToMetadata>\n" +
                "          <UsermetaToMetadata>x-oss-meta-key1</UsermetaToMetadata>\n" +
                "      </Destination>\n" +
                "      <DataPipelineError>\n" +
                "          <ErrorMode>ignoreAndRecord</ErrorMode>\n" +
                "          <ErrorBucket>my-error-bucket</ErrorBucket>\n" +
                "          <ErrorPrefix>error-output/</ErrorPrefix>\n" +
                "      </DataPipelineError>\n" +
                "      <CreateTime>2021-06-29T14:50:13.011643661+08:00</CreateTime>\n" +
                "    </DataPipelineConfiguration>\n" +
                "  </DataPipelineConfigurations>\n" +
                "  <NextToken>xxx</NextToken>\n" +
                "</ListDataPipelineConfigurationsResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-1234567890abcdefg",
                        "Content-Type", "application/xml"
                ))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListDataPipelineConfigurationsResult result = SerdeDataPipelineBasic.toListDataPipelineConfigurations(output);

        assertThat(result).isNotNull();
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.dataPipelineConfigurations()).isNotNull();
        assertThat(result.dataPipelineConfigurations().size()).isEqualTo(1);
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineName()).isEqualTo("my-data-pipeline");
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineDescription()).isEqualTo("使用百炼多模态模型为业务数据向量化");
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineRole()).isEqualTo("my-data-pipeline-role");
        assertThat(result.dataPipelineConfigurations().get(0).status()).isEqualTo("Running");
        assertThat(result.dataPipelineConfigurations().get(0).createTime()).isEqualTo("2021-06-29T14:50:13.011643661+08:00");
        assertThat(result.dataPipelineConfigurations().get(0).sources()).isNotNull();
        assertThat(result.dataPipelineConfigurations().get(0).sources().size()).isEqualTo(1);
        assertThat(result.dataPipelineConfigurations().get(0).sources().get(0).inputBucket()).isEqualTo("my-bucket");
        assertThat(result.dataPipelineConfigurations().get(0).sources().get(0).inputDataScope()).isEqualTo("All");
        assertThat(result.dataPipelineConfigurations().get(0).sources().get(0).ignoreDelete()).isEqualTo(true);
        assertThat(result.dataPipelineConfigurations().get(0).sources().get(0).filterConfiguration()).isNotNull();
        assertThat(result.dataPipelineConfigurations().get(0).sources().get(0).filterConfiguration().prefixSet()).containsExactly("prefix1/", "prefix2/prefix3/");
        assertThat(result.dataPipelineConfigurations().get(0).sources().get(0).filterConfiguration().objectMediaTypes()).containsExactly("text", "image", "video");
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineEmbeddingConfiguration()).isNotNull();
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineEmbeddingConfiguration().embeddingProvider()).isEqualTo("bailian");
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineEmbeddingConfiguration().apiKey()).isEqualTo("xxxx");
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineEmbeddingConfiguration().model()).isEqualTo("qwen2.5-vl-embedding");
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineEmbeddingConfiguration().fps()).isEqualTo(1.0f);
        assertThat(result.dataPipelineConfigurations().get(0).destination()).isNotNull();
        assertThat(result.dataPipelineConfigurations().get(0).destination().vectorBucketName()).isEqualTo("my-vector-bucket");
        assertThat(result.dataPipelineConfigurations().get(0).destination().vectorIndexNames()).containsExactly("my-index");
        assertThat(result.dataPipelineConfigurations().get(0).destination().vectorKeyPrefix()).isEqualTo("");
        assertThat(result.dataPipelineConfigurations().get(0).destination().objectTagToMetadata()).containsExactly("key1", "key2");
        assertThat(result.dataPipelineConfigurations().get(0).destination().usermetaToMetadata()).containsExactly("x-oss-meta-key1");
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineError()).isNotNull();
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineError().errorMode()).isEqualTo("ignoreAndRecord");
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineError().errorBucket()).isEqualTo("my-error-bucket");
        assertThat(result.dataPipelineConfigurations().get(0).dataPipelineError().errorPrefix()).isEqualTo("error-output/");
        assertThat(result.nextToken()).isEqualTo("xxx");
    }
}
