package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSAsyncTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSAsyncTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.UpdateTableMetadataLocationRequest;
import com.aliyun.sdk.service.oss2.tables.models.UpdateTableMetadataLocationResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.concurrent.CompletableFuture;

public class UpdateTableMetadataLocationSampleAsync implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String namespace,
            String name,
            String versionToken,
            String metadataLocation) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncTablesClientBuilder clientBuilder = OSSAsyncTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncTablesClient client = clientBuilder.build()) {

            UpdateTableMetadataLocationRequest request = UpdateTableMetadataLocationRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .namespace(namespace)
                    .name(name)
                    .versionToken(versionToken)
                    .metadataLocation(metadataLocation)
                    .build();

            CompletableFuture<UpdateTableMetadataLocationResult> future = client.updateTableMetadataLocationAsync(request);

            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    System.out.printf("Error:%n%s", throwable);
                } else {
                    System.out.printf("Status code:%d, request id:%s%n",
                            result.statusCode(), result.requestId());
                    System.out.printf("Successfully updated table metadata location for table: %s%n", name);
                    System.out.printf("New metadata location: %s%n", result.metadataLocation());
                    System.out.printf("New version token: %s%n", result.versionToken());
                }
            }).join();

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
        opts.addOption(Option.builder().longOpt("versionToken").desc("The version token of the current metadata location.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("metadataLocation").desc("The new metadata location to set.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String tableBucketARN = cmd.getParsedOptionValue("tableBucketARN");
        String namespace = cmd.getParsedOptionValue("namespace");
        String name = cmd.getParsedOptionValue("name");
        String versionToken = cmd.getParsedOptionValue("versionToken");
        String metadataLocation = cmd.getParsedOptionValue("metadataLocation");
        execute(endpoint, region, tableBucketARN, namespace, name, versionToken, metadataLocation);
    }
}
