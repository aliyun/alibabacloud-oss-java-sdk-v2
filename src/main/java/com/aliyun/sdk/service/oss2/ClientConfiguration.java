package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.retry.Retryer;
import com.aliyun.sdk.service.oss2.signer.Signer;
import com.aliyun.sdk.service.oss2.transport.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public final class ClientConfiguration {
    private final String region;
    private final String endpoint;
    private final Signer signer;
    private final Integer retryMaxAttempts;
    private final Retryer retryer;
    private final CredentialsProvider credentialsProvider;
    private final String signatureVersion;
    private final Boolean disableSsl;
    private final Boolean useDualStackEndpoint;
    private final Boolean useInternalEndpoint;
    private final Boolean useAccelerateEndpoint;
    private final Boolean useCName;
    private final Boolean usePathStyle;
    private final HttpClient httpClient;
    private final List<String> additionalHeaders;
    private final String userAgent;
    private final Duration connectTimeout;
    private final Duration readWriteTimeout;
    private final Boolean insecureSkipVerify;
    private final Boolean enabledRedirect;
    private final String proxyHost;
    private final Boolean disableUploadCRC64Check;
    private final String accountId;


    private ClientConfiguration(Builder builder) {
        this.region = builder.region;
        this.endpoint = builder.endpoint;
        this.signer = builder.signer;
        this.retryer = builder.retryer;
        this.retryMaxAttempts = builder.retryMaxAttempts;
        this.credentialsProvider = builder.credentialsProvider;
        this.signatureVersion = builder.signatureVersion;
        this.disableSsl = builder.disableSsl;
        this.useDualStackEndpoint = builder.useDualStackEndpoint;
        this.useInternalEndpoint = builder.useInternalEndpoint;
        this.useAccelerateEndpoint = builder.useAccelerateEndpoint;
        this.useCName = builder.useCName;
        this.usePathStyle = builder.usePathStyle;
        this.httpClient = builder.httpClient;
        this.additionalHeaders = builder.additionalHeaders;
        this.userAgent = builder.userAgent;
        this.connectTimeout = builder.connectTimeout;
        this.readWriteTimeout = builder.readWriteTimeout;
        this.insecureSkipVerify = builder.insecureSkipVerify;
        this.enabledRedirect = builder.enabledRedirect;
        this.proxyHost = builder.proxyHost;
        this.disableUploadCRC64Check = builder.disableUploadCRC64Check;
        this.accountId = builder.accountId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder defaultBuilder() {
        // TODO, set default values
        return new Builder();
    }

    public Optional<String> region() {
        return Optional.ofNullable(region);
    }

    public Optional<String> endpoint() {
        return Optional.ofNullable(endpoint);
    }

    public Optional<Signer> signer() {
        return Optional.ofNullable(signer);
    }

    public Optional<Integer> retryMaxAttempts() {
        return Optional.ofNullable(retryMaxAttempts);
    }

    public Optional<Retryer> retryer() {
        return Optional.ofNullable(retryer);
    }

    public Optional<CredentialsProvider> credentialsProvider() {
        return Optional.ofNullable(credentialsProvider);
    }

    public Optional<String> signatureVersion() {
        return Optional.ofNullable(signatureVersion);
    }

    public Optional<Boolean> disableSsl() {
        return Optional.ofNullable(disableSsl);
    }

    public Optional<Boolean> useDualStackEndpoint() {
        return Optional.ofNullable(useDualStackEndpoint);
    }

    public Optional<Boolean> useInternalEndpoint() {
        return Optional.ofNullable(useInternalEndpoint);
    }

    public Optional<Boolean> useAccelerateEndpoint() {
        return Optional.ofNullable(useAccelerateEndpoint);
    }

    public Optional<Boolean> useCName() {
        return Optional.ofNullable(useCName);
    }

    public Optional<Boolean> usePathStyle() {
        return Optional.ofNullable(usePathStyle);
    }

    public Optional<HttpClient> httpClient() {
        return Optional.ofNullable(httpClient);
    }

    public Optional<List<String>> additionalHeaders() {
        return Optional.ofNullable(additionalHeaders);
    }

    public Optional<String> userAgent() {
        return Optional.ofNullable(userAgent);
    }

    public Optional<Duration> connectTimeout() {
        return Optional.ofNullable(connectTimeout);
    }

    public Optional<Duration> readWriteTimeout() {
        return Optional.ofNullable(readWriteTimeout);
    }

    public Optional<Boolean> insecureSkipVerify() {
        return Optional.ofNullable(insecureSkipVerify);
    }

    public Optional<Boolean> enabledRedirect() {
        return Optional.ofNullable(enabledRedirect);
    }

    public Optional<String> proxyHost() {
        return Optional.ofNullable(proxyHost);
    }

    public Optional<Boolean> DisableUploadCRC64Check() {
        return Optional.ofNullable(disableUploadCRC64Check);
    }

    public Optional<String> accountId() {
        return Optional.ofNullable(accountId);
    }


    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String region;
        private String endpoint;
        private Signer signer;
        private Integer retryMaxAttempts;
        private Retryer retryer;
        private CredentialsProvider credentialsProvider;
        private String signatureVersion;
        private Boolean disableSsl;
        private Boolean useDualStackEndpoint;
        private Boolean useInternalEndpoint;
        private Boolean useAccelerateEndpoint;
        private Boolean useCName;
        private Boolean usePathStyle;
        private HttpClient httpClient;
        private List<String> additionalHeaders;
        private String userAgent;
        private Duration connectTimeout;
        private Duration readWriteTimeout;
        private Boolean insecureSkipVerify;
        private Boolean enabledRedirect;
        private String proxyHost;
        private Boolean disableUploadCRC64Check;
        private String accountId;

        protected Builder() {
        }

        protected Builder(ClientConfiguration from) {
            this.region = from.region;
            this.endpoint = from.endpoint;
            this.signer = from.signer;
            this.retryer = from.retryer;
            this.retryMaxAttempts = from.retryMaxAttempts;
            this.credentialsProvider = from.credentialsProvider;
            this.signatureVersion = from.signatureVersion;
            this.disableSsl = from.disableSsl;
            this.useDualStackEndpoint = from.useDualStackEndpoint;
            this.useInternalEndpoint = from.useInternalEndpoint;
            this.useAccelerateEndpoint = from.useAccelerateEndpoint;
            this.useCName = from.useCName;
            this.usePathStyle = from.usePathStyle;
            this.httpClient = from.httpClient;
            this.additionalHeaders = from.additionalHeaders;
            this.userAgent = from.userAgent;
            this.connectTimeout = from.connectTimeout;
            this.readWriteTimeout = from.readWriteTimeout;
            this.insecureSkipVerify = from.insecureSkipVerify;
            this.enabledRedirect = from.enabledRedirect;
            this.proxyHost = from.proxyHost;
            this.disableUploadCRC64Check = from.disableUploadCRC64Check;
            this.accountId = from.accountId;
        }

        public Builder region(String region) {
            requireNonNull(region);
            this.region = region;
            return this;
        }

        public Builder endpoint(String endpoint) {
            requireNonNull(endpoint);
            this.endpoint = endpoint;
            return this;
        }

        public Builder signer(Signer signer) {
            requireNonNull(signer);
            this.signer = signer;
            return this;
        }

        public Builder retryMaxAttempts(Integer retryMaxAttempts) {
            requireNonNull(retryMaxAttempts);
            this.retryMaxAttempts = retryMaxAttempts;
            return this;
        }

        public Builder retryer(Retryer retryer) {
            requireNonNull(retryer);
            this.retryer = retryer;
            return this;
        }

        public Builder credentialsProvider(CredentialsProvider credentialsProvider) {
            requireNonNull(credentialsProvider);
            this.credentialsProvider = credentialsProvider;
            return this;
        }

        public Builder signatureVersion(String signatureVersion) {
            requireNonNull(signatureVersion);
            this.signatureVersion = signatureVersion;
            return this;
        }

        public Builder disableSsl(Boolean value) {
            requireNonNull(value);
            this.disableSsl = value;
            return this;
        }

        public Builder useDualStackEndpoint(Boolean value) {
            requireNonNull(value);
            this.useDualStackEndpoint = value;
            return this;
        }

        public Builder useInternalEndpoint(Boolean value) {
            requireNonNull(value);
            this.useInternalEndpoint = value;
            return this;
        }

        public Builder useAccelerateEndpoint(Boolean value) {
            requireNonNull(value);
            this.useAccelerateEndpoint = value;
            return this;
        }

        public Builder useCName(Boolean value) {
            requireNonNull(value);
            this.useCName = value;
            return this;
        }

        public Builder usePathStyle(Boolean value) {
            requireNonNull(value);
            this.usePathStyle = value;
            return this;
        }

        public Builder httpClient(HttpClient value) {
            requireNonNull(value);
            this.httpClient = value;
            return this;
        }

        public Builder additionalHeaders(List<String> value) {
            requireNonNull(value);
            this.additionalHeaders = value;
            return this;
        }

        public Builder userAgent(String value) {
            requireNonNull(value);
            this.userAgent = value;
            return this;
        }

        public Builder connectTimeout(Duration value) {
            requireNonNull(value);
            this.connectTimeout = value;
            return this;
        }

        public Builder readWriteTimeout(Duration value) {
            requireNonNull(value);
            this.readWriteTimeout = value;
            return this;
        }


        public Builder insecureSkipVerify(boolean value) {
            this.insecureSkipVerify = value;
            return this;
        }


        public Builder enabledRedirect(boolean value) {
            this.enabledRedirect = value;
            return this;
        }

        public Builder proxyHost(String value) {
            requireNonNull(value);
            this.proxyHost = value;
            return this;
        }

        public Builder disableUploadCRC64Check(boolean value) {
            this.disableUploadCRC64Check = value;
            return this;
        }

        public Builder accountId(String value) {
            this.accountId = value;
            return this;
        }

        public ClientConfiguration build() {
            return new ClientConfiguration(this);
        }
    }
}
