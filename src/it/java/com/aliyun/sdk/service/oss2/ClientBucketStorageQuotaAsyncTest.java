package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientBucketStorageQuotaAsyncTest extends TestBase {

    @Test
    public void testBucketStorageQuotaOperationsAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();

        try {
            BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                    .storageQuota(10737418240L)
                    .mode("Warning")
                    .build();

            PutBucketStorageQuotaResult putResult = client.putBucketStorageQuotaAsync(
                    PutBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucketName)
                            .bucketStorageQuotaConfiguration(config)
                            .build()).get();
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            GetBucketStorageQuotaResult getResult = client.getBucketStorageQuotaAsync(
                    GetBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucketName)
                            .build()).get();
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.requestId());
            Assert.assertNotNull(getResult.bucketStorageQuotaConfiguration());
            Assert.assertEquals("Warning", getResult.bucketStorageQuotaConfiguration().mode());
            Assert.assertEquals(Long.valueOf(10737418240L), getResult.bucketStorageQuotaConfiguration().storageQuota());
            Assert.assertNotNull(getResult.bucketStorageQuotaConfiguration().currentUsage());
            Assert.assertTrue(getResult.bucketStorageQuotaConfiguration().currentUsage() >= 0);

            // GetBucketStat returns the same usage data as CurrentUsage in GetBucketStorageQuota
            GetBucketStatResult statResult = client.getBucketStatAsync(
                    GetBucketStatRequest.newBuilder()
                            .bucket(bucketName)
                            .build()).get();
            Assert.assertNotNull(statResult);
            Assert.assertEquals(200, statResult.statusCode());
            Assert.assertNotNull(statResult.requestId());
            Assert.assertNotNull(statResult.bucketStat());
            Assert.assertNotNull(statResult.bucketStat().storage());
            Assert.assertTrue(statResult.bucketStat().storage() >= 0);
            Assert.assertNotNull(statResult.bucketStat().objectCount());
            Assert.assertTrue(statResult.bucketStat().objectCount() >= 0);

            DeleteBucketStorageQuotaResult deleteResult = client.deleteBucketStorageQuotaAsync(
                    DeleteBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucketName)
                            .build()).get();
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
            Assert.assertNotNull(deleteResult.requestId());
        } catch (Exception e) {
            ServiceException serr = ServiceException.asCause(e);
            if (serr != null && "BucketStorageQuotaDisabled".equals(serr.errorCode())) {
                System.out.println("Bucket storage quota is disabled for this account, skipping test.");
                return;
            }
            throw e;
        }
    }
}
