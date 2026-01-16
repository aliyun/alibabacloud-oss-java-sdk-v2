package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.io.BoundedInputStream;
import com.aliyun.sdk.service.oss2.models.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Using multipart upload interface to implement resumable upload functionality asynchronously
 */
public class SimplifiedResumableUploadAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String filePath,
            String uploadId,
            boolean enableCheckPoint,
            long partSize,
            int taskNum) throws Exception {
        
        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {
            // Call async resumable upload function
            String resultUploadId = uploadFile(client, bucket, key, filePath, uploadId, enableCheckPoint, partSize, taskNum);
            System.out.println("File upload completed, final UploadId: " + resultUploadId);
        }
    }

    /**
     * Async resumable upload functionality implementation
     */
    public static String uploadFile(OSSAsyncClient client, String bucketName, String objectKey, String localFilePath, String uploadId, boolean enableCheckPoint, long partSize, int taskNum) throws Exception {
        File file = new File(localFilePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + localFilePath);
        }

        // If partSize is not specified, default to 1MB
        if (partSize <= 0) {
            partSize = 1024 * 1024L; // 1MB
        }

        // If taskNum is not specified, default to 1
        if (taskNum <= 0) {
            taskNum = 1;
        }

        // Make uploadId effectively final to avoid lambda variable issue
        final String finalUploadId = uploadId;

        try {
            // Step 1: Initialize upload or get existing upload status
            String actualUploadId = finalUploadId;
            if (actualUploadId == null) {
                // If uploadId is null, initialize upload event and get uploadId
                CompletableFuture<InitiateMultipartUploadResult> initiateFuture = client.initiateMultipartUploadAsync(
                        InitiateMultipartUploadRequest.newBuilder()
                                .bucket(bucketName)
                                .key(objectKey)
                                .build());

                InitiateMultipartUploadResult initiateResult = initiateFuture.get();
                actualUploadId = initiateResult.initiateMultipartUpload().uploadId();
                System.out.printf("Initialized upload, got UploadId: %s\n", actualUploadId);
            } else {
                // If uploadId is not null, get uploaded parts via listParts interface
                System.out.printf("Using existing UploadId: %s, getting uploaded parts\n", actualUploadId);
                CompletableFuture<ListPartsResult> listPartsFuture = client.listPartsAsync(
                        ListPartsRequest.newBuilder()
                                .bucket(bucketName)
                                .key(objectKey)
                                .uploadId(actualUploadId)
                                .build());
                
                ListPartsResult listPartsResult = listPartsFuture.get();
                System.out.printf("Uploaded %d parts\n", listPartsResult.parts().size());
            }

            // Step 2: Filter out uploaded parts, upload unuploaded parts
            long fileLength = file.length();
            int partCount = (int) (fileLength / partSize);
            if (fileLength % partSize != 0) {
                partCount++;
            }

            // Get uploaded parts information
            ConcurrentHashMap<Integer, Part> allPartsMap = new ConcurrentHashMap<>();
            
            // Get uploaded parts list
            CompletableFuture<ListPartsResult> listPartsFuture = client.listPartsAsync(
                    ListPartsRequest.newBuilder()
                            .bucket(bucketName)
                            .key(objectKey)
                            .uploadId(actualUploadId)
                            .build());
            
            ListPartsResult listPartsResult = listPartsFuture.get();
            List<Part> uploadedParts = listPartsResult.parts();
            List<Long> uploadedPartNumbers = uploadedParts.stream()
                    .map(Part::partNumber)
                    .collect(Collectors.toList());

            // Create thread pool for concurrent uploads
            ExecutorService executorService = Executors.newFixedThreadPool(taskNum);
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            // Create upload task for each unuploaded part
            for (int i = 0; i < partCount; i++) {
                long partNumber = i + 1; // Part number starts from 1
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;

                // Check if this part is already uploaded
                if (uploadedPartNumbers.contains(partNumber)) {
                    System.out.printf("Part %d already exists, skipping upload\n", partNumber);
                    // Add uploaded part information
                    Part existingPart = uploadedParts.stream()
                            .filter(p -> p.partNumber().equals(partNumber))
                            .findFirst()
                            .orElse(null);
                    if (existingPart != null) {
                        allPartsMap.put((int)partNumber, Part.newBuilder()
                                .partNumber(existingPart.partNumber())
                                .eTag(existingPart.eTag())
                                .build());
                    }
                    continue;
                }

                // Create upload task
                final long finalPartNumber = partNumber;
                final String finalActualUploadId = actualUploadId;
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        fis.skip(startPos);
                        BoundedInputStream boundedInputStream = new BoundedInputStream(fis, curPartSize);

                        // Upload part asynchronously
                        CompletableFuture<UploadPartResult> uploadPartFuture = client.uploadPartAsync(UploadPartRequest.newBuilder()
                                .bucket(bucketName)
                                .key(objectKey)
                                .uploadId(finalActualUploadId)
                                .partNumber(finalPartNumber)
                                .body(com.aliyun.sdk.service.oss2.transport.BinaryData.fromStream(boundedInputStream))
                                .build());

                        UploadPartResult uploadPartResult = uploadPartFuture.get();

                        System.out.printf("Upload part %d completed, ETag: %s, status code: %d\n",
                                finalPartNumber, uploadPartResult.eTag(), uploadPartResult.statusCode());


                        // Add to allParts map
                        allPartsMap.put((int)finalPartNumber, Part.newBuilder()
                                .partNumber(finalPartNumber)
                                .eTag(uploadPartResult.eTag())
                                .build());

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, executorService);

                futures.add(future);
            }

            // Wait for all parts to finish uploading
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            
            // Shutdown thread pool
            executorService.shutdown();

            // Convert map to list and sort by part number
            List<Part> allParts = new ArrayList<>(allPartsMap.values());
            Collections.sort(allParts, (p1, p2) -> p1.partNumber().compareTo(p2.partNumber()));

            // Step 3: Complete multipart upload
            CompleteMultipartUpload completeMultipartUpload = CompleteMultipartUpload.newBuilder()
                    .parts(allParts)
                    .build();

            CompletableFuture<CompleteMultipartUploadResult> completeFuture = client.completeMultipartUploadAsync(
                    CompleteMultipartUploadRequest.newBuilder()
                            .bucket(bucketName)
                            .key(objectKey)
                            .uploadId(actualUploadId)
                            .completeMultipartUpload(completeMultipartUpload)
                            .build());

            CompleteMultipartUploadResult completeResult = completeFuture.get();

            System.out.printf("File uploaded successfully: %s, status code: %d, request ID: %s\n", 
                    objectKey, completeResult.statusCode(), completeResult.requestId());
            return actualUploadId;

        } catch (Exception e) {
            System.err.println("Exception occurred during upload: " + e.getMessage());
            
            if (enableCheckPoint) {
                // If enableCheckPoint is turned on, the exception includes uploadId for next use
                System.out.println("Checkpoint resume enabled, UploadId: " + finalUploadId + " available for next use");
                throw e; // Throw exception, but uploadId is saved for subsequent use
            } else {
                // If enableCheckPoint is not turned on, call abortMultipartUpload to clean up fragments
                if (finalUploadId != null) {
                    System.out.println("Checkpoint resume disabled, cleaning up fragments, UploadId: " + finalUploadId);
                    try {
                        client.abortMultipartUploadAsync(
                                AbortMultipartUploadRequest.newBuilder()
                                        .bucket(bucketName)
                                        .key(objectKey)
                                        .uploadId(finalUploadId)
                                        .build()).get();

                        System.out.println("Fragment cleanup successful");
                    } catch (Exception abortEx) {
                        System.err.println("Fragment cleanup failed: " + abortEx.getMessage());
                    }
                }
                throw e;
            }
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
    public org.apache.commons.cli.Options getOptions() {
        org.apache.commons.cli.Options opts = new org.apache.commons.cli.Options();
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("filePath").desc("The path of Upload file.").hasArg().required().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("uploadId").desc("The upload ID for resumable upload. If not provided, a new multipart upload will be initiated.").hasArg().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("enableCheckPoint").desc("Enable checkpoint resume feature.").hasArg().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("partSize").desc("The size of each part in bytes. Default is 1MB.").hasArg().get());
        opts.addOption(org.apache.commons.cli.Option.builder().longOpt("taskNum").desc("The number of concurrent upload threads. Default is 1.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(org.apache.commons.cli.CommandLine cmd) throws org.apache.commons.cli.ParseException {
        String endpoint = cmd.getOptionValue("endpoint");
        String region = cmd.getOptionValue("region");
        String bucket = cmd.getOptionValue("bucket");
        String key = cmd.getOptionValue("key");
        String filePath = cmd.getOptionValue("filePath");
        String uploadId = cmd.getOptionValue("uploadId");
        String enableCheckPointStr = cmd.getOptionValue("enableCheckPoint", "false");
        String partSizeStr = cmd.getOptionValue("partSize", "1048576"); // 默认1MB
        String taskNumStr = cmd.getOptionValue("taskNum", "1"); // 默认1个线程
        
        boolean enableCheckPoint = Boolean.parseBoolean(enableCheckPointStr);
        long partSize = Long.parseLong(partSizeStr);
        int taskNum = Integer.parseInt(taskNumStr);
        
        try {
            execute(endpoint, region, bucket, key, filePath, uploadId, enableCheckPoint, partSize, taskNum);
        } catch (Exception e) {
            System.out.printf("Error occurred during resumable upload:\n%s", e);
        }
    }
}