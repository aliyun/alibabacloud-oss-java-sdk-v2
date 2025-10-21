package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketLogging operation.
 */
public final class DeleteBucketLoggingResult extends ResultModel { 

    DeleteBucketLoggingResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketLoggingResult build() {
            return new DeleteBucketLoggingResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketLoggingResult result) {
            super(result);
        }
    }
}
