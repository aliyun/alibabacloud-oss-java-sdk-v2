package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.List;

public class GetBucketLifecycleAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {

            GetBucketLifecycleResult result = client.getBucketLifecycleAsync(GetBucketLifecycleRequest.newBuilder()
                            .bucket(bucket)
                            .build()).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

            LifecycleConfiguration lifecycleConfiguration = result.lifecycleConfiguration();
            if (lifecycleConfiguration != null && lifecycleConfiguration.rules() != null) {
                List<LifecycleRule> rules = lifecycleConfiguration.rules();
                System.out.printf("Found %d lifecycle rules:\n", rules.size());
                for (int i = 0; i < rules.size(); i++) {
                    LifecycleRule rule = rules.get(i);
                    System.out.printf("Rule %d:\n", i + 1);
                    System.out.printf("  ID: %s\n", rule.id());
                    System.out.printf("  Status: %s\n", rule.status());
                    System.out.printf("  Prefix: %s\n", rule.prefix());
                    
                    // Display expiration rules
                    if (rule.expiration() != null) {
                        if (rule.expiration().days() != null) {
                            System.out.printf("  Expiration days: %d\n", rule.expiration().days());
                        }
                        if (rule.expiration().createdBeforeDate() != null) {
                            System.out.printf("  Expiration created before date: %s\n", rule.expiration().createdBeforeDate());
                        }
                        if (rule.expiration().expiredObjectDeleteMarker() != null) {
                            System.out.printf("  Expiration expired object delete marker: %s\n", rule.expiration().expiredObjectDeleteMarker());
                        }
                    }
                    
                    // Display storage class transition rules
                    if (rule.transitions() != null && !rule.transitions().isEmpty()) {
                        System.out.println("  Transitions:");
                        for (Transition transition : rule.transitions()) {
                            if (transition.days() != null) {
                                System.out.printf("    Days: %d, Storage Class: %s\n", transition.days(), transition.storageClass());
                            }
                            if (transition.createdBeforeDate() != null) {
                                System.out.printf("    Created Before Date: %s, Storage Class: %s\n", transition.createdBeforeDate(), transition.storageClass());
                            }
                        }
                    }
                }
            } else {
                System.out.println("No lifecycle configuration found.");
            }

        } catch (Exception e) {
            System.out.printf("error:\n%s", e);
            e.printStackTrace();
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
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        execute(endpoint, region, bucket);
    }
}