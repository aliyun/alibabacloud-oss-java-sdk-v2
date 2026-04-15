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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateTableSampleAsync implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String namespace,
            String name,
            String format) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncTablesClientBuilder clientBuilder = OSSAsyncTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncTablesClient client = clientBuilder.build()) {

            // Create schema fields
            List<SchemaField> fields = new ArrayList<>();
            fields.add(SchemaField.newBuilder()
                .name("id")
                .type("long")
                .required(true)
                .build());
            fields.add(SchemaField.newBuilder()
                .name("name")
                .type("string")
                .required(false)
                .build());
            fields.add(SchemaField.newBuilder()
                .name("ts")
                .type("timestamptz")
                .required(false)
                .build());

            // Create schema
            IcebergSchema icebergSchema = IcebergSchema.newBuilder()
                .fields(fields)
                .build();

            // Create partition spec
            IcebergPartitionField partitionField = IcebergPartitionField.newBuilder()
                .sourceId(2)
                .transform("identity")
                .name("region")
                .fieldId(1001)
                .build();
            List<IcebergPartitionField> partitionFields = new ArrayList<>();
            partitionFields.add(partitionField);
            IcebergPartitionSpec partitionSpec = IcebergPartitionSpec.newBuilder()
                .specId(0)
                .fields(partitionFields)
                .build();

            // Create iceberg metadata
            IcebergMetadata icebergMetadata = IcebergMetadata.newBuilder()
                .schema(icebergSchema)
                .partitionSpec(partitionSpec)
                .build();

            // Set metadata
            TableMetadata metadata = TableMetadata.newBuilder()
                .iceberg(icebergMetadata)
                .build();

            // Add encryption configuration
            EncryptionConfiguration encryptionConfig = EncryptionConfiguration.newBuilder()
                .sseAlgorithm("AES256")
                .build();

            CreateTableRequest request = CreateTableRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .namespace(namespace)
                    .name(name)
                    .format(format)
                    .metadata(metadata)
                    .encryptionConfiguration(encryptionConfig)
                    .build();

            CompletableFuture<CreateTableResult> future = client.createTableAsync(request);

            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    System.out.printf("Error:%n%s", throwable);
                } else {
                    System.out.printf("Status code:%d, request id:%s%n",
                            result.statusCode(), result.requestId());
                    System.out.printf("Created table with ARN: %s%n", result.tableARN());
                    System.out.printf("Version token: %s%n", result.versionToken());
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
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("namespace").desc("The namespace of the table.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("name").desc("The name of the table.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("format").desc("The format of the table (currently only 'iceberg' is supported).").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String tableBucketARN = cmd.getParsedOptionValue("tableBucketARN");
        String namespace = cmd.getParsedOptionValue("namespace");
        String name = cmd.getParsedOptionValue("name");
        String format = cmd.getParsedOptionValue("format");
        execute(endpoint, region, tableBucketARN, namespace, name, format);
    }
}