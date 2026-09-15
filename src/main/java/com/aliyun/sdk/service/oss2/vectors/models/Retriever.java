package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The retriever of the QueryVectorsFusion operation. Only one of the following retrievers
 * can be specified at a time.
 */
public class Retriever {
    @JsonProperty("rrf")
    private RrfRetriever rrf;
    @JsonProperty("weight")
    private WeightRetriever weight;
    @JsonProperty("simple")
    private SimpleRetriever simple;
    @JsonProperty("knn")
    private Knn knn;

    public Retriever() {
    }

    private Retriever(Builder builder) {
        this.rrf = builder.rrf;
        this.weight = builder.weight;
        this.simple = builder.simple;
        this.knn = builder.knn;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The rrf compound retriever.
     */
    public RrfRetriever rrf() {
        return rrf;
    }

    /**
     * The weight compound retriever.
     */
    public WeightRetriever weight() {
        return weight;
    }

    /**
     * The simple leaf retriever.
     */
    public SimpleRetriever simple() {
        return simple;
    }

    /**
     * The knn leaf retriever.
     */
    public Knn knn() {
        return knn;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private RrfRetriever rrf;
        private WeightRetriever weight;
        private SimpleRetriever simple;
        private Knn knn;

        private Builder() {
        }

        private Builder(Retriever from) {
            this.rrf = from.rrf;
            this.weight = from.weight;
            this.simple = from.simple;
            this.knn = from.knn;
        }

        /**
         * The rrf compound retriever.
         */
        public Builder rrf(RrfRetriever rrf) {
            this.rrf = rrf;
            return this;
        }

        /**
         * The weight compound retriever.
         */
        public Builder weight(WeightRetriever weight) {
            this.weight = weight;
            return this;
        }

        /**
         * The simple leaf retriever.
         */
        public Builder simple(SimpleRetriever simple) {
            this.simple = simple;
            return this;
        }

        /**
         * The knn leaf retriever.
         */
        public Builder knn(Knn knn) {
            this.knn = knn;
            return this;
        }

        public Retriever build() {
            return new Retriever(this);
        }
    }
}
