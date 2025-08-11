package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * The request for the InitiateMultipartUpload operation.
 */
public final class InitiateMultipartUploadRequest extends RequestModel {
    private final String bucket;
    private final String key;

    private InitiateMultipartUploadRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket to which the object is uploaded by the multipart upload task.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The name of the object that is uploaded by the multipart upload task.
     */
    public String key() {
        return key;
    }

    /**
     * Specifies whether the InitiateMultipartUpload operation overwrites the existing object that has the same name as the object that you want to upload. When versioning is enabled or suspended for the bucket to which you want to upload the object, the **x-oss-forbid-overwrite** header does not take effect. In this case, the InitiateMultipartUpload operation overwrites the existing object that has the same name as the object that you want to upload.   - If you do not specify the **x-oss-forbid-overwrite** header or set the **x-oss-forbid-overwrite** header to **false**, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.   - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support
     */
    public String forbidOverwrite() {
        String value = headers.get("x-oss-forbid-overwrite");
        return value;
    }

    /**
     * The storage class of the bucket. Default value: Standard.  Valid values:- Standard- IA- Archive- ColdArchive
     */
    public String storageClass() {
        String value = headers.get("x-oss-storage-class");
        return value;
    }

    /**
     * The tag of the object. You can configure multiple tags for the object. Example: TagA=A&amp;TagB=B. The key and value of a tag must be URL-encoded. If a tag does not contain an equal sign (=), the value of the tag is considered an empty string.
     */
    public String tagging() {
        String value = headers.get("x-oss-tagging");
        return value;
    }

    /**
     * The server-side encryption method that is used to encrypt each part of the object that you want to upload. Valid values: **AES256**, **KMS**, and **SM4**. You must activate Key Management Service (KMS) before you set this header to KMS. If you specify this header in the request, this header is included in the response. OSS uses the method specified by this header to encrypt each uploaded part. When you download the object, the x-oss-server-side-encryption header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
     */
    public String serverSideEncryption() {
        String value = headers.get("x-oss-server-side-encryption");
        return value;
    }

    /**
     * The algorithm that is used to encrypt the object that you want to upload. If this header is not specified, the object is encrypted by using AES-256. This header is valid only when **x-oss-server-side-encryption** is set to KMS. Valid value: SM4.
     */
    public String serverSideDataEncryption() {
        String value = headers.get("x-oss-server-side-data-encryption");
        return value;
    }

    /**
     * The ID of the CMK that is managed by KMS. This header is valid only when **x-oss-server-side-encryption** is set to KMS.
     */
    public String serverSideEncryptionKeyId() {
        String value = headers.get("x-oss-server-side-encryption-key-id");
        return value;
    }

    /**
     * The caching behavior of the web page when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
     */
    public String cacheControl() {
        String value = headers.get("Cache-Control");
        return value;
    }

    /**
     * The name of the object when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
     */
    public String contentDisposition() {
        String value = headers.get("Content-Disposition");
        return value;
    }

    /**
     * The content encoding format of the object when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
     */
    public String contentEncoding() {
        String value = headers.get("Content-Encoding");
        return value;
    }

    /**
     * The expiration time of the request. Unit: milliseconds. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
     */
    public String expires() {
        String value = headers.get("Expires");
        return value;
    }

