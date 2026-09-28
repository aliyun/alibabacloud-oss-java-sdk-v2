package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.BucketStorageQuotaConfiguration;
import com.aliyun.sdk.service.oss2.models.ResultModel;

/**
 * The result for the GetAgenticBucketStorageQuota operation.
 */
public final class GetAgenticBucketStorageQuotaResult extends ResultModel {

    /**
     * The container that stores the agentic bucket storage quota configuration.
     */
    public BucketStorageQuotaConfiguration bucketStorageQuotaConfiguration() {
        return (BucketStorageQuotaConfiguration) innerBody;
    }

    GetAgenticBucketStorageQuotaResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetAgenticBucketStorageQuotaResult build() {
            return new GetAgenticBucketStorageQuotaResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetAgenticBucketStorageQuotaResult result) {
            super(result);
        }
    }
}
