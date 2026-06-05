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

public class ListStyle implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            ListStyleRequest request = ListStyleRequest.newBuilder()
                    .bucket(bucket)
                    .build();

            ListStyleResult result = client.listStyle(request);

            System.out.printf("Status code: %d%n", result.statusCode());
            System.out.printf("Request ID: %s%n", result.requestId());
            
            if (result.styleList() != null && result.styleList().styles() != null) {
                System.out.printf("Found %d styles:%n", result.styleList().styles().size());
                for (int i = 0; i < result.styleList().styles().size(); i++) {
                    StyleInfo style = result.styleList().styles().get(i);
                    System.out.printf("Style %d:%n", i + 1);
                    System.out.printf("  Name: %s%n", style.name());
                    System.out.printf("  Content: %s%n", style.content());
                    System.out.printf("  Category: %s%n", style.category());
                    System.out.printf("  Create Time: %s%n", style.createTime());
                    System.out.printf("  Last Modify Time: %s%n", style.lastModifyTime());
                }
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