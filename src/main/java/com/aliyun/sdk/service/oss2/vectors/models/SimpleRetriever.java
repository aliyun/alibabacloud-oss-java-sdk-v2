package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The simple retriever that queries documents by the specified conditions.
 */
public class SimpleRetriever {
    @JsonProperty("query")
    private Object query;

    public SimpleRetriever() {
    }

    private SimpleRetriever(Builder builder) {
        this.query = builder.query;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The query conditions. The syntax is the same as the query parameter of the
     * QueryVectorsFusion operation.
     */
    public Object query() {
        return query;
    }

    /**
     * Converts this retriever to the raw JSON object used on the wire, omitting the attributes
     * that were not set.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (query != null) {
            map.put("query", query);
        }
        return map;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private Object query;

        private Builder() {
        }

        private Builder(SimpleRetriever from) {
            this.query = from.query;
        }

        /**
         * The query conditions. The syntax is the same as the query parameter of the
         * QueryVectorsFusion operation.
         */
        public Builder query(Object query) {
            this.query = query;
            return this;
        }

        public SimpleRetriever build() {
            return new SimpleRetriever(this);
        }
    }
}
