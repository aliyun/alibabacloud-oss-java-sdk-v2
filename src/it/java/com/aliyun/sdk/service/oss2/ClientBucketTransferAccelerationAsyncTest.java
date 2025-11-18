package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.ExecutionException;

public class ClientBucketTransferAccelerationAsyncTest extends TestBase {

    @Test
    public void testBucketTransferAcceleration_default() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();

        // putBucketTransferAcceleration - disable first
        TransferAccelerationConfiguration disableConfig = TransferAccelerationConfiguration.newBuilder()
                .enabled(false)
                .build();

        PutBucketTransferAccelerationResult putResult = client.putBucketTransferAccelerationAsync(PutBucketTransferAccelerationRequest.newBuilder()
                .bucket(bucketName)
                .transferAccelerationConfiguration(disableConfig)
                .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(5);

        // getBucketTransferAcceleration
        GetBucketTransferAccelerationResult getResult = client.getBucketTransferAccelerationAsync(GetBucketTransferAccelerationRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.transferAccelerationConfiguration());
        Assert.assertEquals(Boolean.FALSE, getResult.transferAccelerationConfiguration().enabled());

        // putBucketTransferAcceleration - enable
        TransferAccelerationConfiguration enableConfig = TransferAccelerationConfiguration.newBuilder()
                .enabled(true)
                .build();

        putResult = client.putBucketTransferAccelerationAsync(PutBucketTransferAccelerationRequest.newBuilder()
                .bucket(bucketName)
                .transferAccelerationConfiguration(enableConfig)
                .build()).get();

        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        waitForCacheExpiration(1);

        // getBucketTransferAcceleration
        getResult = client.getBucketTransferAccelerationAsync(GetBucketTransferAccelerationRequest.newBuilder()
                .bucket(bucketName)
                .build()).get();

        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.transferAccelerationConfiguration());
        Assert.assertEquals(Boolean.TRUE, getResult.transferAccelerationConfiguration().enabled());
    }

    @Test
    public void testBucketTransferAcceleration_fail() {
        // Add failure test cases if needed
    }

}