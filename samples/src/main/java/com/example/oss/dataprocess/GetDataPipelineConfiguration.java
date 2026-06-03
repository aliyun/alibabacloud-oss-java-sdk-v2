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

public class GetDataPipelineConfiguration implements Example {

    private static void execute(
            String endpoint,
            String region,
            String pipelineName) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSDataProcessClientBuilder clientBuilder = OSSDataProcessClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSDataProcessClient client = clientBuilder.build()) {

            GetDataPipelineConfigurationResult result = client.getDataPipelineConfiguration(
                    GetDataPipelineConfigurationRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .build());

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            DataPipelineConfiguration cfg = result.dataPipelineConfiguration();
            if (cfg != null) {
                System.out.printf("Pipeline name:%s, status:%s, role:%s, createTime:%s%n",
                        cfg.dataPipelineName(), cfg.status(),
                        cfg.dataPipelineRole(), cfg.createTime());
                if (cfg.destination() != null) {
                    System.out.printf("Destination vector bucket:%s%n",
                            cfg.destination().vectorBucketName());
                }
                if (cfg.dataPipelineError() != null) {
                    System.out.printf("Pipeline error mode:%s, bucket:%s, prefix:%s%n",
                            cfg.dataPipelineError().errorMode(),
                            cfg.dataPipelineError().errorBucket(),
                            cfg.dataPipelineError().errorPrefix());
                }
            }

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
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String pipelineName = cmd.getParsedOptionValue("pipelineName");
        execute(endpoint, region, pipelineName);
    }
}
