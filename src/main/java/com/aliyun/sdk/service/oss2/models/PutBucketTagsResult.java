package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketTags operation.
 */
public final class PutBucketTagsResult extends ResultModel { 

    PutBucketTagsResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketTagsResult build() {
            return new PutBucketTagsResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketTagsResult result) {
            super(result);
        }
    }
}
