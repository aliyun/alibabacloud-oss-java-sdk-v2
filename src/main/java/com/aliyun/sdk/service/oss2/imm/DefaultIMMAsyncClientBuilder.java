package com.aliyun.sdk.service.oss2.imm;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.DefaultBaseClientBuilder;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5AsyncHttpClientBuilder;

/**
 * Internal implementation of {@link IMMAsyncClientBuilder}.
 */
class DefaultIMMAsyncClientBuilder extends DefaultBaseClientBuilder<IMMAsyncClientBuilder, IMMAsyncClient> implements IMMAsyncClientBuilder {

    @Override
    protected IMMAsyncClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();
        config = DefaultIMMClientBuilder.updateUserAgent(config);
        return new DefaultIMMAsyncClient(config);
    }

    private HttpClient determineHttpClient() {
        if (this.httpClient != null) {
            return this.httpClient;
        }
        HttpClientOptions hcOpt = toHttpClientOptions(cfgBuilder.build());
        return Apache5AsyncHttpClientBuilder.create().options(hcOpt).build();
    }
}
