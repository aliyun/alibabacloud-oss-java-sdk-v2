package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketCors operation.
 */
public final class PutBucketCorsResult extends ResultModel {

    PutBucketCorsResult(Builder builder) {
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

        private Builder(PutBucketCorsResult result) {
            super(result);
        }

        public PutBucketCorsResult build() {
            return new PutBucketCorsResult(this);
        }
    }
}
