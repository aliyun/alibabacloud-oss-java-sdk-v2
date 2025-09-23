package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.concurrent.CompletableFuture;

public class SealAppendObjectAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {
            // Append object first part
            String content1 = "Hello, ";
            CompletableFuture<AppendObjectResult> appendFuture1 = client.appendObjectAsync(AppendObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .position(0L)
                    .body(BinaryData.fromString(content1))
                    .build());

            AppendObjectResult appendResult1 = appendFuture1.get();
            System.out.printf("Append object first part success, next position: %s%n", appendResult1.nextAppendPosition());

            // Seal append object
            CompletableFuture<SealAppendObjectResult> sealFuture = client.sealAppendObjectAsync(SealAppendObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .position(appendResult1.nextAppendPosition().toString())
                    .build());

            SealAppendObjectResult sealResult = sealFuture.get();
            System.out.printf("Seal append object success, status code:%d, request id:%s%n",
                    sealResult.statusCode(), sealResult.requestId());

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
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        execute(endpoint, region, bucket, key);
    }
}
