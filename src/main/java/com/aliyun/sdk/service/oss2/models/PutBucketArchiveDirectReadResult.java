package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketArchiveDirectRead operation.
 */
public final class PutBucketArchiveDirectReadResult extends ResultModel { 

    PutBucketArchiveDirectReadResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketArchiveDirectReadResult build() {
            return new PutBucketArchiveDirectReadResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketArchiveDirectReadResult result) {
            super(result);
        }
    }
}
