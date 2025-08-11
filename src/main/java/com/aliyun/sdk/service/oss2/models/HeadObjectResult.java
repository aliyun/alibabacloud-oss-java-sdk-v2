package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import java.util.Map;

/**
 * The result for the HeadObject operation.
 */
public final class HeadObjectResult extends ResultModel {

    HeadObjectResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The caching behavior of the web page when the object is downloaded.
     */
    public String cacheControl() {
        String value = headers.get("Cache-Control");
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
     * The server side data encryption algorithm.
     */
    public String serverSideDataEncryption() {
        String value = headers.get("x-oss-server-side-data-encryption");
        return value;
    }

    /**
     * The origins allowed for cross-origin resource sharing (CORS).
     */
    public String allowOrigin() {
        String value = headers.get("Access-Control-Allow-Origin");
        return value;
    }

    /**
     * The methods allowed for CORS.
     */
    public String allowMethods() {
        String value = headers.get("Access-Control-Allow-Methods");
        return value;
    }

    /**
     * The maximum caching period for CORS.
     */
    public String allowAge() {
        String value = headers.get("Access-Control-Allow-Age");
        return value;
    }

    /**
     * The headers allowed for CORS.
     */
    public String allowHeaders() {
        String value = headers.get("Access-Control-Allow-Headers");
        return value;
    }

    /**
     * The headers that can be accessed by JavaScript applications on the client.
     */
    public String exposeHeaders() {
        String value = headers.get("Access-Control-Expose-Headers");
        return value;
    }

    /**
     * The status of the object when you restore an object.
     */
    public String restore() {
        String value = headers.get("x-oss-restore");
        return value;
    }

    /**
     * The requester. This header is included in the response if the pay-by-requester mode
     */
    public String requestCharged() {
        String value = headers.get("x-oss-request-charged");
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
     */
    public String hashCrc64ecma() {
        String value = headers.get("x-oss-hash-crc64ecma");
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
     * The position for the next append operation.
     */
    public Long nextAppendPosition() {
        String value = headers.get("x-oss-next-append-position");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The time when the returned objects were last modified.
     */
    public String lastModified() {
        String value = headers.get("Last-Modified");
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
     * The type of the object.
     */
    public String objectType() {
        String value = headers.get("x-oss-object-type");
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
     * The result of an event notification that is triggered for the object.
     */
    public String processStatus() {
        String value = headers.get("x-oss-process-status");
        return value;
    }

    /**
     * Size of the body in bytes.
     */
    public Long contentLength() {
        String value = headers.get("Content-Length");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The entity tag (ETag).
     */
    public String eTag() {
        String value = headers.get("ETag");
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
     * The time when the storage class of the object is converted to Cold Archive or Deep Cold Archive based on lifecycle rules.
     */
    public String transitionTime() {
        String value = headers.get("x-oss-transition-time");
        return value;
    }

    /**
     * The number of tags added to the object.
     */
    public Long taggingCount() {
        String value = headers.get("x‑oss‑tagging‑count");
        return ConvertUtils.toLongOrNull(value);
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
     * The lifecycle information about the object.
     * If lifecycle rules are configured for the object, this header is included in the response.
     * This header contains the following parameters: expiry-date that indicates the expiration time of the object,
     * and rule-id that indicates the ID of the matched lifecycle rule.
     */
    public String expiration() {
        String value = headers.get("x-oss-expiration");
        return value;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(HeadObjectResult result) {
            super(result);
        }

        public HeadObjectResult build() {
            return new HeadObjectResult(this);
        }
    }
}
