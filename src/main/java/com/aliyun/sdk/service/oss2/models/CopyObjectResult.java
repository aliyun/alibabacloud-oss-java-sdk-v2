package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CopyObjectResultXml;

/**
 * The result for the CopyObject operation.
 */
public final class CopyObjectResult extends ResultModel {

    CopyObjectResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The version ID of the source object.
     */
    public String copySourceVersionId() {
        String value = headers.get("x-oss-copy-source-version-id");
        return value;
    }

    /**
     * The version ID of the object.
     */
    public String versionId() {
        String value = headers.get("x-oss-version-id");
        return value;
    }

    /**
     * The 64-bit CRC value of the object.
     * This value is calculated based on the ECMA-182 standard.
     */
    public String hashCrc64() {
        String value = headers.get("x-oss-hash-crc64ecma");
        return value;
    }

    /**
     * The encryption method on the server side when an object is created.
     * Valid values: AES256 and KMS
     */
    public String serverSideEncryption() {
        String value = headers.get("x-oss-server-side-encryption");
        return value;
    }

    /**
     * The server side data encryption algorithm.
     */
    public String serverSideDataEncryption() {
        String value = headers.get("x-oss-server-side-data-encryption");
        return value;
    }

    /**
     * The ID of the customer master key (CMK) that is managed by Key Management Service (KMS).
     */
    public String serverSideEncryptionKeyId() {
        String value = headers.get("x-oss-server-side-encryption-key-id");
        return value;
    }

    /**
     * The entity tag (ETag).
     * An ETag is created when an object is created to identify the content of the object.
     */
    public String eTag() {
        if (innerBody instanceof CopyObjectResultXml) {
            CopyObjectResultXml body = (CopyObjectResultXml) innerBody;
            if (body.eTag != null) {
                return body.eTag;
            }
        }
        return headers.get("ETag");
    }

    /**
     * The time when the returned objects were last modified.
     */
    public String lastModified() {
        if (innerBody instanceof CopyObjectResultXml) {
            CopyObjectResultXml body = (CopyObjectResultXml) innerBody;
            if (body.lastModified != null) {
                return body.lastModified;
            }
        }
        return headers.get("Last-Modified");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(CopyObjectResult result) {
            super(result);
        }

        public CopyObjectResult build() {
            return new CopyObjectResult(this);
        }
    }
}
