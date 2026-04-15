package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class PutTableBucketEncryptionRequest extends TableRequestModel {
    private final String tableBucketARN;

    private PutTableBucketEncryptionRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableBucketARN() {
        return tableBucketARN;
    }

    public EncryptionConfiguration encryptionConfiguration() {
        return (EncryptionConfiguration) this.bodyFields.get("encryptionConfiguration");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;

        private Builder() {
            super();
        }

        private Builder(PutTableBucketEncryptionRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
        }

        public Builder tableBucketARN(String value) {
            requireNonNull(value);
            this.tableBucketARN = value;
            return this;
        }

        public Builder encryptionConfiguration(EncryptionConfiguration value) {
            this.bodyFields.put("encryptionConfiguration", value);
            return this;
        }

        public PutTableBucketEncryptionRequest build() {
            return new PutTableBucketEncryptionRequest(this);
        }
    }
}
