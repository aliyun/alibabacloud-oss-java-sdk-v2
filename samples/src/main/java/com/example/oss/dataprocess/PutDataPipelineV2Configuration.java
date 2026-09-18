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

import java.util.Collections;

public class PutDataPipelineV2Configuration implements Example {

    private static void execute(
            String endpoint,
            String region,
            String pipelineName,
            String roleName,
            String inputBucket,
            String vectorBucketName,
            String imageIndexName) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSDataProcessClientBuilder clientBuilder = OSSDataProcessClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);
        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSDataProcessClient client = clientBuilder.build()) {
            DataPipelineSource source = DataPipelineSource.newBuilder()
                    .inputBucket(inputBucket)
                    .inputDataScope("All")
                    .ignoreDelete(false)
                    .filterConfiguration(DataPipelineSourceFilterConfiguration.newBuilder()
                            .prefixSet(Collections.singletonList("media/"))
                            .objectMediaTypes(Collections.singletonList("image"))
                            .build())
                    .build();

            DataPipelineDataProcessConfiguration processConfiguration =
                    DataPipelineDataProcessConfiguration.newBuilder()
                            .searchMode("fast")
                            .insights(DataPipelineInsights.newBuilder()
                                    .image(DataPipelineInsightsImage.newBuilder()
                                            .caption(DataPipelineInsightsCaption.newBuilder()
                                                    .prompt("Describe the image.")
                                                    .build())
                                            .build())
                                    .build())
                            .build();

            DataPipelineDestination destination = DataPipelineDestination.newBuilder()
                    .imageEmbedding(DataPipelineDestinationImageEmbedding.newBuilder()
                            .bucket(vectorBucketName)
                            .indexName(imageIndexName)
                            .prefix("v2")
                            .build())
                    .build();

            PutDataPipelineConfigurationConfiguration configuration =
                    PutDataPipelineConfigurationConfiguration.newBuilder()
                            .dataPipelineDescription("V2 image semantic vector pipeline")
                            .sources(Collections.singletonList(source))
                            .modelTier("standard")
                            .dataPipelineDataProcessConfiguration(processConfiguration)
                            .destination(destination)
                            .build();

            PutDataPipelineConfigurationResult result = client.putDataPipelineConfiguration(
                    PutDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .role(roleName)
                            .putDataPipelineConfigurationConfiguration(configuration)
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
        opts.addOption(Option.builder().longOpt("endpoint").desc("The OSS endpoint.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The OSS region.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("pipelineName").desc("The data pipeline name.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("roleName").desc("The data pipeline service role.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("inputBucket").desc("The source bucket name.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("vectorBucketName").desc("The vector bucket name.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("imageIndexName").desc("The 768-dimensional image index name.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        execute(
                cmd.getParsedOptionValue("endpoint"),
                cmd.getParsedOptionValue("region"),
                cmd.getParsedOptionValue("pipelineName"),
                cmd.getParsedOptionValue("roleName"),
                cmd.getParsedOptionValue("inputBucket"),
                cmd.getParsedOptionValue("vectorBucketName"),
                cmd.getParsedOptionValue("imageIndexName"));
    }
}
