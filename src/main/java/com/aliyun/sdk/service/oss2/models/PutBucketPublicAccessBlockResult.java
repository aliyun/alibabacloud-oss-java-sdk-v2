package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketPublicAccessBlock operation.
 */
public final class PutBucketPublicAccessBlockResult extends ResultModel { 

    PutBucketPublicAccessBlockResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketPublicAccessBlockResult build() {
            return new PutBucketPublicAccessBlockResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketPublicAccessBlockResult result) {
            super(result);
        }
    }
}
