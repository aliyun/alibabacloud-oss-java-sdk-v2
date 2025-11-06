package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.BucketDataRedundancyTransition;
import com.aliyun.sdk.service.oss2.models.ListBucketDataRedundancyTransitionRequest;
import com.aliyun.sdk.service.oss2.models.ListBucketDataRedundancyTransitionResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ListBucketDataRedundancyTransition implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            
            ListBucketDataRedundancyTransitionResult result = client.listBucketDataRedundancyTransition(
                    ListBucketDataRedundancyTransitionRequest.newBuilder()
                            .bucket(bucket)
                            .build());

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());
            
            if (result.listBucketDataRedundancyTransition() != null && 
                result.listBucketDataRedundancyTransition().bucketDataRedundancyTransition() != null) {
                System.out.println("Bucket Data Redundancy Transitions:");
                for (BucketDataRedundancyTransition transition : result.listBucketDataRedundancyTransition().bucketDataRedundancyTransition()) {
                    System.out.printf("- Bucket: %s, Task ID: %s, Status: %s, Progress: %d%%%%\n",
                            transition.bucket(), transition.taskId(), transition.status(), transition.processPercentage());
                }
            } else {
                System.out.println("No data redundancy transitions found.");
            }

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
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        execute(endpoint, region, bucket);
    }
}