package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The container that stores CORS configuration.
 */
@JacksonXmlRootElement(localName = "CORSConfiguration")
public final class CORSConfiguration {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CORSRule")
    private List<CORSRule> corsRules;

    @JacksonXmlProperty(localName = "ResponseVary")
    private Boolean responseVary;

    public CORSConfiguration() {
    }

    private CORSConfiguration(Builder builder) {
        this.corsRules = builder.corsRules;
        this.responseVary = builder.responseVary;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores CORS rules. Up to 10 rules can be configured for a bucket.
     */
    public List<CORSRule> corsRules() {
        return this.corsRules;
    }

    /**
     * Indicates whether the Vary: Origin header was returned. Default value: false.- true: The Vary: Origin header is returned regardless whether the request is a cross-origin request or whether the cross-origin request succeeds.- false: The Vary: Origin header is not returned.
     */
    public Boolean responseVary() {
        return this.responseVary;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<CORSRule> corsRules;
        private Boolean responseVary;

        private Builder() {
            super();
        }

        private Builder(CORSConfiguration from) {
            this.corsRules = from.corsRules;
            this.responseVary = from.responseVary;
        }

        /**
         * The container that stores CORS rules. Up to 10 rules can be configured for a bucket.
         */
        public Builder corsRules(List<CORSRule> value) {
            requireNonNull(value);
            this.corsRules = value;
            return this;
        }

        /**
         * Indicates whether the Vary: Origin header was returned. Default value: false.- true: The Vary: Origin header is returned regardless whether the request is a cross-origin request or whether the cross-origin request succeeds.- false: The Vary: Origin header is not returned.
         */
        public Builder responseVary(Boolean value) {
            requireNonNull(value);
            this.responseVary = value;
            return this;
        }

        public CORSConfiguration build() {
            return new CORSConfiguration(this);
        }
    }
}
