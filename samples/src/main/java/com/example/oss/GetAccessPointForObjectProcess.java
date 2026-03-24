package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetAccessPointForObjectProcess implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String accessPointForObjectProcessName) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSClient client = getDefaultClient(endpoint, region, provider)) {
            
            GetAccessPointForObjectProcessRequest request = GetAccessPointForObjectProcessRequest.newBuilder()
                    .bucket(bucket)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            GetAccessPointForObjectProcessResult result = client.getAccessPointForObjectProcess(request);

            System.out.printf("Get access point for object process success:\n");
            System.out.printf("  Name: %s\n", result.accessPointForObjectProcessResult().accessPointName());
            System.out.printf("  ARN: %s\n", result.accessPointForObjectProcessResult().accessPointForObjectProcessArn());
            System.out.printf("  Alias: %s\n", result.accessPointForObjectProcessResult().accessPointForObjectProcessAlias());
            System.out.printf("  Status: %s\n", result.accessPointForObjectProcessResult().status());
            System.out.printf("  Request ID: %s\n", result.requestId());

        } catch (Exception e) {
            System.out.printf("Error:\n%s", e);
        }
    }

    private static OSSClient getDefaultClient(String endpoint, String region, CredentialsProvider provider) {
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        return clientBuilder.build();
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("accessPointForObjectProcessName").desc("The full name of the access point for object process.").hasArg().required().get());
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