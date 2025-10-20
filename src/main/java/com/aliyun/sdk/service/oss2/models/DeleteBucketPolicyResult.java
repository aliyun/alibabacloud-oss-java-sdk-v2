package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketPolicy operation.
 */
public final class DeleteBucketPolicyResult extends ResultModel { 

    DeleteBucketPolicyResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketPolicyResult build() {
            return new DeleteBucketPolicyResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketPolicyResult result) {
            super(result);
        }
    }
}
