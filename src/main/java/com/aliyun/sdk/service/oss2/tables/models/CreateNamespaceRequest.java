package com.aliyun.sdk.service.oss2.tables.models;

import java.util.List;

import static java.util.Objects.requireNonNull;

public final class CreateNamespaceRequest extends TableRequestModel {
    private final String tableBucketARN;

    private CreateNamespaceRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableBucketARN() {
        return tableBucketARN;
    }

    @SuppressWarnings("unchecked")
    public List<String> namespace() {
        return (List<String>) this.bodyFields.get("namespace");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;

        private Builder() {
            super();
        }

        private Builder(CreateNamespaceRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
        }

        public Builder tableBucketARN(String value) {
            requireNonNull(value);
            this.tableBucketARN = value;
            return this;
        }

        public Builder namespace(List<String> value) {
            this.bodyFields.put("namespace", value);
            return this;
        }

        public CreateNamespaceRequest build() {
            return new CreateNamespaceRequest(this);
        }
    }
}
