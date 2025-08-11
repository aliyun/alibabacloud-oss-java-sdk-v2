package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the InitiateMultipartUpload operation.
 */
public final class InitiateMultipartUploadResult extends ResultModel {

    InitiateMultipartUploadResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The container that stores the copy result.
     */
    public InitiateMultipartUpload initiateMultipartUpload() {
        return (InitiateMultipartUpload) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(InitiateMultipartUploadResult result) {
            super(result);
        }

        public InitiateMultipartUploadResult build() {
            return new InitiateMultipartUploadResult(this);
        }
    }
}
