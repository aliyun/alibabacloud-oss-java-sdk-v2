package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import static java.util.Objects.requireNonNull;

/**
 * The record expiration configuration of a metadata journal table.
 */
public final class MetadataTableRecordExpiration {
    @JacksonXmlProperty(localName = "Expiration")
    private String expiration;

    @JacksonXmlProperty(localName = "Days")
    private Integer days;

    public MetadataTableRecordExpiration() {
    }

    private MetadataTableRecordExpiration(Builder builder) {
        this.expiration = builder.expiration;
        this.days = builder.days;
    }

    /**
     * The expiration state of journal table records.
     * Valid values: ENABLED, DISABLED, UNKNOWN.
     */
    public String expiration() {
        return expiration;
    }

    public Integer days() {
        return days;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder {
        private String expiration;
        private Integer days;

        private Builder() {
        }

        private Builder(MetadataTableRecordExpiration value) {
            this.expiration = value.expiration;
            this.days = value.days;
        }

        /**
         * Sets the expiration state of journal table records.
         * Valid values: ENABLED, DISABLED.
         */
        public Builder expiration(String value) {
            this.expiration = requireNonNull(value);
            return this;
        }

        public Builder days(Integer value) {
            this.days = requireNonNull(value);
            return this;
        }

        public MetadataTableRecordExpiration build() {
            return new MetadataTableRecordExpiration(this);
        }
    }
}
