package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucket operation.
 */
public final class DeleteBucketResult extends ResultModel {

    DeleteBucketResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(DeleteBucketResult result) {
            super(result);
        }

        public DeleteBucketResult build() {
            return new DeleteBucketResult(this);
        }
    }
}
