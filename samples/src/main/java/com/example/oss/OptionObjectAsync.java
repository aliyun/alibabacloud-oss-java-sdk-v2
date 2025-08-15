package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class OptionObjectAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String origin,
            String accessControlRequestMethod,
            String accessControlRequestHeaders) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {
            
            OptionObjectRequest.Builder requestBuilder = OptionObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .origin(origin)
                    .accessControlRequestMethod(accessControlRequestMethod);
            
            if (accessControlRequestHeaders != null) {
                requestBuilder.accessControlRequestHeaders(accessControlRequestHeaders);
            }

            OptionObjectResult result = client.optionObjectAsync(requestBuilder.build()).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());
            
            // 输出CORS响应头
            if (result.accessControlAllowOrigin() != null) {
                System.out.printf("Access-Control-Allow-Origin: %s\n", result.accessControlAllowOrigin());
            }
            if (result.accessControlAllowMethods() != null) {
                System.out.printf("Access-Control-Allow-Methods: %s\n", result.accessControlAllowMethods());
            }
            if (result.accessControlAllowHeaders() != null) {
                System.out.printf("Access-Control-Allow-Headers: %s\n", result.accessControlAllowHeaders());
            }
            if (result.accessControlExposeHeaders() != null) {
                System.out.printf("Access-Control-Expose-Headers: %s\n", result.accessControlExposeHeaders());
            }
            if (result.accessControlMaxAge() != null) {
                System.out.printf("Access-Control-Max-Age: %d\n", result.accessControlMaxAge());
            }

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
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("origin").desc("The origin of the request.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("accessControlRequestMethod").desc("The method to be used in the actual cross-origin request.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("accessControlRequestHeaders").desc("The custom headers to be sent in the actual cross-origin request.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        String origin = cmd.getParsedOptionValue("origin");
        String accessControlRequestMethod = cmd.getParsedOptionValue("accessControlRequestMethod");
        String accessControlRequestHeaders = cmd.getParsedOptionValue("accessControlRequestHeaders");
        execute(endpoint, region, bucket, key, origin, accessControlRequestMethod, accessControlRequestHeaders);
    }
}
