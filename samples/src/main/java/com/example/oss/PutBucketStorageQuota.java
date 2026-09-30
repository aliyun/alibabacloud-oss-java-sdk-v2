package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.QuotaConfiguration;
import com.aliyun.sdk.service.oss2.models.PutBucketStorageQuotaRequest;
import com.aliyun.sdk.service.oss2.models.PutBucketStorageQuotaResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class PutBucketStorageQuota implements Example {

    private static void execute(String endpoint, String region, String bucket, Long storageQuota, String mode) {
        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            PutBucketStorageQuotaRequest request = PutBucketStorageQuotaRequest.newBuilder()
                    .bucket(bucket).quotaConfiguration(QuotaConfiguration.newBuilder()
                            .storageQuota(storageQuota)
                            .mode(mode)
                            .build())
                    .build();

            PutBucketStorageQuotaResult result = client.putBucketStorageQuota(request);

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
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("storage-quota").desc("The storage quota of the bucket. Unit: bytes.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("mode").desc("The mode of the storage quota. Valid values: Strict or Warning.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        Long storageQuota = ((Number) cmd.getParsedOptionValue("storage-quota")).longValue();
        String mode = cmd.getParsedOptionValue("mode");
        execute(endpoint, region, bucket, storageQuota, mode);
    }
}
