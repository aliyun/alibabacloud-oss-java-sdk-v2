package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.Knn;
import com.aliyun.sdk.service.oss2.vectors.models.QueryVectorsFusionRequest;
import com.aliyun.sdk.service.oss2.vectors.models.QueryVectorsFusionResult;
import com.aliyun.sdk.service.oss2.vectors.models.QueryVectorsFusionSummary;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class QueryVectorsFusionAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String indexName,
            String vectorField,
            String queryVector,
            Integer topK,
            Integer limit,
            Boolean returnMetadata,
            String partitionKeys,
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

            // The knn query of the user defined vector field.
            Knn knn = Knn.newBuilder()
                    .field(vectorField)
                    .queryVector(parseVector(queryVector))
                    .topK(topK)
                    .build();

            // Besides the knn query, a fusion query can also carry a multi-way hybrid
            // retriever. The RetrieverComponent.normalizer field provides a String overload
            // and a type-safe NormalizerType enum overload (none/minMax/l2). The enum is
            // serialized to the same value, for example NormalizerType.MIN_MAX -> "minMax".
            // An enum based weight retriever example (uncomment and add the imports for
            // Retriever/WeightRetriever/RetrieverComponent/SubRetriever/NormalizerType):
            //
            // Retriever retriever = Retriever.newBuilder()
            //         .weight(WeightRetriever.newBuilder()
            //                 .windowSize(100)
            //                 .retrievers(Arrays.asList(
            //                         RetrieverComponent.newBuilder()
            //                                 .retriever(SubRetriever.newBuilder()
            //                                         .knn(knn)
            //                                         .build())
            //                                 .weight(0.7f)
            //                                 .normalizer(NormalizerType.MIN_MAX)
            //                                 .build()))
            //                 .build())
            //         .build();
            // Then set it on the request builder: requestBuilder.retriever(retriever);

            QueryVectorsFusionRequest.Builder requestBuilder = QueryVectorsFusionRequest.newBuilder()
                    .bucket(bucket)
                    .indexName(indexName)
                    .knn(Arrays.asList(knn))
                    .limit(limit)
                    .returnMetadata(returnMetadata);

            if (partitionKeys != null) {
                requestBuilder.partitionKeys(Arrays.asList(partitionKeys.split(",")));
            }

            QueryVectorsFusionRequest request = requestBuilder.build();

            CompletableFuture<QueryVectorsFusionResult> future = client.queryVectorsFusionAsync(request);

            QueryVectorsFusionResult result = future.get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            List<QueryVectorsFusionSummary> vectors = result.vectors();
            if (vectors.size() > 0) {
                System.out.printf("Found %d matching vectors%n", vectors.size());

                for (int i = 0; i < vectors.size(); i++) {
                    QueryVectorsFusionSummary vector = vectors.get(i);
                    System.out.printf("Vector %d:%n", i + 1);
                    System.out.printf("  Key: %s%n", vector.key());

                    if (vector.score() != null) {
                        System.out.printf("  Score: %s%n", vector.score());
                    } else {
                        System.out.println("  Score: not returned");
                    }

                    if (vector.metadata() != null) {
                        System.out.printf("  Metadata: %s%n", vector.metadata());
                    } else {
                        System.out.println("  Metadata: not returned");
                    }
                }
            }

            if (result.nextToken() != null) {
                System.out.printf("Next token: %s%n", result.nextToken());
            }

        } catch (Exception e) {
            System.out.printf("error:%n%s", e);
        }
    }

    private static List<Float> parseVector(String vectorStr) {
        String[] parts = vectorStr.split(",");
        List<Float> vector = new ArrayList<Float>(parts.length);
        for (int i = 0; i < parts.length; i++) {
            vector.add(Float.parseFloat(parts[i].trim()));
        }
        return vector;
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("indexName").desc("The name of the index.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("vectorField").desc("The name of the vector field to query.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("queryVector").desc("The query vector as comma-separated values (e.g., '1.0,2.0,3.0').").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("topK").desc("The number of top K vectors to return.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("limit").desc("The number of the rows returned by the request.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("returnMetadata").desc("Whether to return metadata (true/false).").hasArg().type(Boolean.class).get());
        opts.addOption(Option.builder().longOpt("partitionKeys").desc("The partition keys as comma-separated values.").hasArg().get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String indexName = cmd.getParsedOptionValue("indexName");
        String vectorField = cmd.getParsedOptionValue("vectorField");
        String queryVector = cmd.getParsedOptionValue("queryVector");
        Integer topK = null;
        if (cmd.getParsedOptionValue("topK") != null) {
            topK = ((Number) cmd.getParsedOptionValue("topK")).intValue();
        }
        Integer limit = 10;
        if (cmd.getParsedOptionValue("limit") != null) {
            limit = ((Number) cmd.getParsedOptionValue("limit")).intValue();
        }
        Boolean returnMetadata = false;
        if (cmd.getParsedOptionValue("returnMetadata") != null) {
            returnMetadata = Boolean.valueOf(cmd.getParsedOptionValue("returnMetadata"));
        }
        String partitionKeys = cmd.getParsedOptionValue("partitionKeys");
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, bucket, indexName, vectorField, queryVector, topK, limit,
                returnMetadata, partitionKeys, accountId);
    }
}
