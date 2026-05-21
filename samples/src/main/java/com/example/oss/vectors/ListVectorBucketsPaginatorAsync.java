package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSAsyncVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorBucketsRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorBucketsResult;
import com.aliyun.sdk.service.oss2.vectors.models.VectorBucketSummary;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ListVectorBucketsPaginatorAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String prefix,
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

            String marker = null;
            boolean isTruncated = true;
            int pageNo = 1;

            while (isTruncated) {
                ListVectorBucketsRequest.Builder requestBuilder = ListVectorBucketsRequest.newBuilder();
                if (prefix != null) {
                    requestBuilder.prefix(prefix);
                }
                if (marker != null) {
                    requestBuilder.marker(marker);
                }

                ListVectorBucketsResult result = client.listVectorBucketsAsync(requestBuilder.build()).get();

                System.out.printf("Page %d, request id:%s, count:%d%n",
                        pageNo++, result.requestId(),
                        result.buckets() == null ? 0 : result.buckets().size());

                if (result.buckets() != null) {
                    for (VectorBucketSummary bucket : result.buckets()) {
                        System.out.printf("  name:%s, region:%s, location:%s, creationDate:%s%n",
                                bucket.name(), bucket.region(),
                                bucket.location(), bucket.creationDate());
                    }
                }

                isTruncated = result.isTruncated() != null && result.isTruncated();
                marker = result.nextMarker();
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
        opts.addOption(Option.builder().longOpt("prefix").desc("The prefix that the names of returned buckets must contain.").hasArg().get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String prefix = cmd.getParsedOptionValue("prefix");
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, prefix, accountId);
    }
}
