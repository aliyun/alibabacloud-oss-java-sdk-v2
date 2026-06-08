package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.IndexSummary;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorIndexesRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorIndexesResult;
import com.aliyun.sdk.service.oss2.vectors.paginator.ListVectorIndexesIterable;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ListVectorIndexesPaginator implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String prefix,
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

            ListVectorIndexesRequest.Builder requestBuilder = ListVectorIndexesRequest.newBuilder()
                    .bucket(bucket);
            if (prefix != null) {
                requestBuilder.prefix(prefix);
            }

            ListVectorIndexesIterable paginator = client.listVectorIndexesPaginator(requestBuilder.build());

            int pageNo = 1;
            for (ListVectorIndexesResult result : paginator) {
                System.out.printf("Page %d, request id:%s, count:%d, nextToken:%s%n",
                        pageNo++, result.requestId(),
                        result.indexes() == null ? 0 : result.indexes().size(),
                        result.nextToken());

                if (result.indexes() != null) {
                    for (IndexSummary index : result.indexes()) {
                        System.out.printf("  indexName:%s, dataType:%s, dimension:%s, distanceMetric:%s, status:%s%n",
                                index.indexName(), index.dataType(),
                                index.dimension(), index.distanceMetric(), index.status());
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
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the vector bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("prefix").desc("The prefix to filter indexes by name.").hasArg().get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String prefix = cmd.getParsedOptionValue("prefix");
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, bucket, prefix, accountId);
    }
}
