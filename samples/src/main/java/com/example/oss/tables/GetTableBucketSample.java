package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.GetTableBucketRequest;
import com.aliyun.sdk.service.oss2.tables.models.GetTableBucketResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetTableBucketSample implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSTablesClientBuilder clientBuilder = OSSTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSTablesClient client = clientBuilder.build()) {

            GetTableBucketResult result = client.getTableBucket(GetTableBucketRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .build());

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());
            System.out.printf("Table bucket ARN: %s%n", result.arn());
            System.out.printf("Table bucket name: %s%n", result.name());
            System.out.printf("Table bucket type: %s%n", result.type());
            System.out.printf("Owner account ID: %s%n", result.ownerAccountId());
            System.out.printf("Table bucket ID: %s%n", result.tableBucketId());
            System.out.printf("Created at: %s%n", result.createdAt());

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket to get.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String tableBucketARN = cmd.getParsedOptionValue("tableBucketARN");
        execute(endpoint, region, tableBucketARN);
    }
}