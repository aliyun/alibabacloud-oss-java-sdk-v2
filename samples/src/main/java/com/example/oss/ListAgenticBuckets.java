package com.example.oss;

import com.aliyun.sdk.service.oss2.agentic.OSSAgenticBucketClient;
import com.aliyun.sdk.service.oss2.agentic.OSSAgenticBucketClientBuilder;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.paginator.ListAgenticBucketsIterable;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ListAgenticBuckets implements Example {

    private static void execute(
            String endpoint,
            String region,
            String accountId) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAgenticBucketClientBuilder clientBuilder = OSSAgenticBucketClient.newBuilder()
                .credentialsProvider(provider)
                .region(region)
                .accountId(accountId);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAgenticBucketClient client = clientBuilder.build()) {
            ListAgenticBucketsIterable paginator = client.listAgenticBucketsPaginator(
                    ListAgenticBucketsRequest.newBuilder()
                            .build());

            for (ListAgenticBucketsResult result : paginator) {
                if (result.agenticBuckets() == null) {
                    continue;
                }
                for (AgenticBucketSummary info : result.agenticBuckets()) {
                    System.out.printf("agentic bucket: name:%s, storage class:%s, data redundancy type:%s, create time:%s\n",
                            info.name(), info.storageClass(), info.dataRedundancyType(), info.createTime());
                }
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
        opts.addOption(Option.builder().longOpt("account-id").desc("The ID of the Alibaba Cloud account.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String accountId = cmd.getParsedOptionValue("account-id");
        execute(endpoint, region, accountId);
    }
}
