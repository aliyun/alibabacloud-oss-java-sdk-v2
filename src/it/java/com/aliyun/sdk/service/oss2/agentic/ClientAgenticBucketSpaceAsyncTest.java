package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ClientAgenticBucketSpaceAsyncTest extends TestBaseAgentic {

    private static String genBucketSpacePrefix() {
        return "oss-sdk-test-java-bs-" + new Random().nextInt(5000);
    }

    private static String getFullAgenticBucketName() {
        return agenticBucketName + "-" + accountId() + "-" + region() + "-ab-apsr";
    }

    /**
     * BucketSpace lifecycle via standard OSSAsyncClient (reused interfaces):
     * Pass the full BucketSpace name {prefix}-{uid}-{region}-bs-apsr directly.
     * Host: {prefix}-{uid}-{region}-bs-apsr.{endpoint}
     * Header: x-oss-agentic-bucket: {agenticBucketName}
     */
    @Test
    public void testBucketSpaceLifecycleAsync() throws ExecutionException, InterruptedException {
        String bsPrefix = genBucketSpacePrefix();
        String bsFullName = bsPrefix + "-" + accountId() + "-" + region() + "-bs-apsr";
        String bsFullAgenticBucket = getFullAgenticBucketName();

        OSSAsyncClient asyncClient = getDefaultAsyncClient();

        try {
            // 1. PutBucket (reused) - standard OSSAsyncClient with full BucketSpace name
            PutBucketResult putResult = asyncClient.putBucketAsync(PutBucketRequest.newBuilder()
                    .bucket(bsFullName)
                    .agenticBucket(bsFullAgenticBucket)
                    .build()).get();
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());
            waitForCacheExpiration(1);

            // 2. GetBucketInfo (reused) - verify BucketResourceType and AgenticBucketName
            GetBucketInfoResult infoResult = asyncClient.getBucketInfoAsync(
                    GetBucketInfoRequest.newBuilder().bucket(bsFullName).build()).get();
            Assert.assertNotNull(infoResult);
            Assert.assertEquals(200, infoResult.statusCode());
            Assert.assertEquals("AgenticBucketSpace", infoResult.bucketInfo().bucketResourceType());
            Assert.assertNotNull(infoResult.bucketInfo().agenticBucketName());

            // 3. ListBucketSpaces - verify the created BucketSpace via OSSAsyncAgenticBucketClient
            OSSAsyncAgenticBucketClient asyncAgenticClient = newAgenticAsyncClient();
            ListBucketSpacesResult listResult = asyncAgenticClient.listBucketSpacesAsync(
                    ListBucketSpacesRequest.newBuilder().bucket(agenticBucketName).prefix(bsPrefix).build()).get();
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            boolean found = false;
            if (listResult.bucketSpaces() != null) {
                for (BucketSpaceSummary bs : listResult.bucketSpaces()) {
                    if (bsFullName.equals(bs.name())) {
                        found = true;
                        break;
                    }
                }
            }
            Assert.assertTrue("Created BucketSpace should appear in list", found);

        } finally {
            // 4. DeleteBucket (reused) - cleanup
            try {
                asyncClient.deleteBucketAsync(DeleteBucketRequest.newBuilder()
                        .bucket(bsFullName).build()).get();
            } catch (Exception ignore) {
            }
        }
    }

    /**
     * BucketSpace lifecycle via OSSAsyncClient (mirrors BucketSpaceClient test):
     * BucketSpaceClient is sync-only (wraps OSSClient). For async, we use
     * OSSAsyncClient with the full BucketSpace name to achieve the same result.
     */
    @Test
    public void testBucketSpaceClientIndependentAsync() throws ExecutionException, InterruptedException {
        String bsPrefix = genBucketSpacePrefix();
        String expectedFullName = bsPrefix + "-" + accountId() + "-" + region() + "-bs-apsr";
        String bsFullAgenticBucket = getFullAgenticBucketName();

        OSSAsyncClient asyncClient = getDefaultAsyncClient();

        try {
            // 1. PutBucket (reused) - full BucketSpace name (BucketSpaceClient auto-expands in sync)
            PutBucketResult putResult = asyncClient.putBucketAsync(PutBucketRequest.newBuilder()
                    .bucket(expectedFullName)
                    .agenticBucket(bsFullAgenticBucket)
                    .build()).get();
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());
            waitForCacheExpiration(1);

            // 2. GetBucketInfo (reused) - verify via OSSAsyncClient
            GetBucketInfoResult infoResult = asyncClient.getBucketInfoAsync(
                    GetBucketInfoRequest.newBuilder().bucket(expectedFullName).build()).get();
            Assert.assertNotNull(infoResult);
            Assert.assertEquals(200, infoResult.statusCode());
            Assert.assertEquals("AgenticBucketSpace", infoResult.bucketInfo().bucketResourceType());
            Assert.assertNotNull(infoResult.bucketInfo().agenticBucketName());

            // 3. ListBucketSpaces - cross-verify the created BucketSpace via async agentic client
            OSSAsyncAgenticBucketClient asyncAgenticClient = newAgenticAsyncClient();
            ListBucketSpacesResult listResult = asyncAgenticClient.listBucketSpacesAsync(
                    ListBucketSpacesRequest.newBuilder().bucket(agenticBucketName).prefix(bsPrefix).build()).get();
            Assert.assertNotNull(listResult);
            Assert.assertEquals(200, listResult.statusCode());
            boolean found = false;
            if (listResult.bucketSpaces() != null) {
                for (BucketSpaceSummary bs : listResult.bucketSpaces()) {
                    if (expectedFullName.equals(bs.name())) {
                        found = true;
                        break;
                    }
                }
            }
            Assert.assertTrue("BucketSpaceClient-created BucketSpace should appear in list", found);

        } finally {
            // 4. DeleteBucket (reused) - cleanup via OSSAsyncClient
            try {
                asyncClient.deleteBucketAsync(DeleteBucketRequest.newBuilder()
                        .bucket(expectedFullName).build()).get();
            } catch (Exception ignore) {
            }
        }
    }
}
