package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;

public class ClientAgenticBucketSpaceTest extends TestBaseAgentic {

    private static String getFullAgenticBucketName() {
        return buildFullName(agenticBucketName, AGENTIC_BUCKET_SUFFIX);
    }

    /**
     * BucketSpace lifecycle via standard OSSClient (reused interfaces):
     * Pass the full BucketSpace name {prefix}-{uid}-{region}-bs-apsr directly.
     * Host: {prefix}-{uid}-{region}-bs-apsr.{endpoint}
     * Header: x-oss-agentic-bucket: {agenticBucketName}
     */
    @Test
    public void testBucketSpaceLifecycle() {
        String bsPrefix = genBucketSpaceName();
        String bsFullName = buildFullName(bsPrefix, BUCKET_SPACE_SUFFIX);
        String bsFullAgenticBucket = getFullAgenticBucketName();

        try {
            // 1. PutBucket (reused) - standard OSSClient with full BucketSpace name
            //    Host: {prefix}-{uid}-{region}-bs-apsr.oss-{region}-internal.aliyuncs.com
            //    Header: x-oss-agentic-bucket: {agenticBucketName}
            PutBucketResult putResult = getDefaultClient().putBucket(PutBucketRequest.newBuilder()
                    .bucket(bsFullName)
                    .agenticBucket(bsFullAgenticBucket)
                    .build());
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());
            waitForCacheExpiration(1);

            // 2. GetBucketInfo (reused) - verify BucketResourceType and AgenticBucketName
            GetBucketInfoResult infoResult = getDefaultClient().getBucketInfo(
                    GetBucketInfoRequest.newBuilder().bucket(bsFullName).build());
            Assert.assertNotNull(infoResult);
            Assert.assertEquals(200, infoResult.statusCode());
            Assert.assertEquals("AgenticBucketSpace", infoResult.bucketInfo().bucketResourceType());
            Assert.assertNotNull(infoResult.bucketInfo().agenticBucketName());

            // 3. ListBucketSpaces - verify the created BucketSpace via OSSAgenticBucketClient
            ListBucketSpacesResult listResult = agenticClient.listBucketSpaces(
                    ListBucketSpacesRequest.newBuilder().bucket(agenticBucketName).prefix(bsPrefix).build());
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
                getDefaultClient().deleteBucket(DeleteBucketRequest.newBuilder()
                        .bucket(bsFullName).build());
            } catch (Exception ignore) {
            }
        }
    }

    /**
     * BucketSpace lifecycle via BucketSpaceClient:
     * Pass the short prefix only; BucketSpaceClient auto-expands to
     * {prefix}-{uid}-{region}-bs-apsr.
     * <p>
     * Note: BucketSpaceClient returns a standard OSSClient, so listBucketSpaces
     * (an OSSAgenticBucketClient-only operation) is NOT available through it.
     */
    @Test
    public void testBucketSpaceClientIndependent() throws Exception {
        String bsPrefix = genBucketSpaceName();
        String expectedFullName = buildFullName(bsPrefix, BUCKET_SPACE_SUFFIX);
        String bsFullAgenticBucket = getFullAgenticBucketName();

        ClientConfiguration config = ClientConfiguration.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .accountId(accountId())
                .credentialsProvider(new StaticCredentialsProvider(accessKeyId(), accessKeySecret()))
                .build();

        try (OSSClient bsClient = BucketSpaceClient.create(config)) {
            try {
                // 1. PutBucket (reused) - BucketSpaceClient auto-expands prefix to full name
                PutBucketResult putResult = bsClient.putBucket(PutBucketRequest.newBuilder()
                        .bucket(bsPrefix)
                        .agenticBucket(bsFullAgenticBucket)
                        .build());
                Assert.assertNotNull(putResult);
                Assert.assertEquals(200, putResult.statusCode());
                waitForCacheExpiration(1);

                // 2. GetBucketInfo (reused) - verify via BucketSpaceClient
                GetBucketInfoResult infoResult = bsClient.getBucketInfo(
                        GetBucketInfoRequest.newBuilder().bucket(bsPrefix).build());
                Assert.assertNotNull(infoResult);
                Assert.assertEquals(200, infoResult.statusCode());
                Assert.assertEquals("AgenticBucketSpace", infoResult.bucketInfo().bucketResourceType());
                Assert.assertNotNull(infoResult.bucketInfo().agenticBucketName());

                // 3. ListBucketSpaces - cross-verify the created BucketSpace via agenticClient
                ListBucketSpacesResult listResult = agenticClient.listBucketSpaces(
                        ListBucketSpacesRequest.newBuilder().bucket(agenticBucketName).prefix(bsPrefix).build());
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
                // 4. DeleteBucket (reused) - cleanup via BucketSpaceClient
                cleanBucketSpaceObjects(expectedFullName);
                try {
                    bsClient.deleteBucket(DeleteBucketRequest.newBuilder()
                            .bucket(bsPrefix).build());
                } catch (Exception ignore) {
                }
            }
        }
    }

    /**
     * BucketSpace object operations via path-style BucketSpaceClient.
     * Verifies put_object / get_object / delete_object / get_bucket_acl work
     * correctly with usePathStyle(true), mirroring Python
     * test_bucket_space_object_operations_path_style.
     */
    @Test
    public void testBucketSpaceObjectOperationsPathStyle() throws Exception {
        String bsPrefix = genBucketSpaceName();
        String bsFullName = buildFullName(bsPrefix, BUCKET_SPACE_SUFFIX);
        String bsFullAgenticBucket = getFullAgenticBucketName();

        // Create BucketSpace via standard BucketSpaceClient first
        ClientConfiguration stdConfig = ClientConfiguration.newBuilder()
                .region(region())
                .endpoint(endpoint())
                .accountId(accountId())
                .credentialsProvider(new StaticCredentialsProvider(accessKeyId(), accessKeySecret()))
                .build();
        try (OSSClient stdClient = BucketSpaceClient.create(stdConfig)) {
            PutBucketResult putResult = stdClient.putBucket(PutBucketRequest.newBuilder()
                    .bucket(bsPrefix)
                    .agenticBucket(bsFullAgenticBucket)
                    .build());
            Assert.assertEquals(200, putResult.statusCode());
            waitForCacheExpiration(1);

            try {
                // Use path-style client for object operations
                ClientConfiguration pathConfig = ClientConfiguration.newBuilder()
                        .region(region())
                        .endpoint(endpoint())
                        .accountId(accountId())
                        .credentialsProvider(new StaticCredentialsProvider(accessKeyId(), accessKeySecret()))
                        .usePathStyle(true)
                        .build();
                try (OSSClient pathClient = BucketSpaceClient.create(pathConfig)) {
                    String key = "path-style-test.txt";

                    // put_object via path-style
                    try {
                        PutObjectResult putObjResult = pathClient.putObject(PutObjectRequest.newBuilder()
                                .bucket(bsPrefix)
                                .key(key)
                                .body(BinaryData.fromString("hello path style"))
                                .build());
                        Assert.assertEquals(200, putObjResult.statusCode());
                    } catch (Exception e) {
                        if (!isSecondLevelDomainForbidden(e)) {
                            throw e;
                        }
                        System.out.println("put_object path-style not supported: " + e.getMessage());
                    }

                    // get_object via path-style
                    try {
                        GetObjectResult getObjResult = pathClient.getObject(GetObjectRequest.newBuilder()
                                .bucket(bsPrefix)
                                .key(key)
                                .build());
                        Assert.assertEquals(200, getObjResult.statusCode());
                    } catch (Exception e) {
                        if (!isSecondLevelDomainForbidden(e)) {
                            throw e;
                        }
                        System.out.println("get_object path-style not supported: " + e.getMessage());
                    }

                    // delete_object via path-style
                    try {
                        DeleteObjectResult delObjResult = pathClient.deleteObject(DeleteObjectRequest.newBuilder()
                                .bucket(bsPrefix)
                                .key(key)
                                .build());
                        Assert.assertEquals(204, delObjResult.statusCode());
                    } catch (Exception e) {
                        if (!isSecondLevelDomainForbidden(e)) {
                            throw e;
                        }
                        System.out.println("delete_object path-style not supported: " + e.getMessage());
                    }

                    // get_bucket_acl via path-style
                    try {
                        GetBucketAclResult aclResult = pathClient.getBucketAcl(GetBucketAclRequest.newBuilder()
                                .bucket(bsPrefix)
                                .build());
                        Assert.assertEquals(200, aclResult.statusCode());
                        Assert.assertNotNull(aclResult.accessControlPolicy());
                    } catch (Exception e) {
                        if (!isSecondLevelDomainForbidden(e)) {
                            throw e;
                        }
                        System.out.println("get_bucket_acl path-style not supported: " + e.getMessage());
                    }
                }
            } finally {
                // Cleanup BucketSpace: drain first, the object deleted above may still be there when
                // the path-style delete was refused by the endpoint.
                cleanBucketSpaceObjects(bsFullName);
                try {
                    stdClient.deleteBucket(DeleteBucketRequest.newBuilder()
                            .bucket(bsPrefix).build());
                } catch (Exception ignore) {
                }
            }
        }
    }
}
