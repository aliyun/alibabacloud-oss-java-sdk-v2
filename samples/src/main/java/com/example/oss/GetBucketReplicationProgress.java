package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetBucketReplicationProgress implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String ruleId) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {

            GetBucketReplicationProgressResult result = client.getBucketReplicationProgress(GetBucketReplicationProgressRequest.newBuilder()
                            .bucket(bucket)
                            .ruleId(ruleId)
                            .build());

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

            ReplicationProgress replicationProgress = result.replicationProgress();
            if (replicationProgress != null && replicationProgress.rules() != null) {
                System.out.printf("Found %d replication progress rules:\n", replicationProgress.rules().size());
                for (int i = 0; i < replicationProgress.rules().size(); i++) {
                    ReplicationProgressRule rule = replicationProgress.rules().get(i);
                    System.out.printf("Rule %d:\n", i + 1);
                    System.out.printf("  ID: %s\n", rule.id());
                    System.out.printf("  Status: %s\n", rule.status());
                    if (rule.progress() != null) {
                        System.out.printf("  Historical object progress: %s\n", rule.progress().historicalObject());
                        System.out.printf("  New object progress: %s\n", rule.progress().newObject());
                    }
                }
            } else {
                System.out.println("No replication progress found.");
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

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("ruleId").desc("The ID of the replication rule.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String ruleId = cmd.getParsedOptionValue("ruleId");
        execute(endpoint, region, bucket, ruleId);
    }
}