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

public class PutSymlink implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String symlinkTarget,
            String objectAcl,
            String storageClass,
            String forbidOverwrite) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            
            PutSymlinkRequest.Builder requestBuilder = PutSymlinkRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .symlinkTarget(symlinkTarget);
            
            if (objectAcl != null) {
                requestBuilder.objectAcl(objectAcl);
            }
            
            if (storageClass != null) {
                requestBuilder.storageClass(storageClass);
            }
            
            if (forbidOverwrite != null) {
                requestBuilder.forbidOverwrite(forbidOverwrite);
            }

            PutSymlinkResult result = client.putSymlink(requestBuilder.build());

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());
            
            if (result.versionId() != null) {
                System.out.printf("Version ID: %s\n", result.versionId());
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
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key").desc("The name of the symbolic link.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("symlinkTarget").desc("The target object to which the symbolic link points.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("objectAcl").desc("The access control list (ACL) of the object. Valid values: default, private, public-read, public-read-write.").hasArg().get());
        opts.addOption(Option.builder().longOpt("storageClass").desc("The storage class of the object. Valid values: Standard, IA, Archive, ColdArchive.").hasArg().get());
        opts.addOption(Option.builder().longOpt("forbidOverwrite").desc("Specifies whether the PutSymlink operation overwrites the object that has the same name. Valid values: true, false.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        String symlinkTarget = cmd.getParsedOptionValue("symlinkTarget");
        String objectAcl = cmd.getParsedOptionValue("objectAcl");
        String storageClass = cmd.getParsedOptionValue("storageClass");
        String forbidOverwrite = cmd.getParsedOptionValue("forbidOverwrite");
        execute(endpoint, region, bucket, key, symlinkTarget, objectAcl, storageClass, forbidOverwrite);
    }
}
