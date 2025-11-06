package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketResourceGroup operation.
 */
public final class PutBucketResourceGroupResult extends ResultModel {

    PutBucketResourceGroupResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketResourceGroupResult build() {
            return new PutBucketResourceGroupResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketResourceGroupResult result) {
            super(result);
        }
    }
}
