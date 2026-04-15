package com.aliyun.sdk.service.oss2.tables.models;


import static java.util.Objects.requireNonNull;

public final class GetTableRequest extends TableRequestModel {
    private final String tableBucketARN;
    private final String namespace;
    private final String name;
    private final String tableArn;

    private GetTableRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
        this.namespace = builder.namespace;
        this.name = builder.name;
        this.tableArn = builder.tableArn;
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

    public String tableArn() {
        return tableArn;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;
        private String namespace;
        private String name;
        private String tableArn;

        private Builder() {
            super();
        }

        private Builder(GetTableRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
            this.namespace = from.namespace;
            this.name = from.name;
            this.tableArn = from.tableArn;
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

        public Builder tableArn(String v) {
            requireNonNull(v);
            this.tableArn = v;
            return this;
        }

        public GetTableRequest build() {
            return new GetTableRequest(this);
        }
    }
}