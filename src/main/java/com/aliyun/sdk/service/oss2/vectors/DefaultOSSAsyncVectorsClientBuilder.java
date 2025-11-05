package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.DefaultBaseClientBuilder;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5AsyncHttpClientBuilder;

/**
 * Internal implementation of {@link OSSAsyncVectorsClientBuilder}.
 */
class DefaultOSSAsyncVectorsClientBuilder extends DefaultBaseClientBuilder<OSSAsyncVectorsClientBuilder, OSSAsyncVectorsClient> implements OSSAsyncVectorsClientBuilder {

    @Override
    protected OSSAsyncVectorsClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();

        config = DefaultOSSVectorsClientBuilder.updateEndpoint(config);
        config = DefaultOSSVectorsClientBuilder.updateSinger(config);
        config = DefaultOSSVectorsClientBuilder.updateUserAgent(config);

        final String accountId = config.accountId().orElse("");
        return new DefaultOSSAsyncVectorsClient(config,
                x -> x.toBuilder()
                        .endpointProvider(new DefaultOSSVectorsClientBuilder.VectorsEndpointProvider(x.endpoint(), accountId))
                        .build());
    }

    private HttpClient determineHttpClient() {
        if (this.httpClient != null) {
            return this.httpClient;
        }
        HttpClientOptions hcOpt = toHttpClientOptions(cfgBuilder.build());
        return Apache5AsyncHttpClientBuilder.create().options(hcOpt).build();
    }
}
