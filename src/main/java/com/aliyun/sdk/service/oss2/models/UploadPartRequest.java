package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import static java.util.Objects.requireNonNull;

/**
 * The request for the UploadPart operation.
 */
public final class UploadPartRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final BinaryData body;

    private UploadPartRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.body = builder.body;
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
     * The number that identifies a part. Valid values: 1 to 10000.The size of a part ranges from 100 KB to 5 GB.  In multipart upload, each part except the last part must be larger than or equal to 100 KB in size. When you call the UploadPart operation, the size of each part is not verified because not all parts have been uploaded and OSS does not know which part is the last part. The size of each part is verified only when you call CompleteMultipartUpload.
     */
    public Long partNumber() {
        String value = parameters.get("partNumber");
        return ConvertUtils.toLongOrNull(value);
    }

    /**
     * The ID that identifies the object to which the part that you want to upload belongs.
     */
    public String uploadId() {
        String value = parameters.get("uploadId");
        return value;
    }

    /**
     * The MD5 hash of the object that you want to upload.
     */
    public String contentMD5() {
        String value = headers.get("Content-MD5");
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

        private Builder() {
            super();
        }

        private Builder(UploadPartRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.body = request.body;
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
         * The number that identifies a part. Valid values: 1 to 10000.The size of a part ranges from 100 KB to 5 GB.  In multipart upload, each part except the last part must be larger than or equal to 100 KB in size. When you call the UploadPart operation, the size of each part is not verified because not all parts have been uploaded and OSS does not know which part is the last part. The size of each part is verified only when you call CompleteMultipartUpload.
         */
        public Builder partNumber(Long value) {
            requireNonNull(value);
            this.parameters.put("partNumber", value.toString());
            return this;
        }

        /**
         * The ID that identifies the object to which the part that you want to upload belongs.
         */
        public Builder uploadId(String value) {
            requireNonNull(value);
            this.parameters.put("uploadId", value);
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
         * The size of the data in the HTTP message body. Unit: bytes.
         */
        public Builder contentLength(Long value) {
            requireNonNull(value);
            this.headers.put("Content-Length", value.toString());
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

        /**
         * The request body.
         */
        public Builder body(BinaryData value) {
            requireNonNull(value);
            this.body = value;
            return this;
        }

        public UploadPartRequest build() {
            return new UploadPartRequest(this);
        }
    }

}
