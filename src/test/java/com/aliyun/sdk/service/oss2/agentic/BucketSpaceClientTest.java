package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.types.AddressStyleType;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class BucketSpaceClientTest {

    @Test
    public void testBucketSpaceProviderBuildBucketName() {
        AgenticProvider provider = new AgenticProvider(
                URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                "1234567890",
                "cn-hangzhou",
                "bs-apsr",
                AddressStyleType.VirtualHosted
        );

        OperationInput input = OperationInput.newBuilder()
                .bucket("my-space")
                .build();

        String result = provider.buildBucketName(input);
        assertThat(result).isEqualTo("my-space-1234567890-cn-hangzhou-bs-apsr");
    }

    @Test
    public void testBucketSpaceProviderBuildBucketNameNoBucket() {
        AgenticProvider provider = new AgenticProvider(
                URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                "1234567890",
                "cn-hangzhou",
                "bs-apsr",
                AddressStyleType.VirtualHosted
        );

        OperationInput input = OperationInput.newBuilder().build();

        String result = provider.buildBucketName(input);
        assertThat(result).isNull();
    }

    @Test
    public void testBucketSpaceProviderBuildURL() {
        AgenticProvider provider = new AgenticProvider(
                URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                "1234567890",
                "cn-hangzhou",
                "bs-apsr",
                AddressStyleType.VirtualHosted
        );

        OperationInput input = OperationInput.newBuilder()
                .bucket("my-space")
                .build();

        String url = provider.buildURL(input);
        assertThat(url).isEqualTo("https://my-space-1234567890-cn-hangzhou-bs-apsr.oss-cn-hangzhou.aliyuncs.com/");
    }

    @Test
    public void testBucketSpaceProviderBuildURLWithKey() {
        AgenticProvider provider = new AgenticProvider(
                URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                "1234567890",
                "cn-hangzhou",
                "bs-apsr",
                AddressStyleType.VirtualHosted
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
        AgenticProvider provider = new AgenticProvider(
                URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                "1234567890",
                "cn-hangzhou",
                "bs-apsr",
                AddressStyleType.VirtualHosted
        );

        OperationInput input = OperationInput.newBuilder().build();

        String url = provider.buildURL(input);
        assertThat(url).isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/");
    }

    @Test
    public void testBucketSpaceProviderBuildURLPathStyle() {
        AgenticProvider provider = new AgenticProvider(
                URI.create("https://oss-cn-hangzhou.aliyuncs.com"),
                "1234567890",
                "cn-hangzhou",
                "bs-apsr",
                AddressStyleType.Path
        );

        // Under path-style the physical space name lives in the path, host stays bare.
        OperationInput input = OperationInput.newBuilder()
                .bucket("my-space")
                .key("test-object.txt")
                .build();
        assertThat(provider.buildURL(input))
                .isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/my-space-1234567890-cn-hangzhou-bs-apsr/test-object.txt");

        // No bucket still routes to the bare endpoint.
        input = OperationInput.newBuilder().build();
        assertThat(provider.buildURL(input))
                .isEqualTo("https://oss-cn-hangzhou.aliyuncs.com/");
    }
}
