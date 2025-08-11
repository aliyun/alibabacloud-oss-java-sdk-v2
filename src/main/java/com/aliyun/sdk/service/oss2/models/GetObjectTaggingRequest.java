package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetObjectTagging operation.
 */
public final class GetObjectTaggingRequest extends RequestModel {
    private final String bucket;
    private final String key;

    private GetObjectTaggingRequest(Builder builder) {
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
     * The versionID of the object that you want to query.
     */
    public String versionId() {
        String value = parameters.get("versionId");
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

        private Builder(GetObjectTaggingRequest request) {
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
         * The versionID of the object that you want to query.
         */
        public Builder versionId(String value) {
            requireNonNull(value);
            this.parameters.put("versionId", value);
            return this;
        }

        public GetObjectTaggingRequest build() {
            return new GetObjectTaggingRequest(this);
        }
    }

}
