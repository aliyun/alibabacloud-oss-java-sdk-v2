package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetBucketReplicationAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {

            GetBucketReplicationResult result = client.getBucketReplicationAsync(GetBucketReplicationRequest.newBuilder()
                            .bucket(bucket)
                            .build()).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

            ReplicationConfiguration replicationConfiguration = result.replicationConfiguration();
            if (replicationConfiguration != null && replicationConfiguration.rules() != null) {
                System.out.printf("Found %d replication rules:\n", replicationConfiguration.rules().size());
                for (int i = 0; i < replicationConfiguration.rules().size(); i++) {
                    ReplicationRule rule = replicationConfiguration.rules().get(i);
                    System.out.printf("Rule %d:\n", i + 1);
                    System.out.printf("  ID: %s\n", rule.id());
                    System.out.printf("  Status: %s\n", rule.status());
                    if (rule.destination() != null) {
                        System.out.printf("  Destination bucket: %s\n", rule.destination().bucket());
                        System.out.printf("  Destination location: %s\n", rule.destination().location());
                    }
                }
            } else {
                System.out.println("No replication configuration found.");
            }

        } catch (Exception e) {
            //If the exception is caused by ServiceException, detailed information can be obtained in this way.
            //ServiceException se = ServiceException.asCause(e);
            //if (se != null) {
            //   System.out.printf("ServiceException: requestId:%s, errorCode:%s\n", se.requestId(), se.errorCode());
            //}
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