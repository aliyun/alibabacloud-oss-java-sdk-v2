package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.DefaultOSSAsyncClient;
import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class AsyncBucketSpaceClientTest {

    @Test
    public void testCreateReturnsAsyncClient() throws Exception {
        ClientConfiguration config = ClientConfiguration.defaultBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .accountId("1234567890")
                .build();

        try (OSSAsyncClient client = AsyncBucketSpaceClient.create(config)) {
            assertThat(client).isNotNull();
            assertThat(client).isInstanceOf(DefaultOSSAsyncClient.class);
        }
    }

    @Test
    public void testBucketSpaceProviderBuildBucketName() {
        BucketSpaceClient.BucketSpaceProvider provider = new BucketSpaceClient.BucketSpaceProvider(
                URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                "1234567890",
                "cn-hangzhou"
        );

        OperationInput input = OperationInput.newBuilder()
                .bucket("my-space")
                .build();

        String result = provider.buildBucketName(input);
        assertThat(result).isEqualTo("my-space-1234567890-cn-hangzhou-bs-apsr");
    }

    @Test
    public void testBucketSpaceProviderBuildBucketNameNoBucket() {
        BucketSpaceClient.BucketSpaceProvider provider = new BucketSpaceClient.BucketSpaceProvider(
                URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                "1234567890",
                "cn-hangzhou"
        );

        OperationInput input = OperationInput.newBuilder().build();

        String result = provider.buildBucketName(input);
        assertThat(result).isNull();
    }

    @Test
    public void testBucketSpaceProviderBuildURL() {
        BucketSpaceClient.BucketSpaceProvider provider = new BucketSpaceClient.BucketSpaceProvider(
                URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                "1234567890",
                "cn-hangzhou"
        );

        OperationInput input = OperationInput.newBuilder()
                .bucket("my-space")
                .build();

        String url = provider.buildURL(input);
        assertThat(url).isEqualTo("https://my-space-1234567890-cn-hangzhou-bs-apsr.oss-cn-hangzhou.aliyuncs.com/");
    }

    @Test
    public void testBucketSpaceProviderBuildURLWithKey() {
        BucketSpaceClient.BucketSpaceProvider provider = new BucketSpaceClient.BucketSpaceProvider(
                URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                "1234567890",
                "cn-hangzhou"
        );

        OperationInput input = OperationInput.newBuilder()
                .bucket("my-space")
                .key("test-object.txt")
                .build();

        String url = provider.buildURL(input);
        assertThat(url).isEqualTo("https://my-space-1234567890-cn-hangzhou-bs-apsr.oss-cn-hangzhou.aliyuncs.com/test-object.txt");
    }

    @Test
    public void testBucketSpaceProviderBuildURLNoBucket() {
        BucketSpaceClient.BucketSpaceProvider provider = new BucketSpaceClient.BucketSpaceProvider(
                URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                "1234567890",
                "cn-hangzhou"
        );

        OperationInput input = OperationInput.newBuilder().build();

        String url = provider.buildURL(input);
        assertThat(url).isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/");
    }
}
