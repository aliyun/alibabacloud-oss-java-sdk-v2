package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import static java.util.Objects.requireNonNull;

/**
 * Model for vector index name info
 */
public class VectorIndexNameInfo {
    @JsonProperty("indexName")
    private String indexName;

    public VectorIndexNameInfo() {
    }

    private VectorIndexNameInfo(Builder builder) {
        this.indexName = builder.indexName;
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

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String indexName;

        private Builder() {
        }

        private Builder(VectorIndexNameInfo from) {
            this.indexName = from.indexName;
        }

        /**
         * The name of the index.
         */
        public Builder indexName(String indexName) {
            requireNonNull(indexName);
            this.indexName = indexName;
            return this;
        }

        public VectorIndexNameInfo build() {
            return new VectorIndexNameInfo(this);
        }
    }
}