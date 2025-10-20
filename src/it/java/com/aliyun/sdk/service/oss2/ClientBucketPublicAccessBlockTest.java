package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientBucketPublicAccessBlockTest extends TestBase {

    @Test
    public void testBucketPublicAccessBlock_default() throws Exception {

        try (OSSClient client = getOssClient()) {

            // putBucketPublicAccessBlock
            PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                    .blockPublicAccess(true)
                    .build();

            PutBucketPublicAccessBlockResult putResult = client.putBucketPublicAccessBlock(PutBucketPublicAccessBlockRequest.newBuilder()
                    .bucket(bucketName)
                    .publicAccessBlockConfiguration(config)
                    .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(5);

            // getBucketPublicAccessBlock
            GetBucketPublicAccessBlockResult getResult = client.getBucketPublicAccessBlock(GetBucketPublicAccessBlockRequest.newBuilder()
                    .bucket(bucketName)
                    .build());

            Assert.assertNotNull(getResult);
            Assert.assertNotNull(getResult.publicAccessBlockConfiguration());
            Assert.assertEquals(true, getResult.publicAccessBlockConfiguration().blockPublicAccess());

            // deleteBucketPublicAccessBlock
            DeleteBucketPublicAccessBlockResult deleteResult = client.deleteBucketPublicAccessBlock(DeleteBucketPublicAccessBlockRequest.newBuilder()
                    .bucket(bucketName)
                    .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
        }
    }

    @Test
    public void testBucketPublicAccessBlock_disable() throws Exception {

        try (OSSClient client = getOssClient()) {

            // putBucketPublicAccessBlock with false
            PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                    .blockPublicAccess(false)
                    .build();

            PutBucketPublicAccessBlockResult putResult = client.putBucketPublicAccessBlock(PutBucketPublicAccessBlockRequest.newBuilder()
                    .bucket(bucketName)
                    .publicAccessBlockConfiguration(config)
                    .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(5);

            // getBucketPublicAccessBlock
            GetBucketPublicAccessBlockResult getResult = client.getBucketPublicAccessBlock(GetBucketPublicAccessBlockRequest.newBuilder()
                    .bucket(bucketName)
                    .build());

            Assert.assertNotNull(getResult);
            Assert.assertNotNull(getResult.publicAccessBlockConfiguration());
            Assert.assertEquals(false, getResult.publicAccessBlockConfiguration().blockPublicAccess());

            // deleteBucketPublicAccessBlock
            DeleteBucketPublicAccessBlockResult deleteResult = client.deleteBucketPublicAccessBlock(DeleteBucketPublicAccessBlockRequest.newBuilder()
                    .bucket(bucketName)
                    .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
        }
    }

}