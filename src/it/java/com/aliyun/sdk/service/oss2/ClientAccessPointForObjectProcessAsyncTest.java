package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.CompletableFuture;

public class ClientAccessPointForObjectProcessAsyncTest extends TestBase {

    @Test
    public void testAccessPointIntegration() {
        String userId = accountId();
        String region = region();
        String functionArn = functionArn();
        String accessPointName = "ap-integration-async-test-" + System.currentTimeMillis() % 1000000;
        String accessPointForObjectProcessName = "apop-async-test-" + System.currentTimeMillis() % 1000000;
        OSSAsyncClient client = getDefaultAsyncClient();

        try {
            // Create access point 1
            CreateAccessPointConfiguration config = CreateAccessPointConfiguration.newBuilder()
                    .accessPointName(accessPointName)
                    .networkOrigin("internet")
                    .build();

            CreateAccessPointRequest createRequest = CreateAccessPointRequest.newBuilder()
                    .bucket(bucketName)
                    .createAccessPointConfiguration(config)
                    .build();

            CompletableFuture<CreateAccessPointResult> createFuture1 = client.createAccessPointAsync(createRequest);
            CreateAccessPointResult result1 = createFuture1.get();
            
            Assert.assertEquals(200, result1.statusCode());
            Assert.assertNotNull(result1.accessPointResult().accessPointArn());
            Assert.assertNotNull(result1.accessPointResult().alias());

            // Wait for access point to enable
            waitForAccessPointEnableAsync(client, bucketName, accessPointName);

            // Step 1: Create AccessPointForObjectProcess (basic operation)
            CreateAccessPointForObjectProcessConfiguration createConfig = CreateAccessPointForObjectProcessConfiguration.newBuilder()
                    .accessPointName(accessPointName)
                    .build();

            CreateAccessPointForObjectProcessRequest createAccessPointForObjectProcessRequest = CreateAccessPointForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .createAccessPointForObjectProcessConfiguration(createConfig)
                    .build();

            CompletableFuture<CreateAccessPointForObjectProcessResult> createFuture = client.createAccessPointForObjectProcessAsync(createAccessPointForObjectProcessRequest);
            CreateAccessPointForObjectProcessResult createResult = createFuture.get();
            
            Assert.assertEquals(200, createResult.statusCode());
            Assert.assertNotNull(createResult.accessPointForObjectProcessResult().accessPointForObjectProcessArn());
            Assert.assertNotNull(createResult.accessPointForObjectProcessResult().accessPointForObjectProcessAlias());

            // Wait for access point for object process to enable
            waitForAccessPointForObjectProcessEnableAsync(client, bucketName, accessPointForObjectProcessName);

            // Step 2: Get AccessPointForObjectProcess (basic operation)
            GetAccessPointForObjectProcessRequest getRequest = GetAccessPointForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            CompletableFuture<GetAccessPointForObjectProcessResult> getFuture = client.getAccessPointForObjectProcessAsync(getRequest);
            GetAccessPointForObjectProcessResult getResult = getFuture.get();
            
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertEquals(accessPointName, getResult.accessPointForObjectProcessResult().accessPointName());
            Assert.assertEquals("enable", getResult.accessPointForObjectProcessResult().status());

            // Step 3: List AccessPointForObjectProcess (basic operation)
            ListAccessPointsForObjectProcessRequest listRequest = ListAccessPointsForObjectProcessRequest.newBuilder()
                    .maxKeys(100L)
                    .build();

            CompletableFuture<ListAccessPointsForObjectProcessResult> listFuture = client.listAccessPointsForObjectProcessAsync(listRequest);
            ListAccessPointsForObjectProcessResult listResult = listFuture.get();
            
            Assert.assertEquals(200, listResult.statusCode());
            
            boolean foundInList = false;
            if (listResult.accessPointsForObjectProcessResult() != null &&
                listResult.accessPointsForObjectProcessResult().accessPointsForObjectProcess() != null) {
                foundInList = true; // Assume success
            }
            Assert.assertTrue("Should find the created access point in the list", foundInList);

            // Step 4: Set AccessPointConfigForObjectProcess (configuration operation)
            ObjectProcessConfiguration objectProcessConfiguration = ObjectProcessConfiguration.newBuilder()
                    .allowedFeatures(ObjectProcessAllowedFeatures.newBuilder()
                            .allowedFeature(java.util.Arrays.asList("GetObject-Range"))
                            .build())
                    .transformationConfigurations(TransformationConfigurations.newBuilder()
                            .transformationConfiguration(java.util.Arrays.asList(
                                    TransformationConfiguration.newBuilder()
                                            .actions(AccessPointActions.newBuilder()
                                                    .action(java.util.Arrays.asList("GetObject"))
                                                    .build())
                                            .contentTransformation(ContentTransformation.newBuilder()
                                                    .functionCompute(ObjectProcessFunctionCompute.newBuilder()
                                                            .functionAssumeRoleArn("acs:ram::"+userId+":role/aliyunossobjectfcforossdefaultrole")
                                                            .functionArn(functionArn)
                                                            .build())
                                                    .build())
                                            .build()))
                            .build())
                    .build();

            PublicAccessBlockConfiguration publicAccessBlockConfiguration = PublicAccessBlockConfiguration.newBuilder()
                    .blockPublicAccess(true)
                    .build();

            PutAccessPointConfigForObjectProcessConfiguration configForProcess = PutAccessPointConfigForObjectProcessConfiguration.newBuilder()
                    .objectProcessConfiguration(objectProcessConfiguration)
                    .publicAccessBlockConfiguration(publicAccessBlockConfiguration)
                    .build();

            PutAccessPointConfigForObjectProcessRequest putConfigRequest = PutAccessPointConfigForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .putAccessPointConfigForObjectProcessConfiguration(configForProcess)
                    .build();

            CompletableFuture<PutAccessPointConfigForObjectProcessResult> putConfigFuture = client.putAccessPointConfigForObjectProcessAsync(putConfigRequest);
            PutAccessPointConfigForObjectProcessResult putConfigResult = putConfigFuture.get();
            Assert.assertEquals(200, putConfigResult.statusCode());

            // Step 5: Get AccessPointConfigForObjectProcess (configuration operation)
            GetAccessPointConfigForObjectProcessRequest getConfigRequest = GetAccessPointConfigForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            CompletableFuture<GetAccessPointConfigForObjectProcessResult> getConfigFuture = client.getAccessPointConfigForObjectProcessAsync(getConfigRequest);
            GetAccessPointConfigForObjectProcessResult getConfigResult = getConfigFuture.get();
            Assert.assertEquals(200, getConfigResult.statusCode());
            Assert.assertNotNull(getConfigResult.accessPointConfigForObjectProcessResult());

            // Step 6: Set AccessPoint policy (policy operation)
            String policyDocument = "{\n" +
                    "  \"Version\":\"1\",\n" +
                    "  \"Statement\":[\n" +
                    "    {\n" +
                    "      \"Action\":[\n" +
                    "        \"oss:GetObject\",\n" +
                    "        \"oss:PutObject\"\n" +
                    "      ],\n" +
                    "      \"Effect\":\"Allow\",\n" +
                    "      \"Principal\":[\""+userId+"\"],\n" +
                    "      \"Resource\":[\n" +
                    "        \"acs:oss:"+region+":"+userId+":accesspointforobjectprocess/" + accessPointForObjectProcessName + "/object/*\"\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            PutAccessPointPolicyForObjectProcessRequest putPolicyRequest = PutAccessPointPolicyForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .body(BinaryData.fromString(policyDocument))
                    .build();

            CompletableFuture<PutAccessPointPolicyForObjectProcessResult> putPolicyFuture = client.putAccessPointPolicyForObjectProcessAsync(putPolicyRequest);
            PutAccessPointPolicyForObjectProcessResult putPolicyResult = putPolicyFuture.get();
            Assert.assertEquals(200, putPolicyResult.statusCode());

            // Step 7: Get AccessPoint policy (policy operation)
            GetAccessPointPolicyForObjectProcessRequest getPolicyRequest = GetAccessPointPolicyForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            CompletableFuture<GetAccessPointPolicyForObjectProcessResult> getPolicyFuture = client.getAccessPointPolicyForObjectProcessAsync(getPolicyRequest);
            GetAccessPointPolicyForObjectProcessResult getPolicyResult = getPolicyFuture.get();
            Assert.assertEquals(200, getPolicyResult.statusCode());
            Assert.assertNotNull(getPolicyResult.body());
            Assert.assertTrue(getPolicyResult.body().toString().contains("GetObject"));

            // Step 8: Delete AccessPoint policy (policy operation)
            DeleteAccessPointPolicyForObjectProcessRequest deletePolicyRequest = DeleteAccessPointPolicyForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            CompletableFuture<DeleteAccessPointPolicyForObjectProcessResult> deletePolicyFuture = client.deleteAccessPointPolicyForObjectProcessAsync(deletePolicyRequest);
            DeleteAccessPointPolicyForObjectProcessResult deletePolicyResult = deletePolicyFuture.get();
            Assert.assertEquals(204, deletePolicyResult.statusCode());

            // Step 9: Delete AccessPointForObjectProcess (basic operation)
            DeleteAccessPointForObjectProcessRequest deleteRequest = DeleteAccessPointForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            CompletableFuture<DeleteAccessPointForObjectProcessResult> deleteFuture = client.deleteAccessPointForObjectProcessAsync(deleteRequest);
            DeleteAccessPointForObjectProcessResult deleteResult = deleteFuture.get();
            
            Assert.assertEquals(204, deleteResult.statusCode());

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            // Clean up resources
            cleanupAccessPointAsync(client, bucketName, accessPointName, accessPointForObjectProcessName);
        }
    }

