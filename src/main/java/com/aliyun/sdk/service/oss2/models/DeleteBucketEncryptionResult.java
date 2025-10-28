package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketEncryption operation.
 */
public final class DeleteBucketEncryptionResult extends ResultModel { 

    DeleteBucketEncryptionResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketEncryptionResult build() {
            return new DeleteBucketEncryptionResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketEncryptionResult result) {
            super(result);
        }
    }
}
