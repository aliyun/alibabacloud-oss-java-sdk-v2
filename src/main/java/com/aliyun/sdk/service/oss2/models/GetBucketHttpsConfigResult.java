package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

/**
 * The result for the GetBucketHttpsConfig operation.
 */
public final class GetBucketHttpsConfigResult extends ResultModel { 

    /**
     * The container that stores HTTPS configurations.
     */
    public HttpsConfiguration httpsConfiguration() {
        return (HttpsConfiguration)innerBody;
    } 
     

    GetBucketHttpsConfigResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketHttpsConfigResult build() {
            return new GetBucketHttpsConfigResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketHttpsConfigResult result) {
            super(result);
        }
    }
}
