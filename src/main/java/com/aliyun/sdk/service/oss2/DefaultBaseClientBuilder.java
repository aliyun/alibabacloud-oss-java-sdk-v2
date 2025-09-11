package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.retry.Retryer;
import com.aliyun.sdk.service.oss2.signer.Signer;
import com.aliyun.sdk.service.oss2.transport.HttpClient;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;

import java.time.Duration;
import java.util.List;

/**
 * Internal base class for {@link DefaultOSSClientBuilder}, {@link DefaultOSSAsyncClientBuilder}
 * {@link DefaultOSSDualClientBuilder}
 */
@SuppressWarnings("unchecked")
public abstract class DefaultBaseClientBuilder<B extends BaseClientBuilder<B, C>, C> implements BaseClientBuilder<B, C> {

    protected ClientConfiguration.Builder cfgBuilder;
    protected HttpClient httpClient;

    public DefaultBaseClientBuilder() {
        cfgBuilder = ClientConfiguration.defaultBuilder();
        httpClient = null;
    }

    @Override
    public final C build() {
        return buildClient();
    }

    protected abstract C buildClient();


    public B region(String value) {
        cfgBuilder.region(value);
        return (B) this;
    }

    public B endpoint(String value) {
        cfgBuilder.endpoint(value);
        return (B) this;
    }

    public B signer(Signer value) {
        cfgBuilder.signer(value);
        return (B) this;
    }

    public B retryMaxAttempts(Integer value) {
        cfgBuilder.retryMaxAttempts(value);
        return (B) this;
    }

    public B retryer(Retryer value) {
        cfgBuilder.retryer(value);
        return (B) this;
    }

    public B credentialsProvider(CredentialsProvider value) {
        cfgBuilder.credentialsProvider(value);
        return (B) this;
    }

    public B signatureVersion(String value) {
        cfgBuilder.signatureVersion(value);
        return (B) this;
    }

    public B disableSsl(Boolean value) {
        cfgBuilder.disableSsl(value);
        return (B) this;
    }

    public B useDualStackEndpoint(Boolean value) {
        cfgBuilder.useDualStackEndpoint(value);
        return (B) this;
    }

    public B useInternalEndpoint(Boolean value) {
        cfgBuilder.useInternalEndpoint(value);
        return (B) this;
    }

    public B useAccelerateEndpoint(Boolean value) {
        cfgBuilder.useAccelerateEndpoint(value);
        return (B) this;
    }

    public B useCName(Boolean value) {
        cfgBuilder.useCName(value);
        return (B) this;
    }

    public B usePathStyle(Boolean value) {
        cfgBuilder.usePathStyle(value);
        return (B) this;
    }

    public B httpClient(HttpClient value) {
        cfgBuilder.httpClient(value);
        this.httpClient = value;
        return (B) this;
    }

    public B additionalHeaders(List<String> value) {
        cfgBuilder.additionalHeaders(value);
        return (B) this;
    }

    public B userAgent(String value) {
        cfgBuilder.userAgent(value);
        return (B) this;
    }

    public B connectTimeout(Duration value) {
        cfgBuilder.connectTimeout(value);
        return (B) this;
    }

    public B readWriteTimeout(Duration value) {
        cfgBuilder.readWriteTimeout(value);
        return (B) this;
    }

    public B insecureSkipVerify(boolean value) {
        cfgBuilder.insecureSkipVerify(value);
        return (B) this;
    }

    public B enabledRedirect(boolean value) {
        cfgBuilder.enabledRedirect(value);
        return (B) this;
    }

    public B proxyHost(String value) {
        cfgBuilder.proxyHost(value);
        return (B) this;
    }

    public B disableUploadCRC64Check(boolean value) {
        cfgBuilder.disableUploadCRC64Check(value);
        return (B) this;
    }

    public B accountId(String value) {
        cfgBuilder.accountId(value);
        return (B) this;
    }

    protected final HttpClientOptions toHttpClientOptions(ClientConfiguration cfg) {

        HttpClientOptions.Builder b = HttpClientOptions.custom();

        if (cfg.insecureSkipVerify().isPresent()) {
            b.insecureSkipVerify(cfg.insecureSkipVerify().get());
        }

        if (cfg.enabledRedirect().isPresent()) {
            b.redirectsEnabled(cfg.enabledRedirect().get());
        }

        if (cfg.connectTimeout().isPresent()) {
            b.connectTimeout(cfg.connectTimeout().get());
        }

        if (cfg.readWriteTimeout().isPresent()) {
            b.readWriteTimeout(cfg.readWriteTimeout().get());
        }

        if (cfg.proxyHost().isPresent()) {
            b.proxyHost(cfg.proxyHost().get());
        }

        return b.build();
    }
}
