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

public class CreateAccessPointForObjectProcess implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String accessPointName,
            String accessPointForObjectProcessName,
            String functionAssumeRoleArn,
            String functionArn,
            String allowAnonymousAccessForObjectProcess) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSClient client = getDefaultClient(endpoint, region, provider)) {

            ObjectProcessConfiguration.Builder objectProcessConfigBuilder = ObjectProcessConfiguration.newBuilder()
                    .allowedFeatures(ObjectProcessAllowedFeatures.newBuilder()
                            .allowedFeature(java.util.Arrays.asList("GetObject-Range"))
                            .build());

            if (functionAssumeRoleArn != null && !functionAssumeRoleArn.isEmpty() && 
                functionArn != null && !functionArn.isEmpty()) {
                objectProcessConfigBuilder.transformationConfigurations(TransformationConfigurations.newBuilder()
                        .transformationConfiguration(java.util.Arrays.asList(
                                TransformationConfiguration.newBuilder()
                                        .actions(AccessPointActions.newBuilder()
                                                .action(java.util.Arrays.asList("GetObject"))
                                                .build())
                                        .contentTransformation(ContentTransformation.newBuilder()
                                                .functionCompute(ObjectProcessFunctionCompute.newBuilder()
                                                        .functionAssumeRoleArn(functionAssumeRoleArn)
                                                        .functionArn(functionArn)
                                                        .build())
                                                .build())
                                        .build()))
                        .build());
            }
            
            ObjectProcessConfiguration objectProcessConfiguration = objectProcessConfigBuilder.build();

            CreateAccessPointForObjectProcessConfiguration.Builder configBuilder = CreateAccessPointForObjectProcessConfiguration.newBuilder()
                    .accessPointName(accessPointName)
                    .objectProcessConfiguration(objectProcessConfiguration);
            

            if (allowAnonymousAccessForObjectProcess != null && !allowAnonymousAccessForObjectProcess.isEmpty()) {
                configBuilder.allowAnonymousAccessForObjectProcess(allowAnonymousAccessForObjectProcess);
            }
            
            CreateAccessPointForObjectProcessConfiguration config = configBuilder.build();

            CreateAccessPointForObjectProcessRequest request = CreateAccessPointForObjectProcessRequest.newBuilder()
                    .bucket(bucket)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .createAccessPointForObjectProcessConfiguration(config)
                    .build();

            CreateAccessPointForObjectProcessResult result = client.createAccessPointForObjectProcess(request);

            System.out.printf("Create access point for object process success, ARN: %s, alias: %s, request id: %s\n",
                    result.accessPointForObjectProcessResult().accessPointForObjectProcessArn(), 
                    result.accessPointForObjectProcessResult().accessPointForObjectProcessAlias(), 
                    result.requestId());

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
        opts.addOption(Option.builder().longOpt("accessPointName").desc("The basic name of the access point.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("accessPointForObjectProcessName").desc("The full name of the access point for object process.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("functionAssumeRoleArn").desc("The function assume role ARN.").hasArg().get());
        opts.addOption(Option.builder().longOpt("functionArn").desc("The function ARN.").hasArg().get());
        opts.addOption(Option.builder().longOpt("allowAnonymousAccessForObjectProcess").desc("Whether to allow anonymous access for object process.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String accessPointName = cmd.getParsedOptionValue("accessPointName");
        String accessPointForObjectProcessName = cmd.getParsedOptionValue("accessPointForObjectProcessName");
        String functionAssumeRoleArn = cmd.getParsedOptionValue("functionAssumeRoleArn");
        String functionArn = cmd.getParsedOptionValue("functionArn");
        String allowAnonymousAccessForObjectProcess = cmd.getParsedOptionValue("allowAnonymousAccessForObjectProcess");
        execute(endpoint, region, bucket, accessPointName, accessPointForObjectProcessName, functionAssumeRoleArn, functionArn, allowAnonymousAccessForObjectProcess);
    }
}