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

public class PutStyle implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String styleName,
            String styleContent) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            Style style = Style.newBuilder()
                    .content(styleContent)
                    .build();

            PutStyleRequest request = PutStyleRequest.newBuilder()
                    .bucket(bucket)
                    .styleName(styleName)
                    .style(style)
                    .build();

            PutStyleResult result = client.putStyle(request);

            System.out.printf("Status code: %d%n", result.statusCode());
            System.out.printf("Request ID: %s%n", result.requestId());
            System.out.println("Put style successfully.");
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
        opts.addOption(Option.builder().longOpt("styleName").desc("The name of the style.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("styleContent").desc("The content of the style.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String styleName = cmd.getParsedOptionValue("styleName");
        String styleContent = cmd.getParsedOptionValue("styleContent");
        execute(endpoint, region, bucket, styleName, styleContent);
    }
}