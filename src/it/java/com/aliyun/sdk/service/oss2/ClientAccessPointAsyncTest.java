package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.ExecutionException;

public class ClientAccessPointAsyncTest extends TestBase {

    @Test
    public void testAccessPointAsync() throws ExecutionException, InterruptedException {
        String accessPointName1 = "ap-test-oss-" + System.currentTimeMillis() % 1000000;
        String accessPointName2 = "ap-test-oss-" + (System.currentTimeMillis() + 1) % 1000000;
        String userId = accountId();
        String region = region();
        String policy = String.format("{\"Version\":\"1\",\"Statement\":[{\"Action\":[\"oss:PutObject\",\"oss:GetObject\"],\"Effect\":\"Deny\",\"Principal\":[\"%s\"],\"Resource\":[\"acs:oss:%s:%s:accesspoint/%s\",\"acs:oss:%s:%s:accesspoint/%s/object/*\"]}]}", 
                userId, region, userId, accessPointName1, region, userId, accessPointName1);

        OSSAsyncClient client = getDefaultAsyncClient();

        try {
            // Create access point 1
            CreateAccessPointConfiguration config1 = CreateAccessPointConfiguration.newBuilder()
                    .accessPointName(accessPointName1)
                    .networkOrigin("internet")
                    .build();

            CreateAccessPointRequest createRequest1 = CreateAccessPointRequest.newBuilder()
                    .bucket(bucketName)
                    .createAccessPointConfiguration(config1)
                    .build();

            CreateAccessPointResult result1 = client.createAccessPointAsync(createRequest1).get();
            Assert.assertEquals(200, result1.statusCode());
            Assert.assertNotNull(result1.accessPointResult().accessPointArn());
            Assert.assertNotNull(result1.accessPointResult().alias());

            // Create access point 2
            CreateAccessPointConfiguration config2 = CreateAccessPointConfiguration.newBuilder()
                    .accessPointName(accessPointName2)
                    .networkOrigin("internet")
                    .build();

            CreateAccessPointRequest createRequest2 = CreateAccessPointRequest.newBuilder()
                    .bucket(bucketName)
                    .createAccessPointConfiguration(config2)
                    .build();

            CreateAccessPointResult result2 = client.createAccessPointAsync(createRequest2).get();
            Assert.assertEquals(200, result2.statusCode());
            Assert.assertNotNull(result2.accessPointResult().accessPointArn());
            Assert.assertNotNull(result2.accessPointResult().alias());

            // Waiting for access point creation status to be enabled
            int num = 1;
            GetAccessPointResult getApResult1 = null;
            while (true) {
                try {
                    getApResult1 = client.getAccessPointAsync(GetAccessPointRequest.newBuilder()
                            .bucket(bucketName)
                            .accessPointName(accessPointName1)
                            .build()).get();
                } catch (Exception e) {

                }

                if (num > 180 || getApResult1 != null && "enable".equals(getApResult1.accessPointResult().status())) {
                    break;
                }

                num++;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            int num2 = 1;
            GetAccessPointResult getApResult2 = null;
            while (true) {
                try {
                    getApResult2 = client.getAccessPointAsync(GetAccessPointRequest.newBuilder()
                            .bucket(bucketName)
                            .accessPointName(accessPointName2)
                            .build()).get();
                } catch (Exception e) {

                }

                if (num2 > 180 || getApResult2 != null && "enable".equals(getApResult2.accessPointResult().status())) {
                    break;
                }

                num2++;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            // Get access point
            GetAccessPointResult getResult = client.getAccessPointAsync(GetAccessPointRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointName(accessPointName1)
                    .build()).get();
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertEquals(accessPointName1, getResult.accessPointResult().accessPointName());
            Assert.assertEquals("internet", getResult.accessPointResult().networkOrigin());

            // List access points - first page
            ListAccessPointsRequest listRequest1 = ListAccessPointsRequest.newBuilder()
                    .maxKeys(1L)
                    .continuationToken("")
                    .build();

            ListAccessPointsResult listResult1 = client.listAccessPointsAsync(listRequest1).get();
            Assert.assertEquals(200, listResult1.statusCode());
            Assert.assertEquals(Integer.valueOf(1), listResult1.accessPointsResult().maxKeys());
            if (listResult1.accessPointsResult().accessPoints().accessPoint() != null &&
                !listResult1.accessPointsResult().accessPoints().accessPoint().isEmpty()) {
                Assert.assertEquals("internet", listResult1.accessPointsResult().accessPoints().accessPoint().get(0).networkOrigin());
            }

            // List access points - second page if there's a next token
            ListAccessPointsResult listResult2 = null;
            if (listResult1.accessPointsResult().nextContinuationToken() != null && 
                !listResult1.accessPointsResult().nextContinuationToken().isEmpty()) {
                
                ListAccessPointsRequest listRequest2 = ListAccessPointsRequest.newBuilder()
                        .maxKeys(1L)
                        .continuationToken(listResult1.accessPointsResult().nextContinuationToken())
                        .build();
                        
                listResult2 = client.listAccessPointsAsync(listRequest2).get();
                Assert.assertEquals(200, listResult2.statusCode());
                Assert.assertEquals(Integer.valueOf(1), listResult2.accessPointsResult().maxKeys());
                if (listResult2.accessPointsResult().accessPoints().accessPoint() != null &&
                    !listResult2.accessPointsResult().accessPoints().accessPoint().isEmpty()) {
                    Assert.assertEquals("internet", listResult2.accessPointsResult().accessPoints().accessPoint().get(0).networkOrigin());
                }
            }

            // Create access point policy
            PutAccessPointPolicyRequest policyRequest = PutAccessPointPolicyRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointName(accessPointName1)
                    .body(BinaryData.fromString(policy))
                    .build();

            PutAccessPointPolicyResult policyResult = client.putAccessPointPolicyAsync(policyRequest).get();
            Assert.assertEquals(200, policyResult.statusCode());

            // Get access point policy
            GetAccessPointPolicyRequest getPolicyRequest = GetAccessPointPolicyRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointName(accessPointName1)
                    .build();

            GetAccessPointPolicyResult getPolicyResult = client.getAccessPointPolicyAsync(getPolicyRequest).get();
            Assert.assertEquals(200, getPolicyResult.statusCode());
            Assert.assertEquals(policy, getPolicyResult.body().toString());

            // Delete access point policy
            DeleteAccessPointPolicyRequest deletePolicyRequest = DeleteAccessPointPolicyRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointName(accessPointName1)
                    .build();

            DeleteAccessPointPolicyResult deletePolicyResult = client.deleteAccessPointPolicyAsync(deletePolicyRequest).get();
            Assert.assertEquals(204, deletePolicyResult.statusCode());

            // Delete access point 1
            DeleteAccessPointRequest deleteRequest1 = DeleteAccessPointRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointName(accessPointName1)
                    .build();

            DeleteAccessPointResult deleteResult1 = client.deleteAccessPointAsync(deleteRequest1).get();
            Assert.assertEquals(204, deleteResult1.statusCode());

            // Delete access point 2
            DeleteAccessPointRequest deleteRequest2 = DeleteAccessPointRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointName(accessPointName2)
                    .build();

            DeleteAccessPointResult deleteResult2 = client.deleteAccessPointAsync(deleteRequest2).get();
            Assert.assertEquals(204, deleteResult2.statusCode());

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            // Clean up: Delete all access points under the bucket that start with 'ap-test-oss-'
            int num = 1;
            boolean shouldContinue = true;
            while (shouldContinue) {
                ListAccessPointsRequest cleanupRequest = ListAccessPointsRequest.newBuilder()
                        .maxKeys(100L)
                        .continuationToken("")
                        .build();

                ListAccessPointsResult listResult = client.listAccessPointsAsync(cleanupRequest).get();

                if (listResult.accessPointsResult().accessPoints().accessPoint() == null ||
                    listResult.accessPointsResult().accessPoints().accessPoint().isEmpty()) {
                    System.out.println("All access points have been deleted");
                    shouldContinue = false;
                } else if (num > 180) {
                    shouldContinue = false;
                } else {
                    boolean isExistTestAP = false;
                    for (AccessPoint ap : listResult.accessPointsResult().accessPoints().accessPoint()) {
                        if (ap.accessPointName().startsWith("ap-test-oss-")) {
                            if ("enable".equals(ap.status())) {
                                System.out.println("ap name: " + ap.accessPointName() + ", status: " + ap.status());
                                
                                DeleteAccessPointRequest delRequest = DeleteAccessPointRequest.newBuilder()
                                        .bucket(bucketName)
                                        .accessPointName(ap.accessPointName())
                                        .build();
                                        
                                DeleteAccessPointResult delResult = client.deleteAccessPointAsync(delRequest).get();
                                Assert.assertTrue(delResult.statusCode() == 200 || delResult.statusCode() == 204);
                                System.out.println("delete_access_point: " + ap.accessPointName());
                                isExistTestAP = true;
                            }
                            if ("deleting".equals(ap.status())) {
                                isExistTestAP = true;
                                System.out.println("ap name is deleting: " + ap.accessPointName() + ", status: " + ap.status());
                            }
                        }
                    }
                    if (!isExistTestAP) {
                        shouldContinue = false;
                        System.out.println("All test access points have been deleted");
                    }

                    num++;
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        shouldContinue = false;
                    }
                }
            }
        }
    }
}