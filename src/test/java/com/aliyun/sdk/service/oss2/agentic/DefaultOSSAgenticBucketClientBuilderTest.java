package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.agentic.models.GetAgenticBucketRequest;
import com.aliyun.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest;
import com.aliyun.sdk.service.oss2.agentic.models.PutAgenticBucketAclRequest;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DefaultOSSAgenticBucketClientBuilderTest {

    @Test
    public void defaultConfiguration() throws Exception {
        try (OSSAgenticBucketClient client = OSSAgenticBucketClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .accountId("1234567890")
                .build()) {
            assertThat(client).isNotNull();
            assertThat(client).isInstanceOf(DefaultOSSAgenticBucketClient.class);
        }
    }

    @Test
    public void asyncClientConfiguration() throws Exception {
        try (OSSAsyncAgenticBucketClient client = OSSAsyncAgenticBucketClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .accountId("1234567890")
                .build()) {
            assertThat(client).isNotNull();
            assertThat(client).isInstanceOf(DefaultOSSAsyncAgenticBucketClient.class);
        }
    }

    @Test
    public void testInvalidAccountIdDeferredToOperation() {
        // Building the client with an invalid account id must NOT throw
        try (OSSAgenticBucketClient client = OSSAgenticBucketClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .accountId("abc")
                .build()) {

            assertThat(client).isNotNull();

            // The error is surfaced when an operation is invoked
            assertThatThrownBy(() -> client.listAgenticBuckets(
                    ListAgenticBucketsRequest.newBuilder().build()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("invalid account id");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateUserAgent() {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .build();

        ClientConfiguration updated = DefaultOSSAgenticBucketClientBuilder.updateUserAgent(config);
        assertThat(updated.userAgent().isPresent()).isTrue();
        assertThat(updated.userAgent().get()).isEqualTo("agentic-client");

        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .userAgent("my-agent")
                .build();

        updated = DefaultOSSAgenticBucketClientBuilder.updateUserAgent(config);
        assertThat(updated.userAgent().get()).isEqualTo("agentic-client/my-agent");
    }

    @Test
    public void testAgenticProviderBuildBucketName() {
        DefaultOSSAgenticBucketClientBuilder.AgenticProvider provider =
                new DefaultOSSAgenticBucketClientBuilder.AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "cn-hangzhou", "ab-apsr");

        OperationInput input = OperationInput.newBuilder()
                .bucket("my-bucket")
                .build();

        String resolved = provider.buildBucketName(input);
        assertThat(resolved).isEqualTo("my-bucket-1234567890-cn-hangzhou-ab-apsr");

        input = OperationInput.newBuilder().build();
        assertThat(provider.buildBucketName(input)).isNull();
    }

    @Test
    public void testAgenticProviderBuildURL() {
        DefaultOSSAgenticBucketClientBuilder.AgenticProvider provider =
                new DefaultOSSAgenticBucketClientBuilder.AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "cn-hangzhou", "ab-apsr");

        OperationInput input = OperationInput.newBuilder()
                .bucket("my-bucket")
                .build();
        String url = provider.buildURL(input);
        assertThat(url).isEqualTo("https://my-bucket-1234567890-cn-hangzhou-ab-apsr.oss-cn-hangzhou.aliyuncs.com/");

        input = OperationInput.newBuilder().build();
        url = provider.buildURL(input);
        assertThat(url).isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/");

        input = OperationInput.newBuilder()
                .bucket("my-bucket")
                .key("my-key")
                .build();
        url = provider.buildURL(input);
        assertThat(url).isEqualTo("https://my-bucket-1234567890-cn-hangzhou-ab-apsr.oss-cn-hangzhou.aliyuncs.com/my-key");
    }

    @Test
    public void testBucketSpaceProvider() {
        DefaultOSSAgenticBucketClientBuilder.AgenticProvider provider =
                new DefaultOSSAgenticBucketClientBuilder.AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "cn-hangzhou", "bs-apsr");

        OperationInput input = OperationInput.newBuilder()
                .bucket("my-space")
                .build();

        String resolved = provider.buildBucketName(input);
        assertThat(resolved).isEqualTo("my-space-1234567890-cn-hangzhou-bs-apsr");
    }

    @Test
    public void testHostRoutingRegionVsBucket() {
        DefaultOSSAgenticBucketClientBuilder.AgenticProvider provider =
                new DefaultOSSAgenticBucketClientBuilder.AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "cn-hangzhou", "ab-apsr");

        // A bucket-scoped op (GetAgenticBucket) routes to the derived bucket host
        OperationInput bucketInput = SerdeAgenticBucketBasic.fromGetAgenticBucket(
                GetAgenticBucketRequest.newBuilder().bucket("my-bucket").build());
        assertThat(provider.buildURL(bucketInput))
                .isEqualTo("https://my-bucket-1234567890-cn-hangzhou-ab-apsr.oss-cn-hangzhou.aliyuncs.com/");

        // A region-level op (ListAgenticBuckets) sets no bucket and routes to the region host
        OperationInput regionInput = SerdeAgenticBucketBasic.fromListAgenticBuckets(
                ListAgenticBucketsRequest.newBuilder().build());
        assertThat(provider.buildURL(regionInput))
                .isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/");
    }

    @Test
    public void testPutAgenticBucketAclRequiresAcl() throws Exception {
        try (OSSAgenticBucketClient client = OSSAgenticBucketClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .accountId("1234567890")
                .build()) {

            // Missing the required x-oss-acl header must be rejected before any request is sent
            assertThatThrownBy(() -> client.putAgenticBucketAcl(
                    PutAgenticBucketAclRequest.newBuilder().bucket("my-bucket").build()))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("request.acl is required");
        }
    }
}
