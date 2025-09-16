package com.aliyun.sdk.service.oss2.vectors.models;


import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.BucketInfoJson;

/**
 * The result for the GetVectorBucket operation.
 */
public final class GetVectorBucketResult extends ResultModel {

    GetVectorBucketResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * BucketInfoJson defines Bucket information.
     */
    public BucketInfoJson bucketInfoJson() {
        return (BucketInfoJson) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetVectorBucketResult result) {
            super(result);
        }

        public GetVectorBucketResult build() {
            return new GetVectorBucketResult(this);
        }
    }
}

