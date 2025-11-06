package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientBucketDataRedundancyTransitionTest extends TestBase {

    @Test
    public void testBucketDataRedundancyTransitionOperations() {
        OSSClient client = getDefaultClient();
        String bucketName = this.bucketName;

        // Test create bucket data redundancy transition
        CreateBucketDataRedundancyTransitionRequest createRequest = CreateBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket(bucketName)
                .targetRedundancyType("ZRS")
                .build();

        CreateBucketDataRedundancyTransitionResult createResult = client.createBucketDataRedundancyTransition(createRequest);
        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull(createResult.bucketDataRedundancyTransition());
        Assert.assertNotNull(createResult.bucketDataRedundancyTransition().taskId());

        // Test get bucket data redundancy transition
        GetBucketDataRedundancyTransitionRequest getRequest = GetBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket(bucketName)
                .redundancyTransitionTaskid(createResult.bucketDataRedundancyTransition().taskId())
                .build();

        GetBucketDataRedundancyTransitionResult getResult = client.getBucketDataRedundancyTransition(getRequest);
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.bucketDataRedundancyTransition());
        Assert.assertEquals(bucketName, getResult.bucketDataRedundancyTransition().bucket());
        Assert.assertEquals(createResult.bucketDataRedundancyTransition().taskId(), getResult.bucketDataRedundancyTransition().taskId());

        // Test list bucket data redundancy transition
        ListBucketDataRedundancyTransitionRequest listRequest = ListBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket(bucketName)
                .build();

        ListBucketDataRedundancyTransitionResult listResult = client.listBucketDataRedundancyTransition(listRequest);
        Assert.assertNotNull(listResult);
        Assert.assertEquals(200, listResult.statusCode());
        Assert.assertNotNull(listResult.listBucketDataRedundancyTransition());

        // Test list user data redundancy transition
        ListUserDataRedundancyTransitionRequest userListRequest = ListUserDataRedundancyTransitionRequest.newBuilder()
                .maxKeys(11L)
                .build();

        ListUserDataRedundancyTransitionResult userListResult = client.listUserDataRedundancyTransition(userListRequest);
        Assert.assertNotNull(userListResult);
        Assert.assertEquals(200, userListResult.statusCode());
        Assert.assertNotNull(userListResult.listBucketDataRedundancyTransition());

        // Test delete bucket data redundancy transition
        DeleteBucketDataRedundancyTransitionRequest deleteRequest = DeleteBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket(bucketName)
                .redundancyTransitionTaskid(createResult.bucketDataRedundancyTransition().taskId())
                .build();

        DeleteBucketDataRedundancyTransitionResult deleteResult = client.deleteBucketDataRedundancyTransition(deleteRequest);
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());

    }
}