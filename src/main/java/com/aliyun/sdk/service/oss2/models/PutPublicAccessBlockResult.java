package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutPublicAccessBlock operation.
 */
public final class PutPublicAccessBlockResult extends ResultModel { 

    PutPublicAccessBlockResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutPublicAccessBlockResult build() {
            return new PutPublicAccessBlockResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutPublicAccessBlockResult result) {
            super(result);
        }
    }
}
