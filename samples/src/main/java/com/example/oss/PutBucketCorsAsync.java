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

public class PutBucketCorsAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String allowedOrigin,
            String allowedMethod,
            String allowedHeader,
            String exposeHeader,
            Long maxAgeSeconds) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {
            
            // 构建CORS规则
            List<String> allowedOrigins = Arrays.asList(allowedOrigin.split(","));
            List<String> allowedMethods = Arrays.asList(allowedMethod.split(","));
            List<String> allowedHeaders = allowedHeader != null ? Arrays.asList(allowedHeader.split(",")) : null;
            List<String> exposeHeaders = exposeHeader != null ? Arrays.asList(exposeHeader.split(",")) : null;

            CORSRule corsRule = CORSRule.newBuilder()
                    .allowedOrigins(allowedOrigins)
                    .allowedMethods(allowedMethods)
                    .allowedHeaders(allowedHeaders)
                    .exposeHeaders(exposeHeaders)
                    .maxAgeSeconds(maxAgeSeconds)
                    .build();

            CORSConfiguration corsConfiguration = CORSConfiguration.newBuilder()
                    .corsRules(Arrays.asList(corsRule))
                    .build();

            PutBucketCorsResult result = client.putBucketCorsAsync(PutBucketCorsRequest.newBuilder()
                            .bucket(bucket)
                            .corsConfiguration(corsConfiguration)
                            .build()).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

        } catch (Exception e) {
            //If the exception is caused by ServiceException, detailed information can be obtained in this way.
            //ServiceException se = ServiceException.asCause(e);
            //if (se != null) {
            //   System.out.printf("ServiceException: requestId:%s, errorCode:%s\n", se.requestId(), se.errorCode());
            //}
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
        opts.addOption(Option.builder().longOpt("allowedOrigin").desc("The origins that are allowed to send cross-origin requests. Multiple values can be separated by commas.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("allowedMethod").desc("The HTTP methods that are allowed to send cross-origin requests. Multiple values can be separated by commas.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("allowedHeader").desc("The headers that are allowed in cross-origin requests. Multiple values can be separated by commas.").hasArg().get());
        opts.addOption(Option.builder().longOpt("exposeHeader").desc("The response headers that are allowed to be exposed to applications. Multiple values can be separated by commas.").hasArg().get());
        opts.addOption(Option.builder().longOpt("maxAgeSeconds").desc("The period of time within which a browser can cache the response to a preflight request.").hasArg().type(Long.class).get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String allowedOrigin = cmd.getParsedOptionValue("allowedOrigin");
        String allowedMethod = cmd.getParsedOptionValue("allowedMethod");
        String allowedHeader = cmd.getParsedOptionValue("allowedHeader");
        String exposeHeader = cmd.getParsedOptionValue("exposeHeader");
        Long maxAgeSeconds = cmd.getParsedOptionValue("maxAgeSeconds");
        execute(endpoint, region, bucket, allowedOrigin, allowedMethod, allowedHeader, exposeHeader, maxAgeSeconds);
    }
}
