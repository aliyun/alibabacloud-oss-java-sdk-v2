package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketStorageQuota operation.
 */
public final class DeleteBucketStorageQuotaResult extends ResultModel {

    DeleteBucketStorageQuotaResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketStorageQuotaResult build() {
            return new DeleteBucketStorageQuotaResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketStorageQuotaResult result) {
            super(result);
        }
    }
}
