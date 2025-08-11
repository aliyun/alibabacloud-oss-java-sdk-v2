package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The log configurations of the bucket.
 */
@JacksonXmlRootElement(localName = "BucketPolicy")
public final class BucketPolicy {
    @JacksonXmlProperty(localName = "LogBucket")
    private String logBucket;

    @JacksonXmlProperty(localName = "LogPrefix")
    private String logPrefix;

    public BucketPolicy() {
    }

    private BucketPolicy(Builder builder) {
        this.logBucket = builder.logBucket;
        this.logPrefix = builder.logPrefix;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket used to store access logs.
     */
    public String logBucket() {
        return this.logBucket;
    }

    /**
     * The directory used to store access logs.
     */
    public String logPrefix() {
        return this.logPrefix;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String logBucket;
        private String logPrefix;

        private Builder() {
            super();
        }

        private Builder(BucketPolicy from) {
            this.logBucket = from.logBucket;
            this.logPrefix = from.logPrefix;
        }

        /**
         * The name of the bucket used to store access logs.
         */
        public Builder logBucket(String value) {
            requireNonNull(value);
            this.logBucket = value;
            return this;
        }

        /**
         * The directory used to store access logs.
         */
        public Builder logPrefix(String value) {
            requireNonNull(value);
            this.logPrefix = value;
            return this;
        }

        public BucketPolicy build() {
            return new BucketPolicy(this);
        }
    }
}
