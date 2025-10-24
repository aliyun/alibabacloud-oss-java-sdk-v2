package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketLifecycle operation.
 */
public final class DeleteBucketLifecycleResult extends ResultModel { 

    DeleteBucketLifecycleResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketLifecycleResult build() {
            return new DeleteBucketLifecycleResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketLifecycleResult result) {
            super(result);
        }
    }
}
