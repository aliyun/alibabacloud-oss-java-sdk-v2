package com.example.oss.vectors;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClientBuilder;
import com.aliyun.sdk.service.oss2.vectors.models.DeleteVectorBucketRequest;
import com.aliyun.sdk.service.oss2.vectors.models.DeleteVectorBucketResult;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class DeleteVectorBucket implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
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

            DeleteVectorBucketRequest request = DeleteVectorBucketRequest.newBuilder()
                    .bucket(bucket)
                    .build();

            DeleteVectorBucketResult result = client.deleteVectorBucket(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

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
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the vector bucket.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String accountId = cmd.getParsedOptionValue("accountId");
        execute(endpoint, region, bucket, accountId);
    }
}