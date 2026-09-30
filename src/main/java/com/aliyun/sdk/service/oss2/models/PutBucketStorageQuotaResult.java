package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketStorageQuota operation.
 */
public final class PutBucketStorageQuotaResult extends ResultModel {

    PutBucketStorageQuotaResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketStorageQuotaResult build() {
            return new PutBucketStorageQuotaResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketStorageQuotaResult result) {
            super(result);
        }
    }
}
