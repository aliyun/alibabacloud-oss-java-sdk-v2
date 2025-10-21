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

public class PutBucketLogging implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String targetBucket,
            String targetPrefix) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {

            TargetSuffix targetSuffix = TargetSuffix.newBuilder()
                    .useRandomPart(false)
                    .build();
            
            LoggingEnabled loggingEnabled = LoggingEnabled.newBuilder()
                    .targetBucket(targetBucket)
                    .targetPrefix(targetPrefix)
                    .pushSuccessMarker(false)
                    .loggingRole("")
                    .targetSuffix(targetSuffix)
                    .build();

            BucketLoggingStatus bucketLoggingStatus = BucketLoggingStatus.newBuilder()
                    .loggingEnabled(loggingEnabled)
                    .build();

            PutBucketLoggingResult result = client.putBucketLogging(PutBucketLoggingRequest.newBuilder()
                            .bucket(bucket)
                            .bucketLoggingStatus(bucketLoggingStatus)
                            .build());

            System.out.printf("Status code:%d, request id:%s\n",
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
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("targetBucket").desc("The bucket that stores access logs.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("targetPrefix").desc("The prefix of the log objects.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String targetBucket = cmd.getParsedOptionValue("targetBucket");
        String targetPrefix = cmd.getParsedOptionValue("targetPrefix");
        execute(endpoint, region, bucket, targetBucket, targetPrefix);
    }
}