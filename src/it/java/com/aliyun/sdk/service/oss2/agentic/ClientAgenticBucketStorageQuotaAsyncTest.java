package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.BucketStorageQuotaConfiguration;
import org.junit.Assert;
import org.junit.Test;

public class ClientAgenticBucketStorageQuotaAsyncTest extends TestBaseAgentic {

    @Test
    public void testAgenticBucketStorageQuotaOperationsAsync() throws Exception {
        OSSAsyncAgenticBucketClient client = newAgenticAsyncClient();
        String bucket = agenticBucketName;

        try {
            BucketStorageQuotaConfiguration config = BucketStorageQuotaConfiguration.newBuilder()
                    .storageQuota(10737418240L)
                    .mode("Warning")
                    .build();

            PutAgenticBucketStorageQuotaResult putResult = client.putAgenticBucketStorageQuotaAsync(
                    PutAgenticBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucket)
                            .bucketStorageQuotaConfiguration(config)
                            .build()).get();
            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            GetAgenticBucketStorageQuotaResult getResult = client.getAgenticBucketStorageQuotaAsync(
                    GetAgenticBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucket)
                            .build()).get();
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.requestId());
            Assert.assertNotNull(getResult.bucketStorageQuotaConfiguration());
            Assert.assertEquals("Warning", getResult.bucketStorageQuotaConfiguration().mode());
            Assert.assertEquals(Long.valueOf(10737418240L), getResult.bucketStorageQuotaConfiguration().storageQuota());

            DeleteAgenticBucketStorageQuotaResult deleteResult = client.deleteAgenticBucketStorageQuotaAsync(
                    DeleteAgenticBucketStorageQuotaRequest.newBuilder()
                            .bucket(bucket)
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
