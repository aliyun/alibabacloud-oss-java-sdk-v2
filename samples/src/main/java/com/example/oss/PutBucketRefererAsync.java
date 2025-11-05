package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.Arrays;
import java.util.List;

public class PutBucketRefererAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String allowEmptyReferer) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {
            
            // Build referer configuration with example referers
            // Example referers: http://www.aliyun.com, https://www.aliyun.com, http://www.example.com
            List<String> refererValues = Arrays.asList(
                "http://www.aliyun.com",
                "https://www.aliyun.com", 
                "http://www.example.com"
            );
            
            RefererList referers = RefererList.newBuilder()
                    .referers(refererValues)
                    .build();

            RefererConfiguration refererConfiguration = RefererConfiguration.newBuilder()
                    .allowEmptyReferer("true".equalsIgnoreCase(allowEmptyReferer))
                    .refererList(referers)
                    .build();

            PutBucketRefererResult result = client.putBucketRefererAsync(PutBucketRefererRequest.newBuilder()
                            .bucket(bucket)
                            .refererConfiguration(refererConfiguration)
                            .build()).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

        } catch (Exception e) {
            // If the exception is caused by ServiceException, detailed information can be obtained in this way.
            // ServiceException se = ServiceException.asCause(e);
            // if (se != null) {
            //   System.out.printf("ServiceException: requestId:%s, errorCode:%s\n", se.requestId(), se.errorCode());
            // }
            System.out.printf("error:\n%s", e);
        }
    }

    private static OSSAsyncClient getDefaultAsyncClient(String endpoint, String region, CredentialsProvider provider) {
        return OSSAsyncClient.newBuilder()
                .region(region)
                .endpoint(endpoint)
                .credentialsProvider(provider)
                .build();
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("allowEmptyReferer").desc("Whether to allow empty referer.").hasArg().type(String.class).get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String allowEmptyReferer = cmd.getParsedOptionValue("allowEmptyReferer");
        execute(endpoint, region, bucket, allowEmptyReferer);
    }
}