package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.ClientConfiguration;
import com.aliyun.sdk.service.oss2.DefaultBaseClientBuilder;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.internal.Ensure;
import com.aliyun.sdk.service.oss2.signer.TablesSignerV4;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache4client.Apache4HttpClientBuilder;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5HttpClientBuilder;
import com.aliyun.sdk.service.oss2.types.AddressStyleType;
import com.aliyun.sdk.service.oss2.types.EndpointProvider;

import java.net.URI;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Internal implementation of {@link OSSTablesClientBuilder}.
 */
class DefaultOSSTablesClientBuilder extends DefaultBaseClientBuilder<OSSTablesClientBuilder, OSSTablesClient> implements OSSTablesClientBuilder {

    private boolean useApacheHttpClient4;

    DefaultOSSTablesClientBuilder() {
        super();
        this.useApacheHttpClient4 = false;
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
            value = String.format("%s-internal.oss-tables.aliyuncs.com", value);
        } else {
            value = String.format("%s.oss-tables.aliyuncs.com", value);
        }

        return config.toBuilder().endpoint(value).build();
    }

    static ClientConfiguration updateSigner(ClientConfiguration config) {
        return config.toBuilder().signer(new TablesSignerV4()).build();
    }

    static ClientConfiguration updateUserAgent(ClientConfiguration config) {
        String userAgent = "tables-client";
        if (config.userAgent().isPresent()) {
            userAgent += "/" + config.userAgent().get();
        }
        return config.toBuilder().userAgent(userAgent).build();
    }

    @Override
    protected OSSTablesClient buildClient() {
        ClientConfiguration config = cfgBuilder
                .httpClient(determineHttpClient())
                .build();
        config = updateEndpoint(config);
        config = updateSigner(config);
        config = updateUserAgent(config);
        
        return new DefaultOSSTablesClient(config,
                x -> x.toBuilder()
                        .endpointProvider(new TablesEndpointProvider(x.endpoint(), x.addressStyle()))
                        .product("osstables")
                        .build());
    }

    @Override
    public OSSTablesClientBuilder useApacheHttpClient4(boolean value) {
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

    static class TablesEndpointProvider implements EndpointProvider {
        private final URI endpoint;
        private final AddressStyleType addressStyleType;

        TablesEndpointProvider(URI endpoint, AddressStyleType addressStyleType) {
            this.endpoint = endpoint;
            this.addressStyleType = addressStyleType;
        }

        @Override
        public String buildURL(OperationInput input) {
            List<String> paths = new ArrayList<>();
            String host = this.endpoint.getAuthority();

            if (input.bucket().isPresent()) {
                switch (this.addressStyleType) {
                    case VirtualHosted:
                        String[] vhVals1 = input.bucket().get().split(":");
                        if (vhVals1.length != 5) {
                            throw new InvalidParameterException("input.bucket is not bucket arn");
                        }
                        String[] vhVals2 = vhVals1[4].split("/");
                        if (vhVals2.length != 2) {
                            throw new InvalidParameterException("input.bucket is not bucket arn");
                        }
                        host = String.format("%s-%s.%s", vhVals2[1], vhVals1[3], host);
                        break;
                    default://case Path:
                        break;
                }
            }

            if (input.key().isPresent()) {
                paths.add(input.key().get());
            }

            return String.format("%s://%s/%s",
                    this.endpoint.getScheme(),
                    host,
                    String.join("/", paths));
        }
    }
}
