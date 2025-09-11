package com.aliyun.sdk.service.oss2.vectors.models;


import com.aliyun.sdk.service.oss2.models.RequestModel;

/**
 * The request for the ListVectorBuckets operation.
 */
public final class ListVectorBucketsRequest extends RequestModel {

    private ListVectorBucketsRequest(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket from which the list operation begins.
     */
    public String marker() {
        return parameters.get("marker");
    }

    /**
     * The maximum number of buckets that can be returned in the single query.
     * Valid values: 1 to 1000.
     */
    public Integer maxKeys() {
        String value = parameters.get("max-keys");
        return value != null ? Integer.valueOf(value) : null;
    }

    /**
     * The prefix that the names of returned buckets must contain.
     * Limits the response to keys that begin with the specified prefix
     */
    public String prefix() {
        return parameters.get("prefix");
    }

    /**
     * The ID of the resource group.
     */
    public String resourceGroupId() {
        return headers.get("x-oss-resource-group-id");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(ListVectorBucketsRequest request) {
            super(request);
        }

        /**
         * The name of the bucket from which the list operation begins.
         */
        public Builder marker(String value) {
            this.parameters.put("marker", value);
            return this;
        }

        /**
         * The maximum number of buckets that can be returned in the single query.
         * Valid values: 1 to 1000.
         */
        public Builder maxKeys(Integer value) {
            this.parameters.put("max-keys", String.valueOf(value));
            return this;
        }

        /**
         * The prefix that the names of returned buckets must contain.
         * Limits the response to keys that begin with the specified prefix
         */
        public Builder prefix(String value) {
            this.parameters.put("prefix", value);
            return this;
        }

        /**
         * The ID of the resource group.
         */
        public Builder resourceGroupId(String value) {
            this.headers.put("x-oss-resource-group-id", value);
            return this;
        }

        public ListVectorBucketsRequest build() {
            return new ListVectorBucketsRequest(this);
        }
    }
}

