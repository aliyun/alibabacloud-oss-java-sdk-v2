package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.transfermanager.UploadResult;
import com.aliyun.sdk.service.oss2.transfermanager.Uploader;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.FileInputStream;

public class UploadFrom implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String filePath,
            String checkpointDir) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
             Uploader uploader = new Uploader(client);

            // Uploader with custom options
            /* UploaderOptions options = UploaderOptions.newBuilder()
                     .partSize(100 * 1024)
                     .parallelNum(5)
                     .leavePartsOnError(true)
                     .enableCheckpoint(true)
                     .checkpointDir(checkpointDir)
                     .build();
             Uploader uploader = new Uploader(client, options);*/

            try (FileInputStream fis = new FileInputStream(filePath)) {
                UploadResult result = uploader.uploadFrom(PutObjectRequest.newBuilder()
                        .bucket(bucket)
                        .key(key)
                        .build(), BinaryData.fromStream(fis) );

                System.out.printf("status code: %d, request id: %s, content md5: %s, etag: %s, hash crc64: %s, version id: %s, server time: %s%n",
                        result.statusCode(),
                        result.headers() != null ? result.headers().get("x-oss-request-id") : "",
                        result.headers() != null ? result.headers().get("Content-MD5") : "",
                        result.etag(),
                        result.hashCrc64ecma(),
                        result.versionId(),
                        result.headers() != null ? result.headers().get("x-oss-server-time") : ""
                );
            }

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
        opts.addOption(Option.builder().longOpt("filePath").desc("The path of upload file.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("checkpointDir").desc("The directory for checkpoint.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        String filePath = cmd.getParsedOptionValue("filePath");
        String checkpointDir = cmd.getParsedOptionValue("checkpointDir");
        execute(endpoint, region, bucket, key, filePath, checkpointDir);
    }
}
