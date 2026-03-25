package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.OSSAsyncClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class PutBucketObjectWormConfigurationAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String objectWormEnabled,
            String mode,
            Integer days,
            Integer years) throws Exception {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSAsyncClientBuilder clientBuilder = OSSAsyncClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSAsyncClient client = clientBuilder.build()) {
            ObjectWormConfiguration.Builder configBuilder = ObjectWormConfiguration.newBuilder()
                    .objectWormEnabled(objectWormEnabled);

            // Only set rule if days or years is provided
            if (days != null || years != null) {
                ObjectWormConfigurationDefaultRetention defaultRetention = ObjectWormConfigurationDefaultRetention.newBuilder()
                        .mode(mode)
                        .days(days)
                        .years(years)
                        .build();

                ObjectWormConfigurationRule rule = ObjectWormConfigurationRule.newBuilder()
                        .defaultRetention(defaultRetention)
                        .build();

                configBuilder.rule(rule);
            }

            ObjectWormConfiguration objectWormConfiguration = configBuilder.build();

            PutBucketObjectWormConfigurationRequest request = PutBucketObjectWormConfigurationRequest.newBuilder()
                    .bucket(bucket)
                    .objectWormConfiguration(objectWormConfiguration)
                    .build();

            PutBucketObjectWormConfigurationResult result = client.putBucketObjectWormConfigurationAsync(request).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

        } catch (Exception e) {
            System.out.printf("error:\n%s", e);
            throw e;
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("objectWormEnabled").desc("Whether to enable object-level retention policy.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("mode").desc("The retention mode. Valid values: GOVERNANCE, COMPLIANCE.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("days").desc("The retention period in days.").hasArg().get());
        opts.addOption(Option.builder().longOpt("years").desc("The retention period in years.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String objectWormEnabled = cmd.getParsedOptionValue("objectWormEnabled");
        String mode = cmd.getParsedOptionValue("mode");
        Integer days = cmd.getParsedOptionValue("days");
        Integer years = cmd.getParsedOptionValue("years");
        try {
            execute(endpoint, region, bucket, objectWormEnabled, mode, days, years);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
