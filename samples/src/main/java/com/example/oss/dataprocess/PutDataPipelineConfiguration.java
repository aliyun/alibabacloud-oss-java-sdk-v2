package com.example.oss.dataprocess;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.dataprocess.OSSDataProcessClient;
import com.aliyun.sdk.service.oss2.dataprocess.OSSDataProcessClientBuilder;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.Collections;

public class PutDataPipelineConfiguration implements Example {

    private static void execute(
            String endpoint,
            String region,
            String pipelineName,
            String roleName,
            String inputBucket,
            String vectorBucketName,
            String indexName,
            String apiKey,
            String modelType) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSDataProcessClientBuilder clientBuilder = OSSDataProcessClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSDataProcessClient client = clientBuilder.build()) {

            DataPipelineSourceFilterConfiguration filterConfig =
                    DataPipelineSourceFilterConfiguration.newBuilder()
                            .prefixSet(Arrays.asList("data/"))
                            .objectMediaTypes(Arrays.asList("image", "video"))
                            .build();

            DataPipelineSource source = DataPipelineSource.newBuilder()
                    .inputBucket(inputBucket)
                    .inputDataScope("All")
                    .ignoreDelete(true)
                    .filterConfiguration(filterConfig)
                    .build();

            DataPipelineEmbeddingConfiguration embeddingConfig =
                    DataPipelineEmbeddingConfiguration.newBuilder()
                            .embeddingProvider("bailian")
                            .apiKey(apiKey)
                            .model(modelType)
                            .fps(1.0f)
                            .build();

            DataPipelineDestination destination = DataPipelineDestination.newBuilder()
                    .vectorBucketName(vectorBucketName)
                    .vectorIndexNames(Collections.singletonList(indexName))
                    .build();

            PutDataPipelineConfigurationConfiguration config =
                    PutDataPipelineConfigurationConfiguration.newBuilder()
                            .dataPipelineDescription("Data pipeline sample")
                            .sources(Collections.singletonList(source))
                            .dataPipelineEmbeddingConfiguration(embeddingConfig)
                            .destination(destination)
                            .build();

            PutDataPipelineConfigurationResult result = client.putDataPipelineConfiguration(
                    PutDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .role(roleName)
                            .putDataPipelineConfigurationConfiguration(config)
                            .build());

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

        } catch (Exception e) {
            System.out.printf("error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("pipelineName").desc("The name of the data pipeline.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("roleName").desc("The role ARN for the data pipeline.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("inputBucket").desc("The input bucket name.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("vectorBucketName").desc("The vector bucket name.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("indexName").desc("The vector index name.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("apiKey").desc("The API key for embedding service.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("modelType").desc("The model type for embedding.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String pipelineName = cmd.getParsedOptionValue("pipelineName");
        String roleName = cmd.getParsedOptionValue("roleName");
        String inputBucket = cmd.getParsedOptionValue("inputBucket");
        String vectorBucketName = cmd.getParsedOptionValue("vectorBucketName");
        String indexName = cmd.getParsedOptionValue("indexName");
        String apiKey = cmd.getParsedOptionValue("apiKey");
        String modelType = cmd.getParsedOptionValue("modelType");
        execute(endpoint, region, pipelineName, roleName, inputBucket, vectorBucketName, indexName, apiKey, modelType);
    }
}
