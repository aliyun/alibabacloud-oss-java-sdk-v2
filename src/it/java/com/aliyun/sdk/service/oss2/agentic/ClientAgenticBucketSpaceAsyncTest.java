package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.ExecutionException;

public class ClientAgenticBucketSpaceAsyncTest extends TestBaseAgentic {

    private static String getFullAgenticBucketName() {
        return buildFullName(agenticBucketName, AGENTIC_BUCKET_SUFFIX);
    }

    /**
     * BucketSpace lifecycle via standard OSSAsyncClient (reused interfaces):
     * Pass the full BucketSpace name {prefix}-{uid}-{region}-bs-apsr directly.
     * Host: {prefix}-{uid}-{region}-bs-apsr.{endpoint}
     * Header: x-oss-agentic-bucket: {agenticBucketName}
     */
    @Test
    public void testBucketSpaceLifecycleAsync() throws ExecutionException, InterruptedException {
        String bsPrefix = genBucketSpaceName();
        String bsFullName = buildFullName(bsPrefix, BUCKET_SPACE_SUFFIX);
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
            // 4. DeleteBucket (reused) - cleanup, a non-empty bucket space cannot be deleted
            cleanBucketSpaceObjects(bsFullName);
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
        String bsPrefix = genBucketSpaceName();
        String expectedFullName = buildFullName(bsPrefix, BUCKET_SPACE_SUFFIX);
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
            cleanBucketSpaceObjects(expectedFullName);
            try {
                asyncClient.deleteBucketAsync(DeleteBucketRequest.newBuilder()
                        .bucket(expectedFullName).build()).get();
            } catch (Exception ignore) {
            }
        }
    }

    /**
     * BucketSpace object operations via path-style OSSAsyncClient (async).
     * BucketSpaceClient is sync-only, so this test uses OSSAsyncClient with
     * usePathStyle(true) and the full BucketSpace name for object operations,
     * mirroring Python test_bucket_space_object_operations_path_style.
     */
    @Test
    public void testBucketSpaceObjectOperationsPathStyleAsync() throws ExecutionException, InterruptedException {
        String bsPrefix = genBucketSpaceName();
        String bsFullName = buildFullName(bsPrefix, BUCKET_SPACE_SUFFIX);
        String bsFullAgenticBucket = getFullAgenticBucketName();

        // Create BucketSpace via standard OSSAsyncClient first
        OSSAsyncClient stdAsyncClient = getDefaultAsyncClient();
        PutBucketResult putResult = stdAsyncClient.putBucketAsync(PutBucketRequest.newBuilder()
                .bucket(bsFullName)
                .agenticBucket(bsFullAgenticBucket)
                .build()).get();
        Assert.assertEquals(200, putResult.statusCode());
        waitForCacheExpiration(1);

        try {
            // Use path-style async client for object operations
            OSSAsyncClient pathAsyncClient = OSSAsyncClient.newBuilder()
                    .region(region())
                    .endpoint(endpoint())
                    .credentialsProvider(new StaticCredentialsProvider(accessKeyId(), accessKeySecret()))
                    .usePathStyle(true)
                    .build();
            String key = "path-style-test.txt";

            // put_object via path-style
            try {
                PutObjectResult putObjResult = pathAsyncClient.putObjectAsync(PutObjectRequest.newBuilder()
                        .bucket(bsFullName)
                        .key(key)
                        .body(BinaryData.fromString("hello path style"))
                        .build()).get();
                Assert.assertEquals(200, putObjResult.statusCode());
            } catch (Exception e) {
                if (!isSecondLevelDomainForbidden(e)) {
                    throw e;
                }
                System.out.println("put_object path-style not supported: " + e.getMessage());
            }

            // get_object via path-style
            try {
                GetObjectResult getObjResult = pathAsyncClient.getObjectAsync(GetObjectRequest.newBuilder()
                        .bucket(bsFullName)
                        .key(key)
                        .build()).get();
                Assert.assertEquals(200, getObjResult.statusCode());
            } catch (Exception e) {
                if (!isSecondLevelDomainForbidden(e)) {
                    throw e;
                }
                System.out.println("get_object path-style not supported: " + e.getMessage());
            }

            // delete_object via path-style
            try {
                DeleteObjectResult delObjResult = pathAsyncClient.deleteObjectAsync(DeleteObjectRequest.newBuilder()
                        .bucket(bsFullName)
                        .key(key)
                        .build()).get();
                Assert.assertEquals(204, delObjResult.statusCode());
            } catch (Exception e) {
                if (!isSecondLevelDomainForbidden(e)) {
                    throw e;
                }
                System.out.println("delete_object path-style not supported: " + e.getMessage());
            }

            // get_bucket_acl via path-style
            try {
                GetBucketAclResult aclResult = pathAsyncClient.getBucketAclAsync(GetBucketAclRequest.newBuilder()
                        .bucket(bsFullName)
                        .build()).get();
                Assert.assertEquals(200, aclResult.statusCode());
                Assert.assertNotNull(aclResult.accessControlPolicy());
            } catch (Exception e) {
                if (!isSecondLevelDomainForbidden(e)) {
                    throw e;
                }
                System.out.println("get_bucket_acl path-style not supported: " + e.getMessage());
            }

        } finally {
            // Cleanup BucketSpace: drain first, the object deleted above may still be there when
            // the path-style delete was refused by the endpoint.
            cleanBucketSpaceObjects(bsFullName);
            try {
                stdAsyncClient.deleteBucketAsync(DeleteBucketRequest.newBuilder()
                        .bucket(bsFullName).build()).get();
            } catch (Exception ignore) {
            }
        }
    }
}
