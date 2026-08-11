package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.models.ApplyServerSideEncryptionByDefault;
import com.aliyun.sdk.service.oss2.models.PublicAccessBlockConfiguration;
import com.aliyun.sdk.service.oss2.models.ServerSideEncryptionRule;
import com.aliyun.sdk.service.oss2.models.VersioningConfiguration;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.ExecutionException;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientAgenticBucketAttributeAsyncTest extends TestBaseAgentic {

    @Test
    public void testAgenticBucketAclAsync() throws ExecutionException, InterruptedException {
        OSSAsyncAgenticBucketClient client = newAgenticAsyncClient();
        String bucket = agenticBucketName;

        // Put ACL
        PutAgenticBucketAclResult putResult = client.putAgenticBucketAclAsync(
                PutAgenticBucketAclRequest.newBuilder()
                        .bucket(bucket)
                        .acl("private")
                        .build()).get();
        Assert.assertEquals(200, putResult.statusCode());

        // Get ACL
        GetAgenticBucketAclResult getResult = client.getAgenticBucketAclAsync(
                GetAgenticBucketAclRequest.newBuilder().bucket(bucket).build()).get();
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.accessControlPolicy());
        Assert.assertNotNull(getResult.accessControlPolicy().accessControlList());
        assertThat(getResult.accessControlPolicy().accessControlList().grant()).isEqualTo("private");
    }

    @Test
    public void testAgenticBucketEncryptionAsync() throws ExecutionException, InterruptedException {
        OSSAsyncAgenticBucketClient client = newAgenticAsyncClient();
        String bucket = agenticBucketName;

        // Put encryption
        PutAgenticBucketEncryptionResult putResult = client.putAgenticBucketEncryptionAsync(
                PutAgenticBucketEncryptionRequest.newBuilder()
                        .bucket(bucket)
                        .serverSideEncryptionRule(ServerSideEncryptionRule.newBuilder()
                                .applyServerSideEncryptionByDefault(
                                        ApplyServerSideEncryptionByDefault.newBuilder()
                                                .sseAlgorithm("AES256")
                                                .build())
                                .build())
                        .build()).get();
        Assert.assertEquals(200, putResult.statusCode());

        // Get encryption
        GetAgenticBucketEncryptionResult getResult = client.getAgenticBucketEncryptionAsync(
                GetAgenticBucketEncryptionRequest.newBuilder().bucket(bucket).build()).get();
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.serverSideEncryptionRule());
        Assert.assertNotNull(getResult.serverSideEncryptionRule().applyServerSideEncryptionByDefault());
        assertThat(getResult.serverSideEncryptionRule().applyServerSideEncryptionByDefault().sseAlgorithm())
                .isEqualTo("AES256");

        // Delete encryption
        DeleteAgenticBucketEncryptionResult deleteResult = client.deleteAgenticBucketEncryptionAsync(
                DeleteAgenticBucketEncryptionRequest.newBuilder().bucket(bucket).build()).get();
        Assert.assertTrue(deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);
    }

    @Test
    public void testAgenticBucketVersioningAsync() throws ExecutionException, InterruptedException {
        OSSAsyncAgenticBucketClient client = newAgenticAsyncClient();
        String bucket = agenticBucketName;

        // Put versioning
        PutAgenticBucketVersioningResult putResult = client.putAgenticBucketVersioningAsync(
                PutAgenticBucketVersioningRequest.newBuilder()
                        .bucket(bucket)
                        .versioningConfiguration(VersioningConfiguration.newBuilder()
                                .status("Enabled")
                                .build())
                        .build()).get();
        Assert.assertEquals(200, putResult.statusCode());

        // Get versioning
        GetAgenticBucketVersioningResult getResult = client.getAgenticBucketVersioningAsync(
                GetAgenticBucketVersioningRequest.newBuilder().bucket(bucket).build()).get();
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.versioningConfiguration());
        assertThat(getResult.versioningConfiguration().status()).isEqualTo("Enabled");
    }

    @Test
    public void testAgenticBucketPolicyAsync() throws ExecutionException, InterruptedException {
        OSSAsyncAgenticBucketClient client = newAgenticAsyncClient();
        String bucket = agenticBucketName;

        String policy = "{\"Version\":\"1\",\"Statement\":[{\"Effect\":\"Allow\"," +
                "\"Action\":[\"oss:GetObject\"],\"Principal\":[\"" + accountId() + "\"]," +
                "\"Resource\":[\"acs:oss:*:" + accountId() + ":*\"]}]}";

        // Put policy
        PutAgenticBucketPolicyResult putResult = client.putAgenticBucketPolicyAsync(
                PutAgenticBucketPolicyRequest.newBuilder()
                        .bucket(bucket)
                        .policy(policy)
                        .build()).get();
        Assert.assertEquals(200, putResult.statusCode());

        // Get policy
        GetAgenticBucketPolicyResult getResult = client.getAgenticBucketPolicyAsync(
                GetAgenticBucketPolicyRequest.newBuilder().bucket(bucket).build()).get();
        Assert.assertEquals(200, getResult.statusCode());
        assertThat(getResult.policy()).contains("oss:GetObject");

        // Delete policy
        DeleteAgenticBucketPolicyResult deleteResult = client.deleteAgenticBucketPolicyAsync(
                DeleteAgenticBucketPolicyRequest.newBuilder().bucket(bucket).build()).get();
        Assert.assertTrue(deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);
    }

    @Test
    public void testAgenticBucketPublicAccessBlockAsync() throws ExecutionException, InterruptedException {
        OSSAsyncAgenticBucketClient client = newAgenticAsyncClient();
        String bucket = agenticBucketName;

        // Put public access block
        PutAgenticBucketPublicAccessBlockResult putResult = client.putAgenticBucketPublicAccessBlockAsync(
                PutAgenticBucketPublicAccessBlockRequest.newBuilder()
                        .bucket(bucket)
                        .publicAccessBlockConfiguration(PublicAccessBlockConfiguration.newBuilder()
                                .blockPublicAccess(true)
                                .build())
                        .build()).get();
        Assert.assertEquals(200, putResult.statusCode());

        // Get public access block
        GetAgenticBucketPublicAccessBlockResult getResult = client.getAgenticBucketPublicAccessBlockAsync(
                GetAgenticBucketPublicAccessBlockRequest.newBuilder().bucket(bucket).build()).get();
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.publicAccessBlockConfiguration());

        // Delete public access block
        DeleteAgenticBucketPublicAccessBlockResult deleteResult = client.deleteAgenticBucketPublicAccessBlockAsync(
                DeleteAgenticBucketPublicAccessBlockRequest.newBuilder().bucket(bucket).build()).get();
        Assert.assertTrue(deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);
    }

    /**
     * Test put and get agentic bucket ACL using path-style addressing (async).
     * Mirrors Go/Python probe: if the endpoint rejects path-style
     * (SecondLevelDomainForbidden), skip rather than fail.
     */
    @Test
    public void testAgenticBucketAclPathStyleAsync() throws ExecutionException, InterruptedException {
        OSSAsyncAgenticBucketClient client = newAgenticAsyncClientPathStyle();
        String bucket = agenticBucketName;

        // Probe: Put ACL via path-style client
        PutAgenticBucketAclResult putResult;
        try {
            putResult = client.putAgenticBucketAclAsync(
                    PutAgenticBucketAclRequest.newBuilder()
                            .bucket(bucket)
                            .acl("private")
                            .build()).get();
        } catch (Exception e) {
            if (isSecondLevelDomainForbidden(e)) {
                System.out.println("path-style addressing not allowed on this endpoint: " + e.getMessage());
                return;
            }
            throw e;
        }
        Assert.assertEquals(200, putResult.statusCode());

        // Get ACL via path-style client
        GetAgenticBucketAclResult getResult = client.getAgenticBucketAclAsync(
                GetAgenticBucketAclRequest.newBuilder().bucket(bucket).build()).get();
        Assert.assertEquals(200, getResult.statusCode());
        Assert.assertNotNull(getResult.accessControlPolicy());
        assertThat(getResult.accessControlPolicy().accessControlList().grant()).isEqualTo("private");
    }
}
