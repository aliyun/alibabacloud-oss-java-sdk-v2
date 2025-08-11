package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache4client.Apache4HttpClientBuilder;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5HttpClientBuilder;

/**
 * Internal implementation of {@link OSSClientBuilder}.
 */
class DefaultOSSClientBuilder extends DefaultBaseClientBuilder<OSSClientBuilder, OSSClient> implements OSSClientBuilder {

    private boolean useApacheHttpClient4;

    DefaultOSSClientBuilder() {
        super();
        this.useApacheHttpClient4 = false;
    }

    @Override
    protected OSSClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();
        return new DefaultOSSClient(config);
    }

    @Override
    public OSSClientBuilder useApacheHttpClient4(boolean value) {
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
}
