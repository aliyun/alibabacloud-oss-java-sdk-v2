package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class UpdateTableMetadataLocationRequest extends TableRequestModel {
    private final String tableBucketARN;
    private final String namespace;
    private final String name;

    private UpdateTableMetadataLocationRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
        this.namespace = builder.namespace;
        this.name = builder.name;
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
        return name;
    }

    public String versionToken() {
        return (String) this.bodyFields.get("versionToken");
    }

    public String metadataLocation() {
        return (String) this.bodyFields.get("metadataLocation");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;
        private String namespace;
        private String name;

        private Builder() {
            super();
        }

        private Builder(UpdateTableMetadataLocationRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
            this.namespace = from.namespace;
            this.name = from.name;
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
            requireNonNull(v);
            this.name = v;
            return this;
        }

        public Builder versionToken(String v) {
            this.bodyFields.put("versionToken", v);
            return this;
        }

        public Builder metadataLocation(String v) {
            this.bodyFields.put("metadataLocation", v);
            return this;
        }

        public UpdateTableMetadataLocationRequest build() {
            return new UpdateTableMetadataLocationRequest(this);
        }
    }
}
