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

public class ListJobsAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String batchJobStatuses,
            Integer maxKeys) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncClientBuilder clientBuilder = OSSAsyncClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncClient client = clientBuilder.build()) {

            ListJobsRequest.Builder requestBuilder = ListJobsRequest.newBuilder();

            if (batchJobStatuses != null) {
                requestBuilder.batchJobStatuses(batchJobStatuses);
            }
            if (maxKeys != null) {
                requestBuilder.maxKeys(maxKeys);
            }

            ListJobsResult result = client.listJobsAsync(requestBuilder.build()).get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.listJobsResult() != null) {
                if (result.listJobsResult().jobs() != null) {
                    for (JobListDescriptor job : result.listJobsResult().jobs()) {
                        System.out.printf("Job id:%s, description:%s, operation:%s, priority:%d, status:%s%n",
                                job.jobId(), job.description(), job.operation(),
                                job.priority(), job.status());
                    }
                }

                if (result.listJobsResult().nextToken() != null && !result.listJobsResult().nextToken().isEmpty()) {
                    System.out.printf("Next token:%s%n", result.listJobsResult().nextToken());
                }
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
        opts.addOption(Option.builder().longOpt("batchJobStatuses").desc("Filter jobs by status (e.g. Active, Complete, Cancelled).").hasArg().get());
        opts.addOption(Option.builder().longOpt("maxKeys").desc("The maximum number of jobs to return.").hasArg().type(Number.class).get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String batchJobStatuses = cmd.getParsedOptionValue("batchJobStatuses");
        Number maxKeysNum = cmd.getParsedOptionValue("maxKeys");
        Integer maxKeys = maxKeysNum != null ? maxKeysNum.intValue() : null;
        execute(endpoint, region, batchJobStatuses, maxKeys);
    }
}
