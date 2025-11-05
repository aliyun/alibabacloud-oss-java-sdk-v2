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

public class PutPublicAccessBlock implements Example {

    private static void execute(
            String endpoint,
            String region,
            Boolean blockPublicAccess) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {

            PublicAccessBlockConfiguration publicAccessBlockConfiguration = PublicAccessBlockConfiguration.newBuilder()
                    .blockPublicAccess(blockPublicAccess)
                    .build();

            PutPublicAccessBlockResult result = client.putPublicAccessBlock(PutPublicAccessBlockRequest.newBuilder()
                            .publicAccessBlockConfiguration(publicAccessBlockConfiguration)
                            .build());

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

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
        opts.addOption(Option.builder().longOpt("blockPublicAccess").desc("Specifies whether to enable Block Public Access. true: enables Block Public Access. false (default): disables Block Public Access.").hasArg().required().type(Boolean.class).get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String blockPublicAccessStr = cmd.getParsedOptionValue("blockPublicAccess");
        Boolean blockPublicAccess = Boolean.parseBoolean(blockPublicAccessStr);
        execute(endpoint, region, blockPublicAccess);
    }
}