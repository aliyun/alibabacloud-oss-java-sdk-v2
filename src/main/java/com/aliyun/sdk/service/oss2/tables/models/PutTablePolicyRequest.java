package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class PutTablePolicyRequest extends TableRequestModel {
    private final String tableBucketARN;
    private final String namespace;
    private final String name;

    private PutTablePolicyRequest(Builder builder) {
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

    public String resourcePolicy() {
        return (String) this.bodyFields.get("resourcePolicy");
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

        private Builder(PutTablePolicyRequest from) {
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

        public Builder resourcePolicy(String v) {
            this.bodyFields.put("resourcePolicy", v);
            return this;
        }

        public PutTablePolicyRequest build() {
            return new PutTablePolicyRequest(this);
        }
    }
}
