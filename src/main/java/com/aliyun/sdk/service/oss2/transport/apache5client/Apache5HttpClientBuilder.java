package com.aliyun.sdk.service.oss2.transport.apache5client;

import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import org.apache.hc.client5.http.ConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.DnsResolver;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.DefaultHostnameVerifier;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.TlsSocketStrategy;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.apache.hc.client5.http.impl.DefaultConnectionKeepAliveStrategy;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Apache5HttpClientBuilder {

    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = -1;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 50 * 1000;
    private static final int DEFAULT_SOCKET_TIMEOUT = 50 * 1000;
    private static final int DEFAULT_MAX_CONNECTIONS = 1024;
    private static final long DEFAULT_CONNECTION_TTL = -1;
    private static final long DEFAULT_IDLE_CONNECTION_TIME = 50 * 1000;
    private static final int DEFAULT_VALIDATE_AFTER_INACTIVITY = 2 * 1000;
    private static final boolean DEFAULT_USE_REAPER = true;

    private HttpClientOptions options;
    private DnsResolver dnsResolver;
    private PoolingHttpClientConnectionManager connectionManager;
    private RequestConfig requestConfig;

    private boolean useReaper;
    private long connectionTTL;
    private int connectionRequestTimeout;
    private long idleConnectionTime;
    private int maxConnections;

    // internal use
    private long connectionTimeout;
    private long responseTimeout;

    private KeyManager[] keyManagers;
    private X509TrustManager[] x509TrustManagers;
    private HostnameVerifier hostnameVerifier;
    private KeyStore keyStore;
    private SecureRandom secureRandom = null;


    protected Apache5HttpClientBuilder() {
        super();
        this.useReaper = DEFAULT_USE_REAPER;
        this.connectionTTL = DEFAULT_CONNECTION_TTL;
        this.connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT;
        this.idleConnectionTime = DEFAULT_IDLE_CONNECTION_TIME;
        this.maxConnections = DEFAULT_MAX_CONNECTIONS;
    }

    public static Apache5HttpClientBuilder create() {
        return new Apache5HttpClientBuilder();
    }

    private static Timeout millistoTimeout(final long value) {
        if (value < 0) {
            return Timeout.INFINITE;
        }
        return Timeout.ofMilliseconds(value);
    }

    /**
     * Assigns {@link HttpClientOptions} instance.
     */
    public Apache5HttpClientBuilder options(final HttpClientOptions value) {
        this.options = value;
        return this;
    }

    /**
     * Assigns {@link DnsResolver} instance.
     */
    public Apache5HttpClientBuilder dnsResolver(final DnsResolver value) {
        this.dnsResolver = value;
        return this;
    }

    /**
     * Assigns {@link PoolingHttpClientConnectionManager} instance.
     */
    public Apache5HttpClientBuilder connectionManager(final PoolingHttpClientConnectionManager value) {
        this.connectionManager = value;
        return this;
    }

    /**
     * Assigns {@link RequestConfig} instance.
     */
    public Apache5HttpClientBuilder requestConfig(final RequestConfig value) {
        this.requestConfig = value;
        return this;
    }

    /**
     * Sets the max connection count.
     *
     * @param value The max connection count.
     */
    public Apache5HttpClientBuilder maxConnections(int value) {
        this.maxConnections = value;
        return this;
    }

    /**
     * Sets the flag of using {@link IdleConnectionReaper} to manage expired
     * connection.
     *
     * @param value The flag of using {@link IdleConnectionReaper}. By default, it's true.
     */
    public Apache5HttpClientBuilder useReaper(final boolean value) {
        this.useReaper = value;
        return this;
    }

    /**
     * Sets the connection TTL (time to live). Http connection is cached by the
     * connection manager with a TTL.
     *
     * @param value The connection TTL in millisecond.
     */
    public Apache5HttpClientBuilder connectionTTL(final long value) {
        this.connectionTTL = value;
        return this;
    }

    /**
     * Sets the connection's max idle time. If a connection has been idle for
     * more than this number, it would be closed.
     *
     * @param value The connection's max idle time in millisecond.
     */
    public Apache5HttpClientBuilder idleConnectionTime(final long value) {
        this.idleConnectionTime = value;
        return this;
    }

    /**
     * Sets the timeout in millisecond for retrieving an available connection
     * from the connection manager.
     *
     * @param value The timeout in millisecond.
     */
    public Apache5HttpClientBuilder connectionRequestTimeout(int value) {
        this.connectionRequestTimeout = value;
        return this;
    }

    /**
     * Sets the key managers are responsible for managing the key material
     * which is used to authenticate the local SSLSocket to its peer.
     *
     * @param value The key managers
     */
    public Apache5HttpClientBuilder keyManagers(final KeyManager[] value) {
        this.keyManagers = value;
        return this;
    }

    /**
     * Sets the instance of this interface manage which X509 certificates
     * may be used to authenticate the remote side of a secure socket.
     *
     * @param value x509 trust managers
     */
    public Apache5HttpClientBuilder x509TrustManagers(final X509TrustManager[] value) {
        this.x509TrustManagers = value;
        return this;
    }

    /**
     * Sets instance of this interface for hostname verification.
     *
     * @param value The hostname verification instance
     */
    public Apache5HttpClientBuilder hostnameVerifier(final HostnameVerifier value) {
        this.hostnameVerifier = value;
        return this;
    }

    /**
     * Sets the cryptographically strong random number.
     *
     * @param value The cryptographically strong random number
     */
    public Apache5HttpClientBuilder secureRandom(final SecureRandom value) {
        this.secureRandom = value;
        return this;
    }

    /**
     * Sets the KeyStore to be used for SSL/TLS operations.
     *
     * @param value the KeyStore instance (e.g., JKS or PKCS12 format) to be used.
     *              Must not be null.
     */
    public Apache5HttpClientBuilder keyStore(final KeyStore value) {
        this.keyStore = value;
        return this;
    }

    public Apache5HttpClient build() {
        // If not set, use default value
        this.options = Optional
                .ofNullable(this.options)
                .orElse(HttpClientOptions.custom().build());

        this.connectionTimeout = this.options.connectTimeout().toMillis();
        this.responseTimeout = this.options.readWriteTimeout().toMillis();

        PoolingHttpClientConnectionManager connectionManager = Optional
                .ofNullable(this.connectionManager)
                .orElse(createHttpClientConnectionManager());

        RequestConfig requestConfig = Optional
                .ofNullable(this.requestConfig)
                .orElse(createRequestConfig());

        HttpClientBuilder builder = HttpClients.custom()
                .disableAutomaticRetries()
                .disableRedirectHandling()
                .disableContentCompression()
                .setUserAgent("")
                .setKeepAliveStrategy(createKeepAliveStrategy())
                .setConnectionManager(connectionManager);

        if (this.useReaper) {
            IdleConnectionReaper.getInstance().registerConnectionManager(
                    connectionManager, this.idleConnectionTime);
        }

        return new Apache5HttpClient(builder.build(), connectionManager, requestConfig);
    }

    private ConnectionKeepAliveStrategy createKeepAliveStrategy() {
        return this.idleConnectionTime > 0 ? new SdkConnectionKeepAliveStrategy(this.idleConnectionTime) : null;
    }

    private PoolingHttpClientConnectionManager createHttpClientConnectionManager() {
        // Tls Strategy
        TlsSocketStrategy tlsStrategy = null;

        try {
            List<TrustManager> trustManagerList = new ArrayList<>();
            X509TrustManager[] trustManagers = this.x509TrustManagers;

            if (null != trustManagers) {
                trustManagerList.addAll(Arrays.asList(trustManagers));
            }

            // get trustManager using default certification from jdk
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            if (this.keyStore != null) {
                tmf.init(this.keyStore);
            } else {
                tmf.init((KeyStore) null);
            }
            trustManagerList.addAll(Arrays.asList(tmf.getTrustManagers()));

            final List<X509TrustManager> finalTrustManagerList = new ArrayList<X509TrustManager>();
            for (TrustManager tm : trustManagerList) {
                if (tm instanceof X509TrustManager) {
                    finalTrustManagerList.add((X509TrustManager) tm);
                }
            }
            boolean isVerifySSLEnable = !this.options.insecureSkipVerify();
            CompositeX509TrustManager compositeX509TrustManager = new CompositeX509TrustManager(finalTrustManagerList);
            compositeX509TrustManager.setVerifySSL(isVerifySSLEnable);
            KeyManager[] keyManagers = null;
            if (this.keyManagers != null) {
                keyManagers = this.keyManagers;
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, new TrustManager[]{compositeX509TrustManager}, this.secureRandom);

            HostnameVerifier hostnameVerifier = null;
            if (!isVerifySSLEnable) {
                hostnameVerifier = new NoopHostnameVerifier();
            } else if (this.hostnameVerifier != null) {
                hostnameVerifier = this.hostnameVerifier;
            } else {
                hostnameVerifier = new DefaultHostnameVerifier();
            }

            tlsStrategy = ClientTlsStrategyBuilder
                    .create()
                    .setSslContext(sslContext)
                    .setHostnameVerifier(hostnameVerifier)
                    .buildClassic();
        } catch (Exception e) {
            throw new RuntimeException("SSLContext fail", e);
        }

        // ConnectionConfig
        ConnectionConfig.Builder connConfigBuilder = ConnectionConfig.custom()
                .setConnectTimeout(millistoTimeout(this.connectionTimeout))
                .setValidateAfterInactivity(millistoTimeout(DEFAULT_VALIDATE_AFTER_INACTIVITY))
                .setSocketTimeout(millistoTimeout(this.responseTimeout));

        if (this.connectionTTL > 0) {
            connConfigBuilder.setTimeToLive(millistoTimeout(this.connectionTTL));
        }

        ConnectionConfig connConfig = connConfigBuilder.build();

        PoolingHttpClientConnectionManagerBuilder builder =
                PoolingHttpClientConnectionManagerBuilder.create()
                        .setTlsSocketStrategy(tlsStrategy)
                        .setDnsResolver(this.dnsResolver)
                        .setDefaultConnectionConfig(connConfig)
                        .setMaxConnPerRoute(this.maxConnections)
                        .setMaxConnTotal(this.maxConnections);

        return builder.build();
    }


    private RequestConfig createRequestConfig() {
        RequestConfig.Builder builder = RequestConfig.custom();

        builder.setResponseTimeout(millistoTimeout(this.responseTimeout));
        builder.setConnectionRequestTimeout(millistoTimeout(this.connectionRequestTimeout));
        builder.setRedirectsEnabled(this.options.redirectsEnabled());

        return builder.build();
    }

    private static class CompositeX509TrustManager implements X509TrustManager {

        private final List<X509TrustManager> trustManagers;
        private boolean verifySSL = true;

        public CompositeX509TrustManager(List<X509TrustManager> trustManagers) {
            this.trustManagers = trustManagers;
        }

        public boolean isVerifySSL() {
            return this.verifySSL;
        }

        public void setVerifySSL(boolean verifySSL) {
            this.verifySSL = verifySSL;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // do nothing
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            if (!verifySSL) {
                return;
            }
            for (X509TrustManager trustManager : trustManagers) {
                try {
                    trustManager.checkServerTrusted(chain, authType);
                    return; // someone trusts them. success!
                } catch (CertificateException e) {
                    // maybe someone else will trust them
                }
            }
            throw new CertificateException("None of the TrustManagers trust this certificate chain");
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            List<X509Certificate> certificates = new ArrayList<X509Certificate>();
            for (X509TrustManager trustManager : trustManagers) {
                X509Certificate[] accepts = trustManager.getAcceptedIssuers();
                if (accepts != null && accepts.length > 0) {
                    certificates.addAll(Arrays.asList(accepts));
                }
            }
            X509Certificate[] certificatesArray = new X509Certificate[certificates.size()];
            return certificates.toArray(certificatesArray);
        }
    }

    private static class SdkConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {

        private final TimeValue maxIdleTime;

        /**
         * @param maxIdleTime the maximum time a connection may be idle
         */
        public SdkConnectionKeepAliveStrategy(long maxIdleTime) {
            this.maxIdleTime = TimeValue.of(maxIdleTime, TimeUnit.MILLISECONDS);
        }

        @Override
        public TimeValue getKeepAliveDuration(HttpResponse response, HttpContext context) {

            // If there's a Keep-Alive timeout directive in the response and it's
            // shorter than our configured max, honor that. Otherwise go with the
            // configured maximum.

            TimeValue duration = DefaultConnectionKeepAliveStrategy.INSTANCE
                    .getKeepAliveDuration(response, context);

            // Check if duration is positive and less than maxIdleTime
            if (TimeValue.isPositive(duration) && duration.compareTo(maxIdleTime) < 0) {
                return duration;
            }

            return maxIdleTime;
        }
    }

}
