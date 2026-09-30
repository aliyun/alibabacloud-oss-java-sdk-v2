package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import static java.util.Objects.requireNonNull;

/**
 * The encryption configuration of a metadata table.
 */
public final class MetadataTableEncryptionConfiguration {
    @JacksonXmlProperty(localName = "SseAlgorithm")
    private String sseAlgorithm;

    @JacksonXmlProperty(localName = "KmsKeyArn")
    private String kmsKeyArn;

    public MetadataTableEncryptionConfiguration() {
    }

    private MetadataTableEncryptionConfiguration(Builder builder) {
        this.sseAlgorithm = builder.sseAlgorithm;
        this.kmsKeyArn = builder.kmsKeyArn;
    }

    public String sseAlgorithm() {
        return sseAlgorithm;
    }

    public String kmsKeyArn() {
        return kmsKeyArn;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder {
        private String sseAlgorithm;
        private String kmsKeyArn;

        private Builder() {
        }

        private Builder(MetadataTableEncryptionConfiguration value) {
            this.sseAlgorithm = value.sseAlgorithm;
            this.kmsKeyArn = value.kmsKeyArn;
        }

        /**
         * Sets the server-side encryption algorithm of a metadata table.
         * Valid values: AES256, oss:kms.
         */
        public Builder sseAlgorithm(String value) {
            this.sseAlgorithm = requireNonNull(value);
            return this;
        }

        public Builder kmsKeyArn(String value) {
            this.kmsKeyArn = requireNonNull(value);
            return this;
        }

        public MetadataTableEncryptionConfiguration build() {
            return new MetadataTableEncryptionConfiguration(this);
        }
    }
}
