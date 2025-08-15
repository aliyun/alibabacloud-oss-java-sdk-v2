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

public class DescribeRegions implements Example {

    private static void execute(
            String endpoint,
            String region,
            String regions) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {

            DescribeRegionsRequest.Builder requestBuilder = DescribeRegionsRequest.newBuilder();
            
            if (regions != null) {
                requestBuilder.regions(regions);
            }

            DescribeRegionsResult result = client.describeRegions(requestBuilder.build());

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

            RegionInfoList regionInfoList = result.regionInfoList();
            if (regionInfoList != null && regionInfoList.regionInfos() != null) {
                System.out.println("Regions information:");
                for (RegionInfo regionInfo : regionInfoList.regionInfos()) {
                    System.out.printf("  Region: %s\n", regionInfo.region());
                    System.out.printf("    Internet endpoint: %s\n", regionInfo.internetEndpoint());
                    System.out.printf("    Internal endpoint: %s\n", regionInfo.internalEndpoint());
                    System.out.printf("    Accelerate endpoint: %s\n", regionInfo.accelerateEndpoint());
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
        opts.addOption(Option.builder().longOpt("regions").desc("The region ID of the request.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String regions = cmd.getParsedOptionValue("regions");
        execute(endpoint, region, regions);
    }
}
