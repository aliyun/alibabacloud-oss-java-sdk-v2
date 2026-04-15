package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.EncryptionConfiguration;
import com.aliyun.sdk.service.oss2.tables.models.PutTableBucketEncryptionRequest;
import com.aliyun.sdk.service.oss2.tables.models.PutTableBucketEncryptionResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class PutTableBucketEncryptionSample implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String sseAlgorithm,
            String kmsKeyArn) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSTablesClientBuilder clientBuilder = OSSTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSTablesClient client = clientBuilder.build()) {

            EncryptionConfiguration encryptionConfig = EncryptionConfiguration.newBuilder()
                    .sseAlgorithm(sseAlgorithm)
                    .kmsKeyArn(kmsKeyArn)
                    .build();

            PutTableBucketEncryptionRequest request = PutTableBucketEncryptionRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .encryptionConfiguration(encryptionConfig)
                    .build();

            PutTableBucketEncryptionResult result = client.putTableBucketEncryption(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());
            System.out.printf("Successfully updated table bucket encryption for ARN: %s%n", tableBucketARN);

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket to update encryption for.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("sseAlgorithm").desc("The server-side encryption algorithm.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("kmsKeyArn").desc("The KMS key ARN for encryption.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String tableBucketARN = cmd.getParsedOptionValue("tableBucketARN");
        String sseAlgorithm = cmd.getParsedOptionValue("sseAlgorithm");
        String kmsKeyArn = cmd.getParsedOptionValue("kmsKeyArn");
        execute(endpoint, region, tableBucketARN, sseAlgorithm, kmsKeyArn);
    }
}