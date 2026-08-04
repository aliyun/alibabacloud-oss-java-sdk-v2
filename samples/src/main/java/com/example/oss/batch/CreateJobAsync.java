package com.example.oss.batch;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.OSSAsyncClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Collections;
import java.util.UUID;

public class CreateJobAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String roleArn,
            String description,
            Long priority) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncClientBuilder clientBuilder = OSSAsyncClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncClient client = clientBuilder.build()) {

            Tag tag = Tag.newBuilder()
                    .key("env")
                    .value("production")
                    .build();
            TagSet tagSet = TagSet.newBuilder()
                    .tags(Collections.singletonList(tag))
                    .build();
            JobPutObjectTagging putObjectTagging = JobPutObjectTagging.newBuilder()
                    .tagSet(tagSet)
                    .build();
            JobOperation operation = JobOperation.newBuilder()
                    .putObjectTagging(putObjectTagging)
                    .build();

            JobReport report = JobReport.newBuilder()
                    .bucket(bucket)
                    .enabled(true)
                    .prefix("batch-reports/")
                    .reportScope("AllTasks")
                    .build();

            KeyPrefixManifestGenerator generator = KeyPrefixManifestGenerator.newBuilder()
                    .sourceBucket(bucket)
                    .prefix("test/")
                    .build();

            CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                    .confirmationRequired(false)
                    .operation(operation)
                    .report(report)
                    .clientRequestToken(UUID.randomUUID().toString())
                    .keyPrefixManifestGenerator(generator)
                    .description(description)
                    .priority(priority)
                    .roleArn(roleArn)
                    .build();

            CreateJobRequest request = CreateJobRequest.newBuilder()
                    .createJobBody(body)
                    .build();

            CreateJobResult result = client.createJobAsync(request).get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.createJobResult() != null) {
                System.out.printf("Job id:%s%n", result.createJobResult().jobId());
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
        opts.addOption(Option.builder().longOpt("roleArn").desc("The ARN of the RAM role used to access source bucket and report bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("description").desc("The description of the job.").hasArg().get());
        opts.addOption(Option.builder().longOpt("priority").desc("The priority of the job.").hasArg().type(Number.class).get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String roleArn = cmd.getParsedOptionValue("roleArn");
        String description = cmd.getParsedOptionValue("description");
        Number priorityNum = cmd.getParsedOptionValue("priority");
        Long priority = priorityNum != null ? priorityNum.longValue() : 10L;
        if (description == null) {
            description = "Batch operation job created by sample";
        }
        execute(endpoint, region, bucket, roleArn, description, priority);
    }
}
