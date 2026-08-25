package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

/**
 * The request for the ListAgenticBuckets operation.
 */
public final class ListAgenticBucketsRequest extends RequestModel {

    private ListAgenticBucketsRequest(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
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

        private Builder() {
            super();
        }

        private Builder(ListAgenticBucketsRequest request) {
            super(request);
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

        public ListAgenticBucketsRequest build() {
            return new ListAgenticBucketsRequest(this);
        }
    }
}
