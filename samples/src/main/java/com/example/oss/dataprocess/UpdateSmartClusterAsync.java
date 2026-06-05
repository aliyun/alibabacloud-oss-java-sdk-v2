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

public class UpdateSmartClusterAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String datasetName,
            String objectId,
            String name,
            String description) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSDataProcessAsyncClientBuilder clientBuilder = OSSDataProcessAsyncClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSDataProcessAsyncClient client = clientBuilder.build()) {

            UpdateSmartClusterRequest.Builder requestBuilder = UpdateSmartClusterRequest.newBuilder()
                    .bucket(bucket)
                    .datasetName(datasetName)
                    .objectId(objectId);

            if (name != null) {
                requestBuilder.name(name);
            }
            if (description != null) {
                requestBuilder.description(description);
            }

            UpdateSmartClusterResult result = client.updateSmartClusterAsync(requestBuilder.build()).get();

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
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("datasetName").desc("The name of the dataset.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("objectId").desc("The object ID of the smart cluster.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("name").desc("The new name of the smart cluster.").hasArg().get());
        opts.addOption(Option.builder().longOpt("description").desc("The new description of the smart cluster.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String datasetName = cmd.getParsedOptionValue("datasetName");
        String objectId = cmd.getParsedOptionValue("objectId");
        String name = cmd.getParsedOptionValue("name");
        String description = cmd.getParsedOptionValue("description");
        execute(endpoint, region, bucket, datasetName, objectId, name, description);
    }
}
