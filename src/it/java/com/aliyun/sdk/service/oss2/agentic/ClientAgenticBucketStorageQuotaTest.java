package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.BucketStorageQuotaConfiguration;
import org.junit.Assert;
import org.junit.Test;

public class ClientAgenticBucketStorageQuotaTest extends TestBaseAgentic {

    @Test
    public void testAgenticBucketStorageQuotaOperations() {
        OSSAgenticBucketClient client = agenticClient;
        String bucket = agenticBucketName;

        try {
            BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                    .storageQuota(10737418240L)
                    .mode("Strict")
                    .build();

            PutAgenticBucketStorageQuotaResult putResult = client.putAgenticBucketStorageQuota(
                    PutAgenticBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucket)
                            .bucketStorageQuotaConfiguration(config)
                            .build());
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            GetAgenticBucketStorageQuotaResult getResult = client.getAgenticBucketStorageQuota(
                    GetAgenticBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucket)
                            .build());
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.requestId());
            Assert.assertNotNull(getResult.bucketStorageQuotaConfiguration());
            Assert.assertEquals("Strict", getResult.bucketStorageQuotaConfiguration().mode());
            Assert.assertEquals(Long.valueOf(10737418240L), getResult.bucketStorageQuotaConfiguration().storageQuota());

            DeleteAgenticBucketStorageQuotaResult deleteResult = client.deleteAgenticBucketStorageQuota(
                    DeleteAgenticBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucket)
                            .build());
            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());
            Assert.assertNotNull(deleteResult.requestId());
        } catch (Exception e) {
            ServiceException serr = findCause(e, ServiceException.class);
            if (serr != null && "BucketStorageQuotaDisabled".equals(serr.errorCode())) {
                System.out.println("Bucket storage quota is disabled for this account, skipping test.");
                return;
            }
            Assert.fail("Agentic bucket storage quota operations failed: " + e.getMessage());
        }
    }
}
