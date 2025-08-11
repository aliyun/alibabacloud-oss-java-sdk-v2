package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

/**
 * The result for the AppendObject operation.
 */
public final class AppendObjectResult extends ResultModel {

    AppendObjectResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The position that must be provided in the next request,
     */
    public Long nextAppendPosition() {
        String value = headers.get("x-oss-next-append-position");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The 64-bit CRC value of the object.
     */
    public String hashCrc64ecma() {
        String value = headers.get("x-oss-hash-crc64ecma");
        return value;
    }

    /**
     * The version ID of the object for which you want to query metadata.
     */
    public String versionId() {
        String value = headers.get("versionId");
        return value;
    }

    /**
     * The method used to encrypt objects on the specified OSS server. Valid values:- AES256: Keys managed by OSS are used for encryption and decryption (SSE-OSS). - KMS: Keys managed by Key Management Service (KMS) are used for encryption and decryption. - SM4: The SM4 block cipher algorithm is used for encryption and decryption.
     */
    public String serverSideEncryption() {
        String value = headers.get("x-oss-server-side-encryption");
        return value;
    }

    /**
     * The server side data encryption algorithm. Invalid value: SM4
     */
    public String serverSideDataEncryption() {
        String value = headers.get("x-oss-server-side-data-encryption");
        return value;
    }

    /**
     * The ID of the customer master key (CMK) that is managed by KMS. This parameter is available only if you set **x-oss-server-side-encryption** to KMS.
     */
    public String serverSideEncryptionKeyId() {
        String value = headers.get("x-oss-server-side-encryption-key-id");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(AppendObjectResult result) {
            super(result);
        }

        public AppendObjectResult build() {
            return new AppendObjectResult(this);
        }
    }
}
