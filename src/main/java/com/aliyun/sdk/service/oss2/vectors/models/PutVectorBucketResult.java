package com.aliyun.sdk.service.oss2.vectors.models;


import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PutVectorBucket operation.
 */
public final class PutVectorBucketResult extends ResultModel {

    PutVectorBucketResult(Builder builder) {
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

        private Builder(PutVectorBucketResult result) {
            super(result);
        }

        public PutVectorBucketResult build() {
            return new PutVectorBucketResult(this);
        }
    }
}
