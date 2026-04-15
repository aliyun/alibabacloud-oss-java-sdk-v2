package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.DefaultBaseClientBuilder;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5AsyncHttpClientBuilder;

/**
 * Internal implementation of {@link OSSAsyncTablesClientBuilder}.
 */
class DefaultOSSAsyncTablesClientBuilder extends DefaultBaseClientBuilder<OSSAsyncTablesClientBuilder, OSSAsyncTablesClient> implements OSSAsyncTablesClientBuilder {

    @Override
    protected OSSAsyncTablesClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();

        config = DefaultOSSTablesClientBuilder.updateEndpoint(config);
        config = DefaultOSSTablesClientBuilder.updateSigner(config);
        config = DefaultOSSTablesClientBuilder.updateUserAgent(config);

        return new DefaultOSSAsyncTablesClient(config,
                x -> x.toBuilder()
                        .endpointProvider(new DefaultOSSTablesClientBuilder.TablesEndpointProvider(x.endpoint(), x.addressStyle()))
                        .product("osstables")
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
