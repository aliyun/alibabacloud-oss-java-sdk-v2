package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.GetVectorsRequest;
import com.aliyun.sdk.service.oss2.vectors.models.GetVectorsResult;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.Arrays;
import java.util.List;

public class GetVectors implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String indexName,
            String keys,
            Boolean returnData,
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

            List<String> keyList = Arrays.asList(keys.split(","));

            GetVectorsRequest.Builder requestBuilder = GetVectorsRequest.newBuilder()
                    .bucket(bucket)
                    .indexName(indexName)
                    .keys(keyList);

            if (returnData != null) {
                requestBuilder.returnData(returnData);
            }

            if (returnMetadata != null) {
                requestBuilder.returnMetadata(returnMetadata);
            }

            GetVectorsRequest request = requestBuilder.build();
            GetVectorsResult result = client.getVectors(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.vectors() != null) {
                System.out.printf("Retrieved %d vectors%n", result.vectors().size());
                
                // Print detailed information for each vector
                for (int i = 0; i < result.vectors().size(); i++) {
                    com.aliyun.sdk.service.oss2.vectors.models.VectorsSummary vectors = result.vectors().get(i);
                    System.out.printf("Vector %d:%n", i + 1);
                    System.out.printf("  Key: %s%n", vectors.key());
                    
                    // Print vector data
                    System.out.printf("  Data: %s%n", vectors.data());
                    
                    // Print metadata
                    System.out.printf("  Metadata: %s%n", vectors.metadata());
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
        opts.addOption(Option.builder().longOpt("keys").desc("Comma-separated list of vector keys to retrieve.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("returnData").desc("Whether to return vector data (true/false).").hasArg().type(Boolean.class).get());
        opts.addOption(Option.builder().longOpt("returnMetadata").desc("Whether to return vector metadata (true/false).").hasArg().type(Boolean.class).get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String indexName = cmd.getParsedOptionValue("indexName");
        String keys = cmd.getParsedOptionValue("keys");
        Boolean returnData = null;
        if (cmd.getParsedOptionValue("returnData") != null) {
            returnData = Boolean.valueOf(cmd.getParsedOptionValue("returnData"));
        }
        Boolean returnMetadata = null;
        if (cmd.getParsedOptionValue("returnMetadata") != null) {
            returnMetadata = Boolean.valueOf(cmd.getParsedOptionValue("returnMetadata"));
        }
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, bucket, indexName, keys, returnData, returnMetadata, accountId);
    }
}