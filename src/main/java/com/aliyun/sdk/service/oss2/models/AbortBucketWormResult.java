package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the AbortBucketWorm operation.
 */
public final class AbortBucketWormResult extends ResultModel { 

    AbortBucketWormResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public AbortBucketWormResult build() {
            return new AbortBucketWormResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(AbortBucketWormResult result) {
            super(result);
        }
    }
}
