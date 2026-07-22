package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache4client.Apache4HttpClientBuilder;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5HttpClientBuilder;
import com.aliyun.sdk.service.oss2.types.AddressStyleType;
import com.aliyun.sdk.service.oss2.types.BucketNameResolver;
import com.aliyun.sdk.service.oss2.types.EndpointProvider;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class DefaultOSSAgenticBucketClientBuilder extends DefaultBaseClientBuilder<OSSAgenticBucketClientBuilder, OSSAgenticBucketClient> implements OSSAgenticBucketClientBuilder {

    private boolean useApacheHttpClient4;

    DefaultOSSAgenticBucketClientBuilder() {
        super();
        this.useApacheHttpClient4 = false;
    }

    @Override
    protected OSSAgenticBucketClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();
        config = updateUserAgent(config);
        final String accountId = config.accountId().orElse("");
        final String region = config.region().orElse("");

        return new DefaultOSSAgenticBucketClient(config,
                x -> {
                    AgenticProvider provider = new AgenticProvider(
                            x.endpoint(), accountId, region, "ab-apsr",
                            x.addressStyle());
                    return x.toBuilder()
                            .endpointProvider(provider)
                            .bucketNameResolver(provider)
                            .build();
                });
    }

    @Override
    public OSSAgenticBucketClientBuilder useApacheHttpClient4(boolean value) {
        this.useApacheHttpClient4 = value;
        return this;
    }

    private HttpClient determineHttpClient() {
        if (this.httpClient != null) {
            return this.httpClient;
        }
        HttpClientOptions hcOpt = toHttpClientOptions(cfgBuilder.build());
        if (this.useApacheHttpClient4) {
            return Apache4HttpClientBuilder.create().options(hcOpt).build();
        }
        return Apache5HttpClientBuilder.create().options(hcOpt).build();
    }

    static ClientConfiguration updateUserAgent(ClientConfiguration config) {
        String userAgent = "agentic-client";
        if (config.userAgent().isPresent()) {
            userAgent += "/" + config.userAgent().get();
        }
        return config.toBuilder().userAgent(userAgent).build();
    }

    static class AgenticProvider implements EndpointProvider, BucketNameResolver {
        private final URI endpoint;
        private final String accountId;
        private final String region;
        private final String suffix;
        private final AddressStyleType addressStyle;

        AgenticProvider(URI endpoint, String accountId, String region, String suffix, AddressStyleType addressStyle) {
            this.endpoint = endpoint;
            this.accountId = accountId;
            this.region = region;
            this.suffix = suffix;
            this.addressStyle = addressStyle;
        }

        @Override
        public String buildBucketName(OperationInput input) {
            if (!input.bucket().isPresent()) return null;
            String prefix = input.bucket().get();
            return String.format("%s-%s-%s-%s", prefix, accountId, region, suffix);
        }

        @Override
        public String buildURL(OperationInput input) {
            List<String> paths = new ArrayList<>();
            String host = endpoint.getAuthority();
            if (input.bucket().isPresent()) {
                switch (addressStyle) {
                    case Path:
                        paths.add(buildBucketName(input));
                        if (!input.key().isPresent()) {
                            paths.add("");
                        }
                        break;
                    default:
                        host = String.format("%s.%s", buildBucketName(input), endpoint.getAuthority());
                        break;
                }
            }
            if (input.key().isPresent()) {
                paths.add(HttpUtils.urlEncodePath(input.key().get()));
            }
            return String.format("%s://%s/%s", endpoint.getScheme(), host, String.join("/", paths));
        }
    }
}
