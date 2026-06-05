package com.example.oss.dataprocess;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.dataprocess.OSSDataProcessAsyncClient;
import com.aliyun.sdk.service.oss2.dataprocess.OSSDataProcessAsyncClientBuilder;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Collections;

public class DoMetaQueryAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSDataProcessAsyncClientBuilder clientBuilder = OSSDataProcessAsyncClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSDataProcessAsyncClient client = clientBuilder.build()) {

            Aggregation agg = Aggregation.newBuilder()
                    .field("Size")
                    .operation("sum")
                    .build();

            DoMetaQueryResult result = client.doMetaQueryAsync(
                    DoMetaQueryRequest.newBuilder()
                            .bucket(bucket)
                            .mode("basic")
                            .metaQueryBody(MetaQueryDoBody.newBuilder()
                                    .query("{\"Field\": \"Size\", \"Value\": \"0\", \"Operation\": \"gt\"}")
                                    .sort("Size")
                                    .order("asc")
                                    .maxResults(5)
                                    .aggregations(Collections.singletonList(agg))
                                    .build())
                            .build()).get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.files() != null) {
                for (File f : result.files()) {
                    System.out.printf("File: uri=%s, filename=%s, size=%d%n",
                            f.uri(), f.filename(), f.size());
                }
            }

            if (result.aggregations() != null) {
                for (AggregationInfo aggInfo : result.aggregations()) {
                    System.out.printf("Aggregation: field=%s, operation=%s, value=%s%n",
                            aggInfo.field(), aggInfo.operation(), aggInfo.value());
                }
            }

            if (result.nextToken() != null) {
                System.out.printf("Next token:%s%n", result.nextToken());
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
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        execute(endpoint, region, bucket);
    }
}
