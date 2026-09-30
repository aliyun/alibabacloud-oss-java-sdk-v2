package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the DeleteAgenticBucketStorageQuota operation.
 */
public final class DeleteAgenticBucketStorageQuotaResult extends ResultModel {

    DeleteAgenticBucketStorageQuotaResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteAgenticBucketStorageQuotaResult build() {
            return new DeleteAgenticBucketStorageQuotaResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteAgenticBucketStorageQuotaResult result) {
            super(result);
        }
    }
}
