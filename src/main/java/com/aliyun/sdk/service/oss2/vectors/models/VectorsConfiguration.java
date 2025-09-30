package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The configuration for the vectors.
 */
public class VectorsConfiguration {
    @JsonProperty("indexName")
    private String indexName;

    @JsonProperty("vectors")
    private List<Map<String, Object>> vectors;

    public VectorsConfiguration() {
    }

    private VectorsConfiguration(Builder builder) {
        this.indexName = builder.indexName;
        this.vectors = builder.vectors;
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
     * The vectors to insert.
     */
    public List<Map<String, Object>> vectors() {
        return vectors;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String indexName;
        private List<Map<String, Object>> vectors;

        private Builder() {
        }

        private Builder(VectorsConfiguration from) {
            this.indexName = from.indexName;
            this.vectors = from.vectors;
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
         * The vectors to insert.
         */
        public Builder vectors(List<Map<String, Object>> vectors) {
            requireNonNull(vectors);
            this.vectors = vectors;
            return this;
        }

        public VectorsConfiguration build() {
            return new VectorsConfiguration(this);
        }
    }
}