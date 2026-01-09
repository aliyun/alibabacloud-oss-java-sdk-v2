package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketInventory operation.
 */
public final class PutBucketInventoryResult extends ResultModel { 

    PutBucketInventoryResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketInventoryResult build() {
            return new PutBucketInventoryResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketInventoryResult result) {
            super(result);
        }
    }
}
