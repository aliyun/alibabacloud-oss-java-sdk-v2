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
import java.util.Arrays;
import java.util.List;

public class PutUserDefinedLogFieldsConfig implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String headers,
            String parameters) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {

            List<String> headerList = headers != null ? Arrays.asList(headers.split(",")) : null;
            HeaderSet headerSet = headerList != null ? 
                    HeaderSet.newBuilder().headers(headerList).build() : null;

            List<String> paramList = parameters != null ? Arrays.asList(parameters.split(",")) : null;
            ParamSet paramSet = paramList != null ? 
                    ParamSet.newBuilder().parameters(paramList).build() : null;

            UserDefinedLogFieldsConfiguration userDefinedLogFieldsConfiguration = 
                    UserDefinedLogFieldsConfiguration.newBuilder()
                            .headerSet(headerSet)
                            .paramSet(paramSet)
                            .build();

            PutUserDefinedLogFieldsConfigResult result = client.putUserDefinedLogFieldsConfig(
                    PutUserDefinedLogFieldsConfigRequest.newBuilder()
                            .bucket(bucket)
                            .userDefinedLogFieldsConfiguration(userDefinedLogFieldsConfiguration)
                            .build());

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

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("headers").desc("The custom request headers to be included in the real-time log delivery. Multiple values can be separated by commas.").hasArg().get());
        opts.addOption(Option.builder().longOpt("parameters").desc("The custom URL parameters to be included in the real-time log delivery. Multiple values can be separated by commas.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String headers = cmd.getParsedOptionValue("headers");
        String parameters = cmd.getParsedOptionValue("parameters");
        execute(endpoint, region, bucket, headers, parameters);
    }
}