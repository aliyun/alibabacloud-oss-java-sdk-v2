package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketPolicy operation.
 */
public final class PutBucketPolicyResult extends ResultModel { 

    PutBucketPolicyResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketPolicyResult build() {
            return new PutBucketPolicyResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketPolicyResult result) {
            super(result);
        }
    }
}
