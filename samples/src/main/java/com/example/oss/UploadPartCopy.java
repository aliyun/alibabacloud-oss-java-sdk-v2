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

import java.util.ArrayList;
import java.util.List;

public class UploadPartCopy implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String sourceBucket,
            String sourceKey) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            // Step 1: Get source object metadata
            GetObjectMetaResult resultMeta = client.getObjectMeta(GetObjectMetaRequest.newBuilder()
                    .bucket(sourceBucket)
                    .key(sourceKey)
                    .build());

            System.out.printf("Got source object meta, status code:%d, request id:%s, content length:%d\n",
                    resultMeta.statusCode(), resultMeta.requestId(), resultMeta.contentLength());

            // Step 2: Initiate multipart upload
            InitiateMultipartUploadResult initiateResult = client.initiateMultipartUpload(
                    InitiateMultipartUploadRequest.newBuilder()
                            .bucket(bucket)
                            .key(key)
                            .build());

            String uploadId = initiateResult.initiateMultipartUpload().uploadId();
            System.out.printf("Initiated multipart upload, status code:%d, request id:%s, upload id:%s\n",
                    initiateResult.statusCode(), initiateResult.requestId(), uploadId);

            // Step 3: Upload parts by copying from source object
            long partSize = 100 * 1024; // 100KB per part
            long totalSize = resultMeta.contentLength();
            int partNumber = 1;
            List<Part> uploadParts = new ArrayList<>();
            long offset = 0;

            while (offset < totalSize) {
                long numToUpload = Math.min(partSize, totalSize - offset);
                long end = offset + numToUpload - 1;
                
                UploadPartCopyResult upResult = client.uploadPartCopy(UploadPartCopyRequest.newBuilder()
                        .bucket(bucket)
                        .key(key)
                        .uploadId(uploadId)
                        .partNumber((long) partNumber)
                        .sourceBucket(sourceBucket)
                        .sourceKey(sourceKey)
                        .copySourceRange("bytes=" + offset + "-" + end)
                        .build());

                System.out.printf("status code: %d, request id: %s, part number: %d, last modified: %s, etag: %s, source version id: %s\n",
                        upResult.statusCode(), upResult.requestId(), partNumber, upResult.copyPartResult().lastModified(), 
                        upResult.copyPartResult().eTag(), upResult.copySourceVersionId());

                uploadParts.add(Part.newBuilder()
                        .partNumber((long) partNumber)
                        .eTag(upResult.copyPartResult().eTag())
                        .build());

                offset += numToUpload;
                partNumber++;
            }

            // Step 4: Complete multipart upload
            // Sort parts by part number
            uploadParts.sort((p1, p2) -> p1.partNumber().compareTo(p2.partNumber()));

            CompleteMultipartUpload completeMultipartUpload = CompleteMultipartUpload.newBuilder()
                    .parts(uploadParts)
                    .build();

            CompleteMultipartUploadResult completeResult = client.completeMultipartUpload(
                    CompleteMultipartUploadRequest.newBuilder()
                            .bucket(bucket)
                            .key(key)
                            .uploadId(uploadId)
                            .completeMultipartUpload(completeMultipartUpload)
                            .build());

            System.out.printf("Completed multipart upload, status code:%d, request id:%s, bucket:%s, key:%s, location:%s, etag:%s\n",
                    completeResult.statusCode(), completeResult.requestId(), completeResult.completeMultipartUpload().bucket(),
                    completeResult.completeMultipartUpload().key(), completeResult.completeMultipartUpload().location(),
                    completeResult.completeMultipartUpload().eTag());

        } catch (Exception e) {
            System.out.printf("error:\n%s", e);
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
        execute(endpoint, region, bucket, key, sourceBucket, sourceKey);
    }
}
