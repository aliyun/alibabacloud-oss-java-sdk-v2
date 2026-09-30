package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The metadata table configuration of a bucket.
 */
@JacksonXmlRootElement(localName = "MetadataConfiguration")
public final class MetadataConfiguration {
    @JacksonXmlProperty(localName = "JournalTableConfiguration")
    private JournalTableConfiguration journalTableConfiguration;

    @JacksonXmlProperty(localName = "InventoryTableConfiguration")
    private InventoryTableConfiguration inventoryTableConfiguration;

    public MetadataConfiguration() {
    }

    private MetadataConfiguration(Builder builder) {
        this.journalTableConfiguration = builder.journalTableConfiguration;
        this.inventoryTableConfiguration = builder.inventoryTableConfiguration;
    }

    public JournalTableConfiguration journalTableConfiguration() {
        return journalTableConfiguration;
    }

    public InventoryTableConfiguration inventoryTableConfiguration() {
        return inventoryTableConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder {
        private JournalTableConfiguration journalTableConfiguration;
        private InventoryTableConfiguration inventoryTableConfiguration;

        private Builder() {
        }

        private Builder(MetadataConfiguration value) {
            this.journalTableConfiguration = value.journalTableConfiguration;
            this.inventoryTableConfiguration = value.inventoryTableConfiguration;
        }

        public Builder journalTableConfiguration(JournalTableConfiguration value) {
            this.journalTableConfiguration = requireNonNull(value);
            return this;
        }

        public Builder inventoryTableConfiguration(InventoryTableConfiguration value) {
            this.inventoryTableConfiguration = requireNonNull(value);
            return this;
        }

        public MetadataConfiguration build() {
            return new MetadataConfiguration(this);
        }
    }
}
