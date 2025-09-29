package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the ListVectorIndexes operation.
 */
public final class ListVectorIndexesRequest extends RequestModel {
    private final String bucket;
    private final ListVectorIndexesInfo listVectorIndexesInfo;

    private ListVectorIndexesRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.listVectorIndexesInfo = builder.listVectorIndexesInfo;
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
    public ListVectorIndexesInfo listVectorIndexesInfo() {
        return listVectorIndexesInfo;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private ListVectorIndexesInfo listVectorIndexesInfo;

        private Builder() {
            super();
            this.listVectorIndexesInfo = new ListVectorIndexesInfo();
        }

        private Builder(ListVectorIndexesRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.listVectorIndexesInfo = request.listVectorIndexesInfo;
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
            this.listVectorIndexesInfo = this.listVectorIndexesInfo.toBuilder().maxResults(value).build();
            return this;
        }

        /**
         * The token for the next page of indexes.
         */
        public Builder nextToken(String value) {
            this.listVectorIndexesInfo = this.listVectorIndexesInfo.toBuilder().nextToken(value).build();
            return this;
        }

        /**
         * The prefix to filter indexes by name.
         */
        public Builder prefix(String value) {
            this.listVectorIndexesInfo = this.listVectorIndexesInfo.toBuilder().prefix(value).build();
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder listVectorIndexesInfo(ListVectorIndexesInfo listVectorIndexesInfo) {
            requireNonNull(listVectorIndexesInfo);
            this.listVectorIndexesInfo = listVectorIndexesInfo;
            return this;
        }

        public ListVectorIndexesRequest build() {
            return new ListVectorIndexesRequest(this);
        }
    }
}