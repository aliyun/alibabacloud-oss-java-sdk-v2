package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class CreateTableRequest extends TableRequestModel {
    private final String tableBucketARN;
    private final String namespace;

    private CreateTableRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
        this.namespace = builder.namespace;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableBucketARN() {
        return tableBucketARN;
    }

    public String namespace() {
        return namespace;
    }

    public String name() {
        return (String) this.bodyFields.get("name");
    }

    public String format() {
        return (String) this.bodyFields.get("format");
    }

    public TableMetadata metadata() {
        return (TableMetadata) this.bodyFields.get("metadata");
    }

    public EncryptionConfiguration encryptionConfiguration() {
        return (EncryptionConfiguration) this.bodyFields.get("encryptionConfiguration");
    }


    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;
        private String namespace;

        private Builder() {
            super();
        }

        private Builder(CreateTableRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
            this.namespace = from.namespace;
        }

        public Builder tableBucketARN(String v) {
            requireNonNull(v);
            this.tableBucketARN = v;
            return this;
        }

        public Builder namespace(String v) {
            requireNonNull(v);
            this.namespace = v;
            return this;
        }

        public Builder name(String v) {
            this.bodyFields.put("name", v);
            return this;
        }

        public Builder format(String v) {
            this.bodyFields.put("format", v);
            return this;
        }

        public Builder metadata(TableMetadata v) {
            this.bodyFields.put("metadata", v);
            return this;
        }

        public Builder encryptionConfiguration(EncryptionConfiguration v) {
            this.bodyFields.put("encryptionConfiguration", v);
            return this;
        }


        public CreateTableRequest build() {
            return new CreateTableRequest(this);
        }
    }
}
