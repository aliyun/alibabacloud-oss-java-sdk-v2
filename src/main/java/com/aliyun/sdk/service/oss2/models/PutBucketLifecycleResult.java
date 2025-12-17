package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketLifecycle operation.
 */
public final class PutBucketLifecycleResult extends ResultModel { 

    PutBucketLifecycleResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketLifecycleResult build() {
            return new PutBucketLifecycleResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketLifecycleResult result) {
            super(result);
        }
    }
}
