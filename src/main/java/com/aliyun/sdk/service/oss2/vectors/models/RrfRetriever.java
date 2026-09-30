package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private List<?> retrievers;

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
     * <p>
     * Each element is either a {@link RetrieverComponent} instance or its raw map form
     * ({@link RetrieverComponent#toMap()}), the same mix accepted by the builder.
     */
    public List<?> retrievers() {
        return retrievers;
    }

    /**
     * Converts this retriever to the raw JSON object used on the wire, omitting the attributes
     * that were not set.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (k != null) {
            map.put("k", k);
        }
        if (windowSize != null) {
            map.put("windowSize", windowSize);
        }
        if (retrievers != null) {
            List<Object> components = new ArrayList<>(retrievers.size());
            for (Object component : retrievers) {
                components.add(component instanceof RetrieverComponent
                        ? ((RetrieverComponent) component).toMap()
                        : component);
            }
            map.put("retrievers", components);
        }
        return map;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Integer k;
        private Integer windowSize;
        private List<?> retrievers;

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
         * <p>
         * Pass {@link RetrieverComponent} instances directly, or {@link RetrieverComponent#toMap()}
         * results when the typed model does not cover every attribute.
         */
        public Builder retrievers(List<?> retrievers) {
            this.retrievers = retrievers;
            return this;
        }

        public RrfRetriever build() {
            return new RrfRetriever(this);
        }
    }
}
