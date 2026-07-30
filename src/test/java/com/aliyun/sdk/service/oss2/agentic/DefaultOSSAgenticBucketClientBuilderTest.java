package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.agentic.models.GetAgenticBucketRequest;
import com.aliyun.sdk.service.oss2.agentic.models.ListAgenticBucketsRequest;
import com.aliyun.sdk.service.oss2.agentic.models.PutAgenticBucketAclRequest;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import com.aliyun.sdk.service.oss2.types.AddressStyleType;
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
        AgenticProvider provider =
                new AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "cn-hangzhou", "ab-apsr", AddressStyleType.VirtualHosted);

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
        AgenticProvider provider =
                new AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "cn-hangzhou", "ab-apsr", AddressStyleType.VirtualHosted);

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
    public void testAgenticProviderBuildURLPathStyle() {
        AgenticProvider provider =
                new AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "cn-hangzhou", "ab-apsr", AddressStyleType.Path);

        // Under path-style the physical name lives in the path, host stays bare.
        OperationInput input = OperationInput.newBuilder()
                .bucket("my-bucket")
                .build();
        assertThat(provider.buildURL(input))
                .isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/my-bucket-1234567890-cn-hangzhou-ab-apsr/");

        input = OperationInput.newBuilder()
                .bucket("my-bucket")
                .key("my-key")
                .build();
        assertThat(provider.buildURL(input))
                .isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/my-bucket-1234567890-cn-hangzhou-ab-apsr/my-key");

        // A region-level op (no bucket) still routes to the bare endpoint.
        input = OperationInput.newBuilder().build();
        assertThat(provider.buildURL(input))
                .isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/");
    }

    @Test
    public void testBucketSpaceProvider() {
        AgenticProvider provider =
                new AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "cn-hangzhou", "bs-apsr", AddressStyleType.VirtualHosted);

        OperationInput input = OperationInput.newBuilder()
                .bucket("my-space")
                .build();

        String resolved = provider.buildBucketName(input);
        assertThat(resolved).isEqualTo("my-space-1234567890-cn-hangzhou-bs-apsr");
    }

    @Test
    public void testHostRoutingRegionVsBucket() {
        AgenticProvider provider =
                new AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "cn-hangzhou", "ab-apsr", AddressStyleType.VirtualHosted);

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

    @Test
    public void testAgenticProviderMissingRequiredFields() {
        OperationInput input = OperationInput.newBuilder().bucket("my-bucket").build();

        // Missing accountId
        AgenticProvider p1 =
                new AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "", "cn-hangzhou", "ab-apsr", AddressStyleType.VirtualHosted);
        assertThatThrownBy(() -> p1.buildURL(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("AccountId");
        assertThatThrownBy(() -> p1.buildBucketName(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("AccountId");

        // Missing region
        AgenticProvider p2 =
                new AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "", "ab-apsr", AddressStyleType.VirtualHosted);
        assertThatThrownBy(() -> p2.buildURL(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Region");
        assertThatThrownBy(() -> p2.buildBucketName(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Region");

        // null accountId/region is normalized to "" in the constructor, so it
        // surfaces as the required-field error rather than a NullPointerException
        AgenticProvider pNull =
                new AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        null, null, "ab-apsr", AddressStyleType.VirtualHosted);
        assertThatThrownBy(() -> pNull.buildBucketName(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("AccountId");

        // No bucket: validation is skipped, no error
        assertThat(p2.buildBucketName(OperationInput.newBuilder().build())).isNull();
    }

    @Test
    public void testAgenticProviderHostLabelTooLong() {
        // full name = "{bucket}-1234567890-cn-hangzhou-ab-apsr" -> len(bucket) + 31
        String suffixPart = "-1234567890-cn-hangzhou-ab-apsr";
        AgenticProvider vh =
                new AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "cn-hangzhou", "ab-apsr", AddressStyleType.VirtualHosted);

        // Boundary: full name == 63 (bucket 32) is allowed in virtual-hosted style
        String okName = repeat('a', 32);
        assertThat((okName + suffixPart).length()).isEqualTo(63);
        assertThat(vh.buildURL(OperationInput.newBuilder().bucket(okName).build()))
                .isEqualTo("https://" + okName + suffixPart + ".oss-cn-hangzhou.aliyuncs.com/");

        // Over limit: full name == 64 (bucket 33) is rejected in virtual-hosted style
        String longName = repeat('a', 33);
        assertThat((longName + suffixPart).length()).isEqualTo(64);
        assertThatThrownBy(() -> vh.buildURL(OperationInput.newBuilder().bucket(longName).build()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exceeds the maximum length of 63 characters");

        // Path style has no DNS label limit, so the same long name is fine
        AgenticProvider path =
                new AgenticProvider(
                        URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                        "1234567890", "cn-hangzhou", "ab-apsr", AddressStyleType.Path);
        assertThat(path.buildURL(OperationInput.newBuilder().bucket(longName).build()))
                .isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/" + longName + suffixPart + "/");
    }

    private static String repeat(char c, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
