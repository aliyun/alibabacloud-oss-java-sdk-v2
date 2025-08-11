package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutObject operation.
 */
public final class PutObjectRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final BinaryData body;
    private final ProgressListener progressListener;

    private PutObjectRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.body = builder.body;
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
     * Specifies whether the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.  When versioning is enabled or suspended for the bucket to which you want to upload the object, the **x-oss-forbid-overwrite** header does not take effect. In this case, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.   - If you do not specify the **x-oss-forbid-overwrite** header or set the **x-oss-forbid-overwrite** header to **false**, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.   - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support. Default value: **false**.
     */
    public Boolean forbidOverwrite() {
        String value = headers.get("x-oss-forbid-overwrite");
        return ConvertUtils.toBoolOrNull(value);
    }

    /**
     * The method that is used to encrypt the object on the OSS server when the object is created. Valid values: **AES256**, **KMS**, and **SM4****.If you specify the header, the header is returned in the response. OSS uses the method that is specified by this header to encrypt the uploaded object. When you download the encrypted object, the **x-oss-server-side-encryption** header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
     */
    public String serverSideEncryption() {
        String value = headers.get("x-oss-server-side-encryption");
        return value;
    }

    /**
     * The encryption method on the server side when an object is created. Valid values: **AES256**, **KMS**, and **SM4**.If you specify the header, the header is returned in the response. OSS uses the method that is specified by this header to encrypt the uploaded object. When you download the encrypted object, the **x-oss-server-side-encryption** header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
     */
    public String serverSideDataEncryption() {
        String value = headers.get("x-oss-server-side-data-encryption");
        return value;
    }

    /**
     * The ID of the customer master key (CMK) managed by Key Management Service (KMS). This header is valid only when the **x-oss-server-side-encryption** header is set to KMS.
     */
    public String serverSideEncryptionKeyId() {
        String value = headers.get("x-oss-server-side-encryption-key-id");
        return value;
    }

    /**
     * The access control list (ACL) of the object. Default value: default. Valid values:- default: The ACL of the object is the same as that of the bucket in which the object is stored. - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object. - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value. - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value. For more information about the ACL, see **[ACL](~~100676~~)**.
     */
    public String objectAcl() {
        String value = headers.get("x-oss-object-acl");
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
     * The tag of the object. You can configure multiple tags for the object. Example: TagA=A&amp;TagB=B.  The key and value of a tag must be URL-encoded. If a tag does not contain an equal sign (=), the value of the tag is considered an empty string.
     */
    public String tagging() {
        String value = headers.get("x-oss-tagging");
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
    public Integer contentLength() {
        String value = headers.get("Content-Length");
        return ConvertUtils.toIntegerOrNull(value);
    }

    /**
     * The MD5 hash of the object that you want to upload.
     */
    public String contentMd5() {
        String value = headers.get("Content-Md5");
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
     * A callback parameter is a Base64-encoded string that contains multiple fields in the JSON format.
     */
    public String callback() {
        String value = headers.get("x-oss-callback");
        return value;
    }

    /**
     * Configure custom parameters by using the callback-var parameter.
     */
    public String callbackVar() {
        String value = headers.get("x-oss-callback-var");
        return value;
    }

    /**
     * Specify the speed limit value.
     * The speed limit value ranges from 245760 to 838860800, with a unit of bit/s.
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

    /**
     * The body of the request.
     */
    public BinaryData body() {
        return body;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String key;
        private BinaryData body;
        private ProgressListener progressListener;

        private Builder() {
            super();
        }

        private Builder(PutObjectRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.body = request.body;
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
         * Specifies whether the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.  When versioning is enabled or suspended for the bucket to which you want to upload the object, the **x-oss-forbid-overwrite** header does not take effect. In this case, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.   - If you do not specify the **x-oss-forbid-overwrite** header or set the **x-oss-forbid-overwrite** header to **false**, the object that is uploaded by calling the PutObject operation overwrites the existing object that has the same name.   - If the value of **x-oss-forbid-overwrite** is set to **true**, existing objects cannot be overwritten by objects that have the same names. If you specify the **x-oss-forbid-overwrite** request header, the queries per second (QPS) performance of OSS is degraded. If you want to use the **x-oss-forbid-overwrite** request header to perform a large number of operations (QPS greater than 1,000), contact technical support. Default value: **false**.
         */
        public Builder forbidOverwrite(Boolean value) {
            requireNonNull(value);
            this.headers.put("x-oss-forbid-overwrite", value.toString());
            return this;
        }

        /**
         * The method that is used to encrypt the object on the OSS server when the object is created. Valid values: **AES256**, **KMS**, and **SM4****.If you specify the header, the header is returned in the response. OSS uses the method that is specified by this header to encrypt the uploaded object. When you download the encrypted object, the **x-oss-server-side-encryption** header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
         */
        public Builder serverSideEncryption(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-server-side-encryption", value);
            return this;
        }

        /**
         * The encryption method on the server side when an object is created. Valid values: **AES256**, **KMS**, and **SM4**.If you specify the header, the header is returned in the response. OSS uses the method that is specified by this header to encrypt the uploaded object. When you download the encrypted object, the **x-oss-server-side-encryption** header is included in the response and the header value is set to the algorithm that is used to encrypt the object.
         */
        public Builder serverSideDataEncryption(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-server-side-data-encryption", value);
            return this;
        }

        /**
         * The ID of the customer master key (CMK) managed by Key Management Service (KMS). This header is valid only when the **x-oss-server-side-encryption** header is set to KMS.
         */
        public Builder serverSideEncryptionKeyId(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-server-side-encryption-key-id", value);
            return this;
        }

        /**
         * The access control list (ACL) of the object. Default value: default. Valid values:- default: The ACL of the object is the same as that of the bucket in which the object is stored. - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object. - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value. - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value. For more information about the ACL, see **[ACL](~~100676~~)**.
         */
        public Builder objectAcl(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-object-acl", value);
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
         * The tag of the object. You can configure multiple tags for the object. Example: TagA=A&amp;TagB=B.  The key and value of a tag must be URL-encoded. If a tag does not contain an equal sign (=), the value of the tag is considered an empty string.
         */
        public Builder tagging(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-tagging", value);
            return this;
        }

        /**
         * The caching behavior of the web page when the object is downloaded.
         */
        public Builder cacheControl(String value) {
            this.headers.put("Cache-Control", value);
            return this;
        }

        /**
         * The method that is used to access the object.
         */
        public Builder contentDisposition(String value) {
            this.headers.put("Content-Disposition", value);
            return this;
        }

        /**
         * The method that is used to encode the object.
         */
        public Builder contentEncoding(String value) {
            this.headers.put("Content-Encoding", value);
            return this;
        }

        /**
         * The size of the data in the HTTP message body. Unit: bytes.
         */
        public Builder contentLength(Integer value) {
            if (value != null) {
                this.headers.put("Content-Length", value.toString());
            } else {
                this.headers.remove("Content-Length");
            }
            return this;
        }

        /**
         * The MD5 hash of the object that you want to upload.
         */
        public Builder contentMd5(String value) {
            this.headers.put("Content-Md5", value);
            return this;
        }

        /**
         * A standard MIME type describing the format of the contents.
         */
        public Builder contentType(String value) {
            this.headers.put("Content-Type", value);
            return this;
        }

        /**
         * The expiration time of the cache in UTC.
         */
        public Builder expires(String value) {
            this.headers.put("Expires", value);
            return this;
        }

        /**
         * A callback parameter is a Base64-encoded string that contains multiple fields in the JSON format.
         */
        public Builder callback(String value) {
            this.headers.put("x-oss-callback", value);
            return this;
        }

        /**
         * Configure custom parameters by using the callback-var parameter.
         */
        public Builder callbackVar(String value) {
            this.headers.put("x-oss-callback-var", value);
            return this;
        }

        /**
         * Specify the speed limit value.
         * The speed limit value ranges from 245760 to 838860800, with a unit of bit/s.
         */
        public Builder trafficLimit(Integer value) {
            if (value != null) {
                this.headers.put("x-oss-traffic-limit", value.toString());
            } else {
                this.headers.remove("x-oss-traffic-limit");
            }
            return this;
        }

        /**
         * To indicate that the requester is aware that the request and data download will incur costs.
         */
        public Builder requestPayer(String value) {
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
         * The metadata of the object that you want to upload.
         */
        public Builder metadata(Map<String, String> value) {
            requireNonNull(value);
            value.forEach((k, v) -> this.headers.put("x-oss-meta-" + k, v));
            return this;
        }

        /**
         * The body of the request.
         */
        public Builder body(BinaryData value) {
            requireNonNull(value);
            this.body = value;
            return this;
        }

        public PutObjectRequest build() {
            return new PutObjectRequest(this);
        }
    }

}
