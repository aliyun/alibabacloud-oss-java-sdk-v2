package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketCors operation.
 */
public final class DeleteBucketCorsResult extends ResultModel {

    DeleteBucketCorsResult(Builder builder) {
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

        private Builder(DeleteBucketCorsResult result) {
            super(result);
        }

        public DeleteBucketCorsResult build() {
            return new DeleteBucketCorsResult(this);
        }
    }
}
