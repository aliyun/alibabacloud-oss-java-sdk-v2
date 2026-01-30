package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the CompleteBucketWorm operation.
 */
public final class CompleteBucketWormResult extends ResultModel { 

    CompleteBucketWormResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public CompleteBucketWormResult build() {
            return new CompleteBucketWormResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(CompleteBucketWormResult result) {
            super(result);
        }
    }
}
