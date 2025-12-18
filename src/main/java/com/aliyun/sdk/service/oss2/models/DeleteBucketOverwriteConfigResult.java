package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketOverwriteConfig operation.
 */
public final class DeleteBucketOverwriteConfigResult extends ResultModel { 

    DeleteBucketOverwriteConfigResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketOverwriteConfigResult build() {
            return new DeleteBucketOverwriteConfigResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketOverwriteConfigResult result) {
            super(result);
        }
    }
}
