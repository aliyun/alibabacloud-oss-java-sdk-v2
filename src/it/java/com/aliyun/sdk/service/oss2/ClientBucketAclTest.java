package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientBucketAclTest extends TestBase {

    @Test
    public void testBucketAclOperations() {
        OSSClient client = getDefaultClient();
        String testBucketName = genBucketName() + "-acl-test";

        // 1. Create a bucket first
        PutBucketResult putBucketResult = client.putBucket(
                PutBucketRequest.newBuilder()
                        .bucket(testBucketName)
                        .createBucketConfiguration(CreateBucketConfiguration.newBuilder()
                                .storageClass("Standard")
                                .build())
                        .build());
        Assert.assertNotNull(putBucketResult);
        Assert.assertEquals(200, putBucketResult.statusCode());
        waitForCacheExpiration(1);

        client.putBucketPublicAccessBlock(
                PutBucketPublicAccessBlockRequest.newBuilder()
                        .bucket(testBucketName)
                        .bucketPublicAccessBlockConfiguration(BucketPublicAccessBlockConfiguration.newBuilder()
                                .blockPublicAccess(false)
                                .build())
                        .build()
        );

        try {
            // 2. Get bucket ACL (default)
            GetBucketAclResult getAclResult = client.getBucketAcl(
                    GetBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .build());
            Assert.assertNotNull(getAclResult);
            Assert.assertEquals(200, getAclResult.statusCode());
            Assert.assertNotNull(getAclResult.accessControlPolicy());
            Assert.assertNotNull(getAclResult.accessControlPolicy().owner());
            Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
            Assert.assertEquals("private", getAclResult.accessControlPolicy().accessControlList().grant());

            // 3. Put bucket ACL to public-read
            PutBucketAclResult putAclResult = client.putBucketAcl(
                    PutBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .acl("public-read")
                            .build());
            Assert.assertNotNull(putAclResult);
            Assert.assertEquals(200, putAclResult.statusCode());

            // 4. Get bucket ACL (public-read)
            getAclResult = client.getBucketAcl(
                    GetBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .build());
            Assert.assertNotNull(getAclResult);
            Assert.assertEquals(200, getAclResult.statusCode());
            Assert.assertNotNull(getAclResult.accessControlPolicy());
            Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
            Assert.assertEquals("public-read", getAclResult.accessControlPolicy().accessControlList().grant());

            // 5. Put bucket ACL to public-read-write
            putAclResult = client.putBucketAcl(
                    PutBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .acl("public-read-write")
                            .build());
            Assert.assertNotNull(putAclResult);
            Assert.assertEquals(200, putAclResult.statusCode());

            // 6. Get bucket ACL (public-read-write)
            getAclResult = client.getBucketAcl(
                    GetBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .build());
            Assert.assertNotNull(getAclResult);
            Assert.assertEquals(200, getAclResult.statusCode());
            Assert.assertNotNull(getAclResult.accessControlPolicy());
            Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
            Assert.assertEquals("public-read-write", getAclResult.accessControlPolicy().accessControlList().grant());

            // 7. Put bucket ACL to private
            putAclResult = client.putBucketAcl(
                    PutBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .acl("private")
                            .build());
            Assert.assertNotNull(putAclResult);
            Assert.assertEquals(200, putAclResult.statusCode());

            // 8. Get bucket ACL (private)
            getAclResult = client.getBucketAcl(
                    GetBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .build());
            Assert.assertNotNull(getAclResult);
            Assert.assertEquals(200, getAclResult.statusCode());
            Assert.assertNotNull(getAclResult.accessControlPolicy());
            Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
            Assert.assertEquals("private", getAclResult.accessControlPolicy().accessControlList().grant());

        } finally {
            // 9. Clean up - delete bucket
            DeleteBucketResult deleteResult = client.deleteBucket(
                    DeleteBucketRequest.newBuilder()
                            .bucket(testBucketName)
                            .build());
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
        }
    }
}
