package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

/**
 * The component of a compound retriever. It wraps a retriever with the fusion parameters.
 */
public class RetrieverComponent {
    @JsonProperty("retriever")
    private SubRetriever retriever;
    @JsonProperty("weight")
    private Float weight;
    @JsonProperty("normalizer")
    private String normalizer;

    public RetrieverComponent() {
    }

    private RetrieverComponent(Builder builder) {
        this.retriever = builder.retriever;
        this.weight = builder.weight;
        this.normalizer = builder.normalizer;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The retriever of this component.
     */
    public SubRetriever retriever() {
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
     * It is supported by the weight retriever only.
     */
    public String normalizer() {
        return normalizer;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private SubRetriever retriever;
        private Float weight;
        private String normalizer;

        private Builder() {
        }

        private Builder(RetrieverComponent from) {
            this.retriever = from.retriever;
            this.weight = from.weight;
            this.normalizer = from.normalizer;
        }

        /**
         * The retriever of this component.
         */
        public Builder retriever(SubRetriever retriever) {
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
         * It is supported by the weight retriever only.
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

        public RetrieverComponent build() {
            return new RetrieverComponent(this);
        }
    }
}
