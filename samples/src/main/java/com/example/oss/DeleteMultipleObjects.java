package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.models.DeleteObject;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.ArrayList;
import java.util.List;

public class DeleteMultipleObjects implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String[] keys) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            List<DeleteObject> deleteObjects = new ArrayList<>();
            for (String key : keys) {
                // Split keys by comma if they contain commas
                if (key.contains(",")) {
                    String[] splitKeys = key.split(",");
                    for (String splitKey : splitKeys) {
                        deleteObjects.add(DeleteObject.newBuilder().key(splitKey.trim()).build());
                    }
                } else {
                    deleteObjects.add(DeleteObject.newBuilder().key(key).build());
                }
            }

            DeleteMultipleObjectsResult result = client.deleteMultipleObjects(DeleteMultipleObjectsRequest.newBuilder()
                    .bucket(bucket)
                    .deleteObjects(deleteObjects)
                    .build());

            System.out.printf("status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

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
        opts.addOption(Option.builder().longOpt("keys").desc("The names of the objects to delete (comma-separated values allowed).").hasArgs().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String[] keys = cmd.getOptionValues("keys");
        execute(endpoint, region, bucket, keys);
    }
}
