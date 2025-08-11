package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the UploadPartCopy operation.
 */
public final class UploadPartCopyResult extends ResultModel {

    UploadPartCopyResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The version ID of the source object.
     */
    public String copySourceVersionId() {
        return headers.get("x-oss-copy-source-version-id");
    }

    /**
     * The container that stores the copy result.
     */
    public CopyPartResult copyPartResult() {
        return (CopyPartResult) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(UploadPartCopyResult result) {
            super(result);
        }

        public UploadPartCopyResult build() {
            return new UploadPartCopyResult(this);
        }
    }
}
