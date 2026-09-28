package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the PutAgenticBucketStorageQuota operation.
 */
public final class PutAgenticBucketStorageQuotaResult extends ResultModel {

    PutAgenticBucketStorageQuotaResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutAgenticBucketStorageQuotaResult build() {
            return new PutAgenticBucketStorageQuotaResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutAgenticBucketStorageQuotaResult result) {
            super(result);
        }
    }
}
