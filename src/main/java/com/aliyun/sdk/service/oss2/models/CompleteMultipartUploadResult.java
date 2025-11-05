package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the CompleteMultipartUpload operation.
 */
public final class CompleteMultipartUploadResult extends ResultModel {

    CompleteMultipartUploadResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The 64-bit CRC value of the object. This value is calculated based on the ECMA-182 standard.
     */
    public String hashCRC64() {
        return headers.get("x-oss-hash-crc64ecma");
    }

    /**
     * Version of the object.
     */
    public String versionId() {
        return headers.get("x-oss-version-id");
    }

    /**
     * The container that stores the copy result.
     */
    public CompleteMultipartUploadResultXml completeMultipartUpload() {
        if (innerBody instanceof CompleteMultipartUploadResultXml) {
            return (CompleteMultipartUploadResultXml) innerBody;
        }
        return null;
    }

    /**
     * Callback result, it is valid only when the callback is set.
     */
    public String callbackResult() {
        if (innerBody instanceof String) {
            return (String) innerBody;
        }
        return null;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(CompleteMultipartUploadResult result) {
            super(result);
        }

        public CompleteMultipartUploadResult build() {
            return new CompleteMultipartUploadResult(this);
        }
    }
}
