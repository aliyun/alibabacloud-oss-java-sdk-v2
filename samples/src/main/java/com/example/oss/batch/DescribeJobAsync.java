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

public class DescribeJobAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String batchJobId) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncClientBuilder clientBuilder = OSSAsyncClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncClient client = clientBuilder.build()) {

            DescribeJobRequest request = DescribeJobRequest.newBuilder()
                    .batchJobId(batchJobId)
                    .build();

            DescribeJobResult result = client.describeJobAsync(request).get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.describeJobResult() != null && result.describeJobResult().job() != null) {
                JobDetail job = result.describeJobResult().job();
                System.out.printf("Job id:%s, description:%s, status:%s, priority:%d, statusUpdateReason:%s%n",
                        job.jobId(), job.description(), job.status(), job.priority(),
                        job.statusUpdateReason());

                if (job.failureReasons() != null && job.failureReasons().jobFailure() != null) {
                    System.out.printf("Failure - code:%s, reason:%s%n",
                            job.failureReasons().jobFailure().failureCode(),
                            job.failureReasons().jobFailure().failureReason());
                }

                if (job.operation() != null) {
                    System.out.printf("Operation - deleteObjectTagging:%s, putObjectTagging:%s, putObjectAcl:%s, restoreObject:%s%n",
                            job.operation().deleteObjectTagging() != null,
                            job.operation().putObjectTagging() != null,
                            job.operation().putObjectAcl() != null,
                            job.operation().restoreObject() != null);
                }

                if (job.report() != null) {
                    System.out.printf("Report - bucket:%s, enabled:%s, prefix:%s, reportScope:%s%n",
                            job.report().bucket(), job.report().enabled(),
                            job.report().prefix(), job.report().reportScope());
                }

                if (job.progressSummary() != null) {
                    System.out.printf("Progress summary available%n");
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
        opts.addOption(Option.builder().longOpt("batchJobId").desc("The ID of the batch operation job.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String batchJobId = cmd.getParsedOptionValue("batchJobId");
        execute(endpoint, region, batchJobId);
    }
}
