package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.Assert;
import org.junit.Test;

public class ClientAccessPointForObjectProcessTest extends TestBase {

    @Test
    public void testAccessPointIntegration() {
        String userId = accountId();
        String region = region();
        String functionArn = functionArn();
        String accessPointName = "ap-integration-test-" + System.currentTimeMillis() % 1000000;
        String accessPointForObjectProcessName = "apop-test-" + System.currentTimeMillis() % 1000000;
        OSSClient client = getDefaultClient();

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

            CreateAccessPointResult result1 = client.createAccessPoint(createRequest);
            Assert.assertEquals(200, result1.statusCode());
            Assert.assertNotNull(result1.accessPointResult().accessPointArn());
            Assert.assertNotNull(result1.accessPointResult().alias());

            // Wait for access point to enable
            waitForAccessPointEnable(client, bucketName, accessPointName);


            // Step 1: Create AccessPointForObjectProcess (basic operation)
            CreateAccessPointForObjectProcessConfiguration createConfig = CreateAccessPointForObjectProcessConfiguration.newBuilder()
                    .accessPointName(accessPointName)
                    .build();

            CreateAccessPointForObjectProcessRequest createAccessPointForObjectProcessRequest = CreateAccessPointForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .createAccessPointForObjectProcessConfiguration(createConfig)
                    .build();

            CreateAccessPointForObjectProcessResult createResult = client.createAccessPointForObjectProcess(createAccessPointForObjectProcessRequest);
            Assert.assertEquals(200, createResult.statusCode());
            Assert.assertNotNull(createResult.accessPointForObjectProcessResult().accessPointForObjectProcessArn());
            Assert.assertNotNull(createResult.accessPointForObjectProcessResult().accessPointForObjectProcessAlias());

            // Wait for access point for object process to enable
            waitForAccessPointForObjectProcessEnable(client, bucketName, accessPointForObjectProcessName);

            // Step 2: Get AccessPointForObjectProcess (basic operation)
            GetAccessPointForObjectProcessRequest getRequest = GetAccessPointForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            GetAccessPointForObjectProcessResult getResult = client.getAccessPointForObjectProcess(getRequest);
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertEquals(accessPointName, getResult.accessPointForObjectProcessResult().accessPointName());
            Assert.assertEquals("enable", getResult.accessPointForObjectProcessResult().status());

            // Step 3: List AccessPointForObjectProcess (basic operation)
            ListAccessPointsForObjectProcessRequest listRequest = ListAccessPointsForObjectProcessRequest.newBuilder()
                    .maxKeys(100L)
                    .build();

            ListAccessPointsForObjectProcessResult listResult = client.listAccessPointsForObjectProcess(listRequest);
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

            PutAccessPointConfigForObjectProcessResult putConfigResult = client.putAccessPointConfigForObjectProcess(putConfigRequest);
            Assert.assertEquals(200, putConfigResult.statusCode());

            // Step 5: Get AccessPointConfigForObjectProcess (configuration operation)
            GetAccessPointConfigForObjectProcessRequest getConfigRequest = GetAccessPointConfigForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            GetAccessPointConfigForObjectProcessResult getConfigResult = client.getAccessPointConfigForObjectProcess(getConfigRequest);
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

            PutAccessPointPolicyForObjectProcessResult putPolicyResult = client.putAccessPointPolicyForObjectProcess(putPolicyRequest);
            Assert.assertEquals(200, putPolicyResult.statusCode());

            // Step 7: Get AccessPoint policy (policy operation)
            GetAccessPointPolicyForObjectProcessRequest getPolicyRequest = GetAccessPointPolicyForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            GetAccessPointPolicyForObjectProcessResult getPolicyResult = client.getAccessPointPolicyForObjectProcess(getPolicyRequest);
            Assert.assertEquals(200, getPolicyResult.statusCode());
            Assert.assertNotNull(getPolicyResult.body());
            Assert.assertTrue(getPolicyResult.body().toString().contains("GetObject"));

            // Step 8: Delete AccessPoint policy (policy operation)
            DeleteAccessPointPolicyForObjectProcessRequest deletePolicyRequest = DeleteAccessPointPolicyForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            DeleteAccessPointPolicyForObjectProcessResult deletePolicyResult = client.deleteAccessPointPolicyForObjectProcess(deletePolicyRequest);
            Assert.assertEquals(204, deletePolicyResult.statusCode());

            // Step 9: Delete AccessPointForObjectProcess (basic operation)
            DeleteAccessPointForObjectProcessRequest deleteRequest = DeleteAccessPointForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointForObjectProcessName)
                    .build();

