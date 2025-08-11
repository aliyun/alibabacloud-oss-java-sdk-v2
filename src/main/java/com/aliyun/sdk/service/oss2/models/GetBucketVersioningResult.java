package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

/**
 * The result for the GetBucketVersioning operation.
 */
public final class GetBucketVersioningResult extends ResultModel { 

    /**
     * The container that stores the versioning state of the bucket.
     */
    public VersioningConfiguration versioningConfiguration() {
        return (VersioningConfiguration)innerBody;
    } 
     

    GetBucketVersioningResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketVersioningResult build() {
            return new GetBucketVersioningResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketVersioningResult result) {
            super(result);
        }
    }
}
