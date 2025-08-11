package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the AbortMultipartUpload operation.
 */
public final class AbortMultipartUploadResult extends ResultModel {

    AbortMultipartUploadResult(Builder builder) {
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

        private Builder(AbortMultipartUploadResult result) {
            super(result);
        }

        public AbortMultipartUploadResult build() {
            return new AbortMultipartUploadResult(this);
        }
    }
}
