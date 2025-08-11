package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutObjectTagging operation.
 */
public final class PutObjectTaggingRequest extends RequestModel {
    private final String bucket;
    private final String key;
    private final Tagging tagging;

    private PutObjectTaggingRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.key = builder.key;
        this.tagging = builder.tagging;
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
     * The name of the object.
     */
    public String key() {
        return key;
    }

    /**
     * The version id of the target object.
     */
    public String versionId() {
        String value = parameters.get("versionId");
        return value;
    }

    /**
     * The request body schema.
     */
    public Tagging tagging() {
        return tagging;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String key;
        private Tagging tagging;

        private Builder() {
            super();
        }

        private Builder(PutObjectTaggingRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.key = request.key;
            this.tagging = request.tagging;
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
         * The name of the object.
         */
        public Builder key(String value) {
            requireNonNull(value);
            this.key = value;
            return this;
        }

        /**
         * The version id of the target object.
         */
        public Builder versionId(String value) {
            requireNonNull(value);
            this.parameters.put("versionId", value);
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder tagging(Tagging value) {
            requireNonNull(value);
            this.tagging = value;
            return this;
        }

        public PutObjectTaggingRequest build() {
            return new PutObjectTaggingRequest(this);
        }
    }

}
