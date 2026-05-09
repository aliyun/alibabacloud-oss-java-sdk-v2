package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketHttpsConfig operation.
 */
public final class PutBucketHttpsConfigResult extends ResultModel { 

    PutBucketHttpsConfigResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketHttpsConfigResult build() {
            return new PutBucketHttpsConfigResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketHttpsConfigResult result) {
            super(result);
        }
    }
}
