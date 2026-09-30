package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * The summary of the vectors returned by the QueryVectorsFusion operation.
 */
public class QueryVectorsFusionSummary {
    @JsonProperty("key")
    private String key;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @JsonProperty("score")
    private Float score;

    public QueryVectorsFusionSummary() {
    }

    private QueryVectorsFusionSummary(Builder builder) {
        this.key = builder.key;
        this.metadata = builder.metadata;
        this.score = builder.score;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The key of the vector.
     */
    public String key() {
        return key;
    }

    /**
     * The metadata of the vector.
     */
    public Map<String, Object> metadata() {
        return metadata;
    }

    /**
     * The relevance score of the vector.
     */
    public Float score() {
        return score;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String key;
        private Map<String, Object> metadata;
        private Float score;

        private Builder() {
        }

        private Builder(QueryVectorsFusionSummary from) {
            this.key = from.key;
            this.metadata = from.metadata;
            this.score = from.score;
        }

        /**
         * The key of the vector.
         */
        public Builder key(String key) {
            this.key = key;
            return this;
        }

        /**
         * The metadata of the vector.
         */
        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * The relevance score of the vector.
         */
        public Builder score(Float score) {
            this.score = score;
            return this;
        }

        public QueryVectorsFusionSummary build() {
            return new QueryVectorsFusionSummary(this);
        }
    }
}
