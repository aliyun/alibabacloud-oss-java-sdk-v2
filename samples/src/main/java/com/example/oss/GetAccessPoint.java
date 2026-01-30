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

public class GetAccessPoint implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String name) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSClient client = getDefaultClient(endpoint, region, provider)) {
            GetAccessPointRequest request = GetAccessPointRequest.newBuilder()
                    .bucket(bucket)
                    .accessPointName(name)
                    .build();

            GetAccessPointResult result = client.getAccessPoint(request);

            System.out.printf("Get access point success, name: %s, bucket: %s, network origin: %s, request id: %s\n",
                    result.accessPointResult().accessPointName(), result.accessPointResult().bucket(), result.accessPointResult().networkOrigin(), result.requestId());

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
        opts.addOption(Option.builder().longOpt("name").desc("The name of the access point.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String name = cmd.getParsedOptionValue("name");
        execute(endpoint, region, bucket, name);
    }
}