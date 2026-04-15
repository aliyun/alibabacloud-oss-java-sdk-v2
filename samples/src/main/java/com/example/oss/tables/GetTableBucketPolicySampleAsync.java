package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSAsyncTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSAsyncTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.GetTableBucketPolicyRequest;
import com.aliyun.sdk.service.oss2.tables.models.GetTableBucketPolicyResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.concurrent.CompletableFuture;

public class GetTableBucketPolicySampleAsync implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncTablesClientBuilder clientBuilder = OSSAsyncTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncTablesClient client = clientBuilder.build()) {

            GetTableBucketPolicyRequest request = GetTableBucketPolicyRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .build();

            CompletableFuture<GetTableBucketPolicyResult> future = client.getTableBucketPolicyAsync(request);

            future.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    System.out.printf("Error:%n%s", throwable);
                } else {
                    System.out.printf("Status code:%%d, request id:%%s%%n",
                            result.statusCode(), result.requestId());
                    System.out.printf("Successfully retrieved policy for table bucket ARN: %%s%%n", tableBucketARN);
                    if (result.resourcePolicy() != null) {
                        System.out.printf("Policy: %%s%%n", result.resourcePolicy());
                    } else {
                        System.out.println("No policy set for this table bucket.");
                    }
                }
            }).join(); // Wait for the async operation to complete

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket to get policy for.").hasArg().required().get());
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