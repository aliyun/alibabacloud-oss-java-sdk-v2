package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GetObjectAclAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String versionId) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {
            
            GetObjectAclRequest.Builder requestBuilder = GetObjectAclRequest.newBuilder()
                    .bucket(bucket)
                    .key(key);
            
            if (versionId != null) {
                requestBuilder.versionId(versionId);
            }

            GetObjectAclResult result = client.getObjectAclAsync(requestBuilder.build()).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());
            
            if (result.versionId() != null) {
                System.out.printf("Version ID: %s\n", result.versionId());
            }
            
            AccessControlPolicy accessControlPolicy = result.accessControlPolicy();
            if (accessControlPolicy != null && accessControlPolicy.accessControlList() != null) {
                System.out.printf("Object ACL: %s\n", accessControlPolicy.accessControlList().grant());
                System.out.printf("Owner ID: %s\n", accessControlPolicy.owner().id());
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
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("versionId").desc("The version id of the object.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        String versionId = cmd.getParsedOptionValue("versionId");
        execute(endpoint, region, bucket, key, versionId);
    }
}
