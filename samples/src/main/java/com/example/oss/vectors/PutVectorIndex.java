package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.PutVectorIndexRequest;
import com.aliyun.sdk.service.oss2.vectors.models.PutVectorIndexResult;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PutVectorIndex implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String indexName,
            String dataType,
            Integer dimension,
            String distanceMetric,
            String accountId) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSVectorsClientBuilder clientBuilder = OSSVectorsClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        if (accountId != null) {
            clientBuilder.accountId(accountId);
        }

        try (OSSVectorsClient client = clientBuilder.build()) {

            PutVectorIndexRequest.Builder requestBuilder = PutVectorIndexRequest.newBuilder()
                    .bucket(bucket)
                    .indexName(indexName)
                    .dataType(dataType)
                    .dimension(dimension)
                    .distanceMetric(distanceMetric);

            // Add sample metadata
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("nonFilterableMetadataKeys", Arrays.asList("nonKey1", "nonKey2"));
            requestBuilder.metadata(metadata);

            PutVectorIndexRequest request = requestBuilder.build();
            PutVectorIndexResult result = client.putVectorIndex(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

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
        opts.addOption(Option.builder().longOpt("dataType").desc("The data type of the vector (e.g., float32).").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("dimension").desc("The dimension of the vector.").hasArg().required().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("distanceMetric").desc("The distance metric used for the index (e.g., EuclideanDistance).").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String indexName = cmd.getParsedOptionValue("indexName");
        String dataType = cmd.getParsedOptionValue("dataType");
        Integer dimension = ((Number) cmd.getParsedOptionValue("dimension")).intValue();
        String distanceMetric = cmd.getParsedOptionValue("distanceMetric");
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, bucket, indexName, dataType, dimension, distanceMetric, accountId);
    }
}