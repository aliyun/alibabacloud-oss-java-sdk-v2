package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.AnalyzerParameters;
import com.aliyun.sdk.service.oss2.vectors.models.AnalyzerType;
import com.aliyun.sdk.service.oss2.vectors.models.DistanceMetricType;
import com.aliyun.sdk.service.oss2.vectors.models.FieldSchema;
import com.aliyun.sdk.service.oss2.vectors.models.FieldType;
import com.aliyun.sdk.service.oss2.vectors.models.IndexModeType;
import com.aliyun.sdk.service.oss2.vectors.models.PutVectorIndexFusionRequest;
import com.aliyun.sdk.service.oss2.vectors.models.PutVectorIndexFusionResult;
import com.aliyun.sdk.service.oss2.vectors.models.SchemaConfiguration;
import com.aliyun.sdk.service.oss2.vectors.models.TextSchema;
import com.aliyun.sdk.service.oss2.vectors.models.VectorDataType;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PutVectorIndexFusionAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String indexName,
            String vectorField,
            String dataType,
            Integer dimension,
            String distanceMetric,
            String partitionKeyField,
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

            // The type/dataType/distanceMetric/analyzer/mode fields all provide two
            // overloads: a String one and a type-safe enum one (FieldType, VectorDataType,
            // DistanceMetricType, AnalyzerType, IndexModeType). The enum is serialized to
            // the same value, for example FieldType.VECTOR is written as "vector".
            // Using the enum overloads below is the recommended, compile-time-safe way.

            // The vector field of the fusion index.
            FieldSchema vector = FieldSchema.newBuilder()
                    .name(vectorField)
                    .type(FieldType.VECTOR)
                    // dataType comes from the command line, fromString bridges it to the enum.
                    .dataType(VectorDataType.fromString(dataType))
                    .dimension(dimension)
                    .distanceMetric(DistanceMetricType.fromString(distanceMetric))
                    .build();

            // The partition key field. Only one string field can be the partition key.
            FieldSchema partitionKey = FieldSchema.newBuilder()
                    .name(partitionKeyField)
                    .type(FieldType.STRING)
                    .isPartitionKey(true)
                    .build();

            // A string field with the full text search enabled.
            FieldSchema title = FieldSchema.newBuilder()
                    .name("title")
                    .type(FieldType.STRING)
                    .exactMatch(true)
                    .text(TextSchema.newBuilder()
                            .enabled(true)
                            .analyzer(AnalyzerType.STANDARD)
                            .analyzerParameters(AnalyzerParameters.newBuilder()
                                    .caseSensitive(false)
                                    .delimitWord(false)
                                    .build())
                            .build())
                    .build();

            // An array field.
            FieldSchema timestamps = FieldSchema.newBuilder()
                    .name("timestamps")
                    .type(FieldType.LONG)
                    .isArray(true)
                    .build();

            List<FieldSchema> fields = Arrays.asList(vector, partitionKey, title, timestamps);

            PutVectorIndexFusionRequest request = PutVectorIndexFusionRequest.newBuilder()
                    .bucket(bucket)
                    .indexName(indexName)
                    // The mode is fusion by default, it can be omitted.
                    // IndexModeType.FUSION is equivalent to the String "fusion".
                    .mode(IndexModeType.FUSION)
                    .schemaConfiguration(SchemaConfiguration.newBuilder().fields(fields).build())
                    .build();

            CompletableFuture<PutVectorIndexFusionResult> future = client.putVectorIndexFusionAsync(request);

            PutVectorIndexFusionResult result = future.get();

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
        opts.addOption(Option.builder().longOpt("vectorField").desc("The name of the vector field.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("dataType").desc("The data type of the vector (e.g., float32).").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("dimension").desc("The dimension of the vector.").hasArg().required().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("distanceMetric").desc("The distance metric used for the index (e.g., euclidean).").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("partitionKeyField").desc("The name of the string field used as the partition key.").hasArg().required().get());
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
        String dataType = cmd.getParsedOptionValue("dataType");
        Integer dimension = ((Number) cmd.getParsedOptionValue("dimension")).intValue();
        String distanceMetric = cmd.getParsedOptionValue("distanceMetric");
        String partitionKeyField = cmd.getParsedOptionValue("partitionKeyField");
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, bucket, indexName, vectorField, dataType, dimension,
                distanceMetric, partitionKeyField, accountId);
    }
}
