package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.BucketDataRedundancyTransition;
import com.aliyun.sdk.service.oss2.models.ListUserDataRedundancyTransitionRequest;
import com.aliyun.sdk.service.oss2.models.ListUserDataRedundancyTransitionResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ListUserDataRedundancyTransitionAsync implements Example {

    private static void execute(
            String endpoint,
            String region) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {
            
            ListUserDataRedundancyTransitionResult result = client.listUserDataRedundancyTransitionAsync(
                    ListUserDataRedundancyTransitionRequest.newBuilder()
                            .build()).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());
            
            if (result.listBucketDataRedundancyTransition() != null && 
                result.listBucketDataRedundancyTransition().bucketDataRedundancyTransition() != null) {
                System.out.println("User Data Redundancy Transitions:");
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
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        execute(endpoint, region);
    }
}