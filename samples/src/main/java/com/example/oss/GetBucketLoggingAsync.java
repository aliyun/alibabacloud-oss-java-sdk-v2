package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetBucketLoggingAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {

            GetBucketLoggingResult result = client.getBucketLoggingAsync(GetBucketLoggingRequest.newBuilder()
                            .bucket(bucket)
                            .build()).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

            BucketLoggingStatus bucketLoggingStatus = result.bucketLoggingStatus();
            if (bucketLoggingStatus != null && bucketLoggingStatus.loggingEnabled() != null) {
                LoggingEnabled loggingEnabled = bucketLoggingStatus.loggingEnabled();
                System.out.printf("Bucket logging configuration:\n");
                System.out.printf("  Target bucket: %s\n", loggingEnabled.targetBucket());
                System.out.printf("  Target prefix: %s\n", loggingEnabled.targetPrefix());
                System.out.printf("  Logging role: %s\n", loggingEnabled.loggingRole());

            } else {
                System.out.println("No bucket logging configuration found.");
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
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        execute(endpoint, region, bucket);
    }

    private static OSSAsyncClient getDefaultAsyncClient(String endpoint, String region, CredentialsProvider provider) {
        return OSSAsyncClient.newBuilder()
                .region(region)
                .endpoint(endpoint)
                .credentialsProvider(provider)
                .build();
    }
}