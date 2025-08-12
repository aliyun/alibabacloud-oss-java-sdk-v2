package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class ClientBucketAclAsyncTest extends TestBase {

    @Test
    public void testBucketAclOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String testBucketName = genBucketName() + "-acl-test";

        // 1. Create a bucket first
        PutBucketResult putBucketResult = client.putBucketAsync(
                PutBucketRequest.newBuilder()
                        .bucket(testBucketName)
                        .createBucketConfiguration(CreateBucketConfiguration.newBuilder()
                                .storageClass("Standard")
                                .build())
                        .build()).get();
        Assert.assertNotNull(putBucketResult);
        Assert.assertEquals(200, putBucketResult.statusCode());
        waitForCacheExpiration(1);

        try {
            // 2. Get bucket ACL (default)
            GetBucketAclResult getAclResult = client.getBucketAclAsync(
                    GetBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .build()).get();
            Assert.assertNotNull(getAclResult);
            Assert.assertEquals(200, getAclResult.statusCode());
            Assert.assertNotNull(getAclResult.accessControlPolicy());
            Assert.assertNotNull(getAclResult.accessControlPolicy().owner());
            Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
            Assert.assertEquals("private", getAclResult.accessControlPolicy().accessControlList().grant());

            // 3. Put bucket ACL to public-read
            PutBucketAclResult putAclResult = client.putBucketAclAsync(
                    PutBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .acl("public-read")
                            .build()).get();
            Assert.assertNotNull(putAclResult);
            Assert.assertEquals(200, putAclResult.statusCode());

            // 4. Get bucket ACL (public-read)
            getAclResult = client.getBucketAclAsync(
                    GetBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .build()).get();
            Assert.assertNotNull(getAclResult);
            Assert.assertEquals(200, getAclResult.statusCode());
            Assert.assertNotNull(getAclResult.accessControlPolicy());
            Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
            Assert.assertEquals("public-read", getAclResult.accessControlPolicy().accessControlList().grant());

            // 5. Put bucket ACL to public-read-write
            putAclResult = client.putBucketAclAsync(
                    PutBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .acl("public-read-write")
                            .build()).get();
            Assert.assertNotNull(putAclResult);
            Assert.assertEquals(200, putAclResult.statusCode());

            // 6. Get bucket ACL (public-read-write)
            getAclResult = client.getBucketAclAsync(
                    GetBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .build()).get();
            Assert.assertNotNull(getAclResult);
            Assert.assertEquals(200, getAclResult.statusCode());
            Assert.assertNotNull(getAclResult.accessControlPolicy());
            Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
            Assert.assertEquals("public-read-write", getAclResult.accessControlPolicy().accessControlList().grant());

            // 7. Put bucket ACL to private
            putAclResult = client.putBucketAclAsync(
                    PutBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .acl("private")
                            .build()).get();
            Assert.assertNotNull(putAclResult);
            Assert.assertEquals(200, putAclResult.statusCode());

            // 8. Get bucket ACL (private)
            getAclResult = client.getBucketAclAsync(
                    GetBucketAclRequest.newBuilder()
                            .bucket(testBucketName)
                            .build()).get();
            Assert.assertNotNull(getAclResult);
            Assert.assertEquals(200, getAclResult.statusCode());
            Assert.assertNotNull(getAclResult.accessControlPolicy());
            Assert.assertNotNull(getAclResult.accessControlPolicy().accessControlList());
            Assert.assertEquals("private", getAclResult.accessControlPolicy().accessControlList().grant());

        } finally {
            // 9. Clean up - delete bucket
            DeleteBucketResult deleteResult = client.deleteBucketAsync(
                    DeleteBucketRequest.newBuilder()
                            .bucket(testBucketName)
                            .build()).get();
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
        }
    }
}
