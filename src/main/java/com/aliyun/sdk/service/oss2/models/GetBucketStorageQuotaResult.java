package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketStorageQuota operation.
 */
public final class GetBucketStorageQuotaResult extends ResultModel {

    /**
     * The container that stores the bucket storage quota configuration.
     */
    public BucketStorageQuotaConfiguration bucketStorageQuotaConfiguration() {
        return (BucketStorageQuotaConfiguration) innerBody;
    }

    GetBucketStorageQuotaResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketStorageQuotaResult build() {
            return new GetBucketStorageQuotaResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketStorageQuotaResult result) {
            super(result);
        }
    }
}
