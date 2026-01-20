package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.io.BoundedInputStream;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
            SimplifiedResumableUploadAsyncResult result = uploadFile(client, bucket, key, filePath, uploadId, enableCheckPoint, partSize, taskNum);
            System.out.println("File upload completed, final UploadId: " + result.getUploadId());
        }
    }

    /**
     * Async resumable upload functionality implementation
     */
    public static SimplifiedResumableUploadAsyncResult uploadFile(OSSAsyncClient client, String bucketName, String objectKey, String localFilePath, String uploadId, boolean enableCheckPoint, long partSize, int taskNum) throws Exception {
        File file = new File(localFilePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + localFilePath);
        }

        // If partSize is not specified, default to 1MB
        if (partSize <= 0) {
            partSize = 1024 * 1024L; // 1MB
        }

        long fileLength = file.length();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }

        // Ensure part count doesn't exceed 10000
        if (partCount > 10000) {
            throw new IllegalArgumentException("Number of parts exceeds maximum allowed (10000). Consider increasing part size.");
        }

        // If taskNum is not specified, default to 1
        if (taskNum <= 0) {
            taskNum = 1;
        }

        // Make uploadId effectively final
        final String finalUploadId = uploadId;

        // Get uploaded parts information
        ConcurrentHashMap<Long, Part> allPartsMap = new ConcurrentHashMap<>();

        // Step 1: Initialize upload or get existing upload status
        String actualUploadId = finalUploadId;

        // Check if the actual UploadId is valid
        List<Part> uploadedParts = new ArrayList<>();
        List<Long> uploadedPartNumbers = new ArrayList<>();
        if (enableCheckPoint && actualUploadId != null) {

            try {
                // Use listPartsAsync with pagination to get all parts
                List<Part> allPartsList = new ArrayList<>();
                Long nextPartNumberMarker = null;
                boolean isTruncated = true;

                while (isTruncated) {
                    ListPartsRequest.Builder requestBuilder = ListPartsRequest.newBuilder()
                            .bucket(bucketName)
                            .key(objectKey)
                            .uploadId(actualUploadId);

                    if (nextPartNumberMarker != null) {
                        requestBuilder.partNumberMarker(nextPartNumberMarker);
                    }

                    ListPartsRequest request = requestBuilder.build();
                    CompletableFuture<ListPartsResult> listPartsFuture = client.listPartsAsync(request);
                    ListPartsResult result = listPartsFuture.join();

                    allPartsList.addAll(result.parts());

                    isTruncated = Optional.ofNullable(result.isTruncated()).orElse(false);
                    nextPartNumberMarker = result.nextPartNumberMarker();
                }


                System.out.printf("Found %d uploaded parts\n", allPartsList.size());

                // Add all uploaded parts to the map
                for (Part part : allPartsList) {
                    allPartsMap.put(part.partNumber(), part);
                }

                uploadedParts = allPartsList;

                // Generate uploadedPartNumbers from uploadedParts
                uploadedPartNumbers = uploadedParts.stream()
                        .map(Part::partNumber)
                        .collect(Collectors.toList());

            } catch (Exception e) {
                actualUploadId = null;
            }

        } else {
            // If checkpoint is not enabled or actualUploadId is null, initialize empty lists
            uploadedParts = new ArrayList<>();
            uploadedPartNumbers = new ArrayList<>();
        }

        if (actualUploadId == null) {
            CompletableFuture<InitiateMultipartUploadResult> initiateFuture = client.initiateMultipartUploadAsync(
                    InitiateMultipartUploadRequest.newBuilder()
                            .bucket(bucketName)
                            .key(objectKey)
                            .build());

            InitiateMultipartUploadResult initiateResult = initiateFuture.join();
            actualUploadId = initiateResult.initiateMultipartUpload().uploadId();
            System.out.printf("Initialized upload, got UploadId: %s\n", actualUploadId);
        }


        // Create thread pool for concurrent uploads
        ExecutorService executorService = Executors.newFixedThreadPool(taskNum);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // Step 2: upload part
        // Create upload task for each unuploaded part
        for (int i = 0; i < partCount; i++) {
            long partNumber = i + 1; // Part number starts from 1
            long startPos = i * partSize;
            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;

            // Check if this part is already uploaded
            if (uploadedPartNumbers.contains(partNumber)) {
                System.out.printf("Part %d already exists, skipping upload\n", partNumber);
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
                            .body(BinaryData.fromStream(boundedInputStream, curPartSize))
                            .build());

                    UploadPartResult uploadPartResult = uploadPartFuture.join();

                    System.out.printf("Upload part %d completed, ETag: %s, status code: %d\n",
                            finalPartNumber, uploadPartResult.eTag(), uploadPartResult.statusCode());


                    // Add to allParts map
                    allPartsMap.put((long) finalPartNumber, Part.newBuilder()
                            .partNumber(finalPartNumber)
                            .eTag(uploadPartResult.eTag())
                            .build());

                } catch (Exception e) {
                    System.err.println("Exception occurred during upload of part " + finalPartNumber + ", UploadId: " + finalActualUploadId + ", Error: " + e.getMessage());
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

        CompleteMultipartUploadResult completeResult = null;
        try {
            completeResult = completeFuture.join();
        } catch (Exception e) {
            //If the exception is caused by ServiceException, detailed information can be obtained in this way.
            ServiceException se = ServiceException.asCause(e);
            if (se != null) {
                // Try to get object metadata to check if upload was successful
                CompletableFuture<HeadObjectResult> headFuture = client.headObjectAsync(
                        HeadObjectRequest.newBuilder()
                                .bucket(bucketName)
                                .key(objectKey)
                                .build());

                HeadObjectResult headResult = headFuture.join();

                // Compare file size with object size
                if (headResult.contentLength() == file.length()) {
                    System.out.printf("Upload appears to be successful despite NoSuchUpload error. Object exists with matching size.\n");

                } else {
                    System.out.printf("Upload failed: object exists but size doesn't match. Expected: %d, Actual: %d, UploadId: %s\n",
                            file.length(), headResult.contentLength(), actualUploadId);
                    throw e;
                }
            } else {
                // If it's not a NoSuchUpload error, rethrow the original exception
                System.err.println("Exception occurred during complete multipart upload, UploadId: " + actualUploadId + ", Error: " + e.getMessage());
                throw e;
            }
        }

        System.out.printf("File uploaded successfully: %s, status code: %d, request ID: %s\n",
                objectKey, completeResult.statusCode(), completeResult.requestId());

        // Return SimplifiedResumableUploadAsyncResult with uploadId and complete multipart upload result
        return new SimplifiedResumableUploadAsyncResult(actualUploadId, completeResult);

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
        String partSizeStr = cmd.getOptionValue("partSize", "1048576");
        String taskNumStr = cmd.getOptionValue("taskNum", "1");
        
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


class SimplifiedResumableUploadAsyncResult {
    private final String uploadId;
    private final CompleteMultipartUploadResult completeMultipartUploadResult;

    public SimplifiedResumableUploadAsyncResult(String uploadId, CompleteMultipartUploadResult completeMultipartUploadResult) {
        this.uploadId = uploadId;
        this.completeMultipartUploadResult = completeMultipartUploadResult;
    }

    public String getUploadId() {
        return uploadId;
    }

    public CompleteMultipartUploadResult getCompleteMultipartUploadResult() {
        return completeMultipartUploadResult;
    }
}