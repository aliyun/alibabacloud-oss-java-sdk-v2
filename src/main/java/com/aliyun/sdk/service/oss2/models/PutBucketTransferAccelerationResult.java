package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

/**
 * The result for the PutBucketTransferAcceleration operation.
 */
public final class PutBucketTransferAccelerationResult extends ResultModel { 

    PutBucketTransferAccelerationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketTransferAccelerationResult build() {
            return new PutBucketTransferAccelerationResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketTransferAccelerationResult result) {
            super(result);
        }
    }
}