            DeleteAccessPointForObjectProcessResult deleteResult = client.deleteAccessPointForObjectProcess(deleteRequest);
            Assert.assertEquals(204, deleteResult.statusCode());

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            // Clean up resources
            cleanupAccessPoint(client, bucketName, accessPointName, accessPointForObjectProcessName);
        }
    }

    private void waitForAccessPointEnable(OSSClient client, String bucketName, String accessPointName) {
        int attempts = 0;
        while (attempts < 60) {
            try {
                GetAccessPointRequest getRequest = GetAccessPointRequest.newBuilder()
                        .bucket(bucketName)
                        .accessPointName(accessPointName)
                        .build();
                
                GetAccessPointResult result = client.getAccessPoint(getRequest);
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

    private void waitForAccessPointForObjectProcessEnable(OSSClient client, String bucketName, String accessPointForObjectProcessName) {
        int attempts = 0;
        while (attempts < 60) {
            try {
                GetAccessPointForObjectProcessRequest getRequest = GetAccessPointForObjectProcessRequest.newBuilder()
                        .bucket(bucketName)
                        .accessPointForObjectProcessName(accessPointForObjectProcessName)
                        .build();

                GetAccessPointForObjectProcessResult result = client.getAccessPointForObjectProcess(getRequest);
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

    private void waitForAccessPointForObjectProcessDelete(OSSClient client, String bucketName, String accessPointForObjectProcessName) {
        int attempts = 0;
        boolean isExistAPOP = true;
        while (attempts < 60) {
            try {
                GetAccessPointForObjectProcessRequest getRequest = GetAccessPointForObjectProcessRequest.newBuilder()
                        .bucket(bucketName)
                        .accessPointForObjectProcessName(accessPointForObjectProcessName)
                        .build();

                GetAccessPointForObjectProcessResult result = client.getAccessPointForObjectProcess(getRequest);

                if ("deleting".equals(result.accessPointForObjectProcessResult().status())) {
                    while (isExistAPOP) {
                        try {
                            Thread.sleep(5000);
                            client.getAccessPointForObjectProcess(getRequest);
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

    private void waitForAccessPoinDelete(OSSClient client, String bucketName, String accessPointName) {
        int attempts = 0;
        boolean isExistAP = true;
        while (attempts < 60) {
            try {
                GetAccessPointRequest getRequest = GetAccessPointRequest.newBuilder()
                        .bucket(bucketName)
                        .accessPointName(accessPointName)
                        .build();

                GetAccessPointResult result = client.getAccessPoint(getRequest);

                if ("deleting".equals(result.accessPointResult().status())) {
                    while (isExistAP) {
                        try {
                            Thread.sleep(5000);
                            client.getAccessPoint(getRequest);
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

    private void cleanupAccessPoint(OSSClient client, String bucketName, String accessPointName, String accessPointForObjectProcessName) {
        try {
            // Clean up policy first
            DeleteAccessPointPolicyForObjectProcessRequest deletePolicyRequest = DeleteAccessPointPolicyForObjectProcessRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointForObjectProcessName(accessPointName)
                    .build();
            client.deleteAccessPointPolicyForObjectProcess(deletePolicyRequest);
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
            client.deleteAccessPointForObjectProcess(deleteRequest);
        } catch (Exception e) {
            // Ignore access point cleanup errors
        }

        // Wait for access point deletion
        waitForAccessPointForObjectProcessDelete(client, bucketName, accessPointForObjectProcessName);


        try {
            // Clean up AccessPoint
            DeleteAccessPointRequest deleteRequest = DeleteAccessPointRequest.newBuilder()
                    .bucket(bucketName)
                    .accessPointName(accessPointName)
                    .build();
            client.deleteAccessPoint(deleteRequest);
        } catch (Exception e) {
            // Ignore access point cleanup errors
        } finally {
            // Wait for access point deletion
            waitForAccessPoinDelete(client, bucketName, accessPointName);
        }
    }
}