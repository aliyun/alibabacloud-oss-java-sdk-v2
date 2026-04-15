package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class PutTableMaintenanceConfigurationSample implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String namespace,
            String name,
            String type,
            String status,
            Integer targetFileSizeMB,
            String strategy) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSTablesClientBuilder clientBuilder = OSSTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSTablesClient client = clientBuilder.build()) {

            TableMaintenanceSettings settings = null;
            if ("icebergCompaction".equals(type)) {
                IcebergCompactionSettings compactionSettings = IcebergCompactionSettings.newBuilder()
                        .targetFileSizeMB(targetFileSizeMB)
                        .strategy(strategy)
                        .build();
                settings = TableMaintenanceSettings.newBuilder()
                        .icebergCompaction(compactionSettings)
                        .build();
            }

            TableMaintenanceConfigurationValue value = TableMaintenanceConfigurationValue.newBuilder()
                    .status(status)
                    .settings(settings)
                    .build();

            PutTableMaintenanceConfigurationRequest request = PutTableMaintenanceConfigurationRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .namespace(namespace)
                    .name(name)
                    .type(type)
                    .value(value)
                    .build();

            PutTableMaintenanceConfigurationResult result = client.putTableMaintenanceConfiguration(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());
            System.out.printf("Successfully put table maintenance configuration for table: %s/%s, type: %s%n", namespace, name, type);

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("namespace").desc("The namespace of the table.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("name").desc("The name of the table.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("type").desc("The maintenance type (icebergCompaction or icebergSnapshotManagement).").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("status").desc("The status (enabled or disabled).").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("targetFileSizeMB").desc("Target file size in MB for compaction.").hasArg().get());
        opts.addOption(Option.builder().longOpt("strategy").desc("Compaction strategy (auto/binpack/sort/z-order).").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String tableBucketARN = cmd.getParsedOptionValue("tableBucketARN");
        String namespace = cmd.getParsedOptionValue("namespace");
        String name = cmd.getParsedOptionValue("name");
        String type = cmd.getParsedOptionValue("type");
        String status = cmd.getParsedOptionValue("status");
        String targetFileSizeMBStr = cmd.getParsedOptionValue("targetFileSizeMB");
        Integer targetFileSizeMB = targetFileSizeMBStr != null ? Integer.parseInt(targetFileSizeMBStr) : null;
        String strategy = cmd.getParsedOptionValue("strategy");
        execute(endpoint, region, tableBucketARN, namespace, name, type, status, targetFileSizeMB, strategy);
    }
}
