package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CopyObjectAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String sourceBucket,
            String sourceKey,
            String bucket,
            String key) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {

            CopyObjectResult result = client.copyObjectAsync(CopyObjectRequest.newBuilder()
                            .sourceBucket(sourceBucket)
                            .sourceKey(sourceKey)
                            .bucket(bucket)
                            .key(key)
                    .build()).get();

            System.out.printf("status code:%d, request id:%s, eTag:%s\n",
                    result.statusCode(), result.requestId(), result.eTag());

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
        opts.addOption(Option.builder().longOpt("source-bucket").desc("The name of the source bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("source-key").desc("The name of the source object.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the target bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key").desc("The name of the target object.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String sourceBucket = cmd.getParsedOptionValue("source-bucket");
        String sourceKey = cmd.getParsedOptionValue("source-key");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        execute(endpoint, region, sourceBucket, sourceKey, bucket, key);
    }
}
