package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class DeleteAccessPointPolicyForObjectProcessAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String accessPointForObjectProcessName) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {
            DeleteAccessPointPolicyForObjectProcessRequest request = DeleteAccessPointPolicyForObjectProcessRequest.newBuilder()
                    .bucket(bucket)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            DeleteAccessPointPolicyForObjectProcessResult result = client.deleteAccessPointPolicyForObjectProcessAsync(request).get();

            System.out.printf("Delete access point policy for object process async success, request id: %s\n",
                    result.requestId());

        } catch (Exception e) {
            System.out.printf("Error:\n%s", e);
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
        opts.addOption(Option.builder().longOpt("accessPointForObjectProcessName").desc("The name of the access point.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String accessPointForObjectProcessName = cmd.getParsedOptionValue("accessPointForObjectProcessName");
        execute(endpoint, region, bucket, accessPointForObjectProcessName);
    }
}