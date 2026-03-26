package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetObjectLegalHold operation.
 */
public final class GetObjectLegalHoldRequest extends RequestModel {
    private final String bucket;
    private final String key;

    private GetObjectLegalHoldRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The name of the object.
     */
    public String key() {
        return key;
    }

    /**
     * The version ID of the object.
     */
    public String versionId() {
        String value = parameters.get("versionId");
        return value;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String key;

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The name of the object.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The version ID of the object.
         */
        public Builder versionId(String value) {
            this.parameters.put("versionId", value);
            return this;
        }

        public GetObjectLegalHoldRequest build() {
            return new GetObjectLegalHoldRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetObjectLegalHoldRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
        }
    }
}
