package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.CastUtils;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * The request for the AppendObject operation.
 */
public final class AppendObjectRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final BinaryData body;
    private final ProgressListener progressListener;
    private final Long initHashCRC64;

    private AppendObjectRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.body = builder.body;
        this.progressListener = builder.progressListener;
        this.initHashCRC64 = builder.initHashCRC64;
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

    /**
     * The access control list (ACL) of the object. Default value: default.  Valid values:- default: The ACL of the object is the same as that of the bucket in which the object is stored. - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object. - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value. - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value. For more information about the ACL, see [ACL](~~100676~~).
     */
    public String objectAcl() {
        String value = headers.get("x-oss-object-acl");
        return value;
    }

    /**
     * The storage class of the object that you want to upload. Valid values:- Standard- IA- ArchiveIf you specify the object storage class when you upload an object, the storage class of the uploaded object is the specified value regardless of the storage class of the bucket to which the object is uploaded. If you set x-oss-storage-class to Standard when you upload an object to an IA bucket, the object is stored as a Standard object. For more information about storage classes, see the "Overview" topic in Developer Guide. notice The value that you specify takes effect only when you call the AppendObject operation on an object for the first time.
     */
    public String storageClass() {
        String value = headers.get("x-oss-storage-class");
        return value;
    }

    /**
     * metadata
     */
    public Map<String, String> metadata() {
        return CastUtils.toMetadata(this.headers);
    }

    /**
     * The web page caching behavior for the object. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
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
     * The encoding format of the object content. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
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
     * The Content-MD5 header value is a string calculated by using the MD5 algorithm. The header is used to check whether the content of the received message is the same as that of the sent message. To obtain the value of the Content-MD5 header, calculate a 128-bit number based on the message content except for the header, and then encode the number in Base64. Default value: null.Limits: none.
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
     * The expiration time. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
     */
    public String expires() {
        String value = headers.get("Expires");
        return value;
    }

    /**
     * The position from which the AppendObject operation starts.  Each time an AppendObject operation succeeds, the x-oss-next-append-position header is included in the response to specify the position from which the next AppendObject operation starts. The value of position in the first AppendObject operation performed on an object must be 0. The value of position in subsequent AppendObject operations performed on the object is the current length of the object. For example, if the value of position specified in the first AppendObject request is 0 and the value of content-length is 65536, the value of position in the second AppendObject request must be 65536. - If the value of position in the AppendObject request is 0 and the name of the object that you want to append is unique, you can set headers such as x-oss-server-side-encryption in an AppendObject request in the same way as you set in a PutObject request. If you add the x-oss-server-side-encryption header to an AppendObject request, the x-oss-server-side-encryption header is included in the response to the request. If you want to modify metadata, you can call the CopyObject operation. - If you call an AppendObject operation to append a 0 KB object whose position value is valid to an Appendable object, the status of the Appendable object is not changed.
     */
    public Long position() {
        String value = parameters.get("position");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The tag of the destination object. You can add multiple tags to the destination object. Example: TagA=A&amp;TagB=B.  The tag key and tag value must be URL-encoded. If a key-value pair does not contain an equal sign (=), the tag value is considered an empty string.
     */
    public String tagging() {
        String value = headers.get("x-oss-tagging");
        return value;
    }

    /**
     * Specifies whether the AppendObject operation overwrites objects with the same name. The **x-oss-forbid-overwrite** request header does not take effect when versioning is enabled or suspended for the destination bucket. In this case, the AppendObject operation overwrites the existing object that has the same name as the destination object.*   If you do not specify the **x-oss-forbid-overwrite** header or set the header to **false**, an existing object that has the same name as the object that you want to append is overwritten.*****   If you set the **x-oss-forbid-overwrite** header to **true**, an existing object that has the same name as the object that you want to append is not overwritten.If you specify the **x-oss-forbid-overwrite** header, the queries per second (QPS) performance of OSS may be degraded. If you want to specify the **x-oss-forbid-overwrite** header in a large number of requests (QPS greater than 1,000), contact technical support. Default value: false.
     */
    public Boolean forbidOverwrite() {
        String value = headers.get("x-oss-forbid-overwrite");
        return ConvertUtils.toBoolOrNull(value);
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
     * Gets the initial value of CRC64. If not set, the crc check is ignored.
     */
    public Long initHashCRC64 () {
        return initHashCRC64;
    }

    /**
     * The request body.
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
        private Long initHashCRC64;

        private Builder() {
            super();
        }

        private Builder(AppendObjectRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.body = request.body;
            this.progressListener = request.progressListener;
            this.initHashCRC64 = request.initHashCRC64;
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
         * The method used to encrypt objects on the specified OSS server. Valid values:- AES256: Keys managed by OSS are used for encryption and decryption (SSE-OSS). - KMS: Keys managed by Key Management Service (KMS) are used for encryption and decryption. - SM4: The SM4 block cipher algorithm is used for encryption and decryption.
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
         * The access control list (ACL) of the object. Default value: default.  Valid values:- default: The ACL of the object is the same as that of the bucket in which the object is stored. - private: The ACL of the object is private. Only the owner of the object and authorized users can read and write this object. - public-read: The ACL of the object is public-read. Only the owner of the object and authorized users can read and write this object. Other users can only read the object. Exercise caution when you set the object ACL to this value. - public-read-write: The ACL of the object is public-read-write. All users can read and write this object. Exercise caution when you set the object ACL to this value. For more information about the ACL, see [ACL](~~100676~~).
         */
        public Builder objectAcl(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-object-acl", value);
            return this;
        }

        /**
         * The storage class of the object that you want to upload. Valid values:- Standard- IA- ArchiveIf you specify the object storage class when you upload an object, the storage class of the uploaded object is the specified value regardless of the storage class of the bucket to which the object is uploaded. If you set x-oss-storage-class to Standard when you upload an object to an IA bucket, the object is stored as a Standard object. For more information about storage classes, see the "Overview" topic in Developer Guide. notice The value that you specify takes effect only when you call the AppendObject operation on an object for the first time.
         */
        public Builder storageClass(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-storage-class", value);
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
         * The web page caching behavior for the object. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
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
         * The encoding format of the object content. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
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
         * The Content-MD5 header value is a string calculated by using the MD5 algorithm. The header is used to check whether the content of the received message is the same as that of the sent message. To obtain the value of the Content-MD5 header, calculate a 128-bit number based on the message content except for the header, and then encode the number in Base64. Default value: null.Limits: none.
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
         * The expiration time. For more information, see **[RFC 2616](https://www.ietf.org/rfc/rfc2616.txt)**. Default value: null.
         */
        public Builder expires(String value) {
            requireNonNull(value);
            this.headers.put("Expires", value);
            return this;
        }

        /**
         * The position from which the AppendObject operation starts.  Each time an AppendObject operation succeeds, the x-oss-next-append-position header is included in the response to specify the position from which the next AppendObject operation starts. The value of position in the first AppendObject operation performed on an object must be 0. The value of position in subsequent AppendObject operations performed on the object is the current length of the object. For example, if the value of position specified in the first AppendObject request is 0 and the value of content-length is 65536, the value of position in the second AppendObject request must be 65536. - If the value of position in the AppendObject request is 0 and the name of the object that you want to append is unique, you can set headers such as x-oss-server-side-encryption in an AppendObject request in the same way as you set in a PutObject request. If you add the x-oss-server-side-encryption header to an AppendObject request, the x-oss-server-side-encryption header is included in the response to the request. If you want to modify metadata, you can call the CopyObject operation. - If you call an AppendObject operation to append a 0 KB object whose position value is valid to an Appendable object, the status of the Appendable object is not changed.
         */
        public Builder position(Long value) {
            requireNonNull(value);
            this.parameters.put("position", value.toString());
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
         * Specifies whether the AppendObject operation overwrites objects with the same name. The **x-oss-forbid-overwrite** request header does not take effect when versioning is enabled or suspended for the destination bucket. In this case, the AppendObject operation overwrites the existing object that has the same name as the destination object.*   If you do not specify the **x-oss-forbid-overwrite** header or set the header to **false**, an existing object that has the same name as the object that you want to append is overwritten.*****   If you set the **x-oss-forbid-overwrite** header to **true**, an existing object that has the same name as the object that you want to append is not overwritten.If you specify the **x-oss-forbid-overwrite** header, the queries per second (QPS) performance of OSS may be degraded. If you want to specify the **x-oss-forbid-overwrite** header in a large number of requests (QPS greater than 1,000), contact technical support. Default value: false.
         */
        public Builder forbidOverwrite(Boolean value) {
            requireNonNull(value);
            this.headers.put("x-oss-forbid-overwrite", value.toString());
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
         * The request body.
         */
        public Builder body(BinaryData value) {
            requireNonNull(value);
            this.body = value;
            return this;
        }

        /**
         * Specify the initial value of CRC64. If not set, the crc check is ignored.
         */
        public Builder initHashCRC64(long value) {
            this.initHashCRC64 = value;
            return this;
        }

        public AppendObjectRequest build() {
            return new AppendObjectRequest(this);
        }
    }

}
