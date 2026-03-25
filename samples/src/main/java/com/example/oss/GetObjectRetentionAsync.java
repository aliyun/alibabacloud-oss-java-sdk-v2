package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.OSSAsyncClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetObjectRetentionAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String versionId) throws Exception {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncClientBuilder clientBuilder = OSSAsyncClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncClient client = clientBuilder.build()) {
            GetObjectRetentionRequest.Builder requestBuilder = GetObjectRetentionRequest.newBuilder()
                    .bucket(bucket)
                    .key(key);

            if (versionId != null) {
                requestBuilder.versionId(versionId);
            }

            GetObjectRetentionResult result = client.getObjectRetentionAsync(requestBuilder.build()).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

            if (result.retention() != null) {
                System.out.printf("Retention Mode: %s\n", result.retention().mode());
                System.out.printf("Retain Until Date: %s\n", result.retention().retainUntilDate());
            }

        } catch (Exception e) {
            System.out.printf("error:\n%s", e);
            throw e;
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("versionId").desc("The version id of the target object.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        String versionId = cmd.getParsedOptionValue("versionId");
        try {
            execute(endpoint, region, bucket, key, versionId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
