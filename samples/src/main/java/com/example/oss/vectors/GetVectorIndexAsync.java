package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.GetVectorIndexRequest;
import com.aliyun.sdk.service.oss2.vectors.models.GetVectorIndexResult;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.concurrent.CompletableFuture;

public class GetVectorIndexAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String indexName,
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

            GetVectorIndexRequest.Builder requestBuilder = GetVectorIndexRequest.newBuilder()
                    .bucket(bucket)
                    .indexName(indexName);

            CompletableFuture<GetVectorIndexResult> future = client.getVectorIndexAsync(requestBuilder.build());

            GetVectorIndexResult result = future.get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.index() != null) {
                System.out.printf("Index name: %s%n", result.index().indexName());
                System.out.printf("Data type: %s%n", result.index().dataType());
                System.out.printf("Dimension: %d%n", result.index().dimension());
                System.out.printf("Distance metric: %s%n", result.index().distanceMetric());
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
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String indexName = cmd.getParsedOptionValue("indexName");
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, bucket, indexName, accountId);
    }
}