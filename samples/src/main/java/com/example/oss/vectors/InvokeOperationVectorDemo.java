package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClientBuilder;
import com.example.oss.Example;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class InvokeOperationVectorDemo implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String accountId,
            String indexName) {

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

        String dimension = "5";

        try (OSSVectorsClient client = clientBuilder.build()) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            // put vector bucket
            OperationInput putOperationInput = OperationInput.newBuilder()
                    .opName("PutVectorBucket")
                    .method("PUT")
                    .bucket(bucket)
                    .headers(headers)
                    .build();

            OperationOutput putOperationOutput = client.invokeOperation(putOperationInput, OperationOptions.defaults());

            System.out.println("PutVectorBucket status code: " + putOperationOutput.statusCode() +
                    ", request id: " + putOperationOutput.headers().get("x-oss-request-id"));

            // get vector bucket
            Map<String, String> getHeaders = new HashMap<>();
            getHeaders.put("Content-Type", "application/json");

            Map<String, String> getParameters = new HashMap<>();
            getParameters.put("bucketInfo", "");

            OperationInput getOperationInput = OperationInput.newBuilder()
                    .opName("GetVectorBucket")
                    .method("GET")
                    .bucket(bucket)
                    .headers(getHeaders)
                    .parameters(getParameters)
                    .build();

            OperationOutput getOperationOutput = client.invokeOperation(getOperationInput, OperationOptions.defaults());

            System.out.println("GetVectorBucket status code: " + getOperationOutput.statusCode() +
                    ", request id : " + getOperationOutput.headers().get("x-oss-request-id") +
                    ", content: " + new String(getOperationOutput.body().get().toBytes(), StandardCharsets.UTF_8));

            // put vector index
            Map<String, String> putIndexParameters = new HashMap<>();
            putIndexParameters.put("putVectorIndex", "");

            Map<String, String> putHeaders = new HashMap<>();
            putHeaders.put("Content-Type", "application/json");

            String jsonData = "{\"dataType\": \"float32\", \"dimension\": " + dimension + ", \"distanceMetric\": \"cosine\", \"indexName\": \"" + indexName + "\", \"metadata\": {\"nonFilterableMetadataKeys\": [\"key1\", \"key2\"]}}";

            OperationInput putIndexOperationInput = OperationInput.newBuilder()
                    .opName("PutVectorIndex")
                    .method("POST")
                    .bucket(bucket)
                    .headers(putHeaders)
                    .parameters(putIndexParameters)
                    .body(BinaryData.fromString(jsonData))
                    .build();

            OperationOutput putIndexOperationOutput = client.invokeOperation(putIndexOperationInput, OperationOptions.defaults());

            System.out.println("PutVectorIndex status code: " + putIndexOperationOutput.statusCode() +
                    ", request id: " + putIndexOperationOutput.headers().get("x-oss-request-id") +
                    ", content: " + new String(putIndexOperationOutput.body().get().toBytes(), StandardCharsets.UTF_8));

            // list vector index
            Map<String, String> listHeaders = MapUtils.caseInsensitiveMap();
            listHeaders.put("Content-Type", "application/json");

            Map<String, String> listParameters = MapUtils.caseSensitiveMap();
            listParameters.put("listVectorIndexes", "");

            String listJsonData = "{\n" +
                    "   \"maxResults\": 10,\n" +
                    "   \"nextToken\": \"\",\n" +
                    "   \"prefix\": \"\"\n" +
                    "}";

            OperationInput listIndexInput = OperationInput.newBuilder()
                    .opName("ListVectorIndexes")
                    .bucket(bucket)
                    .method("POST")
                    .headers(listHeaders)
                    .parameters(listParameters)
                    .body(BinaryData.fromString(listJsonData))
                    .build();

            OperationOutput listIndexOperationOutput = client.invokeOperation(listIndexInput, OperationOptions.defaults());

            System.out.println("ListVectorIndex status code: " + listIndexOperationOutput.statusCode() +
                    ", request id: " + listIndexOperationOutput.headers().get("x-oss-request-id") +
                    ", content: " + new String(listIndexOperationOutput.body().get().toBytes(), StandardCharsets.UTF_8));

            // put vectors
            Map<String, String> putVectorsParameters = new HashMap<>();
            putVectorsParameters.put("putVectors", "");

            Map<String, String> putVectorsHeaders = new HashMap<>();
            putVectorsHeaders.put("Content-Type", "application/json");

            String putVectorsJsonData = "{\n" +
                    "   \"indexName\": \"" + indexName + "\",\n" +
                    "   \"vectors\": [\n" +
                    "      {\n" +
                    "         \"key\": \"doc-001\",\n" +
                    "         \"data\": {\n" +
                    "            \"float32\": [0.1, 0.2, 0.3, 0.4, 0.5]\n" +
                    "         },\n" +
                    "         \"metadata\": {\n" +
                    "             \"category\": [\"technology\", \"ai\"],\n" +
                    "             \"title\": \"Introduction to Vector Search\"\n" +
                    "         }\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"key\": \"doc-002\",\n" +
                    "         \"data\": {\n" +
                    "            \"float32\": [0.6, 0.7, 0.8, 0.9, 1.0]\n" +
                    "         },\n" +
                    "         \"metadata\": {\n" +
                    "             \"category\": [\"technology\", \"database\"],\n" +
                    "             \"title\": \"Advanced Database Indexing\"\n" +
                    "         }\n" +
                    "      }\n" +
                    "   ]\n" +
                    "}";

            OperationInput putVectorsInput = OperationInput.newBuilder()
                    .opName("PutVectors")
                    .bucket(bucket)
                    .method("POST")
                    .headers(putVectorsHeaders)
                    .parameters(putVectorsParameters)
                    .body(BinaryData.fromString(putVectorsJsonData))
                    .build();

            OperationOutput putVectorsOutput = client.invokeOperation(putVectorsInput, OperationOptions.defaults());

            System.out.println("PutVectors status code: " + putVectorsOutput.statusCode() +
                    ", request id: " + putVectorsOutput.headers().get("x-oss-request-id"));

            // get vectors
            Map<String, String> getVectorsParameters = new HashMap<>();
            getVectorsParameters.put("getVectors", "");

            Map<String, String> getVectorsHeaders = new HashMap<>();
            getVectorsHeaders.put("Content-Type", "application/json");

            String getVectorsJsonData = "{\n" +
                    "   \"indexName\": \"" + indexName + "\",\n" +
                    "   \"keys\": [\"doc-001\", \"doc-002\"],\n" +
                    "   \"returnData\": true,\n" +
                    "   \"returnMetadata\": true\n" +
                    "}";

            OperationInput getVectorsInput = OperationInput.newBuilder()
                    .opName("GetVectors")
                    .bucket(bucket)
                    .method("POST")
                    .headers(getVectorsHeaders)
                    .parameters(getVectorsParameters)
                    .body(BinaryData.fromString(getVectorsJsonData))
                    .build();

            OperationOutput getVectorsOutput = client.invokeOperation(getVectorsInput, OperationOptions.defaults());

            System.out.println("GetVectors status code: " + getVectorsOutput.statusCode() +
                    ", request id: " + getVectorsOutput.headers().get("x-oss-request-id") +
                    ", content: " + new String(getVectorsOutput.body().get().toBytes(), StandardCharsets.UTF_8));

            // Wait for 50 seconds before querying vectors
            try {
                System.out.println("Waiting for 50 seconds before querying vectors...");
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                System.err.println("Interrupted while waiting: " + e.getMessage());
                Thread.currentThread().interrupt();
            }

            // query vectors
            Map<String, String> queryVectorsParameters = new HashMap<>();
            queryVectorsParameters.put("queryVectors", "");

            Map<String, String> queryVectorsHeaders = new HashMap<>();
            queryVectorsHeaders.put("Content-Type", "application/json");

            String queryVectorsJsonData = "{\n" +
                    "   \"filter\": {\n" +
                    "       \"$or\": [{\n" +
                    "           \"category\": {\n" +
                    "               \"$in\": [\"technology\"]\n" +
                    "           }\n" +
                    "       }, {\n" +
                    "           \"year\": {\n" +
                    "               \"$ne\": \"2020\"\n" +
                    "           }\n" +
                    "       }]\n" +
                    "    },\n" +
                    "   \"indexName\": \"" + indexName + "\",\n" +
                    "   \"queryVector\": {\n" +
                    "       \"float32\": [0.1, 0.2, 0.3, 0.4, 0.5]\n" +
                    "    },\n" +
                    "   \"returnDistance\": true,\n" +
                    "   \"returnMetadata\": true,\n" +
                    "   \"topK\": 5\n" +
                    "}";

            OperationInput queryVectorsInput = OperationInput.newBuilder()
                    .opName("QueryVectors")
                    .bucket(bucket)
                    .method("POST")
                    .headers(queryVectorsHeaders)
                    .parameters(queryVectorsParameters)
                    .body(BinaryData.fromString(queryVectorsJsonData))
                    .build();

            OperationOutput queryVectorsOutput = client.invokeOperation(queryVectorsInput, OperationOptions.defaults());

            System.out.println("QueryVectors status code: " + queryVectorsOutput.statusCode() +
                    ", request id: " + queryVectorsOutput.headers().get("x-oss-request-id") +
                    ", content: " + new String(queryVectorsOutput.body().get().toBytes(), StandardCharsets.UTF_8));

            // Clean up resources in reverse order with exception handling
            // delete vectors
            try {
                Map<String, String> deleteVectorsParameters = new HashMap<>();
                deleteVectorsParameters.put("deleteVectors", "");

                Map<String, String> deleteVectorsHeaders = new HashMap<>();
                deleteVectorsHeaders.put("Content-Type", "application/json");

                String deleteVectorsJsonData = "{\n" +
                        "   \"indexName\": \"" + indexName + "\",\n" +
                        "   \"keys\": [\"doc-001\", \"doc-002\"]\n" +
                        "}";

                OperationInput deleteVectorsInput = OperationInput.newBuilder()
                        .opName("DeleteVectors")
                        .bucket(bucket)
                        .method("POST")
                        .headers(deleteVectorsHeaders)
                        .parameters(deleteVectorsParameters)
                        .body(BinaryData.fromString(deleteVectorsJsonData))
                        .build();

                OperationOutput deleteVectorsOutput = client.invokeOperation(deleteVectorsInput, OperationOptions.defaults());

                System.out.println("DeleteVectors status code: " + deleteVectorsOutput.statusCode() +
                        ", request id: " + deleteVectorsOutput.headers().get("x-oss-request-id"));
            } catch (Exception e) {
                System.err.println("Failed to delete vectors: " + e.getMessage());
            }

            // delete vector index
            try {
                Map<String, String> deleteIndexParameters = new HashMap<>();
                deleteIndexParameters.put("deleteVectorIndex", "");

                Map<String, String> deleteIndexHeaders = new HashMap<>();
                deleteIndexHeaders.put("Content-Type", "application/json");

                String deleteIndexJsonData = "{\n" +
                        "   \"indexName\": \"" + indexName + "\"\n" +
                        "}";

                OperationInput deleteIndexInput = OperationInput.newBuilder()
                        .opName("DeleteVectorIndex")
                        .bucket(bucket)
                        .method("POST")
                        .headers(deleteIndexHeaders)
                        .parameters(deleteIndexParameters)
                        .body(BinaryData.fromString(deleteIndexJsonData))
                        .build();

                OperationOutput deleteIndexOutput = client.invokeOperation(deleteIndexInput, OperationOptions.defaults());

                System.out.println("DeleteVectorIndex status code: " + deleteIndexOutput.statusCode() +
                        ", request id: " + deleteIndexOutput.headers().get("x-oss-request-id"));
            } catch (Exception e) {
                System.err.println("Failed to delete vector index: " + e.getMessage());
            }

            // delete vector bucket
            try {
                Map<String, String> deleteParameters = new HashMap<>();
                Map<String, String> deleteVectorHeaders = new HashMap<>();
                deleteVectorHeaders.put("Content-Type", "application/json");

                OperationInput deleteOperationInput = OperationInput.newBuilder()
                        .opName("DeleteVectorBucket")
                        .method("DELETE")
                        .bucket(bucket)
                        .headers(deleteVectorHeaders)
                        .parameters(deleteParameters)
                        .build();

                OperationOutput deleteOperationOutput = client.invokeOperation(deleteOperationInput, OperationOptions.defaults());

                System.out.println("DeleteVectorBucket status code: " + deleteOperationOutput.statusCode() +
                        ", request id: " + deleteOperationOutput.headers().get("x-oss-request-id"));
            } catch (Exception e) {
                System.err.println("Failed to delete vector bucket: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.printf("error:%n%s", e);
        }

        System.out.println("InvokeOperationVectorDemo has been executed successfully.\n");
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("indexName").desc("The name of the vector index.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String accountId = cmd.getParsedOptionValue("accountId");
        String indexName = cmd.getParsedOptionValue("indexName");
        execute(endpoint, region, bucket, accountId, indexName);
    }
}