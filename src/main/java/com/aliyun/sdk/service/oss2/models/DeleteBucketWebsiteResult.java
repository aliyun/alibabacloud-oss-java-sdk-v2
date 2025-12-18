package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

/**
 * The result for the DeleteBucketWebsite operation.
 */
public final class DeleteBucketWebsiteResult extends ResultModel { 

    DeleteBucketWebsiteResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketWebsiteResult build() {
            return new DeleteBucketWebsiteResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketWebsiteResult result) {
            super(result);
        }
    }
}
