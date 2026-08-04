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

public class UpdateJobStatusAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String batchJobId,
            String requestedJobStatus,
            String statusUpdateReason) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncClientBuilder clientBuilder = OSSAsyncClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncClient client = clientBuilder.build()) {

            UpdateJobStatusRequest request = UpdateJobStatusRequest.newBuilder()
                    .batchJobId(batchJobId)
                    .requestedJobStatus(requestedJobStatus)
                    .statusUpdateReason(statusUpdateReason)
                    .build();

            UpdateJobStatusResult result = client.updateJobStatusAsync(request).get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.updateJobStatusResult() != null) {
                System.out.printf("Job id:%s, status:%s, statusUpdateReason:%s%n",
                        result.updateJobStatusResult().jobId(),
                        result.updateJobStatusResult().status(),
                        result.updateJobStatusResult().statusUpdateReason());
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
        opts.addOption(Option.builder().longOpt("requestedJobStatus").desc("The requested job status (Cancelled or Ready).").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("statusUpdateReason").desc("The reason for the status update.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String batchJobId = cmd.getParsedOptionValue("batchJobId");
        String requestedJobStatus = cmd.getParsedOptionValue("requestedJobStatus");
        String statusUpdateReason = cmd.getParsedOptionValue("statusUpdateReason");
        execute(endpoint, region, batchJobId, requestedJobStatus, statusUpdateReason);
    }
}