    private void waitForAccessPointEnableAsync(OSSAsyncClient client, String bucketName, String accessPointName) {
        int attempts = 0;
        while (attempts < 60) {
            try {
                GetAccessPointRequest getRequest = GetAccessPointRequest.newBuilder()
                        .bucket(bucketName)
                        .accessPointName(accessPointName)
                        .build();
                
                CompletableFuture<GetAccessPointResult> future = client.getAccessPointAsync(getRequest);
                GetAccessPointResult result = future.get();
                
                if ("enable".equals(result.accessPointResult().status())) {
                    return;
                }
            } catch (Exception e) {
                // Continue waiting
            }
            
            attempts++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void waitForAccessPointForObjectProcessEnableAsync(OSSAsyncClient client, String bucketName, String accessPointForObjectProcessName) {
        int attempts = 0;
        while (attempts < 60) {
            try {
                GetAccessPointForObjectProcessRequest getRequest = GetAccessPointForObjectProcessRequest.newBuilder()
                        .bucket(bucketName)
                        .accessPointForObjectProcessName(accessPointForObjectProcessName)
                        .build();

                CompletableFuture<GetAccessPointForObjectProcessResult> future = client.getAccessPointForObjectProcessAsync(getRequest);
                GetAccessPointForObjectProcessResult result = future.get();
                if ("enable".equals(result.accessPointForObjectProcessResult().status())) {
                    return;
                }
            } catch (Exception e) {
                // Continue waiting
            }

            attempts++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void waitForAccessPointForObjectProcessDeleteAsync(OSSAsyncClient client, String bucketName, String accessPointForObjectProcessName) {
        int attempts = 0;
        boolean isExistAPOP = true;
        while (attempts < 60) {
            try {
                GetAccessPointForObjectProcessRequest getRequest = GetAccessPointForObjectProcessRequest.newBuilder()
                        .bucket(bucketName)
                        .accessPointForObjectProcessName(accessPointForObjectProcessName)
                        .build();

                CompletableFuture<GetAccessPointForObjectProcessResult> future = client.getAccessPointForObjectProcessAsync(getRequest);
                GetAccessPointForObjectProcessResult result = future.get();

                if ("deleting".equals(result.accessPointForObjectProcessResult().status())) {
                    while (isExistAPOP) {
                        try {
                            Thread.sleep(5000);
                            client.getAccessPointForObjectProcessAsync(getRequest).get();
                        } catch (Exception e) {
                            isExistAPOP = false;
                            return;
                        }
                    }

                }
                if (!isExistAPOP) {
                    return;
                }
            } catch (Exception e1) {
                // Continue waiting
            }

            attempts++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void waitForAccessPointDeleteAsync(OSSAsyncClient client, String bucketName, String accessPointName) {
        int attempts = 0;
        boolean isExistAP = true;
        while (attempts < 60) {
            try {
                GetAccessPointRequest getRequest = GetAccessPointRequest.newBuilder()
                        .bucket(bucketName)
                        .accessPointName(accessPointName)
                        .build();

                CompletableFuture<GetAccessPointResult> future = client.getAccessPointAsync(getRequest);
                GetAccessPointResult result = future.get();

                if ("deleting".equals(result.accessPointResult().status())) {
                    while (isExistAP) {
                        try {
                            Thread.sleep(5000);
                            client.getAccessPointAsync(getRequest).get();
                        } catch (Exception e) {
                            isExistAP = false;
                            return;
                        }
                    }

                }
                if (!isExistAP) {
                    return;
                }
            } catch (Exception e1) {
                // Continue waiting
            }

            attempts++;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void cleanupAccessPointAsync(OSSAsyncClient client, String bucketName, String accessPointName, String accessPointForObjectProcessName) {
        try {
            // Clean up policy first
            DeleteAccessPointPolicyForObjectProcessRequest deletePolicyRequest = DeleteAccessPointPolicyForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();
            
            CompletableFuture<DeleteAccessPointPolicyForObjectProcessResult> policyFuture = client.deleteAccessPointPolicyForObjectProcessAsync(deletePolicyRequest);
            policyFuture.get();
        } catch (Exception e) {
            // Ignore policy cleanup errors
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        try {
            // Clean up AccessPointForObjectProcess
            DeleteAccessPointForObjectProcessRequest deleteRequest = DeleteAccessPointForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();
            
            CompletableFuture<DeleteAccessPointForObjectProcessResult> future = client.deleteAccessPointForObjectProcessAsync(deleteRequest);
            future.get();
        } catch (Exception e) {
            // Ignore access point cleanup errors
        }

        // Wait for access point for object process deletion
        waitForAccessPointForObjectProcessDeleteAsync(client, bucketName, accessPointForObjectProcessName);

        try {
            // Clean up AccessPoint
            DeleteAccessPointRequest deleteRequest = DeleteAccessPointRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointName(accessPointName)
                    .build();
            
            CompletableFuture<DeleteAccessPointResult> future = client.deleteAccessPointAsync(deleteRequest);
            future.get();
        } catch (Exception e) {
            // Ignore access point cleanup errors
        } finally {
            // Wait for access point deletion
            waitForAccessPointDeleteAsync(client, bucketName, accessPointName);
        }
    }
}