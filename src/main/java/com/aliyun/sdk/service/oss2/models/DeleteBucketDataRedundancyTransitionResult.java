package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketDataRedundancyTransition operation.
 */
public final class DeleteBucketDataRedundancyTransitionResult extends ResultModel { 

    DeleteBucketDataRedundancyTransitionResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketDataRedundancyTransitionResult build() {
            return new DeleteBucketDataRedundancyTransitionResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketDataRedundancyTransitionResult result) {
            super(result);
        }
    }
}
