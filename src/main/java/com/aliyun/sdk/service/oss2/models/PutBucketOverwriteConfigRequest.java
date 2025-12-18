package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketOverwriteConfig operation.
 */
public final class PutBucketOverwriteConfigRequest extends RequestModel {
    private final String bucket;
    private final OverwriteConfiguration overwriteConfiguration;

    private PutBucketOverwriteConfigRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.overwriteConfiguration = builder.overwriteConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Bucket Name
     */
    public String bucket() {
        return bucket;
    }

    /**
     * Structure of the API Request Body
     */
    public OverwriteConfiguration overwriteConfiguration() {
        return overwriteConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private OverwriteConfiguration overwriteConfiguration;

        private Builder() {
            super();
        }

        private Builder(PutBucketOverwriteConfigRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.overwriteConfiguration = request.overwriteConfiguration;
        }

        /**
         * Bucket Name
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * Structure of the API Request Body
         */
        public Builder overwriteConfiguration(OverwriteConfiguration value) {
            requireNonNull(value);
            this.overwriteConfiguration = value;
            return this;
        }

        public PutBucketOverwriteConfigRequest build() {
            return new PutBucketOverwriteConfigRequest(this);
        }
    }

}
