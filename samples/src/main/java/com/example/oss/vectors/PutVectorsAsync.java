package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.PutVectorsRequest;
import com.aliyun.sdk.service.oss2.vectors.models.PutVectorsResult;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PutVectorsAsync implements Example {

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

            // Create sample vectors data
            List<Map<String, Object>> vectors = Arrays.asList(
                    createVector("key1", new Float[]{1.0f, 2.0f, 3.0f}, "metadata1"),
                    createVector("key2", new Float[]{4.0f, 5.0f, 6.0f}, "metadata2")
            );

            PutVectorsRequest request = PutVectorsRequest.newBuilder()
                    .bucket(bucket)
                    .indexName(indexName)
                    .vectors(vectors)
                    .build();

            CompletableFuture<PutVectorsResult> future = client.putVectorsAsync(request);

            PutVectorsResult result = future.get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

        } catch (Exception e) {
            System.out.printf("error:%n%s", e);
        }
    }

    private static Map<String, Object> createVector(String key, Float[] data, String metadata) {
        Map<String, Object> vector = new HashMap<>();
        vector.put("key", key);

        Map<String, Object> vectorData = new HashMap<>();
        vectorData.put("float32", Arrays.asList(data));
        vector.put("data", vectorData);

        Map<String, String> meta = new HashMap<>();
        meta.put("description", metadata);
        vector.put("metadata", meta);

        return vector;
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