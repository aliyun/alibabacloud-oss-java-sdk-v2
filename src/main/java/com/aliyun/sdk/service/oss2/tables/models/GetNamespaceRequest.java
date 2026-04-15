package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class GetNamespaceRequest extends TableRequestModel {
    private final String tableBucketARN;
    private final String namespace;

    private GetNamespaceRequest(Builder builder) {
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

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;
        private String namespace;

        private Builder() {
            super();
        }

        private Builder(GetNamespaceRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
            this.namespace = from.namespace;
        }

        public Builder tableBucketARN(String value) {
            requireNonNull(value);
            this.tableBucketARN = value;
            return this;
        }

        public Builder namespace(String value) {
            requireNonNull(value);
            this.namespace = value;
            return this;
        }

        public GetNamespaceRequest build() {
            return new GetNamespaceRequest(this);
        }
    }
}
