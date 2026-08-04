package com.example.oss.batch;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.example.oss.Example;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class UpdateJobPriority implements Example {

    private static void execute(
            String endpoint,
            String region,
            String batchJobId,
            Integer targetPriority) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {

            UpdateJobPriorityRequest request = UpdateJobPriorityRequest.newBuilder()
                    .batchJobId(batchJobId)
                    .targetPriority(targetPriority)
                    .build();

            UpdateJobPriorityResult result = client.updateJobPriority(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            if (result.updateJobPriorityResult() != null) {
                System.out.printf("Job id:%s, priority:%d%n",
                        result.updateJobPriorityResult().jobId(),
                        result.updateJobPriorityResult().priority());
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
        opts.addOption(Option.builder().longOpt("targetPriority").desc("The target priority for the job (1-10).").hasArg().type(Number.class).required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String batchJobId = cmd.getParsedOptionValue("batchJobId");
        Number targetPriorityNum = cmd.getParsedOptionValue("targetPriority");
        Integer targetPriority = targetPriorityNum.intValue();
        execute(endpoint, region, batchJobId, targetPriority);
    }
}
