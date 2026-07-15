package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.models.ApplyServerSideEncryptionByDefault;
import com.aliyun.sdk.service.oss2.models.PublicAccessBlockConfiguration;
import com.aliyun.sdk.service.oss2.models.ServerSideEncryptionRule;
import com.aliyun.sdk.service.oss2.models.VersioningConfiguration;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientAgenticBucketAttributeTest extends TestBaseAgentic {

    @Test
    public void testAgenticBucketAcl() {
        OSSAgenticBucketClient client = newAgenticClient();
        String bucket = genAgenticBucketName();

        try {
            createAgenticBucket(client, bucket);

            // Put ACL
            PutAgenticBucketAclResult putResult = client.putAgenticBucketAcl(
                    PutAgenticBucketAclRequest.newBuilder()
                            .bucket(bucket)
                            .acl("private")
                            .build());
            Assert.assertEquals(200, putResult.statusCode());

            // Get ACL
            GetAgenticBucketAclResult getResult = client.getAgenticBucketAcl(
                    GetAgenticBucketAclRequest.newBuilder().bucket(bucket).build());
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.accessControlPolicy());
            Assert.assertNotNull(getResult.accessControlPolicy().accessControlList());
            assertThat(getResult.accessControlPolicy().accessControlList().grant()).isEqualTo("private");
        } finally {
            cleanAgenticBucket(bucket);
        }
    }

    @Test
    public void testAgenticBucketEncryption() {
        OSSAgenticBucketClient client = newAgenticClient();
        String bucket = genAgenticBucketName();

        try {
            createAgenticBucket(client, bucket);

            // Put encryption
            PutAgenticBucketEncryptionResult putResult = client.putAgenticBucketEncryption(
                    PutAgenticBucketEncryptionRequest.newBuilder()
                            .bucket(bucket)
                            .serverSideEncryptionRule(ServerSideEncryptionRule.newBuilder()
                                    .applyServerSideEncryptionByDefault(
                                            ApplyServerSideEncryptionByDefault.newBuilder()
                                                    .sseAlgorithm("AES256")
                                                    .build())
                                    .build())
                            .build());
            Assert.assertEquals(200, putResult.statusCode());

            // Get encryption
            GetAgenticBucketEncryptionResult getResult = client.getAgenticBucketEncryption(
                    GetAgenticBucketEncryptionRequest.newBuilder().bucket(bucket).build());
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.serverSideEncryptionRule());
            Assert.assertNotNull(getResult.serverSideEncryptionRule().applyServerSideEncryptionByDefault());
            assertThat(getResult.serverSideEncryptionRule().applyServerSideEncryptionByDefault().sseAlgorithm())
                    .isEqualTo("AES256");

            // Delete encryption
            DeleteAgenticBucketEncryptionResult deleteResult = client.deleteAgenticBucketEncryption(
                    DeleteAgenticBucketEncryptionRequest.newBuilder().bucket(bucket).build());
            Assert.assertTrue(deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);
        } finally {
            cleanAgenticBucket(bucket);
        }
    }

    @Test
    public void testAgenticBucketVersioning() {
        OSSAgenticBucketClient client = newAgenticClient();
        String bucket = genAgenticBucketName();

        try {
            createAgenticBucket(client, bucket);

            // Put versioning
            PutAgenticBucketVersioningResult putResult = client.putAgenticBucketVersioning(
                    PutAgenticBucketVersioningRequest.newBuilder()
                            .bucket(bucket)
                            .versioningConfiguration(VersioningConfiguration.newBuilder()
                                    .status("Enabled")
                                    .build())
                            .build());
            Assert.assertEquals(200, putResult.statusCode());

            // Get versioning
            GetAgenticBucketVersioningResult getResult = client.getAgenticBucketVersioning(
                    GetAgenticBucketVersioningRequest.newBuilder().bucket(bucket).build());
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.versioningConfiguration());
            assertThat(getResult.versioningConfiguration().status()).isEqualTo("Enabled");
        } finally {
            cleanAgenticBucket(bucket);
        }
    }

    @Test
    public void testAgenticBucketPolicy() {
        OSSAgenticBucketClient client = newAgenticClient();
        String bucket = genAgenticBucketName();

        try {
            createAgenticBucket(client, bucket);

            String policy = "{\"Version\":\"1\",\"Statement\":[{\"Effect\":\"Allow\"," +
                    "\"Action\":[\"oss:GetObject\"],\"Principal\":[\"*\"]," +
                    "\"Resource\":[\"acs:oss:*:" + accountId() + ":*\"]}]}";

            // Put policy
            PutAgenticBucketPolicyResult putResult = client.putAgenticBucketPolicy(
                    PutAgenticBucketPolicyRequest.newBuilder()
                            .bucket(bucket)
                            .policy(policy)
                            .build());
            Assert.assertEquals(200, putResult.statusCode());

            // Get policy
            GetAgenticBucketPolicyResult getResult = client.getAgenticBucketPolicy(
                    GetAgenticBucketPolicyRequest.newBuilder().bucket(bucket).build());
            Assert.assertEquals(200, getResult.statusCode());
            assertThat(getResult.policy()).contains("oss:GetObject");

            // Delete policy
            DeleteAgenticBucketPolicyResult deleteResult = client.deleteAgenticBucketPolicy(
                    DeleteAgenticBucketPolicyRequest.newBuilder().bucket(bucket).build());
            Assert.assertTrue(deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);
        } finally {
            cleanAgenticBucket(bucket);
        }
    }

    @Test
    public void testAgenticBucketPublicAccessBlock() {
        OSSAgenticBucketClient client = newAgenticClient();
        String bucket = genAgenticBucketName();

        try {
            createAgenticBucket(client, bucket);

            // Put public access block
            PutAgenticBucketPublicAccessBlockResult putResult = client.putAgenticBucketPublicAccessBlock(
                    PutAgenticBucketPublicAccessBlockRequest.newBuilder()
                            .bucket(bucket)
                            .publicAccessBlockConfiguration(PublicAccessBlockConfiguration.newBuilder()
                                    .blockPublicAccess(true)
                                    .build())
                            .build());
            Assert.assertEquals(200, putResult.statusCode());

            // Get public access block
            GetAgenticBucketPublicAccessBlockResult getResult = client.getAgenticBucketPublicAccessBlock(
                    GetAgenticBucketPublicAccessBlockRequest.newBuilder().bucket(bucket).build());
            Assert.assertEquals(200, getResult.statusCode());
            Assert.assertNotNull(getResult.publicAccessBlockConfiguration());

            // Delete public access block
            DeleteAgenticBucketPublicAccessBlockResult deleteResult = client.deleteAgenticBucketPublicAccessBlock(
                    DeleteAgenticBucketPublicAccessBlockRequest.newBuilder().bucket(bucket).build());
            Assert.assertTrue(deleteResult.statusCode() == 200 || deleteResult.statusCode() == 204);
        } finally {
            cleanAgenticBucket(bucket);
        }
    }
}
