package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketObjectWormConfiguration operation.
 */
public final class PutBucketObjectWormConfigurationRequest extends RequestModel {
    private final String bucket;
    private final ObjectWormConfiguration objectWormConfiguration;

    private PutBucketObjectWormConfigurationRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.objectWormConfiguration = builder.objectWormConfiguration;
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The container that stores the object-level retention policy configuration.
     */
    public ObjectWormConfiguration objectWormConfiguration() {
        return objectWormConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private ObjectWormConfiguration objectWormConfiguration;

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The container that stores the object-level retention policy configuration.
         */
        public Builder objectWormConfiguration(ObjectWormConfiguration value) {
            this.objectWormConfiguration = value;
            return this;
        }

        public PutBucketObjectWormConfigurationRequest build() {
            return new PutBucketObjectWormConfigurationRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketObjectWormConfigurationRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.objectWormConfiguration = request.objectWormConfiguration;
        }
    }
}
