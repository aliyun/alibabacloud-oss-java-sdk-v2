package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientBucketAccessMonitorTest extends TestBase {

    @Test
    public void testBucketAccessMonitorOperations() {
        OSSClient client = getDefaultClient();
        String testBucketName = genBucketName() + "-access-monitor-test";

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

        try {
            // 2. Put bucket access monitor configuration - enabled with allowCopy=true
            AccessMonitorConfiguration accessMonitorConfiguration1 = AccessMonitorConfiguration.newBuilder()
                    .status("Enabled")
                    .allowCopy(true)
                    .build();

            PutBucketAccessMonitorResult putResult1 = client.putBucketAccessMonitor(
                    PutBucketAccessMonitorRequest.newBuilder()
                            .bucket(testBucketName)
                            .accessMonitorConfiguration(accessMonitorConfiguration1)
                            .build());
            Assert.assertNotNull(putResult1);
            Assert.assertEquals(200, putResult1.statusCode());

            waitForCacheExpiration(1);

            // 3. Get bucket access monitor configuration
            GetBucketAccessMonitorResult getResult1 = client.getBucketAccessMonitor(
                    GetBucketAccessMonitorRequest.newBuilder()
                            .bucket(testBucketName)
                            .build());
            Assert.assertNotNull(getResult1);
            Assert.assertEquals(200, getResult1.statusCode());
            Assert.assertNotNull(getResult1.accessMonitorConfiguration());
            Assert.assertEquals("Enabled", getResult1.accessMonitorConfiguration().status());
            Assert.assertEquals(true, getResult1.accessMonitorConfiguration().allowCopy());

            // 4. Put bucket access monitor configuration - disabled with allowCopy=false
            AccessMonitorConfiguration accessMonitorConfiguration2 = AccessMonitorConfiguration.newBuilder()
                    .status("Disabled")
                    .build();

            PutBucketAccessMonitorResult putResult2 = client.putBucketAccessMonitor(
                    PutBucketAccessMonitorRequest.newBuilder()
                            .bucket(testBucketName)
                            .accessMonitorConfiguration(accessMonitorConfiguration2)
                            .build());
            Assert.assertNotNull(putResult2);
            Assert.assertEquals(200, putResult2.statusCode());

            waitForCacheExpiration(1);

            // 5. Get updated bucket access monitor configuration
            GetBucketAccessMonitorResult getResult2 = client.getBucketAccessMonitor(
                    GetBucketAccessMonitorRequest.newBuilder()
                            .bucket(testBucketName)
                            .build());
            Assert.assertNotNull(getResult2);
            Assert.assertEquals(200, getResult2.statusCode());
            Assert.assertNotNull(getResult2.accessMonitorConfiguration());
            Assert.assertEquals("Disabled", getResult2.accessMonitorConfiguration().status());

            // 6. Put bucket access monitor configuration - enabled with allowCopy=false
            AccessMonitorConfiguration accessMonitorConfiguration3 = AccessMonitorConfiguration.newBuilder()
                    .status("Enabled")
                    .allowCopy(false)
                    .build();

            PutBucketAccessMonitorResult putResult3 = client.putBucketAccessMonitor(
                    PutBucketAccessMonitorRequest.newBuilder()
                            .bucket(testBucketName)
                            .accessMonitorConfiguration(accessMonitorConfiguration3)
                            .build());
            Assert.assertNotNull(putResult3);
            Assert.assertEquals(200, putResult3.statusCode());

            waitForCacheExpiration(1);

            // 7. Get updated bucket access monitor configuration
            GetBucketAccessMonitorResult getResult3 = client.getBucketAccessMonitor(
                    GetBucketAccessMonitorRequest.newBuilder()
                            .bucket(testBucketName)
                            .build());
            Assert.assertNotNull(getResult3);
            Assert.assertEquals(200, getResult3.statusCode());
            Assert.assertNotNull(getResult3.accessMonitorConfiguration());
            Assert.assertEquals("Enabled", getResult3.accessMonitorConfiguration().status());
            Assert.assertEquals(false, getResult3.accessMonitorConfiguration().allowCopy());

        } finally {
            // 8. Clean up - delete bucket
            DeleteBucketResult deleteResult = client.deleteBucket(
                    DeleteBucketRequest.newBuilder()
                            .bucket(testBucketName)
                            .build());
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
        }
    }
}