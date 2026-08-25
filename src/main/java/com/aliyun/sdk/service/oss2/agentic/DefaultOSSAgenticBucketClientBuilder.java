package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache4client.Apache4HttpClientBuilder;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5HttpClientBuilder;

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
}
