package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CreateAgenticBucket operation.
 */
public final class CreateAgenticBucketRequest extends RequestModel {
    private final String bucket;
    private final CreateAgenticBucketConfiguration createAgenticBucketConfiguration;

    private CreateAgenticBucketRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.createAgenticBucketConfiguration = builder.createAgenticBucketConfiguration;
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
     * The configuration for creating the agentic bucket.
     */
    public CreateAgenticBucketConfiguration createAgenticBucketConfiguration() {
        return createAgenticBucketConfiguration;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private CreateAgenticBucketConfiguration createAgenticBucketConfiguration;

        private Builder() {
            super();
        }

        private Builder(CreateAgenticBucketRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.createAgenticBucketConfiguration = request.createAgenticBucketConfiguration;
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
         * The configuration for creating the agentic bucket.
         */
        public Builder createAgenticBucketConfiguration(CreateAgenticBucketConfiguration value) {
            this.createAgenticBucketConfiguration = value;
            return this;
        }

        public CreateAgenticBucketRequest build() {
            return new CreateAgenticBucketRequest(this);
        }
    }
}
