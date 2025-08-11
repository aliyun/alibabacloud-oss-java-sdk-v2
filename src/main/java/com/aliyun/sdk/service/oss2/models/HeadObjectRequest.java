package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the HeadObject operation.
 */
public final class HeadObjectRequest extends RequestModel {
    private final String bucket;
    private final String key;

    private HeadObjectRequest(Builder builder) {
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
     * If the time that is specified in the request is earlier than the time when the object is modified, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 304 not modified. Default value: null.
     */
    public String ifModifiedSince() {
        String value = headers.get("If-Modified-Since");
        return value;
    }

    /**
     * If the time that is specified in the request is later than or the same as the time when the object is modified, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 412 precondition failed. Default value: null.
     */
    public String ifUnmodifiedSince() {
        String value = headers.get("If-Unmodified-Since");
        return value;
    }

    /**
     * If the ETag value that is specified in the request matches the ETag value of the object, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 412 precondition failed. Default value: null.
     */
    public String ifMatch() {
        String value = headers.get("If-Match");
        return value;
    }

    /**
     * If the ETag value that is specified in the request does not match the ETag value of the object, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 304 Not Modified. Default value: null.
     */
    public String ifNoneMatch() {
        String value = headers.get("If-None-Match");
        return value;
    }

    /**
     * The version ID of the object for which you want to query metadata.
     */
    public String versionId() {
        String value = parameters.get("versionId");
        return value;
    }

    /**
     * To indicate that the requester is aware that the request and data download will incur costs. Default value: null
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

        private Builder(HeadObjectRequest request) {
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
         * If the time that is specified in the request is earlier than the time when the object is modified, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 304 not modified. Default value: null.
         */
        public Builder ifModifiedSince(String value) {
            requireNonNull(value);
            this.headers.put("If-Modified-Since", value);
            return this;
        }

        /**
         * If the time that is specified in the request is later than or the same as the time when the object is modified, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 412 precondition failed. Default value: null.
         */
        public Builder ifUnmodifiedSince(String value) {
            requireNonNull(value);
            this.headers.put("If-Unmodified-Since", value);
            return this;
        }

        /**
         * If the ETag value that is specified in the request matches the ETag value of the object, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 412 precondition failed. Default value: null.
         */
        public Builder ifMatch(String value) {
            requireNonNull(value);
            this.headers.put("If-Match", value);
            return this;
        }

        /**
         * If the ETag value that is specified in the request does not match the ETag value of the object, OSS returns 200 OK and the metadata of the object. Otherwise, OSS returns 304 Not Modified. Default value: null.
         */
        public Builder ifNoneMatch(String value) {
            requireNonNull(value);
            this.headers.put("If-None-Match", value);
            return this;
        }

        /**
         * The version ID of the object for which you want to query metadata.
         */
        public Builder versionId(String value) {
            requireNonNull(value);
            this.parameters.put("versionId", value);
            return this;
        }

        /**
         * To indicate that the requester is aware that the request and data download will incur costs. Default value: null
         */
        public HeadObjectRequest.Builder requestPayer(String value) {
            requireNonNull(value);
            this.headers.put("x-oss-request-payer", value);
            return this;
        }

        public HeadObjectRequest build() {
            return new HeadObjectRequest(this);
        }
    }

}
