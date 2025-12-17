package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.GetBucketDataRedundancyTransitionRequest;
import com.aliyun.sdk.service.oss2.models.GetBucketDataRedundancyTransitionResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetBucketDataRedundancyTransition implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String taskId) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            
            GetBucketDataRedundancyTransitionResult result = client.getBucketDataRedundancyTransition(
                    GetBucketDataRedundancyTransitionRequest.newBuilder()
                            .bucket(bucket)
                            .redundancyTransitionTaskid(taskId)
                            .build());

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());
            System.out.printf("Bucket: %s\n", result.bucketDataRedundancyTransition().bucket());
            System.out.printf("Task ID: %s\n", result.bucketDataRedundancyTransition().taskId());
            System.out.printf("Status: %s\n", result.bucketDataRedundancyTransition().status());
            System.out.printf("Create Time: %s\n", result.bucketDataRedundancyTransition().createTime());
            System.out.printf("Start Time: %s\n", result.bucketDataRedundancyTransition().startTime());
            System.out.printf("Process Percentage: %d%%%%\n", result.bucketDataRedundancyTransition().processPercentage());

        } catch (Exception e) {
            System.out.printf("error:\n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("taskId").desc("The ID of the redundancy transition task.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String taskId = cmd.getParsedOptionValue("taskId");
        execute(endpoint, region, bucket, taskId);
    }
}