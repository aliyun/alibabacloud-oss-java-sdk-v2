package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.GetTableRequest;
import com.aliyun.sdk.service.oss2.tables.models.GetTableResult;

public class GetTableSample implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String namespace,
            String tableName,
            String tableArn) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSTablesClientBuilder clientBuilder = OSSTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSTablesClient client = clientBuilder.build()) {

            GetTableRequest request;
            if (tableArn != null) {
                request = GetTableRequest.newBuilder()
                        .tableArn(tableArn)
                        .build();
            } else {
                request = GetTableRequest.newBuilder()
                        .tableBucketARN(tableBucketARN)
                        .namespace(namespace)
                        .name(tableName)
                        .build();
            }

            GetTableResult result = client.getTable(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());
            System.out.printf("Table name: %s%n", result.name());
            System.out.printf("Table ARN: %s%n", result.tableARN());
            System.out.printf("Format: %s%n", result.format());
            System.out.printf("Created by: %s%n", result.createdBy());
            System.out.printf("Created at: %s%n", result.createdAt());

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public org.apache.commons.cli.Options getOptions() {
        org.apache.commons.cli.Options opts = new org.apache.commons.cli.Options();
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("region").desc("The region in which the table bucket is located.").hasArg().required().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket containing the table to get.").hasArg().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("namespace").desc("The namespace of the table to get.").hasArg().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("tableName").desc("The name of the table to get.").hasArg().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("tableArn").desc("The ARN of the table to get.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(org.apache.commons.cli.CommandLine cmd) throws org.apache.commons.cli.ParseException {
        String endpoint = cmd.getOptionValue("endpoint");
        String region = cmd.getOptionValue("region");
        String tableBucketARN = cmd.getOptionValue("tableBucketARN");
        String namespace = cmd.getOptionValue("namespace");
        String tableName = cmd.getOptionValue("tableName");
        String tableArn = cmd.getOptionValue("tableArn");
        execute(endpoint, region, tableBucketARN, namespace, tableName, tableArn);
    }
}