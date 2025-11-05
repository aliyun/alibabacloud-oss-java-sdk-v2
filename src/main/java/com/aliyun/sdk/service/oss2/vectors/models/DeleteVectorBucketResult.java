package com.aliyun.sdk.service.oss2.vectors.models;


import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the DeleteVectorBucket operation.
 */
public final class DeleteVectorBucketResult extends ResultModel {

    DeleteVectorBucketResult(Builder builder) {
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

        private Builder(DeleteVectorBucketResult result) {
            super(result);
        }

        public DeleteVectorBucketResult build() {
            return new DeleteVectorBucketResult(this);
        }
    }
}

