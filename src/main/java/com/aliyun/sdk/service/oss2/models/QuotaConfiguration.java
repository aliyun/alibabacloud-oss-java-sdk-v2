package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The container that stores the bucket storage quota configuration.
 */
@JacksonXmlRootElement(localName = "QuotaConfiguration")
public final class QuotaConfiguration {

    @JacksonXmlProperty(localName = "StorageQuota")
    private Long storageQuota;

    @JacksonXmlProperty(localName = "Mode")
    private String mode;

    @JacksonXmlProperty(localName = "CurrentUsage")
    private Long currentUsage;

    public QuotaConfiguration() {
    }

    private QuotaConfiguration(Builder builder) {
        this.storageQuota = builder.storageQuota;
        this.mode = builder.mode;
        this.currentUsage = builder.currentUsage;
    }

    /**
     * The storage quota of the bucket. Unit: bytes.
     */
    public Long storageQuota() {
        return storageQuota;
    }

    /**
     * The mode of the storage quota. Valid values: Strict or Warning.
     */
    public String mode() {
        return mode;
    }

    /**
     * The current usage of the bucket. Unit: bytes.
     */
    public Long currentUsage() {
        return currentUsage;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Long storageQuota;
        private String mode;
        private Long currentUsage;

        private Builder() {
            super();
        }

        private Builder(QuotaConfiguration from) {
            this.storageQuota = from.storageQuota;
            this.mode = from.mode;
            this.currentUsage = from.currentUsage;
        }

        /**
         * The storage quota of the bucket. Unit: bytes.
         */
        public Builder storageQuota(Long value) {
            requireNonNull(value);
            this.storageQuota = value;
            return this;
        }

        /**
         * The mode of the storage quota. Valid values: Strict or Warning.
         */
        public Builder mode(String value) {
            requireNonNull(value);
            this.mode = value;
            return this;
        }

        /**
         * The current usage of the bucket. Unit: bytes.
         */
        public Builder currentUsage(Long value) {
            this.currentUsage = value;
            return this;
        }

        public QuotaConfiguration build() {
            return new QuotaConfiguration(this);
        }
    }
}
