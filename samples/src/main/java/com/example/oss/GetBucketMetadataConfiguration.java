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

public class GetBucketMetadataConfiguration implements Example {

    private static void execute(String endpoint, String region, String bucket) {
        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);
        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            // Get the bucket metadata table configuration.
            GetBucketMetadataConfigurationResult result = client.getBucketMetadataConfiguration(
                    GetBucketMetadataConfigurationRequest.newBuilder()
                            .bucket(bucket)
                            .build());
            MetadataConfigurationResult configuration = result.metadataConfigurationResult();
            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());
            System.out.printf("Table bucket ARN:%s, journal status:%s, inventory state:%s\n",
                    configuration.destinationResult().tableBucketArn(),
                    configuration.journalTableConfigurationResult().tableStatus(),
                    configuration.inventoryTableConfigurationResult().configurationState());
        } catch (Exception e) {
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
        execute(cmd.getParsedOptionValue("endpoint"),
                cmd.getParsedOptionValue("region"),
                cmd.getParsedOptionValue("bucket"));
    }
}
