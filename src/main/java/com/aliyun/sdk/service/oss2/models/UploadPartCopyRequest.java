package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import static java.util.Objects.requireNonNull;

/**
 * The request for the UploadPartCopy operation.
 */
public final class UploadPartCopyRequest extends RequestModel {
    private final String bucket;
    private final String key;

    private UploadPartCopyRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
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
     * The address to access the source object. You must have permissions to read the source object.brDefault value: null
     */
    public String copySource() {
        String value = headers.get("x-oss-copy-source");
        return value;
    }

    /**
     * The range of bytes to copy data from the source object. For example, if you specify bytes to 0 to 9, the system transfers byte 0 to byte 9, a total of 10 bytes.brDefault value: null- If the x-oss-copy-source-range request header is not specified, the entire source object is copied.- If the x-oss-copy-source-range request header is specified, the response contains the length of the entire object and the range of bytes to be copied for this operation. For example, Content-Range: bytes 0~9/44 indicates that the length of the entire object is 44 bytes. The range of bytes to be copied is byte 0 to byte 9.- If the specified range does not conform to the range conventions, OSS copies the entire object and does not include Content-Range in the response.
     */
    public String copySourceRange() {
        String value = headers.get("x-oss-copy-source-range");
        return value;
    }

    /**
     * The copy operation condition. If the ETag value of the source object is the same as the ETag value provided by the user, OSS copies data. Otherwise, OSS returns 412 Precondition Failed.brDefault value: null
     */
    public String copySourceIfMatch() {
        String value = headers.get("x-oss-copy-source-if-match");
        return value;
    }

    /**
     * The object transfer condition. If the input ETag value does not match the ETag value of the object, the system transfers the object normally and returns 200 OK. Otherwise, OSS returns 304 Not Modified.brDefault value: null
     */
    public String copySourceIfNoneMatch() {
        String value = headers.get("x-oss-copy-source-if-none-match");
        return value;
    }

    /**
     * The object transfer condition. If the specified time is the same as or later than the actual modified time of the object, OSS transfers the object normally and returns 200 OK. Otherwise, OSS returns 412 Precondition Failed.brDefault value: null
     */
    public String copySourceIfUnmodifiedSince() {
        String value = headers.get("x-oss-copy-source-if-unmodified-since");
        return value;
    }

    /**
     * The object transfer condition. If the specified time is earlier than the actual modified time of the object, the system transfers the object normally and returns 200 OK. Otherwise, OSS returns 304 Not Modified.brDefault value: nullbrTime format: ddd, dd MMM yyyy HH:mm:ss GMT. Example: Fri, 13 Nov 2015 14:47:53 GMT.
     */
    public String copySourceIfModifiedSince() {
        String value = headers.get("x-oss-copy-source-if-modified-since");
        return value;
    }

    /**
     * The number of parts.
     */
    public Long partNumber() {
        String value = parameters.get("partNumber");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The ID that identifies the object to which the parts to upload belong.
     */
    public String uploadId() {
        String value = parameters.get("uploadId");
        return value;
    }

    /**
     * The speed limit value. Unit: bit/s. Value range: 245760 to 838860800.
     */
    public Long trafficLimit() {
        String value = headers.get("x-oss-traffic-limit");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * To indicate that the requester is aware that the request and data download will incur costs.
     */
    public String requestPayer() {
        String value = headers.get("x-oss-request-payer");
        return value;
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

        private Builder(UploadPartCopyRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
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
         * The address to access the source object. You must have permissions to read the source object.brDefault value: null
         */
        public Builder copySource(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-copy-source", value);
            return this;
        }

        /**
         * The range of bytes to copy data from the source object. For example, if you specify bytes to 0 to 9, the system transfers byte 0 to byte 9, a total of 10 bytes.brDefault value: null- If the x-oss-copy-source-range request header is not specified, the entire source object is copied.- If the x-oss-copy-source-range request header is specified, the response contains the length of the entire object and the range of bytes to be copied for this operation. For example, Content-Range: bytes 0~9/44 indicates that the length of the entire object is 44 bytes. The range of bytes to be copied is byte 0 to byte 9.- If the specified range does not conform to the range conventions, OSS copies the entire object and does not include Content-Range in the response.
         */
        public Builder copySourceRange(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-copy-source-range", value);
            return this;
        }

        /**
         * The copy operation condition. If the ETag value of the source object is the same as the ETag value provided by the user, OSS copies data. Otherwise, OSS returns 412 Precondition Failed.brDefault value: null
         */
        public Builder copySourceIfMatch(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-copy-source-if-match", value);
            return this;
        }

        /**
         * The object transfer condition. If the input ETag value does not match the ETag value of the object, the system transfers the object normally and returns 200 OK. Otherwise, OSS returns 304 Not Modified.brDefault value: null
         */
        public Builder copySourceIfNoneMatch(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-copy-source-if-none-match", value);
            return this;
        }

        /**
         * The object transfer condition. If the specified time is the same as or later than the actual modified time of the object, OSS transfers the object normally and returns 200 OK. Otherwise, OSS returns 412 Precondition Failed.brDefault value: null
         */
        public Builder copySourceIfUnmodifiedSince(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-copy-source-if-unmodified-since", value);
            return this;
        }

        /**
         * The object transfer condition. If the specified time is earlier than the actual modified time of the object, the system transfers the object normally and returns 200 OK. Otherwise, OSS returns 304 Not Modified.brDefault value: nullbrTime format: ddd, dd MMM yyyy HH:mm:ss GMT. Example: Fri, 13 Nov 2015 14:47:53 GMT.
         */
        public Builder copySourceIfModifiedSince(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-copy-source-if-modified-since", value);
            return this;
        }

        /**
         * The number of parts.
         */
        public Builder partNumber(Long value) {
            requireNonNull(value);
            this.parameters.put("partNumber", value.toString());
            return this;
        }

        /**
         * The ID that identifies the object to which the parts to upload belong.
         */
        public Builder uploadId(String value) {
            requireNonNull(value);
            this.parameters.put("uploadId", value);
            return this;
        }

        /**
         * The speed limit value. Unit: bit/s. Value range: 245760 to 838860800.
         */
        public Builder trafficLimit(Long value) {
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

        public UploadPartCopyRequest build() {
            return new UploadPartCopyRequest(this);
        }
    }

}
