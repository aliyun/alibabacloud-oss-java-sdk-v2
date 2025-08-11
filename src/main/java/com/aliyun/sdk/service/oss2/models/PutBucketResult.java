package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucket operation.
 */
public final class PutBucketResult extends ResultModel {

    PutBucketResult(Builder builder) {
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

        private Builder(PutBucketResult result) {
            super(result);
        }

        public PutBucketResult build() {
            return new PutBucketResult(this);
        }
    }
}
