package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the RestoreObject operation.
 */
public final class RestoreObjectRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final RestoreRequest restoreRequest;

    private RestoreObjectRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.restoreRequest = builder.restoreRequest;
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
     * The version number of the object that you want to restore.
     */
    public String versionId() {
        String value = parameters.get("versionId");
        return value;
    }

    /**
     * The request body schema.
     */
    public RestoreRequest restoreRequest() {
        return restoreRequest;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String key;
        private RestoreRequest restoreRequest;

        private Builder() {
            super();
        }

        private Builder(RestoreObjectRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.restoreRequest = request.restoreRequest;
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
         * The version number of the object that you want to restore.
         */
        public Builder versionId(String value) {
            requireNonNull(value);
            this.parameters.put("versionId", value);
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder restoreRequest(RestoreRequest value) {
            requireNonNull(value);
            this.restoreRequest = value;
            return this;
        }

        public RestoreObjectRequest build() {
            return new RestoreObjectRequest(this);
        }
    }

}
