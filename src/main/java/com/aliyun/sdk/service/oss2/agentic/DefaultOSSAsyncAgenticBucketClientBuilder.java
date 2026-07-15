package com.aliyun.sdk.service.oss2.agentic;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.DefaultBaseClientBuilder;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5AsyncHttpClientBuilder;

class DefaultOSSAsyncAgenticBucketClientBuilder extends DefaultBaseClientBuilder<OSSAsyncAgenticBucketClientBuilder, OSSAsyncAgenticBucketClient> implements OSSAsyncAgenticBucketClientBuilder {

    @Override
    protected OSSAsyncAgenticBucketClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();

        config = DefaultOSSAgenticBucketClientBuilder.updateUserAgent(config);

        final String accountId = config.accountId().orElse("");
        final String region = config.region().orElse("");

        return new DefaultOSSAsyncAgenticBucketClient(config,
                x -> {
                    DefaultOSSAgenticBucketClientBuilder.AgenticProvider provider =
                            new DefaultOSSAgenticBucketClientBuilder.AgenticProvider(
                                    x.endpoint(), accountId, region, "ab-apsr");
                    return x.toBuilder()
                            .endpointProvider(provider)
                            .bucketNameResolver(provider)
                            .build();
                });
    }

    private HttpClient determineHttpClient() {
        if (this.httpClient != null) {
            return this.httpClient;
        }
        HttpClientOptions hcOpt = toHttpClientOptions(cfgBuilder.build());
        return Apache5AsyncHttpClientBuilder.create().options(hcOpt).build();
    }
}
