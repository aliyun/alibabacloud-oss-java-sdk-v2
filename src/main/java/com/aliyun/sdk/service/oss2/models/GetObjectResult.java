package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.transport.Abortable;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.InputStreamBinaryData;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import java.io.InputStream;
import java.util.Map;

/**
 * The result for the GetObject operation.
 */
public final class GetObjectResult extends ResultModel implements AutoCloseable {


    GetObjectResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Size of the body in bytes.
     */
    public Long contentLength() {
        String value = headers.get("Content-Length");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The number of tags added to the object.
     */
    public Long taggingCount() {
        String value = headers.get("x‑oss‑tagging‑count");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The ID of the customer master key (CMK) that is managed by Key Management Service (KMS).
     */
    public String serverSideEncryptionKeyId() {
        String value = headers.get("x-oss-server-side-encryption-key-id");
        return value;
    }

    /**
     * The position for the next append operation.
     * If the type of the object is Appendable, this header is included in the response.
     */
    public Long nextAppendPosition() {
        String value = headers.get("x-oss-next-append-position");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The lifecycle information about the object.
     * If lifecycle rules are configured for the object, this header is included in the response.
     * This header contains the following parameters: expiry-date that indicates the expiration time of the object,
     * and rule-id that indicates the ID of the matched lifecycle rule.
     */
    public String expiration() {
        String value = headers.get("x-oss-expiration");
        return value;
    }

    /**
     * The entity tag (ETag).
     * An ETag is created when an object is created to identify the content of the object.
     */
    public String eTag() {
        String value = headers.get("ETag");
        return value;
    }

    /**
     * A standard MIME type describing the format of the object data.
     */
    public String objectType() {
        String value = headers.get("x-oss-object-type");
        return value;
    }

    /**
     * The result of an event notification that is triggered for the object.
     */
    public String processStatus() {
        String value = headers.get("x-oss-process-status");
        return value;
    }

    /**
     * Content-Md5 for the uploaded object.
     */
    public String contentMd5() {
        String value = headers.get("Content-Md5");
        return value;
    }

    /**
     * A standard MIME type describing the format of the object data.
     */
    public String contentType() {
        String value = headers.get("Content-Type");
        return value;
    }

    /**
     * A map of metadata to store with the object.
     * The number of metadata entries returned in the headers that are prefixed with x-oss-meta-
     */
    public Map<String, String> metadata() {
        return CastUtils.toMetadata(headers);
    }

    /**
     * If the requested object is encrypted by
     * using a server-side encryption algorithm based on entropy encoding, OSS automatically decrypts
     * the object and returns the decrypted object after OSS receives the GetObject request.
     * The x-oss-server-side-encryption header is included in the response to indicate the encryption algorithm
     * used to encrypt the object on the server.
     */
    public String serverSideEncryption() {
        String value = headers.get("x-oss-server-side-encryption");
        return value;
    }

    /**
     * The storage class of the object.
     */
    public String storageClass() {
        String value = headers.get("x-oss-storage-class");
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
     * The status of the object when you restore an object.
     * If the storage class of the bucket is Archive and a RestoreObject request is submitted,
     */
    public String restore() {
        String value = headers.get("x-oss-restore");
        return value;
    }

    /**
     * The time when the returned objects were last modified.
     */
    public String lastModified() {
        String value = headers.get("Last-Modified");
        return value;
    }

    /**
     * The portion of the object returned in the response.
     */
    public String contentRange() {
        String value = headers.get("Content-Range");
        return value;
    }

    /**
     * The method that is used to access the object.
     */
    public String contentDisposition() {
        String value = headers.get("Content-Disposition");
        return value;
    }

    /**
     * The method that is used to encode the object.
     */
    public String contentEncoding() {
        String value = headers.get("Content-Encoding");
        return value;
    }

    /**
     * The expiration time of the cache in UTC.
     */
    public String expires() {
        String value = headers.get("Expires");
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
     * The ID of the customer master key (CMK) that is managed by Key Management Service (KMS).
     */
    public String serverSideDataEncryption() {
        String value = headers.get("x-oss-server-side-data-encryption");
        return value;
    }

    /**
     * Specifies whether the object retrieved was (true) or was not (false) a Delete Marker.
     */
    public Boolean deleteMarker() {
        String value = headers.get("x-oss-delete-marker");
        return ConvertUtils.toBoolOrNull(value);
    }

    /**
     * The GMT time when the SealAppendable operation was executed on the object.
     * If the object has not been sealed, the current operation time is returned.
     * If the object has been sealed, the corresponding operation time is returned.
     */
    public String sealedTime() {
        String value = headers.get("x-oss-sealed-time");
        return value;
    }

    /**
     * Object data.
     */
    public InputStream body() {
        return (InputStream) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public void close() throws Exception {
        if (innerBody instanceof InputStream) {
            ((InputStream)innerBody).close();
        }
    }

    /**
     * Close the response forcefully . The remaining data in the server will not
     * be downloaded.
     */
    public void abort() {
        if (innerBody instanceof Abortable) {
            ((Abortable)innerBody).abort();
        }
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetObjectResult result) {
            super(result);
        }

        public GetObjectResult build() {
            return new GetObjectResult(this);
        }
    }
}
