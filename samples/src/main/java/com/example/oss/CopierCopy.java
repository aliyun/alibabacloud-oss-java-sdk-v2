package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.CopyObjectRequest;
import com.aliyun.sdk.service.oss2.transfermanager.CopyResult;
import com.aliyun.sdk.service.oss2.transfermanager.Copier;
import com.aliyun.sdk.service.oss2.transfermanager.CopierOptions;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CopierCopy implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String sourceBucket,
            String sourceKey,
            String leavePartsOnError,
            String disableShallowCopy) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {

            // Copier with custom options
             CopierOptions options = CopierOptions.newBuilder()
                     .partSize(100 * 1024)
                     .parallelNum(5)
                     .leavePartsOnError(Boolean.parseBoolean(leavePartsOnError))
                     .disableShallowCopy(Boolean.parseBoolean(disableShallowCopy))
                     .build();
             Copier copier = new Copier(client, options);

            CopyResult result = copier.copy(CopyObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .sourceBucket(sourceBucket)
                    .sourceKey(sourceKey)
                    .build());

            System.out.printf("status code: %d, request id: %s, etag: %s, version id: %s, hash crc64: %s%n",
                    result.statusCode(),
                    result.headers() != null ? result.headers().get("x-oss-request-id") : "",
                    result.etag(),
                    result.versionId(),
                    result.hashCrc64ecma()
            );

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("sourceBucket").desc("The name of the source bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("sourceKey").desc("The name of the source object.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("leavePartsOnError").desc("Leave parts on error.").hasArg().get());
        opts.addOption(Option.builder().longOpt("disableShallowCopy").desc("Disable shallow copy.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        String sourceBucket = cmd.getParsedOptionValue("sourceBucket");
        String sourceKey = cmd.getParsedOptionValue("sourceKey");
        String leavePartsOnError = cmd.getParsedOptionValue("leavePartsOnError");
        String disableShallowCopy = cmd.getParsedOptionValue("disableShallowCopy");
        execute(endpoint, region, bucket, key, sourceBucket, sourceKey, leavePartsOnError, disableShallowCopy);
    }
}
