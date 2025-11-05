package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.exceptions.ServiceException;
import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;

public class ClientBucketResourceGroupTest extends TestBase {

    @Test
    public void testBucketResourceGroupOperations() {
        OSSClient client = getDefaultClient();
        
        try {
            // First get the current resource group configuration
            GetBucketResourceGroupResult getResult = client.getBucketResourceGroup(
                GetBucketResourceGroupRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
            
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            
            // Test put resource group (using a dummy resource group ID)
            // Note: In a real test environment, you would need a valid resource group ID
            try {
                BucketResourceGroupConfiguration config = BucketResourceGroupConfiguration.newBuilder()
                    .resourceGroupId("rg-acfmytestid") // Dummy resource group ID
                    .build();
                
                PutBucketResourceGroupResult putResult = client.putBucketResourceGroup(
                    PutBucketResourceGroupRequest.newBuilder()
                        .bucket(bucketName)
                        .bucketResourceGroupConfiguration(config)
                        .build());
                
                Assert.assertNotNull(putResult);
                // Note: This may fail with a 403 or 400 error in real environment if the resource group ID is invalid
                // But we're testing that the API call is correctly formed
            } catch (Exception e) {
                ServiceException serr = findCause(e, ServiceException.class);
                Assert.assertEquals(400, serr.statusCode());
                Assert.assertEquals("ResourceGroupIdPreCheckError", serr.errorCode());
                Assert.assertEquals("The resource group id precheck error", serr.errorMessage());
            }
            
            // Test get resource group again
            getResult = client.getBucketResourceGroup(
                GetBucketResourceGroupRequest.newBuilder()
                    .bucket(bucketName)
                    .build());
            
            Assert.assertNotNull(getResult);
            Assert.assertEquals(200, getResult.statusCode());
            
        } catch (Exception e) {
            // Handle any unexpected exceptions
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}