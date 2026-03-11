package com.aliyun.sdk.service.oss2.dataprocess;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.DefaultBaseClientBuilder;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5AsyncHttpClientBuilder;

/**
 * Internal implementation of {@link OSSDataProcessAsyncClientBuilder}.
 */
class DefaultOSSDataProcessAsyncClientBuilder extends DefaultBaseClientBuilder<OSSDataProcessAsyncClientBuilder, OSSDataProcessAsyncClient> implements OSSDataProcessAsyncClientBuilder {

    @Override
    protected OSSDataProcessAsyncClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();
        config = DefaultOSSDataProcessClientBuilder.updateUserAgent(config);
        return new DefaultOSSDataProcessAsyncClient(config);
    }

    private HttpClient determineHttpClient() {
        if (this.httpClient != null) {
            return this.httpClient;
        }
        HttpClientOptions hcOpt = toHttpClientOptions(cfgBuilder.build());
        return Apache5AsyncHttpClientBuilder.create().options(hcOpt).build();
    }
}
