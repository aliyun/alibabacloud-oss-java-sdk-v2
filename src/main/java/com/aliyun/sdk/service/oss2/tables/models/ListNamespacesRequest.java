package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class ListNamespacesRequest extends TableRequestModel {
    private final String tableBucketARN;

    private ListNamespacesRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableBucketARN() {
        return tableBucketARN;
    }

    public String prefix() {
        return this.parameters.get("prefix");
    }

    public String continuationToken() {
        return this.parameters.get("continuationToken");
    }

    public Integer maxNamespaces() {
        String value = this.parameters.get("maxNamespaces");
        return value != null ? Integer.valueOf(value) : null;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;

        private Builder() {
            super();
        }

        private Builder(ListNamespacesRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
        }

        public Builder tableBucketARN(String value) {
            requireNonNull(value);
            this.tableBucketARN = value;
            return this;
        }

        public Builder prefix(String value) {
            this.parameters.put("prefix", value);
            return this;
        }

        public Builder continuationToken(String value) {
            this.parameters.put("continuationToken", value);
            return this;
        }

        public Builder maxNamespaces(Integer value) {
            this.parameters.put("maxNamespaces", value != null ? value.toString() : null);
            return this;
        }

        public ListNamespacesRequest build() {
            return new ListNamespacesRequest(this);
        }
    }
}
