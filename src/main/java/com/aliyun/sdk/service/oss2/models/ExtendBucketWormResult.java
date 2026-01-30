package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the ExtendBucketWorm operation.
 */
public final class ExtendBucketWormResult extends ResultModel { 

    ExtendBucketWormResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public ExtendBucketWormResult build() {
            return new ExtendBucketWormResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(ExtendBucketWormResult result) {
            super(result);
        }
    }
}
