package com.aliyun.sdk.service.oss2.vectors.models;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * The component of a compound retriever ({@link RrfRetriever} or {@link WeightRetriever}). It wraps
 * a sub retriever with the fusion weight, and optionally the score normalizer.
 * <p>
 * The attributes are kept as the raw JSON object ({@link #toMap()}), so attributes that the service
 * adds later do not require an SDK change. The rrf compound retriever does not use the normalizer
 * parameter; leave it unset there.
 */
public class RetrieverComponent {
    private final Map<String, Object> bodyFields = new LinkedHashMap<>();

    public RetrieverComponent() {
    }

    private RetrieverComponent(Builder builder) {
        this.bodyFields.putAll(builder.bodyFields);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The nested retriever as the raw JSON object. It can be a leaf retriever (knn/simple) or a
     * nested compound retriever (rrf/weight).
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> retriever() {
        Object value = this.bodyFields.get("retriever");
        return value instanceof Map ? (Map<String, Object>) value : null;
    }

    /**
     * The weight of this component. It is a non-negative float32 number. Default value: 1.0.
     */
    public Float weight() {
        return (Float) this.bodyFields.get("weight");
    }

    /**
     * The normalizer of the score. Valid values: none, minMax and l2. It applies to the weight
     * compound retriever only.
     */
    public String normalizer() {
        return (String) this.bodyFields.get("normalizer");
    }

    /**
     * The raw JSON object used on the wire, omitting the attributes that were not set.
     */
    public Map<String, Object> toMap() {
        return bodyFields;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private final Map<String, Object> bodyFields = new LinkedHashMap<>();

        private Builder() {
        }

        private Builder(RetrieverComponent from) {
            this.bodyFields.putAll(from.bodyFields);
        }

        /**
         * The nested retriever as the raw JSON object. Use it when the retriever has attributes
         * that no strongly-typed model covers yet: the map is passed through verbatim. It also
         * carries a nested compound retriever, for example {@code rrfRetriever.toMap()}.
         */
        public Builder retriever(Map<String, Object> value) {
            requireNonNull(value);
            this.bodyFields.put("retriever", value);
            return this;
        }

        /**
         * The knn leaf retriever of this component, stored as {@code {"knn": ...}}.
         */
        public Builder retriever(Knn value) {
            requireNonNull(value);
            this.bodyFields.put("retriever", wrap("knn", value.toMap()));
            return this;
        }

        /**
         * The simple leaf retriever of this component, stored as {@code {"simple": ...}}.
         */
        public Builder retriever(SimpleRetriever value) {
            requireNonNull(value);
            this.bodyFields.put("retriever", wrap("simple", value.toMap()));
            return this;
        }

        private static Map<String, Object> wrap(String key, Map<String, Object> body) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(key, body);
            return map;
        }

        /**
         * The weight of this component. It is a non-negative float32 number. Default value: 1.0.
         */
        public Builder weight(Float value) {
            requireNonNull(value);
            this.bodyFields.put("weight", value);
            return this;
        }

        /**
         * The normalizer of the score. Valid values: none, minMax and l2. It applies to the weight
         * compound retriever only.
         */
        public Builder normalizer(String value) {
            requireNonNull(value);
            this.bodyFields.put("normalizer", value);
            return this;
        }

        /**
         * Set the normalizer of the score using NormalizerType enum.
         */
        public Builder normalizer(NormalizerType value) {
            requireNonNull(value);
            return normalizer(value.toString());
        }

        public RetrieverComponent build() {
            return new RetrieverComponent(this);
        }
    }
}
