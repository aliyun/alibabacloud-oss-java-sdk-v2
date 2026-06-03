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

public class CreateSmartCluster implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String datasetName,
            String name,
            String clusterType) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSDataProcessClientBuilder clientBuilder = OSSDataProcessClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSDataProcessClient client = clientBuilder.build()) {

            SmartClusterRule rule = SmartClusterRule.newBuilder()
                    .ruleType("Keyword")
                    .keywords(Arrays.asList("sample", "test"))
                    .build();

            CreateSmartClusterResult result = client.createSmartCluster(
                    CreateSmartClusterRequest.newBuilder()
                            .bucket(bucket)
                            .datasetName(datasetName)
                            .name(name)
                            .clusterType(clusterType != null ? clusterType : "User")
                            .rule(rule)
                            .description("Created via dataprocess sample")
                            .build());

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.objectId() != null) {
                System.out.printf("Object ID:%s%n", result.objectId());
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
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("datasetName").desc("The name of the dataset.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("name").desc("The name of the smart cluster.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("clusterType").desc("The type of the smart cluster.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String datasetName = cmd.getParsedOptionValue("datasetName");
        String name = cmd.getParsedOptionValue("name");
        String clusterType = cmd.getParsedOptionValue("clusterType");
        execute(endpoint, region, bucket, datasetName, name, clusterType);
    }
}
