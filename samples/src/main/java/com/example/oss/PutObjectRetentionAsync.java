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

public class PutObjectRetentionAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String mode,
            String retainUntilDate,
            String versionId,
            String bypassGovernanceRetention) throws Exception {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncClientBuilder clientBuilder = OSSAsyncClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncClient client = clientBuilder.build()) {
            Retention retention = Retention.newBuilder()
                    .mode(mode)
                    .retainUntilDate(retainUntilDate)
                    .build();

            PutObjectRetentionRequest.Builder requestBuilder = PutObjectRetentionRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .retention(retention);

            if (versionId != null) {
                requestBuilder.versionId(versionId);
            }

            if (bypassGovernanceRetention != null) {
                requestBuilder.bypassGovernanceRetention(Boolean.parseBoolean(bypassGovernanceRetention));
            }

            PutObjectRetentionResult result = client.putObjectRetentionAsync(requestBuilder.build()).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

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
        opts.addOption(Option.builder().longOpt("mode").desc("The retention mode. Valid values: GOVERNANCE, COMPLIANCE.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("retainUntilDate").desc("The date until which the object is retained. ISO 8601 format.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("versionId").desc("The version id of the target object.").hasArg().get());
        opts.addOption(Option.builder().longOpt("bypassGovernanceRetention").desc("Whether to bypass governance retention.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        String mode = cmd.getParsedOptionValue("mode");
        String retainUntilDate = cmd.getParsedOptionValue("retainUntilDate");
        String versionId = cmd.getParsedOptionValue("versionId");
        String bypassGovernanceRetention = cmd.getParsedOptionValue("bypassGovernanceRetention");
        try {
            execute(endpoint, region, bucket, key, mode, retainUntilDate, versionId, bypassGovernanceRetention);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
