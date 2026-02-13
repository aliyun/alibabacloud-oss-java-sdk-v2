package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.concurrent.CompletableFuture;

public class PutAccessPointConfigForObjectProcessAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String accessPointForObjectProcessName,
            String publicAccessBlockEnabled,
            String functionAssumeRoleArn,
            String functionArn) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {

            ObjectProcessConfiguration objectProcessConfiguration = ObjectProcessConfiguration.newBuilder()
                    .allowedFeatures(ObjectProcessAllowedFeatures.newBuilder()
                            .allowedFeature(java.util.Arrays.asList("GetObject-Range"))
                            .build())
                    .transformationConfigurations(TransformationConfigurations.newBuilder()
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
                            .build())
                    .build();

            PutAccessPointConfigForObjectProcessConfiguration config = PutAccessPointConfigForObjectProcessConfiguration.newBuilder()
                    .objectProcessConfiguration(objectProcessConfiguration)
                    .publicAccessBlockConfiguration(PublicAccessBlockConfiguration.newBuilder()
                            .blockPublicAccess(Boolean.parseBoolean(publicAccessBlockEnabled))
                            .build())
                    .build();

            PutAccessPointConfigForObjectProcessRequest request = PutAccessPointConfigForObjectProcessRequest.newBuilder()
                    .bucket(bucket)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .putAccessPointConfigForObjectProcessConfiguration(config)
                    .build();

            CompletableFuture<PutAccessPointConfigForObjectProcessResult> future = client.putAccessPointConfigForObjectProcessAsync(request);

            PutAccessPointConfigForObjectProcessResult result = future.get();

            System.out.printf("Put access point config for object process success, status code: %d\n",
                    result.statusCode());

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
        opts.addOption(Option.builder().longOpt("accessPointForObjectProcessName").desc("The full name of the access point for object process.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("publicAccessBlockEnabled").desc("Whether to enable public access block.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("functionAssumeRoleArn").desc("The function assume role ARN.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("functionArn").desc("The function ARN.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String accessPointForObjectProcessName = cmd.getParsedOptionValue("accessPointForObjectProcessName");
        String publicAccessBlockEnabled = cmd.getParsedOptionValue("publicAccessBlockEnabled");
        String functionAssumeRoleArn = cmd.getParsedOptionValue("functionAssumeRoleArn");
        String functionArn = cmd.getParsedOptionValue("functionArn");
        execute(endpoint, region, bucket, accessPointForObjectProcessName, publicAccessBlockEnabled, functionAssumeRoleArn, functionArn);
    }
}