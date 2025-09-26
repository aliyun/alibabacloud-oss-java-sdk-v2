package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorIndexesRequestJson;
import static java.util.Objects.requireNonNull;

/**
 * The request for the ListVectorIndexes operation.
 */
public final class ListVectorIndexesRequest extends RequestModel {
    private final String bucket;
    private final ListVectorIndexesRequestJson listVectorIndexesRequestJson;

    private ListVectorIndexesRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.listVectorIndexesRequestJson = builder.listVectorIndexesRequestJson;
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
    public ListVectorIndexesRequestJson listVectorIndexesRequestJson() {
        return listVectorIndexesRequestJson;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private ListVectorIndexesRequestJson listVectorIndexesRequestJson;

        private Builder() {
            super();
            this.listVectorIndexesRequestJson = new ListVectorIndexesRequestJson();
        }

        private Builder(ListVectorIndexesRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.listVectorIndexesRequestJson = request.listVectorIndexesRequestJson;
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
         * The maximum number of indexes to return.
         */
        public Builder maxResults(Long value) {
            this.listVectorIndexesRequestJson.maxResults = value;
            return this;
        }

        /**
         * The token for the next page of indexes.
         */
        public Builder nextToken(String value) {
            this.listVectorIndexesRequestJson.nextToken = value;
            return this;
        }

        /**
         * The prefix to filter indexes by name.
         */
        public Builder prefix(String value) {
            this.listVectorIndexesRequestJson.prefix = value;
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder listVectorIndexesRequestJson(ListVectorIndexesRequestJson listVectorIndexesRequestJson) {
            requireNonNull(listVectorIndexesRequestJson);
            this.listVectorIndexesRequestJson = listVectorIndexesRequestJson;
            return this;
        }

        public ListVectorIndexesRequest build() {
            return new ListVectorIndexesRequest(this);
        }
    }
}
