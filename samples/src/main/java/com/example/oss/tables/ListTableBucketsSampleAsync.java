package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSAsyncTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSAsyncTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.ListTableBucketsRequest;
import com.aliyun.sdk.service.oss2.tables.models.ListTableBucketsResult;
import com.aliyun.sdk.service.oss2.tables.models.TableBucketSummary;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.concurrent.CompletableFuture;

public class ListTableBucketsSampleAsync implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String continuationToken,
            Integer maxBuckets,
            String prefix) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncTablesClientBuilder clientBuilder = OSSAsyncTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncTablesClient client = clientBuilder.build()) {

            ListTableBucketsRequest.Builder requestBuilder = ListTableBucketsRequest.newBuilder();
            
            if (continuationToken != null) {
                requestBuilder.continuationToken(continuationToken);
            }
            
            if (maxBuckets != null) {
                requestBuilder.maxBuckets(maxBuckets);
            }
            
            if (prefix != null) {
                requestBuilder.prefix(prefix);
            }

            CompletableFuture<ListTableBucketsResult> future = client.listTableBucketsAsync(requestBuilder.build());

            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    System.out.printf("Error:%n%s", throwable);
                } else {
                    System.out.printf("Status code:%d, request id:%s%n",
                            result.statusCode(), result.requestId());
                    System.out.printf("Continuation token: %s%n", result.continuationToken());
                    System.out.printf("Number of buckets: %d%n", result.tableBuckets().size());

                    for (int i = 0; i < result.tableBuckets().size(); i++) {
                        TableBucketSummary bucket = result.tableBuckets().get(i);
                        System.out.printf("Bucket %d:%n", i + 1);
                        System.out.printf("  ARN: %s%n", bucket.arn());
                        System.out.printf("  Name: %s%n", bucket.name());
                        System.out.printf("  ID: %s%n", bucket.tableBucketId());
                        System.out.printf("  Owner Account ID: %s%n", bucket.ownerAccountId());
                        System.out.printf("  Created At: %s%n", bucket.createdAt());
                    }
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
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table buckets are located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("continuationToken").desc("The continuation token for pagination.").hasArg().get());
        opts.addOption(Option.builder().longOpt("maxBuckets").desc("The maximum number of buckets to return.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("prefix").desc("The prefix used to filter the table buckets.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String continuationToken = cmd.getParsedOptionValue("continuationToken");
        Number maxBucketsNumber = cmd.getParsedOptionValue("maxBuckets");
        Integer maxBuckets = maxBucketsNumber != null ? maxBucketsNumber.intValue() : null;
        String prefix = cmd.getParsedOptionValue("prefix");
        execute(endpoint, region, continuationToken, maxBuckets, prefix);
    }
}