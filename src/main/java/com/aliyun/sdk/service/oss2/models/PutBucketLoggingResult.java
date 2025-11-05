package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketLogging operation.
 */
public final class PutBucketLoggingResult extends ResultModel { 

    PutBucketLoggingResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketLoggingResult build() {
            return new PutBucketLoggingResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketLoggingResult result) {
            super(result);
        }
    }
}
