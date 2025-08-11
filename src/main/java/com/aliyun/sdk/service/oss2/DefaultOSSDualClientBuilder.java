package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5AsyncHttpClientBuilder;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5HttpClientBuilder;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5MixedHttpClient;

/**
 * Internal implementation of {@link OSSDualClientBuilder}.
 */
class DefaultOSSDualClientBuilder extends DefaultBaseClientBuilder<OSSDualClientBuilder, OSSDualClient> implements OSSDualClientBuilder {

    @Override
    protected OSSDualClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();
        return new DefaultOSSDualClient(config);
    }

    private HttpClient determineHttpClient() {
        if (this.httpClient != null) {
            return this.httpClient;
        }
        HttpClientOptions hcOpt = toHttpClientOptions(cfgBuilder.build());
        return new Apache5MixedHttpClient(
                Apache5HttpClientBuilder.create().options(hcOpt).build(),
                Apache5AsyncHttpClientBuilder.create().options(hcOpt).build()
        );
    }
}
