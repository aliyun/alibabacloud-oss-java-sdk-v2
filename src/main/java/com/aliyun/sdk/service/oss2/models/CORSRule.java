package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores the CORS rules.Up to 10 CORS rules can be configured for a bucket. The XML message body in a request can be up to 16 KB in size.
 */
@JacksonXmlRootElement(localName = "CORSRule")
public final class CORSRule {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "AllowedHeader")
    private List<String> allowedHeaders;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ExposeHeader")
    private List<String> exposeHeaders;

    @JacksonXmlProperty(localName = "MaxAgeSeconds")
    private Long maxAgeSeconds;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "AllowedOrigin")
    private List<String> allowedOrigins;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "AllowedMethod")
    private List<String> allowedMethods;

    public CORSRule() {
    }

    private CORSRule(Builder builder) {
        this.allowedHeaders = builder.allowedHeaders;
        this.exposeHeaders = builder.exposeHeaders;
        this.maxAgeSeconds = builder.maxAgeSeconds;
        this.allowedOrigins = builder.allowedOrigins;
        this.allowedMethods = builder.allowedMethods;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Specifies whether the headers specified by Access-Control-Request-Headers in the OPTIONS preflight request are allowed. Each header specified by Access-Control-Request-Headers must match the value of an AllowedHeader element.  You can use only one asterisk (\*) as the wildcard character.
     */
    public List<String> allowedHeaders() {
        return this.allowedHeaders;
    }

    /**
     * The response headers for allowed access requests from applications, such as an XMLHttpRequest object in JavaScript.  The asterisk (\*) wildcard character is not supported.
     */
    public List<String> exposeHeaders() {
        return this.exposeHeaders;
    }

    /**
     * The period of time within which the browser can cache the response to an OPTIONS preflight request for the specified resource. Unit: seconds.You can specify only one MaxAgeSeconds element in a CORS rule.
     */
    public Long maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    /**
     * The origins from which cross-origin requests are allowed.
     */
    public List<String> allowedOrigins() {
        return this.allowedOrigins;
    }

    /**
     * The methods that you can use in cross-origin requests.
     */
    public List<String> allowedMethods() {
        return this.allowedMethods;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<String> allowedHeaders;
        private List<String> exposeHeaders;
        private Long maxAgeSeconds;
        private List<String> allowedOrigins;
        private List<String> allowedMethods;

        private Builder() {
            super();
        }

        private Builder(CORSRule from) {
            this.allowedHeaders = from.allowedHeaders;
            this.exposeHeaders = from.exposeHeaders;
            this.maxAgeSeconds = from.maxAgeSeconds;
            this.allowedOrigins = from.allowedOrigins;
            this.allowedMethods = from.allowedMethods;
        }

        /**
         * Specifies whether the headers specified by Access-Control-Request-Headers in the OPTIONS preflight request are allowed. Each header specified by Access-Control-Request-Headers must match the value of an AllowedHeader element.  You can use only one asterisk (\*) as the wildcard character.
         */
        public Builder allowedHeaders(List<String> value) {
            requireNonNull(value);
            this.allowedHeaders = value;
            return this;
        }

        /**
         * The response headers for allowed access requests from applications, such as an XMLHttpRequest object in JavaScript.  The asterisk (\*) wildcard character is not supported.
         */
        public Builder exposeHeaders(List<String> value) {
            requireNonNull(value);
            this.exposeHeaders = value;
            return this;
        }

        /**
         * The period of time within which the browser can cache the response to an OPTIONS preflight request for the specified resource. Unit: seconds.You can specify only one MaxAgeSeconds element in a CORS rule.
         */
        public Builder maxAgeSeconds(Long value) {
            requireNonNull(value);
            this.maxAgeSeconds = value;
            return this;
        }

        /**
         * The origins from which cross-origin requests are allowed.
         */
        public Builder allowedOrigins(List<String> value) {
            requireNonNull(value);
            this.allowedOrigins = value;
            return this;
        }

        /**
         * The methods that you can use in cross-origin requests.
         */
        public Builder allowedMethods(List<String> value) {
            requireNonNull(value);
            this.allowedMethods = value;
            return this;
        }

        public CORSRule build() {
            return new CORSRule(this);
        }
    }
}
