package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The configuration for the vector index.
 */
public class VectorIndexConfiguration {
    @JsonProperty("dataType")
    private String dataType;

    @JsonProperty("dimension")
    private Integer dimension;

    @JsonProperty("distanceMetric")
    private String distanceMetric;

    @JsonProperty("indexName")
    private String indexName;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    public VectorIndexConfiguration() {
    }

    private VectorIndexConfiguration(Builder builder) {
        this.dataType = builder.dataType;
        this.dimension = builder.dimension;
        this.distanceMetric = builder.distanceMetric;
        this.indexName = builder.indexName;
        this.metadata = builder.metadata;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The data type of the vector.
     */
    public String dataType() {
        return dataType;
    }

    /**
     * The dimension of the vector.
     */
    public Integer dimension() {
        return dimension;
    }

    /**
     * The distance metric used for the index.
     */
    public String distanceMetric() {
        return distanceMetric;
    }

    /**
     * The name of the index.
     */
    public String indexName() {
        return indexName;
    }

    /**
     * The metadata of the index.
     */
    public Map<String, Object> metadata() {
        return metadata;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String dataType;
        private Integer dimension;
        private String distanceMetric;
        private String indexName;
        private Map<String, Object> metadata;

        private Builder() {
        }

        private Builder(VectorIndexConfiguration from) {
            this.dataType = from.dataType;
            this.dimension = from.dimension;
            this.distanceMetric = from.distanceMetric;
            this.indexName = from.indexName;
            this.metadata = from.metadata;
        }

        /**
         * The data type of the vector.
         */
        public Builder dataType(String dataType) {
            requireNonNull(dataType);
            this.dataType = dataType;
            return this;
        }

        /**
         * The dimension of the vector.
         */
        public Builder dimension(Integer dimension) {
            requireNonNull(dimension);
            this.dimension = dimension;
            return this;
        }

        /**
         * The distance metric used for the index.
         */
        public Builder distanceMetric(String distanceMetric) {
            requireNonNull(distanceMetric);
            this.distanceMetric = distanceMetric;
            return this;
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
         * The metadata of the index.
         */
        public Builder metadata(Map<String, Object> metadata) {
            requireNonNull(metadata);
            this.metadata = metadata;
            return this;
        }

        public VectorIndexConfiguration build() {
            return new VectorIndexConfiguration(this);
        }
    }
}