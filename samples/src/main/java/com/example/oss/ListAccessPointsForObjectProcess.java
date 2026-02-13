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

public class ListAccessPointsForObjectProcess implements Example {

    private static void execute(
            String endpoint,
            String region) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSClient client = getDefaultClient(endpoint, region, provider)) {
            
            ListAccessPointsForObjectProcessRequest request = ListAccessPointsForObjectProcessRequest.newBuilder()
                    .build();

            ListAccessPointsForObjectProcessResult result = client.listAccessPointsForObjectProcess(request);

            System.out.printf("List access points for object process success, request id: %s\n", result.requestId());
            
            if (result.accessPointsForObjectProcessResult() != null && 
                result.accessPointsForObjectProcessResult().accessPointsForObjectProcess() != null) {
                for (AccessPointForObjectProcess apop : result.accessPointsForObjectProcessResult().accessPointsForObjectProcess().accessPointForObjectProcess()) {
                    System.out.printf("  Access point: %s\n", apop.accessPointName());
                    System.out.printf("  Access point for object process: %s\n", apop.accessPointNameForObjectProcess());
                    System.out.printf("  Status: %s\n", apop.status());
                }
                System.out.println("  Access Points found.");
            } else {
                System.out.println("  No access points found.");
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
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        execute(endpoint, region);
    }
}