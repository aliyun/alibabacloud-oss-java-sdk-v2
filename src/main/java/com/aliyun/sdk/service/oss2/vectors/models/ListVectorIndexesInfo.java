package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import static java.util.Objects.requireNonNull;

/**
 * Model for listing vector indexes info
 */
public class ListVectorIndexesInfo {
    @JsonProperty("maxResults")
    private Long maxResults;

    @JsonProperty("nextToken")
    private String nextToken;

    @JsonProperty("prefix")
    private String prefix;

    public ListVectorIndexesInfo() {
    }

    private ListVectorIndexesInfo(Builder builder) {
        this.maxResults = builder.maxResults;
        this.nextToken = builder.nextToken;
        this.prefix = builder.prefix;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The maximum number of indexes to return.
     */
    public Long maxResults() {
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

    public static class Builder {
        private Long maxResults;
        private String nextToken;
        private String prefix;

        private Builder() {
        }

        private Builder(ListVectorIndexesInfo from) {
            this.maxResults = from.maxResults;
            this.nextToken = from.nextToken;
            this.prefix = from.prefix;
        }

        /**
         * The maximum number of indexes to return.
         */
        public Builder maxResults(Long maxResults) {
            this.maxResults = maxResults;
            return this;
        }

        /**
         * The token for the next page of indexes.
         */
        public Builder nextToken(String nextToken) {
            this.nextToken = nextToken;
            return this;
        }

        /**
         * The prefix to filter indexes by name.
         */
        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public ListVectorIndexesInfo build() {
            return new ListVectorIndexesInfo(this);
        }
    }
}