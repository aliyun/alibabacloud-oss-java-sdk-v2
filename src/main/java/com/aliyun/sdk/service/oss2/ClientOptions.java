package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.retry.Retryer;
import com.aliyun.sdk.service.oss2.signer.Signer;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.types.AddressStyleType;
import com.aliyun.sdk.service.oss2.types.AuthMethodType;

import java.net.URI;
import java.util.List;

public class ClientOptions {
    private final String product;
    private final String region;
    private final URI endpoint;
    private final Signer signer;
    private final Retryer retryer;
    private final CredentialsProvider credentialsProvider;
    private final AddressStyleType addressStyle;
    private final AuthMethodType authMethod;
    private final HttpClient httpClient;
    private final List<String> additionalHeaders;
    private final int featureFlags;

    private ClientOptions(Builder builder) {
        this.product = builder.product;
        this.region = builder.region;
        this.endpoint = builder.endpoint;
        this.signer = builder.signer;
        this.retryer = builder.retryer;
        this.credentialsProvider = builder.credentialsProvider;
        this.addressStyle = builder.addressStyle;
        this.authMethod = builder.authMethod;
        this.httpClient = builder.httpClient;
        this.additionalHeaders = builder.additionalHeaders;
        this.featureFlags = builder.featureFlags;
    }

    public String product() {
        return product;
    }

    public String region() {
        return region;
    }

    public URI endpoint() {
        return endpoint;
    }

    public Signer signer() {
        return signer;
    }

    public Retryer retryer() {
        return retryer;
    }

    public CredentialsProvider credentialsProvider() {
        return credentialsProvider;
    }

    public AddressStyleType addressStyle() {
        return addressStyle;
    }

    public AuthMethodType authMethod() {
        return authMethod;
    }

    public HttpClient httpClient() {
        return httpClient;
    }

    public List<String> additionalHeaders() {
        return additionalHeaders;
    }

    public int featureFlags() {
        return featureFlags;
    }

    public Builder toBuilder() {
        return new Builder()
                .product(this.product)
                .region(this.region)
                .endpoint(this.endpoint)
                .signer(this.signer)
                .retryer(this.retryer)
                .credentialsProvider(this.credentialsProvider)
                .addressStyle(this.addressStyle)
                .authMethod(this.authMethod)
                .httpClient(this.httpClient)
                .additionalHeaders(this.additionalHeaders)
                .featureFlags(this.featureFlags);
    }

    public static class Builder {
        private String product;
        private String region;
        private URI endpoint;
        private Signer signer;
        private Retryer retryer;
        private CredentialsProvider credentialsProvider;
        private AddressStyleType addressStyle;
        private AuthMethodType authMethod;
        private HttpClient httpClient;
        private List<String> additionalHeaders;
        private int featureFlags;

        public Builder product(String product) {
            this.product = product;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder endpoint(URI endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public Builder signer(Signer signer) {
            this.signer = signer;
            return this;
        }

        public Builder retryer(Retryer retryer) {
            this.retryer = retryer;
            return this;
        }

        public Builder credentialsProvider(CredentialsProvider credentialsProvider) {
            this.credentialsProvider = credentialsProvider;
            return this;
        }

        public Builder addressStyle(AddressStyleType addressStyle) {
            this.addressStyle = addressStyle;
            return this;
        }

        public Builder authMethod(AuthMethodType authMethod) {
            this.authMethod = authMethod;
            return this;
        }

        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder additionalHeaders(List<String> additionalHeaders) {
            this.additionalHeaders = additionalHeaders;
            return this;
        }

        public Builder featureFlags(int value) {
            this.featureFlags = value;
            return this;
        }

        public ClientOptions build() {
            return new ClientOptions(this);
        }
    }

}
