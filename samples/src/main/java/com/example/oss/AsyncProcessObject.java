package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.models.internal.AsyncProcessJson;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class AsyncProcessObject implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String process) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            AsyncProcessObjectRequest request = AsyncProcessObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .process(process)
                    .build();

            AsyncProcessObjectResult result = client.asyncProcessObject(request);

            System.out.printf("AsyncProcessObject has been executed successfully. Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

            AsyncProcessJson asyncProcess = result.asyncProcessJson();
            if (asyncProcess != null) {
                System.out.printf("Event ID: %s\n", asyncProcess.eventId());
                System.out.printf("Task ID: %s\n", asyncProcess.taskId());
                System.out.printf("Process Request ID: %s\n", asyncProcess.processRequestId());
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

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("process").desc("The process to apply to the object. Example: image/resize,m_fixed,w_100,h_100.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        String process = cmd.getParsedOptionValue("process");
        execute(endpoint, region, bucket, key, process);
    }
}
