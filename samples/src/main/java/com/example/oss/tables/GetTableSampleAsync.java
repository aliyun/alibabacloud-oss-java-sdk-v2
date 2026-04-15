package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSAsyncTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSAsyncTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.GetTableRequest;
import com.aliyun.sdk.service.oss2.tables.models.GetTableResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.concurrent.CompletableFuture;

public class GetTableSampleAsync implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String namespace,
            String tableName,
            String tableArn) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncTablesClientBuilder clientBuilder = OSSAsyncTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncTablesClient client = clientBuilder.build()) {

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

            CompletableFuture<GetTableResult> future = client.getTableAsync(request);

            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    System.out.printf("Error:%n%s", throwable);
                } else {
                    System.out.printf("Status code:%d, request id:%s%n",
                            result.statusCode(), result.requestId());
                    System.out.printf("Table name: %s%n", result.name());
                    System.out.printf("Table ARN: %s%n", result.tableARN());
                    System.out.printf("Format: %s%n", result.format());
                    System.out.printf("Created by: %s%n", result.createdBy());
                    System.out.printf("Created at: %s%n", result.createdAt());
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
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket containing the table to get.").hasArg().get());
        opts.addOption(Option.builder().longOpt("namespace").desc("The namespace of the table to get.").hasArg().get());
        opts.addOption(Option.builder().longOpt("tableName").desc("The name of the table to get.").hasArg().get());
        opts.addOption(Option.builder().longOpt("tableArn").desc("The ARN of the table to get.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getOptionValue("endpoint");
        String region = cmd.getOptionValue("region");
        String tableBucketARN = cmd.getOptionValue("tableBucketARN");
        String namespace = cmd.getOptionValue("namespace");
        String tableName = cmd.getOptionValue("tableName");
        String tableArn = cmd.getOptionValue("tableArn");
        execute(endpoint, region, tableBucketARN, namespace, tableName, tableArn);
    }
}