package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketWebsite operation.
 */
public final class PutBucketWebsiteResult extends ResultModel { 

    PutBucketWebsiteResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketWebsiteResult build() {
            return new PutBucketWebsiteResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketWebsiteResult result) {
            super(result);
        }
    }
}
