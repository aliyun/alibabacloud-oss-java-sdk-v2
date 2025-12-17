package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.ExecutionException;

public class ClientBucketPublicAccessBlockAsyncTest extends TestBase {

    @Test
    public void testBucketPublicAccessBlock_default() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // putBucketPublicAccessBlock
        BucketPublicAccessBlockConfiguration config = BucketPublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutBucketPublicAccessBlockResult putResult = client.putBucketPublicAccessBlockAsync(PutBucketPublicAccessBlockRequest.newBuilder()
                .bucket(bucketName)
                .bucketPublicAccessBlockConfiguration(config)
                .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(5);

        // getBucketPublicAccessBlock
        GetBucketPublicAccessBlockResult getResult = client.getBucketPublicAccessBlockAsync(GetBucketPublicAccessBlockRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(getResult);
        Assert.assertNotNull(getResult.publicAccessBlockConfiguration());
        Assert.assertEquals(true, getResult.publicAccessBlockConfiguration().blockPublicAccess());

        // deleteBucketPublicAccessBlock
        DeleteBucketPublicAccessBlockResult deleteResult = client.deleteBucketPublicAccessBlockAsync(DeleteBucketPublicAccessBlockRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

    @Test
    public void testBucketPublicAccessBlock_disable() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // putBucketPublicAccessBlock with false
        BucketPublicAccessBlockConfiguration config = BucketPublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(false)
                .build();

        PutBucketPublicAccessBlockResult putResult = client.putBucketPublicAccessBlockAsync(PutBucketPublicAccessBlockRequest.newBuilder()
                .bucket(bucketName)
                .bucketPublicAccessBlockConfiguration(config)
                .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(5);

        // getBucketPublicAccessBlock
        GetBucketPublicAccessBlockResult getResult = client.getBucketPublicAccessBlockAsync(GetBucketPublicAccessBlockRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(getResult);
        Assert.assertNotNull(getResult.publicAccessBlockConfiguration());
        Assert.assertEquals(false, getResult.publicAccessBlockConfiguration().blockPublicAccess());

        // deleteBucketPublicAccessBlock
        DeleteBucketPublicAccessBlockResult deleteResult = client.deleteBucketPublicAccessBlockAsync(DeleteBucketPublicAccessBlockRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());
    }

}