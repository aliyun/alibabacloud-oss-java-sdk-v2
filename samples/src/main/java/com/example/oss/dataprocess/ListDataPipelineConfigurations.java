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

public class ListDataPipelineConfigurations implements Example {

    private static void execute(
            String endpoint,
            String region,
            String prefix,
            Integer maxResults) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSDataProcessClientBuilder clientBuilder = OSSDataProcessClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSDataProcessClient client = clientBuilder.build()) {

            // Manual pagination using nextToken
            String nextToken = null;
            int page = 0;
            do {
                ListDataPipelineConfigurationsRequest.Builder reqBuilder =
                        ListDataPipelineConfigurationsRequest.newBuilder();
                if (prefix != null) {
                    reqBuilder.prefix(prefix);
                }
                if (maxResults != null) {
                    reqBuilder.maxResults(maxResults);
                }
                if (nextToken != null) {
                    reqBuilder.nextToken(nextToken);
                }

                ListDataPipelineConfigurationsResult result =
                        client.listDataPipelineConfigurations(reqBuilder.build());

                System.out.printf("Page:%d, status code:%d, request id:%s%n",
                        ++page, result.statusCode(), result.requestId());

                if (result.dataPipelineConfigurations() != null) {
                    for (DataPipelineConfiguration cfg : result.dataPipelineConfigurations()) {
                        System.out.printf("Pipeline: name=%s, status=%s, role=%s, createTime=%s%n",
                                cfg.dataPipelineName(), cfg.status(),
                                cfg.dataPipelineRole(), cfg.createTime());
                    }
                }

                nextToken = result.nextToken();
            } while (nextToken != null && !nextToken.isEmpty());

        } catch (Exception e) {
            System.out.printf("error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("prefix").desc("Optional prefix filter for pipeline name.").hasArg().get());
        opts.addOption(Option.builder().longOpt("maxResults").desc("Optional max results per page.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String prefix = cmd.getParsedOptionValue("prefix");
        String maxResultsStr = cmd.getParsedOptionValue("maxResults");
        Integer maxResults = (maxResultsStr != null) ? Integer.valueOf(maxResultsStr) : null;
        execute(endpoint, region, prefix, maxResults);
    }
}
