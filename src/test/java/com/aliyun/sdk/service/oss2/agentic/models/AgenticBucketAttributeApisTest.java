package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.transform.*;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AgenticBucketAttributeApisTest {

    @Test
    public void testPutAgenticBucketAclSerde() {
        PutAgenticBucketAclRequest request = PutAgenticBucketAclRequest.newBuilder()
                .bucket("test-bucket")
                .acl("private")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.acl()).isEqualTo("private");

        OperationInput input = SerdeAgenticBucketAcl.fromPutAgenticBucketAcl(request);
        assertThat(input.opName()).isEqualTo("PutAgenticBucketAcl");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.parameters().get("acl")).isEqualTo("");
        assertThat(input.bucket().get()).isEqualTo("test-bucket");
    }

    @Test
    public void testGetAgenticBucketAclSerde() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<AccessControlPolicy>\n" +
                "  <Owner>\n" +
                "    <ID>owner-id</ID>\n" +
                "    <DisplayName>owner-name</DisplayName>\n" +
                "  </Owner>\n" +
                "  <AccessControlList>\n" +
                "    <Grant>private</Grant>\n" +
                "  </AccessControlList>\n" +
                "</AccessControlPolicy>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-request-id", "req-acl"))
                .status("OK")
                .statusCode(200)
                .build();

        GetAgenticBucketAclResult result = SerdeAgenticBucketAcl.toGetAgenticBucketAcl(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.accessControlPolicy()).isNotNull();
    }

    @Test
    public void testPutAgenticBucketEncryptionSerde() {
        ServerSideEncryptionRule rule = ServerSideEncryptionRule.newBuilder()
                .applyServerSideEncryptionByDefault(
                        ApplyServerSideEncryptionByDefault.newBuilder()
                                .sseAlgorithm("AES256")
                                .build())
                .build();

        PutAgenticBucketEncryptionRequest request = PutAgenticBucketEncryptionRequest.newBuilder()
                .bucket("test-bucket")
                .serverSideEncryptionRule(rule)
                .build();

        OperationInput input = SerdeAgenticBucketEncryption.fromPutAgenticBucketEncryption(request);
        assertThat(input.opName()).isEqualTo("PutAgenticBucketEncryption");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.parameters().get("encryption")).isEqualTo("");
        assertThat(input.body()).isPresent();
    }

    @Test
    public void testGetAgenticBucketEncryptionSerde() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ServerSideEncryptionRule>\n" +
                "  <ApplyServerSideEncryptionByDefault>\n" +
                "    <SSEAlgorithm>AES256</SSEAlgorithm>\n" +
                "  </ApplyServerSideEncryptionByDefault>\n" +
                "</ServerSideEncryptionRule>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-request-id", "req-enc"))
                .status("OK")
                .statusCode(200)
                .build();

        GetAgenticBucketEncryptionResult result = SerdeAgenticBucketEncryption.toGetAgenticBucketEncryption(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.serverSideEncryptionRule()).isNotNull();
        assertThat(result.serverSideEncryptionRule().applyServerSideEncryptionByDefault().sseAlgorithm()).isEqualTo("AES256");
    }

    @Test
    public void testDeleteAgenticBucketEncryptionSerde() {
        DeleteAgenticBucketEncryptionRequest request = DeleteAgenticBucketEncryptionRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        OperationInput input = SerdeAgenticBucketEncryption.fromDeleteAgenticBucketEncryption(request);
        assertThat(input.opName()).isEqualTo("DeleteAgenticBucketEncryption");
        assertThat(input.method()).isEqualTo("DELETE");
        assertThat(input.parameters().get("encryption")).isEqualTo("");
    }

    @Test
    public void testPutAgenticBucketVersioningSerde() {
        VersioningConfiguration config = VersioningConfiguration.newBuilder()
                .status("Enabled")
                .build();

        PutAgenticBucketVersioningRequest request = PutAgenticBucketVersioningRequest.newBuilder()
                .bucket("test-bucket")
                .versioningConfiguration(config)
                .build();

        OperationInput input = SerdeAgenticBucketVersioning.fromPutAgenticBucketVersioning(request);
        assertThat(input.opName()).isEqualTo("PutAgenticBucketVersioning");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.parameters().get("versioning")).isEqualTo("");
        assertThat(input.body()).isPresent();
    }

    @Test
    public void testGetAgenticBucketVersioningSerde() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<VersioningConfiguration>\n" +
                "  <Status>Enabled</Status>\n" +
                "</VersioningConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-request-id", "req-ver"))
                .status("OK")
                .statusCode(200)
                .build();

        GetAgenticBucketVersioningResult result = SerdeAgenticBucketVersioning.toGetAgenticBucketVersioning(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.versioningConfiguration()).isNotNull();
        assertThat(result.versioningConfiguration().status()).isEqualTo("Enabled");
    }

    @Test
    public void testPutAgenticBucketPolicySerde() {
        String policyJson = "{\"Version\":\"1\",\"Statement\":[]}";
        PutAgenticBucketPolicyRequest request = PutAgenticBucketPolicyRequest.newBuilder()
                .bucket("test-bucket")
                .policy(policyJson)
                .build();

        OperationInput input = SerdeAgenticBucketPolicy.fromPutAgenticBucketPolicy(request);
        assertThat(input.opName()).isEqualTo("PutAgenticBucketPolicy");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/json");
        assertThat(input.parameters().get("policy")).isEqualTo("");
        assertThat(input.body()).isPresent();
    }

    @Test
    public void testGetAgenticBucketPolicySerde() {
        String policyJson = "{\"Version\":\"1\",\"Statement\":[]}";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(policyJson))
                .headers(MapUtils.of("x-oss-request-id", "req-pol"))
                .status("OK")
                .statusCode(200)
                .build();

        GetAgenticBucketPolicyResult result = SerdeAgenticBucketPolicy.toGetAgenticBucketPolicy(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.policy()).isEqualTo(policyJson);
    }

    @Test
    public void testDeleteAgenticBucketPolicySerde() {
        DeleteAgenticBucketPolicyRequest request = DeleteAgenticBucketPolicyRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        OperationInput input = SerdeAgenticBucketPolicy.fromDeleteAgenticBucketPolicy(request);
        assertThat(input.opName()).isEqualTo("DeleteAgenticBucketPolicy");
        assertThat(input.method()).isEqualTo("DELETE");
        assertThat(input.parameters().get("policy")).isEqualTo("");
    }

    @Test
    public void testPutAgenticBucketPublicAccessBlockSerde() {
        PublicAccessBlockConfiguration config = PublicAccessBlockConfiguration.newBuilder()
                .blockPublicAccess(true)
                .build();

        PutAgenticBucketPublicAccessBlockRequest request = PutAgenticBucketPublicAccessBlockRequest.newBuilder()
                .bucket("test-bucket")
                .publicAccessBlockConfiguration(config)
                .build();

        OperationInput input = SerdeAgenticBucketPublicAccessBlock.fromPutAgenticBucketPublicAccessBlock(request);
        assertThat(input.opName()).isEqualTo("PutAgenticBucketPublicAccessBlock");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.parameters().get("publicAccessBlock")).isEqualTo("");
        assertThat(input.body()).isPresent();
    }

    @Test
    public void testGetAgenticBucketPublicAccessBlockSerde() {
        GetAgenticBucketPublicAccessBlockRequest request = GetAgenticBucketPublicAccessBlockRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        OperationInput input = SerdeAgenticBucketPublicAccessBlock.fromGetAgenticBucketPublicAccessBlock(request);
        assertThat(input.opName()).isEqualTo("GetAgenticBucketPublicAccessBlock");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.parameters().get("publicAccessBlock")).isEqualTo("");
        assertThat(input.bucket().get()).isEqualTo("test-bucket");

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<PublicAccessBlockConfiguration>\n" +
                "  <BlockPublicAccess>true</BlockPublicAccess>\n" +
                "</PublicAccessBlockConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(MapUtils.of("x-oss-request-id", "req-pab"))
                .status("OK")
                .statusCode(200)
                .build();

        GetAgenticBucketPublicAccessBlockResult result =
                SerdeAgenticBucketPublicAccessBlock.toGetAgenticBucketPublicAccessBlock(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.publicAccessBlockConfiguration()).isNotNull();
        assertThat(result.publicAccessBlockConfiguration().blockPublicAccess()).isTrue();
    }

    @Test
    public void testDeleteAgenticBucketPublicAccessBlockSerde() {
        DeleteAgenticBucketPublicAccessBlockRequest request = DeleteAgenticBucketPublicAccessBlockRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        OperationInput input = SerdeAgenticBucketPublicAccessBlock.fromDeleteAgenticBucketPublicAccessBlock(request);
        assertThat(input.opName()).isEqualTo("DeleteAgenticBucketPublicAccessBlock");
        assertThat(input.method()).isEqualTo("DELETE");
        assertThat(input.parameters().get("publicAccessBlock")).isEqualTo("");
    }
}
