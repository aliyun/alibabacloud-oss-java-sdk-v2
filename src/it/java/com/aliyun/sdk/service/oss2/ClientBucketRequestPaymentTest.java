package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientBucketRequestPaymentTest extends TestBase {
    @Test
    public void testBucketRequestPayment() {
        OSSClient client = getDefaultClient();
        
        try {
            // First, put the request payment configuration
            PutBucketRequestPaymentRequest putRequest = PutBucketRequestPaymentRequest.newBuilder()
                    .bucket(bucketName)
                    .requestPaymentConfiguration(RequestPaymentConfiguration.newBuilder()
                            .payer("Requester")
                            .build())
                    .build();

            // Execute put request
            PutBucketRequestPaymentResult putResult = client.putBucketRequestPayment(putRequest);
            Assert.assertNotNull(putResult);
            Assert.assertTrue(putResult.statusCode() > 0);

            // Then, get the request payment configuration
            GetBucketRequestPaymentRequest getRequest = GetBucketRequestPaymentRequest.newBuilder()
                    .bucket(bucketName)
                    .build();

            // Execute get request
            GetBucketRequestPaymentResult getResult = client.getBucketRequestPayment(getRequest);
            Assert.assertNotNull(getResult);
            Assert.assertTrue(getResult.statusCode() > 0);

            // Verify that the request payment configuration is present
            if (getResult.requestPaymentConfiguration() != null) {
                Assert.assertNotNull(getResult.requestPaymentConfiguration().payer());
                Assert.assertEquals("Requester", getResult.requestPaymentConfiguration().payer());
            } else {
                Assert.fail("No request payment configuration found");
            }
        } catch (Exception e) {
            // Handle any unexpected exceptions
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}