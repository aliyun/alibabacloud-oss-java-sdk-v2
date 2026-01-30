package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CreateAccessPointAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String name,
            String vpcId) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        CreateAccessPointConfiguration config = null;
        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {
            AccessPointVpcConfiguration vpcConfiguration = null;
            if (vpcId != null && !vpcId.isEmpty()) {
                vpcConfiguration = AccessPointVpcConfiguration.newBuilder()
                        .vpcId(vpcId)
                        .build();
                config = CreateAccessPointConfiguration.newBuilder()
                        .accessPointName(name)
                        .networkOrigin("vpc")
                        .vpcConfiguration(vpcConfiguration)
                        .build();
            } else {
                config = CreateAccessPointConfiguration.newBuilder()
                        .accessPointName(name)
                        .networkOrigin("Internet")
                        .build();
            }


            CreateAccessPointResult result = client.createAccessPointAsync(CreateAccessPointRequest.newBuilder()
                            .bucket(bucket)
                            .createAccessPointConfiguration(config)
                            .build()).get();

            System.out.printf("Create access point async success, alias: %s, ARN: %s, request id: %s\n",
                    result.accessPointResult().alias(), result.accessPointResult().accessPointArn(), result.requestId());

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
        opts.addOption(Option.builder().longOpt("name").desc("The name of the access point.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("vpcId").desc("The VPC ID for the access point.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String name = cmd.getParsedOptionValue("name");
        String vpcId = cmd.getParsedOptionValue("vpcId");
        execute(endpoint, region, bucket, name, vpcId);
    }
}