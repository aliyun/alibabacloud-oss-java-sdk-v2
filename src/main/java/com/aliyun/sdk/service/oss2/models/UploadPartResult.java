package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the UploadPart operation.
 */
public final class UploadPartResult extends ResultModel {

    UploadPartResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Entity tag for the uploaded part.
     */
    public String contentMD5() {
        return headers.get("Content-MD5");
    }

    /**
     * The MD5 hash of the part that you want to upload.
     */
    public String eTag() {
        return headers.get("ETag");
    }

    /**
     * The 64-bit CRC value of the part. This value is calculated based on the ECMA-182 standard.
     */
    public String hashCRC64() {
        return headers.get("x-oss-hash-crc64ecma");
    }


    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(UploadPartResult result) {
            super(result);
        }

        public UploadPartResult build() {
            return new UploadPartResult(this);
        }
    }
}
