package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.ListTablesRequest;
import com.aliyun.sdk.service.oss2.tables.models.ListTablesResult;
import com.aliyun.sdk.service.oss2.tables.models.TableSummary;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ListTablesSample implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String continuationToken,
            Integer maxTables,
            String namespace,
            String prefix) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSTablesClientBuilder clientBuilder = OSSTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSTablesClient client = clientBuilder.build()) {

            ListTablesRequest.Builder requestBuilder = ListTablesRequest.newBuilder()
                    .tableBucketARN(tableBucketARN);
            
            if (continuationToken != null) {
                requestBuilder.continuationToken(continuationToken);
            }
            
            if (maxTables != null) {
                requestBuilder.maxTables(maxTables);
            }
            
            if (namespace != null) {
                requestBuilder.namespace(namespace);
            }
            
            if (prefix != null) {
                requestBuilder.prefix(prefix);
            }

            ListTablesResult result = client.listTables(requestBuilder.build());

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());
            System.out.printf("Continuation token: %s%n", result.continuationToken());
            
            if (result.tables() != null) {
                System.out.printf("Number of tables: %d%n", result.tables().size());
                for (int i = 0; i < result.tables().size(); i++) {
                    TableSummary table = result.tables().get(i);
                    System.out.printf("Table %d:%n", i + 1);
                    System.out.printf("  Name: %s%n", table.name());
                    System.out.printf("  Namespace: %s%n", table.namespace());
                    System.out.printf("  ARN: %s%n", table.tableARN());
                    System.out.printf("  Type: %s%n", table.type());
                    System.out.printf("  Created at: %s%n", table.createdAt());
                    System.out.printf("  Modified at: %s%n", table.modifiedAt());
                }
            } else {
                System.out.println("No tables found.");
            }

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket to list tables from.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("continuationToken").desc("A token to retrieve the next page of results.").hasArg().get());
        opts.addOption(Option.builder().longOpt("maxTables").desc("The maximum number of tables to return.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("namespace").desc("The namespace to filter tables by.").hasArg().get());
        opts.addOption(Option.builder().longOpt("prefix").desc("The prefix to filter tables by.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String tableBucketARN = cmd.getParsedOptionValue("tableBucketARN");
        String continuationToken = cmd.getParsedOptionValue("continuationToken");
        Number maxTablesNumber = cmd.getParsedOptionValue("maxTables");
        Integer maxTables = maxTablesNumber != null ? maxTablesNumber.intValue() : null;
        String namespace = cmd.getParsedOptionValue("namespace");
        String prefix = cmd.getParsedOptionValue("prefix");
        execute(endpoint, region, tableBucketARN, continuationToken, maxTables, namespace, prefix);
    }
}