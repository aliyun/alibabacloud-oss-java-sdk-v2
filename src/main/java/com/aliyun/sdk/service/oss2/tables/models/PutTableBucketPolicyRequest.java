package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class PutTableBucketPolicyRequest extends TableRequestModel {
    private final String tableBucketARN;

    private PutTableBucketPolicyRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableBucketARN() {
        return tableBucketARN;
    }

    public String resourcePolicy() {
        return (String) this.bodyFields.get("resourcePolicy");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;

        private Builder() {
            super();
        }

        private Builder(PutTableBucketPolicyRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
        }

        public Builder tableBucketARN(String value) {
            requireNonNull(value);
            this.tableBucketARN = value;
            return this;
        }

        public Builder resourcePolicy(String value) {
            this.bodyFields.put("resourcePolicy", value);
            return this;
        }

        public PutTableBucketPolicyRequest build() {
            return new PutTableBucketPolicyRequest(this);
        }
    }
}
