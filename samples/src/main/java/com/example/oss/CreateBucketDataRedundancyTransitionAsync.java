package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.CreateBucketDataRedundancyTransitionRequest;
import com.aliyun.sdk.service.oss2.models.CreateBucketDataRedundancyTransitionResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CreateBucketDataRedundancyTransitionAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String redundancyType) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {
            
            CreateBucketDataRedundancyTransitionResult result = client.createBucketDataRedundancyTransitionAsync(
                    CreateBucketDataRedundancyTransitionRequest.newBuilder()
                            .bucket(bucket)
                            .targetRedundancyType(redundancyType)
                            .build()).get();

            System.out.printf("Status code:%d, request id:%s, task id:%s\n",
                    result.statusCode(), result.requestId(), result.bucketDataRedundancyTransition().taskId());

        } catch (Exception e) {
            System.out.printf("error:\n%s", e);
        }
    }

    private static OSSAsyncClient getDefaultAsyncClient(String endpoint, String region, CredentialsProvider provider) {
        return OSSAsyncClient.newBuilder()
                .region(region)
                .endpoint(endpoint)
                .credentialsProvider(provider)
                .build();
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("targetRedundancyType").desc("The target redundancy type (ZRS or LRS).").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String targetRedundancyType = cmd.getParsedOptionValue("targetRedundancyType");
        execute(endpoint, region, bucket, targetRedundancyType);
    }
}