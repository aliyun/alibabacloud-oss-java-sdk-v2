package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The configuration for listing vectors.
 */
public class ListVectorsConfiguration {
    @JsonProperty("indexName")
    private String indexName;

    @JsonProperty("maxResults")
    private Integer maxResults;

    @JsonProperty("nextToken")
    private String nextToken;

    @JsonProperty("returnData")
    private Boolean returnData;

    @JsonProperty("returnMetadata")
    private Boolean returnMetadata;

    @JsonProperty("segmentCount")
    private Integer segmentCount;

    @JsonProperty("segmentIndex")
    private Integer segmentIndex;

    public ListVectorsConfiguration() {
    }

    private ListVectorsConfiguration(Builder builder) {
        this.indexName = builder.indexName;
        this.maxResults = builder.maxResults;
        this.nextToken = builder.nextToken;
        this.returnData = builder.returnData;
        this.returnMetadata = builder.returnMetadata;
        this.segmentCount = builder.segmentCount;
        this.segmentIndex = builder.segmentIndex;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the index.
     */
    public String indexName() {
        return indexName;
    }

    /**
     * The maximum number of vectors to return.
     */
    public Integer maxResults() {
        return maxResults;
    }

    /**
     * The token for the next page of vectors.
     */
    public String nextToken() {
        return nextToken;
    }

    /**
     * Whether to return vector data.
     */
    public Boolean returnData() {
        return returnData;
    }

    /**
     * Whether to return vector metadata.
     */
    public Boolean returnMetadata() {
        return returnMetadata;
    }

    /**
     * Number of concurrent segments.
     */
    public Integer segmentCount() {
        return segmentCount;
    }

    /**
     * Current segment index.
     */
    public Integer segmentIndex() {
        return segmentIndex;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String indexName;
        private Integer maxResults;
        private String nextToken;
        private Boolean returnData;
        private Boolean returnMetadata;
        private Integer segmentCount;
        private Integer segmentIndex;

        private Builder() {
        }

        private Builder(ListVectorsConfiguration from) {
            this.indexName = from.indexName;
            this.maxResults = from.maxResults;
            this.nextToken = from.nextToken;
            this.returnData = from.returnData;
            this.returnMetadata = from.returnMetadata;
            this.segmentCount = from.segmentCount;
            this.segmentIndex = from.segmentIndex;
        }

        /**
         * The name of the index.
         */
        public Builder indexName(String indexName) {
            requireNonNull(indexName);
            this.indexName = indexName;
            return this;
        }

        /**
         * The maximum number of vectors to return.
         */
        public Builder maxResults(Integer maxResults) {
            this.maxResults = maxResults;
            return this;
        }

        /**
         * The token for the next page of vectors.
         */
        public Builder nextToken(String nextToken) {
            this.nextToken = nextToken;
            return this;
        }

        /**
         * Whether to return vector data.
         */
        public Builder returnData(Boolean returnData) {
            this.returnData = returnData;
            return this;
        }

        /**
         * Whether to return vector metadata.
         */
        public Builder returnMetadata(Boolean returnMetadata) {
            this.returnMetadata = returnMetadata;
            return this;
        }

        /**
         * Number of concurrent segments.
         */
        public Builder segmentCount(Integer segmentCount) {
            this.segmentCount = segmentCount;
            return this;
        }

        /**
         * Current segment index.
         */
        public Builder segmentIndex(Integer segmentIndex) {
            this.segmentIndex = segmentIndex;
            return this;
        }

        public ListVectorsConfiguration build() {
            return new ListVectorsConfiguration(this);
        }
    }
}