package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The component of the rrf compound retriever. It wraps a retriever with the fusion weight.
 * The rrf component does not support the normalizer parameter.
 */
public class RrfRetrieverComponent {
    @JsonProperty("retriever")
    private Retriever retriever;
    @JsonProperty("weight")
    private Float weight;

    public RrfRetrieverComponent() {
    }

    private RrfRetrieverComponent(Builder builder) {
        this.retriever = builder.retriever;
        this.weight = builder.weight;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The retriever of this component. It reuses the top level Retriever, so it can be a
     * leaf retriever (knn/simple) or a nested compound retriever.
     */
    public Retriever retriever() {
        return retriever;
    }

    /**
     * The weight of this component. It is a non-negative float32 number. Default value: 1.0.
     */
    public Float weight() {
        return weight;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Retriever retriever;
        private Float weight;

        private Builder() {
        }

        private Builder(RrfRetrieverComponent from) {
            this.retriever = from.retriever;
            this.weight = from.weight;
        }

        /**
         * The retriever of this component. It reuses the top level Retriever, so it can be a
         * leaf retriever (knn/simple) or a nested compound retriever.
         */
        public Builder retriever(Retriever retriever) {
            this.retriever = retriever;
            return this;
        }

        /**
         * The weight of this component. It is a non-negative float32 number. Default value: 1.0.
         */
        public Builder weight(Float weight) {
            this.weight = weight;
            return this;
        }

        public RrfRetrieverComponent build() {
            return new RrfRetrieverComponent(this);
        }
    }
}
