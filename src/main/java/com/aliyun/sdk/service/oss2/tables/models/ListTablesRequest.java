package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class ListTablesRequest extends TableRequestModel {
    private final String tableBucketARN;

    private ListTablesRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableBucketARN() {
        return tableBucketARN;
    }

    public String namespace() {
        return this.parameters.get("namespace");
    }

    public String prefix() {
        return this.parameters.get("prefix");
    }

    public String continuationToken() {
        return this.parameters.get("continuationToken");
    }

    public String maxTables() {
        return this.parameters.get("maxTables");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;

        private Builder() {
            super();
        }

        private Builder(ListTablesRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
        }

        public Builder tableBucketARN(String v) {
            requireNonNull(v);
            this.tableBucketARN = v;
            return this;
        }

        public Builder namespace(String v) {
            this.parameters.put("namespace", v);
            return this;
        }

        public Builder prefix(String v) {
            this.parameters.put("prefix", v);
            return this;
        }

        public Builder continuationToken(String v) {
            this.parameters.put("continuationToken", v);
            return this;
        }

        public Builder maxTables(Integer v) {
            this.parameters.put("maxTables", v != null ? v.toString() : null);
            return this;
        }

        public ListTablesRequest build() {
            return new ListTablesRequest(this);
        }
    }
}