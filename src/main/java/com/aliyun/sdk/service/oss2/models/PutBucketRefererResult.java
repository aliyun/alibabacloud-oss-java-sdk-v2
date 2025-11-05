package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketReferer operation.
 */
public final class PutBucketRefererResult extends ResultModel { 

    PutBucketRefererResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketRefererResult build() {
            return new PutBucketRefererResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketRefererResult result) {
            super(result);
        }
    }
}
