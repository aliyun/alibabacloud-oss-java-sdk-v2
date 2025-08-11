package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

/**
 * The result for the PutBucketVersioning operation.
 */
public final class PutBucketVersioningResult extends ResultModel { 

    PutBucketVersioningResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketVersioningResult build() {
            return new PutBucketVersioningResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketVersioningResult result) {
            super(result);
        }
    }
}
