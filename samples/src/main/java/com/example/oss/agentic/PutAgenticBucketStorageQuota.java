package com.example.oss.agentic;

import com.example.oss.Example;

import com.aliyun.sdk.service.oss2.agentic.OSSAgenticBucketClient;
import com.aliyun.sdk.service.oss2.agentic.OSSAgenticBucketClientBuilder;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.QuotaConfiguration;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class PutAgenticBucketStorageQuota implements Example {

    private static void execute(
            String endpoint,
            String region,
            String accountId,
            String bucket,
            Long storageQuota,
            String mode) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAgenticBucketClientBuilder clientBuilder = OSSAgenticBucketClient.newBuilder()
                .credentialsProvider(provider)
                .region(region)
                .accountId(accountId);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAgenticBucketClient client = clientBuilder.build()) {
            PutAgenticBucketStorageQuotaRequest request = PutAgenticBucketStorageQuotaRequest.newBuilder()
                    .bucket(bucket)
                    .quotaConfiguration(QuotaConfiguration.newBuilder()
                            .storageQuota(storageQuota)
                            .mode(mode)
                            .build())
                    .build();

            PutAgenticBucketStorageQuotaResult result = client.putAgenticBucketStorageQuota(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

        } catch (Exception e) {
            System.out.printf("error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The ID of the Alibaba Cloud account.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the agentic bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("storageQuota").desc("The default storage quota for bucket spaces created in the agentic bucket. Unit: bytes.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("mode").desc("The mode of the storage quota. Valid values: Strict or Warning.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String accountId = cmd.getParsedOptionValue("accountId");
        String bucket = cmd.getParsedOptionValue("bucket");
        Long storageQuota = ((Number) cmd.getParsedOptionValue("storageQuota")).longValue();
        String mode = cmd.getParsedOptionValue("mode");
        execute(endpoint, region, accountId, bucket, storageQuota, mode);
    }
}
