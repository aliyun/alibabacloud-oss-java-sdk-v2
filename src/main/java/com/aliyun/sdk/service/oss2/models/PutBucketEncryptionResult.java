package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketEncryption operation.
 */
public final class PutBucketEncryptionResult extends ResultModel { 

    PutBucketEncryptionResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketEncryptionResult build() {
            return new PutBucketEncryptionResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketEncryptionResult result) {
            super(result);
        }
    }
}
