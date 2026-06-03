package com.example.oss.dataprocess;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.dataprocess.OSSDataProcessClient;
import com.aliyun.sdk.service.oss2.dataprocess.OSSDataProcessClientBuilder;
import com.aliyun.sdk.service.oss2.dataprocess.models.*;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetDataset implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String datasetName) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSDataProcessClientBuilder clientBuilder = OSSDataProcessClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSDataProcessClient client = clientBuilder.build()) {

            GetDatasetResult result = client.getDataset(
                    GetDatasetRequest.newBuilder()
                            .bucket(bucket)
                            .datasetName(datasetName)
                            .build());

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.dataset() != null) {
                Dataset ds = result.dataset();
                System.out.printf("Dataset name:%s%n", ds.datasetName());
                System.out.printf("Description:%s%n", ds.description());
                System.out.printf("Create time:%s%n", ds.createTime());
                System.out.printf("Update time:%s%n", ds.updateTime());
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
        opts.addOption(Option.builder().longOpt("datasetName").desc("The name of the dataset.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String datasetName = cmd.getParsedOptionValue("datasetName");
        execute(endpoint, region, bucket, datasetName);
    }
}
