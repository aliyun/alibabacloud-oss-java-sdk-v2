package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.CreateTableBucketRequest;
import com.aliyun.sdk.service.oss2.tables.models.CreateTableBucketResult;
import com.aliyun.sdk.service.oss2.tables.models.EncryptionConfiguration;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CreateTableBucketSample implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String name,
            String sseAlgorithm,
            String kmsKeyArn) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSTablesClientBuilder clientBuilder = OSSTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSTablesClient client = clientBuilder.build()) {

            CreateTableBucketRequest.Builder requestBuilder = CreateTableBucketRequest.newBuilder()
                    .name(name);

            if (sseAlgorithm != null || kmsKeyArn != null) {
                EncryptionConfiguration encryptionConfig = EncryptionConfiguration.newBuilder()
                        .sseAlgorithm(sseAlgorithm)
                        .kmsKeyArn(kmsKeyArn)
                        .build();
                requestBuilder.encryptionConfiguration(encryptionConfig);
            }

            CreateTableBucketResult result = client.createTableBucket(requestBuilder.build());

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());
            System.out.printf("Created table bucket with ARN: %s%n", result.arn());

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("name").desc("The name of the table bucket.").hasArg().required().get());

        opts.addOption(Option.builder().longOpt("sseAlgorithm").desc("The server-side encryption algorithm.").hasArg().get());
        opts.addOption(Option.builder().longOpt("kmsKeyArn").desc("The KMS key ARN for encryption.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String name = cmd.getParsedOptionValue("name");
        String sseAlgorithm = cmd.getParsedOptionValue("sseAlgorithm");
        String kmsKeyArn = cmd.getParsedOptionValue("kmsKeyArn");
        execute(endpoint, region, name, sseAlgorithm, kmsKeyArn);
    }
}