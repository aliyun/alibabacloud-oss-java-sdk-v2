package com.aliyun.sdk.service.oss2.imm;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.DefaultBaseClientBuilder;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache4client.Apache4HttpClientBuilder;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5HttpClientBuilder;

/**
 * Internal implementation of {@link IMMClientBuilder}.
 */
class DefaultIMMClientBuilder extends DefaultBaseClientBuilder<IMMClientBuilder, IMMClient> implements IMMClientBuilder {

    private boolean useApacheHttpClient4;

    DefaultIMMClientBuilder() {
        super();
        this.useApacheHttpClient4 = false;
    }

    @Override
    protected IMMClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();
        config = updateUserAgent(config);
        return new DefaultIMMClient(config);
    }

    @Override
    public IMMClientBuilder useApacheHttpClient4(boolean value) {
        this.useApacheHttpClient4 = value;
        return this;
    }

    static ClientConfiguration updateUserAgent(ClientConfiguration config) {
        String userAgent = "imm-client";
        if (config.userAgent().isPresent()) {
            userAgent += "/" + config.userAgent().get();
        }
        return config.toBuilder().userAgent(userAgent).build();
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
