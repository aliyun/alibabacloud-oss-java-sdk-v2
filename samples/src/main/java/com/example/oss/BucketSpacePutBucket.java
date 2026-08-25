package com.example.oss;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.agentic.BucketSpaceClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class BucketSpacePutBucket implements Example {

    private static void execute(
            String endpoint,
            String region,
            String accountId,
            String bucket,
            String agenticBucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        ClientConfiguration.Builder configBuilder = ClientConfiguration.newBuilder()
                .credentialsProvider(provider)
                .region(region)
                .accountId(accountId);

        if (endpoint != null) {
            configBuilder.endpoint(endpoint);
        }

        try (OSSClient client = BucketSpaceClient.create(configBuilder.build())) {
            // The bucket space must be created under an agentic bucket, identified by its
            // full name "{bucket}-{accountId}-{region}-ab-apsr".
            PutBucketResult result = client.putBucket(PutBucketRequest.newBuilder()
                    .bucket(bucket)
                    .agenticBucket(String.format("%s-%s-%s-ab-apsr", agenticBucket, accountId, region))
                    .build());

            System.out.printf("status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

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
        opts.addOption(Option.builder().longOpt("account-id").desc("The ID of the Alibaba Cloud account.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket space.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("agentic-bucket").desc("The name of the agentic bucket that the bucket space belongs to.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String accountId = cmd.getParsedOptionValue("account-id");
        String bucket = cmd.getParsedOptionValue("bucket");
        String agenticBucket = cmd.getParsedOptionValue("agentic-bucket");
        execute(endpoint, region, accountId, bucket, agenticBucket);
    }
}
