package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ListBucketSpaces operation.
 */
public final class ListBucketSpacesRequest extends RequestModel {
    private final String bucket;

    private ListBucketSpacesRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
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
     * The prefix that the returned names must contain.
     */
    public String prefix() {
        return parameters.get("prefix");
    }

    /**
     * The token from which the list operation continues.
     */
    public String continuationToken() {
        return parameters.get("continuation-token");
    }

    /**
     * The maximum number of results to return.
     */
    public Long maxKeys() {
        String value = parameters.get("max-keys");
        return value != null ? Long.valueOf(value) : null;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() { super(); }

        private Builder(ListBucketSpacesRequest request) {
            super(request);
            this.bucket = request.bucket;
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
         * The prefix that the returned names must contain.
         */
        public Builder prefix(String value) {
            this.parameters.put("prefix", value);
            return this;
        }

        /**
         * The token from which the list operation continues.
         */
        public Builder continuationToken(String value) {
            this.parameters.put("continuation-token", value);
            return this;
        }

        /**
         * The maximum number of results to return.
         */
        public Builder maxKeys(Long value) {
            this.parameters.put("max-keys", String.valueOf(value));
            return this;
        }

        public ListBucketSpacesRequest build() {
            return new ListBucketSpacesRequest(this);
        }
    }
}
