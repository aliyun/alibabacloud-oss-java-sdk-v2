package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the SealAppendObject operation.
 */
public final class SealAppendObjectRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final String position;

    private SealAppendObjectRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.position = builder.position;
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
     * The position from which the AppendObject operation starts.
     */
    public String position() {
        return position;
    }

    /**
     * The tag of the destination object. You can add multiple tags to the destination object. Example: TagA=A&amp;TagB=B.  The tag key and tag value must be URL-encoded. If a key-value pair does not contain an equal sign (=), the tag value is considered an empty string.
     */
    public String tagging() {
        String value = headers.get("x-oss-tagging");
        return value;
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
        private String position;

        private Builder() {
            super();
        }

        private Builder(SealAppendObjectRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.position = request.position;
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
         * The position from which the AppendObject operation starts.
         */
        public Builder position(String value) {
            requireNonNull(value);
            this.position = value;
            return this;
        }

        /**
         * The position from which the AppendObject operation starts.
         */
        public Builder position(int value) {
            this.position = String.valueOf(value);
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

        public SealAppendObjectRequest build() {
            return new SealAppendObjectRequest(this);
        }
    }
}
