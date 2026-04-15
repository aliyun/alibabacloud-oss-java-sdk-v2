package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.GetTableBucketMaintenanceConfigurationRequest;
import com.aliyun.sdk.service.oss2.tables.models.GetTableBucketMaintenanceConfigurationResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetTableBucketMaintenanceConfigurationSample implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSTablesClientBuilder clientBuilder = OSSTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSTablesClient client = clientBuilder.build()) {

            GetTableBucketMaintenanceConfigurationRequest request = GetTableBucketMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .build();

            GetTableBucketMaintenanceConfigurationResult result = client.getTableBucketMaintenanceConfiguration(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());
            System.out.printf("Successfully got maintenance configuration for table bucket: %s%n", tableBucketARN);
            System.out.printf("Table Bucket ARN: %s%n", result.tableBucketARN());

            result.configuration().forEach((key, value) -> {
                System.out.printf("Configuration Type: %s, Status: %s%n", key, value.status());
            });


        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket to get maintenance configuration for.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String tableBucketARN = cmd.getParsedOptionValue("tableBucketARN");
        execute(endpoint, region, tableBucketARN);
    }
}