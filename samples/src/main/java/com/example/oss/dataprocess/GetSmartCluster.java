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

public class GetSmartCluster implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String datasetName,
            String objectId) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSDataProcessClientBuilder clientBuilder = OSSDataProcessClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSDataProcessClient client = clientBuilder.build()) {

            GetSmartClusterResult result = client.getSmartCluster(
                    GetSmartClusterRequest.newBuilder()
                            .bucket(bucket)
                            .datasetName(datasetName)
                            .objectId(objectId)
                            .build());

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.smartCluster() != null) {
                SmartClusterInfo info = result.smartCluster();
                System.out.printf("Object ID:%s%n", info.objectId());
                System.out.printf("Name:%s%n", info.name());
                System.out.printf("Cluster type:%s%n", info.clusterType());
                System.out.printf("Description:%s%n", info.description());
                System.out.printf("Create time:%s%n", info.createTime());
                System.out.printf("Update time:%s%n", info.updateTime());
                if (info.reason() != null) {
                    System.out.printf("Reason:%s%n", info.reason());
                }
                if (info.rules() != null) {
                    System.out.printf("Rules count:%d%n", info.rules().size());
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
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("datasetName").desc("The name of the dataset.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("objectId").desc("The object ID of the smart cluster.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String datasetName = cmd.getParsedOptionValue("datasetName");
        String objectId = cmd.getParsedOptionValue("objectId");
        execute(endpoint, region, bucket, datasetName, objectId);
    }
}
