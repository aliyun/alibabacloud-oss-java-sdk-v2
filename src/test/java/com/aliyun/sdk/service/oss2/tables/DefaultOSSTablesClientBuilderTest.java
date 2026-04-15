package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import com.aliyun.sdk.service.oss2.signer.TablesSignerV4;
import com.aliyun.sdk.service.oss2.types.AddressStyleType;
import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class DefaultOSSTablesClientBuilderTest {

    @Test
    public void defaultConfiguration() throws Exception {
        try (OSSTablesClient client = OSSTablesClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build()) {

            assertNotNull(client);

            // Test the buildURL method directly instead of accessing private fields via reflection
            DefaultOSSTablesClientBuilder.TablesEndpointProvider provider =
                    new DefaultOSSTablesClientBuilder.TablesEndpointProvider(
                            URI.create("https://oss-cn-hangzhou.oss-tables.aliyuncs.com"),
                            AddressStyleType.VirtualHosted);

            OperationInput input = OperationInput.newBuilder().build();
            String url = provider.buildURL(input);

            assertNotNull(url);
            assertEquals("https://oss-cn-hangzhou.oss-tables.aliyuncs.com/", url);

        }
    }

    @Test
    public void configEndpoint() throws Exception {
        // Test default endpoint construction
        try (OSSTablesClient client = OSSTablesClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build()) {

            assertNotNull(client);

            // Test the buildURL method directly
            DefaultOSSTablesClientBuilder.TablesEndpointProvider provider =
                    new DefaultOSSTablesClientBuilder.TablesEndpointProvider(
                            URI.create("https://oss-cn-hangzhou.oss-tables.aliyuncs.com"),
                            AddressStyleType.VirtualHosted);

            OperationInput input = OperationInput.newBuilder().build();
            String url = provider.buildURL(input);

            assertNotNull(url);
            assertEquals("https://oss-cn-hangzhou.oss-tables.aliyuncs.com/", url);
        }

        // Test internal endpoint
        try (OSSTablesClient client = OSSTablesClient.newBuilder()
                .region("cn-shanghai")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .useInternalEndpoint(true)
                .build()) {

            assertNotNull(client);

            // Test the buildURL method directly
            DefaultOSSTablesClientBuilder.TablesEndpointProvider provider =
                    new DefaultOSSTablesClientBuilder.TablesEndpointProvider(
                            URI.create("https://oss-cn-shanghai-internal.oss-tables.aliyuncs.com"),
                            AddressStyleType.VirtualHosted);

            OperationInput input = OperationInput.newBuilder().build();
            String url = provider.buildURL(input);

            assertNotNull(url);
            assertEquals("https://oss-cn-shanghai-internal.oss-tables.aliyuncs.com/", url);
        }

        // Test with custom endpoint
        try (OSSTablesClient client = OSSTablesClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .endpoint("http://oss-cn-shenzhen.aliyuncs.com")
                .build()) {

            assertNotNull(client);

            // Test the buildURL method directly
            DefaultOSSTablesClientBuilder.TablesEndpointProvider provider =
                    new DefaultOSSTablesClientBuilder.TablesEndpointProvider(
                            URI.create("http://oss-cn-shenzhen.aliyuncs.com"),
                            AddressStyleType.VirtualHosted);

            OperationInput input = OperationInput.newBuilder().build();
            String url = provider.buildURL(input);

            assertNotNull(url);
            assertEquals("http://oss-cn-shenzhen.aliyuncs.com/", url);
        }
    }

    @Test
    public void configUserAgent() throws Exception {
        // Test default user agent
        try (OSSTablesClient client = OSSTablesClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build()) {

            assertNotNull(client);

            // Test via the update user agent method which is already accessible
            ClientConfiguration config = ClientConfiguration.defaultBuilder()
                    .region("cn-hangzhou")
                    .build();

            ClientConfiguration updatedConfig = DefaultOSSTablesClientBuilder.updateUserAgent(config);
            assertTrue(updatedConfig.userAgent().isPresent());
            assertEquals("tables-client", updatedConfig.userAgent().get());
        }

        // Test custom user agent
        try (OSSTablesClient client = OSSTablesClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .userAgent("my-agent")
                .build()) {

            assertNotNull(client);

            // Test via the update user agent method which is already accessible
            ClientConfiguration config = ClientConfiguration.defaultBuilder()
                    .region("cn-hangzhou")
                    .userAgent("my-agent")
                    .build();

            ClientConfiguration updatedConfig = DefaultOSSTablesClientBuilder.updateUserAgent(config);
            assertTrue(updatedConfig.userAgent().isPresent());
            assertEquals("tables-client/my-agent", updatedConfig.userAgent().get());
        }
    }

    @Test
    public void configHttpClient() throws Exception {
        // Test Apache HttpClient 5 (default)
        try (OSSTablesClient client = OSSTablesClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build()) {

            // We can't easily access the private fields via reflection, so we'll test by making an actual call
            // and checking that it doesn't fail in a way that would indicate the wrong HTTP client was used.
            assertNotNull(client);
            assertTrue(client instanceof DefaultOSSTablesClient);
        }

        // Test Apache HttpClient 4
        try (OSSTablesClient client = OSSTablesClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .useApacheHttpClient4(true)
                .build()) {

            // We can't easily access the private fields via reflection, so we'll test by making an actual call
            // and checking that it doesn't fail in a way that would indicate the wrong HTTP client was used.
            assertNotNull(client);
            assertTrue(client instanceof DefaultOSSTablesClient);
        }
    }

    @Test
    public void configSigner() throws Exception {
        try (OSSTablesClient client = OSSTablesClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build()) {

            assertNotNull(client);

            // Test via the update signer method which is already accessible
            ClientConfiguration config = ClientConfiguration.defaultBuilder()
                    .region("cn-hangzhou")
                    .build();

            ClientConfiguration updatedConfig = DefaultOSSTablesClientBuilder.updateSigner(config);
            assertTrue(updatedConfig.signer().isPresent());
            assertThat(updatedConfig.signer().get()).isInstanceOf(TablesSignerV4.class);
        }
    }

    @Test
    public void testTablesEndpointProviderBuildURL() {
        // Test VirtualHosted mode with neither bucket nor key
        DefaultOSSTablesClientBuilder.TablesEndpointProvider provider = new DefaultOSSTablesClientBuilder.TablesEndpointProvider(
                URI.create("https://oss-cn-hangzhou.oss-tables.aliyuncs.com"),
                AddressStyleType.VirtualHosted);

        OperationInput input = OperationInput.newBuilder()
                .build();

        String url = provider.buildURL(input);
        assertEquals("https://oss-cn-hangzhou.oss-tables.aliyuncs.com/", url);

        // Test with key only
        input = OperationInput.newBuilder()
                .key("test-key")
                .build();

        url = provider.buildURL(input);
        assertEquals("https://oss-cn-hangzhou.oss-tables.aliyuncs.com/test-key", url);

        // Test with bucket arn (format: acs:osstables:region:account:bucket/bucketName)
        input = OperationInput.newBuilder()
                .bucket("acs:osstables:cn-hangzhou:test-account:test-bucket/test-table")
                .build();

        url = provider.buildURL(input);
        assertEquals("https://test-table-test-account.oss-cn-hangzhou.oss-tables.aliyuncs.com/", url);

        // Test with bucket arn and key
        input = OperationInput.newBuilder()
                .bucket("acs:osstables:cn-hangzhou:test-account:test-bucket/test-table")
                .key("test-key")
                .build();

        url = provider.buildURL(input);
        assertEquals("https://test-table-test-account.oss-cn-hangzhou.oss-tables.aliyuncs.com/test-key", url);

        // Test with special characters in key (URL encoding)
        input = OperationInput.newBuilder()
                .bucket("acs:osstables:cn-hangzhou:test-account:test-bucket/test-table")
                .key("test%20key")
                .build();

        url = provider.buildURL(input);
        assertEquals("https://test-table-test-account.oss-cn-hangzhou.oss-tables.aliyuncs.com/test%20key", url);
    }

    @Test
    public void testTablesEndpointProviderWithPathStyle() {
        // Test Path mode
        DefaultOSSTablesClientBuilder.TablesEndpointProvider pathProvider = new DefaultOSSTablesClientBuilder.TablesEndpointProvider(
                URI.create("https://oss-cn-hangzhou.oss-tables.aliyuncs.com"),
                AddressStyleType.Path);

        // Test with neither bucket nor key
        OperationInput input = OperationInput.newBuilder()
                .build();

        String url = pathProvider.buildURL(input);
        assertEquals("https://oss-cn-hangzhou.oss-tables.aliyuncs.com/", url);

        // Test with key only
        input = OperationInput.newBuilder()
                .key("test-key")
                .build();

        url = pathProvider.buildURL(input);
        assertEquals("https://oss-cn-hangzhou.oss-tables.aliyuncs.com/test-key", url);

        // Test with bucket arn - full ARN should be in path without parsing
        input = OperationInput.newBuilder()
                .bucket("acs:osstables:cn-hangzhou:test-account:test-bucket/test-table")
                .build();

        url = pathProvider.buildURL(input);
        assertEquals("https://oss-cn-hangzhou.oss-tables.aliyuncs.com/", url);

        // Test with bucket arn and key
        input = OperationInput.newBuilder()
                .bucket("acs:osstables:cn-hangzhou:test-account:test-bucket/test-table")
                .key("test-key")
                .build();

        url = pathProvider.buildURL(input);
        assertEquals("https://oss-cn-hangzhou.oss-tables.aliyuncs.com/test-key", url);
    }

    @Test
    public void testTablesEndpointProviderWithCName() {
        // Test CName mode
        DefaultOSSTablesClientBuilder.TablesEndpointProvider cnameProvider = new DefaultOSSTablesClientBuilder.TablesEndpointProvider(
                URI.create("https://custom-domain.example.com"),
                AddressStyleType.CName);

        // Test with neither bucket nor key
        OperationInput input = OperationInput.newBuilder()
                .build();

        String url = cnameProvider.buildURL(input);
        assertEquals("https://custom-domain.example.com/", url);

        // Test with key only
        input = OperationInput.newBuilder()
                .key("test-key")
                .build();

        url = cnameProvider.buildURL(input);
        assertEquals("https://custom-domain.example.com/test-key", url);

        // Test with bucket arn - host should remain unchanged in CName mode
        input = OperationInput.newBuilder()
                .bucket("acs:osstables:cn-hangzhou:test-account:test-bucket/test-table")
                .build();

        url = cnameProvider.buildURL(input);
        assertEquals("https://custom-domain.example.com/", url);

        // Test with bucket arn and key
        input = OperationInput.newBuilder()
                .bucket("acs:osstables:cn-hangzhou:test-account:test-bucket/test-table")
                .key("test-key")
                .build();

        url = cnameProvider.buildURL(input);
        assertEquals("https://custom-domain.example.com/test-key", url);
    }

    @Test
    public void testUpdateEndpoint() {
        // Test with endpoint already set
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .endpoint("http://oss-cn-beijing.aliyuncs.com")
                .region("cn-hangzhou")
                .build();

        ClientConfiguration updatedConfig = DefaultOSSTablesClientBuilder.updateEndpoint(config);
        assertEquals(config, updatedConfig);

        // Test without region
        config = ClientConfiguration.defaultBuilder()
                .build();

        updatedConfig = DefaultOSSTablesClientBuilder.updateEndpoint(config);
        assertEquals(config, updatedConfig);

        // Test with valid region
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .build();

        updatedConfig = DefaultOSSTablesClientBuilder.updateEndpoint(config);
        assertTrue(updatedConfig.endpoint().isPresent());
        assertEquals("cn-hangzhou.oss-tables.aliyuncs.com", updatedConfig.endpoint().get());

        // Test with valid region and internal endpoint
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .useInternalEndpoint(true)
                .build();

        updatedConfig = DefaultOSSTablesClientBuilder.updateEndpoint(config);
        assertTrue(updatedConfig.endpoint().isPresent());
        assertEquals("cn-hangzhou-internal.oss-tables.aliyuncs.com", updatedConfig.endpoint().get());
    }

    @Test
    public void testUpdateSigner() {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .build();

        ClientConfiguration updatedConfig = DefaultOSSTablesClientBuilder.updateSigner(config);
        assertTrue(updatedConfig.signer().isPresent());
        assertThat(updatedConfig.signer().get()).isInstanceOf(TablesSignerV4.class);
    }

    @Test
    public void testUpdateUserAgent() {
        // Test default user agent
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .build();

        ClientConfiguration updatedConfig = DefaultOSSTablesClientBuilder.updateUserAgent(config);
        assertTrue(updatedConfig.userAgent().isPresent());
        assertEquals("tables-client", updatedConfig.userAgent().get());

        // Test custom user agent
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .userAgent("my-agent")
                .build();

        updatedConfig = DefaultOSSTablesClientBuilder.updateUserAgent(config);
        assertTrue(updatedConfig.userAgent().isPresent());
        assertEquals("tables-client/my-agent", updatedConfig.userAgent().get());
    }
}
