package com.aliyun.sdk.service.oss2.internal;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.ClientOptions;
import com.aliyun.sdk.service.oss2.Defaults;
import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import com.aliyun.sdk.service.oss2.retry.NopRetryer;
import com.aliyun.sdk.service.oss2.retry.StandardRetryer;
import com.aliyun.sdk.service.oss2.signer.SignerV1;
import com.aliyun.sdk.service.oss2.signer.SignerV4;
import com.aliyun.sdk.service.oss2.types.AddressStyleType;
import com.aliyun.sdk.service.oss2.types.AuthMethodType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ClientImplTest {

    @Test
    public void defaultConfiguration() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        ClientImpl client = new ClientImpl(config);

        // default
        assertEquals("oss", client.options.product());
        assertEquals("cn-hangzhou", client.options.region());
        assertNotNull(client.options.endpoint());
        assertEquals("oss-cn-hangzhou.aliyuncs.com", client.options.endpoint().getHost());
        assertEquals("https", client.options.endpoint().getScheme());

        assertEquals(AddressStyleType.VirtualHosted, client.options.addressStyle());
        assertEquals(AuthMethodType.Header, client.options.authMethod());

        assertThat(client.options.retryer()).isInstanceOf(StandardRetryer.class);
        assertEquals(Defaults.MAX_ATTEMPTS, client.options.retryer().maxAttempts());

        assertThat(client.options.signer()).isInstanceOf(SignerV4.class);
        assertThat(client.options.credentialsProvider()).isInstanceOf(AnonymousCredentialsProvider.class);

        assertNotNull(client.options.httpClient());
        assertNotNull(client.options.additionalHeaders());
        assertEquals(0, client.options.additionalHeaders().size());

        client.close();
    }

    @Test
    public void configSignatureVersion() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();


        // default
        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.options.signer()).isInstanceOf(SignerV4.class);
        }

        // set to v1
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .signatureVersion("v1")
                .build();
        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.options.signer()).isInstanceOf(SignerV1.class);
        }

        // set to v4
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .signatureVersion("v4")
                .build();
        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.options.signer()).isInstanceOf(SignerV4.class);
        }

        // set to any string
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .signatureVersion("any")
                .build();
        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.options.signer()).isInstanceOf(SignerV4.class);
        }
    }

    @Test
    public void configEndpoint() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        // default
        try (ClientImpl client = new ClientImpl(config)) {
            assertFalse(config.endpoint().isPresent());
            assertEquals("cn-hangzhou", client.options.region());
            assertNotNull(client.options.endpoint());
            assertEquals("oss-cn-hangzhou.aliyuncs.com", client.options.endpoint().getHost());
            assertEquals("https", client.options.endpoint().getScheme());
        }

        // internal
        config = ClientConfiguration.defaultBuilder()
                .region("cn-shanghai")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .useInternalEndpoint(true)
                .build();
        try (ClientImpl client = new ClientImpl(config)) {
            assertFalse(config.endpoint().isPresent());
            assertEquals("cn-shanghai", client.options.region());
            assertNotNull(client.options.endpoint());
            assertEquals("oss-cn-shanghai-internal.aliyuncs.com", client.options.endpoint().getHost());
            assertEquals("https", client.options.endpoint().getScheme());
        }

        // accelerate
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .useAccelerateEndpoint(true)
                .build();
        try (ClientImpl client = new ClientImpl(config)) {
            assertFalse(config.endpoint().isPresent());
            assertEquals("cn-hangzhou", client.options.region());
            assertNotNull(client.options.endpoint());
            assertEquals("oss-accelerate.aliyuncs.com", client.options.endpoint().getHost());
            assertEquals("https", client.options.endpoint().getScheme());
        }

        // dual stack
        config = ClientConfiguration.defaultBuilder()
                .region("cn-shenzhen")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .useDualStackEndpoint(true)
                .build();
        try (ClientImpl client = new ClientImpl(config)) {
            assertFalse(config.endpoint().isPresent());
            assertEquals("cn-shenzhen", client.options.region());
            assertNotNull(client.options.endpoint());
            assertEquals("cn-shenzhen.oss.aliyuncs.com", client.options.endpoint().getHost());
            assertEquals("https", client.options.endpoint().getScheme());
        }

        // set endpoint
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .endpoint("http://oss-cn-shenzhen.aliyuncs.com")
                .build();
        try (ClientImpl client = new ClientImpl(config)) {
            assertTrue(config.endpoint().isPresent());
            assertEquals("cn-hangzhou", client.options.region());
            assertNotNull(client.options.endpoint());
            assertEquals("oss-cn-shenzhen.aliyuncs.com", client.options.endpoint().getHost());
            assertEquals("http", client.options.endpoint().getScheme());
        }

        // disable ssl
        config = ClientConfiguration.defaultBuilder()
                .region("cn-shanghai")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .useInternalEndpoint(true)
                .disableSsl(true)
                .build();
        try (ClientImpl client = new ClientImpl(config)) {
            assertFalse(config.endpoint().isPresent());
            assertEquals("cn-shanghai", client.options.region());
            assertNotNull(client.options.endpoint());
            assertEquals("oss-cn-shanghai-internal.aliyuncs.com", client.options.endpoint().getHost());
            assertEquals("http", client.options.endpoint().getScheme());
        }
    }

    @Test
    public void configAddressStyle() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        // default
        try (ClientImpl client = new ClientImpl(config)) {
            assertFalse(config.endpoint().isPresent());
            assertNotNull(client.options.endpoint());
            assertEquals(AddressStyleType.VirtualHosted, client.options.addressStyle());
        }

        // cname
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .useCName(true)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            assertFalse(config.endpoint().isPresent());
            assertNotNull(client.options.endpoint());
            assertEquals(AddressStyleType.CName, client.options.addressStyle());
        }

        // path-style
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .usePathStyle(true)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            assertFalse(config.endpoint().isPresent());
            assertNotNull(client.options.endpoint());
            assertEquals(AddressStyleType.Path, client.options.addressStyle());
        }

        // ip endpoint
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .endpoint("http://127.0.0.1")
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            assertTrue(config.endpoint().isPresent());
            assertNotNull(client.options.endpoint());
            assertEquals(AddressStyleType.Path, client.options.addressStyle());
            assertEquals("127.0.0.1", client.options.endpoint().getHost());
            assertEquals("http", client.options.endpoint().getScheme());
        }
    }

    @Test
    public void configAuthMethod() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        // default
        try (ClientImpl client = new ClientImpl(config)) {
            assertFalse(config.endpoint().isPresent());
            assertNotNull(client.options.endpoint());
            assertEquals(AuthMethodType.Header, client.options.authMethod());
        }

        // auth query
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        try (ClientImpl client = new ClientImpl(config,
                Collections.singletonList(
                        opt -> opt.toBuilder().authMethod(AuthMethodType.Query).build())
        )) {
            assertFalse(config.endpoint().isPresent());
            assertNotNull(client.options.endpoint());
            assertEquals(AuthMethodType.Query, client.options.authMethod());
        }
    }

    @Test
    public void configProduct() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        // default
        try (ClientImpl client = new ClientImpl(config)) {
            assertEquals("oss", client.options.product());
        }

        // product
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        List<Function<ClientOptions, ClientOptions>> optFnsList = new ArrayList<>();
        optFnsList.add(opt -> opt.toBuilder().product("oss-cloudbox").build());
        try (ClientImpl client = new ClientImpl(config, optFnsList)) {
            assertEquals("oss-cloudbox", client.options.product());
        }
    }

    @Test
    public void configRetryer() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        // default
        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.options.retryer()).isInstanceOf(StandardRetryer.class);
            assertEquals(Defaults.MAX_ATTEMPTS, client.options.retryer().maxAttempts());
        }

        // set retryer
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .retryer(new NopRetryer())
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.options.retryer()).isInstanceOf(NopRetryer.class);
            assertEquals(1, client.options.retryer().maxAttempts());
        }

        // set MaxAttempts in retryer
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .retryer(StandardRetryer.newBuilder().maxAttempts(5).build())
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.options.retryer()).isInstanceOf(StandardRetryer.class);
            assertEquals(5, client.options.retryer().maxAttempts());
        }

        // set MaxAttempts in configuration
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .retryMaxAttempts(6)
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.options.retryer()).isInstanceOf(StandardRetryer.class);
            assertEquals(6, client.options.retryer().maxAttempts());
        }
    }

    @Test
    public void configTimeout() {
    }

    @Test
    public void configHttpClient() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        // default
        try (ClientImpl client = new ClientImpl(config)) {
            assertNotNull(client.options.httpClient());
        }

        // TODO
    }

    @Test
    public void configProxyHost() {
        // TODO
    }

    @Test
    public void configUserAgent() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        // default
        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.innerOptions.getUserAgent()).startsWith("alibabacloud-java-sdk-v2/0.1");
        }

        // set MaxAttempts in configuration
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .userAgent("my-agent")
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.innerOptions.getUserAgent()).startsWith("alibabacloud-java-sdk-v2/0.1");
            assertThat(client.innerOptions.getUserAgent()).endsWith("/my-agent");
        }
    }

    @Test
    public void configCrcCheck() {
    }

    @Test
    public void configAdditionalHeaders() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        // default
        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.options.additionalHeaders()).hasSize(0);
        }

        //set values
        config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .additionalHeaders(Arrays.asList("host", "content-length"))
                .build();

        try (ClientImpl client = new ClientImpl(config)) {
            assertThat(client.options.additionalHeaders()).hasSize(2);
            assertThat(client.options.additionalHeaders()).contains("host", "content-length");
        }
    }
}
