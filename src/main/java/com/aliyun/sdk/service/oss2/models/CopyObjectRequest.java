package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CopyObject operation.
 */
public final class CopyObjectRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final ProgressListener progressListener;

    private CopyObjectRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.progressListener = builder.progressListener;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The full path of the object.
     */
    public String key() {
        return key;
    }

    /**
     * The path of the source object. By default, this header is left empty.
     */
    public String copySource() {
        String value = headers.get("x-oss-copy-source");
        return value;
    }

    /**
     * Specifies whether the CopyObject operation overwrites objects with the same name. The **x-oss-forbid-overwrite** request header does not take effect when versioning is enabled or suspended for the destination bucket. In this case, the CopyObject operation overwrites the existing object that has the same name as the destination object.*   If you do not specify the **x-oss-forbid-overwrite** header or set the header to **false**, an existing object that has the same name as the object that you want to copy is overwritten.*****   If you set the **x-oss-forbid-overwrite** header to **true**, an existing object that has the same name as the object that you want to copy is not overwritten.If you specify the **x-oss-forbid-overwrite** header, the queries per second (QPS) performance of OSS may be degraded. If you want to specify the **x-oss-forbid-overwrite** header in a large number of requests (QPS greater than 1,000), contact technical support. Default value: false.
     */
    public String forbidOverwrite() {
        String value = headers.get("x-oss-forbid-overwrite");
        return value;
    }

    /**
     * The object copy condition. If the ETag value of the source object is the same as the ETag value that you specify in the request, OSS copies the object and returns 200 OK. By default, this header is left empty.
     */
    public String copySourceIfMatch() {
        String value = headers.get("x-oss-copy-source-if-match");
        return value;
    }

    /**
     * The object copy condition. If the ETag value of the source object is different from the ETag value that you specify in the request, OSS copies the object and returns 200 OK. By default, this header is left empty.
     */
    public String copySourceIfNoneMatch() {
        String value = headers.get("x-oss-copy-source-if-none-match");
        return value;
    }

    /**
     * The object copy condition. If the time that you specify in the request is the same as or later than the modification time of the object, OSS copies the object and returns 200 OK. By default, this header is left empty.
     */
    public String copySourceIfUnmodifiedSince() {
        String value = headers.get("x-oss-copy-source-if-unmodified-since");
        return value;
    }

    /**
     * If the source object is modified after the time that you specify in the request, OSS copies the object. By default, this header is left empty.
     */
    public String copySourceIfModifiedSince() {
        String value = headers.get("x-oss-copy-source-if-modified-since");
        return value;
    }

    /**
     * The method that is used to configure the metadata of the destination object. Default value: COPY.*   **COPY**: The metadata of the source object is copied to the destination object. The **x-oss-server-side-encryption** attribute of the source object is not copied to the destination object. The **x-oss-server-side-encryption** header in the CopyObject request specifies the method that is used to encrypt the destination object.*   **REPLACE**: The metadata that you specify in the request is used as the metadata of the destination object.  If the path of the source object is the same as the path of the destination object and versioning is disabled for the bucket in which the source and destination objects are stored, the metadata that you specify in the CopyObject request is used as the metadata of the destination object regardless of the value of the x-oss-metadata-directive header.
     */
    public String metadataDirective() {
        String value = headers.get("x-oss-metadata-directive");
        return value;
    }

    /**
     * The entropy coding-based encryption algorithm that OSS uses to encrypt an object when you create the object. The valid values of the header are **AES256** and **KMS**. You must activate Key Management Service (KMS) in the OSS console before you can use the KMS encryption algorithm. Otherwise, the KmsServiceNotEnabled error is returned.*   If you do not specify the **x-oss-server-side-encryption** header in the CopyObject request, the destination object is not encrypted on the server regardless of whether the source object is encrypted on the server.*   If you specify the **x-oss-server-side-encryption** header in the CopyObject request, the destination object is encrypted on the server after the CopyObject operation is performed regardless of whether the source object is encrypted on the server. In addition, the response to a CopyObject request contains the **x-oss-server-side-encryption** header whose value is the encryption algorithm of the destination object. When the destination object is downloaded, the **x-oss-server-side-encryption** header is included in the response. The value of this header is the encryption algorithm of the destination object.
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

    /**
     * The access control list (ACL) of the destination object when the object is created. Default value: default.Valid values:*   default: The ACL of the object is the same as the ACL of the bucket in which the object is stored.*   private: The ACL of the object is private. Only the owner of the object and authorized users have read and write permissions on the object. Other users do not have permissions on the object.*   public-read: The ACL of the object is public-read. Only the owner of the object and authorized users have read and write permissions on the object. Other users have only read permissions on the object. Exercise caution when you set the ACL of the bucket to this value.*   public-read-write: The ACL of the object is public-read-write. All users have read and write permissions on the object. Exercise caution when you set the ACL of the bucket to this value.For more information about ACLs, see [Object ACL](~~100676~~).
     */
    public String objectAcl() {
        String value = headers.get("x-oss-object-acl");
        return value;
    }

    /**
     * The storage class of the object that you want to upload. Default value: Standard. If you specify a storage class when you upload the object, the storage class applies regardless of the storage class of the bucket to which you upload the object. For example, if you set **x-oss-storage-class** to Standard when you upload an object to an IA bucket, the storage class of the uploaded object is Standard.Valid values:*   Standard*   IA*   Archive*   ColdArchiveFor more information about storage classes, see [Overview](~~51374~~).
     */
    public String storageClass() {
        String value = headers.get("x-oss-storage-class");
        return value;
    }

    /**
     * The tag of the destination object. You can add multiple tags to the destination object. Example: TagA=A&amp;TagB=B.  The tag key and tag value must be URL-encoded. If a key-value pair does not contain an equal sign (=), the tag value is considered an empty string.
     */
    public String tagging() {
        String value = headers.get("x-oss-tagging");
        return value;
    }

    /**
     * The method that is used to add tags to the destination object. Default value: Copy. Valid values:*   **Copy**: The tags of the source object are copied to the destination object.*   **Replace**: The tags that you specify in the request are added to the destination object.
     */
    public String taggingDirective() {
        String value = headers.get("x-oss-tagging-directive");
        return value;
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
     * The size of the data in the HTTP message body. Unit: bytes.
     */
    public Long contentLength() {
        String value = headers.get("Content-Length");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The MD5 hash of the object that you want to upload.
     */
    public String contentMd5() {
        String value = headers.get("Content-MD5");
        return value;
    }

    /**
     * A standard MIME type describing the format of the contents.
     */
    public String contentType() {
        String value = headers.get("Content-Type");
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
     * The speed limit value. The speed limit value ranges from 245760 to 838860800, with a unit of bit/s.
     */
    public Integer trafficLimit() {
        String value = headers.get("x-oss-traffic-limit");
        return ConvertUtils.toIntegerOrNull(value);
    }

    /**
     * To indicate that the requester is aware that the request and data download will incur costs.
     */
    public String requestPayer() {
        String value = headers.get("x-oss-request-payer");
        return value;
    }

    /**
     * Gets the progress listener for monitoring data transfer during OSS operations.
     */
    public ProgressListener progressListener() {
        return progressListener;
    }

    /**
     * The metadata of the object that you want to upload.
     */
    public Map<String, String> metadata() {
        return CastUtils.toMetadata(this.headers);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String key;
        private ProgressListener progressListener;

        private Builder() {
            super();
        }

        private Builder(CopyObjectRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.progressListener = request.progressListener;
        }

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The full path of the object.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The path of the source object. By default, this header is left empty.
         */
        public Builder copySource(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-copy-source", value);
            return this;
        }

        /**
         * Specifies whether the CopyObject operation overwrites objects with the same name. The **x-oss-forbid-overwrite** request header does not take effect when versioning is enabled or suspended for the destination bucket. In this case, the CopyObject operation overwrites the existing object that has the same name as the destination object.*   If you do not specify the **x-oss-forbid-overwrite** header or set the header to **false**, an existing object that has the same name as the object that you want to copy is overwritten.*****   If you set the **x-oss-forbid-overwrite** header to **true**, an existing object that has the same name as the object that you want to copy is not overwritten.If you specify the **x-oss-forbid-overwrite** header, the queries per second (QPS) performance of OSS may be degraded. If you want to specify the **x-oss-forbid-overwrite** header in a large number of requests (QPS greater than 1,000), contact technical support. Default value: false.
         */
        public Builder forbidOverwrite(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-forbid-overwrite", value);
            return this;
        }

        /**
         * The object copy condition. If the ETag value of the source object is the same as the ETag value that you specify in the request, OSS copies the object and returns 200 OK. By default, this header is left empty.
         */
        public Builder copySourceIfMatch(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-copy-source-if-match", value);
            return this;
        }

        /**
         * The object copy condition. If the ETag value of the source object is different from the ETag value that you specify in the request, OSS copies the object and returns 200 OK. By default, this header is left empty.
         */
        public Builder copySourceIfNoneMatch(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-copy-source-if-none-match", value);
            return this;
        }

        /**
         * The object copy condition. If the time that you specify in the request is the same as or later than the modification time of the object, OSS copies the object and returns 200 OK. By default, this header is left empty.
         */
        public Builder copySourceIfUnmodifiedSince(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-copy-source-if-unmodified-since", value);
            return this;
        }

        /**
         * If the source object is modified after the time that you specify in the request, OSS copies the object. By default, this header is left empty.
         */
        public Builder copySourceIfModifiedSince(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-copy-source-if-modified-since", value);
            return this;
        }

        /**
         * The method that is used to configure the metadata of the destination object. Default value: COPY.*   **COPY**: The metadata of the source object is copied to the destination object. The **x-oss-server-side-encryption** attribute of the source object is not copied to the destination object. The **x-oss-server-side-encryption** header in the CopyObject request specifies the method that is used to encrypt the destination object.*   **REPLACE**: The metadata that you specify in the request is used as the metadata of the destination object.  If the path of the source object is the same as the path of the destination object and versioning is disabled for the bucket in which the source and destination objects are stored, the metadata that you specify in the CopyObject request is used as the metadata of the destination object regardless of the value of the x-oss-metadata-directive header.
         */
        public Builder metadataDirective(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-metadata-directive", value);
            return this;
        }

        /**
         * The entropy coding-based encryption algorithm that OSS uses to encrypt an object when you create the object. The valid values of the header are **AES256** and **KMS**. You must activate Key Management Service (KMS) in the OSS console before you can use the KMS encryption algorithm. Otherwise, the KmsServiceNotEnabled error is returned.*   If you do not specify the **x-oss-server-side-encryption** header in the CopyObject request, the destination object is not encrypted on the server regardless of whether the source object is encrypted on the server.*   If you specify the **x-oss-server-side-encryption** header in the CopyObject request, the destination object is encrypted on the server after the CopyObject operation is performed regardless of whether the source object is encrypted on the server. In addition, the response to a CopyObject request contains the **x-oss-server-side-encryption** header whose value is the encryption algorithm of the destination object. When the destination object is downloaded, the **x-oss-server-side-encryption** header is included in the response. The value of this header is the encryption algorithm of the destination object.
         */
        public Builder serverSideEncryption(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-server-side-encryption", value);
            return this;
        }

        /**
         * The server side data encryption algorithm. Invalid value: SM4
         */
        public Builder serverSideDataEncryption(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-server-side-data-encryption", value);
            return this;
        }

        /**
         * The ID of the customer master key (CMK) that is managed by KMS. This parameter is available only if you set **x-oss-server-side-encryption** to KMS.
         */
        public Builder serverSideEncryptionKeyId(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-server-side-encryption-key-id", value);
            return this;
        }

        /**
         * The access control list (ACL) of the destination object when the object is created. Default value: default.Valid values:*   default: The ACL of the object is the same as the ACL of the bucket in which the object is stored.*   private: The ACL of the object is private. Only the owner of the object and authorized users have read and write permissions on the object. Other users do not have permissions on the object.*   public-read: The ACL of the object is public-read. Only the owner of the object and authorized users have read and write permissions on the object. Other users have only read permissions on the object. Exercise caution when you set the ACL of the bucket to this value.*   public-read-write: The ACL of the object is public-read-write. All users have read and write permissions on the object. Exercise caution when you set the ACL of the bucket to this value.For more information about ACLs, see [Object ACL](~~100676~~).
         */
        public Builder objectAcl(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-object-acl", value);
            return this;
        }

        /**
         * The storage class of the object that you want to upload. Default value: Standard. If you specify a storage class when you upload the object, the storage class applies regardless of the storage class of the bucket to which you upload the object. For example, if you set **x-oss-storage-class** to Standard when you upload an object to an IA bucket, the storage class of the uploaded object is Standard.Valid values:*   Standard*   IA*   Archive*   ColdArchiveFor more information about storage classes, see [Overview](~~51374~~).
         */
        public Builder storageClass(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-storage-class", value);
            return this;
        }

        /**
         * The tag of the destination object. You can add multiple tags to the destination object. Example: TagA=A&amp;TagB=B.  The tag key and tag value must be URL-encoded. If a key-value pair does not contain an equal sign (=), the tag value is considered an empty string.
         */
        public Builder tagging(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-tagging", value);
            return this;
        }

        /**
         * The method that is used to add tags to the destination object. Default value: Copy. Valid values:*   **Copy**: The tags of the source object are copied to the destination object.*   **Replace**: The tags that you specify in the request are added to the destination object.
         */
        public Builder taggingDirective(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-tagging-directive", value);
            return this;
        }

        /**
         * The caching behavior of the web page when the object is downloaded.
         */
        public Builder cacheControl(String value) {
            requireNonNull(value);
            this.headers.put("Cache-Control", value);
            return this;
        }

        /**
         * The method that is used to access the object.
         */
        public Builder contentDisposition(String value) {
            requireNonNull(value);
            this.headers.put("Content-Disposition", value);
            return this;
        }

        /**
         * The method that is used to encode the object.
         */
        public Builder contentEncoding(String value) {
            requireNonNull(value);
            this.headers.put("Content-Encoding", value);
            return this;
        }

        /**
         * The size of the data in the HTTP message body. Unit: bytes.
         */
        public Builder contentLength(Long value) {
            requireNonNull(value);
            this.headers.put("Content-Length", value.toString());
            return this;
        }

        /**
         * The MD5 hash of the object that you want to upload.
         */
        public Builder contentMd5(String value) {
            requireNonNull(value);
            this.headers.put("Content-MD5", value);
            return this;
        }

        /**
         * A standard MIME type describing the format of the contents.
         */
        public Builder contentType(String value) {
            requireNonNull(value);
            this.headers.put("Content-Type", value);
            return this;
        }

        /**
         * The expiration time of the cache in UTC.
         */
        public Builder expires(String value) {
            requireNonNull(value);
            this.headers.put("Expires", value);
            return this;
        }

        /**
         * The speed limit value. The speed limit value ranges from 245760 to 838860800, with a unit of bit/s.
         */
        public Builder trafficLimit(Integer value) {
            requireNonNull(value);
            this.headers.put("x-oss-traffic-limit", value.toString());
            return this;
        }

        /**
         * To indicate that the requester is aware that the request and data download will incur costs.
         */
        public Builder requestPayer(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-request-payer", value);
            return this;
        }

        /**
         * Sets the progress listener for monitoring data transfer during OSS operations.
         */
        public Builder progressListener(ProgressListener progressListener) {
            requireNonNull(progressListener);
            this.progressListener = progressListener;
            return this;
        }

        /**
         * metadata
         */
        public Builder metadata(Map<String, String> value) {
            requireNonNull(value);
            value.forEach((k, v) -> this.headers.put("x-oss-meta-" + k, v));
            return this;
        }

        public CopyObjectRequest build() {
            return new CopyObjectRequest(this);
        }
    }

}
