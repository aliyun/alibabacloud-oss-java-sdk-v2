package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientBucketStorageQuotaTest extends TestBase {

    @Test
    public void testBucketStorageQuotaOperations() {
        OSSClient client = getDefaultClient();

        try {
            BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                    .storageQuota(10737418240L)
                    .mode("Strict")
                    .build();

            PutBucketStorageQuotaResult putResult = client.putBucketStorageQuota(
                    PutBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucketName)
                            .bucketStorageQuotaConfiguration(config)
                            .build());
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            GetBucketStorageQuotaResult getResult = client.getBucketStorageQuota(
                    GetBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucketName)
                            .build());
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.requestId());
            Assert.assertNotNull(getResult.bucketStorageQuotaConfiguration());
            Assert.assertEquals("Strict", getResult.bucketStorageQuotaConfiguration().mode());
            Assert.assertEquals(Long.valueOf(10737418240L), getResult.bucketStorageQuotaConfiguration().storageQuota());
            Assert.assertNotNull(getResult.bucketStorageQuotaConfiguration().currentUsage());
            Assert.assertTrue(getResult.bucketStorageQuotaConfiguration().currentUsage() >= 0);

            // GetBucketStat returns the same usage data as CurrentUsage in GetBucketStorageQuota
            GetBucketStatResult statResult = client.getBucketStat(
                    GetBucketStatRequest.newBuilder()
                            .bucket(bucketName)
                            .build());
            Assert.assertNotNull(statResult);
            Assert.assertEquals(200, statResult.statusCode());
            Assert.assertNotNull(statResult.requestId());
            Assert.assertNotNull(statResult.bucketStat());
            Assert.assertNotNull(statResult.bucketStat().storage());
            Assert.assertTrue(statResult.bucketStat().storage() >= 0);
            Assert.assertNotNull(statResult.bucketStat().objectCount());
            Assert.assertTrue(statResult.bucketStat().objectCount() >= 0);

            DeleteBucketStorageQuotaResult deleteResult = client.deleteBucketStorageQuota(
                    DeleteBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucketName)
                            .build());
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
            Assert.assertNotNull(deleteResult.requestId());
        } catch (Exception e) {
            ServiceException serr = ServiceException.asCause(e);
            if (serr != null && "BucketStorageQuotaDisabled".equals(serr.errorCode())) {
                System.out.println("Bucket storage quota is disabled for this account, skipping test.");
                return;
            }
            Assert.fail("Bucket storage quota operations failed: " + e.getMessage());
        }
    }
}
