package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketOverwriteConfig operation.
 */
public final class PutBucketOverwriteConfigResult extends ResultModel {

    PutBucketOverwriteConfigResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(PutBucketOverwriteConfigResult result) {
            super(result);
        }

        public PutBucketOverwriteConfigResult build() {
            return new PutBucketOverwriteConfigResult(this);
        }
    }
}
