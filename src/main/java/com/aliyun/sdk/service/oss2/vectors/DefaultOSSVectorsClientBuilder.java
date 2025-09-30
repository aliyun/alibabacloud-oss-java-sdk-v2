package com.aliyun.sdk.service.oss2.vectors;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.internal.Ensure;
import com.aliyun.sdk.service.oss2.signer.VectorsSignerV4;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache4client.Apache4HttpClientBuilder;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5HttpClientBuilder;
import com.aliyun.sdk.service.oss2.types.EndpointProvider;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Internal implementation of {@link OSSVectorsClientBuilder}.
 */
class DefaultOSSVectorsClientBuilder extends DefaultBaseClientBuilder<OSSVectorsClientBuilder, OSSVectorsClient> implements OSSVectorsClientBuilder {

    private boolean useApacheHttpClient4;

    DefaultOSSVectorsClientBuilder() {
        super();
        this.useApacheHttpClient4 = false;
    }

    @Override
    protected OSSVectorsClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();
        config = updateEndpoint(config);
        config = updateSinger(config);
        config = updateUserAgent(config);
        final String accountId = config.accountId().orElse("");
        return new DefaultOSSVectorsClient(config,
                x -> x.toBuilder()
                        .endpointProvider(new VectorsEndpointProvider(x.endpoint(), accountId))
                        .build());
    }

    @Override
    public OSSVectorsClientBuilder useApacheHttpClient4(boolean value) {
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

    static ClientConfiguration updateEndpoint(ClientConfiguration config) {
        if (config.endpoint().isPresent()) {
            return config;
        }

        if (!config.region().isPresent()) {
            return config;
        }

        String value = config.region().get();

        if (!Ensure.isValidRegion(value)) {
            return config;
        }

        if (config.useInternalEndpoint().orElse(false)) {
            value = String.format("%s-internal.oss-vectors.aliyuncs.com", value);
        } else {
            value = String.format("%s.oss-vectors.aliyuncs.com", value);
        }

        return config.toBuilder().endpoint(value).build();
    }

    static ClientConfiguration updateSinger(ClientConfiguration config) {
        return config.toBuilder().signer(new VectorsSignerV4(config.accountId().orElse(null))).build();
    }

    static ClientConfiguration updateUserAgent(ClientConfiguration config) {
        String userAgent = "vectors-client";
        if (config.userAgent().isPresent()) {
            userAgent += "/" + config.userAgent().get();
        }
        return config.toBuilder().userAgent(userAgent).build();
    }

    static class VectorsEndpointProvider implements EndpointProvider {
        private final URI endpoint;
        private final String accountId;

        VectorsEndpointProvider(URI endpoint, String accountId) {
            this.endpoint = endpoint;
            this.accountId = accountId;
        }

        @Override
        public String buildURL(OperationInput input) {
            List<String> paths = new ArrayList<>();
            String host = this.endpoint.getAuthority();

            if (input.bucket().isPresent()) {
                host = String.format("%s-%s.%s", input.bucket().get(), this.accountId, host);
            }

            if (input.key().isPresent()) {
                paths.add(HttpUtils.urlEncodePath(input.key().get()));
            }

            return String.format("%s://%s/%s",
                    this.endpoint.getScheme(),
                    host,
                    String.join("/", paths));
        }
    }
}
