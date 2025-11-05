package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.QueryVectorsRequest;
import com.aliyun.sdk.service.oss2.vectors.models.QueryVectorsResult;
import com.example.oss.Example;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.HashMap;
import java.util.Map;

public class QueryVectors implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String indexName,
            String queryVector,
            String filter,
            Integer topK,
            Boolean returnDistance,
            Boolean returnMetadata,
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

            QueryVectorsRequest.Builder requestBuilder = QueryVectorsRequest.newBuilder()
                    .bucket(bucket);

            // Add indexName if provided
            if (indexName != null) {
                requestBuilder.indexName(indexName);
            }

            // Parse query vector from string or use default
            Map<String, Object> vectorData;
            if (queryVector != null) {
                vectorData = parseVector(queryVector);
            } else {
                // Default query vector
                vectorData = new HashMap<>();
                vectorData.put("float32", new float[]{0.1f, 0.2f, 0.3f});
            }
            requestBuilder.queryVector(vectorData);

            // Add filter - use default if not provided
            if (filter == null) {
                // Default filter
                filter = "{\"$and\": [{\"type\": {\"$nin\": [\"comedy\", \"documentary\"]}}]}";
            }
            
            Map<String, Object> filterMap = parseFilter(filter);
            requestBuilder.filter(filterMap);

            // Set topK - use default if not provided
            if (topK != null) {
                requestBuilder.topK(topK);
            } else {
                requestBuilder.topK(10); // Default value
            }

            // Set returnDistance - use default if not provided
            if (returnDistance != null) {
                requestBuilder.returnDistance(returnDistance);
            } else {
                requestBuilder.returnDistance(true); // Default value
            }

            // Set returnMetadata - use default if not provided
            if (returnMetadata != null) {
                requestBuilder.returnMetadata(returnMetadata);
            } else {
                requestBuilder.returnMetadata(false); // Default value
            }

            QueryVectorsRequest request = requestBuilder.build();
            QueryVectorsResult result = client.queryVectors(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.vectors().size() > 0) {
                System.out.printf("Found %d matching vectors%n", result.vectors().size());
                
                // Print detailed information for each vector
                for (int i = 0; i < result.vectors().size(); i++) {
                    com.aliyun.sdk.service.oss2.vectors.models.QueryVectorsSummary vector = result.vectors().get(i);
                    System.out.printf("Vector %d:%n", i + 1);
                    System.out.printf("  Key: %s%n", vector.key());
                    
                    // Print distance if available
                    if (vector.distance() != null) {
                        System.out.printf("  Distance: %d%n", vector.distance());
                    } else {
                        System.out.println("  Distance: not returned");
                    }
                    
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

    private static Map<String, Object> parseVector(String vectorStr) {
        String[] parts = vectorStr.split(",");
        float[] vector = new float[parts.length];
        for (int i = 0; i < parts.length; i++) {
            vector[i] = Float.parseFloat(parts[i].trim());
        }
        
        Map<String, Object> vectorMap = new HashMap<>();
        vectorMap.put("float32", vector);
        return vectorMap;
    }

    private static Map<String, Object> parseFilter(String filterStr) throws Exception {
        // If filter is a JSON string, parse it
        if (filterStr != null && filterStr.trim().startsWith("{")) {
            ObjectMapper mapper = new ObjectMapper();
            @SuppressWarnings("unchecked")
            Map<String, Object> filterMap = mapper.readValue(filterStr, Map.class);
            return filterMap;
        } else if (filterStr != null) {
            // For simplicity in this example, we'll create a basic filter map
            // Example filter format: "field1:value1,field2:value2"
            Map<String, Object> filter = new HashMap<>();
            String[] conditions = filterStr.split(",");
            Map<String, Object> conditionMap = new HashMap<>();
            
            for (String condition : conditions) {
                String[] parts = condition.split(":");
                if (parts.length == 2) {
                    conditionMap.put(parts[0].trim(), parts[1].trim());
                }
            }
            
            filter.putAll(conditionMap);
            return filter;
        } else {
            // Return empty map if no filter
            return new HashMap<>();
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("indexName").desc("The name of the index.").hasArg().get());
        opts.addOption(Option.builder().longOpt("queryVector").desc("The query vector as comma-separated values (e.g., '1.0,2.0,3.0').").hasArg().get());
        opts.addOption(Option.builder().longOpt("filter").desc("Filter conditions as JSON string or comma-separated key:value pairs.").hasArg().get());
        opts.addOption(Option.builder().longOpt("topK").desc("The number of top K vectors to return.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("returnDistance").desc("Whether to return distance (true/false).").hasArg().type(Boolean.class).get());
        opts.addOption(Option.builder().longOpt("returnMetadata").desc("Whether to return metadata (true/false).").hasArg().type(Boolean.class).get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String indexName = cmd.getParsedOptionValue("indexName");
        String queryVector = cmd.getParsedOptionValue("queryVector");
        String filter = cmd.getParsedOptionValue("filter");
        Integer topK = null;
        if (cmd.getParsedOptionValue("topK") != null) {
            topK = ((Number) cmd.getParsedOptionValue("topK")).intValue();
        }
        Boolean returnDistance = null;
        if (cmd.getParsedOptionValue("returnDistance") != null) {
            returnDistance = Boolean.valueOf(cmd.getParsedOptionValue("returnDistance"));
        }
        Boolean returnMetadata = null;
        if (cmd.getParsedOptionValue("returnMetadata") != null) {
            returnMetadata = Boolean.valueOf(cmd.getParsedOptionValue("returnMetadata"));
        }
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, bucket, indexName, queryVector, filter, topK, returnDistance, returnMetadata, accountId);
    }
}