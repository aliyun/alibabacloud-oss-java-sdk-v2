package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.ExecutionException;

public class ClientBucketDataRedundancyTransitionAsyncTest extends TestBase {

    @Test
    public void testBucketDataRedundancyTransitionOperations() throws ExecutionException, InterruptedException {
        OSSAsyncClient client = getDefaultAsyncClient();
        String bucketName = this.bucketName;

        // Test create bucket data redundancy transition
        CreateBucketDataRedundancyTransitionRequest createRequest = CreateBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket(bucketName)
                .targetRedundancyType("ZRS")
                .build();

        CreateBucketDataRedundancyTransitionResult createResult = client.createBucketDataRedundancyTransitionAsync(createRequest).get();
        Assert.assertNotNull(createResult);
        Assert.assertEquals(200, createResult.statusCode());
        Assert.assertNotNull(createResult.bucketDataRedundancyTransition());
        Assert.assertNotNull(createResult.bucketDataRedundancyTransition().taskId());

        // Test get bucket data redundancy transition
        GetBucketDataRedundancyTransitionRequest getRequest = GetBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket(bucketName)
                .redundancyTransitionTaskid(createResult.bucketDataRedundancyTransition().taskId())
                .build();

        GetBucketDataRedundancyTransitionResult getResult = client.getBucketDataRedundancyTransitionAsync(getRequest).get();
        Assert.assertNotNull(getResult);
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.bucketDataRedundancyTransition());
        Assert.assertEquals(bucketName, getResult.bucketDataRedundancyTransition().bucket());
        Assert.assertEquals(createResult.bucketDataRedundancyTransition().taskId(), getResult.bucketDataRedundancyTransition().taskId());

        // Test list bucket data redundancy transition
        ListBucketDataRedundancyTransitionRequest listRequest = ListBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket(bucketName)
                .build();

        ListBucketDataRedundancyTransitionResult listResult = client.listBucketDataRedundancyTransitionAsync(listRequest).get();
        Assert.assertNotNull(listResult);
        Assert.assertEquals(200, listResult.statusCode());
        Assert.assertNotNull(listResult.listBucketDataRedundancyTransition());

        // Test list user data redundancy transition
        ListUserDataRedundancyTransitionRequest userListRequest = ListUserDataRedundancyTransitionRequest.newBuilder()
                .build();

        ListUserDataRedundancyTransitionResult userListResult = client.listUserDataRedundancyTransitionAsync(userListRequest).get();
        Assert.assertNotNull(userListResult);
        Assert.assertEquals(200, userListResult.statusCode());
        Assert.assertNotNull(userListResult.listBucketDataRedundancyTransition());

        // Test delete bucket data redundancy transition
        DeleteBucketDataRedundancyTransitionRequest deleteRequest = DeleteBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket(bucketName)
                .redundancyTransitionTaskid(createResult.bucketDataRedundancyTransition().taskId())
                .build();

        DeleteBucketDataRedundancyTransitionResult deleteResult = client.deleteBucketDataRedundancyTransitionAsync(deleteRequest).get();
        Assert.assertNotNull(deleteResult);
        Assert.assertEquals(204, deleteResult.statusCode());

    }
}