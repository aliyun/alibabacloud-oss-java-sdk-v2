package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The configuration for deleting vectors.
 */
public class DeleteVectorsConfiguration {
    @JsonProperty("indexName")
    private String indexName;

    @JsonProperty("keys")
    private List<String> keys;

    public DeleteVectorsConfiguration() {
    }

    private DeleteVectorsConfiguration(Builder builder) {
        this.indexName = builder.indexName;
        this.keys = builder.keys;
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
     * The list of vector keys to delete.
     */
    public List<String> keys() {
        return keys;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String indexName;
        private List<String> keys;

        private Builder() {
        }

        private Builder(DeleteVectorsConfiguration from) {
            this.indexName = from.indexName;
            this.keys = from.keys;
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
         * The list of vector keys to delete.
         */
        public Builder keys(List<String> keys) {
            requireNonNull(keys);
            this.keys = keys;
            return this;
        }

        public DeleteVectorsConfiguration build() {
            return new DeleteVectorsConfiguration(this);
        }
    }
}