package com.example.oss.dataprocess;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.dataprocess.OSSDataProcessAsyncClient;
import com.aliyun.sdk.service.oss2.dataprocess.OSSDataProcessAsyncClientBuilder;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class RestartDataPipelineAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String pipelineName) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSDataProcessAsyncClientBuilder clientBuilder = OSSDataProcessAsyncClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSDataProcessAsyncClient client = clientBuilder.build()) {

            RestartDataPipelineResult result = client.restartDataPipelineAsync(
                    RestartDataPipelineRequest.newBuilder()
                            .dataPipelineName(pipelineName)
                            .build()).get();

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
