package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.Base64;

public class CreateSelectObjectMetaCsv implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {

            CSVInput csvInput = CSVInput.newBuilder()
                    .recordDelimiter(Base64.getEncoder().encodeToString("\n".getBytes()))
                    .fieldDelimiter(Base64.getEncoder().encodeToString(",".getBytes()))
                    .quoteCharacter(Base64.getEncoder().encodeToString("\"".getBytes()))
                    .build();

            InputSerialization inputSerialization = InputSerialization.newBuilder()
                    .compressionType("None")
                    .csv(csvInput)
                    .build();

            SelectMetaRequest metaRequest = SelectMetaRequest.newBuilder()
                    .inputSerialization(inputSerialization)
                    .overwriteIfExists(true)
                    .build();

            CreateSelectObjectMetaResult result = client.createSelectObjectMeta(CreateSelectObjectMetaRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .selectMetaRequest(metaRequest)
                    .build());

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

            SelectMetaStatus status = result.selectMetaStatus();
            if (status != null) {
                System.out.printf("Rows count:%d, Splits count:%d\n",
                        status.rowsCount(), status.splitsCount());
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
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        execute(endpoint, region, bucket, key);
    }
}
