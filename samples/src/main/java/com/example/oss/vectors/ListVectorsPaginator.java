package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorsRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorsResult;
import com.aliyun.sdk.service.oss2.vectors.models.VectorsSummary;
import com.aliyun.sdk.service.oss2.vectors.paginator.ListVectorsIterable;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ListVectorsPaginator implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String indexName,
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

            ListVectorsRequest.Builder requestBuilder = ListVectorsRequest.newBuilder()
                    .bucket(bucket)
                    .indexName(indexName);
            if (returnData != null) {
                requestBuilder.returnData(returnData);
            }
            if (returnMetadata != null) {
                requestBuilder.returnMetadata(returnMetadata);
            }

            ListVectorsIterable paginator = client.listVectorsPaginator(requestBuilder.build());

            int pageNo = 1;
            for (ListVectorsResult result : paginator) {
                System.out.printf("Page %d, request id:%s, count:%d, nextToken:%s%n",
                        pageNo++, result.requestId(),
                        result.vectors() == null ? 0 : result.vectors().size(),
                        result.nextToken());

                if (result.vectors() != null) {
                    for (VectorsSummary v : result.vectors()) {
                        System.out.printf("  key:%s, data:%s, metadata:%s%n",
                                v.key(), v.data(), v.metadata());
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
        opts.addOption(Option.builder().longOpt("indexName").desc("The name of the vector index.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("returnData").desc("Whether to return vector data.").hasArg().type(Boolean.class).get());
        opts.addOption(Option.builder().longOpt("returnMetadata").desc("Whether to return vector metadata.").hasArg().type(Boolean.class).get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String indexName = cmd.getParsedOptionValue("indexName");
        Boolean returnData = null;
        if (cmd.getParsedOptionValue("returnData") != null) {
            returnData = Boolean.valueOf(cmd.getParsedOptionValue("returnData"));
        }
        Boolean returnMetadata = null;
        if (cmd.getParsedOptionValue("returnMetadata") != null) {
            returnMetadata = Boolean.valueOf(cmd.getParsedOptionValue("returnMetadata"));
        }
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, bucket, indexName, returnData, returnMetadata, accountId);
    }
}
