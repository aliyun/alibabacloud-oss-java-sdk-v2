package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorIndexesRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorIndexesResult;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.concurrent.CompletableFuture;

public class ListVectorIndexesAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            Long maxResults,
            String nextToken,
            String prefix,
            String accountId) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncVectorsClientBuilder clientBuilder = OSSAsyncVectorsClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        if (accountId != null) {
            clientBuilder.accountId(accountId);
        }

        try (OSSAsyncVectorsClient client = clientBuilder.build()) {

            ListVectorIndexesRequest.Builder requestBuilder = ListVectorIndexesRequest.newBuilder()
                    .bucket(bucket);

            if (maxResults != null) {
                requestBuilder.maxResults(maxResults);
            }

            if (nextToken != null) {
                requestBuilder.nextToken(nextToken);
            }

            if (prefix != null) {
                requestBuilder.prefix(prefix);
            }

            ListVectorIndexesRequest request = requestBuilder.build();
            CompletableFuture<ListVectorIndexesResult> future = client.listVectorIndexesAsync(request);

            ListVectorIndexesResult result = future.get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            System.out.printf("Next token: %s%n", result.nextToken());
            
            if (result.indexes().size() > 0) {
                System.out.printf("Found %d indexes%n", result.indexes().size());
                
                for (int i = 0; i < result.indexes().size(); i++) {
                    System.out.printf("  Index %d: %s%n", i+1, result.indexes().get(i).indexName());
                    System.out.printf("  dataType %d: %s%n", i+1, result.indexes().get(i).dataType());
                    System.out.printf("  dimension %d: %s%n", i+1, result.indexes().get(i).dimension());
                    System.out.printf("  distanceMetric %d: %s%n", i+1, result.indexes().get(i).distanceMetric());
                    System.out.printf("  metadata %d: %s%n", i+1, result.indexes().get(i).metadata());
                }
            }

        } catch (Exception e) {
            System.out.printf("error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("maxResults").desc("The maximum number of indexes to return.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("nextToken").desc("The token for the next page of indexes.").hasArg().get());
        opts.addOption(Option.builder().longOpt("prefix").desc("The prefix to filter indexes by name.").hasArg().get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        Long maxResults = null;
        if (cmd.getParsedOptionValue("maxResults") != null) {
            maxResults = ((Number) cmd.getParsedOptionValue("maxResults")).longValue();
        }
        String nextToken = cmd.getParsedOptionValue("nextToken");
        String prefix = cmd.getParsedOptionValue("prefix");
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, bucket, maxResults, nextToken, prefix, accountId);
    }
}