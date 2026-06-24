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

public class ListSmartClusters implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String datasetName,
            String clusterType,
            Integer maxResults) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSDataProcessClientBuilder clientBuilder = OSSDataProcessClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSDataProcessClient client = clientBuilder.build()) {

            ListSmartClustersRequest.Builder requestBuilder = ListSmartClustersRequest.newBuilder()
                    .bucket(bucket)
                    .datasetName(datasetName);

            if (clusterType != null) {
                requestBuilder.clusterType(clusterType);
            }
            if (maxResults != null) {
                requestBuilder.maxResults(maxResults);
            }

            ListSmartClustersResult result = client.listSmartClusters(requestBuilder.build());

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.smartClusters() != null) {
                for (SmartClusterInfo info : result.smartClusters()) {
                    System.out.printf("Object ID:%s, name:%s, cluster type:%s, description:%s%n",
                            info.objectId(), info.name(), info.clusterType(), info.description());
                }
            }

            if (result.nextToken() != null && !result.nextToken().isEmpty()) {
                System.out.printf("Next token:%s%n", result.nextToken());
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
        opts.addOption(Option.builder().longOpt("clusterType").desc("The type of the smart cluster to filter.").hasArg().get());
        opts.addOption(Option.builder().longOpt("maxResults").desc("The maximum number of results to return.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String datasetName = cmd.getParsedOptionValue("datasetName");
        String clusterType = cmd.getParsedOptionValue("clusterType");
        Integer maxResults = cmd.getParsedOptionValue("maxResults") != null
                ? Integer.parseInt(cmd.getParsedOptionValue("maxResults").toString()) : null;
        execute(endpoint, region, bucket, datasetName, clusterType, maxResults);
    }
}
