package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.QuotaConfiguration;
import com.aliyun.sdk.service.oss2.models.GetBucketStorageQuotaRequest;
import com.aliyun.sdk.service.oss2.models.GetBucketStorageQuotaResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetBucketStorageQuota implements Example {

    private static void execute(String endpoint, String region, String bucket) {
        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            GetBucketStorageQuotaRequest request = GetBucketStorageQuotaRequest.newBuilder()
                    .bucket(bucket)
                    .build();

            GetBucketStorageQuotaResult result = client.getBucketStorageQuota(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            QuotaConfiguration config = result.quotaConfiguration();
            if (config != null) {
                System.out.printf("Storage quota: %d bytes%n", config.storageQuota());
                System.out.printf("Mode: %s%n", config.mode());
                if (config.currentUsage() != null) {
                    System.out.printf("Current usage: %d bytes%n", config.currentUsage());
                }
            }

        } catch (Exception e) {
            System.out.printf("error:%n%s", e);
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
}
