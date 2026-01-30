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

public class ListAccessPoints implements Example {

    private static void execute(
            String endpoint,
            String region,
            Integer maxKeys) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSClient client = getDefaultClient(endpoint, region, provider)) {
            ListAccessPointsRequest.Builder requestBuilder = ListAccessPointsRequest.newBuilder();
            
            if (maxKeys != null) {
                requestBuilder.maxKeys(maxKeys.longValue());
            }

            ListAccessPointsResult result = client.listAccessPoints(requestBuilder.build());

            System.out.printf("List access points success, count: %d, request id: %s\n",
                    result.accessPointsResult().accessPoints().accessPoint().size(), result.requestId());
            
            for (AccessPoint accessPoint : result.accessPointsResult().accessPoints().accessPoint()) {
                System.out.printf("Name: %s, Bucket: %s, Network Origin: %s\n",
                        accessPoint.accessPointName(), accessPoint.bucket(), accessPoint.networkOrigin());
            }

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
        opts.addOption(Option.builder().longOpt("maxKeys").desc("The maximum number of access points to return.").hasArg().type(Integer.class).get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        Integer maxKeys = cmd.getParsedOptionValue("maxKeys");
        execute(endpoint, region, maxKeys);
    }
}