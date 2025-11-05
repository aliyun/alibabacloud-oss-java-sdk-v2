package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeletePublicAccessBlock operation.
 */
public final class DeletePublicAccessBlockResult extends ResultModel { 

    DeletePublicAccessBlockResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeletePublicAccessBlockResult build() {
            return new DeletePublicAccessBlockResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeletePublicAccessBlockResult result) {
            super(result);
        }
    }
}
