package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutObject operation.
 */
public final class PutObjectResult extends ResultModel {

    PutObjectResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Content-Md5 for the uploaded object.
     */
    public String contentMd5() {
        String value = headers.get("Content-Md5");
        return value;
    }

    /**
     * Entity tag for the uploaded object.
     */
    public String eTag() {
        String value = headers.get("ETag");
        return value;
    }

    /**
     * The 64-bit CRC value of the object.
     * This value is calculated based on the ECMA-182 standard.
     */
    public String hashCrc64ecma() {
        String value = headers.get("x-oss-hash-crc64ecma");
        return value;
    }

    /**
     * Version of the object.
     */
    public String versionId() {
        String value = headers.get("x-oss-version-id");
        return value;
    }

    /**
     * Callback result, it is valid only when the callback is set.
     */
    public String callbackResult() {
        return (String) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(PutObjectResult result) {
            super(result);
        }

        public PutObjectResult build() {
            return new PutObjectResult(this);
        }
    }
}
