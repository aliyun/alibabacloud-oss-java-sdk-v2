package com.example.oss;

import com.aliyun.sdk.service.oss2.agentic.OSSAsyncAgenticBucketClient;
import com.aliyun.sdk.service.oss2.agentic.OSSAsyncAgenticBucketClientBuilder;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class DeleteAgenticBucketPolicyAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String accountId,
            String bucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncAgenticBucketClientBuilder clientBuilder = OSSAsyncAgenticBucketClient.newBuilder()
                .credentialsProvider(provider)
                .region(region)
                .accountId(accountId);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncAgenticBucketClient client = clientBuilder.build()) {
            DeleteAgenticBucketPolicyResult result = client.deleteAgenticBucketPolicyAsync(DeleteAgenticBucketPolicyRequest.newBuilder()
                    .bucket(bucket)
                    .build()).get();

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
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the agentic bucket.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String accountId = cmd.getParsedOptionValue("account-id");
        String bucket = cmd.getParsedOptionValue("bucket");
        execute(endpoint, region, accountId, bucket);
    }
}
