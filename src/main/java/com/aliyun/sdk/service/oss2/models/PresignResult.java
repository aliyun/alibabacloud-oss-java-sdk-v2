package com.aliyun.sdk.service.oss2.models;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * The result for the presign operation.
 */
public class PresignResult {
    private final String url;
    private final String method;
    private final Instant expiration;
    private final Map<String, String> signedHeaders;

    private PresignResult(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.expiration = builder.expiration;
        this.signedHeaders = builder.signedHeaders;
    }

    /**
     * Gets the pre-signed URL.
     */
    public String url() {
        return this.url;
    }


    /**
     * Gets the HTTP method, which corresponds to the operation.
     * For example, the HTTP method of the GetObject operation is GET.
     */
    public String method() {
        return this.method;
    }

    /**
     * Gets the time when the pre-signed URL expires.
     */
    public Optional<Instant> expiration() {
        return Optional.ofNullable(this.expiration);
    }

    /**
     * Gets the request headers specified in the request.
     */
    public Optional<Map<String, String>> signedHeaders() {
        return Optional.ofNullable(this.signedHeaders);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String url;
        private String method;
        private Instant expiration;
        private Map<String, String> signedHeaders;

        private Builder() {
            super();
        }

        /**
         * Specifies the pre-signed URL.
         */
        public Builder url(String value) {
            requireNonNull(value);
            this.url = value;
            return this;
        }

        /**
         * Specifies the HTTP method, which corresponds to the operation.
         */
        public Builder method(String value) {
            requireNonNull(value);
            this.method = value;
            return this;
        }

        /**
         * Specifies the time when the pre-signed URL expires.
         */
        public Builder expiration(Instant value) {
            //requireNonNull(value);
            this.expiration = value;
            return this;
        }

        /**
         * Specifies the request headers specified in the request.
         */
        public Builder signedHeaders(Map<String, String> value) {
            //requireNonNull(value);
            this.signedHeaders = value;
            return this;
        }

        public PresignResult build() {
            return new PresignResult(this);
        }
    }
}
