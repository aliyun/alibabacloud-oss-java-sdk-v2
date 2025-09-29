package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;

/**
 * The configuration for querying vectors.
 */
public class QueryVectorsConfiguration {
    @JsonProperty("filter")
    private Map<String, Object> filter;

    @JsonProperty("indexName")
    private String indexName;

    @JsonProperty("queryVector")
    private QueryVector queryVector;

    @JsonProperty("returnDistance")
    private Boolean returnDistance;

    @JsonProperty("returnMetadata")
    private Boolean returnMetadata;

    @JsonProperty("topK")
    private Integer topK;

    public QueryVectorsConfiguration() {
    }

    private QueryVectorsConfiguration(Builder builder) {
        this.filter = builder.filter;
        this.indexName = builder.indexName;
        this.queryVector = builder.queryVector;
        this.returnDistance = builder.returnDistance;
        this.returnMetadata = builder.returnMetadata;
        this.topK = builder.topK;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The filter for querying vectors.
     */
    public Map<String, Object> filter() {
        return filter;
    }

    /**
     * The name of the index.
     */
    public String indexName() {
        return indexName;
    }

    /**
     * The query vector.
     */
    public QueryVector queryVector() {
        return queryVector;
    }

    /**
     * Whether to return distance.
     */
    public Boolean returnDistance() {
        return returnDistance;
    }

    /**
     * Whether to return metadata.
     */
    public Boolean returnMetadata() {
        return returnMetadata;
    }

    /**
     * The number of top K vectors to return.
     */
    public Integer topK() {
        return topK;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Map<String, Object> filter;
        private String indexName;
        private QueryVector queryVector;
        private Boolean returnDistance;
        private Boolean returnMetadata;
        private Integer topK;

        private Builder() {
        }

        private Builder(QueryVectorsConfiguration from) {
            this.filter = from.filter;
            this.indexName = from.indexName;
            this.queryVector = from.queryVector;
            this.returnDistance = from.returnDistance;
            this.returnMetadata = from.returnMetadata;
            this.topK = from.topK;
        }

        /**
         * The filter for querying vectors.
         */
        public Builder filter(Map<String, Object> filter) {
            this.filter = filter;
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
         * The query vector.
         */
        public Builder queryVector(QueryVector queryVector) {
            this.queryVector = queryVector;
            return this;
        }

        /**
         * Whether to return distance.
         */
        public Builder returnDistance(Boolean returnDistance) {
            this.returnDistance = returnDistance;
            return this;
        }

        /**
         * Whether to return metadata.
         */
        public Builder returnMetadata(Boolean returnMetadata) {
            this.returnMetadata = returnMetadata;
            return this;
        }

        /**
         * The number of top K vectors to return.
         */
        public Builder topK(Integer topK) {
            this.topK = topK;
            return this;
        }

        public QueryVectorsConfiguration build() {
            return new QueryVectorsConfiguration(this);
        }
    }
    
    /**
     * The query vector data.
     */
    public static class QueryVector {
        @JsonProperty("float32")
        private List<Float> float32;

        public QueryVector() {
        }

        private QueryVector(Builder builder) {
            this.float32 = builder.float32;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        /**
         * The float32 vector data.
         */
        public List<Float> float32() {
            return float32;
        }

        public Builder toBuilder() {
            return new Builder(this);
        }

        public static class Builder {
            private List<Float> float32;

            private Builder() {
            }

            private Builder(QueryVector from) {
                this.float32 = from.float32;
            }

            /**
             * The float32 vector data.
             */
            public Builder float32(List<Float> float32) {
                requireNonNull(float32);
                this.float32 = float32;
                return this;
            }

            public QueryVector build() {
                return new QueryVector(this);
            }
        }
    }
}