package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.GetTableEncryptionRequest;
import com.aliyun.sdk.service.oss2.tables.models.GetTableEncryptionResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetTableEncryptionSample implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String namespace,
            String name) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSTablesClientBuilder clientBuilder = OSSTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSTablesClient client = clientBuilder.build()) {

            GetTableEncryptionRequest request = GetTableEncryptionRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .namespace(namespace)
                    .name(name)
                    .build();

            GetTableEncryptionResult result = client.getTableEncryption(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());
            if (result.encryptionConfiguration() != null) {
                System.out.printf("SSE Algorithm: %s%n", result.encryptionConfiguration().sseAlgorithm());
                System.out.printf("KMS Key ARN: %s%n", result.encryptionConfiguration().kmsKeyArn());
            }

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("namespace").desc("The namespace of the table.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("name").desc("The name of the table.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String tableBucketARN = cmd.getParsedOptionValue("tableBucketARN");
        String namespace = cmd.getParsedOptionValue("namespace");
        String name = cmd.getParsedOptionValue("name");
        execute(endpoint, region, tableBucketARN, namespace, name);
    }
}
