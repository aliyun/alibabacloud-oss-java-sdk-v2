package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.CompletableFuture;

public class ClientBucketArchiveDirectReadAsyncTest extends TestBase {

    @Test
    public void testBucketArchiveDirectReadOperationsAsync() throws Exception {
        OSSAsyncClient asyncClient = getDefaultAsyncClient();

        // First, disable archive direct read (ensure clean state)
        try {
            asyncClient.putBucketArchiveDirectReadAsync(PutBucketArchiveDirectReadRequest.newBuilder()
                    .bucket(bucketName)
                    .archiveDirectReadConfiguration(ArchiveDirectReadConfiguration.newBuilder()
                            .enabled(false)
                            .build())
                    .build()).get();
        } catch (Exception e) {
            // If the operation fails, it might be because the configuration was already disabled
            // We can continue with the test
        }

        // Put archive direct read configuration - enable it (async)
        CompletableFuture<PutBucketArchiveDirectReadResult> putFuture = asyncClient.putBucketArchiveDirectReadAsync(PutBucketArchiveDirectReadRequest.newBuilder()
                .bucket(bucketName)
                .archiveDirectReadConfiguration(ArchiveDirectReadConfiguration.newBuilder()
                        .enabled(true)
                        .build())
                .build());
        
        PutBucketArchiveDirectReadResult putResult = putFuture.get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // Get archive direct read configuration (async)
        CompletableFuture<GetBucketArchiveDirectReadResult> getFuture = asyncClient.getBucketArchiveDirectReadAsync(GetBucketArchiveDirectReadRequest.newBuilder()
                .bucket(bucketName)
                .build());
        
        GetBucketArchiveDirectReadResult getResult = getFuture.get();
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());

        ArchiveDirectReadConfiguration config = getResult.archiveDirectReadConfiguration();
        Assert.assertNotNull(config);
        Assert.assertTrue(config.enabled());

        // Disable archive direct read (async)
        putFuture = asyncClient.putBucketArchiveDirectReadAsync(PutBucketArchiveDirectReadRequest.newBuilder()
                .bucket(bucketName)
                .archiveDirectReadConfiguration(ArchiveDirectReadConfiguration.newBuilder()
                        .enabled(false)
                        .build())
                .build());
        
        putResult = putFuture.get();
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // Verify the configuration is now disabled (async)
        getFuture = asyncClient.getBucketArchiveDirectReadAsync(GetBucketArchiveDirectReadRequest.newBuilder()
                .bucket(bucketName)
                .build());
        
        getResult = getFuture.get();
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());

        config = getResult.archiveDirectReadConfiguration();
        Assert.assertNotNull(config);
        Assert.assertFalse(config.enabled());
    }
}