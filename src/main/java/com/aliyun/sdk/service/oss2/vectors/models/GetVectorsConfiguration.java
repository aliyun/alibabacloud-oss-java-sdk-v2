package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The configuration for getting vectors.
 */
public class GetVectorsConfiguration {
    @JsonProperty("indexName")
    private String indexName;

    @JsonProperty("keys")
    private List<String> keys;

    @JsonProperty("returnData")
    private Boolean returnData;

    @JsonProperty("returnMetadata")
    private Boolean returnMetadata;

    public GetVectorsConfiguration() {
    }

    private GetVectorsConfiguration(Builder builder) {
        this.indexName = builder.indexName;
        this.keys = builder.keys;
        this.returnData = builder.returnData;
        this.returnMetadata = builder.returnMetadata;
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
     * The list of vector keys to retrieve.
     */
    public List<String> keys() {
        return keys;
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

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String indexName;
        private List<String> keys;
        private Boolean returnData;
        private Boolean returnMetadata;

        private Builder() {
        }

        private Builder(GetVectorsConfiguration from) {
            this.indexName = from.indexName;
            this.keys = from.keys;
            this.returnData = from.returnData;
            this.returnMetadata = from.returnMetadata;
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
         * The list of vector keys to retrieve.
         */
        public Builder keys(List<String> keys) {
            requireNonNull(keys);
            this.keys = keys;
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

        public GetVectorsConfiguration build() {
            return new GetVectorsConfiguration(this);
        }
    }
}