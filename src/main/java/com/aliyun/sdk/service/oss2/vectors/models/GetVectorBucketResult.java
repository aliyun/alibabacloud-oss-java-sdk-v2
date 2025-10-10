package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.GetVectorBucketResultJson;

import java.util.Optional;

/**
 * The result for the GetVectorBucket operation.
 */
public final class GetVectorBucketResult extends ResultModel {
    private final GetVectorBucketResultJson delegate;

    private GetVectorBucketResult(Builder builder) {
        super(builder);
        this.delegate = (GetVectorBucketResultJson) Optional
                .ofNullable(innerBody)
                .orElse(new GetVectorBucketResultJson());;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Gets the vector bucket information.
     */
    public VectorBucketInfo bucketInfo() {
        return delegate.bucketInfo;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetVectorBucketResult from) {
            super(from);
        }

        public GetVectorBucketResult build() {
            return new GetVectorBucketResult(this);
        }
    }
}