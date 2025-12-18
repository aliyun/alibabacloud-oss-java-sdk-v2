package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketOverwriteConfig operation.
 */
public final class GetBucketOverwriteConfigRequest extends RequestModel {
    private final String bucket;

    private GetBucketOverwriteConfigRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
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

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }


        private Builder(GetBucketOverwriteConfigRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        /**
         * Bucket Name
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public GetBucketOverwriteConfigRequest build() {
            return new GetBucketOverwriteConfigRequest(this);
        }
    }

}
