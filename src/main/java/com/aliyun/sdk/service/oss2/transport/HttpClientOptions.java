package com.aliyun.sdk.service.oss2.transport;

import java.time.Duration;

public class HttpClientOptions {

    private static final Duration DEFAULT_CONNECTION_TIMEOUT = Duration.ofSeconds(5);
    private static final Duration DEFAULT_READWRITE_TIMEOUT = Duration.ofSeconds(20);
    private static final Duration DEFAULT_IDLE_CONNECTION_TIMEOUT = Duration.ofSeconds(50);
    private static final Duration DEFAULT_EXPECT_CONTINUE_TIMEOUT = Duration.ofSeconds(1);
    private static final Duration DEFAULT_KEEP_ALIVE_TIMEOUT = Duration.ofSeconds(30);
    private static final int DEFAULT_MAX_CONNECTIONS = 1024;

    private final String proxyHost;
    private final boolean redirectsEnabled;
    private final Duration connectTimeout;
    private final Duration readWriteTimeout;
    private final Duration keepAliveTimeout;
    private final boolean insecureSkipVerify;
    private final int maxConnections;

    HttpClientOptions(Builder builder) {
        this.proxyHost = builder.proxyHost;
        this.redirectsEnabled = builder.redirectsEnabled;
        this.insecureSkipVerify = builder.insecureSkipVerify;
        this.connectTimeout = builder.connectTimeout;
        this.readWriteTimeout = builder.readWriteTimeout;
        this.keepAliveTimeout = builder.keepAliveTimeout;
        this.maxConnections = builder.maxConnections;
    }

    public static Builder custom() {
        return new Builder();
    }

    /**
     * @see Builder#proxyHost(String)
     */
    public String proxyHost() {
        return this.proxyHost;
    }

    /**
     * @see Builder#redirectsEnabled(boolean)
     */
    public boolean redirectsEnabled() {
        return this.redirectsEnabled;
    }

    /**
     * @see Builder#insecureSkipVerify(boolean)
     */
    public boolean insecureSkipVerify() {
        return this.insecureSkipVerify;
    }

    /**
     * @see Builder#connectTimeout(Duration)
     */
    public Duration connectTimeout() {
        return this.connectTimeout;
    }

    /**
     * @see Builder#readWriteTimeout(Duration)
     */
    public Duration readWriteTimeout() {
        return this.readWriteTimeout;
    }

    /**
     * @see Builder#keepAliveTimeout(Duration)
     */
    public Duration keepAliveTimeout() {
        return this.keepAliveTimeout;
    }

    public int maxConnections() {
        return this.maxConnections;
    }

    public static class Builder {
        private String proxyHost;
        private boolean redirectsEnabled;
        private boolean insecureSkipVerify;
        private Duration connectTimeout;
        private Duration readWriteTimeout;
        private Duration keepAliveTimeout;
        private int maxConnections;

        Builder() {
            this.redirectsEnabled = false;
            this.insecureSkipVerify = false;
            this.connectTimeout = DEFAULT_CONNECTION_TIMEOUT;
            this.readWriteTimeout = DEFAULT_READWRITE_TIMEOUT;
            this.keepAliveTimeout = DEFAULT_KEEP_ALIVE_TIMEOUT;
            this.maxConnections = DEFAULT_MAX_CONNECTIONS;
        }

        /**
         * Returns HTTP proxy to be used for request execution.
         *
         * @return this instance.
         */
        public Builder proxyHost(final String value) {
            this.proxyHost = value;
            return this;
        }

        /**
         * Determines whether redirects should be handled automatically.
         * <p>
         * Default: {@code false}
         * </p>
         *
         * @return this instance.
         */
        public Builder redirectsEnabled(final boolean value) {
            this.redirectsEnabled = value;
            return this;
        }

        /**
         * Determines whether skip server certificate verification.
         * <p>
         * Default: {@code false}
         * </p>
         *
         * @return this instance.
         */
        public Builder insecureSkipVerify(final boolean value) {
            this.insecureSkipVerify = value;
            return this;
        }

        /**
         * Determines the timeout until a new connection is fully established.
         *
         * @return this instance.
         */
        public Builder connectTimeout(final Duration value) {
            this.connectTimeout = value;
            return this;
        }

        /**
         * Determines the timeout until arrival of a response from the opposite endpoint.
         *
         * @return this instance.
         */
        public Builder readWriteTimeout(final Duration value) {
            this.readWriteTimeout = value;
            return this;
        }

        /**
         * Determines the default of value of connection keep-alive time period when not
         * explicitly communicated by the origin server with a {@code Keep-Alive} response
         * header.
         *
         * @return this instance.
         */
        public Builder keepAliveTimeout(final Duration value) {
            this.keepAliveTimeout = value;
            return this;
        }

        /**
         * Determines the default of value of max connections.
         *
         * @return this instance.
         */
        public Builder maxConnections(final int value) {
            this.maxConnections = value;
            return this;
        }

        public HttpClientOptions build() {
            return new HttpClientOptions(this);
        }

    }

}