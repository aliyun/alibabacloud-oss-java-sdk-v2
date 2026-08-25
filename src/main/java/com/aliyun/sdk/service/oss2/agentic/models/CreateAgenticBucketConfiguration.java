package com.aliyun.sdk.service.oss2.agentic.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The configuration for creating an agentic bucket.
 */
@JacksonXmlRootElement(localName = "CreateAgenticBucketConfiguration")
public final class CreateAgenticBucketConfiguration {
    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;

    @JacksonXmlProperty(localName = "DataRedundancyType")
    private String dataRedundancyType;

    public CreateAgenticBucketConfiguration() {
    }

    private CreateAgenticBucketConfiguration(Builder builder) {
        this.storageClass = builder.storageClass;
        this.dataRedundancyType = builder.dataRedundancyType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The storage class of the bucket.
     */
    public String storageClass() {
        return this.storageClass;
    }

    /**
     * The data redundancy type of the bucket.
     */
    public String dataRedundancyType() {
        return this.dataRedundancyType;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String storageClass;
        private String dataRedundancyType;

        private Builder() {
        }

        private Builder(CreateAgenticBucketConfiguration from) {
            this.storageClass = from.storageClass;
            this.dataRedundancyType = from.dataRedundancyType;
        }

        /**
         * The storage class of the bucket.
         */
        public Builder storageClass(String value) {
            requireNonNull(value);
            this.storageClass = value;
            return this;
        }

        /**
         * The data redundancy type of the bucket.
         */
        public Builder dataRedundancyType(String value) {
            requireNonNull(value);
            this.dataRedundancyType = value;
            return this;
        }

        public CreateAgenticBucketConfiguration build() {
            return new CreateAgenticBucketConfiguration(this);
        }
    }
}
