package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The information about query vectors result.
 */
public class QueryVectorsInfo {
    @JsonProperty("vectors")
    private List<QueryOutputVector> vectors;

    public QueryVectorsInfo() {
    }

    private QueryVectorsInfo(Builder builder) {
        this.vectors = builder.vectors;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The list of query result vectors.
     */
    public List<QueryOutputVector> vectors() {
        return vectors;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<QueryOutputVector> vectors;

        private Builder() {
        }

        private Builder(QueryVectorsInfo from) {
            this.vectors = from.vectors;
        }

        /**
         * The list of query result vectors.
         */
        public Builder vectors(List<QueryOutputVector> vectors) {
            this.vectors = vectors;
            return this;
        }

        public QueryVectorsInfo build() {
            return new QueryVectorsInfo(this);
        }
    }
}