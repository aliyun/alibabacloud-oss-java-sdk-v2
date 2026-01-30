package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientBucketArchiveDirectReadTest extends TestBase {

    @Test
    public void testBucketArchiveDirectReadOperations() {
        OSSClient client = getDefaultClient();

        // First, disable archive direct read (ensure clean state)
        try {
            client.putBucketArchiveDirectRead(PutBucketArchiveDirectReadRequest.newBuilder()
                    .bucket(bucketName)
                    .archiveDirectReadConfiguration(ArchiveDirectReadConfiguration.newBuilder()
                            .enabled(false)
                            .build())
                    .build());
        } catch (Exception e) {
            // If the operation fails, it might be because the configuration was already disabled
            // We can continue with the test
        }

        // Put archive direct read configuration - enable it
        PutBucketArchiveDirectReadResult putResult = client.putBucketArchiveDirectRead(PutBucketArchiveDirectReadRequest.newBuilder()
                .bucket(bucketName)
                .archiveDirectReadConfiguration(ArchiveDirectReadConfiguration.newBuilder()
                        .enabled(true)
                        .build())
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // Get archive direct read configuration
        GetBucketArchiveDirectReadResult getResult = client.getBucketArchiveDirectRead(GetBucketArchiveDirectReadRequest.newBuilder()
                .bucket(bucketName)
                .build());
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());

        ArchiveDirectReadConfiguration config = getResult.archiveDirectReadConfiguration();
        Assert.assertNotNull(config);
        Assert.assertTrue(config.enabled());

        // Disable archive direct read
        putResult = client.putBucketArchiveDirectRead(PutBucketArchiveDirectReadRequest.newBuilder()
                .bucket(bucketName)
                .archiveDirectReadConfiguration(ArchiveDirectReadConfiguration.newBuilder()
                        .enabled(false)
                        .build())
                .build());
        Assert.assertNotNull(putResult);
        Assert.assertEquals(200, putResult.statusCode());

        // Verify the configuration is now disabled
        getResult = client.getBucketArchiveDirectRead(GetBucketArchiveDirectReadRequest.newBuilder()
                .bucket(bucketName)
                .build());
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());

        config = getResult.archiveDirectReadConfiguration();
        Assert.assertNotNull(config);
        Assert.assertFalse(config.enabled());
    }
}