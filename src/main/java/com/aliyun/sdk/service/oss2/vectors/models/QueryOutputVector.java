package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The information about the query output vector.
 */
public class QueryOutputVector {
    @JsonProperty("distance")
    private Integer distance;

    @JsonProperty("key")
    private String key;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    public QueryOutputVector() {
    }

    private QueryOutputVector(Builder builder) {
        this.distance = builder.distance;
        this.key = builder.key;
        this.metadata = builder.metadata;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The distance of the vector.
     */
    public Integer distance() {
        return distance;
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

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Integer distance;
        private String key;
        private Map<String, Object> metadata;

        private Builder() {
        }

        private Builder(QueryOutputVector from) {
            this.distance = from.distance;
            this.key = from.key;
            this.metadata = from.metadata;
        }

        /**
         * The distance of the vector.
         */
        public Builder distance(Integer distance) {
            this.distance = distance;
            return this;
        }

        /**
         * The key of the vector.
         */
        public Builder key(String key) {
            requireNonNull(key);
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

        public QueryOutputVector build() {
            return new QueryOutputVector(this);
        }
    }
}