package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import static java.util.Objects.requireNonNull;

/**
 * The configuration of a metadata journal table.
 */
@JacksonXmlRootElement(localName = "JournalTableConfiguration")
public final class JournalTableConfiguration {
    @JacksonXmlProperty(localName = "RecordExpiration")
    private MetadataTableRecordExpiration recordExpiration;

    @JacksonXmlProperty(localName = "EncryptionConfiguration")
    private MetadataTableEncryptionConfiguration encryptionConfiguration;

    public JournalTableConfiguration() {
    }

    private JournalTableConfiguration(Builder builder) {
        this.recordExpiration = builder.recordExpiration;
        this.encryptionConfiguration = builder.encryptionConfiguration;
    }

    public MetadataTableRecordExpiration recordExpiration() {
        return recordExpiration;
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
        private MetadataTableRecordExpiration recordExpiration;
        private MetadataTableEncryptionConfiguration encryptionConfiguration;

        private Builder() {
        }

        private Builder(JournalTableConfiguration value) {
            this.recordExpiration = value.recordExpiration;
            this.encryptionConfiguration = value.encryptionConfiguration;
        }

        public Builder recordExpiration(MetadataTableRecordExpiration value) {
            this.recordExpiration = requireNonNull(value);
            return this;
        }

        public Builder encryptionConfiguration(MetadataTableEncryptionConfiguration value) {
            this.encryptionConfiguration = requireNonNull(value);
            return this;
        }

        public JournalTableConfiguration build() {
            return new JournalTableConfiguration(this);
        }
    }
}
