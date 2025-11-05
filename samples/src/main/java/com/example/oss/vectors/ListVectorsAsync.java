package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorsRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorsResult;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.concurrent.CompletableFuture;

public class ListVectorsAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String indexName,
            Integer maxResults,
            String nextToken,
            Boolean returnData,
            Boolean returnMetadata,
            Integer segmentCount,
            Integer segmentIndex,
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

            ListVectorsRequest.Builder requestBuilder = ListVectorsRequest.newBuilder()
                    .bucket(bucket)
                    .indexName(indexName);

            if (maxResults != null) {
                requestBuilder.maxResults(maxResults);
            }

            if (nextToken != null) {
                requestBuilder.nextToken(nextToken);
            }

            if (returnData != null) {
                requestBuilder.returnData(returnData);
            }

            if (returnMetadata != null) {
                requestBuilder.returnMetadata(returnMetadata);
            }

            if (segmentCount != null) {
                requestBuilder.segmentCount(segmentCount);
            }

            if (segmentIndex != null) {
                requestBuilder.segmentIndex(segmentIndex);
            }

            CompletableFuture<ListVectorsResult> future = client.listVectorsAsync(requestBuilder.build());

            ListVectorsResult result = future.get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            System.out.printf("Next token: %s%n", result.nextToken());

            if (result.vectors() != null) {
                System.out.printf("Retrieved %d vectors%n", result.vectors().size());

                // Print detailed information for each vector
                for (int i = 0; i < result.vectors().size(); i++) {
                    com.aliyun.sdk.service.oss2.vectors.models.VectorsSummary vector = result.vectors().get(i);
                    System.out.printf("Vector %d:%n", i + 1);
                    System.out.printf("  Key: %s%n", vector.key());

                    // Print vector data if available
                    if (vector.data() != null) {
                        System.out.printf("  Data: %s%n", vector.data());
                    } else {
                        System.out.println("  Data: not returned");
                    }

                    // Print metadata if available
                    if (vector.metadata() != null) {
                        System.out.printf("  Metadata: %s%n", vector.metadata());
                    } else {
                        System.out.println("  Metadata: not returned");
                    }
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
        opts.addOption(Option.builder().longOpt("indexName").desc("The name of the index.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("maxResults").desc("The maximum number of vectors to return.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("nextToken").desc("The token for the next page of vectors.").hasArg().get());
        opts.addOption(Option.builder().longOpt("returnData").desc("Whether to return vector data (true/false).").hasArg().type(Boolean.class).get());
        opts.addOption(Option.builder().longOpt("returnMetadata").desc("Whether to return vector metadata (true/false).").hasArg().type(Boolean.class).get());
        opts.addOption(Option.builder().longOpt("segmentCount").desc("Number of concurrent segments.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("segmentIndex").desc("Current segment index.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String indexName = cmd.getParsedOptionValue("indexName");
        Integer maxResults = null;
        if (cmd.getParsedOptionValue("maxResults") != null) {
            maxResults = ((Number) cmd.getParsedOptionValue("maxResults")).intValue();
        }
        String nextToken = cmd.getParsedOptionValue("nextToken");
        Boolean returnData = null;
        if (cmd.getParsedOptionValue("returnData") != null) {
            returnData = Boolean.valueOf(cmd.getParsedOptionValue("returnData"));
        }
        Boolean returnMetadata = null;
        if (cmd.getParsedOptionValue("returnMetadata") != null) {
            returnMetadata = Boolean.valueOf(cmd.getParsedOptionValue("returnMetadata"));
        }
        Integer segmentCount = null;
        if (cmd.getParsedOptionValue("segmentCount") != null) {
            segmentCount = ((Number) cmd.getParsedOptionValue("segmentCount")).intValue();
        }
        Integer segmentIndex = null;
        if (cmd.getParsedOptionValue("segmentIndex") != null) {
            segmentIndex = ((Number) cmd.getParsedOptionValue("segmentIndex")).intValue();
        }
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, bucket, indexName, maxResults, nextToken, returnData, returnMetadata, segmentCount, segmentIndex, accountId);
    }
}