package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The configuration of a metadata inventory table.
 */
@JacksonXmlRootElement(localName = "InventoryTableConfiguration")
public final class InventoryTableConfiguration {
    @JacksonXmlProperty(localName = "ConfigurationState")
    private String configurationState;

    @JacksonXmlProperty(localName = "EncryptionConfiguration")
    private MetadataTableEncryptionConfiguration encryptionConfiguration;

    public InventoryTableConfiguration() {
    }

    private InventoryTableConfiguration(Builder builder) {
        this.configurationState = builder.configurationState;
        this.encryptionConfiguration = builder.encryptionConfiguration;
    }

    public String configurationState() {
        return configurationState;
    }

    public MetadataTableEncryptionConfiguration encryptionConfiguration() {
        return encryptionConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder {
        private String configurationState;
        private MetadataTableEncryptionConfiguration encryptionConfiguration;

        private Builder() {
        }

        private Builder(InventoryTableConfiguration value) {
            this.configurationState = value.configurationState;
            this.encryptionConfiguration = value.encryptionConfiguration;
        }

        /**
         * Sets the state of the inventory metadata table.
         * Valid values: ENABLED, DISABLED.
         */
        public Builder configurationState(String value) {
            this.configurationState = requireNonNull(value);
            return this;
        }

        public Builder encryptionConfiguration(MetadataTableEncryptionConfiguration value) {
            this.encryptionConfiguration = requireNonNull(value);
            return this;
        }

        public InventoryTableConfiguration build() {
            return new InventoryTableConfiguration(this);
        }
    }
}
