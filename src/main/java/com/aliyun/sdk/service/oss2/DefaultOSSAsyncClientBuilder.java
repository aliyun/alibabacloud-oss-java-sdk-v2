package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5AsyncHttpClientBuilder;

/**
 * Internal implementation of {@link OSSAsyncClientBuilder}.
 */
class DefaultOSSAsyncClientBuilder extends DefaultBaseClientBuilder<OSSAsyncClientBuilder, OSSAsyncClient> implements OSSAsyncClientBuilder {

    @Override
    protected OSSAsyncClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();
        return new DefaultOSSAsyncClient(config);
    }

    private HttpClient determineHttpClient() {
        if (this.httpClient != null) {
            return this.httpClient;
        }
        HttpClientOptions hcOpt = toHttpClientOptions(cfgBuilder.build());
        return Apache5AsyncHttpClientBuilder.create().options(hcOpt).build();
    }
}
