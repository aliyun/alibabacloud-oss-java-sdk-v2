package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

/**
 * The component of the weight compound retriever. It wraps a retriever with the fusion
 * weight and the score normalizer.
 */
public class WeightRetrieverComponent {
    @JsonProperty("retriever")
    private Retriever retriever;
    @JsonProperty("weight")
    private Float weight;
    @JsonProperty("normalizer")
    private String normalizer;

    public WeightRetrieverComponent() {
    }

    private WeightRetrieverComponent(Builder builder) {
        this.retriever = builder.retriever;
        this.weight = builder.weight;
        this.normalizer = builder.normalizer;
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

    /**
     * The normalizer of the score. Valid values: none, minMax and l2.
     */
    public String normalizer() {
        return normalizer;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Retriever retriever;
        private Float weight;
        private String normalizer;

        private Builder() {
        }

        private Builder(WeightRetrieverComponent from) {
            this.retriever = from.retriever;
            this.weight = from.weight;
            this.normalizer = from.normalizer;
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

        /**
         * The normalizer of the score. Valid values: none, minMax and l2.
         */
        public Builder normalizer(String normalizer) {
            this.normalizer = normalizer;
            return this;
        }

        /**
         * Set the normalizer of the score using NormalizerType enum.
         */
        public Builder normalizer(NormalizerType normalizer) {
            requireNonNull(normalizer);
            this.normalizer = normalizer.toString();
            return this;
        }

        public WeightRetrieverComponent build() {
            return new WeightRetrieverComponent(this);
        }
    }
}
