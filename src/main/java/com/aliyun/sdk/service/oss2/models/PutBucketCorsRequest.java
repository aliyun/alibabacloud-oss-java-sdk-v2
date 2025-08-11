package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketCors operation.
 */
public final class PutBucketCorsRequest extends RequestModel {
    private final String bucket;
    private final CORSConfiguration corsConfiguration;

    private PutBucketCorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.corsConfiguration = builder.corsConfiguration;
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
     * The request body schema.
     */
    public CORSConfiguration corsConfiguration() {
        return corsConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private CORSConfiguration corsConfiguration;

        private Builder() {
            super();
        }

        private Builder(PutBucketCorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.corsConfiguration = request.corsConfiguration;
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
         * The request body schema.
         */
        public Builder corsConfiguration(CORSConfiguration value) {
            requireNonNull(value);
            this.corsConfiguration = value;
            return this;
        }

        public PutBucketCorsRequest build() {
            return new PutBucketCorsRequest(this);
        }
    }

}
