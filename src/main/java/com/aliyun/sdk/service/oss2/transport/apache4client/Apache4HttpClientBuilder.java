package com.aliyun.sdk.service.oss2.transport.apache4client;

import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Apache4HttpClientBuilder {

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
    private HttpClientConnectionManager connectionManager;
    private RequestConfig requestConfig;

    private boolean useReaper;
    private long connectionTTL;
    private int connectionRequestTimeout;
    private long idleConnectionTime;
    private int maxConnections;

    // internal use
    private int connectionTimeout;
    private int socketTimeout;

    private KeyManager[] keyManagers;
    private X509TrustManager[] x509TrustManagers;
    private HostnameVerifier hostnameVerifier;
    private KeyStore keyStore;
    private SecureRandom secureRandom = null;

    protected Apache4HttpClientBuilder() {
        super();
        this.useReaper = DEFAULT_USE_REAPER;
        this.connectionTTL = DEFAULT_CONNECTION_TTL;
        this.connectionRequestTimeout = DEFAULT_CONNECTION_REQUEST_TIMEOUT;
        this.idleConnectionTime = DEFAULT_IDLE_CONNECTION_TIME;
        this.maxConnections = DEFAULT_MAX_CONNECTIONS;
    }

    public static Apache4HttpClientBuilder create() {
        return new Apache4HttpClientBuilder();
    }

    protected static String resolveStringValue(String value, String key, boolean flag) {
        if (value == null && flag) {
            try {
                return System.getProperty(key);
            } catch (Exception ignore) {
            }
            return null;
        }
        return value;
    }

    protected static int resolveIntValue(int value, String key, boolean flag) {
        if (value == -1 && flag) {
            try {
                return Integer.parseInt(System.getProperty(key));
            } catch (Exception ignore) {
            }
            return -1;
        }
        return value;
    }

    /**
     * Assigns {@link HttpClientOptions} instance.
     */
    public Apache4HttpClientBuilder options(final HttpClientOptions value) {
        this.options = value;
        return this;
    }

    /**
     * Assigns {@link DnsResolver} instance.
     */
    public Apache4HttpClientBuilder dnsResolver(final DnsResolver value) {
        this.dnsResolver = value;
        return this;
    }

    /**
     * Assigns {@link HttpClientConnectionManager} instance.
     */
    public Apache4HttpClientBuilder connectionManager(final HttpClientConnectionManager value) {
        this.connectionManager = value;
        return this;
    }

    /**
     * Assigns {@link RequestConfig} instance.
     */
    public Apache4HttpClientBuilder requestConfig(final RequestConfig value) {
        this.requestConfig = value;
        return this;
    }

    /**
     * Sets the max connection count.
     *
     * @param value The max connection count.
     */
    public Apache4HttpClientBuilder maxConnections(int value) {
        this.maxConnections = value;
        return this;
    }

    /**
     * Sets the flag of using {@link IdleConnectionReaper} to manage expired
     * connection.
     *
     * @param value The flag of using {@link IdleConnectionReaper}. By default, it's true.
     */
    public Apache4HttpClientBuilder useReaper(final boolean value) {
        this.useReaper = value;
        return this;
    }

    /**
     * Sets the connection TTL (time to live). Http connection is cached by the
     * connection manager with a TTL.
     *
     * @param value The connection TTL in millisecond.
     */
    public Apache4HttpClientBuilder connectionTTL(final long value) {
        this.connectionTTL = value;
        return this;
    }

    /**
     * Sets the connection's max idle time. If a connection has been idle for
     * more than this number, it would be closed.
     *
     * @param value The connection's max idle time in millisecond.
     */
    public Apache4HttpClientBuilder idleConnectionTime(final long value) {
        this.idleConnectionTime = value;
        return this;
    }

    /**
     * Sets the timeout in millisecond for retrieving an available connection
     * from the connection manager.
     *
     * @param value The timeout in millisecond.
     */
    public Apache4HttpClientBuilder connectionRequestTimeout(int value) {
        this.connectionRequestTimeout = value;
        return this;
    }

    /**
     * Sets the key managers are responsible for managing the key material
     * which is used to authenticate the local SSLSocket to its peer.
     *
     * @param value The key managers
     */
    public Apache4HttpClientBuilder keyManagers(final KeyManager[] value) {
        this.keyManagers = value;
        return this;
    }

    /**
     * Sets the instance of this interface manage which X509 certificates
     * may be used to authenticate the remote side of a secure socket.
     *
     * @param value x509 trust managers
     */
    public Apache4HttpClientBuilder x509TrustManagers(final X509TrustManager[] value) {
        this.x509TrustManagers = value;
        return this;
    }

    /**
     * Sets instance of this interface for hostname verification.
     *
     * @param value The hostname verification instance
     */
    public Apache4HttpClientBuilder hostnameVerifier(final HostnameVerifier value) {
        this.hostnameVerifier = value;
        return this;
    }

    /**
     * Sets the cryptographically strong random number.
     *
     * @param value The cryptographically strong random number
     */
    public Apache4HttpClientBuilder secureRandom(final SecureRandom value) {
        this.secureRandom = value;
        return this;
    }

    /**
     * Sets the KeyStore to be used for SSL/TLS operations.
     *
     * @param value the KeyStore instance (e.g., JKS or PKCS12 format) to be used.
     *              Must not be null.
     */
    public Apache4HttpClientBuilder keyStore(final KeyStore value) {
        this.keyStore = value;
        return this;
    }

    public Apache4HttpClient build() {
        // If not set, use default value
        this.options = Optional
                .ofNullable(this.options)
                .orElse(HttpClientOptions.custom().build());

        this.connectionTimeout = (int) this.options.connectTimeout().toMillis();
        this.socketTimeout = (int) this.options.readWriteTimeout().toMillis();

        HttpClientConnectionManager connectionManager = Optional
                .ofNullable(this.connectionManager)
                .orElse(createHttpClientConnectionManager());

        RequestConfig requestConfig = Optional
                .ofNullable(this.requestConfig)
                .orElse(createRequestConfig());

        HttpClientBuilder builder = HttpClients.custom()
                .disableAutomaticRetries()
                .disableContentCompression()
                .disableRedirectHandling()
                .setUserAgent("")
                .setConnectionManager(connectionManager);

        if (this.useReaper) {
            IdleConnectionReaper.getInstance().registerConnectionManager(
                    connectionManager, this.idleConnectionTime);
        }

        return new Apache4HttpClient(builder.build(), connectionManager, requestConfig);
    }

    protected RequestConfig createRequestConfig() {
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectTimeout(this.connectionTimeout);
        builder.setSocketTimeout(this.socketTimeout);
        builder.setConnectionRequestTimeout(this.connectionRequestTimeout);
        builder.setRedirectsEnabled(this.options.redirectsEnabled());

        /*
        //TODO HTTP proxy
        String proxyHost = resolveStringValue(config.getProxyHost(), "http.proxyHost", config.isUseSystemPropertyValues());
        int proxyPort = resolveIntValue(config.getProxyPort(), "http.proxyPort", config.isUseSystemPropertyValues());

        if (proxyHost != null && proxyPort > 0) {
            this.proxyHttpHost = new HttpHost(proxyHost, proxyPort);
            builder.setProxy(proxyHttpHost);

            String proxyUsername = resolveStringValue(config.getProxyUsername(),"http.proxyUser", config.isUseSystemPropertyValues());
            String proxyPassword = resolveStringValue(config.getProxyPassword(),"http.proxyPassword", config.isUseSystemPropertyValues());
            String proxyDomain = config.getProxyDomain();
            String proxyWorkstation = config.getProxyWorkstation();
            if (proxyUsername != null && proxyPassword != null) {
                this.credentialsProvider = new BasicCredentialsProvider();
                this.credentialsProvider.setCredentials(new AuthScope(proxyHost, proxyPort),
                        new NTCredentials(proxyUsername, proxyPassword, proxyWorkstation, proxyDomain));

                this.authCache = new BasicAuthCache();
                authCache.put(this.proxyHttpHost, new BasicScheme());
            }
        }
         */
        //Compatible with HttpClient 4.5.9 or later
        builder.setNormalizeUri(false);

        return builder.build();
    }

    protected HttpClientConnectionManager createHttpClientConnectionManager() {
        SSLConnectionSocketFactory sslSocketFactory = null;
        try {
            List<TrustManager> trustManagerList = new ArrayList<TrustManager>();
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
            sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        } catch (Exception e) {
            throw new RuntimeException("SSLContext fail", e);
        }

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory).build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry,
                this.dnsResolver);

        connectionManager.setDefaultMaxPerRoute(this.maxConnections);
        connectionManager.setMaxTotal(this.maxConnections);
        connectionManager.setValidateAfterInactivity(DEFAULT_VALIDATE_AFTER_INACTIVITY);
        connectionManager.setDefaultSocketConfig(
                SocketConfig.custom()
                        .setSoTimeout(this.socketTimeout)
                        .setTcpNoDelay(true).build());

        return connectionManager;
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

}
