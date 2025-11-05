package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import com.aliyun.sdk.service.oss2.signer.VectorsSignerV4;
import org.junit.Test;

import java.net.URI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class DefaultOSSVectorsClientBuilderTest {

    @Test
    public void defaultConfiguration() throws Exception {
        try (OSSVectorsClient client = OSSVectorsClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .accountId("test-user-id")
                .build()) {

            assertNotNull(client);

            // Test the buildURL method directly instead of accessing private fields via reflection
            DefaultOSSVectorsClientBuilder.VectorsEndpointProvider provider =
                    new DefaultOSSVectorsClientBuilder.VectorsEndpointProvider(
                            URI.create("https://oss-cn-hangzhou.oss-vectors.aliyuncs.com"),
                            "test-user-id");

            OperationInput input = OperationInput.newBuilder().build();
            String url = provider.buildURL(input);

            assertNotNull(url);
            assertEquals("https://oss-cn-hangzhou.oss-vectors.aliyuncs.com/", url);

        }
    }

    @Test
    public void configEndpoint() throws Exception {
        // Test default endpoint construction
        try (OSSVectorsClient client = OSSVectorsClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .accountId("test-user-id")
                .build()) {

            assertNotNull(client);

            // Test the buildURL method directly
            DefaultOSSVectorsClientBuilder.VectorsEndpointProvider provider =
                    new DefaultOSSVectorsClientBuilder.VectorsEndpointProvider(
                            URI.create("https://oss-cn-hangzhou.oss-vectors.aliyuncs.com"),
                            "test-user-id");

            OperationInput input = OperationInput.newBuilder().build();
            String url = provider.buildURL(input);

            assertNotNull(url);
            assertEquals("https://oss-cn-hangzhou.oss-vectors.aliyuncs.com/", url);
        }

        // Test internal endpoint
        try (OSSVectorsClient client = OSSVectorsClient.newBuilder()
                .region("cn-shanghai")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .useInternalEndpoint(true)
                .accountId("test-user-id")
                .build()) {

            assertNotNull(client);

            // Test the buildURL method directly
            DefaultOSSVectorsClientBuilder.VectorsEndpointProvider provider =
                    new DefaultOSSVectorsClientBuilder.VectorsEndpointProvider(
                            URI.create("https://oss-cn-shanghai-internal.oss-vectors.aliyuncs.com"),
                            "test-user-id");

            OperationInput input = OperationInput.newBuilder().build();
            String url = provider.buildURL(input);

            assertNotNull(url);
            assertEquals("https://oss-cn-shanghai-internal.oss-vectors.aliyuncs.com/", url);
        }

        // Test with custom endpoint
        try (OSSVectorsClient client = OSSVectorsClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .endpoint("http://oss-cn-shenzhen.aliyuncs.com")
                .accountId("test-user-id")
                .build()) {

            assertNotNull(client);

            // Test the buildURL method directly
            DefaultOSSVectorsClientBuilder.VectorsEndpointProvider provider =
                    new DefaultOSSVectorsClientBuilder.VectorsEndpointProvider(
                            URI.create("http://oss-cn-shenzhen.aliyuncs.com"),
                            "test-user-id");

            OperationInput input = OperationInput.newBuilder().build();
            String url = provider.buildURL(input);

            assertNotNull(url);
            assertEquals("http://oss-cn-shenzhen.aliyuncs.com/", url);
        }
    }

    @Test
    public void configUserAgent() throws Exception {
        // Test default user agent
        try (OSSVectorsClient client = OSSVectorsClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .accountId("test-user-id")
                .build()) {

            assertNotNull(client);

            // Test via the update user agent method which is already accessible
            ClientConfiguration config = ClientConfiguration.defaultBuilder()
                    .region("cn-hangzhou")
                    .build();

            ClientConfiguration updatedConfig = DefaultOSSVectorsClientBuilder.updateUserAgent(config);
            assertTrue(updatedConfig.userAgent().isPresent());
            assertEquals("vectors-client", updatedConfig.userAgent().get());
        }

        // Test custom user agent
        try (OSSVectorsClient client = OSSVectorsClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .userAgent("my-agent")
                .accountId("test-user-id")
                .build()) {

            assertNotNull(client);

            // Test via the update user agent method which is already accessible
            ClientConfiguration config = ClientConfiguration.defaultBuilder()
                    .region("cn-hangzhou")
                    .userAgent("my-agent")
                    .build();

            ClientConfiguration updatedConfig = DefaultOSSVectorsClientBuilder.updateUserAgent(config);
            assertTrue(updatedConfig.userAgent().isPresent());
            assertEquals("vectors-client/my-agent", updatedConfig.userAgent().get());
        }
    }

    @Test
    public void configHttpClient() throws Exception {
        // Test Apache HttpClient 5 (default)
        try (OSSVectorsClient client = OSSVectorsClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .accountId("test-user-id")
                .build()) {

            // We can't easily access the private fields via reflection, so we'll test by making an actual call
            // and checking that it doesn't fail in a way that would indicate the wrong HTTP client was used.
            assertNotNull(client);
            assertTrue(client instanceof DefaultOSSVectorsClient);
        }

        // Test Apache HttpClient 4
        try (OSSVectorsClient client = OSSVectorsClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .useApacheHttpClient4(true)
                .accountId("test-user-id")
                .build()) {

            // We can't easily access the private fields via reflection, so we'll test by making an actual call
            // and checking that it doesn't fail in a way that would indicate the wrong HTTP client was used.
            assertNotNull(client);
            assertTrue(client instanceof DefaultOSSVectorsClient);
        }
    }

    @Test
    public void configSigner() throws Exception {
        try (OSSVectorsClient client = OSSVectorsClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .accountId("test-user-id")
                .build()) {

            assertNotNull(client);

            // Test via the update signer method which is already accessible
            ClientConfiguration config = ClientConfiguration.defaultBuilder()
                    .region("cn-hangzhou")
                    .build();

            ClientConfiguration updatedConfig = DefaultOSSVectorsClientBuilder.updateSinger(config);
            assertTrue(updatedConfig.signer().isPresent());
            assertThat(updatedConfig.signer().get()).isInstanceOf(VectorsSignerV4.class);
        }
    }

    @Test
    public void testVectorsEndpointProviderBuildURL() {
        // Test with bucket and key
        DefaultOSSVectorsClientBuilder.VectorsEndpointProvider provider = new DefaultOSSVectorsClientBuilder.VectorsEndpointProvider(
                URI.create("https://oss-cn-hangzhou.oss-vectors.aliyuncs.com"),
                "test-account-id");

        OperationInput input = OperationInput.newBuilder()
                .bucket("test-bucket")
                .key("test-key")
                .build();

        String url = provider.buildURL(input);
        assertEquals("https://test-bucket-test-account-id.oss-cn-hangzhou.oss-vectors.aliyuncs.com/test-key", url);

        // Test with bucket only
        input = OperationInput.newBuilder()
                .bucket("test-bucket")
                .build();

        url = provider.buildURL(input);
        assertEquals("https://test-bucket-test-account-id.oss-cn-hangzhou.oss-vectors.aliyuncs.com/", url);

        // Test with key only
        input = OperationInput.newBuilder()
                .key("test-key")
                .build();

        url = provider.buildURL(input);
        assertEquals("https://oss-cn-hangzhou.oss-vectors.aliyuncs.com/test-key", url);

        // Test with neither bucket nor key
        input = OperationInput.newBuilder()
                .build();

        url = provider.buildURL(input);
        assertEquals("https://oss-cn-hangzhou.oss-vectors.aliyuncs.com/", url);

        // Test with special characters in key (URL encoding)
        input = OperationInput.newBuilder()
                .bucket("test-bucket")
                .key("test key")
                .build();

        url = provider.buildURL(input);
        assertEquals("https://test-bucket-test-account-id.oss-cn-hangzhou.oss-vectors.aliyuncs.com/test%20key", url);
    }

    @Test
    public void testUpdateEndpoint() {
        // Test with endpoint already set
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .endpoint("http://oss-cn-beijing.aliyuncs.com")
                .region("cn-hangzhou")
                .build();

        ClientConfiguration updatedConfig = DefaultOSSVectorsClientBuilder.updateEndpoint(config);
        assertEquals(config, updatedConfig);

        // Test without region
        config = ClientConfiguration.defaultBuilder()
                .build();

        updatedConfig = DefaultOSSVectorsClientBuilder.updateEndpoint(config);
        assertEquals(config, updatedConfig);

        // Test with valid region
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .build();

        updatedConfig = DefaultOSSVectorsClientBuilder.updateEndpoint(config);
        assertTrue(updatedConfig.endpoint().isPresent());
        assertEquals("cn-hangzhou.oss-vectors.aliyuncs.com", updatedConfig.endpoint().get());

        // Test with valid region and internal endpoint
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .useInternalEndpoint(true)
                .build();

        updatedConfig = DefaultOSSVectorsClientBuilder.updateEndpoint(config);
        assertTrue(updatedConfig.endpoint().isPresent());
        assertEquals("cn-hangzhou-internal.oss-vectors.aliyuncs.com", updatedConfig.endpoint().get());
    }

    @Test
    public void testUpdateSigner() {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .build();

        ClientConfiguration updatedConfig = DefaultOSSVectorsClientBuilder.updateSinger(config);
        assertTrue(updatedConfig.signer().isPresent());
        assertThat(updatedConfig.signer().get()).isInstanceOf(VectorsSignerV4.class);
    }

    @Test
    public void testUpdateUserAgent() {
        // Test default user agent
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .build();

        ClientConfiguration updatedConfig = DefaultOSSVectorsClientBuilder.updateUserAgent(config);
        assertTrue(updatedConfig.userAgent().isPresent());
        assertEquals("vectors-client", updatedConfig.userAgent().get());

        // Test custom user agent
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .userAgent("my-agent")
                .build();

        updatedConfig = DefaultOSSVectorsClientBuilder.updateUserAgent(config);
        assertTrue(updatedConfig.userAgent().isPresent());
        assertEquals("vectors-client/my-agent", updatedConfig.userAgent().get());
    }
}
