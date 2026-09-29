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

public class CreateBucketMetadataConfigurationAsync implements Example {

    private static void execute(String endpoint, String region, String bucket) {
        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {
            // Build the journal and inventory table configuration.
            MetadataConfiguration configuration = MetadataConfiguration.newBuilder()
                    .journalTableConfiguration(JournalTableConfiguration.newBuilder()
                            .recordExpiration(MetadataTableRecordExpiration.newBuilder()
                                    .expiration("ENABLED")
                                    .days(30)
                                    .build())
                            .encryptionConfiguration(MetadataTableEncryptionConfiguration.newBuilder()
                                    .sseAlgorithm("AES256")
                                    .build())
                            .build())
                    .inventoryTableConfiguration(InventoryTableConfiguration.newBuilder()
                            .configurationState("ENABLED")
                            .encryptionConfiguration(MetadataTableEncryptionConfiguration.newBuilder()
                                    .sseAlgorithm("AES256")
                                    .build())
                            .build())
                    .build();

            CreateBucketMetadataConfigurationResult result = client.createBucketMetadataConfigurationAsync(
                    CreateBucketMetadataConfigurationRequest.newBuilder()
                            .bucket(bucket)
                            .metadataConfiguration(configuration)
                            .build()).get();
            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());
        } catch (Exception e) {
            System.out.printf("error:\n%s", e);
        }
    }

    private static OSSAsyncClient getDefaultAsyncClient(
            String endpoint, String region, CredentialsProvider provider) {
        OSSAsyncClientBuilder builder = OSSAsyncClient.newBuilder()
                .region(region)
                .credentialsProvider(provider);
        if (endpoint != null) {
            builder.endpoint(endpoint);
        }
        return builder.build();
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
        execute(cmd.getParsedOptionValue("endpoint"),
                cmd.getParsedOptionValue("region"),
                cmd.getParsedOptionValue("bucket"));
    }
}
