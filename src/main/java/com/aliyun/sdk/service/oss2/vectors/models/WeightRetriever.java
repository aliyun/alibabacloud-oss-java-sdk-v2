package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The weight compound retriever. It merges the results of the sub retrievers by weight.
 */
public class WeightRetriever {
    @JsonProperty("windowSize")
    private Integer windowSize;
    @JsonProperty("retrievers")
    private List<RetrieverComponent> retrievers;

    public WeightRetriever() {
    }

    private WeightRetriever(Builder builder) {
        this.windowSize = builder.windowSize;
        this.retrievers = builder.retrievers;
    }

    public static Builder newBuilder() {
        return new Builder();
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
        private Integer windowSize;
        private List<RetrieverComponent> retrievers;

        private Builder() {
        }

        private Builder(WeightRetriever from) {
            this.windowSize = from.windowSize;
            this.retrievers = from.retrievers;
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

        public WeightRetriever build() {
            return new WeightRetriever(this);
        }
    }
}