    /**
     * The method used to encode the object name in the response. Only URL encoding is supported. The object name can contain characters encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse specific control characters, such as characters whose ASCII values range from 0 to 10. You can configure the encoding-type parameter to encode object names that include characters that cannot be parsed by XML 1.0 in the response.brDefault value: null
     */
    public String encodingType() {
        String value = parameters.get("encoding-type");
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
    public String contentMD5() {
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
     * To indicate that the requester is aware that the request and data download will incur costs.
     */
    public String requestPayer() {
        String value = headers.get("x-oss-request-payer");
        return value;
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

        private Builder() {
            super();
        }

        private Builder(InitiateMultipartUploadRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
        }

        /**
         * The name of the bucket to which the object is uploaded by the multipart upload task.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The name of the object that is uploaded by the multipart upload task.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * Specifies whether the InitiateMultipartUpload operation overwrites the existing object that has the same name as the object that you want to upload. When versioning is enabled or suspended for the bucket to which you want to upload the object, the **x-oss-forbid-overwrite** header does not take effect. In this case, the InitiateMultipartUpload operation overwrites the existing object that has the same name as the object that you want to upload.   - If you do not specify the **x-oss-forbid-overwrite** header or set the **x-oss-forbid-overwrite** header to **false**, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.   - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support
         */
        public Builder forbidOverwrite(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-forbid-overwrite", value);
            return this;
        }

        /**
         * Specifies whether the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.  When versioning is enabled or suspended for the bucket to which you want to upload the object, the **x-oss-forbid-overwrite** header does not take effect. In this case, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.   - If you do not specify the **x-oss-forbid-overwrite** header or set the **x-oss-forbid-overwrite** header to **false**, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.   - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support. Default value: **false**.
         */
        public Builder forbidOverwrite(Boolean value) {
            requireNonNull(value);
            this.headers.put("x-oss-forbid-overwrite", value.toString());
            return this;
        }

        /**
         * The storage class of the bucket. Default value: Standard.  Valid values:- Standard- IA- Archive- ColdArchive
         */
        public Builder storageClass(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-storage-class", value);
            return this;
        }

        /**
         * The tag of the object. You can configure multiple tags for the object. Example: TagA=A&amp;TagB=B. The key and value of a tag must be URL-encoded. If a tag does not contain an equal sign (=), the value of the tag is considered an empty string.
         */
        public Builder tagging(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-tagging", value);
            return this;
        }

        /**
         * The server-side encryption method that is used to encrypt each part of the object that you want to upload. Valid values: **AES256**, **KMS**, and **SM4**. You must activate Key Management Service (KMS) before you set this header to KMS. If you specify this header in the request, this header is included in the response. OSS uses the method specified by this header to encrypt each uploaded part. When you download the object, the x-oss-server-side-encryption header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
         */
        public Builder serverSideEncryption(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-server-side-encryption", value);
            return this;
        }

        /**
         * The algorithm that is used to encrypt the object that you want to upload. If this header is not specified, the object is encrypted by using AES-256. This header is valid only when **x-oss-server-side-encryption** is set to KMS. Valid value: SM4.
         */
        public Builder serverSideDataEncryption(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-server-side-data-encryption", value);
            return this;
        }

        /**
         * The ID of the CMK that is managed by KMS. This header is valid only when **x-oss-server-side-encryption** is set to KMS.
         */
        public Builder serverSideEncryptionKeyId(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-server-side-encryption-key-id", value);
            return this;
        }

        /**
         * The caching behavior of the web page when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
         */
        public Builder cacheControl(String value) {
            requireNonNull(value);
            this.headers.put("Cache-Control", value);
            return this;
        }

        /**
         * The name of the object when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
         */
        public Builder contentDisposition(String value) {
            requireNonNull(value);
            this.headers.put("Content-Disposition", value);
            return this;
        }

        /**
         * The content encoding format of the object when the object is downloaded. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
         */
        public Builder contentEncoding(String value) {
            requireNonNull(value);
            this.headers.put("Content-Encoding", value);
            return this;
        }

        /**
         * The expiration time of the request. Unit: milliseconds. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
         */
        public Builder expires(String value) {
            requireNonNull(value);
            this.headers.put("Expires", value);
            return this;
        }

        /**
         * The method used to encode the object name in the response. Only URL encoding is supported. The object name can contain characters encoded in UTF-8. However, the XML 1.0 standard cannot be used to parse specific control characters, such as characters whose ASCII values range from 0 to 10. You can configure the encoding-type parameter to encode object names that include characters that cannot be parsed by XML 1.0 in the response.brDefault value: null
         */
        public Builder encodingType(String value) {
            requireNonNull(value);
            this.parameters.put("encoding-type", value);
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
        public Builder contentMD5(String value) {
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
         * To indicate that the requester is aware that the request and data download will incur costs.
         */
        public Builder requestPayer(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-request-payer", value);
            return this;
        }

        /**
         * The metadata of the object that you want to upload.
         */
        public Builder metadata(Map<String, String> value) {
            requireNonNull(value);
            value.forEach((k, v) -> this.headers.put("x-oss-meta-" + k, v));
            return this;
        }

        public InitiateMultipartUploadRequest build() {
            return new InitiateMultipartUploadRequest(this);
        }
    }

}
