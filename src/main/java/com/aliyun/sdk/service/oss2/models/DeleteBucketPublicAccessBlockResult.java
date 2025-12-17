package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketPublicAccessBlock operation.
 */
public final class DeleteBucketPublicAccessBlockResult extends ResultModel { 

    DeleteBucketPublicAccessBlockResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketPublicAccessBlockResult build() {
            return new DeleteBucketPublicAccessBlockResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketPublicAccessBlockResult result) {
            super(result);
        }
    }
}
