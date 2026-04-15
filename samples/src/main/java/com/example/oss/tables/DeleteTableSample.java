package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.DeleteTableRequest;
import com.aliyun.sdk.service.oss2.tables.models.DeleteTableResult;

public class DeleteTableSample implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String namespace,
            String tableName,
            String versionToken) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSTablesClientBuilder clientBuilder = OSSTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSTablesClient client = clientBuilder.build()) {

            DeleteTableRequest request = DeleteTableRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .namespace(namespace)
                    .name(tableName)
                    .versionToken(versionToken)
                    .build();

            DeleteTableResult result = client.deleteTable(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());
            System.out.printf("Successfully deleted table: %s/%s from bucket: %s%n", 
                    namespace, tableName, tableBucketARN);

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public org.apache.commons.cli.Options getOptions() {
        org.apache.commons.cli.Options opts = new org.apache.commons.cli.Options();
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("region").desc("The region in which the table bucket is located.").hasArg().required().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket containing the table to delete.").hasArg().required().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("namespace").desc("The namespace of the table to delete.").hasArg().required().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("tableName").desc("The name of the table to delete.").hasArg().required().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("versionToken").desc("The version token for optimistic locking.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(org.apache.commons.cli.CommandLine cmd) throws org.apache.commons.cli.ParseException {
        String endpoint = cmd.getOptionValue("endpoint");
        String region = cmd.getOptionValue("region");
        String tableBucketARN = cmd.getOptionValue("tableBucketARN");
        String namespace = cmd.getOptionValue("namespace");
        String tableName = cmd.getOptionValue("tableName");
        String versionToken = cmd.getOptionValue("versionToken");
        execute(endpoint, region, tableBucketARN, namespace, tableName, versionToken);
    }
}