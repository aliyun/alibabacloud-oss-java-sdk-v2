package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSAsyncTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSAsyncTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.concurrent.CompletableFuture;

public class PutTableBucketMaintenanceConfigurationSampleAsync implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String type,
            String status,
            Integer unreferencedDays,
            Integer nonCurrentDays) {

        
        // Build the request with only provided values, without setting defaults
        IcebergUnreferencedFileRemovalSettings.Builder removalSettingsBuilder = IcebergUnreferencedFileRemovalSettings.newBuilder();
        if (unreferencedDays != null) {
            removalSettingsBuilder.unreferencedDays(unreferencedDays);
        }
        if (nonCurrentDays != null) {
            removalSettingsBuilder.nonCurrentDays(nonCurrentDays);
        }
        
        TableBucketMaintenanceSettings settings = TableBucketMaintenanceSettings.newBuilder()
                .icebergUnreferencedFileRemoval(removalSettingsBuilder.build())
                .build();
        
        TableBucketMaintenanceConfigurationValue.Builder valueBuilder = 
            TableBucketMaintenanceConfigurationValue.newBuilder()
                .settings(settings);
        if (status != null && !status.isEmpty()) {
            valueBuilder.status(status);
        }
        
        TableBucketMaintenanceConfigurationValue value = valueBuilder.build();

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncTablesClientBuilder clientBuilder = OSSAsyncTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncTablesClient client = clientBuilder.build()) {

            PutTableBucketMaintenanceConfigurationRequest request = PutTableBucketMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .type(type)
                    .value(value)
                    .build();

            CompletableFuture<PutTableBucketMaintenanceConfigurationResult> future = client.putTableBucketMaintenanceConfigurationAsync(request);

            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    System.out.printf("Error:%n%s", throwable);
                } else {
                    System.out.printf("Status code:%d, request id:%s%n",
                            result.statusCode(), result.requestId());
                    System.out.printf("Successfully configured maintenance for table bucket: %s%n", tableBucketARN);
                }
            }).join(); // Wait for the async operation to complete

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket to configure maintenance for.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("type").desc("The type of maintenance configuration.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("status").desc("The status of the maintenance configuration (enabled/disabled).").hasArg().get());
        opts.addOption(Option.builder().longOpt("unreferencedDays").desc("Number of days after which unreferenced files are removed.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("nonCurrentDays").desc("Number of days after which non-current versions are removed.").hasArg().type(Number.class).get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String tableBucketARN = cmd.getParsedOptionValue("tableBucketARN");
        String type = cmd.getParsedOptionValue("type");
        String status = cmd.getParsedOptionValue("status");
        Integer unreferencedDays = cmd.getParsedOptionValue("unreferencedDays");
        Integer nonCurrentDays = cmd.getParsedOptionValue("nonCurrentDays");
        execute(endpoint, region, tableBucketARN, type, status, unreferencedDays, nonCurrentDays);
    }
}