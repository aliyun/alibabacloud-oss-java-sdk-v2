package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorBucketsRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorBucketsResult;
import com.example.oss.Example;
import com.aliyun.sdk.service.oss2.vectors.models.VectorBucketProperties;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ListVectorBuckets implements Example {

    private static void execute(
            String endpoint,
            String region,
            String prefix,
            String marker,
            Integer maxKeys,
            String resourceGroupId,
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

            ListVectorBucketsRequest.Builder requestBuilder = ListVectorBucketsRequest.newBuilder();

            if (prefix != null) {
                requestBuilder.prefix(prefix);
            }

            if (marker != null) {
                requestBuilder.marker(marker);
            }

            if (maxKeys != null) {
                requestBuilder.maxKeys(maxKeys);
            }

            if (resourceGroupId != null) {
                requestBuilder.resourceGroupId(resourceGroupId);
            }

            ListVectorBucketsResult result = client.listVectorBuckets(requestBuilder.build());

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

            System.out.printf("Prefix: %s\n", result.prefix());
            System.out.printf("Marker: %s\n", result.marker());
            System.out.printf("Max keys: %d\n", result.maxKeys());
            System.out.printf("Is truncated: %b\n", result.isTruncated());
            System.out.printf("Next marker: %s\n", result.nextMarker());

            if (result.buckets() != null) {
                System.out.println("Buckets:");
                for (VectorBucketProperties bucket : result.buckets()) {
                    System.out.printf("  Name: %s\n", bucket.name());
                    System.out.printf("  Location: %s\n", bucket.location());
                    System.out.printf("  Creation date: %s\n", bucket.creationDate());
                    System.out.printf("  Extranet endpoint: %s\n", bucket.extranetEndpoint());
                    System.out.printf("  Intranet endpoint: %s\n", bucket.intranetEndpoint());
                    System.out.printf("  Resource group ID: %s\n", bucket.resourceGroupId());
                    System.out.printf("  Region: %s\n", bucket.region());
                    System.out.println();
                }
            }

        } catch (Exception e) {
            System.out.printf("error:\n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("prefix").desc("The prefix that the names of returned buckets must contain.").hasArg().get());
        opts.addOption(Option.builder().longOpt("marker").desc("The name of the bucket from which the list operation begins.").hasArg().get());
        opts.addOption(Option.builder().longOpt("maxKeys").desc("The maximum number of buckets that can be returned in the single query.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("resourceGroupId").desc("The ID of the resource group.").hasArg().get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String prefix = cmd.getParsedOptionValue("prefix");
        String marker = cmd.getParsedOptionValue("marker");
        Integer maxKeys = null;
        if (cmd.getParsedOptionValue("maxKeys") != null) {
            maxKeys = ((Number) cmd.getParsedOptionValue("maxKeys")).intValue();
        }
        String resourceGroupId = cmd.getParsedOptionValue("resourceGroupId");
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, prefix, marker, maxKeys, resourceGroupId, accountId);
    }
}
