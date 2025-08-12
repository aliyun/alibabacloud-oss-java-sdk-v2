package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketAcl operation.
 */
public final class PutBucketAclResult extends ResultModel { 

    PutBucketAclResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketAclResult build() {
            return new PutBucketAclResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketAclResult result) {
            super(result);
        }
    }
}
