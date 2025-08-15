package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.io.BoundedInputStream;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MultipartUpload implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String filePath) throws IOException {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            // Step 1: Initiate multipart upload
            InitiateMultipartUploadResult initiateResult = client.initiateMultipartUpload(
                    com.aliyun.sdk.service.oss2.models.InitiateMultipartUploadRequest.newBuilder()
                            .bucket(bucket)
                            .key(key)
                            .build());

            String uploadId = initiateResult.initiateMultipartUpload().uploadId();
            System.out.printf("Initiated multipart upload, status code:%d, request id:%s, upload id:%s\n",
                    initiateResult.statusCode(), initiateResult.requestId(), uploadId);

            // Step 2: Upload parts
            File file = new File(filePath);
            long fileSize = file.length();
            long partSize = 100 * 1024; // 100KB per part
            int partNumber = 1;
            List<Part> uploadParts = new ArrayList<>();

            for (long start = 0; start < fileSize; start += partSize) {
                long curPartSize = Math.min(partSize, fileSize - start);

                // Create a section of the file to upload
                try (InputStream is = new FileInputStream(file)) {
                    is.skip(start);
                    BoundedInputStream boundedInputStream = new BoundedInputStream(is, curPartSize);

                    // Upload the part
                    UploadPartResult partResult = client.uploadPart(UploadPartRequest.newBuilder()
                            .bucket(bucket)
                            .key(key)
                            .uploadId(uploadId)
                            .partNumber((long) partNumber)
                            .body(BinaryData.fromStream(boundedInputStream))
                            .build());

                    System.out.printf("status code: %d, request id: %s, part number: %d, etag: %s\n",
                            partResult.statusCode(), partResult.requestId(), partNumber, partResult.eTag());

                    uploadParts.add(Part.newBuilder()
                            .partNumber((long) partNumber)
                            .eTag(partResult.eTag())
                            .build());
                }
                partNumber++;
            }

            // Step 3: Complete multipart upload
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
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("file_path").desc("The path of Upload file.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        String filePath = cmd.getParsedOptionValue("file_path");
        try {
            execute(endpoint, region, bucket, key, filePath);
        } catch (IOException e) {
            System.out.printf("IO error:\n%s", e);
        }
    }
}
