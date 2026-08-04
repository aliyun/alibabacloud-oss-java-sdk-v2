package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * Generates a manifest based on a key prefix in a source bucket.
 * Used as an alternative to Manifest in CreateJob.
 */
@JacksonXmlRootElement(localName = "KeyPrefixManifestGenerator")
public final class KeyPrefixManifestGenerator {

    @JacksonXmlProperty(localName = "SourceBucket")
    private String sourceBucket;

    @JacksonXmlProperty(localName = "Prefix")
    private String prefix;

    public KeyPrefixManifestGenerator() {
    }

    private KeyPrefixManifestGenerator(Builder builder) {
        this.sourceBucket = builder.sourceBucket;
        this.prefix = builder.prefix;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The source bucket for the manifest.
     */
    public String sourceBucket() {
        return this.sourceBucket;
    }

    /**
     * The prefix to filter objects.
     */
    public String prefix() {
        return this.prefix;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String sourceBucket;
        private String prefix;

        private Builder() {
            super();
        }

        private Builder(KeyPrefixManifestGenerator from) {
            this.sourceBucket = from.sourceBucket;
            this.prefix = from.prefix;
        }

        /**
         * The source bucket for the manifest.
         */
        public Builder sourceBucket(String value) {
            requireNonNull(value);
            this.sourceBucket = value;
            return this;
        }

        /**
         * The prefix to filter objects.
         */
        public Builder prefix(String value) {
            this.prefix = value;
            return this;
        }

        public KeyPrefixManifestGenerator build() {
            return new KeyPrefixManifestGenerator(this);
        }
    }
}
