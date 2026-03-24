package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.concurrent.CompletableFuture;

public class PutAccessPointPolicyForObjectProcessAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String accessPointForObjectProcessName,
            String userId) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {

            String policyDocument = "{\n" +
                    "  \"Version\":\"1\",\n" +
                    "  \"Statement\":[\n" +
                    "    {\n" +
                    "      \"Action\":[\n" +
                    "        \"oss:GetObject\",\n" +
                    "        \"oss:PutObject\"\n" +
                    "      ],\n" +
                    "      \"Effect\":\"Allow\",\n" +
                    "      \"Principal\":[\"" + userId + "\"],\n" +
                    "      \"Resource\":[\n" +
                    "        \"acs:oss:cn-hangzhou:" + userId + ":accesspointforobjectprocess/" + accessPointForObjectProcessName + "/object/*\"\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            PutAccessPointPolicyForObjectProcessRequest request = PutAccessPointPolicyForObjectProcessRequest.newBuilder()
                    .bucket(bucket)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .body(BinaryData.fromString(policyDocument))
                    .build();

            CompletableFuture<PutAccessPointPolicyForObjectProcessResult> future = client.putAccessPointPolicyForObjectProcessAsync(request);

            PutAccessPointPolicyForObjectProcessResult result = future.get();

            System.out.printf("Put access point policy for object process success, status code: %d\n",
                    result.statusCode());

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
        opts.addOption(Option.builder().longOpt("accessPointForObjectProcessName").desc("The full name of the access point for object process.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("userId").desc("The user ID for the policy principal.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String accessPointForObjectProcessName = cmd.getParsedOptionValue("accessPointForObjectProcessName");
        String userId = cmd.getParsedOptionValue("userId");
        execute(endpoint, region, bucket, accessPointForObjectProcessName, userId);
    }
}