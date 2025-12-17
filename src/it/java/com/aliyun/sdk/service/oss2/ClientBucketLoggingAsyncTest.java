package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.ExecutionException;

public class ClientBucketLoggingAsyncTest extends TestBase {

    @Test
    public void testBucketLoggingOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        LoggingEnabled loggingEnabled = LoggingEnabled.newBuilder()
                .targetBucket(bucketName)
                .targetPrefix("log-")
                .loggingRole("")
                .build();

        BucketLoggingStatus bucketLoggingStatus = BucketLoggingStatus.newBuilder()
                .loggingEnabled(loggingEnabled)
                .build();

        // putBucketLogging
        PutBucketLoggingResult putResult = client.putBucketLoggingAsync(PutBucketLoggingRequest.newBuilder()
                .bucket(bucketName)
                .bucketLoggingStatus(bucketLoggingStatus)
                .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(5);

        // getBucketLogging
        GetBucketLoggingResult getResult = client.getBucketLoggingAsync(GetBucketLoggingRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.bucketLoggingStatus());
        Assert.assertNotNull(getResult.bucketLoggingStatus().loggingEnabled());
        Assert.assertEquals(bucketName, getResult.bucketLoggingStatus().loggingEnabled().targetBucket());
        Assert.assertEquals("log-", getResult.bucketLoggingStatus().loggingEnabled().targetPrefix());
        Assert.assertEquals("", getResult.bucketLoggingStatus().loggingEnabled().loggingRole());

        // deleteBucketLogging
        DeleteBucketLoggingResult deleteResult = client.deleteBucketLoggingAsync(DeleteBucketLoggingRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());

        GetBucketLoggingResult getResultAfterDelete = client.getBucketLoggingAsync(GetBucketLoggingRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(getResultAfterDelete);
        Assert.assertEquals(200, getResultAfterDelete.statusCode());
        Assert.assertNull(getResultAfterDelete.bucketLoggingStatus().loggingEnabled().targetBucket());
    }
}