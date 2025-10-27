package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketTags operation.
 */
public final class DeleteBucketTagsResult extends ResultModel { 

    DeleteBucketTagsResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketTagsResult build() {
            return new DeleteBucketTagsResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketTagsResult result) {
            super(result);
        }
    }
}
