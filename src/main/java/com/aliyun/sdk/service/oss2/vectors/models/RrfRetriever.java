package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The rrf compound retriever. It merges the results of the sub retrievers by the
 * Reciprocal Rank Fusion algorithm.
 */
public class RrfRetriever {
    @JsonProperty("k")
    private Integer k;
    @JsonProperty("windowSize")
    private Integer windowSize;
    @JsonProperty("retrievers")
    private List<RetrieverComponent> retrievers;

    public RrfRetriever() {
    }

    private RrfRetriever(Builder builder) {
        this.k = builder.k;
        this.windowSize = builder.windowSize;
        this.retrievers = builder.retrievers;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The constant k in the RRF formula: score = sum of 1/(k + rank).
     * Valid values: 1 to 65536. Default value: 50.
     */
    public Integer k() {
        return k;
    }

    /**
     * The number of the top results taken from each sub retriever. Default value: 100.
     */
    public Integer windowSize() {
        return windowSize;
    }

    /**
     * The sub retrievers. It contains 1 to 3 elements.
     */
    public List<RetrieverComponent> retrievers() {
        return retrievers;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Integer k;
        private Integer windowSize;
        private List<RetrieverComponent> retrievers;

        private Builder() {
        }

        private Builder(RrfRetriever from) {
            this.k = from.k;
            this.windowSize = from.windowSize;
            this.retrievers = from.retrievers;
        }

        /**
         * The constant k in the RRF formula: score = sum of 1/(k + rank).
         * Valid values: 1 to 65536. Default value: 50.
         */
        public Builder k(Integer k) {
            this.k = k;
            return this;
        }

        /**
         * The number of the top results taken from each sub retriever. Default value: 100.
         */
        public Builder windowSize(Integer windowSize) {
            this.windowSize = windowSize;
            return this;
        }

        /**
         * The sub retrievers. It contains 1 to 3 elements.
         */
        public Builder retrievers(List<RetrieverComponent> retrievers) {
            this.retrievers = retrievers;
            return this;
        }

        public RrfRetriever build() {
            return new RrfRetriever(this);
        }
    }
}
