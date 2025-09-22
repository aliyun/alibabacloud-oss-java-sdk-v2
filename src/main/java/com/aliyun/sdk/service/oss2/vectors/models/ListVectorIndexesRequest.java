package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the ListVectorIndexes operation.
 */
public final class ListVectorIndexesRequest extends RequestModel {
    private final String bucket;
    private final Integer maxResults;
    private final String nextToken;
    private final String prefix;

    private ListVectorIndexesRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.maxResults = builder.maxResults;
        this.nextToken = builder.nextToken;
        this.prefix = builder.prefix;
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
     * The maximum number of indexes to return.
     */
    public Integer maxResults() {
        return maxResults;
    }

    /**
     * The token for the next page of indexes.
     */
    public String nextToken() {
        return nextToken;
    }

    /**
     * The prefix to filter indexes by name.
     */
    public String prefix() {
        return prefix;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private Integer maxResults;
        private String nextToken;
        private String prefix;

        private Builder() {
            super();
        }

        private Builder(ListVectorIndexesRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.maxResults = request.maxResults;
            this.nextToken = request.nextToken;
            this.prefix = request.prefix;
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
        public Builder maxResults(Integer value) {
            this.maxResults = value;
            return this;
        }

        /**
         * The token for the next page of indexes.
         */
        public Builder nextToken(String value) {
            this.nextToken = value;
            return this;
        }

        /**
         * The prefix to filter indexes by name.
         */
        public Builder prefix(String value) {
            this.prefix = value;
            return this;
        }

        public ListVectorIndexesRequest build() {
            return new ListVectorIndexesRequest(this);
        }
    }
}

