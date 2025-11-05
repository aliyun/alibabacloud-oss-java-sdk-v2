package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientBucketLoggingTest extends TestBase {

    @Test
    public void testBucketLoggingOperations() throws Exception {
        try (OSSClient client = getOssClient()) {

            LoggingEnabled loggingEnabled = LoggingEnabled.newBuilder()
                    .targetBucket(bucketName)
                    .targetPrefix("log-")
                    .loggingRole("")
                    .build();

            BucketLoggingStatus bucketLoggingStatus = BucketLoggingStatus.newBuilder()
                    .loggingEnabled(loggingEnabled)
                    .build();

            // putBucketLogging
            PutBucketLoggingResult putResult = client.putBucketLogging(PutBucketLoggingRequest.newBuilder()
                    .bucket(bucketName)
                    .bucketLoggingStatus(bucketLoggingStatus)
                    .build());

            Assert.assertNotNull(putResult);
            Assert.assertEquals(200, putResult.statusCode());

            waitForCacheExpiration(5);

            // getBucketLogging
            GetBucketLoggingResult getResult = client.getBucketLogging(GetBucketLoggingRequest.newBuilder()
                    .bucket(bucketName)
                    .build());

            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.bucketLoggingStatus());
            Assert.assertNotNull(getResult.bucketLoggingStatus().loggingEnabled());
            Assert.assertEquals(bucketName, getResult.bucketLoggingStatus().loggingEnabled().targetBucket());
            Assert.assertEquals("log-", getResult.bucketLoggingStatus().loggingEnabled().targetPrefix());
            Assert.assertEquals("", getResult.bucketLoggingStatus().loggingEnabled().loggingRole());

            // deleteBucketLogging
            DeleteBucketLoggingResult deleteResult = client.deleteBucketLogging(DeleteBucketLoggingRequest.newBuilder()
                    .bucket(bucketName)
                    .build());

            Assert.assertNotNull(deleteResult);
            Assert.assertEquals(204, deleteResult.statusCode());

            GetBucketLoggingResult getResultAfterDelete = client.getBucketLogging(GetBucketLoggingRequest.newBuilder()
                    .bucket(bucketName)
                    .build());

            Assert.assertNotNull(getResultAfterDelete);
            Assert.assertEquals(200, getResultAfterDelete.statusCode());
            Assert.assertNull(getResultAfterDelete.bucketLoggingStatus().loggingEnabled().targetBucket());
        }
    }
}